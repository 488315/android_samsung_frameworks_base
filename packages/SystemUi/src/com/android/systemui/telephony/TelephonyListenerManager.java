package com.android.systemui.telephony;

import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        TelephonyManager telephonyManager = this.mTelephonyManager;
        TelephonyCallback telephonyCallback = this.mTelephonyCallback;
        if (!z && telephonyCallback.hasAnyListeners()) {
            this.mListening = true;
            telephonyManager.registerTelephonyCallback(this.mExecutor, telephonyCallback);
        } else {
            if (!this.mListening || telephonyCallback.hasAnyListeners()) {
                return;
            }
            telephonyManager.unregisterTelephonyCallback(telephonyCallback);
            this.mListening = false;
        }
    }
}
