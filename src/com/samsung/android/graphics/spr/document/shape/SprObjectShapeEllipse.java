package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprObjectShapeEllipse extends SprObjectBase {
    public float bottom;
    public float left;
    public float right;
    public float top;

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalElementCount() {
        return 1;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        return 4;
    }

    public SprObjectShapeEllipse() {
        super((byte) 2);
        this.left = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.bottom = 0.0f;
    }

    public SprObjectShapeEllipse(SprInputStream sprInputStream) throws IOException {
        super((byte) 2);
        this.left = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.bottom = 0.0f;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.left = sprInputStream.readFloat();
        this.top = sprInputStream.readFloat();
        this.right = sprInputStream.readFloat();
        this.bottom = sprInputStream.readFloat();
        super.fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeFloat(this.left);
        dataOutputStream.writeFloat(this.top);
        dataOutputStream.writeFloat(this.right);
        dataOutputStream.writeFloat(this.bottom);
        super.toSPR(dataOutputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        return super.getSPRSize() + 16;
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
        if (this.isVisibleFill) {
            canvas.drawOval(rectF, this.fillPaint);
        }
        if (this.isVisibleStroke) {
            canvas.drawOval(rectF, this.strokePaint);
        }
        clearShadowLayer();
        canvas.restore();
    }
}
