package com.visioture.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.visioture.dao.dbutil.DBTools;
import com.visioture.dao.interfaces.FilmDao;
import com.visioture.model.dto.Film;

public class FilmDaoImpl implements FilmDao {

	@Override
	public List<Film> getAllFilms() {
		Connection connection = DBTools.getConnection();
		
		List<Film> films = new ArrayList<Film>();
		
		String query = "Select * from Film";

		try {
			Statement st = connection.createStatement();
			ResultSet rs = null;
			rs = st.executeQuery(query);

			while (rs.next()) {

				films.add(createFilm(rs));

			}
			DBTools.closeConnection(connection);
			return films;

		} catch (Exception e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}

	}

	@Override
	public List<Film> getFilmByActorAndReleaseYear(String firstName, String lastName, int year) {
		Connection connection = DBTools.getConnection();

		List<Film> result = new ArrayList<Film>();

		String query = "SELECT film.* FROM film_actor INNER JOIN film ON film_actor.film_id = film.film_id INNER JOIN actor ON film_actor.actor_id = actor.actor_id WHERE film.release_year = ? AND actor.first_name = ? AND actor.last_name = ?;";

		ResultSet rs = null;

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setInt(1, year);
			ps.setString(2, firstName);
			ps.setString(3, lastName);

			rs = ps.executeQuery();

			while (rs.next()) {
				result.add(createFilm(rs));
			}

			DBTools.closeConnection(connection);
			return result;

		} catch (SQLException e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}

	}
	
	private Film createFilm(ResultSet rs) throws SQLException {
		int filmId = rs.getInt("film_id");
		String title = rs.getString("title");
		String description = rs.getString("description");
		int releaseYear = rs.getInt("release_year");
		int languageId = rs.getInt("language_id");
		int originalLanguageId = rs.getInt("original_language_id");
		int rentalDuration = rs.getInt("rental_duration");
		double rentalRate = rs.getDouble("rental_rate");
		int length = rs.getInt("length");
		double replacementCost = rs.getDouble("replacement_cost");
		String rating = rs.getString("rating");
		Set<String> specialFeatures = new HashSet<>(Arrays.asList(rs.getString("special_features").split(",")));
		Timestamp lastUpdate = rs.getTimestamp("last_update");

		Film film = new Film();
		film.setFilmId(filmId);
		film.setTitle(title);
		film.setDescription(description);
		film.setReleaseYear(releaseYear);
		film.setLanguageId(languageId);
		film.setOriginalLanguageId(originalLanguageId);
		film.setRentalRate(rentalRate);
		film.setRentalDuration(rentalDuration);
		film.setLength(length);
		film.setReplacementCost(replacementCost);
		film.setRating(Film.Rating.getRating(rating));
		film.setSpecialFeatures(specialFeatures);
		film.setLastUpdate(lastUpdate);
		return film;
	}

}
