package br.com.erudio.services;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.dto.PersonDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.erudio.mapper.ObjectMapper.parseListObjects;
import static br.com.erudio.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());
    private final PersonRepository personRepository;

    public List<PersonDTO> findAll() {
        var personDTOS = parseListObjects(personRepository.findAll(), PersonDTO.class);
        personDTOS.forEach(this::addHateoasLinks);
        return personDTOS;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO create(PersonDTO person) {
        if (person == null) {
            logger.error("Person is null");
            throw new RequiredObjectIsNullException();
        }

        logger.info("Creating a person");
        Person entity = parseObject(person, Person.class);
        PersonDTO personDTO = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(personDTO);
        return personDTO;
    }

    public PersonDTO update(PersonDTO person) {
        if (person == null) {
            logger.error("Person is null");
            throw new RequiredObjectIsNullException();
        }

        logger.info("Updating a person");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        PersonDTO personDTO = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(personDTO);
        return personDTO;
    }

    public void delete(Long id) {
        logger.info("Deleting one Person");
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        personRepository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
