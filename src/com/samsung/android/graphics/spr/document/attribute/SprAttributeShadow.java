package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAttributeShadow extends SprAttributeBase {
    public float dx;
    public float dy;
    public float radius;
    public int shadowColor;

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 16;
    }

    public SprAttributeShadow() {
        super(SprAttributeBase.TYPE_SHADOW);
        this.dy = 0.0f;
        this.dx = 0.0f;
        this.radius = 0.0f;
        this.shadowColor = 0;
    }

    public SprAttributeShadow(float f, float f2, float f3, int i) {
        super(SprAttributeBase.TYPE_SHADOW);
        this.radius = f;
        this.dx = f2;
        this.dy = f3;
        this.shadowColor = i;
    }

    public SprAttributeShadow(SprInputStream sprInputStream) throws IOException {
        super(SprAttributeBase.TYPE_SHADOW);
        this.radius = 0.0f;
        this.dx = 0.0f;
        this.dy = 0.0f;
        this.shadowColor = 0;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.radius = sprInputStream.readFloat();
        this.dx = sprInputStream.readFloat();
        this.dy = sprInputStream.readFloat();
        this.shadowColor = sprInputStream.readInt();
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeFloat(this.radius);
        dataOutputStream.writeFloat(this.dx);
        dataOutputStream.writeFloat(this.dy);
        dataOutputStream.writeInt(this.shadowColor);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    /* renamed from: clone */
    public SprAttributeShadow mo9233clone() throws CloneNotSupportedException {
        SprAttributeShadow sprAttributeShadow = (SprAttributeShadow) super.mo9233clone();
        sprAttributeShadow.radius = this.radius;
        sprAttributeShadow.dx = this.dx;
        sprAttributeShadow.dy = this.dy;
        sprAttributeShadow.shadowColor = this.shadowColor;
        return sprAttributeShadow;
    }
}
