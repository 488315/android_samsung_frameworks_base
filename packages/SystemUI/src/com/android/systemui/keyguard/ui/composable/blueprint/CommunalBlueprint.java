package com.android.systemui.keyguard.ui.composable.blueprint;

import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;

public final class CommunalBlueprint implements ComposableLockscreenSceneBlueprint {
    public final String id = "communal";
    public final LockscreenContentViewModel viewModel;

    public CommunalBlueprint(LockscreenContentViewModel lockscreenContentViewModel) {
    }

    @Override // com.android.systemui.keyguard.ui.composable.blueprint.ComposableLockscreenSceneBlueprint
    public final String getId() {
        return this.id;
    }
}
