package com.android.systemui.volume.panel.component.spatial.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class SpatialAudioViewModel$spatialAudioButton$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SpatialAudioViewModel this$0;

    public SpatialAudioViewModel$spatialAudioButton$1(SpatialAudioViewModel spatialAudioViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = spatialAudioViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SpatialAudioViewModel$spatialAudioButton$1 spatialAudioViewModel$spatialAudioButton$1 = new SpatialAudioViewModel$spatialAudioButton$1(this.this$0, (Continuation) obj3);
        spatialAudioViewModel$spatialAudioButton$1.L$0 = (SpatialAudioEnabledModel) obj;
        spatialAudioViewModel$spatialAudioButton$1.L$1 = (SpatialAudioAvailabilityModel) obj2;
        return spatialAudioViewModel$spatialAudioButton$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SpatialAudioEnabledModel spatialAudioEnabledModel = (SpatialAudioEnabledModel) this.L$0;
        ButtonViewModel access$toViewModel = SpatialAudioViewModel.access$toViewModel(this.this$0, spatialAudioEnabledModel, spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.SpatialAudioEnabled, ((SpatialAudioAvailabilityModel) this.L$1) instanceof SpatialAudioAvailabilityModel.HeadTracking);
        return new ButtonViewModel(access$toViewModel.icon, this.this$0.context.getString(R.string.volume_panel_spatial_audio_title), access$toViewModel.isActive);
    }
}
