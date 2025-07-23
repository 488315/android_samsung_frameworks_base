package androidx.compose.ui.graphics;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Matrix {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final float[] values;

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

    private /* synthetic */ Matrix(float[] fArr) {
        this.values = fArr;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Matrix m480boximpl(float[] fArr) {
        return new Matrix(fArr);
    }

    /* renamed from: constructor-impl$default, reason: not valid java name */
    public static float[] m481constructorimpl$default() {
        return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    }

    /* renamed from: map-MK-Hz9U, reason: not valid java name */
    public static final long m482mapMKHz9U(long j, float[] fArr) {
        if (fArr.length < 16) {
            return j;
        }
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[3];
        float f4 = fArr[4];
        float f5 = fArr[5];
        float f6 = fArr[7];
        float f7 = fArr[12];
        float f8 = fArr[13];
        float f9 = fArr[15];
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        float m = 1 / DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f6, intBitsToFloat2, f3 * intBitsToFloat, f9);
        if ((Float.floatToRawIntBits(m) & Integer.MAX_VALUE) >= 2139095040) {
            m = 0.0f;
        }
        float f10 = f5 * intBitsToFloat2;
        long floatToRawIntBits = (Float.floatToRawIntBits((((f4 * intBitsToFloat2) + (f * intBitsToFloat)) + f7) * m) << 32) | (Float.floatToRawIntBits((f10 + (f2 * intBitsToFloat) + f8) * m) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }

    /* renamed from: map-impl, reason: not valid java name */
    public static final void m483mapimpl(float[] fArr, MutableRect mutableRect) {
        if (fArr.length < 16) {
            return;
        }
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[3];
        float f4 = fArr[4];
        float f5 = fArr[5];
        float f6 = fArr[7];
        float f7 = fArr[12];
        float f8 = fArr[13];
        float f9 = fArr[15];
        float f10 = mutableRect.left;
        float f11 = mutableRect.top;
        float f12 = mutableRect.right;
        float f13 = mutableRect.bottom;
        float f14 = f3 * f10;
        float f15 = f6 * f11;
        float f16 = 1.0f / ((f14 + f15) + f9);
        if ((Float.floatToRawIntBits(f16) & Integer.MAX_VALUE) >= 2139095040) {
            f16 = 0.0f;
        }
        float f17 = f * f10;
        float f18 = f4 * f11;
        float f19 = (f17 + f18 + f7) * f16;
        float f20 = f10 * f2;
        float f21 = f11 * f5;
        float f22 = (f20 + f21 + f8) * f16;
        float f23 = f6 * f13;
        float f24 = 1.0f / ((f14 + f23) + f9);
        if ((Float.floatToRawIntBits(f24) & Integer.MAX_VALUE) >= 2139095040) {
            f24 = 0.0f;
        }
        float f25 = f4 * f13;
        float f26 = (f17 + f25 + f7) * f24;
        float f27 = f5 * f13;
        float f28 = (f20 + f27 + f8) * f24;
        float f29 = f3 * f12;
        float f30 = 1.0f / ((f15 + f29) + f9);
        if ((Float.floatToRawIntBits(f30) & Integer.MAX_VALUE) >= 2139095040) {
            f30 = 0.0f;
        }
        float f31 = f * f12;
        float f32 = (f31 + f18 + f7) * f30;
        float f33 = f12 * f2;
        float f34 = (f21 + f33 + f8) * f30;
        float f35 = 1.0f / ((f29 + f23) + f9);
        float f36 = (Float.floatToRawIntBits(f35) & Integer.MAX_VALUE) < 2139095040 ? f35 : 0.0f;
        float f37 = (f31 + f25 + f7) * f36;
        float f38 = (f33 + f27 + f8) * f36;
        mutableRect.left = Math.min(f19, Math.min(f26, Math.min(f32, f37)));
        mutableRect.top = Math.min(f22, Math.min(f28, Math.min(f34, f38)));
        mutableRect.right = Math.max(f19, Math.max(f26, Math.max(f32, f37)));
        mutableRect.bottom = Math.max(f22, Math.max(f28, Math.max(f34, f38)));
    }

    /* renamed from: reset-impl, reason: not valid java name */
    public static final void m484resetimpl(float[] fArr) {
        if (fArr.length < 16) {
            return;
        }
        fArr[0] = 1.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = 0.0f;
        fArr[5] = 1.0f;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 0.0f;
        fArr[9] = 0.0f;
        fArr[10] = 1.0f;
        fArr[11] = 0.0f;
        fArr[12] = 0.0f;
        fArr[13] = 0.0f;
        fArr[14] = 0.0f;
        fArr[15] = 1.0f;
    }

    /* renamed from: rotateZ-impl, reason: not valid java name */
    public static final void m485rotateZimpl(float f, float[] fArr) {
        if (fArr.length < 16) {
            return;
        }
        double d = f * 0.017453292519943295d;
        float sin = (float) Math.sin(d);
        float cos = (float) Math.cos(d);
        float f2 = fArr[0];
        float f3 = fArr[4];
        float f4 = (sin * f3) + (cos * f2);
        float f5 = -sin;
        float f6 = fArr[1];
        float f7 = fArr[5];
        float f8 = (sin * f7) + (cos * f6);
        float f9 = fArr[2];
        float f10 = fArr[6];
        float f11 = (sin * f10) + (cos * f9);
        float f12 = fArr[3];
        float f13 = fArr[7];
        fArr[0] = f4;
        fArr[1] = f8;
        fArr[2] = f11;
        fArr[3] = (sin * f13) + (cos * f12);
        fArr[4] = (f3 * cos) + (f2 * f5);
        fArr[5] = (f7 * cos) + (f6 * f5);
        fArr[6] = (f10 * cos) + (f9 * f5);
        fArr[7] = (cos * f13) + (f5 * f12);
    }

    /* renamed from: scale-impl, reason: not valid java name */
    public static final void m486scaleimpl(float f, float f2, float[] fArr) {
        if (fArr.length < 16) {
            return;
        }
        fArr[0] = fArr[0] * f;
        fArr[1] = fArr[1] * f;
        fArr[2] = fArr[2] * f;
        fArr[3] = fArr[3] * f;
        fArr[4] = fArr[4] * f2;
        fArr[5] = fArr[5] * f2;
        fArr[6] = fArr[6] * f2;
        fArr[7] = fArr[7] * f2;
        fArr[8] = fArr[8] * 1.0f;
        fArr[9] = fArr[9] * 1.0f;
        fArr[10] = fArr[10] * 1.0f;
        fArr[11] = fArr[11] * 1.0f;
    }

    /* renamed from: timesAssign-58bKbWc, reason: not valid java name */
    public static final void m487timesAssign58bKbWc(float[] fArr, float[] fArr2) {
        if (fArr.length >= 16 && fArr2.length >= 16) {
            float f = fArr[0];
            float f2 = fArr2[0];
            float f3 = fArr[1];
            float f4 = fArr2[4];
            float f5 = fArr[2];
            float f6 = fArr2[8];
            float f7 = f5 * f6;
            float f8 = fArr[3];
            float f9 = fArr2[12];
            float f10 = f8 * f9;
            float f11 = f10 + f7 + (f3 * f4) + (f * f2);
            float f12 = fArr2[1];
            float f13 = fArr2[5];
            float f14 = fArr2[9];
            float f15 = f5 * f14;
            float f16 = fArr2[13];
            float f17 = f8 * f16;
            float f18 = f17 + f15 + (f3 * f13) + (f * f12);
            float f19 = fArr2[2];
            float f20 = fArr2[6];
            float f21 = fArr2[10];
            float f22 = f5 * f21;
            float f23 = fArr2[14];
            float f24 = f8 * f23;
            float f25 = f24 + f22 + (f3 * f20) + (f * f19);
            float f26 = fArr2[3];
            float f27 = fArr2[7];
            float f28 = fArr2[11];
            float f29 = f5 * f28;
            float f30 = fArr2[15];
            float f31 = f8 * f30;
            float f32 = f31 + f29 + (f3 * f27) + (f * f26);
            float f33 = fArr[4];
            float f34 = fArr[5];
            float f35 = fArr[6];
            float f36 = (f35 * f6) + (f34 * f4) + (f33 * f2);
            float f37 = fArr[7];
            float f38 = (f37 * f9) + f36;
            float f39 = (f37 * f16) + (f35 * f14) + (f34 * f13) + (f33 * f12);
            float f40 = (f37 * f23) + (f35 * f21) + (f34 * f20) + (f33 * f19);
            float f41 = f35 * f28;
            float f42 = f37 * f30;
            float f43 = f42 + f41 + (f34 * f27) + (f33 * f26);
            float f44 = fArr[8];
            float f45 = fArr[9];
            float f46 = fArr[10];
            float f47 = (f46 * f6) + (f45 * f4) + (f44 * f2);
            float f48 = fArr[11];
            float f49 = (f48 * f9) + f47;
            float f50 = (f48 * f16) + (f46 * f14) + (f45 * f13) + (f44 * f12);
            float f51 = (f48 * f23) + (f46 * f21) + (f45 * f20) + (f44 * f19);
            float f52 = f46 * f28;
            float f53 = f48 * f30;
            float f54 = f53 + f52 + (f45 * f27) + (f44 * f26);
            float f55 = fArr[12];
            float f56 = fArr[13];
            float f57 = (f4 * f56) + (f2 * f55);
            float f58 = fArr[14];
            float f59 = (f6 * f58) + f57;
            float f60 = fArr[15];
            float f61 = (f9 * f60) + f59;
            float f62 = f14 * f58;
            float f63 = f16 * f60;
            float f64 = f63 + f62 + (f13 * f56) + (f12 * f55);
            float f65 = f21 * f58;
            float f66 = f23 * f60;
            float f67 = f66 + f65 + (f20 * f56) + (f19 * f55);
            float f68 = f58 * f28;
            float f69 = f60 * f30;
            fArr[0] = f11;
            fArr[1] = f18;
            fArr[2] = f25;
            fArr[3] = f32;
            fArr[4] = f38;
            fArr[5] = f39;
            fArr[6] = f40;
            fArr[7] = f43;
            fArr[8] = f49;
            fArr[9] = f50;
            fArr[10] = f51;
            fArr[11] = f54;
            fArr[12] = f61;
            fArr[13] = f64;
            fArr[14] = f67;
            fArr[15] = f69 + f68 + (f56 * f27) + (f55 * f26);
        }
    }

    /* renamed from: translate-impl, reason: not valid java name */
    public static final void m488translateimpl(float f, float f2, float[] fArr) {
        if (fArr.length < 16) {
            return;
        }
        float f3 = (fArr[8] * 0.0f) + (fArr[4] * f2) + (fArr[0] * f) + fArr[12];
        float f4 = (fArr[9] * 0.0f) + (fArr[5] * f2) + (fArr[1] * f) + fArr[13];
        float f5 = (fArr[10] * 0.0f) + (fArr[6] * f2) + (fArr[2] * f) + fArr[14];
        float f6 = (fArr[11] * 0.0f) + (fArr[7] * f2) + (fArr[3] * f) + fArr[15];
        fArr[12] = f3;
        fArr[13] = f4;
        fArr[14] = f5;
        fArr[15] = f6;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Matrix) {
            return Intrinsics.areEqual(this.values, ((Matrix) obj).values);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.values);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("\n            |");
        float[] fArr = this.values;
        sb.append(fArr[0]);
        sb.append(' ');
        sb.append(fArr[1]);
        sb.append(' ');
        sb.append(fArr[2]);
        sb.append(' ');
        sb.append(fArr[3]);
        sb.append("|\n            |");
        sb.append(fArr[4]);
        sb.append(' ');
        sb.append(fArr[5]);
        sb.append(' ');
        sb.append(fArr[6]);
        sb.append(' ');
        sb.append(fArr[7]);
        sb.append("|\n            |");
        sb.append(fArr[8]);
        sb.append(' ');
        sb.append(fArr[9]);
        sb.append(' ');
        sb.append(fArr[10]);
        sb.append(' ');
        sb.append(fArr[11]);
        sb.append("|\n            |");
        sb.append(fArr[12]);
        sb.append(' ');
        sb.append(fArr[13]);
        sb.append(' ');
        sb.append(fArr[14]);
        sb.append(' ');
        sb.append(fArr[15]);
        sb.append("|\n        ");
        return StringsKt__IndentKt.trimIndent(sb.toString());
    }
}
