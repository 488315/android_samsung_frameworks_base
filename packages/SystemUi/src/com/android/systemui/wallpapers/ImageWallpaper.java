package com.android.systemui.wallpapers;

import android.app.WallpaperColors;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
import android.opengl.GLES20;
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
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.MathUtils;
import android.util.Size;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.IRotationWatcher;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.effect.ColorDecorFilterHelper;
import com.android.systemui.wallpaper.effect.HighlightFilterHelper;
import com.android.systemui.wallpaper.glwallpaper.EglHelper;
import com.android.systemui.wallpaper.glwallpaper.ImageDarkModeFilter;
import com.android.systemui.wallpaper.glwallpaper.ImageGLWallpaper;
import com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperGifRenderer;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperRenderer;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.android.systemui.wallpapers.WallpaperLocalColorExtractor;
import com.samsung.android.wallpaper.Rune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ImageWallpaper extends WallpaperService {
    public static final RectF LOCAL_COLOR_BOUNDS = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    public final CoverWallpaper mCoverWallpaper;
    public final DisplayController mDisplayController;
    public final WallpaperLogger mLogger;
    public final DelayableExecutor mLongExecutor;
    public final Handler mMainThreadHandler;
    public Bitmap mMiniBitmap;
    public PowerManager mPm;
    public final SettingsHelper mSettingsHelper;
    public int mSubWallpaperType;
    public final SystemWallpaperColors mSystemWallpaperColors;
    public HandlerThread mWorker;
    public final ArrayList mLocalColorsToAdd = new ArrayList();
    public final ArraySet mColorAreas = new ArraySet();
    public volatile int mPages = 1;
    public boolean mPagesComputed = false;
    public final HashMap mEngineList = new HashMap();
    public final HashMap mCanvasEngineList = new HashMap();
    public boolean mIsNightModeOn = false;
    public final C37143 mSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.wallpapers.ImageWallpaper.3
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (Settings.System.getUriFor("display_night_theme_wallpaper").equals(uri)) {
                Log.i("ImageWallpaper", " Apply Dark mode option changed");
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                for (Map.Entry entry : imageWallpaper.mEngineList.entrySet()) {
                    GLEngine gLEngine = (GLEngine) entry.getValue();
                    if (gLEngine != null) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onChangeApplyDark : notify the change to Engine. Engine displayId = ", ((Integer) entry.getKey()).intValue(), "ImageWallpaper");
                        HandlerThread handlerThread = ImageWallpaper.this.mWorker;
                        if (handlerThread != null) {
                            handlerThread.getThreadHandler().removeCallbacks(gLEngine.mDrawUpdateTask);
                            ImageWallpaper.this.mWorker.getThreadHandler().postDelayed(gLEngine.mDrawUpdateTask, 200L);
                        }
                    }
                }
                for (Map.Entry entry2 : imageWallpaper.mCanvasEngineList.entrySet()) {
                    CanvasEngine canvasEngine = (CanvasEngine) entry2.getValue();
                    if (canvasEngine != null) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onChangeApplyDark : notify the change to Engine. Engine displayId = ", ((Integer) entry2.getKey()).intValue(), "ImageWallpaper");
                        int currentWhich = canvasEngine.mHelper.getCurrentWhich();
                        Rect rect = new Rect(canvasEngine.mSurfaceHolder.getSurfaceFrame());
                        synchronized (canvasEngine.mLock) {
                            canvasEngine.drawFullQualityFrame(currentWhich, rect);
                            canvasEngine.finishRendering();
                        }
                    }
                }
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$1 */
    public final class RunnableC37121 implements Runnable {
        public final /* synthetic */ WakefulnessLifecycle.Observer val$observer;

        public RunnableC37121(ImageWallpaper imageWallpaper, WakefulnessLifecycle.Observer observer) {
            this.val$observer = observer;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ((WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class)).addObserver(this.val$observer);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$2 */
    public final class RunnableC37132 implements Runnable {
        public final /* synthetic */ WakefulnessLifecycle.Observer val$observer;

        public RunnableC37132(ImageWallpaper imageWallpaper, WakefulnessLifecycle.Observer observer) {
            this.val$observer = observer;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ((WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class)).removeObserver(this.val$observer);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class BaseEngine extends WallpaperService.Engine implements IPlayableWallpaper {
        public int mAodState;
        public boolean mIsGoingToSleep;
        public boolean mIsPauseByCommand;

        public BaseEngine() {
            super(ImageWallpaper.this);
            this.mIsGoingToSleep = false;
            this.mIsPauseByCommand = false;
        }

        public boolean isFixedOrientationWallpaper(int i, int i2) {
            int convertDisplayIdToMode = WhichChecker.convertDisplayIdToMode(i, ImageWallpaper.this.getApplicationContext());
            if (convertDisplayIdToMode < 0) {
                NestedScrollView$$ExternalSyntheticOutline0.m34m("isFixedOrientationWallpaper: incorrect mode. displayId = ", i, "ImageWallpaper");
                return false;
            }
            int i3 = convertDisplayIdToMode | 1;
            Bundle wallpaperExtras = ((WallpaperService) ImageWallpaper.this).mWallpaperManager.getWallpaperExtras(i3, i2);
            if (wallpaperExtras == null) {
                return false;
            }
            boolean z = wallpaperExtras.getBoolean("isFixedOrientation");
            NotificationListener$$ExternalSyntheticOutline0.m123m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("isFixedOrientationWallpaper: which=", i3, ", user=", i2, ", fixedOrientation="), z, "ImageWallpaper");
            return z;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public Bundle onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
            Log.d("ImageWallpaper", "onCommand : " + str);
            if (TextUtils.equals(str, "android.wallpaper.wakingup")) {
                this.mIsGoingToSleep = false;
                this.mAodState = 0;
            } else if (TextUtils.equals(str, "android.wallpaper.goingtosleep")) {
                this.mIsGoingToSleep = true;
                pause(100);
            } else if (TextUtils.equals(str, "samsung.android.wallpaper.pause")) {
                this.mIsPauseByCommand = true;
                if (!this.mIsGoingToSleep) {
                    pause(0);
                }
            } else if (TextUtils.equals(str, "samsung.android.wallpaper.resume")) {
                this.mIsPauseByCommand = false;
                if (!this.mIsGoingToSleep && ImageWallpaper.this.mPm.isInteractive()) {
                    resume();
                }
            } else if (TextUtils.equals(str, "android.wallpaper.aodstate")) {
                StringBuilder sb = new StringBuilder("onCommand: Aod visibility state changed from [");
                int i4 = this.mAodState;
                String str2 = "UNIDENTIFIED";
                sb.append(i4 != -1 ? i4 != 0 ? i4 != 1 ? i4 != 2 ? "UNIDENTIFIED" : "VISIBLE_WITH_WALLPAPER" : "VISIBLE" : "INVISIBLE" : "NOT_INITIALIZED");
                sb.append("] to [");
                if (i == -1) {
                    str2 = "NOT_INITIALIZED";
                } else if (i == 0) {
                    str2 = "INVISIBLE";
                } else if (i == 1) {
                    str2 = "VISIBLE";
                } else if (i == 2) {
                    str2 = "VISIBLE_WITH_WALLPAPER";
                }
                ExifInterface$$ExternalSyntheticOutline0.m36m(sb, str2, "]", "ImageWallpaper");
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                if (imageWallpaper.mCoverWallpaper != null && ((WallpaperService) imageWallpaper).mWallpaperManager.semGetWallpaperType(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getCoverWhich()) == 3 && ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).isCoverWallpaperRequired()) {
                    ImageWallpaper.this.getApplicationContext();
                    boolean z2 = WallpaperUtils.mIsEmergencyMode;
                    Log.d("WallpaperUtils", "isSeamlessAodOnLargeCover: false");
                }
                if (i == 2) {
                    resume();
                    pause(100);
                }
                this.mAodState = i;
            }
            return super.onCommand(str, i, i2, i3, bundle, z);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GifGLEngine extends BaseEngine {
        static final int MIN_SURFACE_HEIGHT = 64;
        static final int MIN_SURFACE_WIDTH = 64;
        public final C37262 mGifWakefulNessObserver;
        public final C37251 mPluginGifWallpaperConsumer;
        public final ImageWallpaper$GifGLEngine$$ExternalSyntheticLambda0 mPluginUpdateTask;
        public ImageWallpaperGifRenderer mRenderer;
        public boolean mVirtualDisplayMode;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$GifGLEngine$1 */
        public final class C37251 implements Consumer {
            public C37251() {
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Boolean bool = (Boolean) obj;
                GifGLEngine gifGLEngine = GifGLEngine.this;
                if (gifGLEngine.mRenderer == null) {
                    Log.w("ImageWallpaper[GifGLEngine]", " mPluginGifWallpaperConsumer, skip, renderer is null");
                } else {
                    ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(5, this, bool));
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$GifGLEngine$2 */
        public final class C37262 implements WakefulnessLifecycle.Observer {
            public C37262() {
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GifGLEngine$2$$ExternalSyntheticLambda0(this, 1));
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GifGLEngine$2$$ExternalSyntheticLambda0(this, 0));
            }
        }

        public GifGLEngine() {
            super();
            this.mPluginUpdateTask = new ImageWallpaper$GifGLEngine$$ExternalSyntheticLambda0(this, 3);
            this.mVirtualDisplayMode = false;
            this.mPluginGifWallpaperConsumer = new C37251();
            this.mGifWakefulNessObserver = new C37262();
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("Engine=");
            printWriter.println(this);
            printWriter.print(str);
            printWriter.print("valid surface=");
            printWriter.println((getSurfaceHolder() == null || getSurfaceHolder().getSurface() == null) ? "null" : Boolean.valueOf(getSurfaceHolder().getSurface().isValid()));
            printWriter.print(str);
            printWriter.print("surface frame=");
            printWriter.println(getSurfaceHolder() != null ? getSurfaceHolder().getSurfaceFrame() : "null");
        }

        @Override // com.android.systemui.wallpapers.ImageWallpaper.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final Bundle onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
            Log.i("ImageWallpaper[GifGLEngine]", "onCommand : " + str);
            if (TextUtils.equals(str, "samsung.android.wallpaper.pause")) {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GifGLEngine$$ExternalSyntheticLambda0(this, 0));
            } else if (TextUtils.equals(str, "samsung.android.wallpaper.resume")) {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GifGLEngine$$ExternalSyntheticLambda0(this, 1));
            }
            return super.onCommand(str, i, i2, i3, bundle, z);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onCreate(SurfaceHolder surfaceHolder) {
            semSetFixedOrientation(isFixedOrientationWallpaper(getDisplayId(), getCurrentUserId()), false);
            setFixedSizeAllowed(true);
            setOffsetNotificationsEnabled(false);
            ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log("ImageWallpaper[GifGLEngine]", "Gif Engine onCreate  " + ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType() + " , displayId" + getDisplayId());
            this.mRenderer = new ImageWallpaperGifRenderer(ImageWallpaper.this.getApplicationContext(), ImageWallpaper.this.mWorker);
            setShowForAllUsers(true);
            ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).setWallpaperUpdateConsumer(this.mPluginGifWallpaperConsumer);
            boolean z = LsRune.WALLPAPER_VIRTUAL_DISPLAY;
            if (z) {
                WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext());
                this.mVirtualDisplayMode = WallpaperManager.isVirtualWallpaperDisplay(ImageWallpaper.this.getApplicationContext(), getDisplayId());
            }
            DisplayManager displayManager = (DisplayManager) ImageWallpaper.this.getSystemService("display");
            DisplayInfo displayInfo = new DisplayInfo();
            boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
            if (z2 && LsRune.WALLPAPER_SUB_WATCHFACE) {
                displayManager.getDisplay(1).getDisplayInfo(displayInfo);
            } else if (z) {
                Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
                if (displays.length > 0) {
                    displays[0].getDisplayInfo(displayInfo);
                }
            }
            int i = displayInfo.logicalHeight;
            int i2 = displayInfo.logicalWidth;
            Log.i("ImageWallpaper[GifGLEngine]", " device height : " + i + " , width " + i2);
            if (z2) {
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mMainThreadHandler.post(new RunnableC37121(imageWallpaper, this.mGifWakefulNessObserver));
            }
            ImageWallpaperGifRenderer imageWallpaperGifRenderer = this.mRenderer;
            Rect rect = new Rect(0, 0, i2, i);
            imageWallpaperGifRenderer.getClass();
            Log.i("ImageWallpaperGifRenderer", "setBoundRect : " + rect);
            imageWallpaperGifRenderer.mBoundRect = rect;
            getSurfaceHolder().setFixedSize(i2, i);
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GifGLEngine$$ExternalSyntheticLambda0(this, 4));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onDestroy() {
            super.onDestroy();
            Log.i("ImageWallpaper[GifGLEngine]", "GIF onDestroy ");
            if (LsRune.WALLPAPER_PLAY_GIF && (getDisplayId() == 1 || this.mVirtualDisplayMode)) {
                HandlerThread handlerThread = ImageWallpaper.this.mWorker;
                if (handlerThread != null) {
                    handlerThread.getThreadHandler().removeCallbacks(this.mPluginUpdateTask);
                }
                ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).onHomeWallpaperDestroyed();
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mMainThreadHandler.post(new RunnableC37132(imageWallpaper, this.mGifWakefulNessObserver));
            }
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GifGLEngine$$ExternalSyntheticLambda0(this, 2));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            super.onSurfaceCreated(surfaceHolder);
            ImageWallpaperGifRenderer imageWallpaperGifRenderer = this.mRenderer;
            imageWallpaperGifRenderer.getClass();
            Log.i("ImageWallpaperGifRenderer", " onSurfaceCreated ");
            imageWallpaperGifRenderer.mSurfaceHolder = surfaceHolder;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            super.onSurfaceDestroyed(surfaceHolder);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            super.onSurfaceRedrawNeeded(surfaceHolder);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onVisibilityChanged(boolean z) {
            super.onVisibilityChanged(z);
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda6(this, z, 2));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VideoGLEngine extends BaseEngine {
        static final int MIN_SURFACE_HEIGHT = 64;
        static final int MIN_SURFACE_WIDTH = 64;
        public final C37282 mMediaWakefulNessObserver;
        public final ImageWallpaper$VideoGLEngine$$ExternalSyntheticLambda0 mPluginUpdateTask;
        public final C37271 mPluginVideoWallpaperConsumer;
        public ImageWallpaperVideoRenderer mRenderer;
        public boolean mVirtualDisplayMode;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$VideoGLEngine$1 */
        public final class C37271 implements Consumer {
            public C37271() {
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(8, this, (Boolean) obj));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$VideoGLEngine$2 */
        public final class C37282 implements WakefulnessLifecycle.Observer {
            public C37282() {
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$1$$ExternalSyntheticLambda0(this, 4));
            }
        }

        public VideoGLEngine() {
            super();
            this.mPluginUpdateTask = new ImageWallpaper$VideoGLEngine$$ExternalSyntheticLambda0(this, 1);
            this.mVirtualDisplayMode = false;
            this.mPluginVideoWallpaperConsumer = new C37271();
            this.mMediaWakefulNessObserver = new C37282();
        }

        public final Rect calculateCropHint(Rect rect) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext());
            DisplayManager displayManager = (DisplayManager) ImageWallpaper.this.getSystemService("display");
            DisplayInfo displayInfo = new DisplayInfo();
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && LsRune.WALLPAPER_SUB_WATCHFACE) {
                displayManager.getDisplay(1).getDisplayInfo(displayInfo);
            } else if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
                if (displays.length > 0) {
                    displays[0].getDisplayInfo(displayInfo);
                }
            }
            int i = displayInfo.logicalHeight;
            int i2 = displayInfo.logicalWidth;
            Rect bounds = ((WindowManager) getDisplayContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
            Rect rect2 = new Rect(0, 0, bounds.width(), bounds.height());
            Log.i("ImageWallpaper[VideoGLEngine]", "calculateCropHint : " + rect + " , " + rect2);
            if (rect != null && rect.width() != 0 && rect.height() != 0) {
                Bundle wallpaperExtras = wallpaperManager.getWallpaperExtras(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getCoverWhich(), ImageWallpaper.this.getApplicationContext().getUserId());
                if (wallpaperExtras == null) {
                    return rect;
                }
                int i3 = wallpaperExtras.getInt("coverScreenWidth");
                int i4 = wallpaperExtras.getInt("coverScreenHeight");
                Log.i("ImageWallpaper[VideoGLEngine]", "calculateCropHint : " + i3 + " , " + i4);
                if (i3 <= 0 || i4 <= 0) {
                    return rect;
                }
                float f = i2 / i3;
                float f2 = i / i4;
                rect2.left = Math.round(rect.left * f);
                rect2.right = Math.round(rect.right * f);
                rect2.top = Math.round(rect.top * f2);
                rect2.bottom = Math.round(rect.bottom * f2);
                StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("Scale to video : ", f, " , ", f2, " , ");
                m88m.append(rect2);
                Log.i("ImageWallpaper[VideoGLEngine]", m88m.toString());
            }
            return rect2;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("Engine=");
            printWriter.println(this);
            printWriter.print(str);
            printWriter.print("valid surface=");
            printWriter.println((getSurfaceHolder() == null || getSurfaceHolder().getSurface() == null) ? "null" : Boolean.valueOf(getSurfaceHolder().getSurface().isValid()));
            printWriter.print(str);
            printWriter.print("surface frame=");
            printWriter.println(getSurfaceHolder() != null ? getSurfaceHolder().getSurfaceFrame() : "null");
        }

        @Override // com.android.systemui.wallpapers.ImageWallpaper.BaseEngine, android.service.wallpaper.WallpaperService.Engine
        public final Bundle onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
            return super.onCommand(str, i, i2, i3, bundle, z);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onCreate(SurfaceHolder surfaceHolder) {
            semSetFixedOrientation(isFixedOrientationWallpaper(getDisplayId(), getCurrentUserId()), false);
            setFixedSizeAllowed(true);
            setOffsetNotificationsEnabled(false);
            ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log("ImageWallpaper[VideoGLEngine]", "Video Engine onCreate  " + ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType() + " , displayId" + getDisplayId());
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext());
            DisplayManager displayManager = (DisplayManager) ImageWallpaper.this.getSystemService("display");
            DisplayInfo displayInfo = new DisplayInfo();
            boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
            if (z && LsRune.WALLPAPER_SUB_WATCHFACE) {
                displayManager.getDisplay(1).getDisplayInfo(displayInfo);
            } else if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                this.mVirtualDisplayMode = WallpaperManager.isVirtualWallpaperDisplay(ImageWallpaper.this.getApplicationContext(), getDisplayId());
                Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
                if (displays.length > 0) {
                    displays[0].getDisplayInfo(displayInfo);
                }
            }
            int i = displayInfo.logicalHeight;
            int i2 = displayInfo.logicalWidth;
            Log.i("ImageWallpaper[VideoGLEngine]", " device width : " + i2 + " , height " + i);
            this.mRenderer = new ImageWallpaperVideoRenderer(ImageWallpaper.this.getApplicationContext(), ImageWallpaper.this.mWorker, calculateCropHint(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).isCoverWallpaperRequired() ? ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperRect() : wallpaperManager.semGetWallpaperCropHint(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getCoverWhich())));
            setShowForAllUsers(true);
            ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).setWallpaperUpdateConsumer(this.mPluginVideoWallpaperConsumer);
            if (z) {
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mMainThreadHandler.post(new RunnableC37121(imageWallpaper, this.mMediaWakefulNessObserver));
            }
            getSurfaceHolder().setFixedSize(i2, i);
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(6, this, wallpaperManager));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onDestroy() {
            Log.i("ImageWallpaper[VideoGLEngine]", "onDestroy");
            super.onDestroy();
            if (LsRune.WALLPAPER_PLAY_GIF && (getDisplayId() == 1 || this.mVirtualDisplayMode)) {
                HandlerThread handlerThread = ImageWallpaper.this.mWorker;
                if (handlerThread != null) {
                    handlerThread.getThreadHandler().removeCallbacks(this.mPluginUpdateTask);
                }
                ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).onHomeWallpaperDestroyed();
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mMainThreadHandler.post(new RunnableC37132(imageWallpaper, this.mMediaWakefulNessObserver));
            }
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$VideoGLEngine$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            super.onSurfaceChanged(surfaceHolder, i, i2, i3);
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda1(this, i2, i3, 1));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            super.onSurfaceCreated(surfaceHolder);
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(7, this, surfaceHolder));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            super.onSurfaceDestroyed(surfaceHolder);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            super.onSurfaceRedrawNeeded(surfaceHolder);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onVisibilityChanged(boolean z) {
            super.onVisibilityChanged(z);
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda6(this, z, 3));
        }

        @Override // com.android.systemui.wallpapers.IPlayableWallpaper
        public final void pause(int i) {
            ImageWallpaper.this.mWorker.getThreadHandler().postDelayed(new ImageWallpaper$VideoGLEngine$$ExternalSyntheticLambda0(this, 6), i);
        }

        @Override // com.android.systemui.wallpapers.IPlayableWallpaper
        public final void resume() {
            ImageWallpaper.this.mWorker.getThreadHandler().postDelayed(new ImageWallpaper$VideoGLEngine$$ExternalSyntheticLambda0(this, 5), 0);
        }
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.systemui.wallpapers.ImageWallpaper$3] */
    public ImageWallpaper(DelayableExecutor delayableExecutor, UserTracker userTracker, WallpaperLogger wallpaperLogger, SystemWallpaperColors systemWallpaperColors, KeyguardWallpaper keyguardWallpaper, SettingsHelper settingsHelper, CoverWallpaper coverWallpaper, Optional<DisplayController> optional) {
        this.mDisplayController = null;
        this.mLongExecutor = delayableExecutor;
        this.mLogger = wallpaperLogger;
        SystemWallpaperColors systemWallpaperColors2 = ((KeyguardWallpaperController) keyguardWallpaper).mSystemWallpaperColors;
        this.mSystemWallpaperColors = systemWallpaperColors2;
        if (systemWallpaperColors2 == null) {
            this.mSystemWallpaperColors = systemWallpaperColors;
        }
        this.mSettingsHelper = settingsHelper;
        this.mCoverWallpaper = coverWallpaper;
        if (optional.isPresent()) {
            this.mDisplayController = optional.get();
        }
        this.mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int displayId = super.getDisplayId();
        GLEngine gLEngine = (GLEngine) this.mEngineList.get(Integer.valueOf(displayId));
        if (gLEngine != null) {
            gLEngine.onConfigurationChanged(configuration);
        }
        CanvasEngine canvasEngine = (CanvasEngine) this.mCanvasEngineList.get(Integer.valueOf(displayId));
        if (canvasEngine != null) {
            canvasEngine.onConfigurationChanged(configuration);
        }
        boolean z = (configuration.uiMode & 32) != 0;
        if (this.mIsNightModeOn != z) {
            this.mIsNightModeOn = z;
            GLEngine gLEngine2 = (GLEngine) this.mEngineList.get(2);
            if (gLEngine2 != null) {
                gLEngine2.onConfigurationChanged(configuration);
            }
            CanvasEngine canvasEngine2 = (CanvasEngine) this.mCanvasEngineList.get(2);
            if (canvasEngine2 != null) {
                canvasEngine2.onConfigurationChanged(configuration);
            }
        }
    }

    @Override // android.service.wallpaper.WallpaperService, android.app.Service
    public final void onCreate() {
        super.onCreate();
        Log.i("ImageWallpaper", "Main onCreate");
        HandlerThread handlerThread = new HandlerThread("ImageWallpaper");
        this.mWorker = handlerThread;
        handlerThread.start();
        this.mPm = (PowerManager) getApplicationContext().getSystemService("power");
        this.mSettingsHelper.registerCallback(this.mSettingsCallback, Settings.System.getUriFor("display_night_theme_wallpaper"));
    }

    @Override // android.service.wallpaper.WallpaperService
    public final WallpaperService.Engine onCreateEngine() {
        return new CanvasEngine();
    }

    public final WallpaperService.Engine onCreateSubEngine(int i) {
        CoverWallpaper coverWallpaper;
        boolean z;
        if (LsRune.WALLPAPER_PLAY_GIF && (coverWallpaper = this.mCoverWallpaper) != null) {
            Handler threadHandler = this.mWorker.getThreadHandler();
            final CoverWallpaperController coverWallpaperController = (CoverWallpaperController) coverWallpaper;
            final int coverWhich = coverWallpaperController.getCoverWhich();
            if (coverWallpaperController.mWallpaperManager.semGetWallpaperType(coverWhich) == 3 && coverWallpaperController.mWallpaperManager.getWallpaperId(coverWhich) != coverWallpaperController.mWallpaperId) {
                coverWallpaperController.mWallpaperId = coverWallpaperController.mWallpaperManager.getWallpaperId(coverWhich);
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("onHomeWallpaperReady: startMultiPack, mWallpaperId = "), coverWallpaperController.mWallpaperId, "CoverWallpaperController");
                if (threadHandler != null) {
                    threadHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.CoverWallpaperController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CoverWallpaperController.this.startMultiPack(coverWhich);
                        }
                    });
                } else {
                    coverWallpaperController.startMultiPack(coverWhich);
                }
            } else {
                Log.d("CoverWallpaperController", "onHomeWallpaperReady: Don't start multipack.");
            }
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            int i2 = (LsRune.COVER_VIRTUAL_DISPLAY && WallpaperManager.isVirtualWallpaperDisplay(getApplicationContext(), i)) ? 33 : 17;
            this.mSubWallpaperType = ((CoverWallpaperController) this.mCoverWallpaper).getWallpaperType();
            int semGetWallpaperType = wallpaperManager.semGetWallpaperType(i2);
            StringBuilder sb = new StringBuilder(" onCreateSubEngine: mSubWallpaperType = ");
            sb.append(this.mSubWallpaperType);
            sb.append(", mCoverWallpaper.getFirstWallpaperType() = ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, ((CoverWallpaperController) this.mCoverWallpaper).mFirstWallpaperType, ", wallpaper type = ", semGetWallpaperType, ", displayId = ");
            TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, i, "ImageWallpaper");
            if (semGetWallpaperType == 3) {
                int i3 = this.mSubWallpaperType;
                z = (i3 == -2 && ((CoverWallpaperController) this.mCoverWallpaper).mFirstWallpaperType == 22) || i3 == 22;
                if ((i3 != -2 || ((CoverWallpaperController) this.mCoverWallpaper).mFirstWallpaperType != 23) && i3 != 23) {
                    r6 = false;
                }
            } else {
                boolean z2 = semGetWallpaperType == 5;
                r6 = semGetWallpaperType == 8;
                z = z2;
            }
            Log.i("ImageWallpaper", " onCreateSubEngine: isGif = " + z + ", isVideo = " + r6);
            if (z) {
                return new GifGLEngine();
            }
            if (r6) {
                return new VideoGLEngine();
            }
        }
        return new CanvasEngine();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GLEngine extends BaseEngine implements DisplayManager.DisplayListener {
        static final int MIN_SURFACE_HEIGHT = 128;
        static final int MIN_SURFACE_WIDTH = 128;
        public int mDisplayHeight;
        public boolean mDisplaySizeValid;
        public int mDisplayWidth;
        public final ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 mDrawUpdateTask;
        public EglHelper mEglHelper;
        public final ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 mFinishRenderingTask;
        public int mImgHeight;
        public int mImgWidth;
        public float mLastWallpaperYOffset;
        public final C37244 mPluginHomeWallpaperConsumer;
        public ImageWallpaperRenderer mRenderer;
        public int mRotation;
        public final C37233 mRotationWatcher;
        public boolean mSurfaceCreated;
        public final ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 mUpdatePluginTask;
        public boolean mVirtualDisplayMode;
        public final C37211 mWakefulnessObserver;
        public final C37222 onDisplaysChangedListener;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$GLEngine$1 */
        public final class C37211 implements WakefulnessLifecycle.Observer {
            public C37211() {
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$1$$ExternalSyntheticLambda0(this, 0));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$GLEngine$3 */
        public final class C37233 extends IRotationWatcher.Stub {
            public C37233() {
            }

            public final void onRotationChanged(int i) {
                WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("onRotationChanged: newRotation=", i, ", mRotation=");
                m1m.append(GLEngine.this.mRotation);
                ((WallpaperLoggerImpl) wallpaperLogger).log("ImageWallpaper[GLEngine]", m1m.toString());
                GLEngine gLEngine = GLEngine.this;
                if (gLEngine.mRotation != i) {
                    ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log("ImageWallpaper[GLEngine]", "onRotationChanged rotation is changed ");
                    GLEngine gLEngine2 = GLEngine.this;
                    gLEngine2.mRotation = i;
                    ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$1$$ExternalSyntheticLambda0(this, 3));
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$GLEngine$4 */
        public final class C37244 implements Consumer {
            public C37244() {
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Boolean bool = (Boolean) obj;
                GLEngine gLEngine = GLEngine.this;
                if (gLEngine.mRenderer == null) {
                    Log.w("ImageWallpaper[GLEngine]", " mPluginHomeWallpaperConsumer, skip, renderer is null");
                } else {
                    ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(4, this, bool));
                }
            }
        }

        public static void $r8$lambda$InHMzcxP9yIB3NrHGHisYRbdVpE(GLEngine gLEngine) {
            Log.i("ImageWallpaper[GLEngine]", "Engine onDestroy in work thread " + super.getDisplayId());
            WallpaperUtils.clearCachedWallpaper(1);
            WallpaperUtils.clearCachedWallpaper(5);
            WallpaperUtils.clearCachedWallpaper(17);
            WallpaperUtils.clearCachedWallpaper(9);
            gLEngine.mRenderer.getClass();
            gLEngine.mRenderer = null;
            gLEngine.mEglHelper.finish();
            gLEngine.mEglHelper = null;
        }

        public static void $r8$lambda$MTjZ9OcqEdrPyZzafR7xU6HwKHk(GLEngine gLEngine, boolean z) {
            gLEngine.getClass();
            StringBuilder sb = new StringBuilder(" onVisibilityChanged ");
            sb.append(z);
            sb.append(" , ");
            TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, super.getDisplayId(), "ImageWallpaper[GLEngine]");
            if (z) {
                gLEngine.updateWallpaperOffset(gLEngine.mRotation);
            }
        }

        /* renamed from: $r8$lambda$gvSO08LS-V1OEOlvWqQf1uysZ84, reason: not valid java name */
        public static void m2736$r8$lambda$gvSO08LSV1OEOlvWqQf1uysZ84(GLEngine gLEngine) {
            gLEngine.getClass();
            if (!LsRune.WALLPAPER_PLAY_GIF || ImageWallpaper.this.mCoverWallpaper == null) {
                return;
            }
            if (super.getDisplayId() == 1 || gLEngine.mVirtualDisplayMode) {
                ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).setWallpaperUpdateConsumer(gLEngine.mPluginHomeWallpaperConsumer);
            }
        }

        public GLEngine() {
            super();
            this.mFinishRenderingTask = new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 4);
            this.mDisplaySizeValid = false;
            this.mDisplayWidth = 1;
            this.mDisplayHeight = 1;
            this.mImgWidth = 1;
            this.mImgHeight = 1;
            this.mLastWallpaperYOffset = 0.5f;
            this.mUpdatePluginTask = new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 5);
            this.mVirtualDisplayMode = false;
            this.mDrawUpdateTask = new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 6);
            this.mSurfaceCreated = false;
            this.mWakefulnessObserver = new C37211();
            this.onDisplaysChangedListener = new C37222();
            this.mRotationWatcher = new C37233();
            this.mPluginHomeWallpaperConsumer = new C37244();
        }

        public final void addLocalColorsAreas(List list) {
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda2(this, list, 1));
        }

        public final void cancelFinishRenderingTask() {
            HandlerThread handlerThread = ImageWallpaper.this.mWorker;
            if (handlerThread == null) {
                return;
            }
            handlerThread.getThreadHandler().removeCallbacks(this.mFinishRenderingTask);
        }

        public final void computeAndNotifyLocalColors(List list, Bitmap bitmap) {
            int i;
            int i2;
            Log.i("ImageWallpaper[GLEngine]", " computeAndNotifyLocalColors ");
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
                Log.e("ImageWallpaper[GLEngine]", e.getMessage(), e);
            }
        }

        public final int determineHighlightFilterAmount() {
            int i;
            if (!(!TextUtils.isEmpty(this.mRenderer.mColorDecorFilterData))) {
                Boolean canApplyFilterOnHome = HighlightFilterHelper.canApplyFilterOnHome(WhichChecker.convertDisplayIdToMode(super.getDisplayId(), ImageWallpaper.this.getApplicationContext()));
                if (canApplyFilterOnHome == null) {
                    canApplyFilterOnHome = Boolean.FALSE;
                }
                if (canApplyFilterOnHome.booleanValue()) {
                    i = HighlightFilterHelper.getFilterAmount(ImageWallpaper.this.mSettingsHelper);
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m(" determineHighlightFilterAmount : ", i, "ImageWallpaper[GLEngine]");
                    return i;
                }
            }
            i = -1;
            ListPopupWindow$$ExternalSyntheticOutline0.m10m(" determineHighlightFilterAmount : ", i, "ImageWallpaper[GLEngine]");
            return i;
        }

        public final void drawFrame() {
            boolean z;
            Trace.beginSection("ImageWallpaper#drawFrame");
            Trace.beginSection("ImageWallpaper#preRender");
            boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
            if (z2) {
                Log.i("ImageWallpaper[GLEngine]", " preRenderInternal");
            }
            Rect surfaceFrame = getSurfaceHolder().getSurfaceFrame();
            cancelFinishRenderingTask();
            this.mRenderer.mHighlightFilterAmount = determineHighlightFilterAmount();
            EglHelper eglHelper = this.mEglHelper;
            if (eglHelper != null) {
                eglHelper.destroyEglSurface();
                this.mEglHelper.destroyEglContext();
                if (this.mEglHelper.createEglContext()) {
                    z = true;
                } else {
                    Log.w("ImageWallpaper[GLEngine]", "recreate egl context failed!");
                    z = false;
                }
                if (this.mEglHelper.hasEglContext() && !this.mEglHelper.hasEglSurface() && !this.mEglHelper.createEglSurface(getSurfaceHolder(), this.mRenderer.mTexture.mWcgContent)) {
                    Log.w("ImageWallpaper[GLEngine]", "recreate egl surface failed!");
                }
                if (this.mEglHelper.hasEglContext() && this.mEglHelper.hasEglSurface() && z) {
                    this.mRenderer.onSurfaceCreated();
                    ImageWallpaperRenderer imageWallpaperRenderer = this.mRenderer;
                    int width = surfaceFrame.width();
                    int height = surfaceFrame.height();
                    imageWallpaperRenderer.getClass();
                    GLES20.glViewport(0, 0, width, height);
                }
            }
            Trace.endSection();
            Trace.beginSection("ImageWallpaper#requestRender");
            if (z2) {
                Log.i("ImageWallpaper[GLEngine]", " requestRenderInternal");
            }
            Rect surfaceFrame2 = getSurfaceHolder().getSurfaceFrame();
            if (this.mEglHelper.hasEglContext() && this.mEglHelper.hasEglSurface() && surfaceFrame2.width() > 0 && surfaceFrame2.height() > 0) {
                updateWallpaperOffset(this.mRotation);
                ImageWallpaperRenderer imageWallpaperRenderer2 = this.mRenderer;
                imageWallpaperRenderer2.getClass();
                GLES20.glClear(16384);
                float[] wallpaperFilterColor = ImageDarkModeFilter.getWallpaperFilterColor(imageWallpaperRenderer2.mContext, imageWallpaperRenderer2.mSystemWallpaperColors.getColor(imageWallpaperRenderer2.getCurrentWhich()));
                ImageGLWallpaper imageGLWallpaper = imageWallpaperRenderer2.mWallpaper;
                Rect rect = imageWallpaperRenderer2.mSurfaceSize;
                WallpaperLogger wallpaperLogger = imageWallpaperRenderer2.mLoggerWrapper;
                if (wallpaperFilterColor == null || (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && imageWallpaperRenderer2.mDisplayId == 1)) {
                    GLES20.glUniform1f(imageGLWallpaper.getHandle("uNightFilter"), 0.0f);
                    ((WallpaperLoggerImpl) wallpaperLogger).log("ImageWallpaperRenderer", " onDrawFrame surfaceSize : " + rect);
                } else {
                    GLES20.glUniform1f(imageGLWallpaper.getHandle("uNightFilter"), wallpaperFilterColor[3]);
                    GLES20.glUniform3f(imageGLWallpaper.getHandle("uFilterColor"), wallpaperFilterColor[0], wallpaperFilterColor[1], wallpaperFilterColor[2]);
                    ((WallpaperLoggerImpl) wallpaperLogger).log("ImageWallpaperRenderer", " onDrawFrame dark opacity " + wallpaperFilterColor[3] + " ,surfaceSize : " + rect);
                }
                GLES20.glViewport(0, 0, rect.width(), rect.height());
                imageGLWallpaper.getClass();
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, imageGLWallpaper.mTextureId);
                GLES20.glUniform1i(imageGLWallpaper.mUniTexture, 0);
                GLES20.glDrawArrays(4, 0, 6);
                if (!this.mEglHelper.swapBuffer()) {
                    Log.e("ImageWallpaper[GLEngine]", "drawFrame failed!");
                }
            } else {
                Log.e("ImageWallpaper[GLEngine]", "requestRender: not ready, has context=" + this.mEglHelper.hasEglContext() + ", has surface=" + this.mEglHelper.hasEglSurface() + ", frame=" + surfaceFrame2);
            }
            Trace.endSection();
            Trace.beginSection("ImageWallpaper#postRender");
            if (ImageWallpaper.this.mWorker != null) {
                cancelFinishRenderingTask();
                ImageWallpaper.this.mWorker.getThreadHandler().postDelayed(this.mFinishRenderingTask, 1000L);
            }
            reportEngineShown(false);
            Trace.endSection();
            Trace.endSection();
            if (!z2 || LsRune.SUBSCREEN_WATCHFACE) {
                return;
            }
            setSurfaceAlpha(1.0f);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("Engine=");
            printWriter.println(this);
            printWriter.print(str);
            printWriter.print("valid surface=");
            printWriter.println((getSurfaceHolder() == null || getSurfaceHolder().getSurface() == null) ? "null" : Boolean.valueOf(getSurfaceHolder().getSurface().isValid()));
            printWriter.print(str);
            printWriter.print("surface frame=");
            printWriter.println(getSurfaceHolder() != null ? getSurfaceHolder().getSurfaceFrame() : "null");
            EglHelper eglHelper = this.mEglHelper;
            StringBuilder sb = new StringBuilder();
            int[] iArr = eglHelper.mEglVersion;
            sb.append(iArr[0]);
            sb.append(".");
            sb.append(iArr[1]);
            String sb2 = sb.toString();
            printWriter.print(str);
            printWriter.print("EGL version=");
            printWriter.print(sb2);
            printWriter.print(", ");
            printWriter.print("EGL ready=");
            printWriter.print(eglHelper.mEglReady);
            printWriter.print(", ");
            printWriter.print("has EglContext=");
            printWriter.print(eglHelper.hasEglContext());
            printWriter.print(", ");
            printWriter.print("has EglSurface=");
            printWriter.println(eglHelper.hasEglSurface());
            int[] config = EglHelper.getConfig();
            StringBuilder sb3 = new StringBuilder();
            sb3.append('{');
            for (int i = 0; i < 17; i++) {
                int i2 = config[i];
                sb3.append("0x");
                sb3.append(Integer.toHexString(i2));
                sb3.append(",");
            }
            sb3.setCharAt(sb3.length() - 1, '}');
            printWriter.print(str);
            printWriter.print("EglConfig=");
            printWriter.println(sb3.toString());
            ImageWallpaperRenderer imageWallpaperRenderer = this.mRenderer;
            imageWallpaperRenderer.getClass();
            printWriter.print(str);
            printWriter.print("mSurfaceSize=");
            printWriter.print(imageWallpaperRenderer.mSurfaceSize);
            printWriter.print(str);
            printWriter.print("mWcgContent=");
            printWriter.print(imageWallpaperRenderer.mTexture.mWcgContent);
            imageWallpaperRenderer.mWallpaper.getClass();
        }

        public final void finishRendering() {
            EglHelper eglHelper;
            Settings.System.putLong(ImageWallpaper.this.getApplicationContext().getContentResolver(), "wallpaper_finish_drawing", System.currentTimeMillis());
            Trace.beginSection("ImageWallpaper#finishRendering");
            int semGetWallpaperType = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).semGetWallpaperType(17);
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("finishRendering : which = 17 , wallpaperType = ", semGetWallpaperType, "ImageWallpaper[GLEngine]");
            if (semGetWallpaperType != 8 && (eglHelper = this.mEglHelper) != null) {
                eglHelper.destroyEglSurface();
                this.mEglHelper.destroyEglContext();
            }
            Trace.endSection();
        }

        public final int getDisplayId() {
            return super.getDisplayId();
        }

        public final void onConfigurationChanged(Configuration configuration) {
            if (super.getDisplayId() != 0) {
                ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log("ImageWallpaper[GLEngine]", "onConfigurationChanged display id= " + super.getDisplayId() + " , newConfig =" + configuration);
            }
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(3, this, configuration));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onCreate(SurfaceHolder surfaceHolder) {
            int displayId = super.getDisplayId();
            Log.i("ImageWallpaper[GLEngine]", "Engine onCreate: displayId = " + displayId);
            semSetFixedOrientation(isFixedOrientationWallpaper(displayId, getCurrentUserId()), false);
            Trace.beginSection("ImageWallpaper.Engine#onCreate");
            Log.i("ImageWallpaper[GLEngine]", "Engine onCreate");
            this.mEglHelper = new EglHelper();
            Context displayContext = getDisplayContext();
            int displayId2 = super.getDisplayId();
            ImageWallpaper imageWallpaper = ImageWallpaper.this;
            this.mRenderer = new ImageWallpaperRenderer(displayContext, displayId2, imageWallpaper.mLogger, imageWallpaper.mSystemWallpaperColors, imageWallpaper.mCoverWallpaper);
            setFixedSizeAllowed(true);
            updateSurfaceSize();
            setShowForAllUsers(true);
            this.mRenderer.mOnBitmapUpdated = new ImageWallpaper$GLEngine$$ExternalSyntheticLambda5(this, 0);
            this.mRotation = ImageWallpaper.this.getResources().getConfiguration().windowConfiguration.getRotation();
            if (ImageWallpaper.this.mDisplayController == null || displayId == 2 || displayId == 1) {
                Log.i("ImageWallpaper[GLEngine]", " do not add display controller in dex");
            } else {
                try {
                    WindowManagerGlobal.getWindowManagerService().watchRotation(this.mRotationWatcher, displayId);
                } catch (Exception e) {
                    Log.w("ImageWallpaper[GLEngine]", "Failed to set rotation watcher. e=" + e, e);
                }
                ImageWallpaper.this.mDisplayController.addDisplayWindowListener(this.onDisplaysChangedListener);
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper2 = ImageWallpaper.this;
                imageWallpaper2.mMainThreadHandler.post(new RunnableC37121(imageWallpaper2, this.mWakefulnessObserver));
            }
            ImageWallpaper.this.mEngineList.put(Integer.valueOf(displayId), this);
            if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext());
                this.mVirtualDisplayMode = WallpaperManager.isVirtualWallpaperDisplay(ImageWallpaper.this.getApplicationContext(), displayId);
            }
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 8));
            ((DisplayManager) getDisplayContext().getSystemService(DisplayManager.class)).registerDisplayListener(this, ImageWallpaper.this.mWorker.getThreadHandler());
            Trace.endSection();
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onDestroy() {
            Log.i("ImageWallpaper[GLEngine]", "Engine onDestroy in work thread " + super.getDisplayId());
            ((DisplayManager) getDisplayContext().getSystemService(DisplayManager.class)).unregisterDisplayListener(this);
            ImageWallpaper.this.mMiniBitmap = null;
            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("Engine onDestroy displayId "), super.getDisplayId(), "ImageWallpaper[GLEngine]");
            if (LsRune.WALLPAPER_PLAY_GIF && (super.getDisplayId() == 1 || this.mVirtualDisplayMode)) {
                HandlerThread handlerThread = ImageWallpaper.this.mWorker;
                if (handlerThread != null) {
                    handlerThread.getThreadHandler().removeCallbacks(this.mUpdatePluginTask);
                }
                ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).onHomeWallpaperDestroyed();
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mMainThreadHandler.post(new RunnableC37132(imageWallpaper, this.mWakefulnessObserver));
            }
            if (ImageWallpaper.this.mDisplayController != null && super.getDisplayId() != 2 && super.getDisplayId() != 1) {
                try {
                    WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(this.mRotationWatcher);
                } catch (Exception e) {
                    Log.w("ImageWallpaper[GLEngine]", "Failed to remove rotation watcher. e=" + e, e);
                }
                ImageWallpaper.this.mDisplayController.removeDisplayWindowListener(this.onDisplaysChangedListener);
            }
            ImageWallpaper.this.mEngineList.remove(Integer.valueOf(super.getDisplayId()), this);
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 9));
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            if (i == getDisplayContext().getDisplayId()) {
                this.mDisplaySizeValid = false;
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
            int i3 = 1;
            if (this.mLastWallpaperYOffset != f2) {
                int i4 = 0;
                boolean z = f2 == 0.5f;
                ImageWallpaperRenderer imageWallpaperRenderer = this.mRenderer;
                if (imageWallpaperRenderer.mIsSmartCropAllowed != z) {
                    ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("setSmartCropAllowed: ", z, "ImageWallpaperRenderer");
                }
                imageWallpaperRenderer.mIsSmartCropAllowed = z;
                this.mLastWallpaperYOffset = f2;
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, i4));
            }
            int round = (f3 <= 0.0f || f3 > 1.0f) ? 1 : Math.round(1.0f / f3) + 1;
            if (round == ImageWallpaper.this.mPages) {
                return;
            }
            ImageWallpaper.this.mPages = round;
            Bitmap bitmap = ImageWallpaper.this.mMiniBitmap;
            if (bitmap == null || bitmap.isRecycled()) {
                return;
            }
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, i3));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            if (ImageWallpaper.this.mWorker == null) {
                return;
            }
            Log.i("ImageWallpaper[GLEngine]", " onSurfaceChanged w: " + i2 + " , h: " + i3);
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda1(this, i2, i3, 0));
            if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).isCoverWallpaperRequired()) {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 2));
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            if (ImageWallpaper.this.mWorker == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(" onSurfaceCreated ");
            sb.append(surfaceHolder.getSurfaceFrame());
            sb.append(" , ");
            NotificationListener$$ExternalSyntheticOutline0.m123m(sb, this.mSurfaceCreated, "ImageWallpaper[GLEngine]");
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(0, this, surfaceHolder));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            super.onSurfaceDestroyed(surfaceHolder);
            NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder(" onSurfaceDestroyed "), this.mSurfaceCreated, "ImageWallpaper[GLEngine]");
            this.mSurfaceCreated = false;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            if (ImageWallpaper.this.mWorker == null) {
                return;
            }
            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder(" onSurfaceRedrawNeeded  id: "), super.getDisplayId(), "ImageWallpaper[GLEngine]");
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 3));
        }

        public final void onSwitchDisplayChanged(boolean z) {
            boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
            if (!z2 || this.mRenderer == null) {
                return;
            }
            semSetFixedOrientation(isFixedOrientationWallpaper(super.getDisplayId(), getCurrentUserId()), false);
            this.mRenderer.getClass();
            ImageWallpaperRenderer imageWallpaperRenderer = this.mRenderer;
            imageWallpaperRenderer.getClass();
            if (z2) {
                StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m(" onFolderStateChanged  ", z, "  , ");
                m49m.append(ImageWallpaperRenderer.showLidState(imageWallpaperRenderer.mWallpaperManager.getLidState()));
                m49m.append(" , mLidState:");
                m49m.append(ImageWallpaperRenderer.showLidState(imageWallpaperRenderer.mLidState));
                ((WallpaperLoggerImpl) imageWallpaperRenderer.mLoggerWrapper).log("ImageWallpaperRenderer", m49m.toString());
                imageWallpaperRenderer.mIsFolded = z;
                if (z) {
                    PowerManager powerManager = imageWallpaperRenderer.mPm;
                    if (powerManager != null && !powerManager.isInteractive()) {
                        Log.i("ImageWallpaperRenderer", " onFolderStateChanged screen off.");
                    } else if (imageWallpaperRenderer.mLidState == 1) {
                        Log.i("ImageWallpaperRenderer", " do not change lid state. so request update ");
                        imageWallpaperRenderer.setLidState(0);
                    }
                } else {
                    Log.i("ImageWallpaperRenderer", " Fold open. so request update ");
                    imageWallpaperRenderer.setLidState(1);
                }
            }
            Size reportSurfaceSize = this.mRenderer.reportSurfaceSize();
            Rect surfaceFrame = getSurfaceHolder().getSurfaceFrame();
            if (reportSurfaceSize.getHeight() == surfaceFrame.height() && reportSurfaceSize.getWidth() == surfaceFrame.width()) {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 7));
                return;
            }
            getSurfaceHolder();
            int max = Math.max(128, reportSurfaceSize.getWidth());
            int max2 = Math.max(128, reportSurfaceSize.getHeight());
            Log.i("ImageWallpaper[GLEngine]", " change surface size  width : " + max + " height : " + max2);
            getSurfaceHolder().setFixedSize(max, max2);
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onVisibilityChanged(boolean z) {
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda6(this, z, 0));
        }

        public final void refreshCachedWallpaper(final int i) {
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper.this.mWorker.getThreadHandler().post(new Runnable() { // from class: com.android.systemui.wallpapers.ImageWallpaper$GLEngine$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        if ((i2 & 2) != 0) {
                            return;
                        }
                        if ((i2 & 4) != 0) {
                            i2 &= -5;
                        }
                        WallpaperUtils.clearCachedWallpaper(i2);
                    }
                });
            }
        }

        public final void removeLocalColorsAreas(List list) {
            ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda2(this, list, 0));
        }

        public final void setCurrentUserId(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("setCurrentUserId: userId = ", i, "ImageWallpaper[GLEngine]");
            ImageWallpaperRenderer imageWallpaperRenderer = this.mRenderer;
            if (imageWallpaperRenderer != null) {
                imageWallpaperRenderer.mTexture.mCurrentUserId = i;
            } else {
                Log.d("ImageWallpaper[GLEngine]", "setCurrentUserId: mRenderer is null.");
            }
        }

        public final boolean shouldWaitForEngineShown() {
            return true;
        }

        public final boolean shouldZoomOutWallpaper() {
            return false;
        }

        public final boolean supportsLocalColorExtraction() {
            return true;
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

        public final void updateRendering() {
            EglHelper eglHelper = this.mEglHelper;
            if (eglHelper != null) {
                try {
                    eglHelper.destroyEglSurface();
                    this.mEglHelper.destroyEglContext();
                    drawFrame();
                } catch (Exception e) {
                    Log.i("ImageWallpaper[GLEngine]", " error : " + e.getMessage());
                }
            }
        }

        public final void updateSurfaceSize() {
            Trace.beginSection("ImageWallpaper#updateSurfaceSize");
            SurfaceHolder surfaceHolder = getSurfaceHolder();
            Size reportSurfaceSize = this.mRenderer.reportSurfaceSize();
            int max = Math.max(128, reportSurfaceSize.getWidth());
            int max2 = Math.max(128, reportSurfaceSize.getHeight());
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                Log.i("ImageWallpaper[GLEngine]", " updateSurfaceSize width : " + max + " height : " + max2);
            }
            surfaceHolder.setFixedSize(max, max2);
            Trace.endSection();
        }

        public final boolean updateSurfaceSizeIfNeed() {
            Size reportSurfaceSize = this.mRenderer.reportSurfaceSize();
            Rect surfaceFrame = getSurfaceHolder().getSurfaceFrame();
            if (reportSurfaceSize.getHeight() == surfaceFrame.height() && reportSurfaceSize.getWidth() == surfaceFrame.width()) {
                return false;
            }
            Log.i("ImageWallpaper[GLEngine]", "  updateSurfaceSizeIfNeed frame  " + reportSurfaceSize + " surfaceFrame : " + surfaceFrame);
            finishRendering();
            updateSurfaceSize();
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x01a4  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x01ba  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateWallpaperOffset(int i) {
            String str;
            String str2;
            int min;
            int i2;
            int i3;
            IBinder windowTokenAsBinder = getWindowTokenAsBinder();
            ImageWallpaperRenderer imageWallpaperRenderer = this.mRenderer;
            if (imageWallpaperRenderer == null || windowTokenAsBinder == null) {
                return;
            }
            int i4 = imageWallpaperRenderer.mDisplayId;
            if (i4 == 2 || (LsRune.WALLPAPER_VIRTUAL_DISPLAY && imageWallpaperRenderer.mIsVirtualDisplay)) {
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m(" ignore updateWallpaperOffset ", i4, "ImageWallpaperRenderer");
                return;
            }
            WallpaperManager wallpaperManager = imageWallpaperRenderer.mWallpaperManager;
            if (wallpaperManager != null) {
                int i5 = imageWallpaperRenderer.mSmartCropYOffset;
                int semGetWallpaperType = wallpaperManager.semGetWallpaperType(imageWallpaperRenderer.getCurrentWhich());
                StringBuilder sb = new StringBuilder();
                sb.append("updateWallpaperOffset " + i4);
                sb.append(" lastCropOffset " + i5 + " , wp Type " + semGetWallpaperType + " , rotation " + i + " , allowed " + imageWallpaperRenderer.mIsSmartCropAllowed);
                ImageSmartCropper imageSmartCropper = imageWallpaperRenderer.mImageSmartCropper;
                if (imageSmartCropper != null && imageWallpaperRenderer.mIsSmartCropAllowed && imageSmartCropper.needToSmartCrop()) {
                    int i6 = 1;
                    if ((i == 1 || i == 3) && semGetWallpaperType == 0) {
                        if (!LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                            if (i4 == 2) {
                                i6 = 9;
                            }
                        } else if (wallpaperManager.getLidState() == 0) {
                            i6 = 17;
                        }
                        if (WallpaperUtils.getCachedSmartCroppedRect(i6) == null) {
                            sb.append(" Error Smart rect is Null " + imageWallpaperRenderer.mSmartCropYOffset);
                            i3 = 0;
                            str = "ImageWallpaperRenderer";
                        } else {
                            Context context = imageWallpaperRenderer.mContext;
                            Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(i4);
                            if (display != null) {
                                DisplayInfo displayInfo = new DisplayInfo();
                                display.getDisplayInfo(displayInfo);
                                i2 = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                min = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                            } else {
                                Log.e("ImageWallpaperRenderer", " getDisplaySize use configuration to recognize the screen size");
                                Rect bounds = context.getResources().getConfiguration().windowConfiguration.getBounds();
                                int width = bounds.width();
                                int height = bounds.height();
                                int max = Math.max(width, height);
                                min = Math.min(width, height);
                                i2 = max;
                            }
                            Size size = new Size(i2, min);
                            int width2 = size.getWidth();
                            int height2 = size.getHeight();
                            Rect rect = imageWallpaperRenderer.mSurfaceSize;
                            float f = height2;
                            float height3 = rect.height() * Math.max(width2 / rect.width(), f / rect.height());
                            float height4 = r0.top / imageWallpaperRenderer.mTexture.mDimensions.height();
                            float f2 = height4 * height3;
                            float f3 = (f * 0.5f) + f2;
                            float f4 = imageWallpaperRenderer.mYOffset;
                            int i7 = (int) (height3 * f4);
                            str = "ImageWallpaperRenderer";
                            sb.append(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m(", screenSize=(", width2, ", ", height2, ")"));
                            sb.append(", origTopPos " + height4 + " , calcTopPos " + f2);
                            sb.append(", scaledHeight " + height3 + " , " + i7 + " , " + f4 + " , smartCropCenterY " + f3);
                            i3 = (int) (((float) i7) - f3);
                        }
                        imageWallpaperRenderer.mSmartCropYOffset = i3;
                        if (i5 != imageWallpaperRenderer.mSmartCropYOffset) {
                            sb.append(" Do not change Display offset " + imageWallpaperRenderer.mSmartCropYOffset);
                        } else {
                            sb.append(" : Set Display offset " + imageWallpaperRenderer.mSmartCropYOffset);
                            try {
                                wallpaperManager.setDisplayOffset(windowTokenAsBinder, 0, imageWallpaperRenderer.mSmartCropYOffset);
                            } catch (IllegalArgumentException e) {
                                str2 = str;
                                Log.i(str2, " Wallpaper window proxy does not exist. " + e.getMessage());
                            }
                        }
                        str2 = str;
                        ((WallpaperLoggerImpl) imageWallpaperRenderer.mLoggerWrapper).log(str2, sb.toString());
                    }
                }
                str = "ImageWallpaperRenderer";
                imageWallpaperRenderer.mSmartCropYOffset = 0;
                if (i5 != imageWallpaperRenderer.mSmartCropYOffset) {
                }
                str2 = str;
                ((WallpaperLoggerImpl) imageWallpaperRenderer.mLoggerWrapper).log(str2, sb.toString());
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$GLEngine$2 */
        public final class C37222 implements DisplayController.OnDisplaysChangedListener {
            public long mFixedRotationStartTime;

            public C37222() {
            }

            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
                ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log("ImageWallpaper[GLEngine]", "onDisplayConfigurationChanged displayId=" + i + ", newConfig=" + configuration);
            }

            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onFixedRotationFinished(int i) {
                GLEngine gLEngine = GLEngine.this;
                Configuration configuration = ImageWallpaper.this.getResources().getConfiguration();
                int displayId = gLEngine.getDisplayId();
                DisplayInfo displayInfo = new DisplayInfo();
                Display display = ((DisplayManager) ImageWallpaper.this.getSystemService("display")).getDisplay(displayId);
                if (display != null) {
                    display.getDisplayInfo(displayInfo);
                } else {
                    NestedScrollView$$ExternalSyntheticOutline0.m34m("  getCurrentDisplayRotation failed to get display. displayId=", displayId, "ImageWallpaper[GLEngine]");
                }
                int i2 = displayInfo.rotation;
                long elapsedRealtime = SystemClock.elapsedRealtime() - this.mFixedRotationStartTime;
                WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                StringBuilder sb = new StringBuilder("onFixedRotationFinished mRotation=");
                AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, gLEngine.mRotation, ", displayRotation=", i2, ", configRotation=");
                sb.append(configuration.windowConfiguration.getRotation());
                sb.append(", elapsed=");
                sb.append(elapsedRealtime);
                sb.append(", ");
                sb.append(configuration);
                ((WallpaperLoggerImpl) wallpaperLogger).log("ImageWallpaper[GLEngine]", sb.toString());
                if (i == gLEngine.getDisplayId()) {
                    if (gLEngine.mRotation != i2) {
                        ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log("ImageWallpaper[GLEngine]", "onFixedRotationFinished Error orientation. So update Again.");
                        gLEngine.mRotation = i2;
                        ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$GLEngine$2$$ExternalSyntheticLambda0(this, 1));
                        return;
                    }
                    return;
                }
                ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log("ImageWallpaper[GLEngine]", "onFixedRotationFinished not my display : myId=" + gLEngine.getDisplayId() + ", fixedRotationId=" + i);
            }

            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onFixedRotationStarted(int i, int i2) {
                GLEngine gLEngine = GLEngine.this;
                WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onFixedRotationStarted displayId=", i, ", newRotation=", i2, ", mRotation=");
                m45m.append(gLEngine.mRotation);
                m45m.append(", ");
                m45m.append(ImageWallpaper.this.getResources().getConfiguration());
                ((WallpaperLoggerImpl) wallpaperLogger).log("ImageWallpaper[GLEngine]", m45m.toString());
                this.mFixedRotationStartTime = SystemClock.elapsedRealtime();
                if (i == gLEngine.getDisplayId()) {
                    if (gLEngine.mRotation != i2) {
                        gLEngine.mRotation = i2;
                        ImageWallpaper.this.mWorker.getThreadHandler().postDelayed(new ImageWallpaper$GLEngine$2$$ExternalSyntheticLambda0(this, 0), 150L);
                        return;
                    }
                    return;
                }
                ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log("ImageWallpaper[GLEngine]", "onFixedRotationStarted not my display : myId=" + gLEngine.getDisplayId() + ", fixedRotationId=" + i);
            }

            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayAdded(int i) {
            }

            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayRemoved(int i) {
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CanvasEngine extends BaseEngine implements DisplayManager.DisplayListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        static final int MIN_SURFACE_HEIGHT = 128;
        static final int MIN_SURFACE_WIDTH = 128;
        public String TAG;
        public final Paint mBitmapPaint;
        public int mDisplayHeight;
        public boolean mDisplaySizeValid;
        public int mDisplayWidth;
        public final C37184 mDisplaysChangedListener;
        public ImageWallpaperCanvasHelper mHelper;
        public int mImgHeight;
        public int mImgWidth;
        public IntelligentCropHelper mIntelligentCropHelper;
        public boolean mIsEngineAlive;
        public boolean mIsFixedRotationInProgress;
        public boolean mIsLockscreenLiveWallpaperEnabled;
        public boolean mIsVirtualDisplayMode;
        public DrawState mLastDrawnState;
        public float mLastWallpaperYOffset;
        public final Object mLock;
        public final C37206 mPluginHomeWallpaperConsumer;
        public int mRotation;
        public final C37195 mRotationWatcher;
        public boolean mSurfaceCreated;
        public SurfaceHolder mSurfaceHolder;
        public final C37173 mWakefulnessObserver;
        public final WallpaperLocalColorExtractor mWallpaperLocalColorExtractor;
        public WallpaperManager mWallpaperManager;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$1 */
        public final class C37151 implements WallpaperLocalColorExtractor.WallpaperLocalColorExtractorCallback {
            public C37151(ImageWallpaper imageWallpaper) {
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$2 */
        public final class C37162 implements ImageWallpaperCanvasHelper.Callback {
            public C37162() {
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$3 */
        public final class C37173 implements WakefulnessLifecycle.Observer {
            public C37173() {
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                ImageWallpaper$GLEngine$1$$ExternalSyntheticLambda0 imageWallpaper$GLEngine$1$$ExternalSyntheticLambda0 = new ImageWallpaper$GLEngine$1$$ExternalSyntheticLambda0(this, 2);
                int i = CanvasEngine.$r8$clinit;
                CanvasEngine.this.runAsWorkerThread(imageWallpaper$GLEngine$1$$ExternalSyntheticLambda0);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$6 */
        public final class C37206 implements Consumer {
            public C37206() {
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CanvasEngine canvasEngine = CanvasEngine.this;
                ImageWallpaper$GLEngine$$ExternalSyntheticLambda3 imageWallpaper$GLEngine$$ExternalSyntheticLambda3 = new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(2, this, (Boolean) obj);
                int i = CanvasEngine.$r8$clinit;
                canvasEngine.runAsWorkerThread(imageWallpaper$GLEngine$$ExternalSyntheticLambda3);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class DrawState {
            public final boolean mDarkModeFilterApplied;
            public final boolean mHighlightFilterApplied;
            public final int mSurfaceHeight;
            public final int mSurfaceWidth;
            public final int mWhich;

            public DrawState(CanvasEngine canvasEngine, int i, int i2, int i3, boolean z, boolean z2) {
                if ((i & 60) == 0) {
                    Log.e(canvasEngine.TAG, AbstractC0000x2c234b15.m0m("DrawState : mode value is missing. which=", i), new RuntimeException());
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

        /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$4] */
        /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$5] */
        public CanvasEngine() {
            super();
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
            this.mLock = new Object();
            this.mIsEngineAlive = false;
            this.mWakefulnessObserver = new C37173();
            this.mDisplaysChangedListener = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.4
                public long mFixedRotationStartTime;

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    ((WallpaperLoggerImpl) wallpaperLogger).log(canvasEngine.TAG, "onDisplayConfigurationChanged displayId=" + i + ", newConfig=" + configuration);
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onFixedRotationFinished(int i) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    Configuration configuration = ImageWallpaper.this.getResources().getConfiguration();
                    int displayId = canvasEngine.getDisplayId();
                    DisplayInfo displayInfo = new DisplayInfo();
                    Display display = ((DisplayManager) ImageWallpaper.this.getSystemService("display")).getDisplay(displayId);
                    if (display != null) {
                        display.getDisplayInfo(displayInfo);
                    } else {
                        NestedScrollView$$ExternalSyntheticOutline0.m34m("  getCurrentDisplayRotation failed to get display. displayId=", displayId, canvasEngine.TAG);
                    }
                    int i2 = displayInfo.rotation;
                    long elapsedRealtime = SystemClock.elapsedRealtime() - this.mFixedRotationStartTime;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder sb = new StringBuilder("onFixedRotationFinished mRotation=");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, canvasEngine.mRotation, ", displayRotation=", i2, ", configRotation=");
                    sb.append(configuration.windowConfiguration.getRotation());
                    sb.append(", elapsed=");
                    sb.append(elapsedRealtime);
                    sb.append(", ");
                    sb.append(configuration);
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, sb.toString());
                    if (i == canvasEngine.getDisplayId()) {
                        if (canvasEngine.mRotation != i2) {
                            ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(canvasEngine.TAG, "onFixedRotationFinished Error orientation. So update Again.");
                            canvasEngine.mRotation = i2;
                            canvasEngine.updateWallpaperOffset(canvasEngine.mHelper.getCurrentWhich());
                        }
                        canvasEngine.mIsFixedRotationInProgress = false;
                        return;
                    }
                    WallpaperLogger wallpaperLogger2 = ImageWallpaper.this.mLogger;
                    ((WallpaperLoggerImpl) wallpaperLogger2).log(canvasEngine.TAG, "onFixedRotationFinished not my display : myId=" + canvasEngine.getDisplayId() + ", fixedRotationId=" + i);
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onFixedRotationStarted(int i, int i2) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onFixedRotationStarted displayId=", i, ", newRotation=", i2, ", mRotation=");
                    m45m.append(canvasEngine.mRotation);
                    m45m.append(", ");
                    m45m.append(ImageWallpaper.this.getResources().getConfiguration());
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, m45m.toString());
                    this.mFixedRotationStartTime = SystemClock.elapsedRealtime();
                    if (i == canvasEngine.getDisplayId()) {
                        canvasEngine.mIsFixedRotationInProgress = true;
                        if (canvasEngine.mRotation != i2) {
                            canvasEngine.mRotation = i2;
                            return;
                        }
                        return;
                    }
                    WallpaperLogger wallpaperLogger2 = ImageWallpaper.this.mLogger;
                    ((WallpaperLoggerImpl) wallpaperLogger2).log(canvasEngine.TAG, "onFixedRotationStarted not my display : myId=" + canvasEngine.getDisplayId() + ", fixedRotationId=" + i);
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onDisplayAdded(int i) {
                }

                @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                public final void onDisplayRemoved(int i) {
                }
            };
            this.mRotationWatcher = new IRotationWatcher.Stub() { // from class: com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.5
                public final void onRotationChanged(int i) {
                    CanvasEngine canvasEngine = CanvasEngine.this;
                    WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                    String str = canvasEngine.TAG;
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("onRotationChanged: newRotation=", i, ", mRotation=");
                    m1m.append(CanvasEngine.this.mRotation);
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, m1m.toString());
                    CanvasEngine canvasEngine2 = CanvasEngine.this;
                    if (canvasEngine2.mRotation != i) {
                        ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(canvasEngine2.TAG, "onRotationChanged rotation is changed ");
                        CanvasEngine canvasEngine3 = CanvasEngine.this;
                        canvasEngine3.mRotation = i;
                        int currentWhich = canvasEngine3.mHelper.getCurrentWhich();
                        if (CanvasEngine.this.mHelper.hasIntelligentCropHints(currentWhich)) {
                            CanvasEngine.this.updateSurfaceSize(currentWhich);
                        }
                    }
                }
            };
            this.mPluginHomeWallpaperConsumer = new C37206();
            setFixedSizeAllowed(true);
            setShowForAllUsers(true);
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = new WallpaperLocalColorExtractor(ImageWallpaper.this.mLongExecutor, new C37151(ImageWallpaper.this));
            this.mWallpaperLocalColorExtractor = wallpaperLocalColorExtractor;
            if (ImageWallpaper.this.mPagesComputed) {
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda4(wallpaperLocalColorExtractor, ImageWallpaper.this.mPages));
            }
        }

        public final void addLocalColorsAreas(List list) {
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.getClass();
            int i = 1;
            if (list.size() > 0) {
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda0(wallpaperLocalColorExtractor, list, i));
            } else {
                Log.w("WallpaperLocalColorExtractor", "Attempt to add colors with an empty list");
            }
            Log.i(this.TAG, " addLocalColorsAreas ");
            if (ImageWallpaper.this.mLocalColorsToAdd.size() + ImageWallpaper.this.mColorAreas.size() == 0) {
                setOffsetNotificationsEnabled(true);
            }
            ImageWallpaper imageWallpaper = ImageWallpaper.this;
            Bitmap bitmap = imageWallpaper.mMiniBitmap;
            if (bitmap != null) {
                computeAndNotifyLocalColors(list, bitmap);
                return;
            }
            imageWallpaper.mLocalColorsToAdd.addAll(list);
            this.mHelper.useWallpaperBitmap(this.mHelper.getCurrentWhich(), new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda0(this, i));
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

        public final int determineHighlightFilterAmount() {
            int i;
            if (!(!TextUtils.isEmpty(this.mHelper.mColorDecorFilterData))) {
                Boolean canApplyFilterOnHome = HighlightFilterHelper.canApplyFilterOnHome(WhichChecker.convertDisplayIdToMode(getDisplayId(), ImageWallpaper.this.getApplicationContext()));
                if (canApplyFilterOnHome == null) {
                    canApplyFilterOnHome = Boolean.FALSE;
                }
                if (canApplyFilterOnHome.booleanValue()) {
                    i = HighlightFilterHelper.getFilterAmount(ImageWallpaper.this.mSettingsHelper);
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m(" determineHighlightFilterAmount : ", i, this.TAG);
                    return i;
                }
            }
            i = -1;
            ListPopupWindow$$ExternalSyntheticOutline0.m10m(" determineHighlightFilterAmount : ", i, this.TAG);
            return i;
        }

        public final DrawState drawFrameOnCanvas(int i, long j, Rect rect, Bitmap bitmap, float f, int i2) {
            long j2;
            long j3;
            long j4;
            DrawState drawState;
            DrawState drawState2;
            Rect surfaceFrame;
            float f2;
            float f3;
            int determineHighlightFilterAmount;
            Integer dimFilterColor;
            int i3;
            int i4;
            DrawState drawState3 = null;
            if (bitmap == null) {
                Log.w(this.TAG, "drawFrameOnCanvas : bitmap is null");
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
                        j2 = elapsedRealtime;
                        j3 = j2;
                        j4 = j3;
                        Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                        drawState2 = drawState3;
                        drawState = drawState2;
                        long elapsedRealtime2 = SystemClock.elapsedRealtime() - j;
                        String str = this.TAG;
                        StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("drawFrameOnCanvas : elapsed=", elapsedRealtime2, ", bmpPrepareDur=");
                        m17m.append(j2 - j);
                        m17m.append(", filterApplyDur=");
                        m17m.append(j3 - j2);
                        m17m.append(", drawDur=");
                        m17m.append(j4 - j3);
                        m17m.append(", drawnState=(");
                        m17m.append(drawState);
                        m17m.append(")");
                        Log.i(str, m17m.toString());
                        return drawState;
                    }
                }
                if (WhichChecker.isWatchFace(i) && ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).isCoverWallpaperRequired()) {
                    nearestCropHint = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                }
                Matrix matrix = new Matrix();
                int width = nearestCropHint != null ? nearestCropHint.width() : bitmap.getWidth();
                int height = nearestCropHint != null ? nearestCropHint.height() : bitmap.getHeight();
                int width2 = surfaceFrame.width();
                try {
                    int height2 = surfaceFrame.height();
                    j2 = elapsedRealtime;
                    if (width * height2 > width2 * height) {
                        f2 = height2;
                        f3 = height;
                    } else {
                        f2 = width2;
                        f3 = width;
                    }
                    float f4 = f2 / f3;
                    float f5 = (width2 - (width * f4)) * 0.5f;
                    float f6 = (height2 - (height * f4)) * 0.5f;
                    try {
                        matrix.setScale(f4, f4);
                        if (nearestCropHint != null) {
                            try {
                                matrix.preTranslate(-nearestCropHint.left, -nearestCropHint.top);
                            } catch (Exception e2) {
                                e = e2;
                                drawState3 = null;
                                j4 = j2;
                                j3 = j4;
                                Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                                drawState2 = drawState3;
                                drawState = drawState2;
                                long elapsedRealtime22 = SystemClock.elapsedRealtime() - j;
                                String str2 = this.TAG;
                                StringBuilder m17m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("drawFrameOnCanvas : elapsed=", elapsedRealtime22, ", bmpPrepareDur=");
                                m17m2.append(j2 - j);
                                m17m2.append(", filterApplyDur=");
                                m17m2.append(j3 - j2);
                                m17m2.append(", drawDur=");
                                m17m2.append(j4 - j3);
                                m17m2.append(", drawnState=(");
                                m17m2.append(drawState);
                                m17m2.append(")");
                                Log.i(str2, m17m2.toString());
                                return drawState;
                            }
                        }
                        matrix.postTranslate(Math.round(f5), Math.round(f6));
                        determineHighlightFilterAmount = determineHighlightFilterAmount();
                        ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                        imageWallpaperCanvasHelper.mHighlightFilterAmount = determineHighlightFilterAmount;
                        Bitmap filterAppliedBitmap = imageWallpaperCanvasHelper.getFilterAppliedBitmap(bitmap, i);
                        dimFilterColor = this.mHelper.getDimFilterColor(i);
                        if (dimFilterColor != null) {
                            i4 = height2;
                            i3 = width2;
                            this.mBitmapPaint.setColorFilter(new PorterDuffColorFilter(dimFilterColor.intValue(), PorterDuff.Mode.SRC_OVER));
                            drawState2 = null;
                        } else {
                            i3 = width2;
                            i4 = height2;
                            drawState2 = null;
                            try {
                                this.mBitmapPaint.setColorFilter(null);
                            } catch (Exception e3) {
                                e = e3;
                                drawState3 = drawState2;
                                j4 = j2;
                                j3 = j4;
                                Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                                drawState2 = drawState3;
                                drawState = drawState2;
                                long elapsedRealtime222 = SystemClock.elapsedRealtime() - j;
                                String str22 = this.TAG;
                                StringBuilder m17m22 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("drawFrameOnCanvas : elapsed=", elapsedRealtime222, ", bmpPrepareDur=");
                                m17m22.append(j2 - j);
                                m17m22.append(", filterApplyDur=");
                                m17m22.append(j3 - j2);
                                m17m22.append(", drawDur=");
                                m17m22.append(j4 - j3);
                                m17m22.append(", drawnState=(");
                                m17m22.append(drawState);
                                m17m22.append(")");
                                Log.i(str22, m17m22.toString());
                                return drawState;
                            }
                        }
                        j3 = SystemClock.elapsedRealtime();
                        try {
                            Log.i(this.TAG, "drawFrameOnCanvas : which=" + i + ", bmpW=" + bitmap.getWidth() + ", bmpH=" + bitmap.getHeight() + ", bmpScale=" + f + ", src=" + nearestCropHint + ", dest=" + surfaceFrame + ", highlight=" + determineHighlightFilterAmount + ", dimColor=" + dimFilterColor + ", drawRepeatCount=" + i2);
                            for (int i5 = 0; i5 < i2; i5++) {
                                Surface surface = this.mSurfaceHolder.getSurface();
                                Canvas lockHardwareWideColorGamutCanvas = this.mHelper.mIsWcgContent ? surface.lockHardwareWideColorGamutCanvas() : surface.lockHardwareCanvas();
                                if (lockHardwareWideColorGamutCanvas == null) {
                                    Log.e(this.TAG, "drawFrameOnCanvas : canvas is NULL");
                                    throw new RuntimeException("failed to lock the canvas - " + i5);
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
                            j4 = SystemClock.elapsedRealtime();
                        } catch (Exception e4) {
                            e = e4;
                            drawState3 = drawState2;
                            j4 = j2;
                        }
                        try {
                        } catch (Exception e5) {
                            e = e5;
                            drawState3 = drawState2;
                            Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                            drawState2 = drawState3;
                            drawState = drawState2;
                            long elapsedRealtime2222 = SystemClock.elapsedRealtime() - j;
                            String str222 = this.TAG;
                            StringBuilder m17m222 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("drawFrameOnCanvas : elapsed=", elapsedRealtime2222, ", bmpPrepareDur=");
                            m17m222.append(j2 - j);
                            m17m222.append(", filterApplyDur=");
                            m17m222.append(j3 - j2);
                            m17m222.append(", drawDur=");
                            m17m222.append(j4 - j3);
                            m17m222.append(", drawnState=(");
                            m17m222.append(drawState);
                            m17m222.append(")");
                            Log.i(str222, m17m222.toString());
                            return drawState;
                        }
                    } catch (Exception e6) {
                        e = e6;
                    }
                } catch (Exception e7) {
                    e = e7;
                    j2 = elapsedRealtime;
                }
            } catch (Exception e8) {
                e = e8;
                j2 = elapsedRealtime;
            }
            if (surfaceFrame.equals(rect)) {
                boolean z = true;
                boolean z2 = determineHighlightFilterAmount > 0;
                if (dimFilterColor == null) {
                    z = false;
                }
                drawState = new DrawState(this, i, i3, i4, z2, z);
                long elapsedRealtime22222 = SystemClock.elapsedRealtime() - j;
                String str2222 = this.TAG;
                StringBuilder m17m2222 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("drawFrameOnCanvas : elapsed=", elapsedRealtime22222, ", bmpPrepareDur=");
                m17m2222.append(j2 - j);
                m17m2222.append(", filterApplyDur=");
                m17m2222.append(j3 - j2);
                m17m2222.append(", drawDur=");
                m17m2222.append(j4 - j3);
                m17m2222.append(", drawnState=(");
                m17m2222.append(drawState);
                m17m2222.append(")");
                Log.i(str2222, m17m2222.toString());
                return drawState;
            }
            Log.w(this.TAG, "drawFrameOnCanvas : surface size mismatch. curFrame=" + surfaceFrame + ", requestedFrame=" + rect);
            drawState = drawState2;
            long elapsedRealtime222222 = SystemClock.elapsedRealtime() - j;
            String str22222 = this.TAG;
            StringBuilder m17m22222 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("drawFrameOnCanvas : elapsed=", elapsedRealtime222222, ", bmpPrepareDur=");
            m17m22222.append(j2 - j);
            m17m22222.append(", filterApplyDur=");
            m17m22222.append(j3 - j2);
            m17m22222.append(", drawDur=");
            m17m22222.append(j4 - j3);
            m17m22222.append(", drawnState=(");
            m17m22222.append(drawState);
            m17m22222.append(")");
            Log.i(str22222, m17m22222.toString());
            return drawState;
        }

        public final void drawFullQualityFrame(final int i, final Rect rect) {
            if (this.mSurfaceHolder == null) {
                Log.e(this.TAG, "drawFullQualityFrame: attempt to draw a frame without a valid surface");
                return;
            }
            if (!this.mIsEngineAlive) {
                Log.d(this.TAG, "drawFullQualityFrame: engine is destroyed");
                return;
            }
            Trace.beginSection("ImageWallpaper.CanvasEngine#drawFrame");
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            updateWallpaperOffset(i);
            this.mHelper.useWallpaperBitmap(i, new Consumer() { // from class: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1
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
            Trace.beginSection("ImageWallpaper.CanvasEngine#unloadBitmap");
            getSurfaceHolder().getSurface().hwuiDestroy();
            this.mWallpaperManager.forgetLoadedWallpaper();
            Trace.endSection();
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
            wallpaperLocalColorExtractor.getClass();
            wallpaperLocalColorExtractor.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpapers.WallpaperLocalColorExtractor$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperLocalColorExtractor wallpaperLocalColorExtractor2 = WallpaperLocalColorExtractor.this;
                    int i = width;
                    int i2 = height;
                    synchronized (wallpaperLocalColorExtractor2.mLock) {
                        if (i == wallpaperLocalColorExtractor2.mDisplayWidth && i2 == wallpaperLocalColorExtractor2.mDisplayHeight) {
                            return;
                        }
                        wallpaperLocalColorExtractor2.mDisplayWidth = i;
                        wallpaperLocalColorExtractor2.mDisplayHeight = i2;
                        wallpaperLocalColorExtractor2.processColorsInternal();
                    }
                }
            });
        }

        @Override // com.android.systemui.wallpapers.ImageWallpaper.BaseEngine
        public final boolean isFixedOrientationWallpaper(int i, int i2) {
            if (super.isFixedOrientationWallpaper(i, i2)) {
                return true;
            }
            PackageManager packageManager = ImageWallpaper.this.getBaseContext().getPackageManager();
            boolean z = packageManager != null && packageManager.hasSystemFeature("com.samsung.feature.device_category_tablet");
            boolean z2 = Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mWallpaperManager.getLidState() == 0;
            SettingsHelper.ItemMap itemMap = ImageWallpaper.this.mSettingsHelper.mItemLists;
            boolean z3 = (z2 ? itemMap.get("sub_display_system_wallpaper_transparency").getIntValue() : itemMap.get("android.wallpaper.settings_systemui_transparency").getIntValue()) == 0;
            boolean z4 = i == 0 && !(!(Rune.WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER || z2) || z || z3 || isPreview());
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("isFixedOrientationWallpaper: feature=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, Rune.WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER, ", isTablet=", z, ", isCoverDisplay=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z2, ", isCustomWallpaper=", z3, ", isPreview=");
            sb.append(isPreview());
            sb.append(", isFixedOrientation=");
            sb.append(z4);
            Log.i(str, sb.toString());
            return z4;
        }

        public final void onConfigurationChanged(Configuration configuration) {
            if (getDisplayId() != 0) {
                WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
                ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "onConfigurationChanged display id= " + getDisplayId() + " , newConfig =" + configuration);
            }
            runAsWorkerThread(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(1, this, configuration));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onCreate(SurfaceHolder surfaceHolder) {
            CoverWallpaper coverWallpaper;
            int displayId = getDisplayId();
            int i = 2;
            this.TAG = ConstraintWidget$$ExternalSyntheticOutline0.m19m(AbstractC0000x2c234b15.m1m("ImageWallpaper[CanvasEngine_d", displayId, "_w"), getWallpaperFlags() == 2 ? 2 : 1, "]");
            Trace.beginSection("ImageWallpaper.CanvasEngine#onCreate");
            ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(this.TAG, AbstractC0000x2c234b15.m0m("Engine onCreate: displayId = ", displayId));
            WallpaperManager wallpaperManager = (WallpaperManager) getDisplayContext().getSystemService(WallpaperManager.class);
            this.mWallpaperManager = wallpaperManager;
            this.mIsLockscreenLiveWallpaperEnabled = wallpaperManager.isLockscreenLiveWallpaperEnabled();
            this.mSurfaceHolder = surfaceHolder;
            ((DisplayManager) getDisplayContext().getSystemService(DisplayManager.class)).registerDisplayListener(this, null);
            getDisplaySizeAndUpdateColorExtractor();
            semSetFixedOrientation(isFixedOrientationWallpaper(getDisplayId(), getCurrentUserId()), false);
            this.mIntelligentCropHelper = new IntelligentCropHelper();
            C37162 c37162 = new C37162();
            Context displayContext = getDisplayContext();
            int displayId2 = getDisplayId();
            ImageWallpaper imageWallpaper = ImageWallpaper.this;
            ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = new ImageWallpaperCanvasHelper(displayContext, displayId2, imageWallpaper.mLogger, imageWallpaper.mSystemWallpaperColors, imageWallpaper.mCoverWallpaper, this.mIntelligentCropHelper, this.mIsLockscreenLiveWallpaperEnabled, c37162);
            this.mHelper = imageWallpaperCanvasHelper;
            updateSurfaceSize(imageWallpaperCanvasHelper.getCurrentWhich());
            this.mHelper.mBitmapUpdateConsumer = new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda0(this, i);
            this.mRotation = ImageWallpaper.this.getResources().getConfiguration().windowConfiguration.getRotation();
            if (ImageWallpaper.this.mDisplayController == null || displayId == 2 || displayId == 1) {
                Log.i(this.TAG, " do not add display controller in dex");
            } else {
                try {
                    WindowManagerGlobal.getWindowManagerService().watchRotation(this.mRotationWatcher, displayId);
                } catch (Exception e) {
                    Log.w(this.TAG, "Failed to set rotation watcher. e=" + e, e);
                }
                ImageWallpaper.this.mDisplayController.addDisplayWindowListener(this.mDisplaysChangedListener);
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper2 = ImageWallpaper.this;
                imageWallpaper2.mMainThreadHandler.post(new RunnableC37121(imageWallpaper2, this.mWakefulnessObserver));
            }
            ImageWallpaper.this.mCanvasEngineList.put(Integer.valueOf(displayId), this);
            if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                this.mIsVirtualDisplayMode = WallpaperManager.isVirtualWallpaperDisplay(ImageWallpaper.this.getApplicationContext(), displayId);
            }
            if (LsRune.WALLPAPER_PLAY_GIF && (coverWallpaper = ImageWallpaper.this.mCoverWallpaper) != null && (displayId == 1 || this.mIsVirtualDisplayMode)) {
                ((CoverWallpaperController) coverWallpaper).setWallpaperUpdateConsumer(this.mPluginHomeWallpaperConsumer);
            }
            this.mIsEngineAlive = true;
            Trace.endSection();
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onDestroy() {
            WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
            ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "Engine onDestroy displayId " + getDisplayId());
            ((DisplayManager) getDisplayContext().getSystemService(DisplayManager.class)).unregisterDisplayListener(this);
            final WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.getClass();
            wallpaperLocalColorExtractor.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpapers.WallpaperLocalColorExtractor$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperLocalColorExtractor wallpaperLocalColorExtractor2 = WallpaperLocalColorExtractor.this;
                    synchronized (wallpaperLocalColorExtractor2.mLock) {
                        Bitmap bitmap = wallpaperLocalColorExtractor2.mMiniBitmap;
                        if (bitmap != null) {
                            bitmap.recycle();
                            wallpaperLocalColorExtractor2.mMiniBitmap = null;
                        }
                        ((ArraySet) wallpaperLocalColorExtractor2.mProcessedRegions).clear();
                        ((ArrayList) wallpaperLocalColorExtractor2.mPendingRegions).clear();
                    }
                }
            });
            ImageWallpaper.this.mMiniBitmap = null;
            if (LsRune.WALLPAPER_PLAY_GIF && (getDisplayId() == 1 || this.mIsVirtualDisplayMode)) {
                ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).onHomeWallpaperDestroyed();
            }
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mMainThreadHandler.post(new RunnableC37132(imageWallpaper, this.mWakefulnessObserver));
            }
            if (ImageWallpaper.this.mDisplayController != null && getDisplayId() != 2 && getDisplayId() != 1) {
                try {
                    WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(this.mRotationWatcher);
                } catch (Exception e) {
                    Log.w(this.TAG, "Failed to remove rotation watcher. e=" + e, e);
                }
                ImageWallpaper.this.mDisplayController.removeDisplayWindowListener(this.mDisplaysChangedListener);
            }
            ImageWallpaper.this.mCanvasEngineList.remove(Integer.valueOf(getDisplayId()), this);
            WallpaperUtils.clearCachedWallpaper(1);
            WallpaperUtils.clearCachedWallpaper(5);
            WallpaperUtils.clearCachedWallpaper(17);
            WallpaperUtils.clearCachedWallpaper(9);
            setWallpaperOffsetToDefault();
            this.mIsEngineAlive = false;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            if (i == getDisplayContext().getDisplayId()) {
                this.mDisplaySizeValid = false;
                getDisplaySizeAndUpdateColorExtractor();
            }
        }

        public void onMiniBitmapUpdated() {
            ((ExecutorImpl) ImageWallpaper.this.mLongExecutor).execute(new ImageWallpaper$GLEngine$1$$ExternalSyntheticLambda0(this, 1));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
            if (this.mLastWallpaperYOffset != f2) {
                boolean z = f2 == 0.5f;
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                if (imageWallpaperCanvasHelper.mIsSmartCropAllowed != z) {
                    Log.i(imageWallpaperCanvasHelper.TAG, AbstractC0866xb1ce8deb.m86m("setSmartCropAllowed: ", z));
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
            int i3 = imageWallpaper.mPages;
            wallpaperLocalColorExtractor.getClass();
            wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda4(wallpaperLocalColorExtractor, i3));
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder(" onSurfaceCreated ");
            sb.append(surfaceHolder.getSurfaceFrame());
            sb.append(" , ");
            NotificationListener$$ExternalSyntheticOutline0.m123m(sb, this.mSurfaceCreated, str);
            if (!this.mSurfaceCreated) {
                Trace.beginSection("ImageWallpaper#onSurfaceCreated");
                final ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                final int currentWhich = imageWallpaperCanvasHelper.getCurrentWhich();
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("onSurfaceCreated: which=", currentWhich, ", disp=");
                m1m.append(imageWallpaperCanvasHelper.mDisplayId);
                m1m.append(", colorDecor=");
                m1m.append(!TextUtils.isEmpty(imageWallpaperCanvasHelper.mColorDecorFilterData));
                m1m.append(", highlightAmount=");
                m1m.append(imageWallpaperCanvasHelper.mHighlightFilterAmount);
                Log.i(imageWallpaperCanvasHelper.TAG, m1m.toString());
                imageWallpaperCanvasHelper.useWallpaperBitmap(currentWhich, new Consumer() { // from class: com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper$$ExternalSyntheticLambda0
                    /* JADX WARN: Removed duplicated region for block: B:11:0x0039  */
                    @Override // java.util.function.Consumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void accept(Object obj) {
                        boolean z;
                        ImageWallpaperCanvasHelper imageWallpaperCanvasHelper2 = ImageWallpaperCanvasHelper.this;
                        int i = currentWhich;
                        Bitmap bitmap = (Bitmap) obj;
                        if (bitmap == null) {
                            Log.w(imageWallpaperCanvasHelper2.TAG, "reload texture failed!");
                        } else {
                            Consumer consumer = imageWallpaperCanvasHelper2.mBitmapUpdateConsumer;
                            if (consumer != null) {
                                consumer.accept(bitmap);
                            }
                        }
                        if (bitmap != null) {
                            if (!TextUtils.isEmpty(imageWallpaperCanvasHelper2.mColorDecorFilterData)) {
                                bitmap = ColorDecorFilterHelper.createFilteredBitmap(bitmap, imageWallpaperCanvasHelper2.mColorDecorFilterData);
                            } else {
                                int i2 = imageWallpaperCanvasHelper2.mHighlightFilterAmount;
                                if (i2 >= 0) {
                                    bitmap = HighlightFilterHelper.createFilteredBitmap(bitmap, i2);
                                }
                            }
                            z = true;
                            if (bitmap != null) {
                                boolean z2 = (imageWallpaperCanvasHelper2.mDisplayId == 2) || imageWallpaperCanvasHelper2.mIsVirtualDisplay;
                                ImageSmartCropper imageSmartCropper = imageWallpaperCanvasHelper2.mImageSmartCropper;
                                if (imageSmartCropper != null && !z2) {
                                    imageSmartCropper.updateSmartCropRectIfNeeded(bitmap, i);
                                    Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                                    Rect rect2 = imageSmartCropper.mCropResult;
                                    WallpaperManager wallpaperManager = imageWallpaperCanvasHelper2.mWallpaperManager;
                                    if (rect2 == null) {
                                        wallpaperManager.semSetSmartCropRect(1, rect, rect);
                                    } else {
                                        wallpaperManager.semSetSmartCropRect(1, rect, rect2);
                                    }
                                }
                            }
                            if (z || bitmap == null || bitmap.isRecycled()) {
                                return;
                            }
                            bitmap.recycle();
                            return;
                        }
                        z = false;
                        if (bitmap != null) {
                        }
                        if (z) {
                        }
                    }
                });
                Trace.endSection();
            }
            this.mSurfaceCreated = true;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder(" onSurfaceDestroyed "), this.mSurfaceCreated, this.TAG);
            this.mSurfaceCreated = false;
            this.mSurfaceHolder = null;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            DrawState drawState;
            if (!this.mIsEngineAlive) {
                Log.i(this.TAG, "onSurfaceRedrawNeeded: engine already destroyed");
                return;
            }
            final int currentWhich = this.mHelper.getCurrentWhich();
            final Rect rect = new Rect(this.mSurfaceHolder.getSurfaceFrame());
            String str = this.TAG;
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("onSurfaceRedrawNeeded: curWhich=", currentWhich, ", isFixedRotationInProgress=");
            m1m.append(this.mIsFixedRotationInProgress);
            m1m.append(", mRotation=");
            m1m.append(this.mRotation);
            m1m.append(", surfaceFrame=");
            m1m.append(rect);
            Log.i(str, m1m.toString());
            updateWallpaperOffset(currentWhich);
            SurfaceHolder surfaceHolder2 = this.mSurfaceHolder;
            boolean z = false;
            if (surfaceHolder2 == null) {
                drawState = null;
            } else {
                Rect surfaceFrame = surfaceHolder2.getSurfaceFrame();
                drawState = new DrawState(this, currentWhich, surfaceFrame.width(), surfaceFrame.height(), determineHighlightFilterAmount() > 0, this.mHelper.getDimFilterColor(currentWhich) != null);
            }
            synchronized (this.mLock) {
                Log.i(this.TAG, "onSurfaceRedrawNeeded: displayId=" + getDisplayId() + ", lastDrawn=(" + this.mLastDrawnState + "), toDraw=(" + drawState + ")");
                DrawState drawState2 = this.mLastDrawnState;
                if (drawState2 != null && drawState2.equals(drawState)) {
                    Log.i(this.TAG, "onSurfaceRedrawNeeded: not need redraw");
                    return;
                }
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                imageWallpaperCanvasHelper.getClass();
                if (WhichChecker.isFlagEnabled(currentWhich, 1) && WhichChecker.isFlagEnabled(currentWhich, 2)) {
                    z = true;
                }
                ImageWallpaperCanvasHelper.DownScaledSourceBitmap downScaledSourceBitmap = (ImageWallpaperCanvasHelper.DownScaledSourceBitmap) imageWallpaperCanvasHelper.mDownScaledSourceBitmapSet.get(Integer.valueOf(z ? 1 | (currentWhich & 60) : currentWhich));
                if (downScaledSourceBitmap == null) {
                    synchronized (this.mLock) {
                        drawFullQualityFrame(currentWhich, rect);
                        finishRendering();
                    }
                } else {
                    synchronized (this.mLock) {
                        drawFrameOnCanvas(currentWhich, SystemClock.elapsedRealtime(), rect, downScaledSourceBitmap.mBitmap, downScaledSourceBitmap.mScale, 2);
                    }
                    this.mLastDrawnState = drawState;
                    ((ExecutorImpl) ImageWallpaper.this.mLongExecutor).execute(new Runnable() { // from class: com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ImageWallpaper.CanvasEngine canvasEngine = ImageWallpaper.CanvasEngine.this;
                            int i = currentWhich;
                            Rect rect2 = rect;
                            synchronized (canvasEngine.mLock) {
                                canvasEngine.drawFullQualityFrame(i, rect2);
                                canvasEngine.finishRendering();
                            }
                        }
                    });
                }
            }
        }

        public final void onSwitchDisplayChanged(boolean z) {
            Log.d(this.TAG, "onSwitchDisplayChanged");
            int i = 1;
            if (!this.mIsEngineAlive) {
                return;
            }
            semSetFixedOrientation(isFixedOrientationWallpaper(getDisplayId(), getCurrentUserId()), false);
            if (z) {
                ImageWallpaper.this.mWorker.getThreadHandler().postDelayed(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda6(this, z, i), 200L);
            } else {
                updateOnSwitchDisplayChanged(z);
            }
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onVisibilityChanged(boolean z) {
            String str = this.TAG;
            StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m(" onVisibilityChanged: visible=", z, " , displayId=");
            m49m.append(getDisplayId());
            Log.i(str, m49m.toString());
        }

        public void recomputeColorExtractorMiniBitmap() {
            this.mHelper.useWallpaperBitmap(this.mHelper.getCurrentWhich(), new ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda0(this, 0));
        }

        public final void refreshCachedWallpaper(int i) {
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && (i & 2) == 0) {
                if ((i & 4) != 0) {
                    i &= -5;
                }
                WallpaperUtils.clearCachedWallpaper(i);
            }
        }

        public final void removeLocalColorsAreas(List list) {
            WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.mWallpaperLocalColorExtractor;
            wallpaperLocalColorExtractor.getClass();
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
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("setCurrentUserId: userId = ", i, this.TAG);
            if (!this.mIsEngineAlive) {
                Log.d(this.TAG, "setCurrentUserId: already destroyed");
            } else {
                this.mHelper.mCurrentUserId = i;
            }
        }

        public final void setWallpaperOffsetToDefault() {
            IBinder windowTokenAsBinder = getWindowTokenAsBinder();
            if (windowTokenAsBinder != null) {
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                imageWallpaperCanvasHelper.getClass();
                String str = imageWallpaperCanvasHelper.TAG;
                Log.i(str, "setWallpaperOffsetToDefault : " + windowTokenAsBinder);
                if (imageWallpaperCanvasHelper.mDisplayId == 2 || (LsRune.WALLPAPER_VIRTUAL_DISPLAY && imageWallpaperCanvasHelper.mIsVirtualDisplay)) {
                    TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder(" ignore updateWallpaperOffset "), imageWallpaperCanvasHelper.mDisplayId, str);
                } else {
                    imageWallpaperCanvasHelper.mWallpaperManager.setDisplayOffset(windowTokenAsBinder, 0, 0);
                    imageWallpaperCanvasHelper.mSmartCropYOffset = 0;
                }
            }
        }

        public final boolean shouldWaitForEngineShown() {
            return true;
        }

        public final boolean shouldZoomOutWallpaper() {
            return false;
        }

        public final boolean supportsLocalColorExtraction() {
            return true;
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
                    StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m(" onFolderStateChanged: isFolded=", z, ", WallMgr=");
                    m49m.append(ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mWallpaperManager.getLidState()));
                    m49m.append(", mLidState=");
                    m49m.append(ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mLidState));
                    String sb = m49m.toString();
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
                if (this.mWallpaperManager.getWallpaperInfo(currentWhich) != null) {
                    ((WallpaperLoggerImpl) ImageWallpaper.this.mLogger).log(this.TAG, "updateOnSwitchDisplayChanged: live wallpaper showing. Reset offset.");
                    setWallpaperOffsetToDefault();
                    return;
                }
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
                getSurfaceHolder();
                int max = Math.max(128, reportSurfaceSize.getWidth());
                int max2 = Math.max(128, reportSurfaceSize.getHeight());
                String str2 = this.TAG;
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("updateOnSwitchDisplayChanged: change surface size. which=", currentWhich, ", width=", max, ", height=");
                m45m.append(max2);
                Log.i(str2, m45m.toString());
                getSurfaceHolder().setFixedSize(max, max2);
            }
        }

        public final void updateRendering(int i) {
            try {
                Rect rect = new Rect(this.mSurfaceHolder.getSurfaceFrame());
                synchronized (this.mLock) {
                    drawFullQualityFrame(i, rect);
                    finishRendering();
                }
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
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m(" updateSurfaceSize: width = ", max, ", height = ", max2, ", isVisible = ");
            m45m.append(isVisible());
            Log.i(str, m45m.toString());
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

        public final void updateWallpaperOffset(int i) {
            boolean z;
            WallpaperManager wallpaperManager;
            String str;
            String str2;
            int min;
            int i2;
            int i3;
            WallpaperInfo wallpaperInfo = this.mWallpaperManager.getWallpaperInfo(i);
            if (!(!LsRune.WALLPAPER_SUB_DISPLAY_MODE || ((!WhichChecker.isSubDisplay(i) || this.mHelper.mLidState == 0) && (WhichChecker.isSubDisplay(i) || this.mHelper.mLidState == 1))) || wallpaperInfo != null) {
                setWallpaperOffsetToDefault();
                return;
            }
            if (!this.mHelper.hasIntelligentCropHints(i)) {
                Rect surfaceFrame = getSurfaceHolder().getSurfaceFrame();
                Rect rect = this.mHelper.mDimensions;
                if (surfaceFrame == null || rect == null) {
                    z = false;
                } else {
                    z = Math.min(surfaceFrame.width(), surfaceFrame.height()) == Math.min(rect.width(), rect.height());
                    if (!z) {
                        Log.d(this.TAG, "isValidSurfaceSize() surface=" + surfaceFrame + " , bitmap=" + rect);
                    }
                }
                if (z) {
                    int i4 = this.mRotation;
                    IBinder windowTokenAsBinder = getWindowTokenAsBinder();
                    if (windowTokenAsBinder != null) {
                        ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = this.mHelper;
                        int i5 = imageWallpaperCanvasHelper.mDisplayId;
                        String str3 = imageWallpaperCanvasHelper.TAG;
                        if (i5 == 2 || (LsRune.WALLPAPER_VIRTUAL_DISPLAY && imageWallpaperCanvasHelper.mIsVirtualDisplay)) {
                            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder(" ignore updateWallpaperOffset "), imageWallpaperCanvasHelper.mDisplayId, str3);
                            return;
                        }
                        WallpaperManager wallpaperManager2 = imageWallpaperCanvasHelper.mWallpaperManager;
                        if (wallpaperManager2 != null) {
                            int i6 = imageWallpaperCanvasHelper.mSmartCropYOffset;
                            int semGetWallpaperType = wallpaperManager2.semGetWallpaperType(i);
                            StringBuilder sb = new StringBuilder();
                            sb.append("updateWallpaperOffset " + imageWallpaperCanvasHelper.mDisplayId);
                            sb.append(" lastCropOffset " + i6 + " , wp Type " + semGetWallpaperType + " , rotation " + i4 + " , allowed " + imageWallpaperCanvasHelper.mIsSmartCropAllowed);
                            ImageSmartCropper imageSmartCropper = imageWallpaperCanvasHelper.mImageSmartCropper;
                            if (imageSmartCropper != null && imageWallpaperCanvasHelper.mIsSmartCropAllowed && imageSmartCropper.needToSmartCrop() && ((i4 == 1 || i4 == 3) && semGetWallpaperType == 0 && !imageWallpaperCanvasHelper.hasIntelligentCropHints(i))) {
                                if (WallpaperUtils.getCachedSmartCroppedRect(i) == null) {
                                    sb.append(" Error Smart rect is Null " + imageWallpaperCanvasHelper.mSmartCropYOffset);
                                    i3 = 0;
                                    wallpaperManager = wallpaperManager2;
                                    str = str3;
                                } else {
                                    Context context = imageWallpaperCanvasHelper.mContext;
                                    Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(imageWallpaperCanvasHelper.mDisplayId);
                                    if (display != null) {
                                        DisplayInfo displayInfo = new DisplayInfo();
                                        display.getDisplayInfo(displayInfo);
                                        i2 = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                        min = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                    } else {
                                        Log.e(str3, " getDisplaySize use configuration to recognize the screen size");
                                        Rect bounds = context.getResources().getConfiguration().windowConfiguration.getBounds();
                                        int width = bounds.width();
                                        int height = bounds.height();
                                        int max = Math.max(width, height);
                                        min = Math.min(width, height);
                                        i2 = max;
                                    }
                                    Size size = new Size(i2, min);
                                    int width2 = size.getWidth();
                                    int height2 = size.getHeight();
                                    Rect rect2 = imageWallpaperCanvasHelper.mSurfaceSize;
                                    float f = height2;
                                    float height3 = rect2.height() * Math.max(width2 / rect2.width(), f / rect2.height());
                                    float height4 = r2.top / imageWallpaperCanvasHelper.mDimensions.height();
                                    float f2 = height4 * height3;
                                    float f3 = (f * 0.5f) + f2;
                                    float f4 = imageWallpaperCanvasHelper.mYOffset;
                                    int i7 = (int) (height3 * f4);
                                    str = str3;
                                    wallpaperManager = wallpaperManager2;
                                    sb.append(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m(", screenSize=(", width2, ", ", height2, ")"));
                                    sb.append(", origTopPos " + height4 + " , calcTopPos " + f2);
                                    sb.append(", scaledHeight " + height3 + " , " + i7 + " , " + f4 + " , smartCropCenterY " + f3);
                                    i3 = (int) (((float) i7) - f3);
                                }
                                imageWallpaperCanvasHelper.mSmartCropYOffset = i3;
                            } else {
                                wallpaperManager = wallpaperManager2;
                                str = str3;
                                imageWallpaperCanvasHelper.mSmartCropYOffset = 0;
                            }
                            if (i6 == imageWallpaperCanvasHelper.mSmartCropYOffset) {
                                StringBuilder sb2 = new StringBuilder(" Do not change Display offset ");
                                AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb2, imageWallpaperCanvasHelper.mSmartCropYOffset, " , which = ", i, " , lidstate = ");
                                sb2.append(imageWallpaperCanvasHelper.mLidState);
                                sb.append(sb2.toString());
                            } else {
                                StringBuilder sb3 = new StringBuilder(" : Set Display offset ");
                                AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb3, imageWallpaperCanvasHelper.mSmartCropYOffset, " , which = ", i, ", lidstate = ");
                                sb3.append(imageWallpaperCanvasHelper.mLidState);
                                sb3.append(", token = ");
                                sb3.append(windowTokenAsBinder);
                                sb.append(sb3.toString());
                                try {
                                    wallpaperManager.setDisplayOffset(windowTokenAsBinder, 0, imageWallpaperCanvasHelper.mSmartCropYOffset);
                                } catch (IllegalArgumentException e) {
                                    str2 = str;
                                    Log.i(str2, " Wallpaper window proxy does not exist. " + e.getMessage());
                                }
                            }
                            str2 = str;
                            ((WallpaperLoggerImpl) imageWallpaperCanvasHelper.mLoggerWrapper).log(str2, sb.toString());
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            setWallpaperOffsetToDefault();
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }
    }
}
