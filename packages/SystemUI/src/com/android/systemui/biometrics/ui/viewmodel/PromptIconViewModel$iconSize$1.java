package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class PromptIconViewModel$iconSize$1 extends SuspendLambda implements Function5 {
    final /* synthetic */ PromptViewModel $promptViewModel;
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$iconSize$1(PromptViewModel promptViewModel, Continuation continuation) {
        super(5, continuation);
        this.$promptViewModel = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int intValue = ((Number) obj3).intValue();
        int intValue2 = ((Number) obj4).intValue();
        PromptIconViewModel$iconSize$1 promptIconViewModel$iconSize$1 = new PromptIconViewModel$iconSize$1(this.$promptViewModel, (Continuation) obj5);
        promptIconViewModel$iconSize$1.L$0 = (PromptIconViewModel.AuthType) obj2;
        promptIconViewModel$iconSize$1.I$0 = intValue;
        promptIconViewModel$iconSize$1.I$1 = intValue2;
        return promptIconViewModel$iconSize$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((PromptIconViewModel.AuthType) this.L$0) == PromptIconViewModel.AuthType.Face ? new Pair(new Integer(this.$promptViewModel.faceIconWidth), new Integer(this.$promptViewModel.faceIconHeight)) : new Pair(new Integer(this.I$0), new Integer(this.I$1));
    }
}
