package com.android.systemui.navigation.domain.interactor;

import com.android.systemui.navigation.data.repository.NavigationRepository;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NavigationInteractor {
    public final Flow isGesturalMode;

    public NavigationInteractor(NavigationRepository navigationRepository) {
        this.isGesturalMode = navigationRepository.isGesturalMode;
    }
}
