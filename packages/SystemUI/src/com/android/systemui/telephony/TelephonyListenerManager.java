package com.android.systemui.telephony;

import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public final class TelephonyListenerManager {
    public final Executor mExecutor;
    public boolean mListening = false;
    public final TelephonyCallback mTelephonyCallback;
    public final TelephonyManager mTelephonyManager;

    public TelephonyListenerManager(TelephonyManager telephonyManager, Executor executor, TelephonyCallback telephonyCallback) {
        this.mTelephonyManager = telephonyManager;
        this.mExecutor = executor;
        this.mTelephonyCallback = telephonyCallback;
    }

    public final void addActiveDataSubscriptionIdListener(TelephonyCallback.ActiveDataSubscriptionIdListener activeDataSubscriptionIdListener) {
        ((ArrayList) this.mTelephonyCallback.mActiveDataSubscriptionIdListeners).add(activeDataSubscriptionIdListener);
        updateListening();
    }

    public final void updateListening() {
        boolean z = this.mListening;
        TelephonyCallback telephonyCallback = this.mTelephonyCallback;
        if (!z && telephonyCallback.hasAnyListeners()) {
            this.mListening = true;
            this.mTelephonyManager.registerTelephonyCallback(this.mExecutor, telephonyCallback);
        } else {
            if (!this.mListening || telephonyCallback.hasAnyListeners()) {
                return;
            }
            this.mTelephonyManager.unregisterTelephonyCallback(telephonyCallback);
            this.mListening = false;
        }
    }
}
