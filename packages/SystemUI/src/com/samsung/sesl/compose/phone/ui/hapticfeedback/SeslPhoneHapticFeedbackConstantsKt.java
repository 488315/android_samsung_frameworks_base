package com.samsung.sesl.compose.phone.ui.hapticfeedback;

import com.samsung.sesl.compose.ui.hapticfeedback.SeslHapticFeedbackConstants;
import com.samsung.sesl.sep.compat.view.SepHapticFeedbackConstantsCompat;
import com.samsung.sesl.sep.reflect.SepBaseReflector;
import com.samsung.sesl.sep.reflect.view.SepHapticFeedbackConstantsReflector;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public abstract class SeslPhoneHapticFeedbackConstantsKt {
    public static final SeslHapticFeedbackConstants SeslPhoneHapticFeedbackConstants;

    /* JADX WARN: Removed duplicated region for block: B:9:0x0042  */
    static {
        int iIntValue;
        SeslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1 seslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1 = new SeslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1(SepHapticFeedbackConstantsCompat.INSTANCE);
        SepHapticFeedbackConstantsReflector.INSTANCE.getClass();
        SepBaseReflector sepBaseReflector = SepBaseReflector.INSTANCE;
        Class cls = SepHapticFeedbackConstantsReflector.mClass;
        Class[] clsArr = {Integer.TYPE};
        sepBaseReflector.getClass();
        Method declaredMethod = SepBaseReflector.getDeclaredMethod(cls, "hidden_semGetVibrationIndex", clsArr);
        if (declaredMethod != null) {
            Object objInvoke = SepBaseReflector.invoke(null, declaredMethod, 27);
            Integer num = objInvoke instanceof Integer ? (Integer) objInvoke : null;
            iIntValue = num != null ? num.intValue() : -1;
        }
        SeslPhoneHapticFeedbackConstants = new SeslHapticFeedbackConstants(seslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1, iIntValue);
    }
}
