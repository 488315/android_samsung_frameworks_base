package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAttributeClipPath extends SprAttributeBase {
    public int link;

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 4;
    }

    public SprAttributeClipPath() {
        super((byte) 3);
        this.link = 0;
    }

    public SprAttributeClipPath(int i) {
        super((byte) 3);
        this.link = i;
    }

    public SprAttributeClipPath(SprInputStream sprInputStream) throws IOException {
        super((byte) 3);
        this.link = 0;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.link = sprInputStream.readInt();
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.link);
    }
}
