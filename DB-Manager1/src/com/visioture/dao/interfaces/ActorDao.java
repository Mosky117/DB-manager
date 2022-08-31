package com.visioture.dao.interfaces;

import java.util.List;

import com.visioture.model.dto.Actor;

public interface ActorDao {
	List<Actor> getAllActors();
	
	// dato il titolo del film, restituisce tutti gli attori
	// che hanno recitato in quel film
	List<Actor> getActorsInFilm(String title);
	
	// restituisce i tre attori che hanno recitato in più film (primo secondo e terzo)
	List<Actor> getThreeMostPopularActors();
	
	// Restituisce una lista di attori dato un nome OR un cognome
	List<Actor> getActorsByFirstNameOrLastName(String firstName, String lastName);
	
	//Restiuisce una striga di conferma oppure no, che la query sia stata o non eseguita
	int insert(String firstName, String lastName);
}
