package com.android.systemui.telephony.domain.interactor;

import com.android.systemui.telephony.data.repository.TelephonyRepository;
import com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TelephonyInteractor {
    public final Flow callState;

    public TelephonyInteractor(TelephonyRepository telephonyRepository) {
        this.callState = ((TelephonyRepositoryImpl) telephonyRepository).callState;
    }
}
