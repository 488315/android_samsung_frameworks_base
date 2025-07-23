package com.android.systemui.inputmethod.data.repository;

import android.view.inputmethod.InputMethodManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InputMethodRepositoryImpl implements InputMethodRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final InputMethodManager inputMethodManager;

    public InputMethodRepositoryImpl(CoroutineDispatcher coroutineDispatcher, InputMethodManager inputMethodManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.inputMethodManager = inputMethodManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005e A[LOOP:0: B:11:0x0058->B:13:0x005e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object enabledInputMethodSubtypes(android.os.UserHandle r11, java.lang.String r12, boolean r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            r10 = this;
            boolean r0 = r14 instanceof com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$1
            if (r0 == 0) goto L13
            r0 = r14
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
            r0.<init>(r10, r14)
        L18:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r14)
            goto L47
        L27:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L2f:
            kotlin.ResultKt.throwOnFailure(r14)
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$2 r4 = new com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$2
            r9 = 0
            r5 = r10
            r8 = r11
            r6 = r12
            r7 = r13
            r4.<init>(r5, r6, r7, r8, r9)
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r10 = r5.backgroundDispatcher
            java.lang.Object r14 = kotlinx.coroutines.BuildersKt.withContext(r10, r4, r0)
            if (r14 != r1) goto L47
            return r1
        L47:
            java.lang.Iterable r14 = (java.lang.Iterable) r14
            java.util.ArrayList r10 = new java.util.ArrayList
            r11 = 10
            int r11 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r14, r11)
            r10.<init>(r11)
            java.util.Iterator r11 = r14.iterator()
        L58:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L75
            java.lang.Object r12 = r11.next()
            android.view.inputmethod.InputMethodSubtype r12 = (android.view.inputmethod.InputMethodSubtype) r12
            com.android.systemui.inputmethod.data.model.InputMethodModel$Subtype r13 = new com.android.systemui.inputmethod.data.model.InputMethodModel$Subtype
            int r14 = r12.getSubtypeId()
            boolean r12 = r12.isAuxiliary()
            r13.<init>(r14, r12)
            r10.add(r13)
            goto L58
        L75:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl.enabledInputMethodSubtypes(android.os.UserHandle, java.lang.String, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object enabledInputMethods(final android.os.UserHandle r5, final boolean r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$1
            if (r0 == 0) goto L13
            r0 = r7
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
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            boolean r6 = r0.Z$0
            java.lang.Object r4 = r0.L$1
            r5 = r4
            android.os.UserHandle r5 = (android.os.UserHandle) r5
            java.lang.Object r4 = r0.L$0
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl r4 = (com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl) r4
            kotlin.ResultKt.throwOnFailure(r7)
            goto L54
        L32:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3a:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$2 r7 = new com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$2
            r2 = 0
            r7.<init>(r4, r5, r2)
            r0.L$0 = r4
            r0.L$1 = r5
            r0.Z$0 = r6
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r2 = r4.backgroundDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r2, r7, r0)
            if (r7 != r1) goto L54
            return r1
        L54:
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3 r0 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3
            r0.<init>(r7)
            com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$$inlined$map$1 r7 = new com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$$inlined$map$1
            r7.<init>()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl.enabledInputMethods(android.os.UserHandle, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object showInputMethodPicker(int i, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new InputMethodRepositoryImpl$showInputMethodPicker$2(this, false, i, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
