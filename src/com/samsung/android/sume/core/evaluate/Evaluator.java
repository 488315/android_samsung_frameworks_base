package com.samsung.android.sume.core.evaluate;

import java.util.stream.Stream;

/* loaded from: classes6.dex */
public interface Evaluator extends Comparable<Evaluator> {
    Evaluator and(Evaluator evaluator);

    <V> boolean evaluate(V v);

    <V> V getValue();

    Class<?> getValueType();

    Evaluator or(Evaluator evaluator);

    Stream<Evaluator> stream();

    static <T extends Comparable<T>> Evaluator eq(T t) {
        return new Equal(t);
    }

    static <T extends Comparable<T>> Evaluator ne(T t) {
        return new NotEqual(t);
    }

    static <T extends Comparable<T>> Evaluator le(T t) {
        return new LessEqual(t);
    }

    static <T extends Comparable<T>> Evaluator lt(T t) {
        return new LessThan(t);
    }

    static <T extends Comparable<T>> Evaluator ge(T t) {
        return new GreaterEqual(t);
    }

    static <T extends Comparable<T>> Evaluator gt(T t) {
        return new GreaterThan(t);
    }
}
