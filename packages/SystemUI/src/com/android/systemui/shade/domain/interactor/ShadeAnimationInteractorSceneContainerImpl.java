package com.android.systemui.shade.domain.interactor;

import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.shade.data.repository.ShadeAnimationRepository;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

public final class ShadeAnimationInteractorSceneContainerImpl extends ShadeAnimationInteractor {
    public final ReadonlyStateFlow isAnyCloseAnimationRunning;

    public ShadeAnimationInteractorSceneContainerImpl(CoroutineScope coroutineScope, ShadeAnimationRepository shadeAnimationRepository, SceneInteractor sceneInteractor) {
        super(shadeAnimationRepository);
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.transformLatest(sceneInteractor.transitionState, new ShadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$1(null)));
        SharingStarted.Companion.getClass();
        this.isAnyCloseAnimationRunning = FlowKt.stateIn(distinctUntilChanged, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor
    public final StateFlow isAnyCloseAnimationRunning() {
        return this.isAnyCloseAnimationRunning;
    }
}
