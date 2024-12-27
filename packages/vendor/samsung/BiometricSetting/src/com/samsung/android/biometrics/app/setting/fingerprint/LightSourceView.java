package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.samsung.android.biometrics.app.setting.Utils;

public class LightSourceView extends SurfaceView implements SurfaceHolder.Callback {
    public Canvas mCanvas;
    public final Context mContext;
    public int mCurrentRotation;
    public boolean mHasPendingDraw;
    public final SurfaceHolder mHolder;
    public boolean mIsLightSourceShown;
    public final Paint mPaint;
    public int mPosX;
    public int mPosY;
    public FingerprintSensorInfo mSensorInfo;
    public int mSize;
    public final SurfaceControl.Transaction mSurfaceTransaction;

    public LightSourceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSurfaceTransaction = new SurfaceControl.Transaction();
        this.mContext = context;
        SurfaceHolder holder = getHolder();
        this.mHolder = holder;
        holder.addCallback(this);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        setZOrderOnTop(true);
        holder.setFormat(-2);
    }

    public final void draw() {
        Canvas canvas;
        SurfaceHolder surfaceHolder = this.mHolder;
        if (surfaceHolder == null) {
            Log.d("BSS_LightSourceView", "mHolder is null");
            return;
        }
        try {
            this.mHasPendingDraw = false;
            Canvas lockCanvas = surfaceHolder.lockCanvas(null);
            this.mCanvas = lockCanvas;
            if (lockCanvas == null) {
                Log.d("BSS_LightSourceView", "mCanvas is null");
                if (this.mIsLightSourceShown) {
                    this.mHasPendingDraw = true;
                }
                if (canvas != null) {
                    return;
                } else {
                    return;
                }
            }
            lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            if (this.mIsLightSourceShown) {
                Canvas canvas2 = this.mCanvas;
                int i = this.mPosX;
                canvas2.drawCircle(
                        i + (r2 / 2), this.mPosY + (r2 / 2), this.mSize / 2.0f, this.mPaint);
            }
            Canvas canvas3 = this.mCanvas;
            if (canvas3 != null) {
                this.mHolder.unlockCanvasAndPost(canvas3);
            }
        } finally {
            canvas = this.mCanvas;
            if (canvas != null) {
                this.mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public final void handleLightSource(boolean z) {
        if (z) {
            if (this.mCurrentRotation != Utils.getRotation(this.mContext)) {
                setLightSourceInfo();
            }
            setVisibility(0);
        }
        this.mIsLightSourceShown = z;
        draw();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.i("BSS_LightSourceView", "onConfigurationChanged : " + configuration);
    }

    public final void setLightSourceInfo() {
        this.mCurrentRotation = Utils.getRotation(this.mContext);
        Point maximumWindowSize = Utils.getMaximumWindowSize(this.mContext);
        FingerprintSensorInfo fingerprintSensorInfo = this.mSensorInfo;
        int i = this.mCurrentRotation;
        fingerprintSensorInfo.getClass();
        Point point = new Point();
        if (fingerprintSensorInfo.mIsAnyUdfps) {
            Point sensorImagePoint$2 = fingerprintSensorInfo.getSensorImagePoint$2();
            if (i == 0) {
                point.x = (maximumWindowSize.x / 2) - sensorImagePoint$2.x;
                point.y = maximumWindowSize.y - sensorImagePoint$2.y;
            } else if (i == 1) {
                point.x = maximumWindowSize.x - sensorImagePoint$2.y;
                point.y =
                        ((maximumWindowSize.y / 2) + sensorImagePoint$2.x)
                                - fingerprintSensorInfo.mSensorImageSize;
            } else if (i == 2) {
                int i2 = (maximumWindowSize.x / 2) + sensorImagePoint$2.x;
                int i3 = fingerprintSensorInfo.mSensorImageSize;
                point.x = i2 - i3;
                point.y = sensorImagePoint$2.y - i3;
            } else if (i == 3) {
                point.x = sensorImagePoint$2.y - fingerprintSensorInfo.mSensorImageSize;
                point.y = (maximumWindowSize.y / 2) - sensorImagePoint$2.x;
            }
        }
        this.mPosX = point.x;
        this.mPosY = point.y;
        this.mSize = this.mSensorInfo.mSensorImageSize;
        this.mPaint.setColor(Color.parseColor("#ff" + this.mSensorInfo.mLightColor));
        Log.i(
                "BSS_LightSourceView",
                "setLightSourceInfo : " + maximumWindowSize + ", " + this.mCurrentRotation);
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mSurfaceTransaction.setTrustedOverlay(getSurfaceControl(), true).apply();
        if (this.mHasPendingDraw) {
            draw();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurfaceTransaction.setTrustedOverlay(getSurfaceControl(), true).apply();
        if (this.mHasPendingDraw) {
            draw();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {}
}
