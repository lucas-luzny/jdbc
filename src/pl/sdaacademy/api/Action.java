package pl.sdaacademy.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Action used by {@link Executor}
 */
public interface Action {
    void onExecute(Statement statement);

    void onExecute(PreparedStatement statement);

    ResultSet onExecuteQuery(Statement statement);
}
