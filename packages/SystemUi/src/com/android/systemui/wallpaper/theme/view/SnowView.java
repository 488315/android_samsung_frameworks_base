package com.android.systemui.wallpaper.theme.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.theme.DensityUtil;
import com.android.systemui.wallpaper.theme.LockscreenCallback;
import com.android.systemui.wallpaper.theme.particle.Snow;
import java.util.Random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        if (this.refresh && WallpaperUtils.sDrawState) {
            invalidate();
        }
        for (Snow snow : this.mSnowPool) {
            this.mPaint.setAlpha(snow.alpha);
            canvas.drawCircle(DensityUtil.dip2px(snow.f431cx, this.mContext), DensityUtil.dip2px(snow.f432cy, this.mContext), DensityUtil.dip2px(snow.radius, this.mContext), this.mPaint);
            float f = snow.f431cx + snow.mXSpeed;
            snow.f431cx = f;
            float f2 = snow.f432cy + snow.mYSpeed;
            snow.f432cy = f2;
            if (!(snow.alpha > 0 && f > 0.0f && f < ((float) DensityUtil.sMetricsWidth) && f2 < ((float) DensityUtil.sMetricsHeight))) {
                Random random = snow.mRandom;
                snow.f431cx = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
                snow.f432cy = 0.0f;
                snow.alpha = Snow.FIXEDALPHAS[random.nextInt(10)];
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
