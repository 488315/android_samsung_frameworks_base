package com.android.systemui.statusbar.chips.call.domain.interactor;

import com.android.systemui.statusbar.phone.ongoingcall.data.repository.OngoingCallRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class CallChipInteractor {
    public final ReadonlyStateFlow ongoingCallState;

    public CallChipInteractor(OngoingCallRepository ongoingCallRepository) {
        this.ongoingCallState = ongoingCallRepository.ongoingCallState;
    }
}
