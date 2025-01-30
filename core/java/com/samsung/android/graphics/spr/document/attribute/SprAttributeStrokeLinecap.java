package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAttributeStrokeLinecap extends SprAttributeBase {
    public byte linecap;
    public static byte STROKE_LINECAP_TYPE_NONE = 0;
    public static byte STROKE_LINECAP_TYPE_BUTT = 1;
    public static byte STROKE_LINECAP_TYPE_ROUND = 2;
    public static byte STROKE_LINECAP_TYPE_SQUARE = 3;

    public SprAttributeStrokeLinecap() {
        super((byte) 37);
        this.linecap = STROKE_LINECAP_TYPE_BUTT;
    }

    public SprAttributeStrokeLinecap(SprInputStream in) throws IOException {
        super((byte) 37);
        this.linecap = STROKE_LINECAP_TYPE_BUTT;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.linecap = in.readByte();
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeByte(this.linecap);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 1;
    }
}
