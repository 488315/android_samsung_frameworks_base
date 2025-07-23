package com.android.systemui.navigation.data.repository;

import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NavigationRepository {
    public final NavigationModeController controller;
    public final Flow isGesturalMode = FlowConflatedKt.conflatedCallbackFlow(new NavigationRepository$isGesturalMode$1(this, null));

    public NavigationRepository(NavigationModeController navigationModeController) {
        this.controller = navigationModeController;
    }
}
