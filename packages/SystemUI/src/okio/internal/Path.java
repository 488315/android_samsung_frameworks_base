package okio.internal;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import java.io.EOFException;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;

/* renamed from: okio.internal.-Path, reason: invalid class name */
/* loaded from: classes4.dex */
public abstract class Path {
    public static final ByteString ANY_SLASH;
    public static final ByteString BACKSLASH;
    public static final ByteString DOT;
    public static final ByteString DOT_DOT;
    public static final ByteString SLASH;

    static {
        ByteString.Companion.getClass();
        SLASH = ByteString.Companion.encodeUtf8("/");
        BACKSLASH = ByteString.Companion.encodeUtf8("\\");
        ANY_SLASH = ByteString.Companion.encodeUtf8("/\\");
        DOT = ByteString.Companion.encodeUtf8(".");
        DOT_DOT = ByteString.Companion.encodeUtf8("..");
    }

    public static final int access$rootLength(okio.Path path) {
        if (path.bytes.getSize$external__okio__android_common__okio_lib() != 0) {
            if (path.bytes.internalGet$external__okio__android_common__okio_lib(0) != 47) {
                if (path.bytes.internalGet$external__okio__android_common__okio_lib(0) == 92) {
                    if (path.bytes.getSize$external__okio__android_common__okio_lib() > 2 && path.bytes.internalGet$external__okio__android_common__okio_lib(1) == 92) {
                        int iIndexOf = path.bytes.indexOf(BACKSLASH, 2);
                        return iIndexOf == -1 ? path.bytes.getSize$external__okio__android_common__okio_lib() : iIndexOf;
                    }
                } else if (path.bytes.getSize$external__okio__android_common__okio_lib() > 2 && path.bytes.internalGet$external__okio__android_common__okio_lib(1) == 58 && path.bytes.internalGet$external__okio__android_common__okio_lib(2) == 92) {
                    char cInternalGet$external__okio__android_common__okio_lib = (char) path.bytes.internalGet$external__okio__android_common__okio_lib(0);
                    if ('a' <= cInternalGet$external__okio__android_common__okio_lib && cInternalGet$external__okio__android_common__okio_lib < '{') {
                        return 3;
                    }
                    if ('A' <= cInternalGet$external__okio__android_common__okio_lib && cInternalGet$external__okio__android_common__okio_lib < '[') {
                        return 3;
                    }
                }
            }
            return 1;
        }
        return -1;
    }

    public static final okio.Path commonResolve(okio.Path path, okio.Path path2, boolean z) {
        path2.getClass();
        if (access$rootLength(path2) != -1 || path2.volumeLetter() != null) {
            return path2;
        }
        ByteString slash = getSlash(path);
        if (slash == null && (slash = getSlash(path2)) == null) {
            slash = toSlash(okio.Path.DIRECTORY_SEPARATOR);
        }
        Buffer buffer = new Buffer();
        buffer.write(path.bytes);
        if (buffer.size > 0) {
            buffer.write(slash);
        }
        buffer.write(path2.bytes);
        return toPath(buffer, z);
    }

