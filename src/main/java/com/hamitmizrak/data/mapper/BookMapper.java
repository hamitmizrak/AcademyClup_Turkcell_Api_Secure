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
          return new BookDto(
                  b.getId(),
                  b.getTitle(),
                  b.getAuthor(),
                  b.getYearPublished(),
                  b.getVersion(),
                  b.getCreatedAt(),
                  b.getUpdatedAt()
          );
    }

    //
      public static BookEntity toEntity(BookDto b) {
          return new BookEntity(
                  b.id(),
                  b.title(),
                  b.author(),
                  b.yearPublished(),
                  b.version(),
                  b.createdAt(),
                  b.updatedAt()
          );
    }
}
