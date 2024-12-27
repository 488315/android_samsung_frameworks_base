package com.android.systemui.communal.ui.viewmodel;

import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class CommunalViewModel$isCommunalContentFlowFrozen$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ CommunalViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalViewModel$isCommunalContentFlowFrozen$1(CommunalViewModel communalViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalViewModel$isCommunalContentFlowFrozen$1 communalViewModel$isCommunalContentFlowFrozen$1 = new CommunalViewModel$isCommunalContentFlowFrozen$1(this.this$0, continuation);
        communalViewModel$isCommunalContentFlowFrozen$1.Z$0 = ((Boolean) obj).booleanValue();
        return communalViewModel$isCommunalContentFlowFrozen$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((CommunalViewModel$isCommunalContentFlowFrozen$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Logger.d$default(this.this$0.logger, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("isCommunalContentFlowFrozen: ", this.Z$0), null, 2, null);
        return Unit.INSTANCE;
    }
}
