package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAttributeShadow extends SprAttributeBase {

  /* renamed from: dx */
  public float f2975dx;

  /* renamed from: dy */
  public float f2976dy;
  public float radius;
  public int shadowColor;

  public SprAttributeShadow() {
    super(SprAttributeBase.TYPE_SHADOW);
    this.radius = 0.0f;
    this.f2975dx = 0.0f;
    this.f2976dy = 0.0f;
    this.shadowColor = 0;
    this.f2976dy = 0.0f;
    this.f2975dx = 0.0f;
    this.radius = 0.0f;
    this.shadowColor = 0;
  }

  public SprAttributeShadow(float radius, float dx, float dy, int color) {
    super(SprAttributeBase.TYPE_SHADOW);
    this.radius = 0.0f;
    this.f2975dx = 0.0f;
    this.f2976dy = 0.0f;
    this.shadowColor = 0;
    this.radius = radius;
    this.f2975dx = dx;
    this.f2976dy = dy;
    this.shadowColor = color;
  }

  public SprAttributeShadow(SprInputStream in) throws IOException {
    super(SprAttributeBase.TYPE_SHADOW);
    this.radius = 0.0f;
    this.f2975dx = 0.0f;
    this.f2976dy = 0.0f;
    this.shadowColor = 0;
    fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public void fromSPR(SprInputStream in) throws IOException {
    this.radius = in.readFloat();
    this.f2975dx = in.readFloat();
    this.f2976dy = in.readFloat();
    this.shadowColor = in.readInt();
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public void toSPR(DataOutputStream out) throws IOException {
    out.writeFloat(this.radius);
    out.writeFloat(this.f2975dx);
    out.writeFloat(this.f2976dy);
    out.writeInt(this.shadowColor);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public int getSPRSize() {
    return 16;
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  /* renamed from: clone */
  public SprAttributeShadow mo8903clone() throws CloneNotSupportedException {
    SprAttributeShadow attribute = (SprAttributeShadow) super.mo8903clone();
    attribute.radius = this.radius;
    attribute.f2975dx = this.f2975dx;
    attribute.f2976dy = this.f2976dy;
    attribute.shadowColor = this.shadowColor;
    return attribute;
  }
}
