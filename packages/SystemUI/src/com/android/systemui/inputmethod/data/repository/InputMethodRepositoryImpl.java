package com.android.systemui.inputmethod.data.repository;

import android.view.inputmethod.InputMethodManager;
import kotlinx.coroutines.CoroutineDispatcher;

public final class InputMethodRepositoryImpl implements InputMethodRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final InputMethodManager inputMethodManager;

    public InputMethodRepositoryImpl(CoroutineDispatcher coroutineDispatcher, InputMethodManager inputMethodManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.inputMethodManager = inputMethodManager;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object enabledInputMethodSubtypes(android.view.inputmethod.InputMethodInfo r5, boolean r6, kotlin.coroutines.Continuation r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$1 r0 = (com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$1 r0 = new com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r7)
            goto L43
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$2 r7 = new com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$2
            r2 = 0
            r7.<init>(r4, r5, r6, r2)
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r4 = r4.backgroundDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r4, r7, r0)
            if (r7 != r1) goto L43
            return r1
        L43:
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.ArrayList r4 = new java.util.ArrayList
            r5 = 10
            int r5 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r7, r5)
            r4.<init>(r5)
            java.util.Iterator r5 = r7.iterator()
        L54:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L71
            java.lang.Object r6 = r5.next()
            android.view.inputmethod.InputMethodSubtype r6 = (android.view.inputmethod.InputMethodSubtype) r6
            com.android.systemui.inputmethod.data.model.InputMethodModel$Subtype r7 = new com.android.systemui.inputmethod.data.model.InputMethodModel$Subtype
            int r0 = r6.getSubtypeId()
            boolean r6 = r6.isAuxiliary()
            r7.<init>(r0, r6)
            r4.add(r7)
            goto L54
        L71:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl.enabledInputMethodSubtypes(android.view.inputmethod.InputMethodInfo, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object enabledInputMethods(int r5, kotlin.coroutines.Continuation r6, final boolean r7) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$1 r0 = (com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$1 r0 = new com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            boolean r7 = r0.Z$0
            java.lang.Object r4 = r0.L$0
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl r4 = (com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4d
        L2d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$2 r6 = new com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$2
            r2 = 0
            r6.<init>(r4, r5, r2)
            r0.L$0 = r4
            r0.Z$0 = r7
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r5 = r4.backgroundDispatcher
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r0)
            if (r6 != r1) goto L4d
            return r1
        L4d:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3 r5 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3
            r5.<init>(r6)
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$$inlined$map$1 r6 = new com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$$inlined$map$1
            r6.<init>()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl.enabledInputMethods(int, kotlin.coroutines.Continuation, boolean):java.lang.Object");
    }
}
