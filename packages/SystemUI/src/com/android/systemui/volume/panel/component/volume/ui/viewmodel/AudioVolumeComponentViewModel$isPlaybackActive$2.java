package com.android.systemui.volume.panel.component.volume.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class AudioVolumeComponentViewModel$isPlaybackActive$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ AudioVolumeComponentViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioVolumeComponentViewModel$isPlaybackActive$2(AudioVolumeComponentViewModel audioVolumeComponentViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioVolumeComponentViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AudioVolumeComponentViewModel$isPlaybackActive$2 audioVolumeComponentViewModel$isPlaybackActive$2 = new AudioVolumeComponentViewModel$isPlaybackActive$2(this.this$0, continuation);
        audioVolumeComponentViewModel$isPlaybackActive$2.Z$0 = ((Boolean) obj).booleanValue();
        return audioVolumeComponentViewModel$isPlaybackActive$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((AudioVolumeComponentViewModel$isPlaybackActive$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.mutableIsExpanded.updateState(null, Boolean.valueOf(!this.Z$0));
        return Unit.INSTANCE;
    }
}
