package com.android.systemui.blur.domain.interactor;

import com.android.systemui.blur.data.repository.SecBlurSettingsRepository;
import com.android.systemui.blur.data.repository.SecBlurSettingsRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

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
