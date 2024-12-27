package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineScope;

final class CastVolumeSliderViewModel$onValueChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $newValue;
    int label;
    final /* synthetic */ CastVolumeSliderViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CastVolumeSliderViewModel$onValueChanged$1(CastVolumeSliderViewModel castVolumeSliderViewModel, float f, Continuation continuation) {
        super(2, continuation);
        this.this$0 = castVolumeSliderViewModel;
        this.$newValue = f;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CastVolumeSliderViewModel$onValueChanged$1(this.this$0, this.$newValue, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CastVolumeSliderViewModel$onValueChanged$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CastVolumeSliderViewModel castVolumeSliderViewModel = this.this$0;
            MediaDeviceSessionInteractor mediaDeviceSessionInteractor = castVolumeSliderViewModel.mediaDeviceSessionInteractor;
            MediaDeviceSession mediaDeviceSession = castVolumeSliderViewModel.session;
            int roundToInt = MathKt__MathJVMKt.roundToInt(this.$newValue);
            this.label = 1;
            if (mediaDeviceSessionInteractor.setSessionVolume(mediaDeviceSession, roundToInt, this) == coroutineSingletons) {
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
