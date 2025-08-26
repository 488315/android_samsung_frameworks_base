package com.android.keyguard;

import android.net.Uri;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes.dex */
public final /* synthetic */ class EmergencyButtonController$$ExternalSyntheticLambda3 implements SettingsHelper.OnChangedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EmergencyButtonController f$0;

    public /* synthetic */ EmergencyButtonController$$ExternalSyntheticLambda3(EmergencyButtonController emergencyButtonController, int i) {
        this.$r8$classId = i;
        this.f$0 = emergencyButtonController;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        switch (this.$r8$classId) {
            case 0:
                EmergencyButtonController emergencyButtonController = this.f$0;
                emergencyButtonController.getClass();
                boolean zIsSatelliteModeEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isSatelliteModeEnabled();
                EmergencyButtonController$$ExternalSyntheticOutline0.m("SATELLITE_MODE_ENABLED changed to ", "EmergencyButton", zIsSatelliteModeEnabled);
                if (!zIsSatelliteModeEnabled) {
                    emergencyButtonController.unregisterSatelliteTelephonyCallback();
                    emergencyButtonController.updateEmergencyCallButton();
                    break;
                } else {
                    emergencyButtonController.registerSatelliteTelephonyCallback();
                    break;
                }
            default:
                this.f$0.updateEmergencyCallButton();
                break;
        }
    }
}
