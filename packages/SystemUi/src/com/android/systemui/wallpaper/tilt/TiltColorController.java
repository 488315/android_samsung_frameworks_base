package com.android.systemui.wallpaper.tilt;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.PathInterpolator;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.tilt.GyroDetector;
import com.android.systemui.wallpaper.tilt.SequentialAnimator;
import com.android.systemui.wallpaper.view.KeyguardImageWallpaper;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TiltColorController {
    public static final PathInterpolator BASE_INTERPOLATOR = new PathInterpolator(0.17f, 0.17f, 0.0f, 1.0f);
    public ColorFilter mColorFilter;
    public ColorMatrix mColorMatrix;
    public final Context mContext;
    public final Drawer mDrawer;
    public final GyroDetector mGyroDetector;
    public final C36933 mGyroSensorChangeListener;
    public boolean mNeedUpdateColorFilter;
    public Paint mPaint;
    public C36955 mTiltSettingObserver;
    public boolean mIsEnable = false;
    public boolean mPrevState = false;
    public SequentialAnimator mEnterAnimator = null;
    public SequentialAnimator mAlphaAnimator = null;
    public final HandlerC36911 mAnimateHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.wallpaper.tilt.TiltColorController.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            TiltColorController tiltColorController = TiltColorController.this;
            boolean z = 1 == message.what;
            PathInterpolator pathInterpolator = TiltColorController.BASE_INTERPOLATOR;
            tiltColorController.startEnterAnimationInner(z);
        }
    };
    public final SequentialAnimator.AnimatedValue mHue = new SequentialAnimator.AnimatedValue(-30.0f, "hue");
    public final SequentialAnimator.AnimatedValue mSaturation = new SequentialAnimator.AnimatedValue(1.2f, "saturation");
    public final SequentialAnimator.AnimatedValue mAlpha = new SequentialAnimator.AnimatedValue(0.0f, "alpha");
    public final SequentialAnimator.AnimatedValue mScale = new SequentialAnimator.AnimatedValue(1.1f, "scale");
    public float mHueLimit = 30.0f;
    public final AtomicBoolean mIsDrawRequested = new AtomicBoolean(false);
    public boolean mIsGyroAllowed = false;
    public float mTotalRotation = 0.0f;
    public float mMaxRotation = 0.0f;
    public final SequentialAnimator.AnimatedValue mTiltScale = new SequentialAnimator.AnimatedValue(0.0f);
    public final HandlerC36922 mTiltHandler = new HandlerC36922(Looper.getMainLooper());

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpaper.tilt.TiltColorController$2 */
    public final class HandlerC36922 extends Handler {
        public HandlerC36922(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            TiltColorController tiltColorController = TiltColorController.this;
            if (tiltColorController.mEnterAnimator.mIsRunning || !tiltColorController.mIsGyroAllowed) {
                return;
            }
            SequentialAnimator.AnimatedValue animatedValue = tiltColorController.mTiltScale;
            float f = animatedValue.delta;
            float max = (Math.max(0.001f, 1.0f - TiltColorController.BASE_INTERPOLATOR.getInterpolation(0.0f == f ? 1.0f : Math.abs((animatedValue.currentValue - animatedValue.startValue) / f))) * f) / 20.0f;
            float max2 = f > 0.0f ? Math.max(0.001f, max) : Math.min(-0.001f, max);
            SequentialAnimator.AnimatedValue animatedValue2 = TiltColorController.this.mTiltScale;
            if (Math.abs(max2) > Math.abs(animatedValue2.targetValue - animatedValue2.currentValue)) {
                SequentialAnimator.AnimatedValue animatedValue3 = TiltColorController.this.mTiltScale;
                animatedValue3.currentValue = animatedValue3.targetValue;
            } else {
                TiltColorController.this.mTiltScale.currentValue += max2;
            }
            TiltColorController tiltColorController2 = TiltColorController.this;
            float f2 = tiltColorController2.mTiltScale.currentValue * 30.0f;
            SequentialAnimator.AnimatedValue animatedValue4 = tiltColorController2.mHue;
            if (animatedValue4.currentValue != f2) {
                animatedValue4.currentValue = f2;
                animatedValue4.setTarget(f2);
                tiltColorController2.mNeedUpdateColorFilter = true;
            }
            TiltColorController.this.requestDraw();
            TiltColorController tiltColorController3 = TiltColorController.this;
            if (tiltColorController3.mIsGyroAllowed) {
                SequentialAnimator.AnimatedValue animatedValue5 = tiltColorController3.mTiltScale;
                if (animatedValue5.currentValue != animatedValue5.targetValue) {
                    tiltColorController3.mTiltHandler.sendEmptyMessageDelayed(0, 50L);
                }
            }
            super.handleMessage(message);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpaper.tilt.TiltColorController$3 */
    public final class C36933 implements GyroDetector.GyroSensorChangeListener {
        public C36933() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.wallpaper.tilt.TiltColorController$1] */
    public TiltColorController(Context context, Drawer drawer) {
        C36933 c36933 = new C36933();
        this.mGyroSensorChangeListener = c36933;
        this.mContext = context;
        this.mDrawer = drawer;
        this.mGyroDetector = new GyroDetector(context, c36933);
        this.mPaint = new Paint(1);
        this.mColorMatrix = new ColorMatrix();
        refreshTiltSettings();
    }

    public final void refreshTiltSettings() {
        Context context = this.mContext;
        boolean isEnableTilt = WallpaperUtils.isEnableTilt(context);
        boolean z = false;
        boolean z2 = Settings.Secure.getInt(context.getContentResolver(), "dymlock_suspend_multiwallpaper_switching", 0) == 1;
        if (isEnableTilt && !z2) {
            z = true;
        }
        setEnable(z);
        float f = Settings.System.getInt(context.getContentResolver(), "lockscreen_wallpaper_tilt_effect_hue_limit", 30);
        if (f > 180.0f) {
            Log.w("TiltColorController", "setHueLimit: too big." + f + ". set to 180.0");
            f = 180.0f;
        } else if (f < 0.0f) {
            Log.w("TiltColorController", "setHueLimit: too small." + f + ". set to 30.0");
            f = 30.0f;
        }
        Log.i("TiltColorController", "setHueLimit: " + f);
        this.mHueLimit = f;
        StringBuilder sb = new StringBuilder("refreshTiltSettings: ");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, this.mIsEnable, " isTiltEnabled: ", isEnableTilt, " isSuspendByDynamicLockScreen: ");
        sb.append(z2);
        sb.append(" hueLimit:");
        sb.append(this.mHueLimit);
        Log.i("TiltColorController", sb.toString());
    }

    public final void requestDraw() {
        Drawer drawer;
        if (this.mIsDrawRequested.getAndSet(true) || (drawer = this.mDrawer) == null) {
            return;
        }
        KeyguardImageWallpaper keyguardImageWallpaper = (KeyguardImageWallpaper) drawer;
        if (keyguardImageWallpaper.mIsDrawRequested.getAndSet(true)) {
            return;
        }
        keyguardImageWallpaper.invalidate();
    }

    public final void setEnable(boolean z) {
        NotificationListener$$ExternalSyntheticOutline0.m123m(RowView$$ExternalSyntheticOutline0.m49m("setEnable: ", z, " isGyroAllowed"), this.mIsGyroAllowed, "TiltColorController");
        this.mIsEnable = z;
        GyroDetector gyroDetector = this.mGyroDetector;
        if (z) {
            if (gyroDetector == null || !this.mIsGyroAllowed || gyroDetector.mResumed) {
                return;
            }
            Log.e("GyroDetector", "Sensor resumed.");
            gyroDetector.mResumed = true;
            gyroDetector.mSensorManager.registerListener(gyroDetector.mSensorListener, gyroDetector.mGyroSensor, 2);
            return;
        }
        stopAllAnimations();
        SequentialAnimator.AnimatedValue animatedValue = this.mHue;
        if (animatedValue.currentValue != 0.0f) {
            animatedValue.currentValue = 0.0f;
            animatedValue.setTarget(0.0f);
            this.mNeedUpdateColorFilter = true;
        }
        SequentialAnimator.AnimatedValue animatedValue2 = this.mSaturation;
        if (animatedValue2.currentValue != 1.0f) {
            animatedValue2.currentValue = 1.0f;
            animatedValue2.setTarget(1.0f);
            this.mNeedUpdateColorFilter = true;
        }
        SequentialAnimator.AnimatedValue animatedValue3 = this.mScale;
        animatedValue3.currentValue = 1.0f;
        animatedValue3.setTarget(1.0f);
        SequentialAnimator.AnimatedValue animatedValue4 = this.mTiltScale;
        animatedValue4.currentValue = 0.0f;
        animatedValue4.setTarget(0.0f);
        SequentialAnimator.AnimatedValue animatedValue5 = this.mAlpha;
        animatedValue5.currentValue = 1.0f;
        animatedValue5.setTarget(1.0f);
        this.mIsDrawRequested.set(false);
        if (gyroDetector != null) {
            gyroDetector.pause();
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.wallpaper.tilt.TiltColorController$5] */
    public final void setTiltSettingObserver(boolean z) {
        synchronized (this) {
            if (z) {
                try {
                    if (this.mTiltSettingObserver == null) {
                        this.mTiltSettingObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.systemui.wallpaper.tilt.TiltColorController.5
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z2) {
                                PathInterpolator pathInterpolator = TiltColorController.BASE_INTERPOLATOR;
                                Log.i("TiltColorController", "onChange: setting changed");
                                TiltColorController.this.refreshTiltSettings();
                                TiltColorController.this.requestDraw();
                                super.onChange(z2);
                            }
                        };
                        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("lockscreen_wallpaper_tilt_effect"), false, this.mTiltSettingObserver);
                        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("lockscreen_wallpaper_tilt_effect_hue_limit"), false, this.mTiltSettingObserver);
                        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("dymlock_suspend_multiwallpaper_switching"), false, this.mTiltSettingObserver);
                        refreshTiltSettings();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (!z && this.mTiltSettingObserver != null) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mTiltSettingObserver);
                this.mTiltSettingObserver = null;
            }
        }
    }

    public final void startAlphaAnimation(float f) {
        SequentialAnimator sequentialAnimator = this.mAlphaAnimator;
        SequentialAnimator.AnimatedValue animatedValue = this.mAlpha;
        if (sequentialAnimator == null) {
            SequentialAnimator sequentialAnimator2 = new SequentialAnimator();
            this.mAlphaAnimator = sequentialAnimator2;
            sequentialAnimator2.mDummyAnimator.setInterpolator(BASE_INTERPOLATOR);
            this.mAlphaAnimator.mAnimationValues.add(animatedValue);
            AnimationListenerAdapterProfiler animationListenerAdapterProfiler = new AnimationListenerAdapterProfiler();
            animationListenerAdapterProfiler.startAnimationProfile("TiltColorController_Alpha");
            SequentialAnimator sequentialAnimator3 = this.mAlphaAnimator;
            sequentialAnimator3.mAnimatorListener = animationListenerAdapterProfiler;
            sequentialAnimator3.mAnimatorUpdateListener = animationListenerAdapterProfiler;
        }
        SequentialAnimator sequentialAnimator4 = this.mAlphaAnimator;
        if (sequentialAnimator4 != null && sequentialAnimator4.mIsRunning) {
            sequentialAnimator4.cancel();
        }
        Log.i("TiltColorController", "startAlphaAnimation: " + f + " / 350");
        animatedValue.setTarget(f);
        this.mAlphaAnimator.setDuration(350L);
        this.mAlphaAnimator.start();
    }

    public final void startEnterAnimation(boolean z) {
        Looper myLooper = Looper.myLooper();
        Looper mainLooper = Looper.getMainLooper();
        HandlerC36911 handlerC36911 = this.mAnimateHandler;
        if (myLooper == mainLooper) {
            handlerC36911.removeMessages(0);
            startEnterAnimationInner(z);
            return;
        }
        if (handlerC36911.hasMessages(0)) {
            handlerC36911.removeMessages(0);
        }
        Message obtainMessage = handlerC36911.obtainMessage(0);
        obtainMessage.what = z ? 1 : 0;
        handlerC36911.sendMessage(obtainMessage);
    }

    public final void startEnterAnimationInner(boolean z) {
        Log.i("TiltColorController", "startEnterAnimation: ");
        SequentialAnimator sequentialAnimator = this.mEnterAnimator;
        SequentialAnimator.AnimatedValue animatedValue = this.mTiltScale;
        SequentialAnimator.AnimatedValue animatedValue2 = this.mScale;
        SequentialAnimator.AnimatedValue animatedValue3 = this.mSaturation;
        SequentialAnimator.AnimatedValue animatedValue4 = this.mHue;
        if (sequentialAnimator == null) {
            Log.i("TiltColorController", "startEnterAnimation: create");
            SequentialAnimator sequentialAnimator2 = new SequentialAnimator();
            this.mEnterAnimator = sequentialAnimator2;
            sequentialAnimator2.mDummyAnimator.setInterpolator(BASE_INTERPOLATOR);
            AnimationListenerAdapterProfiler animationListenerAdapterProfiler = new AnimationListenerAdapterProfiler() { // from class: com.android.systemui.wallpaper.tilt.TiltColorController.4
                @Override // com.android.systemui.wallpaper.tilt.AnimationListenerAdapterProfiler, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    GyroDetector gyroDetector;
                    super.onAnimationEnd(animator);
                    TiltColorController.this.mIsDrawRequested.set(false);
                    if (animator.getDuration() != 1000 || this.mIsCanceled) {
                        return;
                    }
                    TiltColorController tiltColorController = TiltColorController.this;
                    tiltColorController.mIsGyroAllowed = true;
                    if (!tiltColorController.mIsEnable || (gyroDetector = tiltColorController.mGyroDetector) == null || gyroDetector.mResumed) {
                        return;
                    }
                    Log.e("GyroDetector", "Sensor resumed.");
                    gyroDetector.mResumed = true;
                    gyroDetector.mSensorManager.registerListener(gyroDetector.mSensorListener, gyroDetector.mGyroSensor, 2);
                }

                @Override // com.android.systemui.wallpaper.tilt.AnimationListenerAdapterProfiler, android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    super.onAnimationUpdate(valueAnimator);
                    TiltColorController tiltColorController = TiltColorController.this;
                    tiltColorController.mNeedUpdateColorFilter = true;
                    tiltColorController.requestDraw();
                }
            };
            animationListenerAdapterProfiler.startAnimationProfile("TiltColorController_Enter");
            SequentialAnimator sequentialAnimator3 = this.mEnterAnimator;
            sequentialAnimator3.mAnimatorListener = animationListenerAdapterProfiler;
            sequentialAnimator3.mAnimatorUpdateListener = animationListenerAdapterProfiler;
            ArrayList arrayList = sequentialAnimator3.mAnimationValues;
            arrayList.add(animatedValue4);
            arrayList.add(animatedValue3);
            arrayList.add(animatedValue2);
            arrayList.add(animatedValue);
        }
        stopAllAnimations();
        if (z) {
            this.mEnterAnimator.setDuration(1000L);
            animatedValue4.setTarget(0.0f);
            animatedValue3.setTarget(1.0f);
            animatedValue2.setTarget(1.0f);
            animatedValue.setTarget(0.0f);
            startAlphaAnimation(1.0f);
        } else {
            this.mEnterAnimator.setDuration(350L);
            animatedValue4.setTarget(-30.0f);
            animatedValue3.setTarget(1.2f);
            animatedValue2.setTarget(1.1f);
            animatedValue.setTarget(0.0f);
            startAlphaAnimation(0.0f);
        }
        this.mEnterAnimator.start();
        requestDraw();
        Log.i("TiltColorController", "startEnterAnimation: done");
    }

    public final void stopAllAnimations() {
        this.mIsGyroAllowed = false;
        GyroDetector gyroDetector = this.mGyroDetector;
        if (gyroDetector != null) {
            gyroDetector.pause();
        }
        this.mTotalRotation = 0.0f;
        SequentialAnimator sequentialAnimator = this.mEnterAnimator;
        if (sequentialAnimator != null && sequentialAnimator.mIsRunning) {
            sequentialAnimator.cancel();
        }
        SequentialAnimator sequentialAnimator2 = this.mAlphaAnimator;
        if (sequentialAnimator2 == null || !sequentialAnimator2.mIsRunning) {
            return;
        }
        sequentialAnimator2.cancel();
    }
}
