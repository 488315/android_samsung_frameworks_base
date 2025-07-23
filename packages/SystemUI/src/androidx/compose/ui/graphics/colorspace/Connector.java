package androidx.compose.ui.graphics.colorspace;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Connector {
    public static final Companion Companion = new Companion(null);
    public final ColorSpace destination;
    public final float[] transform;
    public final ColorSpace transformDestination;
    public final ColorSpace transformSource;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RgbConnector extends Connector {
        public final Rgb mDestination;
        public final Rgb mSource;
        public final float[] mTransform;

        public /* synthetic */ RgbConnector(Rgb rgb, Rgb rgb2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(rgb, rgb2, i);
        }

        @Override // androidx.compose.ui.graphics.colorspace.Connector
        /* renamed from: transformToColor-l2rxGTc$ui_graphics_release */
        public final long mo512transformToColorl2rxGTc$ui_graphics_release(long j) {
            float m461getRedimpl = Color.m461getRedimpl(j);
            float m460getGreenimpl = Color.m460getGreenimpl(j);
            float m458getBlueimpl = Color.m458getBlueimpl(j);
            float m457getAlphaimpl = Color.m457getAlphaimpl(j);
            Rgb rgb = this.mSource;
            float invoke = (float) rgb.eotfFunc.invoke(m461getRedimpl);
            Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = rgb.eotfFunc;
            float invoke2 = (float) rgb$$ExternalSyntheticLambda0.invoke(m460getGreenimpl);
            float invoke3 = (float) rgb$$ExternalSyntheticLambda0.invoke(m458getBlueimpl);
            float[] fArr = this.mTransform;
            float f = (fArr[6] * invoke3) + (fArr[3] * invoke2) + (fArr[0] * invoke);
            float f2 = (fArr[7] * invoke3) + (fArr[4] * invoke2) + (fArr[1] * invoke);
            float f3 = (fArr[8] * invoke3) + (fArr[5] * invoke2) + (fArr[2] * invoke);
            Rgb rgb2 = this.mDestination;
            float invoke4 = (float) rgb2.oetfFunc.invoke(f);
            Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda02 = rgb2.oetfFunc;
            return ColorKt.Color(invoke4, (float) rgb$$ExternalSyntheticLambda02.invoke(f2), (float) rgb$$ExternalSyntheticLambda02.invoke(f3), m457getAlphaimpl, rgb2);
        }

        private RgbConnector(Rgb rgb, Rgb rgb2, int i) {
            super(rgb, rgb2, rgb, rgb2, i, null, null);
            float[] mul3x3;
            this.mSource = rgb;
            this.mDestination = rgb2;
            boolean compare = ColorSpaceKt.compare(rgb.whitePoint, rgb2.whitePoint);
            float[] fArr = rgb.transform;
            float[] fArr2 = rgb2.inverseTransform;
            if (compare) {
                mul3x3 = ColorSpaceKt.mul3x3(fArr2, fArr);
            } else {
                WhitePoint whitePoint = rgb.whitePoint;
                float[] xyz$ui_graphics_release = whitePoint.toXyz$ui_graphics_release();
                WhitePoint whitePoint2 = rgb2.whitePoint;
                float[] xyz$ui_graphics_release2 = whitePoint2.toXyz$ui_graphics_release();
                Illuminant.INSTANCE.getClass();
                WhitePoint whitePoint3 = Illuminant.D50;
                if (!ColorSpaceKt.compare(whitePoint, whitePoint3)) {
                    Adaptation.Companion.getClass();
                    float[] fArr3 = Adaptation.Bradford.transform;
                    float[] fArr4 = Illuminant.D50Xyz;
                    fArr = ColorSpaceKt.mul3x3(ColorSpaceKt.chromaticAdaptation(fArr3, xyz$ui_graphics_release, Arrays.copyOf(fArr4, fArr4.length)), fArr);
                }
                if (!ColorSpaceKt.compare(whitePoint2, whitePoint3)) {
                    Adaptation.Companion.getClass();
                    float[] fArr5 = Adaptation.Bradford.transform;
                    float[] fArr6 = Illuminant.D50Xyz;
                    fArr2 = ColorSpaceKt.inverse3x3(ColorSpaceKt.mul3x3(ColorSpaceKt.chromaticAdaptation(fArr5, xyz$ui_graphics_release2, Arrays.copyOf(fArr6, fArr6.length)), rgb2.transform));
                }
                RenderIntent.Companion.getClass();
                mul3x3 = ColorSpaceKt.mul3x3(fArr2, i == RenderIntent.Absolute ? ColorSpaceKt.mul3x3Diag(new float[]{xyz$ui_graphics_release[0] / xyz$ui_graphics_release2[0], xyz$ui_graphics_release[1] / xyz$ui_graphics_release2[1], xyz$ui_graphics_release[2] / xyz$ui_graphics_release2[2]}, fArr) : fArr);
            }
            this.mTransform = mul3x3;
        }
    }

    public /* synthetic */ Connector(ColorSpace colorSpace, ColorSpace colorSpace2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(colorSpace, colorSpace2, i);
    }

    /* renamed from: transformToColor-l2rxGTc$ui_graphics_release, reason: not valid java name */
    public long mo512transformToColorl2rxGTc$ui_graphics_release(long j) {
        float m461getRedimpl = Color.m461getRedimpl(j);
        float m460getGreenimpl = Color.m460getGreenimpl(j);
        float m458getBlueimpl = Color.m458getBlueimpl(j);
        float m457getAlphaimpl = Color.m457getAlphaimpl(j);
        ColorSpace colorSpace = this.transformSource;
        long xy$ui_graphics_release = colorSpace.toXy$ui_graphics_release(m461getRedimpl, m460getGreenimpl, m458getBlueimpl);
        float intBitsToFloat = Float.intBitsToFloat((int) (xy$ui_graphics_release >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (xy$ui_graphics_release & 4294967295L));
        float z$ui_graphics_release = colorSpace.toZ$ui_graphics_release(m461getRedimpl, m460getGreenimpl, m458getBlueimpl);
        float[] fArr = this.transform;
        if (fArr != null) {
            intBitsToFloat *= fArr[0];
            intBitsToFloat2 *= fArr[1];
            z$ui_graphics_release *= fArr[2];
        }
        float f = intBitsToFloat;
        float f2 = intBitsToFloat2;
        return this.transformDestination.mo510xyzaToColorJlNiLsg$ui_graphics_release(f, f2, z$ui_graphics_release, m457getAlphaimpl, this.destination);
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private Connector(androidx.compose.ui.graphics.colorspace.ColorSpace r14, androidx.compose.ui.graphics.colorspace.ColorSpace r15, int r16) {
        /*
            r13 = this;
            r0 = 2
            r3 = 1
            r4 = 0
            long r5 = r14.model
            androidx.compose.ui.graphics.colorspace.ColorModel$Companion r7 = androidx.compose.ui.graphics.colorspace.ColorModel.Companion
            r7.getClass()
            long r7 = androidx.compose.ui.graphics.colorspace.ColorModel.Rgb
            boolean r5 = androidx.compose.ui.graphics.colorspace.ColorModel.m508equalsimpl0(r5, r7)
            if (r5 == 0) goto L1e
            androidx.compose.ui.graphics.colorspace.Illuminant r5 = androidx.compose.ui.graphics.colorspace.Illuminant.INSTANCE
            r5.getClass()
            androidx.compose.ui.graphics.colorspace.WhitePoint r5 = androidx.compose.ui.graphics.colorspace.Illuminant.D50
            androidx.compose.ui.graphics.colorspace.ColorSpace r5 = androidx.compose.ui.graphics.colorspace.ColorSpaceKt.adapt$default(r14, r5)
            goto L1f
        L1e:
            r5 = r14
        L1f:
            long r9 = r15.model
            boolean r6 = androidx.compose.ui.graphics.colorspace.ColorModel.m508equalsimpl0(r9, r7)
            if (r6 == 0) goto L33
            androidx.compose.ui.graphics.colorspace.Illuminant r6 = androidx.compose.ui.graphics.colorspace.Illuminant.INSTANCE
            r6.getClass()
            androidx.compose.ui.graphics.colorspace.WhitePoint r6 = androidx.compose.ui.graphics.colorspace.Illuminant.D50
            androidx.compose.ui.graphics.colorspace.ColorSpace r6 = androidx.compose.ui.graphics.colorspace.ColorSpaceKt.adapt$default(r15, r6)
            goto L34
        L33:
            r6 = r15
        L34:
            androidx.compose.ui.graphics.colorspace.Connector$Companion r9 = androidx.compose.ui.graphics.colorspace.Connector.Companion
            r9.getClass()
            androidx.compose.ui.graphics.colorspace.RenderIntent$Companion r9 = androidx.compose.ui.graphics.colorspace.RenderIntent.Companion
            r9.getClass()
            int r9 = androidx.compose.ui.graphics.colorspace.RenderIntent.Absolute
            r10 = r16
            if (r10 != r9) goto L97
            long r11 = r14.model
            boolean r9 = androidx.compose.ui.graphics.colorspace.ColorModel.m508equalsimpl0(r11, r7)
            long r11 = r15.model
            boolean r7 = androidx.compose.ui.graphics.colorspace.ColorModel.m508equalsimpl0(r11, r7)
            if (r9 == 0) goto L55
            if (r7 == 0) goto L55
            goto L97
        L55:
            if (r9 != 0) goto L59
            if (r7 == 0) goto L97
        L59:
            if (r9 == 0) goto L5d
            r8 = r14
            goto L5e
        L5d:
            r8 = r15
        L5e:
            androidx.compose.ui.graphics.colorspace.Rgb r8 = (androidx.compose.ui.graphics.colorspace.Rgb) r8
            androidx.compose.ui.graphics.colorspace.WhitePoint r8 = r8.whitePoint
            if (r9 == 0) goto L69
            float[] r9 = r8.toXyz$ui_graphics_release()
            goto L70
        L69:
            androidx.compose.ui.graphics.colorspace.Illuminant r9 = androidx.compose.ui.graphics.colorspace.Illuminant.INSTANCE
            r9.getClass()
            float[] r9 = androidx.compose.ui.graphics.colorspace.Illuminant.D50Xyz
        L70:
            if (r7 == 0) goto L77
            float[] r7 = r8.toXyz$ui_graphics_release()
            goto L7e
        L77:
            androidx.compose.ui.graphics.colorspace.Illuminant r7 = androidx.compose.ui.graphics.colorspace.Illuminant.INSTANCE
            r7.getClass()
            float[] r7 = androidx.compose.ui.graphics.colorspace.Illuminant.D50Xyz
        L7e:
            r8 = r9[r4]
            r11 = r7[r4]
            float r8 = r8 / r11
            r11 = r9[r3]
            r12 = r7[r3]
            float r11 = r11 / r12
            r9 = r9[r0]
            r7 = r7[r0]
            float r9 = r9 / r7
            r7 = 3
            float[] r7 = new float[r7]
            r7[r4] = r8
            r7[r3] = r11
            r7[r0] = r9
            goto L98
        L97:
            r7 = 0
        L98:
            r0 = 0
            r1 = r14
            r2 = r15
            r3 = r5
            r4 = r6
            r6 = r7
            r5 = r10
            r7 = r0
            r0 = r13
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.colorspace.Connector.<init>(androidx.compose.ui.graphics.colorspace.ColorSpace, androidx.compose.ui.graphics.colorspace.ColorSpace, int):void");
    }
}
