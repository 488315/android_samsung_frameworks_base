package com.android.systemui.power;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.PowerNotificationWarnings;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda3 implements ActivityStarter.Callback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.Callback
    public final void onActivityStarted(int i) {
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                ((PowerNotificationWarnings) obj).mUsbHighTempDialog = null;
                break;
            case 1:
                ((PowerNotificationWarnings.DialogInterfaceOnClickListenerC19311) obj).this$0.mHighTempDialog = null;
                break;
            default:
                ((PowerNotificationWarnings.DialogInterfaceOnClickListenerC19322) obj).this$0.mThermalShutdownDialog = null;
                break;
        }
    }
}
