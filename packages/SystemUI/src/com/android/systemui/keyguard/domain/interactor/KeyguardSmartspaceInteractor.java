package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepository;
import com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

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
