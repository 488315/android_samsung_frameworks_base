package okio.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import okio.FileHandle;
import okio.FileMetadata;
import okio.FileSystem;
import okio.Okio;
import okio.Path;
import okio.RealBufferedSource;
import okio.Source;

/* loaded from: classes4.dex */
public final class ResourceFileSystem extends FileSystem {
    public static final Companion Companion = new Companion(null);
    public static final Path ROOT = Path.Companion.get$default(Path.Companion, "/");
    public final ClassLoader classLoader;
    public final Lazy roots$delegate;
    public final FileSystem systemFileSystem;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final boolean access$keepPath(Companion companion, Path path) {
            companion.getClass();
            String strName = path.name();
            return !strName.regionMatches(true, strName.length() - 6, ".class", 0, 6);
        }

        private Companion() {
        }
    }

    public /* synthetic */ ResourceFileSystem(ClassLoader classLoader, boolean z, FileSystem fileSystem, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(classLoader, z, (i & 4) != 0 ? FileSystem.SYSTEM : fileSystem);
    }

    @Override // okio.FileSystem
    public final void atomicMove(Path path, Path path2) throws IOException {
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public final void createDirectory(Path path) throws IOException {
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public final void delete(Path path) throws IOException {
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public final FileMetadata metadataOrNull(Path path) {
        if (!Companion.access$keepPath(Companion, path)) {
            return null;
        }
        Path path2 = ROOT;
        path2.getClass();
        String strUtf8 = Path.commonResolve(path2, path, true).relativeTo(path2).bytes.utf8();
        for (Pair pair : (List) this.roots$delegate.getValue()) {
            FileMetadata fileMetadataMetadataOrNull = ((FileSystem) pair.component1()).metadataOrNull(((Path) pair.component2()).resolve(strUtf8));
            if (fileMetadataMetadataOrNull != null) {
                return fileMetadataMetadataOrNull;
            }
        }
        return null;
    }

    @Override // okio.FileSystem
    public final FileHandle openReadOnly(Path path) throws FileNotFoundException {
        if (!Companion.access$keepPath(Companion, path)) {
            throw new FileNotFoundException("file not found: " + path);
        }
        Path path2 = ROOT;
        path2.getClass();
        String strUtf8 = Path.commonResolve(path2, path, true).relativeTo(path2).bytes.utf8();
        Iterator it = ((List) this.roots$delegate.getValue()).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            try {
                return ((FileSystem) pair.component1()).openReadOnly(((Path) pair.component2()).resolve(strUtf8));
            } catch (FileNotFoundException unused) {
            }
        }
        throw new FileNotFoundException("file not found: " + path);
    }

    @Override // okio.FileSystem
    public final FileHandle openReadWrite(Path path) throws IOException {
        throw new IOException("resources are not writable");
    }

    @Override // okio.FileSystem
    public final Source source(Path path) throws IOException {
        if (!Companion.access$keepPath(Companion, path)) {
            throw new FileNotFoundException("file not found: " + path);
        }
        Path path2 = ROOT;
        path2.getClass();
        URL resource = this.classLoader.getResource(Path.commonResolve(path2, path, false).relativeTo(path2).bytes.utf8());
        if (resource == null) {
            throw new FileNotFoundException("file not found: " + path);
        }
        URLConnection uRLConnectionOpenConnection = resource.openConnection();
        if (uRLConnectionOpenConnection instanceof JarURLConnection) {
            ((JarURLConnection) uRLConnectionOpenConnection).setUseCaches(false);
        }
        return Okio.source(uRLConnectionOpenConnection.getInputStream());
    }

    public ResourceFileSystem(ClassLoader classLoader, boolean z, FileSystem fileSystem) {
        this.classLoader = classLoader;
        this.systemFileSystem = fileSystem;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: okio.internal.ResourceFileSystem$$ExternalSyntheticLambda0
            /* JADX WARN: Code restructure failed: missing block: B:100:0x023e, code lost:
            
                if (r0.offset < r1.centralDirectoryOffset) goto L101;
             */
            /* JADX WARN: Code restructure failed: missing block: B:102:0x0248, code lost:
            
                if (okio.internal.ResourceFileSystem.Companion.access$keepPath(okio.internal.ResourceFileSystem.Companion, r0.canonicalPath) != false) goto L103;
             */
            /* JADX WARN: Code restructure failed: missing block: B:103:0x024a, code lost:
            
                r2.add(r0);
             */
            /* JADX WARN: Code restructure failed: missing block: B:104:0x024e, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:105:0x024f, code lost:
            
                r1 = r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:106:0x0251, code lost:
            
                r14 = r14 + 1;
                r7 = r19;
                r6 = r20;
             */
            /* JADX WARN: Code restructure failed: missing block: B:108:0x0260, code lost:
            
                throw new java.io.IOException("bad zip: local file header offset >= central directory offset");
             */
            /* JADX WARN: Code restructure failed: missing block: B:109:0x0261, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:110:0x0262, code lost:
            
                r20 = r6;
                r19 = r7;
             */
            /* JADX WARN: Code restructure failed: missing block: B:111:0x0267, code lost:
            
                r20 = r6;
                r19 = r7;
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:113:0x0270, code lost:
            
                r0 = null;
             */
            /* JADX WARN: Code restructure failed: missing block: B:114:0x0272, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:116:0x0274, code lost:
            
                r9.close();
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:118:0x027a, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:119:0x027b, code lost:
            
                kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r1, r0);
             */
            /* JADX WARN: Code restructure failed: missing block: B:120:0x027e, code lost:
            
                r0 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:121:0x027f, code lost:
            
                if (r0 != null) goto L200;
             */
            /* JADX WARN: Code restructure failed: missing block: B:122:0x0281, code lost:
            
                r1 = new okio.ZipFileSystem(r18, r10, okio.internal.ZipFilesKt.buildIndex(r2), r8);
             */
            /* JADX WARN: Code restructure failed: missing block: B:123:0x028c, code lost:
            
                r11.close();
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:124:0x0291, code lost:
            
                r2 = new kotlin.Pair(r1, okio.internal.ResourceFileSystem.ROOT);
             */
            /* JADX WARN: Code restructure failed: missing block: B:128:0x02a6, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:132:0x02ae, code lost:
            
                throw new java.io.IOException("unsupported zip: spanned");
             */
            /* JADX WARN: Code restructure failed: missing block: B:25:0x00db, code lost:
            
                r0 = r14.readShortLe() & 65535;
                r9 = r14.readShortLe() & 65535;
                r8 = r14.readShortLe() & 65535;
             */
            /* JADX WARN: Code restructure failed: missing block: B:26:0x00f6, code lost:
            
                r25 = r2;
             */
            /* JADX WARN: Code restructure failed: missing block: B:27:0x00ff, code lost:
            
                if (r8 != (r14.readShortLe() & 65535)) goto L189;
             */
            /* JADX WARN: Code restructure failed: missing block: B:28:0x0101, code lost:
            
                if (r0 != 0) goto L190;
             */
            /* JADX WARN: Code restructure failed: missing block: B:29:0x0103, code lost:
            
                if (r9 != 0) goto L191;
             */
            /* JADX WARN: Code restructure failed: missing block: B:31:0x0107, code lost:
            
                r14.skip(4);
                r1 = new okio.internal.EocdRecord(r8, r14.readIntLe() & 4294967295L, r14.readShortLe() & 65535);
                r8 = r14.readUtf8(r1.commentByteCount);
             */
            /* JADX WARN: Code restructure failed: missing block: B:32:0x012c, code lost:
            
                r14.close();
                r12 = r12 - 20;
             */
            /* JADX WARN: Code restructure failed: missing block: B:33:0x0135, code lost:
            
                if (r12 <= r16) goto L93;
             */
            /* JADX WARN: Code restructure failed: missing block: B:34:0x0137, code lost:
            
                r9 = new okio.RealBufferedSource(r11.source(r12));
             */
            /* JADX WARN: Code restructure failed: missing block: B:36:0x0147, code lost:
            
                if (r9.readIntLe() != 117853008) goto L77;
             */
            /* JADX WARN: Code restructure failed: missing block: B:37:0x0149, code lost:
            
                r0 = r9.readIntLe();
                r12 = r9.readLongLe();
             */
            /* JADX WARN: Code restructure failed: missing block: B:38:0x0156, code lost:
            
                if (r9.readIntLe() != 1) goto L75;
             */
            /* JADX WARN: Code restructure failed: missing block: B:39:0x0158, code lost:
            
                if (r0 != 0) goto L75;
             */
            /* JADX WARN: Code restructure failed: missing block: B:40:0x015a, code lost:
            
                r12 = new okio.RealBufferedSource(r11.source(r12));
             */
            /* JADX WARN: Code restructure failed: missing block: B:41:0x0163, code lost:
            
                r0 = r12.readIntLe();
             */
            /* JADX WARN: Code restructure failed: missing block: B:42:0x016a, code lost:
            
                if (r0 != 101075792) goto L192;
             */
            /* JADX WARN: Code restructure failed: missing block: B:43:0x016c, code lost:
            
                r12.skip(12);
                r0 = r12.readIntLe();
                r13 = r12.readIntLe();
                r20 = r12.readLongLe();
             */
            /* JADX WARN: Code restructure failed: missing block: B:44:0x0183, code lost:
            
                if (r20 != r12.readLongLe()) goto L193;
             */
            /* JADX WARN: Code restructure failed: missing block: B:45:0x0185, code lost:
            
                if (r0 != 0) goto L194;
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x0187, code lost:
            
                if (r13 != 0) goto L195;
             */
            /* JADX WARN: Code restructure failed: missing block: B:47:0x0189, code lost:
            
                r12.skip(8);
                r19 = new okio.internal.EocdRecord(r20, r12.readLongLe(), r1.commentByteCount);
             */
            /* JADX WARN: Code restructure failed: missing block: B:48:0x019b, code lost:
            
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:50:0x01a0, code lost:
            
                r0 = null;
             */
            /* JADX WARN: Code restructure failed: missing block: B:51:0x01a2, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:53:0x01a4, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:54:0x01a5, code lost:
            
                r1 = r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:56:0x01ac, code lost:
            
                throw new java.io.IOException("unsupported zip: spanned");
             */
            /* JADX WARN: Code restructure failed: missing block: B:57:0x01ad, code lost:
            
                r19 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:59:0x01d6, code lost:
            
                throw new java.io.IOException("bad zip: expected " + okio.internal.ZipFilesKt.getHex(101075792) + " but was " + okio.internal.ZipFilesKt.getHex(r0));
             */
            /* JADX WARN: Code restructure failed: missing block: B:60:0x01d7, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:62:0x01d9, code lost:
            
                r12.close();
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:64:0x01df, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:65:0x01e0, code lost:
            
                kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r1, r0);
             */
            /* JADX WARN: Code restructure failed: missing block: B:66:0x01e3, code lost:
            
                r0 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:67:0x01e4, code lost:
            
                if (r0 == null) goto L68;
             */
            /* JADX WARN: Code restructure failed: missing block: B:69:0x01e7, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:70:0x01e8, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:71:0x01e9, code lost:
            
                r1 = r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:72:0x01eb, code lost:
            
                r19 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:73:0x01ee, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:76:0x01f5, code lost:
            
                throw new java.io.IOException("unsupported zip: spanned");
             */
            /* JADX WARN: Code restructure failed: missing block: B:77:0x01f6, code lost:
            
                r19 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:78:0x01f8, code lost:
            
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:80:0x01fd, code lost:
            
                r0 = null;
             */
            /* JADX WARN: Code restructure failed: missing block: B:81:0x01ff, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:83:0x0201, code lost:
            
                r9.close();
                r0 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:85:0x0207, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:86:0x0208, code lost:
            
                kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r1, r0);
             */
            /* JADX WARN: Code restructure failed: missing block: B:87:0x020b, code lost:
            
                r0 = r1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:88:0x020c, code lost:
            
                if (r0 != null) goto L198;
             */
            /* JADX WARN: Code restructure failed: missing block: B:89:0x020e, code lost:
            
                r1 = r19;
             */
            /* JADX WARN: Code restructure failed: missing block: B:90:0x0211, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:93:0x0216, code lost:
            
                r2 = new java.util.ArrayList();
                r9 = new okio.RealBufferedSource(r11.source(r1.centralDirectoryOffset));
             */
            /* JADX WARN: Code restructure failed: missing block: B:94:0x0226, code lost:
            
                r12 = r1.entryCount;
                r14 = r16;
             */
            /* JADX WARN: Code restructure failed: missing block: B:97:0x022e, code lost:
            
                r0 = okio.internal.ZipFilesKt.readCentralDirectoryZipEntry(r9);
             */
            /* JADX WARN: Code restructure failed: missing block: B:98:0x0232, code lost:
            
                r20 = r6;
                r19 = r7;
             */
            /* JADX WARN: Finally extract failed */
            /* JADX WARN: Removed duplicated region for block: B:122:0x0281 A[Catch: all -> 0x0212, TRY_LEAVE, TryCatch #10 {all -> 0x0212, blocks: (B:19:0x00ae, B:21:0x00bc, B:22:0x00c7, B:32:0x012c, B:34:0x0137, B:90:0x0211, B:86:0x0208, B:93:0x0216, B:122:0x0281, B:128:0x02a6, B:119:0x027b, B:134:0x02b7, B:137:0x02ce, B:138:0x02d5, B:139:0x02d6, B:140:0x02d9, B:141:0x02da, B:142:0x02ef, B:83:0x0201, B:23:0x00d2, B:25:0x00db, B:31:0x0107, B:131:0x02a9, B:132:0x02ae, B:116:0x0274), top: B:172:0x00ae, inners: #3, #4, #13 }] */
            /* JADX WARN: Removed duplicated region for block: B:126:0x029a  */
            /* JADX WARN: Removed duplicated region for block: B:198:0x0211 A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:200:0x02a6 A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:203:0x029d A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:89:0x020e  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke() {
                int iLastIndexOf$default;
                ResourceFileSystem resourceFileSystem;
                int i;
                int i2;
                Pair pair;
                int i3 = 1;
                ResourceFileSystem resourceFileSystem2 = this.f$0;
                ClassLoader classLoader2 = resourceFileSystem2.classLoader;
                ArrayList list = Collections.list(classLoader2.getResources(""));
                ArrayList arrayList = new ArrayList();
                int size = list.size();
                int i4 = 0;
                int i5 = 0;
                while (i5 < size) {
                    Object obj = list.get(i5);
                    i5++;
                    URL url = (URL) obj;
                    url.getClass();
                    Pair pair2 = !Intrinsics.areEqual(url.getProtocol(), "file") ? null : new Pair(resourceFileSystem2.systemFileSystem, Path.Companion.get$default(Path.Companion, new File(url.toURI())));
                    if (pair2 != null) {
                        arrayList.add(pair2);
                    }
                }
                ArrayList list2 = Collections.list(classLoader2.getResources("META-INF/MANIFEST.MF"));
                ArrayList arrayList2 = new ArrayList();
                int size2 = list2.size();
                while (i4 < size2) {
                    Object obj2 = list2.get(i4);
                    int i6 = i4 + i3;
                    URL url2 = (URL) obj2;
                    url2.getClass();
                    String string = url2.toString();
                    if (string.startsWith("jar:file:") && (iLastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default(string, "!", 6)) != -1) {
                        Path path = Path.Companion.get$default(Path.Companion, new File(URI.create(string.substring(4, iLastIndexOf$default))));
                        FileSystem fileSystem2 = resourceFileSystem2.systemFileSystem;
                        FileHandle fileHandleOpenReadOnly = fileSystem2.openReadOnly(path);
                        try {
                            long size3 = fileHandleOpenReadOnly.size() - 22;
                            long j = 0;
                            if (size3 < 0) {
                                throw new IOException("not a zip: size=" + fileHandleOpenReadOnly.size());
                            }
                            Path path2 = path;
                            long jMax = Math.max(size3 - 65536, 0L);
                            while (true) {
                                long j2 = j;
                                RealBufferedSource realBufferedSource = new RealBufferedSource(fileHandleOpenReadOnly.source(size3));
                                try {
                                    if (realBufferedSource.readIntLe() == 101010256) {
                                        break;
                                    }
                                    ResourceFileSystem resourceFileSystem3 = resourceFileSystem2;
                                    int i7 = i6;
                                    int i8 = size2;
                                    Path path3 = path2;
                                    realBufferedSource.close();
                                    size3--;
                                    if (size3 < jMax) {
                                        throw new IOException("not a zip: end of central directory signature not found");
                                    }
                                    path2 = path3;
                                    j = j2;
                                    size2 = i8;
                                    i6 = i7;
                                    resourceFileSystem2 = resourceFileSystem3;
                                } finally {
                                    realBufferedSource.close();
                                }
                            }
                        } catch (Throwable th) {
                            if (fileHandleOpenReadOnly == null) {
                                throw th;
                            }
                            try {
                                fileHandleOpenReadOnly.close();
                                Unit unit = Unit.INSTANCE;
                                throw th;
                            } catch (Throwable th2) {
                                ExceptionsKt__ExceptionsKt.addSuppressed(th, th2);
                                throw th;
                            }
                        }
                    } else {
                        resourceFileSystem = resourceFileSystem2;
                        i = i6;
                        i2 = size2;
                        pair = null;
                    }
                    if (pair == null) {
                        arrayList2.add(pair);
                    }
                    size2 = i2;
                    i4 = i;
                    resourceFileSystem2 = resourceFileSystem;
                    i3 = 1;
                }
                return CollectionsKt___CollectionsKt.plus((Iterable) arrayList2, (Collection) arrayList);
            }
        });
        this.roots$delegate = lazy;
        if (z) {
            ((List) lazy.getValue()).size();
        }
    }
}
