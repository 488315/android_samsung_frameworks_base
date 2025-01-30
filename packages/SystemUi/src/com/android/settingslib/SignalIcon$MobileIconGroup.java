package com.android.settingslib;

import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SignalIcon$MobileIconGroup extends SignalIcon$IconGroup {
    public final int dataContentDescription;
    public final int dataType;

    public SignalIcon$MobileIconGroup(String str, int i, int i2) {
        super(str, null, null, AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH, 0, 0, 0, 0, R.string.accessibility_no_phone, TelephonyIcons.MOBILE_DATA_ACTIVITY_ICONS);
        this.dataContentDescription = i;
        this.dataType = i2;
    }

    public SignalIcon$MobileIconGroup(String str, int i, int i2, int[] iArr) {
        this(str, i, i2);
        this.activityIcons = iArr;
    }
}
