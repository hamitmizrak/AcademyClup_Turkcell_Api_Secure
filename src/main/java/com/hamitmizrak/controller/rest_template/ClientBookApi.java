package com.hamitmizrak.controller.rest_template;

import com.hamitmizrak.business.dto.BookDto;
import com.hamitmizrak.business.dto.CreateBookReq;
import com.hamitmizrak.business.dto.UpdateBookReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate aracılığıyla Book API'yi çağıran proxy controller.
 * İstemci -> (bu controller) -> RestTemplate -> Book API
 */
@RestController
@RequestMapping("/rest/template/client")
@RequiredArgsConstructor
public class ClientBookApi {

    // FIELD
    private final RestTemplate restTemplate;

    // Application.Properties data handling
    @Value("${book.api.base-url}")
    private String baseUrl;

    /// /////////////////////////////////////////////////
    /** CREATE -> Book API'ye POST */
    // http://localhost:4444/rest/template/client/create
    @PostMapping(value = "/create")
    public ResponseEntity<BookDto> create(@Valid @RequestBody CreateBookReq req) {
        // Rest Template
        var client = new BookApiCrudClient(restTemplate, baseUrl);
        var created = client.create(req);
        // return (ResponseEntity<BookDto>) ResponseEntity.created(created);
        // return ResponseEntity.status(201).body(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** READ ONE -> Book API'ye GET /{id} */
    // http://localhost:4444/api/v1.0.0/books
    // http://localhost:4444/rest/template/client/find/1
    @GetMapping(value = {"/find","/find/{id}"})
    public ResponseEntity<BookDto> get(@PathVariable(name = "id",required = false) Long id) {
        var client = new BookApiCrudClient(restTemplate, baseUrl);
        return client.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /** READ PAGE -> Book API'ye GET ?page&size&sort&q */
    // http://localhost:4444/rest/template/client
    @GetMapping(value = "list")
    public ResponseEntity<BookApiCrudClient.PageLike<BookDto>> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String q
    ) {
        var client = new BookApiCrudClient(restTemplate, baseUrl);
        var result = client.findPage(page, size, sort, q);
        return ResponseEntity.ok(result);
    }

    /** UPDATE -> Book API'ye PUT /{id} */
    // http://localhost:4444/rest/template/client/update/1
    @PutMapping(value = {"/update","/update/{id}"})
    public ResponseEntity<BookDto> update(
            @PathVariable(name = "id",required = false) Long id,
            @Valid @RequestBody UpdateBookReq req) {
        var client = new BookApiCrudClient(restTemplate, baseUrl);
        var updated = client.update(id, req);
        return ResponseEntity.ok(updated);
    }

    /** DELETE -> Book API'ye DELETE /{id} */
    // http://localhost:4444/rest/template/client/delete/1
    @DeleteMapping(value = {"/delete","/delete/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var client = new BookApiCrudClient(restTemplate, baseUrl);
        client.delete(id);
    }
}


