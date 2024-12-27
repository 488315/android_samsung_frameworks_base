package com.android.systemui.inputmethod.domain.interactor;

import com.android.systemui.inputmethod.data.repository.InputMethodRepository;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class InputMethodInteractor {
    public final InputMethodRepository repository;

    public InputMethodInteractor(InputMethodRepository inputMethodRepository) {
        this.repository = inputMethodRepository;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x008d, code lost:
    
        if (((java.util.List) r9).size() <= 1) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object hasMultipleEnabledImesOrSubtypes(int r8, kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1 r0 = (com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1 r0 = new com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1
            r0.<init>(r7, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 3
            r5 = 2
            r6 = 1
            if (r2 == 0) goto L46
            if (r2 == r6) goto L3e
            if (r2 == r5) goto L36
            if (r2 != r4) goto L2e
            kotlin.ResultKt.throwOnFailure(r9)
            goto L87
        L2e:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L36:
            java.lang.Object r7 = r0.L$0
            com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor r7 = (com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto L6f
        L3e:
            java.lang.Object r7 = r0.L$0
            com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor r7 = (com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto L58
        L46:
            kotlin.ResultKt.throwOnFailure(r9)
            r0.L$0 = r7
            r0.label = r6
            com.android.systemui.inputmethod.data.repository.InputMethodRepository r9 = r7.repository
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl r9 = (com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl) r9
            java.lang.Object r9 = r9.enabledInputMethods(r8, r0, r6)
            if (r9 != r1) goto L58
            return r1
        L58:
            kotlinx.coroutines.flow.Flow r9 = (kotlinx.coroutines.flow.Flow) r9
            com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$$inlined$filter$1 r8 = new com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$$inlined$filter$1
            r8.<init>()
            kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1 r9 = new kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1
            r9.<init>(r8, r5)
            r0.L$0 = r7
            r0.label = r5
            java.lang.Object r9 = kotlinx.coroutines.flow.FlowKt.count(r9, r0)
            if (r9 != r1) goto L6f
            return r1
        L6f:
            java.lang.Number r9 = (java.lang.Number) r9
            int r8 = r9.intValue()
            if (r8 > r6) goto L8f
            com.android.systemui.inputmethod.data.repository.InputMethodRepository r7 = r7.repository
            r8 = 0
            r0.L$0 = r8
            r0.label = r4
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl r7 = (com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl) r7
            java.lang.Object r9 = r7.enabledInputMethodSubtypes(r8, r3, r0)
            if (r9 != r1) goto L87
            return r1
        L87:
            java.util.List r9 = (java.util.List) r9
            int r7 = r9.size()
            if (r7 <= r6) goto L90
        L8f:
            r3 = r6
        L90:
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r3)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor.hasMultipleEnabledImesOrSubtypes(int, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
