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

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemServiceManager;

import com.att.iqi.lib.IQIManager;
import com.att.iqi.lib.metrics.ss.SS2S;

public final class IQIServiceBrokerExt {
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public boolean mServiceRunning = false;
    public final boolean DEBUG = "eng".equals(Build.TYPE);
    public final Handler mHandler = new Handler();
    public boolean mIsOptOutTriggered = false;
    public final AnonymousClass1 rbIqiState =
            new Runnable() { // from class: com.samsung.iqi.IQIServiceBrokerExt.1
                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (IQIServiceBrokerExt.this.mLock) {
                        try {
                            IQIServiceBrokerExt iQIServiceBrokerExt = IQIServiceBrokerExt.this;
                            if (iQIServiceBrokerExt.mIsOptOutTriggered) {
                                iQIServiceBrokerExt.mIsOptOutTriggered = false;
                                IQIServiceBrokerExt.m1234$$Nest$mchangeIqiState(
                                        iQIServiceBrokerExt, false);
                                IQIServiceBrokerExt iQIServiceBrokerExt2 = IQIServiceBrokerExt.this;
                                iQIServiceBrokerExt2.mContext.unregisterReceiver(
                                        iQIServiceBrokerExt2.mUploadStateReceiver);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };
    public final AnonymousClass2 mObserver =
            new ContentObserver(
                    new Handler()) { // from class: com.samsung.iqi.IQIServiceBrokerExt.2
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri, int i) {
                    IQIServiceBrokerExt iQIServiceBrokerExt;
                    IQIServiceBrokerExt iQIServiceBrokerExt2 = IQIServiceBrokerExt.this;
                    int i2 =
                            Settings.System.getInt(
                                    iQIServiceBrokerExt2.mContentResolver,
                                    "att_iqi_report_diagnostic",
                                    -1);
                    if (i2 == -1) {
                        Settings.System.putInt(
                                iQIServiceBrokerExt2.mContentResolver,
                                "att_iqi_report_diagnostic",
                                1);
                        i2 = 1;
                    }
                    if (IQIServiceBrokerExt.this.DEBUG) {
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        i2, "onChange: opt=", " service="),
                                IQIServiceBrokerExt.this.mServiceRunning
                                        ? INetd.IF_FLAG_RUNNING
                                        : "stopped",
                                "IQIServiceBrokerExt");
                    }
                    if (i2 == 0) {
                        IQIServiceBrokerExt iQIServiceBrokerExt3 = IQIServiceBrokerExt.this;
                        if (iQIServiceBrokerExt3.mServiceRunning) {
                            synchronized (iQIServiceBrokerExt3.mLock) {
                                iQIServiceBrokerExt = IQIServiceBrokerExt.this;
                                iQIServiceBrokerExt.mIsOptOutTriggered = true;
                            }
                            iQIServiceBrokerExt.mContext.registerReceiver(
                                    iQIServiceBrokerExt.mUploadStateReceiver,
                                    new IntentFilter("com.att.iqi.action.UPLOAD_COMPLETE"));
                            IQIServiceBrokerExt.m1235$$Nest$smsubmitSS2S(false);
                            IQIServiceBrokerExt iQIServiceBrokerExt4 = IQIServiceBrokerExt.this;
                            iQIServiceBrokerExt4.mHandler.postDelayed(
                                    iQIServiceBrokerExt4.rbIqiState, 30000L);
                            return;
                        }
                    }
                    if (i2 == 1) {
                        IQIServiceBrokerExt iQIServiceBrokerExt5 = IQIServiceBrokerExt.this;
                        if (!iQIServiceBrokerExt5.mServiceRunning) {
                            IQIServiceBrokerExt.m1234$$Nest$mchangeIqiState(
                                    iQIServiceBrokerExt5, true);
                        } else {
                            iQIServiceBrokerExt5.mHandler.removeCallbacks(
                                    iQIServiceBrokerExt5.rbIqiState);
                            IQIServiceBrokerExt.m1235$$Nest$smsubmitSS2S(true);
                        }
                    }
                }
            };
    public final AnonymousClass3 mUploadStateReceiver =
            new BroadcastReceiver() { // from class: com.samsung.iqi.IQIServiceBrokerExt.3
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (IQIServiceBrokerExt.this.DEBUG) {
                        Slog.d("IQIServiceBrokerExt", "mUploadStateReceiver upload done");
                    }
                    synchronized (IQIServiceBrokerExt.this.mLock) {
                        try {
                            IQIServiceBrokerExt iQIServiceBrokerExt = IQIServiceBrokerExt.this;
                            if (iQIServiceBrokerExt.mIsOptOutTriggered) {
                                iQIServiceBrokerExt.mIsOptOutTriggered = false;
                                IQIServiceBrokerExt.m1234$$Nest$mchangeIqiState(
                                        iQIServiceBrokerExt, false);
                                IQIServiceBrokerExt iQIServiceBrokerExt2 = IQIServiceBrokerExt.this;
                                iQIServiceBrokerExt2.mHandler.removeCallbacks(
                                        iQIServiceBrokerExt2.rbIqiState);
                                IQIServiceBrokerExt iQIServiceBrokerExt3 = IQIServiceBrokerExt.this;
                                iQIServiceBrokerExt3.mContext.unregisterReceiver(
                                        iQIServiceBrokerExt3.mUploadStateReceiver);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };
    public serviceStateListnerForIQI mServiceStateListnerForIQI = null;
    public final Object mLock = new Object();

    public final class serviceStateListnerForIQI implements IQIManager.ServiceStateChangeListener {
        public serviceStateListnerForIQI() {}

        @Override // com.att.iqi.lib.IQIManager.ServiceStateChangeListener
        public final void onServiceChange(boolean z) {
            IQIServiceBrokerExt iQIServiceBrokerExt = IQIServiceBrokerExt.this;
            iQIServiceBrokerExt.mServiceRunning = z;
            if (iQIServiceBrokerExt.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "onIQIServiceChangeCallback : IQIService enable: ",
                        "IQIServiceBrokerExt",
                        z);
            }
            if (iQIServiceBrokerExt.DEBUG) {
                Slog.d(
                        "IQIServiceBrokerExt",
                        "onIQIServiceChangeCallback : newServiceState "
                                .concat(
                                        iQIServiceBrokerExt.mServiceRunning
                                                ? INetd.IF_FLAG_RUNNING
                                                : "stopped"));
            }
            if (z) {
                IQIManager iQIManager = IQIManager.getInstance();
                if (iQIManager != null
                        && iQIManager.setUnlockCode(2023L)
                        && iQIServiceBrokerExt.DEBUG) {
                    Slog.d("IQIServiceBrokerExt", "main unlock key was successfully set: #*2023#");
                }
                IQIServiceBrokerExt.m1235$$Nest$smsubmitSS2S(true);
            }
        }
    }

    /* renamed from: -$$Nest$mchangeIqiState, reason: not valid java name */
    public static void m1234$$Nest$mchangeIqiState(
            IQIServiceBrokerExt iQIServiceBrokerExt, boolean z) {
        if (!z) {
            iQIServiceBrokerExt.getClass();
            Intent intent = new Intent("com.att.iqi.action.CHANGE_IQI_STATE");
            intent.putExtra("com.att.iqi.extra.IQI_STATE", z);
            iQIServiceBrokerExt.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
            return;
        }
        if (iQIServiceBrokerExt.DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "changeIqiState: newState= ", "IQIServiceBrokerExt", z);
        }
        if (ServiceManager.getService("iqi") == null) {
            ((SystemServiceManager) LocalServices.getService(SystemServiceManager.class))
                    .startService("com.att.iqi.libs.IQIServiceBroker")
                    .onBootPhase(600);
            iQIServiceBrokerExt.registerIQIServiceStateListener();
        }
        Intent intent2 = new Intent("com.att.iqi.action.CHANGE_IQI_STATE");
        intent2.putExtra("com.att.iqi.extra.IQI_STATE", z);
        iQIServiceBrokerExt.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT);
    }

