package com.android.systemui.wallpaper.theme.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.theme.DensityUtil;
import com.android.systemui.wallpaper.theme.LockscreenCallback;
import com.android.systemui.wallpaper.theme.particle.Rain;

public final class RainView extends View implements LockscreenCallback {
    public final Context mContext;
    public final Paint mPaint;
    public final Rain[] mRainPool;
    public final Bitmap rainline;
    public boolean refresh;

    public RainView(Context context) {
        super(context);
        this.refresh = true;
        this.mPaint = new Paint();
        this.mContext = context;
        this.mRainPool = new Rain[60];
        int i = 0;
        while (true) {
            Rain[] rainArr = this.mRainPool;
            if (i >= rainArr.length) {
                this.rainline = ((BitmapDrawable) getContext().getResources().getDrawable(R.drawable.rainline)).getBitmap();
                return;
            } else {
                rainArr[i] = new Rain(context);
                i++;
            }
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.refresh) {
            boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        }
        for (Rain rain : this.mRainPool) {
            canvas.drawBitmap(this.rainline, DensityUtil.dip2px(this.mContext, rain.x), DensityUtil.dip2px(this.mContext, rain.y), this.mPaint);
            float f = (float) (rain.x + rain.mXSpeed);
            rain.x = f;
            float f2 = rain.y + rain.mYSpeed;
            rain.y = f2;
            if (f <= 0.0f || f >= DensityUtil.sMetricsWidth || f2 >= DensityUtil.sMetricsHeight) {
                rain.x = rain.mRandom.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
                rain.y = 0.0f;
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
