package com.samsung.android.sume.core.evaluate;

import java.util.function.Predicate;

/* loaded from: classes6.dex */
class AndEvaluatorGroup extends EvaluatorGroup {
    AndEvaluatorGroup(Evaluator... evaluatorArr) {
        super(evaluatorArr);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> boolean evaluate(final V v) {
        return stream().allMatch(new Predicate() { // from class: com.samsung.android.sume.core.evaluate.AndEvaluatorGroup$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Evaluator) obj).evaluate(v);
            }
        });
    }
}
