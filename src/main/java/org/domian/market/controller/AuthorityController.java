package org.domian.market.controller;

import org.domian.market.model.Card;
import org.domian.market.model.Person;
import org.domian.market.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorityController {

    private final PersonService personService;

    @Autowired
    public AuthorityController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/user")
    public String user(Model model, @AuthenticationPrincipal UserDetails person) {
        model.addAttribute("personU", personService.getPersonByEmail(person.getUsername()));
        return "userPage";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAdminPage(Model model, @AuthenticationPrincipal UserDetails person) {
        model.addAttribute("card", new Card());
        model.addAttribute("person", new Person());
        model.addAttribute("persons", personService.getAllPersons());
        model.addAttribute("roles", personService.getAllRoles());
        model.addAttribute("personU", personService.getPersonByEmail(person.getUsername()));
        return "adminPage";
    }
}