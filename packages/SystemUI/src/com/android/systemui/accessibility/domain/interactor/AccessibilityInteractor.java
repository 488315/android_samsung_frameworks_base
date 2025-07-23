package com.android.systemui.accessibility.domain.interactor;

import com.android.systemui.accessibility.data.repository.AccessibilityRepository;
import com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
