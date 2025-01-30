package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.samsung.android.cover.CoverState;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda20 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda20(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardUpdateMonitorCallback) obj).onSecurityViewChanged((KeyguardSecurityModel.SecurityMode) this.f$0);
                break;
            case 1:
                ((KeyguardUpdateMonitorCallback) obj).onUpdateCoverState((CoverState) this.f$0);
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onRefreshBatteryInfo((KeyguardBatteryStatus) ((BatteryStatus) this.f$0));
                break;
        }
    }
}
