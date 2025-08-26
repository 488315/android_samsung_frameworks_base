package com.android.systemui.keyevent.data.repository;

import android.view.KeyEvent;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.CommandQueue;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class KeyEventRepositoryImpl$isPowerButtonDown$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyEventRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyEventRepositoryImpl$isPowerButtonDown$1(KeyEventRepositoryImpl keyEventRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyEventRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyEventRepositoryImpl$isPowerButtonDown$1 keyEventRepositoryImpl$isPowerButtonDown$1 = new KeyEventRepositoryImpl$isPowerButtonDown$1(this.this$0, continuation);
        keyEventRepositoryImpl$isPowerButtonDown$1.L$0 = obj;
        return keyEventRepositoryImpl$isPowerButtonDown$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyEventRepositoryImpl$isPowerButtonDown$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            CommandQueue.Callbacks callbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.keyevent.data.repository.KeyEventRepositoryImpl$isPowerButtonDown$1$callback$1
                @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                public final void handleSystemKey(KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() == 26) {
                        ChannelExt channelExt = ChannelExt.INSTANCE;
                        Boolean boolValueOf = Boolean.valueOf(keyEvent.isDown());
                        channelExt.getClass();
                        ChannelExt.trySendWithFailureLogging(producerScope, boolValueOf, "KeyEventRepositoryImpl", "updated isPowerButtonDown");
                    }
                }
            };
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Boolean bool = Boolean.FALSE;
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, bool, "KeyEventRepositoryImpl", "init isPowerButtonDown");
            this.this$0.commandQueue.addCallback(callbacks);
            KeyEventRepositoryImpl$isPowerButtonDown$1$$ExternalSyntheticLambda0 keyEventRepositoryImpl$isPowerButtonDown$1$$ExternalSyntheticLambda0 = new KeyEventRepositoryImpl$isPowerButtonDown$1$$ExternalSyntheticLambda0(this.this$0, callbacks, 0);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, keyEventRepositoryImpl$isPowerButtonDown$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
