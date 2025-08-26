package androidx.reflect.provider;

import android.provider.Settings;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SeslSettingsReflector$SeslSystemReflector {
    public static final Class mClass = Settings.System.class;

    private SeslSettingsReflector$SeslSystemReflector() {
    }

    public static String getField_SEM_PEN_HOVERING() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_SEM_PEN_HOVERING", new Class[0]);
        Object objInvoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        return objInvoke instanceof String ? (String) objInvoke : "pen_hovering";
    }
}
