package com.android.systemui.volume.domain.interactor;

import com.android.settingslib.volume.data.repository.AudioSharingRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class AudioSharingInteractorImpl$setStreamVolume$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $level;
    int label;
    final /* synthetic */ AudioSharingInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioSharingInteractorImpl$setStreamVolume$1(AudioSharingInteractorImpl audioSharingInteractorImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioSharingInteractorImpl;
        this.$level = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AudioSharingInteractorImpl$setStreamVolume$1(this.this$0, this.$level, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioSharingInteractorImpl$setStreamVolume$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AudioSharingRepository audioSharingRepository = this.this$0.audioSharingRepository;
            int i2 = this.$level;
            this.label = 1;
            if (audioSharingRepository.setSecondaryVolume(i2, this) == coroutineSingletons) {
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
