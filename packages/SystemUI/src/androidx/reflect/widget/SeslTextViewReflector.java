package androidx.reflect.widget;

import android.widget.TextView;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslTextViewReflector {
    public static final Class mClass = TextView.class;

    private SeslTextViewReflector() {
    }

    public static void semSetButtonShapeEnabled(TextView textView, boolean z) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semSetButtonShapeEnabled", Boolean.TYPE);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(textView, declaredMethod, Boolean.valueOf(z));
        }
    }

    public static void semSetButtonShapeEnabled(TextView textView, boolean z, int i) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semSetButtonShapeEnabled", Boolean.TYPE, Integer.TYPE);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(textView, declaredMethod, Boolean.valueOf(z), Integer.valueOf(i));
        }
    }
}
