package androidx.datastore.core;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RunOnce {
    public final MutexImpl runMutex = MutexKt.Mutex$default();
    public final CompletableDeferredImpl didRun = CompletableDeferredKt.CompletableDeferred$default();

    public abstract Object doRun(ContinuationImpl continuationImpl);

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0065, code lost:
    
        if (r8.lock(r0) == r1) goto L36;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0070 A[Catch: all -> 0x0076, TRY_LEAVE, TryCatch #1 {all -> 0x0076, blocks: (B:25:0x0068, B:27:0x0070, B:30:0x007b), top: B:24:0x0068 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007b A[Catch: all -> 0x0076, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0076, blocks: (B:25:0x0068, B:27:0x0070, B:30:0x007b), top: B:24:0x0068 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /* JADX WARN: Type inference failed for: r7v11, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object runIfNeeded(kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof androidx.datastore.core.RunOnce$runIfNeeded$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.datastore.core.RunOnce$runIfNeeded$1 r0 = (androidx.datastore.core.RunOnce$runIfNeeded$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.RunOnce$runIfNeeded$1 r0 = new androidx.datastore.core.RunOnce$runIfNeeded$1
            r0.<init>(r7, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L4b
            if (r2 == r4) goto L3d
            if (r2 != r3) goto L35
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            java.lang.Object r0 = r0.L$0
            androidx.datastore.core.RunOnce r0 = (androidx.datastore.core.RunOnce) r0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L33
            goto L8a
        L33:
            r8 = move-exception
            goto L95
        L35:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3d:
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            java.lang.Object r2 = r0.L$0
            androidx.datastore.core.RunOnce r2 = (androidx.datastore.core.RunOnce) r2
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = r7
            r7 = r2
            goto L68
        L4b:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.CompletableDeferredImpl r8 = r7.didRun
            boolean r8 = r8.isCompleted()
            if (r8 == 0) goto L59
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L59:
            kotlinx.coroutines.sync.MutexImpl r8 = r7.runMutex
            r0.L$0 = r7
            r0.L$1 = r8
            r0.label = r4
            java.lang.Object r2 = r8.lock(r0)
            if (r2 != r1) goto L68
            goto L87
        L68:
            kotlinx.coroutines.CompletableDeferredImpl r2 = r7.didRun     // Catch: java.lang.Throwable -> L76
            boolean r2 = r2.isCompleted()     // Catch: java.lang.Throwable -> L76
            if (r2 == 0) goto L7b
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L76
            r8.unlock(r5)
            return r7
        L76:
            r7 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
            goto L95
        L7b:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L76
            r0.L$1 = r8     // Catch: java.lang.Throwable -> L76
            r0.label = r3     // Catch: java.lang.Throwable -> L76
            java.lang.Object r0 = r7.doRun(r0)     // Catch: java.lang.Throwable -> L76
            if (r0 != r1) goto L88
        L87:
            return r1
        L88:
            r0 = r7
            r7 = r8
        L8a:
            kotlinx.coroutines.CompletableDeferredImpl r8 = r0.didRun     // Catch: java.lang.Throwable -> L33
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L33
            r8.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r0)     // Catch: java.lang.Throwable -> L33
            r7.unlock(r5)
            return r0
        L95:
            r7.unlock(r5)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.RunOnce.runIfNeeded(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
