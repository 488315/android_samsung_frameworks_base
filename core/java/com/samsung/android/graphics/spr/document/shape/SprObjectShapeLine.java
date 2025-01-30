package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprObjectShapeLine extends SprObjectBase {

    /* renamed from: x1 */
    public float f2987x1;

    /* renamed from: x2 */
    public float f2988x2;

    /* renamed from: y1 */
    public float f2989y1;

    /* renamed from: y2 */
    public float f2990y2;

    public SprObjectShapeLine() {
        super((byte) 3);
        this.f2987x1 = 0.0f;
        this.f2988x2 = 0.0f;
        this.f2989y1 = 0.0f;
        this.f2990y2 = 0.0f;
    }

    public SprObjectShapeLine(SprInputStream in) throws IOException {
        super((byte) 3);
        this.f2987x1 = 0.0f;
        this.f2988x2 = 0.0f;
        this.f2989y1 = 0.0f;
        this.f2990y2 = 0.0f;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.f2987x1 = in.readFloat();
        this.f2989y1 = in.readFloat();
        this.f2988x2 = in.readFloat();
        this.f2990y2 = in.readFloat();
        super.fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeFloat(this.f2987x1);
        out.writeFloat(this.f2989y1);
        out.writeFloat(this.f2988x2);
        out.writeFloat(this.f2990y2);
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
            canvas.drawLine(this.f2987x1, this.f2989y1, this.f2988x2, this.f2990y2, this.strokePaint);
        }
        clearShadowLayer();
        canvas.restore();
    }
}
