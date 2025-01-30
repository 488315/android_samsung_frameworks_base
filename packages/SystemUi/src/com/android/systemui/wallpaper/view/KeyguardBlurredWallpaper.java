package com.android.systemui.wallpaper.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.graphics.SemGfxImageFilter;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardBlurredWallpaper extends SystemUIWallpaper {
    public int mBitmapHeight;
    public int mBitmapWidth;
    public final SemGfxImageFilter mBlurFilter;
    public boolean mCaptureStart;
    public Bitmap mCapturedBitmap;
    public final Context mContext;
    public final Matrix mDrawMatrix;
    public final Paint mDrawPaint;
    public final ExecutorService mExecutor;
    public final HandlerC37001 mHandler;
    public float mLastAmount;
    public boolean mOccluded;
    public int mOriginDx;
    public int mOriginDy;
    public int mRotation;
    public int mViewHeight;
    public int mViewWidth;
    public final SystemUIWallpaperBase mWallpaperView;

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.wallpaper.view.KeyguardBlurredWallpaper$1] */
    public KeyguardBlurredWallpaper(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WallpaperResultCallback wallpaperResultCallback, ExecutorService executorService, Consumer<Boolean> consumer, boolean z, SystemUIWallpaperBase systemUIWallpaperBase, boolean z2, SemGfxImageFilter semGfxImageFilter) {
        super(context, keyguardUpdateMonitor, wallpaperResultCallback, executorService, consumer, z);
        this.mDrawMatrix = new Matrix();
        this.mDrawPaint = new Paint(2);
        this.mLastAmount = 0.0f;
        this.mRotation = 0;
        this.mCaptureStart = false;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.wallpaper.view.KeyguardBlurredWallpaper.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                Bitmap bitmap;
                float f;
                float f2;
                if (message.what != 1000) {
                    return;
                }
                Log.i("BlurredWallpaper", "capture end " + KeyguardBlurredWallpaper.this.mCapturedBitmap);
                KeyguardBlurredWallpaper keyguardBlurredWallpaper = KeyguardBlurredWallpaper.this;
                keyguardBlurredWallpaper.mCaptureStart = false;
                Log.i("BlurredWallpaper", "initMatrix: view width = " + keyguardBlurredWallpaper.mViewWidth + ", view height = " + keyguardBlurredWallpaper.mViewHeight + " , bitmap = " + keyguardBlurredWallpaper.mCapturedBitmap + " , " + keyguardBlurredWallpaper.getParent());
                if (keyguardBlurredWallpaper.mViewWidth != 0 && keyguardBlurredWallpaper.mViewHeight != 0 && (bitmap = keyguardBlurredWallpaper.mCapturedBitmap) != null && !bitmap.isRecycled()) {
                    keyguardBlurredWallpaper.mBitmapWidth = keyguardBlurredWallpaper.mCapturedBitmap.getWidth();
                    int height = keyguardBlurredWallpaper.mCapturedBitmap.getHeight();
                    keyguardBlurredWallpaper.mBitmapHeight = height;
                    int i = keyguardBlurredWallpaper.mBitmapWidth;
                    int i2 = keyguardBlurredWallpaper.mViewHeight;
                    int i3 = i * i2;
                    int i4 = keyguardBlurredWallpaper.mViewWidth;
                    if (i3 > i4 * height) {
                        f = i2;
                        f2 = height;
                    } else {
                        f = i4;
                        f2 = i;
                    }
                    float f3 = f / f2;
                    float f4 = (i4 - (i * f3)) * 0.5f;
                    float f5 = (i2 - (height * f3)) * 0.5f;
                    keyguardBlurredWallpaper.mOriginDx = Math.round(f4);
                    keyguardBlurredWallpaper.mOriginDy = Math.round(f5);
                    keyguardBlurredWallpaper.mDrawMatrix.setScale(f3, f3);
                    keyguardBlurredWallpaper.mDrawMatrix.postTranslate(keyguardBlurredWallpaper.mOriginDx, keyguardBlurredWallpaper.mOriginDy);
                    Log.d("BlurredWallpaper", "mBitmapWidth = " + keyguardBlurredWallpaper.mBitmapWidth);
                    Log.d("BlurredWallpaper", "mBitmapHeight = " + keyguardBlurredWallpaper.mBitmapHeight);
                    Log.d("BlurredWallpaper", "mViewWidth = " + keyguardBlurredWallpaper.mViewWidth);
                    Log.d("BlurredWallpaper", "mViewHeight = " + keyguardBlurredWallpaper.mViewHeight);
                    Log.d("BlurredWallpaper", "scale = " + f3);
                    Log.d("BlurredWallpaper", "dx = " + f4);
                    Log.d("BlurredWallpaper", "dy = " + f5);
                }
                KeyguardBlurredWallpaper.this.invalidate();
            }
        };
        this.mContext = context;
        setWillNotDraw(false);
        this.mWallpaperView = systemUIWallpaperBase;
        this.mBlurFilter = semGfxImageFilter;
        this.mOccluded = z2;
        this.mExecutor = executorService;
        this.mLastAmount = WallpaperUtils.sLastAmount;
    }

    public final void applyBlur(float f) {
        this.mViewWidth = getWidth();
        this.mViewHeight = getHeight();
        StringBuilder sb = new StringBuilder("applyBlur: amount = ");
        sb.append(f);
        sb.append(", prev amount = ");
        sb.append(this.mLastAmount);
        sb.append(" , bitmap = ");
        sb.append(this.mCapturedBitmap);
        sb.append(" , w = ");
        sb.append(this.mViewWidth);
        sb.append(" , h = ");
        sb.append(this.mViewHeight);
        sb.append(" , mCaptureStart = ");
        NotificationListener$$ExternalSyntheticOutline0.m123m(sb, this.mCaptureStart, "BlurredWallpaper");
        SystemUIWallpaperBase systemUIWallpaperBase = this.mWallpaperView;
        if (systemUIWallpaperBase != null) {
            systemUIWallpaperBase.updateBlurState(f != 0.0f);
        }
        if (f == 0.0f || this.mViewHeight == 0 || this.mViewWidth == 0) {
            cleanUp();
        } else if (this.mCapturedBitmap == null && !this.mCaptureStart) {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.view.KeyguardBlurredWallpaper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardBlurredWallpaper keyguardBlurredWallpaper = KeyguardBlurredWallpaper.this;
                    synchronized (keyguardBlurredWallpaper) {
                        if (keyguardBlurredWallpaper.mCapturedBitmap == null) {
                            Log.i("BlurredWallpaper", "capture start ");
                            keyguardBlurredWallpaper.mCaptureStart = true;
                            keyguardBlurredWallpaper.updateCapturedBitmap();
                        }
                    }
                }
            });
        }
        float f2 = this.mLastAmount;
        if (f2 != f || f2 != 0.0f) {
            this.mBlurFilter.setBlurRadius(f);
            semSetGfxImageFilter(f == 0.0f ? null : this.mBlurFilter);
            this.mLastAmount = f;
        }
        if (this.mCapturedBitmap != null) {
            invalidate();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void cleanUp() {
        Bitmap bitmap = this.mCapturedBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mCapturedBitmap = null;
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        if (WallpaperUtils.isExternalLiveWallpaper()) {
            super.onConfigurationChanged(configuration);
            Log.i("BlurredWallpaper", "onConfigurationChanged: " + configuration.orientation);
            awaitCall();
            this.mRotation = this.mCurDisplayInfo.rotation;
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        canvas.save();
        Bitmap bitmap = this.mCapturedBitmap;
        if (bitmap == null || bitmap.isRecycled() || this.mViewHeight <= 0 || this.mViewWidth <= 0) {
            canvas.drawColor(0);
        } else {
            this.mDrawPaint.setAlpha(this.mLastAmount != 0.0f ? 255 : 0);
            canvas.drawBitmap(this.mCapturedBitmap, this.mDrawMatrix, this.mDrawPaint);
        }
        canvas.restore();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int width = getWidth();
        int height = getHeight();
        updateDisplayInfo();
        awaitCall();
        this.mRotation = this.mCurDisplayInfo.rotation;
        TooltipPopup$$ExternalSyntheticOutline0.m13m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("onLayout: width = ", width, " , height = ", height, " , mRotation = "), this.mRotation, "BlurredWallpaper");
        if (this.mLastAmount == 0.0f || this.mViewHeight == height) {
            return;
        }
        update();
        this.mViewWidth = width;
        this.mViewHeight = height;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onOccluded(boolean z) {
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("onOccluded: ", z, " , mLastAmount: ");
        m49m.append(this.mLastAmount);
        Log.i("BlurredWallpaper", m49m.toString());
        this.mOccluded = z;
        if (z || this.mLastAmount <= 0.0f) {
            return;
        }
        updateCapturedBitmap();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onUnlock() {
        cleanUp();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void update() {
        Log.i("BlurredWallpaper", "update: ");
        cleanUp();
        applyBlur(WallpaperUtils.sLastAmount);
    }

    public final void updateCapturedBitmap() {
        int i;
        if (WallpaperUtils.isExternalLiveWallpaper() || this.mOccluded) {
            StringBuilder sb = new StringBuilder("getScreenShot: start, width = ");
            sb.append(this.mViewHeight);
            sb.append(" , height = ");
            sb.append(this.mViewWidth);
            sb.append(" , mRotation = ");
            TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, this.mRotation, "BlurredWallpaper");
            int i2 = this.mViewHeight;
            this.mCapturedBitmap = (i2 <= 0 || (i = this.mViewWidth) <= 0) ? null : WallpaperUtils.getScreenShot(this.mContext, i, i2, this.mRotation, 2000);
        } else {
            SystemUIWallpaperBase systemUIWallpaperBase = this.mWallpaperView;
            if (systemUIWallpaperBase != null) {
                this.mCapturedBitmap = systemUIWallpaperBase.getCapturedWallpaperForBlur();
            }
        }
        sendMessage(obtainMessage(1000));
    }
}
