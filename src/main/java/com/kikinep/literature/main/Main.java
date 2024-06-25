package com.kikinep.literature.main;

import com.kikinep.literature.model.Book;
import com.kikinep.literature.model.BookData;
import com.kikinep.literature.model.SearchResults;
import com.kikinep.literature.repository.AuthorRepository;
import com.kikinep.literature.repository.BookRepository;
import com.kikinep.literature.service.APIRequest;
import com.kikinep.literature.service.DataMapper;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final APIRequest request = new APIRequest();
    private final DataMapper mapper = new DataMapper();
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final String BASE_URL = "https://gutendex.com/books/?search=";

    public Main(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void userMenu() {
        int selection;
        String menu = """
                What would you like to do?
                
                1 - Register new book
                2 - Display all books
                3 - Display all authors
                4 - Display authors alive within a certain time frame
                5 - Display books by language
                
                0 - Exit application
                """;
        do {
            System.out.println(menu);
            selection = scanner.nextInt();
            scanner.nextLine();

            switch (selection) {
                case 1:
                    addBook();
                    break;
                case 2:
                    displayBooks();
                    break;
                case 3:
                    displayAuthors();
                    break;
                case 4:
                    displayAuthorsAlive();
                    break;
                case 5:
                    displayBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Exiting app...");
                    break;
                default:
                    System.out.println("Please select a valid option from the menu!");
            }
        } while (selection !=0);
    }

    private void addBook() {
        System.out.println("Enter the title of the book:");
        String title = scanner.nextLine();

        String json = request.getData(BASE_URL + title.replace(" ", "%20"));
        SearchResults results = mapper.mapData(json, SearchResults.class);
        Optional<BookData> searchedBook = results.books().stream().findFirst();
        if (searchedBook.isPresent()) {
            Book book = new Book(searchedBook.get());
            System.out.println(book);
            authorRepository.save(book.getAuthor());
            bookRepository.save(book);
        } else {
            System.out.println("Book not found!");
        }
    }

    private void displayBooks() {
        System.out.println(bookRepository.findAll());
    }

    private void displayAuthors() {
        System.out.println(authorRepository.findAll());
    }

    private void displayAuthorsAlive() {
    }

    private void displayBooksByLanguage() {
    }
}
