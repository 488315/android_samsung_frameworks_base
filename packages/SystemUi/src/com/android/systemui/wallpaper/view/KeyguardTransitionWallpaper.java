package com.android.systemui.wallpaper.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
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
import android.view.animation.PathInterpolator;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardTransitionWallpaper extends SystemUIWallpaper {
    public int mBitmapHeight;
    public int mBitmapWidth;
    public boolean mCaptureStart;
    public Bitmap mCapturedBitmap;
    public int mCapturedRotation;
    public Matrix mDrawMatrix;
    public final Paint mDrawPaint;
    public final ExecutorService mExecutor;
    public final HandlerC37072 mHandler;
    public final PluginWallpaperManager mPluginWallpaperManager;
    public int mRotation;
    public int mTransitionAnimatorValue;
    public KeyguardWallpaperController.C36645 mUpdateListener;
    public final ValueAnimator mValueAnimator;
    public int mViewHeight;
    public int mViewWidth;
    public SystemUIWallpaperBase mWallpaperView;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.wallpaper.view.KeyguardTransitionWallpaper$2] */
    public KeyguardTransitionWallpaper(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WallpaperResultCallback wallpaperResultCallback, PluginWallpaperManager pluginWallpaperManager, ExecutorService executorService, Consumer<Boolean> consumer, boolean z, SystemUIWallpaperBase systemUIWallpaperBase, boolean z2) {
        super(context, keyguardUpdateMonitor, wallpaperResultCallback, executorService, consumer, z);
        this.mDrawMatrix = new Matrix();
        this.mDrawPaint = new Paint(2);
        this.mRotation = 0;
        this.mCaptureStart = false;
        this.mTransitionAnimatorValue = 255;
        this.mCapturedRotation = 0;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.wallpaper.view.KeyguardTransitionWallpaper.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1000) {
                    return;
                }
                Log.i("KeyguardTransitionWallpaper", "capture end " + KeyguardTransitionWallpaper.this.mCapturedBitmap);
                KeyguardTransitionWallpaper keyguardTransitionWallpaper = KeyguardTransitionWallpaper.this;
                keyguardTransitionWallpaper.mCaptureStart = false;
                keyguardTransitionWallpaper.setScaleX(1.02f);
                KeyguardTransitionWallpaper.this.setScaleY(1.02f);
                KeyguardTransitionWallpaper.this.initMatrix();
                KeyguardTransitionWallpaper keyguardTransitionWallpaper2 = KeyguardTransitionWallpaper.this;
                keyguardTransitionWallpaper2.mTransitionAnimatorValue = 255;
                keyguardTransitionWallpaper2.invalidate();
                if (KeyguardTransitionWallpaper.this.mUpdateListener != null) {
                    Log.d("KeyguardTransitionWallpaper", "MSG_CAPTURE_FINISHED");
                    KeyguardTransitionWallpaper.this.mUpdateListener.onDrawCompleted();
                }
                ((PluginWallpaperManagerImpl) KeyguardTransitionWallpaper.this.mPluginWallpaperManager).onWallpaperConsumed(WallpaperUtils.isSubDisplay() ? 1 : 0, false);
            }
        };
        setWillNotDraw(false);
        this.mWallpaperView = systemUIWallpaperBase;
        this.mExecutor = executorService;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(255.0f, 0.0f);
        ofFloat.setInterpolator(new PathInterpolator(0.32f, 0.62f, 0.71f, 1.0f));
        ofFloat.setDuration(500L);
        this.mValueAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.view.KeyguardTransitionWallpaper$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardTransitionWallpaper keyguardTransitionWallpaper = KeyguardTransitionWallpaper.this;
                keyguardTransitionWallpaper.getClass();
                keyguardTransitionWallpaper.mTransitionAnimatorValue = Math.round(((Float) valueAnimator.getAnimatedValue()).floatValue());
                keyguardTransitionWallpaper.invalidate();
            }
        });
        ofFloat.addListener(new C37061());
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void cleanUp() {
        Log.i("KeyguardTransitionWallpaper", "cleanUp()");
        Bitmap bitmap = this.mCapturedBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mCapturedBitmap = null;
        }
    }

    public final boolean initMatrix() {
        Bitmap bitmap;
        float f;
        float f2;
        Log.i("KeyguardTransitionWallpaper", "initMatrix: view width = " + this.mViewWidth + ", view height = " + this.mViewHeight + " , bitmap = " + this.mCapturedBitmap + " , " + getParent());
        if (this.mViewWidth == 0 || this.mViewHeight == 0 || (bitmap = this.mCapturedBitmap) == null || bitmap.isRecycled()) {
            return false;
        }
        Matrix matrix = new Matrix();
        int i = this.mCapturedRotation;
        float f3 = 0.0f;
        float f4 = 1.0f;
        if (i == this.mRotation) {
            if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isCustomPackApplied()) {
                this.mBitmapWidth = this.mCapturedBitmap.getWidth();
                int height = this.mCapturedBitmap.getHeight();
                this.mBitmapHeight = height;
                f4 = Math.max(this.mViewWidth / this.mBitmapWidth, this.mViewHeight / height);
                f3 = (this.mViewWidth - (this.mBitmapWidth * f4)) * 0.5f;
                f = (this.mViewHeight - (this.mBitmapHeight * f4)) * 0.5f;
                matrix.postScale(f4, f4);
                matrix.postTranslate(Math.round(f3), Math.round(f));
            } else {
                int i2 = this.mRotation;
                if (i2 == 1) {
                    matrix.postRotate(270.0f);
                    f = this.mViewHeight;
                    matrix.postScale(1.0f, 1.0f);
                    matrix.postTranslate(Math.round(0.0f), Math.round(f));
                } else if (i2 == 3) {
                    matrix.postRotate(90.0f);
                    f2 = this.mViewWidth;
                    matrix.postScale(1.0f, 1.0f);
                    matrix.postTranslate(Math.round(f2), Math.round(0.0f));
                } else {
                    f = 0.0f;
                }
            }
            this.mDrawMatrix = matrix;
            StringBuilder sb = new StringBuilder("initMatrix : bmpW=");
            sb.append(this.mCapturedBitmap.getWidth());
            sb.append(", bmpH=");
            sb.append(this.mCapturedBitmap.getHeight());
            sb.append(", mViewWidth=");
            sb.append(this.mViewWidth);
            sb.append(", mViewHeight=");
            sb.append(this.mViewHeight);
            sb.append(", scale=");
            sb.append(f4);
            sb.append(", dx=");
            sb.append(f3);
            sb.append(", dy=");
            sb.append(f);
            sb.append(", mCapturedRotation=");
            sb.append(this.mCapturedRotation);
            sb.append(", mRotation=");
            RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mRotation, "KeyguardTransitionWallpaper");
            return true;
        }
        if (i == 1) {
            this.mBitmapWidth = this.mCapturedBitmap.getWidth();
            this.mBitmapHeight = this.mCapturedBitmap.getHeight();
            f2 = this.mViewWidth;
            matrix.postRotate(90.0f);
            matrix.postScale(1.0f, 1.0f);
            matrix.postTranslate(Math.round(f2), Math.round(0.0f));
        } else {
            f2 = 0.0f;
        }
        if (this.mCapturedRotation == 3) {
            this.mBitmapWidth = this.mCapturedBitmap.getWidth();
            this.mBitmapHeight = this.mCapturedBitmap.getHeight();
            float f5 = this.mViewHeight;
            matrix.postRotate(270.0f);
            matrix.postScale(1.0f, 1.0f);
            matrix.postTranslate(Math.round(f2), Math.round(f5));
            f3 = f5;
        }
        float f6 = f3;
        f3 = f2;
        f = f6;
        this.mDrawMatrix = matrix;
        StringBuilder sb2 = new StringBuilder("initMatrix : bmpW=");
        sb2.append(this.mCapturedBitmap.getWidth());
        sb2.append(", bmpH=");
        sb2.append(this.mCapturedBitmap.getHeight());
        sb2.append(", mViewWidth=");
        sb2.append(this.mViewWidth);
        sb2.append(", mViewHeight=");
        sb2.append(this.mViewHeight);
        sb2.append(", scale=");
        sb2.append(f4);
        sb2.append(", dx=");
        sb2.append(f3);
        sb2.append(", dy=");
        sb2.append(f);
        sb2.append(", mCapturedRotation=");
        sb2.append(this.mCapturedRotation);
        sb2.append(", mRotation=");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb2, this.mRotation, "KeyguardTransitionWallpaper");
        return true;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        if (WallpaperUtils.isExternalLiveWallpaper()) {
            super.onConfigurationChanged(configuration);
            Log.i("KeyguardTransitionWallpaper", "onConfigurationChanged: " + configuration.orientation);
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
            this.mDrawPaint.setAlpha(this.mTransitionAnimatorValue);
            canvas.drawBitmap(this.mCapturedBitmap, this.mDrawMatrix, this.mDrawPaint);
        }
        canvas.restore();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        updateDisplayInfo();
        awaitCall();
        this.mRotation = this.mCurDisplayInfo.rotation;
        StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("onLayout: changed = ", z, ", left = ", i, ", top = ");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m66m, i2, ", right = ", i3, ", bottom = ");
        m66m.append(i4);
        Log.i("KeyguardTransitionWallpaper", m66m.toString());
        this.mViewWidth = i3 - i;
        this.mViewHeight = i4 - i2;
        initMatrix();
        invalidate();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onUnlock() {
        cleanUp();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void update() {
        Log.i("KeyguardTransitionWallpaper", "update: ");
        cleanUp();
        this.mViewWidth = getWidth();
        this.mViewHeight = getHeight();
        StringBuilder sb = new StringBuilder("updateView: bitmap = ");
        sb.append(this.mCapturedBitmap);
        sb.append(" , w = ");
        sb.append(this.mViewWidth);
        sb.append(" , h = ");
        sb.append(this.mViewHeight);
        sb.append(" , mCaptureStart = ");
        NotificationListener$$ExternalSyntheticOutline0.m123m(sb, this.mCaptureStart, "KeyguardTransitionWallpaper");
        if (this.mCaptureStart) {
            return;
        }
        this.mExecutor.execute(new KeyguardTransitionWallpaper$$ExternalSyntheticLambda1(this, 0));
    }

    public final void updateBitmap() {
        SystemUIWallpaperBase systemUIWallpaperBase = this.mWallpaperView;
        if (systemUIWallpaperBase == null) {
            this.mCaptureStart = false;
            Log.d("KeyguardTransitionWallpaper", "updateBitmap: mWallpaperView is not set.");
            if (this.mUpdateListener != null) {
                Log.d("KeyguardTransitionWallpaper", "updateBitmap: onDrawCompleted.");
                this.mUpdateListener.onDrawCompleted();
            }
            ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).onWallpaperConsumed(WallpaperUtils.isSubDisplay() ? 1 : 0, false);
            return;
        }
        if (systemUIWallpaperBase instanceof KeyguardVideoWallpaper) {
            Log.d("KeyguardTransitionWallpaper", "updateBitmap: VIDEO");
            this.mCapturedRotation = 0;
        } else {
            Log.d("KeyguardTransitionWallpaper", "updateBitmap: IMAGE for now");
            this.mCapturedRotation = this.mRotation;
        }
        this.mCapturedBitmap = this.mWallpaperView.getWallpaperBitmap();
        sendMessage(obtainMessage(1000));
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpaper.view.KeyguardTransitionWallpaper$1 */
    public final class C37061 implements Animator.AnimatorListener {
        public C37061() {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            KeyguardTransitionWallpaper.this.mExecutor.execute(new KeyguardTransitionWallpaper$$ExternalSyntheticLambda1(this, 1));
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }
}
