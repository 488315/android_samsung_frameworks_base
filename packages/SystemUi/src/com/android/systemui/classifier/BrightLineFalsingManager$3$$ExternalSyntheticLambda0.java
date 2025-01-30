package com.android.systemui.classifier;

import com.android.systemui.classifier.FalsingClassifier;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class BrightLineFalsingManager$3$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((FalsingClassifier.Result) obj).mFalsed;
    }
}
