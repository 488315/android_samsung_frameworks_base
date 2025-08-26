package com.samsung.android.sume.core.evaluate;

import com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda13;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class EvaluableMap<T> implements Evaluator {
    private final Map<Evaluator, T> data;

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator and(Evaluator evaluator) {
        return null;
    }

    @Override // java.lang.Comparable
    public int compareTo(Evaluator evaluator) {
        return 0;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> boolean evaluate(V v) {
        return false;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Class<?> getValueType() {
        return null;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator or(Evaluator evaluator) {
        return null;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Stream<Evaluator> stream() {
        return null;
    }

    public EvaluableMap(Map<Evaluator, T> map) {
        this.data = map;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> V getValue() {
        throw new UnsupportedOperationException("EvaluableMap doesn't support this!!!");
    }

    public <V> T get(final V v) {
        return (T) this.data.keySet().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.evaluate.EvaluableMap$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Evaluator) obj).evaluate(v);
            }
        }).findFirst().map(new Function() { // from class: com.samsung.android.sume.core.evaluate.EvaluableMap$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m9548lambda$get$1$comsamsungandroidsumecoreevaluateEvaluableMap((Evaluator) obj);
            }
        }).orElseThrow(new SurfaceChannelImpl$$ExternalSyntheticLambda13());
    }

    /* renamed from: lambda$get$1$com-samsung-android-sume-core-evaluate-EvaluableMap, reason: not valid java name */
    /* synthetic */ Object m9548lambda$get$1$comsamsungandroidsumecoreevaluateEvaluableMap(Evaluator evaluator) {
        return this.data.get(evaluator);
    }

    public <V> T getOr(final V v, T t) {
        return (T) this.data.keySet().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.evaluate.EvaluableMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Evaluator) obj).evaluate(v);
            }
        }).findFirst().map(new Function() { // from class: com.samsung.android.sume.core.evaluate.EvaluableMap$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m9549xaea5c52c((Evaluator) obj);
            }
        }).orElse(t);
    }

    /* renamed from: lambda$getOr$3$com-samsung-android-sume-core-evaluate-EvaluableMap, reason: not valid java name */
    /* synthetic */ Object m9549xaea5c52c(Evaluator evaluator) {
        return this.data.get(evaluator);
    }
}
