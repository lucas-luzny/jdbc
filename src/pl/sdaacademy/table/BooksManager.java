package pl.sdaacademy.table;

import pl.sdaacademy.api.Action;
import pl.sdaacademy.api.ActionAdapter;
import pl.sdaacademy.api.Executor;
import pl.sdaacademy.config.Config;
import pl.sdaacademy.model.Author;
import pl.sdaacademy.model.Book;
import pl.sdaacademy.parser.BookParser;
import pl.sdaacademy.parser.DataParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BooksManager extends BaseManager<Book> {

    public BooksManager(Config config, Executor executor, DataParser<Book> dataParser) {
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
    public void add(final Book object) {
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
    public void update(Book object) {
        executor.execute(new ActionAdapter() {
            @Override
            public void onExecute(PreparedStatement statement) {
                try {
                    statement.setString(1, object.getName());
                    statement.setString(2, object.getBookAuthorId());
                    statement.setString(3, object.getBookType().toString());
                    statement.setString(4, object.getId());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }, getUpdateQuery());
    }

    @Override
    public List<Book> getList() {
        ResultSet rs  = executor.executeQuery(new ActionAdapter() {
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
        return String.format("create table %s%s(%s varchar(32) not null, %s varchar(32), %s varchar(32), %s varchar(32),  PRIMARY KEY(%s), FOREIGN KEY(%s) REFERENCES %s%s(%s))",
        config.getDbName(), Book.BOOK_TABLE, Book.BOOK_ID_COLUMN, Book.BOOK_NAME_COLUMN, Book.BOOK_TYPE_COLUMN,
                Book.BOOK_AUTHOR_ID_COLUMN, Book.BOOK_ID_COLUMN, Book.BOOK_AUTHOR_ID_COLUMN,
                config.getDbName(), Author.AUTHOR_TABLE, Author.AUTHOR_ID_COLUMN);
    }

    @Override
    public String getSelectQuery() {
        return String.format("select %s, %s, %s, %s from %s%s",
        Book.BOOK_ID_COLUMN,
        Book.BOOK_NAME_COLUMN,
        Book.BOOK_TYPE_COLUMN,
        Book.BOOK_AUTHOR_ID_COLUMN,
        config.getDbName(),
        Book.BOOK_TABLE);
    }

    @Override
    public String getInsertQuery(Book object) {
        return String.format("INSERT INTO %s%s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', '%s');",
                config.getDbName(), Book.BOOK_TABLE, Book.BOOK_ID_COLUMN, Book.BOOK_NAME_COLUMN, Book.BOOK_TYPE_COLUMN, Book.BOOK_AUTHOR_ID_COLUMN,
                object.getId(),
                object.getName(),
                object.getBookType(),
                object.getBookAuthorId());

    }

    @Override
    public String getUpdateQuery() {
        return String.format("UPDATE %s%s SET %s = ?, %s = ?, %s =? WHERE %s = ?;", config.getDbName(), Book.BOOK_TABLE,
                Book.BOOK_NAME_COLUMN,
                Book.BOOK_AUTHOR_ID_COLUMN,
                Book.BOOK_TYPE_COLUMN,
                Book.BOOK_ID_COLUMN);
    }
}
