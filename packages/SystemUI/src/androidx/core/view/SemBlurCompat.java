package androidx.core.view;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.provider.SeslSettingsReflector$SeslSystemReflector;
import androidx.reflect.view.SeslViewReflector;
import com.android.systemui.util.SettingsHelper;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SemBlurCompat {
    public static final SemBlurCompat INSTANCE = new SemBlurCompat();

    private SemBlurCompat() {
    }

    public static final boolean setBlurEffectPreset(View view, int i, Integer num, Float f) {
        Constructor<?> constructor;
        Context context = view.getContext();
        INSTANCE.getClass();
        if (!(Settings.System.getString(context.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE) != null)) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslSettingsReflector$SeslSystemReflector.mClass, "hidden_SEM_ACCESSIBILITY_REDUCE_TRANSPARENCY", new Class[0]);
            Object obj = null;
            Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
            String str = invoke instanceof String ? (String) invoke : "not_supported";
            if (Intrinsics.areEqual(str, "not_supported") || Settings.System.getInt(context.getContentResolver(), str, 0) != 1) {
                try {
                    constructor = Class.forName("android.view.SemBlurInfo$Builder").getDeclaredConstructor(Integer.TYPE);
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    Log.e("SeslBaseReflector", "failed to get reflection - " + e);
                    constructor = null;
                }
                if (constructor != null) {
                    try {
                        obj = constructor.newInstance(0);
                    } catch (IllegalAccessException e2) {
                        Log.e("SeslSemBlurInfoRftr", "semCreateBlurBuilder IllegalAccessException", e2);
                    } catch (InstantiationException e3) {
                        Log.e("SeslSemBlurInfoRftr", "semCreateBlurBuilder InstantiationException", e3);
                    } catch (InvocationTargetException e4) {
                        Log.e("SeslSemBlurInfoRftr", "semCreateBlurBuilder InvocationTargetException", e4);
                    }
                }
                if (obj != null) {
                    Class cls = Integer.TYPE;
                    Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod("android.view.SemBlurInfo$Builder", "setColorCurvePreset", cls);
                    if (declaredMethod2 != null) {
                        declaredMethod2.setAccessible(true);
                        SeslBaseReflector.invoke(obj, declaredMethod2, Integer.valueOf(i));
                    }
                    if (num != null) {
                        int intValue = num.intValue();
                        Method declaredMethod3 = SeslBaseReflector.getDeclaredMethod("android.view.SemBlurInfo$Builder", "hidden_setBackgroundColor", cls);
                        if (declaredMethod3 != null) {
                            declaredMethod3.setAccessible(true);
                            SeslBaseReflector.invoke(obj, declaredMethod3, Integer.valueOf(intValue));
                        }
                    }
                    float floatValue = f.floatValue();
                    Method declaredMethod4 = SeslBaseReflector.getDeclaredMethod("android.view.SemBlurInfo$Builder", "hidden_setBackgroundCornerRadius", Float.TYPE);
                    if (declaredMethod4 != null) {
                        declaredMethod4.setAccessible(true);
                        SeslBaseReflector.invoke(obj, declaredMethod4, Float.valueOf(floatValue));
                    }
                    Method declaredMethod5 = SeslBaseReflector.getDeclaredMethod("android.view.SemBlurInfo$Builder", "hidden_build", new Class[0]);
                    if (declaredMethod5 != null) {
                        declaredMethod5.setAccessible(true);
                        Object invoke2 = SeslBaseReflector.invoke(obj, declaredMethod5, new Object[0]);
                        Class cls2 = SeslViewReflector.mClass;
                        try {
                            Method declaredMethod6 = SeslBaseReflector.getDeclaredMethod(SeslViewReflector.mClass, "hidden_semSetBlurInfo", Class.forName("android.view.SemBlurInfo"));
                            if (declaredMethod6 != null) {
                                SeslBaseReflector.invoke(view, declaredMethod6, invoke2);
                            }
                        } catch (ClassNotFoundException e5) {
                            Log.e("SeslViewReflector", "semSetBlurInfo ClassNotFoundException", e5);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
