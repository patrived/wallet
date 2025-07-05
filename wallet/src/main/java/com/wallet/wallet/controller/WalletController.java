package com.wallet.wallet.controller;

import com.wallet.wallet.entity.User;
import com.wallet.wallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallet/")
public class WalletController {
    @Autowired
    private WalletService walletService;
@PostMapping("register")
    public ResponseEntity<User> registerUser(@RequestParam String name, @RequestParam String email)
    {
        return ResponseEntity.ok(this.walletService.registerUser(name,email));
    }
    @PostMapping("add")
    public ResponseEntity<String> addMoney(@RequestParam Long walletid,
                                           @RequestParam Double amount)
    {
        return ResponseEntity.ok(this.walletService.addMoney(walletid,amount));
    }
    @PostMapping("transfer")
    public ResponseEntity <String> transferMoney(@RequestParam Long fromWalletId,
                                                 @RequestParam Long toWalletId,
                                                 @RequestParam Double amount){
    return  ResponseEntity.ok(this.walletService.transferMoney(fromWalletId,toWalletId,amount));

    }
}
