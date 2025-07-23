package androidx.reflect.view;

import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslViewRuneReflector {
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
