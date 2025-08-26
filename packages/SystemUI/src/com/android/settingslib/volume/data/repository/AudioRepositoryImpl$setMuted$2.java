package com.android.settingslib.volume.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class AudioRepositoryImpl$setMuted$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $audioStream;
    final /* synthetic */ boolean $isMuted;
    int label;
    final /* synthetic */ AudioRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioRepositoryImpl$setMuted$2(boolean z, AudioRepositoryImpl audioRepositoryImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.$isMuted = z;
        this.this$0 = audioRepositoryImpl;
        this.$audioStream = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AudioRepositoryImpl$setMuted$2(this.$isMuted, this.this$0, this.$audioStream, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioRepositoryImpl$setMuted$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = false;
        if (this.$isMuted != this.this$0.audioManager.isStreamMute(this.$audioStream)) {
            this.this$0.audioManager.adjustStreamVolume(this.$audioStream, this.$isMuted ? -100 : 100, 0);
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
