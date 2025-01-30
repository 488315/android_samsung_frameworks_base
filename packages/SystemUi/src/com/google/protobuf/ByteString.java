package com.google.protobuf;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.systemui.AbstractC0970x34f4116a;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ByteString implements Iterable<Byte>, Serializable {
    public static final ByteString EMPTY = new LiteralByteString(Internal.EMPTY_BYTE_ARRAY);
    public static final ByteArrayCopier byteArrayCopier;
    private int hash = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.protobuf.ByteString$1 */
    public final class C45231 extends AbstractByteIterator {
        public final int limit;
        public int position = 0;

        public C45231() {
            this.limit = ByteString.this.size();
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.position < this.limit;
        }

        @Override // com.google.protobuf.ByteString.AbstractByteIterator
        public final byte nextByte() {
            int i = this.position;
            if (i >= this.limit) {
                throw new NoSuchElementException();
            }
            this.position = i + 1;
            return ByteString.this.internalByteAt(i);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class AbstractByteIterator implements Iterator {
        @Override // java.util.Iterator
        public final Object next() {
            return Byte.valueOf(nextByte());
        }

        public abstract byte nextByte();

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ArraysByteArrayCopier implements ByteArrayCopier {
        private ArraysByteArrayCopier() {
        }

        public /* synthetic */ ArraysByteArrayCopier(C45231 c45231) {
            this();
        }

        @Override // com.google.protobuf.ByteString.ByteArrayCopier
        public final byte[] copyFrom(byte[] bArr, int i, int i2) {
            return Arrays.copyOfRange(bArr, i, i2 + i);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class BoundedByteString extends LiteralByteString {
        private static final long serialVersionUID = 1;
        private final int bytesLength;
        private final int bytesOffset;

        public BoundedByteString(byte[] bArr, int i, int i2) {
            super(bArr);
            ByteString.checkRange(i, i + i2, bArr.length);
            this.bytesOffset = i;
            this.bytesLength = i2;
        }

        private void readObject(ObjectInputStream objectInputStream) {
            throw new InvalidObjectException("BoundedByteStream instances are not to be serialized directly");
        }

        @Override // com.google.protobuf.ByteString.LiteralByteString, com.google.protobuf.ByteString
        public final byte byteAt(int i) {
            int i2 = this.bytesLength;
            if (((i2 - (i + 1)) | i) >= 0) {
                return this.bytes[this.bytesOffset + i];
            }
            if (i < 0) {
                throw new ArrayIndexOutOfBoundsException(AbstractC0000x2c234b15.m0m("Index < 0: ", i));
            }
            throw new ArrayIndexOutOfBoundsException(AbstractC0970x34f4116a.m94m("Index > length: ", i, ", ", i2));
        }

        public final void copyToInternal(int i, byte[] bArr) {
            System.arraycopy(this.bytes, this.bytesOffset + 0, bArr, 0, i);
        }

        @Override // com.google.protobuf.ByteString.LiteralByteString
        public final int getOffsetIntoBytes() {
            return this.bytesOffset;
        }

        @Override // com.google.protobuf.ByteString.LiteralByteString, com.google.protobuf.ByteString
        public final byte internalByteAt(int i) {
            return this.bytes[this.bytesOffset + i];
        }

        @Override // com.google.protobuf.ByteString.LiteralByteString, com.google.protobuf.ByteString
        public final int size() {
            return this.bytesLength;
        }

        public Object writeReplace() {
            byte[] bArr;
            int i = this.bytesLength;
            if (i == 0) {
                bArr = Internal.EMPTY_BYTE_ARRAY;
            } else {
                byte[] bArr2 = new byte[i];
                copyToInternal(i, bArr2);
                bArr = bArr2;
            }
            return new LiteralByteString(bArr);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ByteArrayCopier {
        byte[] copyFrom(byte[] bArr, int i, int i2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CodedBuilder {
        public final byte[] buffer;
        public final CodedOutputStream.ArrayEncoder output;

        public /* synthetic */ CodedBuilder(int i, C45231 c45231) {
            this(i);
        }

        private CodedBuilder(int i) {
            byte[] bArr = new byte[i];
            this.buffer = bArr;
            Logger logger = CodedOutputStream.logger;
            this.output = new CodedOutputStream.ArrayEncoder(bArr, 0, i);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class LeafByteString extends ByteString {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LiteralByteString extends LeafByteString {
        private static final long serialVersionUID = 1;
        protected final byte[] bytes;

        public LiteralByteString(byte[] bArr) {
            bArr.getClass();
            this.bytes = bArr;
        }

        @Override // com.google.protobuf.ByteString
        public byte byteAt(int i) {
            return this.bytes[i];
        }

        @Override // com.google.protobuf.ByteString
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ByteString) || size() != ((ByteString) obj).size()) {
                return false;
            }
            if (size() == 0) {
                return true;
            }
            if (!(obj instanceof LiteralByteString)) {
                return obj.equals(this);
            }
            LiteralByteString literalByteString = (LiteralByteString) obj;
            int peekCachedHashCode = peekCachedHashCode();
            int peekCachedHashCode2 = literalByteString.peekCachedHashCode();
            if (peekCachedHashCode != 0 && peekCachedHashCode2 != 0 && peekCachedHashCode != peekCachedHashCode2) {
                return false;
            }
            int size = size();
            if (size > literalByteString.size()) {
                throw new IllegalArgumentException("Length too large: " + size + size());
            }
            if (0 + size > literalByteString.size()) {
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("Ran off end of other: 0, ", size, ", ");
                m1m.append(literalByteString.size());
                throw new IllegalArgumentException(m1m.toString());
            }
            byte[] bArr = this.bytes;
            byte[] bArr2 = literalByteString.bytes;
            int offsetIntoBytes = getOffsetIntoBytes() + size;
            int offsetIntoBytes2 = getOffsetIntoBytes();
            int offsetIntoBytes3 = literalByteString.getOffsetIntoBytes() + 0;
            while (offsetIntoBytes2 < offsetIntoBytes) {
                if (bArr[offsetIntoBytes2] != bArr2[offsetIntoBytes3]) {
                    return false;
                }
                offsetIntoBytes2++;
                offsetIntoBytes3++;
            }
            return true;
        }

        public int getOffsetIntoBytes() {
            return 0;
        }

        @Override // com.google.protobuf.ByteString
        public byte internalByteAt(int i) {
            return this.bytes[i];
        }

        @Override // com.google.protobuf.ByteString
        public final boolean isValidUtf8() {
            int offsetIntoBytes = getOffsetIntoBytes();
            return Utf8.processor.partialIsValidUtf8(offsetIntoBytes, size() + offsetIntoBytes, this.bytes) == 0;
        }

        @Override // com.google.protobuf.ByteString
        public final CodedInputStream.ArrayDecoder newCodedInput() {
            byte[] bArr = this.bytes;
            int offsetIntoBytes = getOffsetIntoBytes();
            int size = size();
            CodedInputStream.ArrayDecoder arrayDecoder = new CodedInputStream.ArrayDecoder(bArr, offsetIntoBytes, size, true);
            try {
                arrayDecoder.pushLimit(size);
                return arrayDecoder;
            } catch (InvalidProtocolBufferException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override // com.google.protobuf.ByteString
        public final int partialHash(int i, int i2) {
            byte[] bArr = this.bytes;
            int offsetIntoBytes = getOffsetIntoBytes() + 0;
            Charset charset = Internal.UTF_8;
            for (int i3 = offsetIntoBytes; i3 < offsetIntoBytes + i2; i3++) {
                i = (i * 31) + bArr[i3];
            }
            return i;
        }

        @Override // com.google.protobuf.ByteString
        public int size() {
            return this.bytes.length;
        }

        @Override // com.google.protobuf.ByteString
        public final ByteString substring(int i) {
            int checkRange = ByteString.checkRange(0, i, size());
            return checkRange == 0 ? ByteString.EMPTY : new BoundedByteString(this.bytes, getOffsetIntoBytes() + 0, checkRange);
        }

        @Override // com.google.protobuf.ByteString
        public final String toStringInternal(Charset charset) {
            return new String(this.bytes, getOffsetIntoBytes(), size(), charset);
        }

        @Override // com.google.protobuf.ByteString
        public final void writeTo(ByteOutput byteOutput) {
            byteOutput.writeLazy(this.bytes, getOffsetIntoBytes(), size());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SystemByteArrayCopier implements ByteArrayCopier {
        private SystemByteArrayCopier() {
        }

        public /* synthetic */ SystemByteArrayCopier(C45231 c45231) {
            this();
        }

        @Override // com.google.protobuf.ByteString.ByteArrayCopier
        public final byte[] copyFrom(byte[] bArr, int i, int i2) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            return bArr2;
        }
    }

    static {
        C45231 c45231 = null;
        byteArrayCopier = Android.isOnAndroidDevice() ? new SystemByteArrayCopier(c45231) : new ArraysByteArrayCopier(c45231);
        new Comparator() { // from class: com.google.protobuf.ByteString.2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                ByteString byteString = (ByteString) obj;
                ByteString byteString2 = (ByteString) obj2;
                byteString.getClass();
                C45231 c452312 = byteString.new C45231();
                byteString2.getClass();
                C45231 c452313 = byteString2.new C45231();
                while (c452312.hasNext() && c452313.hasNext()) {
                    int compareTo = Integer.valueOf(c452312.nextByte() & 255).compareTo(Integer.valueOf(c452313.nextByte() & 255));
                    if (compareTo != 0) {
                        return compareTo;
                    }
                }
                return Integer.valueOf(byteString.size()).compareTo(Integer.valueOf(byteString2.size()));
            }
        };
    }

    public static int checkRange(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Beginning index: ", i, " < 0"));
        }
        if (i2 < i) {
            throw new IndexOutOfBoundsException(AbstractC0970x34f4116a.m94m("Beginning index larger than ending index: ", i, ", ", i2));
        }
        throw new IndexOutOfBoundsException(AbstractC0970x34f4116a.m94m("End index: ", i2, " >= ", i3));
    }

    public static ByteString copyFrom(byte[] bArr, int i, int i2) {
        checkRange(i, i + i2, bArr.length);
        return new LiteralByteString(byteArrayCopier.copyFrom(bArr, i, i2));
    }

    public abstract byte byteAt(int i);

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.hash;
        if (i == 0) {
            int size = size();
            i = partialHash(size, size);
            if (i == 0) {
                i = 1;
            }
            this.hash = i;
        }
        return i;
    }

    public abstract byte internalByteAt(int i);

    public abstract boolean isValidUtf8();

    @Override // java.lang.Iterable
    public final Iterator<Byte> iterator() {
        return new C45231();
    }

    public abstract CodedInputStream.ArrayDecoder newCodedInput();

    public abstract int partialHash(int i, int i2);

    public final int peekCachedHashCode() {
        return this.hash;
    }

    public abstract int size();

    public abstract ByteString substring(int i);

    public final String toString() {
        String str;
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.toHexString(System.identityHashCode(this));
        objArr[1] = Integer.valueOf(size());
        if (size() <= 50) {
            str = TextFormatEscaper.escapeBytes(this);
        } else {
            str = TextFormatEscaper.escapeBytes(substring(47)) + "...";
        }
        objArr[2] = str;
        return String.format(locale, "<ByteString@%s size=%d contents=\"%s\">", objArr);
    }

    public abstract String toStringInternal(Charset charset);

    public abstract void writeTo(ByteOutput byteOutput);
}
