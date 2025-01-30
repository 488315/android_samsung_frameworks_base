package com.samsung.iqi;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.INetd;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.SystemServiceManager;
import com.att.iqi.lib.IQIManager;
import com.att.iqi.lib.metrics.ss.SS2S;

/* loaded from: classes2.dex */
public class IQIServiceBrokerExt {
    public ContentResolver mContentResolver;
    public Context mContext;
    public boolean mServiceRunning = false;
    public boolean DEBUG = "eng".equals(Build.TYPE);
    public final Handler mHandler = new Handler();
    public boolean mIsOptOutTriggered = false;
    public Runnable rbIqiState = new Runnable() { // from class: com.samsung.iqi.IQIServiceBrokerExt.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (IQIServiceBrokerExt.this.mLock) {
                if (IQIServiceBrokerExt.this.mIsOptOutTriggered) {
                    IQIServiceBrokerExt.this.mIsOptOutTriggered = false;
                    IQIServiceBrokerExt.this.changeIqiState(false);
                    IQIServiceBrokerExt.this.mContext.unregisterReceiver(IQIServiceBrokerExt.this.mUploadStateReceiver);
                }
            }
        }
    };
    public ContentObserver mObserver = new ContentObserver(new Handler()) { // from class: com.samsung.iqi.IQIServiceBrokerExt.2
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            int opt = IQIServiceBrokerExt.this.getOpt();
            if (IQIServiceBrokerExt.this.DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("onChange: opt=");
                sb.append(opt);
                sb.append(" service=");
                sb.append(IQIServiceBrokerExt.this.mServiceRunning ? INetd.IF_FLAG_RUNNING : "stopped");
                Slog.d("IQIServiceBrokerExt", sb.toString());
            }
            if (opt == 0 && IQIServiceBrokerExt.this.mServiceRunning) {
                synchronized (IQIServiceBrokerExt.this.mLock) {
                    IQIServiceBrokerExt.this.mIsOptOutTriggered = true;
                }
                IQIServiceBrokerExt.this.mContext.registerReceiver(IQIServiceBrokerExt.this.mUploadStateReceiver, new IntentFilter("com.att.iqi.action.UPLOAD_COMPLETE"));
                IQIServiceBrokerExt.submitSS2S(false);
                IQIServiceBrokerExt.this.mHandler.postDelayed(IQIServiceBrokerExt.this.rbIqiState, 30000L);
                return;
            }
            if (opt == 1) {
                if (IQIServiceBrokerExt.this.mServiceRunning) {
                    IQIServiceBrokerExt.this.mHandler.removeCallbacks(IQIServiceBrokerExt.this.rbIqiState);
                    IQIServiceBrokerExt.submitSS2S(true);
                } else {
                    IQIServiceBrokerExt.this.changeIqiState(true);
                }
            }
        }
    };
    public BroadcastReceiver mUploadStateReceiver = new BroadcastReceiver() { // from class: com.samsung.iqi.IQIServiceBrokerExt.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (IQIServiceBrokerExt.this.DEBUG) {
                Slog.d("IQIServiceBrokerExt", "mUploadStateReceiver upload done");
            }
            synchronized (IQIServiceBrokerExt.this.mLock) {
                if (IQIServiceBrokerExt.this.mIsOptOutTriggered) {
                    IQIServiceBrokerExt.this.mIsOptOutTriggered = false;
                    IQIServiceBrokerExt.this.changeIqiState(false);
                    IQIServiceBrokerExt.this.mHandler.removeCallbacks(IQIServiceBrokerExt.this.rbIqiState);
                    IQIServiceBrokerExt.this.mContext.unregisterReceiver(IQIServiceBrokerExt.this.mUploadStateReceiver);
                }
            }
        }
    };
    public serviceStateListnerForIQI mServiceStateListnerForIQI = null;
    public Object mLock = new Object();

    public final void changeIqiState(boolean z) {
        if (z) {
            if (this.DEBUG) {
                Slog.d("IQIServiceBrokerExt", "changeIqiState: newState= " + z);
            }
            if (ServiceManager.getService("iqi") == null) {
                ((SystemServiceManager) LocalServices.getService(SystemServiceManager.class)).startService("com.att.iqi.libs.IQIServiceBroker").onBootPhase(600);
                registerIQIServiceStateListener();
            }
            Intent intent = new Intent("com.att.iqi.action.CHANGE_IQI_STATE");
            intent.putExtra("com.att.iqi.extra.IQI_STATE", z);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
            return;
        }
        Intent intent2 = new Intent("com.att.iqi.action.CHANGE_IQI_STATE");
        intent2.putExtra("com.att.iqi.extra.IQI_STATE", z);
        this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT);
    }

    public final void registerIQIServiceStateListener() {
        IQIManager iQIManager = IQIManager.getInstance();
        if (iQIManager == null || this.mServiceStateListnerForIQI != null) {
            return;
        }
        if (this.DEBUG) {
            Slog.d("IQIServiceBrokerExt", "Register IQI Service State Change Listener");
        }
        serviceStateListnerForIQI servicestatelistnerforiqi = new serviceStateListnerForIQI();
        this.mServiceStateListnerForIQI = servicestatelistnerforiqi;
        iQIManager.registerServiceStateChangeListener(servicestatelistnerforiqi);
    }

    public static void submitSS2S(boolean z) {
        IQIManager iQIManager = IQIManager.getInstance();
        if (iQIManager == null || !iQIManager.shouldSubmitMetric(SS2S.f1766ID)) {
            return;
        }
        SS2S ss2s = new SS2S();
        ss2s.setSetting(0, !z ? (byte) 1 : (byte) 0);
        iQIManager.submitMetric(ss2s);
    }

    public final int getOpt() {
        int i = Settings.System.getInt(this.mContentResolver, "att_iqi_report_diagnostic", -1);
        if (i != -1) {
            return i;
        }
        setOpt(1);
        return 1;
    }

    public final void setOpt(int i) {
        Settings.System.putInt(this.mContentResolver, "att_iqi_report_diagnostic", i);
    }

    public IQIServiceBrokerExt(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
    }

    public void startIqi() {
        int opt = getOpt();
        if (this.DEBUG) {
            Slog.d("IQIServiceBrokerExt", "startIqi opt=" + opt);
        }
        if (opt == 2) {
            return;
        }
        this.mContentResolver.registerContentObserver(Settings.System.getUriFor("att_iqi_report_diagnostic"), false, this.mObserver);
        ((SystemServiceManager) LocalServices.getService(SystemServiceManager.class)).startService("com.att.iqi.libs.IQIServiceBroker");
        registerIQIServiceStateListener();
    }

    public class serviceStateListnerForIQI implements IQIManager.ServiceStateChangeListener {
        public serviceStateListnerForIQI() {
        }

        @Override // com.att.iqi.lib.IQIManager.ServiceStateChangeListener
        public void onServiceChange(boolean z) {
            IQIServiceBrokerExt.this.mServiceRunning = z;
            if (IQIServiceBrokerExt.this.DEBUG) {
                Slog.d("IQIServiceBrokerExt", "onIQIServiceChangeCallback : IQIService enable: " + z);
            }
            if (IQIServiceBrokerExt.this.DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("onIQIServiceChangeCallback : newServiceState ");
                sb.append(IQIServiceBrokerExt.this.mServiceRunning ? INetd.IF_FLAG_RUNNING : "stopped");
                Slog.d("IQIServiceBrokerExt", sb.toString());
            }
            if (z) {
                IQIManager iQIManager = IQIManager.getInstance();
                if (iQIManager != null && iQIManager.setUnlockCode(2023L) && IQIServiceBrokerExt.this.DEBUG) {
                    Slog.d("IQIServiceBrokerExt", "master unlock key was successfully set: #*2023#");
                }
                IQIServiceBrokerExt.submitSS2S(true);
            }
        }
    }
}
