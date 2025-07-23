package com.android.systemui.blur.domain.interactor;

import com.android.systemui.blur.data.repository.SecBlurSettingsRepository;
import com.android.systemui.blur.data.repository.SecBlurSettingsRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecBlurSettingsInteractor {
    public final ReadonlyStateFlow blurReduced;
    public final ReadonlyStateFlow minimalBatteryUse;

    public SecBlurSettingsInteractor(SecBlurSettingsRepository secBlurSettingsRepository) {
        SecBlurSettingsRepositoryImpl secBlurSettingsRepositoryImpl = (SecBlurSettingsRepositoryImpl) secBlurSettingsRepository;
        this.blurReduced = secBlurSettingsRepositoryImpl.blurReduced;
        this.minimalBatteryUse = secBlurSettingsRepositoryImpl.minimalBatteryUse;
    }
}
