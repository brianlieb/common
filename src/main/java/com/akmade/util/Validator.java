package com.akmade.util;

import com.akmade.protobuf.Msg;
import io.vavr.Function2;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Validator<T> {
    Collection<ValidationItem> items;

    private Validator (Builder<T> builder) {
        this.items = builder.items_;
    }


    public static <X> Builder<X> newBuilder() {
        return new Builder<>();
    }


    private Function2<ValidationItem, T, Either<Msg, T>> makeValidation =
            (validationItem, t) ->
                validationItem.predicate.test(t)
                        ? Either.right(t)
                        : Either.left(validationItem.msg);


    private Function<Seq<T>, T> reduce =
        ts -> ts.toJavaStream().reduce((t1, t2) -> t1).orElse(null);

    private Function<Seq<Msg>, Collection<Msg>> makeCollection = Seq::asJava;

    public Either<Collection<Msg>, T> validate(T t) {
              return Either.sequence(items.stream()
                    .map(i -> makeValidation.apply(i))
                    .map(v -> v.apply(t))
                    .collect(Collectors.toList()))
                      .map(reduce)
                      .mapLeft(makeCollection);
    }


    public static class Builder<X> {
        Collection<ValidationItem> items_ = new ArrayList<>();

        public Builder<X> addValidation(Predicate predicate, Msg msg) {
            items_.add(ValidationItem.test(predicate, msg));
            return this;
        }

        public Builder<X> addValidation(Validator validator) {
            items_.addAll(validator.items);
            return this;
        }

        public Builder<X> addValidation(ValidationItem item) {
            items_.add(item);
            return this;
        }

        public Builder<X> addValidation(Collection<ValidationItem> items) {
            items_.addAll(items);
            return this;
        }

        public Validator<X> build() {
            return new Validator<>(this);
        }
    }


    public static class ValidationItem {
        Predicate predicate;
        Msg msg;

        private ValidationItem(Predicate predicate, Msg msg) {
            this.predicate = predicate;
            this.msg = msg;
        }

        public static ValidationItem test(Predicate predicate, Msg msg) {
            return new ValidationItem(predicate, msg);
        }


    }





}
