package com.hamitmizrak.controller.rest_template;

import com.hamitmizrak.business.dto.BookDto;
import com.hamitmizrak.business.dto.CreateBookReq;
import com.hamitmizrak.business.dto.UpdateBookReq;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.Optional;

public class BookApiCrudClient {

    // Injection
    private final RestTemplate restTemplate;
    private final String base; // örn: http://localhost:4444/api/v1.0.0/books

    // Parametreli Constructor
    public BookApiCrudClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.base = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    // CREATE
    public BookDto create(CreateBookReq createBookReq) {
        ResponseEntity<BookDto> bookDtoResponseEntity = restTemplate.  postForEntity(base, createBookReq, BookDto.class);
        return Objects.requireNonNull(bookDtoResponseEntity.getBody(), "Boş cevap alındı");
    }

    // FIND BY ID (LIST)
    public Optional<BookDto> findById(Long id) {
        ResponseEntity<BookDto> bookDtoResponseEntity = restTemplate.getForEntity(base + "/" + id, BookDto.class);
        return Optional.ofNullable(bookDtoResponseEntity.getBody());
    }

    // UPDATE
    public BookDto update(Long id, UpdateBookReq updateBookReq) {
        HttpEntity<UpdateBookReq> http = new HttpEntity<>(updateBookReq);
        ResponseEntity<BookDto> r =
                restTemplate.exchange(base + "/" + id, HttpMethod.PUT, http, BookDto.class);
        return Objects.requireNonNull(r.getBody(), "Boş cevap alındı");
    }

    // DELETE
    public void delete(Long id) {
        restTemplate.delete(base + "/" + id);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Inner Class
    public record PageLike<T>(
            java.util.List<T> content,
            int number,
            int size,
            int totalPages,
            long totalElements,
            boolean last
    ) {}

    // currentPage / pageSize isimleri application.properties ile uyumlu
    public PageLike<BookDto> findPage(Integer page, Integer size, String sort, String q) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(base);
        if (page != null) uri.queryParam("currentPage", page); // <<<<<<
        if (size != null) uri.queryParam("pageSize", size);     // <<<<<<
        if (sort != null && !sort.isBlank()) uri.queryParam("sort", sort);
        if (q != null && !q.isBlank()) uri.queryParam("q", q);

        var type = new ParameterizedTypeReference<PageLike<BookDto>>() {};
        ResponseEntity<PageLike<BookDto>> r =
                restTemplate.exchange(uri.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, type);
        return Objects.requireNonNull(r.getBody(), "Boş sayfa alındı");
    }

}
