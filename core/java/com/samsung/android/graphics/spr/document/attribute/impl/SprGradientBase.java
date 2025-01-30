package com.samsung.android.graphics.spr.document.attribute.impl;

import android.graphics.Matrix;
import android.graphics.Shader;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
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

    public void fromSPR(SprInputStream in) throws IOException {
        this.spreadMode = in.readByte();
        int[] iArr = new int[in.readInt()];
        this.colors = iArr;
        this.positions = new float[iArr.length];
        for (int i = 0; i < this.colors.length; i++) {
            float offset = in.readFloat();
            int color = in.readInt();
            float opacity = in.readFloat();
            this.colors[i] = (((int) (255.0f * opacity)) << 24) | color;
            this.positions[i] = offset;
        }
        byte useMatrix = in.readByte();
        this.matrix = SprMatrix.fromSPR(in);
        if (useMatrix == 0) {
            this.matrix = null;
        }
        updateGradient();
    }

    public void toSPR(DataOutputStream out) throws IOException {
        out.writeByte(this.spreadMode);
        out.writeInt(this.colors.length);
        for (int i = 0; i < this.colors.length; i++) {
            out.writeFloat(this.positions[i]);
            out.writeInt(this.colors[i] & 16777215);
            out.writeFloat((this.colors[i] >> 24) / 255.0f);
        }
        out.writeByte(this.matrix == null ? 0 : 1);
        SprMatrix.toSPR(out, this.matrix);
    }

    public int getSPRSize() {
        return (this.colors.length * 12) + 30;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SprGradientBase m8904clone() throws CloneNotSupportedException {
        SprGradientBase attribute = (SprGradientBase) super.clone();
        attribute.colors = new int[this.colors.length];
        attribute.positions = new float[this.colors.length];
        int i = 0;
        while (true) {
            int[] iArr = this.colors;
            if (i < iArr.length) {
                attribute.colors[i] = iArr[i];
                attribute.positions[i] = this.positions[i];
                i++;
            } else {
                attribute.updateGradient();
                return attribute;
            }
        }
    }
}
