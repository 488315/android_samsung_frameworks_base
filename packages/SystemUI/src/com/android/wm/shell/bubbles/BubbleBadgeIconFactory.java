package com.android.wm.shell.bubbles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.BitmapInfo;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class BubbleBadgeIconFactory extends BaseIconFactory {

    public class CircularAdaptiveIcon extends AdaptiveIconDrawable {
        public final Path mPath;

        public CircularAdaptiveIcon(Drawable drawable, Drawable drawable2) {
            super(drawable, drawable2);
            this.mPath = new Path();
        }

        @Override // android.graphics.drawable.AdaptiveIconDrawable, android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            int iSave = canvas.save();
            canvas.clipPath(getIconMask());
            Drawable background = getBackground();
            if (background != null) {
                background.draw(canvas);
            }
            Drawable foreground = getForeground();
            if (foreground != null) {
                foreground.draw(canvas);
            }
            canvas.restoreToCount(iSave);
        }

        @Override // android.graphics.drawable.AdaptiveIconDrawable
        public final Path getIconMask() {
            this.mPath.reset();
            Rect bounds = getBounds();
            this.mPath.addOval(bounds.left, bounds.top, bounds.right, bounds.bottom, Path.Direction.CW);
            return this.mPath;
        }
    }

    public BubbleBadgeIconFactory(Context context) {
        super(context, context.getResources().getConfiguration().densityDpi, context.getResources().getDimensionPixelSize(R.dimen.bubble_badge_size));
    }

    public final BitmapInfo getBadgeBitmap(Drawable drawable) {
        if (drawable instanceof AdaptiveIconDrawable) {
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
            drawable = new CircularAdaptiveIcon(adaptiveIconDrawable.getBackground(), adaptiveIconDrawable.getForeground());
        }
        return createIconBitmap(createIconBitmap(drawable, 1.0f, 2));
    }
}
