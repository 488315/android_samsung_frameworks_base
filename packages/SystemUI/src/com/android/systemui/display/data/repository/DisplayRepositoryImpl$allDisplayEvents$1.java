package com.android.systemui.display.data.repository;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import com.android.systemui.display.data.DisplayEvent;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DisplayRepositoryImpl$allDisplayEvents$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Handler $backgroundHandler;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DisplayRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayRepositoryImpl$allDisplayEvents$1(DisplayRepositoryImpl displayRepositoryImpl, Handler handler, Continuation continuation) {
        super(2, continuation);
        this.this$0 = displayRepositoryImpl;
        this.$backgroundHandler = handler;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplayRepositoryImpl$allDisplayEvents$1 displayRepositoryImpl$allDisplayEvents$1 = new DisplayRepositoryImpl$allDisplayEvents$1(this.this$0, this.$backgroundHandler, continuation);
        displayRepositoryImpl$allDisplayEvents$1.L$0 = obj;
        return displayRepositoryImpl$allDisplayEvents$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplayRepositoryImpl$allDisplayEvents$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.hardware.display.DisplayManager$DisplayListener, com.android.systemui.display.data.repository.DisplayRepositoryImpl$allDisplayEvents$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new DisplayManager.DisplayListener() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$allDisplayEvents$1$callback$1
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayAdded(int i2) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(new DisplayEvent.Added(i2));
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayChanged(int i2) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(new DisplayEvent.Changed(i2));
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayRemoved(int i2) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(new DisplayEvent.Removed(i2));
                }
            };
            this.this$0.displayManager.registerDisplayListener(r1, this.$backgroundHandler, 7L);
            final DisplayRepositoryImpl displayRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$allDisplayEvents$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DisplayRepositoryImpl.this.displayManager.unregisterDisplayListener(r1);
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