    /* renamed from: -$$Nest$smsubmitSS2S, reason: not valid java name */
    public static void m1235$$Nest$smsubmitSS2S(boolean z) {
        IQIManager iQIManager = IQIManager.getInstance();
        if (iQIManager == null || !iQIManager.shouldSubmitMetric(SS2S.ID)) {
            return;
        }
        SS2S ss2s = new SS2S();
        ss2s.setSetting(0, !z ? (byte) 1 : (byte) 0);
        iQIManager.submitMetric(ss2s);
    }

    public IQIServiceBrokerExt(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
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

    public final void startIqi() {
        int i = Settings.System.getInt(this.mContentResolver, "att_iqi_report_diagnostic", -1);
        if (i == -1) {
            Settings.System.putInt(this.mContentResolver, "att_iqi_report_diagnostic", 1);
            i = 1;
        }
        if (this.DEBUG) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "startIqi opt=", "IQIServiceBrokerExt");
        }
        if (i == 2) {
            return;
        }
        this.mContentResolver.registerContentObserver(
                Settings.System.getUriFor("att_iqi_report_diagnostic"), false, this.mObserver);
        ((SystemServiceManager) LocalServices.getService(SystemServiceManager.class))
                .startService("com.att.iqi.libs.IQIServiceBroker");
        registerIQIServiceStateListener();
    }
}
