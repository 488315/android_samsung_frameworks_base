package androidx.compose.ui.platform;

import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;

/* loaded from: classes.dex */
public interface ViewConfiguration {
    long getDoubleTapTimeoutMillis();

    default float getHandwritingGestureLineMargin() {
        return 16.0f;
    }

    default float getHandwritingSlop() {
        return 2.0f;
    }

    long getLongPressTimeoutMillis();

    default float getMaximumFlingVelocity() {
        return Float.MAX_VALUE;
    }

    /* renamed from: getMinimumTouchTargetSize-MYxV2XQ */
    default long mo645getMinimumTouchTargetSizeMYxV2XQ() {
        float f = 48;
        Dp.Companion companion = Dp.Companion;
        return DpKt.m840DpSizeYgX7TsA(f, f);
    }

    float getTouchSlop();
}
