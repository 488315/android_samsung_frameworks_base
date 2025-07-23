package okio.internal;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$LongRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringsKt;
import okio.Buffer;
import okio.Path;
import okio.RealBufferedSource;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ZipFilesKt {
    public static final Map buildIndex(List list) {
        Path path = Path.Companion.get$default(Path.Companion, "/");
        Map mutableMapOf = MapsKt__MapsKt.mutableMapOf(new Pair(path, new ZipEntry(path, true, null, 0L, 0L, 0L, 0, 0L, 0, 0, null, null, null, null, null, null, 65532, null)));
        for (ZipEntry zipEntry : CollectionsKt___CollectionsKt.sortedWith(list, new Comparator() { // from class: okio.internal.ZipFilesKt$buildIndex$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(((ZipEntry) obj).canonicalPath, ((ZipEntry) obj2).canonicalPath);
            }
        })) {
            if (((ZipEntry) mutableMapOf.put(zipEntry.canonicalPath, zipEntry)) == null) {
                while (true) {
                    Path path2 = zipEntry.canonicalPath;
                    Path parent = path2.parent();
                    if (parent != null) {
                        ZipEntry zipEntry2 = (ZipEntry) ((LinkedHashMap) mutableMapOf).get(parent);
                        if (zipEntry2 != null) {
                            ((ArrayList) zipEntry2.children).add(path2);
                            break;
                        }
                        ZipEntry zipEntry3 = new ZipEntry(parent, true, null, 0L, 0L, 0L, 0, 0L, 0, 0, null, null, null, null, null, null, 65532, null);
                        mutableMapOf.put(parent, zipEntry3);
                        ((ArrayList) zipEntry3.children).add(path2);
                        zipEntry = zipEntry3;
                    }
                }
            }
        }
        return mutableMapOf;
    }

    public static final String getHex(int i) {
        CharsKt__CharJVMKt.checkRadix(16);
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("0x", Integer.toString(i, 16));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ZipEntry readCentralDirectoryZipEntry(final RealBufferedSource realBufferedSource) {
        int readIntLe = realBufferedSource.readIntLe();
        if (readIntLe != 33639248) {
            throw new IOException(AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("bad zip: expected ", getHex(33639248), " but was ", getHex(readIntLe)));
        }
        realBufferedSource.skip(4L);
        short readShortLe = realBufferedSource.readShortLe();
        int i = readShortLe & 65535;
        if ((readShortLe & 1) != 0) {
            throw new IOException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("unsupported zip: general purpose bit flag=", getHex(i)));
        }
        int readShortLe2 = realBufferedSource.readShortLe() & 65535;
        int readShortLe3 = realBufferedSource.readShortLe() & 65535;
        int readShortLe4 = realBufferedSource.readShortLe() & 65535;
        long readIntLe2 = realBufferedSource.readIntLe() & 4294967295L;
        final Ref$LongRef ref$LongRef = new Ref$LongRef();
        ref$LongRef.element = realBufferedSource.readIntLe() & 4294967295L;
        final Ref$LongRef ref$LongRef2 = new Ref$LongRef();
        ref$LongRef2.element = realBufferedSource.readIntLe() & 4294967295L;
        int readShortLe5 = realBufferedSource.readShortLe() & 65535;
        int readShortLe6 = realBufferedSource.readShortLe() & 65535;
        int readShortLe7 = 65535 & realBufferedSource.readShortLe();
        realBufferedSource.skip(8L);
        final Ref$LongRef ref$LongRef3 = new Ref$LongRef();
        ref$LongRef3.element = realBufferedSource.readIntLe() & 4294967295L;
        String readUtf8 = realBufferedSource.readUtf8(readShortLe5);
        if (StringsKt__StringsKt.contains$default(readUtf8, (char) 0)) {
            throw new IOException("bad zip: filename contains 0x00");
        }
        final long j = ref$LongRef2.element == 4294967295L ? 8 : 0L;
        if (ref$LongRef.element == 4294967295L) {
            j += 8;
        }
        if (ref$LongRef3.element == 4294967295L) {
            j += 8;
        }
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        final Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        readExtra(realBufferedSource, readShortLe6, new Function2() { // from class: okio.internal.ZipFilesKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int intValue = ((Integer) obj).intValue();
                long longValue = ((Long) obj2).longValue();
                RealBufferedSource realBufferedSource2 = realBufferedSource;
                if (intValue == 1) {
                    Ref$BooleanRef ref$BooleanRef2 = Ref$BooleanRef.this;
                    if (ref$BooleanRef2.element) {
                        throw new IOException("bad zip: zip64 extra repeated");
                    }
                    ref$BooleanRef2.element = true;
                    if (longValue < j) {
                        throw new IOException("bad zip: zip64 extra too short");
                    }
                    Ref$LongRef ref$LongRef4 = ref$LongRef2;
                    long j2 = ref$LongRef4.element;
                    if (j2 == 4294967295L) {
                        j2 = realBufferedSource2.readLongLe();
                    }
                    ref$LongRef4.element = j2;
                    Ref$LongRef ref$LongRef5 = ref$LongRef;
                    ref$LongRef5.element = ref$LongRef5.element == 4294967295L ? realBufferedSource2.readLongLe() : 0L;
                    Ref$LongRef ref$LongRef6 = ref$LongRef3;
                    ref$LongRef6.element = ref$LongRef6.element == 4294967295L ? realBufferedSource2.readLongLe() : 0L;
                } else if (intValue == 10) {
                    if (longValue < 4) {
                        throw new IOException("bad zip: NTFS extra too short");
                    }
                    realBufferedSource2.skip(4L);
                    ZipFilesKt.readExtra(realBufferedSource2, (int) (longValue - 4), new ZipFilesKt$$ExternalSyntheticLambda0(ref$ObjectRef, realBufferedSource2, ref$ObjectRef2, ref$ObjectRef3));
                }
                return Unit.INSTANCE;
            }
        });
        if (j <= 0 || ref$BooleanRef.element) {
            return new ZipEntry(Path.Companion.get$default(Path.Companion, "/").resolve(readUtf8), readUtf8.endsWith("/"), realBufferedSource.readUtf8(readShortLe7), readIntLe2, ref$LongRef.element, ref$LongRef2.element, readShortLe2, ref$LongRef3.element, readShortLe4, readShortLe3, (Long) ref$ObjectRef.element, (Long) ref$ObjectRef2.element, (Long) ref$ObjectRef3.element, null, null, null, 57344, null);
        }
        throw new IOException("bad zip: zip64 extra required but absent");
    }

    public static final void readExtra(RealBufferedSource realBufferedSource, int i, Function2 function2) {
        long j = i;
        while (j != 0) {
            if (j < 4) {
                throw new IOException("bad zip: truncated header in extra field");
            }
            int readShortLe = realBufferedSource.readShortLe() & 65535;
            long readShortLe2 = realBufferedSource.readShortLe() & 65535;
            long j2 = j - 4;
            if (j2 < readShortLe2) {
                throw new IOException("bad zip: truncated value in extra field");
            }
            realBufferedSource.require(readShortLe2);
            long j3 = realBufferedSource.bufferField.size;
            function2.invoke(Integer.valueOf(readShortLe), Long.valueOf(readShortLe2));
            Buffer buffer = realBufferedSource.bufferField;
            long j4 = (buffer.size + readShortLe2) - j3;
            if (j4 < 0) {
                throw new IOException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(readShortLe, "unsupported zip: too many bytes processed for "));
            }
            if (j4 > 0) {
                buffer.skip(j4);
            }
            j = j2 - readShortLe2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ZipEntry readOrSkipLocalHeader(RealBufferedSource realBufferedSource, ZipEntry zipEntry) {
        int readIntLe = realBufferedSource.readIntLe();
        if (readIntLe != 67324752) {
            throw new IOException(AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("bad zip: expected ", getHex(67324752), " but was ", getHex(readIntLe)));
        }
        realBufferedSource.skip(2L);
        short readShortLe = realBufferedSource.readShortLe();
        int i = readShortLe & 65535;
        if ((readShortLe & 1) != 0) {
            throw new IOException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("unsupported zip: general purpose bit flag=", getHex(i)));
        }
        realBufferedSource.skip(18L);
        int readShortLe2 = realBufferedSource.readShortLe() & 65535;
        realBufferedSource.skip(realBufferedSource.readShortLe() & 65535);
        if (zipEntry == null) {
            realBufferedSource.skip(readShortLe2);
            return null;
        }
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
        readExtra(realBufferedSource, readShortLe2, new ZipFilesKt$$ExternalSyntheticLambda0(realBufferedSource, ref$ObjectRef, ref$ObjectRef2, ref$ObjectRef3));
        return new ZipEntry(zipEntry.canonicalPath, zipEntry.isDirectory, zipEntry.comment, zipEntry.crc, zipEntry.compressedSize, zipEntry.size, zipEntry.compressionMethod, zipEntry.offset, zipEntry.dosLastModifiedAtDate, zipEntry.dosLastModifiedAtTime, zipEntry.ntfsLastModifiedAtFiletime, zipEntry.ntfsLastAccessedAtFiletime, zipEntry.ntfsCreatedAtFiletime, (Integer) ref$ObjectRef.element, (Integer) ref$ObjectRef2.element, (Integer) ref$ObjectRef3.element);
    }
}
