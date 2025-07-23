package com.android.systemui.dreams.ui.viewmodel;

import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DreamUserActionsViewModel extends UserActionsViewModel {
    public final DeviceUnlockedInteractor deviceUnlockedInteractor;
    public final ShadeInteractor shadeInteractor;
    public final ShadeModeInteractor shadeModeInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        DreamUserActionsViewModel create();
    }

    public DreamUserActionsViewModel(DeviceUnlockedInteractor deviceUnlockedInteractor, ShadeInteractor shadeInteractor, ShadeModeInteractor shadeModeInteractor) {
        this.deviceUnlockedInteractor = deviceUnlockedInteractor;
        this.shadeInteractor = shadeInteractor;
        this.shadeModeInteractor = shadeModeInteractor;
    }

    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    public final Object hydrateActions(final UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
        Object collect = LatestConflatedKt.flatMapLatestConflated(((ShadeInteractorImpl) this.shadeInteractor).isShadeTouchable, new DreamUserActionsViewModel$hydrateActions$2(this, null)).collect(new FlowCollector() { // from class: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                Function1.this.mo779invoke((Map) obj);
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
