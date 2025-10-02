package com.hamitmizrak.business.services;

import com.hamitmizrak.business.dto.BookDto;
import com.hamitmizrak.business.dto.CreateBookReq;
import com.hamitmizrak.business.dto.UpdateBookReq;
import com.hamitmizrak.data.entity.BookEntity;
import com.hamitmizrak.data.mapper.BookMapper;
import com.hamitmizrak.data.repository.IBookRepository;
import com.hamitmizrak.utils.HamitMizrakException;
import com.hamitmizrak.utils.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// LOMBOK
@RequiredArgsConstructor

/*
Servis Katmanı: Sorumlulukların iş kurallarınını ,transaction,validation
Controller, repository
 */
//


@Service
public class BookService {

    // Injection
    // @Autowired // 1.YOL
    private final IBookRepository iBookRepository;

    // Arama: q parametresi+Sayfalama
    public Page<BookDto> list(String q, org.springframework.data.domain.Pageable pageable) {
        Page<BookEntity> page = (q == null || q.isEmpty() || q.isBlank())
                ? iBookRepository.findAll(pageable)
                : iBookRepository.findByTitleContainingIgnoreCase(q, (java.awt.print.Pageable) pageable);
        return page.map(BookMapper::toDto);
    }

    // FIND
    public BookDto get(Long id) {
        BookEntity bookEntity = iBookRepository.findById(id)
                .orElseGet(() -> throw new HamitMizrakException("id bulunamadı"));
        return BookMapper.toDto(bookEntity);
    }

    // CREATE
    @Transactional
    public BookDto create(CreateBookReq req) {
        BookEntity bookEntity = new BookEntity(req.title(), req.author(), req.yearPublished());
        //BookEntity bookEntity = BookEntity.builder().build(); // 2.YOL
        bookEntity = iBookRepository.save(bookEntity);
        return BookMapper.toDto(bookEntity);
    }

    // UPDATE
    @Transactional
    public BookDto update(Long id, UpdateBookReq req) {
        BookEntity bookEntity = iBookRepository.findById(id).orElseGet(()-> throw  HamitMizrakException("id bulunamadı"));

        // Locking uyuşmazlığı
        if(!req.version().equals(bookEntity.getVersion())){
            throw new IllegalStateException("Versiyon uyuşmazlığı vardır Lütfen veriyi tekrar yazınız");
        }

        bookEntity.setTitle(req.title());
        bookEntity.setAuthor(req.author());
        bookEntity.setYearPublished(req.yearPublished());
        //BookEntity bookEntity = BookEntity.builder().build(); // 2.YOL
        bookEntity = iBookRepository.save(bookEntity);
        return BookMapper.toDto(bookEntity);
    }


    // DELÊTE Kayıt yoksa 404 fırlat
    @Transactional
    public void delete(Long id) {
       if(!iBookRepository.existsById(id)) NotFoundException.of("Book",id);
      iBookRepository.deleteById(id);
    }

}
