package com.android.systemui.display.data.repository;

import com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl;
import com.android.systemui.statusbar.CommandQueue;
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
final class DisplaysWithDecorationsRepositoryImpl$decorationEvents$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DisplaysWithDecorationsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplaysWithDecorationsRepositoryImpl$decorationEvents$1(DisplaysWithDecorationsRepositoryImpl displaysWithDecorationsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = displaysWithDecorationsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplaysWithDecorationsRepositoryImpl$decorationEvents$1 displaysWithDecorationsRepositoryImpl$decorationEvents$1 = new DisplaysWithDecorationsRepositoryImpl$decorationEvents$1(this.this$0, continuation);
        displaysWithDecorationsRepositoryImpl$decorationEvents$1.L$0 = obj;
        return displaysWithDecorationsRepositoryImpl$decorationEvents$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplaysWithDecorationsRepositoryImpl$decorationEvents$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$decorationEvents$1$callback$1, com.android.systemui.statusbar.CommandQueue$Callbacks] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new CommandQueue.Callbacks() { // from class: com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$decorationEvents$1$callback$1
                @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                public final void onDisplayAddSystemDecorations(int i2) {
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(new DisplaysWithDecorationsRepositoryImpl.Event.Add(i2));
                }

                @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                public final void onDisplayRemoveSystemDecorations(int i2) {
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(new DisplaysWithDecorationsRepositoryImpl.Event.Remove(i2));
                }
            };
            this.this$0.commandQueue.addCallback((CommandQueue.Callbacks) r1);
            final DisplaysWithDecorationsRepositoryImpl displaysWithDecorationsRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$decorationEvents$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    displaysWithDecorationsRepositoryImpl.commandQueue.removeCallback((CommandQueue.Callbacks) r1);
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
