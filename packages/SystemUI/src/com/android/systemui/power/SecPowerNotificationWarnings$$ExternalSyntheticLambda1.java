package com.android.systemui.power;

import android.content.DialogInterface;
import android.util.Log;

public final /* synthetic */ class SecPowerNotificationWarnings$$ExternalSyntheticLambda1 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecPowerNotificationWarnings f$0;

    public /* synthetic */ SecPowerNotificationWarnings$$ExternalSyntheticLambda1(SecPowerNotificationWarnings secPowerNotificationWarnings, int i) {
        this.$r8$classId = i;
        this.f$0 = secPowerNotificationWarnings;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        int i = this.$r8$classId;
        SecPowerNotificationWarnings secPowerNotificationWarnings = this.f$0;
        secPowerNotificationWarnings.getClass();
        switch (i) {
            case 0:
                Log.d("PowerUI.Notification", "showBatterySwellingLowTempPopup() dismissed");
                secPowerNotificationWarnings.mSwellingDialog = null;
                break;
            default:
                Log.d("PowerUI.Notification", "showIncompleteChargerConnectionInfoPopUp() dismissed");
                secPowerNotificationWarnings.mSlowByChargerConnectionInfoDialog = null;
                break;
        }
    }
}
