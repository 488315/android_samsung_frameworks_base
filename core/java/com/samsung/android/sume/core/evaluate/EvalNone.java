package com.samsung.android.sume.core.evaluate;

import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class EvalNone implements Evaluator {
  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public <V> V getValue() {
    throw new UnsupportedOperationException("EvalNone doesn't support this!!!");
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public <V> boolean evaluate(V value) {
    return true;
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public Evaluator and(Evaluator other) {
    return other;
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  /* renamed from: or */
  public Evaluator mo360or(Evaluator other) {
    return this;
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public Stream<Evaluator> stream() {
    return Stream.of(this);
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public Class<?> getValueType() {
    return null;
  }

  @Override // java.lang.Comparable
  public int compareTo(Evaluator o) {
    return 0;
  }
}
