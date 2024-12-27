package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.qs.QS;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class StatusBarLocation {
    public static final /* synthetic */ StatusBarLocation[] $VALUES;
    public static final StatusBarLocation AOD;
    public static final StatusBarLocation HOME;
    public static final StatusBarLocation KEYGUARD;
    public static final StatusBarLocation QS;
    public static final StatusBarLocation SHADE_CARRIER_GROUP;
    public static final StatusBarLocation SUB_SCREEN_QUICK_PANEL;

    static {
        StatusBarLocation statusBarLocation = new StatusBarLocation("HOME", 0);
        HOME = statusBarLocation;
        StatusBarLocation statusBarLocation2 = new StatusBarLocation("KEYGUARD", 1);
        KEYGUARD = statusBarLocation2;
        StatusBarLocation statusBarLocation3 = new StatusBarLocation(QS.TAG, 2);
        QS = statusBarLocation3;
        StatusBarLocation statusBarLocation4 = new StatusBarLocation("SHADE_CARRIER_GROUP", 3);
        SHADE_CARRIER_GROUP = statusBarLocation4;
        StatusBarLocation statusBarLocation5 = new StatusBarLocation("AOD", 4);
        AOD = statusBarLocation5;
        StatusBarLocation statusBarLocation6 = new StatusBarLocation("SUB_SCREEN_QUICK_PANEL", 5);
        SUB_SCREEN_QUICK_PANEL = statusBarLocation6;
        StatusBarLocation[] statusBarLocationArr = {statusBarLocation, statusBarLocation2, statusBarLocation3, statusBarLocation4, statusBarLocation5, statusBarLocation6};
        $VALUES = statusBarLocationArr;
        EnumEntriesKt.enumEntries(statusBarLocationArr);
    }

    private StatusBarLocation(String str, int i) {
    }

    public static StatusBarLocation valueOf(String str) {
        return (StatusBarLocation) Enum.valueOf(StatusBarLocation.class, str);
    }

    public static StatusBarLocation[] values() {
        return (StatusBarLocation[]) $VALUES.clone();
    }
}
