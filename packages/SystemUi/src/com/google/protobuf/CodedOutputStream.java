package com.google.protobuf;

import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.google.protobuf.Utf8;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class CodedOutputStream extends ByteOutput {
    public CodedOutputStreamWriter wrapper;
    public static final Logger logger = Logger.getLogger(CodedOutputStream.class.getName());
    public static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS;

    public static int computeBytesSize(int i, ByteString byteString) {
        int computeTagSize = computeTagSize(i);
        int size = byteString.size();
        return computeUInt32SizeNoTag(size) + size + computeTagSize;
    }

    public static int computeFixed32Size(int i) {
        return computeTagSize(i) + 4;
    }

    public static int computeFixed64Size(int i) {
        return computeTagSize(i) + 8;
    }

    public static int computeGroupSize(int i, MessageLite messageLite, Schema schema) {
        return ((AbstractMessageLite) messageLite).getSerializedSize(schema) + (computeTagSize(i) * 2);
    }

    public static int computeInt32SizeNoTag(int i) {
        if (i >= 0) {
            return computeUInt32SizeNoTag(i);
        }
        return 10;
    }

    public static int computeLazyFieldSizeNoTag(LazyFieldLite lazyFieldLite) {
        int size;
        if (lazyFieldLite.memoizedBytes != null) {
            size = lazyFieldLite.memoizedBytes.size();
        } else {
            ByteString byteString = lazyFieldLite.delayedBytes;
            size = byteString != null ? byteString.size() : lazyFieldLite.value != null ? ((GeneratedMessageLite) lazyFieldLite.value).getSerializedSize() : 0;
        }
        return computeUInt32SizeNoTag(size) + size;
    }

    public static int computeStringSizeNoTag(String str) {
        int length;
        try {
            length = Utf8.encodedLength(str);
        } catch (Utf8.UnpairedSurrogateException unused) {
            length = str.getBytes(Internal.UTF_8).length;
        }
        return computeUInt32SizeNoTag(length) + length;
    }

    public static int computeTagSize(int i) {
        return computeUInt32SizeNoTag((i << 3) | 0);
    }

    public static int computeUInt32Size(int i, int i2) {
        return computeUInt32SizeNoTag(i2) + computeTagSize(i);
    }

    public static int computeUInt32SizeNoTag(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int computeUInt64SizeNoTag(long j) {
        int i;
        if (((-128) & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if (((-34359738368L) & j) != 0) {
            j >>>= 28;
            i = 6;
        } else {
            i = 2;
        }
        if (((-2097152) & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        return (j & (-16384)) != 0 ? i + 1 : i;
    }

    public abstract void write(byte b);

    public abstract void writeBool(int i, boolean z);

    public abstract void writeByteArrayNoTag(byte[] bArr, int i);

    public abstract void writeBytes(int i, ByteString byteString);

    public abstract void writeBytesNoTag(ByteString byteString);

    public abstract void writeFixed32(int i, int i2);

    public abstract void writeFixed32NoTag(int i);

    public abstract void writeFixed64(int i, long j);

    public abstract void writeFixed64NoTag(long j);

    public abstract void writeInt32(int i, int i2);

    public abstract void writeInt32NoTag(int i);

    public abstract void writeMessage(int i, MessageLite messageLite, Schema schema);

    public abstract void writeMessageNoTag(MessageLite messageLite);

    public abstract void writeMessageSetExtension(int i, MessageLite messageLite);

    public abstract void writeRawMessageSetExtension(int i, ByteString byteString);

    public abstract void writeString(int i, String str);

    public abstract void writeStringNoTag(String str);

    public abstract void writeTag(int i, int i2);

    public abstract void writeUInt32(int i, int i2);

    public abstract void writeUInt32NoTag(int i);

    public abstract void writeUInt64(int i, long j);

    public abstract void writeUInt64NoTag(long j);

    private CodedOutputStream() {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ArrayEncoder extends CodedOutputStream {
        public final byte[] buffer;
        public final int limit;
        public int position;

        public ArrayEncoder(byte[] bArr, int i, int i2) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            }
            int i3 = i + i2;
            if ((i | i2 | (bArr.length - i3)) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
            }
            this.buffer = bArr;
            this.position = i;
            this.limit = i3;
        }

        public final int spaceLeft() {
            return this.limit - this.position;
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void write(byte b) {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeBool(int i, boolean z) {
            writeTag(i, 0);
            write(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeByteArrayNoTag(byte[] bArr, int i) {
            writeUInt32NoTag(i);
            write(bArr, 0, i);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeBytes(int i, ByteString byteString) {
            writeTag(i, 2);
            writeBytesNoTag(byteString);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeBytesNoTag(ByteString byteString) {
            writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeFixed32(int i, int i2) {
            writeTag(i, 5);
            writeFixed32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeFixed32NoTag(int i) {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                int i3 = i2 + 1;
                bArr[i2] = (byte) (i & 255);
                int i4 = i3 + 1;
                bArr[i3] = (byte) ((i >> 8) & 255);
                int i5 = i4 + 1;
                bArr[i4] = (byte) ((i >> 16) & 255);
                this.position = i5 + 1;
                bArr[i5] = (byte) ((i >> 24) & 255);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeFixed64(int i, long j) {
            writeTag(i, 1);
            writeFixed64NoTag(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeFixed64NoTag(long j) {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                int i2 = i + 1;
                bArr[i] = (byte) (((int) j) & 255);
                int i3 = i2 + 1;
                bArr[i2] = (byte) (((int) (j >> 8)) & 255);
                int i4 = i3 + 1;
                bArr[i3] = (byte) (((int) (j >> 16)) & 255);
                int i5 = i4 + 1;
                bArr[i4] = (byte) (((int) (j >> 24)) & 255);
                int i6 = i5 + 1;
                bArr[i5] = (byte) (((int) (j >> 32)) & 255);
                int i7 = i6 + 1;
                bArr[i6] = (byte) (((int) (j >> 40)) & 255);
                int i8 = i7 + 1;
                bArr[i7] = (byte) (((int) (j >> 48)) & 255);
                this.position = i8 + 1;
                bArr[i8] = (byte) (((int) (j >> 56)) & 255);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeInt32(int i, int i2) {
            writeTag(i, 0);
            writeInt32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeInt32NoTag(int i) {
            if (i >= 0) {
                writeUInt32NoTag(i);
            } else {
                writeUInt64NoTag(i);
            }
        }

        @Override // com.google.protobuf.ByteOutput
        public final void writeLazy(byte[] bArr, int i, int i2) {
            write(bArr, i, i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeMessage(int i, MessageLite messageLite, Schema schema) {
            writeTag(i, 2);
            writeUInt32NoTag(((AbstractMessageLite) messageLite).getSerializedSize(schema));
            schema.writeTo(messageLite, this.wrapper);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeMessageNoTag(MessageLite messageLite) {
            GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) messageLite;
            writeUInt32NoTag(generatedMessageLite.getSerializedSize(null));
            generatedMessageLite.writeTo(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeMessageSetExtension(int i, MessageLite messageLite) {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeTag(3, 2);
            writeMessageNoTag(messageLite);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeRawMessageSetExtension(int i, ByteString byteString) {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeString(int i, String str) {
            writeTag(i, 2);
            writeStringNoTag(str);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeStringNoTag(String str) {
            int i = this.position;
            try {
                int computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(str.length() * 3);
                int computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(str.length());
                int i2 = this.limit;
                byte[] bArr = this.buffer;
                if (computeUInt32SizeNoTag2 == computeUInt32SizeNoTag) {
                    int i3 = i + computeUInt32SizeNoTag2;
                    this.position = i3;
                    int encodeUtf8 = Utf8.processor.encodeUtf8(str, bArr, i3, i2 - i3);
                    this.position = i;
                    writeUInt32NoTag((encodeUtf8 - i) - computeUInt32SizeNoTag2);
                    this.position = encodeUtf8;
                } else {
                    writeUInt32NoTag(Utf8.encodedLength(str));
                    int i4 = this.position;
                    this.position = Utf8.processor.encodeUtf8(str, bArr, i4, i2 - i4);
                }
            } catch (Utf8.UnpairedSurrogateException e) {
                this.position = i;
                CodedOutputStream.logger.log(Level.WARNING, "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) e);
                byte[] bytes = str.getBytes(Internal.UTF_8);
                try {
                    writeUInt32NoTag(bytes.length);
                    write(bytes, 0, bytes.length);
                } catch (IndexOutOfBoundsException e2) {
                    throw new OutOfSpaceException(e2);
                }
            } catch (IndexOutOfBoundsException e3) {
                throw new OutOfSpaceException(e3);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeTag(int i, int i2) {
            writeUInt32NoTag((i << 3) | i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeUInt32(int i, int i2) {
            writeTag(i, 0);
            writeUInt32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeUInt32NoTag(int i) {
            while (true) {
                int i2 = i & (-128);
                byte[] bArr = this.buffer;
                if (i2 == 0) {
                    int i3 = this.position;
                    this.position = i3 + 1;
                    bArr[i3] = (byte) i;
                    return;
                } else {
                    try {
                        int i4 = this.position;
                        this.position = i4 + 1;
                        bArr[i4] = (byte) ((i & 127) | 128);
                        i >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
                    }
                }
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeUInt64(int i, long j) {
            writeTag(i, 0);
            writeUInt64NoTag(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeUInt64NoTag(long j) {
            int i = this.limit;
            byte[] bArr = this.buffer;
            if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS && i - this.position >= 10) {
                while ((j & (-128)) != 0) {
                    int i2 = this.position;
                    this.position = i2 + 1;
                    UnsafeUtil.putByte(bArr, i2, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                int i3 = this.position;
                this.position = i3 + 1;
                UnsafeUtil.putByte(bArr, i3, (byte) j);
                return;
            }
            while ((j & (-128)) != 0) {
                try {
                    int i4 = this.position;
                    this.position = i4 + 1;
                    bArr[i4] = (byte) ((((int) j) & 127) | 128);
                    j >>>= 7;
                } catch (IndexOutOfBoundsException e) {
                    throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(i), 1), e);
                }
            }
            int i5 = this.position;
            this.position = i5 + 1;
            bArr[i5] = (byte) j;
        }

        public final void write(byte[] bArr, int i, int i2) {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)), e);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class OutOfSpaceException extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        public OutOfSpaceException(String str) {
            super(KeyAttributes$$ExternalSyntheticOutline0.m21m("CodedOutputStream was writing to a flat byte array and ran out of space.: ", str));
        }

        public OutOfSpaceException(String str, Throwable th) {
            super(KeyAttributes$$ExternalSyntheticOutline0.m21m("CodedOutputStream was writing to a flat byte array and ran out of space.: ", str), th);
        }

        public OutOfSpaceException() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        public OutOfSpaceException(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }
    }
}