    public static final ByteString getSlash(okio.Path path) {
        ByteString byteString = path.bytes;
        ByteString byteString2 = SLASH;
        if (ByteString.indexOf$default(byteString, byteString2) != -1) {
            return byteString2;
        }
        ByteString byteString3 = path.bytes;
        ByteString byteString4 = BACKSLASH;
        if (ByteString.indexOf$default(byteString3, byteString4) != -1) {
            return byteString4;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x012b A[EDGE_INSN: B:103:0x012b->B:85:0x012b BREAK  A[LOOP:1: B:54:0x00ba->B:117:0x00ba], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0148  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final okio.Path toPath(Buffer buffer, boolean z) throws EOFException {
        ByteString byteString;
        long j;
        char c;
        boolean zExhausted;
        ByteString byteString2;
        int size;
        int i;
        ByteString byteString3;
        Buffer buffer2 = new Buffer();
        ByteString slash = null;
        int i2 = 0;
        while (true) {
            if (!buffer.rangeEquals(SLASH)) {
                byteString = BACKSLASH;
                if (!buffer.rangeEquals(byteString)) {
                    break;
                }
            }
            byte b = buffer.readByte();
            if (slash == null) {
                slash = toSlash(b);
            }
            i2++;
        }
        boolean z2 = i2 >= 2 && Intrinsics.areEqual(slash, byteString);
        ByteString byteString4 = ANY_SLASH;
        if (z2) {
            slash.getClass();
            slash.write$external__okio__android_common__okio_lib(buffer2, slash.getSize$external__okio__android_common__okio_lib());
            slash.write$external__okio__android_common__okio_lib(buffer2, slash.getSize$external__okio__android_common__okio_lib());
        } else {
            if (i2 <= 0) {
                long jIndexOfElement = buffer.indexOfElement(byteString4, 0L);
                if (slash == null) {
                    slash = jIndexOfElement == -1 ? toSlash(okio.Path.DIRECTORY_SEPARATOR) : toSlash(buffer.getByte(jIndexOfElement));
                }
                if (Intrinsics.areEqual(slash, byteString) && buffer.size >= 2) {
                    j = -1;
                    if (buffer.getByte(1L) == 58 && (('a' <= (c = (char) buffer.getByte(0L)) && c < '{') || ('A' <= c && c < '['))) {
                        if (jIndexOfElement == 2) {
                            buffer2.write(buffer, 3L);
                        } else {
                            buffer2.write(buffer, 2L);
                        }
                    }
                } else {
                    j = -1;
                }
                Unit unit = Unit.INSTANCE;
                boolean z3 = buffer2.size <= 0;
                ArrayList arrayList = new ArrayList();
                while (true) {
                    zExhausted = buffer.exhausted();
                    byteString2 = DOT;
                    if (!zExhausted) {
                        break;
                    }
                    long jIndexOfElement2 = buffer.indexOfElement(byteString4, 0L);
                    if (jIndexOfElement2 == j) {
                        byteString3 = buffer.readByteString(buffer.size);
                    } else {
                        byteString3 = buffer.readByteString(jIndexOfElement2);
                        buffer.readByte();
                    }
                    ByteString byteString5 = DOT_DOT;
                    if (Intrinsics.areEqual(byteString3, byteString5)) {
                        if (!z3 || !arrayList.isEmpty()) {
                            if (!z || (!z3 && (arrayList.isEmpty() || Intrinsics.areEqual(CollectionsKt___CollectionsKt.last(arrayList), byteString5)))) {
                                arrayList.add(byteString3);
                            } else if (!z2 || arrayList.size() != 1) {
                                if (!arrayList.isEmpty()) {
                                    arrayList.remove(arrayList.size() - 1);
                                }
                            }
                        }
                    } else if (!Intrinsics.areEqual(byteString3, byteString2) && !Intrinsics.areEqual(byteString3, ByteString.EMPTY)) {
                        arrayList.add(byteString3);
                    }
                }
                size = arrayList.size();
                for (i = 0; i < size; i++) {
                    if (i > 0) {
                        buffer2.write(slash);
                    }
                    buffer2.write((ByteString) arrayList.get(i));
                }
                if (buffer2.size == 0) {
                    buffer2.write(byteString2);
                }
                return new okio.Path(buffer2.readByteString(buffer2.size));
            }
            slash.getClass();
            slash.write$external__okio__android_common__okio_lib(buffer2, slash.getSize$external__okio__android_common__okio_lib());
        }
        j = -1;
        if (buffer2.size <= 0) {
        }
        ArrayList arrayList2 = new ArrayList();
        while (true) {
            zExhausted = buffer.exhausted();
            byteString2 = DOT;
            if (!zExhausted) {
            }
        }
        size = arrayList2.size();
        while (i < size) {
        }
        if (buffer2.size == 0) {
        }
        return new okio.Path(buffer2.readByteString(buffer2.size));
    }

    public static final ByteString toSlash(String str) {
        if (Intrinsics.areEqual(str, "/")) {
            return SLASH;
        }
        if (Intrinsics.areEqual(str, "\\")) {
            return BACKSLASH;
        }
        throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("not a directory separator: ", str));
    }

    public static final ByteString toSlash(byte b) {
        if (b == 47) {
            return SLASH;
        }
        if (b == 92) {
            return BACKSLASH;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(b, "not a directory separator: "));
    }
}
