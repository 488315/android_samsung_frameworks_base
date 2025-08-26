package androidx.datastore.core.okio;

import androidx.datastore.core.Closeable;
import androidx.datastore.core.InterProcessCoordinator;
import androidx.datastore.core.StorageConnection;
import com.samsung.android.knox.lockscreen.LSOUtils;
import java.io.IOException;
import java.util.Iterator;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;
import okio.FileSystem;
import okio.Path;

/* loaded from: classes.dex */
public final class OkioStorageConnection implements StorageConnection {
    public final InterProcessCoordinator coordinator;
    public final FileSystem fileSystem;
    public final Function0 onClose;
    public final Path path;
    public final OkioSerializer serializer;
    public final AtomicBoolean closed = new AtomicBoolean(false);
    public final MutexImpl transactionMutex = MutexKt.Mutex$default();

    /* renamed from: androidx.datastore.core.okio.OkioStorageConnection$readScope$1, reason: invalid class name */
    final class AnonymousClass1<R> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OkioStorageConnection.this.readScope(null, this);
        }
    }

    /* renamed from: androidx.datastore.core.okio.OkioStorageConnection$writeScope$1, reason: invalid class name and case insensitive filesystem */
    final class C07611 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public C07611(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OkioStorageConnection.this.writeScope(null, this);
        }
    }

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0085 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r10v0, types: [kotlin.jvm.functions.Function3] */
    /* JADX WARN: Type inference failed for: r11v8, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v7 */
    @Override // androidx.datastore.core.StorageConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object readScope(Function3 function3, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Throwable th;
        Closeable closeable;
        OkioStorageConnection okioStorageConnection;
        ?? r9;
        Object obj;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj2 = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                if (this.closed.delegate.get()) {
                    throw new IllegalStateException("StorageConnection has already been disposed.");
                }
                ?? TryLock = this.transactionMutex.tryLock();
                OkioReadScope okioReadScope = new OkioReadScope(this.fileSystem, this.path, this.serializer);
                try {
                    Boolean boolValueOf = Boolean.valueOf((boolean) TryLock);
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = okioReadScope;
                    anonymousClass1.Z$0 = TryLock;
                    anonymousClass1.label = 1;
                    Object objInvoke = function3.invoke(okioReadScope, boolValueOf, anonymousClass1);
                    if (objInvoke == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    okioStorageConnection = this;
                    r9 = TryLock == true ? 1 : 0;
                    obj = objInvoke;
                    closeable = okioReadScope;
                    closeable.close();
                    th = null;
                    if (th != null) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    closeable = okioReadScope;
                    obj2 = TryLock;
                    closeable.close();
                    throw th;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                r9 = anonymousClass1.Z$0;
                closeable = (Closeable) anonymousClass1.L$1;
                okioStorageConnection = (OkioStorageConnection) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(obj2);
                    obj = obj2;
                    try {
                        closeable.close();
                        th = null;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                    if (th != null) {
                        if (r9 != false) {
                            okioStorageConnection.transactionMutex.unlock(null);
                        }
                        return obj;
                    }
                    try {
                        throw th;
                    } catch (Throwable th4) {
                        th = th4;
                        obj2 = r9;
                        this = okioStorageConnection;
                    }
                } catch (Throwable th5) {
                    obj2 = r9;
                    this = okioStorageConnection;
                    th = th5;
                    try {
                        closeable.close();
                        throw th;
                    } catch (Throwable th6) {
                        ExceptionsKt__ExceptionsKt.addSuppressed(th, th6);
                        throw th;
                    }
                }
            }
        } catch (Throwable th7) {
            th = th7;
        }
        if (obj2 != null) {
            this.transactionMutex.unlock(null);
        }
        throw th;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00fd A[Catch: all -> 0x010d, IOException -> 0x0110, TRY_ENTER, TryCatch #3 {IOException -> 0x0110, blocks: (B:50:0x00fd, B:52:0x0105, B:61:0x011b, B:68:0x0129, B:67:0x0126), top: B:86:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x011b A[Catch: all -> 0x010d, IOException -> 0x0110, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0110, blocks: (B:50:0x00fd, B:52:0x0105, B:61:0x011b, B:68:0x0129, B:67:0x0126), top: B:86:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
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
    */
    public final Object writeScope(Function2 function2, ContinuationImpl continuationImpl) throws Throwable {
        ?? c07611;
        ?? r11;
        OkioStorageConnection okioStorageConnection;
        ?? r2;
        Path path;
        Mutex mutex;
        FileSystem fileSystem;
        Path pathResolve;
        OkioWriteScope okioWriteScope;
        Throwable th;
        Closeable closeable;
        OkioStorageConnection okioStorageConnection2;
        Mutex mutex2;
        Path path2;
        if (continuationImpl instanceof C07611) {
            C07611 c076112 = (C07611) continuationImpl;
            int i = c076112.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c076112.label = i - Integer.MIN_VALUE;
                c07611 = c076112;
            } else {
                c07611 = new C07611(continuationImpl);
            }
        }
        ?? r12 = c07611.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07611.label;
        try {
            try {
                try {
                } catch (Throwable th2) {
                    th = th2;
                    r12.unlock(null);
                    throw th;
                }
            } catch (IOException e) {
                e = e;
                okioStorageConnection = c07611;
                r11 = function2;
            }
            try {
                try {
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(r12);
                        if (this.closed.delegate.get()) {
                            throw new IllegalStateException("StorageConnection has already been disposed.");
                        }
                        Path pathParent = this.path.parent();
                        if (pathParent == null) {
                            throw new IllegalStateException("must have a parent path");
                        }
                        FileSystem fileSystem2 = this.fileSystem;
                        fileSystem2.getClass();
                        ArrayDeque arrayDeque = new ArrayDeque();
                        for (Path pathParent2 = pathParent; pathParent2 != null && !fileSystem2.exists(pathParent2); pathParent2 = pathParent2.parent()) {
                            arrayDeque.addFirst(pathParent2);
                        }
                        Iterator it = arrayDeque.iterator();
                        while (it.hasNext()) {
                            fileSystem2.createDirectory((Path) it.next());
                        }
                        ?? r22 = this.transactionMutex;
                        c07611.L$0 = this;
                        c07611.L$1 = function2;
                        c07611.L$2 = pathParent;
                        c07611.L$3 = r22;
                        c07611.label = 1;
                        if (r22.lock(c07611) != coroutineSingletons) {
                            r2 = function2;
                            path = pathParent;
                            mutex = r22;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        closeable = (Closeable) c07611.L$3;
                        Path path3 = (Path) c07611.L$2;
                        mutex2 = (Mutex) c07611.L$1;
                        okioStorageConnection2 = (OkioStorageConnection) c07611.L$0;
                        try {
                            ResultKt.throwOnFailure(r12);
                            path2 = path3;
                            Unit unit = Unit.INSTANCE;
                            try {
                                closeable.close();
                                th = null;
                            } catch (Throwable th3) {
                                th = th3;
                            }
                            if (th == null) {
                                throw th;
                            }
                            if (okioStorageConnection2.fileSystem.exists(path2)) {
                                okioStorageConnection2.fileSystem.atomicMove(path2, okioStorageConnection2.path);
                            }
                            Unit unit2 = Unit.INSTANCE;
                            mutex2.unlock(null);
                            return Unit.INSTANCE;
                        } catch (Throwable th4) {
                            th = th4;
                            try {
                                closeable.close();
                            } catch (Throwable th5) {
                                ExceptionsKt__ExceptionsKt.addSuppressed(th, th5);
                            }
                            throw th;
                        }
                    }
                    Mutex mutex3 = (Mutex) c07611.L$3;
                    path = (Path) c07611.L$2;
                    Function2 function22 = (Function2) c07611.L$1;
                    OkioStorageConnection okioStorageConnection3 = (OkioStorageConnection) c07611.L$0;
                    ResultKt.throwOnFailure(r12);
                    mutex = mutex3;
                    this = okioStorageConnection3;
                    r2 = function22;
                    c07611.L$0 = this;
                    c07611.L$1 = mutex;
                    c07611.L$2 = pathResolve;
                    c07611.L$3 = okioWriteScope;
                    c07611.label = 2;
                    if (r2.invoke(okioWriteScope, c07611) != coroutineSingletons) {
                        okioStorageConnection2 = this;
                        mutex2 = mutex;
                        closeable = okioWriteScope;
                        path2 = pathResolve;
                        Unit unit3 = Unit.INSTANCE;
                        closeable.close();
                        th = null;
                        if (th == null) {
                        }
                    }
                    return coroutineSingletons;
                } catch (Throwable th6) {
                    th = th6;
                    closeable = okioWriteScope;
                    closeable.close();
                    throw th;
                }
                fileSystem.delete(pathResolve);
                okioWriteScope = new OkioWriteScope(fileSystem, pathResolve, this.serializer);
            } catch (IOException e2) {
                okioStorageConnection = this;
                e = e2;
                r11 = pathResolve;
                if (!okioStorageConnection.fileSystem.exists(r11)) {
                    throw e;
                }
                try {
                    okioStorageConnection.fileSystem.delete(r11);
                    throw e;
                } catch (IOException unused) {
                    throw e;
                }
            }
            StringBuilder sb = new StringBuilder();
            Path path4 = this.path;
            fileSystem = this.fileSystem;
            sb.append(path4.name());
            sb.append(LSOUtils.TEMP_DIR);
            pathResolve = path.resolve(sb.toString());
        } catch (Throwable th7) {
            th = th7;
            r12 = coroutineSingletons;
            r12.unlock(null);
            throw th;
        }
    }
}
