package androidx.compose.ui.graphics.colorspace;

import androidx.compose.material3.internal.colorUtil.Frame$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import java.util.Arrays;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Rgb extends ColorSpace {
    public static final Companion Companion = new Companion(null);
    public static final Rgb$$ExternalSyntheticLambda2 DoubleIdentity = new Rgb$$ExternalSyntheticLambda2();
    public final Function1 eotf;
    public final Rgb$$ExternalSyntheticLambda0 eotfFunc;
    public final DoubleFunction eotfOrig;
    public final float[] inverseTransform;
    public final boolean isSrgb;
    public final float max;
    public final float min;
    public final Function1 oetf;
    public final Rgb$$ExternalSyntheticLambda0 oetfFunc;
    public final DoubleFunction oetfOrig;
    public final float[] primaries;
    public final TransferParameters transferParameters;
    public final float[] transform;
    public final WhitePoint whitePoint;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final WhitePoint access$computeWhitePoint(Companion companion, float[] fArr) {
            companion.getClass();
            float[] mul3x3Float3 = ColorSpaceKt.mul3x3Float3(fArr, new float[]{1.0f, 1.0f, 1.0f});
            float f = mul3x3Float3[0];
            float f2 = mul3x3Float3[1];
            float f3 = f + f2 + mul3x3Float3[2];
            return new WhitePoint(f / f3, f2 / f3);
        }

        public static float area(float[] fArr) {
            if (fArr.length < 6) {
                return 0.0f;
            }
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            float f4 = fArr[3];
            float f5 = fArr[4];
            float f6 = fArr[5];
            float m = Frame$$ExternalSyntheticOutline0.m(f, f6, (((f3 * f6) + ((f2 * f5) + (f * f4))) - (f4 * f5)) - (f2 * f3), 0.5f);
            return m < 0.0f ? -m : m;
        }

        public static float[] computePrimaries$ui_graphics_release(float[] fArr) {
            float[] mul3x3Float3 = ColorSpaceKt.mul3x3Float3(fArr, new float[]{1.0f, 0.0f, 0.0f});
            float[] mul3x3Float32 = ColorSpaceKt.mul3x3Float3(fArr, new float[]{0.0f, 1.0f, 0.0f});
            float[] mul3x3Float33 = ColorSpaceKt.mul3x3Float3(fArr, new float[]{0.0f, 0.0f, 1.0f});
            float f = mul3x3Float3[0];
            float f2 = mul3x3Float3[1];
            float f3 = f + f2 + mul3x3Float3[2];
            float f4 = mul3x3Float32[0];
            float f5 = mul3x3Float32[1];
            float f6 = f4 + f5 + mul3x3Float32[2];
            float f7 = mul3x3Float33[0];
            float f8 = mul3x3Float33[1];
            float f9 = f7 + f8 + mul3x3Float33[2];
            return new float[]{f / f3, f2 / f3, f4 / f6, f5 / f6, f7 / f9, f8 / f9};
        }

        private Companion() {
        }
    }

    public Rgb(Rgb rgb, float[] fArr, WhitePoint whitePoint) {
        this(rgb.name, rgb.primaries, whitePoint, fArr, rgb.oetfOrig, rgb.eotfOrig, rgb.min, rgb.max, rgb.transferParameters, -1);
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Rgb.class != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        Rgb rgb = (Rgb) obj;
        if (Float.compare(rgb.min, this.min) != 0 || Float.compare(rgb.max, this.max) != 0 || !Intrinsics.areEqual(this.whitePoint, rgb.whitePoint) || !Arrays.equals(this.primaries, rgb.primaries)) {
            return false;
        }
        TransferParameters transferParameters = rgb.transferParameters;
        TransferParameters transferParameters2 = this.transferParameters;
        if (transferParameters2 != null) {
            return Intrinsics.areEqual(transferParameters2, transferParameters);
        }
        if (transferParameters == null) {
            return true;
        }
        if (Intrinsics.areEqual(this.oetfOrig, rgb.oetfOrig)) {
            return Intrinsics.areEqual(this.eotfOrig, rgb.eotfOrig);
        }
        return false;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float[] fromXyz(float[] fArr) {
        ColorSpaceKt.mul3x3Float3(this.inverseTransform, fArr);
        if (fArr.length < 3) {
            return fArr;
        }
        double d = fArr[0];
        Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = this.oetfFunc;
        fArr[0] = (float) rgb$$ExternalSyntheticLambda0.invoke(d);
        fArr[1] = (float) rgb$$ExternalSyntheticLambda0.invoke(fArr[1]);
        fArr[2] = (float) rgb$$ExternalSyntheticLambda0.invoke(fArr[2]);
        return fArr;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float getMaxValue(int i) {
        return this.max;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float getMinValue(int i) {
        return this.min;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final int hashCode() {
        int hashCode = (Arrays.hashCode(this.primaries) + ((this.whitePoint.hashCode() + (super.hashCode() * 31)) * 31)) * 31;
        float f = this.min;
        int floatToIntBits = (hashCode + (f == 0.0f ? 0 : Float.floatToIntBits(f))) * 31;
        float f2 = this.max;
        int floatToIntBits2 = (floatToIntBits + (f2 == 0.0f ? 0 : Float.floatToIntBits(f2))) * 31;
        TransferParameters transferParameters = this.transferParameters;
        int hashCode2 = floatToIntBits2 + (transferParameters != null ? transferParameters.hashCode() : 0);
        if (transferParameters != null) {
            return hashCode2;
        }
        return this.eotfOrig.hashCode() + ((this.oetfOrig.hashCode() + (hashCode2 * 31)) * 31);
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final boolean isSrgb() {
        return this.isSrgb;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final long toXy$ui_graphics_release(float f, float f2, float f3) {
        double d = f;
        Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = this.eotfFunc;
        float invoke = (float) rgb$$ExternalSyntheticLambda0.invoke(d);
        float invoke2 = (float) rgb$$ExternalSyntheticLambda0.invoke(f2);
        float invoke3 = (float) rgb$$ExternalSyntheticLambda0.invoke(f3);
        float[] fArr = this.transform;
        if (fArr.length < 9) {
            return 0L;
        }
        float f4 = (fArr[6] * invoke3) + (fArr[3] * invoke2) + (fArr[0] * invoke);
        float f5 = (fArr[7] * invoke3) + (fArr[4] * invoke2) + (fArr[1] * invoke);
        return (Float.floatToRawIntBits(f4) << 32) | (4294967295L & Float.floatToRawIntBits(f5));
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float[] toXyz(float[] fArr) {
        if (fArr.length < 3) {
            return fArr;
        }
        double d = fArr[0];
        Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = this.eotfFunc;
        fArr[0] = (float) rgb$$ExternalSyntheticLambda0.invoke(d);
        fArr[1] = (float) rgb$$ExternalSyntheticLambda0.invoke(fArr[1]);
        fArr[2] = (float) rgb$$ExternalSyntheticLambda0.invoke(fArr[2]);
        return ColorSpaceKt.mul3x3Float3(this.transform, fArr);
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final float toZ$ui_graphics_release(float f, float f2, float f3) {
        double d = f;
        Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = this.eotfFunc;
        float invoke = (float) rgb$$ExternalSyntheticLambda0.invoke(d);
        float invoke2 = (float) rgb$$ExternalSyntheticLambda0.invoke(f2);
        float invoke3 = (float) rgb$$ExternalSyntheticLambda0.invoke(f3);
        float[] fArr = this.transform;
        return (fArr[8] * invoke3) + (fArr[5] * invoke2) + (fArr[2] * invoke);
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    /* renamed from: xyzaToColor-JlNiLsg$ui_graphics_release */
    public final long mo510xyzaToColorJlNiLsg$ui_graphics_release(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        float[] fArr = this.inverseTransform;
        float f5 = (fArr[6] * f3) + (fArr[3] * f2) + (fArr[0] * f);
        float f6 = (fArr[7] * f3) + (fArr[4] * f2) + (fArr[1] * f);
        float f7 = (fArr[8] * f3) + (fArr[5] * f2) + (fArr[2] * f);
        Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = this.oetfFunc;
        return ColorKt.Color((float) rgb$$ExternalSyntheticLambda0.invoke(f5), (float) rgb$$ExternalSyntheticLambda0.invoke(f6), (float) rgb$$ExternalSyntheticLambda0.invoke(f7), f4, colorSpace);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x01e9, code lost:
    
        if ((((r22 - r5) * r2) - ((r2 - r8) * r3)) >= 0.0f) goto L40;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r24v0 */
    /* JADX WARN: Type inference failed for: r24v1 */
    /* JADX WARN: Type inference failed for: r24v2 */
    /* JADX WARN: Type inference failed for: r28v0 */
    /* JADX WARN: Type inference failed for: r28v1 */
    /* JADX WARN: Type inference failed for: r28v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Rgb(java.lang.String r33, float[] r34, androidx.compose.ui.graphics.colorspace.WhitePoint r35, float[] r36, androidx.compose.ui.graphics.colorspace.DoubleFunction r37, androidx.compose.ui.graphics.colorspace.DoubleFunction r38, float r39, float r40, androidx.compose.ui.graphics.colorspace.TransferParameters r41, int r42) {
        /*
            Method dump skipped, instructions count: 689
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.colorspace.Rgb.<init>(java.lang.String, float[], androidx.compose.ui.graphics.colorspace.WhitePoint, float[], androidx.compose.ui.graphics.colorspace.DoubleFunction, androidx.compose.ui.graphics.colorspace.DoubleFunction, float, float, androidx.compose.ui.graphics.colorspace.TransferParameters, int):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Rgb(java.lang.String r13, float[] r14, final kotlin.jvm.functions.Function1 r15, final kotlin.jvm.functions.Function1 r16) {
        /*
            r12 = this;
            androidx.compose.ui.graphics.colorspace.Rgb$Companion r0 = androidx.compose.ui.graphics.colorspace.Rgb.Companion
            r0.getClass()
            float[] r3 = androidx.compose.ui.graphics.colorspace.Rgb.Companion.computePrimaries$ui_graphics_release(r14)
            androidx.compose.ui.graphics.colorspace.WhitePoint r4 = androidx.compose.ui.graphics.colorspace.Rgb.Companion.access$computeWhitePoint(r0, r14)
            androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3 r6 = new androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3
            r14 = 0
            r6.<init>()
            androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3 r7 = new androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3
            r14 = 1
            r15 = r16
            r7.<init>()
            r8 = 0
            r9 = 1065353216(0x3f800000, float:1.0)
            r5 = 0
            r10 = 0
            r11 = -1
            r1 = r12
            r2 = r13
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.colorspace.Rgb.<init>(java.lang.String, float[], kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Rgb(java.lang.String r12, float[] r13, androidx.compose.ui.graphics.colorspace.WhitePoint r14, final kotlin.jvm.functions.Function1 r15, final kotlin.jvm.functions.Function1 r16, float r17, float r18) {
        /*
            r11 = this;
            androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3 r5 = new androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3
            r0 = 2
            r5.<init>()
            androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3 r6 = new androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3
            r15 = 3
            r0 = r16
            r6.<init>()
            r10 = -1
            r4 = 0
            r9 = 0
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r14
            r7 = r17
            r8 = r18
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.colorspace.Rgb.<init>(java.lang.String, float[], androidx.compose.ui.graphics.colorspace.WhitePoint, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, float, float):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Rgb(java.lang.String r8, float[] r9, androidx.compose.ui.graphics.colorspace.TransferParameters r10) {
        /*
            r7 = this;
            androidx.compose.ui.graphics.colorspace.Rgb$Companion r0 = androidx.compose.ui.graphics.colorspace.Rgb.Companion
            r0.getClass()
            float[] r3 = androidx.compose.ui.graphics.colorspace.Rgb.Companion.computePrimaries$ui_graphics_release(r9)
            androidx.compose.ui.graphics.colorspace.WhitePoint r4 = androidx.compose.ui.graphics.colorspace.Rgb.Companion.access$computeWhitePoint(r0, r9)
            r6 = -1
            r1 = r7
            r2 = r8
            r5 = r10
            r1.<init>(r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.colorspace.Rgb.<init>(java.lang.String, float[], androidx.compose.ui.graphics.colorspace.TransferParameters):void");
    }

    public Rgb(String str, float[] fArr, WhitePoint whitePoint, TransferParameters transferParameters) {
        this(str, fArr, whitePoint, transferParameters, -1);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Rgb(java.lang.String r11, float[] r12, double r13) {
        /*
            r10 = this;
            androidx.compose.ui.graphics.colorspace.Rgb$Companion r0 = androidx.compose.ui.graphics.colorspace.Rgb.Companion
            r0.getClass()
            float[] r3 = androidx.compose.ui.graphics.colorspace.Rgb.Companion.computePrimaries$ui_graphics_release(r12)
            androidx.compose.ui.graphics.colorspace.WhitePoint r4 = androidx.compose.ui.graphics.colorspace.Rgb.Companion.access$computeWhitePoint(r0, r12)
            r9 = -1
            r7 = 0
            r8 = 1065353216(0x3f800000, float:1.0)
            r1 = r10
            r2 = r11
            r5 = r13
            r1.<init>(r2, r3, r4, r5, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.colorspace.Rgb.<init>(java.lang.String, float[], double):void");
    }

    public Rgb(String str, float[] fArr, WhitePoint whitePoint, double d) {
        this(str, fArr, whitePoint, d, 0.0f, 1.0f, -1);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Rgb(java.lang.String r20, float[] r21, androidx.compose.ui.graphics.colorspace.WhitePoint r22, final double r23, float r25, float r26, int r27) {
        /*
            r19 = this;
            r1 = r23
            r3 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r0 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda2 r3 = androidx.compose.ui.graphics.colorspace.Rgb.DoubleIdentity
            if (r0 != 0) goto Ld
            r17 = r3
            goto L15
        Ld:
            androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda5 r4 = new androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda5
            r5 = 0
            r4.<init>()
            r17 = r4
        L15:
            if (r0 != 0) goto L1a
        L17:
            r18 = r3
            goto L21
        L1a:
            androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda5 r3 = new androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda5
            r0 = 1
            r3.<init>()
            goto L17
        L21:
            androidx.compose.ui.graphics.colorspace.TransferParameters r15 = new androidx.compose.ui.graphics.colorspace.TransferParameters
            r11 = 0
            r13 = 0
            r3 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r5 = 0
            r7 = 0
            r9 = 0
            r0 = r15
            r15 = 96
            r16 = 0
            r0.<init>(r1, r3, r5, r7, r9, r11, r13, r15, r16)
            r10 = 0
            r6 = r19
            r7 = r20
            r8 = r21
            r9 = r22
            r13 = r25
            r14 = r26
            r16 = r27
            r15 = r0
            r11 = r17
            r12 = r18
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.colorspace.Rgb.<init>(java.lang.String, float[], androidx.compose.ui.graphics.colorspace.WhitePoint, double, float, float, int):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Rgb(java.lang.String r16, float[] r17, androidx.compose.ui.graphics.colorspace.WhitePoint r18, final androidx.compose.ui.graphics.colorspace.TransferParameters r19, int r20) {
        /*
            r15 = this;
            r9 = r19
            androidx.compose.ui.graphics.colorspace.Rgb$Companion r0 = androidx.compose.ui.graphics.colorspace.Rgb.Companion
            r0.getClass()
            double r0 = r9.gamma
            r2 = -4609434218613702656(0xc008000000000000, double:-3.0)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L11
            r4 = 1
            goto L12
        L11:
            r4 = 0
        L12:
            r5 = -4611686018427387904(0xc000000000000000, double:-2.0)
            r7 = 0
            double r10 = r9.f
            double r12 = r9.e
            if (r4 == 0) goto L23
            androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0 r4 = new androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
            r14 = 4
            r4.<init>()
            goto L43
        L23:
            int r4 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r4 != 0) goto L2e
            androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0 r4 = new androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
            r14 = 5
            r4.<init>()
            goto L43
        L2e:
            int r4 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r4 != 0) goto L3d
            int r4 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r4 != 0) goto L3d
            androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0 r4 = new androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
            r14 = 6
            r4.<init>()
            goto L43
        L3d:
            androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0 r4 = new androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
            r14 = 7
            r4.<init>()
        L43:
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 != 0) goto L4f
            androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0 r0 = new androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
            r1 = 0
            r0.<init>()
        L4d:
            r6 = r0
            goto L70
        L4f:
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 != 0) goto L5a
            androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0 r0 = new androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
            r1 = 1
            r0.<init>()
            goto L4d
        L5a:
            int r0 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r0 != 0) goto L69
            int r0 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r0 != 0) goto L69
            androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0 r0 = new androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
            r1 = 2
            r0.<init>()
            goto L4d
        L69:
            androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0 r0 = new androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
            r1 = 3
            r0.<init>()
            goto L4d
        L70:
            r8 = 1065353216(0x3f800000, float:1.0)
            r5 = r4
            r4 = 0
            r7 = 0
            r0 = r15
            r1 = r16
            r2 = r17
            r3 = r18
            r10 = r20
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.colorspace.Rgb.<init>(java.lang.String, float[], androidx.compose.ui.graphics.colorspace.WhitePoint, androidx.compose.ui.graphics.colorspace.TransferParameters, int):void");
    }
}
