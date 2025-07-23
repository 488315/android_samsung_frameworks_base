package androidx.datastore.core.okio;

import androidx.datastore.core.InterProcessCoordinator;
import androidx.datastore.core.Storage;
import androidx.datastore.core.StorageConnection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okio.FileSystem;
import okio.Path;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OkioStorage implements Storage {
    public static final Set activeFiles;
    public static final Synchronizer activeFilesLock;
    public final Lazy canonicalPath$delegate;
    public final Function2 coordinatorProducer;
    public final FileSystem fileSystem;
    public final Function0 producePath;
    public final OkioSerializer serializer;

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
        activeFilesLock = new Synchronizer();
    }

    public OkioStorage(FileSystem fileSystem, OkioSerializer okioSerializer, Function2 function2, Function0 function0) {
        this.fileSystem = fileSystem;
        this.serializer = okioSerializer;
        this.coordinatorProducer = function2;
        this.producePath = function0;
        this.canonicalPath$delegate = LazyKt__LazyJVMKt.lazy(new OkioStorage$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // androidx.datastore.core.Storage
    public final StorageConnection createConnection() {
        String utf8 = ((Path) this.canonicalPath$delegate.getValue()).bytes.utf8();
        synchronized (activeFilesLock) {
            Set set = activeFiles;
            if (set.contains(utf8)) {
                throw new IllegalStateException(("There are multiple DataStores active for the same file: " + utf8 + ". You should either maintain your DataStore as a singleton or confirm that there is no two DataStore's active on the same file (by confirming that the scope is cancelled).").toString());
            }
            set.add(utf8);
        }
        return new OkioStorageConnection(this.fileSystem, (Path) this.canonicalPath$delegate.getValue(), this.serializer, (InterProcessCoordinator) this.coordinatorProducer.invoke((Path) this.canonicalPath$delegate.getValue(), this.fileSystem), new OkioStorage$$ExternalSyntheticLambda0(this, 0));
    }

    public /* synthetic */ OkioStorage(FileSystem fileSystem, OkioSerializer okioSerializer, Function2 function2, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(fileSystem, okioSerializer, (i & 4) != 0 ? new OkioStorage$$ExternalSyntheticLambda1() : function2, function0);
    }
}
