package org.domian.market.controller;

import lombok.AllArgsConstructor;
import org.domian.market.model.Person;
import org.domian.market.service.PersonService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class PageController {

    private PersonService personService;

    @GetMapping("/start")
    public String start(Model model) {
        model.addAttribute("products", personService.getAllCards());
        return "start";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    @GetMapping("/support")
    public String support() {
        return "support";
    }

    @GetMapping("/shop")
    public String shop(Model model, @AuthenticationPrincipal Person person) {
        model.addAttribute("products", person.getAllCards());
        return "shop";
    }
}
