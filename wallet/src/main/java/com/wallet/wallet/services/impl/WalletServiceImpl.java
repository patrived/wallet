package com.wallet.wallet.services.impl;

import com.wallet.wallet.entity.User;

import com.wallet.wallet.entity.UserWallet;
import com.wallet.wallet.repo.UserRepo;
import com.wallet.wallet.repo.WalletRepo;
import com.wallet.wallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WalletRepo walletRepo;


    @Override
    public User registerUser(String name, String email) {
        UserWallet wallet2 = new UserWallet();
        wallet2.setBalance(0.0);
        walletRepo.save(wallet2);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setWallet(wallet2);
        return userRepo.save(user);
    }

    @Override
    public String addMoney(Long walletid, Double amount) {
        UserWallet wallet = walletRepo.findById(walletid).orElseThrow();
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepo.save(wallet);
        return "Money Added.New balance " + wallet.getBalance();
    }

    @Override
    public String transferMoney(Long fromWalletId, Long toWalletId, Double amount) {
        UserWallet sender = walletRepo.findById(fromWalletId).orElseThrow();
        UserWallet reciver = walletRepo.findById(toWalletId).orElseThrow();
        if (sender.getBalance() < amount) throw new RuntimeException("Insufficent money");
        sender.setBalance(sender.getBalance() - amount);
        reciver.setBalance(reciver.getBalance() + amount);
        walletRepo.save(sender);
        walletRepo.save(reciver);
        return "Transfer done. Remaning  fund = " + sender.getBalance();
    }
}
