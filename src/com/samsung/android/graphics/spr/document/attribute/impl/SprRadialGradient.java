package com.samsung.android.graphics.spr.document.attribute.impl;

import android.graphics.RadialGradient;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprRadialGradient extends SprGradientBase {
    public float cx;
    public float cy;
    public float r;

    public SprRadialGradient() {
    }

    public SprRadialGradient(SprInputStream sprInputStream) throws IOException {
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.cx = sprInputStream.readFloat();
        this.cy = sprInputStream.readFloat();
        this.r = sprInputStream.readFloat();
        super.fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeFloat(this.cx);
        dataOutputStream.writeFloat(this.cy);
        dataOutputStream.writeFloat(this.r);
        super.toSPR(dataOutputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
    public int getSPRSize() {
        return super.getSPRSize() + 12;
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
    public void updateGradient() {
        int i;
        int length = this.positions.length;
        if (this.positions[length - 1] != 1.0f) {
            length++;
        }
        int i2 = 0;
        if (this.positions[0] != 0.0f) {
            length++;
        }
        int[] iArr = this.colors;
        float[] fArr = this.positions;
        if (length != this.positions.length) {
            iArr = new int[length];
            fArr = new float[length];
            if (this.positions[0] != 0.0f) {
                iArr[0] = this.colors[0];
                fArr[0] = 0.0f;
                i = 1;
            } else {
                i = 0;
            }
            while (i2 < this.colors.length) {
                iArr[i] = this.colors[i2];
                fArr[i] = this.positions[i2];
                i2++;
                i++;
            }
            if (this.positions[this.positions.length - 1] != 1.0f) {
                int i3 = length - 1;
                iArr[i3] = this.colors[this.positions.length - 1];
                fArr[i3] = 1.0f;
            }
        }
        this.shader = new RadialGradient(this.cx, this.cy, this.r, iArr, fArr, sTileModeArray[this.spreadMode]);
        if (this.matrix != null) {
            this.shader.setLocalMatrix(this.matrix);
        }
    }
}
