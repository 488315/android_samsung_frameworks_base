package com.android.systemui.navigationbar.gestural.domain;

import com.android.systemui.shared.system.TaskStackChangeListener;
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

/* loaded from: classes2.dex */
final class GestureInteractor$_topActivity$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ GestureInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GestureInteractor$_topActivity$1(GestureInteractor gestureInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = gestureInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        GestureInteractor$_topActivity$1 gestureInteractor$_topActivity$1 = new GestureInteractor$_topActivity$1(this.this$0, continuation);
        gestureInteractor$_topActivity$1.L$0 = obj;
        return gestureInteractor$_topActivity$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GestureInteractor$_topActivity$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.navigationbar.gestural.domain.GestureInteractor$_topActivity$1$taskListener$1, com.android.systemui.shared.system.TaskStackChangeListener] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.gestural.domain.GestureInteractor$_topActivity$1$taskListener$1
                @Override // com.android.systemui.shared.system.TaskStackChangeListener
                public final void onTaskStackChanged() {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.this$0.taskStackChangeListeners.registerTaskStackListener(r1);
            final GestureInteractor gestureInteractor = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.navigationbar.gestural.domain.GestureInteractor$_topActivity$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    gestureInteractor.taskStackChangeListeners.unregisterTaskStackListener(r1);
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
