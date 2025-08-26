package androidx.datastore.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public class FileReadScope implements ReadScope {
    public final AtomicBoolean closed = new AtomicBoolean(false);
    public final File file;
    public final Serializer serializer;

    /* renamed from: androidx.datastore.core.FileReadScope$readData$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function1 {
        Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(1, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return FileReadScope.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((AnonymousClass2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0041, code lost:
        
            if (r7 == r0) goto L34;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r1v1, types: [java.io.Closeable] */
        /* JADX WARN: Type inference failed for: r1v10 */
        /* JADX WARN: Type inference failed for: r1v11 */
        /* JADX WARN: Type inference failed for: r1v7, types: [java.io.Closeable] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws IOException {
            Throwable th;
            java.io.Closeable closeable;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            ?? r1 = this.label;
            try {
                try {
                } catch (FileNotFoundException unused) {
                    if (!FileReadScope.this.file.exists()) {
                        return FileReadScope.this.serializer.getDefaultValue();
                    }
                    FileInputStream fileInputStream = new FileInputStream(FileReadScope.this.file);
                    try {
                        Serializer serializer = FileReadScope.this.serializer;
                        this.L$0 = fileInputStream;
                        this.label = 2;
                        Object from = serializer.readFrom(fileInputStream);
                        if (from != coroutineSingletons) {
                            obj = from;
                            closeable = fileInputStream;
                        }
                        return coroutineSingletons;
                    } catch (Throwable th2) {
                        th = th2;
                        closeable = fileInputStream;
                        try {
                            throw th;
                        } catch (Throwable th3) {
                            CloseableKt.closeFinally(closeable, th);
                            throw th3;
                        }
                    }
                }
                if (r1 == 0) {
                    ResultKt.throwOnFailure(obj);
                    FileInputStream fileInputStream2 = new FileInputStream(FileReadScope.this.file);
                    Serializer serializer2 = FileReadScope.this.serializer;
                    this.L$0 = fileInputStream2;
                    this.label = 1;
                    obj = serializer2.readFrom(fileInputStream2);
                    r1 = fileInputStream2;
                } else {
                    if (r1 != 1) {
                        if (r1 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        closeable = (java.io.Closeable) this.L$0;
                        try {
                            ResultKt.throwOnFailure(obj);
                            CloseableKt.closeFinally(closeable, null);
                            return obj;
                        } catch (Throwable th4) {
                            th = th4;
                            throw th;
                        }
                    }
                    java.io.Closeable closeable2 = (java.io.Closeable) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    r1 = closeable2;
                }
                CloseableKt.closeFinally(r1, null);
                return obj;
            } finally {
            }
        }
    }

    public FileReadScope(File file, Serializer serializer) {
        this.file = file;
        this.serializer = serializer;
    }

    @Override // androidx.datastore.core.Closeable
    public final void close() {
        this.closed.set(true);
    }

    @Override // androidx.datastore.core.ReadScope
    public final Object readData(Continuation continuation) {
        if (this.closed.get()) {
            throw new IllegalStateException("This scope has already been closed.");
        }
        return FileStorageKt.access$runFileDiagnosticsIfNotCorruption(this.file, new AnonymousClass2(null), (ContinuationImpl) continuation);
    }
}
