package com.hamitmizrak.data.repository;

import com.hamitmizrak.data.entity.BookEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface IBookRepository  extends JpaRepository<BookEntity, Long> {

    // Delivered Query
    // Başlıkta geçen metne göre sayfa sonuçlarını dönderelim
    Page<BookEntity> findByTitleContainingIgnoreCase(String q, Pageable pageable);
}
