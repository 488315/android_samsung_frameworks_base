package com.android.server.biometrics;

import android.hardware.keymaster.HardwareAuthToken;
import android.hardware.keymaster.Timestamp;

import java.nio.ByteOrder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class HardwareAuthTokenUtils {
    public static long getLong(int i, byte[] bArr) {
        long j = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            j += (bArr[i2 + i] & 255) << (i2 * 8);
        }
        return j;
    }

    public static byte[] toByteArray(HardwareAuthToken hardwareAuthToken) {
        byte[] bArr = new byte[69];
        bArr[0] = 0;
        writeLong(hardwareAuthToken.challenge, bArr, 1);
        writeLong(hardwareAuthToken.userId, bArr, 9);
        writeLong(hardwareAuthToken.authenticatorId, bArr, 17);
        int i = hardwareAuthToken.authenticatorType;
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        if (byteOrder == ByteOrder.nativeOrder()) {
            i = Integer.reverseBytes(i);
        }
        bArr[25] = (byte) i;
        bArr[26] = (byte) (i >> 8);
        bArr[27] = (byte) (i >> 16);
        bArr[28] = (byte) (i >> 24);
        long j = hardwareAuthToken.timestamp.milliSeconds;
        if (byteOrder == ByteOrder.nativeOrder()) {
            j = Long.reverseBytes(j);
        }
        writeLong(j, bArr, 29);
        byte[] bArr2 = hardwareAuthToken.mac;
        System.arraycopy(bArr2, 0, bArr, 37, bArr2.length);
        return bArr;
    }

    public static HardwareAuthToken toHardwareAuthToken(byte[] bArr) {
        HardwareAuthToken hardwareAuthToken = new HardwareAuthToken();
        hardwareAuthToken.challenge = getLong(1, bArr);
        hardwareAuthToken.userId = getLong(9, bArr);
        hardwareAuthToken.authenticatorId = getLong(17, bArr);
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i += (bArr[i2 + 25] & 255) << (i2 * 8);
        }
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        if (byteOrder == ByteOrder.nativeOrder()) {
            i = Integer.reverseBytes(i);
        }
        hardwareAuthToken.authenticatorType = i;
        Timestamp timestamp = new Timestamp();
        long j = getLong(29, bArr);
        if (byteOrder == ByteOrder.nativeOrder()) {
            j = Long.reverseBytes(j);
        }
        timestamp.milliSeconds = j;
        hardwareAuthToken.timestamp = timestamp;
        byte[] bArr2 = new byte[32];
        hardwareAuthToken.mac = bArr2;
        System.arraycopy(bArr, 37, bArr2, 0, 32);
        return hardwareAuthToken;
    }

    public static void writeLong(long j, byte[] bArr, int i) {
        bArr[i] = (byte) j;
        bArr[i + 1] = (byte) (j >> 8);
        bArr[i + 2] = (byte) (j >> 16);
        bArr[i + 3] = (byte) (j >> 24);
        bArr[i + 4] = (byte) (j >> 32);
        bArr[i + 5] = (byte) (j >> 40);
        bArr[i + 6] = (byte) (j >> 48);
        bArr[i + 7] = (byte) (j >> 56);
    }
}
