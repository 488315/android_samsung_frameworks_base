package androidx.reflect.view;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslViewRuneReflector {
    private SeslViewRuneReflector() {
    }

    public static boolean isEdgeEffectStretchType() {
        Method method = SeslBaseReflector.getMethod("com.samsung.android.rune.ViewRune", "hidden_isEdgeEffectStretchType", new Class[0]);
        Object invoke = method != null ? SeslBaseReflector.invoke("com.samsung.android.rune.ViewRune", method, new Object[0]) : null;
        if (invoke instanceof Boolean) {
            return ((Boolean) invoke).booleanValue();
        }
        return false;
    }

    public static boolean supportFoldableDualDisplay() {
        Method method = SeslBaseReflector.getMethod("com.samsung.android.rune.ViewRune", "hidden_supportFoldableDualDisplay", new Class[0]);
        Object invoke = method != null ? SeslBaseReflector.invoke("com.samsung.android.rune.ViewRune", method, new Object[0]) : null;
        if (invoke instanceof Boolean) {
            return ((Boolean) invoke).booleanValue();
        }
        return false;
    }

    public static boolean supportFoldableNoSubDisplay() {
        Method method = SeslBaseReflector.getMethod("com.samsung.android.rune.ViewRune", "hidden_supportFoldableNoSubDisplay", new Class[0]);
        Object invoke = method != null ? SeslBaseReflector.invoke("com.samsung.android.rune.ViewRune", method, new Object[0]) : null;
        if (invoke instanceof Boolean) {
            return ((Boolean) invoke).booleanValue();
        }
        return false;
    }
}
