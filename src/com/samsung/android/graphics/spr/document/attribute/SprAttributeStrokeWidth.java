package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAttributeStrokeWidth extends SprAttributeBase {
    public float strokeWidth;

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 4;
    }

    public SprAttributeStrokeWidth() {
        super((byte) 40);
        this.strokeWidth = 0.0f;
    }

    public SprAttributeStrokeWidth(float f) {
        super((byte) 40);
        this.strokeWidth = f;
    }

    public SprAttributeStrokeWidth(SprInputStream sprInputStream) throws IOException {
        super((byte) 40);
        this.strokeWidth = 0.0f;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        float f = sprInputStream.readFloat();
        this.strokeWidth = f;
        if (f <= 0.0f || f >= 0.3f) {
            return;
        }
        this.strokeWidth = 0.3f;
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeFloat(this.strokeWidth);
    }
}
