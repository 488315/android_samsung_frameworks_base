package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAttributeStrokeMiterlimit extends SprAttributeBase {
    public float miterLimit;

    public SprAttributeStrokeMiterlimit() {
        super((byte) 41);
        this.miterLimit = 0.0f;
    }

    public SprAttributeStrokeMiterlimit(SprInputStream in) throws IOException {
        super((byte) 41);
        this.miterLimit = 0.0f;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.miterLimit = in.readFloat();
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeFloat(this.miterLimit);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 4;
    }
}
