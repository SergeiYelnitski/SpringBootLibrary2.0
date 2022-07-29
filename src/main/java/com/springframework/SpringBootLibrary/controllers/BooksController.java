package com.springframework.SpringBootLibrary.controllers;



import com.springframework.SpringBootLibrary.models.Person;
import com.springframework.SpringBootLibrary.models.Book;
import com.springframework.SpringBootLibrary.services.BookService;
import com.springframework.SpringBootLibrary.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class  BooksController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page",required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean isSortByYear,
                        Model model) {
        if (page == null || booksPerPage == null) {
            model.addAttribute("books", bookService.findAll(isSortByYear));
        } else {
            model.addAttribute("books", bookService.findWithPagination(page,booksPerPage, isSortByYear));
        }
        return "books/index";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "/books/search";
    }

    @PostMapping("/search")
    public String searchBook(Model model,
                             @RequestParam(value = "query", required = false) String query) {

        model.addAttribute("books", bookService.searchByTitle(query));
        return "/books/search";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Person bookOwner = bookService.getBookOwner(id);
        if (bookOwner != null) {
        model.addAttribute("owner", bookOwner);
        } else
            model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String release (@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/" + id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
