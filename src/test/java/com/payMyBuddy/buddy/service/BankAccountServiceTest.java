package com.payMyBuddy.buddy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payMyBuddy.buddy.dao.BankAccountDao;
import com.payMyBuddy.buddy.dao.UserDao;
import com.payMyBuddy.buddy.model.BankAccount;
import com.payMyBuddy.buddy.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class BankAccountServiceTest {


    @TestConfiguration
    static class BankAccountServiceImplTestContextConfiguration {
        @Bean
        public BankAccountService bankAccountService() {
            return new BankAccountServiceImpl();
        }
    }

    @Resource
    BankAccountService bankAccountService;

    @MockBean
    BankAccountDao bankAccountDao;

    @MockBean
    UserDao userDao;

    ObjectMapper mapper = new ObjectMapper();
    static Date today = new Date();
    public static User user = new User("John", "Boyd", "jaboyd@email.com", "password", today);

    public static BankAccount bankAccount = new BankAccount("1234", "BANKNAME", user);

    @Test
    void addBankAccount() {
        // GIVEN
        bankAccount.setId(1);
        given(bankAccountDao.save(any(BankAccount.class))).willReturn(bankAccount);
        // WHEN
        boolean bankAccount = bankAccountService.addBankAccount(new BankAccount("1234", "BANKNAME", user));
        // THEN
        verify(bankAccountDao, times(1)).save(any(BankAccount.class));

        assertThat(bankAccount).isEqualTo(true);

    }

    @Test
    void addBankAccount_NotExist() {
        // GIVEN
        given(bankAccountDao.save(any(BankAccount.class))).willReturn(null);
        // WHEN
        boolean bankAccount = bankAccountService.addBankAccount(new BankAccount("1234", "BANKNAME", user));
        // THEN
        verify(bankAccountDao, times(1)).save(any(BankAccount.class));

        assertThat(bankAccount).isEqualTo(false);
    }

    @Test
    void updateBankAccount() {
        // GIVEN
        given(bankAccountDao.existsByNumber(anyString())).willReturn(true);
        bankAccount.setId(1);
        given(bankAccountDao.save(any(BankAccount.class))).willReturn(bankAccount);
        // WHEN
        boolean bankAccount = bankAccountService.updateBankAccount(new BankAccount("1234", "BANKNAME", user));
        // THEN
        verify(bankAccountDao, times(1)).existsByNumber(anyString());
        verify(bankAccountDao, times(1)).save(any(BankAccount.class));

        assertThat(bankAccount).isEqualTo(true);
    }

    @Test
    void updateBankAccount_Exist() {
        // GIVEN
        given(bankAccountDao.existsByNumber(anyString())).willReturn(false);
        // WHEN
        boolean bankAccount = bankAccountService.updateBankAccount(new BankAccount("1234", "BANKNAME", user));
        // THEN
        verify(bankAccountDao, times(1)).existsByNumber(anyString());

        assertThat(bankAccount).isEqualTo(false);
    }

    @Test
    void updateBankAccount_Invalid() {
        // GIVEN
        given(bankAccountDao.existsByNumber(anyString())).willReturn(false);
        // WHEN
        boolean bankAccount = bankAccountService.updateBankAccount(new BankAccount("1234", "BANKNAME", user));
        // THEN
        verify(bankAccountDao, times(1)).existsByNumber(anyString());

        assertThat(bankAccount).isEqualTo(false);
    }


    @Test
    void deleteBankAccount() {
        // GIVEN
        given(bankAccountDao.existsById(anyInt())).willReturn(true);
        bankAccount.setId(1);

        // WHEN
        boolean bankAccountResult = bankAccountService.deleteBankAccount(bankAccount);
        // THEN
        verify(bankAccountDao, times(1)).existsById(anyInt());
        verify(bankAccountDao, times(1)).delete(any(BankAccount.class));

        assertThat(bankAccountResult).isEqualTo(true);
    }

    @Test
    void deleteBankAccount_Exist() {
        // GIVEN
        given(bankAccountDao.existsById(anyInt())).willReturn(false);
        // WHEN
        bankAccount.setId(1);

        // WHEN
        boolean bankAccountResult = bankAccountService.deleteBankAccount(bankAccount);
        // THEN
        verify(bankAccountDao, times(1)).existsById(anyInt());

        assertThat(bankAccountResult).isEqualTo(false);
    }


    public static List<BankAccount> bankAccounts = new ArrayList<>();

    static {
        bankAccounts.add(bankAccount);
        bankAccounts.add(bankAccount);
        bankAccounts.add(bankAccount);
    }

    @Test
    void getBankAccounts() {
        // GIVEN
        given(bankAccountDao.findAll()).willReturn(bankAccounts);
        // WHEN
        List<BankAccount> bankAccountsAll = bankAccountService.getBankAccounts();
        // THEN
        verify(bankAccountDao, times(1)).findAll();
        assertThat(bankAccountsAll.size()).isEqualTo(3);
    }

    @Test
    void getBankAccountsByUserId() {
        // GIVEN
        user.setId(1);
        given(userDao.existsById(anyInt())).willReturn(true);
        given(bankAccountDao.findAllByUserId(anyInt())).willReturn(bankAccounts);
        // WHEN
        List<BankAccount> bankAccountsAll = bankAccountService.getBankAccountsByUserId(1);
        // THEN
        verify(userDao, times(1)).existsById(anyInt());
        verify(bankAccountDao, times(1)).findAllByUserId(anyInt());
        assertThat(bankAccountsAll.size()).isEqualTo(3);
    }

    @Test
    void getBankAccountsByUserId_NotFound() {
        // GIVEN
        user.setId(1);
        given(userDao.existsById(anyInt())).willReturn(false);
        // WHEN
        List<BankAccount> bankAccountsAll = bankAccountService.getBankAccountsByUserId(1);
        // THEN
        verify(userDao, times(1)).existsById(anyInt());
        assertThat(bankAccountsAll).isEqualTo(null);
    }
}