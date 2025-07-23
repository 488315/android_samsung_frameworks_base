package androidx.reflect.view;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslSemWindowManagerReflector {
    private SeslSemWindowManagerReflector() {
    }

    public static boolean isTableMode() {
        Object invoke;
        Method method = SeslBaseReflector.getMethod("com.samsung.android.view.SemWindowManager", "isTableMode", new Class[0]);
        if (method == null) {
            return false;
        }
        Method method2 = SeslBaseReflector.getMethod("com.samsung.android.view.SemWindowManager", "getInstance", new Class[0]);
        Object obj = null;
        if (method2 != null && (invoke = SeslBaseReflector.invoke(null, method2, new Object[0])) != null && invoke.getClass().getName().equals("com.samsung.android.view.SemWindowManager")) {
            obj = invoke;
        }
        Object invoke2 = SeslBaseReflector.invoke(obj, method, new Object[0]);
        if (invoke2 instanceof Boolean) {
            return ((Boolean) invoke2).booleanValue();
        }
        return false;
    }
}
