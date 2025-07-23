package com.android.systemui.volume.dialog.sliders.ui;

import androidx.compose.foundation.interaction.DragInteraction$Cancel;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.DragInteraction$Stop;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderViewModel;
import com.android.systemui.volume.dialog.ui.VolumeDialogUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogSliderViewBinderKt$VolumeDialogSlider$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableInteractionSource $interactionSource;
    final /* synthetic */ VolumeDialogSliderViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSliderViewBinderKt$VolumeDialogSlider$1$1(MutableInteractionSource mutableInteractionSource, VolumeDialogSliderViewModel volumeDialogSliderViewModel, Continuation continuation) {
        super(2, continuation);
        this.$interactionSource = mutableInteractionSource;
        this.$viewModel = volumeDialogSliderViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumeDialogSliderViewBinderKt$VolumeDialogSlider$1$1(this.$interactionSource, this.$viewModel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogSliderViewBinderKt$VolumeDialogSlider$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SharedFlowImpl interactions = this.$interactionSource.getInteractions();
            final VolumeDialogSliderViewModel volumeDialogSliderViewModel = this.$viewModel;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$1$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Interaction interaction = (Interaction) obj2;
                    boolean z = interaction instanceof DragInteraction$Start;
                    VolumeDialogSliderViewModel volumeDialogSliderViewModel2 = VolumeDialogSliderViewModel.this;
                    if (z) {
                        volumeDialogSliderViewModel2.uiEventLogger.log(VolumeDialogUiEvent.VOLUME_DIALOG_SLIDER_STARTED_TRACKING_TOUCH);
                    } else if (interaction instanceof DragInteraction$Cancel) {
                        volumeDialogSliderViewModel2.uiEventLogger.log(VolumeDialogUiEvent.VOLUME_DIALOG_SLIDER_STOPPED_TRACKING_TOUCH);
                    } else if (interaction instanceof DragInteraction$Stop) {
                        volumeDialogSliderViewModel2.uiEventLogger.log(VolumeDialogUiEvent.VOLUME_DIALOG_SLIDER_STOPPED_TRACKING_TOUCH);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            interactions.getClass();
            if (SharedFlowImpl.collect$suspendImpl(interactions, flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
