package com.visioture.dao.factory;

import com.visioture.dao.impl.ActorDaoImpl;
import com.visioture.dao.impl.FilmDaoImpl;
import com.visioture.dao.interfaces.ActorDao;
import com.visioture.dao.interfaces.FilmDao;

public class DaoFactory {

	public static ActorDao getActorDao() {
		return new ActorDaoImpl();
	}

	public static FilmDao getFilmDao() {
		return new FilmDaoImpl();
	}

}
