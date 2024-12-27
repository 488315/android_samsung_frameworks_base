package com.android.systemui.telephony.domain.interactor;

import com.android.systemui.telephony.data.repository.TelephonyRepository;
import com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class TelephonyInteractor {
    public final Flow callState;
    public final boolean hasTelephonyRadio;
    public final StateFlow isInCall;

    public TelephonyInteractor(TelephonyRepository telephonyRepository) {
        TelephonyRepositoryImpl telephonyRepositoryImpl = (TelephonyRepositoryImpl) telephonyRepository;
        this.callState = telephonyRepositoryImpl.callState;
        this.isInCall = telephonyRepositoryImpl.isInCall;
        this.hasTelephonyRadio = telephonyRepositoryImpl.hasTelephonyRadio;
    }
}
