package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprObjectShapeEllipse extends SprObjectBase {
  public float bottom;
  public float left;
  public float right;
  public float top;

  public SprObjectShapeEllipse() {
    super((byte) 2);
    this.left = 0.0f;
    this.top = 0.0f;
    this.right = 0.0f;
    this.bottom = 0.0f;
  }

  public SprObjectShapeEllipse(SprInputStream in) throws IOException {
    super((byte) 2);
    this.left = 0.0f;
    this.top = 0.0f;
    this.right = 0.0f;
    this.bottom = 0.0f;
    fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
  public void fromSPR(SprInputStream in) throws IOException {
    this.left = in.readFloat();
    this.top = in.readFloat();
    this.right = in.readFloat();
    this.bottom = in.readFloat();
    super.fromSPR(in);
  }

  @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
  public void toSPR(DataOutputStream out) throws IOException {
    out.writeFloat(this.left);
    out.writeFloat(this.top);
    out.writeFloat(this.right);
    out.writeFloat(this.bottom);
    super.toSPR(out);
  }

  @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
  public int getSPRSize() {
    return super.getSPRSize() + 16;
  }

  @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
  public int getTotalSegmentCount() {
    return 4;
  }

  @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
  public int getTotalElementCount() {
    return 1;
  }

  @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
  public void draw(SprDocument document, Canvas canvas, float sx, float sy, float alpha) {
    canvas.save(31);
    float curAlpha = this.alpha * alpha;
    if (this.mAttributeList.size() > 0) {
      applyAttribute(document, canvas, curAlpha);
    }
    RectF rect = new RectF(this.left, this.top, this.right, this.bottom);
    setShadowLayer();
    if (this.isVisibleFill) {
      canvas.drawOval(rect, this.fillPaint);
    }
    if (this.isVisibleStroke) {
      canvas.drawOval(rect, this.strokePaint);
    }
    clearShadowLayer();
    canvas.restore();
  }
}
