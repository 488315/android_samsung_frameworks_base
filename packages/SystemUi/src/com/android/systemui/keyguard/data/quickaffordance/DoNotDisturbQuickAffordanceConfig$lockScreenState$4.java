package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$4", m277f = "DoNotDisturbQuickAffordanceConfig.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class DoNotDisturbQuickAffordanceConfig$lockScreenState$4 extends SuspendLambda implements Function2 {
    /* synthetic */ int I$0;
    int label;
    final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoNotDisturbQuickAffordanceConfig$lockScreenState$4(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, Continuation<? super DoNotDisturbQuickAffordanceConfig$lockScreenState$4> continuation) {
        super(2, continuation);
        this.this$0 = doNotDisturbQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DoNotDisturbQuickAffordanceConfig$lockScreenState$4 doNotDisturbQuickAffordanceConfig$lockScreenState$4 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$4(this.this$0, continuation);
        doNotDisturbQuickAffordanceConfig$lockScreenState$4.I$0 = ((Number) obj).intValue();
        return doNotDisturbQuickAffordanceConfig$lockScreenState$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DoNotDisturbQuickAffordanceConfig$lockScreenState$4) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.settingsValue = this.I$0;
        return Unit.INSTANCE;
    }
}
