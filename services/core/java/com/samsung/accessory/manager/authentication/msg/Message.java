package com.samsung.accessory.manager.authentication.msg;

/* loaded from: classes.dex */
public class Message {
    public final int INF_SIZE;
    public final int RES_ATQS;
    public byte[] apdu;
    public byte[] data;
    public byte[] inf;

    public Message(int i, byte[] bArr) {
        this.INF_SIZE = 5;
        this.RES_ATQS = 1;
        byte[] bArr2 = new byte[bArr.length];
        this.apdu = bArr2;
        this.inf = new byte[5];
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        parseMessage(i);
    }

    public Message(byte b, byte b2, byte b3, byte b4, byte b5) {
        this.INF_SIZE = 5;
        this.RES_ATQS = 1;
        this.apdu = new byte[5];
        this.inf = new byte[5];
        setInf(b, b2, b3, b4, b5);
        this.data = null;
        setApdu();
    }

    public Message(byte b, byte b2, byte b3, byte b4, byte b5, byte[] bArr) {
        this.INF_SIZE = 5;
        this.RES_ATQS = 1;
        this.apdu = new byte[bArr.length + 5];
        this.inf = new byte[5];
        setInf(b, b2, b3, b4, b5);
        this.data = (byte[]) bArr.clone();
        setApdu();
    }

    public final void setInf(byte b, byte b2, byte b3, byte b4, byte b5) {
        byte[] bArr = this.inf;
        bArr[0] = b;
        bArr[1] = b2;
        bArr[2] = b3;
        bArr[3] = b4;
        bArr[4] = b5;
    }

    public final void setApdu() {
        byte[] bArr = this.inf;
        System.arraycopy(bArr, 0, this.apdu, 0, bArr.length);
        byte[] bArr2 = this.data;
        if (bArr2 != null) {
            System.arraycopy(bArr2, 0, this.apdu, this.inf.length, bArr2.length);
        }
    }

    public final void parseMessage(int i) {
        byte[] bArr = this.apdu;
        int length = bArr.length;
        if (i == 1) {
            int i2 = length - 2;
            byte[] bArr2 = new byte[i2];
            this.data = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            return;
        }
        int i3 = length - 3;
        byte[] bArr3 = new byte[i3];
        this.data = bArr3;
        System.arraycopy(bArr, 0, bArr3, 0, i3);
    }

    public byte[] getApdu() {
        return (byte[]) this.apdu.clone();
    }

    public byte[] getData() {
        return (byte[]) this.data.clone();
    }
}
