package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$lockScreenState$1", m277f = "MuteQuickAffordanceConfig.kt", m278l = {75}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class MuteQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MuteQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MuteQuickAffordanceConfig$lockScreenState$1(MuteQuickAffordanceConfig muteQuickAffordanceConfig, Continuation<? super MuteQuickAffordanceConfig$lockScreenState$1> continuation) {
        super(2, continuation);
        this.this$0 = muteQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MuteQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MuteQuickAffordanceConfig$lockScreenState$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MuteQuickAffordanceConfig muteQuickAffordanceConfig = this.this$0;
            this.label = 1;
            int i2 = MuteQuickAffordanceConfig.$r8$clinit;
            muteQuickAffordanceConfig.getClass();
            if (BuildersKt.withContext(muteQuickAffordanceConfig.backgroundDispatcher, new MuteQuickAffordanceConfig$getLastNonSilentRingerMode$2(muteQuickAffordanceConfig, null), this) == coroutineSingletons) {
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
