package com.android.systemui.globalactions.presentation;

import com.android.systemui.plugins.GlobalActions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;

public final class SystemUIGlobalActionsManager implements SamsungGlobalActionsManager {
    public final GlobalActions.GlobalActionsManager mWindowManagerFuncs;

    public SystemUIGlobalActionsManager(GlobalActions.GlobalActionsManager globalActionsManager) {
        this.mWindowManagerFuncs = globalActionsManager;
    }

    public final boolean isFOTAAvailableForGlobalActions() {
        return this.mWindowManagerFuncs.isFOTAAvailableForGlobalActions();
    }

    public final void onGlobalActionsHidden() {
        this.mWindowManagerFuncs.onGlobalActionsHidden();
    }

    public final void onGlobalActionsShown() {
        this.mWindowManagerFuncs.onGlobalActionsShown();
    }

    public final void reboot(boolean z) {
        this.mWindowManagerFuncs.reboot(z);
    }

    public final void shutdown() {
        this.mWindowManagerFuncs.shutdown();
    }
}
