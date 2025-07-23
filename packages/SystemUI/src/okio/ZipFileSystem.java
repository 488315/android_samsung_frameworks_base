package okio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.zip.Inflater;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okio.Path;
import okio.internal.FixedLengthSource;
import okio.internal.ZipEntry;
import okio.internal.ZipFilesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ZipFileSystem extends FileSystem {
    public static final Path ROOT;
    public final Map entries;
    public final FileSystem fileSystem;
    public final Path zipPath;

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
        ROOT = Path.Companion.get$default(Path.Companion, "/");
    }

    public ZipFileSystem(Path path, FileSystem fileSystem, Map<Path, ZipEntry> map, String str) {
        this.zipPath = path;
        this.fileSystem = fileSystem;
        this.entries = map;
    }

    @Override // okio.FileSystem
    public final void atomicMove(Path path, Path path2) {
        throw new IOException("zip file systems are read-only");
    }

    @Override // okio.FileSystem
    public final void createDirectory(Path path) {
        throw new IOException("zip file systems are read-only");
    }

    @Override // okio.FileSystem
    public final void delete(Path path) {
        throw new IOException("zip file systems are read-only");
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x011c  */
    @Override // okio.FileSystem
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final okio.FileMetadata metadataOrNull(okio.Path r25) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.ZipFileSystem.metadataOrNull(okio.Path):okio.FileMetadata");
    }

    @Override // okio.FileSystem
    public final FileHandle openReadOnly(Path path) {
        throw new UnsupportedOperationException("not implemented yet!");
    }

    @Override // okio.FileSystem
    public final FileHandle openReadWrite(Path path) {
        throw new IOException("zip entries are not writable");
    }

    @Override // okio.FileSystem
    public final Source source(Path path) {
        RealBufferedSource realBufferedSource;
        Throwable th;
        Path path2 = ROOT;
        path2.getClass();
        ZipEntry zipEntry = (ZipEntry) this.entries.get(okio.internal.Path.commonResolve(path2, path, true));
        if (zipEntry == null) {
            throw new FileNotFoundException("no such file: " + path);
        }
        FileHandle openReadOnly = this.fileSystem.openReadOnly(this.zipPath);
        try {
            realBufferedSource = new RealBufferedSource(openReadOnly.source(zipEntry.offset));
            try {
                openReadOnly.close();
                th = null;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            if (openReadOnly != null) {
                try {
                    openReadOnly.close();
                } catch (Throwable th4) {
                    ExceptionsKt__ExceptionsKt.addSuppressed(th3, th4);
                }
            }
            realBufferedSource = null;
            th = th3;
        }
        if (th != null) {
            throw th;
        }
        ZipFilesKt.readOrSkipLocalHeader(realBufferedSource, null);
        int i = zipEntry.compressionMethod;
        long j = zipEntry.size;
        return i == 0 ? new FixedLengthSource(realBufferedSource, j, true) : new FixedLengthSource(new InflaterSource(new FixedLengthSource(realBufferedSource, zipEntry.compressedSize, true), new Inflater(true)), j, false);
    }
}
