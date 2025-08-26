package androidx.reflect.view.inputmethod;

import android.view.inputmethod.InputMethodManager;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SeslInputMethodManagerReflector {
    public static final Class mClass = InputMethodManager.class;

    private SeslInputMethodManagerReflector() {
    }

    public static int isAccessoryKeyboardState(InputMethodManager inputMethodManager) {
        Method method = SeslBaseReflector.getMethod(mClass, "isAccessoryKeyboardState", new Class[0]);
        if (method != null) {
            Object objInvoke = SeslBaseReflector.invoke(inputMethodManager, method, new Object[0]);
            if (objInvoke instanceof Integer) {
                return ((Integer) objInvoke).intValue();
            }
        }
        return 0;
    }
}
