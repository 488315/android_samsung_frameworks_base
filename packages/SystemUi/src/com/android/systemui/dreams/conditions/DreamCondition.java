package com.android.systemui.dreams.conditions;

import android.app.DreamManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.shared.condition.Condition;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamCondition extends Condition {
    public final DreamManager mDreamManager;
    public final KeyguardUpdateMonitorCallback mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;

    public DreamCondition(CoroutineScope coroutineScope, DreamManager dreamManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        super(coroutineScope);
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.dreams.conditions.DreamCondition.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDreamingStateChanged(boolean z) {
                DreamCondition.this.updateCondition(z);
            }
        };
        this.mDreamManager = dreamManager;
        this.mUpdateMonitor = keyguardUpdateMonitor;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void start() {
        this.mUpdateMonitor.registerCallback(this.mUpdateCallback);
        updateCondition(this.mDreamManager.isDreaming());
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
        this.mUpdateMonitor.removeCallback(this.mUpdateCallback);
    }
}
