package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprAttributeStroke extends SprAttributeColor {
  public SprAttributeStroke() {
    super((byte) 35);
  }

  public SprAttributeStroke(byte type, int value) {
    super((byte) 35, type, value);
  }

  public SprAttributeStroke(byte type, SprGradientBase gradient) {
    super((byte) 35, type, gradient);
  }

  public SprAttributeStroke(SprInputStream in) throws IOException {
    super((byte) 35, in);
  }
}
