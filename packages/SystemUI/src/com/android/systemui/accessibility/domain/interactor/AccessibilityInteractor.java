package com.android.systemui.accessibility.domain.interactor;

import com.android.systemui.accessibility.data.repository.AccessibilityRepository;
import com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

public final class AccessibilityInteractor {
    public final Flow isEnabled;
    public final Flow isTouchExplorationEnabled;

    public AccessibilityInteractor(AccessibilityRepository accessibilityRepository) {
        AccessibilityRepositoryImpl accessibilityRepositoryImpl = (AccessibilityRepositoryImpl) accessibilityRepository;
        this.isTouchExplorationEnabled = accessibilityRepositoryImpl.isTouchExplorationEnabled;
        this.isEnabled = accessibilityRepositoryImpl.isEnabled;
    }
}
