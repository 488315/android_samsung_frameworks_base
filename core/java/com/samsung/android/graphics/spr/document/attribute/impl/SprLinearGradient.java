package com.samsung.android.graphics.spr.document.attribute.impl;

import android.graphics.LinearGradient;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprLinearGradient extends SprGradientBase {

  /* renamed from: x1 */
  public float f2977x1;

  /* renamed from: x2 */
  public float f2978x2;

  /* renamed from: y1 */
  public float f2979y1;

  /* renamed from: y2 */
  public float f2980y2;

  public SprLinearGradient() {}

  public SprLinearGradient(SprInputStream in) throws IOException {
    fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
  public void fromSPR(SprInputStream in) throws IOException {
    this.f2977x1 = in.readFloat();
    this.f2979y1 = in.readFloat();
    this.f2978x2 = in.readFloat();
    this.f2980y2 = in.readFloat();
    super.fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
  public void toSPR(DataOutputStream out) throws IOException {
    out.writeFloat(this.f2977x1);
    out.writeFloat(this.f2979y1);
    out.writeFloat(this.f2978x2);
    out.writeFloat(this.f2980y2);
    super.toSPR(out);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
  public int getSPRSize() {
    return super.getSPRSize() + 16;
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
  public void updateGradient() {
    this.shader =
        new LinearGradient(
            this.f2977x1,
            this.f2979y1,
            this.f2978x2,
            this.f2980y2,
            this.colors,
            this.positions,
            sTileModeArray[this.spreadMode - 1]);
    if (this.matrix != null) {
      this.shader.setLocalMatrix(this.matrix);
    }
  }
}
