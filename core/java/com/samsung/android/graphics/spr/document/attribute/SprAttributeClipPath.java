package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAttributeClipPath extends SprAttributeBase {
    public int link;

    public SprAttributeClipPath() {
        super((byte) 3);
        this.link = 0;
    }

    public SprAttributeClipPath(int l) {
        super((byte) 3);
        this.link = 0;
        this.link = l;
    }

    public SprAttributeClipPath(SprInputStream in) throws IOException {
        super((byte) 3);
        this.link = 0;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.link = in.readInt();
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeInt(this.link);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 4;
    }
}
