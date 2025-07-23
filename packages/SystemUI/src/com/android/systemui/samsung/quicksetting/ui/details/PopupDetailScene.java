package com.android.systemui.samsung.quicksetting.ui.details;

import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel;
import com.android.systemui.scene.ui.composable.Scene;
import kotlin.NotImplementedError;
import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PopupDetailScene extends ExclusiveActivatable implements Scene {
    public final QuickSettingSceneViewModel viewModel;

    public PopupDetailScene(QuickSettingSceneViewModel quickSettingSceneViewModel) {
        this.viewModel = quickSettingSceneViewModel;
    }

    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    public final Object onActivated(Continuation continuation) {
        throw new NotImplementedError("An operation is not implemented: Not implemented yet");
    }
}
