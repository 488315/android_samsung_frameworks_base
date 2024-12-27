package com.android.systemui.dreams.callbacks;

import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.shared.condition.Monitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AssistantAttentionCallback implements Monitor.Callback {
    public final DreamOverlayStateController mStateController;

    public AssistantAttentionCallback(DreamOverlayStateController dreamOverlayStateController) {
        this.mStateController = dreamOverlayStateController;
    }

    @Override // com.android.systemui.shared.condition.Monitor.Callback
    public final void onConditionsChanged(boolean z) {
        if (Log.isLoggable("AssistAttentionCallback", 3)) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onConditionChanged:", "AssistAttentionCallback", z);
        }
        this.mStateController.setHasAssistantAttention(z);
    }
}
