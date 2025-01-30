package com.android.keyguard;

import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardKnoxGuardViewController$$ExternalSyntheticLambda4 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardKnoxGuardViewController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KeyguardKnoxGuardViewController$$ExternalSyntheticLambda4(KeyguardKnoxGuardViewController keyguardKnoxGuardViewController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardKnoxGuardViewController;
        this.f$1 = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0095  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onClick(View view) {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = this.f$0;
                Context context = (Context) this.f$1;
                boolean isAllSimState = ((KeyguardAbsKeyInputViewController) keyguardKnoxGuardViewController).mKeyguardUpdateMonitor.isAllSimState();
                boolean isDataAllowed = DeviceState.isDataAllowed(context);
                EmergencyButtonController$$ExternalSyntheticOutline0.m59m("mDataButton OnClick noSimState : ", isAllSimState, ", dataAllowed : ", isDataAllowed, "KeyguardKnoxGuardView");
                if (isAllSimState) {
                    keyguardKnoxGuardViewController.showToast(keyguardKnoxGuardViewController.getContext().getString(R.string.kg_knox_guard_no_sim_card_toast));
                    break;
                } else if (!isDataAllowed) {
                    keyguardKnoxGuardViewController.showToast(context.getString(R.string.tile_prevent_change_setting_toast, context.getString(R.string.mobile_data_title)));
                    break;
                } else {
                    if (Operator.isKoreaQsTileBranding()) {
                        String str = SystemProperties.get("ril.currentplmn");
                        TelephonyManager telephonyManager = keyguardKnoxGuardViewController.mTelephonyManager;
                        if (telephonyManager != null && str != null) {
                            boolean equals = "oversea".equals(str);
                            boolean isNetworkRoaming = telephonyManager.isNetworkRoaming();
                            z = equals || isNetworkRoaming;
                            if (z) {
                                StringBuilder sb = new StringBuilder("isNetworkRoaming : ");
                                sb.append(isNetworkRoaming);
                                sb.append(" currentplmn : ");
                                sb.append(str);
                                sb.append(" oversea : ");
                                ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, equals, "KeyguardKnoxGuardView");
                            }
                            if (z) {
                                if (!(Settings.Global.getInt(keyguardKnoxGuardViewController.getContext().getContentResolver(), "data_roaming", 0) == 1)) {
                                    Log.d("KeyguardKnoxGuardView", "update data roaming settings");
                                    Settings.Global.putInt(keyguardKnoxGuardViewController.getContext().getContentResolver(), "data_roaming", 1);
                                    break;
                                }
                            }
                            keyguardKnoxGuardViewController.mDataController.setMobileDataEnabled(true);
                            break;
                        }
                    }
                    z = false;
                    if (z) {
                    }
                    keyguardKnoxGuardViewController.mDataController.setMobileDataEnabled(true);
                }
            default:
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController2 = this.f$0;
                KeyguardSecurityCallback keyguardSecurityCallback = (KeyguardSecurityCallback) this.f$1;
                keyguardKnoxGuardViewController2.getClass();
                Log.d("KeyguardKnoxGuardView", "mOptionButton OnClick");
                keyguardKnoxGuardViewController2.resetPinErrorMessage();
                Intent intent = new Intent("com.samsung.kgclient.intent.action.SUPPORT_PAGE");
                intent.setClassName("com.samsung.android.kgclient", "com.samsung.android.kgclient.receiver.KGIntentReceiver");
                intent.addFlags(32);
                keyguardKnoxGuardViewController2.getContext().sendBroadcastAsUser(intent, UserHandle.CURRENT, "com.samsung.android.knoxguard.STATUS");
                ((KeyguardAbsKeyInputViewController) keyguardKnoxGuardViewController2).mKeyguardUpdateMonitor.reportEmergencyCallAction();
                if (keyguardSecurityCallback != null) {
                    keyguardSecurityCallback.userActivity();
                    break;
                }
                break;
        }
    }
}
