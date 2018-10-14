package com.akmade.hibernate;


import org.hibernate.Session;

import java.util.Optional;

import static com.akmade.hibernate.BaseRepository.logAndThrowError;
import static com.akmade.hibernate.SessionUtility.*;

@FunctionalInterface
public interface Qry<T> {
	Optional<T> execute(Session s);
	
	default Optional<T> run(HibernateSessionFactory.DataSource ds) {
		Session session = createSession(ds);
		logger.debug("Running query."); 
		try {
			return this.execute(session);
		} catch (Exception e) {
			rollbackAndClose(session);
			throw logAndThrowError("Error running the query. " + e.getMessage());
		} finally {
			endSession(session);
		}
	}
}
