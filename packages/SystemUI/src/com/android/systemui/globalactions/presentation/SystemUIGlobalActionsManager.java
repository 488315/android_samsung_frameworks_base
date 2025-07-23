package com.android.systemui.globalactions.presentation;

import com.android.systemui.plugins.GlobalActions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SystemUIGlobalActionsManager implements SamsungGlobalActionsManager {
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
