package androidx.compose.ui.graphics.colorspace;

import androidx.compose.ui.graphics.ColorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Lab extends ColorSpace {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Lab(String str, int i) {
        super(str, ColorModel.Lab, i, null);
        ColorModel.Companion.getClass();
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float[] fromXyz(float[] fArr) {
        float f = fArr[0];
        Illuminant.INSTANCE.getClass();
        float[] fArr2 = Illuminant.D50Xyz;
        float f2 = f / fArr2[0];
        float f3 = fArr[1] / fArr2[1];
        float f4 = fArr[2] / fArr2[2];
        float cbrt = f2 > 0.008856452f ? (float) Math.cbrt(f2) : (f2 * 7.787037f) + 0.13793103f;
        float cbrt2 = f3 > 0.008856452f ? (float) Math.cbrt(f3) : (f3 * 7.787037f) + 0.13793103f;
        float cbrt3 = f4 > 0.008856452f ? (float) Math.cbrt(f4) : (f4 * 7.787037f) + 0.13793103f;
        float f5 = (116.0f * cbrt2) - 16.0f;
        float f6 = (cbrt - cbrt2) * 500.0f;
        float f7 = (cbrt2 - cbrt3) * 200.0f;
        if (f5 < 0.0f) {
            f5 = 0.0f;
        }
        if (f5 > 100.0f) {
            f5 = 100.0f;
        }
        fArr[0] = f5;
        if (f6 < -128.0f) {
            f6 = -128.0f;
        }
        if (f6 > 128.0f) {
            f6 = 128.0f;
        }
        fArr[1] = f6;
        if (f7 < -128.0f) {
            f7 = -128.0f;
        }
        fArr[2] = f7 <= 128.0f ? f7 : 128.0f;
        return fArr;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float getMaxValue(int i) {
        return i == 0 ? 100.0f : 128.0f;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float getMinValue(int i) {
        return i == 0 ? 0.0f : -128.0f;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final long toXy$ui_graphics_release(float f, float f2, float f3) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 100.0f) {
            f = 100.0f;
        }
        if (f2 < -128.0f) {
            f2 = -128.0f;
        }
        if (f2 > 128.0f) {
            f2 = 128.0f;
        }
        float f4 = (f + 16.0f) / 116.0f;
        float f5 = (f2 * 0.002f) + f4;
        float f6 = f5 > 0.20689656f ? f5 * f5 * f5 : (f5 - 0.13793103f) * 0.12841855f;
        float f7 = f4 > 0.20689656f ? f4 * f4 * f4 : (f4 - 0.13793103f) * 0.12841855f;
        Illuminant illuminant = Illuminant.INSTANCE;
        illuminant.getClass();
        float f8 = f6 * Illuminant.D50Xyz[0];
        illuminant.getClass();
        return (Float.floatToRawIntBits(f8) << 32) | (4294967295L & Float.floatToRawIntBits(f7 * r4[1]));
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float[] toXyz(float[] fArr) {
        float f = fArr[0];
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 100.0f) {
            f = 100.0f;
        }
        fArr[0] = f;
        float f2 = fArr[1];
        if (f2 < -128.0f) {
            f2 = -128.0f;
        }
        if (f2 > 128.0f) {
            f2 = 128.0f;
        }
        fArr[1] = f2;
        float f3 = fArr[2];
        float f4 = f3 >= -128.0f ? f3 : -128.0f;
        float f5 = f4 <= 128.0f ? f4 : 128.0f;
        fArr[2] = f5;
        float f6 = (f + 16.0f) / 116.0f;
        float f7 = (f2 * 0.002f) + f6;
        float f8 = f6 - (f5 * 0.005f);
        float f9 = f7 > 0.20689656f ? f7 * f7 * f7 : (f7 - 0.13793103f) * 0.12841855f;
        float f10 = f6 > 0.20689656f ? f6 * f6 * f6 : (f6 - 0.13793103f) * 0.12841855f;
        float f11 = f8 > 0.20689656f ? f8 * f8 * f8 : (f8 - 0.13793103f) * 0.12841855f;
        Illuminant.INSTANCE.getClass();
        float[] fArr2 = Illuminant.D50Xyz;
        fArr[0] = f9 * fArr2[0];
        fArr[1] = f10 * fArr2[1];
        fArr[2] = f11 * fArr2[2];
        return fArr;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float toZ$ui_graphics_release(float f, float f2, float f3) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 100.0f) {
            f = 100.0f;
        }
        if (f3 < -128.0f) {
            f3 = -128.0f;
        }
        if (f3 > 128.0f) {
            f3 = 128.0f;
        }
        float f4 = ((f + 16.0f) / 116.0f) - (f3 * 0.005f);
        float f5 = f4 > 0.20689656f ? f4 * f4 * f4 : 0.12841855f * (f4 - 0.13793103f);
        Illuminant.INSTANCE.getClass();
        return f5 * Illuminant.D50Xyz[2];
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    /* renamed from: xyzaToColor-JlNiLsg$ui_graphics_release */
    public final long mo510xyzaToColorJlNiLsg$ui_graphics_release(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        Illuminant illuminant = Illuminant.INSTANCE;
        illuminant.getClass();
        float[] fArr = Illuminant.D50Xyz;
        float f5 = f / fArr[0];
        illuminant.getClass();
        float f6 = f2 / fArr[1];
        illuminant.getClass();
        float f7 = f3 / fArr[2];
        float cbrt = f5 > 0.008856452f ? (float) Math.cbrt(f5) : (f5 * 7.787037f) + 0.13793103f;
        float cbrt2 = f6 > 0.008856452f ? (float) Math.cbrt(f6) : (f6 * 7.787037f) + 0.13793103f;
        float cbrt3 = f7 > 0.008856452f ? (float) Math.cbrt(f7) : (f7 * 7.787037f) + 0.13793103f;
        float f8 = (116.0f * cbrt2) - 16.0f;
        float f9 = (cbrt - cbrt2) * 500.0f;
        float f10 = (cbrt2 - cbrt3) * 200.0f;
        if (f8 < 0.0f) {
            f8 = 0.0f;
        }
        if (f8 > 100.0f) {
            f8 = 100.0f;
        }
        if (f9 < -128.0f) {
            f9 = -128.0f;
        }
        if (f9 > 128.0f) {
            f9 = 128.0f;
        }
        if (f10 < -128.0f) {
            f10 = -128.0f;
        }
        return ColorKt.Color(f8, f9, f10 <= 128.0f ? f10 : 128.0f, f4, colorSpace);
    }
}
