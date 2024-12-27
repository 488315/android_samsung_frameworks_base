package com.android.systemui.dreams.conditions;

import android.app.DreamManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.shared.condition.Condition;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
