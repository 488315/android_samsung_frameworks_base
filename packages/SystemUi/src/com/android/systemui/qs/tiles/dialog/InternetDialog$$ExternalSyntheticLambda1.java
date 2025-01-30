package com.android.systemui.qs.tiles.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDialog$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InternetDialog$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                final InternetDialog internetDialog = (InternetDialog) this.f$0;
                final int activeAutoSwitchNonDdsSubId = internetDialog.mInternetDialogController.getActiveAutoSwitchNonDdsSubId();
                if (activeAutoSwitchNonDdsSubId != -1) {
                    CharSequence mobileNetworkTitle = internetDialog.getMobileNetworkTitle(internetDialog.mDefaultDataSubId);
                    if (TextUtils.isEmpty(mobileNetworkTitle)) {
                        mobileNetworkTitle = internetDialog.mContext.getString(R.string.mobile_data_disable_message_default_carrier);
                    }
                    AlertDialog create = new AlertDialog.Builder(internetDialog.mContext).setTitle(internetDialog.mContext.getString(R.string.auto_data_switch_disable_title, mobileNetworkTitle)).setMessage(R.string.auto_data_switch_disable_message).setNegativeButton(R.string.auto_data_switch_dialog_negative_button, new InternetDialog$$ExternalSyntheticLambda7()).setPositiveButton(R.string.auto_data_switch_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda8
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            InternetDialog internetDialog2 = InternetDialog.this;
                            int i2 = activeAutoSwitchNonDdsSubId;
                            InternetDialogController internetDialogController = internetDialog2.mInternetDialogController;
                            TelephonyManager orDefault = internetDialogController.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i2), internetDialogController.mTelephonyManager);
                            if (orDefault != null) {
                                orDefault.setMobileDataPolicyEnabled(3, false);
                            } else if (InternetDialogController.DEBUG) {
                                Log.d("InternetDialogController", "TelephonyManager is null, can not set mobile data.");
                            }
                            LinearLayout linearLayout = internetDialog2.mSecondaryMobileNetworkLayout;
                            if (linearLayout != null) {
                                linearLayout.setVisibility(8);
                            }
                        }
                    }).create();
                    internetDialog.mAlertDialog = create;
                    create.getWindow().setType(2009);
                    SystemUIDialog.setShowForAllUsers(internetDialog.mAlertDialog);
                    SystemUIDialog.registerDismissListener(internetDialog.mAlertDialog, null);
                    SystemUIDialog.setWindowOnTop(internetDialog.mAlertDialog, ((KeyguardStateControllerImpl) internetDialog.mKeyguard).mShowing);
                    internetDialog.mDialogLaunchAnimator.showFromDialog(internetDialog.mAlertDialog, internetDialog, null, false);
                }
                InternetDialogController internetDialogController = internetDialog.mInternetDialogController;
                boolean isMobileDataEnabled = internetDialogController.isMobileDataEnabled();
                boolean z = InternetDialogController.DEBUG;
                if (!isMobileDataEnabled) {
                    if (z) {
                        Log.d("InternetDialogController", "Fail to connect carrier network : settings OFF");
                        break;
                    }
                } else if (!(!internetDialogController.mKeyguardStateController.isUnlocked())) {
                    if (!internetDialogController.activeNetworkIsCellular()) {
                        MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) internetDialogController.mAccessPointController).getMergedCarrierEntry();
                        if (mergedCarrierEntry != null) {
                            if (!mergedCarrierEntry.canConnect()) {
                                Log.w("InternetDialogController", "Fail to connect carrier network : merged entry connect state " + mergedCarrierEntry.getConnectedState());
                                break;
                            } else {
                                mergedCarrierEntry.connect$1(null);
                                SysUIToast.makeText(R.string.wifi_wont_autoconnect_for_now, internetDialogController.mContext, 0).show();
                                break;
                            }
                        } else {
                            Log.e("InternetDialogController", "Fail to connect carrier network : no merged entry");
                            break;
                        }
                    } else {
                        Log.d("InternetDialogController", "Fail to connect carrier network : already active");
                        break;
                    }
                } else if (z) {
                    Log.d("InternetDialogController", "Fail to connect carrier network : device locked");
                    break;
                }
                break;
            case 1:
                InternetDialog internetDialog2 = (InternetDialog) this.f$0;
                WifiEntry wifiEntry = internetDialog2.mConnectedWifiEntry;
                if (wifiEntry != null) {
                    internetDialog2.mInternetDialogController.launchWifiDetailsSetting(view, wifiEntry.getKey());
                    break;
                }
                break;
            case 2:
                InternetDialogController internetDialogController2 = ((InternetDialog) this.f$0).mInternetDialogController;
                internetDialogController2.startActivity(internetDialogController2.getSettingsIntent(), view);
                break;
            case 3:
                ((InternetDialog) this.f$0).dismiss();
                break;
            case 4:
                InternetDialogController internetDialogController3 = ((InternetDialog) this.f$0).mInternetDialogController;
                internetDialogController3.mSettingsHelper.setAirplaneMode(false);
                Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
                intent.putExtra("state", false);
                internetDialogController3.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                break;
            case 5:
                InternetDialogController internetDialogController4 = ((InternetDialog) this.f$0).mInternetDialogController;
                int activeAutoSwitchNonDdsSubId2 = internetDialogController4.getActiveAutoSwitchNonDdsSubId();
                if (activeAutoSwitchNonDdsSubId2 != -1) {
                    Intent intent2 = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
                    Bundle bundle = new Bundle();
                    bundle.putString(":settings:fragment_args_key", "auto_data_switch");
                    intent2.putExtra("android.provider.extra.SUB_ID", activeAutoSwitchNonDdsSubId2);
                    intent2.putExtra(":settings:show_fragment_args", bundle);
                    internetDialogController4.startActivity(intent2, view);
                    break;
                } else {
                    IconCompat$$ExternalSyntheticOutline0.m30m("launchMobileNetworkSettings fail, invalid subId:", activeAutoSwitchNonDdsSubId2, "InternetDialogController");
                    break;
                }
            default:
                InternetDialogController internetDialogController5 = (InternetDialogController) this.f$0;
                internetDialogController5.getClass();
                Intent intent3 = new Intent("android.settings.WIFI_SCANNING_SETTINGS");
                intent3.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                internetDialogController5.startActivity(intent3, view);
                break;
        }
    }
}
