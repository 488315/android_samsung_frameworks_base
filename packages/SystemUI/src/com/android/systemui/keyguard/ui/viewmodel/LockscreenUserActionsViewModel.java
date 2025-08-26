package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class LockscreenUserActionsViewModel extends UserActionsViewModel {
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final SceneContainerOcclusionInteractor occlusionInteractor;
    public final ShadeInteractor shadeInteractor;
    public final ShadeModeInteractor shadeModeInteractor;

    public interface Factory {
        LockscreenUserActionsViewModel create();
    }

    public LockscreenUserActionsViewModel(DeviceEntryInteractor deviceEntryInteractor, ShadeInteractor shadeInteractor, ShadeModeInteractor shadeModeInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor) {
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.shadeInteractor = shadeInteractor;
        this.shadeModeInteractor = shadeModeInteractor;
        this.occlusionInteractor = sceneContainerOcclusionInteractor;
    }

    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    public final Object hydrateActions(final UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
        Object objCollect = FlowKt.transformLatest(((ShadeInteractorImpl) this.shadeInteractor).isShadeTouchable, new LockscreenUserActionsViewModel$hydrateActions$$inlined$flatMapLatest$1(null, this)).collect(new FlowCollector() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenUserActionsViewModel.hydrateActions.3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                userActionsViewModel$$ExternalSyntheticLambda0.mo781invoke((Map) obj);
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
