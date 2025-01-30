package com.samsung.android.sume.core.evaluate;

import java.util.stream.Stream;

/* loaded from: classes4.dex */
public interface Evaluator extends Comparable<Evaluator> {
    Evaluator and(Evaluator evaluator);

    <V> boolean evaluate(V v);

    <V> V getValue();

    Class<?> getValueType();

    /* renamed from: or */
    Evaluator mo360or(Evaluator evaluator);

    Stream<Evaluator> stream();

    /* renamed from: eq */
    static <T extends Comparable<T>> Evaluator m362eq(T value) {
        return new Equal(value);
    }

    /* renamed from: ne */
    static <T extends Comparable<T>> Evaluator m367ne(T value) {
        return new NotEqual(value);
    }

    /* renamed from: le */
    static <T extends Comparable<T>> Evaluator m365le(T value) {
        return new LessEqual(value);
    }

    /* renamed from: lt */
    static <T extends Comparable<T>> Evaluator m366lt(T value) {
        return new LessThan(value);
    }

    /* renamed from: ge */
    static <T extends Comparable<T>> Evaluator m363ge(T value) {
        return new GreaterEqual(value);
    }

    /* renamed from: gt */
    static <T extends Comparable<T>> Evaluator m364gt(T value) {
        return new GreaterThan(value);
    }
}
