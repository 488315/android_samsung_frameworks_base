package okio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.zip.Inflater;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okio.Path;
import okio.internal.FixedLengthSource;
import okio.internal.ZipEntry;
import okio.internal.ZipFilesKt;

/* loaded from: classes4.dex */
public final class ZipFileSystem extends FileSystem {
    public static final Path ROOT;
    public final Map entries;
    public final FileSystem fileSystem;
    public final Path zipPath;

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
    public final void atomicMove(Path path, Path path2) throws IOException {
        throw new IOException("zip file systems are read-only");
    }

    @Override // okio.FileSystem
    public final void createDirectory(Path path) throws IOException {
        throw new IOException("zip file systems are read-only");
    }

    @Override // okio.FileSystem
    public final void delete(Path path) throws IOException {
        throw new IOException("zip file systems are read-only");
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x011c  */
    @Override // okio.FileSystem
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FileMetadata metadataOrNull(Path path) throws Throwable {
        Long lValueOf;
        long j;
        Long l;
        Long lValueOf2;
        Long l2;
        Long l3;
        Long lValueOf3;
        Throwable th;
        Throwable th2;
        Path path2 = ROOT;
        path2.getClass();
        ZipEntry orSkipLocalHeader = (ZipEntry) this.entries.get(okio.internal.Path.commonResolve(path2, path, true));
        if (orSkipLocalHeader == null) {
            return null;
        }
        long j2 = orSkipLocalHeader.offset;
        if (j2 != -1) {
            FileHandle fileHandleOpenReadOnly = this.fileSystem.openReadOnly(this.zipPath);
            try {
                RealBufferedSource realBufferedSource = new RealBufferedSource(fileHandleOpenReadOnly.source(j2));
                try {
                    orSkipLocalHeader = ZipFilesKt.readOrSkipLocalHeader(realBufferedSource, orSkipLocalHeader);
                    orSkipLocalHeader.getClass();
                    try {
                        realBufferedSource.close();
                        th2 = null;
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                } catch (Throwable th4) {
                    try {
                        realBufferedSource.close();
                    } catch (Throwable th5) {
                        ExceptionsKt__ExceptionsKt.addSuppressed(th4, th5);
                    }
                    th2 = th4;
                    orSkipLocalHeader = null;
                }
            } catch (Throwable th6) {
                if (fileHandleOpenReadOnly != null) {
                    try {
                        fileHandleOpenReadOnly.close();
                    } catch (Throwable th7) {
                        ExceptionsKt__ExceptionsKt.addSuppressed(th6, th7);
                    }
                }
                th = th6;
                orSkipLocalHeader = null;
            }
            if (th2 != null) {
                throw th2;
            }
            try {
                fileHandleOpenReadOnly.close();
                th = null;
            } catch (Throwable th8) {
                th = th8;
            }
            if (th != null) {
                throw th;
            }
        }
        boolean z = orSkipLocalHeader.isDirectory;
        boolean z2 = !z;
        Long lValueOf4 = z ? null : Long.valueOf(orSkipLocalHeader.size);
        Long l4 = orSkipLocalHeader.ntfsCreatedAtFiletime;
        if (l4 != null) {
            lValueOf = Long.valueOf((l4.longValue() / 10000) - 11644473600000L);
        } else {
            lValueOf = orSkipLocalHeader.extendedCreatedAtSeconds != null ? Long.valueOf(r2.intValue() * 1000) : null;
        }
        Long l5 = orSkipLocalHeader.ntfsLastModifiedAtFiletime;
        if (l5 != null) {
            j = 11644473600000L;
            lValueOf2 = Long.valueOf((l5.longValue() / 10000) - 11644473600000L);
        } else {
            j = 11644473600000L;
            if (orSkipLocalHeader.extendedLastModifiedAtSeconds != null) {
                lValueOf2 = Long.valueOf(r3.intValue() * 1000);
            } else {
                int i = orSkipLocalHeader.dosLastModifiedAtTime;
                if (i == -1 || i == -1) {
                    l = null;
                    l2 = orSkipLocalHeader.ntfsLastAccessedAtFiletime;
                    if (l2 == null) {
                        lValueOf3 = Long.valueOf((l2.longValue() / 10000) - j);
                    } else {
                        if (orSkipLocalHeader.extendedLastAccessedAtSeconds == null) {
                            l3 = null;
                            return new FileMetadata(z2, z, null, lValueOf4, lValueOf, l, l3, null, 128, null);
                        }
                        lValueOf3 = Long.valueOf(r0.intValue() * 1000);
                    }
                    l3 = lValueOf3;
                    return new FileMetadata(z2, z, null, lValueOf4, lValueOf, l, l3, null, 128, null);
                }
                int i2 = orSkipLocalHeader.dosLastModifiedAtDate;
                int i3 = (i2 >> 5) & 15;
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.set(14, 0);
                gregorianCalendar.set(((i2 >> 9) & 127) + 1980, i3 - 1, i2 & 31, (i >> 11) & 31, (i >> 5) & 63, (i & 31) << 1);
                lValueOf2 = Long.valueOf(gregorianCalendar.getTime().getTime());
            }
        }
        l = lValueOf2;
        l2 = orSkipLocalHeader.ntfsLastAccessedAtFiletime;
        if (l2 == null) {
        }
        l3 = lValueOf3;
        return new FileMetadata(z2, z, null, lValueOf4, lValueOf, l, l3, null, 128, null);
    }

    @Override // okio.FileSystem
    public final FileHandle openReadOnly(Path path) {
        throw new UnsupportedOperationException("not implemented yet!");
    }

    @Override // okio.FileSystem
    public final FileHandle openReadWrite(Path path) throws IOException {
        throw new IOException("zip entries are not writable");
    }

    @Override // okio.FileSystem
    public final Source source(Path path) throws Throwable {
        RealBufferedSource realBufferedSource;
        Throwable th;
        Path path2 = ROOT;
        path2.getClass();
        ZipEntry zipEntry = (ZipEntry) this.entries.get(okio.internal.Path.commonResolve(path2, path, true));
        if (zipEntry == null) {
            throw new FileNotFoundException("no such file: " + path);
        }
        FileHandle fileHandleOpenReadOnly = this.fileSystem.openReadOnly(this.zipPath);
        try {
            realBufferedSource = new RealBufferedSource(fileHandleOpenReadOnly.source(zipEntry.offset));
            try {
                fileHandleOpenReadOnly.close();
                th = null;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            if (fileHandleOpenReadOnly != null) {
                try {
                    fileHandleOpenReadOnly.close();
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
