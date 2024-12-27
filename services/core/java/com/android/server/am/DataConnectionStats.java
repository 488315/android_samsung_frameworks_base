package com.android.server.am;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.RemoteException;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.util.Log;

import com.android.internal.app.IBatteryStats;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DataConnectionStats extends BroadcastReceiver {
    public final Context mContext;
    public final Handler mListenerHandler;
    public final PhoneStateListenerImpl mPhoneStateListener;
    public ServiceState mServiceState;
    public SignalStrength mSignalStrength;
    public int mSimState = 5;
    public int mDataState = 0;
    public int mNrState = 0;
    public final IBatteryStats mBatteryStats = BatteryStatsService.getService();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhoneStateListenerExecutor implements Executor {
        public final Handler mHandler;

        public PhoneStateListenerExecutor(Handler handler) {
            this.mHandler = handler;
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            if (this.mHandler.post(runnable)) {
                return;
            }
            throw new RejectedExecutionException(this.mHandler + " is shutting down");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhoneStateListenerImpl extends PhoneStateListener {
        public PhoneStateListenerImpl(PhoneStateListenerExecutor phoneStateListenerExecutor) {
            super(phoneStateListenerExecutor);
        }

        @Override // android.telephony.PhoneStateListener
        public final void onDataActivity(int i) {
            DataConnectionStats.this.notePhoneDataConnectionState();
        }

        @Override // android.telephony.PhoneStateListener
        public final void onDataConnectionStateChanged(int i, int i2) {
            DataConnectionStats dataConnectionStats = DataConnectionStats.this;
            dataConnectionStats.mDataState = i;
            dataConnectionStats.notePhoneDataConnectionState();
        }

        @Override // android.telephony.PhoneStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            DataConnectionStats dataConnectionStats = DataConnectionStats.this;
            dataConnectionStats.mServiceState = serviceState;
            dataConnectionStats.mNrState = serviceState.getNrState();
            DataConnectionStats.this.notePhoneDataConnectionState();
        }

        @Override // android.telephony.PhoneStateListener
        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            DataConnectionStats.this.mSignalStrength = signalStrength;
        }
    }

    public DataConnectionStats(Context context, Handler handler) {
        this.mContext = context;
        this.mListenerHandler = handler;
        this.mPhoneStateListener =
                new PhoneStateListenerImpl(new PhoneStateListenerExecutor(handler));
    }

    public final void notePhoneDataConnectionState() {
        ServiceState serviceState;
        SignalStrength signalStrength;
        if (this.mServiceState == null) {
            return;
        }
        int i = this.mSimState;
        boolean z =
                ((i != 5
                                        && i != 0
                                        && ((signalStrength = this.mSignalStrength) == null
                                                || signalStrength.isGsm()))
                                || (serviceState = this.mServiceState) == null
                                || serviceState.getState() == 1
                                || this.mServiceState.getState() == 3
                                || this.mDataState != 2)
                        ? false
                        : true;
        NetworkRegistrationInfo networkRegistrationInfo =
                this.mServiceState.getNetworkRegistrationInfo(2, 1);
        try {
            this.mBatteryStats.notePhoneDataConnectionState(
                    networkRegistrationInfo != null
                            ? networkRegistrationInfo.getAccessNetworkTechnology()
                            : 0,
                    z,
                    this.mServiceState.getState(),
                    this.mNrState,
                    this.mServiceState.getNrFrequencyRange());
        } catch (RemoteException e) {
            Log.w("DataConnectionStats", "Error noting data connection state", e);
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constants.SIM_STATE_CHANGED)) {
            String stringExtra = intent.getStringExtra("ss");
            if ("ABSENT".equals(stringExtra)) {
                this.mSimState = 1;
            } else if ("READY".equals(stringExtra)) {
                this.mSimState = 5;
            } else if ("LOCKED".equals(stringExtra)) {
                String stringExtra2 = intent.getStringExtra("reason");
                if ("PIN".equals(stringExtra2)) {
                    this.mSimState = 2;
                } else if ("PUK".equals(stringExtra2)) {
                    this.mSimState = 3;
                } else {
                    this.mSimState = 4;
                }
            } else {
                this.mSimState = 0;
            }
            notePhoneDataConnectionState();
        }
    }
}
