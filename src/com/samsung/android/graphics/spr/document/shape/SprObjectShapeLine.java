package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprObjectShapeLine extends SprObjectBase {
    public float x1;
    public float x2;
    public float y1;
    public float y2;

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalElementCount() {
        return 1;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        return 2;
    }

    public SprObjectShapeLine() {
        super((byte) 3);
        this.x1 = 0.0f;
        this.x2 = 0.0f;
        this.y1 = 0.0f;
        this.y2 = 0.0f;
    }

    public SprObjectShapeLine(SprInputStream sprInputStream) throws IOException {
        super((byte) 3);
        this.x1 = 0.0f;
        this.x2 = 0.0f;
        this.y1 = 0.0f;
        this.y2 = 0.0f;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.x1 = sprInputStream.readFloat();
        this.y1 = sprInputStream.readFloat();
        this.x2 = sprInputStream.readFloat();
        this.y2 = sprInputStream.readFloat();
        super.fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeFloat(this.x1);
        dataOutputStream.writeFloat(this.y1);
        dataOutputStream.writeFloat(this.x2);
        dataOutputStream.writeFloat(this.y2);
        super.toSPR(dataOutputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        return super.getSPRSize() + 16;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void draw(SprDocument sprDocument, Canvas canvas, float f, float f2, float f3) {
        Canvas canvas2;
        canvas.save(31);
        float f4 = f3 * this.alpha;
        if (this.mAttributeList.size() > 0) {
            applyAttribute(sprDocument, canvas, f4);
        }
        setShadowLayer();
        if (this.isVisibleStroke) {
            canvas2 = canvas;
            canvas2.drawLine(this.x1, this.y1, this.x2, this.y2, this.strokePaint);
        } else {
            canvas2 = canvas;
        }
        clearShadowLayer();
        canvas2.restore();
    }
}
