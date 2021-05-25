package com.emsi.cinema.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emsi.cinema.dao.CategorieRepository;
import com.emsi.cinema.dao.CinemaRepository;
import com.emsi.cinema.dao.FilmRepository;
import com.emsi.cinema.dao.PlaceRepository;
import com.emsi.cinema.dao.ProjectionRepository;
import com.emsi.cinema.dao.SalleRepository;
import com.emsi.cinema.dao.SeanceRepository;
import com.emsi.cinema.dao.TicketRepository;
import com.emsi.cinema.dao.VilleRepository;
import com.emsi.cinema.entities.Categorie;
import com.emsi.cinema.entities.Cinema;
import com.emsi.cinema.entities.Film;
import com.emsi.cinema.entities.Place;
import com.emsi.cinema.entities.Projection;
import com.emsi.cinema.entities.Salle;
import com.emsi.cinema.entities.Seance;
import com.emsi.cinema.entities.Ticket;
import com.emsi.cinema.entities.Ville;
@Service
@Transactional
public class  CinemaInitServiceImpl implements ICinemaInitService    {
	
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public void initVilles() {
		Stream.of("casablanca","marrakech","rabat","tanger").forEach(nomVille->{
			Ville ville = new Ville();
			ville.setNom(nomVille);
			villeRepository.save(ville);
		});
		
	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(v->{
			Stream.of("MegaRama","IMax","Founoun","Chahrazad","Daouliz","kawakeb","elbayda").forEach(nomCinema->{
				Cinema cinema = new Cinema();
				cinema.setName(nomCinema);
				cinema.setNombreDeSalles((int)(3+Math.random()*7));
				cinema.setVille(v);
				cinemaRepository.save(cinema);
			});
		});
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema->{
			for (int i=0;i<cinema.getNombreDeSalles();i++) {
				Salle salle = new Salle();
				salle.setNom("sale "+(i+1));
				salle.setCinema(cinema);
				salle.setNbrPlace((int)(15+Math.random()*35));
				salleRepository.save(salle);
			}
		});
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle->{
			for(int i=0;i<salle.getNbrPlace();i++) {
				Place place = new Place();
				place.setNumeroPlace(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
		});
		
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void initCategories() {
		Stream.of("horror","Action","Fiction","Drama").forEach(c->{
			Categorie categorie = new Categorie();
			categorie.setName(c);
			categorieRepository.save(categorie);
		});
		
	}

	@Override
	public void initFilms() {
		double[] durees = new double[] {1,1.5,2,2.5};
		List<Categorie> categories = categorieRepository.findAll();
		Stream.of("12 Homes En Colaire","Forrest Gump","Green Book","La Ligne Verte","Le Parin","Le Seigneur Des Anneaux").forEach(f->{
			Film film = new Film();
			film.setTitre(f);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(f.replaceAll(" ", "")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
		});
		
	}

	@Override
	public void initProjections() {
double[] prices = new double[] {30,40,50,60,70,80,90,100};
		
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					filmRepository.findAll().forEach(film->{
						seanceRepository.findAll().forEach(seance->{
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							projectionRepository.save(projection);
						});
					});
				});
			});
		});
		
		
		
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(projection->{
			projection.getSalle().getPlaces().forEach(place->{
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(projection.getPrix());
				ticket.setProjection(projection);
				ticket.setReservee(false);
				ticketRepository.save(ticket);
			});
		});
		
	}

}
