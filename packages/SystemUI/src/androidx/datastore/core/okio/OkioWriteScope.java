package androidx.datastore.core.okio;

import androidx.datastore.core.WriteScope;
import androidx.datastore.preferences.core.PreferencesSerializer;
import java.io.Closeable;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import okio.FileHandle;
import okio.FileSystem;
import okio.Path;
import okio.RealBufferedSink;

/* loaded from: classes.dex */
public final class OkioWriteScope extends OkioReadScope implements WriteScope {

    /* renamed from: androidx.datastore.core.okio.OkioWriteScope$writeData$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return OkioWriteScope.this.writeData(null, this);
        }
    }

    public OkioWriteScope(FileSystem fileSystem, Path path, OkioSerializer okioSerializer) {
        super(fileSystem, path, okioSerializer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x009d A[Catch: all -> 0x00a7, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x00a7, blocks: (B:51:0x009d, B:59:0x00a9, B:21:0x0053), top: B:84:0x0053 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a9 A[Catch: all -> 0x00a7, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x00a7, blocks: (B:51:0x009d, B:59:0x00a9, B:21:0x0053), top: B:84:0x0053 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x008d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
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
    */
    public final Object writeData(Object obj, Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        ?? r0;
        Closeable closeable;
        FileHandle fileHandle;
        Throwable th;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj2 = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        Throwable th2 = null;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            if (this.closed.delegate.get()) {
                throw new IllegalStateException("This scope has already been closed.");
            }
            FileHandle fileHandleOpenReadWrite = this.fileSystem.openReadWrite(this.path);
            try {
                RealBufferedSink realBufferedSink = new RealBufferedSink(FileHandle.sink$default(fileHandleOpenReadWrite));
                try {
                    OkioSerializer okioSerializer = this.serializer;
                    anonymousClass1.L$0 = fileHandleOpenReadWrite;
                    anonymousClass1.L$1 = fileHandleOpenReadWrite;
                    anonymousClass1.L$2 = realBufferedSink;
                    anonymousClass1.label = 1;
                    try {
                        if (((PreferencesSerializer) okioSerializer).writeTo(obj, realBufferedSink) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        fileHandle = fileHandleOpenReadWrite;
                        r0 = fileHandle;
                        closeable = realBufferedSink;
                    } catch (Throwable th3) {
                        th = th3;
                        r0 = fileHandleOpenReadWrite;
                        closeable = realBufferedSink;
                        if (closeable != null) {
                            try {
                                closeable.close();
                            } catch (Throwable th4) {
                                try {
                                    ExceptionsKt__ExceptionsKt.addSuppressed(th, th4);
                                } catch (Throwable th5) {
                                    th = th5;
                                    fileHandleOpenReadWrite = r0;
                                    if (fileHandleOpenReadWrite != null) {
                                        try {
                                            fileHandleOpenReadWrite.close();
                                        } catch (Throwable th6) {
                                            ExceptionsKt__ExceptionsKt.addSuppressed(th, th6);
                                        }
                                    }
                                    th2 = th;
                                    if (th2 == null) {
                                    }
                                }
                            }
                        }
                        th = th;
                        Closeable closeable2 = r0;
                        if (th == null) {
                        }
                    }
                } catch (Throwable th7) {
                    th = th7;
                }
            } catch (Throwable th8) {
                th = th8;
                if (fileHandleOpenReadWrite != null) {
                }
                th2 = th;
                if (th2 == null) {
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            closeable = (Closeable) anonymousClass1.L$2;
            fileHandle = (FileHandle) anonymousClass1.L$1;
            r0 = (Closeable) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj2);
                r0 = r0;
            } catch (Throwable th9) {
                th = th9;
                if (closeable != null) {
                }
                th = th;
                Closeable closeable22 = r0;
                if (th == null) {
                }
            }
        }
        fileHandle.flush();
        Unit unit = Unit.INSTANCE;
        if (closeable != null) {
            try {
                closeable.close();
                th = null;
            } catch (Throwable th10) {
                th = th10;
            }
            Closeable closeable222 = r0;
            if (th == null) {
                throw th;
            }
            Unit unit2 = Unit.INSTANCE;
            if (closeable222 != null) {
                try {
                    closeable222.close();
                } catch (Throwable th11) {
                    th2 = th11;
                }
            }
        } else {
            th = null;
            Closeable closeable2222 = r0;
            if (th == null) {
            }
        }
        if (th2 == null) {
            return Unit.INSTANCE;
        }
        throw th2;
    }
}
