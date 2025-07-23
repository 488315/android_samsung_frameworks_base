package com.android.systemui.classifier;

import android.view.MotionEvent;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.systemui.classifier.FalsingClassifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PointerCountClassifier extends FalsingClassifier {
    public int mMaxPointerCount;

    public PointerCountClassifier(FalsingDataProvider falsingDataProvider) {
        super(falsingDataProvider);
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final FalsingClassifier.Result calculateFalsingResult(int i) {
        int i2 = 2;
        if (i != 0 && i != 2) {
            i2 = 1;
        }
        int i3 = this.mMaxPointerCount;
        return i3 > i2 ? falsed(1.0d, MutableVectorKt$$ExternalSyntheticOutline0.m(i3, i2, "{pointersObserved=", ", threshold=", "}")) : FalsingClassifier.Result.passed(0.0d);
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final void onTouchEvent(MotionEvent motionEvent) {
        int i = this.mMaxPointerCount;
        if (motionEvent.getActionMasked() == 0) {
            this.mMaxPointerCount = motionEvent.getPointerCount();
        } else {
            this.mMaxPointerCount = Math.max(this.mMaxPointerCount, motionEvent.getPointerCount());
        }
        if (i != this.mMaxPointerCount) {
            boolean z = BrightLineFalsingManager.DEBUG;
        }
    }
}
