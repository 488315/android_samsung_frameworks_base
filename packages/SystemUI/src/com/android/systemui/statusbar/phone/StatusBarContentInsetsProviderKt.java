package com.android.systemui.statusbar.phone;

import android.graphics.Rect;

public abstract class StatusBarContentInsetsProviderKt {
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0192, code lost:
    
        r1 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01a3, code lost:
    
        if (r15.right >= r9) goto L95;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final android.graphics.Rect calculateInsetsForRotationWithRotatedResources(int r16, int r17, com.android.systemui.SysUICutoutInformation r18, android.graphics.Rect r19, int r20, int r21, int r22, boolean r23, int r24, int r25, int r26) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarContentInsetsProviderKt.calculateInsetsForRotationWithRotatedResources(int, int, com.android.systemui.SysUICutoutInformation, android.graphics.Rect, int, int, int, boolean, int, int, int):android.graphics.Rect");
    }

    public static final Rect getPrivacyChipBoundingRectForInsets(Rect rect, int i, int i2, boolean z) {
        if (z) {
            int i3 = rect.left;
            return new Rect(i3 - i, rect.top, i3 + i2, rect.bottom);
        }
        int i4 = rect.right;
        return new Rect(i4 - i2, rect.top, i4 + i, rect.bottom);
    }
}
