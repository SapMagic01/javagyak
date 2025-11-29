package com.example.beadando.controller;

import com.example.beadando.entity.Huzas;
import com.example.beadando.entity.Huzott;
import com.example.beadando.entity.Nyeremeny;
import com.example.beadando.repository.HuzasRepository;
import com.example.beadando.repository.HuzottRepository;
import com.example.beadando.repository.NyeremenyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/crud")
public class CrudController {

    @Autowired
    private HuzasRepository huzasRepository;

    @Autowired
    private HuzottRepository huzottRepository;

    @Autowired
    private NyeremenyRepository nyeremenyRepository;

    // 1. LISTA MEGJELENÍTÉSE
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("huzasok", huzasRepository.findAllByOrderByEvDescHetDesc());
        return "crud_list";
    }

    // 2. ÚJ HÚZÁS FORM (Csak az alapok)
    @GetMapping("/add")
    public String createForm(Model model) {
        model.addAttribute("huzas", new Huzas());
        model.addAttribute("pageTitle", "Új Húzás Rögzítése");
        // Új létrehozásnál még nincsenek számok/nyeremények
        return "crud_form";
    }

    // 3. MENTÉS (Húzás alapadatok)
    @PostMapping("/save")
    public String saveHuzas(@ModelAttribute("huzas") Huzas huzas) {
        // Elmentjük a húzást
        Huzas savedHuzas = huzasRepository.save(huzas);
        // Mentés után rögtön a szerkesztő oldalra irányítjuk, hogy fel lehessen vinni a számokat is
        return "redirect:/crud/edit/" + savedHuzas.getId() + "?success";
    }

    // 4. SZERKESZTÉS FORM (Itt már látszanak a számok és nyeremények is)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Huzas huzas = huzasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hibás ID: " + id));

        model.addAttribute("huzas", huzas);
        model.addAttribute("pageTitle", "Húzás Szerkesztése");

        // Mivel a kapcsolatok (@OneToMany) be vannak állítva az Entity-ben,
        // a Thymeleaf eléri a huzas.huzottSzamok és huzas.nyeremenyek listát.
        return "crud_form";
    }

    // 5. TÖRLÉS (Húzás)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        huzasRepository.deleteById(id);
        return "redirect:/crud?deleted";
    }

    // --- ÚJ FUNKCIÓK: SZÁMOK ÉS NYEREMÉNYEK KEZELÉSE ---

    // Szám hozzáadása
    @PostMapping("/number/add/{huzasId}")
    public String addNumber(@PathVariable Long huzasId, @RequestParam Integer szam) {
        Huzas huzas = huzasRepository.findById(huzasId).orElseThrow();

        Huzott ujSzam = new Huzott();
        ujSzam.setSzam(szam);
        ujSzam.setHuzas(huzas); // Beállítjuk a szülőt

        huzottRepository.save(ujSzam);
        return "redirect:/crud/edit/" + huzasId;
    }

    // Szám törlése
    @GetMapping("/number/delete/{id}")
    public String deleteNumber(@PathVariable Long id) {
        Huzott szam = huzottRepository.findById(id).orElseThrow();
        Long huzasId = szam.getHuzas().getId(); // Megjegyezzük, hova kell visszalépni
        huzottRepository.delete(szam);
        return "redirect:/crud/edit/" + huzasId;
    }

    // Nyeremény hozzáadása
    @PostMapping("/prize/add/{huzasId}")
    public String addPrize(@PathVariable Long huzasId,
                           @RequestParam Integer talalat,
                           @RequestParam Integer darab,
                           @RequestParam Long ertek) {
        Huzas huzas = huzasRepository.findById(huzasId).orElseThrow();

        Nyeremeny ujNyeremeny = new Nyeremeny();
        ujNyeremeny.setTalalat(talalat);
        ujNyeremeny.setDarab(darab);
        ujNyeremeny.setErtek(ertek);
        ujNyeremeny.setHuzas(huzas);

        nyeremenyRepository.save(ujNyeremeny);
        return "redirect:/crud/edit/" + huzasId;
    }

    // Nyeremény törlése
    @GetMapping("/prize/delete/{id}")
    public String deletePrize(@PathVariable Long id) {
        Nyeremeny nyeremeny = nyeremenyRepository.findById(id).orElseThrow();
        Long huzasId = nyeremeny.getHuzas().getId();
        nyeremenyRepository.delete(nyeremeny);
        return "redirect:/crud/edit/" + huzasId;
    }
}