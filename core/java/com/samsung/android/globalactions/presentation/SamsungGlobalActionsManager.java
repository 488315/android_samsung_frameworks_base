package com.samsung.android.globalactions.presentation;

public interface SamsungGlobalActionsManager {
    boolean isFOTAAvailableForGlobalActions();

    void onGlobalActionsHidden();

    void onGlobalActionsShown();

    void reboot(boolean z);

    void shutdown();
}
