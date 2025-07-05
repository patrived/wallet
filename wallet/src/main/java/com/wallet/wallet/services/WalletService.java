package com.wallet.wallet.services;

import com.wallet.wallet.entity.User;

public interface WalletService {

    public User registerUser(String name, String email);
    public String addMoney(Long walletid, Double amount);
    public String transferMoney(Long fromWalletId,Long toWalletId,Double amount);



}
