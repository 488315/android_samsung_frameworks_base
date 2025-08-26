package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import java.util.ArrayList;

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
