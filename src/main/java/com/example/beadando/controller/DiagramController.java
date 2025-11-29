package com.example.beadando.controller;

import com.example.beadando.entity.Huzas;
import com.example.beadando.entity.Nyeremeny;
import com.example.beadando.repository.HuzasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DiagramController {

    @Autowired
    private HuzasRepository huzasRepository;

    @GetMapping("/diagram")
    public String showDiagramPage() {
        return "diagram";
    }

    @GetMapping("/api/diagram-data")
    @ResponseBody
    public List<Map<String, Object>> getDiagramData() {
        List<Huzas> huzasok = huzasRepository.findAllByOrderByEvDescHetDesc();
        List<Map<String, Object>> result = new ArrayList<>();

        // Biztonsági ellenőrzés: ha nincs adat
        if (huzasok == null || huzasok.isEmpty()) {
            System.out.println("DIAGRAM HIBA: Nincs lekérhető húzás az adatbázisban.");
            return result;
        }

        int limit = Math.min(huzasok.size(), 10);

        for (int i = 0; i < limit; i++) {
            Huzas h = huzasok.get(i);
            long osszNyeremeny = 0;

            if (h.getNyeremenyek() != null) {
                for (Nyeremeny ny : h.getNyeremenyek()) {
                    // Null pointer védelem (ha véletlenül hiányos az adat)
                    if (ny.getDarab() != null && ny.getErtek() != null) {
                        osszNyeremeny += (ny.getDarab() * ny.getErtek());
                    }
                }
            }

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("label", h.getEv() + "/" + h.getHet() + ". hét");
            dataPoint.put("value", osszNyeremeny);

            result.add(dataPoint);
        }

        // Debug: Kiírjuk a konzolra, mit küldünk vissza (Lásd az IntelliJ alsó ablakát)
        System.out.println("DIAGRAM ADATOK: " + result);

        return result;
    }
}