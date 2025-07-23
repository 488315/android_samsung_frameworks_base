package androidx.compose.ui.platform;

import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    default long mo643getMinimumTouchTargetSizeMYxV2XQ() {
        float f = 48;
        Dp.Companion companion = Dp.Companion;
        return DpKt.m838DpSizeYgX7TsA(f, f);
    }

    float getTouchSlop();
}
