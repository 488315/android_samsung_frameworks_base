package android.internal.aconfig.storage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/* loaded from: classes2.dex */
public class ByteBufferReader {
    private ByteBuffer mByteBuffer;
    private int mPosition;

    public ByteBufferReader(ByteBuffer byteBuffer) {
        this.mByteBuffer = byteBuffer;
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public int readByte() {
        return Byte.toUnsignedInt(this.mByteBuffer.get(nextGetIndex(1)));
    }

    public int readShort() {
        return Short.toUnsignedInt(this.mByteBuffer.getShort(nextGetIndex(2)));
    }

    public int readInt() {
        return this.mByteBuffer.getInt(nextGetIndex(4));
    }

    public long readLong() {
        return this.mByteBuffer.getLong(nextGetIndex(8));
    }

    public String readString() {
        int readInt = readInt();
        if (readInt > 1024) {
            throw new AconfigStorageException("String length exceeds maximum allowed size (1024 bytes): " + readInt);
        }
        byte[] bArr = new byte[readInt];
        getArray(nextGetIndex(readInt), bArr, 0, readInt);
        return new String(bArr, StandardCharsets.UTF_8);
    }

    public int readByte(int i) {
        return Byte.toUnsignedInt(this.mByteBuffer.get(i));
    }

    public void position(int i) {
        this.mPosition = i;
    }

    public int position() {
        return this.mPosition;
    }

    private int nextGetIndex(int i) {
        int i2 = this.mPosition;
        this.mPosition = i + i2;
        return i2;
    }

    private void getArray(int i, byte[] bArr, int i2, int i3) {
        Objects.checkFromIndexSize(i, i3, this.mByteBuffer.limit());
        Objects.checkFromIndexSize(i2, i3, bArr.length);
        int i4 = i3 + i2;
        while (i2 < i4) {
            bArr[i2] = this.mByteBuffer.get(i);
            i2++;
            i++;
        }
    }
}
