package androidx.appcompat.util;

import android.content.Context;
import android.util.TypedValue;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class SeslMisc {
    public static boolean isLightTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        return typedValue.data != 0;
    }
}
