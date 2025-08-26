package com.android.keyguard;

import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;

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

    /* JADX WARN: Removed duplicated region for block: B:15:0x008d  */
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
                boolean zIsAllSimState = ((KeyguardAbsKeyInputViewController) keyguardKnoxGuardViewController).mKeyguardUpdateMonitor.isAllSimState();
                boolean zIsDataAllowed = DeviceState.isDataAllowed(context);
                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("mDataButton OnClick noSimState : ", ", dataAllowed : ", "KeyguardKnoxGuardView", zIsAllSimState, zIsDataAllowed);
                if (!zIsAllSimState) {
                    if (!zIsDataAllowed) {
                        keyguardKnoxGuardViewController.showToast(context.getString(R.string.tile_prevent_change_setting_toast, context.getString(R.string.mobile_data_title)));
                        break;
                    } else {
                        if (Operator.isKoreaQsTileBranding()) {
                            String str = SystemProperties.get("ril.currentplmn");
                            if (keyguardKnoxGuardViewController.mTelephonyManager != null && str != null) {
                                boolean zEquals = "oversea".equals(str);
                                boolean zIsNetworkRoaming = keyguardKnoxGuardViewController.mTelephonyManager.isNetworkRoaming();
                                z = zEquals || zIsNetworkRoaming;
                                if (z) {
                                    StringBuilder sb = new StringBuilder("isNetworkRoaming : ");
                                    sb.append(zIsNetworkRoaming);
                                    sb.append(" currentplmn : ");
                                    sb.append(str);
                                    sb.append(" oversea : ");
                                    ActionBarContextView$$ExternalSyntheticOutline0.m(sb, zEquals, "KeyguardKnoxGuardView");
                                }
                            }
                        } else {
                            z = false;
                        }
                        if (z) {
                            if (!(Settings.Global.getInt(keyguardKnoxGuardViewController.getContext().getContentResolver(), SettingsHelper.INDEX_DATA_ROAMING, 0) == 1)) {
                                Log.d("KeyguardKnoxGuardView", "update data roaming settings");
                                Settings.Global.putInt(keyguardKnoxGuardViewController.getContext().getContentResolver(), SettingsHelper.INDEX_DATA_ROAMING, 1);
                                break;
                            }
                        }
                        keyguardKnoxGuardViewController.mDataController.setMobileDataEnabled(true);
                        break;
                    }
                } else {
                    keyguardKnoxGuardViewController.showToast(keyguardKnoxGuardViewController.getContext().getString(R.string.kg_knox_guard_no_sim_card_toast));
                    break;
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
