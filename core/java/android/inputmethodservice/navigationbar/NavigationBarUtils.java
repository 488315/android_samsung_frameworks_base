package android.inputmethodservice.navigationbar;

import android.content.res.Resources;
import android.util.TypedValue;

final class NavigationBarUtils {
    private NavigationBarUtils() {}

    static int dpToPx(float dpValue, Resources res) {
        return (int) TypedValue.applyDimension(1, dpValue, res.getDisplayMetrics());
    }
}
