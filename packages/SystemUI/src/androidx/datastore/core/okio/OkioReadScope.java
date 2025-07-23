package androidx.datastore.core.okio;

import androidx.datastore.core.ReadScope;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import okio.FileSystem;
import okio.Path;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class OkioReadScope implements ReadScope {
    public final AtomicBoolean closed = new AtomicBoolean(false);
    public final FileSystem fileSystem;
    public final Path path;
    public final OkioSerializer serializer;

    public OkioReadScope(FileSystem fileSystem, Path path, OkioSerializer okioSerializer) {
        this.fileSystem = fileSystem;
        this.path = path;
        this.serializer = okioSerializer;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(2:3|(7:5|6|7|(1:(1:(6:11|12|13|(2:21|22)|15|(2:17|18)(1:20))(2:33|34))(3:35|36|37))(2:58|(6:62|63|64|65|(1:67)|68)(2:60|61))|(2:45|46)|39|(2:41|42)(1:43)))|87|6|7|(0)(0)|(0)|39|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0092, code lost:
    
        r8 = r2;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00d6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0099 A[Catch: FileNotFoundException -> 0x0092, TRY_LEAVE, TryCatch #3 {FileNotFoundException -> 0x0092, blocks: (B:43:0x0099, B:57:0x008e, B:54:0x0089), top: B:7:0x0021, inners: #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v11, types: [androidx.datastore.core.okio.OkioReadScope] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object readData$suspendImpl(androidx.datastore.core.okio.OkioReadScope r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.okio.OkioReadScope.readData$suspendImpl(androidx.datastore.core.okio.OkioReadScope, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // androidx.datastore.core.Closeable
    public final void close() {
        this.closed.delegate.set(true);
    }

    @Override // androidx.datastore.core.ReadScope
    public final Object readData(Continuation continuation) {
        return readData$suspendImpl(this, (ContinuationImpl) continuation);
    }
}
