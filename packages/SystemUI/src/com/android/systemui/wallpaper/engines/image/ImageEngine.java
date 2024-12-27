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
import android.os.Debug;
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
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.effect.ColorDecorFilterHelper;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.engines.image.DownScaledSourceBitmapManager;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.glwallpaper.ImageDarkModeFilter;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import com.samsung.android.wallpaper.live.sdk.utils.GraphicsUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ImageEngine extends WallpaperEngine {
    public String TAG;
    public final Paint mBitmapPaint;
    public int mBitmapUsages;
    public final DownScaledSourceBitmapManager mDownScaledSourceBitmapManager;
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
    public final WallpaperManager mWallpaperManager;
    public final ImageSource mWallpaperSource;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DrawState {
        public final boolean mDarkModeFilterApplied;
        public final boolean mHighlightFilterApplied;
        public final int mSurfaceHeight;
        public final int mSurfaceWidth;
        public final int mWhich;

        public DrawState(ImageEngine imageEngine, int i, int i2, int i3, boolean z, boolean z2) {
            if ((i & 60) == 0) {
                Log.e(imageEngine.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "DrawState : mode value is missing. which="), new RuntimeException());
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

    public ImageEngine(ImageSource imageSource, WallpaperEngineCallback wallpaperEngineCallback, SettingsHelper settingsHelper, SystemWallpaperColors systemWallpaperColors, DelayableExecutor delayableExecutor, KeyguardWallpaper keyguardWallpaper) {
        super(wallpaperEngineCallback);
        this.mBitmapPaint = new Paint(2);
        this.mIsFullQualityFrameDrawn = false;
        this.mIsWallpaperSizeWarningEnabled = false;
        this.mBitmapUsages = 0;
        this.mLock = new Object();
        this.mSurfaceLock = new Object();
        this.mIsEngineAlive = false;
        this.TAG = "ImageWallpaper_" + getWhich() + "[Image]";
        this.mWallpaperSource = imageSource;
        this.mSettingsHelper = settingsHelper;
        this.mSystemWallpaperColors = systemWallpaperColors;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mLongExecutor = delayableExecutor;
        WallpaperEngineCallback wallpaperEngineCallback2 = this.mCallback;
        this.mLogger = ImageWallpaper.this.mLogger;
        this.mWallpaperManager = ImageWallpaper.IntegratedEngine.this.mWallpaperManager;
        this.mDownScaledSourceBitmapManager = new DownScaledSourceBitmapManager();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final boolean draw(SurfaceHolder surfaceHolder) {
        onSurfaceRedrawNeeded(surfaceHolder);
        return false;
    }

    public final DrawState drawFrameOnCanvas(Canvas canvas, int i, long j, Rect rect, Bitmap bitmap, ArrayList arrayList, float f) {
        long j2;
        long j3;
        long j4;
        DrawState drawState;
        Rect rect2;
        Matrix matrix;
        int width;
        int height;
        float f2;
        float f3;
        Bitmap createFilteredBitmap;
        Integer dimFilterColor;
        WallpaperLogger wallpaperLogger;
        String str;
        DrawState drawState2;
        if (!WallpaperUtils.isValidBitmap(bitmap)) {
            return null;
        }
        if (canvas == null && !isSurfaceCreated()) {
            Log.e(this.TAG, "drawFrameOnCanvas: the surface holder is invalid");
            return null;
        }
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
                    Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                    drawState = null;
                    long elapsedRealtime2 = SystemClock.elapsedRealtime() - j;
                    WallpaperLogger wallpaperLogger2 = this.mLogger;
                    String str2 = this.TAG;
                    StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime2, ", bmpPrepareDur=");
                    m.append(j2 - j);
                    m.append(", filterApplyDur=");
                    m.append(j4 - j2);
                    m.append(", drawDur=");
                    m.append(j3 - j4);
                    m.append(", drawnState=(");
                    m.append(drawState);
                    m.append(")");
                    ((WallpaperLoggerImpl) wallpaperLogger2).log(str2, m.toString());
                    return drawState;
                }
            }
            Rect nearestCropHint = IntelligentCropHelper.getNearestCropHint(new Point(rect2.width(), rect2.height()), arrayList);
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
            float m2 = Rgb$Companion$$ExternalSyntheticOutline0.m(width2, f4, width, 0.5f);
            float m3 = Rgb$Companion$$ExternalSyntheticOutline0.m(height2, f4, height, 0.5f);
            matrix.setScale(f4, f4);
            if (nearestCropHint != null) {
                matrix.preTranslate(-nearestCropHint.left, -nearestCropHint.top);
            }
            matrix.postTranslate(Math.round(m2), Math.round(m3));
            String filterData = this.mWallpaperSource.getSupplier().getFilterData();
            createFilteredBitmap = !TextUtils.isEmpty(filterData) ? ColorDecorFilterHelper.createFilteredBitmap(filterData, bitmap) : bitmap;
            dimFilterColor = getDimFilterColor(i);
            if (dimFilterColor != null) {
                this.mBitmapPaint.setColorFilter(new PorterDuffColorFilter(dimFilterColor.intValue(), PorterDuff.Mode.SRC_OVER));
            } else {
                this.mBitmapPaint.setColorFilter(null);
            }
            j4 = SystemClock.elapsedRealtime();
            try {
                wallpaperLogger = this.mLogger;
                str = this.TAG;
            } catch (Exception e2) {
                e = e2;
                j3 = j2;
            }
            try {
                StringBuilder sb = new StringBuilder("drawFrameOnCanvas : which=");
                sb.append(i);
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
                sb.append(", highlight=-1, dimColor=");
                sb.append(dimFilterColor);
                sb.append(", drawRepeatCount=1, customCanvas=");
                sb.append(canvas != null);
                ((WallpaperLoggerImpl) wallpaperLogger).log(str, sb.toString());
            } catch (Exception e3) {
                e = e3;
                j3 = j2;
                j4 = j4;
                Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                drawState = null;
                long elapsedRealtime22 = SystemClock.elapsedRealtime() - j;
                WallpaperLogger wallpaperLogger22 = this.mLogger;
                String str22 = this.TAG;
                StringBuilder m4 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime22, ", bmpPrepareDur=");
                m4.append(j2 - j);
                m4.append(", filterApplyDur=");
                m4.append(j4 - j2);
                m4.append(", drawDur=");
                m4.append(j3 - j4);
                m4.append(", drawnState=(");
                m4.append(drawState);
                m4.append(")");
                ((WallpaperLoggerImpl) wallpaperLogger22).log(str22, m4.toString());
                return drawState;
            }
        } catch (Exception e4) {
            e = e4;
            j2 = elapsedRealtime;
        }
        if (createFilteredBitmap.isRecycled()) {
            Log.e(this.TAG, "drawFrameOnCanvas : bitmap is recycled!");
            throw new RuntimeException("tried to draw with recycled bitmap");
        }
        for (int i2 = 0; i2 < 1; i2++) {
            if (canvas != null) {
                canvas.drawBitmap(createFilteredBitmap, matrix, this.mBitmapPaint);
            } else {
                synchronized (this.mSurfaceLock) {
                    try {
                        if (!isSurfaceCreated()) {
                            throw new RuntimeException("incorrect surface - " + i2);
                        }
                        Surface surface = this.mSurfaceHolder.getSurface();
                        Canvas lockHardwareWideColorGamutCanvas = this.mWallpaperManager.wallpaperSupportsWcg(bitmap) ? surface.lockHardwareWideColorGamutCanvas() : surface.lockHardwareCanvas();
                        if (lockHardwareWideColorGamutCanvas == null) {
                            Log.e(this.TAG, "drawFrameOnCanvas : canvas is NULL");
                            throw new RuntimeException("failed to lock the canvas - " + i2);
                        }
                        try {
                            lockHardwareWideColorGamutCanvas.drawBitmap(createFilteredBitmap, matrix, this.mBitmapPaint);
                            if (this.mIsWallpaperSizeWarningEnabled) {
                                drawWarningTextIfNeeded(lockHardwareWideColorGamutCanvas, bitmap);
                            }
                        } finally {
                            surface.unlockCanvasAndPost(lockHardwareWideColorGamutCanvas);
                        }
                    } finally {
                    }
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
                j4 = j4;
                Log.e(this.TAG, "drawFrameOnCanvas : failed draw bitmap. e=" + e, e);
                drawState = null;
                long elapsedRealtime222 = SystemClock.elapsedRealtime() - j;
                WallpaperLogger wallpaperLogger222 = this.mLogger;
                String str222 = this.TAG;
                StringBuilder m42 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime222, ", bmpPrepareDur=");
                m42.append(j2 - j);
                m42.append(", filterApplyDur=");
                m42.append(j4 - j2);
                m42.append(", drawDur=");
                m42.append(j3 - j4);
                m42.append(", drawnState=(");
                m42.append(drawState);
                m42.append(")");
                ((WallpaperLoggerImpl) wallpaperLogger222).log(str222, m42.toString());
                return drawState;
            }
            if (rect2.equals(rect)) {
                drawState2 = new DrawState(this, i, width, height, false, dimFilterColor != null);
                drawState = drawState2;
                j4 = j4;
                long elapsedRealtime2222 = SystemClock.elapsedRealtime() - j;
                WallpaperLogger wallpaperLogger2222 = this.mLogger;
                String str2222 = this.TAG;
                StringBuilder m422 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime2222, ", bmpPrepareDur=");
                m422.append(j2 - j);
                m422.append(", filterApplyDur=");
                m422.append(j4 - j2);
                m422.append(", drawDur=");
                m422.append(j3 - j4);
                m422.append(", drawnState=(");
                m422.append(drawState);
                m422.append(")");
                ((WallpaperLoggerImpl) wallpaperLogger2222).log(str2222, m422.toString());
                return drawState;
            }
            Log.w(this.TAG, "drawFrameOnCanvas : surface size mismatch. curFrame=" + rect2 + ", requestedFrame=" + rect);
        }
        drawState2 = null;
        drawState = drawState2;
        j4 = j4;
        long elapsedRealtime22222 = SystemClock.elapsedRealtime() - j;
        WallpaperLogger wallpaperLogger22222 = this.mLogger;
        String str22222 = this.TAG;
        StringBuilder m4222 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("drawFrameOnCanvas : elapsed=", elapsedRealtime22222, ", bmpPrepareDur=");
        m4222.append(j2 - j);
        m4222.append(", filterApplyDur=");
        m4222.append(j4 - j2);
        m4222.append(", drawDur=");
        m4222.append(j3 - j4);
        m4222.append(", drawnState=(");
        m4222.append(drawState);
        m4222.append(")");
        ((WallpaperLoggerImpl) wallpaperLogger22222).log(str22222, m4222.toString());
        return drawState;
    }

    public final void drawFrameSynchronized(int i, Rect rect) {
        synchronized (this.mLock) {
            drawFullQualityFrame(null, i, rect);
            Settings.System.putLong(getAppContext().getContentResolver(), "wallpaper_finish_drawing", System.currentTimeMillis());
            Trace.beginSection("ImageWallpaper#finishRendering");
            Log.i(this.TAG, "finishRendering");
            Trace.endSection();
        }
    }

    public final void drawFullQualityFrame(final Canvas canvas, final int i, final Rect rect) {
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
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mWallpaperSource.useBitmap(new Consumer() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImageEngine imageEngine = ImageEngine.this;
                int i2 = i;
                Canvas canvas2 = canvas;
                long j = elapsedRealtime;
                Rect rect2 = rect;
                ImageSource.WallpaperImage wallpaperImage = (ImageSource.WallpaperImage) obj;
                imageEngine.getClass();
                Bitmap bitmap = wallpaperImage.mBitmap;
                DownScaledSourceBitmapManager downScaledSourceBitmapManager = imageEngine.mDownScaledSourceBitmapManager;
                if (bitmap == null) {
                    downScaledSourceBitmapManager.mSourceBitmapSet.remove(Integer.valueOf(WhichChecker.getSourceWhich(i2)));
                    imageEngine.mLastDrawnState = null;
                } else {
                    if (((DownScaledSourceBitmapManager.Item) downScaledSourceBitmapManager.mSourceBitmapSet.get(Integer.valueOf(WhichChecker.getSourceWhich(i2)))) == null) {
                        imageEngine.putDownScaledSourceBitmap(i2, wallpaperImage);
                    }
                    imageEngine.mLastDrawnState = imageEngine.drawFrameOnCanvas(canvas2, i2, j, rect2, bitmap, wallpaperImage.mCropRects, 1.0f);
                    imageEngine.mIsFullQualityFrameDrawn = true;
                }
            }
        });
        Trace.endSection();
        ImageWallpaper.IntegratedEngine.this.reportEngineShown(false);
        int i2 = this.mBitmapUsages - 1;
        this.mBitmapUsages = i2;
        if (i2 <= 0) {
            this.mBitmapUsages = 0;
            Trace.beginSection("ImageWallpaper.ImageEngine#unloadBitmap");
            this.mWallpaperManager.forgetLoadedWallpaper();
            Trace.endSection();
        }
    }

    public final void drawWarningTextIfNeeded(Canvas canvas, Bitmap bitmap) {
        Point displaySize;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Context appContext = getAppContext();
        int sourceWhich = getSourceWhich();
        int displayIdByWhich = DisplayUtils.getDisplayIdByWhich(sourceWhich, appContext);
        Display display = ((DisplayManager) appContext.getSystemService("display")).getDisplay(displayIdByWhich);
        int i = 0;
        if (display == null) {
            Log.e("CommonUtils", "getLongLengthOfMaxResolution: failed to get display. which=" + sourceWhich + ", displayId=" + displayIdByWhich);
        } else {
            int i2 = 0;
            for (Display.Mode mode : display.getSupportedModes()) {
                int max = Math.max(mode.getPhysicalWidth(), mode.getPhysicalHeight());
                if (max > i2) {
                    i2 = max;
                }
            }
            i = (i2 != 0 || (displaySize = DisplayUtils.getDisplaySize(appContext, sourceWhich, 0)) == null) ? i2 : Math.max(displaySize.x, displaySize.y);
        }
        if (Math.max(width, height) <= ((int) (i * 1.1f)) || i == 0) {
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
        sb.append(i);
        sb.append("x");
        canvas.drawText(Anchor$$ExternalSyntheticOutline0.m(i, "]", sb), centerX, centerY + 50.0f, paint);
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
                return new DrawState(this, i, surfaceFrame.width(), surfaceFrame.height(), false, getDimFilterColor(i) != null);
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
        ImageEngine$$ExternalSyntheticLambda0 imageEngine$$ExternalSyntheticLambda0 = new ImageEngine$$ExternalSyntheticLambda0(this, 0);
        Handler threadHandler = ImageWallpaper.this.mWorker.getThreadHandler();
        if (threadHandler == null) {
            Log.w(this.TAG, "runAsWorkerThread: worker handler is null.");
        } else {
            threadHandler.post(imageEngine$$ExternalSyntheticLambda0);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(final SurfaceHolder surfaceHolder) {
        int semGetWallpaperType;
        String str = this.TAG;
        String str2 = "Engine onCreate: which=" + getWhich() + ", displayId=" + getDisplayId();
        WallpaperLoggerImpl wallpaperLoggerImpl = (WallpaperLoggerImpl) this.mLogger;
        wallpaperLoggerImpl.log(str, str2);
        Trace.beginSection("ImageWallpaper.ImageEngine#onCreate");
        boolean isValid = surfaceHolder.getSurface().isValid();
        boolean isPreview = ImageWallpaper.IntegratedEngine.this.isPreview();
        SettingsHelper settingsHelper = this.mSettingsHelper;
        ImageSource imageSource = this.mWallpaperSource;
        setFixedOrientation(imageSource.isFixedOrientation(isPreview, settingsHelper), isValid);
        if (imageSource.getSupplier().supportWallpaperScrolling()) {
            wallpaperLoggerImpl.log(this.TAG, "onCreate: require fixed size surface");
            imageSource.useBitmap(new Consumer() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImageEngine imageEngine = ImageEngine.this;
                    SurfaceHolder surfaceHolder2 = surfaceHolder;
                    ImageSource.WallpaperImage wallpaperImage = (ImageSource.WallpaperImage) obj;
                    imageEngine.getClass();
                    Bitmap bitmap = wallpaperImage.mBitmap;
                    surfaceHolder2.setFixedSize(bitmap.getWidth(), bitmap.getHeight());
                    imageEngine.putDownScaledSourceBitmap(imageEngine.getSourceWhich(), wallpaperImage);
                }
            });
        }
        this.mIsEngineAlive = true;
        int sourceWhich = getSourceWhich();
        boolean z = false;
        if (Debug.semIsProductDev() && Build.VERSION.SEM_FIRST_SDK_INT >= 35 && !DeviceType.isFactoryBinary() && (semGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(sourceWhich)) != 3 && semGetWallpaperType != 1000) {
            Bundle wallpaperExtras = imageSource.mWallpaperManager.getWallpaperExtras(imageSource.mWhich, imageSource.mUserId);
            String string = wallpaperExtras == null ? null : wallpaperExtras.getString("imageCategory");
            Log.e(this.TAG, "isWallpaperSizeWarningEnabled: " + string);
            if (!"Graphical".equals(string) && !"Colors".equals(string)) {
                z = this.mWallpaperManager.semIsPreloadedWallpaper(sourceWhich, ImageWallpaper.IntegratedEngine.this.getCurrentUserId());
            }
        }
        this.mIsWallpaperSizeWarningEnabled = z;
        Trace.endSection();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
        ((WallpaperLoggerImpl) this.mLogger).log(this.TAG, "Engine onDestroy: which=" + getWhich() + ", displayId=" + getDisplayId());
        this.mIsEngineAlive = false;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final ScreenshotResults onGetScreenshot(ScreenshotOptions screenshotOptions) {
        Point point;
        Bitmap bitmap;
        int i;
        int i2;
        Point point2;
        Log.i(this.TAG, "onGetScreenshot");
        Context appContext = getAppContext();
        int sourceWhich = getSourceWhich();
        int displayRotation = DisplayUtils.getDisplayRotation(sourceWhich, appContext);
        Point displaySize = DisplayUtils.getDisplaySize(appContext, sourceWhich, displayRotation);
        if (displaySize == null) {
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(sourceWhich, "onGetScreenshot: failed to get display size. srcWhich=", this.TAG);
            return null;
        }
        boolean isFixedOrientation = this.mWallpaperSource.isFixedOrientation(ImageWallpaper.IntegratedEngine.this.isPreview(), this.mSettingsHelper);
        boolean supportWallpaperScrolling = this.mWallpaperSource.getSupplier().supportWallpaperScrolling();
        synchronized (this.mSurfaceLock) {
            try {
                if (isSurfaceCreated()) {
                    Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
                    int width = surfaceFrame.width();
                    int height = surfaceFrame.height();
                    if (supportWallpaperScrolling) {
                        Rect centerCropRect = GraphicsUtils.getCenterCropRect(width, height, displaySize.x, displaySize.y);
                        if (centerCropRect == null) {
                            point2 = displaySize;
                            bitmap = null;
                            point = point2;
                        } else {
                            int width2 = centerCropRect.width();
                            int height2 = centerCropRect.height();
                            point2 = new Point(width2, height2);
                            if (this.mIsFullQualityFrameDrawn) {
                                bitmap = DisplayUtils.copySurfaceToBitmapSync(this.mSurfaceHolder, centerCropRect, new Size(width2, height2));
                                point = point2;
                            }
                            bitmap = null;
                            point = point2;
                        }
                    } else {
                        displaySize = new Point(width, height);
                        if (this.mIsFullQualityFrameDrawn) {
                            Bitmap copySurfaceToBitmapSync = DisplayUtils.copySurfaceToBitmapSync(this.mSurfaceHolder, null, new Size(width, height));
                            point2 = displaySize;
                            bitmap = copySurfaceToBitmapSync;
                            point = point2;
                        }
                        point2 = displaySize;
                        bitmap = null;
                        point = point2;
                    }
                } else {
                    point = isFixedOrientation ? new Point(Math.min(displaySize.x, displaySize.y), Math.max(displaySize.x, displaySize.y)) : displaySize;
                    bitmap = null;
                }
                if (bitmap == null && (i = point.x) > 0 && (i2 = point.y) > 0) {
                    bitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888, false);
                    drawFullQualityFrame(new Canvas(bitmap), getSourceWhich(), null);
                }
            } catch (Throwable th) {
                throw th;
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
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i3, "onSurfaceChanged: ", " x ", this.TAG);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i(this.TAG, " onSurfaceCreated: " + surfaceHolder.getSurfaceFrame());
        this.mSurfaceHolder = surfaceHolder;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i(this.TAG, "onSurfaceDestroyed");
        Flags.FEATURE_FLAGS.getClass();
        this.mLongExecutor.execute(new ImageEngine$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        if (!this.mIsEngineAlive) {
            Log.i(this.TAG, "onSurfaceRedrawNeeded: engine already destroyed");
            return;
        }
        final int sourceWhich = getSourceWhich();
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
                        Log.i(this.TAG, "onSurfaceRedrawNeeded: displayId=" + getDisplayId() + ", lastDrawn=(" + this.mLastDrawnState + "), toDraw=(" + estimateDrawStateToDraw + ")");
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
                            drawFrameSynchronized(sourceWhich, rect);
                            return;
                        }
                        synchronized (this.mLock) {
                            drawFrameOnCanvas(null, sourceWhich, SystemClock.elapsedRealtime(), rect, item.mBitmap, item.mCropRects, item.mScale);
                        }
                        this.mLastDrawnState = estimateDrawStateToDraw;
                        this.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                ImageEngine.this.drawFrameSynchronized(sourceWhich, rect);
                            }
                        });
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
        m.append(getDisplayId());
        Log.i(str, m.toString());
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onWhichChanged(int i) {
        this.TAG = LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[Image]");
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
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "putDownScaledSourceBitmap: failed to get display size. srcWhich=", this.TAG);
            return;
        }
        ArrayList arrayList = wallpaperImage.mCropRects;
        DownScaledSourceBitmapManager downScaledSourceBitmapManager = this.mDownScaledSourceBitmapManager;
        downScaledSourceBitmapManager.getClass();
        int max = Math.max(displaySize.x, displaySize.y);
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float max2 = ((int) Math.max(1024.0f, max * 0.5f)) / min;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(max, min, "createDownScaledSourceBitmap: longDisplay=", ", shortBmpLen=", ", scale="), max2, "ImageWallpaper[DownScaledSourceBitmapManager]");
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
