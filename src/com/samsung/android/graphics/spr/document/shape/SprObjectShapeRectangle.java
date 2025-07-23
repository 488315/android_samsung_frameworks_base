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

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalElementCount() {
        return 1;
    }

    public SprObjectShapeRectangle() {
        super((byte) 5);
        this.left = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.bottom = 0.0f;
        this.rx = 0.0f;
        this.ry = 0.0f;
    }

    public SprObjectShapeRectangle(float f, float f2, float f3, float f4) {
        super((byte) 5);
        this.rx = 0.0f;
        this.ry = 0.0f;
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public SprObjectShapeRectangle(SprInputStream sprInputStream) throws IOException {
        super((byte) 5);
        this.left = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.bottom = 0.0f;
        this.rx = 0.0f;
        this.ry = 0.0f;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.left = sprInputStream.readFloat();
        this.top = sprInputStream.readFloat();
        this.right = sprInputStream.readFloat();
        this.bottom = sprInputStream.readFloat();
        this.rx = sprInputStream.readFloat();
        this.ry = sprInputStream.readFloat();
        super.fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeFloat(this.left);
        dataOutputStream.writeFloat(this.top);
        dataOutputStream.writeFloat(this.right);
        dataOutputStream.writeFloat(this.bottom);
        dataOutputStream.writeFloat(this.rx);
        dataOutputStream.writeFloat(this.ry);
        super.toSPR(dataOutputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        return super.getSPRSize() + 24;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        return (this.rx == 0.0f && this.ry == 0.0f) ? 4 : 8;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void draw(SprDocument sprDocument, Canvas canvas, float f, float f2, float f3) {
        canvas.save(31);
        float f4 = f3 * this.alpha;
        if (this.mAttributeList.size() > 0) {
            applyAttribute(sprDocument, canvas, f4);
        }
        RectF rectF = new RectF(this.left, this.top, this.right, this.bottom);
        setShadowLayer();
        if (this.rx != 0.0f || this.ry != 0.0f) {
            if (this.isVisibleFill) {
                canvas.drawRoundRect(rectF, this.rx, this.ry, this.fillPaint);
            }
            if (this.isVisibleStroke) {
                canvas.drawRoundRect(rectF, this.rx, this.ry, this.strokePaint);
            }
        } else {
            if (this.isVisibleFill) {
                canvas.drawRect(rectF, this.fillPaint);
            }
            if (this.isVisibleStroke) {
                canvas.drawRect(rectF, this.strokePaint);
            }
        }
        clearShadowLayer();
        canvas.restore();
    }
}
