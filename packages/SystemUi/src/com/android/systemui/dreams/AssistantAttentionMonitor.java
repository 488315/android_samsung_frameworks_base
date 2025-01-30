package com.android.systemui.dreams;

import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.callbacks.AssistantAttentionCallback;
import com.android.systemui.dreams.conditions.AssistantAttentionCondition;
import com.android.systemui.shared.condition.Monitor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
