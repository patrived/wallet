package com.wallet.wallet.controller;


import com.wallet.wallet.entity.User;
import com.wallet.wallet.services.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletControllerTest {

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        String name = "John";
        String email = "john@example.com";
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        when(walletService.registerUser(name, email)).thenReturn(user);

        // Act
        ResponseEntity<User> response = walletController.registerUser(name, email);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(name, response.getBody().getName());
        assertEquals(email, response.getBody().getEmail());
        verify(walletService, times(1)).registerUser(name, email);
    }

    @Test
    public void testAddMoney() {
        // Arrange
        Long walletId = 1L;
        Double amount = 500.0;
        String expectedMessage = "Amount added successfully";

        when(walletService.addMoney(walletId, amount)).thenReturn(expectedMessage);

        // Act
        ResponseEntity<String> response = walletController.addMoney(walletId, amount);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody());
        verify(walletService, times(1)).addMoney(walletId, amount);
    }

    @Test
    public void testTransferMoney() {
        // Arrange
        Long fromWalletId = 1L;
        Long toWalletId = 2L;
        Double amount = 100.0;
        String expectedMessage = "Transfer successful";

        when(walletService.transferMoney(fromWalletId, toWalletId, amount)).thenReturn(expectedMessage);

        // Act
        ResponseEntity<String> response = walletController.transferMoney(fromWalletId, toWalletId, amount);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody());
        verify(walletService, times(1)).transferMoney(fromWalletId, toWalletId, amount);
    }
}
