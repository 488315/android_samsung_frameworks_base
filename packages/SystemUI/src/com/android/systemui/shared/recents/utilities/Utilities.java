package com.android.systemui.shared.recents.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.WindowManager;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class Utilities {
    public static boolean isLargeScreen(Context context) {
        return isLargeScreen(WindowManagerUtils.getWindowManager(context), context.getResources());
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0009, code lost:
    
        if (r3 != 3) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int updateNavbarFlagsFromIme(int r2, int r3, boolean r4, boolean r5) {
        /*
            r0 = 2
            if (r3 == 0) goto Lf
            r1 = 1
            if (r3 == r1) goto Lf
            if (r3 == r0) goto Lf
            r1 = 3
            if (r3 == r1) goto Lc
            goto L13
        Lc:
            r2 = r2 & (-2)
            goto L13
        Lf:
            if (r4 == 0) goto Lc
            r2 = r2 | 1
        L13:
            if (r4 == 0) goto L17
            r2 = r2 | r0
            goto L19
        L17:
            r2 = r2 & (-3)
        L19:
            if (r5 == 0) goto L20
            if (r4 == 0) goto L20
            r2 = r2 | 4
            return r2
        L20:
            r2 = r2 & (-5)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shared.recents.utilities.Utilities.updateNavbarFlagsFromIme(int, int, boolean, boolean):int");
    }

    public static boolean isLargeScreen(WindowManager windowManager, Resources resources) {
        Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
        return ((float) Math.min(bounds.width(), bounds.height())) / (((float) resources.getConfiguration().densityDpi) / 160.0f) >= 600.0f;
    }
}
