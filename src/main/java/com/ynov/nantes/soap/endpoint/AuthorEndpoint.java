package com.ynov.nantes.soap.endpoint;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ynov.nantes.soap.author.AddAuthorRequest;
import com.ynov.nantes.soap.author.GetAuthorRequest;
import com.ynov.nantes.soap.author.GetAuthorResponse;
import com.ynov.nantes.soap.author.GetAuthorsRequest;
import com.ynov.nantes.soap.author.GetAuthorsResponse;
import com.ynov.nantes.soap.author.RmAuthorRequest;
import com.ynov.nantes.soap.entity.Author;
import com.ynov.nantes.soap.repository.AuthorRepository;




@Endpoint
public class AuthorEndpoint {
    private static final String NAMESPACE_URI = "http://nantes.ynov.com/soap/author";

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorEndpoint(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAuthorRequest")
    @ResponsePayload
    public GetAuthorResponse getAuthor(@RequestPayload GetAuthorRequest request) {
        GetAuthorResponse response = new GetAuthorResponse();
        
        int author = request.getId(); //Converti l'objet receptionné en database
        
        response.setAuthor(entitytoweb(authorRepository.findAuteurById(author)));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAuthorsRequest")
    @ResponsePayload
    public GetAuthorsResponse getAuthors(@RequestPayload GetAuthorsRequest request) {
        GetAuthorsResponse response = new GetAuthorsResponse();
        List<Author> authors = authorRepository.findAll();
        
        for (Author aut : authors) {
            System.out.println(aut.getFirst_name().toString());
            response.getAuthors().add(entitytoweb(aut));
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addAuthorRequest")
    @ResponsePayload
    public void addAuthor(@RequestPayload AddAuthorRequest request) {
        authorRepository.save(webtoentity(request.getAuthor()));
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RmAuthorRequest")
    @ResponsePayload
    public void rmAuthor(@RequestPayload RmAuthorRequest request) {
        authorRepository.delete(webtoentity(request.getAuthor()));
    }
    
    /**
     * Fonction de conversion d'objet de la base de données vers le WebService.
     * 
     * @param book objet de la base de donnée à transférer
     * @return le DTO
     */
    private com.ynov.nantes.soap.author.Author entitytoweb(@NonNull Author author) {
        ModelMapper modelMapper = new ModelMapper();
        com.ynov.nantes.soap.author.Author orderDTO = modelMapper.map(author,com.ynov.nantes.soap.author.Author.class);
        
        
        return orderDTO;
    }
    
    private Author webtoentity(@NonNull com.ynov.nantes.soap.author.Author author) {
        ModelMapper modelMapper = new ModelMapper();
        Author orderDTO = modelMapper.map(author,Author.class);
        
        
        return orderDTO;
    }
    
    

}
