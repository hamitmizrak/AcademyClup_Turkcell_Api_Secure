package com.hamitmizrak.business.dto;

import java.time.Instant;

// İstemci modelimizi döndereceğimiz yer
// Record: immutable taşıyıcı = equals+hashCode+toString otomatik gelir
public record BookDto(
        Long id,
        String title,
        String author,
        int yearPublished,
        Long version,
        Instant createdAt,
        Instant updatedAt
) {
}
