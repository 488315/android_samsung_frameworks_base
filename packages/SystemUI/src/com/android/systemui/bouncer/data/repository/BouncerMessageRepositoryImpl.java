package com.android.systemui.bouncer.data.repository;

import android.hardware.biometrics.BiometricSourceType;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class BouncerMessageRepositoryImpl implements BouncerMessageRepository {
    public final StateFlowImpl _bouncerMessage;
    public final StateFlowImpl bouncerMessage;
    public BiometricSourceType messageSource;

    public BouncerMessageRepositoryImpl() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new BouncerMessageModel(null, null, 3, null));
        this._bouncerMessage = stateFlowImplMutableStateFlow;
        this.bouncerMessage = stateFlowImplMutableStateFlow;
    }
}
