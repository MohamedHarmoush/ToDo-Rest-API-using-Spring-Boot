package com.harmoush.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HomeController {

    @RequestMapping(value = "/")
    public String greeting() {
        return "Hello, Welcome to SpringBoot!!";
    }

    @GetMapping(value = "/{name}")
    public String greetingWithName(@PathVariable String name) {
        return String.format(Locale.getDefault(), "Welcome %s to Spring Boot Tutorial!", name);
    }
}
