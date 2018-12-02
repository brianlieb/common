package com.akmade.util;

import com.akmade.common.proto.Msg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ServicePackage<T> {

    private static final Collection<Msg> EMPTY_MESSAGES = new ArrayList<>();
    T object;
    Collection<Msg> msg;

    private ServicePackage(T object, Collection<Msg> msg) {
        this.object = object;
        this.msg = msg;
    }

    public static <X> ServicePackage<X> of (X object) {
        return new ServicePackage<>(object, EMPTY_MESSAGES);
    }

    public static <X> ServicePackage<X> of (X object, Collection<Msg> messages) {
        return new ServicePackage<>(object, messages);
    }

    public static <X> ServicePackage<X> ofReply(Reply<X> reply, X start) {
        return new ServicePackage<>(reply.orElse(start), reply.messagesOrElse(EMPTY_MESSAGES));
    }

    public static <X> ServicePackage<X> ofOptional(Reply<Optional<X>> reply, X start) {
        return new ServicePackage<>(reply.orElse(Optional.empty()).orElse(start), reply.messagesOrElse(EMPTY_MESSAGES));
    }




}
