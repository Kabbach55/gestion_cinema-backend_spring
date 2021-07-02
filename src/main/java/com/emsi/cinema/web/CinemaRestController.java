package com.emsi.cinema.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emsi.cinema.dao.FilmRepository;
import com.emsi.cinema.dao.TicketRepository;
import com.emsi.cinema.entities.Film;
import com.emsi.cinema.entities.Ticket;
import lombok.Data;


@RestController
@CrossOrigin("*")
public class CinemaRestController {
	@Autowired
	private FilmRepository filmRepository ;
	@Autowired
	private TicketRepository  tiketRepository ;
	@GetMapping(path = "/imageFilm/{id}" , produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable(name="id") Long id) throws Exception{
		Film f = filmRepository.findById(id).get();
		String photoName = f.getPhoto();
		File file = new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path) ; 
	}
	
	@PostMapping(path = "/payerTickets")
	@Transactional
	public List<Ticket> payerTicket( @RequestBody TicketForm ticketForm ){
		List<Ticket> tickets = new ArrayList<Ticket>();
		ticketForm.getTickets().forEach(idTicket->{
			Ticket ticket = tiketRepository.findById(idTicket).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setCodePayement(ticketForm.getCodePayement());
			ticket.setReservee(true);
			tiketRepository.save(ticket);
			tickets.add(ticket);
		});
		return tickets;
	}

}

@Data
class TicketForm{
	private String nomClient;
	private int codePayement;
	private List<Long> tickets = new ArrayList<>();
}
