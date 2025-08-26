package androidx.datastore.core;

import com.samsung.android.knox.lockscreen.LSOUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes.dex */
public final class FileStorageConnection implements StorageConnection {
    public final InterProcessCoordinator coordinator;
    public final File file;
    public final Function0 onClose;
    public final Serializer serializer;
    public final AtomicBoolean closed = new AtomicBoolean(false);
    public final MutexImpl transactionMutex = MutexKt.Mutex$default();

    /* renamed from: androidx.datastore.core.FileStorageConnection$readScope$1, reason: invalid class name */
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
            return FileStorageConnection.this.readScope(null, this);
        }
    }

    /* renamed from: androidx.datastore.core.FileStorageConnection$writeScope$1, reason: invalid class name and case insensitive filesystem */
    final class C07591 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public C07591(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FileStorageConnection.this.writeScope(null, this);
        }
    }

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0083 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r10v7, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v7 */
    @Override // androidx.datastore.core.StorageConnection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object readScope(Function3 function3, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Throwable th;
        Closeable closeable;
        FileStorageConnection fileStorageConnection;
        ?? r8;
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
                if (this.closed.get()) {
                    throw new IllegalStateException("StorageConnection has already been disposed.");
                }
                ?? TryLock = this.transactionMutex.tryLock();
                FileReadScope fileReadScope = new FileReadScope(this.file, this.serializer);
                try {
                    Boolean boolValueOf = Boolean.valueOf((boolean) TryLock);
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = fileReadScope;
                    anonymousClass1.Z$0 = TryLock;
                    anonymousClass1.label = 1;
                    Object objInvoke = ((StorageConnectionKt$readData$2) function3).invoke(fileReadScope, boolValueOf, anonymousClass1);
                    if (objInvoke == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    fileStorageConnection = this;
                    r8 = TryLock == true ? 1 : 0;
                    obj = objInvoke;
                    closeable = fileReadScope;
                    closeable.close();
                    th = null;
                    if (th != null) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    closeable = fileReadScope;
                    obj2 = TryLock;
                    closeable.close();
                    throw th;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                r8 = anonymousClass1.Z$0;
                closeable = (Closeable) anonymousClass1.L$1;
                fileStorageConnection = (FileStorageConnection) anonymousClass1.L$0;
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
                        if (r8 != false) {
                            fileStorageConnection.transactionMutex.unlock(null);
                        }
                        return obj;
                    }
                    try {
                        throw th;
                    } catch (Throwable th4) {
                        th = th4;
                        obj2 = r8;
                        this = fileStorageConnection;
                    }
                } catch (Throwable th5) {
                    obj2 = r8;
                    this = fileStorageConnection;
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
    /* JADX WARN: Removed duplicated region for block: B:43:0x00e3 A[Catch: all -> 0x0113, IOException -> 0x0116, TRY_ENTER, TryCatch #3 {all -> 0x0113, blocks: (B:43:0x00e3, B:45:0x00e9, B:48:0x00f2, B:49:0x0112, B:54:0x011a, B:57:0x0122, B:64:0x0130, B:63:0x012d), top: B:78:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0122 A[Catch: all -> 0x0113, IOException -> 0x0116, TRY_ENTER, TRY_LEAVE, TryCatch #3 {all -> 0x0113, blocks: (B:43:0x00e3, B:45:0x00e9, B:48:0x00f2, B:49:0x0112, B:54:0x011a, B:57:0x0122, B:64:0x0130, B:63:0x012d), top: B:78:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
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
    */
    public final Object writeScope(Function2 function2, ContinuationImpl continuationImpl) throws Throwable {
        C07591 c07591;
        ?? file;
        MutexImpl mutexImpl;
        Function2 function22;
        FileWriteScope fileWriteScope;
        Throwable th;
        Closeable closeable;
        FileStorageConnection fileStorageConnection;
        Mutex mutex;
        File file2;
        if (continuationImpl instanceof C07591) {
            c07591 = (C07591) continuationImpl;
            int i = c07591.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07591.label = i - Integer.MIN_VALUE;
            } else {
                c07591 = new C07591(continuationImpl);
            }
        }
        ?? r10 = c07591.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07591.label;
        try {
            try {
                try {
                } catch (Throwable th2) {
                    th = th2;
                    r10.unlock(null);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                r10 = coroutineSingletons;
                r10.unlock(null);
                throw th;
            }
        } catch (IOException e) {
            e = e;
            file = function2;
        }
        try {
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(r10);
                    if (this.closed.get()) {
                        throw new IllegalStateException("StorageConnection has already been disposed.");
                    }
                    File file3 = this.file;
                    File parentFile = file3.getCanonicalFile().getParentFile();
                    if (parentFile != null) {
                        parentFile.mkdirs();
                        if (!parentFile.isDirectory()) {
                            throw new IOException("Unable to create parent directories of " + file3);
                        }
                    }
                    mutexImpl = this.transactionMutex;
                    c07591.L$0 = this;
                    c07591.L$1 = function2;
                    c07591.L$2 = mutexImpl;
                    c07591.label = 1;
                    function22 = function2;
                    if (mutexImpl.lock(c07591) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    closeable = (Closeable) c07591.L$3;
                    file2 = (File) c07591.L$2;
                    mutex = (Mutex) c07591.L$1;
                    fileStorageConnection = (FileStorageConnection) c07591.L$0;
                    try {
                        ResultKt.throwOnFailure(r10);
                        Unit unit = Unit.INSTANCE;
                        try {
                            closeable.close();
                            th = null;
                        } catch (Throwable th4) {
                            th = th4;
                        }
                        if (th == null) {
                            throw th;
                        }
                        if (file2.exists() && !FileMoves_androidKt.atomicMoveTo(file2, fileStorageConnection.file)) {
                            throw new IOException("Unable to rename " + file2 + " to " + fileStorageConnection.file + ". This likely means that there are multiple instances of DataStore for this file. Ensure that you are only creating a single instance of datastore for this file.");
                        }
                        Unit unit2 = Unit.INSTANCE;
                        mutex.unlock(null);
                        return Unit.INSTANCE;
                    } catch (Throwable th5) {
                        th = th5;
                        try {
                            closeable.close();
                        } catch (Throwable th6) {
                            ExceptionsKt__ExceptionsKt.addSuppressed(th, th6);
                        }
                        throw th;
                    }
                }
                ?? r8 = (Mutex) c07591.L$2;
                Function2 function23 = (Function2) c07591.L$1;
                FileStorageConnection fileStorageConnection2 = (FileStorageConnection) c07591.L$0;
                ResultKt.throwOnFailure(r10);
                mutexImpl = r8;
                this = fileStorageConnection2;
                function22 = function23;
                c07591.L$0 = this;
                c07591.L$1 = mutexImpl;
                c07591.L$2 = file;
                c07591.L$3 = fileWriteScope;
                c07591.label = 2;
                if (function22.invoke(fileWriteScope, c07591) != coroutineSingletons) {
                    fileStorageConnection = this;
                    mutex = mutexImpl;
                    file2 = file;
                    closeable = fileWriteScope;
                    Unit unit3 = Unit.INSTANCE;
                    closeable.close();
                    th = null;
                    if (th == null) {
                    }
                }
                return coroutineSingletons;
            } catch (Throwable th7) {
                th = th7;
                closeable = fileWriteScope;
                closeable.close();
                throw th;
            }
            fileWriteScope = new FileWriteScope(file, this.serializer);
        } catch (IOException e2) {
            e = e2;
            if (file.exists()) {
                file.delete();
            }
            throw e;
        }
        file = new File(this.file.getAbsolutePath() + LSOUtils.TEMP_DIR);
    }
}
