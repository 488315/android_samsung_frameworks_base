package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CutoutDrawable extends MaterialShapeDrawable {
    public final RectF cutoutBounds;

    public CutoutDrawable() {
        this(null);
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable
    public final void drawStrokeShape(Canvas canvas) {
        if (this.cutoutBounds.isEmpty()) {
            super.drawStrokeShape(canvas);
            return;
        }
        canvas.save();
        canvas.clipOutRect(this.cutoutBounds);
        super.drawStrokeShape(canvas);
        canvas.restore();
    }

    public final void setCutout(float f, float f2, float f3, float f4) {
        RectF rectF = this.cutoutBounds;
        if (f == rectF.left && f2 == rectF.top && f3 == rectF.right && f4 == rectF.bottom) {
            return;
        }
        rectF.set(f, f2, f3, f4);
        invalidateSelf();
    }

    public CutoutDrawable(ShapeAppearanceModel shapeAppearanceModel) {
        super(shapeAppearanceModel == null ? new ShapeAppearanceModel() : shapeAppearanceModel);
        this.cutoutBounds = new RectF();
    }
}
