package com.android.systemui.util.kotlin;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;

public final class DisposableHandleExtKt {
    /* JADX WARN: Removed duplicated region for block: B:18:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object awaitCancellationThenDispose(kotlinx.coroutines.DisposableHandle r4, kotlin.coroutines.Continuation r5) {
        /*
            boolean r0 = r5 instanceof com.android.systemui.util.kotlin.DisposableHandleExtKt$awaitCancellationThenDispose$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.util.kotlin.DisposableHandleExtKt$awaitCancellationThenDispose$1 r0 = (com.android.systemui.util.kotlin.DisposableHandleExtKt$awaitCancellationThenDispose$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.kotlin.DisposableHandleExtKt$awaitCancellationThenDispose$1 r0 = new com.android.systemui.util.kotlin.DisposableHandleExtKt$awaitCancellationThenDispose$1
            r0.<init>(r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.DisposableHandle r4 = (kotlinx.coroutines.DisposableHandle) r4
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: java.lang.Throwable -> L33
            goto L43
        L33:
            r5 = move-exception
            goto L49
        L35:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4     // Catch: java.lang.Throwable -> L33
            r0.label = r3     // Catch: java.lang.Throwable -> L33
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L33
            if (r5 != r1) goto L43
            return r1
        L43:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L33
            r5.<init>()     // Catch: java.lang.Throwable -> L33
            throw r5     // Catch: java.lang.Throwable -> L33
        L49:
            r4.dispose()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.DisposableHandleExtKt.awaitCancellationThenDispose(kotlinx.coroutines.DisposableHandle, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final Job launchAndDispose(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function0 function0) {
        return BuildersKt.launch(coroutineScope, coroutineContext, coroutineStart, new DisposableHandleExtKt$launchAndDispose$1(function0, null));
    }

    public static /* synthetic */ Job launchAndDispose$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 2) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        return BuildersKt.launch(coroutineScope, coroutineContext, coroutineStart, new DisposableHandleExtKt$launchAndDispose$1(function0, null));
    }
}
