package androidx.reflect.widget;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SeslHoverPopupWindowReflector {
    private SeslHoverPopupWindowReflector() {
    }

    public static int getField_TYPE_NONE() throws NoSuchMethodException, SecurityException {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("com.samsung.android.widget.SemHoverPopupWindow", "hidden_TYPE_NONE", new Class[0]);
        Object objInvoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        if (objInvoke instanceof Integer) {
            return ((Integer) objInvoke).intValue();
        }
        return 0;
    }
}
