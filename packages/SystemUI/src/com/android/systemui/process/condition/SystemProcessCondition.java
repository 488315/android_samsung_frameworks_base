package com.android.systemui.process.condition;

import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.shared.condition.Condition;
import kotlinx.coroutines.CoroutineScope;

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
