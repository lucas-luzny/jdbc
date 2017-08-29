package pl.sdaacademy.api;


import java.sql.ResultSet;

/**
 * Base execution interface used for data operation
 */
public interface Executor {

    void execute(Action action);

    ResultSet executeQuery(Action action);

    void execute(Action action, String sql);
}
