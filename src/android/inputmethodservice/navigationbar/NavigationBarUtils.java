package android.inputmethodservice.navigationbar;

import android.content.res.Resources;
import android.util.TypedValue;

/* loaded from: classes2.dex */
final class NavigationBarUtils {
    private NavigationBarUtils() {
    }

    static int dpToPx(float f, Resources resources) {
        return (int) TypedValue.applyDimension(1, f, resources.getDisplayMetrics());
    }
}
