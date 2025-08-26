package androidx.compose.ui.graphics.colorspace;

import androidx.appcompat.animation.SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.colorspace.Connector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class ColorSpaceKt {
    public static ColorSpace adapt$default(ColorSpace colorSpace, WhitePoint whitePoint) {
        Adaptation.Companion.getClass();
        Adaptation$Companion$Bradford$1 adaptation$Companion$Bradford$1 = Adaptation.Bradford;
        ColorModel.Companion.getClass();
        if (ColorModel.m510equalsimpl0(colorSpace.model, ColorModel.Rgb)) {
            Rgb rgb = (Rgb) colorSpace;
            WhitePoint whitePoint2 = rgb.whitePoint;
            if (!compare(whitePoint2, whitePoint)) {
                return new Rgb(rgb, mul3x3(chromaticAdaptation(adaptation$Companion$Bradford$1.transform, whitePoint2.toXyz$ui_graphics_release(), whitePoint.toXyz$ui_graphics_release()), rgb.transform), whitePoint);
            }
        }
        return colorSpace;
    }

    public static final float[] chromaticAdaptation(float[] fArr, float[] fArr2, float[] fArr3) {
        float[] fArrMul3x3Float3 = mul3x3Float3(fArr, fArr2);
        float[] fArrMul3x3Float32 = mul3x3Float3(fArr, fArr3);
        return mul3x3(inverse3x3(fArr), mul3x3Diag(new float[]{fArrMul3x3Float32[0] / fArrMul3x3Float3[0], fArrMul3x3Float32[1] / fArrMul3x3Float3[1], fArrMul3x3Float32[2] / fArrMul3x3Float3[2]}, fArr));
    }

    public static final boolean compare(WhitePoint whitePoint, WhitePoint whitePoint2) {
        if (whitePoint == whitePoint2) {
            return true;
        }
        return Math.abs(whitePoint.x - whitePoint2.x) < 0.001f && Math.abs(whitePoint.y - whitePoint2.y) < 0.001f;
    }

    /* renamed from: createConnector-YBCOT_4, reason: not valid java name */
    public static final Connector m513createConnectorYBCOT_4(ColorSpace colorSpace, ColorSpace colorSpace2) {
        if (colorSpace == colorSpace2) {
            Connector.Companion.getClass();
            RenderIntent.Companion.getClass();
            return new Connector$Companion$identity$1(colorSpace, RenderIntent.Relative);
        }
        ColorModel.Companion.getClass();
        long j = ColorModel.Rgb;
        int i = 0;
        DefaultConstructorMarker defaultConstructorMarker = null;
        return (ColorModel.m510equalsimpl0(colorSpace.model, j) && ColorModel.m510equalsimpl0(colorSpace2.model, j)) ? new Connector.RgbConnector((Rgb) colorSpace, (Rgb) colorSpace2, i, defaultConstructorMarker) : new Connector(colorSpace, colorSpace2, i, defaultConstructorMarker);
    }

    public static final float[] inverse3x3(float[] fArr) {
        float f = fArr[0];
        float f2 = fArr[3];
        float f3 = fArr[6];
        float f4 = fArr[1];
        float f5 = fArr[4];
        float f6 = fArr[7];
        float f7 = fArr[2];
        float f8 = fArr[5];
        float f9 = fArr[8];
        float f10 = (f5 * f9) - (f6 * f8);
        float f11 = (f6 * f7) - (f4 * f9);
        float f12 = (f4 * f8) - (f5 * f7);
        float f13 = (f3 * f12) + (f2 * f11) + (f * f10);
        float[] fArr2 = new float[fArr.length];
        fArr2[0] = f10 / f13;
        fArr2[1] = f11 / f13;
        fArr2[2] = f12 / f13;
        fArr2[3] = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f2, f9, f3 * f8, f13);
        fArr2[4] = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f3, f7, f9 * f, f13);
        fArr2[5] = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f, f8, f7 * f2, f13);
        fArr2[6] = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f3, f5, f2 * f6, f13);
        fArr2[7] = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f, f6, f3 * f4, f13);
        fArr2[8] = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f2, f4, f * f5, f13);
        return fArr2;
    }

    public static final float[] mul3x3(float[] fArr, float[] fArr2) {
        float[] fArr3 = new float[9];
        if (fArr.length < 9 || fArr2.length < 9) {
            return fArr3;
        }
        float f = fArr[0] * fArr2[0];
        float f2 = fArr[3];
        float f3 = fArr2[1];
        float f4 = fArr[6];
        float f5 = fArr2[2];
        fArr3[0] = (f4 * f5) + (f2 * f3) + f;
        float f6 = fArr[1];
        float f7 = fArr2[0];
        float f8 = fArr[4];
        float f9 = fArr[7];
        float f10 = f9 * f5;
        fArr3[1] = f10 + (f3 * f8) + (f6 * f7);
        float f11 = fArr[2] * f7;
        float f12 = fArr[5];
        float f13 = (fArr2[1] * f12) + f11;
        float f14 = fArr[8];
        fArr3[2] = (f5 * f14) + f13;
        float f15 = fArr[0];
        float f16 = fArr2[3] * f15;
        float f17 = fArr2[4];
        float f18 = (f2 * f17) + f16;
        float f19 = fArr2[5];
        fArr3[3] = (f4 * f19) + f18;
        float f20 = fArr[1];
        float f21 = fArr2[3];
        float f22 = f8 * f17;
        fArr3[4] = (f9 * f19) + f22 + (f20 * f21);
        float f23 = fArr[2];
        float f24 = f19 * f14;
        fArr3[5] = f24 + (f12 * fArr2[4]) + (f21 * f23);
        float f25 = f15 * fArr2[6];
        float f26 = fArr[3];
        float f27 = fArr2[7];
        float f28 = (f26 * f27) + f25;
        float f29 = fArr2[8];
        fArr3[6] = (f4 * f29) + f28;
        float f30 = fArr2[6];
        float f31 = f9 * f29;
        fArr3[7] = f31 + (fArr[4] * f27) + (f20 * f30);
        float f32 = f14 * f29;
        fArr3[8] = f32 + (fArr[5] * fArr2[7]) + (f23 * f30);
        return fArr3;
    }

    public static final float[] mul3x3Diag(float[] fArr, float[] fArr2) {
        float f = fArr[0];
        float f2 = fArr2[0] * f;
        float f3 = fArr[1];
        float f4 = fArr2[1] * f3;
        float f5 = fArr[2];
        return new float[]{f2, f4, fArr2[2] * f5, fArr2[3] * f, fArr2[4] * f3, fArr2[5] * f5, f * fArr2[6], f3 * fArr2[7], f5 * fArr2[8]};
    }

    public static final float[] mul3x3Float3(float[] fArr, float[] fArr2) {
        if (fArr.length < 9 || fArr2.length < 3) {
            return fArr2;
        }
        float f = fArr2[0];
        float f2 = fArr2[1];
        float f3 = fArr2[2];
        fArr2[0] = (fArr[6] * f3) + (fArr[3] * f2) + (fArr[0] * f);
        fArr2[1] = (fArr[7] * f3) + (fArr[4] * f2) + (fArr[1] * f);
        fArr2[2] = (fArr[8] * f3) + (fArr[5] * f2) + (fArr[2] * f);
        return fArr2;
    }
}
