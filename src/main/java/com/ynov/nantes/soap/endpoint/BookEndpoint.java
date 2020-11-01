package com.ynov.nantes.soap.endpoint;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

 

import com.ynov.nantes.soap.book.AddBookRequest;
import com.ynov.nantes.soap.book.DeleteBookRequest;
import com.ynov.nantes.soap.book.GetBookByTitleRequest;
import com.ynov.nantes.soap.book.GetBookResponse;
import com.ynov.nantes.soap.book.GetBooksRequest;
import com.ynov.nantes.soap.book.GetBooksResponse;
import com.ynov.nantes.soap.entity.Book;
import com.ynov.nantes.soap.repository.AuthorRepository;
import com.ynov.nantes.soap.repository.BookRepository;

 

@Endpoint
public class BookEndpoint {
    private static final String NAMESPACE_URI = "http://nantes.ynov.com/soap/book";

 

    private BookRepository bookRepository;

 

    @Autowired
    public BookEndpoint(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
    }

 

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookByTitleRequest")
    @ResponsePayload
    public GetBookResponse getBookByTitle(@RequestPayload GetBookByTitleRequest request) {
        GetBookResponse response = new GetBookResponse();
        
        Book book = new Book();
        book = bookRepository.findBookByTitle(request.getTitle());
       
        response.setBook(entitytoweb(book));
        return response;
    }

 

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBooksRequest")
    @ResponsePayload
    public GetBooksResponse getBooks(@RequestPayload GetBooksRequest request) {
        GetBooksResponse response = new GetBooksResponse();
        
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            response.getBook().add(entitytoweb(book));
        }  
        return response;
    }

 

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addBookRequest")
    @ResponsePayload
    public void addBook(@RequestPayload AddBookRequest request) {
        bookRepository.save(webtoentity(request.getBook()));
    }

 

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBookRequest")
    @ResponsePayload
    public void addBook(@RequestPayload DeleteBookRequest request) {
        bookRepository.delete(webtoentity(request.getBook()));
    }

 

    /**
     * Fonction de conversion d'objet de la base de données vers le WebService.
     * 
     * @param book objet de la base de donnée à transférer
     * @return le DTO
     */
    private com.ynov.nantes.soap.book.Book entitytoweb(@NonNull Book book) {
        ModelMapper modelMapper = new ModelMapper();
        com.ynov.nantes.soap.book.Book bookDto =modelMapper.map(book,com.ynov.nantes.soap.book.Book.class);
        
        return bookDto;
    }
    private Book webtoentity(@NonNull com.ynov.nantes.soap.book.Book book) {
        ModelMapper modelMapper = new ModelMapper();
        Book toEnt =modelMapper.map(book,Book.class);
        
        return toEnt;
    }

 

}