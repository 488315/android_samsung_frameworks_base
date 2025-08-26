package com.android.systemui.lifecycle;

import android.view.View;
import com.android.systemui.util.Assert;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class RepeatWhenAttachedKt$isAttached$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ View $this_isAttached;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RepeatWhenAttachedKt$isAttached$1(View view, Continuation continuation) {
        super(2, continuation);
        this.$this_isAttached = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RepeatWhenAttachedKt$isAttached$1 repeatWhenAttachedKt$isAttached$1 = new RepeatWhenAttachedKt$isAttached$1(this.$this_isAttached, continuation);
        repeatWhenAttachedKt$isAttached$1.L$0 = obj;
        return repeatWhenAttachedKt$isAttached$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RepeatWhenAttachedKt$isAttached$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            View.OnAttachStateChangeListener onAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$isAttached$1$onAttachListener$1
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    Assert.isMainThread();
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.TRUE);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.FALSE);
                }
            };
            this.$this_isAttached.addOnAttachStateChangeListener(onAttachStateChangeListener);
            ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(this.$this_isAttached.isAttachedToWindow()));
            RepeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0 repeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0 = new RepeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0(0, this.$this_isAttached, onAttachStateChangeListener);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, repeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
