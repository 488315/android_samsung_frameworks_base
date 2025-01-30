package com.android.systemui.telephony;

import android.telephony.ServiceState;
import android.telephony.TelephonyCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TelephonyCallback extends android.telephony.TelephonyCallback implements TelephonyCallback.ActiveDataSubscriptionIdListener, TelephonyCallback.CallStateListener, TelephonyCallback.ServiceStateListener {
    public final List mActiveDataSubscriptionIdListeners = new ArrayList();
    public final List mCallStateListeners = new ArrayList();
    public final List mServiceStateListeners = new ArrayList();

    public final boolean hasAnyListeners() {
        return (((ArrayList) this.mActiveDataSubscriptionIdListeners).isEmpty() && ((ArrayList) this.mCallStateListeners).isEmpty() && ((ArrayList) this.mServiceStateListeners).isEmpty()) ? false : true;
    }

    @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
    public final void onActiveDataSubscriptionIdChanged(int i) {
        ArrayList arrayList;
        synchronized (this.mActiveDataSubscriptionIdListeners) {
            arrayList = new ArrayList(this.mActiveDataSubscriptionIdListeners);
        }
        arrayList.forEach(new TelephonyCallback$$ExternalSyntheticLambda0(i, 0));
    }

    @Override // android.telephony.TelephonyCallback.CallStateListener
    public final void onCallStateChanged(int i) {
        ArrayList arrayList;
        synchronized (this.mCallStateListeners) {
            arrayList = new ArrayList(this.mCallStateListeners);
        }
        arrayList.forEach(new TelephonyCallback$$ExternalSyntheticLambda0(i, 1));
    }

    @Override // android.telephony.TelephonyCallback.ServiceStateListener
    public final void onServiceStateChanged(final ServiceState serviceState) {
        ArrayList arrayList;
        synchronized (this.mServiceStateListeners) {
            arrayList = new ArrayList(this.mServiceStateListeners);
        }
        arrayList.forEach(new Consumer() { // from class: com.android.systemui.telephony.TelephonyCallback$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((TelephonyCallback.ServiceStateListener) obj).onServiceStateChanged(serviceState);
            }
        });
    }
}
