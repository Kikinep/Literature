package com.kikinep.literature.repository;

import com.kikinep.literature.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
