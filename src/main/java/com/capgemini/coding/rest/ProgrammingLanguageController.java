package com.capgemini.coding.rest;

import com.capgemini.coding.model.ProgrammingLanguage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/pl")
public class ProgrammingLanguageController {

    @GetMapping("list")
    public Iterable<ProgrammingLanguage> list() {
        List<ProgrammingLanguage> list = new ArrayList<>();
        {
            ProgrammingLanguage java = new ProgrammingLanguage();
            java.setType("Java");
            list.add(java);
        }
        {
            ProgrammingLanguage cpp = new ProgrammingLanguage();
            cpp.setType("C++");
            list.add(cpp);
        }

        return list;
    }
    @GetMapping("single")
    public ProgrammingLanguage single() {
        ProgrammingLanguage java = new ProgrammingLanguage();
        java.setType("PHP");
        java.setRaymondLikesIt(true);

        return java;
    }

    @GetMapping("String")
    public  String string() {
        return "This is a string";
    }
}
