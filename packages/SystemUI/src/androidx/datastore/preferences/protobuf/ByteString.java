package androidx.datastore.preferences.protobuf;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.protobuf.CodedInputStream;
import androidx.datastore.preferences.protobuf.CodedOutputStream;
import java.io.IOException;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ByteString implements Iterable<Byte>, Serializable {
    public static final ByteString EMPTY = new LiteralByteString(Internal.EMPTY_BYTE_ARRAY);
    public static final ByteArrayCopier byteArrayCopier;
    private static final long serialVersionUID = 1;
    private int hash = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.datastore.preferences.protobuf.ByteString$1, reason: invalid class name */
    public class AnonymousClass1 extends AbstractByteIterator {
        public final int limit;
        public int position = 0;

        public AnonymousClass1() {
            this.limit = ByteString.this.size();
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.position < this.limit;
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString.AbstractByteIterator
        public final byte nextByte() {
            int i = this.position;
            if (i >= this.limit) {
                throw new NoSuchElementException();
            }
            this.position = i + 1;
            return ByteString.this.internalByteAt(i);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ArraysByteArrayCopier implements ByteArrayCopier {
        private ArraysByteArrayCopier() {
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString.ByteArrayCopier
        public final byte[] copyFrom(int i, int i2, byte[] bArr) {
            return Arrays.copyOfRange(bArr, i, i2 + i);
        }

        public /* synthetic */ ArraysByteArrayCopier(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class BoundedByteString extends LiteralByteString {
        private static final long serialVersionUID = 1;
        private final int bytesLength;
        private final int bytesOffset;

        public BoundedByteString(byte[] bArr, int i, int i2) {
            super(bArr);
            ByteString.checkRange(i, i + i2, bArr.length);
            this.bytesOffset = i;
            this.bytesLength = i2;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException {
            throw new InvalidObjectException("BoundedByteStream instances are not to be serialized directly");
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString.LiteralByteString, androidx.datastore.preferences.protobuf.ByteString
        public final byte byteAt(int i) {
            int i2 = this.bytesLength;
            if (((i2 - (i + 1)) | i) >= 0) {
                return this.bytes[this.bytesOffset + i];
            }
            if (i < 0) {
                throw new ArrayIndexOutOfBoundsException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Index < 0: "));
            }
            throw new ArrayIndexOutOfBoundsException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Index > length: ", ", "));
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString.LiteralByteString, androidx.datastore.preferences.protobuf.ByteString
        public final void copyToInternal(int i, byte[] bArr) {
            System.arraycopy(this.bytes, this.bytesOffset, bArr, 0, i);
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString.LiteralByteString
        public final int getOffsetIntoBytes() {
            return this.bytesOffset;
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString.LiteralByteString, androidx.datastore.preferences.protobuf.ByteString
        public final byte internalByteAt(int i) {
            return this.bytes[this.bytesOffset + i];
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString.LiteralByteString, androidx.datastore.preferences.protobuf.ByteString
        public final int size() {
            return this.bytesLength;
        }

        public Object writeReplace() {
            byte[] bArr;
            int size = size();
            if (size == 0) {
                bArr = Internal.EMPTY_BYTE_ARRAY;
            } else {
                byte[] bArr2 = new byte[size];
                copyToInternal(size, bArr2);
                bArr = bArr2;
            }
            return new LiteralByteString(bArr);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ByteArrayCopier {
        byte[] copyFrom(int i, int i2, byte[] bArr);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CodedBuilder {
        public final byte[] buffer;
        public final CodedOutputStream.ArrayEncoder output;

        public /* synthetic */ CodedBuilder(int i, AnonymousClass1 anonymousClass1) {
            this(i);
        }

        private CodedBuilder(int i) {
            byte[] bArr = new byte[i];
            this.buffer = bArr;
            Logger logger = CodedOutputStream.logger;
            this.output = new CodedOutputStream.ArrayEncoder(bArr, 0, i);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    abstract class LeafByteString extends ByteString {
        private static final long serialVersionUID = 1;

        public /* synthetic */ LeafByteString(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString, java.lang.Iterable
        public final Iterator<Byte> iterator() {
            return new AnonymousClass1();
        }

        private LeafByteString() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class LiteralByteString extends LeafByteString {
        private static final long serialVersionUID = 1;
        protected final byte[] bytes;

        public LiteralByteString(byte[] bArr) {
            super(null);
            bArr.getClass();
            this.bytes = bArr;
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString
        public byte byteAt(int i) {
            return this.bytes[i];
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString
        public void copyToInternal(int i, byte[] bArr) {
            System.arraycopy(this.bytes, 0, bArr, 0, i);
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString
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
            if (size > literalByteString.size()) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size, "Ran off end of other: 0, ", ", ");
                m.append(literalByteString.size());
                throw new IllegalArgumentException(m.toString());
            }
            byte[] bArr = this.bytes;
            byte[] bArr2 = literalByteString.bytes;
            int offsetIntoBytes = getOffsetIntoBytes() + size;
            int offsetIntoBytes2 = getOffsetIntoBytes();
            int offsetIntoBytes3 = literalByteString.getOffsetIntoBytes();
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

        @Override // androidx.datastore.preferences.protobuf.ByteString
        public byte internalByteAt(int i) {
            return this.bytes[i];
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString
        public final CodedInputStream.ArrayDecoder newCodedInput() {
            return CodedInputStream.newInstance(this.bytes, getOffsetIntoBytes(), size(), true);
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString
        public final int partialHash(int i, int i2) {
            byte[] bArr = this.bytes;
            int offsetIntoBytes = getOffsetIntoBytes();
            Charset charset = Internal.UTF_8;
            for (int i3 = offsetIntoBytes; i3 < offsetIntoBytes + i2; i3++) {
                i = (i * 31) + bArr[i3];
            }
            return i;
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString
        public int size() {
            return this.bytes.length;
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString
        public final ByteString substring(int i) {
            int checkRange = ByteString.checkRange(0, i, size());
            return checkRange == 0 ? ByteString.EMPTY : new BoundedByteString(this.bytes, getOffsetIntoBytes(), checkRange);
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString
        public final void writeTo(CodedOutputStream codedOutputStream) {
            codedOutputStream.writeLazy(getOffsetIntoBytes(), size(), this.bytes);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SystemByteArrayCopier implements ByteArrayCopier {
        private SystemByteArrayCopier() {
        }

        @Override // androidx.datastore.preferences.protobuf.ByteString.ByteArrayCopier
        public final byte[] copyFrom(int i, int i2, byte[] bArr) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            return bArr2;
        }

        public /* synthetic */ SystemByteArrayCopier(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    static {
        AnonymousClass1 anonymousClass1 = null;
        byteArrayCopier = Android.isOnAndroidDevice() ? new SystemByteArrayCopier(anonymousClass1) : new ArraysByteArrayCopier(anonymousClass1);
        new Comparator() { // from class: androidx.datastore.preferences.protobuf.ByteString.2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                ByteString byteString = (ByteString) obj;
                ByteString byteString2 = (ByteString) obj2;
                byteString.getClass();
                AnonymousClass1 anonymousClass12 = byteString.new AnonymousClass1();
                byteString2.getClass();
                AnonymousClass1 anonymousClass13 = byteString2.new AnonymousClass1();
                while (anonymousClass12.hasNext() && anonymousClass13.hasNext()) {
                    int compareTo = Integer.valueOf(anonymousClass12.nextByte() & 255).compareTo(Integer.valueOf(anonymousClass13.nextByte() & 255));
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
            throw new IndexOutOfBoundsException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Beginning index: ", " < 0"));
        }
        if (i2 < i) {
            throw new IndexOutOfBoundsException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Beginning index larger than ending index: ", ", "));
        }
        throw new IndexOutOfBoundsException(ListImplementation$$ExternalSyntheticOutline0.m(i2, i3, "End index: ", " >= "));
    }

    public static ByteString copyFrom(int i, int i2, byte[] bArr) {
        checkRange(i, i + i2, bArr.length);
        return new LiteralByteString(byteArrayCopier.copyFrom(i, i2, bArr));
    }

    public abstract byte byteAt(int i);

    public abstract void copyToInternal(int i, byte[] bArr);

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

    @Override // java.lang.Iterable
    public Iterator<Byte> iterator() {
        return new AnonymousClass1();
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
        String hexString = Integer.toHexString(System.identityHashCode(this));
        int size = size();
        if (size() <= 50) {
            str = TextFormatEscaper.escapeBytes(this);
        } else {
            str = TextFormatEscaper.escapeBytes(substring(47)) + "...";
        }
        return TransitionKt$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(size, "<ByteString@", hexString, " size=", " contents=\""), str, "\">");
    }

    public abstract void writeTo(CodedOutputStream codedOutputStream);
}
