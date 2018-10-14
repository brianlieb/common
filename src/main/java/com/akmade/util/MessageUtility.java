package com.akmade.util;

import com.akmade.protobuf.Msg;
import io.vavr.Function2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class MessageUtility {
    private static final Function2<Msg.Severity, String, Msg> makeMessage =
            (sev, msg) -> Msg.newBuilder().setSeverity(sev).setMessage(msg).build();

    private static final Function2<Msg.Severity, String, Collection<Msg>> makeMessages =
            (sev, msg) -> {
                Collection<Msg> collection = new ArrayList<>();
                collection.add(makeMessage.apply(sev, msg));
                return collection;
            };

    public static final Function<String, Msg> makeError =
            s -> makeMessage.apply(Msg.Severity.ERROR, s);

    public static final Function<String, Collection<Msg>> makeErrors =
            s -> makeMessages.apply(Msg.Severity.ERROR, s);

    public static final Function<String, Msg> makeWarning =
            s -> makeMessage.apply(Msg.Severity.WARNING, s);

    public static final Function<String, Collection<Msg>> makeWarnings =
            s -> makeMessages.apply(Msg.Severity.WARNING, s);

    public static final Function<String, Msg> makeException =
            s -> makeMessage.apply(Msg.Severity.EXCEPTION, s);

    public static final Function<String,  Collection<Msg>> makeExceptions =
            s -> makeMessages.apply(Msg.Severity.EXCEPTION, s);

    public static final Function<String, Msg> makeInfo =
            s -> makeMessage.apply(Msg.Severity.INFO, s);

    public static final Function<String, Collection<Msg>> makeInfos =
            s -> makeMessages.apply(Msg.Severity.INFO, s);

}
