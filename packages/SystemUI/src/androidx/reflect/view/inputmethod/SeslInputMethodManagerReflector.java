package androidx.reflect.view.inputmethod;

import android.view.inputmethod.InputMethodManager;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslInputMethodManagerReflector {
    public static final Class mClass = InputMethodManager.class;

    private SeslInputMethodManagerReflector() {
    }

    public static int isAccessoryKeyboardState(InputMethodManager inputMethodManager) {
        Method method = SeslBaseReflector.getMethod(mClass, "isAccessoryKeyboardState", new Class[0]);
        if (method != null) {
            Object invoke = SeslBaseReflector.invoke(inputMethodManager, method, new Object[0]);
            if (invoke instanceof Integer) {
                return ((Integer) invoke).intValue();
            }
        }
        return 0;
    }
}
