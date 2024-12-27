package com.android.systemui.media.mediaoutput.compose.common;

import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

final class Feature$animateDismiss$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ Feature this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Feature$animateDismiss$1(Feature feature, Continuation continuation) {
        super(2, continuation);
        this.this$0 = feature;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new Feature$animateDismiss$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Feature$animateDismiss$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(50L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        Feature feature = this.this$0;
        Function0 function0 = feature.dismissCallback;
        if (function0 != null) {
            function0.invoke();
        }
        feature.setState(MediaOutputState.StateInfo.Dismissed);
        return Unit.INSTANCE;
    }
}
