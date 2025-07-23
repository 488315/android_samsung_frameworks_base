package androidx.compose.ui.spatial;

import androidx.compose.ui.unit.IntOffset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RectManagerKt {
    /* renamed from: access$analyzeComponents-58bKbWc, reason: not valid java name */
    public static final int m721access$analyzeComponents58bKbWc(float[] fArr) {
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
    public static final boolean m722access$isSetgyyYBs(long j) {
        IntOffset.Companion.getClass();
        return !IntOffset.m849equalsimpl0(j, IntOffset.Max);
    }
}
