package androidx.reflect.view;

import android.graphics.Rect;
import android.view.PointerIcon;
import android.view.View;
import androidx.appcompat.widget.SeslAbsSeekBar;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SeslViewReflector {
    public static final Class mClass = View.class;

    private SeslViewReflector() {
    }

    public static int getField_mPaddingLeft(View view) {
        Field declaredField = SeslBaseReflector.getDeclaredField(mClass, "mPaddingLeft");
        if (declaredField == null) {
            return 0;
        }
        Object obj = SeslBaseReflector.get(declaredField, view);
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 0;
    }

    public static int getField_mPaddingRight(View view) {
        Field declaredField = SeslBaseReflector.getDeclaredField(mClass, "mPaddingRight");
        if (declaredField == null) {
            return 0;
        }
        Object obj = SeslBaseReflector.get(declaredField, view);
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 0;
    }

    public static boolean isVisibleToUser(View view) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "isVisibleToUser", Rect.class);
        if (declaredMethod != null) {
            Object objInvoke = SeslBaseReflector.invoke(view, declaredMethod, null);
            if (objInvoke instanceof Boolean) {
                return ((Boolean) objInvoke).booleanValue();
            }
        }
        return false;
    }

    public static Object semGetHoverPopup(SeslAbsSeekBar seslAbsSeekBar) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semGetHoverPopup", Boolean.TYPE);
        if (declaredMethod != null) {
            return SeslBaseReflector.invoke(seslAbsSeekBar, declaredMethod, Boolean.TRUE);
        }
        return null;
    }

    public static void semSetHoverPopupType(View view, int i) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semSetHoverPopupType", Integer.TYPE);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(view, declaredMethod, Integer.valueOf(i));
        }
    }

    public static void semSetPointerIcon(View view, int i, PointerIcon pointerIcon) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semSetPointerIcon", Integer.TYPE, PointerIcon.class);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(view, declaredMethod, Integer.valueOf(i), pointerIcon);
        }
    }

    public static void setFrameContentVelocity(float f, View view) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "setFrameContentVelocity", Float.TYPE);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(view, declaredMethod, Float.valueOf(f));
        }
    }
}
