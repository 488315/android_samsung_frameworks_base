package com.android.systemui.samsung.quicksetting.ui.settings;

import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.scene.ui.composable.Scene;
import kotlin.NotImplementedError;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public final class SecPanelSettingsScene extends ExclusiveActivatable implements Scene {
    public final SecPanelSettingsViewModel viewModel;

    public SecPanelSettingsScene(SecPanelSettingsViewModel secPanelSettingsViewModel) {
        this.viewModel = secPanelSettingsViewModel;
    }

    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    public final Object onActivated(Continuation continuation) {
        throw new NotImplementedError("An operation is not implemented: Not implemented yet");
    }
}
