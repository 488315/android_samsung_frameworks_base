package androidx.reflect.view;

import android.view.DisplayCutout;
import android.view.WindowInsets;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SeslWindowInsetsReflector {
    public static final Class mClass = WindowInsets.class;

    private SeslWindowInsetsReflector() {
    }

    public static DisplayCutout getDisplayCutoutForUdc(WindowInsets windowInsets) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_getDisplayCutoutForUdc", new Class[0]);
        if (declaredMethod == null) {
            return null;
        }
        Object objInvoke = SeslBaseReflector.invoke(windowInsets, declaredMethod, new Object[0]);
        if (objInvoke instanceof DisplayCutout) {
            return (DisplayCutout) objInvoke;
        }
        return null;
    }
}
