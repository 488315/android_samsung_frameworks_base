package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$lockScreenState$2", m277f = "MuteQuickAffordanceConfig.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class MuteQuickAffordanceConfig$lockScreenState$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MuteQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MuteQuickAffordanceConfig$lockScreenState$2(MuteQuickAffordanceConfig muteQuickAffordanceConfig, Continuation<? super MuteQuickAffordanceConfig$lockScreenState$2> continuation) {
        super(2, continuation);
        this.this$0 = muteQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MuteQuickAffordanceConfig$lockScreenState$2 muteQuickAffordanceConfig$lockScreenState$2 = new MuteQuickAffordanceConfig$lockScreenState$2(this.this$0, continuation);
        muteQuickAffordanceConfig$lockScreenState$2.L$0 = obj;
        return muteQuickAffordanceConfig$lockScreenState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MuteQuickAffordanceConfig$lockScreenState$2) create((Integer) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Integer num = (Integer) this.L$0;
        if (num != null && num.intValue() != 0) {
            this.this$0.previousNonSilentMode = num.intValue();
        }
        return Unit.INSTANCE;
    }
}
