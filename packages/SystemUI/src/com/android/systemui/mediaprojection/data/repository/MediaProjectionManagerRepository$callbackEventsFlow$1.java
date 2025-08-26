package com.android.systemui.mediaprojection.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class MediaProjectionManagerRepository$callbackEventsFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaProjectionManagerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionManagerRepository$callbackEventsFlow$1(MediaProjectionManagerRepository mediaProjectionManagerRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaProjectionManagerRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaProjectionManagerRepository$callbackEventsFlow$1 mediaProjectionManagerRepository$callbackEventsFlow$1 = new MediaProjectionManagerRepository$callbackEventsFlow$1(this.this$0, continuation);
        mediaProjectionManagerRepository$callbackEventsFlow$1.L$0 = obj;
        return mediaProjectionManagerRepository$callbackEventsFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaProjectionManagerRepository$callbackEventsFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final MediaProjectionManagerRepository$callbackEventsFlow$1$callback$1 mediaProjectionManagerRepository$callbackEventsFlow$1$callback$1 = new MediaProjectionManagerRepository$callbackEventsFlow$1$callback$1(this.this$0, producerScope);
            MediaProjectionManagerRepository mediaProjectionManagerRepository = this.this$0;
            mediaProjectionManagerRepository.mediaProjectionManager.addCallback(mediaProjectionManagerRepository$callbackEventsFlow$1$callback$1, mediaProjectionManagerRepository.handler);
            final MediaProjectionManagerRepository mediaProjectionManagerRepository2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$callbackEventsFlow$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    mediaProjectionManagerRepository2.mediaProjectionManager.removeCallback(mediaProjectionManagerRepository$callbackEventsFlow$1$callback$1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
