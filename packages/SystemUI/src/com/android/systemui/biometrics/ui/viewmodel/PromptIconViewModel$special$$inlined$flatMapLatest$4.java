package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PromptIconViewModel$special$$inlined$flatMapLatest$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ PromptViewModel $promptViewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$special$$inlined$flatMapLatest$4(Continuation continuation, PromptViewModel promptViewModel) {
        super(3, continuation);
        this.$promptViewModel$inlined = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewModel$special$$inlined$flatMapLatest$4 promptIconViewModel$special$$inlined$flatMapLatest$4 = new PromptIconViewModel$special$$inlined$flatMapLatest$4((Continuation) obj3, this.$promptViewModel$inlined);
        promptIconViewModel$special$$inlined$flatMapLatest$4.L$0 = (FlowCollector) obj;
        promptIconViewModel$special$$inlined$flatMapLatest$4.L$1 = obj2;
        return promptIconViewModel$special$$inlined$flatMapLatest$4.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004c A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4d
        Ld:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L15:
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r1 = r4.L$1
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$AuthType r1 = (com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.AuthType) r1
            int[] r3 = com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.WhenMappings.$EnumSwitchMapping$1
            int r1 = r1.ordinal()
            r1 = r3[r1]
            if (r1 == r2) goto L3c
            r3 = 2
            if (r1 == r3) goto L37
            r3 = 3
            if (r1 != r3) goto L31
            goto L3c
        L31:
            kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
            r4.<init>()
            throw r4
        L37:
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r1 = r4.$promptViewModel$inlined
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r1.isAuthenticating
            goto L44
        L3c:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r3 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r3.<init>(r1)
            r1 = r3
        L44:
            r4.label = r2
            java.lang.Object r4 = kotlinx.coroutines.flow.FlowKt.emitAll(r5, r1, r4)
            if (r4 != r0) goto L4d
            return r0
        L4d:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$special$$inlined$flatMapLatest$4.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
