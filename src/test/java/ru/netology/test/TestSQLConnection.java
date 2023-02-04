package ru.netology.test;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.open;

public class TestSQLConnection {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    @SneakyThrows
    static void cleanUp() {
        var runner = new QueryRunner();
        var auth_codesSQL = "DELETE FROM auth_codes;";
        var cardsSQL = "DELETE FROM cards;";
        var usersSQL = "DELETE FROM users;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runner.update(conn, auth_codesSQL);
            runner.update(conn, cardsSQL);
            runner.update(conn, usersSQL);
        }
    }

    @Test
    void shouldGetDashboardTest() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var code = authInfo.getLogin();
        verificationPage.validVerify(getAuthCode(code));
    }

    @Test
    void shouldNotGetDashboardThreeTimesTest() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getNotAuthInfo();
        var popUp1 = loginPage.notValidLogin(authInfo);
        popUp1.notValidPopUp();
        var popUp2 =loginPage.notValidLogin(authInfo);
        popUp2.notValidPopUp();
        var popUp3 =loginPage.notValidLogin(authInfo);
        popUp3.notValidPopUp();
        var popUp4 =loginPage.notValidLogin(authInfo);
        popUp4.notValidPopUp();
    }

    @SneakyThrows
    private String getAuthCode(String logUser) {
        var codeSQL = "SELECT code FROM auth_codes JOIN users ON auth_codes.user_id=users.id WHERE users.login = ?;";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            return runner.query(conn, codeSQL, new ScalarHandler<>(), logUser);
        }
    }
}
