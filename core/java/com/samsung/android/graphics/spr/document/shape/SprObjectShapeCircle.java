package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SprObjectShapeCircle extends SprObjectBase {

    /* renamed from: cr */
    public float f2984cr;

    /* renamed from: cx */
    public float f2985cx;

    /* renamed from: cy */
    public float f2986cy;

    public SprObjectShapeCircle() {
        super((byte) 1);
        this.f2985cx = 0.0f;
        this.f2986cy = 0.0f;
        this.f2984cr = 0.0f;
    }

    public SprObjectShapeCircle(SprInputStream in) throws IOException {
        super((byte) 1);
        this.f2985cx = 0.0f;
        this.f2986cy = 0.0f;
        this.f2984cr = 0.0f;
        fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream in) throws IOException {
        this.f2985cx = in.readFloat();
        this.f2986cy = in.readFloat();
        this.f2984cr = in.readFloat();
        super.fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeFloat(this.f2985cx);
        out.writeFloat(this.f2986cy);
        out.writeFloat(this.f2984cr);
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
            canvas.drawCircle(this.f2985cx, this.f2986cy, this.f2984cr, this.fillPaint);
        }
        if (this.isVisibleStroke) {
            canvas.drawCircle(this.f2985cx, this.f2986cy, this.f2984cr, this.strokePaint);
        }
        clearShadowLayer();
        canvas.restore();
    }
}
