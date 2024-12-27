package com.samsung.android.service.EngineeringMode.token;

public class AttributeInfo {
    private byte[] mAttribute;
    private int mLen;
    private int mType;

    public AttributeInfo(int mType, int mLen, byte[] mAttribute) {
        this.mType = mType;
        this.mLen = mLen;
        this.mAttribute = mAttribute;
    }

    public int getType() {
        return this.mType;
    }

    public int getLen() {
        return this.mLen;
    }

    public byte[] getData() {
        return this.mAttribute;
    }
}
