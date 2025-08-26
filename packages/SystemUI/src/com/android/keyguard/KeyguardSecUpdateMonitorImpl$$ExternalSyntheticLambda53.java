package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.samsung.android.bio.face.SemBioFaceManager;
import com.samsung.android.cover.CoverState;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda53 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda53(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardUpdateMonitorCallback) obj).onUpdateCoverState((CoverState) obj2);
                break;
            case 1:
                SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardUpdateMonitorCallback) obj).onSecurityViewChanged((KeyguardSecurityModel.SecurityMode) obj2);
                break;
            default:
                SemBioFaceManager semBioFaceManager3 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardUpdateMonitorCallback) obj).onRefreshBatteryInfo((KeyguardBatteryStatus) ((BatteryStatus) obj2));
                break;
        }
    }
}
