package com.petarstoykov.xmlsecureprocessor.controllers;

import com.petarstoykov.xmlsecureprocessor.services.XmlProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.petarstoykov.xmlsecureprocessor.controllers.BooksController.PATH;

@RestController
@RequestMapping(PATH)
public class BooksController {

    protected static final String PATH = "/books";

    private final XmlProcessorService xmlProcessorService;

    public BooksController(XmlProcessorService xmlProcessorService) {
        this.xmlProcessorService = xmlProcessorService;
    }

    @GetMapping
    public ResponseEntity<String> getBooksInformation() {
        try {
            return ResponseEntity.ok().body(xmlProcessorService.transformXmlToHtml());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
