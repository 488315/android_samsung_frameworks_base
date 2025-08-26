package com.android.systemui.shade.domain.interactor;

import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.shade.data.repository.ShadeAnimationRepository;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class ShadeAnimationInteractorSceneContainerImpl extends ShadeAnimationInteractor {
    public final ReadonlyStateFlow isAnyCloseAnimationRunning;

    public ShadeAnimationInteractorSceneContainerImpl(CoroutineScope coroutineScope, ShadeAnimationRepository shadeAnimationRepository, SceneInteractor sceneInteractor) {
        super(shadeAnimationRepository);
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.transformLatest(sceneInteractor.transitionState, new ShadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$1(null)));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        this.isAnyCloseAnimationRunning = FlowKt.stateIn(flowDistinctUntilChanged, coroutineScope, startedEagerly, bool);
        FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.transformLatest(sceneInteractor.transitionState, new ShadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2(null))), coroutineScope, startedEagerly, bool);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor
    public final StateFlow isAnyCloseAnimationRunning() {
        return this.isAnyCloseAnimationRunning;
    }
}
