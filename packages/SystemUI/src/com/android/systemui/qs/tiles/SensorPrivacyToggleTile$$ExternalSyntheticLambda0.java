package com.android.systemui.qs.tiles;

import com.android.systemui.qs.tiles.SensorPrivacyToggleTile;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SensorPrivacyToggleTile$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((SensorPrivacyToggleTile) obj).toggleTileState();
                break;
            default:
                SensorPrivacyToggleTile sensorPrivacyToggleTile = ((SensorPrivacyToggleTile.SensorPrivacyToggleDetailAdapter) obj).this$0;
                ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).setSensorBlocked(1, sensorPrivacyToggleTile.getSensorId(), !((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).isSensorBlocked(sensorPrivacyToggleTile.getSensorId()));
                break;
        }
    }
}
