package com.payMyBuddy.buddy.service;

import com.payMyBuddy.buddy.dao.BankAccountDao;
import com.payMyBuddy.buddy.dao.BankTransferDao;
import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.BankAccount;
import com.payMyBuddy.buddy.model.BankTransfer;
import com.payMyBuddy.buddy.model.TransferType;
import com.payMyBuddy.buddy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class BankTransferServiceTest {


    @TestConfiguration
    static class BankTransferServiceImplTestContextConfiguration {
        @Bean
        public BankTransferService bankTransferService() {
            return new BankTransferServiceImpl();
        }
    }

    @MockBean
    BankTransferDao bankTransferDao;

    @MockBean
    BankAccountDao bankAccountDao;

    @MockBean
    UserDao userDao;

    @Resource
    BankTransferService bankTransferService;

    static Date today = new Date();
    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);
    public static User userInvalid = new User("John", "Boyd", "jaboyd@email.com", "password",
            today);


    public static BankAccount bankAccount = new BankAccount("1234", "BANKNAME", user);
    public static BankAccount bankAccountInvalid = new BankAccount("1234", "BANKNAME", userInvalid);


    public static BankTransfer bankTransfer = new BankTransfer(1000.0,
            bankAccount, TransferType.INCOMING, today, "descritpion");


    @Test
    void addBankTransfer() {

        // GIVEN
        bankAccount.setId(1);
        bankTransfer.setAmount(1000.0);
        user.setId(1);
        given(bankAccountDao.getById(anyInt())).willReturn(Optional.of(bankAccount));
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(userDao.save(any(User.class))).willReturn(user);
        given(bankTransferDao.save(any(BankTransfer.class))).willReturn(bankTransfer);

        // WHEN
        boolean bankTransfer = bankTransferService.addBankTransfer(new BankTransfer(1000.0,
                bankAccount, TransferType.INCOMING, today, "descritpion"));

        // THEN
        verify(bankAccountDao, times(1)).getById(anyInt());
        verify(userDao, times(1)).getById(anyInt());
        verify(userDao, times(1)).save(any(User.class));
        verify(bankTransferDao, times(1)).save(any(BankTransfer.class));
        assertThat(bankTransfer).isEqualTo(true);
    }



    @Test
    void addBankTransfer_InvalidAccount() {
        // GIVEN
        bankTransfer.setAmount(1000.0);
        user.setId(1);
        given(bankAccountDao.getById(anyInt())).willReturn(Optional.of(bankAccount));
        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> bankTransferService.addBankTransfer(new BankTransfer(1000.0,
                        bankAccount, TransferType.INCOMING, today, "descritpion")));
    }

    @Test
    void addBankTransfer_InvalidUser() {

        // GIVEN
        bankAccount.setId(1);
        bankTransfer.setAmount(1000.0);
        given(bankAccountDao.getById(anyInt())).willReturn(Optional.of(bankAccountInvalid));
        given(userDao.getById(anyInt())).willReturn(Optional.of(userInvalid));
        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> bankTransferService.addBankTransfer(new BankTransfer(1000.0,
                        bankAccount, TransferType.INCOMING, today, "descritpion")));
    }

    @Test
    void addBankTransfer_InvalidAmount() {

        // GIVEN
        bankAccount.setId(1);
        user.setId(1);
        given(bankAccountDao.getById(anyInt())).willReturn(Optional.of(bankAccount));
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(userDao.save(any(User.class))).willReturn(user);
        given(bankTransferDao.save(any(BankTransfer.class))).willReturn(bankTransfer);
        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> bankTransferService.addBankTransfer(new BankTransfer(0.0,
                        bankAccount, TransferType.INCOMING, today, "descritpion")));

    }

    @Test
    void addBankTransfer_InvalidBankAccount() {

        // GIVEN
        user.setId(1);
        given(bankAccountDao.getById(anyInt())).willReturn(Optional.of(bankAccount));
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(userDao.save(any(User.class))).willReturn(user);
        given(bankTransferDao.save(any(BankTransfer.class))).willReturn(bankTransfer);
        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> bankTransferService.addBankTransfer(new BankTransfer(0.0,
                        bankAccount, TransferType.INCOMING, today, "descritpion")));

    }

    @Test
    void addBankTransfer_InvalidWallet() {

        // GIVEN
        user.setId(1);
        bankAccount.setId(1);
        userInvalid.setWallet(null);
        given(bankAccountDao.getById(anyInt())).willReturn(Optional.of(bankAccountInvalid));
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(userDao.save(any(User.class))).willReturn(user);
        given(bankTransferDao.save(any(BankTransfer.class))).willReturn(bankTransfer);
        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> bankTransferService.addBankTransfer(new BankTransfer(10000.0,
                        bankAccount, TransferType.INCOMING, today, "descritpion")));

    }

    @Test
    void addBankTransfer_Negative() {

        // GIVEN
        bankAccount.setId(1);
        bankTransfer.setAmount(1000.0);
        user.setId(1);
        given(bankAccountDao.getById(anyInt())).willReturn(Optional.of(bankAccount));
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(userDao.save(any(User.class))).willReturn(user);
        given(bankTransferDao.save(any(BankTransfer.class))).willReturn(bankTransfer);

        // WHEN & THEN
        assertThrows(NoSuchElementException.class,
                () -> bankTransferService.addBankTransfer(new BankTransfer(-10000.0,
                        bankAccount, TransferType.INCOMING, today, "descritpion")));
    }
    @Test
    void addBankTransfer_NegativeWallet() {

        // GIVEN
        bankAccount.setId(1);
        bankTransfer.setAmount(-1000.0);
        user.setId(1);
        user.setWallet(10000.0);
        given(bankAccountDao.getById(anyInt())).willReturn(Optional.of(bankAccount));
        given(userDao.getById(anyInt())).willReturn(Optional.of(user));
        given(userDao.save(any(User.class))).willReturn(user);
        given(bankTransferDao.save(any(BankTransfer.class))).willReturn(bankTransfer);

        // WHEN
        boolean bankTransfer = bankTransferService.addBankTransfer(new BankTransfer(-1000.0,
                bankAccount, TransferType.EXITING, today, "descritpion"));
        // THEN
        verify(bankAccountDao, times(1)).getById(anyInt());
        verify(userDao, times(1)).getById(anyInt());
        verify(userDao, times(1)).save(any(User.class));
        verify(bankTransferDao, times(1)).save(any(BankTransfer.class));
        assertThat(bankTransfer).isEqualTo(true);
    }

    public static List<BankTransfer> bankTransfers = new ArrayList<>();

    static {
        bankTransfers.add(bankTransfer);
        bankTransfers.add(bankTransfer);
        bankTransfers.add(bankTransfer);
    }

    @Test
    void getBankTransfers() {
        // GIVEN
        given(bankTransferDao.findAll()).willReturn(bankTransfers);
        // WHEN
        List<BankTransfer> bankTransfersAll = bankTransferService.getBankTransfers();
        // THEN
        verify(bankTransferDao, times(1)).findAll();
        assertThat(bankTransfersAll.size()).isEqualTo(3);
    }

    public static List<BankAccount> bankAccounts = new ArrayList<>();

    static {
        bankAccounts.add(bankAccount);
        bankAccounts.add(bankAccount);
        bankAccounts.add(bankAccount);
    }

    @Test
    void getBankTransferByUserId() {
        // GIVEN
        user.setId(1);
        bankAccount.setId(1);
        given(userDao.existsById(anyInt())).willReturn(true);
        given(bankAccountDao.findAllByUserId(anyInt())).willReturn(bankAccounts);
        given(bankTransferDao.findAllByBankAccountId(anyInt())).willReturn(bankTransfers);

        // WHEN
        List<BankTransfer> bankTransfersAll = bankTransferService.getBankTransferByUserId(1);
        // THEN
        verify(userDao, times(1)).existsById(anyInt());
        verify(bankAccountDao, times(1)).findAllByUserId(anyInt());
        verify(bankTransferDao, times(3)).findAllByBankAccountId(anyInt());

        assertThat(bankTransfersAll.size()).isEqualTo(9);
    }

    @Test
    void getBankTransferByUserId_InvalidUser() {
        // GIVEN
        user.setId(1);
        bankAccount.setId(1);
        given(userDao.existsById(anyInt())).willReturn(false);
        //given(bankAccountDao.findAllByUserId(anyInt())).willReturn(bankAccounts);
        //given(bankTransferDao.findAllByBankAccountId(anyInt())).willReturn(bankTransfers);

        // WHEN
        List<BankTransfer> bankTransfersAll = bankTransferService.getBankTransferByUserId(1);
        // THEN
        verify(userDao, times(1)).existsById(anyInt());
        // verify(bankAccountDao, times(1)).findAllByUserId(anyInt());
        //verify(bankTransferDao, times(3)).findAllByBankAccountId(anyInt());

        assertThat(bankTransfersAll).isEqualTo(null);
    }

    @Test
    void getBankTransferByUserId_InvalidAccount() {
        // GIVEN
        user.setId(1);
        // bankAccount.setId(1);
        given(userDao.existsById(anyInt())).willReturn(true);
        given(bankAccountDao.findAllByUserId(anyInt())).willReturn(bankAccounts);
        //given(bankTransferDao.findAllByBankAccountId(anyInt())).willReturn(bankTransfers);

        // WHEN
        List<BankTransfer> bankTransfersAll = bankTransferService.getBankTransferByUserId(1);
        // THEN
        verify(userDao, times(1)).existsById(anyInt());
        verify(bankAccountDao, times(1)).findAllByUserId(anyInt());
        //verify(bankTransferDao, times(3)).findAllByBankAccountId(anyInt());

        assertThat(bankTransfersAll).isEqualTo(Collections.emptyList());
    }

}