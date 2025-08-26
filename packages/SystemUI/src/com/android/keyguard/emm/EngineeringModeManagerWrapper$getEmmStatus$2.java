package com.android.keyguard.emm;

import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class EngineeringModeManagerWrapper$getEmmStatus$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ EngineeringModeManagerWrapper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EngineeringModeManagerWrapper$getEmmStatus$2(EngineeringModeManagerWrapper engineeringModeManagerWrapper, Continuation continuation) {
        super(2, continuation);
        this.this$0 = engineeringModeManagerWrapper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EngineeringModeManagerWrapper$getEmmStatus$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EngineeringModeManagerWrapper$getEmmStatus$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x002d  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (((EngineeringModeManager) this.this$0.emm$delegate.getValue()).isConnected()) {
            z = ((EngineeringModeManager) this.this$0.emm$delegate.getValue()).getStatus(64) == 1;
        }
        return Boolean.valueOf(z);
    }
}
