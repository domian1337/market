package org.domian.market.controller;

import org.domian.market.model.Person;
import org.domian.market.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    private PersonService personService;

    @Autowired
    public RegistrationController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String registerPerson(Model model) {
        model.addAttribute("person", new Person());
        return "registration";
    }

    @PostMapping
    public String registerPerson(@ModelAttribute("person") Person person) {
        personService.registrationUser(person);
           return "redirect:/login";
    }
}