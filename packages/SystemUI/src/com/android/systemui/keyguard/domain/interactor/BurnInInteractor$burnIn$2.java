package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class BurnInInteractor$burnIn$2 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    int label;
    final /* synthetic */ BurnInInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BurnInInteractor$burnIn$2(BurnInInteractor burnInInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = burnInInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        int intValue2 = ((Number) obj2).intValue();
        BurnInInteractor$burnIn$2 burnInInteractor$burnIn$2 = new BurnInInteractor$burnIn$2(this.this$0, (Continuation) obj3);
        burnInInteractor$burnIn$2.I$0 = intValue;
        burnInInteractor$burnIn$2.I$1 = intValue2;
        return burnInInteractor$burnIn$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        int i2 = this.I$1;
        this.this$0.burnInHelperWrapper.getClass();
        return new BurnInModel(i, i2, BurnInHelperKt.zigzag(System.currentTimeMillis() / 60000.0f, 0.2f, 181.0f) + 0.8f, false, 8, null);
    }
}
