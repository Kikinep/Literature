package com.kikinep.literature.repository;

import com.kikinep.literature.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
