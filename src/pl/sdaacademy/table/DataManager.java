package pl.sdaacademy.table;

import pl.sdaacademy.model.BaseModel;

import java.util.List;

/**
 * Interface used for base data operation - CRUD
 *
 * @param <T> BaseModel details object
 */
public interface DataManager<T extends BaseModel> {

    void createRepository();

    void add(T object);

    void update(T object);

    List<T> getList();

}
