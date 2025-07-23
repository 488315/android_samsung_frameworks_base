package com.android.systemui.statusbar.layout;

import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class StatusBarContentInsetsProviderKt {
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0194, code lost:
    
        r1 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01a5, code lost:
    
        if (r5.right >= r9) goto L94;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final android.graphics.Rect calculateInsetsForRotationWithRotatedResources(int r17, int r18, com.android.systemui.SysUICutoutInformation r19, android.graphics.Rect r20, int r21, int r22, int r23, boolean r24, int r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 475
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderKt.calculateInsetsForRotationWithRotatedResources(int, int, com.android.systemui.SysUICutoutInformation, android.graphics.Rect, int, int, int, boolean, int, int, int):android.graphics.Rect");
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
