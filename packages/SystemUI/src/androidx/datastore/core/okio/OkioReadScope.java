package androidx.datastore.core.okio;

import androidx.datastore.core.ReadScope;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.PreferencesSerializer;
import java.io.Closeable;
import java.io.FileNotFoundException;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import okio.FileSystem;
import okio.Path;
import okio.RealBufferedSource;

/* loaded from: classes.dex */
public class OkioReadScope implements ReadScope {
    public final AtomicBoolean closed = new AtomicBoolean(false);
    public final FileSystem fileSystem;
    public final Path path;
    public final OkioSerializer serializer;

    /* renamed from: androidx.datastore.core.okio.OkioReadScope$readData$1, reason: invalid class name */
    final class AnonymousClass1<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OkioReadScope.readData$suspendImpl(OkioReadScope.this, this);
        }
    }

    public OkioReadScope(FileSystem fileSystem, Path path, OkioSerializer okioSerializer) {
        this.fileSystem = fileSystem;
        this.path = path;
        this.serializer = okioSerializer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0099 A[Catch: FileNotFoundException -> 0x0092, TRY_LEAVE, TryCatch #3 {FileNotFoundException -> 0x0092, blocks: (B:49:0x0099, B:44:0x008e, B:41:0x0089), top: B:84:0x0021, inners: #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00d6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
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
    */
    public static Object readData$suspendImpl(OkioReadScope okioReadScope, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Throwable th;
        Closeable closeable;
        MutablePreferences from;
        Object obj;
        Closeable closeable2;
        Throwable th2;
        Object obj2;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = okioReadScope.new AnonymousClass1(continuationImpl);
            }
        }
        Object obj3 = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        ?? r2 = anonymousClass1.label;
        Object th3 = null;
        try {
        } catch (FileNotFoundException unused) {
            okioReadScope = r2;
        }
        if (r2 == 0) {
            ResultKt.throwOnFailure(obj3);
            if (okioReadScope.closed.delegate.get()) {
                throw new IllegalStateException("This scope has already been closed.");
            }
            try {
                RealBufferedSource realBufferedSource = new RealBufferedSource(okioReadScope.fileSystem.source(okioReadScope.path));
                try {
                    OkioSerializer okioSerializer = okioReadScope.serializer;
                    anonymousClass1.L$0 = okioReadScope;
                    anonymousClass1.L$1 = realBufferedSource;
                    anonymousClass1.label = 1;
                    MutablePreferences from2 = ((PreferencesSerializer) okioSerializer).readFrom(realBufferedSource);
                    if (from2 != coroutineSingletons) {
                        r2 = okioReadScope;
                        closeable2 = realBufferedSource;
                        obj2 = from2;
                        if (closeable2 == null) {
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    r2 = okioReadScope;
                    closeable2 = realBufferedSource;
                    if (closeable2 != null) {
                        try {
                            closeable2.close();
                        } catch (Throwable th5) {
                            ExceptionsKt__ExceptionsKt.addSuppressed(th, th5);
                        }
                    }
                    th2 = th;
                    obj2 = null;
                    if (th2 == null) {
                    }
                }
            } catch (FileNotFoundException unused2) {
                FileSystem fileSystem = okioReadScope.fileSystem;
                Path path = okioReadScope.path;
                boolean zExists = fileSystem.exists(path);
                OkioSerializer okioSerializer2 = okioReadScope.serializer;
                if (!zExists) {
                    ((PreferencesSerializer) okioSerializer2).getClass();
                    return new MutablePreferences(null, true, 1, null);
                }
                RealBufferedSource realBufferedSource2 = new RealBufferedSource(okioReadScope.fileSystem.source(path));
                try {
                    anonymousClass1.L$0 = realBufferedSource2;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.label = 2;
                    from = ((PreferencesSerializer) okioSerializer2).readFrom(realBufferedSource2);
                } catch (Throwable th6) {
                    th = th6;
                    closeable = realBufferedSource2;
                    if (closeable != null) {
                    }
                    if (th != null) {
                    }
                }
                if (from != coroutineSingletons) {
                    obj = from;
                    closeable = realBufferedSource2;
                    if (closeable != null) {
                    }
                    Throwable th7 = th3;
                    th3 = obj;
                    th = th7;
                    if (th != null) {
                    }
                }
                return coroutineSingletons;
            }
            return coroutineSingletons;
        }
        if (r2 != 1) {
            if (r2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            closeable = (Closeable) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj3);
                obj = obj3;
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (Throwable th8) {
                        th3 = th8;
                    }
                }
                Throwable th72 = th3;
                th3 = obj;
                th = th72;
            } catch (Throwable th9) {
                th = th9;
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (Throwable th10) {
                        ExceptionsKt__ExceptionsKt.addSuppressed(th, th10);
                    }
                }
                if (th != null) {
                }
            }
            if (th != null) {
                return th3;
            }
            throw th;
        }
        closeable2 = (Closeable) anonymousClass1.L$1;
        r2 = (OkioReadScope) anonymousClass1.L$0;
        try {
            ResultKt.throwOnFailure(obj3);
            r2 = r2;
            obj2 = obj3;
            if (closeable2 == null) {
                try {
                    closeable2.close();
                    th2 = null;
                } catch (Throwable th11) {
                    th2 = th11;
                }
            } else {
                th2 = null;
            }
        } catch (Throwable th12) {
            th = th12;
            if (closeable2 != null) {
            }
            th2 = th;
            obj2 = null;
            if (th2 == null) {
            }
        }
        if (th2 == null) {
            return obj2;
        }
        throw th2;
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
