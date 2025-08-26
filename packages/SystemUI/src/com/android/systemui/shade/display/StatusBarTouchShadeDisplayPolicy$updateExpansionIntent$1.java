package com.android.systemui.shade.display;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes3.dex */
final class StatusBarTouchShadeDisplayPolicy$updateExpansionIntent$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ StatusBarTouchShadeDisplayPolicy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarTouchShadeDisplayPolicy$updateExpansionIntent$1(StatusBarTouchShadeDisplayPolicy statusBarTouchShadeDisplayPolicy, Continuation continuation) {
        super(2, continuation);
        this.this$0 = statusBarTouchShadeDisplayPolicy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new StatusBarTouchShadeDisplayPolicy$updateExpansionIntent$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarTouchShadeDisplayPolicy$updateExpansionIntent$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            StatusBarTouchShadeDisplayPolicy.Companion.getClass();
            long j = StatusBarTouchShadeDisplayPolicy.EXPANSION_INTENT_EXPIRY;
            this.label = 1;
            if (DelayKt.m3469delayVtjQ1oo(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.this$0.latestIntent.set(null);
        return Unit.INSTANCE;
    }
}
