package androidx.reflect.text;

import android.text.TextPaint;
import android.text.TextUtils;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslTextUtilsReflector {
    public static final Class mClass = TextUtils.class;

    private SeslTextUtilsReflector() {
    }

    public static char[] semGetPrefixCharForSpan(TextPaint textPaint, CharSequence charSequence, char[] cArr) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semGetPrefixCharForSpan", TextPaint.class, CharSequence.class, char[].class);
        if (declaredMethod == null) {
            return new char[0];
        }
        Object invoke = SeslBaseReflector.invoke(null, declaredMethod, textPaint, charSequence, cArr);
        if (invoke instanceof char[]) {
            return (char[]) invoke;
        }
        return null;
    }
}
