package com.android.systemui.lifecycle;

import android.view.ViewTreeObserver;
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
final class RepeatWhenAttachedKt$isWindowFocused$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ViewTreeObserver $this_isWindowFocused;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RepeatWhenAttachedKt$isWindowFocused$2(ViewTreeObserver viewTreeObserver, Continuation continuation) {
        super(2, continuation);
        this.$this_isWindowFocused = viewTreeObserver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RepeatWhenAttachedKt$isWindowFocused$2 repeatWhenAttachedKt$isWindowFocused$2 = new RepeatWhenAttachedKt$isWindowFocused$2(this.$this_isWindowFocused, continuation);
        repeatWhenAttachedKt$isWindowFocused$2.L$0 = obj;
        return repeatWhenAttachedKt$isWindowFocused$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RepeatWhenAttachedKt$isWindowFocused$2) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            ViewTreeObserver.OnWindowFocusChangeListener onWindowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$isWindowFocused$2$listener$1
                @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
                public final void onWindowFocusChanged(boolean z) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            this.$this_isWindowFocused.addOnWindowFocusChangeListener(onWindowFocusChangeListener);
            RepeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0 repeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0 = new RepeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0(1, this.$this_isWindowFocused, onWindowFocusChangeListener);
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
