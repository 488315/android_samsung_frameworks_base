package androidx.reflect.provider;

import android.provider.Settings;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslSettingsReflector$SeslSystemReflector {
    public static final Class mClass = Settings.System.class;

    private SeslSettingsReflector$SeslSystemReflector() {
    }

    public static String getField_SEM_PEN_HOVERING() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_SEM_PEN_HOVERING", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        return invoke instanceof String ? (String) invoke : "pen_hovering";
    }
}
