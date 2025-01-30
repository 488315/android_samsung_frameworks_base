package com.samsung.accessory.manager.authentication.cover;

/* loaded from: classes.dex */
public class CoverInfo {
    public byte[] chip_id;
    public int color;

    /* renamed from: id */
    public String f1767id;
    public int idVersion;
    public int model;
    public int month;
    public byte reserved;
    public String serial;
    public int smapp;
    public int type;
    public int url;
    public boolean valid;
    public int year;

    /* renamed from: sn */
    public byte[] f1768sn = new byte[14];
    public byte[] cover_id = new byte[9];
    public byte[] vendorInfo = new byte[2];
    public String[] HexDecimalTable = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z", "I", "O", "U"};

    public CoverInfo(byte[] bArr) {
        this.chip_id = new byte[23];
        this.valid = false;
        if (bArr == null || bArr.length != 23) {
            return;
        }
        this.chip_id = (byte[]) bArr.clone();
        setId();
        this.valid = true;
    }

    public final void setId() {
        System.arraycopy(this.chip_id, 0, this.f1768sn, 0, 14);
        System.arraycopy(this.chip_id, 14, this.cover_id, 0, 9);
        this.serial = convertHexStringTo33Hexdecimal(byteArrayToString(this.f1768sn));
        this.f1767id = convertHexStringTo33Hexdecimal(byteArrayToString(this.cover_id));
        byte[] bArr = this.chip_id;
        this.year = bArr[3] & 255;
        this.month = bArr[4] & 255;
        this.model = bArr[14] & 255;
        this.smapp = bArr[15] & 255;
        this.color = bArr[16] & 255;
        this.type = bArr[17] & 255;
        this.idVersion = bArr[18] & 255;
        byte[] bArr2 = this.vendorInfo;
        bArr2[0] = bArr[19];
        bArr2[1] = bArr[20];
        this.url = bArr[21] & 255;
        this.reserved = bArr[22];
    }

    public boolean isValid() {
        return this.valid;
    }

    public byte[] getId() {
        return this.chip_id;
    }

    public int getYear() {
        return this.year;
    }

    public String getSn() {
        return this.serial;
    }

    public String getCoverId() {
        return this.f1767id;
    }

    public int getModel() {
        return this.model;
    }

    public int getSmapp() {
        return this.smapp;
    }

    public int getColor() {
        return this.color;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getUrl() {
        return this.url;
    }

    public byte getReserved() {
        return this.reserved;
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

    public String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }
}
