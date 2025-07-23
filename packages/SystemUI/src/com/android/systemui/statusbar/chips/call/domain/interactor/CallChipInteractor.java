package com.android.systemui.statusbar.chips.call.domain.interactor;

import com.android.systemui.statusbar.phone.ongoingcall.data.repository.OngoingCallRepository;
import com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CallChipInteractor {
    public final ReadonlyStateFlow ongoingCallState;

    public CallChipInteractor(CoroutineScope coroutineScope, OngoingCallInteractor ongoingCallInteractor, OngoingCallRepository ongoingCallRepository) {
        ReadonlyStateFlow readonlyStateFlow = ongoingCallRepository.ongoingCallState;
        SharingStarted.Companion.getClass();
        this.ongoingCallState = FlowKt.stateIn(readonlyStateFlow, coroutineScope, SharingStarted.Companion.Lazily, OngoingCallModel.NoCall.INSTANCE);
    }
}
