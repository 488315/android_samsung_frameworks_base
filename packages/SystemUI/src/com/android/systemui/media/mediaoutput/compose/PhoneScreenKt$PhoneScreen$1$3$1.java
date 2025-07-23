package com.android.systemui.media.mediaoutput.compose;

import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class PhoneScreenKt$PhoneScreen$1$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaOutputState $mediaOutputState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhoneScreenKt$PhoneScreen$1$3$1(MediaOutputState mediaOutputState, Continuation continuation) {
        super(2, continuation);
        this.$mediaOutputState = mediaOutputState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PhoneScreenKt$PhoneScreen$1$3$1(this.$mediaOutputState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PhoneScreenKt$PhoneScreen$1$3$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((Feature) this.$mediaOutputState).setState(MediaOutputState.StateInfo.Showing);
        return Unit.INSTANCE;
    }
}
