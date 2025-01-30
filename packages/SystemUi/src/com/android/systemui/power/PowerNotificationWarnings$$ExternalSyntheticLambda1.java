package com.android.systemui.power;

import android.content.DialogInterface;
import android.content.Intent;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda1 implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PowerNotificationWarnings f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda1(PowerNotificationWarnings powerNotificationWarnings, int i) {
        this.$r8$classId = i;
        this.f$0 = powerNotificationWarnings;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mUsbHighTempDialog = null;
                break;
            case 1:
                PowerNotificationWarnings powerNotificationWarnings = this.f$0;
                String string = powerNotificationWarnings.mContext.getString(R.string.high_temp_alarm_help_url);
                Intent intent = new Intent();
                intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.HelpTrampoline");
                intent.putExtra("android.intent.extra.TEXT", string);
                powerNotificationWarnings.mActivityStarter.startActivity(intent, true, (ActivityStarter.Callback) new PowerNotificationWarnings$$ExternalSyntheticLambda3(powerNotificationWarnings, 0));
                break;
            case 2:
                PowerNotificationWarnings powerNotificationWarnings2 = this.f$0;
                BatterySaverUtils.setPowerSaveMode(1, powerNotificationWarnings2.mContext, true, false);
                powerNotificationWarnings2.logEvent(BatteryWarningEvents$LowBatteryWarningEvent.SAVER_CONFIRM_OK);
                break;
            default:
                PowerNotificationWarnings powerNotificationWarnings3 = this.f$0;
                powerNotificationWarnings3.getClass();
                powerNotificationWarnings3.logEvent(BatteryWarningEvents$LowBatteryWarningEvent.SAVER_CONFIRM_CANCEL);
                break;
        }
    }
}
