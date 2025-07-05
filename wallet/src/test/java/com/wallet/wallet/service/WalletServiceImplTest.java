package com.wallet.wallet.service;

import com.wallet.wallet.entity.User;
import com.wallet.wallet.entity.UserWallet;
import com.wallet.wallet.repo.UserRepo;
import com.wallet.wallet.repo.WalletRepo;
import com.wallet.wallet.services.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private WalletRepo walletRepo;

    @InjectMocks
    private WalletServiceImpl walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_CreatesUserWithWallet() {

        String name = "John";
        String email = "john@example.com";

        when(walletRepo.save(any(UserWallet.class))).thenAnswer(i -> i.getArgument(0));
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));


        User registeredUser = walletService.registerUser(name, email);


        assertNotNull(registeredUser);
        assertEquals(name, registeredUser.getName());
        assertEquals(email, registeredUser.getEmail());
        assertNotNull(registeredUser.getWallet());
        assertEquals(0.0, registeredUser.getWallet().getBalance());

        verify(walletRepo, times(1)).save(any(UserWallet.class));
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testAddMoney_Success() {

        Long walletId = 1L;
        Double initialBalance = 100.0;
        Double amountToAdd = 50.0;

        UserWallet wallet = new UserWallet();
        wallet.setId(walletId);
        wallet.setBalance(initialBalance);

        when(walletRepo.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepo.save(any(UserWallet.class))).thenReturn(wallet);


        String response = walletService.addMoney(walletId, amountToAdd);


        assertEquals("Money Added.New balance " + (initialBalance + amountToAdd), response);
        verify(walletRepo, times(1)).save(wallet);
    }

    @Test
    void testTransferMoney_Success() {

        Long fromId = 1L;
        Long toId = 2L;
        Double initialSenderBalance = 100.0;
        Double initialReceiverBalance = 20.0;
        Double amount = 50.0;

        UserWallet sender = new UserWallet();
        sender.setId(fromId);
        sender.setBalance(initialSenderBalance);

        UserWallet receiver = new UserWallet();
        receiver.setId(toId);
        receiver.setBalance(initialReceiverBalance);

        when(walletRepo.findById(fromId)).thenReturn(Optional.of(sender));
        when(walletRepo.findById(toId)).thenReturn(Optional.of(receiver));
        when(walletRepo.save(any(UserWallet.class))).thenAnswer(i -> i.getArgument(0));


        String result = walletService.transferMoney(fromId, toId, amount);


        assertEquals("Transfer done. Remaning  fund = " + (initialSenderBalance - amount), result);
        assertEquals(50.0, sender.getBalance());
        assertEquals(70.0, receiver.getBalance());

        verify(walletRepo, times(1)).save(sender);
        verify(walletRepo, times(1)).save(receiver);
    }

    @Test
    void testTransferMoney_InsufficientBalance() {

        Long fromId = 1L;
        Long toId = 2L;
        Double senderBalance = 30.0;
        Double amount = 50.0;

        UserWallet sender = new UserWallet();
        sender.setId(fromId);
        sender.setBalance(senderBalance);

        UserWallet receiver = new UserWallet();
        receiver.setId(toId);
        receiver.setBalance(0.0);

        when(walletRepo.findById(fromId)).thenReturn(Optional.of(sender));
        when(walletRepo.findById(toId)).thenReturn(Optional.of(receiver));


        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                walletService.transferMoney(fromId, toId, amount)
        );

        assertEquals("Insufficent money", exception.getMessage());
        verify(walletRepo, never()).save(sender);
        verify(walletRepo, never()).save(receiver);
    }
}
