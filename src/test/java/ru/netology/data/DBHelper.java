package ru.netology.data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {

    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getAuthCode(String logUser) {
        var codeSQL = "SELECT code FROM auth_codes JOIN users ON auth_codes.user_id=users.id WHERE users.login = ?;";
        var runner = new QueryRunner();
        try (
                var conn = connectToSQL();
        ) {
            return new VerificationCode(runner.query(conn, codeSQL, new ScalarHandler<>(), logUser));
        }
    }

    @SneakyThrows
    public static void cleanUpDB() {
        var runner = new QueryRunner();
        var auth_codesSQL = "DELETE FROM auth_codes;";
        var cardsSQL = "DELETE FROM cards;";
        var usersSQL = "DELETE FROM users;";

        try (
                var conn = connectToSQL()
        ) {
            runner.update(conn, auth_codesSQL);
            runner.update(conn, cardsSQL);
            runner.update(conn, usersSQL);
        }
    }

    @SneakyThrows
    public static Connection connectToSQL() {
        var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        return conn;
    }

}
