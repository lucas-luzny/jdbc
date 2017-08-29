package pl.sdaacademy.api;

import com.sun.rowset.CachedRowSetImpl;
import pl.sdaacademy.service.JDBCService;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

public class DBExecutor implements Executor{
	private JDBCService jdbcService;

	public DBExecutor(JDBCService jdbcService) {
		this.jdbcService = jdbcService;
	}

	@Override
	public void execute(Action action) {
		Statement statement = null;
		Connection connection = jdbcService.connect();
		try {
			statement = connection.createStatement();
			action.onExecute(statement);
			System.out.println("create execution completed!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(statement, connection);
		}
	}

	@Override
	public ResultSet executeQuery(Action action) {
		CachedRowSet cachedRowSet = null;
		Statement statement = null;
		ResultSet rs = null;
		Connection connection = jdbcService.connect();
		try {
			statement = connection.createStatement();
			rs = action.onExecuteQuery(statement);
			cachedRowSet = new CachedRowSetImpl();
			cachedRowSet.populate(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(statement, connection);
		}
		return cachedRowSet;
	}

	@Override
	public void execute(Action action, String sql) {
		PreparedStatement statement = null;
		Connection connection = jdbcService.connect();
		try {
			statement = connection.prepareStatement(sql);
			action.onExecute(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void closeConnection (Statement statement, Connection connection){
		if(statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			jdbcService.disconnect();
	}
}
