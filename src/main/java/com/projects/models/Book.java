package com.projects.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    // International Standard Book Number
    @Column(nullable = false, unique = true)
    private String bIsbn;

    @Column(nullable = false)
    private String bName;

    @Column(nullable = false)
    private String bSerialName;

    @Column(nullable = false)
    private String bDescription;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(name = "fk_authors", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
            @JoinColumn(name = "author_id") })
    private Set<Author> authors = new HashSet<Author>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "fk_categories", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
            @JoinColumn(name = "category_id") })
    private Set<Category> categories = new HashSet<Category>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "fk_publishers", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
            @JoinColumn(name = "publisher_id") })
    private Set<Publisher> publishers = new HashSet<Publisher>();

    public Book(String bIsbn, String bName, String bSerialName, String bDescription) {
        this.bIsbn = bIsbn;
        this.bName = bName;
        this.bSerialName = bSerialName;
        this.bDescription = bDescription;
    }

    public void addAuthors(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthors(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    public void addCategories(Category category) {
        this.categories.add(category);
        category.getBooks().add(this);
    }

    public void removeCategories(Category category) {
        this.categories.remove(category);
        category.getBooks().remove(this);
    }

    public void addPublishers(Publisher publisher) {
        this.publishers.add(publisher);
        publisher.getBooks().add(this);
    }

    public void removePublishers(Publisher publisher) {
        this.publishers.remove(publisher);
        publisher.getBooks().remove(this);
    }
}
