package com.android.systemui.statusbar.phone;

import android.graphics.Rect;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class StatusBarContentInsetsProviderKt {
    public static final Rect getPrivacyChipBoundingRectForInsets(Rect rect, int i, int i2, boolean z) {
        if (z) {
            int i3 = rect.left;
            return new Rect(i3 - i, rect.top, i3 + i2, rect.bottom);
        }
        int i4 = rect.right;
        return new Rect(i4 - i2, rect.top, i4 + i, rect.bottom);
    }
}
