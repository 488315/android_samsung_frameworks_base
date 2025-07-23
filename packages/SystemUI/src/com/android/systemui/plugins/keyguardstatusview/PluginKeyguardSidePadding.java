package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@SupportVersionChecker
/* loaded from: classes2.dex */
public interface PluginKeyguardSidePadding {
    int getClockSidePadding();

    @VersionCheck(version = 1023)
    int getClockSidePadding(boolean z);

    int getShortCutSidePadding();

    @VersionCheck(version = 1022)
    int getShortCutSidePadding(boolean z);

    int getSidePaddingWhenIndisplayFP();

    int getTabletClockSidePadding(int i, int i2, boolean z);

    boolean needToSidePaddingForClock();
}
