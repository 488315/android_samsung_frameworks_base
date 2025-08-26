package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class VolumeDialogSliderViewModel$model$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ VolumeDialogSliderViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSliderViewModel$model$1(VolumeDialogSliderViewModel volumeDialogSliderViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = volumeDialogSliderViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogSliderViewModel$model$1 volumeDialogSliderViewModel$model$1 = new VolumeDialogSliderViewModel$model$1(this.this$0, (Continuation) obj3);
        volumeDialogSliderViewModel$model$1.L$0 = (VolumeDialogStreamModel) obj;
        volumeDialogSliderViewModel$model$1.L$1 = (VolumeDialogSliderViewModel.VolumeUpdate) obj2;
        return volumeDialogSliderViewModel$model$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) this.L$0;
        VolumeDialogSliderViewModel.VolumeUpdate volumeUpdate = (VolumeDialogSliderViewModel.VolumeUpdate) this.L$1;
        return (volumeUpdate != null && this.this$0.systemClock.uptimeMillis() - volumeUpdate.timestampMillis < 1000) ? VolumeDialogStreamModel.copy$default(volumeDialogStreamModel, volumeUpdate.newVolumeLevel) : volumeDialogStreamModel;
    }
}
