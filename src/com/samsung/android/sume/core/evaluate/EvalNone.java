package com.samsung.android.sume.core.evaluate;

import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class EvalNone implements Evaluator {
    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator and(Evaluator evaluator) {
        return evaluator;
    }

    @Override // java.lang.Comparable
    public int compareTo(Evaluator evaluator) {
        return 0;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> boolean evaluate(V v) {
        return true;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Class<?> getValueType() {
        return null;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator or(Evaluator evaluator) {
        return this;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> V getValue() {
        throw new UnsupportedOperationException("EvalNone doesn't support this!!!");
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Stream<Evaluator> stream() {
        return Stream.of(this);
    }
}
