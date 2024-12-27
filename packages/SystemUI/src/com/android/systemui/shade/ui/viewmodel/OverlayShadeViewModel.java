package com.android.systemui.shade.ui.viewmodel;

import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class OverlayShadeViewModel {
    public final ReadonlyStateFlow backgroundScene;
    public final SceneInteractor sceneInteractor;

    public OverlayShadeViewModel(CoroutineScope coroutineScope, SceneInteractor sceneInteractor) {
        FlowKt.stateIn(sceneInteractor.resolveSceneFamily(SceneFamilies.Home), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Scenes.Lockscreen);
    }
}
