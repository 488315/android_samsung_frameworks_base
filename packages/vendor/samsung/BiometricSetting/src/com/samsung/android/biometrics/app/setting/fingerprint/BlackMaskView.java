package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BlackMaskView extends SurfaceView implements SurfaceHolder.Callback {
    public final String TAG;
    public Canvas mCanvas;
    public final SurfaceHolder mHolder;
    public boolean mIsBlackMaskModeOn;
    public final Paint mPaint;
    public final SurfaceControl.Transaction mSurfaceTransaction;

    public BlackMaskView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "BSS_BlackMaskView";
        this.mSurfaceTransaction = new SurfaceControl.Transaction();
        SurfaceHolder holder = getHolder();
        this.mHolder = holder;
        holder.addCallback(this);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        paint.setColor(-16777216);
        holder.setFormat(-2);
        setZOrderMediaOverlay(false);
    }

    public void setBlackMaskMode(boolean z) {
        if (this.mIsBlackMaskModeOn == z) {
            return;
        }
        this.mIsBlackMaskModeOn = z;
        setZOrderMediaOverlay(z);
        SurfaceHolder surfaceHolder = this.mHolder;
        if (surfaceHolder == null) {
            Log.d(this.TAG, "mHolder is null");
            return;
        }
        try {
            Canvas lockCanvas = surfaceHolder.lockCanvas(null);
            this.mCanvas = lockCanvas;
            if (lockCanvas == null) {
                Log.d(this.TAG, "mCanvas is null");
            } else {
                lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                if (this.mIsBlackMaskModeOn) {
                    this.mCanvas.drawPaint(this.mPaint);
                }
                Canvas canvas = this.mCanvas;
                if (canvas == null) {}
            }
        } finally {
            Canvas canvas2 = this.mCanvas;
            if (canvas2 != null) {
                this.mHolder.unlockCanvasAndPost(canvas2);
            }
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mSurfaceTransaction.setTrustedOverlay(getSurfaceControl(), true).apply();
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurfaceTransaction.setTrustedOverlay(getSurfaceControl(), true).apply();
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {}
}
