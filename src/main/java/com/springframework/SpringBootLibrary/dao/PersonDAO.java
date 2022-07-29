//package com.springframeworkcourse.SpringBootLibrary20.dao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import com.library.springframework.models.Book;
//import com.library.springframework.models.Person;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class PersonDAO {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public PersonDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//    public List<Person> index() {
//        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
//    }
//
//    public Person show(int id) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
//                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny().orElse(null);
//    }
//
//    public void save(Person person) {
//        jdbcTemplate.update("INSERT INTO Person(name, birthday) VALUES(?, ?)",
//                person.getName(), person.getBirthday());
//    }
//
//    public void update(int id, Person updatedPerson) {
//        jdbcTemplate.update("UPDATE Person SET name=?, birthday=? WHERE id=?",
//                updatedPerson.getName(),
//                updatedPerson.getBirthday(), id);
//    }
//
//    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
//    }
//
//    public Optional<Person> getPersonByName(String name) {
//        return jdbcTemplate.query("SELECT * from Person where name=?", new Object[]{name},
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
//    }
//    public List<Book> getBookByPersonId(int id){
//        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{id},
//                new BeanPropertyRowMapper<>(Book.class));
//    }
//}
