package androidx.reflect.view;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslSemWindowManagerReflector {
    private SeslSemWindowManagerReflector() {
    }

    public static boolean isTableMode() {
        Method method = SeslBaseReflector.getMethod("com.samsung.android.view.SemWindowManager", "isTableMode", new Class[0]);
        if (method == null) {
            return false;
        }
        Method method2 = SeslBaseReflector.getMethod("com.samsung.android.view.SemWindowManager", "getInstance", new Class[0]);
        Object obj = null;
        if (method2 != null) {
            Object invoke = SeslBaseReflector.invoke(null, method2, new Object[0]);
            if (invoke.getClass().getName().equals("com.samsung.android.view.SemWindowManager")) {
                obj = invoke;
            }
        }
        Object invoke2 = SeslBaseReflector.invoke(obj, method, new Object[0]);
        if (invoke2 instanceof Boolean) {
            return ((Boolean) invoke2).booleanValue();
        }
        return false;
    }
}
