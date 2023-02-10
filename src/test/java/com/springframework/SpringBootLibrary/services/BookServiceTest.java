package com.springframework.SpringBootLibrary.services;

import com.springframework.SpringBootLibrary.models.Book;
import com.springframework.SpringBootLibrary.models.Person;
import com.springframework.SpringBootLibrary.repositories.BooksRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

  @Mock
  private BooksRepository booksRepository;

  @InjectMocks
  private BookService bookService;

  @Test
  public void findAll() {
    List<Book> books = Arrays.asList(new Book(), new Book());
    when(booksRepository.findAll()).thenReturn(books);

    List<Book> result = bookService.findAll(false);

    assertEquals(books, result);
  }

  @Test
  public void findOne() {
    int id = 1;
    Book book = new Book();
    Optional<Book> optionalBook = Optional.of(book);
    when(booksRepository.findById(id)).thenReturn(optionalBook);

    Book result = bookService.findOne(id);

    assertEquals(book, result);
  }

  @Test
  public void save() {
    Book book = new Book();
    bookService.save(book);
  }

  @Test
  public void update() {
    int id = 1;
    Book bookUpdate = new Book();
    bookService.update(id, bookUpdate);
  }

  @Test
  public void delete() {
    int id = 1;
    bookService.delete(id);
  }

  @Test
  public void getBookOwner() {
    int id = 1;
    Person person = new Person();
    Book book = new Book();
    book.setOwner(person);
    Optional<Book> optionalBook = Optional.of(book);
    when(booksRepository.findById(id)).thenReturn(optionalBook);

    Person result = bookService.getBookOwner(id);

    assertEquals(person, result);
  }

  @Test
  public void release() {
    int id = 1;
    Book book = new Book();
    book.setOwner(new Person());
    book.setCreatedAt(new Date());
    Optional<Book> optionalBook = Optional.of(book);
    when(booksRepository.findById(id)).thenReturn(optionalBook);

    bookService.release(id);

    assertNull(book.getOwner());
    assertNull(book.getCreatedAt());
  }

  @Test
  public void assign() {
    int bookId = 1;
    Person selectedPerson = new Person();
    Book book = new Book();

    when(booksRepository.findById(anyInt())).thenReturn(Optional.of(book));

    bookService.assign(bookId, selectedPerson);

    verify(booksRepository).findById(bookId);
    assertEquals(selectedPerson, book.getOwner());
    assertNotNull(book.getCreatedAt());
  }
}
