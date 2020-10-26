package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.model.BankAccount;

import java.util.List;

public interface BankAccountService {
    boolean addBankAccount(BankAccount bankAccount);

    boolean updateBankAccount(BankAccount bankAccount);

    boolean deleteBankAccount(BankAccount bankAccount);

    List<BankAccount> getBankAccounts();
}
