package com.android.systemui.navigationbar.util;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.navigationbar.util.ScopeTimer$start$1", m277f = "ScopeTimer.kt", m278l = {17}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class ScopeTimer$start$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $action;
    final /* synthetic */ long $delay;
    int label;
    final /* synthetic */ ScopeTimer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScopeTimer$start$1(long j, Function0 function0, ScopeTimer scopeTimer, Continuation<? super ScopeTimer$start$1> continuation) {
        super(2, continuation);
        this.$delay = j;
        this.$action = function0;
        this.this$0 = scopeTimer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScopeTimer$start$1(this.$delay, this.$action, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScopeTimer$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.$delay;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.$action.invoke();
        this.this$0.cancel();
        return Unit.INSTANCE;
    }
}
