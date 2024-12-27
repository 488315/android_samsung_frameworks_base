package com.android.systemui.qs.tiles;

import com.android.systemui.qs.tiles.SensorPrivacyToggleTile;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;

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
