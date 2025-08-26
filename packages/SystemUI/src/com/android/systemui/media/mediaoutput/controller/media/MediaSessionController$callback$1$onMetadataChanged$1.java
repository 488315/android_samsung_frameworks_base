package com.android.systemui.media.mediaoutput.controller.media;

import android.media.MediaMetadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class MediaSessionController$callback$1$onMetadataChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaMetadata $metadata;
    int label;
    final /* synthetic */ MediaSessionController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaSessionController$callback$1$onMetadataChanged$1(MediaSessionController mediaSessionController, MediaMetadata mediaMetadata, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaSessionController;
        this.$metadata = mediaMetadata;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaSessionController$callback$1$onMetadataChanged$1(this.this$0, this.$metadata, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionController$callback$1$onMetadataChanged$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MediaSessionController mediaSessionController = this.this$0;
            MediaMetadata mediaMetadata = this.$metadata;
            this.label = 1;
            if (MediaSessionController.access$update(mediaSessionController, mediaMetadata, this) == coroutineSingletons) {
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
