package com.android.systemui.lifecycle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SysUiViewModelKt {
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0030, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006b, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object rememberViewModel(java.lang.String r2, java.lang.Object r3, kotlin.jvm.functions.Function0 r4, androidx.compose.runtime.Composer r5, int r6, int r7) {
        /*
            androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
            r0 = 1224575454(0x48fd89de, float:519246.94)
            r5.startReplaceGroup(r0)
            r7 = r7 & 2
            if (r7 == 0) goto Le
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
        Le:
            boolean r7 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r7 == 0) goto L19
            java.lang.String r7 = "com.android.systemui.lifecycle.rememberViewModel (SysUiViewModel.kt:42)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r7)
        L19:
            r7 = -618643601(0xffffffffdb203f6f, float:-4.5105742E16)
            r5.startReplaceGroup(r7)
            boolean r3 = r5.changed(r3)
            java.lang.Object r7 = r5.rememberedValue()
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            if (r3 != 0) goto L32
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r7 != r3) goto L39
        L32:
            java.lang.Object r7 = r4.invoke()
            r5.updateRememberedValue(r7)
        L39:
            r3 = 0
            r5.end(r3)
            boolean r4 = r7 instanceof com.android.systemui.lifecycle.Activatable
            if (r4 == 0) goto L7e
            r4 = -618640503(0xffffffffdb204b89, float:-4.511905E16)
            r5.startReplaceGroup(r4)
            r4 = r6 & 14
            r4 = r4 ^ 6
            r1 = 4
            if (r4 <= r1) goto L54
            boolean r4 = r5.changed(r2)
            if (r4 != 0) goto L58
        L54:
            r4 = r6 & 6
            if (r4 != r1) goto L5a
        L58:
            r4 = 1
            goto L5b
        L5a:
            r4 = r3
        L5b:
            boolean r6 = r5.changedInstance(r7)
            r4 = r4 | r6
            java.lang.Object r6 = r5.rememberedValue()
            if (r4 != 0) goto L6d
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
            if (r6 != r4) goto L76
        L6d:
            com.android.systemui.lifecycle.SysUiViewModelKt$rememberViewModel$1$1 r6 = new com.android.systemui.lifecycle.SysUiViewModelKt$rememberViewModel$1$1
            r4 = 0
            r6.<init>(r2, r7, r4)
            r5.updateRememberedValue(r6)
        L76:
            kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6
            r5.end(r3)
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r5, r7, r6)
        L7e:
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L87
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L87:
            r5.end(r3)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.SysUiViewModelKt.rememberViewModel(java.lang.String, java.lang.Object, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int, int):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons viewModel(android.view.View r4, java.lang.String r5, com.android.systemui.lifecycle.WindowLifecycleState r6, kotlin.jvm.functions.Function0 r7, kotlin.jvm.functions.Function3 r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            boolean r0 = r9 instanceof com.android.systemui.lifecycle.SysUiViewModelKt$viewModel$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.lifecycle.SysUiViewModelKt$viewModel$1 r0 = (com.android.systemui.lifecycle.SysUiViewModelKt$viewModel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.lifecycle.SysUiViewModelKt$viewModel$1 r0 = new com.android.systemui.lifecycle.SysUiViewModelKt$viewModel$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r9)
            com.android.systemui.lifecycle.SysUiViewModelKt$viewModel$2 r9 = new com.android.systemui.lifecycle.SysUiViewModelKt$viewModel$2
            r2 = 0
            r9.<init>(r7, r8, r5, r2)
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r4 = com.android.systemui.lifecycle.RepeatWhenAttachedKt.repeatOnWindowLifecycle(r4, r6, r9, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.SysUiViewModelKt.viewModel(android.view.View, java.lang.String, com.android.systemui.lifecycle.WindowLifecycleState, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function3, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }
}
