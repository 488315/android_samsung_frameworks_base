package androidx.reflect.os;

import android.os.UserHandle;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslUserHandleReflector {
    public static final Class mClass = UserHandle.class;

    private SeslUserHandleReflector() {
    }

    public static int myUserId() {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_myUserId", new Class[0]);
        if (declaredMethod != null) {
            Object invoke = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            if (invoke instanceof Integer) {
                return ((Integer) invoke).intValue();
            }
        }
        return 0;
    }

    /* renamed from: of */
    public static UserHandle m48of(int i) {
        Method method = SeslBaseReflector.getMethod(mClass, "of", Integer.TYPE);
        if (method != null) {
            Object invoke = SeslBaseReflector.invoke(null, method, Integer.valueOf(i));
            if (invoke instanceof UserHandle) {
                return (UserHandle) invoke;
            }
        }
        return null;
    }
}
