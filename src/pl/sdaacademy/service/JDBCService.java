package pl.sdaacademy.service;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * Service used for base jdbc operations
 */
public interface JDBCService {

    Connection connect();

    void disconnect();

    Connection getConnection();
}
