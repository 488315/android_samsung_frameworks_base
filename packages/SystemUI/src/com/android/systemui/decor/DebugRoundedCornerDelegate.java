package com.android.systemui.decor;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Size;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DebugRoundedCornerDelegate implements RoundedCornerResDelegate {
    public Drawable bottomRoundedDrawable;
    public boolean hasBottom;
    public boolean hasTop;
    public final Paint paint;
    public Drawable topRoundedDrawable;
    public Size topRoundedSize = new Size(0, 0);
    public Size bottomRoundedSize = new Size(0, 0);
    public float physicalPixelDisplaySizeRatio = 1.0f;
    public int color = -65536;

    public DebugRoundedCornerDelegate() {
        Paint paint = new Paint();
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        this.paint = paint;
    }

    public final void applyNewDebugCorners(DebugRoundedCornerModel debugRoundedCornerModel, DebugRoundedCornerModel debugRoundedCornerModel2) {
        if (debugRoundedCornerModel != null) {
            this.hasTop = true;
            this.topRoundedDrawable = new PathDrawable(debugRoundedCornerModel.path, debugRoundedCornerModel.width, debugRoundedCornerModel.height, debugRoundedCornerModel.scaleX, debugRoundedCornerModel.scaleY, this.paint);
            this.topRoundedSize = new Size(debugRoundedCornerModel.width, debugRoundedCornerModel.height);
            Unit unit = Unit.INSTANCE;
        } else {
            new Function0() { // from class: com.android.systemui.decor.DebugRoundedCornerDelegate$applyNewDebugCorners$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DebugRoundedCornerDelegate debugRoundedCornerDelegate = DebugRoundedCornerDelegate.this;
                    debugRoundedCornerDelegate.hasTop = false;
                    debugRoundedCornerDelegate.topRoundedDrawable = null;
                    debugRoundedCornerDelegate.topRoundedSize = new Size(0, 0);
                    return Unit.INSTANCE;
                }
            };
        }
        if (debugRoundedCornerModel2 == null) {
            new Function0() { // from class: com.android.systemui.decor.DebugRoundedCornerDelegate$applyNewDebugCorners$4
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DebugRoundedCornerDelegate debugRoundedCornerDelegate = DebugRoundedCornerDelegate.this;
                    debugRoundedCornerDelegate.hasBottom = false;
                    debugRoundedCornerDelegate.bottomRoundedDrawable = null;
                    debugRoundedCornerDelegate.bottomRoundedSize = new Size(0, 0);
                    return Unit.INSTANCE;
                }
            };
            return;
        }
        this.hasBottom = true;
        this.bottomRoundedDrawable = new PathDrawable(debugRoundedCornerModel2.path, debugRoundedCornerModel2.width, debugRoundedCornerModel2.height, debugRoundedCornerModel2.scaleX, debugRoundedCornerModel2.scaleY, this.paint);
        this.bottomRoundedSize = new Size(debugRoundedCornerModel2.width, debugRoundedCornerModel2.height);
        Unit unit2 = Unit.INSTANCE;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Drawable getBottomRoundedDrawable() {
        return this.bottomRoundedDrawable;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Size getBottomRoundedSize() {
        return this.bottomRoundedSize;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final boolean getHasBottom() {
        return this.hasBottom;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final boolean getHasTop() {
        return this.hasTop;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Drawable getTopRoundedDrawable() {
        return this.topRoundedDrawable;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Size getTopRoundedSize() {
        return this.topRoundedSize;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final void updateDisplayUniqueId(String str, Integer num) {
    }
}
