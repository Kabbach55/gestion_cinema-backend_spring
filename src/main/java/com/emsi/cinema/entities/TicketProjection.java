package com.emsi.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="ticketProj",types= {com.emsi.cinema.entities.Ticket.class})
public interface TicketProjection {
	public Long getId();
	public String getNomClient();
	public double getPrix();
	public Integer getcodePayement();	
	public  Place getPlace();
	public boolean getReservee();

}
