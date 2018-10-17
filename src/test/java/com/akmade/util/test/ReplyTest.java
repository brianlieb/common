package com.akmade.util.test;

import com.akmade.common.proto.Msg;
import com.akmade.util.Reply;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.akmade.util.MessageUtility.MAKE_ERROR;
import static org.junit.Assert.*;

public class ReplyTest {


    @Test
    public void ofTest() {
        Reply<Integer> reply = Reply.of(1);
        assertTrue(reply.isPresent());
        assert(replyHasNoMessages(reply));

        try {
            Reply<Integer> replyNull = Reply.of(null);
        } catch (NullPointerException e) {
            assert(true);
        }

    }

    @Test
    public void ofNullableTest() {
        Reply<Integer> reply = Reply.ofNullable(1);
        assertTrue(reply.isPresent());
        assert(replyHasNoMessages(reply));

        Reply<Integer> replyNull = Reply.ofNullable(null);
        assertFalse(replyNull.isPresent());
        assert(replyHasNoObject(replyNull));
        assertEquals(1, replyNull.messages().size());
    }


    private static boolean replyHasNoObject(Reply<Integer> reply) {
        try {
            reply.get();
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    private static boolean replyHasNoMessages(Reply<Integer> reply) {
        try {
            reply.messages();
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    @Test
    public void emptyTest() {
        Reply<Integer> reply = Reply.empty();
        assertFalse(reply.isPresent());
        assertTrue(reply.messages().size() == 1);
        assertTrue(reply.messages().stream().anyMatch(m -> m.equals(Reply.EMPTY_MESSAGE)));
        assert(replyHasNoObject(reply));

        Msg error = MAKE_ERROR.apply("Error Message");
        Reply<Integer> reply2 = Reply.empty(error);
        assertFalse(reply2.isPresent());
        assertTrue(reply2.messages().size() == 1);
        assertTrue(reply2.messages().stream().anyMatch(m -> m.equals(error)));
        assert(replyHasNoObject(reply2));

        Msg error2 =  MAKE_ERROR.apply("Error Message 2");
        Collection<Msg> errors = new ArrayList<Msg>(){{add(error); add(error2);}};
        Reply<Integer> reply3 = Reply.empty(errors);
        assertFalse(reply3.isPresent());
        assertEquals(2, reply3.messages().size());
        assertTrue(reply3.messages().stream().anyMatch(m -> m.equals(error)));
        assertTrue(reply3.messages().stream().anyMatch(m -> m.equals(error2)));
        assert(replyHasNoObject(reply3));


        Reply<Integer> reply4 = Reply.empty(error, error2);
        assertFalse(reply4.isPresent());
        assertEquals(2, reply4.messages().size());
        assertTrue(reply4.messages().stream().anyMatch(m -> m.equals(error)));
        assertTrue(reply4.messages().stream().anyMatch(m -> m.equals(error2)));
        assert(replyHasNoObject(reply4));


        Reply<Integer> reply5 = Reply.empty(error, error2);
        assertFalse(reply5.isPresent());
        assertEquals(2, reply5.messages().size());
        assertTrue(reply5.messages().stream().anyMatch(m -> m.equals(error)));
        assertTrue(reply5.messages().stream().anyMatch(m -> m.equals(error2)));
        assert(replyHasNoObject(reply5));
    }

    @Test
    public void getTest() {
        Reply<Integer> reply = Reply.of(1);
        assertEquals((Integer)1, reply.get());

        Reply<Integer> reply2 = Reply.ofNullable(1);
        assertEquals((Integer)1, reply2.get());

        Reply<Integer> reply3 = Reply.ofNullable(null);
        try {
            reply3.get();
        } catch (NoSuchElementException e) {
            assert(true);
        }
    }


    @Test
    public void orElseTest() {
        Reply<Integer> reply = Reply.of(1);
        assertEquals((Integer)1, reply.orElse(5));

        Reply<Integer> reply3 = Reply.ofNullable(null);
        assertEquals((Integer)5, reply3.orElse(5));
    }

    @Test
    public void orElseGetTest() {
        Reply<Integer> reply = Reply.of(1);
        assertEquals((Integer)1, reply.orElseGet(() -> 5));

        Reply<Integer> reply3 = Reply.ofNullable(null);
        assertEquals((Integer)5, reply3.orElseGet(() -> 5));

        try {
            reply3.orElseGet(null);
        } catch (NullPointerException e) {
            assert(true);
        }
    }


    @Test
    public void messagesTest() {
        Msg error = MAKE_ERROR.apply("Error 1");
        Msg error2 = MAKE_ERROR.apply("Error 2");
        Collection<Msg> messages = new ArrayList<Msg>(){{add(error); add(error2);}};
        Reply<Integer> reply = Reply.of(1);
        try {
            reply.messages();
        } catch (NoSuchElementException e) {
            assert(true);
        }
        Collection<Msg> results = reply.messagesOrElse(messages);
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(m -> m.equals(error)));
        assertTrue(results.stream().anyMatch(m -> m.equals(error2)));

        results = reply.messagesOrElseGet(() -> messages);
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(m -> m.equals(error)));
        assertTrue(results.stream().anyMatch(m -> m.equals(error2)));

        try {
            reply.orElseGet(null);
        } catch (NullPointerException e) {
            assert(true);
        }

        Reply<Integer> reply2 = Reply.empty();
        results = reply2.messages();
        assertTrue(results.size() == 1);
        assertTrue(results.stream().anyMatch(m -> m.equals(Reply.EMPTY_MESSAGE)));

        results = reply2.messagesOrElse(messages);
        assertTrue(results.size() == 1);
        assertTrue(results.stream().anyMatch(m -> m.equals(Reply.EMPTY_MESSAGE)));
        results = reply2.messagesOrElseGet(() -> messages);
        assertTrue(results.size() == 1);
        assertTrue(results.stream().anyMatch(m -> m.equals(Reply.EMPTY_MESSAGE)));

        Reply<Integer> reply3 = Reply.empty();
        results = reply3.messagesOrElse(messages);
        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(m -> m.equals(Reply.EMPTY_MESSAGE)));

        results = reply3.messagesOrElseGet(() -> messages);
        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(m -> m.equals(Reply.EMPTY_MESSAGE)));
    }

