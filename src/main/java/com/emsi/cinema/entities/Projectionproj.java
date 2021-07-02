package com.emsi.cinema.entities;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="p1",types= {com.emsi.cinema.entities.Projection.class})
public interface Projectionproj extends JpaRepository<Projection, Long> {
	public void getId();
	public void getPrix();
	public Date getDateProjection();
	public Salle getSalle();
	public Film getFilm();
	public Seance getSeance();
	public Collection<Ticket> getTickets();

}
