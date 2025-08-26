package androidx.datastore.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class FileWriteScope extends FileReadScope implements WriteScope {

    /* renamed from: androidx.datastore.core.FileWriteScope$writeData$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function1 {
        final /* synthetic */ Object $value;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Object obj, Continuation continuation) {
            super(1, continuation);
            this.$value = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return FileWriteScope.this.new AnonymousClass2(this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((AnonymousClass2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1 */
        /* JADX WARN: Type inference failed for: r0v3, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r0v6 */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws IOException {
            Throwable th;
            java.io.Closeable closeable;
            ?? r0;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FileOutputStream fileOutputStream = new FileOutputStream(FileWriteScope.this.file);
                FileWriteScope fileWriteScope = FileWriteScope.this;
                Object obj2 = this.$value;
                try {
                    Serializer serializer = fileWriteScope.serializer;
                    UncloseableOutputStream uncloseableOutputStream = new UncloseableOutputStream(fileOutputStream);
                    this.L$0 = fileOutputStream;
                    this.L$1 = fileOutputStream;
                    this.label = 1;
                    if (serializer.writeTo(obj2, uncloseableOutputStream) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    closeable = fileOutputStream;
                    r0 = closeable;
                } catch (Throwable th2) {
                    th = th2;
                    closeable = fileOutputStream;
                    throw th;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                FileOutputStream fileOutputStream2 = (FileOutputStream) this.L$1;
                closeable = (java.io.Closeable) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    r0 = fileOutputStream2;
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        throw th;
                    } catch (Throwable th4) {
                        CloseableKt.closeFinally(closeable, th);
                        throw th4;
                    }
                }
            }
            r0.getFD().sync();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(closeable, null);
            return Unit.INSTANCE;
        }
    }

    public FileWriteScope(File file, Serializer serializer) {
        super(file, serializer);
    }

    @Override // androidx.datastore.core.WriteScope
    public final Object writeData(Object obj, Continuation continuation) throws IOException {
        if (this.closed.get()) {
            throw new IllegalStateException("This scope has already been closed.");
        }
        Object objAccess$runFileDiagnosticsIfNotCorruption = FileStorageKt.access$runFileDiagnosticsIfNotCorruption(this.file, new AnonymousClass2(obj, null), (ContinuationImpl) continuation);
        return objAccess$runFileDiagnosticsIfNotCorruption == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$runFileDiagnosticsIfNotCorruption : Unit.INSTANCE;
    }
}
