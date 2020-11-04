package com.payMyBuddy.buddy;

import com.payMyBuddy.buddy.dao.BankAccountDao;
import com.payMyBuddy.buddy.dao.BankTransferDao;
import com.payMyBuddy.buddy.dao.TransferDao;
import com.payMyBuddy.buddy.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BuddyApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	void main() {
		BuddyApplication.main(new String[] {});
	}

}
