package com.android.systemui.communal;

import android.app.DreamManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlinx.coroutines.CoroutineScope;

public final class CommunalDreamStartable implements CoreStartable {
    public final DreamManager dreamManager;

    public CommunalDreamStartable(PowerInteractor powerInteractor, KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, DreamManager dreamManager, CoroutineScope coroutineScope) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.communalHub();
    }
}
