package com.petarstoykov.xmlsecureprocessor.controllers;

import com.petarstoykov.xmlsecureprocessor.services.XmlProcessorService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static com.petarstoykov.xmlsecureprocessor.controllers.BooksController.PATH;

@RestController
@RequestMapping(PATH)
public class BooksController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BooksController.class);

    protected static final String PATH = "/books";
    private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

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

    @GetMapping("/download")
    public void downloadBooksInformation(HttpServletResponse response) {
        try(OutputStream outputStream = response.getOutputStream()) {

            // Indicate browser that the returned value is to be downloaded
            response.setContentType(APPLICATION_OCTET_STREAM);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.html");

            String htmlString = xmlProcessorService.transformXmlToHtml();
            outputStream.write(htmlString.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (Exception e) {
            response.reset();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            LOGGER.error(e.getMessage(), e);
        }
    }

}
