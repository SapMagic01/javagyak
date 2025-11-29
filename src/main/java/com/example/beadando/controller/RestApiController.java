package com.example.beadando.controller;

import com.example.beadando.entity.Huzas;
import com.example.beadando.repository.HuzasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Ez jelzi, hogy itt JSON válaszok lesznek (REST API)
@RequestMapping("/api/huzasok")
public class RestApiController {

    @Autowired
    private HuzasRepository huzasRepository;

    // 1. GET: Összes húzás lekérése
    @GetMapping
    public List<Huzas> getAllHuzas() {
        return huzasRepository.findAllByOrderByEvDescHetDesc();
    }

    // 2. GET: Egy konkrét húzás lekérése ID alapján
    @GetMapping("/{id}")
    public ResponseEntity<Huzas> getHuzasById(@PathVariable Long id) {
        return huzasRepository.findById(id)
                .map(huzas -> ResponseEntity.ok().body(huzas))
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST: Új húzás létrehozása (JSON-ból)
    @PostMapping
    public Huzas createHuzas(@RequestBody Huzas huzas) {
        return huzasRepository.save(huzas);
    }

    // 4. DELETE: Törlés
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHuzas(@PathVariable Long id) {
        if (!huzasRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        huzasRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}