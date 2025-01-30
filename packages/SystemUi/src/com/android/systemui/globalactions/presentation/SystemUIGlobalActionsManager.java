package com.android.systemui.globalactions.presentation;

import com.android.systemui.plugins.GlobalActions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
