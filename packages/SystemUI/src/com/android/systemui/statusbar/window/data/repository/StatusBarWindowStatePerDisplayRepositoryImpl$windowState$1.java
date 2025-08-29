package com.android.systemui.statusbar.window.data.repository;

import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.window.shared.model.StatusBarWindowState;
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

/* loaded from: classes3.dex */
final class StatusBarWindowStatePerDisplayRepositoryImpl$windowState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StatusBarWindowStatePerDisplayRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarWindowStatePerDisplayRepositoryImpl$windowState$1(StatusBarWindowStatePerDisplayRepositoryImpl statusBarWindowStatePerDisplayRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = statusBarWindowStatePerDisplayRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StatusBarWindowStatePerDisplayRepositoryImpl$windowState$1 statusBarWindowStatePerDisplayRepositoryImpl$windowState$1 = new StatusBarWindowStatePerDisplayRepositoryImpl$windowState$1(this.this$0, continuation);
        statusBarWindowStatePerDisplayRepositoryImpl$windowState$1.L$0 = obj;
        return statusBarWindowStatePerDisplayRepositoryImpl$windowState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarWindowStatePerDisplayRepositoryImpl$windowState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.CommandQueue$Callbacks, com.android.systemui.statusbar.window.data.repository.StatusBarWindowStatePerDisplayRepositoryImpl$windowState$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final StatusBarWindowStatePerDisplayRepositoryImpl statusBarWindowStatePerDisplayRepositoryImpl = this.this$0;
            final ?? r1 = new CommandQueue.Callbacks() { // from class: com.android.systemui.statusbar.window.data.repository.StatusBarWindowStatePerDisplayRepositoryImpl$windowState$1$callback$1
                @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                public final void setWindowState(int i2, int i3, int i4) {
                    StatusBarWindowStatePerDisplayRepositoryImpl statusBarWindowStatePerDisplayRepositoryImpl2 = statusBarWindowStatePerDisplayRepositoryImpl;
                    if (i2 == statusBarWindowStatePerDisplayRepositoryImpl2.thisDisplayId && i3 == 1) {
                        statusBarWindowStatePerDisplayRepositoryImpl2.getClass();
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(i4 != 0 ? i4 != 1 ? i4 != 2 ? StatusBarWindowState.Hidden : StatusBarWindowState.Hidden : StatusBarWindowState.Hiding : StatusBarWindowState.Showing);
                    }
                }
            };
            this.this$0.commandQueue.addCallback((CommandQueue.Callbacks) r1);
            final StatusBarWindowStatePerDisplayRepositoryImpl statusBarWindowStatePerDisplayRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.window.data.repository.StatusBarWindowStatePerDisplayRepositoryImpl$windowState$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    statusBarWindowStatePerDisplayRepositoryImpl2.commandQueue.removeCallback((CommandQueue.Callbacks) r1);
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
