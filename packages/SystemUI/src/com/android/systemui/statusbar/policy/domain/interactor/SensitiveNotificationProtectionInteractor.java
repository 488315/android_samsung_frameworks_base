package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class SensitiveNotificationProtectionInteractor {
    public final SensitiveNotificationProtectionController controller;
    public final Flow isSensitiveStateActive = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1(this, null)));

    public SensitiveNotificationProtectionInteractor(SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        this.controller = sensitiveNotificationProtectionController;
    }
}
