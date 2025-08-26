package com.android.systemui.shared.recents.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.WindowManager;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;

/* loaded from: classes3.dex */
public class Utilities {
    public static boolean isLargeScreen(Context context) {
        return isLargeScreen(WindowManagerUtils.getWindowManager(context), context.getResources());
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x000c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int updateNavbarFlagsFromIme(int i, int i2, boolean z, boolean z2) {
        if (i2 == 0 || i2 == 1 || i2 == 2) {
            i = z ? i | 1 : i & (-2);
        } else if (i2 == 3) {
        }
        int i3 = z ? i | 2 : i & (-3);
        return (z2 && z) ? i3 | 4 : i3 & (-5);
    }

    public static boolean isLargeScreen(WindowManager windowManager, Resources resources) {
        Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
        return ((float) Math.min(bounds.width(), bounds.height())) / (((float) resources.getConfiguration().densityDpi) / 160.0f) >= 600.0f;
    }
}
