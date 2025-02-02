package com.samsung.android.graphics.spr.document.attribute.impl;

import android.graphics.RadialGradient;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprRadialGradient extends SprGradientBase {

  /* renamed from: cx */
  public float f2981cx;

  /* renamed from: cy */
  public float f2982cy;

  /* renamed from: r */
  public float f2983r;

  public SprRadialGradient() {}

  public SprRadialGradient(SprInputStream in) throws IOException {
    fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
  public void fromSPR(SprInputStream in) throws IOException {
    this.f2981cx = in.readFloat();
    this.f2982cy = in.readFloat();
    this.f2983r = in.readFloat();
    super.fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
  public void toSPR(DataOutputStream out) throws IOException {
    out.writeFloat(this.f2981cx);
    out.writeFloat(this.f2982cy);
    out.writeFloat(this.f2983r);
    super.toSPR(out);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
  public int getSPRSize() {
    return super.getSPRSize() + 12;
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase
  public void updateGradient() {
    int size = this.positions.length;
    if (this.positions[size - 1] != 1.0f) {
      size++;
    }
    if (this.positions[0] != 0.0f) {
      size++;
    }
    int[] lcolors = this.colors;
    float[] lpositions = this.positions;
    if (size != this.positions.length) {
      lcolors = new int[size];
      lpositions = new float[size];
      int index = 0;
      if (this.positions[0] != 0.0f) {
        lcolors[0] = this.colors[0];
        lpositions[0] = 0.0f;
        index = 0 + 1;
      }
      int cnt = 0;
      while (cnt < this.colors.length) {
        lcolors[index] = this.colors[cnt];
        lpositions[index] = this.positions[cnt];
        cnt++;
        index++;
      }
      if (this.positions[this.positions.length - 1] != 1.0f) {
        lcolors[size - 1] = this.colors[this.positions.length - 1];
        lpositions[size - 1] = 1.0f;
      }
    }
    this.shader =
        new RadialGradient(
            this.f2981cx,
            this.f2982cy,
            this.f2983r,
            lcolors,
            lpositions,
            sTileModeArray[this.spreadMode]);
    if (this.matrix != null) {
      this.shader.setLocalMatrix(this.matrix);
    }
  }
}
