package androidx.reflect.content;

import android.content.Context;
import android.os.UserHandle;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslContextReflector {
    public static final Class mClass = Context.class;

    private SeslContextReflector() {
    }

    public static Context createPackageContextAsUser(Context context, String str, UserHandle userHandle) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_createPackageContextAsUser", String.class, Integer.TYPE, UserHandle.class);
        if (declaredMethod == null) {
            return null;
        }
        Object invoke = SeslBaseReflector.invoke(context, declaredMethod, str, 0, userHandle);
        if (invoke instanceof Context) {
            return (Context) invoke;
        }
        return null;
    }
}
