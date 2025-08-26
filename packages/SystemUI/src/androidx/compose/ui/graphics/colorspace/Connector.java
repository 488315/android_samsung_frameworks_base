package androidx.compose.ui.graphics.colorspace;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public class Connector {
    public static final Companion Companion = new Companion(null);
    public final ColorSpace destination;
    public final float[] transform;
    public final ColorSpace transformDestination;
    public final ColorSpace transformSource;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class RgbConnector extends Connector {
        public final Rgb mDestination;
        public final Rgb mSource;
        public final float[] mTransform;

        public /* synthetic */ RgbConnector(Rgb rgb, Rgb rgb2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(rgb, rgb2, i);
        }

        @Override // androidx.compose.ui.graphics.colorspace.Connector
        /* renamed from: transformToColor-l2rxGTc$ui_graphics_release */
        public final long mo514transformToColorl2rxGTc$ui_graphics_release(long j) {
            float fM463getRedimpl = Color.m463getRedimpl(j);
            float fM462getGreenimpl = Color.m462getGreenimpl(j);
            float fM460getBlueimpl = Color.m460getBlueimpl(j);
            float fM459getAlphaimpl = Color.m459getAlphaimpl(j);
            Rgb rgb = this.mSource;
            float fInvoke = (float) rgb.eotfFunc.invoke(fM463getRedimpl);
            Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = rgb.eotfFunc;
            float fInvoke2 = (float) rgb$$ExternalSyntheticLambda0.invoke(fM462getGreenimpl);
            float fInvoke3 = (float) rgb$$ExternalSyntheticLambda0.invoke(fM460getBlueimpl);
            float[] fArr = this.mTransform;
            float f = (fArr[6] * fInvoke3) + (fArr[3] * fInvoke2) + (fArr[0] * fInvoke);
            float f2 = (fArr[7] * fInvoke3) + (fArr[4] * fInvoke2) + (fArr[1] * fInvoke);
            float f3 = (fArr[8] * fInvoke3) + (fArr[5] * fInvoke2) + (fArr[2] * fInvoke);
            Rgb rgb2 = this.mDestination;
            float fInvoke4 = (float) rgb2.oetfFunc.invoke(f);
            Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda02 = rgb2.oetfFunc;
            return ColorKt.Color(fInvoke4, (float) rgb$$ExternalSyntheticLambda02.invoke(f2), (float) rgb$$ExternalSyntheticLambda02.invoke(f3), fM459getAlphaimpl, rgb2);
        }

        private RgbConnector(Rgb rgb, Rgb rgb2, int i) {
            float[] fArrMul3x3;
            super(rgb, rgb2, rgb, rgb2, i, null, null);
            this.mSource = rgb;
            this.mDestination = rgb2;
            boolean zCompare = ColorSpaceKt.compare(rgb.whitePoint, rgb2.whitePoint);
            float[] fArrMul3x32 = rgb.transform;
            float[] fArrInverse3x3 = rgb2.inverseTransform;
            if (zCompare) {
                fArrMul3x3 = ColorSpaceKt.mul3x3(fArrInverse3x3, fArrMul3x32);
            } else {
                WhitePoint whitePoint = rgb.whitePoint;
                float[] xyz$ui_graphics_release = whitePoint.toXyz$ui_graphics_release();
                WhitePoint whitePoint2 = rgb2.whitePoint;
                float[] xyz$ui_graphics_release2 = whitePoint2.toXyz$ui_graphics_release();
                Illuminant.INSTANCE.getClass();
                WhitePoint whitePoint3 = Illuminant.D50;
                if (!ColorSpaceKt.compare(whitePoint, whitePoint3)) {
                    Adaptation.Companion.getClass();
                    float[] fArr = Adaptation.Bradford.transform;
                    float[] fArr2 = Illuminant.D50Xyz;
                    fArrMul3x32 = ColorSpaceKt.mul3x3(ColorSpaceKt.chromaticAdaptation(fArr, xyz$ui_graphics_release, Arrays.copyOf(fArr2, fArr2.length)), fArrMul3x32);
                }
                if (!ColorSpaceKt.compare(whitePoint2, whitePoint3)) {
                    Adaptation.Companion.getClass();
                    float[] fArr3 = Adaptation.Bradford.transform;
                    float[] fArr4 = Illuminant.D50Xyz;
                    fArrInverse3x3 = ColorSpaceKt.inverse3x3(ColorSpaceKt.mul3x3(ColorSpaceKt.chromaticAdaptation(fArr3, xyz$ui_graphics_release2, Arrays.copyOf(fArr4, fArr4.length)), rgb2.transform));
                }
                RenderIntent.Companion.getClass();
                fArrMul3x3 = ColorSpaceKt.mul3x3(fArrInverse3x3, i == RenderIntent.Absolute ? ColorSpaceKt.mul3x3Diag(new float[]{xyz$ui_graphics_release[0] / xyz$ui_graphics_release2[0], xyz$ui_graphics_release[1] / xyz$ui_graphics_release2[1], xyz$ui_graphics_release[2] / xyz$ui_graphics_release2[2]}, fArrMul3x32) : fArrMul3x32);
            }
            this.mTransform = fArrMul3x3;
        }
    }

    public /* synthetic */ Connector(ColorSpace colorSpace, ColorSpace colorSpace2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(colorSpace, colorSpace2, i);
    }

    /* renamed from: transformToColor-l2rxGTc$ui_graphics_release, reason: not valid java name */
    public long mo514transformToColorl2rxGTc$ui_graphics_release(long j) {
        float fM463getRedimpl = Color.m463getRedimpl(j);
        float fM462getGreenimpl = Color.m462getGreenimpl(j);
        float fM460getBlueimpl = Color.m460getBlueimpl(j);
        float fM459getAlphaimpl = Color.m459getAlphaimpl(j);
        ColorSpace colorSpace = this.transformSource;
        long xy$ui_graphics_release = colorSpace.toXy$ui_graphics_release(fM463getRedimpl, fM462getGreenimpl, fM460getBlueimpl);
        float fIntBitsToFloat = Float.intBitsToFloat((int) (xy$ui_graphics_release >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (xy$ui_graphics_release & 4294967295L));
        float z$ui_graphics_release = colorSpace.toZ$ui_graphics_release(fM463getRedimpl, fM462getGreenimpl, fM460getBlueimpl);
        float[] fArr = this.transform;
        if (fArr != null) {
            fIntBitsToFloat *= fArr[0];
            fIntBitsToFloat2 *= fArr[1];
            z$ui_graphics_release *= fArr[2];
        }
        float f = fIntBitsToFloat;
        float f2 = fIntBitsToFloat2;
        return this.transformDestination.mo512xyzaToColorJlNiLsg$ui_graphics_release(f, f2, z$ui_graphics_release, fM459getAlphaimpl, this.destination);
    }

    public /* synthetic */ Connector(ColorSpace colorSpace, ColorSpace colorSpace2, ColorSpace colorSpace3, ColorSpace colorSpace4, int i, float[] fArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(colorSpace, colorSpace2, colorSpace3, colorSpace4, i, fArr);
    }

    private Connector(ColorSpace colorSpace, ColorSpace colorSpace2, ColorSpace colorSpace3, ColorSpace colorSpace4, int i, float[] fArr) {
        this.destination = colorSpace2;
        this.transformSource = colorSpace3;
        this.transformDestination = colorSpace4;
        this.transform = fArr;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0097  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Connector(ColorSpace colorSpace, ColorSpace colorSpace2, int i) {
        ColorSpace colorSpaceAdapt$default;
        ColorSpace colorSpaceAdapt$default2;
        float[] fArr;
        float[] xyz$ui_graphics_release;
        float[] xyz$ui_graphics_release2;
        long j = colorSpace.model;
        ColorModel.Companion.getClass();
        long j2 = ColorModel.Rgb;
        if (ColorModel.m510equalsimpl0(j, j2)) {
            Illuminant.INSTANCE.getClass();
            colorSpaceAdapt$default = ColorSpaceKt.adapt$default(colorSpace, Illuminant.D50);
        } else {
            colorSpaceAdapt$default = colorSpace;
        }
        if (ColorModel.m510equalsimpl0(colorSpace2.model, j2)) {
            Illuminant.INSTANCE.getClass();
            colorSpaceAdapt$default2 = ColorSpaceKt.adapt$default(colorSpace2, Illuminant.D50);
        } else {
            colorSpaceAdapt$default2 = colorSpace2;
        }
        Companion.getClass();
        RenderIntent.Companion.getClass();
        if (i == RenderIntent.Absolute) {
            boolean zM510equalsimpl0 = ColorModel.m510equalsimpl0(colorSpace.model, j2);
            boolean zM510equalsimpl02 = ColorModel.m510equalsimpl0(colorSpace2.model, j2);
            if (!(zM510equalsimpl0 && zM510equalsimpl02) && (zM510equalsimpl0 || zM510equalsimpl02)) {
                WhitePoint whitePoint = ((Rgb) (zM510equalsimpl0 ? colorSpace : colorSpace2)).whitePoint;
                if (zM510equalsimpl0) {
                    xyz$ui_graphics_release = whitePoint.toXyz$ui_graphics_release();
                } else {
                    Illuminant.INSTANCE.getClass();
                    xyz$ui_graphics_release = Illuminant.D50Xyz;
                }
                if (zM510equalsimpl02) {
                    xyz$ui_graphics_release2 = whitePoint.toXyz$ui_graphics_release();
                } else {
                    Illuminant.INSTANCE.getClass();
                    xyz$ui_graphics_release2 = Illuminant.D50Xyz;
                }
                fArr = new float[]{xyz$ui_graphics_release[0] / xyz$ui_graphics_release2[0], xyz$ui_graphics_release[1] / xyz$ui_graphics_release2[1], xyz$ui_graphics_release[2] / xyz$ui_graphics_release2[2]};
            } else {
                fArr = null;
            }
        }
        this(colorSpace, colorSpace2, colorSpaceAdapt$default, colorSpaceAdapt$default2, i, fArr, null);
    }
}
