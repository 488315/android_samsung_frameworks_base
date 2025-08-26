package com.android.systemui.power;

import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.power.constants.PowerUiConstants;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecPowerNotificationWarnings$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecPowerNotificationWarnings f$0;

    public /* synthetic */ SecPowerNotificationWarnings$$ExternalSyntheticLambda0(SecPowerNotificationWarnings secPowerNotificationWarnings, int i) {
        this.$r8$classId = i;
        this.f$0 = secPowerNotificationWarnings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SecPowerNotificationWarnings secPowerNotificationWarnings = this.f$0;
        switch (i) {
            case 0:
                secPowerNotificationWarnings.showWaterProtectionAlertDialog(secPowerNotificationWarnings.mIsWaterDetected);
                break;
            case 1:
                secPowerNotificationWarnings.showUsbDamageProtectionAlertDialog();
                break;
            default:
                secPowerNotificationWarnings.getClass();
                Log.i("PowerUI.Notification", "dumpsAdditionalBatteryInfo call DC service in worker thread");
                Intent intent = new Intent();
                intent.setPackage(PowerUiConstants.DC_PACKAGE_NAME);
                intent.setAction("com.samsung.android.sm.DUMP");
                try {
                    if (secPowerNotificationWarnings.mContext.getPackageManager().queryIntentServices(intent, 0).isEmpty()) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("dump", (Integer) 1);
                        Log.i("PowerUI.Notification", "update dc dump provider");
                        secPowerNotificationWarnings.mResolver.update(PowerUiConstants.SMART_MGR_VERIFY_FORCED_APP_STANDBY_URI, contentValues, null, null);
                    } else {
                        Log.i("PowerUI.Notification", "start dc dump service");
                        secPowerNotificationWarnings.mContext.startService(intent);
                        Log.w("PowerUI.Notification", "quitBgThread");
                        secPowerNotificationWarnings.mHandlerWrapper.mWorkerThread.quitSafely();
                        secPowerNotificationWarnings.mHandlerWrapper = null;
                    }
                    break;
                } catch (Error | Exception e) {
                    Log.w("PowerUI.Notification", "err", e);
                    return;
                }
        }
    }
}
