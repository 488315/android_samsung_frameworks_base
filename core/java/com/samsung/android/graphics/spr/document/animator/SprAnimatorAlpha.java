package com.samsung.android.graphics.spr.document.animator;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAnimatorAlpha extends SprAnimatorBase {
  private float from;

  /* renamed from: to */
  private float f2971to;

  public SprAnimatorAlpha() {
    super((byte) 6);
    this.from = 0.0f;
    this.f2971to = 0.0f;
  }

  public SprAnimatorAlpha(SprInputStream in) throws IOException {
    super((byte) 6);
    this.from = 0.0f;
    this.f2971to = 0.0f;
    fromSPR(in);
    init();
  }

  private void init() {
    float f = this.from;
    if (f < 0.0f) {
      f = 0.0f;
    }
    this.from = f;
    if (f > 1.0f) {
      f = 1.0f;
    }
    this.from = f;
    float f2 = this.f2971to;
    float f3 = f2 >= 0.0f ? f2 : 0.0f;
    this.f2971to = f3;
    float f4 = f3 <= 1.0f ? f3 : 1.0f;
    this.f2971to = f4;
    setFloatValues(f, f4);
  }

  @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
  public void fromSPR(SprInputStream in) throws IOException {
    super.fromSPR(in);
    this.from = in.readFloat();
    this.f2971to = in.readFloat();
  }

  @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
  public void toSPR(DataOutputStream out) throws IOException {
    super.toSPR(out);
    float f = this.from;
    if (f < 0.0f) {
      f = 0.0f;
    }
    this.from = f;
    if (f > 1.0f) {
      f = 1.0f;
    }
    this.from = f;
    float f2 = this.f2971to;
    float f3 = f2 >= 0.0f ? f2 : 0.0f;
    this.f2971to = f3;
    this.f2971to = f3 <= 1.0f ? f3 : 1.0f;
    out.writeFloat(f);
    out.writeFloat(this.f2971to);
  }

  @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
  public int getSPRSize() {
    return super.getSPRSize() + 8;
  }

  @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
  public boolean updateValues(SprAnimatorBase.UpdateParameter parameter) {
    if (parameter.isLastFrame) {
      parameter.alpha = this.f2971to;
      return false;
    }
    parameter.alpha = ((Float) getAnimatedValue()).floatValue();
    return false;
  }

  public void set(float from, float to) {
    this.from = from;
    this.f2971to = to;
    init();
  }
}
