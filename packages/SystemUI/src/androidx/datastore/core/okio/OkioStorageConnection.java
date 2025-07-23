package androidx.datastore.core.okio;

import androidx.datastore.core.InterProcessCoordinator;
import androidx.datastore.core.StorageConnection;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;
import okio.FileSystem;
import okio.Path;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OkioStorageConnection implements StorageConnection {
    public final InterProcessCoordinator coordinator;
    public final FileSystem fileSystem;
    public final Function0 onClose;
    public final Path path;
    public final OkioSerializer serializer;
    public final AtomicBoolean closed = new AtomicBoolean(false);
    public final MutexImpl transactionMutex = MutexKt.Mutex$default();

    public OkioStorageConnection(FileSystem fileSystem, Path path, OkioSerializer okioSerializer, InterProcessCoordinator interProcessCoordinator, Function0 function0) {
        this.fileSystem = fileSystem;
        this.path = path;
        this.serializer = okioSerializer;
        this.coordinator = interProcessCoordinator;
        this.onClose = function0;
    }

    @Override // androidx.datastore.core.Closeable
    public final void close() {
        this.closed.delegate.set(true);
        this.onClose.invoke();
    }

    @Override // androidx.datastore.core.StorageConnection
    public final InterProcessCoordinator getCoordinator() {
        return this.coordinator;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:(2:3|(8:5|6|7|(1:(3:10|11|12)(2:41|42))(2:43|(5:45|46|47|48|(1:50)(1:51))(2:55|56))|14|15|16|(2:(1:19)|20)(2:22|23)))|7|(0)(0)|14|15|16|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007a, code lost:
    
        r10 = th;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0085 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /* JADX WARN: Type inference failed for: r10v0, types: [kotlin.jvm.functions.Function3] */
    /* JADX WARN: Type inference failed for: r11v8, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v7 */
    @Override // androidx.datastore.core.StorageConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object readScope(kotlin.jvm.functions.Function3 r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof androidx.datastore.core.okio.OkioStorageConnection$readScope$1
            if (r0 == 0) goto L13
            r0 = r11
            androidx.datastore.core.okio.OkioStorageConnection$readScope$1 r0 = (androidx.datastore.core.okio.OkioStorageConnection$readScope$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.okio.OkioStorageConnection$readScope$1 r0 = new androidx.datastore.core.okio.OkioStorageConnection$readScope$1
            r0.<init>(r9, r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L40
            if (r2 != r4) goto L38
            boolean r9 = r0.Z$0
            java.lang.Object r10 = r0.L$1
            androidx.datastore.core.Closeable r10 = (androidx.datastore.core.Closeable) r10
            java.lang.Object r0 = r0.L$0
            androidx.datastore.core.okio.OkioStorageConnection r0 = (androidx.datastore.core.okio.OkioStorageConnection) r0
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L32
            goto L75
        L32:
            r11 = move-exception
            r8 = r11
            r11 = r9
            r9 = r0
            r0 = r8
            goto L8d
        L38:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L40:
            kotlin.ResultKt.throwOnFailure(r11)
            androidx.datastore.core.okio.AtomicBoolean r11 = r9.closed
            java.util.concurrent.atomic.AtomicBoolean r11 = r11.delegate
            boolean r11 = r11.get()
            if (r11 != 0) goto L9f
            kotlinx.coroutines.sync.MutexImpl r11 = r9.transactionMutex
            boolean r11 = r11.tryLock()
            androidx.datastore.core.okio.OkioReadScope r2 = new androidx.datastore.core.okio.OkioReadScope     // Catch: java.lang.Throwable -> L96
            okio.FileSystem r5 = r9.fileSystem     // Catch: java.lang.Throwable -> L96
            okio.Path r6 = r9.path     // Catch: java.lang.Throwable -> L96
            androidx.datastore.core.okio.OkioSerializer r7 = r9.serializer     // Catch: java.lang.Throwable -> L96
            r2.<init>(r5, r6, r7)     // Catch: java.lang.Throwable -> L96
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r11)     // Catch: java.lang.Throwable -> L8a
            r0.L$0 = r9     // Catch: java.lang.Throwable -> L8a
            r0.L$1 = r2     // Catch: java.lang.Throwable -> L8a
            r0.Z$0 = r11     // Catch: java.lang.Throwable -> L8a
            r0.label = r4     // Catch: java.lang.Throwable -> L8a
            java.lang.Object r10 = r10.invoke(r2, r5, r0)     // Catch: java.lang.Throwable -> L8a
            if (r10 != r1) goto L71
            return r1
        L71:
            r0 = r9
            r9 = r11
            r11 = r10
            r10 = r2
        L75:
            r10.close()     // Catch: java.lang.Throwable -> L7a
            r10 = r3
            goto L7b
        L7a:
            r10 = move-exception
        L7b:
            if (r10 != 0) goto L85
            if (r9 == 0) goto L84
            kotlinx.coroutines.sync.MutexImpl r9 = r0.transactionMutex
            r9.unlock(r3)
        L84:
            return r11
        L85:
            throw r10     // Catch: java.lang.Throwable -> L86
        L86:
            r10 = move-exception
            r11 = r9
            r9 = r0
            goto L97
        L8a:
            r10 = move-exception
            r0 = r10
            r10 = r2
        L8d:
            r10.close()     // Catch: java.lang.Throwable -> L91
            goto L95
        L91:
            r10 = move-exception
            kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r0, r10)     // Catch: java.lang.Throwable -> L96
        L95:
            throw r0     // Catch: java.lang.Throwable -> L96
        L96:
            r10 = move-exception
        L97:
            if (r11 == 0) goto L9e
            kotlinx.coroutines.sync.MutexImpl r9 = r9.transactionMutex
            r9.unlock(r3)
        L9e:
            throw r10
        L9f:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "StorageConnection has already been disposed."
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.okio.OkioStorageConnection.readScope(kotlin.jvm.functions.Function3, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00fd A[Catch: all -> 0x010d, IOException -> 0x0110, TRY_ENTER, TryCatch #3 {IOException -> 0x0110, blocks: (B:19:0x00fd, B:21:0x0105, B:26:0x011b, B:33:0x0129, B:36:0x0126), top: B:7:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x011b A[Catch: all -> 0x010d, IOException -> 0x0110, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0110, blocks: (B:19:0x00fd, B:21:0x0105, B:26:0x011b, B:33:0x0129, B:36:0x0126), top: B:7:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.datastore.core.okio.OkioStorageConnection$writeScope$1, java.lang.Object, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference failed for: r0v4, types: [okio.FileSystem] */
    /* JADX WARN: Type inference failed for: r11v1, types: [okio.Path] */
    /* JADX WARN: Type inference failed for: r11v15 */
    /* JADX WARN: Type inference failed for: r11v16 */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v4, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r1v1, types: [okio.FileSystem] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Object, kotlinx.coroutines.sync.MutexImpl] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [kotlin.jvm.functions.Function2] */
    /* JADX WARN: Type inference failed for: r2v7 */
    @Override // androidx.datastore.core.StorageConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object writeScope(kotlin.jvm.functions.Function2 r11, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.okio.OkioStorageConnection.writeScope(kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
