package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.model.BankTransfer;

import java.util.List;

public interface BankTransferService {
    boolean addBankTransfer(BankTransfer bankTransfer);

    List<BankTransfer> getBankTransfers();

    List<BankTransfer> getBankTransferByUserId(Integer userId);
}
