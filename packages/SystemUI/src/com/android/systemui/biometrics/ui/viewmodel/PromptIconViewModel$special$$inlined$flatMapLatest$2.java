package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

public final class PromptIconViewModel$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ PromptSelectorInteractor $promptSelectorInteractor$inlined;
    final /* synthetic */ PromptViewModel $promptViewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$special$$inlined$flatMapLatest$2(Continuation continuation, PromptIconViewModel promptIconViewModel, PromptSelectorInteractor promptSelectorInteractor, PromptViewModel promptViewModel) {
        super(3, continuation);
        this.this$0 = promptIconViewModel;
        this.$promptSelectorInteractor$inlined = promptSelectorInteractor;
        this.$promptViewModel$inlined = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewModel$special$$inlined$flatMapLatest$2 promptIconViewModel$special$$inlined$flatMapLatest$2 = new PromptIconViewModel$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0, this.$promptSelectorInteractor$inlined, this.$promptViewModel$inlined);
        promptIconViewModel$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        promptIconViewModel$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return promptIconViewModel$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x006b A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r11)
            goto L6c
        Ld:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L15:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlinx.coroutines.flow.FlowCollector r11 = (kotlinx.coroutines.flow.FlowCollector) r11
            java.lang.Object r1 = r10.L$1
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$AuthType r1 = (com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.AuthType) r1
            int[] r3 = com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.WhenMappings.$EnumSwitchMapping$1
            int r1 = r1.ordinal()
            r1 = r3[r1]
            if (r1 == r2) goto L43
            r3 = 2
            if (r1 == r3) goto L37
            r3 = 3
            if (r1 != r3) goto L31
            goto L43
        L31:
            kotlin.NoWhenBranchMatchedException r10 = new kotlin.NoWhenBranchMatchedException
            r10.<init>()
            throw r10
        L37:
            java.lang.Integer r1 = new java.lang.Integer
            r3 = -1
            r1.<init>(r3)
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r3 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r3.<init>(r1)
            goto L63
        L43:
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel r1 = r10.this$0
            com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor r3 = r1.displayStateInteractor
            com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl r3 = (com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl) r3
            kotlinx.coroutines.flow.ReadonlyStateFlow r4 = r3.currentRotation
            com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor r3 = r10.$promptSelectorInteractor$inlined
            com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl r3 = (com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl) r3
            kotlinx.coroutines.flow.StateFlow r5 = r3.sensorType
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r3 = r10.$promptViewModel$inlined
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r3.isAuthenticated
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconOverlayAsset$1$1 r9 = new com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconOverlayAsset$1$1
            r7 = 0
            r9.<init>(r1, r7)
            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r3.isAuthenticating
            kotlinx.coroutines.flow.Flow r8 = r3.showingError
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 r3 = kotlinx.coroutines.flow.FlowKt.combine(r4, r5, r6, r7, r8, r9)
        L63:
            r10.label = r2
            java.lang.Object r10 = kotlinx.coroutines.flow.FlowKt.emitAll(r10, r3, r11)
            if (r10 != r0) goto L6c
            return r0
        L6c:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$special$$inlined$flatMapLatest$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
