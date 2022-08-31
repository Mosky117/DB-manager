package com.visioture.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.visioture.model.dto.Actor;
import com.visioture.model.dto.Film;
import com.visioture.service.ServiceAPI;

public class Test {

	public static void main(String[] args) {

		ServiceAPI service = new ServiceAPI();
		
//		print(service.getThreeMostPopularActors());
		
//		print(service.getFilmByActorAndReleaseYear("ed", "chase", 2006));
		
//		print(service.getAllActors());
		
		//print(service.getActorsByFirstNameOrLastName("Penelope", "Guiness"));
		int rs = service.insert("Giorgio", "Magalli");
		switch(rs) {
		case 1: System.out.println("inserimento riuscito");
		break;
		case 0: System.out.println("Inserimento fallito");
		break;
		case -1: System.out.println("Problema!");
		break;
		default: System.out.println("Qualcosa è andato storto");
		}

	}

	
	private static void print(List<?> myList) {
		for(Object o : myList) {
			System.out.println(o);
		}
		System.out.println();
	}
	
	
}
