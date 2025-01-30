package com.android.systemui.classifier;

import android.view.MotionEvent;
import com.android.systemui.classifier.FalsingClassifier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        return i3 > i2 ? falsed(1.0d, String.format(null, "{pointersObserved=%d, threshold=%d}", Integer.valueOf(i3), Integer.valueOf(i2))) : FalsingClassifier.Result.passed(0.0d);
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
