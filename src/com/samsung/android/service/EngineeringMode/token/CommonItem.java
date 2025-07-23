package com.samsung.android.service.EngineeringMode.token;

/* loaded from: classes6.dex */
public class CommonItem {
    private byte[] mContents;
    private int mLen;
    private int mType;

    public CommonItem(int i, int i2, byte[] bArr) {
        this.mType = i;
        this.mLen = i2;
        this.mContents = bArr;
    }

    public int getType() {
        return this.mType;
    }

    public int getLen() {
        return this.mLen;
    }

    public byte[] getData() {
        return this.mContents;
    }
}
