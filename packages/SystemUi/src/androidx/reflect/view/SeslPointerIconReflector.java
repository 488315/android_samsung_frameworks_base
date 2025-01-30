package androidx.reflect.view;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslPointerIconReflector {
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

    public static int getField_SEM_TYPE_STYLUS_MORE() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_MORE", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (invoke instanceof Integer) {
            return ((Integer) invoke).intValue();
        }
        return 20010;
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
