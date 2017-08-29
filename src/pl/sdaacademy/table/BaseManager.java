package pl.sdaacademy.table;

import pl.sdaacademy.api.Executor;
import pl.sdaacademy.config.Config;
import pl.sdaacademy.model.BaseModel;
import pl.sdaacademy.parser.DataParser;


public abstract class BaseManager<T extends BaseModel> implements DataManager<T>, SqlQueries<T> {

    protected Executor executor;
    protected Config config;
    protected DataParser<T> parser;

    public BaseManager(Config config, Executor executor, DataParser<T> parser) {
        this.config = config;
        this.executor = executor;
        this.parser = parser;
    }

}
