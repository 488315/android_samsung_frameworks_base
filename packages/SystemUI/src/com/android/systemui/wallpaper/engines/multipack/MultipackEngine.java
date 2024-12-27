package com.android.systemui.wallpaper.engines.multipack;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.SurfaceHolder;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
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
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.engines.WallpaperSource;
import com.android.systemui.wallpaper.engines.gif.GifSource;
import com.android.systemui.wallpaper.engines.image.ImageEngine;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.engines.video.VideoSource;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;

public final class MultipackEngine extends WallpaperEngine {
    public final String TAG;
    public final WallpaperEngineCallback mCallback;
    public final Context mContext;
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
    public WallpaperSource mNextSource;
    public final MultipackEngine$$ExternalSyntheticLambda1 mPluginUpdateTask;
    public final PluginWallpaper mPluginWallpaper;
    public final AnonymousClass2 mPluginWallpaperConsumer;
    private final SettingsHelper mSettingsHelper;
    public WallpaperEngine mSubEngine;
    public SurfaceHolder mSurfaceHolder;
    public final SystemWallpaperColors mSystemWallpaperColors;
    public final Handler mWorkerHandler;

    /* renamed from: com.android.systemui.wallpaper.engines.multipack.MultipackEngine$1, reason: invalid class name */
    public final class AnonymousClass1 implements Consumer {
        public AnonymousClass1() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Log.i(MultipackEngine.this.TAG, "Cover wallpaper consumer accepted");
            MultipackEngine multipackEngine = MultipackEngine.this;
            MultipackEngine$$ExternalSyntheticLambda1 multipackEngine$$ExternalSyntheticLambda1 = new MultipackEngine$$ExternalSyntheticLambda1(this, 1);
            Handler handler = multipackEngine.mWorkerHandler;
            if (handler == null) {
                return;
            }
            handler.postDelayed(multipackEngine$$ExternalSyntheticLambda1, 0L);
        }
    }

    /* renamed from: com.android.systemui.wallpaper.engines.multipack.MultipackEngine$2, reason: invalid class name */
    public final class AnonymousClass2 implements Consumer {
        public AnonymousClass2() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Log.i(MultipackEngine.this.TAG, "Plugin wallpaper consumer accepted");
            MultipackEngine multipackEngine = MultipackEngine.this;
            MultipackEngine$$ExternalSyntheticLambda1 multipackEngine$$ExternalSyntheticLambda1 = new MultipackEngine$$ExternalSyntheticLambda1(this, 2);
            Handler handler = multipackEngine.mWorkerHandler;
            if (handler == null) {
                return;
            }
            handler.postDelayed(multipackEngine$$ExternalSyntheticLambda1, 0L);
        }
    }

    public MultipackEngine(Context context, WallpaperEngineCallback wallpaperEngineCallback, CoverWallpaper coverWallpaper, PluginWallpaper pluginWallpaper, SettingsHelper settingsHelper, SystemWallpaperColors systemWallpaperColors, DelayableExecutor delayableExecutor, DozeParameters dozeParameters, KeyguardWallpaper keyguardWallpaper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        super(wallpaperEngineCallback);
        this.mDrawLock = new Object();
        this.mPluginUpdateTask = new MultipackEngine$$ExternalSyntheticLambda1(this, 0);
        this.mCoverWallpaperConsumer = new AnonymousClass1();
        this.mPluginWallpaperConsumer = new AnonymousClass2();
        this.mIsAlive = false;
        this.mIsVisibilityReported = false;
        this.mIsFirstTransition = true;
        this.TAG = "ImageWallpaper_" + getWhich() + "[Multipack]";
        this.mContext = context;
        this.mCallback = wallpaperEngineCallback;
        this.mLongExecutor = delayableExecutor;
        this.mSettingsHelper = settingsHelper;
        this.mCoverWallpaper = coverWallpaper;
        this.mPluginWallpaper = pluginWallpaper;
        this.mWorkerHandler = ImageWallpaper.this.mWorker.getThreadHandler();
        this.mSystemWallpaperColors = systemWallpaperColors;
        this.mDozeParameters = dozeParameters;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public final ImageEngine createImageEngine() {
        if (!verifyNextSource(0)) {
            Log.e(this.TAG, "createImageEngine: Failed to load proper source.");
            return null;
        }
        return new ImageEngine((ImageSource) this.mNextSource, this.mCallback, this.mSettingsHelper, this.mSystemWallpaperColors, this.mLongExecutor, this.mKeyguardWallpaper);
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

    public final int getCurrentWallpaperType() {
        int which = getWhich();
        if (WhichChecker.isFlagEnabled(which, 1)) {
            CoverWallpaper coverWallpaper = this.mCoverWallpaper;
            if (((CoverWallpaperController) coverWallpaper).isCoverWallpaperRequired()) {
                return ((CoverWallpaperController) coverWallpaper).getWallpaperType();
            }
        }
        if (WhichChecker.isFlagEnabled(which, 2)) {
            PluginWallpaper pluginWallpaper = this.mPluginWallpaper;
            if (((PluginWallpaperController) pluginWallpaper).isPluginWallpaperRequired(which)) {
                return ((PluginWallpaperController) pluginWallpaper).getWallpaperType(which);
            }
        }
        Log.w(this.TAG, "getCurrentWallpaperType: Not ready");
        return -1;
    }

    public final void initSubEngine(WallpaperEngine wallpaperEngine) {
        wallpaperEngine.onCreate(getSurfaceHolder());
        SurfaceHolder surfaceHolder = this.mSurfaceHolder;
        if (surfaceHolder != null) {
            wallpaperEngine.onSurfaceCreated(surfaceHolder);
            Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
            if (surfaceFrame != null && !surfaceFrame.isEmpty()) {
                wallpaperEngine.onSurfaceChanged(this.mSurfaceHolder, 0, surfaceFrame.width(), surfaceFrame.height());
            }
            if (this.mIsVisibilityReported) {
                wallpaperEngine.onSurfaceRedrawNeeded(this.mSurfaceHolder);
                ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) super.mCallback;
                anonymousClass2.getClass();
                int i = ImageWallpaper.IntegratedEngine.$r8$clinit;
                wallpaperEngine.onVisibilityChanged(ImageWallpaper.IntegratedEngine.this.isVisible());
            }
        }
    }

    public final void loadData() {
        int which = getWhich();
        int currentWallpaperType = getCurrentWallpaperType();
        Context appContext = getAppContext();
        String m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(currentWallpaperType, "loadData: wallpaperType = ");
        String str = this.TAG;
        Log.d(str, m);
        if (currentWallpaperType != 0 && currentWallpaperType != 1) {
            if (currentWallpaperType != 2 && currentWallpaperType != 8) {
                if (currentWallpaperType != 12) {
                    if (currentWallpaperType != 13) {
                        switch (currentWallpaperType) {
                            case 21:
                                break;
                            case 22:
                                break;
                            case 23:
                                break;
                            default:
                                Log.e(str, "loadData: Cannot create proper WallpaperSource yet. Use default one.");
                                this.mNextSource = new ImageSource(appContext, this.mCoverWallpaper, this.mPluginWallpaper, which, ImageWallpaper.IntegratedEngine.this.getCurrentUserId(), getDisplayId());
                                break;
                        }
                    }
                }
                this.mNextSource = new GifSource(appContext, which, this.mCoverWallpaper);
                return;
            }
            this.mNextSource = new VideoSource(appContext, which, 2, ImageWallpaper.IntegratedEngine.this.getCurrentUserId(), this.mCoverWallpaper, this.mPluginWallpaper);
            return;
        }
        this.mNextSource = new ImageSource(appContext, this.mCoverWallpaper, this.mPluginWallpaper, which, ImageWallpaper.IntegratedEngine.this.getCurrentUserId(), getDisplayId());
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onCommand(str, i, i2, i3, bundle, z);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(SurfaceHolder surfaceHolder) {
        Log.i(this.TAG, "onCreate");
        this.mIsAlive = true;
        int which = getWhich();
        if (!WhichChecker.isWatchFace(which) && !WhichChecker.isVirtualDisplay(which)) {
            if (WhichChecker.isFlagEnabled(getWhich(), 2)) {
                PluginWallpaperController pluginWallpaperController = (PluginWallpaperController) this.mPluginWallpaper;
                pluginWallpaperController.setWallpaperUpdateConsumer(which, this.mPluginWallpaperConsumer);
                updateWallpaperIfReady(pluginWallpaperController.mWallpaperId[PluginWallpaperController.getScreen(getWhich())]);
                return;
            }
            return;
        }
        CoverWallpaperController coverWallpaperController = (CoverWallpaperController) this.mCoverWallpaper;
        coverWallpaperController.getClass();
        StringBuilder sb = new StringBuilder("setWallpaperUpdateConsumer: consumer = ");
        AnonymousClass1 anonymousClass1 = this.mCoverWallpaperConsumer;
        sb.append(anonymousClass1);
        Log.d("CoverWallpaperController", sb.toString());
        coverWallpaperController.mWallpaperConsumer = anonymousClass1;
        updateWallpaperIfReady(coverWallpaperController.mWallpaperId);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
        Log.d(this.TAG, "onDestroy");
        this.mIsAlive = false;
        Handler handler = this.mWorkerHandler;
        Objects.toString(handler);
        if (handler != null) {
            handler.removeCallbacks(this.mPluginUpdateTask);
        }
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onDestroy();
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDisplayStateChanged(DisplayState displayState, DisplayState displayState2) {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            wallpaperEngine.onDisplayStateChanged(displayState, displayState2);
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
    }

    public final boolean shouldTransition() {
        int which = getWhich();
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        int i = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1);
        return ((i == 1 || i == 2) ? WhichChecker.isFlagEnabled(which, 4) : i != 3 ? false : WhichChecker.isFlagEnabled(which, 16)) && this.mDozeParameters.mControlScreenOffAnimation && this.mSettingsHelper.isAODEnabled();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final boolean shouldWaitForEngineShown() {
        WallpaperEngine wallpaperEngine = this.mSubEngine;
        if (wallpaperEngine != null) {
            return wallpaperEngine.shouldWaitForEngineShown();
        }
        return false;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateWallpaper() {
        /*
            Method dump skipped, instructions count: 432
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.multipack.MultipackEngine.updateWallpaper():void");
    }

    public final void updateWallpaperIfReady(int i) {
        int wallpaperId = WallpaperManager.getInstance(this.mContext).getWallpaperId(getSourceWhich());
        String m = HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i, wallpaperId, "updateWallpaperIfReady: wallpaperId = ", ", wallpaperManagerId = ");
        String str = this.TAG;
        Log.d(str, m);
        if (i == wallpaperId) {
            updateWallpaper();
            return;
        }
        int which = getWhich();
        if (WhichChecker.isFlagEnabled(which, 2)) {
            boolean isPluginWallpaperRequired = ((PluginWallpaperController) this.mPluginWallpaper).isPluginWallpaperRequired(which);
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("updateWallpaperIfReady: isPluginLockReady = ", str, isPluginWallpaperRequired);
            if (isPluginWallpaperRequired) {
                updateWallpaper();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001f, code lost:
    
        if ((r3.mNextSource instanceof com.android.systemui.wallpaper.engines.image.ImageSource) != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0021, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0038, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0027, code lost:
    
        if ((r3.mNextSource instanceof com.android.systemui.wallpaper.engines.gif.GifSource) != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x002e, code lost:
    
        if ((r3.mNextSource instanceof com.android.systemui.wallpaper.engines.video.VideoSource) != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0035, code lost:
    
        if ((r3.mNextSource instanceof com.android.systemui.wallpaper.engines.image.ImageSource) != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean verifyNextSource(int r4) {
        /*
            r3 = this;
            int r0 = r3.getCurrentWallpaperType()
            r1 = 1
            if (r0 == 0) goto L31
            if (r0 == r1) goto L31
            r2 = 2
            if (r0 == r2) goto L2a
            r2 = 8
            if (r0 == r2) goto L2a
            r2 = 12
            if (r0 == r2) goto L23
            r2 = 13
            if (r0 == r2) goto L2a
            switch(r0) {
                case 21: goto L31;
                case 22: goto L23;
                case 23: goto L2a;
                default: goto L1b;
            }
        L1b:
            com.android.systemui.wallpaper.engines.WallpaperSource r0 = r3.mNextSource
            boolean r0 = r0 instanceof com.android.systemui.wallpaper.engines.image.ImageSource
            if (r0 == 0) goto L38
        L21:
            r0 = r1
            goto L39
        L23:
            com.android.systemui.wallpaper.engines.WallpaperSource r0 = r3.mNextSource
            boolean r0 = r0 instanceof com.android.systemui.wallpaper.engines.gif.GifSource
            if (r0 == 0) goto L38
            goto L21
        L2a:
            com.android.systemui.wallpaper.engines.WallpaperSource r0 = r3.mNextSource
            boolean r0 = r0 instanceof com.android.systemui.wallpaper.engines.video.VideoSource
            if (r0 == 0) goto L38
            goto L21
        L31:
            com.android.systemui.wallpaper.engines.WallpaperSource r0 = r3.mNextSource
            boolean r0 = r0 instanceof com.android.systemui.wallpaper.engines.image.ImageSource
            if (r0 == 0) goto L38
            goto L21
        L38:
            r0 = 0
        L39:
            if (r0 != 0) goto L46
            if (r4 >= r1) goto L46
            r3.loadData()
            int r4 = r4 + r1
            boolean r3 = r3.verifyNextSource(r4)
            return r3
        L46:
            java.lang.String r4 = "verifyNextSource: Returns "
            java.lang.String r4 = com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m(r4, r0)
            java.lang.String r3 = r3.TAG
            android.util.Log.d(r3, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.multipack.MultipackEngine.verifyNextSource(int):boolean");
    }
}
