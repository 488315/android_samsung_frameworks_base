package okio;

import android.support.v4.media.AbstractC0000x2c234b15;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Charsets;
import kotlin.text.StringsKt__StringsJVMKt;
import okio.internal.ByteStringKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ByteString implements Serializable, Comparable<ByteString> {
    public static final Companion Companion = new Companion(null);
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private static final long serialVersionUID = 1;
    private final byte[] data;
    public transient int hashCode;
    public transient String utf8;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ByteString(byte[] bArr) {
        this.data = bArr;
    }

    public static final ByteString encodeUtf8(String str) {
        Companion.getClass();
        ByteString byteString = new ByteString(str.getBytes(Charsets.UTF_8));
        byteString.utf8 = str;
        return byteString;
    }

    private final void readObject(ObjectInputStream objectInputStream) {
        int readInt = objectInputStream.readInt();
        Companion.getClass();
        int i = 0;
        if (!(readInt >= 0)) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("byteCount < 0: ", readInt).toString());
        }
        byte[] bArr = new byte[readInt];
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

    private final void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.writeInt(this.data.length);
        objectOutputStream.write(this.data);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002b A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002d A[ORIG_RETURN, RETURN] */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int compareTo(ByteString byteString) {
        ByteString byteString2 = byteString;
        int size$okio = getSize$okio();
        int size$okio2 = byteString2.getSize$okio();
        int min = Math.min(size$okio, size$okio2);
        for (int i = 0; i < min; i++) {
            int internalGet$okio = internalGet$okio(i) & 255;
            int internalGet$okio2 = byteString2.internalGet$okio(i) & 255;
            if (internalGet$okio != internalGet$okio2) {
                return internalGet$okio < internalGet$okio2 ? -1 : 1;
            }
        }
        if (size$okio == size$okio2) {
            return 0;
        }
        if (size$okio < size$okio2) {
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            int size$okio = byteString.getSize$okio();
            byte[] bArr = this.data;
            if (size$okio == bArr.length && byteString.rangeEquals(0, 0, bArr.length, bArr)) {
                return true;
            }
        }
        return false;
    }

    public final byte[] getData$okio() {
        return this.data;
    }

    public int getSize$okio() {
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
            char[] cArr2 = ByteStringKt.HEX_DIGIT_CHARS;
            cArr[i] = cArr2[(b >> 4) & 15];
            i = i2 + 1;
            cArr[i2] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public byte[] internalArray$okio() {
        return this.data;
    }

    public byte internalGet$okio(int i) {
        return this.data[i];
    }

    public boolean rangeEquals(int i, int i2, int i3, byte[] bArr) {
        boolean z;
        if (i < 0) {
            return false;
        }
        byte[] bArr2 = this.data;
        if (i > bArr2.length - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int i4 = 0;
        while (true) {
            if (i4 >= i3) {
                z = true;
                break;
            }
            if (bArr2[i4 + i] != bArr[i4 + i2]) {
                z = false;
                break;
            }
            i4++;
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:120:0x01a6, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x01ad, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x019e, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0188, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0179, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0168, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0157, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x01e2, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x00a1, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0096, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0087, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x011a, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x01e5, code lost:
    
        r7 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0111, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x00ff, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00f0, code lost:
    
        if (r6 == 64) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00df, code lost:
    
        if (r6 == 64) goto L215;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String toString() {
        byte b;
        int i;
        ByteString byteString = this;
        byte[] bArr = byteString.data;
        if (bArr.length == 0) {
            return "[size=0]";
        }
        int length = bArr.length;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        loop0: while (true) {
            if (i2 >= length) {
                break;
            }
            byte b2 = bArr[i2];
            if (b2 >= 0) {
                int i5 = i3 + 1;
                if (i3 == 64) {
                    break;
                }
                if (b2 != 10 && b2 != 13) {
                    if ((b2 >= 0 && 31 >= b2) || (Byte.MAX_VALUE <= b2 && 159 >= b2)) {
                        break;
                    }
                }
                if (b2 == 65533) {
                    break;
                }
                i4 += b2 < 65536 ? 1 : 2;
                i2++;
                while (true) {
                    i3 = i5;
                    if (i2 < length && (b = bArr[i2]) >= 0) {
                        i2++;
                        i5 = i3 + 1;
                        if (i3 == 64) {
                            break loop0;
                        }
                        if (b != 10 && b != 13) {
                            if ((b >= 0 && 31 >= b) || (Byte.MAX_VALUE <= b && 159 >= b)) {
                                break loop0;
                            }
                        }
                        if (b == 65533) {
                            break loop0;
                        }
                        i4 += b < 65536 ? 1 : 2;
                    }
                }
            } else if ((b2 >> 5) == -2) {
                int i6 = i2 + 1;
                if (length > i6) {
                    byte b3 = bArr[i6];
                    if ((b3 & 192) == 128) {
                        int i7 = (b3 ^ 3968) ^ (b2 << 6);
                        if (i7 >= 128) {
                            int i8 = i3 + 1;
                            if (i3 == 64) {
                                break;
                            }
                            if (i7 != 10 && i7 != 13) {
                                if ((i7 >= 0 && 31 >= i7) || (127 <= i7 && 159 >= i7)) {
                                    break;
                                }
                            }
                            if (i7 == 65533) {
                                break;
                            }
                            i4 += i7 < 65536 ? 1 : 2;
                            i2 += 2;
                            i3 = i8;
                        }
                    }
                }
            } else if ((b2 >> 4) == -2) {
                int i9 = i2 + 2;
                if (length > i9) {
                    byte b4 = bArr[i2 + 1];
                    if ((b4 & 192) == 128) {
                        byte b5 = bArr[i9];
                        if ((b5 & 192) == 128) {
                            int i10 = (((-123008) ^ b5) ^ (b4 << 6)) ^ (b2 << 12);
                            if (i10 >= 2048) {
                                if (55296 > i10 || 57343 < i10) {
                                    i = i3 + 1;
                                    if (i3 == 64) {
                                        break;
                                    }
                                    if (i10 != 10 && i10 != 13) {
                                        if ((i10 >= 0 && 31 >= i10) || (127 <= i10 && 159 >= i10)) {
                                            break;
                                        }
                                    }
                                    if (i10 == 65533) {
                                        break;
                                    }
                                    i4 += i10 < 65536 ? 1 : 2;
                                    i2 += 3;
                                    i3 = i;
                                }
                            }
                        }
                    }
                }
            } else if ((b2 >> 3) == -2) {
                int i11 = i2 + 3;
                if (length > i11) {
                    byte b6 = bArr[i2 + 1];
                    if ((b6 & 192) == 128) {
                        byte b7 = bArr[i2 + 2];
                        if ((b7 & 192) == 128) {
                            byte b8 = bArr[i11];
                            if ((b8 & 192) == 128) {
                                int i12 = (((3678080 ^ b8) ^ (b7 << 6)) ^ (b6 << 12)) ^ (b2 << 18);
                                if (i12 <= 1114111) {
                                    if (55296 > i12 || 57343 < i12) {
                                        if (i12 >= 65536) {
                                            i = i3 + 1;
                                            if (i3 == 64) {
                                                break;
                                            }
                                            if (i12 != 10 && i12 != 13) {
                                                if ((i12 >= 0 && 31 >= i12) || (127 <= i12 && 159 >= i12)) {
                                                    break;
                                                }
                                            }
                                            if (i12 == 65533) {
                                                break;
                                            }
                                            i4 += i12 < 65536 ? 1 : 2;
                                            i2 += 4;
                                            i3 = i;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (i4 != -1) {
            String str = byteString.utf8;
            if (str == null) {
                String str2 = new String(internalArray$okio(), Charsets.UTF_8);
                byteString.utf8 = str2;
                str = str2;
            }
            String replace$default = StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(str.substring(0, i4), "\\", "\\\\"), "\n", "\\n"), "\r", "\\r");
            if (i4 >= str.length()) {
                return "[text=" + replace$default + ']';
            }
            return "[size=" + byteString.data.length + " text=" + replace$default + "…]";
        }
        if (byteString.data.length <= 64) {
            return "[hex=" + hex() + ']';
        }
        StringBuilder sb = new StringBuilder("[size=");
        sb.append(byteString.data.length);
        sb.append(" hex=");
        byte[] bArr2 = byteString.data;
        if (!(64 <= bArr2.length)) {
            throw new IllegalArgumentException(("endIndex > length(" + byteString.data.length + ')').toString());
        }
        if (64 != bArr2.length) {
            ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(64, bArr2.length);
            byteString = new ByteString(Arrays.copyOfRange(bArr2, 0, 64));
        }
        sb.append(byteString.hex());
        sb.append("…]");
        return sb.toString();
    }

    public boolean rangeEquals(ByteString byteString, int i) {
        return byteString.rangeEquals(0, 0, i, this.data);
    }
}
