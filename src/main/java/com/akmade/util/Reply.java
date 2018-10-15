package com.akmade.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A Reply is a form of an optional, but it contains a collection of Strings
 * describing why there is no object
 * @param <T> - The type of object contained in the Reply.
 */
public class Reply<T> {
    public static final String EMPTY_MESSAGE = "There is no object";
    private T object;
    private Collection<String> messages;

    /**
     * Constructs an empty instance, with a default message.
     */
    private Reply() {
        this.object = null;
        messages = new ArrayList<String>(){{add(EMPTY_MESSAGE);}};
    }

    /**
     * Constructs an instance with the passed in object and null messages.
     * @param object - The object to set in the Reply.
     */
    private Reply(T object) {
        this.object = Objects.requireNonNull(object);
        this.messages = null;
    }

    /**
     * Constructs an empty instance, with the passed in Messages as the messages.
     * @param messages A {@link Collection} of messages.
     */
    private Reply(Collection<String> messages) {
        this.object = null;
        this.messages = Objects.requireNonNull(messages);
    }

    /**
     * Creates an empty instance with the default message as the only message in the Collection
     * @param <T> - The type of the non-existent value
     * @return an empty {@code Reply}
     */
    public static <T> Reply<T> empty() {
        return empty(EMPTY_MESSAGE);
    }

    /**
     * Creates an empty instance with the message as the only message in the Collection
     * @param message - The message to set as the only message
     * @param <T> - The type of the non-existent value
     * @return an empty {@code Reply}
     */
    public static <T> Reply<T> empty(String message) {
        Objects.requireNonNull(message);
        return empty(new ArrayList<String>(){{add(message);}});
    }

    /**
     * Creates an empty instance with the messages as the only {@code Reply} messages
     *
     * @param messages - The {@link Collection} of messages
     * @param <T> - The type of the non-existent value
     * @return an empty {@code Reply}
     */
    public static <T> Reply<T> empty(Collection<String> messages) {
        return new Reply<>(messages);
    }

    /**
     * Returns an {@code Reply} describing the given non-{@code null}
     * value.
     *
     * @param value the value to describe, which must be non-{@code null}
     * @param <T> the type of the value
     * @return an {@code Reply} with the value present
     * @throws NullPointerException if value is {@code null}
     */
    public static <T> Reply<T> of(T value) {
        return new Reply<>(value);
    }

    /**
     * Returns an {@code Reply} describing the given non-{@code null}
     * {@link Optional} value, containing the value from the {@code Optional}
     * or an empty containing the messages.
     *
     * @param optional the value to describe, which must be non-{@code null}
     * @param messages the messages to assign to empty if no value is in the optional
     * @param <T> the type of the value
     * @return an {@code Reply} with the value contained in the {@code Optional}, or empty with the messages
     * if the {@code Optional} contains no object.
     * @throws NullPointerException if optional is {@code null}
     */
    public static <T> Reply<T> ofOptional(Optional<T> optional, Collection<String> messages) {
        Objects.requireNonNull(optional);
        return optional.map(Reply::of)
                .orElse(Reply.empty(messages));
    }

    /**
     * Returns an {@code Reply} describing the given non-{@code null}
     * {@link Optional} value, containing the value from the {@code Optional}
     * or an empty containing the message.
     *
     * @param optional the value to describe, which must be non-{@code null}
     * @param message the messages to assign to empty if no value is in the optional
     * @param <T> the type of the value
     * @return an {@code Reply} with the value contained in the {@code Optional}, or empty with the message
     * if the {@code Optional} contains no object.
     * @throws NullPointerException if optional is {@code null}
     */
    public static <T> Reply<T> ofOptional(Optional<T> optional, String message) {
        return ofOptional(optional, new ArrayList<String>(){{add(message);}});
    }

