package com.akmade.util;

import com.akmade.protobuf.Msg;
import io.vavr.control.Either;

import java.util.Collection;
import java.util.Optional;

public class ServicePackage<T> {

    T object;
    Collection<Msg> msg;

    private ServicePackage(T object, Collection<Msg> msg) {
        this.object = object;
        this.msg = msg;
    }

    public static <X> ServicePackage<X> of(Either<Collection<Msg>, X> either, X start) {
        return new ServicePackage<>(either.getOrElse(start), either.getLeft());
    }

    public static <X> ServicePackage<X> ofOptional(Either<Collection<Msg>, Optional<X>> either, X start) {
        return new ServicePackage<>(either.getOrElse(Optional.empty()).orElse(start), either.getLeft());
    }



}
