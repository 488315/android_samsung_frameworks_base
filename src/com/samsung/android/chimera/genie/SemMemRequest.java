package com.samsung.android.chimera.genie;

/* loaded from: classes6.dex */
public class SemMemRequest {
    public static final int MEMTYPE_CONTIGUOUS = 1;
    public static final int MEMTYPE_DEFAULT = 0;
    private final int mSize;
    private final int mType;

    public @interface MemType {
    }

    public SemMemRequest(int i, int i2) {
        this.mType = i;
        this.mSize = i2;
    }

    public int getSize() {
        return this.mSize;
    }

    public int getType() {
        return this.mType;
    }
}
