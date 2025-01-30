package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAttributeStrokeLinejoin extends SprAttributeBase {
  public byte linejoin;
  public static byte STROKE_LINEJOIN_TYPE_NONE = 0;
  public static byte STROKE_LINEJOIN_TYPE_MITER = 1;
  public static byte STROKE_LINEJOIN_TYPE_ROUND = 2;
  public static byte STROKE_LINEJOIN_TYPE_BEVEL = 3;

  public SprAttributeStrokeLinejoin() {
    super((byte) 38);
    this.linejoin = STROKE_LINEJOIN_TYPE_MITER;
  }

  public SprAttributeStrokeLinejoin(byte linejoin) {
    super((byte) 38);
    this.linejoin = STROKE_LINEJOIN_TYPE_MITER;
    this.linejoin = linejoin;
  }

  public SprAttributeStrokeLinejoin(SprInputStream in) throws IOException {
    super((byte) 38);
    this.linejoin = STROKE_LINEJOIN_TYPE_MITER;
    fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public void fromSPR(SprInputStream in) throws IOException {
    this.linejoin = in.readByte();
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public void toSPR(DataOutputStream out) throws IOException {
    out.writeByte(this.linejoin);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public int getSPRSize() {
    return 1;
  }
}
