package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.media.session.MediaController;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.IntRange;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class CastVolumeSliderViewModel$slider$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaController.PlaybackInfo $it;
    int label;
    final /* synthetic */ CastVolumeSliderViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CastVolumeSliderViewModel$slider$1$1(CastVolumeSliderViewModel castVolumeSliderViewModel, MediaController.PlaybackInfo playbackInfo, Continuation continuation) {
        super(2, continuation);
        this.this$0 = castVolumeSliderViewModel;
        this.$it = playbackInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CastVolumeSliderViewModel$slider$1$1(this.this$0, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CastVolumeSliderViewModel$slider$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CastVolumeSliderViewModel castVolumeSliderViewModel = this.this$0;
        MediaController.PlaybackInfo playbackInfo = this.$it;
        castVolumeSliderViewModel.getClass();
        IntRange intRange = new IntRange(0, playbackInfo.getMaxVolume());
        return new CastVolumeSliderViewModel.State(playbackInfo.getCurrentVolume(), new ClosedFloatRange(intRange.first, intRange.last), castVolumeSliderViewModel.castIcon, castVolumeSliderViewModel.castLabel, true, 1.0f);
    }
}
