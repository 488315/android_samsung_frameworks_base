package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepository;
import com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardSmartspaceInteractor {
    public final ReadonlyStateFlow bcSmartspaceVisibility;
    public final ReadonlyStateFlow isWeatherEnabled;

    public KeyguardSmartspaceInteractor(KeyguardSmartspaceRepository keyguardSmartspaceRepository) {
        KeyguardSmartspaceRepositoryImpl keyguardSmartspaceRepositoryImpl = (KeyguardSmartspaceRepositoryImpl) keyguardSmartspaceRepository;
        this.bcSmartspaceVisibility = keyguardSmartspaceRepositoryImpl.bcSmartspaceVisibility;
        this.isWeatherEnabled = keyguardSmartspaceRepositoryImpl.isWeatherEnabled;
    }
}
