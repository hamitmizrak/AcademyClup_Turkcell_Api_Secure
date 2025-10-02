package com.hamitmizrak.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

// LOMBOK
//@Data  // Getter +Setter+ HashCode + toString
@Getter
@Setter
// @EqualsAndHashCode
// @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

// ENTITY
@Entity(name = "Books")
@Table(name="books")
public class BookEntity implements Serializable {

    // Serileştirme
    private static final long serialVersionUID = 1L;

    // ID (Birincil Anahtar)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TITLE
    @Column(name = "title",nullable = false,length = 200)
    private String title;

    // AUTHOR
    @Column(nullable = false,length = 200)
    private String author;

    // Yayın Yılı
    @Column(nullable = false,length = 200)
    private int yearPublished;

    /*
    Optimistick Locking için sürüm alanı
    Aynı kaydı iki kullanıcı farklı sürümlerde güncelleme yaparsa 409 olan çatışmayı yakalarız
     */
    @Version
    private Long version;

    // Oluşturma zamanı: JPA Auditing tarafından set edilir.
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;


    // Oluşturma zamanı: JPA Auditing tarafından set edilir.
    @LastModifiedDate
    private Instant updatedAt;

    // Constructor
    public BookEntity(String title, String author, int yearPublished) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
    }

    // Method
}
