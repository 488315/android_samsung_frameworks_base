package com.android.systemui.shared.recents.utilities;

import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Utilities {
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0009, code lost:
    
        if (r3 != 3) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int calculateBackDispositionHints(int i, int i2, boolean z, boolean z2) {
        if (i2 == 0 || i2 == 1 || i2 == 2) {
            if (z) {
                i |= 1;
            }
            i &= -2;
        }
        int i3 = z ? i | 2 : i & (-3);
        return z2 ? i3 | 4 : i3 & (-5);
    }

    public static boolean isLargeScreen(Context context) {
        Rect bounds = ((WindowManager) context.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
        return ((float) Math.min(bounds.width(), bounds.height())) / (((float) context.getResources().getConfiguration().densityDpi) / 160.0f) >= 600.0f;
    }
}
