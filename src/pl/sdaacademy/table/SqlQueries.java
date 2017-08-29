package pl.sdaacademy.table;

import pl.sdaacademy.model.BaseModel;

/**
 * Interface used for base db queries
 *
 * @param <T> BaseModel details object
 */
public interface SqlQueries<T extends BaseModel> {

    String getCreateTableQuery();

    String getSelectQuery();

    String getInsertQuery(T object);

    String getUpdateQuery();
}
