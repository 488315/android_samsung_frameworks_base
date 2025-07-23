package androidx.appcompat.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.util.SeslRoundedCorner;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslSubheaderRoundedCorner extends SeslRoundedCorner {
    public SeslSubheaderRoundedCorner(Context context) {
        super(context);
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

    public final void drawRoundedCornerInternal(Canvas canvas) {
        Rect rect = this.mRoundedCornerBounds;
        int i = rect.left;
        int i2 = rect.right;
        int i3 = rect.top;
        int i4 = rect.bottom;
        int i5 = this.mRoundedCornerMode & 1;
        int i6 = this.mRoundRadius;
        if (i5 != 0) {
            SeslRoundedCorner.SeslRoundedChunkingDrawable seslRoundedChunkingDrawable = this.mTopLeftRound;
            seslRoundedChunkingDrawable.setBounds(i, i4, i + i6, i4 + i6);
            seslRoundedChunkingDrawable.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 2) != 0) {
            SeslRoundedCorner.SeslRoundedChunkingDrawable seslRoundedChunkingDrawable2 = this.mTopRightRound;
            seslRoundedChunkingDrawable2.setBounds(i2 - i6, i4, i2, i4 + i6);
            seslRoundedChunkingDrawable2.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 4) != 0) {
            SeslRoundedCorner.SeslRoundedChunkingDrawable seslRoundedChunkingDrawable3 = this.mBottomLeftRound;
            seslRoundedChunkingDrawable3.setBounds(i, i3 - i6, i + i6, i3);
            seslRoundedChunkingDrawable3.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 8) != 0) {
            SeslRoundedCorner.SeslRoundedChunkingDrawable seslRoundedChunkingDrawable4 = this.mBottomRightRound;
            seslRoundedChunkingDrawable4.setBounds(i2 - i6, i3 - i6, i2, i3);
            seslRoundedChunkingDrawable4.draw(canvas);
        }
    }
}
