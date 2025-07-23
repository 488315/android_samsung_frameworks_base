package com.samsung.android.graphics.spr.document.fileAttribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public abstract class SprFileAttributeBase implements Cloneable {
    public static final byte TYPE_NINE_PATCH = 1;
    public static final byte TYPE_NONE = 0;
    protected final SprFileAttributeBase mIntrinsic = this;
    public final byte mType;

    public abstract void fromSPR(SprInputStream sprInputStream) throws IOException;

    public int getSPRSize() {
        return 0;
    }

    public boolean isValid() {
        return false;
    }

    public abstract void toSPR(DataOutputStream dataOutputStream) throws IOException;

    protected SprFileAttributeBase(byte b) {
        this.mType = b;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SprFileAttributeBase m9223clone() throws CloneNotSupportedException {
        return (SprFileAttributeBase) super.clone();
    }
}
