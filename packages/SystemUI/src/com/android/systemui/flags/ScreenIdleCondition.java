package com.android.systemui.flags;

import com.android.systemui.flags.ConditionalRestarter;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import dagger.Lazy;
import kotlinx.coroutines.flow.Flow;

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
