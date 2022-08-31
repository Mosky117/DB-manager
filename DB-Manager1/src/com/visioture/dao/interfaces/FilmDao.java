package com.visioture.dao.interfaces;

import java.util.List;

import com.visioture.model.dto.Film;

public interface FilmDao {

	List<Film> getAllFilms();

	// dato il nome e cognome di un attore e un anno di uscita dei film,
	// restituisce i titoli dei film in cui ha recitato in quell'anno
	List<Film> getFilmByActorAndReleaseYear(String firstName, String lastName, int year);
}
