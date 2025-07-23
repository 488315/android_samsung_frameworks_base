package androidx.reflect.view;

import android.view.HapticFeedbackConstants;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslHapticFeedbackConstantsReflector {
    public static final Class mClass = HapticFeedbackConstants.class;

    private SeslHapticFeedbackConstantsReflector() {
    }

    public static int semGetVibrationIndex(int i) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semGetVibrationIndex", Integer.TYPE);
        if (declaredMethod == null) {
            return -1;
        }
        Object invoke = SeslBaseReflector.invoke(null, declaredMethod, Integer.valueOf(i));
        if (invoke instanceof Integer) {
            return ((Integer) invoke).intValue();
        }
        return -1;
    }
}
