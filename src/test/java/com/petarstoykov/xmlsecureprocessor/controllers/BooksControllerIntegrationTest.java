package com.petarstoykov.xmlsecureprocessor.controllers;

import com.petarstoykov.xmlsecureprocessor.services.XmlProcessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BooksControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private XmlProcessorService xmlProcessorService;

    private static final String EXPECTED_HTML = """
            <html>
                <head>
                    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <title>Bookshop Inventory</title>
                    <style>
                                table {
                                width: 100%;
                                border-collapse: collapse;
                                }
                                th, td {
                                padding: 8px;
                                border: 1px solid #ddd;
                                }
                                th {
                                background-color: #f2f2f2;
                                }
                            </style>
                </head>
                <body>
                    <h2>Bookshop Inventory</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Title</th><th>Author</th><th>Genre</th><th>Price</th><th>Availability</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Effective Java</td><td>Joshua Bloch</td><td>Programming</td><td>45.00 USD</td><td>In Stock</td>
                            </tr>
                            <tr>
                                <td>Clean Code</td><td>Robert C. Martin</td><td>Programming</td><td>40.00 USD</td><td>Out of Stock</td>
                            </tr>
                            <tr>
                                <td>The Pragmatic Programmer</td><td>Andy Hunt, Dave Thomas</td><td>Programming</td><td>50.00 USD</td><td>In Stock</td>
                            </tr>
                        </tbody>
                    </table>
                </body>
            </html>
            """;

    @Test
    @WithMockUser(roles = "VIEW")
    public void getBooksInformation_shouldWork() throws Exception{

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED_HTML));
    }

    @Test
    @WithMockUser(roles = "VIEW")
    public void getBooksInformation_shouldReturnInternalServerError() throws Exception {
        doThrow(new RuntimeException("Mocked Exception")).when(xmlProcessorService).transformXmlToHtml();

        mockMvc.perform(get("/books"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles = "VIEW")
    public void downloadBooksInformation_shouldWork() throws Exception {
        mockMvc.perform(get("/books/download"))
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED_HTML))
                .andExpect(header().string("Content-Disposition", "attachment; filename=books.html"));
    }

    @Test
    @WithMockUser(roles = "VIEW")
    public void downloadBooksInformation_shouldReturnInternalServerError() throws Exception {
        doThrow(new RuntimeException("Mocked Exception")).when(xmlProcessorService).transformXmlToHtml();

        mockMvc.perform(get("/books/download"))
                .andExpect(status().isInternalServerError());
    }
}
