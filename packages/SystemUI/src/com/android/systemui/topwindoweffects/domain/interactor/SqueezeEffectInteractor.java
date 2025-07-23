package com.android.systemui.topwindoweffects.domain.interactor;

import com.android.systemui.topwindoweffects.data.repository.SqueezeEffectRepository;
import com.android.systemui.topwindoweffects.data.repository.SqueezeEffectRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SqueezeEffectInteractor {
    public final Flow isSqueezeEffectEnabled;

    public SqueezeEffectInteractor(SqueezeEffectRepository squeezeEffectRepository) {
        this.isSqueezeEffectEnabled = ((SqueezeEffectRepositoryImpl) squeezeEffectRepository).isSqueezeEffectEnabled;
    }
}
