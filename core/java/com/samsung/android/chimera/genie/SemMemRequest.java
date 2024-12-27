package com.samsung.android.chimera.genie;

public class SemMemRequest {
    public static final int MEMTYPE_CONTIGUOUS = 1;
    public static final int MEMTYPE_DEFAULT = 0;
    private final int mSize;
    private final int mType;

    public @interface MemType {}

    public SemMemRequest(int type, int size) {
        this.mType = type;
        this.mSize = size;
    }

    public int getSize() {
        return this.mSize;
    }

    public int getType() {
        return this.mType;
    }
}
