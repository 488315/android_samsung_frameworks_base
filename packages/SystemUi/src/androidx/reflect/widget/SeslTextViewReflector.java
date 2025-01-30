package androidx.reflect.widget;

import android.widget.TextView;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslTextViewReflector {
    public static final Class mClass = TextView.class;

    private SeslTextViewReflector() {
    }

    public static void semSetButtonShapeEnabled(TextView textView, boolean z) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semSetButtonShapeEnabled", Boolean.TYPE);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(textView, declaredMethod, Boolean.valueOf(z));
        }
    }

    public static void semSetButtonShapeEnabled(int i, TextView textView, boolean z) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semSetButtonShapeEnabled", Boolean.TYPE, Integer.TYPE);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(textView, declaredMethod, Boolean.valueOf(z), Integer.valueOf(i));
        }
    }
}
