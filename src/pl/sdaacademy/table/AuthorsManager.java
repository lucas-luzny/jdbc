package pl.sdaacademy.table;

import pl.sdaacademy.api.ActionAdapter;
import pl.sdaacademy.api.Executor;
import pl.sdaacademy.config.Config;
import pl.sdaacademy.model.Author;
import pl.sdaacademy.parser.AuthorParser;
import pl.sdaacademy.parser.DataParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorsManager extends BaseManager<Author> {

    public AuthorsManager(Config config, Executor executor, DataParser<Author> dataParser) {
        super(config, executor, dataParser);
    }

    @Override
    public void createRepository() {
        executor.execute(new ActionAdapter() {
            @Override
            public void onExecute(Statement statement) {
                try {
                    statement.execute(getCreateTableQuery());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void add(final Author object) {
        executor.execute(new ActionAdapter() {
            @Override
            public void onExecute(Statement statement) {
                try {
                    statement.execute(getInsertQuery(object));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void update(Author object) {
        executor.execute(new ActionAdapter() {
            @Override
            public void onExecute(PreparedStatement statement) {
                try {
                    statement.setString(1, object.getFirstName());
                    statement.setString(2, object.getLastName());
                    statement.setString(3, object.getYearOfBirth());
                    statement.setString(4, object.getId());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, getUpdateQuery());
    }
    @Override
    public List<Author> getList(){
        ResultSet rs = executor.executeQuery(new ActionAdapter() {
            @Override
            public ResultSet onExecuteQuery(Statement statement) {
                ResultSet rs2 = null;
                try {
                    rs2 = statement.executeQuery(getSelectQuery());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return rs2;
            }
        });
        return parser.parseToList(rs);
    }

    @Override
    public String getCreateTableQuery() {
        return String.format("create table %s%s(%s varchar(32) not null, %s varchar(32), %s varchar(32), %s varchar(4), PRIMARY KEY(%s));",
                config.getDbName(), Author.AUTHOR_TABLE, Author.AUTHOR_ID_COLUMN, Author.FIRST_NAME_COLUMN,
                Author.LAST_NAME_COLUMN, Author.YEAR_OF_BIRTH_COLUMN, Author.AUTHOR_ID_COLUMN);

    }

    @Override
    public String getSelectQuery() {
        return String.format("select %s, %s, %s, %s from %s%s",
        Author.AUTHOR_ID_COLUMN,
        Author.FIRST_NAME_COLUMN,
        Author.LAST_NAME_COLUMN,
        Author.YEAR_OF_BIRTH_COLUMN,
        config.getDbName(),
        Author.AUTHOR_TABLE);
    }

    @Override
    public String getInsertQuery(Author object) {
        return String.format("INSERT INTO %s%s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', '%s');",
                config.getDbName(), Author.AUTHOR_TABLE,  Author.AUTHOR_ID_COLUMN, Author.FIRST_NAME_COLUMN, Author.LAST_NAME_COLUMN, Author.YEAR_OF_BIRTH_COLUMN,
                object.getId(),
                object.getFirstName(),
                object.getLastName(),
                object.getYearOfBirth());
    }

    @Override
    public String getUpdateQuery() {
        return String.format("update %s%s set %s = ?, %s = ?, %s = ? WHERE %s = ?",
                config.getDbName(), Author.AUTHOR_TABLE, Author.FIRST_NAME_COLUMN, Author.LAST_NAME_COLUMN,
                Author.YEAR_OF_BIRTH_COLUMN, Author.AUTHOR_ID_COLUMN);
    }
}
