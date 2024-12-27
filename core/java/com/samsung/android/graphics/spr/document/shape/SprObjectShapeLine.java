package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;

import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;

import java.io.DataOutputStream;
import java.io.IOException;

public class SprObjectShapeLine extends SprObjectBase {
    public float x1;
    public float x2;
    public float y1;
    public float y2;

    public SprObjectShapeLine() {
        super((byte) 3);
        this.x1 = 0.0f;
        this.x2 = 0.0f;
        this.y1 = 0.0f;
        this.y2 = 0.0f;
    }

    public SprObjectShapeLine(SprInputStream in) throws IOException {
        super((byte) 3);
        this.x1 = 0.0f;
        this.x2 = 0.0f;
        this.y1 = 0.0f;
        this.y2 = 0.0f;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.x1 = in.readFloat();
        this.y1 = in.readFloat();
        this.x2 = in.readFloat();
        this.y2 = in.readFloat();
        super.fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeFloat(this.x1);
        out.writeFloat(this.y1);
        out.writeFloat(this.x2);
        out.writeFloat(this.y2);
        super.toSPR(out);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        return super.getSPRSize() + 16;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        return 2;
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
        if (this.isVisibleStroke) {
            canvas.drawLine(this.x1, this.y1, this.x2, this.y2, this.strokePaint);
        }
        clearShadowLayer();
        canvas.restore();
    }
}
