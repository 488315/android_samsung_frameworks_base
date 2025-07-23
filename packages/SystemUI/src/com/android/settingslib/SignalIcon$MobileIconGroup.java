package com.android.settingslib;

import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SignalIcon$MobileIconGroup extends SignalIcon$IconGroup {
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
