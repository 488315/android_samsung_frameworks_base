package com.android.systemui.dreams.callbacks;

import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.shared.condition.Monitor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AssistantAttentionCallback implements Monitor.Callback {
    public final DreamOverlayStateController mStateController;

    public AssistantAttentionCallback(DreamOverlayStateController dreamOverlayStateController) {
        this.mStateController = dreamOverlayStateController;
    }

    @Override // com.android.systemui.shared.condition.Monitor.Callback
    public final void onConditionsChanged(boolean z) {
        if (Log.isLoggable("AssistAttentionCallback", 3)) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onConditionChanged:", z, "AssistAttentionCallback");
        }
        DreamOverlayStateController dreamOverlayStateController = this.mStateController;
        dreamOverlayStateController.getClass();
        dreamOverlayStateController.modifyState(z ? 2 : 1, 16);
    }
}
