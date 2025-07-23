package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public abstract class SprAttributeBase implements Cloneable {
    public static final byte TYPE_ANIMATOR_SET = 97;
    public static final byte TYPE_CLIP = 1;
    public static final byte TYPE_CLIP_PATH = 3;
    public static final byte TYPE_DURATION = 96;
    public static final byte TYPE_FILL = 32;
    public static final byte TYPE_MATRIX = 64;
    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_SHADOW = 112;
    public static final byte TYPE_STROKE = 35;
    public static final byte TYPE_STROKE_LINECAP = 37;
    public static final byte TYPE_STROKE_LINEJOIN = 38;
    public static final byte TYPE_STROKE_MITERLIMIT = 41;
    public static final byte TYPE_STROKE_WIDTH = 40;
    protected final SprAttributeBase mIntrinsic = this;
    public final byte mType;

    public abstract void fromSPR(SprInputStream sprInputStream) throws IOException;

    public int getSPRSize() {
        return 0;
    }

    public abstract void toSPR(DataOutputStream dataOutputStream) throws IOException;

    protected SprAttributeBase(byte b) {
        this.mType = b;
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SprAttributeBase mo9221clone() throws CloneNotSupportedException {
        return (SprAttributeBase) super.clone();
    }
}
