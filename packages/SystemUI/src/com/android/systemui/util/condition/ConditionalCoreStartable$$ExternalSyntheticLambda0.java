package com.android.systemui.util.condition;

import com.android.systemui.shared.condition.Monitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
