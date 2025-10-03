package com.hamitmizrak.runner;

import com.hamitmizrak.business.dto.BookDto;
import com.hamitmizrak.business.dto.CreateBookReq;
import com.hamitmizrak.business.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

// LOMBOK
@RequiredArgsConstructor
@Slf4j
@Component
@Order(1)
//@ConditionalOnProperty(name = "api.seed.enabled", havingValue = "true") //Profile
public class _1_ApiRunner implements CommandLineRunner {

    // D.I
    private final BookService bookService;


    // RUNNING
    @Override
    public void run(String... args) throws Exception {

        long total = bookService.list(null, PageRequest.of(0, 1)).getTotalElements();
        if (total > 0) {
            log.info("Seed atlandı: zaten {} kayıt var.", total);
            return;
        }

        log.info("Seed başlıyor: örnek kitaplar ekleniyor...");
        List<CreateBookReq> seeds = List.of(
                new CreateBookReq("Clean Code", "Robert C. Martin", 2008),
                new CreateBookReq("Refactoring", "Martin Fowler", 1999),
                new CreateBookReq("Domain-Driven Design", "Eric Evans", 2003),
                new CreateBookReq("Effective Java", "Joshua Bloch", 2018),
                new CreateBookReq("Clean Architecture", "Robert C. Martin", 2017)
        );


        for (CreateBookReq req : seeds) {
            BookDto dto = bookService.create(req);
            log.info("Eklendi -> id={}, title='{}'", dto.id(), dto.title());
        }



        long after = bookService.list(null, PageRequest.of(0, 1)).getTotalElements();
        log.info("Seed tamam: toplam kayıt = {}", after);
        log.info("Hazır uçlar: GET /api/v1/books, GET /api/v1/books/{id}, POST/PUT/DELETE ...");
        log.info("Swagger (varsa): /swagger-ui/index.html  |  OpenAPI: /v3/api-docs");
    }
}
