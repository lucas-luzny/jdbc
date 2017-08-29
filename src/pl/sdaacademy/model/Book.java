package pl.sdaacademy.model;

public class Book extends BaseModel {

    public static final String BOOK_TABLE = ".BOOKS";
    public static final String BOOK_ID_COLUMN = "BOOK_ID";
    public static final String BOOK_NAME_COLUMN = "BOOK_NAME";
    public static final String BOOK_AUTHOR_ID_COLUMN = "BOOK_AUTHOR_ID";
    public static final String BOOK_TYPE_COLUMN ="BOOK_TYPE";


    private String name;
    private String bookAuthorId;
    private BookType bookType;

    public Book(String id, String name, String bookAuthorId, BookType bookType) {
        super(id);
        this.name = name;
        this.bookAuthorId = bookAuthorId;
        this.bookType = bookType;
    }

    public String getName() {
        return name;
    }

    public String getBookAuthorId() {
        return bookAuthorId;
    }

    public BookType getBookType() {
        return bookType;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", bookAuthorId='" + bookAuthorId + '\'' +
                ", bookType=" + bookType +
                "} " + super.toString();
    }
}
