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

    public SprFileAttributeNinePatch(SprInputStream sprInputStream) throws IOException {
        super((byte) 1);
        this.xSize = 0;
        this.xStart = null;
        this.xEnd = null;
        this.ySize = 0;
        this.yStart = null;
        this.yEnd = null;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        int readInt = sprInputStream.readInt();
        this.xSize = readInt;
        this.xStart = new float[readInt];
        this.xEnd = new float[readInt];
        for (int i = 0; i < this.xSize; i++) {
            this.xStart[i] = sprInputStream.readFloat();
            this.xEnd[i] = sprInputStream.readFloat();
        }
        int readInt2 = sprInputStream.readInt();
        this.ySize = readInt2;
        this.yStart = new float[readInt2];
        this.yEnd = new float[readInt2];
        for (int i2 = 0; i2 < this.ySize; i2++) {
            this.yStart[i2] = sprInputStream.readFloat();
            this.yEnd[i2] = sprInputStream.readFloat();
        }
    }

    @Override // com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.xSize);
        for (int i = 0; i < this.xSize; i++) {
            dataOutputStream.writeFloat(this.xStart[i]);
            dataOutputStream.writeFloat(this.xEnd[i]);
        }
        dataOutputStream.writeInt(this.ySize);
        for (int i2 = 0; i2 < this.ySize; i2++) {
            dataOutputStream.writeFloat(this.yStart[i2]);
            dataOutputStream.writeFloat(this.yEnd[i2]);
        }
    }

    @Override // com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeBase
    public int getSPRSize() {
        return (this.xSize * 8) + 8 + (this.ySize * 8);
    }

    @Override // com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeBase
    public boolean isValid() {
        return this.xSize * this.ySize >= 2;
    }
}
