package com.bookstore.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

public class TagEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "tags")
    private Set<BookEntity> books;
}
