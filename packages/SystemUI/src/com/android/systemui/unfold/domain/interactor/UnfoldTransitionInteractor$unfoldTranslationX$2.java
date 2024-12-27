package com.android.systemui.unfold.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class UnfoldTransitionInteractor$unfoldTranslationX$2 extends SuspendLambda implements Function4 {
    final /* synthetic */ boolean $isOnStartSide;
    /* synthetic */ float F$0;
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnfoldTransitionInteractor$unfoldTranslationX$2(boolean z, Continuation continuation) {
        super(4, continuation);
        this.$isOnStartSide = z;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        float floatValue = ((Number) obj).floatValue();
        int intValue = ((Number) obj2).intValue();
        int intValue2 = ((Number) obj3).intValue();
        UnfoldTransitionInteractor$unfoldTranslationX$2 unfoldTransitionInteractor$unfoldTranslationX$2 = new UnfoldTransitionInteractor$unfoldTranslationX$2(this.$isOnStartSide, (Continuation) obj4);
        unfoldTransitionInteractor$unfoldTranslationX$2.F$0 = floatValue;
        unfoldTransitionInteractor$unfoldTranslationX$2.I$0 = intValue;
        unfoldTransitionInteractor$unfoldTranslationX$2.I$1 = intValue2;
        return unfoldTransitionInteractor$unfoldTranslationX$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Float((1 - this.F$0) * this.I$0 * (this.$isOnStartSide ? 1 : -1) * this.I$1);
    }
}
