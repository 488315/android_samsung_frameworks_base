package androidx.reflect.view;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslPointerIconReflector {
    private SeslPointerIconReflector() {
    }

    public static int getField_SEM_TYPE_STYLUS_DEFAULT() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_DEFAULT", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (invoke instanceof Integer) {
            return ((Integer) invoke).intValue();
        }
        return 1;
    }

    public static int getField_SEM_TYPE_STYLUS_PEN_SELECT() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_PEN_SELECT", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (invoke instanceof Integer) {
            return ((Integer) invoke).intValue();
        }
        return 21;
    }

    public static int getField_SEM_TYPE_STYLUS_SCROLL_DOWN() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_SCROLL_DOWN", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (invoke instanceof Integer) {
            return ((Integer) invoke).intValue();
        }
        return 15;
    }

    public static int getField_SEM_TYPE_STYLUS_SCROLL_UP() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_SCROLL_UP", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (invoke instanceof Integer) {
            return ((Integer) invoke).intValue();
        }
        return 11;
    }
}
