package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PromptIconViewModel$special$$inlined$flatMapLatest$6 extends SuspendLambda implements Function3 {
    final /* synthetic */ PromptSelectorInteractor $promptSelectorInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$special$$inlined$flatMapLatest$6(Continuation continuation, PromptSelectorInteractor promptSelectorInteractor, PromptIconViewModel promptIconViewModel) {
        super(3, continuation);
        this.$promptSelectorInteractor$inlined = promptSelectorInteractor;
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewModel$special$$inlined$flatMapLatest$6 promptIconViewModel$special$$inlined$flatMapLatest$6 = new PromptIconViewModel$special$$inlined$flatMapLatest$6((Continuation) obj3, this.$promptSelectorInteractor$inlined, this.this$0);
        promptIconViewModel$special$$inlined$flatMapLatest$6.L$0 = (FlowCollector) obj;
        promptIconViewModel$special$$inlined$flatMapLatest$6.L$1 = obj2;
        return promptIconViewModel$special$$inlined$flatMapLatest$6.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0061 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r7)
            goto L62
        Ld:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L15:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            java.lang.Object r1 = r6.L$1
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
            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException
            r6.<init>()
            throw r6
        L37:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r3 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r3.<init>(r1)
            goto L59
        L3f:
            com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor r1 = r6.$promptSelectorInteractor$inlined
            com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl r1 = (com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl) r1
            kotlinx.coroutines.flow.StateFlow r1 = r1.sensorType
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel r3 = r6.this$0
            com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor r3 = r3.displayStateInteractor
            com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl r3 = (com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl) r3
            kotlinx.coroutines.flow.ReadonlyStateFlow r3 = r3.currentRotation
            com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$shouldFlipIconView$1$1 r4 = new com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$shouldFlipIconView$1$1
            r5 = 0
            r4.<init>(r5)
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 r5 = new kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1
            r5.<init>(r1, r3, r4)
            r3 = r5
        L59:
            r6.label = r2
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.emitAll(r6, r3, r7)
            if (r6 != r0) goto L62
            return r0
        L62:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$special$$inlined$flatMapLatest$6.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
