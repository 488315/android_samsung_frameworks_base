package com.samsung.accessory.manager.authentication.msg;

public final class Message {
    public final byte[] apdu;
    public final byte[] data;
    public final byte[] inf;

    public Message(byte b, byte b2, byte b3, byte b4) {
        byte[] bArr = new byte[5];
        this.apdu = bArr;
        byte[] bArr2 = {b, b2, b3, 0, b4};
        this.data = null;
        System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
        byte[] bArr3 = this.data;
        if (bArr3 != null) {
            System.arraycopy(bArr3, 0, bArr, bArr2.length, bArr3.length);
        }
    }

    public Message(int i, byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        this.apdu = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, length);
        if (i == 1) {
            int i2 = length - 2;
            byte[] bArr3 = new byte[i2];
            this.data = bArr3;
            System.arraycopy(bArr2, 0, bArr3, 0, i2);
            return;
        }
        int i3 = length - 3;
        byte[] bArr4 = new byte[i3];
        this.data = bArr4;
        System.arraycopy(bArr2, 0, bArr4, 0, i3);
    }

    public final byte[] getApdu() {
        return (byte[]) this.apdu.clone();
    }

    public final byte[] getData() {
        return (byte[]) this.data.clone();
    }
}
