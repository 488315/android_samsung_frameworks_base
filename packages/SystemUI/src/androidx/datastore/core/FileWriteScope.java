package androidx.datastore.core;

import java.io.File;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FileWriteScope extends FileReadScope implements WriteScope {
    public FileWriteScope(File file, Serializer serializer) {
        super(file, serializer);
    }

    @Override // androidx.datastore.core.WriteScope
    public final Object writeData(Object obj, Continuation continuation) {
        if (this.closed.get()) {
            throw new IllegalStateException("This scope has already been closed.");
        }
        Object access$runFileDiagnosticsIfNotCorruption = FileStorageKt.access$runFileDiagnosticsIfNotCorruption(this.file, new FileWriteScope$writeData$2(this, obj, null), (ContinuationImpl) continuation);
        return access$runFileDiagnosticsIfNotCorruption == CoroutineSingletons.COROUTINE_SUSPENDED ? access$runFileDiagnosticsIfNotCorruption : Unit.INSTANCE;
    }
}
