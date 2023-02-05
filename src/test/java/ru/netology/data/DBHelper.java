package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DBHelper {

    @SneakyThrows
    public static String getAuthCode(String logUser) {
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

    @SneakyThrows
    public static void cleanUpDB() {
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
}
