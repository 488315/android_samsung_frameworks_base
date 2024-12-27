package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PromptIconViewModel$special$$inlined$flatMapLatest$5 extends SuspendLambda implements Function3 {
    final /* synthetic */ PromptSelectorInteractor $promptSelectorInteractor$inlined;
    final /* synthetic */ PromptViewModel $promptViewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$special$$inlined$flatMapLatest$5(Continuation continuation, PromptSelectorInteractor promptSelectorInteractor, PromptViewModel promptViewModel, PromptIconViewModel promptIconViewModel) {
        super(3, continuation);
        this.$promptSelectorInteractor$inlined = promptSelectorInteractor;
        this.$promptViewModel$inlined = promptViewModel;
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewModel$special$$inlined$flatMapLatest$5 promptIconViewModel$special$$inlined$flatMapLatest$5 = new PromptIconViewModel$special$$inlined$flatMapLatest$5((Continuation) obj3, this.$promptSelectorInteractor$inlined, this.$promptViewModel$inlined, this.this$0);
        promptIconViewModel$special$$inlined$flatMapLatest$5.L$0 = (FlowCollector) obj;
        promptIconViewModel$special$$inlined$flatMapLatest$5.L$1 = obj2;
        return promptIconViewModel$special$$inlined$flatMapLatest$5.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0061 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r9)
            goto L62
        Ld:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L15:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            java.lang.Object r1 = r8.L$1
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$AuthType r1 = (com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.AuthType) r1
            int[] r3 = com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.WhenMappings.$EnumSwitchMapping$1
            int r1 = r1.ordinal()
            r1 = r3[r1]
            if (r1 == r2) goto L3f
            r3 = 2
            if (r1 == r3) goto L37
            r3 = 3
            if (r1 != r3) goto L31
            goto L3f
        L31:
            kotlin.NoWhenBranchMatchedException r8 = new kotlin.NoWhenBranchMatchedException
            r8.<init>()
            throw r8
        L37:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r3 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r3.<init>(r1)
            goto L59
        L3f:
            com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor r1 = r8.$promptSelectorInteractor$inlined
            com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl r1 = (com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl) r1
            kotlinx.coroutines.flow.StateFlow r1 = r1.sensorType
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r3 = r8.$promptViewModel$inlined
            kotlinx.coroutines.flow.ReadonlyStateFlow r4 = r3.isAuthenticated
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$shouldAnimateIconOverlay$1$1 r5 = new com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$shouldAnimateIconOverlay$1$1
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel r6 = r8.this$0
            r7 = 0
            r5.<init>(r6, r7)
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r3.isAuthenticating
            kotlinx.coroutines.flow.Flow r3 = r3.showingError
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 r3 = kotlinx.coroutines.flow.FlowKt.combine(r1, r4, r6, r3, r5)
        L59:
            r8.label = r2
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.emitAll(r8, r3, r9)
            if (r8 != r0) goto L62
            return r0
        L62:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$special$$inlined$flatMapLatest$5.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
