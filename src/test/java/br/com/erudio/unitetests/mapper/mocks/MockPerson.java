package br.com.erudio.unitetests.mapper.mocks;

import br.com.erudio.dto.PersonDTO;
import br.com.erudio.model.Person;

import java.util.ArrayList;
import java.util.List;

public class MockPerson {
    public Person mockEntity() {
        return mockEntity();
    }

    public PersonDTO mockDTO() {
        return mockDTO();
    }

    public List<Person> mockEntityList() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            personList.add(mockEntity(i));
        }
        return personList;
    }

    public List<PersonDTO> mockDTOList() {
        List<PersonDTO> personList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            personList.add(mockDTO(i));
        }
        return personList;
    }

    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2) == 0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

    public PersonDTO mockDTO(Integer number) {
        PersonDTO dto = new PersonDTO();
        dto.setAddress("Address Test" + number);
        dto.setFirstName("First Name Test" + number);
        dto.setGender(((number % 2) == 0) ? "Male" : "Female");
        dto.setId(number.longValue());
        dto.setLastName("Last Name Test" + number);
        return dto;
    }


}
