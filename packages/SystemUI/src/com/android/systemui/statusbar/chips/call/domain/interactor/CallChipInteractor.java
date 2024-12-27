package com.android.systemui.statusbar.chips.call.domain.interactor;

import com.android.systemui.statusbar.phone.ongoingcall.data.repository.OngoingCallRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CallChipInteractor {
    public final ReadonlyStateFlow ongoingCallState;

    public CallChipInteractor(OngoingCallRepository ongoingCallRepository) {
        this.ongoingCallState = ongoingCallRepository.ongoingCallState;
    }
}
