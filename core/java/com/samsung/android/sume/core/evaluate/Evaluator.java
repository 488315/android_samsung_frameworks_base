package com.samsung.android.sume.core.evaluate;

import java.util.stream.Stream;

public interface Evaluator extends Comparable<Evaluator> {
    Evaluator and(Evaluator evaluator);

    <V> boolean evaluate(V v);

    <V> V getValue();

    Class<?> getValueType();

    Evaluator or(Evaluator evaluator);

    Stream<Evaluator> stream();

    static <T extends Comparable<T>> Evaluator eq(T value) {
        return new Equal(value);
    }

    static <T extends Comparable<T>> Evaluator ne(T value) {
        return new NotEqual(value);
    }

    static <T extends Comparable<T>> Evaluator le(T value) {
        return new LessEqual(value);
    }

    static <T extends Comparable<T>> Evaluator lt(T value) {
        return new LessThan(value);
    }

    static <T extends Comparable<T>> Evaluator ge(T value) {
        return new GreaterEqual(value);
    }

    static <T extends Comparable<T>> Evaluator gt(T value) {
        return new GreaterThan(value);
    }
}
