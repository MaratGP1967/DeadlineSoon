package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class TestSQLConnection {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanUp() {
        DBHelper.cleanUpDB();
    }

    @Test
    void shouldGetDashboardTest() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.setLogin(authInfo);
        var code = authInfo.getLogin();
        verificationPage.validVerify(DBHelper.getAuthCode(code));
    }

    @Test
    void shouldNotGetDashboardThreeTimesTest() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getNotAuthInfo();
        loginPage.setLogin(authInfo);
        loginPage.notValidPopUp();
        loginPage.setLogin(authInfo);
        loginPage.notValidPopUp();
        loginPage.setLogin(authInfo);
        loginPage.notValidPopUp();
        loginPage.setLogin(authInfo);
        loginPage.notValidPopUp();
    }

}
