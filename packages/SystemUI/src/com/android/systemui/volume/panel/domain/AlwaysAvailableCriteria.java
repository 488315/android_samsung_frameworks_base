package com.android.systemui.volume.panel.domain;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AlwaysAvailableCriteria implements ComponentAvailabilityCriteria {
    @Override // com.android.systemui.volume.panel.domain.ComponentAvailabilityCriteria
    public final Flow isAvailable() {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }
}
