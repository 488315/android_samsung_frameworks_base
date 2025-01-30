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
import java.util.Vector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RainView extends View implements LockscreenCallback {
    public final Context mContext;
    public final Paint mPaint;
    public final Rain[] mRainPool;
    public final Bitmap rainline;
    public boolean refresh;

    public RainView(Context context) {
        super(context);
        new Vector();
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
        if (this.refresh && WallpaperUtils.sDrawState) {
            invalidate();
        }
        for (Rain rain : this.mRainPool) {
            canvas.drawBitmap(this.rainline, DensityUtil.dip2px(rain.f429x, this.mContext), DensityUtil.dip2px(rain.f430y, this.mContext), this.mPaint);
            float f = (float) (rain.f429x + rain.mXSpeed);
            rain.f429x = f;
            float f2 = rain.f430y + rain.mYSpeed;
            rain.f430y = f2;
            if (!(f > 0.0f && f < ((float) DensityUtil.sMetricsWidth) && f2 < ((float) DensityUtil.sMetricsHeight))) {
                rain.f429x = rain.mRandom.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
                rain.f430y = 0.0f;
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
