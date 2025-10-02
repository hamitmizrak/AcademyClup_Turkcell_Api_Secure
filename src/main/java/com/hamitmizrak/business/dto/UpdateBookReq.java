package com.hamitmizrak.business.dto;

import jakarta.validation.constraints.*;

/*
Güncel Kitap oluşturma isteği
Bean validation anotasyon'larının girdi doğrulamasını yapmak için validation'lar kullandık
Version:  Zorunlı optimistic locking kontrolü gerekli
 */
public record UpdateBookReq(

        @NotBlank
        @Size(min = 5, max = 200)
        String title,

        @NotBlank
        @Size(min = 5, max = 200)
        String author,

        @Min(1500) @Max(2500)
        int yearPublished,

        @NotNull
        Long version
) {
}

