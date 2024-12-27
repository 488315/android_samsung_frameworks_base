package com.samsung.android.graphics.spr.document.fileAttribute;

import com.samsung.android.graphics.spr.document.SprInputStream;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class SprFileAttributeBase implements Cloneable {
    public static final byte TYPE_NINE_PATCH = 1;
    public static final byte TYPE_NONE = 0;
    protected final SprFileAttributeBase mIntrinsic = this;
    public final byte mType;

    public abstract void fromSPR(SprInputStream sprInputStream) throws IOException;

    public abstract void toSPR(DataOutputStream dataOutputStream) throws IOException;

    protected SprFileAttributeBase(byte type) {
        this.mType = type;
    }

    public int getSPRSize() {
        return 0;
    }

    public boolean isValid() {
        return false;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SprFileAttributeBase m8815clone() throws CloneNotSupportedException {
        return (SprFileAttributeBase) super.clone();
    }
}
