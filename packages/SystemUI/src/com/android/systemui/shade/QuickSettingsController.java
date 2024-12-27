package com.android.systemui.shade;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface QuickSettingsController {
    void closeQs();

    void closeQsCustomizer();

    boolean getExpanded$1();

    SecQuickSettingsControllerImpl getSecQuickSettingsControllerImpl$1();

    boolean isCustomizing();
}
