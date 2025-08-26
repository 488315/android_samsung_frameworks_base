package com.android.systemui.media.mediaoutput.controller.media;

import com.android.systemui.monet.ColorScheme;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class SessionController$colorScheme$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public SessionController$colorScheme$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SessionController$colorScheme$1 sessionController$colorScheme$1 = new SessionController$colorScheme$1((Continuation) obj3);
        sessionController$colorScheme$1.L$0 = (ColorScheme) obj;
        sessionController$colorScheme$1.L$1 = (ColorScheme) obj2;
        return sessionController$colorScheme$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ColorScheme colorScheme = (ColorScheme) this.L$0;
        return colorScheme == null ? (ColorScheme) this.L$1 : colorScheme;
    }
}
