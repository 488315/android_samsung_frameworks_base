package com.android.systemui.qs.tiles.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.settingslib.satellite.SatelliteDialogUtils;
import com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy;
import com.android.wifitrackerlib.WifiEntry;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDialogDelegateLegacy$$ExternalSyntheticLambda8 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InternetDialogDelegateLegacy f$0;

    public /* synthetic */ InternetDialogDelegateLegacy$$ExternalSyntheticLambda8(InternetDialogDelegateLegacy internetDialogDelegateLegacy, int i) {
        this.$r8$classId = i;
        this.f$0 = internetDialogDelegateLegacy;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        final InternetDialogDelegateLegacy internetDialogDelegateLegacy = this.f$0;
        switch (i) {
            case 0:
                WifiEntry wifiEntry = internetDialogDelegateLegacy.mConnectedWifiEntry;
                if (wifiEntry != null) {
                    internetDialogDelegateLegacy.mInternetDetailsContentController.launchWifiDetailsSetting(view, wifiEntry.getKey());
                    break;
                }
                break;
            case 1:
                InternetDetailsContentController internetDetailsContentController = internetDialogDelegateLegacy.mInternetDetailsContentController;
                Intent configuratorQrCodeGeneratorIntentOrNull = internetDetailsContentController.getConfiguratorQrCodeGeneratorIntentOrNull(internetDialogDelegateLegacy.mConnectedWifiEntry);
                if (configuratorQrCodeGeneratorIntentOrNull != null) {
                    internetDetailsContentController.startActivity(configuratorQrCodeGeneratorIntentOrNull, view);
                    internetDialogDelegateLegacy.mUiEventLogger.log(InternetDialogDelegateLegacy.InternetDialogEvent.SHARE_WIFI_QS_BUTTON_CLICKED);
                    break;
                }
                break;
            case 2:
                final boolean isChecked = internetDialogDelegateLegacy.mWiFiToggle.isChecked();
                StandaloneCoroutine standaloneCoroutine = internetDialogDelegateLegacy.mClickJob;
                if (standaloneCoroutine == null || standaloneCoroutine.isCompleted()) {
                    internetDialogDelegateLegacy.mClickJob = SatelliteDialogUtils.mayStartSatelliteWarningDialog(internetDialogDelegateLegacy.mDialog.getContext(), internetDialogDelegateLegacy.mCoroutineScope, new Function1() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda15
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            boolean z = InternetDialogDelegateLegacy.DEBUG;
                            InternetDialogDelegateLegacy internetDialogDelegateLegacy2 = InternetDialogDelegateLegacy.this;
                            internetDialogDelegateLegacy2.getClass();
                            boolean booleanValue = ((Boolean) obj).booleanValue();
                            boolean z2 = isChecked;
                            if (!booleanValue) {
                                internetDialogDelegateLegacy2.mWiFiToggle.setChecked(!z2);
                                return null;
                            }
                            InternetDetailsContentController internetDetailsContentController2 = internetDialogDelegateLegacy2.mInternetDetailsContentController;
                            if (internetDetailsContentController2.mWifiStateWorker.isWifiEnabled() == z2) {
                                return null;
                            }
                            WifiStateWorker wifiStateWorker = internetDetailsContentController2.mWifiStateWorker;
                            wifiStateWorker.mBackgroundExecutor.execute(new WifiStateWorker$$ExternalSyntheticLambda1(wifiStateWorker, z2));
                            return null;
                        }
                    });
                    break;
                }
                break;
            case 3:
                internetDialogDelegateLegacy.mInternetDetailsContentController.mConnectivityManager.setAirplaneMode(false);
                break;
            case 4:
                InternetDetailsContentController internetDetailsContentController2 = internetDialogDelegateLegacy.mInternetDetailsContentController;
                int activeAutoSwitchNonDdsSubId = internetDetailsContentController2.getActiveAutoSwitchNonDdsSubId();
                if (activeAutoSwitchNonDdsSubId != -1) {
                    Intent intent = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
                    Bundle bundle = new Bundle();
                    bundle.putString(":settings:fragment_args_key", "auto_data_switch");
                    intent.putExtra("android.provider.extra.SUB_ID", activeAutoSwitchNonDdsSubId);
                    intent.putExtra(":settings:show_fragment_args", bundle);
                    internetDetailsContentController2.startActivity(intent, view);
                    break;
                } else {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(activeAutoSwitchNonDdsSubId, "launchMobileNetworkSettings fail, invalid subId:", "InternetDetailsContentController");
                    break;
                }
            default:
                InternetDetailsContentController internetDetailsContentController3 = internetDialogDelegateLegacy.mInternetDetailsContentController;
                internetDetailsContentController3.startActivity(internetDetailsContentController3.getSettingsIntent(), view);
                break;
        }
    }
}
