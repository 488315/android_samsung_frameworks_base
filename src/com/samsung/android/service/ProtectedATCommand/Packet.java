package com.samsung.android.service.ProtectedATCommand;

import android.util.Slog;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class Packet {
    public static final int MAX_PACKET_SIZE = 512;
    public static final int PAC_PACKET_CMD_AT_CMD_CHECK = 1;
    public static final int PAC_PACKET_TYPE_AT_CMD = 2;
    public static final int PAC_PACKET_TYPE_AT_CMD_ATD_DDEXE = 4;
    public static final int PAC_PACKET_TYPE_AT_CMD_RET = 3;
    private static final String TAG = "PACMPacket";
    private final int VERSION_BUFFER_SIZE = 1;
    private final int COMMAND_BUFFER_SIZE = 2;
    private final int ITEM_COUNT_BUFFER_SIZE = 1;
    private byte[] mBuffer = new byte[512];
    private int mSize = 0;

    public void readStream(InputStream inputStream) throws IOException {
        try {
            int i = inputStream.read(this.mBuffer);
            this.mSize = i;
            if (i < 0 || i > 512) {
                Slog.e(TAG, "Buffer size is abnormal : " + this.mSize);
                this.mSize = 0;
            }
        } catch (IOException e) {
            Slog.e(TAG, "Failed to read the stream from the client : " + e.toString());
        }
    }

    public byte[] buffer() {
        return Arrays.copyOf(this.mBuffer, this.mSize);
    }

    public int size() {
        return this.mSize;
    }

    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    public int compareVersion(int i) {
        if (!isEmpty() && this.mBuffer[0] == i) {
            return 1;
        }
        Slog.e(TAG, "Version is abnormal : " + ((int) this.mBuffer[0]));
        return 0;
    }

    public int getCommand() {
        if (this.mSize < 3) {
            Slog.e(TAG, "Packet size is abnormal : " + this.mSize);
            return -268435456;
        }
        byte[] bArr = this.mBuffer;
        return new BigInteger(new byte[]{bArr[2], bArr[1]}).intValue();
    }

    public byte[] getItem(int i) {
        ByteBuffer byteBufferPut = ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN).put(this.mBuffer);
        byteBufferPut.position(3);
        byte b = byteBufferPut.get(byteBufferPut.position());
        byteBufferPut.position(byteBufferPut.position() + 1);
        Slog.d(TAG, "The number of items : " + ((int) b));
        for (int i2 = 0; i2 < b; i2++) {
            short s = byteBufferPut.getShort();
            int i3 = byteBufferPut.getShort();
            Slog.d(TAG, "Item type : " + ((int) s) + ", Item size : " + i3);
            if (s == i) {
                byte[] bArr = new byte[i3];
                byteBufferPut.get(bArr, 0, i3);
                return bArr;
            }
            byteBufferPut.position(byteBufferPut.position() + i3);
        }
        return null;
    }

    public byte[] getResponsePacket(int i, int i2, int i3) {
        initPacket(i, i2);
        return putItem(Integer.valueOf(i3), 3);
    }

    private void initPacket(int i, int i2) {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.put((byte) i);
        byteBufferOrder.putShort((short) i2);
        byteBufferOrder.position(0);
        byteBufferOrder.get(this.mBuffer, 0, 512);
        this.mSize = 4;
    }

    private <T> byte[] putItem(T t, int i) {
        ByteBuffer byteBufferPut = ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN).put(this.mBuffer);
        byteBufferPut.position(this.mSize);
        if (i == 3) {
            if (byteBufferPut.position() + 8 >= 512) {
                Slog.e(TAG, "Packet is full, Can't put item to packet");
                return null;
            }
            byteBufferPut.putShort((short) i);
            byteBufferPut.putShort((short) 4);
            byteBufferPut.putInt(Integer.parseInt(t.toString()));
            this.mSize += 8;
            byteBufferPut.rewind();
            byteBufferPut.get(this.mBuffer, 0, this.mSize);
            byte[] bArr = this.mBuffer;
            bArr[3] = (byte) (bArr[3] + 1);
            return buffer();
        }
        Slog.e(TAG, "Unknown item type : " + i);
        return null;
    }
}
