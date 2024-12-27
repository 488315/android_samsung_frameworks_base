package com.android.systemui.wallpaper.theme.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.theme.DensityUtil;
import com.android.systemui.wallpaper.theme.LockscreenCallback;
import com.android.systemui.wallpaper.theme.particle.Snow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SnowView extends View implements LockscreenCallback {
    public final Context mContext;
    public final Paint mPaint;
    public final Snow[] mSnowPool;
    public boolean refresh;

    public SnowView(Context context) {
        super(context);
        Paint paint = new Paint();
        this.mPaint = paint;
        this.refresh = true;
        this.mContext = context;
        this.mSnowPool = new Snow[35];
        paint.setColor(-1);
        int i = 0;
        while (true) {
            Snow[] snowArr = this.mSnowPool;
            if (i >= snowArr.length) {
                return;
            }
            snowArr[i] = new Snow(context);
            i++;
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.refresh) {
            boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        }
        for (Snow snow : this.mSnowPool) {
            this.mPaint.setAlpha(snow.alpha);
            canvas.drawCircle(DensityUtil.dip2px(this.mContext, snow.cx), DensityUtil.dip2px(this.mContext, snow.cy), DensityUtil.dip2px(this.mContext, snow.radius), this.mPaint);
            float f = snow.cx + snow.mXSpeed;
            snow.cx = f;
            float f2 = snow.cy + snow.mYSpeed;
            snow.cy = f2;
            if (snow.alpha <= 0 || f <= 0.0f || f >= DensityUtil.sMetricsWidth || f2 >= DensityUtil.sMetricsHeight) {
                snow.cx = snow.mRandom.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
                snow.cy = 0.0f;
                snow.alpha = Snow.FIXEDALPHAS[snow.mRandom.nextInt(10)];
            }
        }
    }

    @Override // com.android.systemui.wallpaper.theme.LockscreenCallback
    public final void screenTurnedOff() {
        this.refresh = false;
    }

    @Override // com.android.systemui.wallpaper.theme.LockscreenCallback
    public final void screenTurnedOn() {
        this.refresh = true;
        invalidate();
    }
}
