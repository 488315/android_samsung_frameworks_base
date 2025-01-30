package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAttributeClip extends SprAttributeBase {
  public float bottom;
  public float left;
  public float right;
  public float top;

  public SprAttributeClip() {
    super((byte) 1);
    this.left = 0.0f;
    this.top = 0.0f;
    this.right = 0.0f;
    this.bottom = 0.0f;
  }

  public SprAttributeClip(SprInputStream in) throws IOException {
    super((byte) 1);
    this.left = 0.0f;
    this.top = 0.0f;
    this.right = 0.0f;
    this.bottom = 0.0f;
    fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public void fromSPR(SprInputStream in) throws IOException {
    this.left = in.readFloat();
    this.top = in.readFloat();
    this.right = in.readFloat();
    this.bottom = in.readFloat();
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public void toSPR(DataOutputStream out) throws IOException {
    out.writeFloat(this.left);
    out.writeFloat(this.top);
    out.writeFloat(this.right);
    out.writeFloat(this.bottom);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public int getSPRSize() {
    return 16;
  }
}
