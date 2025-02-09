package org.domian.market.controller;

import org.domian.market.model.Person;
import org.domian.market.service.PersonService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class CardController {

    private final PersonService personService;

    public CardController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public String getCard(@PathVariable int id, Model model) {
        model.addAttribute("product", personService.getCardById(id));
        return "cardPage";
    }
}