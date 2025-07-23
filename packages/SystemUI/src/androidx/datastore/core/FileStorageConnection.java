package androidx.datastore.core;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FileStorageConnection implements StorageConnection {
    public final InterProcessCoordinator coordinator;
    public final File file;
    public final Function0 onClose;
    public final Serializer serializer;
    public final AtomicBoolean closed = new AtomicBoolean(false);
    public final MutexImpl transactionMutex = MutexKt.Mutex$default();

    public FileStorageConnection(File file, Serializer serializer, InterProcessCoordinator interProcessCoordinator, Function0 function0) {
        this.file = file;
        this.serializer = serializer;
        this.coordinator = interProcessCoordinator;
        this.onClose = function0;
    }

    @Override // androidx.datastore.core.Closeable
    public final void close() {
        this.closed.set(true);
        this.onClose.invoke();
    }

    @Override // androidx.datastore.core.StorageConnection
    public final InterProcessCoordinator getCoordinator() {
        return this.coordinator;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:(2:3|(8:5|6|7|(1:(3:10|11|12)(2:41|42))(2:43|(5:45|46|47|48|(1:50)(1:51))(2:55|56))|14|15|16|(2:(1:19)|20)(2:22|23)))|7|(0)(0)|14|15|16|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0078, code lost:
    
        r9 = th;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0083 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /* JADX WARN: Type inference failed for: r10v7, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v7 */
    @Override // androidx.datastore.core.StorageConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object readScope(kotlin.jvm.functions.Function3 r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof androidx.datastore.core.FileStorageConnection$readScope$1
            if (r0 == 0) goto L13
            r0 = r10
            androidx.datastore.core.FileStorageConnection$readScope$1 r0 = (androidx.datastore.core.FileStorageConnection$readScope$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.FileStorageConnection$readScope$1 r0 = new androidx.datastore.core.FileStorageConnection$readScope$1
            r0.<init>(r8, r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L40
            if (r2 != r4) goto L38
            boolean r8 = r0.Z$0
            java.lang.Object r9 = r0.L$1
            androidx.datastore.core.Closeable r9 = (androidx.datastore.core.Closeable) r9
            java.lang.Object r0 = r0.L$0
            androidx.datastore.core.FileStorageConnection r0 = (androidx.datastore.core.FileStorageConnection) r0
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L32
            goto L73
        L32:
            r10 = move-exception
            r7 = r10
            r10 = r8
            r8 = r0
            r0 = r7
            goto L8d
        L38:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L40:
            kotlin.ResultKt.throwOnFailure(r10)
            java.util.concurrent.atomic.AtomicBoolean r10 = r8.closed
            boolean r10 = r10.get()
            if (r10 != 0) goto L9f
            kotlinx.coroutines.sync.MutexImpl r10 = r8.transactionMutex
            boolean r10 = r10.tryLock()
            androidx.datastore.core.FileReadScope r2 = new androidx.datastore.core.FileReadScope     // Catch: java.lang.Throwable -> L96
            java.io.File r5 = r8.file     // Catch: java.lang.Throwable -> L96
            androidx.datastore.core.Serializer r6 = r8.serializer     // Catch: java.lang.Throwable -> L96
            r2.<init>(r5, r6)     // Catch: java.lang.Throwable -> L96
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r10)     // Catch: java.lang.Throwable -> L8b
            r0.L$0 = r8     // Catch: java.lang.Throwable -> L8b
            r0.L$1 = r2     // Catch: java.lang.Throwable -> L8b
            r0.Z$0 = r10     // Catch: java.lang.Throwable -> L8b
            r0.label = r4     // Catch: java.lang.Throwable -> L8b
            androidx.datastore.core.StorageConnectionKt$readData$2 r9 = (androidx.datastore.core.StorageConnectionKt$readData$2) r9     // Catch: java.lang.Throwable -> L8b
            java.lang.Object r9 = r9.invoke(r2, r5, r0)     // Catch: java.lang.Throwable -> L8b
            if (r9 != r1) goto L6f
            return r1
        L6f:
            r0 = r8
            r8 = r10
            r10 = r9
            r9 = r2
        L73:
            r9.close()     // Catch: java.lang.Throwable -> L78
            r9 = r3
            goto L79
        L78:
            r9 = move-exception
        L79:
            if (r9 != 0) goto L83
            if (r8 == 0) goto L82
            kotlinx.coroutines.sync.MutexImpl r8 = r0.transactionMutex
            r8.unlock(r3)
        L82:
            return r10
        L83:
            throw r9     // Catch: java.lang.Throwable -> L84
        L84:
            r9 = move-exception
            r10 = r8
            r8 = r0
            goto L97
        L88:
            r0 = r9
            r9 = r2
            goto L8d
        L8b:
            r9 = move-exception
            goto L88
        L8d:
            r9.close()     // Catch: java.lang.Throwable -> L91
            goto L95
        L91:
            r9 = move-exception
            kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r0, r9)     // Catch: java.lang.Throwable -> L96
        L95:
            throw r0     // Catch: java.lang.Throwable -> L96
        L96:
            r9 = move-exception
        L97:
            if (r10 == 0) goto L9e
            kotlinx.coroutines.sync.MutexImpl r8 = r8.transactionMutex
            r8.unlock(r3)
        L9e:
            throw r9
        L9f:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "StorageConnection has already been disposed."
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.FileStorageConnection.readScope(kotlin.jvm.functions.Function3, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:70:0x009e, code lost:
    
        if (r10.lock(r1) == r2) goto L36;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00e3 A[Catch: all -> 0x0113, IOException -> 0x0116, TRY_ENTER, TryCatch #3 {all -> 0x0113, blocks: (B:19:0x00e3, B:21:0x00e9, B:24:0x00f2, B:25:0x0112, B:27:0x011a, B:30:0x0122, B:37:0x0130, B:40:0x012d), top: B:7:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0122 A[Catch: all -> 0x0113, IOException -> 0x0116, TRY_ENTER, TRY_LEAVE, TryCatch #3 {all -> 0x0113, blocks: (B:19:0x00e3, B:21:0x00e9, B:24:0x00f2, B:25:0x0112, B:27:0x011a, B:30:0x0122, B:37:0x0130, B:40:0x012d), top: B:7:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /* JADX WARN: Type inference failed for: r10v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.io.File, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v27, types: [kotlinx.coroutines.sync.Mutex] */
    @Override // androidx.datastore.core.StorageConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object writeScope(kotlin.jvm.functions.Function2 r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.FileStorageConnection.writeScope(kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
