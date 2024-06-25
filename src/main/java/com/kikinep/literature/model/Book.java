package com.kikinep.literature.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @ManyToOne
    private Author author;
    private String language;
    private Integer downloads;

    public Book(BookData d) {
        this.title = d.title();
        this.author = new Author(d.authors().stream().findFirst().orElse(new AuthorData(null, null, null)));
        this.language = d.languages().stream().findFirst().orElse(null);
        this.downloads = d.downloads();
    }

    public Book() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return String.format("""
                ------ BOOK ------
                Title: %s
                Author: %s
                Language: %s
                Downloads: %d
                ------------------
                """, title, author.getName(), language, downloads);
    }
}
