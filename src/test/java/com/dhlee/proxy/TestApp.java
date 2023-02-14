package com.dhlee.proxy;

import static com.dhlee.proxy.jdbc.GetConnectionHandler.proxy;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TestApp {

    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();

        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", "postgres");
        props.setProperty("dataSource.password", "postgres");
        props.setProperty("dataSource.databaseName", "postgres");
        props.put("dataSource.logWriter", new PrintWriter(System.out));

        HikariConfig config = new HikariConfig(props);

        try(HikariDataSource ds = new HikariDataSource(config)) {
            DataSource dataSource = proxy(ds);
            try(Connection connection = dataSource.getConnection()) {
                System.out.println("Should be printed after the proxy");
            }
        }
    }

}
