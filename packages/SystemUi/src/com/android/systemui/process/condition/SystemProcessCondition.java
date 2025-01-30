package com.android.systemui.process.condition;

import android.os.Process;
import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.shared.condition.Condition;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        updateCondition(Process.myUserHandle().isSystem());
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
    }
}
