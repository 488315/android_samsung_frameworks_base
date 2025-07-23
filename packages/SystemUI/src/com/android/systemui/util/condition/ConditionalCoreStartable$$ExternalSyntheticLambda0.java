package com.android.systemui.util.condition;

import com.android.systemui.shared.condition.Monitor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
                conditionalCoreStartable.lambda$onBootCompleted$1(z);
                break;
            default:
                conditionalCoreStartable.lambda$start$0(z);
                break;
        }
    }
}
