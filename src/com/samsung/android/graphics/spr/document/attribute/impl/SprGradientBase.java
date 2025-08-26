package com.samsung.android.graphics.spr.document.attribute.impl;

import android.graphics.Matrix;
import android.graphics.Shader;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public abstract class SprGradientBase implements Cloneable {
    public static final byte SPREAD_TYPE_NONE = 0;
    public static final byte SPREAD_TYPE_PAD = 1;
    public static final byte SPREAD_TYPE_REFLECT = 2;
    public static final byte SPREAD_TYPE_REPEAT = 3;
    static final Shader.TileMode[] sTileModeArray = {Shader.TileMode.CLAMP, Shader.TileMode.CLAMP, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT};
    public int[] colors;
    public float[] positions;
    public byte spreadMode = 0;
    public Matrix matrix = null;
    public Shader shader = null;
    protected final SprGradientBase mIntrinsic = this;

    public abstract void updateGradient();

    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.spreadMode = sprInputStream.readByte();
        int[] iArr = new int[sprInputStream.readInt()];
        this.colors = iArr;
        this.positions = new float[iArr.length];
        for (int i = 0; i < this.colors.length; i++) {
            float f = sprInputStream.readFloat();
            this.colors[i] = sprInputStream.readInt() | (((int) (sprInputStream.readFloat() * 255.0f)) << 24);
            this.positions[i] = f;
        }
        byte b = sprInputStream.readByte();
        this.matrix = SprMatrix.fromSPR(sprInputStream);
        if (b == 0) {
            this.matrix = null;
        }
        updateGradient();
    }

    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(this.spreadMode);
        dataOutputStream.writeInt(this.colors.length);
        for (int i = 0; i < this.colors.length; i++) {
            dataOutputStream.writeFloat(this.positions[i]);
            dataOutputStream.writeInt(this.colors[i] & 16777215);
            dataOutputStream.writeFloat((this.colors[i] >> 24) / 255.0f);
        }
        dataOutputStream.writeByte(this.matrix != null ? 1 : 0);
        SprMatrix.toSPR(dataOutputStream, this.matrix);
    }

    public int getSPRSize() {
        return (this.colors.length * 12) + 30;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SprGradientBase m9234clone() throws CloneNotSupportedException {
        SprGradientBase sprGradientBase = (SprGradientBase) super.clone();
        sprGradientBase.colors = new int[this.colors.length];
        sprGradientBase.positions = new float[this.colors.length];
        int i = 0;
        while (true) {
            int[] iArr = this.colors;
            if (i < iArr.length) {
                sprGradientBase.colors[i] = iArr[i];
                sprGradientBase.positions[i] = this.positions[i];
                i++;
            } else {
                sprGradientBase.updateGradient();
                return sprGradientBase;
            }
        }
    }
}
