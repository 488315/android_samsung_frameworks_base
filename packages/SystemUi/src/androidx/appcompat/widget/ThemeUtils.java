package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import androidx.appcompat.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ThemeUtils {
    static {
        new ThreadLocal();
    }

    private ThemeUtils() {
    }

    public static void checkAppCompatTheme(Context context, View view) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R$styleable.AppCompatTheme);
        try {
            if (!obtainStyledAttributes.hasValue(145)) {
                Log.e("ThemeUtils", "View " + view.getClass() + " is an AppCompat widget that can only be used with a Theme.AppCompat theme (or descendant).");
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
