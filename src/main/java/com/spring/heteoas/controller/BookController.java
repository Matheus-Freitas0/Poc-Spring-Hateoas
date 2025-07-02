package com.spring.heteoas.controller;

import com.spring.heteoas.domain.Book;
import com.spring.heteoas.dto.BookDto;
import com.spring.heteoas.dto.BookModelAssembler;
import com.spring.heteoas.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookModelAssembler bookModelAssembler;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public PagedModel<EntityModel<BookDto>> getAllBooks(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        List<EntityModel<BookDto>> bookModels = page.getContent().stream().map(book -> bookModelAssembler.toModel(modelMapper.map(book, BookDto.class))).toList();
        return PagedModel.of(bookModels, new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements()), linkTo(methodOn(BookController.class).getAllBooks(pageable)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<BookDto> getBookById(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        return bookModelAssembler.toModel(modelMapper.map(book, BookDto.class));
    }

    @PostMapping
    public ResponseEntity<EntityModel<BookDto>> createBook(@RequestBody BookDto dto) {
        Book saved = bookRepository.save(modelMapper.map(dto, Book.class));
        EntityModel<BookDto> model = bookModelAssembler.toModel(modelMapper.map(saved, BookDto.class));
        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    @PutMapping("/{id}")
    public EntityModel<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());

        Book updated = bookRepository.save(book);
        return bookModelAssembler.toModel(modelMapper.map(updated, BookDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        bookRepository.delete(book);
        return ResponseEntity.noContent().build();
    }
}
