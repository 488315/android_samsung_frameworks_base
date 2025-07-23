package androidx.compose.ui.graphics;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.colorspace.ColorModel;
import androidx.compose.ui.graphics.colorspace.ColorSpace;
import androidx.compose.ui.graphics.colorspace.ColorSpaces;
import androidx.compose.ui.graphics.colorspace.Oklab;
import androidx.compose.ui.graphics.colorspace.Rgb;
import androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda0;
import androidx.compose.ui.util.MathHelpersKt;
import kotlin.ULong;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ColorKt {
    /* JADX WARN: Removed duplicated region for block: B:101:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0172  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final long Color(float r20, float r21, float r22, float r23, androidx.compose.ui.graphics.colorspace.ColorSpace r24) {
        /*
            Method dump skipped, instructions count: 476
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.ColorKt.Color(float, float, float, float, androidx.compose.ui.graphics.colorspace.ColorSpace):long");
    }

    public static long Color$default(float f, float f2, float f3, int i) {
        float f4 = (i & 8) != 0 ? 1.0f : 0.2f;
        ColorSpaces.INSTANCE.getClass();
        return Color(f, f2, f3, f4, ColorSpaces.Srgb);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final long UncheckedColor(float r18, float r19, float r20, float r21, androidx.compose.ui.graphics.colorspace.ColorSpace r22) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.ColorKt.UncheckedColor(float, float, float, float, androidx.compose.ui.graphics.colorspace.ColorSpace):long");
    }

    /* renamed from: compositeOver--OWjLjI, reason: not valid java name */
    public static final long m464compositeOverOWjLjI(long j, long j2) {
        float f;
        float f2;
        long m455convertvNxB06k = Color.m455convertvNxB06k(j, Color.m459getColorSpaceimpl(j2));
        float m457getAlphaimpl = Color.m457getAlphaimpl(j2);
        float m457getAlphaimpl2 = Color.m457getAlphaimpl(m455convertvNxB06k);
        float f3 = 1.0f - m457getAlphaimpl2;
        float f4 = (m457getAlphaimpl * f3) + m457getAlphaimpl2;
        float m461getRedimpl = Color.m461getRedimpl(m455convertvNxB06k);
        float m461getRedimpl2 = Color.m461getRedimpl(j2);
        float f5 = 0.0f;
        if (f4 == 0.0f) {
            f = 0.0f;
        } else {
            f = (((m461getRedimpl2 * m457getAlphaimpl) * f3) + (m461getRedimpl * m457getAlphaimpl2)) / f4;
        }
        float m460getGreenimpl = Color.m460getGreenimpl(m455convertvNxB06k);
        float m460getGreenimpl2 = Color.m460getGreenimpl(j2);
        if (f4 == 0.0f) {
            f2 = 0.0f;
        } else {
            f2 = (((m460getGreenimpl2 * m457getAlphaimpl) * f3) + (m460getGreenimpl * m457getAlphaimpl2)) / f4;
        }
        float m458getBlueimpl = Color.m458getBlueimpl(m455convertvNxB06k);
        float m458getBlueimpl2 = Color.m458getBlueimpl(j2);
        if (f4 != 0.0f) {
            f5 = (((m458getBlueimpl2 * m457getAlphaimpl) * f3) + (m458getBlueimpl * m457getAlphaimpl2)) / f4;
        }
        return UncheckedColor(f, f2, f5, f4, Color.m459getColorSpaceimpl(j2));
    }

    /* renamed from: lerp-jxsXWHM, reason: not valid java name */
    public static final long m465lerpjxsXWHM(long j, long j2, float f) {
        ColorSpaces.INSTANCE.getClass();
        Oklab oklab = ColorSpaces.Oklab;
        long m455convertvNxB06k = Color.m455convertvNxB06k(j, oklab);
        long m455convertvNxB06k2 = Color.m455convertvNxB06k(j2, oklab);
        float m457getAlphaimpl = Color.m457getAlphaimpl(m455convertvNxB06k);
        float m461getRedimpl = Color.m461getRedimpl(m455convertvNxB06k);
        float m460getGreenimpl = Color.m460getGreenimpl(m455convertvNxB06k);
        float m458getBlueimpl = Color.m458getBlueimpl(m455convertvNxB06k);
        float m457getAlphaimpl2 = Color.m457getAlphaimpl(m455convertvNxB06k2);
        float m461getRedimpl2 = Color.m461getRedimpl(m455convertvNxB06k2);
        float m460getGreenimpl2 = Color.m460getGreenimpl(m455convertvNxB06k2);
        float m458getBlueimpl2 = Color.m458getBlueimpl(m455convertvNxB06k2);
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        return Color.m455convertvNxB06k(UncheckedColor(MathHelpersKt.lerp(m461getRedimpl, m461getRedimpl2, f), MathHelpersKt.lerp(m460getGreenimpl, m460getGreenimpl2, f), MathHelpersKt.lerp(m458getBlueimpl, m458getBlueimpl2, f), MathHelpersKt.lerp(m457getAlphaimpl, m457getAlphaimpl2, f), oklab), Color.m459getColorSpaceimpl(j2));
    }

    /* renamed from: luminance-8_81llA, reason: not valid java name */
    public static final float m466luminance8_81llA(long j) {
        ColorSpace m459getColorSpaceimpl = Color.m459getColorSpaceimpl(j);
        long j2 = m459getColorSpaceimpl.model;
        ColorModel.Companion.getClass();
        if (!ColorModel.m508equalsimpl0(j2, ColorModel.Rgb)) {
            InlineClassHelperKt.throwIllegalArgumentException("The specified color must be encoded in an RGB color space. The supplied color space is " + ((Object) ColorModel.m509toStringimpl(m459getColorSpaceimpl.model)));
        }
        double m461getRedimpl = Color.m461getRedimpl(j);
        Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = ((Rgb) m459getColorSpaceimpl).eotfFunc;
        double invoke = rgb$$ExternalSyntheticLambda0.invoke(m461getRedimpl);
        float invoke2 = (float) ((rgb$$ExternalSyntheticLambda0.invoke(Color.m458getBlueimpl(j)) * 0.0722d) + (rgb$$ExternalSyntheticLambda0.invoke(Color.m460getGreenimpl(j)) * 0.7152d) + (invoke * 0.2126d));
        if (invoke2 < 0.0f) {
            invoke2 = 0.0f;
        }
        if (invoke2 > 1.0f) {
            return 1.0f;
        }
        return invoke2;
    }

    /* renamed from: toArgb-8_81llA, reason: not valid java name */
    public static final int m467toArgb8_81llA(long j) {
        ColorSpaces.INSTANCE.getClass();
        long m455convertvNxB06k = Color.m455convertvNxB06k(j, ColorSpaces.Srgb) >>> 32;
        int i = ULong.$r8$clinit;
        return (int) m455convertvNxB06k;
    }

    public static long Color$default(int i, int i2, int i3) {
        return Color(((i & 255) << 16) | (-16777216) | ((i2 & 255) << 8) | (i3 & 255));
    }

    public static final long Color(int i) {
        long j = i;
        int i2 = ULong.$r8$clinit;
        long j2 = j << 32;
        Color.Companion companion = Color.Companion;
        return j2;
    }

    public static final long Color(long j) {
        long j2 = j << 32;
        int i = ULong.$r8$clinit;
        Color.Companion companion = Color.Companion;
        return j2;
    }
}
