package com.spring.heteoas.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RootController {
    @GetMapping("/")
    public EntityModel<String> index() {
        return EntityModel.of("API Spring HATEOAS",
                linkTo(methodOn(BookController.class).getAllBooks(Pageable.unpaged())).withRel("books"));
    }
}
