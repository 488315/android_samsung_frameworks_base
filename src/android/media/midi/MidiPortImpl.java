package android.media.midi;

/* loaded from: classes3.dex */
class MidiPortImpl {
    private static final int DATA_PACKET_OVERHEAD = 9;
    public static final int MAX_PACKET_DATA_SIZE = 1015;
    public static final int MAX_PACKET_SIZE = 1024;
    public static final int PACKET_TYPE_DATA = 1;
    public static final int PACKET_TYPE_FLUSH = 2;
    private static final String TAG = "MidiPort";
    private static final int TIMESTAMP_SIZE = 8;

    public static int getDataOffset(byte[] bArr, int i) {
        return 1;
    }

    public static int getDataSize(byte[] bArr, int i) {
        return i - 9;
    }

    MidiPortImpl() {
    }

    public static int packData(byte[] bArr, int i, int i2, long j, byte[] bArr2) {
        if (i2 > 1015) {
            i2 = 1015;
        }
        int i3 = 0;
        bArr2[0] = 1;
        System.arraycopy(bArr, i, bArr2, 1, i2);
        int i4 = 1 + i2;
        while (i3 < 8) {
            bArr2[i4] = (byte) j;
            j >>= 8;
            i3++;
            i4++;
        }
        return i4;
    }

    public static int packFlush(byte[] bArr) {
        bArr[0] = 2;
        return 1;
    }

    public static int getPacketType(byte[] bArr, int i) {
        return bArr[0];
    }

    public static long getPacketTimestamp(byte[] bArr, int i) {
        long j = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            i--;
            j = (j << 8) | (bArr[i] & 255);
        }
        return j;
    }
}
