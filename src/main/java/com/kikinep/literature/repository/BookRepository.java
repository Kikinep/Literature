package com.kikinep.literature.repository;

import com.kikinep.literature.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleEquals(String title);
    List<Book> findByLanguageEquals(String language);
    @Query("SELECT b FROM Book b ORDER BY b.downloads DESC LIMIT 3")
    List<Book> getTop3Books();
}
