package com.android.systemui.wifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.systemui.R;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class WifiDebuggingSecondaryUserActivity extends AlertActivity implements DialogInterface.OnClickListener {
    public WifiChangeReceiver mWifiChangeReceiver;
    public WifiManager mWifiManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiChangeReceiver extends BroadcastReceiver {
        public final Activity mActivity;

        public WifiChangeReceiver(Activity activity) {
            this.mActivity = activity;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                if (intent.getIntExtra("wifi_state", 1) == 1) {
                    this.mActivity.finish();
                }
            } else if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
                if (networkInfo.getType() == 1) {
                    if (!networkInfo.isConnected()) {
                        this.mActivity.finish();
                        return;
                    }
                    WifiInfo connectionInfo = WifiDebuggingSecondaryUserActivity.this.mWifiManager.getConnectionInfo();
                    if (connectionInfo == null || connectionInfo.getNetworkId() == -1) {
                        this.mActivity.finish();
                    }
                }
            }
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mWifiManager = (WifiManager) getSystemService(ImsProfile.PDN_WIFI);
        this.mWifiChangeReceiver = new WifiChangeReceiver(this);
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle = getString(R.string.wifi_debugging_secondary_user_title);
        alertParams.mMessage = getString(R.string.wifi_debugging_secondary_user_message);
        alertParams.mPositiveButtonText = getString(android.R.string.ok);
        alertParams.mPositiveButtonListener = this;
        setupAlert();
    }

    public final void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(this.mWifiChangeReceiver, intentFilter);
        closeSystemDialogs();
    }

    public final void onStop() {
        WifiChangeReceiver wifiChangeReceiver = this.mWifiChangeReceiver;
        if (wifiChangeReceiver != null) {
            unregisterReceiver(wifiChangeReceiver);
        }
        super.onStop();
    }
}
