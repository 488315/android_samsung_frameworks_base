package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import androidx.appcompat.R$styleable;

/* loaded from: classes.dex */
public class ThemeUtils {
    static {
        new ThreadLocal();
    }

    private ThemeUtils() {
    }

    public static void checkAppCompatTheme(Context context, View view) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R$styleable.AppCompatTheme);
        try {
            if (!typedArrayObtainStyledAttributes.hasValue(145)) {
                Log.e("ThemeUtils", "View " + view.getClass() + " is an AppCompat widget that can only be used with a Theme.AppCompat theme (or descendant).");
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }
}
