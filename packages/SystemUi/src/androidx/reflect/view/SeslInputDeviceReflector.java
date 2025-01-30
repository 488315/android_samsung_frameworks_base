package androidx.reflect.view;

import android.view.InputDevice;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslInputDeviceReflector {
    public static final Class mClass = InputDevice.class;

    private SeslInputDeviceReflector() {
    }

    public static void semSetPointerType(InputDevice inputDevice, int i) {
        Method declaredMethod;
        if (inputDevice == null || (declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_setPointerType", Integer.TYPE)) == null) {
            return;
        }
        SeslBaseReflector.invoke(inputDevice, declaredMethod, Integer.valueOf(i));
    }
}
