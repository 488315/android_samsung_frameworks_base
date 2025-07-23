package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SensitiveNotificationProtectionInteractor {
    public final SensitiveNotificationProtectionController controller;
    public final Flow isSensitiveStateActive = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new SensitiveNotificationProtectionInteractor$isSensitiveStateActive$1(this, null)));

    public SensitiveNotificationProtectionInteractor(SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        this.controller = sensitiveNotificationProtectionController;
    }
}
