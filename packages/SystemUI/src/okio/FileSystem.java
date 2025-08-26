package okio;

import java.io.Closeable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okio.Path;
import okio.internal.ResourceFileSystem;

/* loaded from: classes4.dex */
public abstract class FileSystem implements Closeable {
    public static final JvmSystemFileSystem SYSTEM;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        JvmSystemFileSystem jvmSystemFileSystem;
        new Companion(null);
        try {
            Class.forName("java.nio.file.Files");
            jvmSystemFileSystem = new NioSystemFileSystem();
        } catch (ClassNotFoundException unused) {
            jvmSystemFileSystem = new JvmSystemFileSystem();
        }
        SYSTEM = jvmSystemFileSystem;
        Path.Companion.get$default(Path.Companion, System.getProperty("java.io.tmpdir"));
        new ResourceFileSystem(ResourceFileSystem.class.getClassLoader(), false, null, 4, null);
    }

    public abstract void atomicMove(Path path, Path path2);

    public abstract void createDirectory(Path path);

    public abstract void delete(Path path);

    public final boolean exists(Path path) {
        return metadataOrNull(path) != null;
    }

    public abstract FileMetadata metadataOrNull(Path path);

    public abstract FileHandle openReadOnly(Path path);

    public abstract FileHandle openReadWrite(Path path);

    public abstract Source source(Path path);

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }
}
