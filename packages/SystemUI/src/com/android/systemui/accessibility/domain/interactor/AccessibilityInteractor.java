package com.android.systemui.accessibility.domain.interactor;

import com.android.systemui.accessibility.data.repository.AccessibilityRepository;
import com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class AccessibilityInteractor {
    public final Flow isEnabled;
    public final Flow isTouchExplorationEnabled;

    public AccessibilityInteractor(AccessibilityRepository accessibilityRepository) {
        AccessibilityRepositoryImpl accessibilityRepositoryImpl = (AccessibilityRepositoryImpl) accessibilityRepository;
        this.isTouchExplorationEnabled = accessibilityRepositoryImpl.isTouchExplorationEnabled;
        this.isEnabled = accessibilityRepositoryImpl.isEnabled;
    }
}
