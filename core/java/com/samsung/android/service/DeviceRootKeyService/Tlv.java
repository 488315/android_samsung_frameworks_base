package com.samsung.android.service.DeviceRootKeyService;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Set;

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

    public void setTotalSize(int len) {
        this.mTotalLength = len;
    }

    public boolean setTlv(int tlvTag, byte[] tlvValue) {
        if (tlvTag >= 1 && tlvTag < 30) {
            this.mTlvList.put(Integer.valueOf(tlvTag), tlvValue);
            this.mTotalLength += tlvValue.length + 3;
            return true;
        }
        return false;
    }

    public boolean setTlvOnly(int tlvTag, byte[] tlvValue) {
        if (tlvTag >= 1 && tlvTag < 30) {
            this.mTlvList.put(Integer.valueOf(tlvTag), tlvValue);
            return true;
        }
        return false;
    }

    public byte[] getTlvValue(int tlvTag) {
        if (tlvTag >= 1 && tlvTag < 30) {
            return this.mTlvList.get(Integer.valueOf(tlvTag));
        }
        return null;
    }

    public byte[] encodeTlv() {
        byte[] result = new byte[this.mTotalLength + 3];
        result[0] = -2;
        int offset = 0 + 1;
        byte[] totLen = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.mTotalLength).array();
        System.arraycopy(totLen, 0, result, offset, totLen.length);
        int offset2 = offset + 2;
        Set<Integer> keys = this.mTlvList.keySet();
        for (Integer key : keys) {
            byte[] tlvValue = this.mTlvList.get(key);
            byte[] tlvLen = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) tlvValue.length).array();
            result[offset2] = key.byteValue();
            int offset3 = offset2 + 1;
            System.arraycopy(tlvLen, 0, result, offset3, tlvLen.length);
            int offset4 = offset3 + 2;
            System.arraycopy(tlvValue, 0, result, offset4, tlvValue.length);
            offset2 = offset4 + tlvValue.length;
        }
        return result;
    }

    public static Tlv decodeTlv(byte[] buffer, int offset, int length) {
        Tlv lcTlv = new Tlv();
        if (length < 3 || buffer[0] != -2) {
            return null;
        }
        int parsed = 0 + 1;
        short totLen = ByteBuffer.wrap(buffer, offset + parsed, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
        int parsed2 = parsed + 2;
        lcTlv.setTotalSize(totLen);
        while (parsed2 < length) {
            int tlvTag = Byte.toUnsignedInt(buffer[parsed2]);
            int parsed3 = parsed2 + 1;
            int i = ByteBuffer.wrap(buffer, offset + parsed3, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
            int parsed4 = parsed3 + 2;
            byte[] tlvValue = new byte[i];
            System.arraycopy(buffer, offset + parsed4, tlvValue, 0, i);
            parsed2 = parsed4 + i;
            lcTlv.setTlvOnly(tlvTag, tlvValue);
        }
        return lcTlv;
    }
}
