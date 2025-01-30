package com.android.server.policy.globalactions.presentation;

import com.android.server.policy.WindowManagerPolicy;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;

/* loaded from: classes3.dex */
public class PolicyGlobalActionsManager implements SamsungGlobalActionsManager {
    public final WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;

    public boolean isFOTAAvailableForGlobalActions() {
        return false;
    }

    public void onGlobalActionsHidden() {
    }

    public void onGlobalActionsShown() {
    }

    public PolicyGlobalActionsManager(WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
        this.mWindowManagerFuncs = windowManagerFuncs;
    }

    public void shutdown() {
        this.mWindowManagerFuncs.shutdown(false);
    }

    public void reboot(boolean z) {
        if (z) {
            this.mWindowManagerFuncs.rebootSafeMode(false);
        } else {
            this.mWindowManagerFuncs.reboot(false);
        }
    }
}
