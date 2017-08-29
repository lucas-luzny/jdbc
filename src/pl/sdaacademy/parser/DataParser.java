package pl.sdaacademy.parser;

import pl.sdaacademy.model.BaseModel;

import java.sql.ResultSet;
import java.util.List;

/**
 * Parses interface used to parse ResultSet collection to List implementation
 *
 * @param <T> BaseModel details object
 */
public interface DataParser<T extends BaseModel> {

    List<T> parseToList(ResultSet resultSet);
}
