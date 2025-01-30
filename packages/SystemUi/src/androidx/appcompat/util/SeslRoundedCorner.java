package androidx.appcompat.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslRoundedCorner {
    public final Drawable mBottomLeftRound;
    public final Drawable mBottomRightRound;
    public final int mRoundRadius;
    public final Rect mRoundedCornerBounds;
    public int mRoundedCornerMode;
    public final Drawable mTopLeftRound;
    public int mTopLeftRoundColor;
    public final Drawable mTopRightRound;

    public SeslRoundedCorner(Context context) {
        this(context, false);
    }

    public void drawRoundedCorner(View view, Canvas canvas) {
        int left;
        int top;
        if (view.getTranslationY() != 0.0f) {
            left = Math.round(view.getX());
            top = Math.round(view.getY());
            canvas.translate((view.getX() - left) + 0.5f, (view.getY() - top) + 0.5f);
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
            Drawable drawable = this.mTopLeftRound;
            drawable.setBounds(i, i3, i + i6, i3 + i6);
            drawable.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 2) != 0) {
            Drawable drawable2 = this.mTopRightRound;
            drawable2.setBounds(i2 - i6, i3, i2, i3 + i6);
            drawable2.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 4) != 0) {
            Drawable drawable3 = this.mBottomLeftRound;
            drawable3.setBounds(i, i4 - i6, i + i6, i4);
            drawable3.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 8) != 0) {
            Drawable drawable4 = this.mBottomRightRound;
            drawable4.setBounds(i2 - i6, i4 - i6, i2, i4);
            drawable4.draw(canvas);
        }
    }

    public final void setRoundedCornerColor(int i, int i2) {
        if (i == 0) {
            throw new IllegalArgumentException("There is no rounded corner on = " + this);
        }
        if ((i & (-16)) != 0) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Use wrong rounded corners to the param, corners = ", i));
        }
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_IN);
        if ((i & 1) != 0) {
            this.mTopLeftRoundColor = i2;
            this.mTopLeftRound.setColorFilter(porterDuffColorFilter);
        }
        if ((i & 2) != 0) {
            this.mTopRightRound.setColorFilter(porterDuffColorFilter);
        }
        if ((i & 4) != 0) {
            this.mBottomLeftRound.setColorFilter(porterDuffColorFilter);
        }
        if ((i & 8) != 0) {
            this.mBottomRightRound.setColorFilter(porterDuffColorFilter);
        }
    }

    public final void setRoundedCorners(int i) {
        if ((i & (-16)) != 0) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Use wrong rounded corners to the param, corners = ", i));
        }
        this.mRoundedCornerMode = i;
    }

    public SeslRoundedCorner(Context context, boolean z) {
        this.mRoundedCornerBounds = new Rect();
        Resources resources = context.getResources();
        this.mRoundRadius = resources.getDimensionPixelSize(R.dimen.sesl_rounded_corner_radius);
        boolean z2 = !SeslMisc.isLightTheme(context);
        Resources.Theme theme = context.getTheme();
        if (z) {
            this.mTopLeftRound = resources.getDrawable(R.drawable.sesl_top_left_round, theme).mutate();
            this.mTopRightRound = resources.getDrawable(R.drawable.sesl_top_right_round, theme).mutate();
            this.mBottomLeftRound = resources.getDrawable(R.drawable.sesl_bottom_left_round, theme).mutate();
            this.mBottomRightRound = resources.getDrawable(R.drawable.sesl_bottom_right_round, theme).mutate();
        } else {
            this.mTopLeftRound = resources.getDrawable(R.drawable.sesl_top_left_round, theme);
            this.mTopRightRound = resources.getDrawable(R.drawable.sesl_top_right_round, theme);
            this.mBottomLeftRound = resources.getDrawable(R.drawable.sesl_bottom_left_round, theme);
            this.mBottomRightRound = resources.getDrawable(R.drawable.sesl_bottom_right_round, theme);
        }
        if (z2) {
            this.mTopLeftRoundColor = resources.getColor(R.color.sesl_round_and_bgcolor_dark);
        } else {
            this.mTopLeftRoundColor = resources.getColor(R.color.sesl_round_and_bgcolor_light);
        }
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(this.mTopLeftRoundColor, PorterDuff.Mode.SRC_IN);
        this.mTopLeftRound.setColorFilter(porterDuffColorFilter);
        this.mTopRightRound.setColorFilter(porterDuffColorFilter);
        this.mBottomLeftRound.setColorFilter(porterDuffColorFilter);
        this.mBottomRightRound.setColorFilter(porterDuffColorFilter);
    }
}
