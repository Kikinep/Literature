package com.kikinep.literature.repository;

import com.kikinep.literature.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameEquals(String name);
    List<Author> findByBirthDateLessThanEqualAndDeathDateGreaterThanEqual(Integer year, Integer year2);
}
