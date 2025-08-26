package com.android.systemui.power;

import android.content.DialogInterface;
import android.util.Log;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecPowerNotificationWarnings$$ExternalSyntheticLambda3 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecPowerNotificationWarnings f$0;

    public /* synthetic */ SecPowerNotificationWarnings$$ExternalSyntheticLambda3(SecPowerNotificationWarnings secPowerNotificationWarnings, int i) {
        this.$r8$classId = i;
        this.f$0 = secPowerNotificationWarnings;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        int i = this.$r8$classId;
        SecPowerNotificationWarnings secPowerNotificationWarnings = this.f$0;
        switch (i) {
            case 0:
                secPowerNotificationWarnings.mUsbDamageProtectionAlertDialog = null;
                secPowerNotificationWarnings.stopPowerSound(1600);
                break;
            case 1:
                secPowerNotificationWarnings.getClass();
                Log.d("PowerUI.Notification", "mWaterProtectionAlertDialog onDismiss");
                secPowerNotificationWarnings.mWaterProtectionAlertDialog = null;
                secPowerNotificationWarnings.stopPowerSound(1600);
                break;
            case 2:
                secPowerNotificationWarnings.getClass();
                Log.d("PowerUI.Notification", "showBatterySwellingLowTempPopup() dismissed");
                secPowerNotificationWarnings.mSwellingDialog = null;
                break;
            default:
                secPowerNotificationWarnings.getClass();
                Log.d("PowerUI.Notification", "showIncompleteChargerConnectionInfoPopUp() dismissed");
                secPowerNotificationWarnings.mSlowByChargerConnectionInfoDialog = null;
                break;
        }
    }
}
