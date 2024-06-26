package com.kikinep.literature.repository;

import com.kikinep.literature.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameEquals(String name);
    @Query("SELECT a FROM Author a WHERE a.birthDate <= :year AND a.deathDate >= :year")
    List<Author> findAuthorsByYearAlive(Integer year);
}
