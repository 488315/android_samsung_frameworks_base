package com.android.systemui.mediarouter.data.repository;

import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaRouterRepositoryImpl$castDevices$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaRouterRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaRouterRepositoryImpl$castDevices$1(MediaRouterRepositoryImpl mediaRouterRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaRouterRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaRouterRepositoryImpl$castDevices$1 mediaRouterRepositoryImpl$castDevices$1 = new MediaRouterRepositoryImpl$castDevices$1(this.this$0, continuation);
        mediaRouterRepositoryImpl$castDevices$1.L$0 = obj;
        return mediaRouterRepositoryImpl$castDevices$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaRouterRepositoryImpl$castDevices$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.mediarouter.data.repository.MediaRouterRepositoryImpl$castDevices$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MediaRouterRepositoryImpl mediaRouterRepositoryImpl = this.this$0;
            final ?? r1 = new CastController.Callback() { // from class: com.android.systemui.mediarouter.data.repository.MediaRouterRepositoryImpl$castDevices$1$callback$1
                @Override // com.android.systemui.statusbar.policy.CastController.Callback
                public final void onCastDevicesChanged() {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(((CastControllerImpl) mediaRouterRepositoryImpl.castController).getCastDevices());
                }
            };
            ((CastControllerImpl) this.this$0.castController).addCallback(r1);
            final MediaRouterRepositoryImpl mediaRouterRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.mediarouter.data.repository.MediaRouterRepositoryImpl$castDevices$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((CastControllerImpl) MediaRouterRepositoryImpl.this.castController).removeCallback(r1);
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
