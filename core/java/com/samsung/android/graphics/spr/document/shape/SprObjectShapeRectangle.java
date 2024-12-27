package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprObjectShapeRectangle extends SprObjectBase {
    public float bottom;
    public float left;
    public float right;
    public float rx;
    public float ry;
    public float top;

    public SprObjectShapeRectangle() {
        super((byte) 5);
        this.left = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.bottom = 0.0f;
        this.rx = 0.0f;
        this.ry = 0.0f;
    }

    public SprObjectShapeRectangle(float l, float t, float r, float b) {
        super((byte) 5);
        this.left = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.bottom = 0.0f;
        this.rx = 0.0f;
        this.ry = 0.0f;
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
    }

    public SprObjectShapeRectangle(SprInputStream in) throws IOException {
        super((byte) 5);
        this.left = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.bottom = 0.0f;
        this.rx = 0.0f;
        this.ry = 0.0f;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.left = in.readFloat();
        this.top = in.readFloat();
        this.right = in.readFloat();
        this.bottom = in.readFloat();
        this.rx = in.readFloat();
        this.ry = in.readFloat();
        super.fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeFloat(this.left);
        out.writeFloat(this.top);
        out.writeFloat(this.right);
        out.writeFloat(this.bottom);
        out.writeFloat(this.rx);
        out.writeFloat(this.ry);
        super.toSPR(out);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        return super.getSPRSize() + 24;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        if (this.rx == 0.0f && this.ry == 0.0f) {
            return 4;
        }
        return 8;
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
        if (this.rx != 0.0f || this.ry != 0.0f) {
            if (this.isVisibleFill) {
                canvas.drawRoundRect(rect, this.rx, this.ry, this.fillPaint);
            }
            if (this.isVisibleStroke) {
                canvas.drawRoundRect(rect, this.rx, this.ry, this.strokePaint);
            }
        } else {
            if (this.isVisibleFill) {
                canvas.drawRect(rect, this.fillPaint);
            }
            if (this.isVisibleStroke) {
                canvas.drawRect(rect, this.strokePaint);
            }
        }
        clearShadowLayer();
        canvas.restore();
    }
}
