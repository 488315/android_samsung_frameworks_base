package com.samsung.accessory.manager.authentication.cover;

import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;

public final class CoverInfo {
    public final String[] HexDecimalTable = {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
        "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z", "I", "O", "U"
    };
    public final byte[] chip_id;
    public final int color;
    public final String id;
    public final int model;
    public final byte reserved;
    public final String serial;
    public final int smapp;
    public int type;
    public final int url;
    public final boolean valid;
    public final int year;

    public CoverInfo(byte[] bArr) {
        this.chip_id = new byte[23];
        byte[] bArr2 = new byte[14];
        byte[] bArr3 = new byte[9];
        this.valid = false;
        if (bArr == null || bArr.length != 23) {
            return;
        }
        byte[] bArr4 = (byte[]) bArr.clone();
        this.chip_id = bArr4;
        System.arraycopy(bArr4, 0, bArr2, 0, 14);
        System.arraycopy(bArr4, 14, bArr3, 0, 9);
        this.serial = convertHexStringTo33Hexdecimal(byteArrayToString(bArr2));
        this.id = convertHexStringTo33Hexdecimal(byteArrayToString(bArr3));
        this.year = bArr4[3] & 255;
        byte b = bArr4[4];
        this.model = bArr4[14] & 255;
        this.smapp = bArr4[15] & 255;
        this.color = bArr4[16] & 255;
        this.type = bArr4[17] & 255;
        byte b2 = bArr4[18];
        byte b3 = bArr4[19];
        byte b4 = bArr4[20];
        this.url = bArr4[21] & 255;
        this.reserved = bArr4[22];
        this.valid = true;
    }

    public static String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < bArr.length) {
            i =
                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                            "%02x", new Object[] {Byte.valueOf(bArr[i])}, sb, i, 1);
        }
        return sb.toString();
    }

    public final String convertHexStringTo33Hexdecimal(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 2;
            try {
                int parseInt = Integer.parseInt(str.substring(i, i2), 16);
                if (parseInt == 255) {
                    sb.append("0");
                } else {
                    if (parseInt >= 0) {
                        String[] strArr = this.HexDecimalTable;
                        if (parseInt < strArr.length) {
                            sb.append(strArr[parseInt]);
                        }
                    }
                    sb.append(" ");
                }
            } catch (NumberFormatException unused) {
            }
            i = i2;
        }
        return sb.toString();
    }
}
