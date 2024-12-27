package com.android.systemui.wallpapers;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BLASTBufferQueue;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Settings;
import android.service.wallpaper.WallpaperService;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.MathUtils;
import android.util.Size;
import android.view.Choreographer;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.IRotationWatcher;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.PluginWallpaper;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.engines.gif.GifEngine;
import com.android.systemui.wallpaper.engines.gif.GifSource;
import com.android.systemui.wallpaper.engines.image.ImageEngine;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.engines.multipack.MultipackEngine;
import com.android.systemui.wallpaper.engines.theme.AnimatedEngine;
import com.android.systemui.wallpaper.engines.theme.MotionEngine;
import com.android.systemui.wallpaper.engines.video.VideoController;
import com.android.systemui.wallpaper.engines.video.VideoEngine;
import com.android.systemui.wallpaper.engines.video.VideoSource;
import com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.android.systemui.wallpapers.WallpaperLocalColorExtractor;
import com.android.wm.shell.common.DisplayController;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperEngineManager;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ImageWallpaper extends LiveWallpaperService {
    public static final RectF LOCAL_COLOR_BOUNDS = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    public final CoverWallpaper mCoverWallpaper;
    public final DozeParameters mDozeParameters;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public final WallpaperLogger mLogger;
    public final DelayableExecutor mLongExecutor;
    public final Handler mMainThreadHandler;
    public Bitmap mMiniBitmap;
    public final PluginWallpaper mPluginWallpaper;
    public int mPluginWallpaperType;
    private final SettingsHelper mSettingsHelper;
    public int mSubWallpaperType;
    public final SystemWallpaperColors mSystemWallpaperColors;
    public HandlerThread mWorker;
    public final ArrayList mLocalColorsToAdd = new ArrayList();
    public final ArraySet mColorAreas = new ArraySet();
    public volatile int mPages = 1;
    public boolean mPagesComputed = false;
    public final HashMap mCanvasEngineList = new HashMap();
    public final ExecutorService mKeygurdEventExecutor = Executors.newFixedThreadPool(2);
    private SettingsHelper.OnChangedCallback mSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.wallpapers.ImageWallpaper.3
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (Settings.System.getUriFor(SettingsHelper.INDEX_DARK_FILTER_WALLPAPER).equals(uri)) {
                Log.i("ImageWallpaper", " Apply Dark mode option changed");
                RectF rectF = ImageWallpaper.LOCAL_COLOR_BOUNDS;
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                Iterator it = LiveWallpaperEngineManager.getInstance(imageWallpaper.getApplicationContext()).getEngines().iterator();
                while (it.hasNext()) {
                    LiveWallpaperService.BaseEngine baseEngine = (LiveWallpaperService.BaseEngine) it.next();
                    if ((baseEngine instanceof IntegratedEngine) && !baseEngine.isPreview()) {
                        Log.d("ImageWallpaper", "onChangeApplyDark : notify change to BaseEngine. semGetWallpaperFlags() = " + baseEngine.semGetWallpaperFlags());
                        ((IntegratedEngine) baseEngine).mSubEngine.onApplyDarkModeDimSettingChanged();
                    }
                }
                for (Map.Entry entry : imageWallpaper.mCanvasEngineList.entrySet()) {
                    CanvasEngine canvasEngine = (CanvasEngine) entry.getValue();
                    if (canvasEngine != null) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(((Integer) entry.getKey()).intValue(), "onChangeApplyDark : notify the change to CanvasEngine. Engine displayId = ", "ImageWallpaper");
                        canvasEngine.drawFrameSynchronized(canvasEngine.mHelper.getCurrentWhich(), new Rect(canvasEngine.mSurfaceHolder.getSurfaceFrame()));
                    }
                }
            }
        }
    };

    /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ WakefulnessLifecycle.Observer val$observer;

        public AnonymousClass1(ImageWallpaper imageWallpaper, WakefulnessLifecycle.Observer observer) {
            this.val$observer = observer;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ((WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class)).addObserver(this.val$observer);
        }
    }

    /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ WakefulnessLifecycle.Observer val$observer;

        public AnonymousClass2(ImageWallpaper imageWallpaper, WakefulnessLifecycle.Observer observer) {
            this.val$observer = observer;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ((WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class)).removeObserver(this.val$observer);
        }
    }

    public class BaseEngine extends LiveWallpaperService.BaseEngine {
        public int mWhich;

        public BaseEngine(int i) {
            super();
            this.mWhich = i;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onWallpaperFlagsChanged(int i) {
            this.mWhich = i;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onWallpaperFlagsChanged: which = ", "ImageWallpaper");
        }

        public boolean supportsLocalColorExtraction() {
            return true;
        }
    }

    public final class IntegratedEngine extends BaseEngine {
        public static final /* synthetic */ int $r8$clinit = 0;
        public String TAG;
        public final AnonymousClass1 mKeyguardWallpaperEventListener;
        public WallpaperEngine mSubEngine;
        public SurfaceHolder mSurfaceHolder;
        public final AnonymousClass3 mWakefulnessObserver;
        public final AnonymousClass2 mWallpaperEngineCallback;
        public WallpaperManager mWallpaperManager;
        public final int mWallpaperType;

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$IntegratedEngine$1, reason: invalid class name */
        public final class AnonymousClass1 {
            public AnonymousClass1() {
            }

            public final Bundle onEventReceived(final int i, final Bundle bundle) {
                IntegratedEngine integratedEngine = IntegratedEngine.this;
                if (!WhichChecker.isSystemAndLock(integratedEngine.mWhich) && !WhichChecker.isFlagEnabled(integratedEngine.mWhich, 2)) {
                    RecyclerView$$ExternalSyntheticOutline0.m(integratedEngine.mWhich, integratedEngine.TAG, new StringBuilder("apply: Event received from KeyguardWallpaper. mWhich = "));
                    return null;
                }
                if (i != 605 && i != 606) {
                    if (i == 615 || i == 616) {
                        try {
                            return (Bundle) ImageWallpaper.this.mKeygurdEventExecutor.submit(new Callable(i, bundle) { // from class: com.android.systemui.wallpapers.ImageWallpaper$IntegratedEngine$1$$ExternalSyntheticLambda0
                                public final /* synthetic */ int f$1;

                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    int currentPosition;
                                    VideoController.PlayerSession playerSession;
                                    ImageWallpaper.IntegratedEngine.AnonymousClass1 anonymousClass1 = ImageWallpaper.IntegratedEngine.AnonymousClass1.this;
                                    int i2 = this.f$1;
                                    ImageWallpaper.IntegratedEngine integratedEngine2 = ImageWallpaper.IntegratedEngine.this;
                                    int i3 = ImageWallpaper.IntegratedEngine.$r8$clinit;
                                    integratedEngine2.getClass();
                                    Bundle bundle2 = new Bundle();
                                    if (i2 == 615 && integratedEngine2.mWallpaperType == 8) {
                                        WallpaperEngine wallpaperEngine = integratedEngine2.mSubEngine;
                                        if (wallpaperEngine instanceof VideoEngine) {
                                            VideoController videoController = ((VideoEngine) wallpaperEngine).mVideoController;
                                            synchronized (videoController.mLock) {
                                                try {
                                                    currentPosition = (videoController.mIsReleased || (playerSession = videoController.mActiveSession) == null) ? 0 : playerSession.getCurrentPosition();
                                                    Log.i(videoController.TAG, "getCurrentPosition: released");
                                                } finally {
                                                }
                                            }
                                            bundle2.putInt("current_position", currentPosition);
                                        }
                                    }
                                    return bundle2;
                                }
                            }).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return null;
                        } catch (ExecutionException e2) {
                            e2.printStackTrace();
                            return null;
                        }
                    }
                    if (i != 724 && i != 732) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "apply: event = ", integratedEngine.TAG);
                        return null;
                    }
                }
                ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda5 imageWallpaper$CanvasEngine$$ExternalSyntheticLambda5 = new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda5(this, i, bundle);
                HandlerThread handlerThread = ImageWallpaper.this.mWorker;
                if (handlerThread == null) {
                    Log.w(integratedEngine.TAG, "runAsWorkerThread: mWorker is null.");
                } else {
                    handlerThread.getThreadHandler().post(imageWallpaper$CanvasEngine$$ExternalSyntheticLambda5);
                }
                return null;
            }
        }

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$IntegratedEngine$2, reason: invalid class name */
        public final class AnonymousClass2 implements WallpaperEngineCallback {
            public BLASTBufferQueue mBbqOfPendingTransactionRequest;
            public final AnonymousClass1 mChoreographerFrameCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.wallpapers.ImageWallpaper.IntegratedEngine.2.1
                @Override // android.view.Choreographer.FrameCallback
                public final void doFrame(long j) {
                    IntegratedEngine integratedEngine = IntegratedEngine.this;
                    if (integratedEngine.mSubEngine.draw(integratedEngine.mSurfaceHolder)) {
                        Choreographer.getInstance().postFrameCallback(this);
                    }
                }
            };

            public AnonymousClass2() {
            }
        }

        public IntegratedEngine(int i, int i2) {
            super(i);
            this.mKeyguardWallpaperEventListener = new AnonymousClass1();
            this.mWallpaperEngineCallback = new AnonymousClass2();
            this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.wallpapers.ImageWallpaper.IntegratedEngine.3
                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public final void onStartedWakingUp() {
                    IntegratedEngine.this.mSubEngine.onStartedWakingUp();
                }
            };
            this.mWallpaperType = i2;
        }

        public final ImageEngine createImageEngine(int i) {
            ImageWallpaper imageWallpaper = ImageWallpaper.this;
            ImageSource imageSource = new ImageSource(imageWallpaper, imageWallpaper.mCoverWallpaper, imageWallpaper.mPluginWallpaper, i, getCurrentUserId(), getDisplayId());
            AnonymousClass2 anonymousClass2 = this.mWallpaperEngineCallback;
            SettingsHelper settingsHelper = ImageWallpaper.this.mSettingsHelper;
            ImageWallpaper imageWallpaper2 = ImageWallpaper.this;
            return new ImageEngine(imageSource, anonymousClass2, settingsHelper, imageWallpaper2.mSystemWallpaperColors, imageWallpaper2.mLongExecutor, imageWallpaper2.mKeyguardWallpaper);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            this.mSubEngine.dump(str, fileDescriptor, printWriter, strArr);
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final Bundle onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
            if (!"samsung.android.wallpaper.blocktoucharea".equals(str)) {
                this.mSubEngine.onCommand(str, i, i2, i3, bundle, z);
            }
            return super.onCommand(str, i, i2, i3, bundle, z);
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final void onCreate(SurfaceHolder surfaceHolder) {
            WallpaperEngine createImageEngine;
            super.onCreate(surfaceHolder);
            int semGetWallpaperFlags = semGetWallpaperFlags();
            String m = LazyListMeasuredItem$$ExternalSyntheticOutline0.m(semGetWallpaperFlags, "ImageWallpaper_", "[Integrated]");
            this.TAG = m;
            Log.i(m, "onCreate");
            this.mSurfaceHolder = surfaceHolder;
            this.mWallpaperManager = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext());
            if (WhichChecker.isSystemAndLock(semGetWallpaperFlags) || WhichChecker.isFlagEnabled(semGetWallpaperFlags, 2)) {
                KeyguardWallpaper keyguardWallpaper = ImageWallpaper.this.mKeyguardWallpaper;
                int i = this.mWhich;
                ((HashMap) ((KeyguardWallpaperController) keyguardWallpaper).mEventListeners).put(Integer.valueOf(WhichChecker.isFlagEnabled(i, 16) ? 1 : 0), this.mKeyguardWallpaperEventListener);
            }
            setShowForAllUsers(true);
            setFixedSizeAllowed(true);
            int i2 = this.mWallpaperType;
            if (i2 == 0) {
                createImageEngine = createImageEngine(semGetWallpaperFlags);
            } else if (i2 != 1) {
                if (i2 != 3) {
                    if (i2 == 4) {
                        createImageEngine = new AnimatedEngine(ImageWallpaper.this.getBaseContext(), this.mWallpaperEngineCallback);
                    } else if (i2 == 5) {
                        createImageEngine = new GifEngine(new GifSource(ImageWallpaper.this.getBaseContext(), semGetWallpaperFlags, ImageWallpaper.this.mCoverWallpaper), this.mWallpaperEngineCallback);
                    } else if (i2 == 8) {
                        ImageWallpaper imageWallpaper = ImageWallpaper.this;
                        int currentUserId = getCurrentUserId();
                        ImageWallpaper imageWallpaper2 = ImageWallpaper.this;
                        createImageEngine = new VideoEngine(new VideoSource(imageWallpaper, semGetWallpaperFlags, 1, currentUserId, imageWallpaper2.mCoverWallpaper, imageWallpaper2.mPluginWallpaper), this.mWallpaperEngineCallback, ImageWallpaper.this.mKeyguardUpdateMonitor);
                    } else if (i2 != 1000) {
                        Log.e(this.TAG, "createEngine: Unknown wallpaper type = " + this.mWallpaperType);
                        createImageEngine = createImageEngine(semGetWallpaperFlags);
                    }
                }
                Context baseContext = ImageWallpaper.this.getBaseContext();
                AnonymousClass2 anonymousClass2 = this.mWallpaperEngineCallback;
                ImageWallpaper imageWallpaper3 = ImageWallpaper.this;
                CoverWallpaper coverWallpaper = imageWallpaper3.mCoverWallpaper;
                PluginWallpaper pluginWallpaper = imageWallpaper3.mPluginWallpaper;
                SettingsHelper settingsHelper = imageWallpaper3.mSettingsHelper;
                ImageWallpaper imageWallpaper4 = ImageWallpaper.this;
                createImageEngine = new MultipackEngine(baseContext, anonymousClass2, coverWallpaper, pluginWallpaper, settingsHelper, imageWallpaper4.mSystemWallpaperColors, imageWallpaper4.mLongExecutor, imageWallpaper4.mDozeParameters, imageWallpaper4.mKeyguardWallpaper, imageWallpaper4.mKeyguardUpdateMonitor);
            } else {
                createImageEngine = new MotionEngine(ImageWallpaper.this.getBaseContext(), this.mWallpaperEngineCallback);
            }
            this.mSubEngine = createImageEngine;
            createImageEngine.onCreate(surfaceHolder);
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper5 = ImageWallpaper.this;
                imageWallpaper5.mMainThreadHandler.post(new AnonymousClass1(imageWallpaper5, this.mWakefulnessObserver));
            }
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final void onDestroy() {
            super.onDestroy();
            ((HashMap) ((KeyguardWallpaperController) ImageWallpaper.this.mKeyguardWallpaper).mEventListeners).remove(Integer.valueOf(WhichChecker.isFlagEnabled(this.mWhich, 16) ? 1 : 0));
            ImageWallpaper.this.mCanvasEngineList.remove(Integer.valueOf(getDisplayId()), this);
            this.mSubEngine.onDestroy();
            int semGetWallpaperFlags = semGetWallpaperFlags();
            if (ImageWallpaper.this.mCoverWallpaper != null && (WhichChecker.isWatchFace(semGetWallpaperFlags) || WhichChecker.isVirtualDisplay(semGetWallpaperFlags))) {
                ImageWallpaper.this.mCoverWallpaper.getClass();
            }
            if (ImageWallpaper.this.mPluginWallpaper != null && WhichChecker.isFlagEnabled(semGetWallpaperFlags, 2)) {
                PluginWallpaperController pluginWallpaperController = (PluginWallpaperController) ImageWallpaper.this.mPluginWallpaper;
                pluginWallpaperController.getClass();
                Log.d("PluginWallpaperController", "onWallpaperDestroyed: which = " + semGetWallpaperFlags);
                if ((semGetWallpaperFlags & 1) != 1) {
                    int screen = PluginWallpaperController.getScreen(semGetWallpaperFlags);
                    int wallpaperId = pluginWallpaperController.mWallpaperManager.getWallpaperId(WhichChecker.getSourceWhich(semGetWallpaperFlags));
                    StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(wallpaperId, screen, "onWallpaperDestroyed: wallpaperId = ", ", mWallpaperId[", "] = ");
                    int[] iArr = pluginWallpaperController.mWallpaperId;
                    RecyclerView$$ExternalSyntheticOutline0.m(iArr[screen], "PluginWallpaperController", m);
                    if (wallpaperId != iArr[screen]) {
                        pluginWallpaperController.mWallpaperConsumers.put(screen, null);
                        iArr[screen] = wallpaperId;
                    }
                }
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mMainThreadHandler.post(new AnonymousClass2(imageWallpaper, this.mWakefulnessObserver));
            }
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine
        public final void onDisplayStateChanged(DisplayState displayState, DisplayState displayState2) {
            this.mSubEngine.onDisplayStateChanged(displayState, displayState2);
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine
        public final ScreenshotResults onGetScreenshot(ScreenshotOptions screenshotOptions) {
            return this.mSubEngine.onGetScreenshot(screenshotOptions);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            super.onSurfaceChanged(surfaceHolder, i, i2, i3);
            this.mSubEngine.onSurfaceChanged(surfaceHolder, i, i2, i3);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            super.onSurfaceCreated(surfaceHolder);
            this.mSubEngine.onSurfaceCreated(surfaceHolder);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            super.onSurfaceDestroyed(surfaceHolder);
            this.mSubEngine.onSurfaceDestroyed(surfaceHolder);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            super.onSurfaceRedrawNeeded(surfaceHolder);
            this.mSubEngine.onSurfaceRedrawNeeded(surfaceHolder);
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine
        public final void onSwitchDisplayChanged(boolean z) {
            this.mSubEngine.onSwitchDisplayChanged(z);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onVisibilityChanged(boolean z) {
            super.onVisibilityChanged(z);
            this.mSubEngine.onVisibilityChanged(z);
        }

        @Override // com.android.systemui.wallpapers.ImageWallpaper.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final void onWallpaperFlagsChanged(int i) {
            super.onWallpaperFlagsChanged(i);
            int semGetWallpaperFlags = semGetWallpaperFlags();
            this.TAG = LazyListMeasuredItem$$ExternalSyntheticOutline0.m(semGetWallpaperFlags, "ImageWallpaper_", "[Integrated]");
            this.mSubEngine.onWhichChanged(semGetWallpaperFlags);
            if (WhichChecker.isFlagEnabled(semGetWallpaperFlags, 1)) {
                KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) ImageWallpaper.this.mKeyguardWallpaper;
                boolean isFlagEnabled = WhichChecker.isFlagEnabled(semGetWallpaperFlags, 16);
                ((HashMap) keyguardWallpaperController.mEventListeners).remove(Integer.valueOf(isFlagEnabled ? 1 : 0));
                return;
            }
            KeyguardWallpaper keyguardWallpaper = ImageWallpaper.this.mKeyguardWallpaper;
            AnonymousClass1 anonymousClass1 = this.mKeyguardWallpaperEventListener;
            boolean isFlagEnabled2 = WhichChecker.isFlagEnabled(semGetWallpaperFlags, 16);
            ((HashMap) ((KeyguardWallpaperController) keyguardWallpaper).mEventListeners).put(Integer.valueOf(isFlagEnabled2 ? 1 : 0), anonymousClass1);
        }

        public final boolean shouldWaitForEngineShown() {
            return this.mSubEngine.shouldWaitForEngineShown();
        }

        public final boolean shouldZoomOutWallpaper() {
            return false;
        }
    }

    public ImageWallpaper(DelayableExecutor delayableExecutor, UserTracker userTracker, WallpaperLogger wallpaperLogger, SystemWallpaperColors systemWallpaperColors, KeyguardWallpaper keyguardWallpaper, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, CoverWallpaper coverWallpaper, PluginWallpaper pluginWallpaper, DozeParameters dozeParameters) {
        this.mLongExecutor = delayableExecutor;
        this.mLogger = wallpaperLogger;
        SystemWallpaperColors systemWallpaperColors2 = ((KeyguardWallpaperController) keyguardWallpaper).mSystemWallpaperColors;
        this.mSystemWallpaperColors = systemWallpaperColors2;
        if (systemWallpaperColors2 == null) {
            this.mSystemWallpaperColors = systemWallpaperColors;
        }
        this.mSettingsHelper = settingsHelper;
        this.mCoverWallpaper = coverWallpaper;
        this.mPluginWallpaper = pluginWallpaper;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDozeParameters = dozeParameters;
        this.mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int displayId = super.getDisplayId();
        Context applicationContext = getApplicationContext();
        Iterator it = LiveWallpaperEngineManager.getInstance(applicationContext).getEngines().iterator();
        while (it.hasNext()) {
            LiveWallpaperService.BaseEngine baseEngine = (LiveWallpaperService.BaseEngine) it.next();
            if ((baseEngine instanceof IntegratedEngine) && !baseEngine.isPreview()) {
                int convertDisplayIdToMode = WhichChecker.convertDisplayIdToMode(displayId, applicationContext);
                int semGetWallpaperFlags = baseEngine.semGetWallpaperFlags() & 60;
                RecyclerView$$ExternalSyntheticOutline0.m(semGetWallpaperFlags, "ImageWallpaper", RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(displayId, convertDisplayIdToMode, "onConfigurationChanged: displayId = ", ", modeFromDisplayId = ", ", modeFromEngine = "));
                if (convertDisplayIdToMode == semGetWallpaperFlags) {
                    ((IntegratedEngine) baseEngine).onConfigurationChanged(configuration);
                }
            }
        }
        CanvasEngine canvasEngine = (CanvasEngine) this.mCanvasEngineList.get(Integer.valueOf(displayId));
        if (canvasEngine != null) {
            canvasEngine.onConfigurationChanged(configuration);
        }
    }

    @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService, android.service.wallpaper.WallpaperService, android.app.Service
    public final void onCreate() {
        super.onCreate();
        Log.i("ImageWallpaper", "Main onCreate");
        HandlerThread handlerThread = new HandlerThread("ImageWallpaper");
        this.mWorker = handlerThread;
        handlerThread.start();
        this.mSettingsHelper.registerCallback(this.mSettingsCallback, Settings.System.getUriFor(SettingsHelper.INDEX_DARK_FILTER_WALLPAPER));
    }

    @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService
    public final WallpaperService.Engine onCreateEngine(int i) {
        if (this.mCoverWallpaper != null && (WhichChecker.isWatchFace(i) || WhichChecker.isVirtualDisplay(i))) {
            CoverWallpaper coverWallpaper = this.mCoverWallpaper;
            this.mWorker.getThreadHandler();
            CoverWallpaperController coverWallpaperController = (CoverWallpaperController) coverWallpaper;
            coverWallpaperController.mWallpaperId = coverWallpaperController.mWallpaperManager.getWallpaperId(CoverWallpaperController.getCoverWhich());
        }
        return new IntegratedEngine(i, ((WallpaperService) this).mWallpaperManager.semGetWallpaperType(i));
    }

    @Override // android.service.wallpaper.WallpaperService, android.app.Service
    public final void onDestroy() {
        Log.i("ImageWallpaper", "Main onDestroy");
        super.onDestroy();
        this.mWorker.quitSafely();
        this.mSettingsHelper.unregisterCallback(this.mSettingsCallback);
        this.mWorker = null;
        this.mMiniBitmap = null;
    }

    public final Looper onProvideEngineLooper() {
        HandlerThread handlerThread = this.mWorker;
        return handlerThread != null ? handlerThread.getLooper() : super.onProvideEngineLooper();
    }

    public final class CanvasEngine extends BaseEngine implements DisplayManager.DisplayListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        static final int MIN_SURFACE_HEIGHT = 128;
        static final int MIN_SURFACE_WIDTH = 128;
        public String TAG;
        public final Paint mBitmapPaint;
        public int mBitmapUsages;
        public int mDisplayHeight;
        public boolean mDisplaySizeValid;
        public int mDisplayWidth;
        public ImageWallpaperCanvasHelper mHelper;
        public int mImgHeight;
        public int mImgWidth;
        public IntelligentCropHelper mIntelligentCropHelper;
        public boolean mIsEngineAlive;
        public boolean mIsFixedRotationInProgress;
        public boolean mIsVirtualDisplayMode;
        public DrawState mLastDrawnState;
        public float mLastWallpaperYOffset;
        public final Object mLock;
        public final AnonymousClass7 mPluginHomeWallpaperConsumer;
        public final AnonymousClass8 mPluginWallpaperConsumer;
        public int mRotation;
        public boolean mSurfaceCreated;
        public SurfaceHolder mSurfaceHolder;
        public final Object mSurfaceLock;
        public final AnonymousClass4 mWakefulnessObserver;
        public final WallpaperLocalColorExtractor mWallpaperLocalColorExtractor;
        public WallpaperManager mWallpaperManager;

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$3, reason: invalid class name */
        public final class AnonymousClass3 implements ImageWallpaperCanvasHelper.Callback {
            public AnonymousClass3() {
            }
        }

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$4, reason: invalid class name */
        public final class AnonymousClass4 implements WakefulnessLifecycle.Observer {
            public AnonymousClass4() {
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                ImageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0 imageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0 = new ImageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0(this, 0);
                int i = CanvasEngine.$r8$clinit;
                CanvasEngine.this.runAsWorkerThread(imageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0);
            }
        }

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$7, reason: invalid class name */
        public final class AnonymousClass7 implements Consumer {
            public AnonymousClass7() {
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CanvasEngine canvasEngine = CanvasEngine.this;
                ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda7 imageWallpaper$CanvasEngine$$ExternalSyntheticLambda7 = new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda7(this, (Boolean) obj);
                int i = CanvasEngine.$r8$clinit;
                canvasEngine.runAsWorkerThread(imageWallpaper$CanvasEngine$$ExternalSyntheticLambda7);
            }
        }

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$8, reason: invalid class name */
        public final class AnonymousClass8 implements Consumer {
            public AnonymousClass8() {
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CanvasEngine canvasEngine = CanvasEngine.this;
                ImageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0 imageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0 = new ImageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0(this, 1);
                int i = CanvasEngine.$r8$clinit;
                canvasEngine.runAsWorkerThread(imageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0);
            }
        }

        public final class DrawState {
            public final boolean mDarkModeFilterApplied;
            public final boolean mHighlightFilterApplied;
            public final int mSurfaceHeight;
            public final int mSurfaceWidth;
            public final int mWhich;

            public DrawState(CanvasEngine canvasEngine, int i, int i2, int i3, boolean z, boolean z2) {
                if ((i & 60) == 0) {
                    Log.e(canvasEngine.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "DrawState : mode value is missing. which="), new RuntimeException());
                }
                this.mWhich = i;
                this.mSurfaceWidth = i2;
                this.mSurfaceHeight = i3;
                this.mHighlightFilterApplied = z;
                this.mDarkModeFilterApplied = z2;
            }

            public final boolean equals(Object obj) {
                if (!(obj instanceof DrawState)) {
                    return false;
                }
                DrawState drawState = (DrawState) obj;
                return this.mWhich == drawState.mWhich && this.mSurfaceWidth == drawState.mSurfaceWidth && this.mSurfaceHeight == drawState.mSurfaceHeight && this.mHighlightFilterApplied == drawState.mHighlightFilterApplied && this.mDarkModeFilterApplied == drawState.mDarkModeFilterApplied;
            }

            public final String toString() {
                return "which=" + this.mWhich + ", " + this.mSurfaceWidth + "x" + this.mSurfaceHeight + ", highlight=" + this.mHighlightFilterApplied + ", darkMode=" + this.mDarkModeFilterApplied;
            }
        }

        /* renamed from: -$$Nest$mupdatePluginWallpaper, reason: not valid java name */
        public static void m2376$$Nest$mupdatePluginWallpaper(CanvasEngine canvasEngine) {
            int semGetWallpaperFlags = canvasEngine.semGetWallpaperFlags();
            if (!WhichChecker.isWatchFace(semGetWallpaperFlags)) {
                if (((PluginWallpaperController) ImageWallpaper.this.mPluginWallpaper).isPluginWallpaperRequired(canvasEngine.mWhich)) {
                    ImageWallpaper imageWallpaper = ImageWallpaper.this;
                    if (imageWallpaper.mPluginWallpaperType != ((PluginWallpaperController) imageWallpaper.mPluginWallpaper).getWallpaperType(canvasEngine.mWhich)) {
                        ImageWallpaper imageWallpaper2 = ImageWallpaper.this;
                        imageWallpaper2.mPluginWallpaperType = ((PluginWallpaperController) imageWallpaper2.mPluginWallpaper).getWallpaperType(canvasEngine.mWhich);
                        WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).forceRebindWallpaper(canvasEngine.mWhich);
                        return;
                    }
                    ImageWallpaper imageWallpaper3 = ImageWallpaper.this;
                    imageWallpaper3.mPluginWallpaperType = ((PluginWallpaperController) imageWallpaper3.mPluginWallpaper).getWallpaperType(canvasEngine.mWhich);
                }
            } else if (((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType() != 21) {
                WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).forceRebindWallpaper(semGetWallpaperFlags);
                return;
            }
            if (canvasEngine.mSurfaceCreated) {
                if (canvasEngine.updateSurfaceSizeIfNeed(semGetWallpaperFlags) && WhichChecker.isWatchFace(semGetWallpaperFlags)) {
                    return;
                }
                canvasEngine.drawFrameSynchronized(semGetWallpaperFlags, new Rect(canvasEngine.mSurfaceHolder.getSurfaceFrame()));
            }
        }

        public CanvasEngine(int i) {
            super(i);
            this.TAG = "ImageWallpaper[CanvasEngine]";
            this.mDisplaySizeValid = false;
            this.mDisplayWidth = 1;
            this.mDisplayHeight = 1;
            this.mImgWidth = 1;
            this.mImgHeight = 1;
            this.mLastWallpaperYOffset = 0.5f;
            this.mIsVirtualDisplayMode = false;
            this.mSurfaceCreated = false;
            this.mBitmapPaint = new Paint(2);
            this.mBitmapUsages = 0;
            Object obj = new Object();
            this.mLock = obj;
            this.mIsEngineAlive = false;
            this.mWakefulnessObserver = new AnonymousClass4();
            new DisplayController.OnDisplaysChangedListener() { // from class: com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.5
                public long mFixedRotationStartTime;

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onDisplayConfigurationChanged(int i2, Configuration configuration) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    ((WallpaperLoggerImpl) wallpaperLogger).log(canvasEngine.TAG, "onDisplayConfigurationChanged displayId=" + i2 + ", newConfig=" + configuration);
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onFixedRotationFinished(int i2) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    Configuration configuration = ImageWallpaper.this.getResources().getConfiguration();
                    int displayId = canvasEngine.getDisplayId();
                    DisplayInfo displayInfo = new DisplayInfo();
                    Display display = ((DisplayManager) ImageWallpaper.this.getSystemService("display")).getDisplay(displayId);
                    if (display != null) {
                        display.getDisplayInfo(displayInfo);
                    } else {
                        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(displayId, "  getCurrentDisplayRotation failed to get display. displayId=", canvasEngine.TAG);
                    }
                    int i3 = displayInfo.rotation;
                    long elapsedRealtime = SystemClock.elapsedRealtime() - this.mFixedRotationStartTime;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder sb = new StringBuilder("onFixedRotationFinished mRotation=");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, canvasEngine.mRotation, ", displayRotation=", i3, ", configRotation=");
                    sb.append(configuration.windowConfiguration.getRotation());
                    sb.append(", elapsed=");
                    sb.append(elapsedRealtime);
                    sb.append(", ");
                    sb.append(configuration);
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, sb.toString());
                    if (i2 != canvasEngine.getDisplayId()) {
                        WallpaperLogger wallpaperLogger2 = ImageWallpaper.this.mLogger;
                        ((WallpaperLoggerImpl) wallpaperLogger2).log(canvasEngine.TAG, "onFixedRotationFinished not my display : myId=" + canvasEngine.getDisplayId() + ", fixedRotationId=" + i2);
                        return;
                    }
                    if (canvasEngine.mRotation != i3) {
                        ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(canvasEngine.TAG, "onFixedRotationFinished Error orientation. So update Again.");
                        canvasEngine.mRotation = i3;
                        int currentWhich = canvasEngine.mHelper.getCurrentWhich();
                        if (!canvasEngine.mHelper.hasIntelligentCropHints(currentWhich)) {
                            canvasEngine.updateWallpaperOffset(currentWhich, canvasEngine.mRotation);
                        }
                    }
                    canvasEngine.mIsFixedRotationInProgress = false;
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onFixedRotationStarted(int i2, int i3) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i2, i3, "onFixedRotationStarted displayId=", ", newRotation=", ", mRotation=");
                    m.append(canvasEngine.mRotation);
                    m.append(", ");
                    m.append(ImageWallpaper.this.getResources().getConfiguration());
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, m.toString());
                    this.mFixedRotationStartTime = SystemClock.elapsedRealtime();
                    if (i2 == canvasEngine.getDisplayId()) {
                        canvasEngine.mIsFixedRotationInProgress = true;
                        if (canvasEngine.mRotation != i3) {
                            canvasEngine.mRotation = i3;
                            return;
                        }
                        return;
                    }
                    WallpaperLogger wallpaperLogger2 = ImageWallpaper.this.mLogger;
                    ((WallpaperLoggerImpl) wallpaperLogger2).log(canvasEngine.TAG, "onFixedRotationStarted not my display : myId=" + canvasEngine.getDisplayId() + ", fixedRotationId=" + i2);
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onDisplayAdded(int i2) {
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onDisplayRemoved(int i2) {
                }
            };
            new IRotationWatcher.Stub() { // from class: com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.6
                public final void onRotationChanged(int i2) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "onRotationChanged: newRotation=", ", mRotation=");
                    m.append(CanvasEngine.this.mRotation);
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, m.toString());
                    CanvasEngine canvasEngine2 = CanvasEngine.this;
                    if (canvasEngine2.mRotation != i2) {
                        ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(canvasEngine2.TAG, "onRotationChanged rotation is changed ");
                        CanvasEngine canvasEngine3 = CanvasEngine.this;
                        canvasEngine3.mRotation = i2;
                        int currentWhich = canvasEngine3.mHelper.getCurrentWhich();
                        if (CanvasEngine.this.mHelper.hasIntelligentCropHints(currentWhich)) {
                            CanvasEngine.this.updateSurfaceSize(currentWhich);
                        }
                    }
                }
            };
            this.mPluginHomeWallpaperConsumer = new AnonymousClass7();
            AnonymousClass8 anonymousClass8 = new AnonymousClass8();
            Log.d(this.TAG, "CanvasEngine 1");
            setFixedSizeAllowed(true);
            setShowForAllUsers(true);
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = new WallpaperLocalColorExtractor(ImageWallpaper.this.mLongExecutor, obj, new WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback(ImageWallpaper.this) { // from class: com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.1
                @Override // com.android.systemui.wallpapers.WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback
                public final void onActivated() {
                    CanvasEngine.this.setOffsetNotificationsEnabled(true);
                }

                @Override // com.android.systemui.wallpapers.WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback
                public final void onColorsProcessed(List list, List list2) {
                    int i2 = CanvasEngine.$r8$clinit;
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    canvasEngine.getClass();
                    try {
                        canvasEngine.notifyLocalColorsChanged(list, list2);
                    } catch (RuntimeException e) {
                        Log.e(canvasEngine.TAG, e.getMessage(), e);
                    }
                }

                @Override // com.android.systemui.wallpapers.WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback
                public final void onDeactivated() {
                    CanvasEngine.this.setOffsetNotificationsEnabled(false);
                }

                @Override // com.android.systemui.wallpapers.WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback
                public final void onMiniBitmapUpdated() {
                    CanvasEngine.this.onMiniBitmapUpdated();
                }
            });
            this.mWallpaperLocalColorExtractor = wallpaperLocalColorExtractor;
            if (ImageWallpaper.this.mPagesComputed) {
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda5(wallpaperLocalColorExtractor, ImageWallpaper.this.mPages));
            }
            ((PluginWallpaperController) ImageWallpaper.this.mPluginWallpaper).setWallpaperUpdateConsumer(this.mWhich, anonymousClass8);
        }

        public final void addLocalColorsAreas(List list) {
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.getClass();
            if (list.size() > 0) {
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda1(wallpaperLocalColorExtractor, list, 1));
            } else {
                Log.w("WallpaperLocalColorExtractor", "Attempt to add colors with an empty list");
            }
            Log.i(this.TAG, " addLocalColorsAreas ");
            if (ImageWallpaper.this.mLocalColorsToAdd.size() + ImageWallpaper.this.mColorAreas.size() == 0) {
                setOffsetNotificationsEnabled(true);
            }
            Bitmap bitmap = ImageWallpaper.this.mMiniBitmap;
            if (WallpaperUtils.isValidBitmap(bitmap)) {
                computeAndNotifyLocalColors(list, bitmap);
                return;
            }
            ImageWallpaper.this.mLocalColorsToAdd.addAll(list);
            this.mHelper.useWallpaperBitmap(this.mHelper.getCurrentWhich(), new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1(this, 2));
        }

        public final void computeAndNotifyLocalColors(List list, Bitmap bitmap) {
            int i;
            int i2;
            Log.i(this.TAG, " computeAndNotifyLocalColors ");
            ArrayList arrayList = new ArrayList(list.size());
            for (int i3 = 0; i3 < list.size(); i3++) {
                RectF rectF = (RectF) list.get(i3);
                if (!this.mDisplaySizeValid) {
                    Rect bounds = ((WindowManager) getDisplayContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
                    this.mDisplayWidth = bounds.width();
                    this.mDisplayHeight = bounds.height();
                    this.mDisplaySizeValid = true;
                }
                float f = 1.0f / ImageWallpaper.this.mPages;
                float f2 = (rectF.left % f) / f;
                float f3 = (rectF.right % f) / f;
                int floor = (int) Math.floor(rectF.centerX() / f);
                RectF rectF2 = new RectF();
                if (this.mImgWidth != 0 && (i = this.mImgHeight) != 0 && this.mDisplayWidth > 0 && (i2 = this.mDisplayHeight) > 0) {
                    rectF2.bottom = rectF.bottom;
                    rectF2.top = rectF.top;
                    float min = this.mDisplayWidth * Math.min(i / i2, 1.0f);
                    int i4 = this.mImgWidth;
                    float min2 = Math.min(1.0f, i4 > 0 ? min / i4 : 1.0f);
                    float f4 = floor * ((1.0f - min2) / (ImageWallpaper.this.mPages - 1));
                    rectF2.left = MathUtils.constrain((f2 * min2) + f4, 0.0f, 1.0f);
                    float constrain = MathUtils.constrain((f3 * min2) + f4, 0.0f, 1.0f);
                    rectF2.right = constrain;
                    if (rectF2.left > constrain) {
                        rectF2.left = 0.0f;
                        rectF2.right = 1.0f;
                    }
                }
                if (ImageWallpaper.LOCAL_COLOR_BOUNDS.contains(rectF2)) {
                    Rect rect = new Rect((int) Math.floor(rectF2.left * bitmap.getWidth()), (int) Math.floor(rectF2.top * bitmap.getHeight()), (int) Math.ceil(rectF2.right * bitmap.getWidth()), (int) Math.ceil(rectF2.bottom * bitmap.getHeight()));
                    if (rect.isEmpty()) {
                        arrayList.add(null);
                    } else {
                        arrayList.add(WallpaperColors.fromBitmap(Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height())));
                    }
                } else {
                    arrayList.add(null);
                }
            }
            ImageWallpaper.this.mColorAreas.addAll(list);
            try {
                notifyLocalColorsChanged(list, arrayList);
            } catch (RuntimeException e) {
                Log.e(this.TAG, e.getMessage(), e);
            }
        }

        public final void determineHighlightFilterAmount() {
            if (!(!TextUtils.isEmpty(this.mHelper.mColorDecorFilterData))) {
                int convertDisplayIdToMode = WhichChecker.convertDisplayIdToMode(getDisplayId(), ImageWallpaper.this.getApplicationContext());
                Boolean bool = Boolean.FALSE;
                Log.i("HighlightFilterHelper", "canApplyFilterOnHome : elapsed=" + (SystemClock.elapsedRealtime() - SystemClock.elapsedRealtime()) + ", mode=" + convertDisplayIdToMode + ", result=" + bool + ", wait=false");
            }
            Log.d(this.TAG, " determineHighlightFilterAmount : -1");
        }

        public final DrawState drawFrameOnCanvas(int i, long j, Rect rect, Bitmap bitmap, float f, int i2) {
            long j2;
            long j3;
            long j4;
            DrawState drawState;
            Rect surfaceFrame;
            int width;
            int height;
            float f2;
            float f3;
            Integer dimFilterColor;
            if (!WallpaperUtils.isValidBitmap(bitmap)) {
                return null;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            try {
                surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
                Rect nearestCropHint = IntelligentCropHelper.getNearestCropHint(new Point(surfaceFrame.width(), surfaceFrame.height()), this.mHelper.getIntelligentCropHints(i));
                if (nearestCropHint != null) {
                    try {
                        nearestCropHint = new Rect((int) (nearestCropHint.left * f), (int) (nearestCropHint.top * f), (int) (nearestCropHint.right * f), (int) (nearestCropHint.bottom * f));
                    } catch (Exception e) {
                        e = e;
                        j3 = elapsedRealtime;
                        j4 = j3;
                        j2 = j4;
                        Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                        drawState = null;
                        long elapsedRealtime2 = SystemClock.elapsedRealtime() - j;
                        String str = this.TAG;
                        StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime2, ", bmpPrepareDur=");
                        m.append(j2 - j);
                        m.append(", filterApplyDur=");
                        m.append(j4 - j2);
                        m.append(", drawDur=");
                        m.append(j3 - j4);
                        m.append(", drawnState=(");
                        m.append(drawState);
                        m.append(")");
                        Log.i(str, m.toString());
                        return drawState;
                    }
                }
                Matrix matrix = new Matrix();
                int width2 = nearestCropHint != null ? nearestCropHint.width() : bitmap.getWidth();
                int height2 = nearestCropHint != null ? nearestCropHint.height() : bitmap.getHeight();
                width = surfaceFrame.width();
                j2 = elapsedRealtime;
                try {
                    height = surfaceFrame.height();
                    if (width2 * height > width * height2) {
                        f2 = height;
                        f3 = height2;
                    } else {
                        f2 = width;
                        f3 = width2;
                    }
                    float f4 = f2 / f3;
                    float m2 = Rgb$Companion$$ExternalSyntheticOutline0.m(width2, f4, width, 0.5f);
                    float m3 = Rgb$Companion$$ExternalSyntheticOutline0.m(height2, f4, height, 0.5f);
                    matrix.setScale(f4, f4);
                    if (nearestCropHint != null) {
                        matrix.preTranslate(-nearestCropHint.left, -nearestCropHint.top);
                    }
                    matrix.postTranslate(Math.round(m2), Math.round(m3));
                    determineHighlightFilterAmount();
                    ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                    imageWallpaperCanvasHelper.mHighlightFilterAmount = -1;
                    Bitmap filterAppliedBitmap = imageWallpaperCanvasHelper.getFilterAppliedBitmap(bitmap, i);
                    dimFilterColor = this.mHelper.getDimFilterColor(i);
                    if (dimFilterColor != null) {
                        this.mBitmapPaint.setColorFilter(new PorterDuffColorFilter(dimFilterColor.intValue(), PorterDuff.Mode.SRC_OVER));
                    } else {
                        this.mBitmapPaint.setColorFilter(null);
                    }
                    j4 = SystemClock.elapsedRealtime();
                    try {
                        Log.i(this.TAG, "drawFrameOnCanvas : which=" + i + ", bmpW=" + bitmap.getWidth() + ", bmpH=" + bitmap.getHeight() + ", bmpScale=" + f + ", src=" + nearestCropHint + ", dest=" + surfaceFrame + ", highlight=-1, dimColor=" + dimFilterColor + ", drawRepeatCount=" + i2);
                        for (int i3 = 0; i3 < i2; i3++) {
                            Surface surface = this.mSurfaceHolder.getSurface();
                            Canvas lockHardwareWideColorGamutCanvas = this.mHelper.mIsWcgContent ? surface.lockHardwareWideColorGamutCanvas() : surface.lockHardwareCanvas();
                            if (lockHardwareWideColorGamutCanvas == null) {
                                Log.e(this.TAG, "drawFrameOnCanvas : canvas is NULL");
                                throw new RuntimeException("failed to lock the canvas - " + i3);
                            }
                            try {
                                lockHardwareWideColorGamutCanvas.drawBitmap(filterAppliedBitmap, matrix, this.mBitmapPaint);
                                surface.unlockCanvasAndPost(lockHardwareWideColorGamutCanvas);
                            } catch (Throwable th) {
                                surface.unlockCanvasAndPost(lockHardwareWideColorGamutCanvas);
                                throw th;
                            }
                        }
                        if (filterAppliedBitmap != null && filterAppliedBitmap != bitmap) {
                            filterAppliedBitmap.recycle();
                        }
                        j3 = SystemClock.elapsedRealtime();
                    } catch (Exception e2) {
                        e = e2;
                        j3 = j2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    j3 = j2;
                    j4 = j3;
                    Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                    drawState = null;
                    long elapsedRealtime22 = SystemClock.elapsedRealtime() - j;
                    String str2 = this.TAG;
                    StringBuilder m4 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime22, ", bmpPrepareDur=");
                    m4.append(j2 - j);
                    m4.append(", filterApplyDur=");
                    m4.append(j4 - j2);
                    m4.append(", drawDur=");
                    m4.append(j3 - j4);
                    m4.append(", drawnState=(");
                    m4.append(drawState);
                    m4.append(")");
                    Log.i(str2, m4.toString());
                    return drawState;
                }
                try {
                } catch (Exception e4) {
                    e = e4;
                    Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                    drawState = null;
                    long elapsedRealtime222 = SystemClock.elapsedRealtime() - j;
                    String str22 = this.TAG;
                    StringBuilder m42 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime222, ", bmpPrepareDur=");
                    m42.append(j2 - j);
                    m42.append(", filterApplyDur=");
                    m42.append(j4 - j2);
                    m42.append(", drawDur=");
                    m42.append(j3 - j4);
                    m42.append(", drawnState=(");
                    m42.append(drawState);
                    m42.append(")");
                    Log.i(str22, m42.toString());
                    return drawState;
                }
            } catch (Exception e5) {
                e = e5;
                j2 = elapsedRealtime;
            }
            if (surfaceFrame.equals(rect)) {
                drawState = new DrawState(this, i, width, height, false, dimFilterColor != null);
                long elapsedRealtime2222 = SystemClock.elapsedRealtime() - j;
                String str222 = this.TAG;
                StringBuilder m422 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime2222, ", bmpPrepareDur=");
                m422.append(j2 - j);
                m422.append(", filterApplyDur=");
                m422.append(j4 - j2);
                m422.append(", drawDur=");
                m422.append(j3 - j4);
                m422.append(", drawnState=(");
                m422.append(drawState);
                m422.append(")");
                Log.i(str222, m422.toString());
                return drawState;
            }
            Log.w(this.TAG, "drawFrameOnCanvas : surface size mismatch. curFrame=" + surfaceFrame + ", requestedFrame=" + rect);
            drawState = null;
            long elapsedRealtime22222 = SystemClock.elapsedRealtime() - j;
            String str2222 = this.TAG;
            StringBuilder m4222 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime22222, ", bmpPrepareDur=");
            m4222.append(j2 - j);
            m4222.append(", filterApplyDur=");
            m4222.append(j4 - j2);
            m4222.append(", drawDur=");
            m4222.append(j3 - j4);
            m4222.append(", drawnState=(");
            m4222.append(drawState);
            m4222.append(")");
            Log.i(str2222, m4222.toString());
            return drawState;
        }

        public final void drawFrameSynchronized(int i, Rect rect) {
            synchronized (this.mLock) {
                drawFullQualityFrame(i, rect);
                finishRendering();
            }
        }

        public final void drawFullQualityFrame(final int i, final Rect rect) {
            if (this.mSurfaceHolder == null) {
                Flags.FEATURE_FLAGS.getClass();
                Log.e(this.TAG, "drawFullQualityFrame: attempt to draw a frame without a valid surface");
                return;
            }
            if (!this.mIsEngineAlive) {
                Log.d(this.TAG, "drawFullQualityFrame: engine is destroyed");
                return;
            }
            Trace.beginSection("ImageWallpaper.CanvasEngine#drawFrame");
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            updateWallpaperOffset(i, this.mRotation);
            this.mHelper.useWallpaperBitmap(i, new Consumer() { // from class: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImageWallpaper.CanvasEngine canvasEngine = ImageWallpaper.CanvasEngine.this;
                    canvasEngine.mLastDrawnState = canvasEngine.drawFrameOnCanvas(i, elapsedRealtime, rect, (Bitmap) obj, 1.0f, 1);
                }
            });
            Trace.endSection();
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.SUBSCREEN_WATCHFACE) {
                setSurfaceAlpha(1.0f);
            }
            reportEngineShown(false);
            unloadBitmapIfNotUsedInternal();
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("Engine=");
            printWriter.println(this);
            printWriter.print(str);
            printWriter.print("valid surface=");
            String str2 = "null";
            printWriter.println((getSurfaceHolder() == null || getSurfaceHolder().getSurface() == null) ? "null" : Boolean.valueOf(getSurfaceHolder().getSurface().isValid()));
            printWriter.print(str);
            printWriter.print("surface frame=");
            printWriter.println(getSurfaceHolder() != null ? getSurfaceHolder().getSurfaceFrame() : "null");
            printWriter.print(str);
            printWriter.print("bitmap=");
            printWriter.println("null");
            ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
            imageWallpaperCanvasHelper.getClass();
            printWriter.print(str);
            printWriter.print("mSurfaceSize=");
            printWriter.print(imageWallpaperCanvasHelper.mSurfaceSize);
            printWriter.print(str);
            printWriter.print("mWcgContent=");
            printWriter.print(imageWallpaperCanvasHelper.mIsWcgContent);
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.getClass();
            printWriter.print(str);
            printWriter.print("display=");
            printWriter.println(wallpaperLocalColorExtractor.mDisplayWidth + "x" + wallpaperLocalColorExtractor.mDisplayHeight);
            printWriter.print(str);
            printWriter.print("mPages=");
            printWriter.println(wallpaperLocalColorExtractor.mPages);
            printWriter.print(str);
            printWriter.print("bitmap dimensions=");
            printWriter.println(wallpaperLocalColorExtractor.mBitmapWidth + "x" + wallpaperLocalColorExtractor.mBitmapHeight);
            printWriter.print(str);
            printWriter.print("bitmap=");
            Bitmap bitmap = wallpaperLocalColorExtractor.mMiniBitmap;
            if (bitmap != null) {
                if (bitmap.isRecycled()) {
                    str2 = "recycled";
                } else {
                    str2 = wallpaperLocalColorExtractor.mMiniBitmap.getWidth() + "x" + wallpaperLocalColorExtractor.mMiniBitmap.getHeight();
                }
            }
            printWriter.println(str2);
            printWriter.print(str);
            printWriter.print("PendingRegions size=");
            printWriter.print(((ArrayList) wallpaperLocalColorExtractor.mPendingRegions).size());
            printWriter.print(str);
            printWriter.print("ProcessedRegions size=");
            printWriter.print(((ArraySet) wallpaperLocalColorExtractor.mProcessedRegions).size());
        }

        public final void finishRendering() {
            Settings.System.putLong(ImageWallpaper.this.getApplicationContext().getContentResolver(), "wallpaper_finish_drawing", System.currentTimeMillis());
            Trace.beginSection("ImageWallpaper#finishRendering");
            Log.i(this.TAG, "finishRendering");
            Trace.endSection();
        }

        public final void getDisplaySizeAndUpdateColorExtractor() {
            Rect bounds = ((WindowManager) getDisplayContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
            final WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            final int width = bounds.width();
            final int height = bounds.height();
            wallpaperLocalColorExtractor.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpapers.WallpaperLocalColorExtractor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperLocalColorExtractor wallpaperLocalColorExtractor2 = WallpaperLocalColorExtractor.this;
                    int i = width;
                    int i2 = height;
                    synchronized (wallpaperLocalColorExtractor2.mLock) {
                        try {
                            if (i == wallpaperLocalColorExtractor2.mDisplayWidth && i2 == wallpaperLocalColorExtractor2.mDisplayHeight) {
                                return;
                            }
                            wallpaperLocalColorExtractor2.mDisplayWidth = i;
                            wallpaperLocalColorExtractor2.mDisplayHeight = i2;
                            wallpaperLocalColorExtractor2.processLocalColorsInternal();
                        } finally {
                        }
                    }
                }
            });
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isFixedOrientationWallpaper(int r9, int r10) {
            /*
                r8 = this;
                com.android.systemui.wallpapers.ImageWallpaper r0 = com.android.systemui.wallpapers.ImageWallpaper.this
                android.content.Context r0 = r0.getApplicationContext()
                int r0 = com.android.systemui.wallpaper.utils.WhichChecker.convertDisplayIdToMode(r9, r0)
                r1 = 0
                r2 = 1
                java.lang.String r3 = "ImageWallpaper"
                if (r0 >= 0) goto L17
                java.lang.String r10 = "isFixedOrientationWallpaper: incorrect mode. displayId = "
                com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(r9, r10, r3)
            L15:
                r4 = r1
                goto L38
            L17:
                r0 = r0 | r2
                com.android.systemui.wallpapers.ImageWallpaper r4 = com.android.systemui.wallpapers.ImageWallpaper.this
                android.app.WallpaperManager r4 = com.android.systemui.wallpapers.ImageWallpaper.access$400(r4)
                android.os.Bundle r4 = r4.getWallpaperExtras(r0, r10)
                if (r4 != 0) goto L25
                goto L15
            L25:
                java.lang.String r5 = "isFixedOrientation"
                boolean r4 = r4.getBoolean(r5)
                java.lang.String r5 = "isFixedOrientationWallpaper: which="
                java.lang.String r6 = ", user="
                java.lang.String r7 = ", fixedOrientation="
                java.lang.StringBuilder r10 = androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(r0, r10, r5, r6, r7)
                com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(r10, r4, r3)
            L38:
                if (r4 == 0) goto L3b
                return r2
            L3b:
                com.android.systemui.wallpapers.ImageWallpaper r10 = com.android.systemui.wallpapers.ImageWallpaper.this
                android.content.Context r10 = r10.getBaseContext()
                android.content.pm.PackageManager r10 = r10.getPackageManager()
                if (r10 == 0) goto L51
                java.lang.String r0 = "com.samsung.feature.device_category_tablet"
                boolean r10 = r10.hasSystemFeature(r0)
                if (r10 == 0) goto L51
                r10 = r2
                goto L52
            L51:
                r10 = r1
            L52:
                boolean r0 = com.samsung.android.wallpaper.Rune.SUPPORT_SUB_DISPLAY_MODE
                if (r0 == 0) goto L64
                boolean r0 = com.samsung.android.wallpaper.Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
                if (r0 != 0) goto L64
                android.app.WallpaperManager r0 = r8.mWallpaperManager
                int r0 = r0.getLidState()
                if (r0 != 0) goto L64
                r0 = r2
                goto L65
            L64:
                r0 = r1
            L65:
                com.android.systemui.wallpapers.ImageWallpaper r3 = com.android.systemui.wallpapers.ImageWallpaper.this
                com.android.systemui.util.SettingsHelper r3 = com.android.systemui.wallpapers.ImageWallpaper.m2375$$Nest$fgetmSettingsHelper(r3)
                int r3 = r3.getHomescreenWallpaperSource(r0)
                if (r3 != 0) goto L73
                r3 = r2
                goto L74
            L73:
                r3 = r1
            L74:
                if (r9 != 0) goto L87
                boolean r9 = com.samsung.android.wallpaper.Rune.WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER
                if (r9 != 0) goto L7c
                if (r0 == 0) goto L87
            L7c:
                if (r10 != 0) goto L87
                if (r3 != 0) goto L87
                boolean r9 = r8.isPreview()
                if (r9 != 0) goto L87
                r1 = r2
            L87:
                java.lang.String r9 = r8.TAG
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r4 = "isFixedOrientationWallpaper: feature="
                r2.<init>(r4)
                boolean r4 = com.samsung.android.wallpaper.Rune.WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER
                java.lang.String r5 = ", isTablet="
                java.lang.String r6 = ", isCoverDisplay="
                com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(r2, r4, r5, r10, r6)
                java.lang.String r10 = ", isCustomWallpaper="
                java.lang.String r4 = ", isPreview="
                com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(r2, r0, r10, r3, r4)
                boolean r8 = r8.isPreview()
                r2.append(r8)
                java.lang.String r8 = ", isFixedOrientation="
                r2.append(r8)
                r2.append(r1)
                java.lang.String r8 = r2.toString()
                android.util.Log.i(r9, r8)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.isFixedOrientationWallpaper(int, int):boolean");
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final WallpaperColors onComputeColors() {
            com.android.window.flags.Flags.offloadColorExtraction();
            return null;
        }

        public final void onConfigurationChanged(Configuration configuration) {
            if (getDisplayId() != 0) {
                WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "onConfigurationChanged display id= " + getDisplayId() + " , newConfig =" + configuration);
            }
            runAsWorkerThread(new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda7(this, configuration, 0));
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final void onCreate(SurfaceHolder surfaceHolder) {
            CoverWallpaper coverWallpaper;
            super.onCreate(surfaceHolder);
            int displayId = getDisplayId();
            this.TAG = Anchor$$ExternalSyntheticOutline0.m(getWallpaperFlags() == 2 ? 2 : 1, "]", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(displayId, "ImageWallpaper[CanvasEngine_d", "_w"));
            Trace.beginSection("ImageWallpaper.CanvasEngine#onCreate");
            ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(this.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(displayId, "Engine onCreate: displayId = "));
            WallpaperManager wallpaperManager = (WallpaperManager) getDisplayContext().getSystemService(WallpaperManager.class);
            this.mWallpaperManager = wallpaperManager;
            this.mSurfaceHolder = surfaceHolder;
            Rect peekBitmapDimensions = wallpaperManager.peekBitmapDimensions(getWallpaperFlags() != 2 ? 1 : 2, true);
            if (peekBitmapDimensions != null) {
                this.mSurfaceHolder.setFixedSize(Math.max(128, peekBitmapDimensions.width()), Math.max(128, peekBitmapDimensions.height()));
            }
            ((DisplayManager) getDisplayContext().getSystemService(DisplayManager.class)).registerDisplayListener(this, null);
            getDisplaySizeAndUpdateColorExtractor();
            semSetFixedOrientation(isFixedOrientationWallpaper(getDisplayId(), getCurrentUserId()), false);
            this.mIntelligentCropHelper = new IntelligentCropHelper();
            AnonymousClass3 anonymousClass3 = new AnonymousClass3();
            Context displayContext = getDisplayContext();
            int displayId2 = getDisplayId();
            ImageWallpaper imageWallpaper = ImageWallpaper.this;
            this.mHelper = new ImageWallpaperCanvasHelper(displayContext, displayId2, imageWallpaper.mLogger, imageWallpaper.mSystemWallpaperColors, imageWallpaper.mCoverWallpaper, imageWallpaper.mPluginWallpaper, this.mIntelligentCropHelper, anonymousClass3);
            int semGetWallpaperFlags = semGetWallpaperFlags();
            if (WhichChecker.isSystemAndLock(semGetWallpaperFlags)) {
                semGetWallpaperFlags = (semGetWallpaperFlags & 60) | 1;
            }
            updateSurfaceSize(semGetWallpaperFlags);
            this.mHelper.mBitmapUpdateConsumer = new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1(this, 1);
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper2 = ImageWallpaper.this;
                imageWallpaper2.mMainThreadHandler.post(new AnonymousClass1(imageWallpaper2, this.mWakefulnessObserver));
            }
            ImageWallpaper.this.mCanvasEngineList.put(Integer.valueOf(displayId), this);
            if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                this.mIsVirtualDisplayMode = WallpaperManager.isVirtualWallpaperDisplay(ImageWallpaper.this.getApplicationContext(), displayId);
            }
            if (LsRune.WALLPAPER_PLAY_GIF && (coverWallpaper = ImageWallpaper.this.mCoverWallpaper) != null && (displayId == 1 || this.mIsVirtualDisplayMode)) {
                AnonymousClass7 anonymousClass7 = this.mPluginHomeWallpaperConsumer;
                Log.d("CoverWallpaperController", "setWallpaperUpdateConsumer: consumer = " + anonymousClass7);
                ((CoverWallpaperController) coverWallpaper).mWallpaperConsumer = anonymousClass7;
            }
            this.mIsEngineAlive = true;
            Trace.endSection();
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final void onDestroy() {
            DisplayManager displayManager;
            WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
            ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "Engine onDestroy displayId " + getDisplayId());
            super.onDestroy();
            Context displayContext = getDisplayContext();
            if (displayContext != null && (displayManager = (DisplayManager) displayContext.getSystemService(DisplayManager.class)) != null) {
                displayManager.unregisterDisplayListener(this);
            }
            final WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpapers.WallpaperLocalColorExtractor$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperLocalColorExtractor wallpaperLocalColorExtractor2 = WallpaperLocalColorExtractor.this;
                    synchronized (wallpaperLocalColorExtractor2.mLock) {
                        try {
                            Bitmap bitmap = wallpaperLocalColorExtractor2.mMiniBitmap;
                            if (bitmap != null) {
                                bitmap.recycle();
                                wallpaperLocalColorExtractor2.mMiniBitmap = null;
                            }
                            ((ArraySet) wallpaperLocalColorExtractor2.mProcessedRegions).clear();
                            ((ArrayList) wallpaperLocalColorExtractor2.mPendingRegions).clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
            ImageWallpaper.this.mMiniBitmap = null;
            if (LsRune.WALLPAPER_PLAY_GIF && (getDisplayId() == 1 || this.mIsVirtualDisplayMode)) {
                ImageWallpaper.this.mCoverWallpaper.getClass();
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mMainThreadHandler.post(new AnonymousClass2(imageWallpaper, this.mWakefulnessObserver));
            }
            ImageWallpaper.this.mCanvasEngineList.remove(Integer.valueOf(getDisplayId()), this);
            IBinder windowTokenAsBinder = getWindowTokenAsBinder();
            if (windowTokenAsBinder != null) {
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                imageWallpaperCanvasHelper.mWallpaperManager.setDisplayOffset(windowTokenAsBinder, 0, 0);
                imageWallpaperCanvasHelper.mSmartCropYOffset = 0;
            }
            this.mIsEngineAlive = false;
        }

        public final void onDimAmountChanged(float f) {
            com.android.window.flags.Flags.offloadColorExtraction();
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            Trace.beginSection("ImageWallpaper.CanvasEngine#onDisplayChanged");
            try {
                if (i == getDisplayContext().getDisplayId()) {
                    this.mDisplaySizeValid = false;
                    getDisplaySizeAndUpdateColorExtractor();
                }
            } finally {
                Trace.endSection();
            }
        }

        public void onMiniBitmapUpdated() {
            ImageWallpaper.this.mLongExecutor.execute(new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2(this, 0));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
            if (this.mLastWallpaperYOffset != f2) {
                boolean z = f2 == 0.5f;
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                if (imageWallpaperCanvasHelper.mIsSmartCropAllowed != z) {
                    Log.i(imageWallpaperCanvasHelper.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("setSmartCropAllowed: ", z));
                }
                imageWallpaperCanvasHelper.mIsSmartCropAllowed = z;
                this.mLastWallpaperYOffset = f2;
            }
            int round = (f3 <= 0.0f || f3 > 1.0f) ? 1 : Math.round(1.0f / f3) + 1;
            if (round == ImageWallpaper.this.mPages && ImageWallpaper.this.mPagesComputed) {
                return;
            }
            ImageWallpaper.this.mPages = round;
            ImageWallpaper imageWallpaper = ImageWallpaper.this;
            imageWallpaper.mPagesComputed = true;
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda5(wallpaperLocalColorExtractor, imageWallpaper.mPages));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder(" onSurfaceCreated ");
            sb.append(surfaceHolder.getSurfaceFrame());
            sb.append(" , ");
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sb, this.mSurfaceCreated, str);
            if (!this.mSurfaceCreated) {
                Trace.beginSection("ImageWallpaper#onSurfaceCreated");
                final ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                final int currentWhich = imageWallpaperCanvasHelper.getCurrentWhich();
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(currentWhich, "onSurfaceCreated: which=", ", disp=");
                m.append(imageWallpaperCanvasHelper.mDisplayId);
                m.append(", colorDecor=");
                m.append(!TextUtils.isEmpty(imageWallpaperCanvasHelper.mColorDecorFilterData));
                m.append(", highlightAmount=");
                m.append(imageWallpaperCanvasHelper.mHighlightFilterAmount);
                Log.i(imageWallpaperCanvasHelper.TAG, m.toString());
                imageWallpaperCanvasHelper.useWallpaperBitmap(currentWhich, new Consumer() { // from class: com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void accept(java.lang.Object r8) {
                        /*
                            r7 = this;
                            com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper r0 = com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper.this
                            int r7 = r2
                            android.graphics.Bitmap r8 = (android.graphics.Bitmap) r8
                            r0.getClass()
                            boolean r1 = com.android.systemui.wallpaper.WallpaperUtils.isValidBitmap(r8)
                            if (r1 != 0) goto L18
                            java.lang.String r1 = r0.TAG
                            java.lang.String r2 = "reload texture failed!"
                            android.util.Log.w(r1, r2)
                            goto L1f
                        L18:
                            java.util.function.Consumer r1 = r0.mBitmapUpdateConsumer
                            if (r1 == 0) goto L1f
                            r1.accept(r8)
                        L1f:
                            boolean r1 = com.android.systemui.wallpaper.WallpaperUtils.isValidBitmap(r8)
                            r2 = 1
                            r3 = 0
                            if (r1 == 0) goto L41
                            java.lang.String r1 = r0.mColorDecorFilterData
                            boolean r1 = android.text.TextUtils.isEmpty(r1)
                            r1 = r1 ^ r2
                            if (r1 == 0) goto L38
                            java.lang.String r1 = r0.mColorDecorFilterData
                            android.graphics.Bitmap r8 = com.android.systemui.wallpaper.effect.ColorDecorFilterHelper.createFilteredBitmap(r1, r8)
                        L36:
                            r1 = r2
                            goto L42
                        L38:
                            int r1 = r0.mHighlightFilterAmount
                            if (r1 < 0) goto L41
                            android.graphics.Bitmap r8 = com.android.systemui.wallpaper.effect.HighlightFilterHelper.createFilteredBitmap(r8, r1)
                            goto L36
                        L41:
                            r1 = r3
                        L42:
                            boolean r4 = com.android.systemui.wallpaper.WallpaperUtils.isValidBitmap(r8)
                            if (r4 == 0) goto L7c
                            int r4 = r0.mDisplayId
                            r5 = 2
                            if (r4 != r5) goto L4e
                            goto L52
                        L4e:
                            boolean r4 = r0.mIsVirtualDisplay
                            if (r4 == 0) goto L54
                        L52:
                            r4 = r2
                            goto L55
                        L54:
                            r4 = r3
                        L55:
                            com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper r5 = r0.mImageSmartCropper
                            if (r5 == 0) goto L7c
                            if (r4 != 0) goto L7c
                            int r4 = r0.mCurrentUserId
                            r5.updateSmartCropRectIfNeeded(r8, r7, r4)
                            android.graphics.Rect r7 = new android.graphics.Rect
                            int r4 = r8.getWidth()
                            int r6 = r8.getHeight()
                            r7.<init>(r3, r3, r4, r6)
                            android.graphics.Rect r3 = r5.mCropResult
                            if (r3 != 0) goto L77
                            android.app.WallpaperManager r0 = r0.mWallpaperManager
                            r0.semSetSmartCropRect(r2, r7, r7)
                            goto L7c
                        L77:
                            android.app.WallpaperManager r0 = r0.mWallpaperManager
                            r0.semSetSmartCropRect(r2, r7, r3)
                        L7c:
                            if (r1 == 0) goto L83
                            if (r8 == 0) goto L83
                            r8.recycle()
                        L83:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper$$ExternalSyntheticLambda0.accept(java.lang.Object):void");
                    }
                });
                Trace.endSection();
            }
            this.mSurfaceCreated = true;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            Log.i(this.TAG, "onSurfaceDestroyed");
            Flags.FEATURE_FLAGS.getClass();
            ImageWallpaper.this.mLongExecutor.execute(new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2(this, 1));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            DrawState drawState;
            if (!this.mIsEngineAlive) {
                Log.i(this.TAG, "onSurfaceRedrawNeeded: engine already destroyed");
                return;
            }
            int currentWhich = this.mHelper.getCurrentWhich();
            Rect rect = new Rect(this.mSurfaceHolder.getSurfaceFrame());
            String str = this.TAG;
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(currentWhich, "onSurfaceRedrawNeeded: curWhich=", ", isFixedRotationInProgress=");
            m.append(this.mIsFixedRotationInProgress);
            m.append(", mRotation=");
            m.append(this.mRotation);
            m.append(", surfaceFrame=");
            m.append(rect);
            Log.i(str, m.toString());
            if (!this.mHelper.hasIntelligentCropHints(currentWhich)) {
                updateWallpaperOffset(currentWhich, this.mRotation);
            }
            SurfaceHolder surfaceHolder2 = this.mSurfaceHolder;
            if (surfaceHolder2 == null) {
                drawState = null;
            } else {
                Rect surfaceFrame = surfaceHolder2.getSurfaceFrame();
                determineHighlightFilterAmount();
                drawState = new DrawState(this, currentWhich, surfaceFrame.width(), surfaceFrame.height(), false, this.mHelper.getDimFilterColor(currentWhich) != null);
            }
            synchronized (this.mLock) {
                try {
                    Log.i(this.TAG, "onSurfaceRedrawNeeded: displayId=" + getDisplayId() + ", lastDrawn=(" + this.mLastDrawnState + "), toDraw=(" + drawState + ")");
                    DrawState drawState2 = this.mLastDrawnState;
                    if (drawState2 != null && drawState2.equals(drawState)) {
                        Log.i(this.TAG, "onSurfaceRedrawNeeded: not need redraw");
                        return;
                    }
                    ImageWallpaperCanvasHelper.DownScaledSourceBitmap downScaledSourceBitmap = (ImageWallpaperCanvasHelper.DownScaledSourceBitmap) this.mHelper.mDownScaledSourceBitmapSet.get(Integer.valueOf(WhichChecker.getSourceWhich(currentWhich)));
                    if (downScaledSourceBitmap == null) {
                        drawFrameSynchronized(currentWhich, rect);
                        return;
                    }
                    synchronized (this.mLock) {
                        drawFrameOnCanvas(currentWhich, SystemClock.elapsedRealtime(), rect, downScaledSourceBitmap.mBitmap, downScaledSourceBitmap.mScale, 2);
                    }
                    synchronized (this.mLock) {
                        this.mLastDrawnState = drawState;
                    }
                    ImageWallpaper.this.mLongExecutor.execute(new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda5(this, currentWhich, rect));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine
        public final void onSwitchDisplayChanged(final boolean z) {
            Log.d(this.TAG, "onSwitchDisplayChanged");
            if (!this.mIsEngineAlive) {
                return;
            }
            semSetFixedOrientation(isFixedOrientationWallpaper(getDisplayId(), getCurrentUserId()), false);
            if (z) {
                ImageWallpaper.this.mWorker.getThreadHandler().postDelayed(new Runnable() { // from class: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ImageWallpaper.CanvasEngine.this.updateOnSwitchDisplayChanged(z);
                    }
                }, 200L);
            } else {
                updateOnSwitchDisplayChanged(z);
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onVisibilityChanged(boolean z) {
            super.onVisibilityChanged(z);
            String str = this.TAG;
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m(" onVisibilityChanged: visible=", " , displayId=", z);
            m.append(getDisplayId());
            Log.i(str, m.toString());
        }

        public void recomputeColorExtractorMiniBitmap() {
            this.mHelper.useWallpaperBitmap(this.mHelper.getCurrentWhich(), new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1(this, 0));
        }

        public final void removeLocalColorsAreas(List list) {
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda1(wallpaperLocalColorExtractor, list, 0));
        }

        public final void runAsWorkerThread(Runnable runnable) {
            HandlerThread handlerThread = ImageWallpaper.this.mWorker;
            if (handlerThread == null) {
                Log.w(this.TAG, "runAsWorkerThread: mWorker is null.");
            } else {
                handlerThread.getThreadHandler().post(runnable);
            }
        }

        public final void setCurrentUserId(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setCurrentUserId: userId = ", this.TAG);
            if (!this.mIsEngineAlive) {
                Log.d(this.TAG, "setCurrentUserId: already destroyed");
            } else {
                this.mHelper.mCurrentUserId = i;
            }
        }

        public final boolean shouldWaitForEngineShown() {
            return true;
        }

        public final boolean shouldZoomOutWallpaper() {
            return false;
        }

        public final void unloadBitmapIfNotUsedInternal() {
            int i = this.mBitmapUsages - 1;
            this.mBitmapUsages = i;
            if (i <= 0) {
                this.mBitmapUsages = 0;
                Trace.beginSection("ImageWallpaper.CanvasEngine#unloadBitmap");
                Flags.FEATURE_FLAGS.getClass();
                getSurfaceHolder().getSurface().hwuiDestroy();
                this.mWallpaperManager.forgetLoadedWallpaper();
                Trace.endSection();
            }
        }

        public final void updateMiniBitmapAndNotify(Bitmap bitmap) {
            if (bitmap == null) {
                return;
            }
            int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
            float f = min > 128 ? 128.0f / min : 1.0f;
            this.mImgHeight = bitmap.getHeight();
            this.mImgWidth = bitmap.getWidth();
            ImageWallpaper.this.mMiniBitmap = Bitmap.createScaledBitmap(bitmap, (int) Math.max(bitmap.getWidth() * f, 1.0f), (int) Math.max(f * bitmap.getHeight(), 1.0f), false);
            ImageWallpaper imageWallpaper = ImageWallpaper.this;
            computeAndNotifyLocalColors(imageWallpaper.mLocalColorsToAdd, imageWallpaper.mMiniBitmap);
            ImageWallpaper.this.mLocalColorsToAdd.clear();
        }

        public final void updateOnSwitchDisplayChanged(boolean z) {
            boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
            if (z2) {
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                imageWallpaperCanvasHelper.getClass();
                if (z2) {
                    StringBuilder m = RowView$$ExternalSyntheticOutline0.m(" onFolderStateChanged: isFolded=", ", WallMgr=", z);
                    m.append(ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mWallpaperManager.getLidState()));
                    m.append(", mLidState=");
                    m.append(ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mLidState));
                    String sb = m.toString();
                    WallpaperLoggerImpl wallpaperLoggerImpl = (WallpaperLoggerImpl) imageWallpaperCanvasHelper.mLoggerWrapper;
                    String str = imageWallpaperCanvasHelper.TAG;
                    wallpaperLoggerImpl.log(str, sb);
                    imageWallpaperCanvasHelper.mIsFolded = z;
                    if (z) {
                        PowerManager powerManager = imageWallpaperCanvasHelper.mPm;
                        if (powerManager != null && !powerManager.isInteractive()) {
                            Log.i(str, " onFolderStateChanged screen off.");
                        } else if (imageWallpaperCanvasHelper.mLidState == 1) {
                            Log.i(str, " do not change lid state. so request update ");
                            imageWallpaperCanvasHelper.setLidState(0);
                        }
                    } else {
                        Log.i(str, " Fold open. so request update ");
                        imageWallpaperCanvasHelper.setLidState(1);
                    }
                }
                int currentWhich = this.mHelper.getCurrentWhich();
                if (this.mHelper.hasIntelligentCropHints(currentWhich)) {
                    getSurfaceHolder().setFixedSize(-1, -1);
                    return;
                }
                Size reportSurfaceSize = this.mHelper.reportSurfaceSize(currentWhich);
                Rect surfaceFrame = getSurfaceHolder().getSurfaceFrame();
                if (reportSurfaceSize.getHeight() == surfaceFrame.height() && reportSurfaceSize.getWidth() == surfaceFrame.width()) {
                    updateRendering(currentWhich);
                    return;
                }
                int max = Math.max(128, reportSurfaceSize.getWidth());
                int max2 = Math.max(128, reportSurfaceSize.getHeight());
                String str2 = this.TAG;
                StringBuilder m2 = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(currentWhich, max, "updateOnSwitchDisplayChanged: change surface size. which=", ", width=", ", height=");
                m2.append(max2);
                Log.i(str2, m2.toString());
                getSurfaceHolder().setFixedSize(max, max2);
            }
        }

        public final void updateRendering(int i) {
            try {
                drawFrameSynchronized(i, new Rect(this.mSurfaceHolder.getSurfaceFrame()));
            } catch (Exception e) {
                Log.i(this.TAG, " error : " + e.getMessage());
            }
        }

        public final void updateSurfaceSize(int i) {
            Trace.beginSection("ImageWallpaper#updateSurfaceSize");
            SurfaceHolder surfaceHolder = getSurfaceHolder();
            Size reportSurfaceSize = this.mHelper.reportSurfaceSize(i);
            int max = Math.max(128, reportSurfaceSize.getWidth());
            int max2 = Math.max(128, reportSurfaceSize.getHeight());
            String str = this.TAG;
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(max, max2, " updateSurfaceSize: width = ", ", height = ", ", isVisible = ");
            m.append(isVisible());
            Log.i(str, m.toString());
            if (this.mHelper.hasIntelligentCropHints(i)) {
                surfaceHolder.setFixedSize(-1, -1);
            } else {
                surfaceHolder.setFixedSize(max, max2);
            }
            Trace.endSection();
        }

        public final boolean updateSurfaceSizeIfNeed(int i) {
            Size reportSurfaceSize = this.mHelper.reportSurfaceSize(i);
            Rect surfaceFrame = getSurfaceHolder().getSurfaceFrame();
            if (reportSurfaceSize.getHeight() == surfaceFrame.height() && reportSurfaceSize.getWidth() == surfaceFrame.width()) {
                return false;
            }
            Log.i(this.TAG, "  updateSurfaceSizeIfNeed frame  " + reportSurfaceSize + " surfaceFrame : " + surfaceFrame);
            updateSurfaceSize(i);
            finishRendering();
            return true;
        }

        public final void updateWallpaperOffset(int i, int i2) {
            int min;
            int i3;
            int i4;
            IBinder windowTokenAsBinder = getWindowTokenAsBinder();
            if (windowTokenAsBinder != null) {
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                int i5 = imageWallpaperCanvasHelper.mDisplayId;
                String str = imageWallpaperCanvasHelper.TAG;
                if (i5 == 2 || (LsRune.WALLPAPER_VIRTUAL_DISPLAY && imageWallpaperCanvasHelper.mIsVirtualDisplay)) {
                    TooltipPopup$$ExternalSyntheticOutline0.m(imageWallpaperCanvasHelper.mDisplayId, str, new StringBuilder(" ignore updateWallpaperOffset "));
                    return;
                }
                WallpaperManager wallpaperManager = imageWallpaperCanvasHelper.mWallpaperManager;
                if (wallpaperManager != null) {
                    int i6 = imageWallpaperCanvasHelper.mSmartCropYOffset;
                    int semGetWallpaperType = wallpaperManager.semGetWallpaperType(i);
                    StringBuilder sb = new StringBuilder();
                    sb.append("updateWallpaperOffset " + imageWallpaperCanvasHelper.mDisplayId);
                    sb.append(" lastCropOffset " + i6 + " , wp Type " + semGetWallpaperType + " , rotation " + i2 + " , allowed " + imageWallpaperCanvasHelper.mIsSmartCropAllowed);
                    ImageSmartCropper imageSmartCropper = imageWallpaperCanvasHelper.mImageSmartCropper;
                    if (imageSmartCropper != null && imageWallpaperCanvasHelper.mIsSmartCropAllowed && imageSmartCropper.needToSmartCrop() && ((i2 == 1 || i2 == 3) && semGetWallpaperType == 0 && !imageWallpaperCanvasHelper.hasIntelligentCropHints(i))) {
                        if (((Rect) WallpaperUtils.sCachedSmartCroppedRect.get(i)) == null) {
                            sb.append(" Error Smart rect is Null " + imageWallpaperCanvasHelper.mSmartCropYOffset);
                            i4 = 0;
                        } else {
                            Display display = ((DisplayManager) imageWallpaperCanvasHelper.mContext.getSystemService("display")).getDisplay(imageWallpaperCanvasHelper.mDisplayId);
                            if (display != null) {
                                DisplayInfo displayInfo = new DisplayInfo();
                                display.getDisplayInfo(displayInfo);
                                i3 = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                min = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                            } else {
                                Log.e(str, " getDisplaySize use configuration to recognize the screen size");
                                Rect bounds = imageWallpaperCanvasHelper.mContext.getResources().getConfiguration().windowConfiguration.getBounds();
                                int width = bounds.width();
                                int height = bounds.height();
                                int max = Math.max(width, height);
                                min = Math.min(width, height);
                                i3 = max;
                            }
                            Size size = new Size(i3, min);
                            int width2 = size.getWidth();
                            int height2 = size.getHeight();
                            float f = height2;
                            float height3 = imageWallpaperCanvasHelper.mSurfaceSize.height() * Math.max(width2 / imageWallpaperCanvasHelper.mSurfaceSize.width(), f / imageWallpaperCanvasHelper.mSurfaceSize.height());
                            float height4 = r0.top / imageWallpaperCanvasHelper.mDimensions.height();
                            float f2 = height4 * height3;
                            float f3 = (f * 0.5f) + f2;
                            float f4 = imageWallpaperCanvasHelper.mYOffset;
                            int i7 = (int) (height3 * f4);
                            sb.append(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(width2, height2, ", screenSize=(", ", ", ")"));
                            sb.append(", origTopPos " + height4 + " , calcTopPos " + f2);
                            sb.append(", scaledHeight " + height3 + " , " + i7 + " , " + f4 + " , smartCropCenterY " + f3);
                            i4 = (int) (((float) i7) - f3);
                        }
                        imageWallpaperCanvasHelper.mSmartCropYOffset = i4;
                    } else {
                        imageWallpaperCanvasHelper.mSmartCropYOffset = 0;
                    }
                    if (i6 == imageWallpaperCanvasHelper.mSmartCropYOffset) {
                        sb.append(" Do not change Display offset " + imageWallpaperCanvasHelper.mSmartCropYOffset);
                    } else {
                        sb.append(" : Set Display offset " + imageWallpaperCanvasHelper.mSmartCropYOffset);
                        try {
                            imageWallpaperCanvasHelper.mWallpaperManager.setDisplayOffset(windowTokenAsBinder, 0, imageWallpaperCanvasHelper.mSmartCropYOffset);
                        } catch (IllegalArgumentException e) {
                            Log.i(str, " Wallpaper window proxy does not exist. " + e.getMessage());
                        }
                    }
                    ((WallpaperLoggerImpl) imageWallpaperCanvasHelper.mLoggerWrapper).log(str, sb.toString());
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }

        public CanvasEngine() {
            super(5);
            this.TAG = "ImageWallpaper[CanvasEngine]";
            this.mDisplaySizeValid = false;
            this.mDisplayWidth = 1;
            this.mDisplayHeight = 1;
            this.mImgWidth = 1;
            this.mImgHeight = 1;
            this.mLastWallpaperYOffset = 0.5f;
            this.mIsVirtualDisplayMode = false;
            this.mSurfaceCreated = false;
            this.mBitmapPaint = new Paint(2);
            this.mBitmapUsages = 0;
            Object obj = new Object();
            this.mLock = obj;
            this.mIsEngineAlive = false;
            this.mWakefulnessObserver = new AnonymousClass4();
            new DisplayController.OnDisplaysChangedListener() { // from class: com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.5
                public long mFixedRotationStartTime;

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onDisplayConfigurationChanged(int i2, Configuration configuration) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    ((WallpaperLoggerImpl) wallpaperLogger).log(canvasEngine.TAG, "onDisplayConfigurationChanged displayId=" + i2 + ", newConfig=" + configuration);
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onFixedRotationFinished(int i2) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    Configuration configuration = ImageWallpaper.this.getResources().getConfiguration();
                    int displayId = canvasEngine.getDisplayId();
                    DisplayInfo displayInfo = new DisplayInfo();
                    Display display = ((DisplayManager) ImageWallpaper.this.getSystemService("display")).getDisplay(displayId);
                    if (display != null) {
                        display.getDisplayInfo(displayInfo);
                    } else {
                        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(displayId, "  getCurrentDisplayRotation failed to get display. displayId=", canvasEngine.TAG);
                    }
                    int i3 = displayInfo.rotation;
                    long elapsedRealtime = SystemClock.elapsedRealtime() - this.mFixedRotationStartTime;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder sb = new StringBuilder("onFixedRotationFinished mRotation=");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, canvasEngine.mRotation, ", displayRotation=", i3, ", configRotation=");
                    sb.append(configuration.windowConfiguration.getRotation());
                    sb.append(", elapsed=");
                    sb.append(elapsedRealtime);
                    sb.append(", ");
                    sb.append(configuration);
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, sb.toString());
                    if (i2 != canvasEngine.getDisplayId()) {
                        WallpaperLogger wallpaperLogger2 = ImageWallpaper.this.mLogger;
                        ((WallpaperLoggerImpl) wallpaperLogger2).log(canvasEngine.TAG, "onFixedRotationFinished not my display : myId=" + canvasEngine.getDisplayId() + ", fixedRotationId=" + i2);
                        return;
                    }
                    if (canvasEngine.mRotation != i3) {
                        ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(canvasEngine.TAG, "onFixedRotationFinished Error orientation. So update Again.");
                        canvasEngine.mRotation = i3;
                        int currentWhich = canvasEngine.mHelper.getCurrentWhich();
                        if (!canvasEngine.mHelper.hasIntelligentCropHints(currentWhich)) {
                            canvasEngine.updateWallpaperOffset(currentWhich, canvasEngine.mRotation);
                        }
                    }
                    canvasEngine.mIsFixedRotationInProgress = false;
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onFixedRotationStarted(int i2, int i3) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i2, i3, "onFixedRotationStarted displayId=", ", newRotation=", ", mRotation=");
                    m.append(canvasEngine.mRotation);
                    m.append(", ");
                    m.append(ImageWallpaper.this.getResources().getConfiguration());
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, m.toString());
                    this.mFixedRotationStartTime = SystemClock.elapsedRealtime();
                    if (i2 == canvasEngine.getDisplayId()) {
                        canvasEngine.mIsFixedRotationInProgress = true;
                        if (canvasEngine.mRotation != i3) {
                            canvasEngine.mRotation = i3;
                            return;
                        }
                        return;
                    }
                    WallpaperLogger wallpaperLogger2 = ImageWallpaper.this.mLogger;
                    ((WallpaperLoggerImpl) wallpaperLogger2).log(canvasEngine.TAG, "onFixedRotationStarted not my display : myId=" + canvasEngine.getDisplayId() + ", fixedRotationId=" + i2);
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onDisplayAdded(int i2) {
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onDisplayRemoved(int i2) {
                }
            };
            new IRotationWatcher.Stub() { // from class: com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.6
                public final void onRotationChanged(int i2) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "onRotationChanged: newRotation=", ", mRotation=");
                    m.append(CanvasEngine.this.mRotation);
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, m.toString());
                    CanvasEngine canvasEngine2 = CanvasEngine.this;
                    if (canvasEngine2.mRotation != i2) {
                        ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(canvasEngine2.TAG, "onRotationChanged rotation is changed ");
                        CanvasEngine canvasEngine3 = CanvasEngine.this;
                        canvasEngine3.mRotation = i2;
                        int currentWhich = canvasEngine3.mHelper.getCurrentWhich();
                        if (CanvasEngine.this.mHelper.hasIntelligentCropHints(currentWhich)) {
                            CanvasEngine.this.updateSurfaceSize(currentWhich);
                        }
                    }
                }
            };
            this.mPluginHomeWallpaperConsumer = new AnonymousClass7();
            new AnonymousClass8();
            Log.d(this.TAG, "CanvasEngine 2");
            setFixedSizeAllowed(true);
            setShowForAllUsers(true);
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = new WallpaperLocalColorExtractor(ImageWallpaper.this.mLongExecutor, obj, new WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback(ImageWallpaper.this) { // from class: com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.2
                @Override // com.android.systemui.wallpapers.WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback
                public final void onActivated() {
                    CanvasEngine.this.setOffsetNotificationsEnabled(true);
                }

                @Override // com.android.systemui.wallpapers.WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback
                public final void onColorsProcessed(List list, List list2) {
                    int i = CanvasEngine.$r8$clinit;
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    canvasEngine.getClass();
                    try {
                        canvasEngine.notifyLocalColorsChanged(list, list2);
                    } catch (RuntimeException e) {
                        Log.e(canvasEngine.TAG, e.getMessage(), e);
                    }
                }

                @Override // com.android.systemui.wallpapers.WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback
                public final void onDeactivated() {
                    CanvasEngine.this.setOffsetNotificationsEnabled(false);
                }

                @Override // com.android.systemui.wallpapers.WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback
                public final void onMiniBitmapUpdated() {
                    CanvasEngine.this.onMiniBitmapUpdated();
                }
            });
            this.mWallpaperLocalColorExtractor = wallpaperLocalColorExtractor;
            if (ImageWallpaper.this.mPagesComputed) {
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda5(wallpaperLocalColorExtractor, ImageWallpaper.this.mPages));
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }
    }
}
