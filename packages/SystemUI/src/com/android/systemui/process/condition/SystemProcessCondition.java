package com.android.systemui.process.condition;

import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.shared.condition.Condition;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SystemProcessCondition extends Condition {
    public final ProcessWrapper processWrapper;

    public SystemProcessCondition(CoroutineScope coroutineScope, ProcessWrapper processWrapper) {
        super(coroutineScope, null, false, 6, null);
        this.processWrapper = processWrapper;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final int getStartStrategy() {
        return 0;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final Object start(Continuation continuation) {
        this.processWrapper.getClass();
        updateCondition(ProcessWrapper.isSystemUser());
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
    }
}
