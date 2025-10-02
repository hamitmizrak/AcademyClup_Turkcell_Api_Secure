package com.hamitmizrak.controller.api;

import com.hamitmizrak.business.dto.BookDto;
import com.hamitmizrak.business.dto.CreateBookReq;
import com.hamitmizrak.business.dto.UpdateBookReq;
import com.hamitmizrak.business.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

// LOMBOK
@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1.0.0/books")
public class BookApi {

    // DI
    private final BookService bookService;

    // http://localhost:4444/api/v1.0.0/books?q=araba
    // LISTELEME
    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(value="q", required = false) String q,
            @PageableDefault(size=10, sort="id") Pageable pageable){
        return ResponseEntity.ok(bookService.list(q, pageable));
    }

    // http://localhost:4444/api/v1.0.0/books/1
    // FIND
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> get(@PathVariable Long id){
        return  ResponseEntity.ok(bookService.get(id));
    }

    // http://localhost:4444/api/v1.0.0/books/
    // CREATE
    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody CreateBookReq req, UriComponentsBuilder uri){
        BookDto bookDto = bookService.create(req);
        var location = uri.path("/api/v1.0.0/books/{id}").buildAndExpand(bookDto.id()).toUri();
        return ResponseEntity.created((location)).body(bookDto);
    }

    // http://localhost:4444/api/v1.0.0/books/1
    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(
            @PathVariable Long id,
            @Valid@RequestBody UpdateBookReq req){
        return ResponseEntity.ok(bookService.update(id, req));
    }


    // http://localhost:4444/api/v1.0.0/books/1
    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
         bookService.deleteById(id);
    }

}
