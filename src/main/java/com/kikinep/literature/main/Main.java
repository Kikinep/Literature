package com.kikinep.literature.main;

import com.kikinep.literature.model.Author;
import com.kikinep.literature.model.Book;
import com.kikinep.literature.model.BookData;
import com.kikinep.literature.model.SearchResults;
import com.kikinep.literature.repository.AuthorRepository;
import com.kikinep.literature.repository.BookRepository;
import com.kikinep.literature.service.APIRequest;
import com.kikinep.literature.service.DataMapper;

import java.util.ArrayList;
import java.util.List;
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
                
                1 - Add new book to collection
                2 - Display books
                3 - Display authors
                4 - Display authors alive in a certain year
                5 - Display books in a certain language
                
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
        System.out.println("Input the book's title and/or author:");
        String title = scanner.nextLine();

        String json = request.getData(BASE_URL + title.replace(" ", "%20"));
        SearchResults results = mapper.mapData(json, SearchResults.class);
        Optional<BookData> searchedBook = results.books().stream().findFirst();
        if (searchedBook.isPresent()) {
            Book book = new Book(searchedBook.get());
            System.out.println(book);
            System.out.println("Would you like to add this book to you collection? (Yes/No)");
            String option = scanner.nextLine();
            if(option.equalsIgnoreCase("Yes")) {
                List<Book> books = new ArrayList<>();
                books.add(book);
                if(bookRepository.findByTitleEquals(book.getTitle()).isEmpty()){
                    Optional<Author> searchedAuthor = authorRepository.findByNameEquals(book.getAuthor().getName());
                    if(searchedAuthor.isPresent()) {
                        Author author = searchedAuthor.get();
                        author.setBooks(books);
                        authorRepository.save(author);
                    } else {
                        book.getAuthor().setBooks(books);
                        authorRepository.save(book.getAuthor());
                    }
                } else {
                    System.out.println("Book title already registered!");
                }
            }
        } else {
            System.out.println("Book not found!");
        }
    }

    private void displayBooks() {
        List<Book> books = bookRepository.findAll();
        if(!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("No authors found!");
        }
    }

    private void displayAuthors() {
        List<Author> authors = authorRepository.findAll();
        if(!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
            System.out.println("No authors found!");
        }
    }

    private void displayAuthorsAlive() {
        System.out.println("Input year:");
        Integer year = scanner.nextInt();
        scanner.nextLine();
        List<Author> authors = authorRepository.findByBirthDateLessThanEqualAndDeathDateGreaterThanEqual(year, year);
        if(!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
            System.out.println("No authors found!");
        }
    }

    private void displayBooksByLanguage() {
        System.out.println("Input the two-letter language code:");
        String language = scanner.nextLine();
        List<Book> books = bookRepository.findByLanguageEquals(language);
        if(!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("No books found!");
        }
    }
}
