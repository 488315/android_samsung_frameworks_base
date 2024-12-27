package com.android.systemui.dreams;

import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.callbacks.AssistantAttentionCallback;
import com.android.systemui.dreams.conditions.AssistantAttentionCondition;
import com.android.systemui.shared.condition.Monitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AssistantAttentionMonitor implements CoreStartable {
    public final AssistantAttentionCondition mAssistantAttentionCondition;
    public final AssistantAttentionCallback mCallback;
    public final Monitor mConditionMonitor;

    public AssistantAttentionMonitor(Monitor monitor, AssistantAttentionCondition assistantAttentionCondition, AssistantAttentionCallback assistantAttentionCallback) {
        this.mConditionMonitor = monitor;
        this.mAssistantAttentionCondition = assistantAttentionCondition;
        this.mCallback = assistantAttentionCallback;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (Log.isLoggable("AssistAttentionMonitor", 3)) {
            Log.d("AssistAttentionMonitor", "started");
        }
        Monitor.Subscription.Builder builder = new Monitor.Subscription.Builder(this.mCallback);
        builder.mConditions.add(this.mAssistantAttentionCondition);
        Monitor.Subscription build = builder.build();
        Monitor monitor = this.mConditionMonitor;
        monitor.addSubscription(build, monitor.mPreconditions);
    }
}
