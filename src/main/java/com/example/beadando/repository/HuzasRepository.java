package com.example.beadando.repository;

import com.example.beadando.entity.Huzas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HuzasRepository extends JpaRepository<Huzas, Long> {
    List<Huzas> findAllByOrderByEvDescHetDesc();
}