package com.android.systemui.communal.ui.compose;

import androidx.compose.ui.graphics.ColorMatrix;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Colors {
    public static final Colors INSTANCE = new Colors();
    public static final Lazy DisabledColorFilter$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.ui.compose.Colors$DisabledColorFilter$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Colors.INSTANCE.getClass();
            float[] m469constructorimpl$default = ColorMatrix.m469constructorimpl$default();
            float f = (int) (255 * 0.5f);
            m469constructorimpl$default[0] = 0.5f;
            m469constructorimpl$default[6] = 0.5f;
            m469constructorimpl$default[12] = 0.5f;
            m469constructorimpl$default[4] = f;
            m469constructorimpl$default[9] = f;
            m469constructorimpl$default[14] = f;
            float[] m469constructorimpl$default2 = ColorMatrix.m469constructorimpl$default();
            if (m469constructorimpl$default2.length >= 20) {
                m469constructorimpl$default2[0] = 1.0f;
                m469constructorimpl$default2[1] = 0.0f;
                m469constructorimpl$default2[2] = 0.0f;
                m469constructorimpl$default2[3] = 0.0f;
                m469constructorimpl$default2[4] = 0.0f;
                m469constructorimpl$default2[5] = 0.0f;
                m469constructorimpl$default2[6] = 1.0f;
                m469constructorimpl$default2[7] = 0.0f;
                m469constructorimpl$default2[8] = 0.0f;
                m469constructorimpl$default2[9] = 0.0f;
                m469constructorimpl$default2[10] = 0.0f;
                m469constructorimpl$default2[11] = 0.0f;
                m469constructorimpl$default2[12] = 1.0f;
                m469constructorimpl$default2[13] = 0.0f;
                m469constructorimpl$default2[14] = 0.0f;
                m469constructorimpl$default2[15] = 0.0f;
                m469constructorimpl$default2[16] = 0.0f;
                m469constructorimpl$default2[17] = 0.0f;
                m469constructorimpl$default2[18] = 1.0f;
                m469constructorimpl$default2[19] = 0.0f;
                float f2 = 1 - 0.0f;
                float f3 = 0.213f * f2;
                float f4 = 0.715f * f2;
                float f5 = f2 * 0.072f;
                m469constructorimpl$default2[0] = f3 + 0.0f;
                m469constructorimpl$default2[1] = f4;
                m469constructorimpl$default2[2] = f5;
                m469constructorimpl$default2[5] = f3;
                m469constructorimpl$default2[6] = f4 + 0.0f;
                m469constructorimpl$default2[7] = f5;
                m469constructorimpl$default2[10] = f3;
                m469constructorimpl$default2[11] = f4;
                m469constructorimpl$default2[12] = f5 + 0.0f;
            }
            float f6 = m469constructorimpl$default2[0];
            float f7 = m469constructorimpl$default[0];
            float f8 = m469constructorimpl$default2[1];
            float f9 = m469constructorimpl$default[5];
            float f10 = m469constructorimpl$default2[2];
            float f11 = m469constructorimpl$default[10];
            float f12 = m469constructorimpl$default2[3];
            float f13 = m469constructorimpl$default[15];
            float f14 = f12 * f13;
            float f15 = f14 + (f10 * f11) + (f8 * f9) + (f6 * f7);
            float f16 = m469constructorimpl$default[1];
            float f17 = m469constructorimpl$default[6];
            float f18 = m469constructorimpl$default[11];
            float f19 = m469constructorimpl$default[16];
            float f20 = f12 * f19;
            float f21 = f20 + (f10 * f18) + (f8 * f17) + (f6 * f16);
            float f22 = m469constructorimpl$default[2];
            float f23 = m469constructorimpl$default[7];
            float f24 = m469constructorimpl$default[12];
            float f25 = m469constructorimpl$default[17];
            float f26 = (f12 * f25) + (f10 * f24) + (f8 * f23) + (f6 * f22);
            float f27 = m469constructorimpl$default[3];
            float f28 = m469constructorimpl$default[8];
            float f29 = m469constructorimpl$default[13];
            float f30 = m469constructorimpl$default[18];
            float f31 = f12 * f30;
            float f32 = f31 + (f10 * f29) + (f8 * f28) + (f6 * f27);
            float f33 = m469constructorimpl$default[4];
            float f34 = m469constructorimpl$default[9];
            float f35 = (f8 * f34) + (f6 * f33);
            float f36 = m469constructorimpl$default[14];
            float f37 = m469constructorimpl$default[19];
            float f38 = (f12 * f37) + (f10 * f36) + f35 + m469constructorimpl$default2[4];
            float f39 = m469constructorimpl$default2[5];
            float f40 = m469constructorimpl$default2[6];
            float f41 = m469constructorimpl$default2[7];
            float f42 = m469constructorimpl$default2[8];
            float f43 = (f42 * f13) + (f41 * f11) + (f40 * f9) + (f39 * f7);
            float f44 = (f42 * f19) + (f41 * f18) + (f40 * f17) + (f39 * f16);
            float f45 = (f42 * f25) + (f41 * f24) + (f40 * f23) + (f39 * f22);
            float f46 = (f42 * f30) + (f41 * f29) + (f40 * f28) + (f39 * f27);
            float f47 = f42 * f37;
            float f48 = f47 + (f41 * f36) + (f40 * f34) + (f39 * f33) + m469constructorimpl$default2[9];
            float f49 = m469constructorimpl$default2[10];
            float f50 = m469constructorimpl$default2[11];
            float f51 = m469constructorimpl$default2[12];
            float f52 = m469constructorimpl$default2[13];
            float f53 = (f52 * f13) + (f51 * f11) + (f50 * f9) + (f49 * f7);
            float f54 = (f52 * f19) + (f51 * f18) + (f50 * f17) + (f49 * f16);
            float f55 = (f52 * f25) + (f51 * f24) + (f50 * f23) + (f49 * f22);
            float f56 = (f52 * f30) + (f51 * f29) + (f50 * f28) + (f49 * f27);
            float f57 = f52 * f37;
            float f58 = f57 + (f51 * f36) + (f50 * f34) + (f49 * f33) + m469constructorimpl$default2[14];
            float f59 = m469constructorimpl$default2[15];
            float f60 = m469constructorimpl$default2[16];
            float f61 = (f9 * f60) + (f7 * f59);
            float f62 = m469constructorimpl$default2[17];
            float f63 = (f11 * f62) + f61;
            float f64 = m469constructorimpl$default2[18];
            float f65 = (f13 * f64) + f63;
            float f66 = f19 * f64;
            float f67 = f66 + (f18 * f62) + (f17 * f60) + (f16 * f59);
            float f68 = f25 * f64;
            float f69 = f68 + (f24 * f62) + (f23 * f60) + (f22 * f59);
            float f70 = f30 * f64;
            float f71 = f70 + (f29 * f62) + (f28 * f60) + (f27 * f59);
            float f72 = f64 * f37;
            float f73 = f72 + (f62 * f36) + (f60 * f34) + (f59 * f33) + m469constructorimpl$default2[19];
            m469constructorimpl$default2[0] = f15;
            m469constructorimpl$default2[1] = f21;
            m469constructorimpl$default2[2] = f26;
            m469constructorimpl$default2[3] = f32;
            m469constructorimpl$default2[4] = f38;
            m469constructorimpl$default2[5] = f43;
            m469constructorimpl$default2[6] = f44;
            m469constructorimpl$default2[7] = f45;
            m469constructorimpl$default2[8] = f46;
            m469constructorimpl$default2[9] = f48;
            m469constructorimpl$default2[10] = f53;
            m469constructorimpl$default2[11] = f54;
            m469constructorimpl$default2[12] = f55;
            m469constructorimpl$default2[13] = f56;
            m469constructorimpl$default2[14] = f58;
            m469constructorimpl$default2[15] = f65;
            m469constructorimpl$default2[16] = f67;
            m469constructorimpl$default2[17] = f69;
            m469constructorimpl$default2[18] = f71;
            m469constructorimpl$default2[19] = f73;
            return ColorMatrix.m468boximpl(m469constructorimpl$default2);
        }
    });

    private Colors() {
    }
}
