package com.android.systemui.inputmethod.domain.interactor;

import com.android.systemui.inputmethod.data.repository.InputMethodRepository;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InputMethodInteractor {
    public final InputMethodRepository repository;

    public InputMethodInteractor(InputMethodRepository inputMethodRepository) {
        this.repository = inputMethodRepository;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x00bc, code lost:
    
        if (((java.util.List) r10).size() > 1) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00b3, code lost:
    
        if (r10 == r1) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006a, code lost:
    
        if (r10 == r1) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object hasMultipleEnabledImesOrSubtypes(int r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1
            if (r0 == 0) goto L13
            r0 = r10
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
            r0.<init>(r8, r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 3
            r5 = 2
            r6 = 1
            if (r2 == 0) goto L52
            if (r2 == r6) goto L43
            if (r2 == r5) goto L37
            if (r2 != r4) goto L2f
            kotlin.ResultKt.throwOnFailure(r10)
            goto Lb6
        L2f:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L37:
            java.lang.Object r8 = r0.L$1
            android.os.UserHandle r8 = (android.os.UserHandle) r8
            java.lang.Object r9 = r0.L$0
            com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor r9 = (com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L88
        L43:
            java.lang.Object r8 = r0.L$1
            android.os.UserHandle r8 = (android.os.UserHandle) r8
            java.lang.Object r9 = r0.L$0
            com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor r9 = (com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor) r9
            kotlin.ResultKt.throwOnFailure(r10)
            r7 = r9
            r9 = r8
            r8 = r7
            goto L6d
        L52:
            kotlin.ResultKt.throwOnFailure(r10)
            android.os.UserHandle r9 = android.os.UserHandle.of(r9)
            r9.getClass()
            r0.L$0 = r8
            r0.L$1 = r9
            r0.label = r6
            com.android.systemui.inputmethod.data.repository.InputMethodRepository r10 = r8.repository
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl r10 = (com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl) r10
            java.lang.Object r10 = r10.enabledInputMethods(r9, r6, r0)
            if (r10 != r1) goto L6d
            goto Lb5
        L6d:
            kotlinx.coroutines.flow.Flow r10 = (kotlinx.coroutines.flow.Flow) r10
            com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$$inlined$filter$1 r2 = new com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$$inlined$filter$1
            r2.<init>()
            kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1 r10 = kotlinx.coroutines.flow.FlowKt.take(r2, r5)
            r0.L$0 = r8
            r0.L$1 = r9
            r0.label = r5
            java.lang.Object r10 = kotlinx.coroutines.flow.FlowKt.count(r10, r0)
            if (r10 != r1) goto L85
            goto Lb5
        L85:
            r7 = r9
            r9 = r8
            r8 = r7
        L88:
            java.lang.Number r10 = (java.lang.Number) r10
            int r10 = r10.intValue()
            if (r10 > r6) goto Lbe
            com.android.systemui.inputmethod.data.repository.InputMethodRepository r9 = r9.repository
            r8.getClass()
            r10 = 0
            r0.L$0 = r10
            r0.L$1 = r10
            r0.label = r4
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl r9 = (com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl) r9
            android.view.inputmethod.InputMethodManager r10 = r9.inputMethodManager
            android.view.inputmethod.InputMethodInfo r10 = r10.getCurrentInputMethodInfoAsUser(r8)
            if (r10 != 0) goto Laa
            kotlin.collections.EmptyList r8 = kotlin.collections.EmptyList.INSTANCE
        La8:
            r10 = r8
            goto Lb3
        Laa:
            java.lang.String r10 = r10.getId()
            java.lang.Object r8 = r9.enabledInputMethodSubtypes(r8, r10, r3, r0)
            goto La8
        Lb3:
            if (r10 != r1) goto Lb6
        Lb5:
            return r1
        Lb6:
            java.util.List r10 = (java.util.List) r10
            int r8 = r10.size()
            if (r8 <= r6) goto Lbf
        Lbe:
            r3 = r6
        Lbf:
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r3)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor.hasMultipleEnabledImesOrSubtypes(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
