package com.android.systemui.samsung.quicksetting.ui.panel;

import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.scene.ui.composable.Scene;
import kotlin.NotImplementedError;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public final class SecQuickSettingSceneMain extends ExclusiveActivatable implements Scene {
    public final QuickSettingSceneViewModel viewModel;

    public SecQuickSettingSceneMain(QuickSettingSceneViewModel quickSettingSceneViewModel) {
        this.viewModel = quickSettingSceneViewModel;
    }

    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    public final Object onActivated(Continuation continuation) {
        throw new NotImplementedError("An operation is not implemented: Not implemented yet");
    }
}
