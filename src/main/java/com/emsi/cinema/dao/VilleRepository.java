package com.emsi.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.emsi.cinema.entities.Ville;

@RepositoryRestController
public interface VilleRepository extends JpaRepository<Ville, Long> {

}
