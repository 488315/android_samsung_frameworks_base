package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepository;
import com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSmartspaceInteractor {
    public final ReadonlyStateFlow bcSmartspaceVisibility;
    public final ReadonlyStateFlow isWeatherEnabled;
    public final KeyguardSmartspaceRepository keyguardSmartspaceRepository;

    public KeyguardSmartspaceInteractor(KeyguardSmartspaceRepository keyguardSmartspaceRepository) {
        this.keyguardSmartspaceRepository = keyguardSmartspaceRepository;
        KeyguardSmartspaceRepositoryImpl keyguardSmartspaceRepositoryImpl = (KeyguardSmartspaceRepositoryImpl) keyguardSmartspaceRepository;
        this.bcSmartspaceVisibility = keyguardSmartspaceRepositoryImpl.bcSmartspaceVisibility;
        this.isWeatherEnabled = keyguardSmartspaceRepositoryImpl.isWeatherEnabled;
    }
}
