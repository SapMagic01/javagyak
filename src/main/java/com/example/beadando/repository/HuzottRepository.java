package com.example.beadando.repository;

import com.example.beadando.entity.Huzott;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HuzottRepository extends JpaRepository<Huzott, Long> {

    List<Huzott> findByHuzasId(Long huzasId);
}