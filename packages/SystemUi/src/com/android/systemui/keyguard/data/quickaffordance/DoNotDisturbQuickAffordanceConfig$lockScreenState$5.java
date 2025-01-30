package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$5", m277f = "DoNotDisturbQuickAffordanceConfig.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class DoNotDisturbQuickAffordanceConfig$lockScreenState$5 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public DoNotDisturbQuickAffordanceConfig$lockScreenState$5(Continuation<? super DoNotDisturbQuickAffordanceConfig$lockScreenState$5> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj2).intValue();
        DoNotDisturbQuickAffordanceConfig$lockScreenState$5 doNotDisturbQuickAffordanceConfig$lockScreenState$5 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$5((Continuation) obj3);
        doNotDisturbQuickAffordanceConfig$lockScreenState$5.L$0 = (KeyguardQuickAffordanceConfig.LockScreenState) obj;
        return doNotDisturbQuickAffordanceConfig$lockScreenState$5.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return (KeyguardQuickAffordanceConfig.LockScreenState) this.L$0;
    }
}
