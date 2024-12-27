package com.samsung.android.graphics.spr.document.fileAttribute;

import com.samsung.android.graphics.spr.document.SprInputStream;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprFileAttributeNinePatch extends SprFileAttributeBase {
    public float[] xEnd;
    public int xSize;
    public float[] xStart;
    public float[] yEnd;
    public int ySize;
    public float[] yStart;

    public SprFileAttributeNinePatch() {
        super((byte) 1);
        this.xSize = 0;
        this.xStart = null;
        this.xEnd = null;
        this.ySize = 0;
        this.yStart = null;
        this.yEnd = null;
    }

    public SprFileAttributeNinePatch(SprInputStream in) throws IOException {
        super((byte) 1);
        this.xSize = 0;
        this.xStart = null;
        this.xEnd = null;
        this.ySize = 0;
        this.yStart = null;
        this.yEnd = null;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.xSize = in.readInt();
        this.xStart = new float[this.xSize];
        this.xEnd = new float[this.xSize];
        for (int cnt = 0; cnt < this.xSize; cnt++) {
            this.xStart[cnt] = in.readFloat();
            this.xEnd[cnt] = in.readFloat();
        }
        this.ySize = in.readInt();
        this.yStart = new float[this.ySize];
        this.yEnd = new float[this.ySize];
        for (int cnt2 = 0; cnt2 < this.ySize; cnt2++) {
            this.yStart[cnt2] = in.readFloat();
            this.yEnd[cnt2] = in.readFloat();
        }
    }

    @Override // com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeInt(this.xSize);
        for (int cnt = 0; cnt < this.xSize; cnt++) {
            out.writeFloat(this.xStart[cnt]);
            out.writeFloat(this.xEnd[cnt]);
        }
        out.writeInt(this.ySize);
        for (int cnt2 = 0; cnt2 < this.ySize; cnt2++) {
            out.writeFloat(this.yStart[cnt2]);
            out.writeFloat(this.yEnd[cnt2]);
        }
    }

    @Override // com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeBase
    public int getSPRSize() {
        return (this.xSize * 8) + 4 + 4 + (this.ySize * 8);
    }

    @Override // com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeBase
    public boolean isValid() {
        return this.xSize * this.ySize >= 2;
    }
}
