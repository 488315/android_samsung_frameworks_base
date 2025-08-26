package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.material3.internal.colorUtil.Frame$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.effect.ColorDecorFilterHelper;
import com.android.systemui.wallpaper.engines.WallpaperAnimator;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.engines.image.DownScaledSourceBitmapManager;
import com.android.systemui.wallpaper.engines.image.ImageEngine;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.glwallpaper.ImageDarkModeFilter;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import com.samsung.android.wallpaper.live.sdk.utils.GraphicsUtils;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ImageEngine extends WallpaperEngine {
    public String TAG;
    public final Paint mBitmapPaint;
    public int mBitmapUsages;
    public final DownScaledSourceBitmapManager mDownScaledSourceBitmapManager;
    public final DozeParameters mDozeParameters;
    public boolean mIsEngineAlive;
    public boolean mIsFullQualityFrameDrawn;
    public boolean mIsWallpaperSizeWarningEnabled;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public DrawState mLastDrawnState;
    public final Object mLock;
    public final WallpaperLogger mLogger;
    public final DelayableExecutor mLongExecutor;
    private final SettingsHelper mSettingsHelper;
    public SurfaceHolder mSurfaceHolder;
    public final Object mSurfaceLock;
    public final SystemWallpaperColors mSystemWallpaperColors;
    public final WallpaperAnimator mWallpaperAnimator;
    public final WallpaperManager mWallpaperManager;
    public final ImageSource mWallpaperSource;

    public class DrawState {
        public final boolean mDarkModeFilterApplied;
        public final int mSurfaceHeight;
        public final int mSurfaceWidth;
        public final int mWhich;

        public DrawState(ImageEngine imageEngine, int i, int i2, int i3, boolean z) {
            if ((i & 60) == 0) {
                Log.e(imageEngine.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "DrawState : mode value is missing. which="), new RuntimeException());
            }
            this.mWhich = i;
            this.mSurfaceWidth = i2;
            this.mSurfaceHeight = i3;
            this.mDarkModeFilterApplied = z;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof DrawState) {
                DrawState drawState = (DrawState) obj;
                if (this.mWhich == drawState.mWhich && this.mSurfaceWidth == drawState.mSurfaceWidth && this.mSurfaceHeight == drawState.mSurfaceHeight && this.mDarkModeFilterApplied == drawState.mDarkModeFilterApplied) {
                    return true;
                }
            }
            return false;
        }

        public final String toString() {
            return "which=" + this.mWhich + ", " + this.mSurfaceWidth + "x" + this.mSurfaceHeight + ", darkMode=" + this.mDarkModeFilterApplied;
        }
    }

    public ImageEngine(ImageSource imageSource, WallpaperEngineCallback wallpaperEngineCallback, SettingsHelper settingsHelper, SystemWallpaperColors systemWallpaperColors, DelayableExecutor delayableExecutor, KeyguardWallpaper keyguardWallpaper, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeParameters dozeParameters) {
        super(wallpaperEngineCallback);
        this.mBitmapPaint = new Paint(2);
        this.mIsEngineAlive = false;
        this.mIsFullQualityFrameDrawn = false;
        this.mIsWallpaperSizeWarningEnabled = false;
        this.mBitmapUsages = 0;
        this.mLock = new Object();
        this.mSurfaceLock = new Object();
        this.TAG = "ImageWallpaper_" + getWhich() + "[Image]";
        WallpaperEngineCallback wallpaperEngineCallback2 = this.mCallback;
        this.mLogger = ImageWallpaper.this.mLogger;
        this.mLongExecutor = delayableExecutor;
        this.mDozeParameters = dozeParameters;
        this.mSettingsHelper = settingsHelper;
        this.mWallpaperSource = imageSource;
        this.mWallpaperManager = ImageWallpaper.IntegratedEngine.this.mWallpaperManager;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mSystemWallpaperColors = systemWallpaperColors;
        this.mDownScaledSourceBitmapManager = new DownScaledSourceBitmapManager();
        if (imageSource.isMultipack()) {
            return;
        }
        this.mWallpaperAnimator = new WallpaperAnimator(getSurfaceHolder(), ((ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback).getSurfaceControl(), keyguardUpdateMonitor);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final boolean draw(SurfaceHolder surfaceHolder) {
        onSurfaceRedrawNeeded(surfaceHolder);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x01e5 A[Catch: all -> 0x01ba, DONT_GENERATE, TRY_LEAVE, TryCatch #6 {all -> 0x01ba, blocks: (B:64:0x01a0, B:66:0x01a6, B:68:0x01b4, B:83:0x01d6, B:85:0x01e1, B:95:0x020a, B:87:0x01e5, B:117:0x025f, B:118:0x026d, B:111:0x024c, B:113:0x0257, B:116:0x025e, B:115:0x025b, B:90:0x01fa, B:92:0x0205, B:72:0x01bd, B:119:0x026e, B:120:0x0275, B:75:0x01c4, B:77:0x01cd, B:89:0x01e9), top: B:142:0x01a0, outer: #4, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final DrawState drawFrameOnCanvas(Canvas canvas, long j, Rect rect, Bitmap bitmap, ArrayList arrayList, float f) {
        long j2;
        long jElapsedRealtime;
        long jElapsedRealtime2;
        DrawState drawState;
        Rect rect2;
        Matrix matrix;
        int iWidth;
        int iHeight;
        float f2;
        float f3;
        Bitmap bitmapCreateFilteredBitmap;
        Integer dimFilterColor;
        if (!WallpaperUtils.isValidBitmap(bitmap)) {
            return null;
        }
        if (canvas == null && !isSurfaceCreated()) {
            Log.e(this.TAG, "drawFrameOnCanvas: the surface holder is invalid");
            return null;
        }
        int sourceWhich = getSourceWhich();
        long jElapsedRealtime3 = SystemClock.elapsedRealtime();
        try {
            synchronized (this.mSurfaceLock) {
                try {
                    try {
                        rect2 = canvas != null ? new Rect(0, 0, canvas.getWidth(), canvas.getHeight()) : this.mSurfaceHolder.getSurfaceFrame();
                    } finally {
                        th = th;
                        while (true) {
                            try {
                            } catch (Throwable th) {
                                th = th;
                            }
                        }
                    }
                } catch (Exception e) {
                    e = e;
                    jElapsedRealtime = j2;
                    jElapsedRealtime2 = jElapsedRealtime;
                    WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : failed draw bitmap. e=", e, this.TAG, e);
                    drawState = null;
                    long jElapsedRealtime4 = SystemClock.elapsedRealtime() - j;
                    WallpaperLogger wallpaperLogger = this.mLogger;
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
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, sbM.toString());
                    return drawState;
                }
            }
            Rect nearestCropHint = IntelligentCropHelper.getNearestCropHint(new Point(rect2.width(), rect2.height()), arrayList);
            if (nearestCropHint != null) {
                j2 = jElapsedRealtime3;
                Rect rect3 = new Rect((int) (nearestCropHint.left * f), (int) (nearestCropHint.top * f), (int) (nearestCropHint.right * f), (int) (nearestCropHint.bottom * f));
                rect3.intersect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                nearestCropHint = rect3;
            } else {
                j2 = jElapsedRealtime3;
            }
            matrix = new Matrix();
            int iWidth2 = nearestCropHint != null ? nearestCropHint.width() : bitmap.getWidth();
            int iHeight2 = nearestCropHint != null ? nearestCropHint.height() : bitmap.getHeight();
            iWidth = rect2.width();
            iHeight = rect2.height();
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
            String filterData = this.mWallpaperSource.getSupplier().getFilterData();
            bitmapCreateFilteredBitmap = !TextUtils.isEmpty(filterData) ? ColorDecorFilterHelper.createFilteredBitmap(filterData, bitmap) : bitmap;
            dimFilterColor = getDimFilterColor(sourceWhich);
            if (dimFilterColor != null) {
                this.mBitmapPaint.setColorFilter(new PorterDuffColorFilter(dimFilterColor.intValue(), PorterDuff.Mode.SRC_OVER));
            } else {
                this.mBitmapPaint.setColorFilter(null);
            }
            jElapsedRealtime2 = SystemClock.elapsedRealtime();
            try {
                WallpaperLogger wallpaperLogger2 = this.mLogger;
                String str2 = this.TAG;
                StringBuilder sb = new StringBuilder("drawFrameOnCanvas : which=");
                sb.append(sourceWhich);
                sb.append(", bmpW=");
                sb.append(bitmap.getWidth());
                sb.append(", bmpH=");
                sb.append(bitmap.getHeight());
                sb.append(", bmpScale=");
                sb.append(f);
                sb.append(", src=");
                sb.append(nearestCropHint);
                sb.append(", dest=");
                sb.append(rect2);
                sb.append(", dimColor=");
                sb.append(dimFilterColor);
                sb.append(", customCanvas=");
                sb.append(canvas != null);
                ((WallpaperLoggerImpl) wallpaperLogger2).log(str2, sb.toString());
            } catch (Exception e2) {
                e = e2;
                jElapsedRealtime = j2;
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : failed draw bitmap. e=", e, this.TAG, e);
                drawState = null;
                long jElapsedRealtime42 = SystemClock.elapsedRealtime() - j;
                WallpaperLogger wallpaperLogger3 = this.mLogger;
                String str3 = this.TAG;
                StringBuilder sbM2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", jElapsedRealtime42, ", bmpPrepareDur=");
                sbM2.append(j2 - j);
                sbM2.append(", filterApplyDur=");
                sbM2.append(jElapsedRealtime2 - j2);
                sbM2.append(", drawDur=");
                sbM2.append(jElapsedRealtime - jElapsedRealtime2);
                sbM2.append(", drawnState=(");
                sbM2.append(drawState);
                sbM2.append(")");
                ((WallpaperLoggerImpl) wallpaperLogger3).log(str3, sbM2.toString());
                return drawState;
            }
        } catch (Exception e3) {
            e = e3;
            j2 = jElapsedRealtime3;
        }
        if (bitmapCreateFilteredBitmap.isRecycled()) {
            Log.e(this.TAG, "drawFrameOnCanvas : bitmap is recycled!");
            throw new RuntimeException("tried to draw with recycled bitmap");
        }
        if (canvas == null) {
            synchronized (this.mSurfaceLock) {
                try {
                    if (!isSurfaceCreated()) {
                        throw new RuntimeException("incorrect surface");
                    }
                    Surface surface = this.mSurfaceHolder.getSurface();
                    Canvas canvasLockHardwareWideColorGamutCanvas = this.mWallpaperManager.wallpaperSupportsWcg(bitmap) ? surface.lockHardwareWideColorGamutCanvas() : surface.lockHardwareCanvas();
                    try {
                        if (canvasLockHardwareWideColorGamutCanvas == null) {
                            Log.e(this.TAG, "drawFrameOnCanvas: canvas is NULL");
                            throw new RuntimeException("failed to lock the canvas");
                        }
                        try {
                            canvasLockHardwareWideColorGamutCanvas.drawBitmap(bitmapCreateFilteredBitmap, matrix, this.mBitmapPaint);
                            if (this.mIsWallpaperSizeWarningEnabled) {
                                drawWarningTextIfNeeded(sourceWhich, canvasLockHardwareWideColorGamutCanvas, bitmap);
                            }
                        } catch (Exception e4) {
                            Log.e(this.TAG, "drawFrameOnCanvas: e = " + e4, e4);
                            surface.unlockCanvasAndPost(canvasLockHardwareWideColorGamutCanvas);
                            if (!this.mWallpaperSource.isMultipack() || !LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
                            }
                        }
                        long jElapsedRealtime422 = SystemClock.elapsedRealtime() - j;
                        WallpaperLogger wallpaperLogger32 = this.mLogger;
                        String str32 = this.TAG;
                        StringBuilder sbM22 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", jElapsedRealtime422, ", bmpPrepareDur=");
                        sbM22.append(j2 - j);
                        sbM22.append(", filterApplyDur=");
                        sbM22.append(jElapsedRealtime2 - j2);
                        sbM22.append(", drawDur=");
                        sbM22.append(jElapsedRealtime - jElapsedRealtime2);
                        sbM22.append(", drawnState=(");
                        sbM22.append(drawState);
                        sbM22.append(")");
                        ((WallpaperLoggerImpl) wallpaperLogger32).log(str32, sbM22.toString());
                        return drawState;
                    } finally {
                        surface.unlockCanvasAndPost(canvasLockHardwareWideColorGamutCanvas);
                        if (!this.mWallpaperSource.isMultipack() || !LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
                            surface.hwuiDestroy();
                        }
                    }
                } finally {
                }
            }
        }
        canvas.drawBitmap(bitmapCreateFilteredBitmap, matrix, this.mBitmapPaint);
        if (bitmapCreateFilteredBitmap != bitmap) {
            bitmapCreateFilteredBitmap.recycle();
        }
        jElapsedRealtime = SystemClock.elapsedRealtime();
        if (canvas == null) {
            try {
            } catch (Exception e5) {
                e = e5;
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : failed draw bitmap. e=", e, this.TAG, e);
                drawState = null;
                long jElapsedRealtime4222 = SystemClock.elapsedRealtime() - j;
                WallpaperLogger wallpaperLogger322 = this.mLogger;
                String str322 = this.TAG;
                StringBuilder sbM222 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", jElapsedRealtime4222, ", bmpPrepareDur=");
                sbM222.append(j2 - j);
                sbM222.append(", filterApplyDur=");
                sbM222.append(jElapsedRealtime2 - j2);
                sbM222.append(", drawDur=");
                sbM222.append(jElapsedRealtime - jElapsedRealtime2);
                sbM222.append(", drawnState=(");
                sbM222.append(drawState);
                sbM222.append(")");
                ((WallpaperLoggerImpl) wallpaperLogger322).log(str322, sbM222.toString());
                return drawState;
            }
            if (rect2.equals(rect)) {
                drawState = new DrawState(this, sourceWhich, iWidth, iHeight, dimFilterColor != null);
            } else {
                Log.w(this.TAG, "drawFrameOnCanvas : surface size mismatch. curFrame=" + rect2 + ", requestedFrame=" + rect);
                drawState = null;
            }
        } else {
            drawState = null;
        }
        long jElapsedRealtime42222 = SystemClock.elapsedRealtime() - j;
        WallpaperLogger wallpaperLogger3222 = this.mLogger;
        String str3222 = this.TAG;
        StringBuilder sbM2222 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", jElapsedRealtime42222, ", bmpPrepareDur=");
        sbM2222.append(j2 - j);
        sbM2222.append(", filterApplyDur=");
        sbM2222.append(jElapsedRealtime2 - j2);
        sbM2222.append(", drawDur=");
        sbM2222.append(jElapsedRealtime - jElapsedRealtime2);
        sbM2222.append(", drawnState=(");
        sbM2222.append(drawState);
        sbM2222.append(")");
        ((WallpaperLoggerImpl) wallpaperLogger3222).log(str3222, sbM2222.toString());
        return drawState;
    }

    public final void drawFrameSynchronized(Rect rect) {
        synchronized (this.mLock) {
            drawFullQualityFrame(null, rect);
            Settings.System.putLong(getAppContext().getContentResolver(), "wallpaper_finish_drawing", System.currentTimeMillis());
            Trace.beginSection("ImageWallpaper#finishRendering");
            Log.i(this.TAG, "finishRendering");
            Trace.endSection();
        }
    }

    public final void drawFullQualityFrame(final Canvas canvas, final Rect rect) {
        if (canvas == null) {
            if (!isSurfaceCreated()) {
                Log.e(this.TAG, "drawFullQualityFrame: attempt to draw a frame without a valid surface");
                return;
            } else if (!this.mIsEngineAlive) {
                Log.d(this.TAG, "drawFullQualityFrame: engine is destroyed");
                return;
            }
        }
        Trace.beginSection("ImageWallpaper.ImageEngine#drawFrame");
        final int sourceWhich = getSourceWhich();
        final long jElapsedRealtime = SystemClock.elapsedRealtime();
        this.mWallpaperSource.useBitmap(new Consumer() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws NoSuchMethodException, SecurityException {
                ImageEngine imageEngine = this.f$0;
                int i = sourceWhich;
                Canvas canvas2 = canvas;
                long j = jElapsedRealtime;
                Rect rect2 = rect;
                ImageSource.WallpaperImage wallpaperImage = (ImageSource.WallpaperImage) obj;
                imageEngine.getClass();
                Bitmap bitmap = wallpaperImage.mBitmap;
                DownScaledSourceBitmapManager downScaledSourceBitmapManager = imageEngine.mDownScaledSourceBitmapManager;
                if (bitmap == null) {
                    downScaledSourceBitmapManager.mSourceBitmapSet.remove(Integer.valueOf(WhichChecker.getSourceWhich(i)));
                    imageEngine.mLastDrawnState = null;
                    return;
                }
                if (((DownScaledSourceBitmapManager.Item) downScaledSourceBitmapManager.mSourceBitmapSet.get(Integer.valueOf(WhichChecker.getSourceWhich(i)))) == null) {
                    imageEngine.putDownScaledSourceBitmap(i, wallpaperImage);
                }
                ImageEngine.DrawState drawStateDrawFrameOnCanvas = imageEngine.drawFrameOnCanvas(canvas2, j, rect2, bitmap, wallpaperImage.mCropRects, 1.0f);
                if (canvas2 == null) {
                    imageEngine.mLastDrawnState = drawStateDrawFrameOnCanvas;
                }
                imageEngine.mIsFullQualityFrameDrawn = true;
            }
        });
        Trace.endSection();
        ImageWallpaper.IntegratedEngine.this.reportEngineShown(false);
        int i = this.mBitmapUsages - 1;
        this.mBitmapUsages = i;
        if (i <= 0) {
            this.mBitmapUsages = 0;
            Trace.beginSection("ImageWallpaper.ImageEngine#unloadBitmap");
            this.mWallpaperManager.forgetLoadedWallpaper();
            Trace.endSection();
        }
    }

    public final void drawWarningTextIfNeeded(int i, Canvas canvas, Bitmap bitmap) throws NoSuchMethodException, SecurityException {
        Point displaySize;
        Context appContext = getAppContext();
        int sourceWhich = getSourceWhich();
        int displayIdByWhich = DisplayUtils.getDisplayIdByWhich(sourceWhich, appContext);
        Display display = ((DisplayManager) appContext.getSystemService("display")).getDisplay(displayIdByWhich);
        int iMax = 0;
        if (display == null) {
            Log.e("CommonUtils", "getLongLengthOfMaxResolution: failed to get display. which=" + sourceWhich + ", displayId=" + displayIdByWhich);
        } else {
            int i2 = 0;
            for (Display.Mode mode : display.getSupportedModes()) {
                int iMax2 = Math.max(mode.getPhysicalWidth(), mode.getPhysicalHeight());
                if (iMax2 > i2) {
                    i2 = iMax2;
                }
            }
            iMax = (i2 != 0 || (displaySize = DisplayUtils.getDisplaySize(appContext, sourceWhich, 0)) == null) ? i2 : Math.max(displaySize.x, displaySize.y);
        }
        if (WhichChecker.isFlagEnabled(i, 8)) {
            iMax = Math.max(4096, iMax);
        }
        int i3 = (int) (iMax * 1.1f);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (Math.max(width, height) <= i3 || iMax == 0) {
            return;
        }
        float fCenterX = this.mSurfaceHolder.getSurfaceFrame().centerX();
        float fCenterY = this.mSurfaceHolder.getSurfaceFrame().centerY();
        Paint paint = new Paint();
        paint.setColor(-16777216);
        Path path = new Path();
        path.addRect(new RectF(0.0f, fCenterY - 100.0f, this.mSurfaceHolder.getSurfaceFrame().width(), 100.0f + fCenterY), Path.Direction.CCW);
        paint.setStrokeWidth(20.0f);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        paint.setStrokeWidth(0.0f);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30.0f);
        paint.setColor(-65536);
        String str = "This wallpaper isn't supported size : [" + width + "x" + height + "]";
        canvas.drawText(str, fCenterX, fCenterY, paint);
        paint.setColor(-1);
        StringBuilder sb = new StringBuilder("Maximum supported size : [");
        sb.append(iMax);
        sb.append("x");
        canvas.drawText(ReorderTile$$ExternalSyntheticOutline0.m(iMax, "]", sb), fCenterX, fCenterY + 50.0f, paint);
        Log.e(this.TAG, "drawWarningTextIfNeeded: " + str);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("Engine=");
        printWriter.println(this);
        printWriter.print(str);
        printWriter.print("valid surface=");
        printWriter.println((getSurfaceHolder() == null || getSurfaceHolder().getSurface() == null) ? "null" : Boolean.valueOf(getSurfaceHolder().getSurface().isValid()));
        printWriter.print(str);
        printWriter.print("surface frame=");
        printWriter.println(getSurfaceHolder() != null ? getSurfaceHolder().getSurfaceFrame() : "null");
        printWriter.print(str);
        printWriter.print("bitmap=");
    }

    public final DrawState estimateDrawStateToDraw(int i) {
        synchronized (this.mSurfaceLock) {
            try {
                if (!isSurfaceCreated()) {
                    return null;
                }
                Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
                return new DrawState(this, i, surfaceFrame.width(), surfaceFrame.height(), getDimFilterColor(i) != null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Integer getDimFilterColor(int i) {
        float[] wallpaperFilterColor = ImageDarkModeFilter.getWallpaperFilterColor(getAppContext(), WhichChecker.isFlagEnabled(i, 1) ? this.mSystemWallpaperColors.getColor(i) : (!LsRune.WALLPAPER_SUB_DISPLAY_MODE || LsRune.WALLPAPER_SUB_WATCHFACE) ? ((KeyguardWallpaperController) this.mKeyguardWallpaper).getHints() : WallpaperUtils.getCachedSemWallpaperColors(WhichChecker.isFlagEnabled(i, 16)));
        if (wallpaperFilterColor == null) {
            return null;
        }
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && WhichChecker.isFlagEnabled(getSourceWhich(), 16)) {
            return null;
        }
        return Integer.valueOf(Color.argb(wallpaperFilterColor[3], wallpaperFilterColor[0], wallpaperFilterColor[1], wallpaperFilterColor[2]));
    }

    public final boolean isSurfaceCreated() {
        boolean z;
        synchronized (this.mSurfaceLock) {
            z = this.mSurfaceHolder != null;
        }
        return z;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onApplyDarkModeDimSettingChanged() {
        Runnable runnable = new Runnable() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                boolean z;
                ImageEngine imageEngine = this.f$0;
                if (imageEngine.mIsEngineAlive) {
                    int sourceWhich = imageEngine.getSourceWhich();
                    ImageEngine.DrawState drawStateEstimateDrawStateToDraw = imageEngine.estimateDrawStateToDraw(sourceWhich);
                    synchronized (imageEngine.mLock) {
                        try {
                            ImageEngine.DrawState drawState = imageEngine.mLastDrawnState;
                            z = drawState == null || !drawState.equals(drawStateEstimateDrawStateToDraw);
                            if (z) {
                                Log.i(imageEngine.TAG, "isFrameDrawNeeded: draw needed. which=" + sourceWhich + ", lastDrawn=(" + imageEngine.mLastDrawnState + "), toDraw=(" + drawStateEstimateDrawStateToDraw + ")");
                            }
                        } finally {
                        }
                    }
                    ((WallpaperLoggerImpl) imageEngine.mLogger).log(imageEngine.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("onConfigurationChanged: redrawNeeded=", z));
                    if (z) {
                        synchronized (imageEngine.mSurfaceLock) {
                            try {
                                if (imageEngine.isSurfaceCreated()) {
                                    imageEngine.drawFrameSynchronized(new Rect(imageEngine.mSurfaceHolder.getSurfaceFrame()));
                                } else {
                                    Log.i(imageEngine.TAG, "drawFrameSynchronized: the surface holder is invalid");
                                }
                            } finally {
                            }
                        }
                    }
                }
            }
        };
        Handler threadHandler = ImageWallpaper.this.mWorker.getThreadHandler();
        if (threadHandler == null) {
            Log.w(this.TAG, "runAsWorkerThread: worker handler is null.");
        } else {
            threadHandler.post(runnable);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        Log.i(this.TAG, "onCommand: action = " + str);
        boolean zEquals = TextUtils.equals(str, "samsung.android.wallpaper.resume");
        WallpaperAnimator wallpaperAnimator = this.mWallpaperAnimator;
        if (zEquals && wallpaperAnimator != null && WhichChecker.isFlagEnabled(getWhich(), 2)) {
            Log.d(wallpaperAnimator.TAG, "changeToDownScaleImmediately");
            wallpaperAnimator.release();
            wallpaperAnimator.onTransitionScaleChanged(1.0f);
        } else if (TextUtils.equals(str, "android.wallpaper.keyguardgoingaway") && wallpaperAnimator != null && WhichChecker.isFlagEnabled(getWhich(), 2)) {
            wallpaperAnimator.mKeyguardState = false;
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(final SurfaceHolder surfaceHolder) {
        int iSemGetWallpaperType;
        String str = this.TAG;
        StringBuilder sb = new StringBuilder("Engine onCreate: which=");
        sb.append(getWhich());
        sb.append(", displayId=");
        WallpaperEngineCallback wallpaperEngineCallback = this.mCallback;
        sb.append(ImageWallpaper.IntegratedEngine.this.getDisplayId());
        String string = sb.toString();
        WallpaperLoggerImpl wallpaperLoggerImpl = (WallpaperLoggerImpl) this.mLogger;
        wallpaperLoggerImpl.log(str, string);
        Trace.beginSection("ImageWallpaper.ImageEngine#onCreate");
        boolean zIsValid = surfaceHolder.getSurface().isValid();
        boolean zIsPreview = ImageWallpaper.IntegratedEngine.this.isPreview();
        SettingsHelper settingsHelper = this.mSettingsHelper;
        ImageSource imageSource = this.mWallpaperSource;
        ImageWallpaper.IntegratedEngine.this.semSetFixedOrientation(imageSource.isFixedOrientation(zIsPreview, settingsHelper), zIsValid);
        if (imageSource.getSupplier().supportWallpaperScrolling()) {
            wallpaperLoggerImpl.log(this.TAG, "onCreate: require fixed size surface");
            imageSource.useBitmap(new Consumer() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws NoSuchMethodException, SecurityException {
                    ImageEngine imageEngine = this.f$0;
                    SurfaceHolder surfaceHolder2 = surfaceHolder;
                    ImageSource.WallpaperImage wallpaperImage = (ImageSource.WallpaperImage) obj;
                    imageEngine.getClass();
                    Bitmap bitmap = wallpaperImage.mBitmap;
                    if (bitmap != null) {
                        surfaceHolder2.setFixedSize(bitmap.getWidth(), bitmap.getHeight());
                        imageEngine.putDownScaledSourceBitmap(imageEngine.getSourceWhich(), wallpaperImage);
                    }
                }
            });
        }
        this.mIsEngineAlive = true;
        int sourceWhich = getSourceWhich();
        boolean zSemIsPreloadedWallpaper = false;
        if (!DeviceType.isShipBuild() && Build.VERSION.SEM_FIRST_SDK_INT >= 35 && !DeviceType.isFactoryBinary() && (iSemGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(sourceWhich)) != 3 && iSemGetWallpaperType != 1000) {
            Bundle wallpaperExtras = imageSource.mWallpaperManager.getWallpaperExtras(imageSource.mWhich, imageSource.mUserId);
            String string2 = wallpaperExtras == null ? null : wallpaperExtras.getString("imageCategory");
            Log.e(this.TAG, "isWallpaperSizeWarningEnabled: " + string2);
            if (!"Graphical".equals(string2) && !"Colors".equals(string2)) {
                zSemIsPreloadedWallpaper = this.mWallpaperManager.semIsPreloadedWallpaper(sourceWhich, getCurrentUserId());
            }
        }
        this.mIsWallpaperSizeWarningEnabled = zSemIsPreloadedWallpaper;
        Trace.endSection();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
        ((WallpaperLoggerImpl) this.mLogger).log(this.TAG, "Engine onDestroy: which=" + getWhich() + ", displayId=" + ImageWallpaper.IntegratedEngine.this.getDisplayId());
        WallpaperAnimator wallpaperAnimator = this.mWallpaperAnimator;
        if (wallpaperAnimator != null) {
            wallpaperAnimator.release();
        }
        this.mIsEngineAlive = false;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDisplayStateChanged(DisplayState displayState, DisplayState displayState2) {
        Log.i(this.TAG, "onDisplayStateChanged: " + displayState2 + " -> " + displayState);
        Context appContext = getAppContext();
        int currentUserId = getCurrentUserId();
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        boolean zIsLockScreenDisabled = new LockPatternUtils(appContext).isLockScreenDisabled(currentUserId);
        if (this.mWallpaperAnimator == null || !WhichChecker.isFlagEnabled(getWhich(), 2) || zIsLockScreenDisabled) {
            return;
        }
        this.mWallpaperAnimator.onDisplayStateChanged(displayState, getWhich(), isVisible(), WallpaperUtils.isShowWallpaperOnAodEnabled(getWhich() & 60) && this.mDozeParameters.mControlScreenOffAnimation, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00ae A[PHI: r9
      0x00ae: PHI (r9v2 android.graphics.Point) = 
      (r9v0 android.graphics.Point)
      (r9v3 android.graphics.Point)
      (r9v4 android.graphics.Point)
      (r9v6 android.graphics.Point)
     binds: [B:24:0x0099, B:26:0x00b0, B:21:0x0089, B:16:0x0071] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ScreenshotResults onGetScreenshot(ScreenshotOptions screenshotOptions) throws NoSuchMethodException, SecurityException {
        Point point;
        Bitmap bitmapCreateBitmap;
        Log.i(this.TAG, "onGetScreenshot");
        Context appContext = getAppContext();
        int sourceWhich = getSourceWhich();
        int displayRotation = DisplayUtils.getDisplayRotation(sourceWhich, appContext);
        Point displaySize = DisplayUtils.getDisplaySize(appContext, sourceWhich, displayRotation);
        if (displaySize == null) {
            ClockEventController$$ExternalSyntheticOutline0.m(sourceWhich, "onGetScreenshot: failed to get display size. srcWhich=", this.TAG);
            return null;
        }
        boolean zIsFixedOrientation = this.mWallpaperSource.isFixedOrientation(ImageWallpaper.IntegratedEngine.this.isPreview(), this.mSettingsHelper);
        boolean zSupportWallpaperScrolling = this.mWallpaperSource.getSupplier().supportWallpaperScrolling();
        synchronized (this.mSurfaceLock) {
            try {
                if (isSurfaceCreated()) {
                    Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
                    int iWidth = surfaceFrame.width();
                    int iHeight = surfaceFrame.height();
                    if (zSupportWallpaperScrolling) {
                        Rect centerCropRect = GraphicsUtils.getCenterCropRect(iWidth, iHeight, displaySize.x, displaySize.y);
                        if (centerCropRect != null) {
                            int iWidth2 = centerCropRect.width();
                            int iHeight2 = centerCropRect.height();
                            point = new Point(iWidth2, iHeight2);
                            bitmapCreateBitmap = this.mIsFullQualityFrameDrawn ? DisplayUtils.copySurfaceToBitmapSync(this.mSurfaceHolder, centerCropRect, new Size(iWidth2, iHeight2)) : null;
                        }
                    } else {
                        point = new Point(iWidth, iHeight);
                        if (this.mIsFullQualityFrameDrawn) {
                            bitmapCreateBitmap = DisplayUtils.copySurfaceToBitmapSync(this.mSurfaceHolder, null, new Size(iWidth, iHeight));
                        }
                    }
                } else {
                    point = zIsFixedOrientation ? new Point(Math.min(displaySize.x, displaySize.y), Math.max(displaySize.x, displaySize.y)) : displaySize;
                }
                if (bitmapCreateBitmap == null && point.x > 0 && point.y > 0) {
                    if (TextUtils.equals(screenshotOptions.mPurpose, "prev")) {
                        bitmapCreateBitmap = WallpaperUtils.getScreenShot(appContext, displaySize.x, displaySize.y, displayRotation);
                    } else {
                        bitmapCreateBitmap = Bitmap.createBitmap(point.x, point.y, Bitmap.Config.ARGB_8888, false);
                        drawFullQualityFrame(new Canvas(bitmapCreateBitmap), null);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (bitmapCreateBitmap == null) {
            Log.e(this.TAG, "onGetScreenshot: failed to generate screenshot");
            return null;
        }
        if (zIsFixedOrientation) {
            Bitmap bitmapCropRotateResizeBitmap = BitmapUtils.cropRotateResizeBitmap(bitmapCreateBitmap, null, -DisplayUtils.convertDisplayRotationToAngle(displayRotation), 1.0f, true);
            if (bitmapCropRotateResizeBitmap != bitmapCreateBitmap) {
                bitmapCreateBitmap.recycle();
            }
            bitmapCreateBitmap = bitmapCropRotateResizeBitmap;
        }
        return new ScreenshotResults(bitmapCreateBitmap);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onStartedWakingUp() {
        Log.i(this.TAG, "onStartedWakingUp");
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        Log.i(this.TAG, "onSurfaceChanged: width = " + i2 + ", height = " + i3);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i(this.TAG, " onSurfaceCreated: " + surfaceHolder.getSurfaceFrame());
        this.mSurfaceHolder = surfaceHolder;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i(this.TAG, "onSurfaceDestroyed");
        synchronized (this.mSurfaceLock) {
            this.mSurfaceHolder = null;
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        if (!this.mIsEngineAlive) {
            Log.i(this.TAG, "onSurfaceRedrawNeeded: engine already destroyed");
            return;
        }
        setVisibleRectOfSurface(null);
        int sourceWhich = getSourceWhich();
        synchronized (this.mSurfaceLock) {
            try {
                if (!isSurfaceCreated()) {
                    Log.i(this.TAG, "onSurfaceRedrawNeeded: the surface holder is invalid");
                    return;
                }
                final Rect rect = new Rect(this.mSurfaceHolder.getSurfaceFrame());
                Log.i(this.TAG, "onSurfaceRedrawNeeded: srcWhich=" + sourceWhich + ", surfaceFrame=" + rect);
                DrawState drawStateEstimateDrawStateToDraw = estimateDrawStateToDraw(sourceWhich);
                synchronized (this.mLock) {
                    try {
                        Log.i(this.TAG, "onSurfaceRedrawNeeded: displayId=" + ImageWallpaper.IntegratedEngine.this.getDisplayId() + ", lastDrawn=(" + this.mLastDrawnState + "), toDraw=(" + drawStateEstimateDrawStateToDraw + ")");
                        DrawState drawState = this.mLastDrawnState;
                        if (drawState != null && drawState.equals(drawStateEstimateDrawStateToDraw)) {
                            Log.i(this.TAG, "onSurfaceRedrawNeeded: not need redraw");
                            return;
                        }
                        DrawState drawState2 = this.mLastDrawnState;
                        if (drawState2 != null && drawStateEstimateDrawStateToDraw != null && drawState2.mSurfaceWidth * drawState2.mSurfaceHeight != drawStateEstimateDrawStateToDraw.mSurfaceWidth * drawStateEstimateDrawStateToDraw.mSurfaceHeight) {
                            this.mWallpaperSource.updateSupplier(sourceWhich);
                        }
                        DownScaledSourceBitmapManager.Item item = (DownScaledSourceBitmapManager.Item) this.mDownScaledSourceBitmapManager.mSourceBitmapSet.get(Integer.valueOf(WhichChecker.getSourceWhich(sourceWhich)));
                        if (item == null) {
                            drawFrameSynchronized(rect);
                            return;
                        }
                        synchronized (this.mLock) {
                            drawFrameOnCanvas(null, SystemClock.elapsedRealtime(), rect, item.mBitmap, item.mCropRects, item.mScale);
                        }
                        this.mLastDrawnState = drawStateEstimateDrawStateToDraw;
                        Runnable runnable = new Runnable() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.drawFrameSynchronized(rect);
                            }
                        };
                        Handler threadHandler = ImageWallpaper.this.mWorker.getThreadHandler();
                        if (threadHandler == null) {
                            Log.w(this.TAG, "runAsWorkerThread: worker handler is null.");
                        } else {
                            threadHandler.post(runnable);
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        String str = this.TAG;
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m(" onVisibilityChanged: visible=", " , displayId=", z);
        sbM.append(ImageWallpaper.IntegratedEngine.this.getDisplayId());
        Log.i(str, sbM.toString());
        Context appContext = getAppContext();
        int currentUserId = getCurrentUserId();
        boolean z2 = WallpaperUtils.mIsExternalLiveWallpaper;
        boolean zIsLockScreenDisabled = new LockPatternUtils(appContext).isLockScreenDisabled(currentUserId);
        WallpaperAnimator wallpaperAnimator = this.mWallpaperAnimator;
        if (z && wallpaperAnimator != null && zIsLockScreenDisabled) {
            wallpaperAnimator.mKeyguardState = false;
        }
        if (wallpaperAnimator == null || !WhichChecker.isFlagEnabled(getWhich(), 2) || zIsLockScreenDisabled) {
            return;
        }
        wallpaperAnimator.onEngineVisibilityChanged(getWhich(), z, WallpaperUtils.isShowWallpaperOnAodEnabled(getWhich() & 60) && this.mDozeParameters.mControlScreenOffAnimation, false);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onWhichChanged(int i) {
        this.TAG = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[Image]");
        int sourceWhich = WhichChecker.getSourceWhich(i);
        ImageSource imageSource = this.mWallpaperSource;
        imageSource.getClass();
        imageSource.TAG = "ImageWallpaper_" + sourceWhich + "[ImageSource]";
        imageSource.mWhich = sourceWhich;
        imageSource.mWallpaperType = imageSource.mWallpaperManager.semGetWallpaperType(sourceWhich);
        imageSource.updateSupplier(sourceWhich);
    }

    public final void putDownScaledSourceBitmap(int i, ImageSource.WallpaperImage wallpaperImage) throws NoSuchMethodException, SecurityException {
        Context appContext = getAppContext();
        Bitmap bitmap = wallpaperImage.mBitmap;
        Point displaySize = DisplayUtils.getDisplaySize(appContext, i, DisplayUtils.getDisplayRotation(i, appContext));
        if (displaySize == null) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "putDownScaledSourceBitmap: failed to get display size. srcWhich=", this.TAG);
            return;
        }
        ArrayList arrayList = wallpaperImage.mCropRects;
        DownScaledSourceBitmapManager downScaledSourceBitmapManager = this.mDownScaledSourceBitmapManager;
        downScaledSourceBitmapManager.getClass();
        int iMax = Math.max(displaySize.x, displaySize.y);
        int iMin = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float fMax = ((int) Math.max(1024.0f, iMax * 0.5f)) / iMin;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(fMax, "ImageWallpaper[DownScaledSourceBitmapManager]", MutableObjectList$$ExternalSyntheticOutline0.m(iMax, iMin, "createDownScaledSourceBitmap: longDisplay=", ", shortBmpLen=", ", scale="));
        DownScaledSourceBitmapManager.Item item = null;
        if (fMax <= 1.0f) {
            Bitmap bitmapCopy = fMax == 1.0f ? bitmap.copy(bitmap.getConfig(), false) : Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * fMax), (int) (bitmap.getHeight() * fMax), true);
            if (bitmapCopy == null || bitmapCopy == bitmap) {
                Log.e("ImageWallpaper[DownScaledSourceBitmapManager]", "createDownScaledSourceBitmap: Resized bitmap creation failed. org=" + bitmap + ", resized=" + bitmapCopy);
            } else {
                item = new DownScaledSourceBitmapManager.Item(i, bitmapCopy, fMax, arrayList);
            }
        }
        if (item == null) {
            downScaledSourceBitmapManager.mSourceBitmapSet.remove(Integer.valueOf(WhichChecker.getSourceWhich(i)));
        } else {
            downScaledSourceBitmapManager.mSourceBitmapSet.put(Integer.valueOf(WhichChecker.getSourceWhich(i)), item);
        }
    }
}
