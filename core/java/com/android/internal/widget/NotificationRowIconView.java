package com.android.internal.widget;

import android.app.Flags;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
public class NotificationRowIconView extends CachingIconView {
    private boolean mApplyCircularCrop;

    public NotificationRowIconView(Context context) {
        super(context);
        this.mApplyCircularCrop = false;
    }

    public NotificationRowIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mApplyCircularCrop = false;
    }

    public NotificationRowIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mApplyCircularCrop = false;
    }

    public NotificationRowIconView(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mApplyCircularCrop = false;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        if (Flags.notificationsUseAppIcon() || Flags.notificationsUseAppIconInRow()) {
            setPadding(0, 0, 0, 0);
            setBackground(null);
        }
        super.onFinishInflate();
    }

    @Override // com.android.internal.widget.CachingIconView
    Drawable loadSizeRestrictedIcon(Icon icon) {
        Drawable original = super.loadSizeRestrictedIcon(icon);
        if (this.mApplyCircularCrop) {
            Drawable result = makeCircularDrawable(original);
            return result;
        }
        return original;
    }

    @RemotableViewMethod(asyncImpl = "setApplyCircularCropAsync")
    public void setApplyCircularCrop(boolean applyCircularCrop) {
        this.mApplyCircularCrop = applyCircularCrop;
    }

    public Runnable setApplyCircularCropAsync(boolean applyCircularCrop) {
        this.mApplyCircularCrop = applyCircularCrop;
        return new Runnable() { // from class:
                                // com.android.internal.widget.NotificationRowIconView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationRowIconView.lambda$setApplyCircularCropAsync$0();
            }
        };
    }

    static /* synthetic */ void lambda$setApplyCircularCropAsync$0() {}

    private Drawable makeCircularDrawable(Drawable original) {
        if (original == null) {
            return original;
        }
        Bitmap source = drawableToBitmap(original);
        int size = Math.min(source.getWidth(), source.getHeight());
        Bitmap squared = Bitmap.createScaledBitmap(source, size, size, false);
        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float radius = size / 2.0f;
        canvas.drawCircle(radius, radius, radius, paint);
        return new BitmapDrawable(getResources(), result);
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap.getConfig() == Bitmap.Config.HARDWARE) {
                return bitmap.copy(Bitmap.Config.ARGB_8888, false);
            }
            return bitmap;
        }
        int width = drawable.getIntrinsicWidth();
        int width2 = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap2 =
                Bitmap.createBitmap(width2, height > 0 ? height : 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap2;
    }
}
