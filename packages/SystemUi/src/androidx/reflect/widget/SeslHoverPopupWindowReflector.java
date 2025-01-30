package androidx.reflect.widget;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslHoverPopupWindowReflector {
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
