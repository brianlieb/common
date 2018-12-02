package com.akmade.util;


import com.akmade.common.proto.Msg;
import com.akmade.common.proto.MsgList;
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

    public static final Function<String, Msg> MAKE_ERROR =
            s -> makeMessage.apply(Msg.Severity.ERROR, s);

    public static final Function<String, Collection<Msg>> MAKE_ERRORS =
            s -> makeMessages.apply(Msg.Severity.ERROR, s);

    public static final Function<String, Msg> MAKE_WARNING =
            s -> makeMessage.apply(Msg.Severity.WARNING, s);

    public static final Function<String, Collection<Msg>> MAKE_WARNINGS =
            s -> makeMessages.apply(Msg.Severity.WARNING, s);

    public static final Function<String, Msg> MAKE_EXCEPTION =
            s -> makeMessage.apply(Msg.Severity.EXCEPTION, s);

    public static final Function<String,  Collection<Msg>> MAKE_EXCEPTIONS =
            s -> makeMessages.apply(Msg.Severity.EXCEPTION, s);

    public static final Function<String, Msg> MAKE_INFO =
            s -> makeMessage.apply(Msg.Severity.INFO, s);

    public static final Function<String, Collection<Msg>> MAKE_INFOS =
            s -> makeMessages.apply(Msg.Severity.INFO, s);


    public static final Function<Collection<Msg>, MsgList> MAKE_MSG_LIST =
            msgs -> MsgList.newBuilder().addAllMessages(msgs).build();


}
