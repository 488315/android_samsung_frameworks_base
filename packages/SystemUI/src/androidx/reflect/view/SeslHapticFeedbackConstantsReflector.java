package androidx.reflect.view;

import android.view.HapticFeedbackConstants;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

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
        Object objInvoke = SeslBaseReflector.invoke(null, declaredMethod, Integer.valueOf(i));
        if (objInvoke instanceof Integer) {
            return ((Integer) objInvoke).intValue();
        }
        return -1;
    }
}
