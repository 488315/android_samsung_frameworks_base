package androidx.appcompat.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslSubheaderRoundedCorner extends SeslRoundedCorner {
    public SeslSubheaderRoundedCorner(Context context) {
        super(context);
    }

    private void drawRoundedCornerInternal(Canvas canvas) {
        Rect rect = this.mRoundedCornerBounds;
        int i = rect.left;
        int i2 = rect.right;
        int i3 = rect.top;
        int i4 = rect.bottom;
        int i5 = this.mRoundedCornerMode & 1;
        int i6 = this.mRoundRadius;
        if (i5 != 0) {
            Drawable drawable = this.mTopLeftRound;
            drawable.setBounds(i, i4, i + i6, i4 + i6);
            drawable.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 2) != 0) {
            Drawable drawable2 = this.mTopRightRound;
            drawable2.setBounds(i2 - i6, i4, i2, i4 + i6);
            drawable2.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 4) != 0) {
            Drawable drawable3 = this.mBottomLeftRound;
            drawable3.setBounds(i, i3 - i6, i + i6, i3);
            drawable3.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 8) != 0) {
            Drawable drawable4 = this.mBottomRightRound;
            drawable4.setBounds(i2 - i6, i3 - i6, i2, i3);
            drawable4.draw(canvas);
        }
    }

    public final void drawRoundedCorner(int i, int i2, int i3, Canvas canvas) {
        this.mRoundedCornerBounds.set(0, i, i2, i3);
        drawRoundedCornerInternal(canvas);
    }

    @Override // androidx.appcompat.util.SeslRoundedCorner
    public final void drawRoundedCorner(View view, Canvas canvas) {
        int left;
        int top;
        if (view.getTranslationY() != 0.0f) {
            left = Math.round(view.getX());
            top = Math.round(view.getY());
        } else {
            left = view.getLeft();
            top = view.getTop();
        }
        this.mRoundedCornerBounds.set(left, top, view.getWidth() + left, view.getHeight() + top);
        drawRoundedCornerInternal(canvas);
    }
}
