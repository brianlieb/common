package com.akmade.hibernate;

import com.akmade.exceptions.UnrecoverableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BaseRepository {
	protected static Logger logger = LoggerFactory.getLogger(BaseRepository.class);

	protected static final UnrecoverableException logAndThrowError(String msg) throws UnrecoverableException {
		logger.error(msg);
		return new UnrecoverableException(msg);
	}

	protected static final UnrecoverableException logAndThrowError(String msg, Exception e) throws UnrecoverableException {
		UnrecoverableException ex= new UnrecoverableException(msg + "\n" + e.getMessage());
		ex.addSuppressed(e);
		logger.error(msg, ex);
		return ex;
	}


    protected static final <X> Qry<X> saveOrUpdate(X x) {
        return session -> {
                session.saveOrUpdate(x);
                return Optional.of(x);
        };
    }

    protected static final <X> Qry<X> delete(X x) {
        return session -> {
                session.delete(x);
                return Optional.of(x);
        };
    }

	protected static final <X, Z> Function<Collection<X>, Collection<Z>> collectionMapper(Function<X,Z> mapper) {
	    return collection -> collection.stream().map(mapper).collect(Collectors.toList());
    }

    protected static final <X,Z> Qry<Z> prepareQry(Qry<X> results, Function<X,Z> mapper) {
	    return session -> results.execute(session).map(mapper);
    }

    protected static final <X,Z> Qry<Z> prepareQry(Supplier<Qry<X>> results, Function<X,Z> mapper) {
        return prepareQry(results.get(), mapper);
    }

    protected static final <W,X,Y,Z> Function<W, Qry<Z>> prepareQry(Function<W,X> prepMap, Function<X, Qry<Y>> results, Function<Y,Z> mapper) {
        return input ->
                session ->
                        Optional.ofNullable(input)
                                .map(prepMap)
                                .map(x -> results.apply(x).execute(session).orElse(null))
                                .map(mapper);
    }


}
