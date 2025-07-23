package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase;
import com.samsung.android.graphics.spr.document.attribute.impl.SprLinearGradient;
import com.samsung.android.graphics.spr.document.attribute.impl.SprRadialGradient;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public abstract class SprAttributeColor extends SprAttributeBase {
    public static final byte TYPE_ARGB = 1;
    public static final byte TYPE_LINEAR_GRADIENT = 3;
    public static final byte TYPE_LINK = 2;
    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_RADIAL_GRADIENT = 4;
    public int color;
    public byte colorType;
    public SprGradientBase gradient;

    public SprAttributeColor(byte b) {
        super(b);
        this.gradient = null;
        this.colorType = (byte) 1;
        this.color = 0;
    }

    public SprAttributeColor(byte b, byte b2, int i) {
        super(b);
        this.gradient = null;
        this.colorType = b2;
        if (b2 != 0) {
            if (b2 == 1 || b2 == 2) {
                this.color = i;
            } else {
                throw new RuntimeException("unexpected stroke type:" + ((int) b2));
            }
        }
    }

    public SprAttributeColor(byte b, byte b2, SprGradientBase sprGradientBase) {
        super(b);
        this.gradient = null;
        this.colorType = b2;
        if (b2 != 0) {
            if (b2 == 3 || b2 == 4) {
                this.gradient = sprGradientBase;
            } else {
                throw new RuntimeException("unexpected stroke type:" + ((int) b2));
            }
        }
    }

    public SprAttributeColor(byte b, SprInputStream sprInputStream) throws IOException {
        super(b);
        this.colorType = (byte) 1;
        this.gradient = null;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        byte readByte = sprInputStream.readByte();
        this.colorType = readByte;
        if (readByte == 0) {
            sprInputStream.readInt();
            return;
        }
        if (readByte == 1 || readByte == 2) {
            this.color = sprInputStream.readInt();
            return;
        }
        if (readByte == 3) {
            this.gradient = new SprLinearGradient(sprInputStream);
        } else if (readByte == 4) {
            this.gradient = new SprRadialGradient(sprInputStream);
        } else {
            throw new RuntimeException("unknown fill type:" + ((int) this.colorType));
        }
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(this.colorType);
        byte b = this.colorType;
        if (b == 0) {
            dataOutputStream.writeInt(0);
            return;
        }
        if (b == 1 || b == 2) {
            dataOutputStream.writeInt(this.color);
        } else if (b == 3 || b == 4) {
            this.gradient.toSPR(dataOutputStream);
        } else {
            throw new RuntimeException("unknown fill type:" + ((int) this.colorType));
        }
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        byte b = this.colorType;
        if (b == 0) {
            return 0;
        }
        if (b == 1 || b == 2) {
            return 5;
        }
        if (b == 3 || b == 4) {
            return this.gradient.getSPRSize() + 1;
        }
        throw new RuntimeException("unknown fill type:" + ((int) this.colorType));
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    /* renamed from: clone */
    public SprAttributeColor mo9221clone() throws CloneNotSupportedException {
        SprAttributeColor sprAttributeColor = (SprAttributeColor) super.mo9221clone();
        SprGradientBase sprGradientBase = this.gradient;
        if (sprGradientBase != null) {
            sprAttributeColor.gradient = sprGradientBase.m9222clone();
        }
        return sprAttributeColor;
    }
}
