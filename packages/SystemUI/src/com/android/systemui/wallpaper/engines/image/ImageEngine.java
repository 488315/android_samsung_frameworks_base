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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final DrawState drawFrameOnCanvas(Canvas canvas, long j, Rect rect, Bitmap bitmap, ArrayList arrayList, float f) {
        long j2;
        long j3;
        long j4;
        DrawState drawState;
        Rect rect2;
        Rect nearestCropHint;
        Matrix matrix;
        int width;
        int height;
        float f2;
        float f3;
        Bitmap createFilteredBitmap;
        Integer dimFilterColor;
        if (!WallpaperUtils.isValidBitmap(bitmap)) {
            return null;
        }
        if (canvas == null && !isSurfaceCreated()) {
            Log.e(this.TAG, "drawFrameOnCanvas: the surface holder is invalid");
            return null;
        }
        int sourceWhich = getSourceWhich();
        long elapsedRealtime = SystemClock.elapsedRealtime();
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
                    j3 = j2;
                    j4 = j3;
                    WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : failed draw bitmap. e=", e, this.TAG, e);
                    drawState = null;
                    long elapsedRealtime2 = SystemClock.elapsedRealtime() - j;
                    WallpaperLogger wallpaperLogger = this.mLogger;
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
                    ((WallpaperLoggerImpl) wallpaperLogger).log(str, m.toString());
                    return drawState;
                }
            }
            nearestCropHint = IntelligentCropHelper.getNearestCropHint(new Point(rect2.width(), rect2.height()), arrayList);
            if (nearestCropHint != null) {
                j2 = elapsedRealtime;
                Rect rect3 = new Rect((int) (nearestCropHint.left * f), (int) (nearestCropHint.top * f), (int) (nearestCropHint.right * f), (int) (nearestCropHint.bottom * f));
                rect3.intersect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                nearestCropHint = rect3;
            } else {
                j2 = elapsedRealtime;
            }
            matrix = new Matrix();
            int width2 = nearestCropHint != null ? nearestCropHint.width() : bitmap.getWidth();
            int height2 = nearestCropHint != null ? nearestCropHint.height() : bitmap.getHeight();
            width = rect2.width();
            height = rect2.height();
            if (width2 * height > width * height2) {
                f2 = height;
                f3 = height2;
            } else {
                f2 = width;
                f3 = width2;
            }
            float f4 = f2 / f3;
            float m2 = Frame$$ExternalSyntheticOutline0.m(width2, f4, width, 0.5f);
            float m3 = Frame$$ExternalSyntheticOutline0.m(height2, f4, height, 0.5f);
            matrix.setScale(f4, f4);
            if (nearestCropHint != null) {
                matrix.preTranslate(-nearestCropHint.left, -nearestCropHint.top);
            }
            matrix.postTranslate(Math.round(m2), Math.round(m3));
            String filterData = this.mWallpaperSource.getSupplier().getFilterData();
            createFilteredBitmap = !TextUtils.isEmpty(filterData) ? ColorDecorFilterHelper.createFilteredBitmap(filterData, bitmap) : bitmap;
            dimFilterColor = getDimFilterColor(sourceWhich);
            if (dimFilterColor != null) {
                this.mBitmapPaint.setColorFilter(new PorterDuffColorFilter(dimFilterColor.intValue(), PorterDuff.Mode.SRC_OVER));
            } else {
                this.mBitmapPaint.setColorFilter(null);
            }
            j4 = SystemClock.elapsedRealtime();
        } catch (Exception e2) {
            e = e2;
            j2 = elapsedRealtime;
        }
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
        } catch (Exception e3) {
            e = e3;
            j3 = j2;
            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : failed draw bitmap. e=", e, this.TAG, e);
            drawState = null;
            long elapsedRealtime22 = SystemClock.elapsedRealtime() - j;
            WallpaperLogger wallpaperLogger3 = this.mLogger;
            String str3 = this.TAG;
            StringBuilder m4 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime22, ", bmpPrepareDur=");
            m4.append(j2 - j);
            m4.append(", filterApplyDur=");
            m4.append(j4 - j2);
            m4.append(", drawDur=");
            m4.append(j3 - j4);
            m4.append(", drawnState=(");
            m4.append(drawState);
            m4.append(")");
            ((WallpaperLoggerImpl) wallpaperLogger3).log(str3, m4.toString());
            return drawState;
        }
        if (createFilteredBitmap.isRecycled()) {
            Log.e(this.TAG, "drawFrameOnCanvas : bitmap is recycled!");
            throw new RuntimeException("tried to draw with recycled bitmap");
        }
        if (canvas != null) {
            canvas.drawBitmap(createFilteredBitmap, matrix, this.mBitmapPaint);
        } else {
            synchronized (this.mSurfaceLock) {
                try {
                    if (!isSurfaceCreated()) {
                        throw new RuntimeException("incorrect surface");
                    }
                    Surface surface = this.mSurfaceHolder.getSurface();
                    Canvas lockHardwareWideColorGamutCanvas = this.mWallpaperManager.wallpaperSupportsWcg(bitmap) ? surface.lockHardwareWideColorGamutCanvas() : surface.lockHardwareCanvas();
                    try {
                        if (lockHardwareWideColorGamutCanvas == null) {
                            Log.e(this.TAG, "drawFrameOnCanvas: canvas is NULL");
                            throw new RuntimeException("failed to lock the canvas");
                        }
                        try {
                            lockHardwareWideColorGamutCanvas.drawBitmap(createFilteredBitmap, matrix, this.mBitmapPaint);
                            if (this.mIsWallpaperSizeWarningEnabled) {
                                drawWarningTextIfNeeded(sourceWhich, lockHardwareWideColorGamutCanvas, bitmap);
                            }
                        } catch (Exception e4) {
                            Log.e(this.TAG, "drawFrameOnCanvas: e = " + e4, e4);
                            surface.unlockCanvasAndPost(lockHardwareWideColorGamutCanvas);
                            if (this.mWallpaperSource.isMultipack()) {
                                if (!LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
                                }
                            }
                        }
                    } finally {
                        surface.unlockCanvasAndPost(lockHardwareWideColorGamutCanvas);
                        if (!this.mWallpaperSource.isMultipack() || !LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
                            surface.hwuiDestroy();
                        }
                    }
                } finally {
                }
            }
        }
        if (createFilteredBitmap != bitmap) {
            createFilteredBitmap.recycle();
        }
        j3 = SystemClock.elapsedRealtime();
        if (canvas == null) {
            try {
            } catch (Exception e5) {
                e = e5;
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : failed draw bitmap. e=", e, this.TAG, e);
                drawState = null;
                long elapsedRealtime222 = SystemClock.elapsedRealtime() - j;
                WallpaperLogger wallpaperLogger32 = this.mLogger;
                String str32 = this.TAG;
                StringBuilder m42 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime222, ", bmpPrepareDur=");
                m42.append(j2 - j);
                m42.append(", filterApplyDur=");
                m42.append(j4 - j2);
                m42.append(", drawDur=");
                m42.append(j3 - j4);
                m42.append(", drawnState=(");
                m42.append(drawState);
                m42.append(")");
                ((WallpaperLoggerImpl) wallpaperLogger32).log(str32, m42.toString());
                return drawState;
            }
            if (rect2.equals(rect)) {
                drawState = new DrawState(this, sourceWhich, width, height, dimFilterColor != null);
                long elapsedRealtime2222 = SystemClock.elapsedRealtime() - j;
                WallpaperLogger wallpaperLogger322 = this.mLogger;
                String str322 = this.TAG;
                StringBuilder m422 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime2222, ", bmpPrepareDur=");
                m422.append(j2 - j);
                m422.append(", filterApplyDur=");
                m422.append(j4 - j2);
                m422.append(", drawDur=");
                m422.append(j3 - j4);
                m422.append(", drawnState=(");
                m422.append(drawState);
                m422.append(")");
                ((WallpaperLoggerImpl) wallpaperLogger322).log(str322, m422.toString());
                return drawState;
            }
            Log.w(this.TAG, "drawFrameOnCanvas : surface size mismatch. curFrame=" + rect2 + ", requestedFrame=" + rect);
        }
        drawState = null;
        long elapsedRealtime22222 = SystemClock.elapsedRealtime() - j;
        WallpaperLogger wallpaperLogger3222 = this.mLogger;
        String str3222 = this.TAG;
        StringBuilder m4222 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime22222, ", bmpPrepareDur=");
        m4222.append(j2 - j);
        m4222.append(", filterApplyDur=");
        m4222.append(j4 - j2);
        m4222.append(", drawDur=");
        m4222.append(j3 - j4);
        m4222.append(", drawnState=(");
        m4222.append(drawState);
        m4222.append(")");
        ((WallpaperLoggerImpl) wallpaperLogger3222).log(str3222, m4222.toString());
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
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mWallpaperSource.useBitmap(new Consumer() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImageEngine imageEngine = ImageEngine.this;
                int i = sourceWhich;
                Canvas canvas2 = canvas;
                long j = elapsedRealtime;
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
                ImageEngine.DrawState drawFrameOnCanvas = imageEngine.drawFrameOnCanvas(canvas2, j, rect2, bitmap, wallpaperImage.mCropRects, 1.0f);
                if (canvas2 == null) {
                    imageEngine.mLastDrawnState = drawFrameOnCanvas;
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

    public final void drawWarningTextIfNeeded(int i, Canvas canvas, Bitmap bitmap) {
        Point displaySize;
        Context appContext = getAppContext();
        int sourceWhich = getSourceWhich();
        int displayIdByWhich = DisplayUtils.getDisplayIdByWhich(sourceWhich, appContext);
        Display display = ((DisplayManager) appContext.getSystemService("display")).getDisplay(displayIdByWhich);
        int i2 = 0;
        if (display == null) {
            Log.e("CommonUtils", "getLongLengthOfMaxResolution: failed to get display. which=" + sourceWhich + ", displayId=" + displayIdByWhich);
        } else {
            int i3 = 0;
            for (Display.Mode mode : display.getSupportedModes()) {
                int max = Math.max(mode.getPhysicalWidth(), mode.getPhysicalHeight());
                if (max > i3) {
                    i3 = max;
                }
            }
            i2 = (i3 != 0 || (displaySize = DisplayUtils.getDisplaySize(appContext, sourceWhich, 0)) == null) ? i3 : Math.max(displaySize.x, displaySize.y);
        }
        if (WhichChecker.isFlagEnabled(i, 8)) {
            i2 = Math.max(4096, i2);
        }
        int i4 = (int) (i2 * 1.1f);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (Math.max(width, height) <= i4 || i2 == 0) {
            return;
        }
        float centerX = this.mSurfaceHolder.getSurfaceFrame().centerX();
        float centerY = this.mSurfaceHolder.getSurfaceFrame().centerY();
        Paint paint = new Paint();
        paint.setColor(-16777216);
        Path path = new Path();
        path.addRect(new RectF(0.0f, centerY - 100.0f, this.mSurfaceHolder.getSurfaceFrame().width(), 100.0f + centerY), Path.Direction.CCW);
        paint.setStrokeWidth(20.0f);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        paint.setStrokeWidth(0.0f);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30.0f);
        paint.setColor(-65536);
        String str = "This wallpaper isn't supported size : [" + width + "x" + height + "]";
        canvas.drawText(str, centerX, centerY, paint);
        paint.setColor(-1);
        StringBuilder sb = new StringBuilder("Maximum supported size : [");
        sb.append(i2);
        sb.append("x");
        canvas.drawText(ReorderTile$$ExternalSyntheticOutline0.m(i2, "]", sb), centerX, centerY + 50.0f, paint);
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
            /* JADX WARN: Removed duplicated region for block: B:13:0x0027 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:6:0x0015, B:8:0x0019, B:13:0x0027, B:14:0x004f), top: B:5:0x0015 }] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    r7 = this;
                    com.android.systemui.wallpaper.engines.image.ImageEngine r7 = com.android.systemui.wallpaper.engines.image.ImageEngine.this
                    boolean r0 = r7.mIsEngineAlive
                    if (r0 != 0) goto L8
                    goto L87
                L8:
                    java.lang.String r0 = "isFrameDrawNeeded: draw needed. which="
                    int r1 = r7.getSourceWhich()
                    com.android.systemui.wallpaper.engines.image.ImageEngine$DrawState r2 = r7.estimateDrawStateToDraw(r1)
                    java.lang.Object r3 = r7.mLock
                    monitor-enter(r3)
                    com.android.systemui.wallpaper.engines.image.ImageEngine$DrawState r4 = r7.mLastDrawnState     // Catch: java.lang.Throwable -> L22
                    if (r4 == 0) goto L24
                    boolean r4 = r4.equals(r2)     // Catch: java.lang.Throwable -> L22
                    if (r4 != 0) goto L20
                    goto L24
                L20:
                    r4 = 0
                    goto L25
                L22:
                    r7 = move-exception
                    goto L88
                L24:
                    r4 = 1
                L25:
                    if (r4 == 0) goto L4f
                    java.lang.String r5 = r7.TAG     // Catch: java.lang.Throwable -> L22
                    java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L22
                    r6.<init>(r0)     // Catch: java.lang.Throwable -> L22
                    r6.append(r1)     // Catch: java.lang.Throwable -> L22
                    java.lang.String r0 = ", lastDrawn=("
                    r6.append(r0)     // Catch: java.lang.Throwable -> L22
                    com.android.systemui.wallpaper.engines.image.ImageEngine$DrawState r0 = r7.mLastDrawnState     // Catch: java.lang.Throwable -> L22
                    r6.append(r0)     // Catch: java.lang.Throwable -> L22
                    java.lang.String r0 = "), toDraw=("
                    r6.append(r0)     // Catch: java.lang.Throwable -> L22
                    r6.append(r2)     // Catch: java.lang.Throwable -> L22
                    java.lang.String r0 = ")"
                    r6.append(r0)     // Catch: java.lang.Throwable -> L22
                    java.lang.String r0 = r6.toString()     // Catch: java.lang.Throwable -> L22
                    android.util.Log.i(r5, r0)     // Catch: java.lang.Throwable -> L22
                L4f:
                    monitor-exit(r3)     // Catch: java.lang.Throwable -> L22
                    com.android.systemui.wallpaper.log.WallpaperLogger r0 = r7.mLogger
                    java.lang.String r1 = r7.TAG
                    java.lang.String r2 = "onConfigurationChanged: redrawNeeded="
                    java.lang.String r2 = com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m(r2, r4)
                    com.android.systemui.wallpaper.log.WallpaperLoggerImpl r0 = (com.android.systemui.wallpaper.log.WallpaperLoggerImpl) r0
                    r0.log(r1, r2)
                    if (r4 == 0) goto L87
                    java.lang.Object r0 = r7.mSurfaceLock
                    monitor-enter(r0)
                    boolean r1 = r7.isSurfaceCreated()     // Catch: java.lang.Throwable -> L73
                    if (r1 != 0) goto L75
                    java.lang.String r7 = r7.TAG     // Catch: java.lang.Throwable -> L73
                    java.lang.String r1 = "drawFrameSynchronized: the surface holder is invalid"
                    android.util.Log.i(r7, r1)     // Catch: java.lang.Throwable -> L73
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L73
                    return
                L73:
                    r7 = move-exception
                    goto L85
                L75:
                    android.graphics.Rect r1 = new android.graphics.Rect     // Catch: java.lang.Throwable -> L73
                    android.view.SurfaceHolder r2 = r7.mSurfaceHolder     // Catch: java.lang.Throwable -> L73
                    android.graphics.Rect r2 = r2.getSurfaceFrame()     // Catch: java.lang.Throwable -> L73
                    r1.<init>(r2)     // Catch: java.lang.Throwable -> L73
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L73
                    r7.drawFrameSynchronized(r1)
                    return
                L85:
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L73
                    throw r7
                L87:
                    return
                L88:
                    monitor-exit(r3)     // Catch: java.lang.Throwable -> L22
                    throw r7
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda0.run():void");
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
        boolean equals = TextUtils.equals(str, "samsung.android.wallpaper.resume");
        WallpaperAnimator wallpaperAnimator = this.mWallpaperAnimator;
        if (equals && wallpaperAnimator != null && WhichChecker.isFlagEnabled(getWhich(), 2)) {
            Log.d(wallpaperAnimator.TAG, "changeToDownScaleImmediately");
            wallpaperAnimator.release();
            wallpaperAnimator.onTransitionScaleChanged(1.0f);
        } else if (TextUtils.equals(str, "android.wallpaper.keyguardgoingaway") && wallpaperAnimator != null && WhichChecker.isFlagEnabled(getWhich(), 2)) {
            wallpaperAnimator.mKeyguardState = false;
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(final SurfaceHolder surfaceHolder) {
        int semGetWallpaperType;
        String str = this.TAG;
        StringBuilder sb = new StringBuilder("Engine onCreate: which=");
        sb.append(getWhich());
        sb.append(", displayId=");
        WallpaperEngineCallback wallpaperEngineCallback = this.mCallback;
        sb.append(ImageWallpaper.IntegratedEngine.this.getDisplayId());
        String sb2 = sb.toString();
        WallpaperLoggerImpl wallpaperLoggerImpl = (WallpaperLoggerImpl) this.mLogger;
        wallpaperLoggerImpl.log(str, sb2);
        Trace.beginSection("ImageWallpaper.ImageEngine#onCreate");
        boolean isValid = surfaceHolder.getSurface().isValid();
        boolean isPreview = ImageWallpaper.IntegratedEngine.this.isPreview();
        SettingsHelper settingsHelper = this.mSettingsHelper;
        ImageSource imageSource = this.mWallpaperSource;
        ImageWallpaper.IntegratedEngine.this.semSetFixedOrientation(imageSource.isFixedOrientation(isPreview, settingsHelper), isValid);
        if (imageSource.getSupplier().supportWallpaperScrolling()) {
            wallpaperLoggerImpl.log(this.TAG, "onCreate: require fixed size surface");
            imageSource.useBitmap(new Consumer() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImageEngine imageEngine = ImageEngine.this;
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
        boolean z = false;
        if (!DeviceType.isShipBuild() && Build.VERSION.SEM_FIRST_SDK_INT >= 35 && !DeviceType.isFactoryBinary() && (semGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(sourceWhich)) != 3 && semGetWallpaperType != 1000) {
            Bundle wallpaperExtras = imageSource.mWallpaperManager.getWallpaperExtras(imageSource.mWhich, imageSource.mUserId);
            String string = wallpaperExtras == null ? null : wallpaperExtras.getString("imageCategory");
            Log.e(this.TAG, "isWallpaperSizeWarningEnabled: " + string);
            if (!"Graphical".equals(string) && !"Colors".equals(string)) {
                z = this.mWallpaperManager.semIsPreloadedWallpaper(sourceWhich, getCurrentUserId());
            }
        }
        this.mIsWallpaperSizeWarningEnabled = z;
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
        boolean isLockScreenDisabled = new LockPatternUtils(appContext).isLockScreenDisabled(currentUserId);
        if (this.mWallpaperAnimator == null || !WhichChecker.isFlagEnabled(getWhich(), 2) || isLockScreenDisabled) {
            return;
        }
        this.mWallpaperAnimator.onDisplayStateChanged(displayState, getWhich(), isVisible(), WallpaperUtils.isShowWallpaperOnAodEnabled(getWhich() & 60) && this.mDozeParameters.mControlScreenOffAnimation, false);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final ScreenshotResults onGetScreenshot(ScreenshotOptions screenshotOptions) {
        Point point;
        Bitmap bitmap;
        Log.i(this.TAG, "onGetScreenshot");
        Context appContext = getAppContext();
        int sourceWhich = getSourceWhich();
        int displayRotation = DisplayUtils.getDisplayRotation(sourceWhich, appContext);
        Point displaySize = DisplayUtils.getDisplaySize(appContext, sourceWhich, displayRotation);
        if (displaySize == null) {
            ClockEventController$$ExternalSyntheticOutline0.m(sourceWhich, "onGetScreenshot: failed to get display size. srcWhich=", this.TAG);
            return null;
        }
        boolean isFixedOrientation = this.mWallpaperSource.isFixedOrientation(ImageWallpaper.IntegratedEngine.this.isPreview(), this.mSettingsHelper);
        boolean supportWallpaperScrolling = this.mWallpaperSource.getSupplier().supportWallpaperScrolling();
        synchronized (this.mSurfaceLock) {
            try {
            } catch (Throwable th) {
                throw th;
            }
            if (isSurfaceCreated()) {
                Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
                int width = surfaceFrame.width();
                int height = surfaceFrame.height();
                if (supportWallpaperScrolling) {
                    Rect centerCropRect = GraphicsUtils.getCenterCropRect(width, height, displaySize.x, displaySize.y);
                    if (centerCropRect == null) {
                        point = displaySize;
                        bitmap = null;
                    } else {
                        int width2 = centerCropRect.width();
                        int height2 = centerCropRect.height();
                        point = new Point(width2, height2);
                        if (this.mIsFullQualityFrameDrawn) {
                            bitmap = DisplayUtils.copySurfaceToBitmapSync(this.mSurfaceHolder, centerCropRect, new Size(width2, height2));
                        }
                        bitmap = null;
                    }
                } else {
                    point = new Point(width, height);
                    if (this.mIsFullQualityFrameDrawn) {
                        bitmap = DisplayUtils.copySurfaceToBitmapSync(this.mSurfaceHolder, null, new Size(width, height));
                    }
                    bitmap = null;
                }
                throw th;
            }
            if (isFixedOrientation) {
                point = new Point(Math.min(displaySize.x, displaySize.y), Math.max(displaySize.x, displaySize.y));
                bitmap = null;
            }
            point = displaySize;
            bitmap = null;
            if (bitmap == null && point.x > 0 && point.y > 0) {
                if (TextUtils.equals(screenshotOptions.mPurpose, "prev")) {
                    bitmap = WallpaperUtils.getScreenShot(appContext, displaySize.x, displaySize.y, displayRotation);
                } else {
                    bitmap = Bitmap.createBitmap(point.x, point.y, Bitmap.Config.ARGB_8888, false);
                    drawFullQualityFrame(new Canvas(bitmap), null);
                }
            }
        }
        if (bitmap == null) {
            Log.e(this.TAG, "onGetScreenshot: failed to generate screenshot");
            return null;
        }
        if (isFixedOrientation) {
            Bitmap cropRotateResizeBitmap = BitmapUtils.cropRotateResizeBitmap(bitmap, null, -DisplayUtils.convertDisplayRotationToAngle(displayRotation), 1.0f, true);
            if (cropRotateResizeBitmap != bitmap) {
                bitmap.recycle();
            }
            bitmap = cropRotateResizeBitmap;
        }
        return new ScreenshotResults(bitmap);
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
                DrawState estimateDrawStateToDraw = estimateDrawStateToDraw(sourceWhich);
                synchronized (this.mLock) {
                    try {
                        Log.i(this.TAG, "onSurfaceRedrawNeeded: displayId=" + ImageWallpaper.IntegratedEngine.this.getDisplayId() + ", lastDrawn=(" + this.mLastDrawnState + "), toDraw=(" + estimateDrawStateToDraw + ")");
                        DrawState drawState = this.mLastDrawnState;
                        if (drawState != null && drawState.equals(estimateDrawStateToDraw)) {
                            Log.i(this.TAG, "onSurfaceRedrawNeeded: not need redraw");
                            return;
                        }
                        DrawState drawState2 = this.mLastDrawnState;
                        if (drawState2 != null && estimateDrawStateToDraw != null && drawState2.mSurfaceWidth * drawState2.mSurfaceHeight != estimateDrawStateToDraw.mSurfaceWidth * estimateDrawStateToDraw.mSurfaceHeight) {
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
                        this.mLastDrawnState = estimateDrawStateToDraw;
                        Runnable runnable = new Runnable() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ImageEngine.this.drawFrameSynchronized(rect);
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
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m(" onVisibilityChanged: visible=", " , displayId=", z);
        m.append(ImageWallpaper.IntegratedEngine.this.getDisplayId());
        Log.i(str, m.toString());
        Context appContext = getAppContext();
        int currentUserId = getCurrentUserId();
        boolean z2 = WallpaperUtils.mIsExternalLiveWallpaper;
        boolean isLockScreenDisabled = new LockPatternUtils(appContext).isLockScreenDisabled(currentUserId);
        WallpaperAnimator wallpaperAnimator = this.mWallpaperAnimator;
        if (wallpaperAnimator == null || !WhichChecker.isFlagEnabled(getWhich(), 2) || isLockScreenDisabled) {
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

    public final void putDownScaledSourceBitmap(int i, ImageSource.WallpaperImage wallpaperImage) {
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
        int max = Math.max(displaySize.x, displaySize.y);
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float max2 = ((int) Math.max(1024.0f, max * 0.5f)) / min;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(max2, "ImageWallpaper[DownScaledSourceBitmapManager]", MutableObjectList$$ExternalSyntheticOutline0.m(max, min, "createDownScaledSourceBitmap: longDisplay=", ", shortBmpLen=", ", scale="));
        DownScaledSourceBitmapManager.Item item = null;
        if (max2 <= 1.0f) {
            Bitmap copy = max2 == 1.0f ? bitmap.copy(bitmap.getConfig(), false) : Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * max2), (int) (bitmap.getHeight() * max2), true);
            if (copy == null || copy == bitmap) {
                Log.e("ImageWallpaper[DownScaledSourceBitmapManager]", "createDownScaledSourceBitmap: Resized bitmap creation failed. org=" + bitmap + ", resized=" + copy);
            } else {
                item = new DownScaledSourceBitmapManager.Item(i, copy, max2, arrayList);
            }
        }
        if (item == null) {
            downScaledSourceBitmapManager.mSourceBitmapSet.remove(Integer.valueOf(WhichChecker.getSourceWhich(i)));
        } else {
            downScaledSourceBitmapManager.mSourceBitmapSet.put(Integer.valueOf(WhichChecker.getSourceWhich(i)), item);
        }
    }
}
