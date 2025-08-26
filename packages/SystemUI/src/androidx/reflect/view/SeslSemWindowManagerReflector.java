package androidx.reflect.view;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SeslSemWindowManagerReflector {
    private SeslSemWindowManagerReflector() {
    }

    public static boolean isTableMode() {
        Object objInvoke;
        Method method = SeslBaseReflector.getMethod("com.samsung.android.view.SemWindowManager", "isTableMode", new Class[0]);
        if (method == null) {
            return false;
        }
        Method method2 = SeslBaseReflector.getMethod("com.samsung.android.view.SemWindowManager", "getInstance", new Class[0]);
        Object obj = null;
        if (method2 != null && (objInvoke = SeslBaseReflector.invoke(null, method2, new Object[0])) != null && objInvoke.getClass().getName().equals("com.samsung.android.view.SemWindowManager")) {
            obj = objInvoke;
        }
        Object objInvoke2 = SeslBaseReflector.invoke(obj, method, new Object[0]);
        if (objInvoke2 instanceof Boolean) {
            return ((Boolean) objInvoke2).booleanValue();
        }
        return false;
    }
}
