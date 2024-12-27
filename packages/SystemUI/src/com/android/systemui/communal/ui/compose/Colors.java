package com.android.systemui.communal.ui.compose;

import androidx.compose.ui.graphics.ColorMatrix;
import java.util.Arrays;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class Colors {
    public static final Colors INSTANCE = new Colors();
    public static final Lazy DisabledColorFilter$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.ui.compose.Colors$DisabledColorFilter$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Colors.INSTANCE.getClass();
            float[] m399constructorimpl$default = ColorMatrix.m399constructorimpl$default();
            float f = (int) (255 * 0.5f);
            m399constructorimpl$default[0] = 0.5f;
            m399constructorimpl$default[6] = 0.5f;
            m399constructorimpl$default[12] = 0.5f;
            m399constructorimpl$default[4] = f;
            m399constructorimpl$default[9] = f;
            m399constructorimpl$default[14] = f;
            float[] m399constructorimpl$default2 = ColorMatrix.m399constructorimpl$default();
            Arrays.fill(m399constructorimpl$default2, 0, 20, 0.0f);
            m399constructorimpl$default2[0] = 1.0f;
            m399constructorimpl$default2[12] = 1.0f;
            m399constructorimpl$default2[6] = 1.0f;
            m399constructorimpl$default2[18] = 1.0f;
            float f2 = 1 - 0.0f;
            float f3 = 0.213f * f2;
            float f4 = 0.715f * f2;
            float f5 = f2 * 0.072f;
            m399constructorimpl$default2[0] = f3 + 0.0f;
            m399constructorimpl$default2[1] = f4;
            m399constructorimpl$default2[2] = f5;
            m399constructorimpl$default2[5] = f3;
            m399constructorimpl$default2[6] = f4 + 0.0f;
            m399constructorimpl$default2[7] = f5;
            m399constructorimpl$default2[10] = f3;
            m399constructorimpl$default2[11] = f4;
            m399constructorimpl$default2[12] = f5 + 0.0f;
            float m400dotMe4OoYI = ColorMatrix.m400dotMe4OoYI(0, 0, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI2 = ColorMatrix.m400dotMe4OoYI(0, 1, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI3 = ColorMatrix.m400dotMe4OoYI(0, 2, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI4 = ColorMatrix.m400dotMe4OoYI(0, 3, m399constructorimpl$default2, m399constructorimpl$default);
            float f6 = (m399constructorimpl$default2[3] * m399constructorimpl$default[19]) + (m399constructorimpl$default2[2] * m399constructorimpl$default[14]) + (m399constructorimpl$default2[1] * m399constructorimpl$default[9]) + (m399constructorimpl$default2[0] * m399constructorimpl$default[4]) + m399constructorimpl$default2[4];
            float m400dotMe4OoYI5 = ColorMatrix.m400dotMe4OoYI(1, 0, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI6 = ColorMatrix.m400dotMe4OoYI(1, 1, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI7 = ColorMatrix.m400dotMe4OoYI(1, 2, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI8 = ColorMatrix.m400dotMe4OoYI(1, 3, m399constructorimpl$default2, m399constructorimpl$default);
            float f7 = (m399constructorimpl$default2[8] * m399constructorimpl$default[19]) + (m399constructorimpl$default2[7] * m399constructorimpl$default[14]) + (m399constructorimpl$default2[6] * m399constructorimpl$default[9]) + (m399constructorimpl$default2[5] * m399constructorimpl$default[4]) + m399constructorimpl$default2[9];
            float m400dotMe4OoYI9 = ColorMatrix.m400dotMe4OoYI(2, 0, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI10 = ColorMatrix.m400dotMe4OoYI(2, 1, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI11 = ColorMatrix.m400dotMe4OoYI(2, 2, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI12 = ColorMatrix.m400dotMe4OoYI(2, 3, m399constructorimpl$default2, m399constructorimpl$default);
            float f8 = (m399constructorimpl$default2[13] * m399constructorimpl$default[19]) + (m399constructorimpl$default2[12] * m399constructorimpl$default[14]) + (m399constructorimpl$default2[11] * m399constructorimpl$default[9]) + (m399constructorimpl$default2[10] * m399constructorimpl$default[4]) + m399constructorimpl$default2[14];
            float m400dotMe4OoYI13 = ColorMatrix.m400dotMe4OoYI(3, 0, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI14 = ColorMatrix.m400dotMe4OoYI(3, 1, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI15 = ColorMatrix.m400dotMe4OoYI(3, 2, m399constructorimpl$default2, m399constructorimpl$default);
            float m400dotMe4OoYI16 = ColorMatrix.m400dotMe4OoYI(3, 3, m399constructorimpl$default2, m399constructorimpl$default);
            float f9 = (m399constructorimpl$default2[18] * m399constructorimpl$default[19]) + (m399constructorimpl$default2[17] * m399constructorimpl$default[14]) + (m399constructorimpl$default2[16] * m399constructorimpl$default[9]) + (m399constructorimpl$default2[15] * m399constructorimpl$default[4]) + m399constructorimpl$default2[19];
            m399constructorimpl$default2[0] = m400dotMe4OoYI;
            m399constructorimpl$default2[1] = m400dotMe4OoYI2;
            m399constructorimpl$default2[2] = m400dotMe4OoYI3;
            m399constructorimpl$default2[3] = m400dotMe4OoYI4;
            m399constructorimpl$default2[4] = f6;
            m399constructorimpl$default2[5] = m400dotMe4OoYI5;
            m399constructorimpl$default2[6] = m400dotMe4OoYI6;
            m399constructorimpl$default2[7] = m400dotMe4OoYI7;
            m399constructorimpl$default2[8] = m400dotMe4OoYI8;
            m399constructorimpl$default2[9] = f7;
            m399constructorimpl$default2[10] = m400dotMe4OoYI9;
            m399constructorimpl$default2[11] = m400dotMe4OoYI10;
            m399constructorimpl$default2[12] = m400dotMe4OoYI11;
            m399constructorimpl$default2[13] = m400dotMe4OoYI12;
            m399constructorimpl$default2[14] = f8;
            m399constructorimpl$default2[15] = m400dotMe4OoYI13;
            m399constructorimpl$default2[16] = m400dotMe4OoYI14;
            m399constructorimpl$default2[17] = m400dotMe4OoYI15;
            m399constructorimpl$default2[18] = m400dotMe4OoYI16;
            m399constructorimpl$default2[19] = f9;
            return ColorMatrix.m398boximpl(m399constructorimpl$default2);
        }
    });

    private Colors() {
    }
}
