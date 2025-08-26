package androidx.reflect.view;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SeslPointerIconReflector {
    private SeslPointerIconReflector() {
    }

    public static int getField_SEM_TYPE_STYLUS_DEFAULT() throws NoSuchMethodException, SecurityException {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_DEFAULT", new Class[0]);
        Object objInvoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (objInvoke instanceof Integer) {
            return ((Integer) objInvoke).intValue();
        }
        return 1;
    }

    public static int getField_SEM_TYPE_STYLUS_PEN_SELECT() throws NoSuchMethodException, SecurityException {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_PEN_SELECT", new Class[0]);
        Object objInvoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (objInvoke instanceof Integer) {
            return ((Integer) objInvoke).intValue();
        }
        return 21;
    }

    public static int getField_SEM_TYPE_STYLUS_SCROLL_DOWN() throws NoSuchMethodException, SecurityException {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_SCROLL_DOWN", new Class[0]);
        Object objInvoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (objInvoke instanceof Integer) {
            return ((Integer) objInvoke).intValue();
        }
        return 15;
    }

    public static int getField_SEM_TYPE_STYLUS_SCROLL_UP() throws NoSuchMethodException, SecurityException {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_SCROLL_UP", new Class[0]);
        Object objInvoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (objInvoke instanceof Integer) {
            return ((Integer) objInvoke).intValue();
        }
        return 11;
    }
}
