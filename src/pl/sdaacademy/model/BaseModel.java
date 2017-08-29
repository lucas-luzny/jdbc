package pl.sdaacademy.model;

public abstract class BaseModel {
    protected String id;

    public BaseModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id='" + id + '\'' +
                '}';
    }
}
