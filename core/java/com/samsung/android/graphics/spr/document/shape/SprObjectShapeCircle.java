package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;

import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;

import java.io.DataOutputStream;
import java.io.IOException;

public class SprObjectShapeCircle extends SprObjectBase {
    public float cr;
    public float cx;
    public float cy;

    public SprObjectShapeCircle() {
        super((byte) 1);
        this.cx = 0.0f;
        this.cy = 0.0f;
        this.cr = 0.0f;
    }

    public SprObjectShapeCircle(SprInputStream in) throws IOException {
        super((byte) 1);
        this.cx = 0.0f;
        this.cy = 0.0f;
        this.cr = 0.0f;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.cx = in.readFloat();
        this.cy = in.readFloat();
        this.cr = in.readFloat();
        super.fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeFloat(this.cx);
        out.writeFloat(this.cy);
        out.writeFloat(this.cr);
        super.toSPR(out);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        return super.getSPRSize() + 12;
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
        setShadowLayer();
        if (this.isVisibleFill) {
            canvas.drawCircle(this.cx, this.cy, this.cr, this.fillPaint);
        }
        if (this.isVisibleStroke) {
            canvas.drawCircle(this.cx, this.cy, this.cr, this.strokePaint);
        }
        clearShadowLayer();
        canvas.restore();
    }
}
