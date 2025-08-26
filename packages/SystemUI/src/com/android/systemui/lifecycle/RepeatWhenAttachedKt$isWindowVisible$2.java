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
final class RepeatWhenAttachedKt$isWindowVisible$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ViewTreeObserver $this_isWindowVisible;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RepeatWhenAttachedKt$isWindowVisible$2(ViewTreeObserver viewTreeObserver, Continuation continuation) {
        super(2, continuation);
        this.$this_isWindowVisible = viewTreeObserver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RepeatWhenAttachedKt$isWindowVisible$2 repeatWhenAttachedKt$isWindowVisible$2 = new RepeatWhenAttachedKt$isWindowVisible$2(this.$this_isWindowVisible, continuation);
        repeatWhenAttachedKt$isWindowVisible$2.L$0 = obj;
        return repeatWhenAttachedKt$isWindowVisible$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RepeatWhenAttachedKt$isWindowVisible$2) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            ViewTreeObserver.OnWindowVisibilityChangeListener onWindowVisibilityChangeListener = new ViewTreeObserver.OnWindowVisibilityChangeListener() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$isWindowVisible$2$listener$1
                @Override // android.view.ViewTreeObserver.OnWindowVisibilityChangeListener
                public final void onWindowVisibilityChanged(int i2) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(i2 == 0));
                }
            };
            this.$this_isWindowVisible.addOnWindowVisibilityChangeListener(onWindowVisibilityChangeListener);
            RepeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0 repeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0 = new RepeatWhenAttachedKt$isAttached$1$$ExternalSyntheticLambda0(2, this.$this_isWindowVisible, onWindowVisibilityChangeListener);
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
