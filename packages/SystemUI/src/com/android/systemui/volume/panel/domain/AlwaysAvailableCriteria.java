package com.android.systemui.volume.panel.domain;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AlwaysAvailableCriteria implements ComponentAvailabilityCriteria {
    @Override // com.android.systemui.volume.panel.domain.ComponentAvailabilityCriteria
    public final Flow isAvailable() {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }
}
