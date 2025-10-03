package com.hamitmizrak.controller.api;

import com.hamitmizrak.Frontend;
import com.hamitmizrak.business.dto.BookDto;
import com.hamitmizrak.business.dto.CreateBookReq;
import com.hamitmizrak.business.dto.UpdateBookReq;
import com.hamitmizrak.business.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1.0.0/books") // <<<<<< v1.0.0
@RequiredArgsConstructor
@CrossOrigin
//@CrossOrigin(originPatterns = Frontend.REACT_URL)
public class BookApi {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookDto>> list(
            @RequestParam(value = "q", required = false) String q,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.list(q, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.get(id));
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody CreateBookReq req,
                                          UriComponentsBuilder uri) {
        BookDto dto = bookService.create(req);
        var location = uri.path("/api/v1.0.0/books/{id}") // <<<<<< v1.0.0
                .buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id,
                                          @Valid @RequestBody UpdateBookReq req) {
        return ResponseEntity.ok(bookService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
