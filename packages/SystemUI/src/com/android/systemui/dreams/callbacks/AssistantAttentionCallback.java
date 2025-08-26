package com.android.systemui.dreams.callbacks;

import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.dreams.DreamLogger;
import com.android.systemui.dreams.DreamLogger$$ExternalSyntheticLambda0;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shared.condition.Monitor;

/* loaded from: classes2.dex */
public class AssistantAttentionCallback implements Monitor.Callback {
    public final DreamOverlayStateController mStateController;

    public AssistantAttentionCallback(DreamOverlayStateController dreamOverlayStateController) {
        this.mStateController = dreamOverlayStateController;
    }

    @Override // com.android.systemui.shared.condition.Monitor.Callback
    public final void onConditionsChanged(boolean z) {
        if (Log.isLoggable("AssistAttentionCallback", 3)) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("onConditionChanged:", "AssistAttentionCallback", z);
        }
        DreamOverlayStateController dreamOverlayStateController = this.mStateController;
        DreamLogger dreamLogger = dreamOverlayStateController.mLogger;
        dreamLogger.getClass();
        LogMessage logMessageObtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, new DreamLogger$$ExternalSyntheticLambda0(7), null);
        logMessageObtain.setBool1(z);
        dreamLogger.getBuffer().commit(logMessageObtain);
        dreamOverlayStateController.modifyState(z ? 2 : 1, 16);
    }
}
