package pl.sdaacademy.parser;

import pl.sdaacademy.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorParser implements DataParser<Author> {
	@Override
	public List<Author> parseToList(ResultSet resultSet) {
		List<Author> tempList = null;
		try {
			tempList = new ArrayList<>();
			while (resultSet.next()){
				String author_id =resultSet.getString(Author.AUTHOR_ID_COLUMN);
				String first_name = resultSet.getString(Author.FIRST_NAME_COLUMN);
				String last_name = resultSet.getString(Author.LAST_NAME_COLUMN);
				String year_of_birth = resultSet.getString(Author.YEAR_OF_BIRTH_COLUMN);
				Author author = new Author(author_id, first_name, last_name, year_of_birth);
				tempList.add(author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempList;
	}
}
