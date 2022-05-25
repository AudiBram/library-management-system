package com.projects.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Data
@NoArgsConstructor
@Table(name = "authors")
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String aName;

    @Column(nullable = false)
    private String aDescription;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {PERSIST, MERGE, REMOVE}, mappedBy = "authors")
    private Set<Book> books = new HashSet<Book>();

    public Author(String name, String description) {
        this.aName = aName;
        this.aDescription = aDescription;
    }
}
