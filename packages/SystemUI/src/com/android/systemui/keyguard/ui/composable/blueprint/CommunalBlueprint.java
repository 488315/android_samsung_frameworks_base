package com.android.systemui.keyguard.ui.composable.blueprint;

import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
