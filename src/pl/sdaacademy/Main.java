package pl.sdaacademy;

import pl.sdaacademy.api.DBExecutor;
import pl.sdaacademy.api.Executor;
import pl.sdaacademy.config.Config;
import pl.sdaacademy.model.Author;
import pl.sdaacademy.model.Book;
import pl.sdaacademy.model.BookType;
import pl.sdaacademy.parser.AuthorParser;
import pl.sdaacademy.parser.BookParser;
import pl.sdaacademy.parser.DataParser;
import pl.sdaacademy.service.JDBCService;
import pl.sdaacademy.service.PostgreSQLService;
import pl.sdaacademy.table.AuthorsManager;
import pl.sdaacademy.table.BooksManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Menu menu = new Menu();

    public static void main(String[] args) throws SQLException{
        final Config config = new Config("library", "jdbc:postgresql://localhost/library", "lluzny", "sda123");
        final JDBCService service = new PostgreSQLService(config);
        Connection connection = null;
        final Executor executor = new DBExecutor(service);
        final DataParser<Book> bookDataParser = new BookParser();
        final DataParser<Author> authorDataParser = new AuthorParser();
        final BooksManager booksManager = new BooksManager(config,executor, bookDataParser);
        final AuthorsManager authorsManager = new AuthorsManager(config,executor,authorDataParser);
        Book book1 = new Book("1", "Hobbit", "1", BookType.FANTASY);
        Book book2 = new Book ("2", "Pan Tadeusz", "2", BookType.DEFAULT);
        Book book3 = new Book("3", "Ojciec chrzestny", "3", BookType.DEFAULT);
        Author author1 = new Author("1", "J.R.R", "Tolkien", "1892");
        Author author2 = new Author("2", "Adam", "Mickiewicz", "1798");
        Author author3 = new Author("3", "Mario", "Puzo", "1920");
        Book bookUpdate = new Book("1", "Władca Pierścieni", "1", BookType.FANTASY);
        Author authorUpdate = new Author("1", "John Ronald Reuel", "Tolkien", "1892");
        while (true) {
            menu.showMenu();
            int menuOption = menu.chooseMenuOption();
            System.out.println("Menu option: " + menuOption);
            switch (menuOption) {
                case 1:
                    System.out.println("Option 1");
                    connection = service.connect();
                    break;
                case 2:
                    System.out.println("Option 2");
                    service.disconnect();
                    break;
                case 3:
                    System.out.println("Option 3");
                    booksManager.createRepository();
                    break;
                case 4:
                    System.out.println("Option 4");
                    authorsManager.createRepository();
                    break;
                case 5:
                    System.out.println("Closing app!");
                    System.exit(0);
                    break;
                case 6:
                    System.out.println("Option 6");
                    booksManager.add(book3);
                    break;
                case 7:
                    System.out.println("Option 7");
                    authorsManager.add(author3);
                    break;
                case 8:
                    System.out.println("Option 8");
                    List<Book> listaDoDruku = null;
                    listaDoDruku = booksManager.getList();
                    for (Book book : listaDoDruku) {
                        System.out.println(book);
                    }
                    break;
                case 9:
                    System.out.println("Option 9");
                    List<Author> listaDoDruku2 = null;
                    listaDoDruku2 = authorsManager.getList();
                    for (Author author : listaDoDruku2) {
                        System.out.println(author);
                    };
                    break;
                case 10:
                    System.out.println("Option 10");
                    booksManager.update(bookUpdate);
                    break;
                case 11:
                    System.out.println("Option 11");
                    //authorsManager.update(authorUpdate);
                    authorsManager.update(author1);
                    break;
                case 20:
                    System.out.println("test");
                    //System.out.println(booksManager.getInsertQuery(book1));
                    //System.out.println(authorsManager.getInsertQuery(author1));
                    //System.out.println(booksManager.getUpdateQuery());
                    break;
                default:
            }
        }
    }

    private static class Menu {

        void showMenu() {
            System.out.println("Choose option");
            System.out.println("1. Establish connection: ");
            System.out.println("2. Close connection");
            System.out.println("3. Create Book table");
            System.out.println("4. Create Author table");
            System.out.println("5. Close");
            System.out.println("6. Add Book object");
            System.out.println("7. Add Author object");
            System.out.println("8. Get Books list");
            System.out.println("9. Get Authors list");
            System.out.println("10. Update Book");
            System.out.println("11. Update Author");
            //add other necessary menu options
        }

        int chooseMenuOption() {
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter a menu option: ");
            return reader.nextInt();
        }
    }
}
