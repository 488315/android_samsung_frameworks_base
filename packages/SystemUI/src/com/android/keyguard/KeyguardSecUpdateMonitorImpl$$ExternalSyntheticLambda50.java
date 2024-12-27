package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.samsung.android.cover.CoverState;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda50 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda50(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((KeyguardUpdateMonitorCallback) obj).onUpdateCoverState((CoverState) obj2);
                break;
            case 1:
                ((KeyguardUpdateMonitorCallback) obj).onSecurityViewChanged((KeyguardSecurityModel.SecurityMode) obj2);
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onRefreshBatteryInfo((KeyguardBatteryStatus) ((BatteryStatus) obj2));
                break;
        }
    }
}
