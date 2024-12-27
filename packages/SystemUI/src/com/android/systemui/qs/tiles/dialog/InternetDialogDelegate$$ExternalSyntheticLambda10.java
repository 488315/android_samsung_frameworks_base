package com.android.systemui.qs.tiles.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.internal.telephony.flags.Flags;
import com.android.settingslib.satellite.SatelliteDialogUtils;
import com.android.systemui.qs.tiles.dialog.InternetDialogDelegate;
import com.android.wifitrackerlib.WifiEntry;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class InternetDialogDelegate$$ExternalSyntheticLambda10 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InternetDialogDelegate f$0;

    public /* synthetic */ InternetDialogDelegate$$ExternalSyntheticLambda10(InternetDialogDelegate internetDialogDelegate, int i) {
        this.$r8$classId = i;
        this.f$0 = internetDialogDelegate;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.tiles.dialog.InternetDialogDelegate$$ExternalSyntheticLambda25] */
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        final InternetDialogDelegate internetDialogDelegate = this.f$0;
        switch (i) {
            case 0:
                WifiEntry wifiEntry = internetDialogDelegate.mConnectedWifiEntry;
                if (wifiEntry != null) {
                    internetDialogDelegate.mInternetDialogController.launchWifiDetailsSetting(view, wifiEntry.getKey());
                    break;
                }
                break;
            case 1:
                WifiEntry wifiEntry2 = internetDialogDelegate.mConnectedWifiEntry;
                InternetDialogController internetDialogController = internetDialogDelegate.mInternetDialogController;
                Intent configuratorQrCodeGeneratorIntentOrNull = internetDialogController.getConfiguratorQrCodeGeneratorIntentOrNull(wifiEntry2);
                if (configuratorQrCodeGeneratorIntentOrNull != null) {
                    internetDialogController.startActivity(configuratorQrCodeGeneratorIntentOrNull, view);
                    internetDialogDelegate.mUiEventLogger.log(InternetDialogDelegate.InternetDialogEvent.SHARE_WIFI_QS_BUTTON_CLICKED);
                    break;
                }
                break;
            case 2:
                InternetDialogController internetDialogController2 = internetDialogDelegate.mInternetDialogController;
                internetDialogController2.startActivity(internetDialogController2.getSettingsIntent(), view);
                break;
            case 3:
                final boolean isChecked = internetDialogDelegate.mWiFiToggle.isChecked();
                if (!Flags.oemEnabledSatelliteFlag()) {
                    InternetDialogController internetDialogController3 = internetDialogDelegate.mInternetDialogController;
                    if (internetDialogController3.isWifiEnabled() != isChecked) {
                        WifiStateWorker wifiStateWorker = internetDialogController3.mWifiStateWorker;
                        wifiStateWorker.mBackgroundExecutor.execute(new WifiStateWorker$$ExternalSyntheticLambda1(wifiStateWorker, isChecked));
                        break;
                    }
                } else {
                    Job job = internetDialogDelegate.mClickJob;
                    if (job == null || job.isCompleted()) {
                        internetDialogDelegate.mClickJob = SatelliteDialogUtils.mayStartSatelliteWarningDialog(internetDialogDelegate.mDialog.getContext(), internetDialogDelegate.mCoroutineScope, new Function1() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegate$$ExternalSyntheticLambda25
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                InternetDialogDelegate internetDialogDelegate2 = InternetDialogDelegate.this;
                                internetDialogDelegate2.getClass();
                                boolean booleanValue = ((Boolean) obj).booleanValue();
                                boolean z = isChecked;
                                if (!booleanValue) {
                                    internetDialogDelegate2.mWiFiToggle.setChecked(!z);
                                    return null;
                                }
                                InternetDialogController internetDialogController4 = internetDialogDelegate2.mInternetDialogController;
                                if (internetDialogController4.isWifiEnabled() == z) {
                                    return null;
                                }
                                WifiStateWorker wifiStateWorker2 = internetDialogController4.mWifiStateWorker;
                                wifiStateWorker2.mBackgroundExecutor.execute(new WifiStateWorker$$ExternalSyntheticLambda1(wifiStateWorker2, z));
                                return null;
                            }
                        });
                        break;
                    }
                }
                break;
            case 4:
                internetDialogDelegate.mInternetDialogController.mConnectivityManager.setAirplaneMode(false);
                break;
            default:
                InternetDialogController internetDialogController4 = internetDialogDelegate.mInternetDialogController;
                int activeAutoSwitchNonDdsSubId = internetDialogController4.getActiveAutoSwitchNonDdsSubId();
                if (activeAutoSwitchNonDdsSubId != -1) {
                    Intent intent = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
                    Bundle bundle = new Bundle();
                    bundle.putString(":settings:fragment_args_key", "auto_data_switch");
                    intent.putExtra("android.provider.extra.SUB_ID", activeAutoSwitchNonDdsSubId);
                    intent.putExtra(":settings:show_fragment_args", bundle);
                    internetDialogController4.startActivity(intent, view);
                    break;
                } else {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(activeAutoSwitchNonDdsSubId, "launchMobileNetworkSettings fail, invalid subId:", "InternetDialogController");
                    break;
                }
        }
    }
}
