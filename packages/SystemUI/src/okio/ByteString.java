package okio;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Charsets;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ByteString implements Serializable, Comparable<ByteString> {
    public static final Companion Companion = new Companion(null);
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private static final long serialVersionUID = 1;
    private final byte[] data;
    public transient int hashCode;
    public transient String utf8;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static ByteString encodeUtf8(String str) {
            ByteString byteString = new ByteString(str.getBytes(Charsets.UTF_8));
            byteString.utf8 = str;
            return byteString;
        }

        private Companion() {
        }
    }

    public ByteString(byte[] bArr) {
        this.data = bArr;
    }

    public static int indexOf$default(ByteString byteString, ByteString byteString2) {
        byteString.getClass();
        return byteString.indexOf(0, byteString2.data);
    }

    public static int lastIndexOf$default(ByteString byteString, ByteString byteString2) {
        int i = SegmentedByteString.DEFAULT__ByteString_size;
        byteString.getClass();
        return byteString.lastIndexOf(i, byteString2.data);
    }

    private final void readObject(ObjectInputStream objectInputStream) throws IOException {
        int readInt = objectInputStream.readInt();
        Companion.getClass();
        if (readInt < 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(readInt, "byteCount < 0: ").toString());
        }
        byte[] bArr = new byte[readInt];
        int i = 0;
        while (i < readInt) {
            int read = objectInputStream.read(bArr, i, readInt - i);
            if (read == -1) {
                throw new EOFException();
            }
            i += read;
        }
        ByteString byteString = new ByteString(bArr);
        Field declaredField = ByteString.class.getDeclaredField("data");
        declaredField.setAccessible(true);
        declaredField.set(this, byteString.data);
    }

    public static ByteString substring$default(ByteString byteString, int i, int i2, int i3) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = SegmentedByteString.DEFAULT__ByteString_size;
        }
        return byteString.substring(i, i2);
    }

    private final void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.data.length);
        objectOutputStream.write(this.data);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            int size$external__okio__android_common__okio_lib = byteString.getSize$external__okio__android_common__okio_lib();
            byte[] bArr = this.data;
            if (size$external__okio__android_common__okio_lib == bArr.length && byteString.rangeEquals(0, 0, bArr.length, bArr)) {
                return true;
            }
        }
        return false;
    }

    public final byte[] getData$external__okio__android_common__okio_lib() {
        return this.data;
    }

    public int getSize$external__okio__android_common__okio_lib() {
        return this.data.length;
    }

    public int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int hashCode = Arrays.hashCode(this.data);
        this.hashCode = hashCode;
        return hashCode;
    }

    public String hex() {
        byte[] bArr = this.data;
        char[] cArr = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            char[] cArr2 = okio.internal.ByteString.HEX_DIGIT_CHARS;
            cArr[i] = cArr2[(b >> 4) & 15];
            i += 2;
            cArr[i2] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public int indexOf(int i, byte[] bArr) {
        int length = this.data.length - bArr.length;
        int max = Math.max(i, 0);
        if (max > length) {
            return -1;
        }
        while (!SegmentedByteString.arrayRangeEquals(this.data, max, 0, bArr, bArr.length)) {
            if (max == length) {
                return -1;
            }
            max++;
        }
        return max;
    }

    public byte[] internalArray$external__okio__android_common__okio_lib() {
        return this.data;
    }

    public byte internalGet$external__okio__android_common__okio_lib(int i) {
        return this.data[i];
    }

    public int lastIndexOf(int i, byte[] bArr) {
        if (i == SegmentedByteString.DEFAULT__ByteString_size) {
            i = getSize$external__okio__android_common__okio_lib();
        }
        for (int min = Math.min(i, this.data.length - bArr.length); -1 < min; min--) {
            if (SegmentedByteString.arrayRangeEquals(this.data, min, 0, bArr, bArr.length)) {
                return min;
            }
        }
        return -1;
    }

    public boolean rangeEquals(int i, int i2, int i3, byte[] bArr) {
        if (i < 0) {
            return false;
        }
        byte[] bArr2 = this.data;
        return i <= bArr2.length - i3 && i2 >= 0 && i2 <= bArr.length - i3 && SegmentedByteString.arrayRangeEquals(bArr2, i, i2, bArr, i3);
    }

    public ByteString substring(int i, int i2) {
        if (i2 == SegmentedByteString.DEFAULT__ByteString_size) {
            i2 = getSize$external__okio__android_common__okio_lib();
        }
        if (i < 0) {
            throw new IllegalArgumentException("beginIndex < 0");
        }
        byte[] bArr = this.data;
        if (i2 > bArr.length) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(bArr.length, "endIndex > length(", ")").toString());
        }
        if (i2 - i < 0) {
            throw new IllegalArgumentException("endIndex < beginIndex");
        }
        if (i == 0 && i2 == bArr.length) {
            return this;
        }
        ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(i2, bArr.length);
        return new ByteString(Arrays.copyOfRange(bArr, i, i2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01c3, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0105, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x00fd, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x013b, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x013f, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x00dd, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0099, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x00cb, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0088, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x007a, code lost:
    
        r1 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x017e, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0185, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0177, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01ba, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01bd, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01c0, code lost:
    
        if (r8 == 64) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x014b, code lost:
    
        if (r8 == 64) goto L184;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            Method dump skipped, instructions count: 642
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.ByteString.toString():java.lang.String");
    }

    public final String utf8() {
        String str = this.utf8;
        if (str != null) {
            return str;
        }
        String str2 = new String(internalArray$external__okio__android_common__okio_lib(), Charsets.UTF_8);
        this.utf8 = str2;
        return str2;
    }

    public void write$external__okio__android_common__okio_lib(Buffer buffer, int i) {
        buffer.write(this.data, 0, i);
    }

    @Override // java.lang.Comparable
    public final int compareTo(ByteString byteString) {
        int size$external__okio__android_common__okio_lib = getSize$external__okio__android_common__okio_lib();
        int size$external__okio__android_common__okio_lib2 = byteString.getSize$external__okio__android_common__okio_lib();
        int min = Math.min(size$external__okio__android_common__okio_lib, size$external__okio__android_common__okio_lib2);
        for (int i = 0; i < min; i++) {
            int internalGet$external__okio__android_common__okio_lib = internalGet$external__okio__android_common__okio_lib(i) & 255;
            int internalGet$external__okio__android_common__okio_lib2 = byteString.internalGet$external__okio__android_common__okio_lib(i) & 255;
            if (internalGet$external__okio__android_common__okio_lib != internalGet$external__okio__android_common__okio_lib2) {
                return internalGet$external__okio__android_common__okio_lib < internalGet$external__okio__android_common__okio_lib2 ? -1 : 1;
            }
        }
        if (size$external__okio__android_common__okio_lib == size$external__okio__android_common__okio_lib2) {
            return 0;
        }
        return size$external__okio__android_common__okio_lib < size$external__okio__android_common__okio_lib2 ? -1 : 1;
    }

    public boolean rangeEquals(int i, ByteString byteString, int i2) {
        return byteString.rangeEquals(0, i, i2, this.data);
    }

    public final int indexOf(ByteString byteString, int i) {
        return indexOf(i, byteString.data);
    }
}
