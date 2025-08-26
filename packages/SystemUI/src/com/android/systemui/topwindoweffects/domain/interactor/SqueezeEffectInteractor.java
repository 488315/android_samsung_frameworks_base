package com.android.systemui.topwindoweffects.domain.interactor;

import com.android.systemui.topwindoweffects.data.repository.SqueezeEffectRepository;
import com.android.systemui.topwindoweffects.data.repository.SqueezeEffectRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final class SqueezeEffectInteractor {
    public final Flow isSqueezeEffectEnabled;

    public SqueezeEffectInteractor(SqueezeEffectRepository squeezeEffectRepository) {
        this.isSqueezeEffectEnabled = ((SqueezeEffectRepositoryImpl) squeezeEffectRepository).isSqueezeEffectEnabled;
    }
}
