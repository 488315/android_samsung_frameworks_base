package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAttributeStrokeLinejoin extends SprAttributeBase {
    public static byte STROKE_LINEJOIN_TYPE_BEVEL = 3;
    public static byte STROKE_LINEJOIN_TYPE_MITER = 1;
    public static byte STROKE_LINEJOIN_TYPE_NONE = 0;
    public static byte STROKE_LINEJOIN_TYPE_ROUND = 2;
    public byte linejoin;

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public int getSPRSize() {
        return 1;
    }

    public SprAttributeStrokeLinejoin() {
        super((byte) 38);
        this.linejoin = STROKE_LINEJOIN_TYPE_MITER;
    }

    public SprAttributeStrokeLinejoin(byte b) {
        super((byte) 38);
        this.linejoin = b;
    }

    public SprAttributeStrokeLinejoin(SprInputStream sprInputStream) throws IOException {
        super((byte) 38);
        this.linejoin = STROKE_LINEJOIN_TYPE_MITER;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.linejoin = sprInputStream.readByte();
    }

    @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(this.linejoin);
    }
}
