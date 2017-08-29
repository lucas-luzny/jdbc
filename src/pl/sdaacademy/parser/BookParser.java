package pl.sdaacademy.parser;

import pl.sdaacademy.model.Book;
import pl.sdaacademy.model.BookType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookParser implements DataParser {
	@Override
	public List<Book> parseToList(ResultSet resultSet) {
		List<Book> tempList = null;
		try {
			tempList = new ArrayList<>();
			while (resultSet.next()){
				String book_id =resultSet.getString(Book.BOOK_ID_COLUMN);
				String book_name = resultSet.getString(Book.BOOK_NAME_COLUMN);
				BookType book_type = BookType.parseType(resultSet.getString(Book.BOOK_TYPE_COLUMN));
				String book_author_id = resultSet.getString(Book.BOOK_AUTHOR_ID_COLUMN);
				Book book = new Book(book_id,book_name,book_author_id, book_type);
				tempList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempList;
	}
}
