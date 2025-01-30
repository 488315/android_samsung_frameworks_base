package com.android.systemui.power;

import android.content.DialogInterface;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecPowerNotificationWarnings$$ExternalSyntheticLambda0 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecPowerNotificationWarnings f$0;

    public /* synthetic */ SecPowerNotificationWarnings$$ExternalSyntheticLambda0(SecPowerNotificationWarnings secPowerNotificationWarnings, int i) {
        this.$r8$classId = i;
        this.f$0 = secPowerNotificationWarnings;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        switch (this.$r8$classId) {
            case 0:
                SecPowerNotificationWarnings secPowerNotificationWarnings = this.f$0;
                secPowerNotificationWarnings.getClass();
                Log.d("SecPowerUI.Notification", "showIncompleteChargerConnectionInfoPopUp() dismissed");
                secPowerNotificationWarnings.mSlowByChargerConnectionInfoDialog = null;
                break;
            default:
                SecPowerNotificationWarnings secPowerNotificationWarnings2 = this.f$0;
                secPowerNotificationWarnings2.getClass();
                Log.d("SecPowerUI.Notification", "showBatterySwellingLowTempPopup() dismissed");
                secPowerNotificationWarnings2.mSwellingDialog = null;
                break;
        }
    }
}
