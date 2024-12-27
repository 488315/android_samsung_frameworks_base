package com.samsung.android.graphics.spr.document.attribute.impl;

import android.graphics.LinearGradient;

import com.samsung.android.graphics.spr.document.SprInputStream;

import java.io.DataOutputStream;
import java.io.IOException;

public class SprLinearGradient extends SprGradientBase {
    public float x1;
    public float x2;
    public float y1;
    public float y2;

    public SprLinearGradient() {}

    public SprLinearGradient(SprInputStream in) throws IOException {
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.x1 = in.readFloat();
        this.y1 = in.readFloat();
        this.x2 = in.readFloat();
        this.y2 = in.readFloat();
        super.fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeFloat(this.x1);
        out.writeFloat(this.y1);
        out.writeFloat(this.x2);
        out.writeFloat(this.y2);
        super.toSPR(out);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
    public int getSPRSize() {
        return super.getSPRSize() + 16;
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
    public void updateGradient() {
        this.shader =
                new LinearGradient(
                        this.x1,
                        this.y1,
                        this.x2,
                        this.y2,
                        this.colors,
                        this.positions,
                        sTileModeArray[this.spreadMode - 1]);
        if (this.matrix != null) {
            this.shader.setLocalMatrix(this.matrix);
        }
    }
}
