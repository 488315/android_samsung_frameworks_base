package androidx.compose.ui.spatial;

import androidx.compose.ui.unit.IntOffset;

/* loaded from: classes.dex */
public abstract class RectManagerKt {
    /* renamed from: access$analyzeComponents-58bKbWc, reason: not valid java name */
    public static final int m723access$analyzeComponents58bKbWc(float[] fArr) {
        int i = 0;
        if (fArr.length < 16) {
            return 0;
        }
        int i2 = (fArr[0] == 1.0f && fArr[1] == 0.0f && fArr[2] == 0.0f && fArr[4] == 0.0f && fArr[5] == 1.0f && fArr[6] == 0.0f && fArr[8] == 0.0f && fArr[9] == 0.0f && fArr[10] == 1.0f) ? 1 : 0;
        if (fArr[12] == 0.0f && fArr[13] == 0.0f && fArr[14] == 0.0f && fArr[15] == 1.0f) {
            i = 1;
        }
        return (i2 << 1) | i;
    }

    /* renamed from: access$isSet--gyyYBs, reason: not valid java name */
    public static final boolean m724access$isSetgyyYBs(long j) {
        IntOffset.Companion.getClass();
        return !IntOffset.m851equalsimpl0(j, IntOffset.Max);
    }
}
