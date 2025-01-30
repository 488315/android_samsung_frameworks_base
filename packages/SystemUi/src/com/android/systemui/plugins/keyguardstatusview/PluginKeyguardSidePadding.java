package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
