package com.android.systemui.navigation.domain.interactor;

import com.android.systemui.navigation.data.repository.NavigationRepository;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class NavigationInteractor {
    public final Flow isGesturalMode;

    public NavigationInteractor(NavigationRepository navigationRepository) {
        this.isGesturalMode = navigationRepository.isGesturalMode;
    }
}