    @Test
    public void ifPresentTest() {
        Consumer<Collection<Integer>> consumer = collection -> collection.add(5);

        Reply<Collection<Integer>> reply = Reply.of(new ArrayList<Integer>() {{
            add(1);
            add(3);
        }});
        reply.ifPresent(consumer);
        assertTrue(reply.get().contains(5));

        Reply<Collection<Integer>> reply2 = Reply.ofNullable(null);
        reply2.ifPresent(consumer);
        assertFalse(reply2.isPresent());
    }

    @Test
    public void filterTest() {
        Msg error = MAKE_ERROR.apply("Error 1");
        Msg error2 = MAKE_ERROR.apply("Error 2");
        Msg error3 = null;
        Collection<Msg> messages = new ArrayList<Msg>(){{add(error); add(error2);}};


        Reply<Integer> reply = Reply.of(6);
        Reply<Integer> results = reply.filter(i -> i%2==0);
        assertTrue(results.isPresent());
        Reply<Integer> results2 = reply.filter(i -> i%5==0);
        assertFalse(results2.isPresent());
        assertTrue(results2.messages().size()==1);
        assertTrue(results2.messages().stream().anyMatch(m -> m.equals(Reply.EMPTY_MESSAGE)));


        try {
            results.filter(null);
        } catch (NullPointerException e ) {
            assert(true);
        }

        Reply<Integer> results3 = reply.filter(i -> i%2==0, error);
        assertTrue(results3.isPresent());
        Reply<Integer> results4 = reply.filter(i -> i%5==0, error);
        assertFalse(results4.isPresent());
        assertTrue(results4.messages().size()==1);
        assertTrue(results4.messages().stream().anyMatch(m -> m.equals(error)));


        try {
            reply.filter(i -> i%2==0, error3);
        } catch (NullPointerException e ) {
            assert(true);
        }

        Reply<Integer> results5 = reply.filter(i -> i%2==0, messages);
        assertTrue(results3.isPresent());
        Reply<Integer> results6 = reply.filter(i -> i%5==0, messages);
        assertFalse(results6.isPresent());
        assertTrue(results6.messages().size()==2);
        assertTrue(results6.messages().stream().anyMatch(m -> m.equals(error)));
        assertTrue(results6.messages().stream().anyMatch(m -> m.equals(error2)));
    }

