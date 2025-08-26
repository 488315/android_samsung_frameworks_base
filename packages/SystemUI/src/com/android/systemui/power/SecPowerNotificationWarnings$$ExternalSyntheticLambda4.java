package com.android.systemui.power;

import android.content.Intent;
import android.os.PowerManager;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecPowerNotificationWarnings$$ExternalSyntheticLambda4 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecPowerNotificationWarnings f$0;

    public /* synthetic */ SecPowerNotificationWarnings$$ExternalSyntheticLambda4(SecPowerNotificationWarnings secPowerNotificationWarnings, int i) {
        this.$r8$classId = i;
        this.f$0 = secPowerNotificationWarnings;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        SecPowerNotificationWarnings secPowerNotificationWarnings = this.f$0;
        switch (i) {
            case 0:
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("UsbDamageProtectionAlertDialog can dismiss : "), !secPowerNotificationWarnings.mIsTemperatureHiccupState, "PowerUI.Notification");
                if (!secPowerNotificationWarnings.mIsTemperatureHiccupState) {
                    secPowerNotificationWarnings.mContext.sendBroadcast(new Intent("com.samsung.systemui.power.action.WATER_POPUP_DISMISSED"));
                    secPowerNotificationWarnings.mHandler.removeCallbacks(secPowerNotificationWarnings.mUsbDamageProtectionAlertTask);
                    PowerManager.WakeLock wakeLock = secPowerNotificationWarnings.mUsbDamageProtectionPartialWakeLock;
                    if (wakeLock != null) {
                        wakeLock.release();
                        secPowerNotificationWarnings.mUsbDamageProtectionPartialWakeLock = null;
                    }
                    AlertDialog alertDialog = secPowerNotificationWarnings.mUsbDamageProtectionAlertDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                        break;
                    }
                }
                break;
            default:
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("WaterProtectionAlertDialog can dismiss : "), !secPowerNotificationWarnings.mIsHiccupState, "PowerUI.Notification");
                if (!secPowerNotificationWarnings.mIsHiccupState) {
                    secPowerNotificationWarnings.mHandler.removeCallbacks(secPowerNotificationWarnings.mWaterProtectionAlertTask);
                    secPowerNotificationWarnings.mContext.sendBroadcast(new Intent("com.samsung.systemui.power.action.WATER_POPUP_DISMISSED"));
                    PowerManager.WakeLock wakeLock2 = secPowerNotificationWarnings.mWaterProtectionPartialWakeLock;
                    if (wakeLock2 != null) {
                        wakeLock2.release();
                        secPowerNotificationWarnings.mWaterProtectionPartialWakeLock = null;
                    }
                    AlertDialog alertDialog2 = secPowerNotificationWarnings.mWaterProtectionAlertDialog;
                    if (alertDialog2 != null) {
                        alertDialog2.dismiss();
                        break;
                    }
                }
                break;
        }
    }
}
