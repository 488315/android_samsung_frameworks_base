package androidx.reflect.os;

import android.os.Build;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public class SeslBuildReflector$SeslVersionReflector {
    public static final Class mClass = Build.VERSION.class;

    private SeslBuildReflector$SeslVersionReflector() {
    }

    public static int getField_SEM_PLATFORM_INT() {
        Field declaredField = SeslBaseReflector.getDeclaredField(mClass, "SEM_PLATFORM_INT");
        if (declaredField == null || !(SeslBaseReflector.get(declaredField, null) instanceof Integer)) {
            return -1;
        }
        return ((Integer) SeslBaseReflector.get(declaredField, null)).intValue();
    }
}
