package com.android.systemui.util.condition;

import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.shared.condition.Monitor$$ExternalSyntheticLambda2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ConditionalCoreStartable$$ExternalSyntheticLambda0 implements Monitor.Callback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ConditionalCoreStartable f$0;

    public /* synthetic */ ConditionalCoreStartable$$ExternalSyntheticLambda0(ConditionalCoreStartable conditionalCoreStartable, int i) {
        this.$r8$classId = i;
        this.f$0 = conditionalCoreStartable;
    }

    @Override // com.android.systemui.shared.condition.Monitor.Callback
    public final void onConditionsChanged(boolean z) {
        int i = this.$r8$classId;
        ConditionalCoreStartable conditionalCoreStartable = this.f$0;
        switch (i) {
            case 0:
                if (!z) {
                    conditionalCoreStartable.getClass();
                    break;
                } else {
                    Monitor monitor = conditionalCoreStartable.mMonitor;
                    Monitor.Subscription.Token token = conditionalCoreStartable.mBootCompletedToken;
                    monitor.getClass();
                    monitor.mExecutor.execute(new Monitor$$ExternalSyntheticLambda2(0, monitor, token));
                    conditionalCoreStartable.mBootCompletedToken = null;
                    break;
                }
            default:
                if (!z) {
                    conditionalCoreStartable.getClass();
                    break;
                } else {
                    Monitor monitor2 = conditionalCoreStartable.mMonitor;
                    Monitor.Subscription.Token token2 = conditionalCoreStartable.mStartToken;
                    monitor2.getClass();
                    monitor2.mExecutor.execute(new Monitor$$ExternalSyntheticLambda2(0, monitor2, token2));
                    conditionalCoreStartable.mStartToken = null;
                    conditionalCoreStartable.onStart();
                    break;
                }
        }
    }
}
