package com.samsung.sesl.compose.phone.ui.hapticfeedback;

import com.samsung.sesl.compose.ui.hapticfeedback.SeslHapticFeedbackConstants;
import com.samsung.sesl.sep.compat.view.SepHapticFeedbackConstantsCompat;
import com.samsung.sesl.sep.reflect.SepBaseReflector;
import com.samsung.sesl.sep.reflect.view.SepHapticFeedbackConstantsReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SeslPhoneHapticFeedbackConstantsKt {
    public static final SeslHapticFeedbackConstants SeslPhoneHapticFeedbackConstants;

    static {
        int i;
        SeslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1 seslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1 = new SeslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1(SepHapticFeedbackConstantsCompat.INSTANCE);
        SepHapticFeedbackConstantsReflector.INSTANCE.getClass();
        SepBaseReflector sepBaseReflector = SepBaseReflector.INSTANCE;
        Class cls = SepHapticFeedbackConstantsReflector.mClass;
        Class[] clsArr = {Integer.TYPE};
        sepBaseReflector.getClass();
        Method declaredMethod = SepBaseReflector.getDeclaredMethod(cls, "hidden_semGetVibrationIndex", clsArr);
        if (declaredMethod != null) {
            Object invoke = SepBaseReflector.invoke(null, declaredMethod, 27);
            Integer num = invoke instanceof Integer ? (Integer) invoke : null;
            if (num != null) {
                i = num.intValue();
                SeslPhoneHapticFeedbackConstants = new SeslHapticFeedbackConstants(seslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1, i);
            }
        }
        i = -1;
        SeslPhoneHapticFeedbackConstants = new SeslHapticFeedbackConstants(seslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1, i);
    }
}
