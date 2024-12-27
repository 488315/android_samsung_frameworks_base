package com.android.systemui.flags;

import com.android.systemui.flags.ConditionalRestarter;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import dagger.Lazy;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenIdleCondition implements ConditionalRestarter.Condition {
    public final Lazy powerInteractorLazy;

    public ScreenIdleCondition(Lazy lazy) {
        this.powerInteractorLazy = lazy;
    }

    @Override // com.android.systemui.flags.ConditionalRestarter.Condition
    public final Flow getCanRestartNow() {
        return ((PowerInteractor) this.powerInteractorLazy.get()).isAsleep;
    }
}
