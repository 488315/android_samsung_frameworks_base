package com.android.server.location.injector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.flags.Flags;
import com.android.server.location.LocationServiceThread;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemEmergencyHelper extends EmergencyHelper {
    public final Context mContext;
    public boolean mIsInEmergencyCall;
    public TelephonyManager mTelephonyManager;
    public final EmergencyCallTelephonyCallback mEmergencyCallTelephonyCallback = new EmergencyCallTelephonyCallback();
    public long mEmergencyCallEndRealtimeMs = Long.MIN_VALUE;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EmergencyCallTelephonyCallback extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        public EmergencyCallTelephonyCallback() {
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            if (i == 0) {
                synchronized (SystemEmergencyHelper.this) {
                    try {
                        SystemEmergencyHelper systemEmergencyHelper = SystemEmergencyHelper.this;
                        if (systemEmergencyHelper.mIsInEmergencyCall) {
                            systemEmergencyHelper.mEmergencyCallEndRealtimeMs = SystemClock.elapsedRealtime();
                            SystemEmergencyHelper systemEmergencyHelper2 = SystemEmergencyHelper.this;
                            systemEmergencyHelper2.mIsInEmergencyCall = false;
                            systemEmergencyHelper2.dispatchEmergencyStateChanged();
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public SystemEmergencyHelper(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.location.injector.EmergencyHelper
    public final synchronized boolean isInEmergency(long j) {
        try {
            if (this.mTelephonyManager == null) {
                return false;
            }
            boolean z = this.mEmergencyCallEndRealtimeMs != Long.MIN_VALUE && SystemClock.elapsedRealtime() - this.mEmergencyCallEndRealtimeMs < j;
            if (!Flags.enforceTelephonyFeatureMapping()) {
                return this.mIsInEmergencyCall || z || this.mTelephonyManager.getEmergencyCallbackMode() || this.mTelephonyManager.isInEmergencySmsMode();
            }
            PackageManager packageManager = this.mContext.getPackageManager();
            return this.mIsInEmergencyCall || z || (packageManager.hasSystemFeature("android.hardware.telephony.calling") ? this.mTelephonyManager.getEmergencyCallbackMode() : false) || (packageManager.hasSystemFeature("android.hardware.telephony.messaging") ? this.mTelephonyManager.isInEmergencySmsMode() : false);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void onSystemReady() {
        if (this.mTelephonyManager != null) {
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        Objects.requireNonNull(telephonyManager);
        TelephonyManager telephonyManager2 = telephonyManager;
        this.mTelephonyManager = telephonyManager;
        telephonyManager.registerTelephonyCallback(LocationServiceThread.getExecutor(), this.mEmergencyCallTelephonyCallback);
        final int i = 0;
        this.mContext.registerReceiver(new BroadcastReceiver(this) { // from class: com.android.server.location.injector.SystemEmergencyHelper.1
            public final /* synthetic */ SystemEmergencyHelper this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                switch (i) {
                    case 0:
                        if ("android.intent.action.NEW_OUTGOING_CALL".equals(intent.getAction())) {
                            synchronized (this.this$0) {
                                try {
                                    SystemEmergencyHelper systemEmergencyHelper = this.this$0;
                                    systemEmergencyHelper.mIsInEmergencyCall = systemEmergencyHelper.mTelephonyManager.isEmergencyNumber(intent.getStringExtra("android.intent.extra.PHONE_NUMBER"));
                                    this.this$0.dispatchEmergencyStateChanged();
                                } catch (IllegalStateException e) {
                                    Log.w("LocationManagerService", "Failed to call TelephonyManager.isEmergencyNumber().", e);
                                }
                            }
                            return;
                        }
                        return;
                    default:
                        if ("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(intent.getAction())) {
                            this.this$0.dispatchEmergencyStateChanged();
                            return;
                        }
                        return;
                }
            }
        }, new IntentFilter("android.intent.action.NEW_OUTGOING_CALL"));
        final int i2 = 1;
        this.mContext.registerReceiver(new BroadcastReceiver(this) { // from class: com.android.server.location.injector.SystemEmergencyHelper.1
            public final /* synthetic */ SystemEmergencyHelper this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                switch (i2) {
                    case 0:
                        if ("android.intent.action.NEW_OUTGOING_CALL".equals(intent.getAction())) {
                            synchronized (this.this$0) {
                                try {
                                    SystemEmergencyHelper systemEmergencyHelper = this.this$0;
                                    systemEmergencyHelper.mIsInEmergencyCall = systemEmergencyHelper.mTelephonyManager.isEmergencyNumber(intent.getStringExtra("android.intent.extra.PHONE_NUMBER"));
                                    this.this$0.dispatchEmergencyStateChanged();
                                } catch (IllegalStateException e) {
                                    Log.w("LocationManagerService", "Failed to call TelephonyManager.isEmergencyNumber().", e);
                                }
                            }
                            return;
                        }
                        return;
                    default:
                        if ("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(intent.getAction())) {
                            this.this$0.dispatchEmergencyStateChanged();
                            return;
                        }
                        return;
                }
            }
        }, new IntentFilter("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED"));
    }
}
