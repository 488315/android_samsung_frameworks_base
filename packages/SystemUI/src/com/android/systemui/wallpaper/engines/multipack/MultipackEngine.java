package com.android.systemui.wallpaper.engines.multipack;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
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
import com.android.systemui.wallpaper.engines.image.ImageEngine;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        boolean isFlagEnabled = WhichChecker.isFlagEnabled(getWhich(), 2);
        Context appContext = getAppContext();
        int currentUserId = getCurrentUserId();
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        return isFlagEnabled && !new LockPatternUtils(appContext).isLockScreenDisabled(currentUserId) && (wallpaperEngine instanceof ImageEngine);
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
                MultipackEngine multipackEngine = MultipackEngine.this;
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0166  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateWallpaper() {
        /*
            Method dump skipped, instructions count: 559
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.multipack.MultipackEngine.updateWallpaper():void");
    }

    public final void updateWallpaperIfReady(int i) {
        int wallpaperId = WallpaperManager.getInstance(getAppContext()).getWallpaperId(getSourceWhich());
        String m = ListImplementation$$ExternalSyntheticOutline0.m(i, wallpaperId, "updateWallpaperIfReady: wallpaperId = ", ", wallpaperManagerId = ");
        String str = this.TAG;
        Log.i(str, m);
        if (i == wallpaperId) {
            updateWallpaper();
            return;
        }
        int which = getWhich();
        if (WhichChecker.isFlagEnabled(which, 2)) {
            boolean isPluginWallpaperRequired = ((PluginWallpaperController) this.mPluginWallpaper).isPluginWallpaperRequired(which);
            AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("updateWallpaperIfReady: isPluginLockReady = ", str, isPluginWallpaperRequired);
            if (isPluginWallpaperRequired) {
                updateWallpaper();
            }
        }
    }

    public final boolean willShowAodWithWallpaper$1() {
        return WallpaperUtils.isShowWallpaperOnAodEnabled(getWhich() & 60) && this.mDozeParameters.mControlScreenOffAnimation;
    }
}
