package com.android.systemui.keyguard.p009ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor;
import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$map$1;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LightRevealScrimViewModel {
    public final SafeFlow lightRevealEffect;
    public final LightRevealScrimInteractor$special$$inlined$map$1 revealAmount;

    public LightRevealScrimViewModel(LightRevealScrimInteractor lightRevealScrimInteractor) {
        this.lightRevealEffect = lightRevealScrimInteractor.lightRevealEffect;
        this.revealAmount = lightRevealScrimInteractor.revealAmount;
    }
}
