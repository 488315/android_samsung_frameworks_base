package com.android.systemui.telephony.domain.interactor;

import com.android.systemui.telephony.data.repository.TelephonyRepository;
import com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

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
