package com.ynov.nantes.soap.entity;

import java.util.Date;

import com.ynov.nantes.soap.entity.Author;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entité Livre persistente en base de données.
 * 
 * @author Matthieu BACHELIER
 * @since 2020-10
 * @version 1.0
 */
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /// TODO les autres champs
    
    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private int isbn;

    @Column(name = "datepublication")
    private int datepublication;

    /// TODO la relation Foreign Key (ManyToOne ? ManyToMany ? OneToMany ?)
    
    @ManyToOne
    @JoinColumn(name = "id_auteur")
    private Author author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public int getDatepublication() {
        return datepublication;
    }

    public void setDatepublication(int date_publication) {
        this.datepublication = date_publication;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}