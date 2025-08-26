package com.samsung.android.service.DeviceRootKeyService;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

/* loaded from: classes6.dex */
public final class Tlv {
    public static final int TAG_LENGTH_FIELD_LEN = 3;
    public static final int TLV_TAG_DN_QUALIFIER = 6;
    public static final int TLV_TAG_EXPONENT = 1;
    public static final int TLV_TAG_EXT_KEYUSAGE = 7;
    public static final int TLV_TAG_HASH_ALGO = 3;
    public static final int TLV_TAG_ISSUER = 2;
    public static final int TLV_TAG_KEYUSAGE = 5;
    private static final int TLV_TAG_MAX = 30;
    public static final int TLV_TAG_SUBJECT = 4;
    public static final int TLV_TAG_SUBJECT_ALTER_NAME = 29;
    private int mTotalLength = 0;
    private HashMap<Integer, byte[]> mTlvList = new HashMap<>();

    public int getTotalSize() {
        return this.mTotalLength;
    }

    public void setTotalSize(int i) {
        this.mTotalLength = i;
    }

    public boolean setTlv(int i, byte[] bArr) {
        if (i < 1 || i >= 30) {
            return false;
        }
        this.mTlvList.put(Integer.valueOf(i), bArr);
        this.mTotalLength += bArr.length + 3;
        return true;
    }

    public boolean setTlvOnly(int i, byte[] bArr) {
        if (i < 1 || i >= 30) {
            return false;
        }
        this.mTlvList.put(Integer.valueOf(i), bArr);
        return true;
    }

    public byte[] getTlvValue(int i) {
        if (i < 1 || i >= 30) {
            return null;
        }
        return this.mTlvList.get(Integer.valueOf(i));
    }

    public byte[] encodeTlv() {
        byte[] bArr = new byte[this.mTotalLength + 3];
        bArr[0] = -2;
        byte[] bArrArray = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.mTotalLength).array();
        System.arraycopy(bArrArray, 0, bArr, 1, bArrArray.length);
        int length = 3;
        for (Integer num : this.mTlvList.keySet()) {
            byte[] bArr2 = this.mTlvList.get(num);
            byte[] bArrArray2 = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) bArr2.length).array();
            bArr[length] = num.byteValue();
            System.arraycopy(bArrArray2, 0, bArr, length + 1, bArrArray2.length);
            int i = length + 3;
            System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
            length = i + bArr2.length;
        }
        return bArr;
    }

    public static Tlv decodeTlv(byte[] bArr, int i, int i2) {
        Tlv tlv = new Tlv();
        int i3 = 3;
        if (i2 < 3 || bArr[0] != -2) {
            return null;
        }
        tlv.setTotalSize(ByteBuffer.wrap(bArr, i + 1, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());
        while (i3 < i2) {
            int unsignedInt = Byte.toUnsignedInt(bArr[i3]);
            int i4 = ByteBuffer.wrap(bArr, i3 + 1 + i, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
            int i5 = i3 + 3;
            byte[] bArr2 = new byte[i4];
            System.arraycopy(bArr, i + i5, bArr2, 0, i4);
            i3 = i5 + i4;
            tlv.setTlvOnly(unsignedInt, bArr2);
        }
        return tlv;
    }
}
