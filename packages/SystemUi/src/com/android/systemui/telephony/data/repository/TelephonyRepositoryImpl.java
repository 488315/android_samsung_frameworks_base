package com.android.systemui.telephony.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.telephony.TelephonyListenerManager;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TelephonyRepositoryImpl implements TelephonyRepository {
    public final Flow callState;
    public final TelephonyListenerManager manager;

    public TelephonyRepositoryImpl(TelephonyListenerManager telephonyListenerManager) {
        this.manager = telephonyListenerManager;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        TelephonyRepositoryImpl$callState$1 telephonyRepositoryImpl$callState$1 = new TelephonyRepositoryImpl$callState$1(this, null);
        conflatedCallbackFlow.getClass();
        this.callState = ConflatedCallbackFlow.conflatedCallbackFlow(telephonyRepositoryImpl$callState$1);
    }
}
