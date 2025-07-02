package com.spring.heteoas.dto;

import com.spring.heteoas.controller.BookController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<BookDto, EntityModel<BookDto>> {

    @Override
    public EntityModel<BookDto> toModel(BookDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(BookController.class).getBookById(dto.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).getAllBooks(Pageable.unpaged())).withRel("books")
        );
    }
}
