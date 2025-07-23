package androidx.reflect.provider;

import android.provider.Settings;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslSettingsReflector$SeslSystemReflector {
    public static final Class mClass = Settings.System.class;

    private SeslSettingsReflector$SeslSystemReflector() {
    }

    public static String getField_SEM_PEN_HOVERING() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_SEM_PEN_HOVERING", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        return invoke instanceof String ? (String) invoke : "pen_hovering";
    }
}
