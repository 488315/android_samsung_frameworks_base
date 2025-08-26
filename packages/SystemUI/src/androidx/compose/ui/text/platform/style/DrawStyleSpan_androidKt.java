package androidx.compose.ui.text.platform.style;

import android.graphics.Paint;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;

/* loaded from: classes.dex */
public abstract class DrawStyleSpan_androidKt {
    /* renamed from: toAndroidCap-BeK7IIE, reason: not valid java name */
    public static final Paint.Cap m791toAndroidCapBeK7IIE(int i) {
        StrokeCap.Companion companion = StrokeCap.Companion;
        companion.getClass();
        if (i == 0) {
            return Paint.Cap.BUTT;
        }
        companion.getClass();
        if (i == StrokeCap.Round) {
            return Paint.Cap.ROUND;
        }
        companion.getClass();
        return i == StrokeCap.Square ? Paint.Cap.SQUARE : Paint.Cap.BUTT;
    }

    /* renamed from: toAndroidJoin-Ww9F2mQ, reason: not valid java name */
    public static final Paint.Join m792toAndroidJoinWw9F2mQ(int i) {
        StrokeJoin.Companion companion = StrokeJoin.Companion;
        companion.getClass();
        if (i == 0) {
            return Paint.Join.MITER;
        }
        companion.getClass();
        if (i == StrokeJoin.Round) {
            return Paint.Join.ROUND;
        }
        companion.getClass();
        return i == StrokeJoin.Bevel ? Paint.Join.BEVEL : Paint.Join.MITER;
    }
}
