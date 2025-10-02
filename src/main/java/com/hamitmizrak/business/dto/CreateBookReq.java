package com.hamitmizrak.business.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/*
Yeni Kitap oluşturma isteği
Bean validation anotasyon'larının girdi doğrulamasını yapmak için validation'lar kullandık
 */
public record CreateBookReq(

        @NotBlank
        @Size(min = 5, max = 200)
        String title,

        @NotBlank
        @Size(min = 5, max = 200)
        String author,

        @Min(1500) @Max(2500)
        int yearPublished
) {
}
