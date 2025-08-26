package com.android.systemui.telephony.domain.interactor;

import com.android.systemui.telephony.data.repository.TelephonyRepository;
import com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class TelephonyInteractor {
    public final Flow callState;
    public final ReadonlyStateFlow isInCall;
    public final TelephonyRepository repository;

    public TelephonyInteractor(TelephonyRepository telephonyRepository) {
        this.repository = telephonyRepository;
        TelephonyRepositoryImpl telephonyRepositoryImpl = (TelephonyRepositoryImpl) telephonyRepository;
        this.callState = telephonyRepositoryImpl.callState;
        this.isInCall = telephonyRepositoryImpl.isInCall;
    }
}
