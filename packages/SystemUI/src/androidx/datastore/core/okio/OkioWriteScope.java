package androidx.datastore.core.okio;

import androidx.datastore.core.WriteScope;
import okio.FileSystem;
import okio.Path;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OkioWriteScope extends OkioReadScope implements WriteScope {
    public OkioWriteScope(FileSystem fileSystem, Path path, OkioSerializer okioSerializer) {
        super(fileSystem, path, okioSerializer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x009d A[Catch: all -> 0x00a7, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x00a7, blocks: (B:17:0x009d, B:28:0x00a9, B:59:0x0053), top: B:58:0x0053 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a9 A[Catch: all -> 0x00a7, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x00a7, blocks: (B:17:0x009d, B:28:0x00a9, B:59:0x0053), top: B:58:0x0053 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    @Override // androidx.datastore.core.WriteScope
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object writeData(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
        /*
            Method dump skipped, instructions count: 195
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.okio.OkioWriteScope.writeData(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
