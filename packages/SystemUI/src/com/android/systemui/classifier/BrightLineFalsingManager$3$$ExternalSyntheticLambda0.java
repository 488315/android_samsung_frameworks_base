package com.android.systemui.classifier;

import com.android.systemui.classifier.FalsingClassifier;
import java.util.function.Predicate;

public final /* synthetic */ class BrightLineFalsingManager$3$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((FalsingClassifier.Result) obj).mFalsed;
    }
}
