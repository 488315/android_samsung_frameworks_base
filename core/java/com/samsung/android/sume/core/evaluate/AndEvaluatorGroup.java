package com.samsung.android.sume.core.evaluate;

import java.util.function.Predicate;

/* loaded from: classes4.dex */
class AndEvaluatorGroup extends EvaluatorGroup {
  AndEvaluatorGroup(Evaluator... evaluators) {
    super(evaluators);
  }

  @Override // com.samsung.android.sume.core.evaluate.Evaluator
  public <V> boolean evaluate(final V value) {
    return stream()
        .allMatch(
            new Predicate() { // from class:
                              // com.samsung.android.sume.core.evaluate.AndEvaluatorGroup$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean evaluate;
                evaluate = ((Evaluator) obj).evaluate(value);
                return evaluate;
              }
            });
  }
}
