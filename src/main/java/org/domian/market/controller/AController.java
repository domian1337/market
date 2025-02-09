package org.domian.market.controller;

import org.domian.market.model.Card;
import org.domian.market.model.Person;
import org.domian.market.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/marketD")
public class AController {

    private final PersonService personService;

    @Autowired
    public AController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/addCard")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void saveCard(@RequestBody Card card) {
        personService.addCard(card);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Person getPerson(@PathVariable int id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void savePerson(@RequestBody Person person) {
        personService.savePerson(person);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void editPerson(@RequestBody Person person) {
        personService.editPerson(person.getId(), person.getName(), person.getSurname(),
                person.getEmail(), person.getAge(), person.getBalance(), person.getGender(),
                person.getPassword(), person.getStatus(), person.getRoles());
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deletePerson(@RequestBody Person person) {
        personService.deletePerson(person.getId());
    }

    @PostMapping("/product/{article}")
    public void addToShop(@AuthenticationPrincipal Person person, @PathVariable int article) {
        personService.addCardToShop(person.getId(), article);
    }

    @PostMapping("/cart/remove")
    public void deleteFromShop(@AuthenticationPrincipal Person person, @RequestBody int article) {
        personService.deleteCardFromShop(person.getId(), article);
    }

    @PutMapping("/profile/update")
    public void updateProfile(@AuthenticationPrincipal Person person, @RequestBody Person profile) {
        personService.editProfile(person.getId(), profile);
    }
}