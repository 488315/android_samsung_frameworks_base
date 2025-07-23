package okio.internal;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        int indexOf = path.bytes.indexOf(BACKSLASH, 2);
                        return indexOf == -1 ? path.bytes.getSize$external__okio__android_common__okio_lib() : indexOf;
                    }
                } else if (path.bytes.getSize$external__okio__android_common__okio_lib() > 2 && path.bytes.internalGet$external__okio__android_common__okio_lib(1) == 58 && path.bytes.internalGet$external__okio__android_common__okio_lib(2) == 92) {
                    char internalGet$external__okio__android_common__okio_lib = (char) path.bytes.internalGet$external__okio__android_common__okio_lib(0);
                    if ('a' <= internalGet$external__okio__android_common__okio_lib && internalGet$external__okio__android_common__okio_lib < '{') {
                        return 3;
                    }
                    if ('A' <= internalGet$external__okio__android_common__okio_lib && internalGet$external__okio__android_common__okio_lib < '[') {
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
        if (access$rootLength(path2) != -1) {
            return path2;
        }
        if (path2.volumeLetter() != null) {
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

    /* JADX WARN: Removed duplicated region for block: B:18:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x012b A[EDGE_INSN: B:72:0x012b->B:73:0x012b BREAK  A[LOOP:1: B:20:0x00ba->B:36:0x00ba], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final okio.Path toPath(okio.Buffer r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 356
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.Path.toPath(okio.Buffer, boolean):okio.Path");
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
