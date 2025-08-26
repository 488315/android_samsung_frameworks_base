package okio;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Charsets;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: classes4.dex */
public class ByteString implements Serializable, Comparable<ByteString> {
    public static final Companion Companion = new Companion(null);
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private static final long serialVersionUID = 1;
    private final byte[] data;
    public transient int hashCode;
    public transient String utf8;

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

    private final void readObject(ObjectInputStream objectInputStream) throws IllegalAccessException, NoSuchFieldException, IOException, IllegalArgumentException {
        int i = objectInputStream.readInt();
        Companion.getClass();
        if (i < 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "byteCount < 0: ").toString());
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = objectInputStream.read(bArr, i2, i - i2);
            if (i3 == -1) {
                throw new EOFException();
            }
            i2 += i3;
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
        int iHashCode = Arrays.hashCode(this.data);
        this.hashCode = iHashCode;
        return iHashCode;
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
        int iMax = Math.max(i, 0);
        if (iMax > length) {
            return -1;
        }
        while (!SegmentedByteString.arrayRangeEquals(this.data, iMax, 0, bArr, bArr.length)) {
            if (iMax == length) {
                return -1;
            }
            iMax++;
        }
        return iMax;
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
        for (int iMin = Math.min(i, this.data.length - bArr.length); -1 < iMin; iMin--) {
            if (SegmentedByteString.arrayRangeEquals(this.data, iMin, 0, bArr, bArr.length)) {
                return iMin;
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
                int i5 = i4 + 1;
                if (i4 == 64) {
                    break;
                }
                if ((b2 != 10 && b2 != 13 && ((b2 >= 0 && b2 < 32) || (Byte.MAX_VALUE <= b2 && b2 < 160))) || b2 == 65533) {
                    break;
                }
                i3 += b2 < 65536 ? 1 : 2;
                i2++;
                while (true) {
                    i4 = i5;
                    if (i2 < length && (b = bArr[i2]) >= 0) {
                        i2++;
                        i5 = i4 + 1;
                        if (i4 == 64) {
                            break loop0;
                        }
                        if ((b != 10 && b != 13 && ((b >= 0 && b < 32) || (Byte.MAX_VALUE <= b && b < 160))) || b == 65533) {
                            break loop0;
                        }
                        i3 += b < 65536 ? 1 : 2;
                    } else {
                        break;
                    }
                }
                Unit unit = Unit.INSTANCE;
            } else if ((b2 >> 5) == -2) {
                int i6 = i2 + 1;
                if (length > i6) {
                    byte b3 = bArr[i6];
                    if ((b3 & 192) == 128) {
                        int i7 = (b3 ^ 3968) ^ (b2 << 6);
                        if (i7 >= 128) {
                            i = i4 + 1;
                            if (i4 == 64) {
                                break;
                            }
                            if ((i7 != 10 && i7 != 13 && ((i7 >= 0 && i7 < 32) || (127 <= i7 && i7 < 160))) || i7 == 65533) {
                                break;
                            }
                            i3 += i7 < 65536 ? 1 : 2;
                            Unit unit2 = Unit.INSTANCE;
                            i2 += 2;
                            i4 = i;
                        } else if (i4 != 64) {
                            break;
                        }
                    } else if (i4 != 64) {
                        break;
                    }
                } else if (i4 != 64) {
                    break;
                }
            } else if ((b2 >> 4) == -2) {
                int i8 = i2 + 2;
                if (length > i8) {
                    byte b4 = bArr[i2 + 1];
                    if ((b4 & 192) == 128) {
                        byte b5 = bArr[i8];
                        if ((b5 & 192) == 128) {
                            int i9 = ((b5 ^ (-123008)) ^ (b4 << 6)) ^ (b2 << 12);
                            if (i9 < 2048) {
                                if (i4 != 64) {
                                    break;
                                }
                            } else if (55296 > i9 || i9 >= 57344) {
                                i = i4 + 1;
                                if (i4 == 64) {
                                    break;
                                }
                                if ((i9 != 10 && i9 != 13 && ((i9 >= 0 && i9 < 32) || (127 <= i9 && i9 < 160))) || i9 == 65533) {
                                    break;
                                }
                                i3 += i9 < 65536 ? 1 : 2;
                                Unit unit3 = Unit.INSTANCE;
                                i2 += 3;
                                i4 = i;
                            } else if (i4 != 64) {
                                break;
                            }
                        } else if (i4 != 64) {
                            break;
                        }
                    } else if (i4 != 64) {
                        break;
                    }
                } else if (i4 != 64) {
                    break;
                }
            } else if ((b2 >> 3) == -2) {
                int i10 = i2 + 3;
                if (length > i10) {
                    byte b6 = bArr[i2 + 1];
                    if ((b6 & 192) == 128) {
                        byte b7 = bArr[i2 + 2];
                        if ((b7 & 192) == 128) {
                            byte b8 = bArr[i10];
                            if ((b8 & 192) == 128) {
                                int i11 = (((b8 ^ 3678080) ^ (b7 << 6)) ^ (b6 << 12)) ^ (b2 << 18);
                                if (i11 > 1114111) {
                                    if (i4 != 64) {
                                        break;
                                    }
                                } else if (55296 > i11 || i11 >= 57344) {
                                    if (i11 >= 65536) {
                                        int i12 = i4 + 1;
                                        if (i4 == 64) {
                                            break;
                                        }
                                        if ((i11 != 10 && i11 != 13 && ((i11 >= 0 && i11 < 32) || (127 <= i11 && i11 < 160))) || i11 == 65533) {
                                            break;
                                        }
                                        i3 += i11 < 65536 ? 1 : 2;
                                        Unit unit4 = Unit.INSTANCE;
                                        i2 += 4;
                                        i4 = i12;
                                    } else if (i4 != 64) {
                                        break;
                                    }
                                } else if (i4 != 64) {
                                    break;
                                }
                            } else if (i4 != 64) {
                                break;
                            }
                        } else if (i4 != 64) {
                            break;
                        }
                    } else if (i4 != 64) {
                        break;
                    }
                } else if (i4 != 64) {
                    break;
                }
            } else if (i4 != 64) {
                break;
            }
        }
        i3 = -1;
        if (i3 != -1) {
            String strUtf8 = byteString.utf8();
            String strReplace$default = StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(strUtf8.substring(0, i3), "\\", "\\\\"), "\n", "\\n"), "\r", "\\r");
            if (i3 >= strUtf8.length()) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[text=", strReplace$default, "]");
            }
            return "[size=" + byteString.data.length + " text=" + strReplace$default + "…]";
        }
        byte[] bArr2 = byteString.data;
        if (bArr2.length <= 64) {
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[hex=", byteString.hex(), "]");
        }
        int length2 = bArr2.length;
        int size$external__okio__android_common__okio_lib = 64 == SegmentedByteString.DEFAULT__ByteString_size ? byteString.getSize$external__okio__android_common__okio_lib() : 64;
        byte[] bArr3 = byteString.data;
        if (size$external__okio__android_common__okio_lib > bArr3.length) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(bArr3.length, "endIndex > length(", ")").toString());
        }
        if (size$external__okio__android_common__okio_lib < 0) {
            throw new IllegalArgumentException("endIndex < beginIndex");
        }
        if (size$external__okio__android_common__okio_lib != bArr3.length) {
            ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(size$external__okio__android_common__okio_lib, bArr3.length);
            byteString = new ByteString(Arrays.copyOfRange(bArr3, 0, size$external__okio__android_common__okio_lib));
        }
        return "[size=" + length2 + " hex=" + byteString.hex() + "…]";
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
        int iMin = Math.min(size$external__okio__android_common__okio_lib, size$external__okio__android_common__okio_lib2);
        for (int i = 0; i < iMin; i++) {
            int iInternalGet$external__okio__android_common__okio_lib = internalGet$external__okio__android_common__okio_lib(i) & 255;
            int iInternalGet$external__okio__android_common__okio_lib2 = byteString.internalGet$external__okio__android_common__okio_lib(i) & 255;
            if (iInternalGet$external__okio__android_common__okio_lib != iInternalGet$external__okio__android_common__okio_lib2) {
                return iInternalGet$external__okio__android_common__okio_lib < iInternalGet$external__okio__android_common__okio_lib2 ? -1 : 1;
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
