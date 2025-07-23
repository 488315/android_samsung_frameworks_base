package androidx.datastore.core;

import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SingleProcessCoordinator implements InterProcessCoordinator {
    public final MutexImpl mutex = MutexKt.Mutex$default();
    public final AtomicInt version = new AtomicInt(0);
    public final SafeFlow updateNotifications = new SafeFlow(new SingleProcessCoordinator$updateNotifications$1(null));

    public SingleProcessCoordinator(String str) {
    }

    public final Object getVersion() {
        return new Integer(this.version.delegate.get());
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0061, code lost:
    
        if (r8 != r1) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0063, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0054, code lost:
    
        if (r8 == r1) goto L25;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /* JADX WARN: Type inference failed for: r6v0, types: [androidx.datastore.core.SingleProcessCoordinator] */
    /* JADX WARN: Type inference failed for: r6v1, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v4, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object lock(kotlin.jvm.functions.Function1 r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof androidx.datastore.core.SingleProcessCoordinator$lock$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.datastore.core.SingleProcessCoordinator$lock$1 r0 = (androidx.datastore.core.SingleProcessCoordinator$lock$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.SingleProcessCoordinator$lock$1 r0 = new androidx.datastore.core.SingleProcessCoordinator$lock$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L45
            if (r2 == r4) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.sync.Mutex r6 = (kotlinx.coroutines.sync.Mutex) r6
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L2f
            goto L64
        L2f:
            r7 = move-exception
            goto L68
        L31:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L39:
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.sync.Mutex r6 = (kotlinx.coroutines.sync.Mutex) r6
            java.lang.Object r7 = r0.L$0
            kotlin.jvm.functions.Function1 r7 = (kotlin.jvm.functions.Function1) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L57
        L45:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.sync.MutexImpl r6 = r6.mutex
            r0.L$0 = r7
            r0.L$1 = r6
            r0.label = r4
            java.lang.Object r8 = r6.lock(r0)
            if (r8 != r1) goto L57
            goto L63
        L57:
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L2f
            r0.L$1 = r5     // Catch: java.lang.Throwable -> L2f
            r0.label = r3     // Catch: java.lang.Throwable -> L2f
            java.lang.Object r8 = r7.mo779invoke(r0)     // Catch: java.lang.Throwable -> L2f
            if (r8 != r1) goto L64
        L63:
            return r1
        L64:
            r6.unlock(r5)
            return r8
        L68:
            r6.unlock(r5)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessCoordinator.lock(kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object tryLock(kotlin.jvm.functions.Function2 r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof androidx.datastore.core.SingleProcessCoordinator$tryLock$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.datastore.core.SingleProcessCoordinator$tryLock$1 r0 = (androidx.datastore.core.SingleProcessCoordinator$tryLock$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.SingleProcessCoordinator$tryLock$1 r0 = new androidx.datastore.core.SingleProcessCoordinator$tryLock$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L38
            if (r2 != r3) goto L30
            boolean r6 = r0.Z$0
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L2e
            goto L56
        L2e:
            r8 = move-exception
            goto L61
        L30:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L38:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.sync.MutexImpl r6 = r6.mutex
            boolean r8 = r6.tryLock()
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r8)     // Catch: java.lang.Throwable -> L5c
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L5c
            r0.Z$0 = r8     // Catch: java.lang.Throwable -> L5c
            r0.label = r3     // Catch: java.lang.Throwable -> L5c
            java.lang.Object r7 = r7.invoke(r2, r0)     // Catch: java.lang.Throwable -> L5c
            if (r7 != r1) goto L52
            return r1
        L52:
            r5 = r7
            r7 = r6
            r6 = r8
            r8 = r5
        L56:
            if (r6 == 0) goto L5b
            r7.unlock(r4)
        L5b:
            return r8
        L5c:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r8
            r8 = r5
        L61:
            if (r6 == 0) goto L66
            r7.unlock(r4)
        L66:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessCoordinator.tryLock(kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
