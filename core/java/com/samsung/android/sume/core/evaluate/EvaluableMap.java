package com.samsung.android.sume.core.evaluate;

import com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda5;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class EvaluableMap<T> implements Evaluator {
  private final Map<Evaluator, T> data;

  public EvaluableMap(Map<Evaluator, T> data) {
    this.data = data;
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public <V> boolean evaluate(V value) {
    return false;
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public Evaluator and(Evaluator other) {
    return null;
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  /* renamed from: or */
  public Evaluator mo360or(Evaluator other) {
    return null;
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public Stream<Evaluator> stream() {
    return null;
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public Class<?> getValueType() {
    return null;
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public <V> V getValue() {
    throw new UnsupportedOperationException("EvaluableMap doesn't support this!!!");
  }

  @Override // java.lang.Comparable
  public int compareTo(Evaluator o) {
    return 0;
  }

  public <V> T get(final V v) {
    return (T)
        this.data.keySet().stream()
            .filter(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.evaluate.EvaluableMap$$ExternalSyntheticLambda0
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean evaluate;
                    evaluate = ((Evaluator) obj).evaluate(v);
                    return evaluate;
                  }
                })
            .findFirst()
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.evaluate.EvaluableMap$$ExternalSyntheticLambda1
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return EvaluableMap.this
                        .m9206lambda$get$1$comsamsungandroidsumecoreevaluateEvaluableMap(
                            (Evaluator) obj);
                  }
                })
            .orElseThrow(new SurfaceChannelImpl$$ExternalSyntheticLambda5());
  }

  /* renamed from: lambda$get$1$com-samsung-android-sume-core-evaluate-EvaluableMap, reason: not valid java name */
  /* synthetic */ Object m9206lambda$get$1$comsamsungandroidsumecoreevaluateEvaluableMap(
      Evaluator e) {
    return this.data.get(e);
  }

  public <V> T getOr(final V v, T t) {
    return (T)
        this.data.keySet().stream()
            .filter(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.evaluate.EvaluableMap$$ExternalSyntheticLambda2
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean evaluate;
                    evaluate = ((Evaluator) obj).evaluate(v);
                    return evaluate;
                  }
                })
            .findFirst()
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.evaluate.EvaluableMap$$ExternalSyntheticLambda3
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return EvaluableMap.this.m361xaea5c52c((Evaluator) obj);
                  }
                })
            .orElse(t);
  }

  /* renamed from: lambda$getOr$3$com-samsung-android-sume-core-evaluate-EvaluableMap */
  /* synthetic */ Object m361xaea5c52c(Evaluator e) {
    return this.data.get(e);
  }
}
