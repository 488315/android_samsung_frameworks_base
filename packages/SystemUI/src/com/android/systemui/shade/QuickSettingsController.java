package com.android.systemui.shade;

/* loaded from: classes3.dex */
public interface QuickSettingsController {
    void closeQs();

    void closeQsCustomizer();

    boolean getExpanded();

    SecQuickSettingsControllerImpl getSecQuickSettingsControllerImpl$1();

    boolean isCustomizing();

    boolean shouldQuickSettingsIntercept(float f, float f2, float f3);
}
