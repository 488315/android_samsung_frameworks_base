package com.android.systemui.media;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;

public class QSMediaCornerRoundedView extends FrameLayout {
    public int mHeight;
    public boolean mIsCornerRound;
    public final Path mPath;
    public final int mRadius;
    public final RectF mRect;

    public QSMediaCornerRoundedView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHeight = 0;
        this.mIsCornerRound = true;
        this.mPath = new Path();
        this.mRect = new RectF();
        this.mRadius = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_background_corner_radius);
        setBackgroundColor(0);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        if (this.mIsCornerRound) {
            if (this.mHeight == 0 || this.mRect.height() == 0.0f) {
                this.mRect.set(0.0f, 0.0f, getWidth(), getHeight());
            }
            this.mPath.reset();
            Path path = this.mPath;
            RectF rectF = this.mRect;
            int i = this.mRadius;
            path.addRoundRect(rectF, i, i, Path.Direction.CCW);
            this.mPath.close();
            canvas.clipPath(this.mPath);
            canvas.restoreToCount(canvas.save());
        }
        super.draw(canvas);
    }

    @Override // android.view.View
    public final void setClipBounds(Rect rect) {
        super.setClipBounds(rect);
        this.mRect.set(rect.left, rect.top, rect.right, rect.bottom);
        this.mHeight = rect.height();
        invalidate();
    }
}
