package com.android.systemui.globalactions.presentation;

import com.android.systemui.plugins.GlobalActions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
