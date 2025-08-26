package androidx.compose.ui.graphics.colorspace;

import androidx.appcompat.animation.SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0;
import androidx.compose.material3.internal.colorUtil.Frame$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.colorspace.Rgb;
import java.util.Arrays;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final WhitePoint access$computeWhitePoint(Companion companion, float[] fArr) {
            companion.getClass();
            float[] fArrMul3x3Float3 = ColorSpaceKt.mul3x3Float3(fArr, new float[]{1.0f, 1.0f, 1.0f});
            float f = fArrMul3x3Float3[0];
            float f2 = fArrMul3x3Float3[1];
            float f3 = f + f2 + fArrMul3x3Float3[2];
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
            float fM = Frame$$ExternalSyntheticOutline0.m(f, f6, (((f3 * f6) + ((f2 * f5) + (f * f4))) - (f4 * f5)) - (f2 * f3), 0.5f);
            return fM < 0.0f ? -fM : fM;
        }

        public static float[] computePrimaries$ui_graphics_release(float[] fArr) {
            float[] fArrMul3x3Float3 = ColorSpaceKt.mul3x3Float3(fArr, new float[]{1.0f, 0.0f, 0.0f});
            float[] fArrMul3x3Float32 = ColorSpaceKt.mul3x3Float3(fArr, new float[]{0.0f, 1.0f, 0.0f});
            float[] fArrMul3x3Float33 = ColorSpaceKt.mul3x3Float3(fArr, new float[]{0.0f, 0.0f, 1.0f});
            float f = fArrMul3x3Float3[0];
            float f2 = fArrMul3x3Float3[1];
            float f3 = f + f2 + fArrMul3x3Float3[2];
            float f4 = fArrMul3x3Float32[0];
            float f5 = fArrMul3x3Float32[1];
            float f6 = f4 + f5 + fArrMul3x3Float32[2];
            float f7 = fArrMul3x3Float33[0];
            float f8 = fArrMul3x3Float33[1];
            float f9 = f7 + f8 + fArrMul3x3Float33[2];
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
        int iHashCode = (Arrays.hashCode(this.primaries) + ((this.whitePoint.hashCode() + (super.hashCode() * 31)) * 31)) * 31;
        float f = this.min;
        int iFloatToIntBits = (iHashCode + (f == 0.0f ? 0 : Float.floatToIntBits(f))) * 31;
        float f2 = this.max;
        int iFloatToIntBits2 = (iFloatToIntBits + (f2 == 0.0f ? 0 : Float.floatToIntBits(f2))) * 31;
        TransferParameters transferParameters = this.transferParameters;
        int iHashCode2 = iFloatToIntBits2 + (transferParameters != null ? transferParameters.hashCode() : 0);
        if (transferParameters != null) {
            return iHashCode2;
        }
        return this.eotfOrig.hashCode() + ((this.oetfOrig.hashCode() + (iHashCode2 * 31)) * 31);
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final boolean isSrgb() {
        return this.isSrgb;
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    public final long toXy$ui_graphics_release(float f, float f2, float f3) {
        double d = f;
        Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = this.eotfFunc;
        float fInvoke = (float) rgb$$ExternalSyntheticLambda0.invoke(d);
        float fInvoke2 = (float) rgb$$ExternalSyntheticLambda0.invoke(f2);
        float fInvoke3 = (float) rgb$$ExternalSyntheticLambda0.invoke(f3);
        float[] fArr = this.transform;
        if (fArr.length < 9) {
            return 0L;
        }
        float f4 = (fArr[6] * fInvoke3) + (fArr[3] * fInvoke2) + (fArr[0] * fInvoke);
        float f5 = (fArr[7] * fInvoke3) + (fArr[4] * fInvoke2) + (fArr[1] * fInvoke);
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
        float fInvoke = (float) rgb$$ExternalSyntheticLambda0.invoke(d);
        float fInvoke2 = (float) rgb$$ExternalSyntheticLambda0.invoke(f2);
        float fInvoke3 = (float) rgb$$ExternalSyntheticLambda0.invoke(f3);
        float[] fArr = this.transform;
        return (fArr[8] * fInvoke3) + (fArr[5] * fInvoke2) + (fArr[2] * fInvoke);
    }

    @Override // androidx.compose.ui.graphics.colorspace.ColorSpace
    /* renamed from: xyzaToColor-JlNiLsg$ui_graphics_release */
    public final long mo512xyzaToColorJlNiLsg$ui_graphics_release(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        float[] fArr = this.inverseTransform;
        float f5 = (fArr[6] * f3) + (fArr[3] * f2) + (fArr[0] * f);
        float f6 = (fArr[7] * f3) + (fArr[4] * f2) + (fArr[1] * f);
        float f7 = (fArr[8] * f3) + (fArr[5] * f2) + (fArr[2] * f);
        Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = this.oetfFunc;
        return ColorKt.Color((float) rgb$$ExternalSyntheticLambda0.invoke(f5), (float) rgb$$ExternalSyntheticLambda0.invoke(f6), (float) rgb$$ExternalSyntheticLambda0.invoke(f7), f4, colorSpace);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0273  */
    /* JADX WARN: Type inference failed for: r24v0 */
    /* JADX WARN: Type inference failed for: r24v1 */
    /* JADX WARN: Type inference failed for: r24v2 */
    /* JADX WARN: Type inference failed for: r28v0 */
    /* JADX WARN: Type inference failed for: r28v1 */
    /* JADX WARN: Type inference failed for: r28v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Rgb(String str, float[] fArr, WhitePoint whitePoint, float[] fArr2, DoubleFunction doubleFunction, DoubleFunction doubleFunction2, float f, float f2, TransferParameters transferParameters, int i) {
        ?? r24;
        ?? r28;
        int i2;
        float f3;
        float f4;
        boolean z;
        double d;
        super(str, ColorModel.Rgb, i, null);
        ColorModel.Companion.getClass();
        this.whitePoint = whitePoint;
        this.min = f;
        this.max = f2;
        this.transferParameters = transferParameters;
        this.oetfOrig = doubleFunction;
        this.oetf = new Function1() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$oetf$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                double dInvoke = this.this$0.oetfOrig.invoke(((Number) obj).doubleValue());
                Rgb rgb = this.this$0;
                return Double.valueOf(RangesKt___RangesKt.coerceIn(dInvoke, rgb.min, rgb.max));
            }
        };
        this.oetfFunc = new Rgb$$ExternalSyntheticLambda0(this, 0);
        this.eotfOrig = doubleFunction2;
        this.eotf = new Function1() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$eotf$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                double dDoubleValue = ((Number) obj).doubleValue();
                return Double.valueOf(this.this$0.eotfOrig.invoke(RangesKt___RangesKt.coerceIn(dDoubleValue, r6.min, r6.max)));
            }
        };
        this.eotfFunc = new Rgb$$ExternalSyntheticLambda0(this, 1);
        if (fArr.length != 6 && fArr.length != 9) {
            throw new IllegalArgumentException("The color space's primaries must be defined as an array of 6 floats in xyY or 9 floats in XYZ");
        }
        if (f < f2) {
            Companion.getClass();
            float[] fArr3 = new float[6];
            if (fArr.length != 9) {
                System.arraycopy(fArr, 0, fArr3, 0, (6 & 8) != 0 ? fArr.length : 6);
            } else {
                float f5 = fArr[0];
                float f6 = fArr[1];
                float f7 = f5 + f6 + fArr[2];
                fArr3[0] = f5 / f7;
                fArr3[1] = f6 / f7;
                float f8 = fArr[3];
                float f9 = fArr[4];
                float f10 = f8 + f9 + fArr[5];
                fArr3[2] = f8 / f10;
                fArr3[3] = f9 / f10;
                float f11 = fArr[6];
                float f12 = fArr[7];
                float f13 = f11 + f12 + fArr[8];
                fArr3[4] = f11 / f13;
                fArr3[5] = f12 / f13;
            }
            this.primaries = fArr3;
            if (fArr2 == null) {
                float f14 = fArr3[0];
                float f15 = fArr3[1];
                float f16 = fArr3[2];
                float f17 = fArr3[3];
                float f18 = fArr3[4];
                float f19 = fArr3[5];
                f3 = 1.0f;
                float f20 = whitePoint.x;
                r24 = 0;
                float f21 = 1;
                float f22 = (f21 - f14) / f15;
                float f23 = (f21 - f16) / f17;
                float f24 = (f21 - f18) / f19;
                r28 = 1;
                float f25 = whitePoint.y;
                float f26 = (f21 - f20) / f25;
                float f27 = f14 / f15;
                float f28 = f20 / f25;
                float f29 = (f16 / f17) - f27;
                float f30 = f28 - f27;
                float f31 = f23 - f22;
                float f32 = ((f26 - f22) * f29) - (f30 * f31);
                float f33 = (f24 - f22) * f29;
                i2 = 6;
                float f34 = (f18 / f19) - f27;
                float f35 = f32 / (f33 - (f31 * f34));
                float fM = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f35, f34, f30, f29);
                float f36 = (1.0f - fM) - f35;
                float f37 = f36 / f15;
                float f38 = fM / f17;
                float f39 = f35 / f19;
                this.transform = new float[]{f37 * f14, f36, ((1.0f - f14) - f15) * f37, f38 * f16, fM, ((1.0f - f16) - f17) * f38, f39 * f18, f35, ((1.0f - f18) - f19) * f39};
            } else {
                r24 = 0;
                r28 = 1;
                i2 = 6;
                f3 = 1.0f;
                if (fArr2.length == 9) {
                    this.transform = fArr2;
                } else {
                    throw new IllegalArgumentException("Transform must have 9 entries! Has " + fArr2.length);
                }
            }
            this.inverseTransform = ColorSpaceKt.inverse3x3(this.transform);
            float fArea = Companion.area(fArr3);
            ColorSpaces.INSTANCE.getClass();
            if (fArea / Companion.area(ColorSpaces.Ntsc1953Primaries) > 0.9f) {
                float[] fArr4 = ColorSpaces.SrgbPrimaries;
                float f40 = fArr3[r24];
                float f41 = fArr4[r24];
                float f42 = fArr3[r28];
                float f43 = fArr4[r28];
                float f44 = fArr3[2];
                float f45 = fArr4[2];
                float f46 = fArr3[3];
                float f47 = fArr4[3];
                float f48 = fArr3[4];
                float f49 = fArr4[4];
                float f50 = fArr3[5];
                float f51 = fArr4[5];
                float f52 = f50 - f51;
                f4 = 0.0f;
                float[] fArr5 = new float[i2];
                fArr5[r24] = f40 - f41;
                fArr5[r28] = f42 - f43;
                fArr5[2] = f44 - f45;
                fArr5[3] = f46 - f47;
                fArr5[4] = f48 - f49;
                fArr5[5] = f52;
                float f53 = fArr5[r24];
                float f54 = fArr5[r28];
                if (((f43 - f51) * f53) - ((f41 - f49) * f54) >= 0.0f && ((f41 - f45) * f54) - ((f43 - f47) * f53) >= 0.0f) {
                    float f55 = fArr5[2];
                    float f56 = fArr5[3];
                    if (((f47 - f43) * f55) - ((f45 - f41) * f56) >= 0.0f && ((f45 - f49) * f56) - ((f47 - f51) * f55) >= 0.0f) {
                        float f57 = fArr5[4];
                        float f58 = fArr5[5];
                        if (((f51 - f47) * f57) - ((f49 - f45) * f58) < 0.0f || ((f49 - f41) * f58) - ((f51 - f43) * f57) < 0.0f) {
                        }
                    }
                }
                if (i != 0) {
                    float[] fArr6 = ColorSpaces.SrgbPrimaries;
                    if (fArr3 == fArr6) {
                        Illuminant.INSTANCE.getClass();
                        if (ColorSpaceKt.compare(whitePoint, Illuminant.D65) && f == f4 && f2 == f3) {
                            ColorSpaces.INSTANCE.getClass();
                            Rgb rgb = ColorSpaces.Srgb;
                            for (d = 0.0d; d <= 1.0d; d += 0.00392156862745098d) {
                                if (Math.abs(doubleFunction.invoke(d) - rgb.oetfOrig.invoke(d)) <= 0.001d && Math.abs(doubleFunction2.invoke(d) - rgb.eotfOrig.invoke(d)) <= 0.001d) {
                                }
                            }
                            z = r28;
                        }
                        z = r24;
                        break;
                    }
                    for (int i3 = r24; i3 < 6; i3++) {
                        if (Float.compare(fArr3[i3], fArr6[i3]) != 0 && Math.abs(fArr3[i3] - fArr6[i3]) > 0.001f) {
                            break;
                        }
                    }
                    Illuminant.INSTANCE.getClass();
                    if (ColorSpaceKt.compare(whitePoint, Illuminant.D65)) {
                        ColorSpaces.INSTANCE.getClass();
                        Rgb rgb2 = ColorSpaces.Srgb;
                        while (d <= 1.0d) {
                        }
                        z = r28;
                    }
                    z = r24;
                    break;
                }
                z = r28;
                this.isSrgb = z;
                return;
            }
            f4 = 0.0f;
            int i4 = (f > f4 ? 1 : (f == f4 ? 0 : -1));
            if (i != 0) {
            }
            this.isSrgb = z;
            return;
        }
        throw new IllegalArgumentException("Invalid range: min=" + f + ", max=" + f2 + "; min must be strictly < max");
    }

    public Rgb(String str, float[] fArr, final Function1 function1, final Function1 function12) {
        Companion companion = Companion;
        companion.getClass();
        float[] fArrComputePrimaries$ui_graphics_release = Companion.computePrimaries$ui_graphics_release(fArr);
        WhitePoint whitePointAccess$computeWhitePoint = Companion.access$computeWhitePoint(companion, fArr);
        final int i = 0;
        DoubleFunction doubleFunction = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3
            @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
            public final double invoke(double d) {
                Function1 function13 = function1;
                switch (i) {
                    case 0:
                        Rgb.Companion companion2 = Rgb.Companion;
                        break;
                    case 1:
                        Rgb.Companion companion3 = Rgb.Companion;
                        break;
                    case 2:
                        Rgb.Companion companion4 = Rgb.Companion;
                        break;
                    default:
                        Rgb.Companion companion5 = Rgb.Companion;
                        break;
                }
                return ((Number) function13.mo781invoke(Double.valueOf(d))).doubleValue();
            }
        };
        final int i2 = 1;
        this(str, fArrComputePrimaries$ui_graphics_release, whitePointAccess$computeWhitePoint, null, doubleFunction, new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3
            @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
            public final double invoke(double d) {
                Function1 function13 = function12;
                switch (i2) {
                    case 0:
                        Rgb.Companion companion2 = Rgb.Companion;
                        break;
                    case 1:
                        Rgb.Companion companion3 = Rgb.Companion;
                        break;
                    case 2:
                        Rgb.Companion companion4 = Rgb.Companion;
                        break;
                    default:
                        Rgb.Companion companion5 = Rgb.Companion;
                        break;
                }
                return ((Number) function13.mo781invoke(Double.valueOf(d))).doubleValue();
            }
        }, 0.0f, 1.0f, null, -1);
    }

    public Rgb(String str, float[] fArr, WhitePoint whitePoint, final Function1 function1, final Function1 function12, float f, float f2) {
        final int i = 2;
        DoubleFunction doubleFunction = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3
            @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
            public final double invoke(double d) {
                Function1 function13 = function1;
                switch (i) {
                    case 0:
                        Rgb.Companion companion2 = Rgb.Companion;
                        break;
                    case 1:
                        Rgb.Companion companion3 = Rgb.Companion;
                        break;
                    case 2:
                        Rgb.Companion companion4 = Rgb.Companion;
                        break;
                    default:
                        Rgb.Companion companion5 = Rgb.Companion;
                        break;
                }
                return ((Number) function13.mo781invoke(Double.valueOf(d))).doubleValue();
            }
        };
        final int i2 = 3;
        this(str, fArr, whitePoint, null, doubleFunction, new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda3
            @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
            public final double invoke(double d) {
                Function1 function13 = function12;
                switch (i2) {
                    case 0:
                        Rgb.Companion companion2 = Rgb.Companion;
                        break;
                    case 1:
                        Rgb.Companion companion3 = Rgb.Companion;
                        break;
                    case 2:
                        Rgb.Companion companion4 = Rgb.Companion;
                        break;
                    default:
                        Rgb.Companion companion5 = Rgb.Companion;
                        break;
                }
                return ((Number) function13.mo781invoke(Double.valueOf(d))).doubleValue();
            }
        }, f, f2, null, -1);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Rgb(String str, float[] fArr, TransferParameters transferParameters) {
        Companion companion = Companion;
        companion.getClass();
        this(str, Companion.computePrimaries$ui_graphics_release(fArr), Companion.access$computeWhitePoint(companion, fArr), transferParameters, -1);
    }

    public Rgb(String str, float[] fArr, WhitePoint whitePoint, TransferParameters transferParameters) {
        this(str, fArr, whitePoint, transferParameters, -1);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Rgb(String str, float[] fArr, double d) {
        Companion companion = Companion;
        companion.getClass();
        this(str, Companion.computePrimaries$ui_graphics_release(fArr), Companion.access$computeWhitePoint(companion, fArr), d, 0.0f, 1.0f, -1);
    }

    public Rgb(String str, float[] fArr, WhitePoint whitePoint, double d) {
        this(str, fArr, whitePoint, d, 0.0f, 1.0f, -1);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Rgb(String str, float[] fArr, WhitePoint whitePoint, final double d, float f, float f2, int i) {
        DoubleFunction doubleFunction;
        DoubleFunction doubleFunction2 = DoubleIdentity;
        if (d == 1.0d) {
            doubleFunction = doubleFunction2;
        } else {
            final int i2 = 0;
            doubleFunction = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda5
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d2) {
                    double d3 = d;
                    switch (i2) {
                        case 0:
                            Rgb.Companion companion = Rgb.Companion;
                            if (d2 < 0.0d) {
                                d2 = 0.0d;
                            }
                            return Math.pow(d2, 1.0d / d3);
                        default:
                            Rgb.Companion companion2 = Rgb.Companion;
                            if (d2 < 0.0d) {
                                d2 = 0.0d;
                            }
                            return Math.pow(d2, d3);
                    }
                }
            };
        }
        if (d != 1.0d) {
            final int i3 = 1;
            doubleFunction2 = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda5
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d2) {
                    double d3 = d;
                    switch (i3) {
                        case 0:
                            Rgb.Companion companion = Rgb.Companion;
                            if (d2 < 0.0d) {
                                d2 = 0.0d;
                            }
                            return Math.pow(d2, 1.0d / d3);
                        default:
                            Rgb.Companion companion2 = Rgb.Companion;
                            if (d2 < 0.0d) {
                                d2 = 0.0d;
                            }
                            return Math.pow(d2, d3);
                    }
                }
            };
        }
        this(str, fArr, whitePoint, null, doubleFunction, doubleFunction2, f, f2, new TransferParameters(d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 96, null), i);
    }

    public Rgb(String str, float[] fArr, WhitePoint whitePoint, final TransferParameters transferParameters, int i) {
        DoubleFunction doubleFunction;
        DoubleFunction doubleFunction2;
        Companion.getClass();
        double d = transferParameters.gamma;
        boolean z = d == -3.0d;
        double d2 = transferParameters.f;
        double d3 = transferParameters.e;
        if (z) {
            final int i2 = 4;
            doubleFunction = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d4) {
                    switch (i2) {
                        case 0:
                            ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters2 = transferParameters;
                            colorSpaces.getClass();
                            return ColorSpaces.transferHlgEotf$ui_graphics_release(transferParameters2, d4);
                        case 1:
                            ColorSpaces colorSpaces2 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters3 = transferParameters;
                            colorSpaces2.getClass();
                            return ColorSpaces.transferSt2048Eotf$ui_graphics_release(transferParameters3, d4);
                        case 2:
                            TransferParameters transferParameters4 = transferParameters;
                            return d4 >= transferParameters4.d ? Math.pow((transferParameters4.a * d4) + transferParameters4.b, transferParameters4.gamma) : transferParameters4.c * d4;
                        case 3:
                            TransferParameters transferParameters5 = transferParameters;
                            return d4 >= transferParameters5.d ? Math.pow((transferParameters5.a * d4) + transferParameters5.b, transferParameters5.gamma) + transferParameters5.e : (transferParameters5.c * d4) + transferParameters5.f;
                        case 4:
                            ColorSpaces colorSpaces3 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters6 = transferParameters;
                            colorSpaces3.getClass();
                            return ColorSpaces.transferHlgOetf$ui_graphics_release(transferParameters6, d4);
                        case 5:
                            ColorSpaces colorSpaces4 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters7 = transferParameters;
                            colorSpaces4.getClass();
                            return ColorSpaces.transferSt2048Oetf$ui_graphics_release(transferParameters7, d4);
                        case 6:
                            TransferParameters transferParameters8 = transferParameters;
                            double d5 = transferParameters8.d;
                            double d6 = transferParameters8.c;
                            return d4 >= d5 * d6 ? (Math.pow(d4, 1.0d / transferParameters8.gamma) - transferParameters8.b) / transferParameters8.a : d4 / d6;
                        default:
                            TransferParameters transferParameters9 = transferParameters;
                            double d7 = transferParameters9.a;
                            double d8 = transferParameters9.d;
                            double d9 = transferParameters9.c;
                            return d4 >= d8 * d9 ? (Math.pow(d4 - transferParameters9.e, 1.0d / transferParameters9.gamma) - transferParameters9.b) / d7 : (d4 - transferParameters9.f) / d9;
                    }
                }
            };
        } else if (d == -2.0d) {
            final int i3 = 5;
            doubleFunction = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d4) {
                    switch (i3) {
                        case 0:
                            ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters2 = transferParameters;
                            colorSpaces.getClass();
                            return ColorSpaces.transferHlgEotf$ui_graphics_release(transferParameters2, d4);
                        case 1:
                            ColorSpaces colorSpaces2 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters3 = transferParameters;
                            colorSpaces2.getClass();
                            return ColorSpaces.transferSt2048Eotf$ui_graphics_release(transferParameters3, d4);
                        case 2:
                            TransferParameters transferParameters4 = transferParameters;
                            return d4 >= transferParameters4.d ? Math.pow((transferParameters4.a * d4) + transferParameters4.b, transferParameters4.gamma) : transferParameters4.c * d4;
                        case 3:
                            TransferParameters transferParameters5 = transferParameters;
                            return d4 >= transferParameters5.d ? Math.pow((transferParameters5.a * d4) + transferParameters5.b, transferParameters5.gamma) + transferParameters5.e : (transferParameters5.c * d4) + transferParameters5.f;
                        case 4:
                            ColorSpaces colorSpaces3 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters6 = transferParameters;
                            colorSpaces3.getClass();
                            return ColorSpaces.transferHlgOetf$ui_graphics_release(transferParameters6, d4);
                        case 5:
                            ColorSpaces colorSpaces4 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters7 = transferParameters;
                            colorSpaces4.getClass();
                            return ColorSpaces.transferSt2048Oetf$ui_graphics_release(transferParameters7, d4);
                        case 6:
                            TransferParameters transferParameters8 = transferParameters;
                            double d5 = transferParameters8.d;
                            double d6 = transferParameters8.c;
                            return d4 >= d5 * d6 ? (Math.pow(d4, 1.0d / transferParameters8.gamma) - transferParameters8.b) / transferParameters8.a : d4 / d6;
                        default:
                            TransferParameters transferParameters9 = transferParameters;
                            double d7 = transferParameters9.a;
                            double d8 = transferParameters9.d;
                            double d9 = transferParameters9.c;
                            return d4 >= d8 * d9 ? (Math.pow(d4 - transferParameters9.e, 1.0d / transferParameters9.gamma) - transferParameters9.b) / d7 : (d4 - transferParameters9.f) / d9;
                    }
                }
            };
        } else if (d3 == 0.0d && d2 == 0.0d) {
            final int i4 = 6;
            doubleFunction = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d4) {
                    switch (i4) {
                        case 0:
                            ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters2 = transferParameters;
                            colorSpaces.getClass();
                            return ColorSpaces.transferHlgEotf$ui_graphics_release(transferParameters2, d4);
                        case 1:
                            ColorSpaces colorSpaces2 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters3 = transferParameters;
                            colorSpaces2.getClass();
                            return ColorSpaces.transferSt2048Eotf$ui_graphics_release(transferParameters3, d4);
                        case 2:
                            TransferParameters transferParameters4 = transferParameters;
                            return d4 >= transferParameters4.d ? Math.pow((transferParameters4.a * d4) + transferParameters4.b, transferParameters4.gamma) : transferParameters4.c * d4;
                        case 3:
                            TransferParameters transferParameters5 = transferParameters;
                            return d4 >= transferParameters5.d ? Math.pow((transferParameters5.a * d4) + transferParameters5.b, transferParameters5.gamma) + transferParameters5.e : (transferParameters5.c * d4) + transferParameters5.f;
                        case 4:
                            ColorSpaces colorSpaces3 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters6 = transferParameters;
                            colorSpaces3.getClass();
                            return ColorSpaces.transferHlgOetf$ui_graphics_release(transferParameters6, d4);
                        case 5:
                            ColorSpaces colorSpaces4 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters7 = transferParameters;
                            colorSpaces4.getClass();
                            return ColorSpaces.transferSt2048Oetf$ui_graphics_release(transferParameters7, d4);
                        case 6:
                            TransferParameters transferParameters8 = transferParameters;
                            double d5 = transferParameters8.d;
                            double d6 = transferParameters8.c;
                            return d4 >= d5 * d6 ? (Math.pow(d4, 1.0d / transferParameters8.gamma) - transferParameters8.b) / transferParameters8.a : d4 / d6;
                        default:
                            TransferParameters transferParameters9 = transferParameters;
                            double d7 = transferParameters9.a;
                            double d8 = transferParameters9.d;
                            double d9 = transferParameters9.c;
                            return d4 >= d8 * d9 ? (Math.pow(d4 - transferParameters9.e, 1.0d / transferParameters9.gamma) - transferParameters9.b) / d7 : (d4 - transferParameters9.f) / d9;
                    }
                }
            };
        } else {
            final int i5 = 7;
            doubleFunction = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d4) {
                    switch (i5) {
                        case 0:
                            ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters2 = transferParameters;
                            colorSpaces.getClass();
                            return ColorSpaces.transferHlgEotf$ui_graphics_release(transferParameters2, d4);
                        case 1:
                            ColorSpaces colorSpaces2 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters3 = transferParameters;
                            colorSpaces2.getClass();
                            return ColorSpaces.transferSt2048Eotf$ui_graphics_release(transferParameters3, d4);
                        case 2:
                            TransferParameters transferParameters4 = transferParameters;
                            return d4 >= transferParameters4.d ? Math.pow((transferParameters4.a * d4) + transferParameters4.b, transferParameters4.gamma) : transferParameters4.c * d4;
                        case 3:
                            TransferParameters transferParameters5 = transferParameters;
                            return d4 >= transferParameters5.d ? Math.pow((transferParameters5.a * d4) + transferParameters5.b, transferParameters5.gamma) + transferParameters5.e : (transferParameters5.c * d4) + transferParameters5.f;
                        case 4:
                            ColorSpaces colorSpaces3 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters6 = transferParameters;
                            colorSpaces3.getClass();
                            return ColorSpaces.transferHlgOetf$ui_graphics_release(transferParameters6, d4);
                        case 5:
                            ColorSpaces colorSpaces4 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters7 = transferParameters;
                            colorSpaces4.getClass();
                            return ColorSpaces.transferSt2048Oetf$ui_graphics_release(transferParameters7, d4);
                        case 6:
                            TransferParameters transferParameters8 = transferParameters;
                            double d5 = transferParameters8.d;
                            double d6 = transferParameters8.c;
                            return d4 >= d5 * d6 ? (Math.pow(d4, 1.0d / transferParameters8.gamma) - transferParameters8.b) / transferParameters8.a : d4 / d6;
                        default:
                            TransferParameters transferParameters9 = transferParameters;
                            double d7 = transferParameters9.a;
                            double d8 = transferParameters9.d;
                            double d9 = transferParameters9.c;
                            return d4 >= d8 * d9 ? (Math.pow(d4 - transferParameters9.e, 1.0d / transferParameters9.gamma) - transferParameters9.b) / d7 : (d4 - transferParameters9.f) / d9;
                    }
                }
            };
        }
        if (d == -3.0d) {
            final int i6 = 0;
            doubleFunction2 = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d4) {
                    switch (i6) {
                        case 0:
                            ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters2 = transferParameters;
                            colorSpaces.getClass();
                            return ColorSpaces.transferHlgEotf$ui_graphics_release(transferParameters2, d4);
                        case 1:
                            ColorSpaces colorSpaces2 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters3 = transferParameters;
                            colorSpaces2.getClass();
                            return ColorSpaces.transferSt2048Eotf$ui_graphics_release(transferParameters3, d4);
                        case 2:
                            TransferParameters transferParameters4 = transferParameters;
                            return d4 >= transferParameters4.d ? Math.pow((transferParameters4.a * d4) + transferParameters4.b, transferParameters4.gamma) : transferParameters4.c * d4;
                        case 3:
                            TransferParameters transferParameters5 = transferParameters;
                            return d4 >= transferParameters5.d ? Math.pow((transferParameters5.a * d4) + transferParameters5.b, transferParameters5.gamma) + transferParameters5.e : (transferParameters5.c * d4) + transferParameters5.f;
                        case 4:
                            ColorSpaces colorSpaces3 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters6 = transferParameters;
                            colorSpaces3.getClass();
                            return ColorSpaces.transferHlgOetf$ui_graphics_release(transferParameters6, d4);
                        case 5:
                            ColorSpaces colorSpaces4 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters7 = transferParameters;
                            colorSpaces4.getClass();
                            return ColorSpaces.transferSt2048Oetf$ui_graphics_release(transferParameters7, d4);
                        case 6:
                            TransferParameters transferParameters8 = transferParameters;
                            double d5 = transferParameters8.d;
                            double d6 = transferParameters8.c;
                            return d4 >= d5 * d6 ? (Math.pow(d4, 1.0d / transferParameters8.gamma) - transferParameters8.b) / transferParameters8.a : d4 / d6;
                        default:
                            TransferParameters transferParameters9 = transferParameters;
                            double d7 = transferParameters9.a;
                            double d8 = transferParameters9.d;
                            double d9 = transferParameters9.c;
                            return d4 >= d8 * d9 ? (Math.pow(d4 - transferParameters9.e, 1.0d / transferParameters9.gamma) - transferParameters9.b) / d7 : (d4 - transferParameters9.f) / d9;
                    }
                }
            };
        } else if (d == -2.0d) {
            final int i7 = 1;
            doubleFunction2 = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d4) {
                    switch (i7) {
                        case 0:
                            ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters2 = transferParameters;
                            colorSpaces.getClass();
                            return ColorSpaces.transferHlgEotf$ui_graphics_release(transferParameters2, d4);
                        case 1:
                            ColorSpaces colorSpaces2 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters3 = transferParameters;
                            colorSpaces2.getClass();
                            return ColorSpaces.transferSt2048Eotf$ui_graphics_release(transferParameters3, d4);
                        case 2:
                            TransferParameters transferParameters4 = transferParameters;
                            return d4 >= transferParameters4.d ? Math.pow((transferParameters4.a * d4) + transferParameters4.b, transferParameters4.gamma) : transferParameters4.c * d4;
                        case 3:
                            TransferParameters transferParameters5 = transferParameters;
                            return d4 >= transferParameters5.d ? Math.pow((transferParameters5.a * d4) + transferParameters5.b, transferParameters5.gamma) + transferParameters5.e : (transferParameters5.c * d4) + transferParameters5.f;
                        case 4:
                            ColorSpaces colorSpaces3 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters6 = transferParameters;
                            colorSpaces3.getClass();
                            return ColorSpaces.transferHlgOetf$ui_graphics_release(transferParameters6, d4);
                        case 5:
                            ColorSpaces colorSpaces4 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters7 = transferParameters;
                            colorSpaces4.getClass();
                            return ColorSpaces.transferSt2048Oetf$ui_graphics_release(transferParameters7, d4);
                        case 6:
                            TransferParameters transferParameters8 = transferParameters;
                            double d5 = transferParameters8.d;
                            double d6 = transferParameters8.c;
                            return d4 >= d5 * d6 ? (Math.pow(d4, 1.0d / transferParameters8.gamma) - transferParameters8.b) / transferParameters8.a : d4 / d6;
                        default:
                            TransferParameters transferParameters9 = transferParameters;
                            double d7 = transferParameters9.a;
                            double d8 = transferParameters9.d;
                            double d9 = transferParameters9.c;
                            return d4 >= d8 * d9 ? (Math.pow(d4 - transferParameters9.e, 1.0d / transferParameters9.gamma) - transferParameters9.b) / d7 : (d4 - transferParameters9.f) / d9;
                    }
                }
            };
        } else if (d3 == 0.0d && d2 == 0.0d) {
            final int i8 = 2;
            doubleFunction2 = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d4) {
                    switch (i8) {
                        case 0:
                            ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters2 = transferParameters;
                            colorSpaces.getClass();
                            return ColorSpaces.transferHlgEotf$ui_graphics_release(transferParameters2, d4);
                        case 1:
                            ColorSpaces colorSpaces2 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters3 = transferParameters;
                            colorSpaces2.getClass();
                            return ColorSpaces.transferSt2048Eotf$ui_graphics_release(transferParameters3, d4);
                        case 2:
                            TransferParameters transferParameters4 = transferParameters;
                            return d4 >= transferParameters4.d ? Math.pow((transferParameters4.a * d4) + transferParameters4.b, transferParameters4.gamma) : transferParameters4.c * d4;
                        case 3:
                            TransferParameters transferParameters5 = transferParameters;
                            return d4 >= transferParameters5.d ? Math.pow((transferParameters5.a * d4) + transferParameters5.b, transferParameters5.gamma) + transferParameters5.e : (transferParameters5.c * d4) + transferParameters5.f;
                        case 4:
                            ColorSpaces colorSpaces3 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters6 = transferParameters;
                            colorSpaces3.getClass();
                            return ColorSpaces.transferHlgOetf$ui_graphics_release(transferParameters6, d4);
                        case 5:
                            ColorSpaces colorSpaces4 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters7 = transferParameters;
                            colorSpaces4.getClass();
                            return ColorSpaces.transferSt2048Oetf$ui_graphics_release(transferParameters7, d4);
                        case 6:
                            TransferParameters transferParameters8 = transferParameters;
                            double d5 = transferParameters8.d;
                            double d6 = transferParameters8.c;
                            return d4 >= d5 * d6 ? (Math.pow(d4, 1.0d / transferParameters8.gamma) - transferParameters8.b) / transferParameters8.a : d4 / d6;
                        default:
                            TransferParameters transferParameters9 = transferParameters;
                            double d7 = transferParameters9.a;
                            double d8 = transferParameters9.d;
                            double d9 = transferParameters9.c;
                            return d4 >= d8 * d9 ? (Math.pow(d4 - transferParameters9.e, 1.0d / transferParameters9.gamma) - transferParameters9.b) / d7 : (d4 - transferParameters9.f) / d9;
                    }
                }
            };
        } else {
            final int i9 = 3;
            doubleFunction2 = new DoubleFunction() { // from class: androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.graphics.colorspace.DoubleFunction
                public final double invoke(double d4) {
                    switch (i9) {
                        case 0:
                            ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters2 = transferParameters;
                            colorSpaces.getClass();
                            return ColorSpaces.transferHlgEotf$ui_graphics_release(transferParameters2, d4);
                        case 1:
                            ColorSpaces colorSpaces2 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters3 = transferParameters;
                            colorSpaces2.getClass();
                            return ColorSpaces.transferSt2048Eotf$ui_graphics_release(transferParameters3, d4);
                        case 2:
                            TransferParameters transferParameters4 = transferParameters;
                            return d4 >= transferParameters4.d ? Math.pow((transferParameters4.a * d4) + transferParameters4.b, transferParameters4.gamma) : transferParameters4.c * d4;
                        case 3:
                            TransferParameters transferParameters5 = transferParameters;
                            return d4 >= transferParameters5.d ? Math.pow((transferParameters5.a * d4) + transferParameters5.b, transferParameters5.gamma) + transferParameters5.e : (transferParameters5.c * d4) + transferParameters5.f;
                        case 4:
                            ColorSpaces colorSpaces3 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters6 = transferParameters;
                            colorSpaces3.getClass();
                            return ColorSpaces.transferHlgOetf$ui_graphics_release(transferParameters6, d4);
                        case 5:
                            ColorSpaces colorSpaces4 = ColorSpaces.INSTANCE;
                            TransferParameters transferParameters7 = transferParameters;
                            colorSpaces4.getClass();
                            return ColorSpaces.transferSt2048Oetf$ui_graphics_release(transferParameters7, d4);
                        case 6:
                            TransferParameters transferParameters8 = transferParameters;
                            double d5 = transferParameters8.d;
                            double d6 = transferParameters8.c;
                            return d4 >= d5 * d6 ? (Math.pow(d4, 1.0d / transferParameters8.gamma) - transferParameters8.b) / transferParameters8.a : d4 / d6;
                        default:
                            TransferParameters transferParameters9 = transferParameters;
                            double d7 = transferParameters9.a;
                            double d8 = transferParameters9.d;
                            double d9 = transferParameters9.c;
                            return d4 >= d8 * d9 ? (Math.pow(d4 - transferParameters9.e, 1.0d / transferParameters9.gamma) - transferParameters9.b) / d7 : (d4 - transferParameters9.f) / d9;
                    }
                }
            };
        }
        this(str, fArr, whitePoint, null, doubleFunction, doubleFunction2, 0.0f, 1.0f, transferParameters, i);
    }
}
