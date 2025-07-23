package android.util;

import android.content.res.Resources;
import android.view.Display;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class DisplayUtils {
    public static int getDisplayUniqueIdConfigIndex(Resources resources, String str) {
        if (str == null || str.isEmpty()) {
            return -1;
        }
        String[] stringArray = resources.getStringArray(R.array.config_displayUniqueIdArray);
        int length = stringArray.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(stringArray[i])) {
                return i;
            }
        }
        return -1;
    }

    public static Display.Mode getMaximumResolutionDisplayMode(Display.Mode[] modeArr) {
        Display.Mode mode = null;
        if (modeArr != null && modeArr.length != 0) {
            int i = 0;
            for (Display.Mode mode2 : modeArr) {
                if (mode2.getPhysicalWidth() > i) {
                    i = mode2.getPhysicalWidth();
                    mode = mode2;
                }
            }
        }
        return mode;
    }

    public static float getPhysicalPixelDisplaySizeRatio(int i, int i2, int i3, int i4) {
        if (i == i3 && i2 == i4) {
            return 1.0f;
        }
        return Math.min(i3 / i, i4 / i2);
    }
}
