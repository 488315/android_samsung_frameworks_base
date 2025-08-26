package com.android.systemui.dreams.conditions;

import android.app.DreamManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.shared.condition.Condition;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class DreamCondition extends Condition {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DreamManager _dreamManager;
    public final DreamCondition$_updateCallback$1 _updateCallback;
    public final KeyguardUpdateMonitor _updateMonitor;

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.dreams.conditions.DreamCondition$_updateCallback$1] */
    public DreamCondition(CoroutineScope coroutineScope, DreamManager dreamManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        super(coroutineScope, null, false, 6, null);
        this._dreamManager = dreamManager;
        this._updateMonitor = keyguardUpdateMonitor;
        this._updateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.dreams.conditions.DreamCondition$_updateCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDreamingStateChanged(boolean z) {
                int i = DreamCondition.$r8$clinit;
                this.this$0.updateCondition(z);
            }
        };
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final int getStartStrategy() {
        return 0;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final Object start(Continuation continuation) {
        this._updateMonitor.registerCallback(this._updateCallback);
        updateCondition(this._dreamManager.isDreaming());
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
        this._updateMonitor.removeCallback(this._updateCallback);
    }
}
