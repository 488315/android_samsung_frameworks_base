package androidx.reflect.view;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SeslViewRuneReflector {
    private SeslViewRuneReflector() {
    }

    public static boolean isEdgeEffectStretchType() {
        Method method = SeslBaseReflector.getMethod("com.samsung.android.rune.ViewRune", "hidden_isEdgeEffectStretchType", new Class[0]);
        Object objInvoke = method != null ? SeslBaseReflector.invoke("com.samsung.android.rune.ViewRune", method, new Object[0]) : null;
        if (objInvoke instanceof Boolean) {
            return ((Boolean) objInvoke).booleanValue();
        }
        return false;
    }

    public static boolean supportFoldableDualDisplay() {
        Method method = SeslBaseReflector.getMethod("com.samsung.android.rune.ViewRune", "hidden_supportFoldableDualDisplay", new Class[0]);
        Object objInvoke = method != null ? SeslBaseReflector.invoke("com.samsung.android.rune.ViewRune", method, new Object[0]) : null;
        if (objInvoke instanceof Boolean) {
            return ((Boolean) objInvoke).booleanValue();
        }
        return false;
    }

    public static boolean supportFoldableNoSubDisplay() {
        Method method = SeslBaseReflector.getMethod("com.samsung.android.rune.ViewRune", "hidden_supportFoldableNoSubDisplay", new Class[0]);
        Object objInvoke = method != null ? SeslBaseReflector.invoke("com.samsung.android.rune.ViewRune", method, new Object[0]) : null;
        if (objInvoke instanceof Boolean) {
            return ((Boolean) objInvoke).booleanValue();
        }
        return false;
    }
}
