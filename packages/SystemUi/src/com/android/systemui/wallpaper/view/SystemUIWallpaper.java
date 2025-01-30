package com.android.systemui.wallpaper.view;

import android.animation.Animator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.display.DisplayManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SystemUIWallpaper extends FrameLayout implements SystemUIWallpaperBase {
    public final C37112 mAnimatorListener;
    public boolean mBouncer;
    public final DisplayInfo mCurDisplayInfo;
    public int mDensity;
    public Display mDisplay;
    public long mDisplayInfoUpdatedState;
    public DisplayManager mDisplayManager;
    public final DisplayMetrics mDisplayMetrics;
    public int mDrawingState;
    public final ExecutorService mExecutor;
    public float mFontScale;
    public Future mFutureDisplayInfo;
    public boolean mIsKeyguardShowing;
    public final boolean mIsPreview;
    public boolean mIsScreenOffAnimation;
    public int mMetricsHeight;
    public int mMetricsWidth;
    public boolean mOccluded;
    public int mOrientation;
    public boolean mResumed;
    public KeyguardWallpaperController.C36634 mTransitionAnimationListener;
    public final CallableC37101 mUpdateCallable;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public WallpaperResultCallback mWallpaperResultCallback;
    public final Consumer mWcgConsumer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpaper.view.SystemUIWallpaper$1 */
    public final class CallableC37101 implements Callable {
        public CallableC37101() {
        }

        @Override // java.util.concurrent.Callable
        public final Object call() {
            Log.d("SystemUIWallpaper", "mUpdateCallable, start");
            SystemUIWallpaper systemUIWallpaper = SystemUIWallpaper.this;
            if (systemUIWallpaper.mDisplayManager == null) {
                systemUIWallpaper.mDisplayManager = (DisplayManager) ((FrameLayout) systemUIWallpaper).mContext.getSystemService("display");
            }
            DisplayManager displayManager = systemUIWallpaper.mDisplayManager;
            if (displayManager != null) {
                systemUIWallpaper.mDisplay = displayManager.getDisplay(0);
            }
            if (systemUIWallpaper.mIsPreview) {
                Display display = systemUIWallpaper.mDisplay;
                if (display != null) {
                    display.getDisplayInfo(systemUIWallpaper.mCurDisplayInfo);
                }
            } else {
                ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).getDisplay(0).getDisplayInfo(systemUIWallpaper.mCurDisplayInfo);
            }
            Display display2 = systemUIWallpaper.mDisplay;
            if (display2 != null) {
                display2.getRealMetrics(systemUIWallpaper.mDisplayMetrics);
                DisplayMetrics displayMetrics = systemUIWallpaper.mDisplayMetrics;
                systemUIWallpaper.mMetricsWidth = displayMetrics.widthPixels;
                systemUIWallpaper.mMetricsHeight = displayMetrics.heightPixels;
                int i = systemUIWallpaper.mCurDisplayInfo.rotation;
                if (i == 1 || i == 3) {
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("mUpdateCallable, deviceRotation=", i, "mMetricsWidth=");
                    m1m.append(systemUIWallpaper.mMetricsWidth);
                    m1m.append(" mMetricsHeight=");
                    RecyclerView$$ExternalSyntheticOutline0.m46m(m1m, systemUIWallpaper.mMetricsHeight, "SystemUIWallpaper");
                    DisplayMetrics displayMetrics2 = systemUIWallpaper.mDisplayMetrics;
                    systemUIWallpaper.mMetricsWidth = displayMetrics2.heightPixels;
                    systemUIWallpaper.mMetricsHeight = displayMetrics2.widthPixels;
                }
            }
            systemUIWallpaper.mDisplayInfoUpdatedState = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder("mUpdateCallable, end, ");
            sb.append(systemUIWallpaper.mCurDisplayInfo.logicalWidth);
            sb.append(", ");
            RecyclerView$$ExternalSyntheticOutline0.m46m(sb, systemUIWallpaper.mCurDisplayInfo.logicalHeight, "SystemUIWallpaper");
            return systemUIWallpaper.mCurDisplayInfo;
        }
    }

    public SystemUIWallpaper(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WallpaperResultCallback wallpaperResultCallback, ExecutorService executorService, Consumer<Boolean> consumer, boolean z) {
        this(context, keyguardUpdateMonitor, wallpaperResultCallback, executorService, consumer, z, false, true);
    }

    public static boolean isSubDisplay() {
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            return false;
        }
        return WallpaperUtils.isSubDisplay();
    }

    public final void awaitCall() {
        boolean z;
        if (this.mDisplayInfoUpdatedState == Long.MAX_VALUE) {
            try {
                Log.d("SystemUIWallpaper", "awaitCall()");
                this.mFutureDisplayInfo.get(300L, TimeUnit.MILLISECONDS);
                Log.d("SystemUIWallpaper", "awaitCall() done");
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        if (!LsRune.WALLPAPER_SUB_DISPLAY_MODE || LsRune.WALLPAPER_SUB_WATCHFACE) {
            return;
        }
        boolean z2 = false;
        int i = 0;
        while (true) {
            try {
                boolean z3 = !WallpaperUtils.isSubDisplay();
                DisplayInfo displayInfo = this.mCurDisplayInfo;
                if (z3 != WallpaperUtils.isMainScreenRatio(displayInfo.logicalWidth, displayInfo.logicalHeight)) {
                    Log.d("SystemUIWallpaper", "isValidDisplayInfo: Invalid DisplayInfo.");
                    z = false;
                } else {
                    z = true;
                }
                if (z || i >= 3) {
                    break;
                }
                Thread.sleep(20);
                this.mUpdateCallable.call();
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        boolean z4 = !WallpaperUtils.isSubDisplay();
        DisplayInfo displayInfo2 = this.mCurDisplayInfo;
        if (z4 != WallpaperUtils.isMainScreenRatio(displayInfo2.logicalWidth, displayInfo2.logicalHeight)) {
            Log.d("SystemUIWallpaper", "isValidDisplayInfo: Invalid DisplayInfo.");
        } else {
            z2 = true;
        }
        if (z2) {
            return;
        }
        Log.e("SystemUIWallpaper", "awaitCall: DisplayInfo is still not updated. May cause screen distortion.");
    }

    public void cleanUp() {
        this.mTransitionAnimationListener = null;
    }

    public Bitmap getCapturedWallpaper() {
        Bitmap bitmap;
        try {
            Log.d("SystemUIWallpaper", "getCapturedWallpaper() hw accelerated: " + isHardwareAccelerated());
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        } catch (Throwable th) {
            th = th;
            bitmap = null;
        }
        try {
            draw(new Canvas(bitmap));
            return bitmap;
        } catch (Throwable th2) {
            th = th2;
            th.printStackTrace();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return null;
        }
    }

    public Bitmap getCapturedWallpaperForBlur() {
        return getCapturedWallpaper();
    }

    public Bitmap getWallpaperBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) WallpaperManager.getInstance(((FrameLayout) this).mContext).semGetDrawable(WallpaperUtils.sCurrentWhich);
        if (bitmapDrawable != null) {
            return bitmapDrawable.getBitmap();
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void onBackDropLayoutChange() {
        updateDisplayInfo();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("SystemUIWallpaper", "onConfigurationChanged, newConfig: " + configuration);
        float f = configuration.fontScale;
        int i = configuration.densityDpi;
        if (i != this.mDensity || this.mFontScale != f) {
            this.mDensity = i;
            this.mFontScale = f;
        }
        updateDisplayInfo();
        this.mOrientation = configuration.orientation;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mIsPreview) {
            return;
        }
        this.mWallpaperResultCallback = null;
    }

    public void onKeyguardBouncerFullyShowingChanged(boolean z) {
        this.mBouncer = z;
    }

    public void onKeyguardShowing(boolean z) {
        this.mIsKeyguardShowing = z;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (i3 == 0 || i4 == 0) {
            return;
        }
        updateDisplayInfo();
    }

    public void onOccluded(boolean z) {
        this.mOccluded = z;
    }

    public void onPause() {
        this.mResumed = false;
    }

    public void onResume() {
        this.mResumed = true;
    }

    public void preInit() {
        Consumer consumer = this.mWcgConsumer;
        if (consumer != null) {
            consumer.accept(Boolean.FALSE);
        }
    }

    public final void updateDisplayInfo() {
        Log.d("SystemUIWallpaper", "updateDisplayInfo()");
        this.mDisplayInfoUpdatedState = Long.MAX_VALUE;
        this.mFutureDisplayInfo = this.mExecutor.submit(this.mUpdateCallable);
    }

    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.systemui.wallpaper.view.SystemUIWallpaper$2] */
    public SystemUIWallpaper(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WallpaperResultCallback wallpaperResultCallback, ExecutorService executorService, Consumer<Boolean> consumer, boolean z, boolean z2, boolean z3) {
        super(context, null);
        this.mDisplayMetrics = new DisplayMetrics();
        this.mCurDisplayInfo = new DisplayInfo();
        this.mOrientation = 0;
        this.mDrawingState = 0;
        this.mDisplayInfoUpdatedState = 0L;
        this.mUpdateCallable = new CallableC37101();
        this.mAnimatorListener = new Animator.AnimatorListener() { // from class: com.android.systemui.wallpaper.view.SystemUIWallpaper.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("scaleView@onAnimationEnd: mIsScreenOffAnimation = "), SystemUIWallpaper.this.mIsScreenOffAnimation, "SystemUIWallpaper");
                SystemUIWallpaper systemUIWallpaper = SystemUIWallpaper.this;
                KeyguardWallpaperController.C36634 c36634 = systemUIWallpaper.mTransitionAnimationListener;
                if (c36634 != null) {
                    boolean z4 = systemUIWallpaper.mIsScreenOffAnimation;
                    c36634.getClass();
                    KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("TransitionAnimationListener@onAnimationEnd: isScreenOff = ", z4, "KeyguardWallpaperController");
                    KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                    if (((PluginWallpaperManagerImpl) keyguardWallpaperController2.mPluginWallpaperManager).isDynamicWallpaperEnabled()) {
                        if (!z4) {
                            SystemUIWallpaperBase systemUIWallpaperBase = keyguardWallpaperController2.mTransitionView;
                            if (systemUIWallpaperBase != null) {
                                systemUIWallpaperBase.cleanUp();
                                return;
                            }
                            return;
                        }
                        if (keyguardWallpaperController2.mTransitionView == null) {
                            KeyguardTransitionWallpaper keyguardTransitionWallpaper = new KeyguardTransitionWallpaper(keyguardWallpaperController2.mContext, keyguardWallpaperController2.mUpdateMonitor, null, keyguardWallpaperController2.mPluginWallpaperManager, keyguardWallpaperController2.mExecutor, keyguardWallpaperController2.mWcgConsumer, false, keyguardWallpaperController2.mWallpaperView, keyguardWallpaperController2.mOccluded);
                            keyguardWallpaperController2.mTransitionView = keyguardTransitionWallpaper;
                            keyguardTransitionWallpaper.mUpdateListener = keyguardWallpaperController2.mTransitionListener;
                            ViewGroup viewGroup = keyguardWallpaperController2.mRootView;
                            if (viewGroup != null) {
                                viewGroup.addView(keyguardTransitionWallpaper);
                            }
                        }
                        keyguardWallpaperController2.mTransitionView.update();
                    }
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("scaleView@onAnimationStart: mIsScreenOffAnimation = "), SystemUIWallpaper.this.mIsScreenOffAnimation, "SystemUIWallpaper");
                SystemUIWallpaper systemUIWallpaper = SystemUIWallpaper.this;
                KeyguardWallpaperController.C36634 c36634 = systemUIWallpaper.mTransitionAnimationListener;
                if (c36634 != null) {
                    boolean z4 = systemUIWallpaper.mIsScreenOffAnimation;
                    c36634.getClass();
                    KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                    StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("TransitionAnimationListener@onAnimationStart: isScreenOff = ", z4, ", mDozeParameters.shouldControlScreenOff() = ");
                    KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.this;
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(m49m, keyguardWallpaperController2.mDozeParameters.mControlScreenOffAnimation, "KeyguardWallpaperController");
                    if (((PluginWallpaperManagerImpl) keyguardWallpaperController2.mPluginWallpaperManager).isDynamicWallpaperEnabled() && z4 && keyguardWallpaperController2.mDozeParameters.mControlScreenOffAnimation) {
                        keyguardWallpaperController2.mIsPendingTypeChangeForTransition = true;
                    }
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        };
        this.mIsPreview = z;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mExecutor = executorService;
        this.mWallpaperResultCallback = wallpaperResultCallback;
        this.mWcgConsumer = consumer;
        this.mOccluded = z2;
        this.mIsKeyguardShowing = z3;
        Configuration configuration = ((FrameLayout) this).mContext.getResources().getConfiguration();
        this.mDensity = configuration.densityDpi;
        this.mFontScale = configuration.fontScale;
        updateDisplayInfo();
        preInit();
    }

    public void handleTouchEvent(MotionEvent motionEvent) {
    }

    public void onFingerprintAuthSuccess(boolean z) {
    }

    public void updateBlurState(boolean z) {
    }

    public void onFaceAuthError() {
    }

    public void onUnlock() {
    }

    public void reset() {
    }

    public void update() {
    }
}
