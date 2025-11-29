package com.example.beadando.repository;

import com.example.beadando.entity.Nyeremeny;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NyeremenyRepository extends JpaRepository<Nyeremeny, Long> {

    List<Nyeremeny> findByHuzasId(Long huzasId);
}