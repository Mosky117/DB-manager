package com.visioture.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.visioture.dao.dbutil.DBTools;
import com.visioture.dao.interfaces.ActorDao;
import com.visioture.model.dto.Actor;

public class ActorDaoImpl implements ActorDao {

	@Override
	public List<Actor> getAllActors() {
		Connection connection = DBTools.getConnection();
		
		String query = "SELECT * FROM actor";
		
		List<Actor> result = new ArrayList<Actor>();
		
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Actor actor = getFullActorFromResultSet(rs);
				result.add(actor);
			}
			DBTools.closeConnection(connection);
			return result;

		} catch (Exception e) {
			System.out.println("NON FUNZIONA LA QUERY!");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return null;
		}
	}

	@Override
	public List<Actor> getActorsInFilm(String title) {
		Connection connection = DBTools.getConnection();
		
		List<Actor> result = new ArrayList<Actor>();
		
		String query = "SELECT actor.first_name, actor.last_name FROM film_actor INNER JOIN film ON film_actor.film_id = film.film_id INNER JOIN actor ON film_actor.actor_id = actor.actor_id WHERE film.title = ?;";

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, title);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Actor actor = new Actor();
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
				result.add(actor);
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

	@Override
	public List<Actor> getThreeMostPopularActors() {
		Connection connection = DBTools.getConnection();
		
		List<Actor> result = new ArrayList<Actor>();
		
		String query = "SELECT actor.first_name, actor.last_name, COUNT(film.film_id) AS numFilm FROM film_actor INNER JOIN film ON film_actor.film_id = film.film_id INNER JOIN actor ON film_actor.actor_id = actor.actor_id GROUP BY actor.actor_id ORDER BY numFilm DESC LIMIT 3;";

		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Actor actor = new Actor();
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
				result.add(actor);
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
	

	@Override
	public List<Actor> getActorsByFirstNameOrLastName(String firstName, String lastName) {
		Connection connection = DBTools.getConnection();
		
		List<Actor> result = new ArrayList<Actor>();
		
		String query = "Select * from actor where first_name = ? || last_name = ?;";

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, firstName);
			ps.setString(2, lastName);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Actor actor = getFullActorFromResultSet(rs);
				result.add(actor);
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
	
	public boolean flag;
	@Override
	public int insert(String firstName, String lastName) {
		int state= -1;
		Connection connection = DBTools.getConnection();
		String query = "INSERT INTO actor (actor_id, first_name, last_name, last_update) VALUES (null, ?, ?,current_date());";
		
		try {
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, firstName);
			ps.setString(2, lastName);
			
			int affectedRows = ps.executeUpdate();
			
			DBTools.closeConnection(connection);
			System.out.println("La query DML è avvenuta con successo! :)");
			
			if(affectedRows>0)
				return state=1;
			else
				return state=0;

			
			//return flag;

		} catch (SQLException e) {
			System.out.println("La query DML è fallita! :(");
			System.out.println(e.getMessage());
			DBTools.closeConnection(connection);
			return state;
		}

	}
	
	private Actor getFullActorFromResultSet(ResultSet rs) throws SQLException {
		Actor a = new Actor();
		a.setId(rs.getInt("actor_id"));
		a.setFirstName(rs.getString("first_name"));
		a.setLastName(rs.getString("last_name"));
		a.setLastUpdate(rs.getDate("last_update"));
		return a;
	}

}
