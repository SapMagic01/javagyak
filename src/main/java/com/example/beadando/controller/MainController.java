package com.example.beadando.controller;

import com.example.beadando.repository.HuzasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private HuzasRepository huzasRepository;

    // Főoldal megjelenítése [cite: 70]
    @GetMapping("/")
    public String index(Model model) {
        return "index"; // index.html sablont keresi
    }

    // Adatbázis menü: 3 tábla adatait jeleníti meg [cite: 71]
    @GetMapping("/adatbazis")
    public String adatbazis(Model model) {
        // A repository-ban írtuk ezt a metódust (findAllByOrderByEvDescHetDesc)
        // Ha nem találja, használd a sima findAll()-t: huzasRepository.findAll()
        model.addAttribute("huzasok", huzasRepository.findAllByOrderByEvDescHetDesc());
        return "database"; // database.html sablont keresi
    }

    // Kapcsolat oldal
    @GetMapping("/kapcsolat")
    public String kapcsolat(Model model) {
        return "contact"; // contact.html
    }
}