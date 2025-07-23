package androidx.compose.ui.platform;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ChainedPlatformTextInputInterceptor {
    public final MutableState interceptor$delegate;
    public final ChainedPlatformTextInputInterceptor parent;

    public ChainedPlatformTextInputInterceptor(PlatformTextInputInterceptor platformTextInputInterceptor, ChainedPlatformTextInputInterceptor chainedPlatformTextInputInterceptor) {
        this.parent = chainedPlatformTextInputInterceptor;
        this.interceptor$delegate = SnapshotStateKt.mutableStateOf$default(platformTextInputInterceptor);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.coroutines.intrinsics.CoroutineSingletons textInputSession(androidx.compose.ui.node.Owner r5, kotlin.jvm.functions.Function2 r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$1
            if (r0 == 0) goto L13
            r0 = r7
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$1 r0 = (androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$1 r0 = new androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
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
            kotlin.ResultKt.throwOnFailure(r7)
            goto L43
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2 r7 = new androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2
            r2 = 0
            r7.<init>(r6, r4, r2)
            r0.label = r3
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor r4 = r4.parent
            kotlin.coroutines.intrinsics.CoroutineSingletons r4 = androidx.compose.ui.platform.PlatformTextInputModifierNodeKt.interceptedTextInputSession(r5, r4, r7, r0)
            if (r4 != r1) goto L43
            return r1
        L43:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor.textInputSession(androidx.compose.ui.node.Owner, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }
}
