package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;

import java.io.DataOutputStream;
import java.io.IOException;

public class SprAttributeShadow extends SprAttributeBase {
    public float dx;
    public float dy;
    public float radius;
    public int shadowColor;

    public SprAttributeShadow() {
        super(SprAttributeBase.TYPE_SHADOW);
        this.radius = 0.0f;
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.shadowColor = 0;
        this.dy = 0.0f;
        this.dx = 0.0f;
        this.radius = 0.0f;
        this.shadowColor = 0;
    }

    public SprAttributeShadow(float radius, float dx, float dy, int color) {
        super(SprAttributeBase.TYPE_SHADOW);
        this.radius = 0.0f;
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.shadowColor = 0;
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
        this.shadowColor = color;
    }

    public SprAttributeShadow(SprInputStream in) throws IOException {
        super(SprAttributeBase.TYPE_SHADOW);
        this.radius = 0.0f;
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.shadowColor = 0;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.radius = in.readFloat();
        this.dx = in.readFloat();
        this.dy = in.readFloat();
        this.shadowColor = in.readInt();
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeFloat(this.radius);
        out.writeFloat(this.dx);
        out.writeFloat(this.dy);
        out.writeInt(this.shadowColor);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 16;
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    /* renamed from: clone */
    public SprAttributeShadow mo8813clone() throws CloneNotSupportedException {
        SprAttributeShadow attribute = (SprAttributeShadow) super.mo8813clone();
        attribute.radius = this.radius;
        attribute.dx = this.dx;
        attribute.dy = this.dy;
        attribute.shadowColor = this.shadowColor;
        return attribute;
    }
}
