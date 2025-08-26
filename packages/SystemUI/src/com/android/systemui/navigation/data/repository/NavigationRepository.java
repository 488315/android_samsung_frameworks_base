package com.android.systemui.navigation.data.repository;

import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class NavigationRepository {
    public final NavigationModeController controller;
    public final Flow isGesturalMode = FlowConflatedKt.conflatedCallbackFlow(new NavigationRepository$isGesturalMode$1(this, null));

    public NavigationRepository(NavigationModeController navigationModeController) {
        this.controller = navigationModeController;
    }
}
