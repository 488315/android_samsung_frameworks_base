package com.android.server.policy.globalactions.presentation;

import com.android.server.policy.WindowManagerPolicy;

import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PolicyGlobalActionsManager implements SamsungGlobalActionsManager {
    public final WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;

    public PolicyGlobalActionsManager(WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
        this.mWindowManagerFuncs = windowManagerFuncs;
    }

    public final boolean isFOTAAvailableForGlobalActions() {
        return false;
    }

    public final void onGlobalActionsHidden() {}

    public final void onGlobalActionsShown() {}

    public final void reboot(boolean z) {
        if (z) {
            this.mWindowManagerFuncs.rebootSafeMode(false);
        } else {
            this.mWindowManagerFuncs.reboot(false);
        }
    }

    public final void shutdown() {
        this.mWindowManagerFuncs.shutdown(false);
    }
}
