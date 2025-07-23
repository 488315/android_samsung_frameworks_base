package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeShadow;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprObjectShapeUse extends SprObjectBase {
    public int link;

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalElementCount() {
        return 1;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        return 1;
    }

    public SprObjectShapeUse() {
        super((byte) 17);
        this.link = 0;
    }

    public SprObjectShapeUse(SprInputStream sprInputStream) throws IOException {
        super((byte) 17);
        this.link = 0;
        fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.link = sprInputStream.readInt();
        super.fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.link);
        super.toSPR(dataOutputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        return super.getSPRSize() + 4;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void draw(SprDocument sprDocument, Canvas canvas, float f, float f2, float f3) {
        canvas.save(31);
        float f4 = f3 * this.alpha;
        if (this.mAttributeList.size() > 0) {
            applyAttribute(sprDocument, canvas, f4);
        }
        SprObjectBase reference = sprDocument.getReference(this.link);
        if (reference != null) {
            reference.draw(sprDocument, canvas, f, f2, f4);
        }
        canvas.restore();
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void preDraw(SprDocument sprDocument, Paint paint, Paint paint2, boolean z, boolean z2, SprAttributeShadow sprAttributeShadow) {
        SprObjectBase reference = sprDocument.getReference(this.link);
        if (reference != null) {
            reference.preDraw(sprDocument, paint, paint2, z, z2, sprAttributeShadow);
        }
    }
}
