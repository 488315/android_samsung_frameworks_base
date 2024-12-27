package com.android.server.ibs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IntelligentBatterySaverGoogleAppPolicy {
    public final Context mContext;
    public final IBSGoogleAppPolicyHandler mHandler;
    public final IntelligentBatterySaverScpmManager mIBSScpmManager;
    public INetworkManagementService mNetworkService;
    public final ArrayMap mGoogleAppsList = new ArrayMap();
    public final Object mLockGoogleAppsList = new Object();
    public boolean mNetworkLimited = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IBSGoogleAppPolicyHandler extends Handler {
        public IBSGoogleAppPolicyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            IntelligentBatterySaverGoogleAppPolicy intelligentBatterySaverGoogleAppPolicy =
                    IntelligentBatterySaverGoogleAppPolicy.this;
            if (i == 1) {
                intelligentBatterySaverGoogleAppPolicy.mNetworkLimited = true;
                intelligentBatterySaverGoogleAppPolicy.setGoogleAppsNetworkAllow(false);
            } else {
                if (i != 2) {
                    return;
                }
                intelligentBatterySaverGoogleAppPolicy.mNetworkLimited = false;
                intelligentBatterySaverGoogleAppPolicy.setGoogleAppsNetworkAllow(true);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IBSGoogleAppPolicyReceiver extends BroadcastReceiver {
        public IBSGoogleAppPolicyReceiver() {
            IntelligentBatterySaverGoogleAppPolicy.this.mContext.registerReceiver(
                    this,
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "com.samsung.android.server.action_google_net_state"),
                    2);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.android.server.action_google_net_state".equals(intent.getAction())) {
                boolean booleanExtra =
                        intent.getBooleanExtra(
                                LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false);
                Slog.v(
                        "IntelligentBatterySaverGoogleAppPolicy",
                        "ACTION***" + intent.getAction() + " state = " + booleanExtra);
                if (booleanExtra) {
                    IntelligentBatterySaverGoogleAppPolicy.this.mHandler.sendEmptyMessage(2);
                } else {
                    IntelligentBatterySaverGoogleAppPolicy.this.mHandler.sendEmptyMessage(1);
                }
            }
        }
    }

    public IntelligentBatterySaverGoogleAppPolicy(Context context, HandlerThread handlerThread) {
        this.mContext = context;
        this.mHandler = new IBSGoogleAppPolicyHandler(handlerThread.getLooper());
        this.mIBSScpmManager = IntelligentBatterySaverScpmManager.getInstance(context);
    }

    public final void setGoogleAppsNetworkAllow(boolean z) {
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setGoogleAppsNetworkAllowallow = ", "IntelligentBatterySaverGoogleAppPolicy", z);
        if ((this.mIBSScpmManager.mPolicyControlSwitch & 2) == 0) {
            return;
        }
        if (this.mNetworkService == null) {
            Slog.d(
                    "IntelligentBatterySaverGoogleAppPolicy",
                    "failed to get NetworkManagementService instance");
            return;
        }
        try {
            int size = this.mGoogleAppsList.size();
            for (int i = 0; i < size; i++) {
                int intValue = ((Integer) this.mGoogleAppsList.keyAt(i)).intValue();
                int intValue2 = ((Integer) this.mGoogleAppsList.valueAt(i)).intValue();
                if (intValue != -1 && (intValue2 & 1) != 0) {
                    this.mNetworkService.setFirewallRuleWifi(intValue, z);
                    this.mNetworkService.setFirewallRuleMobileData(intValue, z);
                }
            }
        } catch (RemoteException e) {
            Slog.v("IntelligentBatterySaverGoogleAppPolicy", "RemoteException:" + e);
        } catch (IllegalStateException e2) {
            Slog.v("IntelligentBatterySaverGoogleAppPolicy", "IllegalStateException:" + e2);
        }
    }
}
