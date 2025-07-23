package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAttributeStrokeLinecap extends SprAttributeBase {
    public static byte STROKE_LINECAP_TYPE_BUTT = 1;
    public static byte STROKE_LINECAP_TYPE_NONE = 0;
    public static byte STROKE_LINECAP_TYPE_ROUND = 2;
    public static byte STROKE_LINECAP_TYPE_SQUARE = 3;
    public byte linecap;

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 1;
    }

    public SprAttributeStrokeLinecap() {
        super((byte) 37);
        this.linecap = STROKE_LINECAP_TYPE_BUTT;
    }

    public SprAttributeStrokeLinecap(SprInputStream sprInputStream) throws IOException {
        super((byte) 37);
        this.linecap = STROKE_LINECAP_TYPE_BUTT;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.linecap = sprInputStream.readByte();
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(this.linecap);
    }
}
