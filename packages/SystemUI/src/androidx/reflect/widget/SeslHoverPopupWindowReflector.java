package androidx.reflect.widget;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslHoverPopupWindowReflector {
    private SeslHoverPopupWindowReflector() {
    }

    public static int getField_TYPE_NONE() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("com.samsung.android.widget.SemHoverPopupWindow", "hidden_TYPE_NONE", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (invoke instanceof Integer) {
            return ((Integer) invoke).intValue();
        }
        return 0;
    }
}
