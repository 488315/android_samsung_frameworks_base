package com.android.systemui.wallpaper.engines;

import android.content.Context;
import android.graphics.BLASTBufferQueue;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import com.android.systemui.wallpaper.engines.image.ImageEngine;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class WallpaperEngine {
    public final WallpaperEngineCallback mCallback;

    public WallpaperEngine(WallpaperEngineCallback wallpaperEngineCallback) {
        this.mCallback = wallpaperEngineCallback;
    }

    public boolean draw(SurfaceHolder surfaceHolder) {
        return false;
    }

    public final Context getAppContext() {
        return ImageWallpaper.this.getApplicationContext();
    }

    public final int getDisplayId() {
        return ImageWallpaper.IntegratedEngine.this.getDisplayId();
    }

    public final int getSourceWhich() {
        return WhichChecker.getSourceWhich(getWhich());
    }

    public final SurfaceHolder getSurfaceHolder() {
        return ImageWallpaper.IntegratedEngine.this.getSurfaceHolder();
    }

    public final int getWhich() {
        return ImageWallpaper.IntegratedEngine.this.semGetWallpaperFlags();
    }

    public final boolean isWindowVisible() {
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.getClass();
        int i = ImageWallpaper.IntegratedEngine.$r8$clinit;
        ImageWallpaper.IntegratedEngine integratedEngine = ImageWallpaper.IntegratedEngine.this;
        integratedEngine.getClass();
        try {
            Field declaredField = WallpaperService.Engine.class.getDeclaredField("mVisible");
            declaredField.setAccessible(true);
            return ((Boolean) declaredField.get(integratedEngine)).booleanValue();
        } catch (Exception e) {
            Log.e(integratedEngine.TAG, "isWindowVisible: e=" + e, e);
            return false;
        }
    }

    public ScreenshotResults onGetScreenshot(ScreenshotOptions screenshotOptions) {
        return null;
    }

    public final void setFixedOrientation(boolean z, boolean z2) {
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        synchronized (ImageWallpaper.IntegratedEngine.this) {
            ImageWallpaper.IntegratedEngine.this.semSetFixedOrientation(z, z2);
        }
    }

    public final void setVisibleRectOfSurface(final Rect rect) {
        BLASTBufferQueue bLASTBufferQueue;
        final ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.getClass();
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        ImageWallpaper.IntegratedEngine integratedEngine = ImageWallpaper.IntegratedEngine.this;
        final Rect surfaceFrame = integratedEngine.getSurfaceHolder().getSurfaceFrame();
        Log.i(integratedEngine.TAG, "setVisibleRectOfSurface: frame=" + surfaceFrame + ", visibleRect=" + rect);
        try {
            Field declaredField = WallpaperService.Engine.class.getDeclaredField("mBlastBufferQueue");
            declaredField.setAccessible(true);
            bLASTBufferQueue = (BLASTBufferQueue) declaredField.get(integratedEngine);
        } catch (Exception e) {
            Log.e(integratedEngine.TAG, "getBlastBufferQueue: e=" + e, e);
            bLASTBufferQueue = null;
        }
        BLASTBufferQueue bLASTBufferQueue2 = bLASTBufferQueue;
        if (bLASTBufferQueue2 == null) {
            Log.i(integratedEngine.TAG, "setVisibleRectOfSurface: failed to get the BBQ");
            return;
        }
        BLASTBufferQueue bLASTBufferQueue3 = anonymousClass2.mBbqOfPendingTransactionRequest;
        if (bLASTBufferQueue3 != null) {
            bLASTBufferQueue3.clearSyncTransaction();
        }
        anonymousClass2.mBbqOfPendingTransactionRequest = bLASTBufferQueue2;
        bLASTBufferQueue2.syncNextTransaction(new Consumer() { // from class: com.android.systemui.wallpapers.ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass22 = ImageWallpaper.IntegratedEngine.AnonymousClass2.this;
                long j = elapsedRealtime;
                Rect rect2 = surfaceFrame;
                Rect rect3 = rect;
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) obj;
                synchronized (ImageWallpaper.IntegratedEngine.this) {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime() - j;
                    Log.i(ImageWallpaper.IntegratedEngine.this.TAG, "setVisibleRectOfSurface: frame=" + rect2 + ", transaction ready. delay=" + elapsedRealtime2);
                    SurfaceControl surfaceControl = null;
                    anonymousClass22.mBbqOfPendingTransactionRequest = null;
                    ImageWallpaper.IntegratedEngine integratedEngine2 = ImageWallpaper.IntegratedEngine.this;
                    integratedEngine2.getClass();
                    try {
                        Field declaredField2 = WallpaperService.Engine.class.getDeclaredField("mSurfaceControl");
                        declaredField2.setAccessible(true);
                        surfaceControl = (SurfaceControl) declaredField2.get(integratedEngine2);
                    } catch (Exception e2) {
                        Log.e(integratedEngine2.TAG, "getSurfaceControl: e=" + e2, e2);
                    }
                    if (surfaceControl == null || !surfaceControl.isValid()) {
                        Log.i(ImageWallpaper.IntegratedEngine.this.TAG, "setVisibleRectOfSurface : failed to get surface control");
                    } else {
                        try {
                            transaction.setGeometry(surfaceControl, rect3, rect2, 0);
                        } catch (Exception e3) {
                            Log.e(ImageWallpaper.IntegratedEngine.this.TAG, "setVisibleRectOfSurface : e=" + e3, e3);
                        }
                    }
                    transaction.apply();
                }
            }
        });
    }

    public boolean shouldWaitForEngineShown() {
        return this instanceof ImageEngine;
    }

    public void onApplyDarkModeDimSettingChanged() {
    }

    public void onDestroy() {
    }

    public void onStartedWakingUp() {
    }

    public void onCreate(SurfaceHolder surfaceHolder) {
    }

    public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
    }

    public void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
    }

    public void onSwitchDisplayChanged(boolean z) {
    }

    public void onVisibilityChanged(boolean z) {
    }

    public void onWhichChanged(int i) {
    }

    public void onDisplayStateChanged(DisplayState displayState, DisplayState displayState2) {
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    public void onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
    }
}
