package androidx.datastore.core;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FileStorage implements Storage {
    public static final Set activeFiles;
    public static final Object activeFilesLock;
    public final Function1 coordinatorProducer;
    public final Function0 produceFile;
    public final Serializer serializer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        activeFiles = new LinkedHashSet();
        activeFilesLock = new Object();
    }

    public FileStorage(Serializer serializer, Function1 function1, Function0 function0) {
        this.serializer = serializer;
        this.coordinatorProducer = function1;
        this.produceFile = function0;
    }

    @Override // androidx.datastore.core.Storage
    public final StorageConnection createConnection() {
        final File canonicalFile = ((File) this.produceFile.invoke()).getCanonicalFile();
        synchronized (activeFilesLock) {
            String absolutePath = canonicalFile.getAbsolutePath();
            Set set = activeFiles;
            if (set.contains(absolutePath)) {
                throw new IllegalStateException(("There are multiple DataStores active for the same file: " + absolutePath + ". You should either maintain your DataStore as a singleton or confirm that there is no two DataStore's active on the same file (by confirming that the scope is cancelled).").toString());
            }
            absolutePath.getClass();
            set.add(absolutePath);
        }
        return new FileStorageConnection(canonicalFile, this.serializer, (InterProcessCoordinator) this.coordinatorProducer.mo779invoke(canonicalFile), new Function0() { // from class: androidx.datastore.core.FileStorage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                File file = canonicalFile;
                synchronized (FileStorage.activeFilesLock) {
                    FileStorage.activeFiles.remove(file.getAbsolutePath());
                }
                return Unit.INSTANCE;
            }
        });
    }

    public /* synthetic */ FileStorage(Serializer serializer, Function1 function1, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(serializer, (i & 2) != 0 ? new FileStorage$$ExternalSyntheticLambda0() : function1, function0);
    }
}
