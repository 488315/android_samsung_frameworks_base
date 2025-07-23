package okio.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okio.FileHandle;
import okio.FileMetadata;
import okio.FileSystem;
import okio.Okio;
import okio.Path;
import okio.Source;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ResourceFileSystem extends FileSystem {
    public static final Companion Companion = new Companion(null);
    public static final Path ROOT = Path.Companion.get$default(Path.Companion, "/");
    public final ClassLoader classLoader;
    public final Lazy roots$delegate;
    public final FileSystem systemFileSystem;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final boolean access$keepPath(Companion companion, Path path) {
            companion.getClass();
            String name = path.name();
            return !name.regionMatches(true, name.length() - 6, ".class", 0, 6);
        }

        private Companion() {
        }
    }

    public /* synthetic */ ResourceFileSystem(ClassLoader classLoader, boolean z, FileSystem fileSystem, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(classLoader, z, (i & 4) != 0 ? FileSystem.SYSTEM : fileSystem);
    }

    @Override // okio.FileSystem
    public final void atomicMove(Path path, Path path2) {
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public final void createDirectory(Path path) {
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public final void delete(Path path) {
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public final FileMetadata metadataOrNull(Path path) {
        if (!Companion.access$keepPath(Companion, path)) {
            return null;
        }
        Path path2 = ROOT;
        path2.getClass();
        String utf8 = Path.commonResolve(path2, path, true).relativeTo(path2).bytes.utf8();
        for (Pair pair : (List) this.roots$delegate.getValue()) {
            FileMetadata metadataOrNull = ((FileSystem) pair.component1()).metadataOrNull(((Path) pair.component2()).resolve(utf8));
            if (metadataOrNull != null) {
                return metadataOrNull;
            }
        }
        return null;
    }

    @Override // okio.FileSystem
    public final FileHandle openReadOnly(Path path) {
        if (!Companion.access$keepPath(Companion, path)) {
            throw new FileNotFoundException("file not found: " + path);
        }
        Path path2 = ROOT;
        path2.getClass();
        String utf8 = Path.commonResolve(path2, path, true).relativeTo(path2).bytes.utf8();
        Iterator it = ((List) this.roots$delegate.getValue()).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            try {
                return ((FileSystem) pair.component1()).openReadOnly(((Path) pair.component2()).resolve(utf8));
            } catch (FileNotFoundException unused) {
            }
        }
        throw new FileNotFoundException("file not found: " + path);
    }

    @Override // okio.FileSystem
    public final FileHandle openReadWrite(Path path) {
        throw new IOException("resources are not writable");
    }

    @Override // okio.FileSystem
    public final Source source(Path path) {
        if (!Companion.access$keepPath(Companion, path)) {
            throw new FileNotFoundException("file not found: " + path);
        }
        Path path2 = ROOT;
        path2.getClass();
        URL resource = this.classLoader.getResource(Path.commonResolve(path2, path, false).relativeTo(path2).bytes.utf8());
        if (resource == null) {
            throw new FileNotFoundException("file not found: " + path);
        }
        URLConnection openConnection = resource.openConnection();
        if (openConnection instanceof JarURLConnection) {
            ((JarURLConnection) openConnection).setUseCaches(false);
        }
        return Okio.source(openConnection.getInputStream());
    }

    public ResourceFileSystem(ClassLoader classLoader, boolean z, FileSystem fileSystem) {
        this.classLoader = classLoader;
        this.systemFileSystem = fileSystem;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: okio.internal.ResourceFileSystem$$ExternalSyntheticLambda0
            /* JADX WARN: Code restructure failed: missing block: B:101:0x01a4, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:102:0x01a5, code lost:
            
                r1 = r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:104:0x01d9, code lost:
            
                r12.close();
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:105:0x01e3, code lost:
            
                r0 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:107:0x01df, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:108:0x01e0, code lost:
            
                kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r1, r0);
             */
            /* JADX WARN: Code restructure failed: missing block: B:111:0x01ac, code lost:
            
                throw new java.io.IOException("unsupported zip: spanned");
             */
            /* JADX WARN: Code restructure failed: missing block: B:116:0x01d6, code lost:
            
                throw new java.io.IOException("bad zip: expected " + okio.internal.ZipFilesKt.getHex(101075792) + " but was " + okio.internal.ZipFilesKt.getHex(r0));
             */
            /* JADX WARN: Code restructure failed: missing block: B:117:0x01d7, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:118:0x01ad, code lost:
            
                r19 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:120:0x01f5, code lost:
            
                throw new java.io.IOException("unsupported zip: spanned");
             */
            /* JADX WARN: Code restructure failed: missing block: B:123:0x01f6, code lost:
            
                r19 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:124:0x01ee, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:125:0x01eb, code lost:
            
                r19 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:126:0x0216, code lost:
            
                r2 = new java.util.ArrayList();
                r9 = new okio.RealBufferedSource(r11.source(r1.centralDirectoryOffset));
             */
            /* JADX WARN: Code restructure failed: missing block: B:128:0x0226, code lost:
            
                r12 = r1.entryCount;
                r14 = r16;
             */
            /* JADX WARN: Code restructure failed: missing block: B:131:0x022e, code lost:
            
                r0 = okio.internal.ZipFilesKt.readCentralDirectoryZipEntry(r9);
             */
            /* JADX WARN: Code restructure failed: missing block: B:132:0x0232, code lost:
            
                r20 = r6;
                r19 = r7;
             */
            /* JADX WARN: Code restructure failed: missing block: B:135:0x023e, code lost:
            
                if (r0.offset < r1.centralDirectoryOffset) goto L101;
             */
            /* JADX WARN: Code restructure failed: missing block: B:137:0x0248, code lost:
            
                if (okio.internal.ResourceFileSystem.Companion.access$keepPath(okio.internal.ResourceFileSystem.Companion, r0.canonicalPath) != false) goto L103;
             */
            /* JADX WARN: Code restructure failed: missing block: B:138:0x024a, code lost:
            
                r2.add(r0);
             */
            /* JADX WARN: Code restructure failed: missing block: B:140:0x0251, code lost:
            
                r14 = r14 + 1;
                r7 = r19;
                r6 = r20;
             */
            /* JADX WARN: Code restructure failed: missing block: B:144:0x0260, code lost:
            
                throw new java.io.IOException("bad zip: local file header offset >= central directory offset");
             */
            /* JADX WARN: Code restructure failed: missing block: B:147:0x024e, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:148:0x024f, code lost:
            
                r1 = r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:150:0x0274, code lost:
            
                r9.close();
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:151:0x027e, code lost:
            
                r0 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:152:0x027f, code lost:
            
                if (r0 != null) goto L193;
             */
            /* JADX WARN: Code restructure failed: missing block: B:153:0x0281, code lost:
            
                r1 = new okio.ZipFileSystem(r18, r10, okio.internal.ZipFilesKt.buildIndex(r2), r8);
             */
            /* JADX WARN: Code restructure failed: missing block: B:155:0x028c, code lost:
            
                r11.close();
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:156:0x0291, code lost:
            
                r2 = new kotlin.Pair(r1, okio.internal.ResourceFileSystem.ROOT);
             */
            /* JADX WARN: Code restructure failed: missing block: B:160:0x02a6, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:162:0x027a, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:163:0x027b, code lost:
            
                kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r1, r0);
             */
            /* JADX WARN: Code restructure failed: missing block: B:165:0x0267, code lost:
            
                r20 = r6;
                r19 = r7;
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:168:0x0270, code lost:
            
                r0 = null;
             */
            /* JADX WARN: Code restructure failed: missing block: B:170:0x0272, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:172:0x0261, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:173:0x0262, code lost:
            
                r20 = r6;
                r19 = r7;
             */
            /* JADX WARN: Code restructure failed: missing block: B:176:0x02ae, code lost:
            
                throw new java.io.IOException("unsupported zip: spanned");
             */
            /* JADX WARN: Code restructure failed: missing block: B:44:0x00db, code lost:
            
                r0 = r14.readShortLe() & 65535;
                r9 = r14.readShortLe() & 65535;
                r8 = r14.readShortLe() & 65535;
             */
            /* JADX WARN: Code restructure failed: missing block: B:45:0x00f6, code lost:
            
                r25 = r2;
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x00ff, code lost:
            
                if (r8 != (r14.readShortLe() & 65535)) goto L201;
             */
            /* JADX WARN: Code restructure failed: missing block: B:47:0x0101, code lost:
            
                if (r0 != 0) goto L187;
             */
            /* JADX WARN: Code restructure failed: missing block: B:48:0x0103, code lost:
            
                if (r9 != 0) goto L188;
             */
            /* JADX WARN: Code restructure failed: missing block: B:50:0x0107, code lost:
            
                r14.skip(4);
                r1 = new okio.internal.EocdRecord(r8, r14.readIntLe() & 4294967295L, r14.readShortLe() & 65535);
                r8 = r14.readUtf8(r1.commentByteCount);
             */
            /* JADX WARN: Code restructure failed: missing block: B:51:0x012c, code lost:
            
                r14.close();
                r12 = r12 - 20;
             */
            /* JADX WARN: Code restructure failed: missing block: B:52:0x0135, code lost:
            
                if (r12 <= r16) goto L93;
             */
            /* JADX WARN: Code restructure failed: missing block: B:53:0x0137, code lost:
            
                r9 = new okio.RealBufferedSource(r11.source(r12));
             */
            /* JADX WARN: Code restructure failed: missing block: B:56:0x0147, code lost:
            
                if (r9.readIntLe() != 117853008) goto L77;
             */
            /* JADX WARN: Code restructure failed: missing block: B:57:0x0149, code lost:
            
                r0 = r9.readIntLe();
                r12 = r9.readLongLe();
             */
            /* JADX WARN: Code restructure failed: missing block: B:58:0x0156, code lost:
            
                if (r9.readIntLe() != 1) goto L75;
             */
            /* JADX WARN: Code restructure failed: missing block: B:59:0x0158, code lost:
            
                if (r0 != 0) goto L75;
             */
            /* JADX WARN: Code restructure failed: missing block: B:60:0x015a, code lost:
            
                r12 = new okio.RealBufferedSource(r11.source(r12));
             */
            /* JADX WARN: Code restructure failed: missing block: B:62:0x0163, code lost:
            
                r0 = r12.readIntLe();
             */
            /* JADX WARN: Code restructure failed: missing block: B:63:0x016a, code lost:
            
                if (r0 != 101075792) goto L192;
             */
            /* JADX WARN: Code restructure failed: missing block: B:64:0x016c, code lost:
            
                r12.skip(12);
                r0 = r12.readIntLe();
                r13 = r12.readIntLe();
                r20 = r12.readLongLe();
             */
            /* JADX WARN: Code restructure failed: missing block: B:65:0x0183, code lost:
            
                if (r20 != r12.readLongLe()) goto L194;
             */
            /* JADX WARN: Code restructure failed: missing block: B:66:0x0185, code lost:
            
                if (r0 != 0) goto L195;
             */
            /* JADX WARN: Code restructure failed: missing block: B:67:0x0187, code lost:
            
                if (r13 != 0) goto L196;
             */
            /* JADX WARN: Code restructure failed: missing block: B:68:0x0189, code lost:
            
                r12.skip(8);
                r19 = new okio.internal.EocdRecord(r20, r12.readLongLe(), r1.commentByteCount);
             */
            /* JADX WARN: Code restructure failed: missing block: B:70:0x019b, code lost:
            
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:73:0x01a0, code lost:
            
                r0 = null;
             */
            /* JADX WARN: Code restructure failed: missing block: B:74:0x01e4, code lost:
            
                if (r0 == null) goto L68;
             */
            /* JADX WARN: Code restructure failed: missing block: B:77:0x01f8, code lost:
            
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:80:0x01fd, code lost:
            
                r0 = null;
             */
            /* JADX WARN: Code restructure failed: missing block: B:81:0x020c, code lost:
            
                if (r0 != null) goto L189;
             */
            /* JADX WARN: Code restructure failed: missing block: B:82:0x020e, code lost:
            
                r1 = r19;
             */
            /* JADX WARN: Code restructure failed: missing block: B:84:0x0211, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:86:0x01ff, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:87:0x01e8, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:88:0x01e9, code lost:
            
                r1 = r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:90:0x0201, code lost:
            
                r9.close();
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:91:0x020b, code lost:
            
                r0 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:93:0x0207, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:94:0x0208, code lost:
            
                kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r1, r0);
             */
            /* JADX WARN: Code restructure failed: missing block: B:95:0x01e7, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:99:0x01a2, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Finally extract failed */
            /* JADX WARN: Removed duplicated region for block: B:153:0x0281 A[Catch: all -> 0x0212, TRY_LEAVE, TryCatch #10 {all -> 0x0212, blocks: (B:28:0x00ae, B:30:0x00bc, B:31:0x00c7, B:51:0x012c, B:53:0x0137, B:84:0x0211, B:94:0x0208, B:126:0x0216, B:153:0x0281, B:160:0x02a6, B:163:0x027b, B:36:0x02b7, B:40:0x02ce, B:41:0x02d5, B:181:0x02d6, B:182:0x02d9, B:184:0x02da, B:185:0x02ef, B:90:0x0201, B:33:0x00d2, B:44:0x00db, B:50:0x0107, B:175:0x02a9, B:176:0x02ae, B:150:0x0274), top: B:27:0x00ae, inners: #3, #4, #13 }] */
            /* JADX WARN: Removed duplicated region for block: B:159:0x02a6 A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:20:0x029a  */
            /* JADX WARN: Removed duplicated region for block: B:23:0x029d A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:82:0x020e  */
            /* JADX WARN: Removed duplicated region for block: B:83:0x0211 A[SYNTHETIC] */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke() {
                /*
                    Method dump skipped, instructions count: 770
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: okio.internal.ResourceFileSystem$$ExternalSyntheticLambda0.invoke():java.lang.Object");
            }
        });
        this.roots$delegate = lazy;
        if (z) {
            ((List) lazy.getValue()).size();
        }
    }
}
