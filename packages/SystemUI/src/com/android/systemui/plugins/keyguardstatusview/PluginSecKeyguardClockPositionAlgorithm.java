package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import java.util.ArrayList;
import java.util.function.Supplier;

@SupportVersionChecker
public interface PluginSecKeyguardClockPositionAlgorithm {
    public static final int NOTIFICATION_CARD = 0;
    public static final int NOTIFICATION_ICON = 1;
    public static final int NOTIFICATION_TYPO = 2;

    int getBottomMarginY();

    float getMinStackScrollerPadding();

    boolean isPanelExpanded();

    @VersionCheck(version = 1005)
    void loadDimens();

    void run(ArrayList<Object> arrayList);

    void setup(int i, int i2, int i3, float f, int i4, int i5, int i6, boolean z, boolean z2, float f2, float f3, boolean z3, int i7, int i8, int i9, Supplier<Float> supplier);
}
