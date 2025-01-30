package com.android.systemui.biometrics;

import android.util.Log;
import com.android.systemui.shade.ShadeExpansionStateManager;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthDialogPanelInteractionDetector {
    public Action action;
    public final Executor mainExecutor;
    public int panelState = -1;
    public final ShadeExpansionStateManager shadeExpansionStateManager;

    public AuthDialogPanelInteractionDetector(ShadeExpansionStateManager shadeExpansionStateManager, Executor executor) {
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.mainExecutor = executor;
    }

    public final void disable() {
        if (this.action != null) {
            Log.i("AuthDialogPanelInteractionDetector", "Disable dectector");
            this.action = null;
            this.panelState = -1;
            AuthDialogPanelInteractionDetector$disable$1 authDialogPanelInteractionDetector$disable$1 = new AuthDialogPanelInteractionDetector$disable$1(this);
            ShadeExpansionStateManager shadeExpansionStateManager = this.shadeExpansionStateManager;
            shadeExpansionStateManager.stateListeners.remove(authDialogPanelInteractionDetector$disable$1);
            shadeExpansionStateManager.expansionListeners.remove(new AuthDialogPanelInteractionDetector$disable$2(this));
        }
    }
}
