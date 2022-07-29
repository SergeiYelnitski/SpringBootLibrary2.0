package com.springframework.SpringBootLibrary.services;



import com.springframework.SpringBootLibrary.models.Person;
import com.springframework.SpringBootLibrary.repositories.BooksRepository;
import com.springframework.SpringBootLibrary.models.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BooksRepository booksRepository;


    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean isSortByYear) {
        if (isSortByYear)
            return booksRepository.findAll(Sort.by("yearOfProduction"));
        else
            return booksRepository.findAll();
    }


    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean isSortByYear) {

        if(isSortByYear) {
            return booksRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by("yearOfProduction"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
        }
    }

    public List<Book> searchByTitle(String query) {
        return booksRepository.findByTitleStartingWith(query);
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book bookUpdate) {
        bookUpdate.setId(id);
        booksRepository.save(bookUpdate);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person getBookOwner(int id) {
       return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setCreatedAt(null);
                }
        );
    }


    // Назначает книгу человеку (этот метод вызывается, когда человек забирает книгу из библиотеки)
    @Transactional
    public void assign(int id, Person selectedPerson) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setCreatedAt(new Date());
                }
                       );

    }
}