    @Test
    public void mapTest() {
        Msg error = MAKE_ERROR.apply("Error ");
        Function<String, Integer> mapper = Integer::valueOf;
        Reply<Integer> results = Reply.of("10").map(mapper);
        assertTrue(results.isPresent());
        Reply<Integer> results2 = Reply.<String>empty(error).map(mapper);
        assertFalse(results2.isPresent());
        assertTrue(results2.messages().size() == 1);
        assertTrue(results2.messages().stream().anyMatch(m -> m.equals(error)));
    }

    @Test
    public void flatMapTest() {
        Msg error = MAKE_ERROR.apply("Can't map to Integer");

        Function<String, Reply<Integer>> mapper =
                s ->
                {
                    try {
                        return Reply.of(Integer.valueOf(s));
                    } catch (Exception e ) {
                        return Reply.empty(error);
                    }
                };

        Reply<Integer> results = Reply.of("10").flatMap(mapper);
        assertTrue(results.isPresent());

        Reply<Integer> results2 = Reply.of("asd").flatMap(mapper);
        assertFalse(results2.isPresent());
        assertTrue(results2.messages().stream().anyMatch(m -> m.equals(error)));


        Reply<Integer> results3 = Reply.<String>empty().flatMap(mapper);
        assertFalse(results3.isPresent());
        assertTrue(results3.messages().size() == 1);
        assertTrue(results3.messages().stream().anyMatch(m -> m.equals(Reply.EMPTY_MESSAGE)));
    }

    @Test
    public void orTest() {
        Reply<Integer> results = Reply.of(1).or(5);
        assertTrue(results.isPresent());
        assertEquals((Integer)1, results.get());

        results = Reply.<Integer>empty().or(5);
        assertTrue(results.isPresent());
        assertEquals((Integer)5, results.get());

        results = Reply.of(1).or(() -> Reply.of(5));
        assertTrue(results.isPresent());
        assertEquals((Integer)1, results.get());

        results = Reply.<Integer>empty().or(() -> Reply.of(5));
        assertTrue(results.isPresent());
        assertEquals((Integer)5, results.get());
    }

    @Test
    public void streamTest() {
        Reply<Integer> reply = Reply.of(1);
        assertEquals((Integer)2, reply.stream().map(i -> i*2).findFirst().get());
    }

    @Test
    public void ofOptional() {
        Integer nullInt = null;
        Msg error = MAKE_ERROR.apply("Error 1");
        Msg error2 = MAKE_ERROR.apply("Error 2");
        Collection<Msg> messages = new ArrayList<Msg>(){{add(error); add(error2);}};

        Reply<Integer> reply = Reply.ofOptional(Optional.of(3));
        assertTrue (reply.isPresent());
        assertEquals((Integer)3, reply.get());

        reply = Reply.ofOptional(Optional.ofNullable(nullInt));
        assertFalse(reply.isPresent());
        assertTrue(reply.messages().size() == 1);
        assertTrue(reply.messages().stream().anyMatch(m -> m.equals(Reply.EMPTY_MESSAGE)));

        reply = Reply.ofOptional(Optional.of(3), error);
        assertTrue (reply.isPresent());
        assertEquals((Integer)3, reply.get());

        reply = Reply.ofOptional(Optional.ofNullable(nullInt),error);
        assertFalse(reply.isPresent());
        assertTrue(reply.messages().size() == 1);
        assertTrue(reply.messages().stream().anyMatch(m -> m.equals(error)));

        reply = Reply.ofOptional(Optional.of(3), messages);
        assertTrue (reply.isPresent());
        assertEquals((Integer)3, reply.get());

        reply = Reply.ofOptional(Optional.ofNullable(nullInt),messages);
        assertFalse(reply.isPresent());
        assertTrue(reply.messages().size() == 2);
        assertTrue(reply.messages().stream().anyMatch(m -> m.equals(error)));
        assertTrue(reply.messages().stream().anyMatch(m -> m.equals(error2)));
    }

}
