package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAttributeStrokeWidth extends SprAttributeBase {
    public float strokeWidth;

    public SprAttributeStrokeWidth() {
        super((byte) 40);
        this.strokeWidth = 0.0f;
    }

    public SprAttributeStrokeWidth(float width) {
        super((byte) 40);
        this.strokeWidth = 0.0f;
        this.strokeWidth = width;
    }

    public SprAttributeStrokeWidth(SprInputStream in) throws IOException {
        super((byte) 40);
        this.strokeWidth = 0.0f;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream in) throws IOException {
        float readFloat = in.readFloat();
        this.strokeWidth = readFloat;
        if (readFloat > 0.0f && readFloat < 0.3f) {
            this.strokeWidth = 0.3f;
        }
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeFloat(this.strokeWidth);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 4;
    }
}
