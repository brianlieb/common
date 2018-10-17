package com.akmade.util;

import com.akmade.common.proto.Msg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ServicePackage<T> {

    T object;
    Collection<Msg> msg = new ArrayList<>();

    private ServicePackage(T object, Collection<Msg> msg) {
        this.object = object;
        this.msg = msg;
    }

    public static <X> ServicePackage<X> of(Reply<X> reply, X start) {
        return new ServicePackage<>(reply.orElse(start), reply.messagesOrElse(new ArrayList<>()));
    }

    public static <X> ServicePackage<X> ofOptional(Reply<Optional<X>> reply, X start) {
        return new ServicePackage<>(reply.orElse(Optional.empty()).orElse(start), reply.messagesOrElse(new ArrayList<>()));
    }



}
