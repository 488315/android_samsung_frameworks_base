package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAttributeStrokeMiterlimit extends SprAttributeBase {
    public float miterLimit;

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 4;
    }

    public SprAttributeStrokeMiterlimit() {
        super((byte) 41);
        this.miterLimit = 0.0f;
    }

    public SprAttributeStrokeMiterlimit(SprInputStream sprInputStream) throws IOException {
        super((byte) 41);
        this.miterLimit = 0.0f;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.miterLimit = sprInputStream.readFloat();
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeFloat(this.miterLimit);
    }
}
