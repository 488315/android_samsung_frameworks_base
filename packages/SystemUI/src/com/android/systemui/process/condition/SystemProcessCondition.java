package com.android.systemui.process.condition;

import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.shared.condition.Condition;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SystemProcessCondition extends Condition {
    public final ProcessWrapper mProcessWrapper;

    public SystemProcessCondition(CoroutineScope coroutineScope, ProcessWrapper processWrapper) {
        super(coroutineScope);
        this.mProcessWrapper = processWrapper;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void start() {
        this.mProcessWrapper.getClass();
        updateCondition(ProcessWrapper.isSystemUser());
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
    }
}
