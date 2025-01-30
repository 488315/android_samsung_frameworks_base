package com.android.wm.shell.bubbles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BubbleIconFactory extends BaseIconFactory {
    public BubbleIconFactory(Context context) {
        super(context, context.getResources().getConfiguration().densityDpi, context.getResources().getDimensionPixelSize(R.dimen.bubble_size));
    }

    public final Bitmap getCircledBubble(Drawable drawable, boolean z) {
        Bitmap createIconBitmap;
        if (drawable instanceof AdaptiveIconDrawable) {
            createIconBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createIconBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
            if (adaptiveIconDrawable.getBackground() != null) {
                adaptiveIconDrawable.getBackground().draw(canvas);
            }
            if (adaptiveIconDrawable.getForeground() != null) {
                adaptiveIconDrawable.getForeground().draw(canvas);
            }
        } else {
            createIconBitmap = createIconBitmap(drawable, 1.0f, this.mContext.getResources().getDimensionPixelSize(R.dimen.bubble_size));
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.bubble_size);
        if (z) {
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_overflow_button_size);
        }
        float dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_icon_outline_border);
        Bitmap createBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap);
        Paint paint = new Paint();
        if (z) {
            paint.setColor(this.mContext.getResources().getColor(R.color.sec_bubble_overflow_icon_color));
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_overflow_plus_button_size);
        } else {
            paint.setColor(-12303292);
        }
        Rect rect = new Rect(z ? (canvas2.getWidth() / 2) - (dimensionPixelSize / 2) : 0, z ? (canvas2.getHeight() / 2) - (dimensionPixelSize / 2) : 0, z ? (dimensionPixelSize / 2) + (canvas2.getWidth() / 2) : canvas2.getWidth(), z ? (dimensionPixelSize / 2) + (canvas2.getHeight() / 2) : canvas2.getHeight());
        paint.setAntiAlias(true);
        canvas2.drawARGB(0, 0, 0, 0);
        canvas2.drawCircle(canvas2.getWidth() / 2, canvas2.getHeight() / 2, (canvas2.getWidth() / 2) - dimensionPixelSize2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas2.drawBitmap(createIconBitmap, (Rect) null, rect, paint);
        paint.setColor(this.mContext.getResources().getColor(R.color.sec_bubble_noti_icon_outline_border_color));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        canvas2.drawCircle(canvas2.getWidth() / 2, canvas2.getHeight() / 2, canvas2.getWidth() / 2, paint);
        return createBitmap;
    }
}
