package com.android.systemui.education.domain.interactor;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor;
import com.android.systemui.recents.LauncherProxyService;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyboardTouchpadEduInteractor$statsUpdateRequests$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyboardTouchpadEduInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardTouchpadEduInteractor$statsUpdateRequests$1(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyboardTouchpadEduInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyboardTouchpadEduInteractor$statsUpdateRequests$1 keyboardTouchpadEduInteractor$statsUpdateRequests$1 = new KeyboardTouchpadEduInteractor$statsUpdateRequests$1(this.this$0, continuation);
        keyboardTouchpadEduInteractor$statsUpdateRequests$1.L$0 = obj;
        return keyboardTouchpadEduInteractor$statsUpdateRequests$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardTouchpadEduInteractor$statsUpdateRequests$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$statsUpdateRequests$1$listener$1, com.android.systemui.recents.LauncherProxyService$LauncherProxyListener] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new LauncherProxyService.LauncherProxyListener() { // from class: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$statsUpdateRequests$1$listener$1
                @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
                public final void updateContextualEduStats(boolean z, GestureType gestureType) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, new KeyboardTouchpadEduInteractor.StatsUpdateRequest(z, gestureType), "KeyboardTouchpadEduInteractor");
                }
            };
            this.this$0.launcherProxyService.addCallback((LauncherProxyService.LauncherProxyListener) r1);
            final KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$statsUpdateRequests$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyboardTouchpadEduInteractor.this.launcherProxyService.removeCallback((LauncherProxyService.LauncherProxyListener) r1);
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
