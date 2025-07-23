package com.android.systemui.telephony;

import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TelephonyListenerManager {
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
        if (!this.mListening && this.mTelephonyCallback.hasAnyListeners()) {
            this.mListening = true;
            this.mTelephonyManager.registerTelephonyCallback(this.mExecutor, this.mTelephonyCallback);
        } else {
            if (!this.mListening || this.mTelephonyCallback.hasAnyListeners()) {
                return;
            }
            this.mTelephonyManager.unregisterTelephonyCallback(this.mTelephonyCallback);
            this.mListening = false;
        }
    }
}
