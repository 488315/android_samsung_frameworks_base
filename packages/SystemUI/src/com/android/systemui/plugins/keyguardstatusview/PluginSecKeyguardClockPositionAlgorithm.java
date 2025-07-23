package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@SupportVersionChecker
/* loaded from: classes2.dex */
public interface PluginSecKeyguardClockPositionAlgorithm {
    int getBottomMarginY();

    float getMinStackScrollerPadding();

    boolean isPanelExpanded();

    @VersionCheck(version = 1005)
    void loadDimens();

    void run(ArrayList<Object> arrayList);

    void setup(float f, float f2, boolean z, int i, boolean z2);
}
