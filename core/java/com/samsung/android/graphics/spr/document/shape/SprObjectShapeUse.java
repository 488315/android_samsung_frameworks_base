package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeShadow;

import java.io.DataOutputStream;
import java.io.IOException;

public class SprObjectShapeUse extends SprObjectBase {
    public int link;

    public SprObjectShapeUse() {
        super((byte) 17);
        this.link = 0;
    }

    public SprObjectShapeUse(SprInputStream in) throws IOException {
        super((byte) 17);
        this.link = 0;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.link = in.readInt();
        super.fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeInt(this.link);
        super.toSPR(out);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        return super.getSPRSize() + 4;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        return 1;
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
        SprObjectBase link = document.getReference(this.link);
        if (link != null) {
            link.draw(document, canvas, sx, sy, curAlpha);
        }
        canvas.restore();
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void preDraw(
            SprDocument document,
            Paint strokePaint,
            Paint fillPaint,
            boolean isVisibleStroke,
            boolean isVisibleFill,
            SprAttributeShadow shadow) {
        SprObjectBase link = document.getReference(this.link);
        if (link != null) {
            link.preDraw(document, strokePaint, fillPaint, isVisibleStroke, isVisibleFill, shadow);
        }
    }
}
