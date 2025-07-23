package com.android.systemui.kairos.internal.util;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class UtilKt {
    /* JADX WARN: Can't wrap try/catch for region: R(9:0|1|(2:3|(6:5|6|7|(1:(1:(2:11|12)(2:14|15))(2:16|17))(3:21|22|(2:24|25))|18|19))|31|6|7|(0)(0)|18|19) */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x003e, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003f, code lost:
    
        r7 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:
    
        r0.L$0 = r7;
        r0.label = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005f, code lost:
    
        if (r7.mo779invoke(r0) == r1) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:?, code lost:
    
        throw r7;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons awaitCancellationAndThen(kotlin.jvm.functions.Function1 r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            boolean r0 = r7 instanceof com.android.systemui.kairos.internal.util.UtilKt$awaitCancellationAndThen$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.kairos.internal.util.UtilKt$awaitCancellationAndThen$1 r0 = (com.android.systemui.kairos.internal.util.UtilKt$awaitCancellationAndThen$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.kairos.internal.util.UtilKt$awaitCancellationAndThen$1 r0 = new com.android.systemui.kairos.internal.util.UtilKt$awaitCancellationAndThen$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L43
            if (r2 == r4) goto L36
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2e:
            java.lang.Object r6 = r0.L$0
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L62
        L36:
            java.lang.Object r6 = r0.L$0
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L3e
            goto L51
        L3e:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
            goto L57
        L43:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L3e
            r0.label = r4     // Catch: java.lang.Throwable -> L3e
            kotlin.coroutines.intrinsics.CoroutineSingletons r7 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L3e
            if (r7 != r1) goto L51
            goto L61
        L51:
            kotlin.KotlinNothingValueException r7 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L3e
            r7.<init>()     // Catch: java.lang.Throwable -> L3e
            throw r7     // Catch: java.lang.Throwable -> L3e
        L57:
            r0.L$0 = r6
            r0.label = r3
            java.lang.Object r7 = r7.mo779invoke(r0)
            if (r7 != r1) goto L62
        L61:
            return r1
        L62:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.internal.util.UtilKt.awaitCancellationAndThen(kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    public static ContextScope childScope$default(CoroutineScope coroutineScope) {
        CoroutineContext newCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, EmptyCoroutineContext.INSTANCE);
        return CoroutineScopeKt.CoroutineScope(newCoroutineContext.plus(new JobImpl((Job) newCoroutineContext.get(Job.Key))));
    }

    public static final String getHashString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }
}
