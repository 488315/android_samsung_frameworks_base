package com.android.app.displaylib;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import com.android.app.displaylib.DisplayEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes.dex */
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
    /* JADX WARN: Type inference failed for: r1v1, types: [android.hardware.display.DisplayManager$DisplayListener, com.android.app.displaylib.DisplayRepositoryImpl$allDisplayEvents$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            ?? r1 = new DisplayManager.DisplayListener() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$allDisplayEvents$1$callback$1
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayAdded(int i2) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(new DisplayEvent.Added(i2));
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayChanged(int i2) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(new DisplayEvent.Changed(i2));
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayRemoved(int i2) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(new DisplayEvent.Removed(i2));
                }
            };
            this.this$0.displayManager.registerDisplayListener(r1, this.$backgroundHandler, 7L);
            DisplayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0 displayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0 = new DisplayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0(this.this$0, (DisplayRepositoryImpl$allDisplayEvents$1$callback$1) r1);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, displayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
