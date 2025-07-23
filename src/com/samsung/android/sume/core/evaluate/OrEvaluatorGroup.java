package com.samsung.android.sume.core.evaluate;

import java.util.function.Predicate;

/* loaded from: classes6.dex */
class OrEvaluatorGroup extends EvaluatorGroup {
    OrEvaluatorGroup(Evaluator... evaluatorArr) {
        super(evaluatorArr);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> boolean evaluate(final V v) {
        return stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.evaluate.OrEvaluatorGroup$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean evaluate;
                evaluate = ((Evaluator) obj).evaluate(v);
                return evaluate;
            }
        });
    }
}
