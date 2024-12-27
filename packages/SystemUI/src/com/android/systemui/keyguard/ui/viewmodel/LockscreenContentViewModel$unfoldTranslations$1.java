package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class LockscreenContentViewModel$unfoldTranslations$1 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    int label;

    public LockscreenContentViewModel$unfoldTranslations$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj).floatValue();
        float floatValue2 = ((Number) obj2).floatValue();
        LockscreenContentViewModel$unfoldTranslations$1 lockscreenContentViewModel$unfoldTranslations$1 = new LockscreenContentViewModel$unfoldTranslations$1((Continuation) obj3);
        lockscreenContentViewModel$unfoldTranslations$1.F$0 = floatValue;
        lockscreenContentViewModel$unfoldTranslations$1.F$1 = floatValue2;
        return lockscreenContentViewModel$unfoldTranslations$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new LockscreenContentViewModel.UnfoldTranslations(this.F$0, this.F$1);
    }
}
