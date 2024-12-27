package com.samsung.android.service.ProtectedATCommand;

import android.util.Slog;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

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

    public void readStream(InputStream inputStream) {
        try {
            this.mSize = inputStream.read(this.mBuffer);
            if (this.mSize < 0 || this.mSize > 512) {
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

    public int compareVersion(int protocolVersion) {
        if (isEmpty() || this.mBuffer[0] != protocolVersion) {
            Slog.e(TAG, "Version is abnormal : " + ((int) this.mBuffer[0]));
            return 0;
        }
        return 1;
    }

    public int getCommand() {
        if (this.mSize < 3) {
            Slog.e(TAG, "Packet size is abnormal : " + this.mSize);
            return -268435456;
        }
        byte[] command = {this.mBuffer[2], this.mBuffer[1]};
        return new BigInteger(command).intValue();
    }

    public byte[] getItem(int itemType) {
        ByteBuffer byteBuffer =
                ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN).put(this.mBuffer);
        byteBuffer.position(3);
        int itemCount = byteBuffer.get(byteBuffer.position());
        byteBuffer.position(byteBuffer.position() + 1);
        Slog.d(TAG, "The number of items : " + itemCount);
        for (int i = 0; i < itemCount; i++) {
            int type = byteBuffer.getShort();
            int size = byteBuffer.getShort();
            Slog.d(TAG, "Item type : " + type + ", Item size : " + size);
            if (type == itemType) {
                byte[] ret = new byte[size];
                byteBuffer.get(ret, 0, size);
                return ret;
            }
            byteBuffer.position(byteBuffer.position() + size);
        }
        return null;
    }

    public byte[] getResponsePacket(int protocolVersion, int command, int item) {
        initPacket(protocolVersion, command);
        return putItem(Integer.valueOf(item), 3);
    }

    private void initPacket(int protocolVersion, int command) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte) protocolVersion);
        byteBuffer.putShort((short) command);
        byteBuffer.position(0);
        byteBuffer.get(this.mBuffer, 0, 512);
        this.mSize = 4;
    }

    private <T> byte[] putItem(T item, int itemType) {
        ByteBuffer byteBuffer =
                ByteBuffer.allocate(512).order(ByteOrder.LITTLE_ENDIAN).put(this.mBuffer);
        byteBuffer.position(this.mSize);
        switch (itemType) {
            case 3:
                if (byteBuffer.position() + 8 < 512) {
                    byteBuffer.putShort((short) itemType);
                    byteBuffer.putShort((short) 4);
                    byteBuffer.putInt(Integer.parseInt(item.toString()));
                    this.mSize += 8;
                    byteBuffer.rewind();
                    byteBuffer.get(this.mBuffer, 0, this.mSize);
                    byte[] bArr = this.mBuffer;
                    bArr[3] = (byte) (bArr[3] + 1);
                    return buffer();
                }
                Slog.e(TAG, "Packet is full, Can't put item to packet");
                return null;
            default:
                Slog.e(TAG, "Unknown item type : " + itemType);
                return null;
        }
    }
}
