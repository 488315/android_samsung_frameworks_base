package com.android.systemui.shade;

public interface QuickSettingsController {
    void closeQs();

    void closeQsCustomizer();

    boolean getExpanded$1();

    SecQuickSettingsControllerImpl getSecQuickSettingsControllerImpl$1();

    boolean isCustomizing();
}
