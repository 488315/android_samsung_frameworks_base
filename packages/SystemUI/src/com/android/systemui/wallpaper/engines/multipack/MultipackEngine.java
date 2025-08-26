package com.android.systemui.wallpaper.engines.multipack;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.PluginWallpaper;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.engines.WallpaperAnimator;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.engines.gif.GifEngine;
import com.android.systemui.wallpaper.engines.gif.GifSource;
import com.android.systemui.wallpaper.engines.image.ImageEngine;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.engines.video.VideoEngine;
import com.android.systemui.wallpaper.engines.video.VideoSource;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class MultipackEngine extends WallpaperEngine {
    public final String TAG;
    public final WallpaperEngineCallback mCallback;
    public final CoverWallpaper mCoverWallpaper;
    public final AnonymousClass1 mCoverWallpaperConsumer;
    public final DozeParameters mDozeParameters;
    public final Object mDrawLock;
    public boolean mIsAlive;
    public boolean mIsFirstTransition;
    public boolean mIsVisibilityReported;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public final DelayableExecutor mLongExecutor;
    public int mNextType;
    public final MultipackEngine$$ExternalSyntheticLambda1 mPluginUpdateTask;
    public final PluginWallpaper mPluginWallpaper;
    public final AnonymousClass2 mPluginWallpaperConsumer;
    public Bitmap mPrevScreenshot;
    public int mPrevType;
    private final SettingsHelper mSettingsHelper;
    public WallpaperEngine mSubEngine;
    public SurfaceHolder mSurfaceHolder;
    public final SystemWallpaperColors mSystemWallpaperColors;
    public final WallpaperAnimator mWallpaperAnimator;

    /* renamed from: com.android.systemui.wallpaper.engines.multipack.MultipackEngine$1, reason: invalid class name */
    public class AnonymousClass1 implements Consumer {
        public AnonymousClass1() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Log.i(MultipackEngine.this.TAG, "Cover wallpaper consumer accepted");
            MultipackEngine.this.runAsWorkerThread$1(new MultipackEngine$$ExternalSyntheticLambda1(this, 1));
        }
    }

    /* renamed from: com.android.systemui.wallpaper.engines.multipack.MultipackEngine$2, reason: invalid class name */
    public class AnonymousClass2 implements Consumer {
        public AnonymousClass2() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Log.i(MultipackEngine.this.TAG, "Plugin wallpaper consumer accepted");
            MultipackEngine.this.runAsWorkerThread$1(new MultipackEngine$$ExternalSyntheticLambda1(this, 2));
        }
    }

    public MultipackEngine(WallpaperEngineCallback wallpaperEngineCallback, CoverWallpaper coverWallpaper, PluginWallpaper pluginWallpaper, SettingsHelper settingsHelper, SystemWallpaperColors systemWallpaperColors, DelayableExecutor delayableExecutor, DozeParameters dozeParameters, KeyguardWallpaper keyguardWallpaper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        super(wallpaperEngineCallback);
        this.mDrawLock = new Object();
        this.mPluginUpdateTask = new MultipackEngine$$ExternalSyntheticLambda1(this, 0);
        this.mCoverWallpaperConsumer = new AnonymousClass1();
        this.mPluginWallpaperConsumer = new AnonymousClass2();
        this.mPrevType = -1;
        this.mNextType = -1;
        this.mIsAlive = false;
        this.mIsFirstTransition = true;
        this.mIsVisibilityReported = false;
        this.TAG = "ImageWallpaper_" + getWhich() + "[Multipack]";
        this.mCallback = wallpaperEngineCallback;
        this.mLongExecutor = delayableExecutor;
        this.mCoverWallpaper = coverWallpaper;
        this.mDozeParameters = dozeParameters;
        this.mSettingsHelper = settingsHelper;
        this.mPluginWallpaper = pluginWallpaper;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSystemWallpaperColors = systemWallpaperColors;
        this.mWallpaperAnimator = new WallpaperAnimator(getSurfaceHolder(), ((ImageWallpaper.IntegratedEngine.AnonymousClass2) super.mCallback).getSurfaceControl(), keyguardUpdateMonitor);
    }

    public final boolean canSupportScaleAnimation(WallpaperEngine wallpaperEngine) {
        if (wallpaperEngine == null) {
            return false;
        }
        boolean zIsFlagEnabled = WhichChecker.isFlagEnabled(getWhich(), 2);
        Context appContext = getAppContext();
        int currentUserId = getCurrentUserId();
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        return zIsFlagEnabled && !new LockPatternUtils(appContext).isLockScreenDisabled(currentUserId) && (wallpaperEngine instanceof ImageEngine);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final boolean draw(SurfaceHolder surfaceHolder) {
        synchronized (this.mDrawLock) {
            try {
                WallpaperEngine wallpaperEngine = this.mSubEngine;
                if (wallpaperEngine == null) {
                    return false;
                }
                return wallpaperEngine.draw(surfaceHolder);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public final void initSubEngine(WallpaperEngine wallpaperEngine) {
        wallpaperEngine.onCreate(getSurfaceHolder());
        if (this.mSurfaceHolder != null) {
            if (!(wallpaperEngine instanceof TransitionEngine)) {
                Log.i(this.TAG, "initSubEngine: mPrevType = " + this.mPrevType + ", mNextType = " + this.mNextType);
                this.mPrevType = this.mNextType;
                if (!canSupportScaleAnimation(wallpaperEngine)) {
                    this.mWallpaperAnimator.release();
                }
            }
            wallpaperEngine.onSurfaceCreated(this.mSurfaceHolder);
            Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
            if (surfaceFrame != null && !surfaceFrame.isEmpty()) {
                wallpaperEngine.onSurfaceChanged(this.mSurfaceHolder, 0, surfaceFrame.width(), surfaceFrame.height());
            }
            if (this.mIsVisibilityReported) {
                wallpaperEngine.onSurfaceRedrawNeeded(this.mSurfaceHolder);
                wallpaperEngine.onVisibilityChanged(isVisible());
            }
        }
    }

    public final boolean isScaleAnimationEnabled() {
        return canSupportScaleAnimation(this.mSubEngine) && !willShowAodWithWallpaper$1();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onCommand(str, i, i2, i3, bundle, z);
        }
        if (isScaleAnimationEnabled()) {
            str.getClass();
            WallpaperAnimator wallpaperAnimator = this.mWallpaperAnimator;
            if (str.equals("samsung.android.wallpaper.resume")) {
                Log.d(wallpaperAnimator.TAG, "changeToDownScaleImmediately");
                wallpaperAnimator.release();
                wallpaperAnimator.onTransitionScaleChanged(1.0f);
            } else if (str.equals("android.wallpaper.keyguardgoingaway")) {
                wallpaperAnimator.mKeyguardState = false;
            }
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(SurfaceHolder surfaceHolder) {
        Log.i(this.TAG, "onCreate");
        this.mIsAlive = true;
        int which = getWhich();
        if (!WhichChecker.isWatchFace(which) && !WhichChecker.isVirtualDisplay(which)) {
            if (WhichChecker.isFlagEnabled(getWhich(), 2)) {
                AnonymousClass2 anonymousClass2 = this.mPluginWallpaperConsumer;
                PluginWallpaperController pluginWallpaperController = (PluginWallpaperController) this.mPluginWallpaper;
                pluginWallpaperController.setWallpaperUpdateConsumer(which, anonymousClass2);
                updateWallpaperIfReady(pluginWallpaperController.mWallpaperId[PluginWallpaperController.getScreen(getWhich())]);
                return;
            }
            return;
        }
        AnonymousClass1 anonymousClass1 = this.mCoverWallpaperConsumer;
        CoverWallpaperController coverWallpaperController = (CoverWallpaperController) this.mCoverWallpaper;
        coverWallpaperController.getClass();
        Log.d("CoverWallpaperController", "setWallpaperUpdateConsumer: consumer = " + anonymousClass1);
        coverWallpaperController.mWallpaperConsumer = anonymousClass1;
        updateWallpaperIfReady(coverWallpaperController.mWallpaperId);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
        Log.i(this.TAG, "onDestroy");
        this.mIsAlive = false;
        Handler threadHandler = ImageWallpaper.this.mWorker.getThreadHandler();
        if (threadHandler != null) {
            threadHandler.removeCallbacks(this.mPluginUpdateTask);
        }
        this.mWallpaperAnimator.release();
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onDestroy();
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDisplayStateChanged(final DisplayState displayState, final DisplayState displayState2) {
        runAsWorkerThread$1(new Runnable() { // from class: com.android.systemui.wallpaper.engines.multipack.MultipackEngine$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MultipackEngine multipackEngine = this.f$0;
                DisplayState displayState3 = displayState;
                DisplayState displayState4 = displayState2;
                WallpaperEngine wallpaperEngine = multipackEngine.mSubEngine;
                if (wallpaperEngine != null) {
                    wallpaperEngine.onDisplayStateChanged(displayState3, displayState4);
                }
            }
        });
        if (isScaleAnimationEnabled()) {
            this.mWallpaperAnimator.onDisplayStateChanged(displayState, getWhich(), isVisible(), willShowAodWithWallpaper$1(), true);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final ScreenshotResults onGetScreenshot(ScreenshotOptions screenshotOptions) {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            return wallpaperEngine.onGetScreenshot(screenshotOptions);
        }
        return null;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onStartedWakingUp() {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onStartedWakingUp();
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mSurfaceHolder = surfaceHolder;
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onSurfaceChanged(surfaceHolder, i, i2, i3);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onSurfaceCreated(surfaceHolder);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onSurfaceDestroyed(surfaceHolder);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onSurfaceRedrawNeeded(surfaceHolder);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSwitchDisplayChanged(boolean z) {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onSwitchDisplayChanged(z);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        this.mIsVisibilityReported = true;
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onVisibilityChanged(z);
        }
        if (isScaleAnimationEnabled()) {
            this.mWallpaperAnimator.onEngineVisibilityChanged(getWhich(), z, willShowAodWithWallpaper$1(), true);
        }
    }

    public final void runAsWorkerThread$1(Runnable runnable) {
        Handler threadHandler = ImageWallpaper.this.mWorker.getThreadHandler();
        if (threadHandler == null) {
            return;
        }
        threadHandler.post(runnable);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final boolean shouldWaitForEngineShown() {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            return wallpaperEngine.shouldWaitForEngineShown();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ed  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateWallpaper() {
        int wallpaperType;
        final WallpaperEngine videoEngine;
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine instanceof VideoEngine) {
            ((VideoEngine) wallpaperEngine).mVideoController.pause(true);
        }
        if (!this.mIsAlive) {
            Log.w(this.TAG, "changeWallpaper: engine destroyed");
            return;
        }
        if (this.mSubEngine != null) {
            if (!this.mIsFirstTransition && willShowAodWithWallpaper$1()) {
                ScreenshotResults screenshotResultsOnGetScreenshot = this.mSubEngine.onGetScreenshot(new ScreenshotOptions("prev"));
                this.mPrevScreenshot = screenshotResultsOnGetScreenshot != null ? screenshotResultsOnGetScreenshot.mBitmap : null;
            }
            this.mSubEngine.onDestroy();
        }
        int which = getWhich();
        boolean zIsFlagEnabled = WhichChecker.isFlagEnabled(which, 1);
        String str = this.TAG;
        CoverWallpaper coverWallpaper = this.mCoverWallpaper;
        if (zIsFlagEnabled) {
            CoverWallpaperController coverWallpaperController = (CoverWallpaperController) coverWallpaper;
            if (coverWallpaperController.isCoverWallpaperRequired()) {
                wallpaperType = coverWallpaperController.getWallpaperType();
            } else if (WhichChecker.isFlagEnabled(which, 2)) {
                PluginWallpaperController pluginWallpaperController = (PluginWallpaperController) this.mPluginWallpaper;
                if (pluginWallpaperController.isPluginWallpaperRequired(which)) {
                    wallpaperType = pluginWallpaperController.getWallpaperType(which);
                } else {
                    Log.e(str, "getCurrentWallpaperType: Not ready");
                    wallpaperType = -1;
                }
            }
        }
        this.mNextType = wallpaperType;
        TooltipPopup$$ExternalSyntheticOutline0.m(this.mNextType, str, new StringBuilder("createNextEngine: mNextType = "));
        int i = this.mNextType;
        WallpaperEngineCallback wallpaperEngineCallback = this.mCallback;
        if (i == 2 || i == 8) {
            videoEngine = new VideoEngine(new VideoSource(getAppContext(), getWhich(), 2, getCurrentUserId(), this.mCoverWallpaper, this.mPluginWallpaper), wallpaperEngineCallback, this.mKeyguardUpdateMonitor);
        } else if (i == 12) {
            videoEngine = new GifEngine(new GifSource(getAppContext(), getWhich(), coverWallpaper), wallpaperEngineCallback);
        } else if (i != 13) {
            if (i != 22) {
                if (i != 23) {
                    videoEngine = new ImageEngine(new ImageSource(getAppContext(), this.mCoverWallpaper, this.mPluginWallpaper, getWhich(), getCurrentUserId(), ImageWallpaper.IntegratedEngine.this.getDisplayId()), this.mCallback, this.mSettingsHelper, this.mSystemWallpaperColors, this.mLongExecutor, this.mKeyguardWallpaper, this.mKeyguardUpdateMonitor, this.mDozeParameters);
                }
            }
        }
        if (!canSupportScaleAnimation(videoEngine)) {
            this.mWallpaperAnimator.release();
        }
        if (willShowAodWithWallpaper$1()) {
            if (!this.mIsFirstTransition && this.mPrevScreenshot != null) {
                ScreenshotResults screenshotResultsOnGetScreenshot2 = videoEngine.onGetScreenshot(new ScreenshotOptions("next"));
                TransitionEngine transitionEngine = new TransitionEngine(this.mCallback, this.mPrevScreenshot, screenshotResultsOnGetScreenshot2 != null ? screenshotResultsOnGetScreenshot2.mBitmap : null, new Runnable() { // from class: com.android.systemui.wallpaper.engines.multipack.MultipackEngine$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MultipackEngine multipackEngine = this.f$0;
                        WallpaperEngine wallpaperEngine2 = videoEngine;
                        multipackEngine.mSubEngine = wallpaperEngine2;
                        multipackEngine.initSubEngine(wallpaperEngine2);
                    }
                });
                this.mSubEngine = transitionEngine;
                initSubEngine(transitionEngine);
                return;
            }
            Log.i(this.TAG, "handleTransition: mIsFirstTransition = " + this.mIsFirstTransition);
            this.mSubEngine = videoEngine;
            initSubEngine(videoEngine);
            this.mIsFirstTransition = false;
            return;
        }
        int which2 = getWhich();
        if ((WhichChecker.isFlagEnabled(which2, 2) || WhichChecker.isVirtualDisplay(which2)) && ((videoEngine instanceof VideoEngine) || (videoEngine instanceof GifEngine))) {
            try {
                Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
                if (surfaceFrame == null || surfaceFrame.isEmpty()) {
                    Log.i(this.TAG, "makeSurfaceBlack: empty surface frame. frame=" + surfaceFrame);
                } else {
                    int iWidth = surfaceFrame.width();
                    int iHeight = surfaceFrame.height();
                    Log.i(this.TAG, "makeSurfaceBlack: width == " + iWidth + ", height = " + iHeight);
                    Paint paint = new Paint();
                    paint.setColor(-16777216);
                    Surface surface = this.mSurfaceHolder.getSurface();
                    if (surface == null || !surface.isValid()) {
                        Log.w(this.TAG, "makeSurfaceBlack: surface = " + surface);
                    } else {
                        synchronized (this.mDrawLock) {
                            try {
                                Canvas canvasLockHardwareCanvas = surface.lockHardwareCanvas();
                                if (canvasLockHardwareCanvas == null) {
                                    Log.w(this.TAG, "makeSurfaceBlack: canvas is null");
                                } else {
                                    canvasLockHardwareCanvas.drawRect(0.0f, 0.0f, iWidth, iHeight, paint);
                                    surface.unlockCanvasAndPost(canvasLockHardwareCanvas);
                                }
                            } finally {
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.w(this.TAG, "makeSurfaceBlack: e = " + e, e);
            }
        }
        this.mSubEngine = videoEngine;
        initSubEngine(videoEngine);
    }

    public final void updateWallpaperIfReady(int i) {
        int wallpaperId = WallpaperManager.getInstance(getAppContext()).getWallpaperId(getSourceWhich());
        String strM = ListImplementation$$ExternalSyntheticOutline0.m(i, wallpaperId, "updateWallpaperIfReady: wallpaperId = ", ", wallpaperManagerId = ");
        String str = this.TAG;
        Log.i(str, strM);
        if (i == wallpaperId) {
            updateWallpaper();
            return;
        }
        int which = getWhich();
        if (WhichChecker.isFlagEnabled(which, 2)) {
            boolean zIsPluginWallpaperRequired = ((PluginWallpaperController) this.mPluginWallpaper).isPluginWallpaperRequired(which);
            AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("updateWallpaperIfReady: isPluginLockReady = ", str, zIsPluginWallpaperRequired);
            if (zIsPluginWallpaperRequired) {
                updateWallpaper();
            }
        }
    }

    public final boolean willShowAodWithWallpaper$1() {
        return WallpaperUtils.isShowWallpaperOnAodEnabled(getWhich() & 60) && this.mDozeParameters.mControlScreenOffAnimation;
    }
}
