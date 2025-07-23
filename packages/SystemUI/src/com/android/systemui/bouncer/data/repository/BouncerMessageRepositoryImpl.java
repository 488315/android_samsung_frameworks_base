package com.android.systemui.bouncer.data.repository;

import android.hardware.biometrics.BiometricSourceType;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerMessageRepositoryImpl implements BouncerMessageRepository {
    public final StateFlowImpl _bouncerMessage;
    public final StateFlowImpl bouncerMessage;
    public BiometricSourceType messageSource;

    public BouncerMessageRepositoryImpl() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new BouncerMessageModel(null, null, 3, null));
        this._bouncerMessage = MutableStateFlow;
        this.bouncerMessage = MutableStateFlow;
    }
}
