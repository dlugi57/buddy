package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.model.Transfer;

import java.util.List;

public interface TransferService {
    boolean addTransfer(Transfer transfer);

    List<Transfer> getTransfers();

    List<Transfer> getTransfersByUserId(Integer user);
}
