package com.android.server.ibs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Slog;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class IntelligentBatterySaverGoogleAppPolicy {
    public final Context mContext;
    public final IBSGoogleAppPolicyHandler mHandler;
    public final HandlerThread mHandlerThread;
    public IntelligentBatterySaverScpmManager mIBSScpmManager;
    public INetworkManagementService mNetworkService;
    public IBSGoogleAppPolicyReceiver mReceiver;
    public final ArrayMap mGoogleAppsList = new ArrayMap();
    public final Object mLockGoogleAppsList = new Object();
    public boolean mNetworkLimited = false;

    public IntelligentBatterySaverGoogleAppPolicy(Context context, HandlerThread handlerThread) {
        this.mContext = context;
        this.mHandlerThread = handlerThread;
        this.mHandler = new IBSGoogleAppPolicyHandler(handlerThread.getLooper());
        this.mIBSScpmManager = IntelligentBatterySaverScpmManager.getInstance(context);
    }

    public void init() {
        addPackageNameGoogleAppsList("com.android.vending", 1);
        this.mReceiver = new IBSGoogleAppPolicyReceiver();
        getNetworkManagementService();
    }

    public final INetworkManagementService getNetworkManagementService() {
        IBinder service = ServiceManager.getService("network_management");
        if (service != null) {
            this.mNetworkService = INetworkManagementService.Stub.asInterface(service);
        }
        return this.mNetworkService;
    }

    public void clearAllGoogleAppsList() {
        synchronized (this.mLockGoogleAppsList) {
            this.mGoogleAppsList.clear();
        }
    }

    public void addPackageNameGoogleAppsList(String str, Integer num) {
        Slog.d("IntelligentBatterySaverGoogleAppPolicy", "addPackageNameGoogleAppsList pkg= " + str + "value = " + num);
        synchronized (this.mLockGoogleAppsList) {
            try {
                this.mGoogleAppsList.put(Integer.valueOf(this.mContext.getPackageManager().getPackageUid(str, 0)), num);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.v("IntelligentBatterySaverGoogleAppPolicy", "NameNotFoundException" + e);
                this.mGoogleAppsList.put(-1, -1);
            }
        }
    }

    public void setGoogAppNetworkForceReset() {
        Slog.d("IntelligentBatterySaverGoogleAppPolicy", "setGoogAppNetworkForceReset mNetworkLimited = " + this.mNetworkLimited);
        if (this.mNetworkService == null) {
            Slog.d("IntelligentBatterySaverGoogleAppPolicy", "failed to get NetworkManagementService instance");
            return;
        }
        if (this.mNetworkLimited) {
            setGoogleAppsNetworkAllow(true);
        }
        clearAllGoogleAppsList();
    }

    public final void setGoogleAppsNetworkAllow(boolean z) {
        Slog.d("IntelligentBatterySaverGoogleAppPolicy", "setGoogleAppsNetworkAllowallow = " + z);
        if (this.mIBSScpmManager.isGoogleAppPolicyDisabled()) {
            return;
        }
        if (this.mNetworkService == null) {
            Slog.d("IntelligentBatterySaverGoogleAppPolicy", "failed to get NetworkManagementService instance");
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

    public class IBSGoogleAppPolicyReceiver extends BroadcastReceiver {
        public IBSGoogleAppPolicyReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.server.action_google_net_state");
            IntelligentBatterySaverGoogleAppPolicy.this.mContext.registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("com.samsung.android.server.action_google_net_state".equals(intent.getAction())) {
                boolean booleanExtra = intent.getBooleanExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false);
                Slog.v("IntelligentBatterySaverGoogleAppPolicy", "ACTION***" + intent.getAction() + " state = " + booleanExtra);
                if (!booleanExtra) {
                    IntelligentBatterySaverGoogleAppPolicy.this.mHandler.sendEmptyMessage(1);
                } else {
                    IntelligentBatterySaverGoogleAppPolicy.this.mHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    public class IBSGoogleAppPolicyHandler extends Handler {
        public IBSGoogleAppPolicyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                IntelligentBatterySaverGoogleAppPolicy.this.mNetworkLimited = true;
                IntelligentBatterySaverGoogleAppPolicy.this.setGoogleAppsNetworkAllow(false);
            } else {
                if (i != 2) {
                    return;
                }
                IntelligentBatterySaverGoogleAppPolicy.this.mNetworkLimited = false;
                IntelligentBatterySaverGoogleAppPolicy.this.setGoogleAppsNetworkAllow(true);
            }
        }
    }

    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("");
        printWriter.println("IntelligentBatterySaverGoogleAppPolicy ");
        for (int i = 0; i < this.mGoogleAppsList.size(); i++) {
            printWriter.println("SCPM GoogleApps uid " + i + "is " + this.mGoogleAppsList.keyAt(i));
            printWriter.println("SCPM GoogleApps stats " + i + "= " + this.mGoogleAppsList.valueAt(i));
        }
    }
}
