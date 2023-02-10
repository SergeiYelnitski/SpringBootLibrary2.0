package com.springframework.SpringBootLibrary.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.springframework.SpringBootLibrary.models.Person;
import com.springframework.SpringBootLibrary.repositories.PeopleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PeopleServiceTest {

  @Mock
  private PeopleRepository peopleRepository;

  private PeopleService peopleService;

  @Before
  public void setUp() {
    peopleService = new PeopleService(peopleRepository);
  }

  @Test
  public void findAll_shouldReturnAllPeople() {
    Person person1 = new Person();
    person1.setId(1);
    person1.setName("John Doe");

    Person person2 = new Person();
    person2.setId(2);
    person2.setName("Jane Doe");

    when(peopleRepository.findAll()).thenReturn(Arrays.asList(person1, person2));

    List<Person> result = peopleService.findAll();

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo(1);
    assertThat(result.get(0).getName()).isEqualTo("John Doe");
    assertThat(result.get(1).getId()).isEqualTo(2);
    assertThat(result.get(1).getName()).isEqualTo("Jane Doe");
  }

  @Test
  public void findOne_shouldReturnPersonWithGivenId() {
    Person person = new Person();
    person.setId(1);
    person.setName("John Doe");

    when(peopleRepository.findById(1)).thenReturn(Optional.of(person));

    Person result = peopleService.findOne(1);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(1);
    assertThat(result.getName()).isEqualTo("John Doe");
  }

  @Test
  public void findOne_shouldReturnNull_whenPersonNotFound() {
    when(peopleRepository.findById(1)).thenReturn(Optional.empty());

    Person result = peopleService.findOne(1);

    assertThat(result).isNull();
  }

  @Test
  public void save_shouldSavePerson() {
    Person person = new Person();
    person.setName("John Doe");

    peopleService.save(person);
  }

}

