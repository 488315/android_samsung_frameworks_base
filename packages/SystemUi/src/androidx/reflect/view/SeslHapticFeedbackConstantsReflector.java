package androidx.reflect.view;

import android.view.HapticFeedbackConstants;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslHapticFeedbackConstantsReflector {
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
