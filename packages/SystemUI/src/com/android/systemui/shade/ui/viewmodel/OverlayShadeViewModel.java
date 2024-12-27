package com.android.systemui.shade.ui.viewmodel;

import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OverlayShadeViewModel {
    public final ReadonlyStateFlow backgroundScene;
    public final SceneInteractor sceneInteractor;

    public OverlayShadeViewModel(CoroutineScope coroutineScope, SceneInteractor sceneInteractor) {
        FlowKt.stateIn(sceneInteractor.resolveSceneFamily(SceneFamilies.Home), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Scenes.Lockscreen);
    }
}
