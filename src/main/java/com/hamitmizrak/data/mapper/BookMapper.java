package com.hamitmizrak.data.mapper;

import com.hamitmizrak.business.dto.BookDto;
import com.hamitmizrak.data.entity.BookEntity;
import lombok.NoArgsConstructor;

import java.awt.print.Book;

// LOMBOK


public final class BookMapper {

    // Parametresiz Constrcutor (private)
    private BookMapper() {}

    //
      public static BookDto toDto(BookEntity b) {
        return null;
    }

    //
    /*  public static BookDto toEntity(Book b) {
        return new BookDto()
    }*/
}
