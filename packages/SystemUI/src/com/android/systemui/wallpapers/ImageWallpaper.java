package com.android.systemui.wallpapers;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.material3.internal.colorUtil.Frame$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.PluginWallpaper;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.effect.ColorDecorFilterHelper;
import com.android.systemui.wallpaper.effect.HighlightFilterHelper;
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
import com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1;
import com.android.systemui.wallpapers.WallpaperLocalColorExtractor;
import com.android.wm.shell.common.DisplayController;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperEngineManager;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
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
    public final WindowManagerProvider mWindowManagerProvider;
    public HandlerThread mWorker;
    public final ArrayList mLocalColorsToAdd = new ArrayList();
    public final ArraySet mColorAreas = new ArraySet();
    public volatile int mPages = 1;
    public boolean mPagesComputed = false;
    public final HashMap mCanvasEngineList = new HashMap();
    public final ExecutorService mKeygurdEventExecutor = Executors.newFixedThreadPool(2);
    private SettingsHelper.OnChangedCallback mSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.wallpapers.ImageWallpaper$$ExternalSyntheticLambda0
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            RectF rectF = ImageWallpaper.LOCAL_COLOR_BOUNDS;
            ImageWallpaper imageWallpaper = this.f$0;
            if (Settings.System.getUriFor(SettingsHelper.INDEX_DARK_FILTER_WALLPAPER).equals(uri)) {
                Log.i("ImageWallpaper", " Apply Dark mode option changed");
                ArrayList engines = LiveWallpaperEngineManager.getInstance(imageWallpaper.getApplicationContext()).getEngines();
                int size = engines.size();
                int i = 0;
                while (i < size) {
                    Object obj = engines.get(i);
                    i++;
                    LiveWallpaperService.BaseEngine baseEngine = (LiveWallpaperService.BaseEngine) obj;
                    if ((baseEngine instanceof ImageWallpaper.IntegratedEngine) && !baseEngine.isPreview()) {
                        Log.d("ImageWallpaper", "onChangeApplyDark : notify change to BaseEngine. semGetWallpaperFlags() = " + baseEngine.semGetWallpaperFlags());
                        ((ImageWallpaper.IntegratedEngine) baseEngine).mSubEngine.onApplyDarkModeDimSettingChanged();
                    }
                }
                for (Map.Entry entry : imageWallpaper.mCanvasEngineList.entrySet()) {
                    ImageWallpaper.CanvasEngine canvasEngine = (ImageWallpaper.CanvasEngine) entry.getValue();
                    if (canvasEngine != null) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(((Integer) entry.getKey()).intValue(), "onChangeApplyDark : notify the change to CanvasEngine. Engine displayId = ", "ImageWallpaper");
                        canvasEngine.drawFrameSynchronized(canvasEngine.mHelper.getCurrentWhich(), new Rect(canvasEngine.mSurfaceHolder.getSurfaceFrame()));
                    }
                }
            }
        }
    };

    public class BaseEngine extends LiveWallpaperService.BaseEngine {
        public int mWhich;

        public BaseEngine(int i) {
            super();
            this.mWhich = i;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onWallpaperFlagsChanged(int i) {
            this.mWhich = i;
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "onWallpaperFlagsChanged: which = ", "ImageWallpaper");
        }

        public boolean supportsLocalColorExtraction() {
            return true;
        }
    }

    public class IntegratedEngine extends BaseEngine {
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
        public class AnonymousClass1 {
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
                                    ImageWallpaper.IntegratedEngine.AnonymousClass1 anonymousClass1 = this.f$0;
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
                                                    if (videoController.mIsReleased || (playerSession = videoController.mActiveSession) == null) {
                                                        Log.i(videoController.TAG, "getCurrentPosition: released");
                                                        currentPosition = 0;
                                                    } else {
                                                        currentPosition = playerSession.getCurrentPosition();
                                                    }
                                                } finally {
                                                }
                                            }
                                            bundle2.putInt("current_position", currentPosition);
                                            return bundle2;
                                        }
                                    }
                                    return bundle2;
                                }
                            }).get();
                        } catch (InterruptedException | ExecutionException e) {
                            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("onEventReceived: e = ", e, integratedEngine.TAG, e);
                            return null;
                        }
                    }
                    if (i != 724 && i != 732) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onEventReceived: event = ", integratedEngine.TAG);
                        return null;
                    }
                }
                ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda4 imageWallpaper$CanvasEngine$$ExternalSyntheticLambda4 = new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda4(this, i, bundle);
                HandlerThread handlerThread = ImageWallpaper.this.mWorker;
                if (handlerThread == null) {
                    Log.w(integratedEngine.TAG, "runAsWorkerThread: mWorker is null.");
                    return null;
                }
                handlerThread.getThreadHandler().post(imageWallpaper$CanvasEngine$$ExternalSyntheticLambda4);
                return null;
            }
        }

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$IntegratedEngine$2, reason: invalid class name */
        public class AnonymousClass2 implements WallpaperEngineCallback {
            public BLASTBufferQueue mBbqOfPendingTransactionRequest;
            public final Choreographer mChoreographer;
            public final AnonymousClass1 mChoreographerFrameCallback;
            public final Handler mChoreographerHandler;

            /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.wallpapers.ImageWallpaper$IntegratedEngine$2$1] */
            public AnonymousClass2() {
                Choreographer choreographer = Choreographer.getInstance();
                this.mChoreographer = choreographer;
                this.mChoreographerHandler = new Handler(choreographer.getLooper());
                this.mChoreographerFrameCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.wallpapers.ImageWallpaper.IntegratedEngine.2.1
                    @Override // android.view.Choreographer.FrameCallback
                    public final void doFrame(long j) {
                        IntegratedEngine integratedEngine = IntegratedEngine.this;
                        if (integratedEngine.mSubEngine.draw(integratedEngine.mSurfaceHolder)) {
                            AnonymousClass2.this.mChoreographer.postFrameCallback(this);
                        }
                    }
                };
            }

            public final SurfaceControl getSurfaceControl() throws NoSuchFieldException {
                int i = IntegratedEngine.$r8$clinit;
                IntegratedEngine integratedEngine = IntegratedEngine.this;
                integratedEngine.getClass();
                try {
                    Field declaredField = WallpaperService.Engine.class.getDeclaredField("mSurfaceControl");
                    declaredField.setAccessible(true);
                    return (SurfaceControl) declaredField.get(integratedEngine);
                } catch (Exception e) {
                    WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("getSurfaceControl: e=", e, integratedEngine.TAG, e);
                    return null;
                }
            }
        }

        /* renamed from: -$$Nest$mrecreateSurfaceControl, reason: not valid java name */
        public static void m3230$$Nest$mrecreateSurfaceControl(IntegratedEngine integratedEngine) {
            integratedEngine.getClass();
            WallpaperService.Engine.SurfaceData surfaceDataSemCreateSurface = super.semCreateSurface(true, 1.0f);
            if (surfaceDataSemCreateSurface != null) {
                ImageWallpaper.this.mMainThreadHandler.postDelayed(new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda6(integratedEngine, surfaceDataSemCreateSurface, 2), 500L);
            }
        }

        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.wallpapers.ImageWallpaper$IntegratedEngine$3] */
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
            return new ImageEngine(imageSource, anonymousClass2, settingsHelper, imageWallpaper2.mSystemWallpaperColors, imageWallpaper2.mLongExecutor, imageWallpaper2.mKeyguardWallpaper, imageWallpaper2.mKeyguardUpdateMonitor, imageWallpaper2.mDozeParameters);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            WallpaperEngine wallpaperEngine = this.mSubEngine;
            if (wallpaperEngine != null) {
                wallpaperEngine.dump(str, fileDescriptor, printWriter, strArr);
            }
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final Bundle onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
            if (!"samsung.android.wallpaper.blocktoucharea".equals(str)) {
                this.mSubEngine.onCommand(str, i, i2, i3, bundle, z);
            }
            return super.onCommand(str, i, i2, i3, bundle, z);
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x00c3  */
        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onCreate(SurfaceHolder surfaceHolder) {
            WallpaperEngine wallpaperEngineCreateImageEngine;
            WallpaperEngine gifEngine;
            super.onCreate(surfaceHolder);
            int iSemGetWallpaperFlags = semGetWallpaperFlags();
            String strM = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(iSemGetWallpaperFlags, "ImageWallpaper_", "[Integrated]");
            this.TAG = strM;
            Log.i(strM, "onCreate");
            this.mSurfaceHolder = surfaceHolder;
            this.mWallpaperManager = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext());
            if (WhichChecker.isFlagEnabled(iSemGetWallpaperFlags, 2)) {
                KeyguardWallpaper keyguardWallpaper = ImageWallpaper.this.mKeyguardWallpaper;
                int i = this.mWhich;
                ((HashMap) ((KeyguardWallpaperController) keyguardWallpaper).mEventListeners).put(Integer.valueOf(WhichChecker.isFlagEnabled(i, 16) ? 1 : 0), this.mKeyguardWallpaperEventListener);
            }
            setShowForAllUsers(true);
            setFixedSizeAllowed(true);
            int i2 = this.mWallpaperType;
            if (i2 == 0) {
                wallpaperEngineCreateImageEngine = createImageEngine(iSemGetWallpaperFlags);
            } else if (i2 == 1) {
                wallpaperEngineCreateImageEngine = new MotionEngine(ImageWallpaper.this.getBaseContext(), this.mWallpaperEngineCallback);
            } else if (i2 == 3) {
                AnonymousClass2 anonymousClass2 = this.mWallpaperEngineCallback;
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                CoverWallpaper coverWallpaper = imageWallpaper.mCoverWallpaper;
                PluginWallpaper pluginWallpaper = imageWallpaper.mPluginWallpaper;
                SettingsHelper settingsHelper = imageWallpaper.mSettingsHelper;
                ImageWallpaper imageWallpaper2 = ImageWallpaper.this;
                wallpaperEngineCreateImageEngine = new MultipackEngine(anonymousClass2, coverWallpaper, pluginWallpaper, settingsHelper, imageWallpaper2.mSystemWallpaperColors, imageWallpaper2.mLongExecutor, imageWallpaper2.mDozeParameters, imageWallpaper2.mKeyguardWallpaper, imageWallpaper2.mKeyguardUpdateMonitor);
            } else if (i2 != 4) {
                if (i2 == 5) {
                    gifEngine = new GifEngine(new GifSource(ImageWallpaper.this.getBaseContext(), iSemGetWallpaperFlags, ImageWallpaper.this.mCoverWallpaper), this.mWallpaperEngineCallback);
                } else if (i2 == 8) {
                    ImageWallpaper imageWallpaper3 = ImageWallpaper.this;
                    int currentUserId = getCurrentUserId();
                    ImageWallpaper imageWallpaper4 = ImageWallpaper.this;
                    gifEngine = new VideoEngine(new VideoSource(imageWallpaper3, iSemGetWallpaperFlags, 1, currentUserId, imageWallpaper4.mCoverWallpaper, imageWallpaper4.mPluginWallpaper), this.mWallpaperEngineCallback, ImageWallpaper.this.mKeyguardUpdateMonitor);
                } else if (i2 != 1000) {
                    Log.e(this.TAG, "createEngine: Unknown wallpaper type = " + this.mWallpaperType);
                    wallpaperEngineCreateImageEngine = createImageEngine(iSemGetWallpaperFlags);
                }
                wallpaperEngineCreateImageEngine = gifEngine;
            } else {
                wallpaperEngineCreateImageEngine = new AnimatedEngine(ImageWallpaper.this.getBaseContext(), this.mWallpaperEngineCallback);
            }
            this.mSubEngine = wallpaperEngineCreateImageEngine;
            wallpaperEngineCreateImageEngine.onCreate(surfaceHolder);
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper.this.mMainThreadHandler.post(new ImageWallpaper$$ExternalSyntheticLambda1(this.mWakefulnessObserver, 1));
            }
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final void onDestroy() {
            super.onDestroy();
            ((HashMap) ((KeyguardWallpaperController) ImageWallpaper.this.mKeyguardWallpaper).mEventListeners).remove(Integer.valueOf(WhichChecker.isFlagEnabled(this.mWhich, 16) ? 1 : 0));
            ImageWallpaper.this.mCanvasEngineList.remove(Integer.valueOf(getDisplayId()), this);
            this.mSubEngine.onDestroy();
            int iSemGetWallpaperFlags = semGetWallpaperFlags();
            if (ImageWallpaper.this.mCoverWallpaper != null && (WhichChecker.isWatchFace(iSemGetWallpaperFlags) || WhichChecker.isVirtualDisplay(iSemGetWallpaperFlags))) {
                ImageWallpaper.this.mCoverWallpaper.getClass();
            }
            if (ImageWallpaper.this.mPluginWallpaper != null && WhichChecker.isFlagEnabled(iSemGetWallpaperFlags, 2)) {
                PluginWallpaperController pluginWallpaperController = (PluginWallpaperController) ImageWallpaper.this.mPluginWallpaper;
                pluginWallpaperController.getClass();
                Log.d("PluginWallpaperController", "onWallpaperDestroyed: which = " + iSemGetWallpaperFlags);
                if ((iSemGetWallpaperFlags & 1) != 1 && !WhichChecker.isFlagEnabled(iSemGetWallpaperFlags, 8)) {
                    int screen = PluginWallpaperController.getScreen(iSemGetWallpaperFlags);
                    int wallpaperId = pluginWallpaperController.mWallpaperManager.getWallpaperId(WhichChecker.getSourceWhich(iSemGetWallpaperFlags));
                    StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(wallpaperId, screen, "onWallpaperDestroyed: wallpaperId = ", ", mWallpaperId[", "] = ");
                    int[] iArr = pluginWallpaperController.mWallpaperId;
                    RecyclerView$$ExternalSyntheticOutline0.m(iArr[screen], "PluginWallpaperController", sbM);
                    if (wallpaperId != iArr[screen]) {
                        pluginWallpaperController.mWallpaperConsumers.put(screen, null);
                        iArr[screen] = wallpaperId;
                    }
                }
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper.this.mMainThreadHandler.post(new ImageWallpaper$$ExternalSyntheticLambda1(this.mWakefulnessObserver, 0));
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
            AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("onSwitchDisplayChanged: isFolded = ", this.TAG, z);
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
            int iSemGetWallpaperFlags = semGetWallpaperFlags();
            String strM = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(iSemGetWallpaperFlags, "ImageWallpaper_", "[Integrated]");
            this.TAG = strM;
            Log.i(strM, "onWallpaperFlagsChanged: which = " + i + ", semWhich = " + iSemGetWallpaperFlags);
            this.mSubEngine.onWhichChanged(iSemGetWallpaperFlags);
            if (WhichChecker.isFlagEnabled(iSemGetWallpaperFlags, 1)) {
                ((HashMap) ((KeyguardWallpaperController) ImageWallpaper.this.mKeyguardWallpaper).mEventListeners).remove(Integer.valueOf(WhichChecker.isFlagEnabled(iSemGetWallpaperFlags, 16) ? 1 : 0));
                return;
            }
            KeyguardWallpaper keyguardWallpaper = ImageWallpaper.this.mKeyguardWallpaper;
            ((HashMap) ((KeyguardWallpaperController) keyguardWallpaper).mEventListeners).put(Integer.valueOf(WhichChecker.isFlagEnabled(iSemGetWallpaperFlags, 16) ? 1 : 0), this.mKeyguardWallpaperEventListener);
        }

        public final boolean shouldWaitForEngineShown() {
            return this.mSubEngine.shouldWaitForEngineShown();
        }

        public final boolean shouldZoomOutWallpaper() {
            return false;
        }
    }

    public ImageWallpaper(DelayableExecutor delayableExecutor, UserTracker userTracker, WindowManagerProvider windowManagerProvider, WallpaperLogger wallpaperLogger, SystemWallpaperColors systemWallpaperColors, KeyguardWallpaper keyguardWallpaper, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, CoverWallpaper coverWallpaper, PluginWallpaper pluginWallpaper, DozeParameters dozeParameters) {
        this.mLongExecutor = delayableExecutor;
        this.mWindowManagerProvider = windowManagerProvider;
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
        ArrayList engines = LiveWallpaperEngineManager.getInstance(applicationContext).getEngines();
        int size = engines.size();
        int i = 0;
        while (i < size) {
            Object obj = engines.get(i);
            i++;
            LiveWallpaperService.BaseEngine baseEngine = (LiveWallpaperService.BaseEngine) obj;
            if ((baseEngine instanceof IntegratedEngine) && !baseEngine.isPreview()) {
                int iConvertDisplayIdToMode = WhichChecker.convertDisplayIdToMode(displayId, applicationContext);
                int iSemGetWallpaperFlags = baseEngine.semGetWallpaperFlags() & 60;
                RecyclerView$$ExternalSyntheticOutline0.m(iSemGetWallpaperFlags, "ImageWallpaper", MutableObjectList$$ExternalSyntheticOutline0.m(displayId, iConvertDisplayIdToMode, "onConfigurationChanged: displayId = ", ", modeFromDisplayId = ", ", modeFromEngine = "));
                if (iConvertDisplayIdToMode == iSemGetWallpaperFlags) {
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

    public class CanvasEngine extends BaseEngine implements DisplayManager.DisplayListener {
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
        public class AnonymousClass3 implements ImageWallpaperCanvasHelper.Callback {
            public AnonymousClass3() {
            }
        }

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$4, reason: invalid class name */
        public class AnonymousClass4 implements WakefulnessLifecycle.Observer {
            public AnonymousClass4() {
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2 imageWallpaper$CanvasEngine$$ExternalSyntheticLambda2 = new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2(this, 1);
                int i = CanvasEngine.$r8$clinit;
                CanvasEngine.this.runAsWorkerThread(imageWallpaper$CanvasEngine$$ExternalSyntheticLambda2);
            }
        }

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$7, reason: invalid class name */
        public class AnonymousClass7 implements Consumer {
            public AnonymousClass7() {
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CanvasEngine canvasEngine = CanvasEngine.this;
                ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda6 imageWallpaper$CanvasEngine$$ExternalSyntheticLambda6 = new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda6(this, (Boolean) obj);
                int i = CanvasEngine.$r8$clinit;
                canvasEngine.runAsWorkerThread(imageWallpaper$CanvasEngine$$ExternalSyntheticLambda6);
            }
        }

        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$8, reason: invalid class name */
        public class AnonymousClass8 implements Consumer {
            public AnonymousClass8() {
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CanvasEngine canvasEngine = CanvasEngine.this;
                ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2 imageWallpaper$CanvasEngine$$ExternalSyntheticLambda2 = new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2(this, 2);
                int i = CanvasEngine.$r8$clinit;
                canvasEngine.runAsWorkerThread(imageWallpaper$CanvasEngine$$ExternalSyntheticLambda2);
            }
        }

        public class DrawState {
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
        public static void m3229$$Nest$mupdatePluginWallpaper(CanvasEngine canvasEngine) {
            int iSemGetWallpaperFlags = canvasEngine.semGetWallpaperFlags();
            if (!WhichChecker.isWatchFace(iSemGetWallpaperFlags)) {
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
                WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).forceRebindWallpaper(iSemGetWallpaperFlags);
                return;
            }
            if (canvasEngine.mSurfaceCreated) {
                if (canvasEngine.updateSurfaceSizeIfNeed(iSemGetWallpaperFlags) && WhichChecker.isWatchFace(iSemGetWallpaperFlags)) {
                    return;
                }
                canvasEngine.drawFrameSynchronized(iSemGetWallpaperFlags, new Rect(canvasEngine.mSurfaceHolder.getSurfaceFrame()));
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
            this.mSurfaceLock = new Object();
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
                        ClockEventController$$ExternalSyntheticOutline0.m(displayId, "  getCurrentDisplayRotation failed to get display. displayId=", canvasEngine.TAG);
                    }
                    int i3 = displayInfo.rotation;
                    long jElapsedRealtime = SystemClock.elapsedRealtime() - this.mFixedRotationStartTime;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder sb = new StringBuilder("onFixedRotationFinished mRotation=");
                    ViewPager$$ExternalSyntheticOutline0.m(sb, canvasEngine.mRotation, ", displayRotation=", i3, ", configRotation=");
                    sb.append(configuration.windowConfiguration.getRotation());
                    sb.append(", elapsed=");
                    sb.append(jElapsedRealtime);
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
                    StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i2, i3, "onFixedRotationStarted displayId=", ", newRotation=", ", mRotation=");
                    sbM.append(canvasEngine.mRotation);
                    sbM.append(", ");
                    sbM.append(ImageWallpaper.this.getResources().getConfiguration());
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, sbM.toString());
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
                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "onRotationChanged: newRotation=", ", mRotation=");
                    sbM.append(CanvasEngine.this.mRotation);
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, sbM.toString());
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
            this.mPluginWallpaperConsumer = anonymousClass8;
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
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda4(wallpaperLocalColorExtractor, ImageWallpaper.this.mPages));
            }
            ((PluginWallpaperController) ImageWallpaper.this.mPluginWallpaper).setWallpaperUpdateConsumer(this.mWhich, anonymousClass8);
        }

        public final void addLocalColorsAreas(List list) {
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.getClass();
            if (list.size() > 0) {
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda0(wallpaperLocalColorExtractor, list, 1));
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
                int iFloor = (int) Math.floor(rectF.centerX() / f);
                RectF rectF2 = new RectF();
                if (this.mImgWidth != 0 && (i = this.mImgHeight) != 0 && this.mDisplayWidth > 0 && (i2 = this.mDisplayHeight) > 0) {
                    rectF2.bottom = rectF.bottom;
                    rectF2.top = rectF.top;
                    float fMin = this.mDisplayWidth * Math.min(i / i2, 1.0f);
                    int i4 = this.mImgWidth;
                    float fMin2 = Math.min(1.0f, i4 > 0 ? fMin / i4 : 1.0f);
                    float f4 = iFloor * ((1.0f - fMin2) / (ImageWallpaper.this.mPages - 1));
                    rectF2.left = MathUtils.constrain((f2 * fMin2) + f4, 0.0f, 1.0f);
                    float fConstrain = MathUtils.constrain((f3 * fMin2) + f4, 0.0f, 1.0f);
                    rectF2.right = fConstrain;
                    if (rectF2.left > fConstrain) {
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
            if (TextUtils.isEmpty(this.mHelper.mColorDecorFilterData)) {
                int iConvertDisplayIdToMode = WhichChecker.convertDisplayIdToMode(getDisplayId(), ImageWallpaper.this.getApplicationContext());
                Boolean bool = Boolean.FALSE;
                Log.i("HighlightFilterHelper", "canApplyFilterOnHome : elapsed=" + (SystemClock.elapsedRealtime() - SystemClock.elapsedRealtime()) + ", mode=" + iConvertDisplayIdToMode + ", result=" + bool + ", wait=false");
            }
            Log.d(this.TAG, " determineHighlightFilterAmount : -1");
        }

        public final DrawState drawFrameOnCanvas(int i, long j, Rect rect, Bitmap bitmap, float f, int i2) {
            long j2;
            long jElapsedRealtime;
            long jElapsedRealtime2;
            DrawState drawState;
            Rect surfaceFrame;
            int iWidth;
            int iHeight;
            float f2;
            float f3;
            Integer dimFilterColor;
            if (!WallpaperUtils.isValidBitmap(bitmap)) {
                return null;
            }
            long jElapsedRealtime3 = SystemClock.elapsedRealtime();
            try {
                surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
                Rect nearestCropHint = IntelligentCropHelper.getNearestCropHint(new Point(surfaceFrame.width(), surfaceFrame.height()), this.mHelper.getIntelligentCropHints(i));
                if (nearestCropHint != null) {
                    try {
                        nearestCropHint = new Rect((int) (nearestCropHint.left * f), (int) (nearestCropHint.top * f), (int) (nearestCropHint.right * f), (int) (nearestCropHint.bottom * f));
                    } catch (Exception e) {
                        e = e;
                        jElapsedRealtime = jElapsedRealtime3;
                        jElapsedRealtime2 = jElapsedRealtime;
                        j2 = jElapsedRealtime2;
                        WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : failed draw bitmap. e=", e, this.TAG, e);
                        drawState = null;
                        long jElapsedRealtime4 = SystemClock.elapsedRealtime() - j;
                        String str = this.TAG;
                        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", jElapsedRealtime4, ", bmpPrepareDur=");
                        sbM.append(j2 - j);
                        sbM.append(", filterApplyDur=");
                        sbM.append(jElapsedRealtime2 - j2);
                        sbM.append(", drawDur=");
                        sbM.append(jElapsedRealtime - jElapsedRealtime2);
                        sbM.append(", drawnState=(");
                        sbM.append(drawState);
                        sbM.append(")");
                        Log.i(str, sbM.toString());
                        return drawState;
                    }
                }
                Matrix matrix = new Matrix();
                int iWidth2 = nearestCropHint != null ? nearestCropHint.width() : bitmap.getWidth();
                int iHeight2 = nearestCropHint != null ? nearestCropHint.height() : bitmap.getHeight();
                iWidth = surfaceFrame.width();
                j2 = jElapsedRealtime3;
                try {
                    iHeight = surfaceFrame.height();
                    if (iWidth2 * iHeight > iWidth * iHeight2) {
                        f2 = iHeight;
                        f3 = iHeight2;
                    } else {
                        f2 = iWidth;
                        f3 = iWidth2;
                    }
                    float f4 = f2 / f3;
                    float fM = Frame$$ExternalSyntheticOutline0.m(iWidth2, f4, iWidth, 0.5f);
                    float fM2 = Frame$$ExternalSyntheticOutline0.m(iHeight2, f4, iHeight, 0.5f);
                    matrix.setScale(f4, f4);
                    if (nearestCropHint != null) {
                        matrix.preTranslate(-nearestCropHint.left, -nearestCropHint.top);
                    }
                    matrix.postTranslate(Math.round(fM), Math.round(fM2));
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
                    jElapsedRealtime2 = SystemClock.elapsedRealtime();
                    try {
                        Log.i(this.TAG, "drawFrameOnCanvas : which=" + i + ", bmpW=" + bitmap.getWidth() + ", bmpH=" + bitmap.getHeight() + ", bmpScale=" + f + ", src=" + nearestCropHint + ", dest=" + surfaceFrame + ", highlight=-1, dimColor=" + dimFilterColor + ", drawRepeatCount=" + i2);
                        for (int i3 = 0; i3 < i2; i3++) {
                            Surface surface = this.mSurfaceHolder.getSurface();
                            Canvas canvasLockHardwareWideColorGamutCanvas = this.mHelper.mIsWcgContent ? surface.lockHardwareWideColorGamutCanvas() : surface.lockHardwareCanvas();
                            if (canvasLockHardwareWideColorGamutCanvas == null) {
                                Log.e(this.TAG, "drawFrameOnCanvas : canvas is NULL");
                                throw new RuntimeException("failed to lock the canvas - " + i3);
                            }
                            try {
                                canvasLockHardwareWideColorGamutCanvas.drawBitmap(filterAppliedBitmap, matrix, this.mBitmapPaint);
                                surface.unlockCanvasAndPost(canvasLockHardwareWideColorGamutCanvas);
                            } catch (Throwable th) {
                                surface.unlockCanvasAndPost(canvasLockHardwareWideColorGamutCanvas);
                                throw th;
                            }
                        }
                        if (filterAppliedBitmap != null && filterAppliedBitmap != bitmap) {
                            filterAppliedBitmap.recycle();
                        }
                        jElapsedRealtime = SystemClock.elapsedRealtime();
                    } catch (Exception e2) {
                        e = e2;
                        jElapsedRealtime = j2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    jElapsedRealtime = j2;
                    jElapsedRealtime2 = jElapsedRealtime;
                    WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : failed draw bitmap. e=", e, this.TAG, e);
                    drawState = null;
                    long jElapsedRealtime42 = SystemClock.elapsedRealtime() - j;
                    String str2 = this.TAG;
                    StringBuilder sbM2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", jElapsedRealtime42, ", bmpPrepareDur=");
                    sbM2.append(j2 - j);
                    sbM2.append(", filterApplyDur=");
                    sbM2.append(jElapsedRealtime2 - j2);
                    sbM2.append(", drawDur=");
                    sbM2.append(jElapsedRealtime - jElapsedRealtime2);
                    sbM2.append(", drawnState=(");
                    sbM2.append(drawState);
                    sbM2.append(")");
                    Log.i(str2, sbM2.toString());
                    return drawState;
                }
            } catch (Exception e4) {
                e = e4;
                j2 = jElapsedRealtime3;
            }
            try {
            } catch (Exception e5) {
                e = e5;
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : failed draw bitmap. e=", e, this.TAG, e);
                drawState = null;
                long jElapsedRealtime422 = SystemClock.elapsedRealtime() - j;
                String str22 = this.TAG;
                StringBuilder sbM22 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", jElapsedRealtime422, ", bmpPrepareDur=");
                sbM22.append(j2 - j);
                sbM22.append(", filterApplyDur=");
                sbM22.append(jElapsedRealtime2 - j2);
                sbM22.append(", drawDur=");
                sbM22.append(jElapsedRealtime - jElapsedRealtime2);
                sbM22.append(", drawnState=(");
                sbM22.append(drawState);
                sbM22.append(")");
                Log.i(str22, sbM22.toString());
                return drawState;
            }
            if (surfaceFrame.equals(rect)) {
                drawState = new DrawState(this, i, iWidth, iHeight, false, dimFilterColor != null);
                long jElapsedRealtime4222 = SystemClock.elapsedRealtime() - j;
                String str222 = this.TAG;
                StringBuilder sbM222 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", jElapsedRealtime4222, ", bmpPrepareDur=");
                sbM222.append(j2 - j);
                sbM222.append(", filterApplyDur=");
                sbM222.append(jElapsedRealtime2 - j2);
                sbM222.append(", drawDur=");
                sbM222.append(jElapsedRealtime - jElapsedRealtime2);
                sbM222.append(", drawnState=(");
                sbM222.append(drawState);
                sbM222.append(")");
                Log.i(str222, sbM222.toString());
                return drawState;
            }
            Log.w(this.TAG, "drawFrameOnCanvas : surface size mismatch. curFrame=" + surfaceFrame + ", requestedFrame=" + rect);
            drawState = null;
            long jElapsedRealtime42222 = SystemClock.elapsedRealtime() - j;
            String str2222 = this.TAG;
            StringBuilder sbM2222 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", jElapsedRealtime42222, ", bmpPrepareDur=");
            sbM2222.append(j2 - j);
            sbM2222.append(", filterApplyDur=");
            sbM2222.append(jElapsedRealtime2 - j2);
            sbM2222.append(", drawDur=");
            sbM2222.append(jElapsedRealtime - jElapsedRealtime2);
            sbM2222.append(", drawnState=(");
            sbM2222.append(drawState);
            sbM2222.append(")");
            Log.i(str2222, sbM2222.toString());
            return drawState;
        }

        public final void drawFrameSynchronized(int i, Rect rect) {
            synchronized (this.mLock) {
                drawFullQualityFrame(i, rect);
                finishRendering();
            }
        }

        public final void drawFullQualityFrame(final int i, final Rect rect) {
            if (!this.mIsEngineAlive) {
                Log.d(this.TAG, "drawFullQualityFrame: engine is destroyed");
                return;
            }
            Trace.beginSection("ImageWallpaper.CanvasEngine#drawFrame");
            final long jElapsedRealtime = SystemClock.elapsedRealtime();
            updateWallpaperOffset(i, this.mRotation);
            this.mHelper.useWallpaperBitmap(i, new Consumer() { // from class: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImageWallpaper.CanvasEngine canvasEngine = this.f$0;
                    int i2 = ImageWallpaper.CanvasEngine.$r8$clinit;
                    canvasEngine.mLastDrawnState = canvasEngine.drawFrameOnCanvas(i, jElapsedRealtime, rect, (Bitmap) obj, 1.0f, 1);
                }
            });
            Trace.endSection();
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
            WindowManagerProvider windowManagerProvider = ImageWallpaper.this.mWindowManagerProvider;
            Context displayContext = getDisplayContext();
            ((WindowManagerProviderImpl) windowManagerProvider).getClass();
            Rect bounds = WindowManagerUtils.getWindowManager(displayContext).getCurrentWindowMetrics().getBounds();
            final WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            final int iWidth = bounds.width();
            final int iHeight = bounds.height();
            wallpaperLocalColorExtractor.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpapers.WallpaperLocalColorExtractor$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperLocalColorExtractor wallpaperLocalColorExtractor2 = wallpaperLocalColorExtractor;
                    int i = iWidth;
                    int i2 = iHeight;
                    synchronized (wallpaperLocalColorExtractor2.mLock) {
                        try {
                            if (i == wallpaperLocalColorExtractor2.mDisplayWidth && i2 == wallpaperLocalColorExtractor2.mDisplayHeight) {
                                return;
                            }
                            wallpaperLocalColorExtractor2.mDisplayWidth = i;
                            wallpaperLocalColorExtractor2.mDisplayHeight = i2;
                            wallpaperLocalColorExtractor2.processLocalColorsInternal();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x003a A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:12:0x003b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean isFixedOrientationWallpaper(int i, int i2) {
            boolean z;
            int iConvertDisplayIdToMode = WhichChecker.convertDisplayIdToMode(i, ImageWallpaper.this.getApplicationContext());
            boolean z2 = false;
            if (iConvertDisplayIdToMode >= 0) {
                int i3 = iConvertDisplayIdToMode | 1;
                Bundle wallpaperExtras = ((WallpaperService) ImageWallpaper.this).mWallpaperManager.getWallpaperExtras(i3, i2);
                if (wallpaperExtras != null) {
                    z = wallpaperExtras.getBoolean("isFixedOrientation");
                    KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(i3, i2, "isFixedOrientationWallpaper: which=", ", user=", ", fixedOrientation="), z, "ImageWallpaper");
                }
                if (!z) {
                    return true;
                }
                PackageManager packageManager = ImageWallpaper.this.getBaseContext().getPackageManager();
                boolean z3 = packageManager != null && packageManager.hasSystemFeature("com.samsung.feature.device_category_tablet");
                boolean z4 = Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mWallpaperManager.getLidState() == 0;
                boolean z5 = ImageWallpaper.this.mSettingsHelper.getHomescreenWallpaperSource(z4) == 0;
                if (i == 0 && ((Rune.WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER || z4) && !z3 && !z5 && !isPreview())) {
                    z2 = true;
                }
                String str = this.TAG;
                StringBuilder sb = new StringBuilder("isFixedOrientationWallpaper: feature=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, Rune.WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER, ", isTablet=", z3, ", isCoverDisplay=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z4, ", isCustomWallpaper=", z5, ", isPreview=");
                sb.append(isPreview());
                sb.append(", isFixedOrientation=");
                sb.append(z2);
                Log.i(str, sb.toString());
                return z2;
            }
            ClockEventController$$ExternalSyntheticOutline0.m(i, "isFixedOrientationWallpaper: incorrect mode. displayId = ", "ImageWallpaper");
            z = false;
            if (!z) {
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final WallpaperColors onComputeColors() {
            return null;
        }

        public final void onConfigurationChanged(Configuration configuration) {
            if (getDisplayId() != 0) {
                WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "onConfigurationChanged display id= " + getDisplayId() + " , newConfig =" + configuration);
            }
            runAsWorkerThread(new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda6(this, configuration, 0));
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final void onCreate(SurfaceHolder surfaceHolder) {
            CoverWallpaper coverWallpaper;
            super.onCreate(surfaceHolder);
            int displayId = getDisplayId();
            this.TAG = ReorderTile$$ExternalSyntheticOutline0.m(getWallpaperFlags() == 2 ? 2 : 1, "]", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(displayId, "ImageWallpaper[CanvasEngine_d", "_w"));
            Trace.beginSection("ImageWallpaper.CanvasEngine#onCreate");
            ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(this.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(displayId, "Engine onCreate: displayId = "));
            WallpaperManager wallpaperManager = (WallpaperManager) getDisplayContext().getSystemService(WallpaperManager.class);
            this.mWallpaperManager = wallpaperManager;
            this.mSurfaceHolder = surfaceHolder;
            Rect rectPeekBitmapDimensions = wallpaperManager.peekBitmapDimensions(getWallpaperFlags() != 2 ? 1 : 2, true);
            if (rectPeekBitmapDimensions != null) {
                this.mSurfaceHolder.setFixedSize(Math.max(128, rectPeekBitmapDimensions.width()), Math.max(128, rectPeekBitmapDimensions.height()));
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
            int iSemGetWallpaperFlags = semGetWallpaperFlags();
            if (WhichChecker.isSystemAndLock(iSemGetWallpaperFlags)) {
                iSemGetWallpaperFlags = (iSemGetWallpaperFlags & 60) | 1;
            }
            updateSurfaceSize(iSemGetWallpaperFlags);
            this.mHelper.mBitmapUpdateConsumer = new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1(this, 1);
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper.this.mMainThreadHandler.post(new ImageWallpaper$$ExternalSyntheticLambda1(this.mWakefulnessObserver, 1));
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
            wallpaperLocalColorExtractor.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpapers.WallpaperLocalColorExtractor$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperLocalColorExtractor wallpaperLocalColorExtractor2 = wallpaperLocalColorExtractor;
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
                ImageWallpaper.this.mMainThreadHandler.post(new ImageWallpaper$$ExternalSyntheticLambda1(this.mWakefulnessObserver, 0));
            }
            ImageWallpaper.this.mCanvasEngineList.remove(Integer.valueOf(getDisplayId()), this);
            IBinder windowTokenAsBinder = super.getWindowTokenAsBinder();
            if (windowTokenAsBinder != null) {
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                imageWallpaperCanvasHelper.mWallpaperManager.setDisplayOffset(windowTokenAsBinder, 0, 0);
                imageWallpaperCanvasHelper.mSmartCropYOffset = 0;
            }
            this.mIsEngineAlive = false;
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
                    Log.i(imageWallpaperCanvasHelper.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("setSmartCropAllowed: ", z));
                }
                imageWallpaperCanvasHelper.mIsSmartCropAllowed = z;
                this.mLastWallpaperYOffset = f2;
            }
            int iRound = (f3 <= 0.0f || f3 > 1.0f) ? 1 : Math.round(1.0f / f3) + 1;
            if (iRound == ImageWallpaper.this.mPages && ImageWallpaper.this.mPagesComputed) {
                return;
            }
            ImageWallpaper.this.mPages = iRound;
            ImageWallpaper imageWallpaper = ImageWallpaper.this;
            imageWallpaper.mPagesComputed = true;
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda4(wallpaperLocalColorExtractor, imageWallpaper.mPages));
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
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(currentWhich, "onSurfaceCreated: which=", ", disp=");
                sbM.append(imageWallpaperCanvasHelper.mDisplayId);
                sbM.append(", colorDecor=");
                sbM.append(!TextUtils.isEmpty(imageWallpaperCanvasHelper.mColorDecorFilterData));
                sbM.append(", highlightAmount=");
                sbM.append(imageWallpaperCanvasHelper.mHighlightFilterAmount);
                Log.i(imageWallpaperCanvasHelper.TAG, sbM.toString());
                imageWallpaperCanvasHelper.useWallpaperBitmap(currentWhich, new Consumer() { // from class: com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean z;
                        ImageWallpaperCanvasHelper imageWallpaperCanvasHelper2 = imageWallpaperCanvasHelper;
                        int i = currentWhich;
                        Bitmap bitmapCreateFilteredBitmap = (Bitmap) obj;
                        imageWallpaperCanvasHelper2.getClass();
                        if (WallpaperUtils.isValidBitmap(bitmapCreateFilteredBitmap)) {
                            ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1 imageWallpaper$CanvasEngine$$ExternalSyntheticLambda1 = imageWallpaperCanvasHelper2.mBitmapUpdateConsumer;
                            if (imageWallpaper$CanvasEngine$$ExternalSyntheticLambda1 != null) {
                                imageWallpaper$CanvasEngine$$ExternalSyntheticLambda1.accept(bitmapCreateFilteredBitmap);
                            }
                        } else {
                            Log.w(imageWallpaperCanvasHelper2.TAG, "reload texture failed!");
                        }
                        if (WallpaperUtils.isValidBitmap(bitmapCreateFilteredBitmap)) {
                            if (TextUtils.isEmpty(imageWallpaperCanvasHelper2.mColorDecorFilterData)) {
                                int i2 = imageWallpaperCanvasHelper2.mHighlightFilterAmount;
                                if (i2 >= 0) {
                                    bitmapCreateFilteredBitmap = HighlightFilterHelper.createFilteredBitmap(bitmapCreateFilteredBitmap, i2);
                                }
                                z = false;
                            } else {
                                bitmapCreateFilteredBitmap = ColorDecorFilterHelper.createFilteredBitmap(imageWallpaperCanvasHelper2.mColorDecorFilterData, bitmapCreateFilteredBitmap);
                            }
                            z = true;
                        } else {
                            z = false;
                        }
                        if (WallpaperUtils.isValidBitmap(bitmapCreateFilteredBitmap)) {
                            boolean z2 = imageWallpaperCanvasHelper2.mDisplayId == 2 || imageWallpaperCanvasHelper2.mIsVirtualDisplay;
                            ImageSmartCropper imageSmartCropper = imageWallpaperCanvasHelper2.mImageSmartCropper;
                            if (imageSmartCropper != null && !z2) {
                                imageSmartCropper.updateSmartCropRectIfNeeded(bitmapCreateFilteredBitmap, i, imageWallpaperCanvasHelper2.mCurrentUserId);
                                Rect rect = new Rect(0, 0, bitmapCreateFilteredBitmap.getWidth(), bitmapCreateFilteredBitmap.getHeight());
                                Rect rect2 = imageSmartCropper.mCropResult;
                                if (rect2 == null) {
                                    imageWallpaperCanvasHelper2.mWallpaperManager.semSetSmartCropRect(1, rect, rect);
                                } else {
                                    imageWallpaperCanvasHelper2.mWallpaperManager.semSetSmartCropRect(1, rect, rect2);
                                }
                            }
                        }
                        if (!z || bitmapCreateFilteredBitmap == null) {
                            return;
                        }
                        bitmapCreateFilteredBitmap.recycle();
                    }
                });
                Trace.endSection();
            }
            this.mSurfaceCreated = true;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            Log.i(this.TAG, "onSurfaceDestroyed");
            synchronized (this.mSurfaceLock) {
                this.mSurfaceHolder = null;
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            DrawState drawState;
            int i;
            CanvasEngine canvasEngine;
            CanvasEngine canvasEngine2;
            int i2;
            if (!this.mIsEngineAlive) {
                Log.i(this.TAG, "onSurfaceRedrawNeeded: engine already destroyed");
                return;
            }
            int currentWhich = this.mHelper.getCurrentWhich();
            Rect rect = new Rect(this.mSurfaceHolder.getSurfaceFrame());
            String str = this.TAG;
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(currentWhich, "onSurfaceRedrawNeeded: curWhich=", ", isFixedRotationInProgress=");
            sbM.append(this.mIsFixedRotationInProgress);
            sbM.append(", mRotation=");
            sbM.append(this.mRotation);
            sbM.append(", surfaceFrame=");
            sbM.append(rect);
            Log.i(str, sbM.toString());
            if (!this.mHelper.hasIntelligentCropHints(currentWhich)) {
                updateWallpaperOffset(currentWhich, this.mRotation);
            }
            SurfaceHolder surfaceHolder2 = this.mSurfaceHolder;
            if (surfaceHolder2 == null) {
                drawState = null;
                i = currentWhich;
                canvasEngine = this;
            } else {
                Rect surfaceFrame = surfaceHolder2.getSurfaceFrame();
                determineHighlightFilterAmount();
                boolean z = this.mHelper.getDimFilterColor(currentWhich) != null;
                i = currentWhich;
                canvasEngine = this;
                drawState = new DrawState(canvasEngine, i, surfaceFrame.width(), surfaceFrame.height(), false, z);
            }
            DrawState drawState2 = drawState;
            synchronized (canvasEngine.mLock) {
                try {
                    Log.i(canvasEngine.TAG, "onSurfaceRedrawNeeded: displayId=" + canvasEngine.getDisplayId() + ", lastDrawn=(" + canvasEngine.mLastDrawnState + "), toDraw=(" + drawState2 + ")");
                    DrawState drawState3 = canvasEngine.mLastDrawnState;
                    if (drawState3 != null && drawState3.equals(drawState2)) {
                        Log.i(canvasEngine.TAG, "onSurfaceRedrawNeeded: not need redraw");
                        return;
                    }
                    ImageWallpaperCanvasHelper.DownScaledSourceBitmap downScaledSourceBitmap = (ImageWallpaperCanvasHelper.DownScaledSourceBitmap) canvasEngine.mHelper.mDownScaledSourceBitmapSet.get(Integer.valueOf(WhichChecker.getSourceWhich(i)));
                    if (downScaledSourceBitmap == null) {
                        canvasEngine.drawFrameSynchronized(i, rect);
                        return;
                    }
                    synchronized (canvasEngine.mLock) {
                        canvasEngine2 = canvasEngine;
                        i2 = i;
                        canvasEngine2.drawFrameOnCanvas(i2, SystemClock.elapsedRealtime(), rect, downScaledSourceBitmap.mBitmap, downScaledSourceBitmap.mScale, 2);
                    }
                    synchronized (canvasEngine2.mLock) {
                        canvasEngine2.mLastDrawnState = drawState2;
                    }
                    ImageWallpaper.this.mLongExecutor.execute(new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda4(canvasEngine2, i2, rect));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService.BaseEngine
        public final void onSwitchDisplayChanged(final boolean z) {
            Log.d(this.TAG, "onSwitchDisplayChanged");
            if (this.mIsEngineAlive) {
                semSetFixedOrientation(isFixedOrientationWallpaper(getDisplayId(), getCurrentUserId()), false);
                if (z) {
                    ImageWallpaper.this.mWorker.getThreadHandler().postDelayed(new Runnable() { // from class: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            ImageWallpaper.CanvasEngine canvasEngine = this.f$0;
                            boolean z2 = z;
                            int i = ImageWallpaper.CanvasEngine.$r8$clinit;
                            canvasEngine.updateOnSwitchDisplayChanged(z2);
                        }
                    }, 200L);
                } else {
                    updateOnSwitchDisplayChanged(z);
                }
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onVisibilityChanged(boolean z) {
            super.onVisibilityChanged(z);
            String str = this.TAG;
            StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m(" onVisibilityChanged: visible=", " , displayId=", z);
            sbM.append(getDisplayId());
            Log.i(str, sbM.toString());
        }

        public void recomputeColorExtractorMiniBitmap() {
            this.mHelper.useWallpaperBitmap(this.mHelper.getCurrentWhich(), new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1(this, 0));
        }

        public final void removeLocalColorsAreas(List list) {
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda0(wallpaperLocalColorExtractor, list, 0));
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
            if (this.mIsEngineAlive) {
                this.mHelper.mCurrentUserId = i;
            } else {
                Log.d(this.TAG, "setCurrentUserId: already destroyed");
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
                synchronized (this.mSurfaceLock) {
                    try {
                        SurfaceHolder surfaceHolder = this.mSurfaceHolder;
                        if (surfaceHolder != null) {
                            surfaceHolder.getSurface().hwuiDestroy();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                this.mWallpaperManager.forgetLoadedWallpaper();
                Trace.endSection();
            }
        }

        public final void updateMiniBitmapAndNotify(Bitmap bitmap) {
            if (bitmap == null) {
                return;
            }
            int iMin = Math.min(bitmap.getWidth(), bitmap.getHeight());
            float f = iMin > 128 ? 128.0f / iMin : 1.0f;
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
                    StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m(" onFolderStateChanged: isFolded=", ", WallMgr=", z);
                    sbM.append(ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mWallpaperManager.getLidState()));
                    sbM.append(", mLidState=");
                    sbM.append(ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mLidState));
                    String string = sbM.toString();
                    WallpaperLoggerImpl wallpaperLoggerImpl = (WallpaperLoggerImpl) imageWallpaperCanvasHelper.mLoggerWrapper;
                    String str = imageWallpaperCanvasHelper.TAG;
                    wallpaperLoggerImpl.log(str, string);
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
                Size sizeReportSurfaceSize = this.mHelper.reportSurfaceSize(currentWhich);
                Rect surfaceFrame = getSurfaceHolder().getSurfaceFrame();
                if (sizeReportSurfaceSize.getHeight() == surfaceFrame.height() && sizeReportSurfaceSize.getWidth() == surfaceFrame.width()) {
                    updateRendering(currentWhich);
                    return;
                }
                int iMax = Math.max(128, sizeReportSurfaceSize.getWidth());
                int iMax2 = Math.max(128, sizeReportSurfaceSize.getHeight());
                String str2 = this.TAG;
                StringBuilder sbM2 = MutableObjectList$$ExternalSyntheticOutline0.m(currentWhich, iMax, "updateOnSwitchDisplayChanged: change surface size. which=", ", width=", ", height=");
                sbM2.append(iMax2);
                Log.i(str2, sbM2.toString());
                getSurfaceHolder().setFixedSize(iMax, iMax2);
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
            Size sizeReportSurfaceSize = this.mHelper.reportSurfaceSize(i);
            int iMax = Math.max(128, sizeReportSurfaceSize.getWidth());
            int iMax2 = Math.max(128, sizeReportSurfaceSize.getHeight());
            String str = this.TAG;
            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(iMax, iMax2, " updateSurfaceSize: width = ", ", height = ", ", isVisible = ");
            sbM.append(isVisible());
            Log.i(str, sbM.toString());
            if (this.mHelper.hasIntelligentCropHints(i)) {
                surfaceHolder.setFixedSize(-1, -1);
            } else {
                surfaceHolder.setFixedSize(iMax, iMax2);
            }
            Trace.endSection();
        }

        public final boolean updateSurfaceSizeIfNeed(int i) {
            Size sizeReportSurfaceSize = this.mHelper.reportSurfaceSize(i);
            Rect surfaceFrame = getSurfaceHolder().getSurfaceFrame();
            if (sizeReportSurfaceSize.getHeight() == surfaceFrame.height() && sizeReportSurfaceSize.getWidth() == surfaceFrame.width()) {
                return false;
            }
            Log.i(this.TAG, "  updateSurfaceSizeIfNeed frame  " + sizeReportSurfaceSize + " surfaceFrame : " + surfaceFrame);
            updateSurfaceSize(i);
            finishRendering();
            return true;
        }

        public final void updateWallpaperOffset(int i, int i2) {
            int iMin;
            int iMax;
            int i3;
            IBinder windowTokenAsBinder = super.getWindowTokenAsBinder();
            if (windowTokenAsBinder != null) {
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                int i4 = imageWallpaperCanvasHelper.mDisplayId;
                String str = imageWallpaperCanvasHelper.TAG;
                if (i4 == 2 || (LsRune.WALLPAPER_VIRTUAL_DISPLAY && imageWallpaperCanvasHelper.mIsVirtualDisplay)) {
                    TooltipPopup$$ExternalSyntheticOutline0.m(imageWallpaperCanvasHelper.mDisplayId, str, new StringBuilder(" ignore updateWallpaperOffset "));
                    return;
                }
                WallpaperManager wallpaperManager = imageWallpaperCanvasHelper.mWallpaperManager;
                if (wallpaperManager != null) {
                    int i5 = imageWallpaperCanvasHelper.mSmartCropYOffset;
                    int iSemGetWallpaperType = wallpaperManager.semGetWallpaperType(i);
                    StringBuilder sb = new StringBuilder();
                    sb.append("updateWallpaperOffset " + imageWallpaperCanvasHelper.mDisplayId);
                    sb.append(" lastCropOffset " + i5 + " , wp Type " + iSemGetWallpaperType + " , rotation " + i2 + " , allowed " + imageWallpaperCanvasHelper.mIsSmartCropAllowed);
                    ImageSmartCropper imageSmartCropper = imageWallpaperCanvasHelper.mImageSmartCropper;
                    if (imageSmartCropper != null && imageWallpaperCanvasHelper.mIsSmartCropAllowed && imageSmartCropper.needToSmartCrop() && ((i2 == 1 || i2 == 3) && iSemGetWallpaperType == 0 && !imageWallpaperCanvasHelper.hasIntelligentCropHints(i))) {
                        if (((Rect) WallpaperUtils.sCachedSmartCroppedRect.get(i)) == null) {
                            sb.append(" Error Smart rect is Null " + imageWallpaperCanvasHelper.mSmartCropYOffset);
                            i3 = 0;
                        } else {
                            Display display = ((DisplayManager) imageWallpaperCanvasHelper.mContext.getSystemService("display")).getDisplay(imageWallpaperCanvasHelper.mDisplayId);
                            if (display != null) {
                                DisplayInfo displayInfo = new DisplayInfo();
                                display.getDisplayInfo(displayInfo);
                                iMax = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                iMin = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                            } else {
                                Log.e(str, " getDisplaySize use configuration to recognize the screen size");
                                Rect bounds = imageWallpaperCanvasHelper.mContext.getResources().getConfiguration().windowConfiguration.getBounds();
                                int iWidth = bounds.width();
                                int iHeight = bounds.height();
                                int iMax2 = Math.max(iWidth, iHeight);
                                iMin = Math.min(iWidth, iHeight);
                                iMax = iMax2;
                            }
                            Size size = new Size(iMax, iMin);
                            int width = size.getWidth();
                            int height = size.getHeight();
                            float f = height;
                            float fHeight = imageWallpaperCanvasHelper.mSurfaceSize.height() * Math.max(width / imageWallpaperCanvasHelper.mSurfaceSize.width(), f / imageWallpaperCanvasHelper.mSurfaceSize.height());
                            float fHeight2 = r0.top / imageWallpaperCanvasHelper.mDimensions.height();
                            float f2 = fHeight2 * fHeight;
                            float f3 = (f * 0.5f) + f2;
                            float f4 = imageWallpaperCanvasHelper.mYOffset;
                            int i6 = (int) (fHeight * f4);
                            sb.append(MutableVectorKt$$ExternalSyntheticOutline0.m(width, height, ", screenSize=(", ", ", ")"));
                            sb.append(", origTopPos " + fHeight2 + " , calcTopPos " + f2);
                            sb.append(", scaledHeight " + fHeight + " , " + i6 + " , " + f4 + " , smartCropCenterY " + f3);
                            i3 = (int) (((float) i6) - f3);
                        }
                        imageWallpaperCanvasHelper.mSmartCropYOffset = i3;
                    } else {
                        imageWallpaperCanvasHelper.mSmartCropYOffset = 0;
                    }
                    if (i5 == imageWallpaperCanvasHelper.mSmartCropYOffset) {
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

        public final void onDimAmountChanged(float f) {
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
            this.mSurfaceLock = new Object();
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
                        ClockEventController$$ExternalSyntheticOutline0.m(displayId, "  getCurrentDisplayRotation failed to get display. displayId=", canvasEngine.TAG);
                    }
                    int i3 = displayInfo.rotation;
                    long jElapsedRealtime = SystemClock.elapsedRealtime() - this.mFixedRotationStartTime;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder sb = new StringBuilder("onFixedRotationFinished mRotation=");
                    ViewPager$$ExternalSyntheticOutline0.m(sb, canvasEngine.mRotation, ", displayRotation=", i3, ", configRotation=");
                    sb.append(configuration.windowConfiguration.getRotation());
                    sb.append(", elapsed=");
                    sb.append(jElapsedRealtime);
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
                    StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i2, i3, "onFixedRotationStarted displayId=", ", newRotation=", ", mRotation=");
                    sbM.append(canvasEngine.mRotation);
                    sbM.append(", ");
                    sbM.append(ImageWallpaper.this.getResources().getConfiguration());
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, sbM.toString());
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
                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "onRotationChanged: newRotation=", ", mRotation=");
                    sbM.append(CanvasEngine.this.mRotation);
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, sbM.toString());
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
            this.mPluginWallpaperConsumer = new AnonymousClass8();
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
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda4(wallpaperLocalColorExtractor, ImageWallpaper.this.mPages));
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }
    }
}
