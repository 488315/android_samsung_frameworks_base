package androidx.reflect.view.inputmethod;

import android.view.inputmethod.InputMethodManager;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslInputMethodManagerReflector {
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
