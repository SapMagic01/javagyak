package com.example.beadando.controller;

import com.example.beadando.entity.Message;
import com.example.beadando.repository.HuzasRepository;
import com.example.beadando.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private HuzasRepository huzasRepository;

    @Autowired
    private MessageRepository messageRepository; // EZT ADTUK HOZZÁ

    // Főoldal
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    // Adatbázis menü
    @GetMapping("/adatbazis")
    public String adatbazis(Model model) {
        model.addAttribute("huzasok", huzasRepository.findAllByOrderByEvDescHetDesc());
        return "database";
    }

    // --- KAPCSOLAT (Üzenet küldés) ---

// --- KAPCSOLAT (Üzenet küldés) - JAVÍTOTT ---

    // 1. Űrlap megjelenítése
    @GetMapping("/kapcsolat")
    public String kapcsolat(Model model) {
        // Átneveztük "message"-ről "ujUzenet"-re a névütközés elkerülése végett
        model.addAttribute("ujUzenet", new Message());
        return "contact";
    }

    // 2. Űrlap feldolgozása (POST)
    @PostMapping("/kapcsolat")
    public String kapcsolatSubmit(@ModelAttribute("ujUzenet") Message message,
                                  org.springframework.validation.BindingResult bindingResult) {

        // Hibakezelés: Ha a Spring nem tudja feldolgozni az adatokat
        if (bindingResult.hasErrors()) {
            System.out.println("ŰRLAP HIBA: " + bindingResult.getAllErrors());
            return "contact"; // Visszaküld az űrlapra hiba esetén (nem lesz Whitelabel page)
        }

        // Mentés az adatbázisba
        messageRepository.save(message);
        return "redirect:/kapcsolat?success";
    }

    // --- ÜZENETEK (Csak belépve) ---
    @GetMapping("/uzenetek")
    public String uzenetek(Model model) {
        // [cite: 78] "fordított időrend szerint"
        // Ezt a metódust a MessageRepository-ban írtuk meg korábban
        model.addAttribute("messages", messageRepository.findAllByOrderByCreatedAtDesc());
        return "messages";
    }
}