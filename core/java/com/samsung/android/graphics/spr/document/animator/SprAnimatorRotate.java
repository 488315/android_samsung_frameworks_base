package com.samsung.android.graphics.spr.document.animator;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAnimatorRotate extends SprAnimatorBase {
  private float from;
  private float pivotX;
  private float pivotY;

  /* renamed from: to */
  private float f2973to;

  public SprAnimatorRotate() {
    super((byte) 3);
  }

  public SprAnimatorRotate(SprInputStream in) throws IOException {
    super((byte) 3);
    fromSPR(in);
    init();
  }

  private void init() {
    setFloatValues(this.from, this.f2973to);
  }

  @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
  public void fromSPR(SprInputStream in) throws IOException {
    super.fromSPR(in);
    this.pivotX = in.readFloat();
    this.pivotY = in.readFloat();
    this.from = in.readFloat();
    this.f2973to = in.readFloat();
  }

  @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
  public void toSPR(DataOutputStream out) throws IOException {
    super.toSPR(out);
    out.writeFloat(this.pivotX);
    out.writeFloat(this.pivotY);
    out.writeFloat(this.from);
    out.writeFloat(this.f2973to);
  }

  @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
  public int getSPRSize() {
    return super.getSPRSize() + 16;
  }

  @Override // com.samsung.android.graphics.spr.document.animator.SprAnimatorBase
  public boolean updateValues(SprAnimatorBase.UpdateParameter parameter) {
    parameter.isUpdatedRotate = true;
    parameter.rotatePivotX = this.pivotX;
    parameter.rotatePivotY = this.pivotY;
    if (parameter.isLastFrame) {
      parameter.rotateDegree = this.f2973to;
      return false;
    }
    parameter.rotateDegree = ((Float) getAnimatedValue()).floatValue();
    return false;
  }

  public void set(float from, float to, float pivotX, float pivotY) {
    this.from = from;
    this.f2973to = to;
    this.pivotX = pivotX;
    this.pivotY = pivotY;
    init();
  }
}