    /**
     * Returns an {@code Reply} describing the given non-{@code null}
     * {@link Optional} value, containing the value from the {@code Optional}
     * or the default messages.
     *
     * @param optional the value to describe, which must be non-{@code null}
     * @param <T> the type of the value
     * @return an {@code Reply} with the value contained in the {@code Optional}, or empty if the {@code Optional} contains no object.
     * @throws NullPointerException if optional is {@code null}
     */
    @SuppressWarnings("")
    public static <T> Reply<T> ofOptional(Optional<T> optional) {
        return ofOptional(optional, EMPTY_MESSAGE);
    }

    /**
     * Returns an {@code Reply} describing the given value, if
     * non-{@code null}, otherwise returns an empty {@code Reply}.
     *
     * @param value the possibly-{@code null} value to describe
     * @param <T> the type of the value
     * @return an {@code Reply} with a present value if the specified value
     *         is non-{@code null}, otherwise an empty {@code Reply} with a default message
     */
    public static <T> Reply<T> ofNullable(T value) {
        return value == null
                ? new Reply<>()
                : new Reply<>(value);
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @return the non-{@code null} value described by this {@code Reply}
     * @throws NoSuchElementException if no value is present
     */
    public T get() {
        if (object == null)
            throw new NoSuchElementException("No object present");
        return object;
    }

    /**
     * If a value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no value is present.
     *        May be {@code null}.
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return object!=null
                ? object
                : other;
    }

    /**
     * If a value is present, returns the value, otherwise returns the result
     * produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the value, if present, otherwise the result produced by the
     *         supplying function
     * @throws NullPointerException if no value is present and the supplying
     *         function is {@code null}
     */
    public T orElseGet(Supplier<T> supplier) {
        return orElse(supplier.get());
    }

    /**
     * If a messages are populated, returns them, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @return the non-{@code null} messages
     * @throws NoSuchElementException if no value is present
     */
    public Collection<String> messages() {
        if (messages == null)
            throw new NoSuchElementException("There are no messages");
        return messages;
    }

    /**
     * If a messages are populated, returns them, otherwise returns the passed in messages.
     *
     * @param messages the messages to be returned, if none are present.
     *        May be {@code null}.
     * @return the messages, if present, otherwise {@code messages}
     */
    public Collection<String> messagesOrElse(Collection<String> messages) {
        if (this.messages == null)
            return messages;
        return this.messages;
    }

    /**
     * If a messages are populated, returns them, otherwise returns the result
     * produced by the supplying function.
     * @param supplier the supplying function that produces messages to be returned
     * @return the messages, if present, otherwise the result produced by the
     *       supplying function
     * @throws NullPointerException if no messages are present and the supplying
     *         function is {@code null}
     */
    public Collection<String> messagesOrElseGet(Supplier<Collection<String>> supplier) {
        return messagesOrElse(supplier.get());
    }

    /**
     * If a value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if a value is present, otherwise {@code false}
     */
    public boolean isPresent() {
        return object!=null;
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if a value is present
     * @throws NullPointerException if value is present and the given action is
     *         {@code null}
     */
    public void ifPresent(Consumer<T> action) {
        if (isPresent()) action.accept(object);
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * returns an {@code Reply} describing the value, otherwise returns an
     * empty {@code Reply} with the default messages.
     *
     * @param predicate the predicate to apply to a value, if present
     * @return an {@code Reply} describing the value of this
     *         {@code Reply}, if a value is present and the value matches the
     *         given predicate, otherwise an empty {@code Reply} with the default messages.
     * @throws NullPointerException if the predicate is {@code null}
     */
    public Reply<T> filter(Predicate<T> predicate) {
        return filter(predicate, EMPTY_MESSAGE);
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * returns an {@code Reply} describing the value, otherwise returns an
     * empty {@code Reply} with the message.
     *
     * @param predicate the predicate to apply to a value, if present
     * @param message a message to put in the resulting reply if the predicate fails.
     * @return an {@code Reply} describing the value of this
     *         {@code Reply}, if a value is present and the value matches the
     *         given predicate, otherwise an empty {@code Reply} with the message.
     * @throws NullPointerException if the predicate is {@code null}
     */
    public Reply<T> filter(Predicate<T> predicate, String message) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return empty(message);
        } else {
            return predicate.test(object) ? this : empty(message);
        }
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * returns an {@code Reply} describing the value, otherwise returns an
     * empty {@code Reply} with the messages.
     *
     * @param predicate the predicate to apply to a value, if present
     * @param messages a Collection of messages to put in the resulting reply if the predicate fails.
     * @return an {@code Reply} describing the value of this
     *         {@code Reply}, if a value is present and the value matches the
     *         given predicate, otherwise an empty {@code Reply} with the messages.
     * @throws NullPointerException if the predicate is {@code null}
     */
    public Reply<T> filter(Predicate<T> predicate, Collection<String> messages) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return empty(messages);
        } else {
            return predicate.test(object) ? this : empty(messages);
        }
    }

    /**
     * If a value is present, returns an {@code Reply} describing (as if by
     * {@link #ofNullable}) the result of applying the given mapping function to
     * the value, otherwise returns an empty {@code Reply} the messages.
     *
     * <p>If the mapping function returns a {@code null} result then this method
     * returns an empty {@code Reply} with the default messages.

     * @param mapper the mapping function to apply to a value, if present
     * @param <U> The type of the value returned from the mapping function
     * @return an {@code Reply} describing the result of applying a mapping
     *         function to the value of this {@code Reply}, if a value is
     *         present, otherwise an empty {@code Reply}
     * @throws NullPointerException if the mapping function is {@code null}     */
    public <U> Reply<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty(this.messages);
        } else {
            return Reply.ofNullable(mapper.apply(object));
        }
    }

    /**
     * If a value is present, returns the result of applying the given
     * {@code Reply}-bearing mapping function to the value, otherwise returns
     * an empty {@code Reply}.
     *
     * <p>This method is similar to {@link #map(Function)}, but the mapping
     * function is one whose result is already an {@code Reply}, and if
     * invoked, {@code flatMap} does not wrap it within an additional
     * {@code Reply}.
     *
     * @param <U> The type of value of the {@code Reply} returned by the
     *            mapping function
     * @param mapper the mapping function to apply to a value, if present
     * @return the result of applying an {@code Reply}-bearing mapping
     *         function to the value of this {@code Reply}, if a value is
     *         present, otherwise an empty {@code Reply}
     * @throws NullPointerException if the mapping function is {@code null} or
     *         returns a {@code null} result
     */
    public <U> Reply<U> flatMap(Function<? super T, ? extends Reply<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty(this.messages);
        } else {
            @SuppressWarnings("unchecked")
            Reply<U> r = (Reply<U>) mapper.apply(object);
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns an {@code Reply} describing the value,
     * otherwise returns an {@code Reply} produced by the supplying function.
     *
     * @param supplier the supplying function that produces an {@code Reply}
     *        to be returned
     * @return returns an {@code Reply} describing the value of this
     *         {@code Reply}, if a value is present, otherwise an
     *         {@code Reply} produced by the supplying function.
     * @throws NullPointerException if the supplying function is {@code null} or
     *         produces a {@code null} result
     */
    public Reply<T> or(Supplier<? extends Reply<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            Reply<T> r = (Reply<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns an {@code Reply} describing the value,
     * otherwise returns an {@code Reply} with the passed in object.
     *
     * @param other the supplying function that produces an {@code Reply}
     *        to be returned
     * @return returns an {@code Reply} describing the value of this
     *         {@code Reply}, if a value is present, otherwise an
     *         {@code Reply} of other.
     * @throws NullPointerException if the other is {@code null}
     */
    public Reply<T> or(T other) {
        Objects.requireNonNull(other);
        if (isPresent()) {
            return this;
        } else {
            return of(other);
        }
    }

    /**
     * If a value is present, returns a sequential {@link Stream} containing
     * only that value, otherwise returns an empty {@code Stream}.
     *
     * <pre>{@code
     *     Stream<Optional<T>> os = ..
     *     Stream<T> s = os.flatMap(Optional::stream)
     * }</pre>
     *
     * @return the optional value as a {@code Stream}
     */
    public Stream<T> stream() {
        if (!isPresent()) {
            return Stream.empty();
        } else {
            return Stream.of(object);
        }
    }

}
