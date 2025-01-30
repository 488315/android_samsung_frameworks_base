package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase;
import com.samsung.android.graphics.spr.document.attribute.impl.SprLinearGradient;
import com.samsung.android.graphics.spr.document.attribute.impl.SprRadialGradient;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public abstract class SprAttributeColor extends SprAttributeBase {
  public static final byte TYPE_ARGB = 1;
  public static final byte TYPE_LINEAR_GRADIENT = 3;
  public static final byte TYPE_LINK = 2;
  public static final byte TYPE_NONE = 0;
  public static final byte TYPE_RADIAL_GRADIENT = 4;
  public int color;
  public byte colorType;
  public SprGradientBase gradient;

  public SprAttributeColor(byte type) {
    super(type);
    this.colorType = (byte) 1;
    this.gradient = null;
    this.colorType = (byte) 1;
    this.color = 0;
  }

  public SprAttributeColor(byte type, byte colorType, int value) {
    super(type);
    this.colorType = (byte) 1;
    this.gradient = null;
    this.colorType = colorType;
    switch (colorType) {
      case 0:
        return;
      case 1:
      case 2:
        this.color = value;
        return;
      default:
        throw new RuntimeException("unexpected stroke type:" + ((int) colorType));
    }
  }

  public SprAttributeColor(byte type, byte colorType, SprGradientBase gradient) {
    super(type);
    this.colorType = (byte) 1;
    this.gradient = null;
    this.colorType = colorType;
    switch (colorType) {
      case 0:
        return;
      case 1:
      case 2:
      default:
        throw new RuntimeException("unexpected stroke type:" + ((int) colorType));
      case 3:
      case 4:
        this.gradient = gradient;
        return;
    }
  }

  public SprAttributeColor(byte type, SprInputStream in) throws IOException {
    super(type);
    this.colorType = (byte) 1;
    this.gradient = null;
    fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public void fromSPR(SprInputStream in) throws IOException {
    byte readByte = in.readByte();
    this.colorType = readByte;
    switch (readByte) {
      case 0:
        in.readInt();
        return;
      case 1:
      case 2:
        this.color = in.readInt();
        return;
      case 3:
        this.gradient = new SprLinearGradient(in);
        return;
      case 4:
        this.gradient = new SprRadialGradient(in);
        return;
      default:
        throw new RuntimeException("unknown fill type:" + ((int) this.colorType));
    }
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public void toSPR(DataOutputStream out) throws IOException {
    out.writeByte(this.colorType);
    switch (this.colorType) {
      case 0:
        out.writeInt(0);
        return;
      case 1:
      case 2:
        out.writeInt(this.color);
        return;
      case 3:
      case 4:
        this.gradient.toSPR(out);
        return;
      default:
        throw new RuntimeException("unknown fill type:" + ((int) this.colorType));
    }
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  public int getSPRSize() {
    switch (this.colorType) {
      case 0:
        return 0;
      case 1:
      case 2:
        return 5;
      case 3:
      case 4:
        int size = this.gradient.getSPRSize() + 1;
        return size;
      default:
        throw new RuntimeException("unknown fill type:" + ((int) this.colorType));
    }
  }

  @Override // com.samsung.android.graphics.spr.document.attribute.SprAttributeBase
  /* renamed from: clone */
  public SprAttributeColor mo8903clone() throws CloneNotSupportedException {
    SprAttributeColor attribute = (SprAttributeColor) super.mo8903clone();
    SprGradientBase sprGradientBase = this.gradient;
    if (sprGradientBase != null) {
      attribute.gradient = sprGradientBase.m8904clone();
    }
    return attribute;
  }
}
