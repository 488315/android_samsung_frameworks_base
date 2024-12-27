package com.android.systemui.classifier;

import android.view.MotionEvent;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.systemui.classifier.FalsingClassifier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PointerCountClassifier extends FalsingClassifier {
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
        return i3 > i2 ? falsed(1.0d, HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i3, i2, "{pointersObserved=", ", threshold=", "}")) : FalsingClassifier.Result.passed(0.0d);
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
