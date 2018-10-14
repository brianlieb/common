package com.akmade.util.test;

import com.akmade.util.Reply;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        String error = "Error Message";
        Reply<Integer> reply2 = Reply.empty(error);
        assertFalse(reply2.isPresent());
        assertTrue(reply2.messages().size() == 1);
        assertTrue(reply2.messages().stream().anyMatch(m -> m.equals(error)));
        assert(replyHasNoObject(reply2));

        String error2 = "Error Message 2";
        Collection<String> errors = new ArrayList<String>(){{add(error); add(error2);}};
        Reply<Integer> reply3 = Reply.empty(errors);
        assertFalse(reply3.isPresent());
        assertEquals(2, reply3.messages().size());
        assertTrue(reply3.messages().stream().anyMatch(m -> m.equals(error)));
        assertTrue(reply3.messages().stream().anyMatch(m -> m.equals(error2)));
        assert(replyHasNoObject(reply3));
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
        assertEquals((Integer)1, reply.orElse(5));

        Reply<Integer> reply3 = Reply.ofNullable(null);
        assertEquals((Integer)5, reply3.orElse(5));
    }


}
