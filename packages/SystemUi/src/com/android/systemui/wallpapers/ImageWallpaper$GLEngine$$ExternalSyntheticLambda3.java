package com.android.systemui.wallpapers;

import android.app.WallpaperManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.HandlerThread;
import android.os.Trace;
import android.util.Log;
import android.view.SurfaceHolder;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper;
import com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperGifRenderer;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperRenderer;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperVideoRenderer;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.nexus.video.VideoLayer;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GLEngine$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01f2  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        HandlerThread handlerThread;
        String videoFilePath;
        String str;
        String str2;
        String str3;
        String str4;
        boolean z;
        VideoLayer videoLayer;
        MediaMetadataRetriever mediaMetadataRetriever;
        int parseInt;
        int parseInt2;
        String str5;
        String str6;
        String str7;
        String str8;
        boolean z2;
        VideoLayer videoLayer2;
        MediaMetadataRetriever mediaMetadataRetriever2;
        int parseInt3;
        int parseInt4;
        boolean z3 = false;
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.GLEngine gLEngine = (ImageWallpaper.GLEngine) this.f$0;
                SurfaceHolder surfaceHolder = (SurfaceHolder) this.f$1;
                if (!gLEngine.mSurfaceCreated) {
                    Trace.beginSection("ImageWallpaper#onSurfaceCreated");
                    gLEngine.mEglHelper.finish();
                    gLEngine.mEglHelper.init(surfaceHolder, gLEngine.mRenderer.mTexture.mWcgContent);
                    gLEngine.mRenderer.mHighlightFilterAmount = gLEngine.determineHighlightFilterAmount();
                    gLEngine.mRenderer.onSurfaceCreated();
                    Trace.endSection();
                }
                gLEngine.mSurfaceCreated = true;
                return;
            case 1:
                ImageWallpaper.CanvasEngine canvasEngine = (ImageWallpaper.CanvasEngine) this.f$0;
                Configuration configuration = (Configuration) this.f$1;
                if (!canvasEngine.mIsEngineAlive) {
                    return;
                }
                canvasEngine.mRotation = configuration.windowConfiguration.getRotation();
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = canvasEngine.mHelper;
                imageWallpaperCanvasHelper.getClass();
                StringBuilder sb = new StringBuilder();
                boolean z4 = (configuration.uiMode & 32) != 0;
                if (imageWallpaperCanvasHelper.mIsNightModeOn != z4) {
                    imageWallpaperCanvasHelper.mIsNightModeOn = z4;
                    sb.append(" Dark Mode change " + imageWallpaperCanvasHelper.mIsNightModeOn);
                    z3 = true;
                }
                if (imageWallpaperCanvasHelper.mCurDensityDpi != configuration.densityDpi) {
                    sb.append(" onConfigurationChanged  mCurDensityDpi " + imageWallpaperCanvasHelper.mCurDensityDpi + " -> " + configuration.densityDpi);
                    imageWallpaperCanvasHelper.mCurDensityDpi = configuration.densityDpi;
                    WallpaperUtils.clearCachedWallpaper(1);
                    WallpaperUtils.clearCachedSmartCroppedRect(1);
                    if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                        WallpaperUtils.clearCachedWallpaper(5);
                        WallpaperUtils.clearCachedSmartCroppedRect(5);
                        WallpaperUtils.clearCachedWallpaper(17);
                        WallpaperUtils.clearCachedSmartCroppedRect(17);
                    }
                    z3 = true;
                }
                int i = configuration.orientation;
                sb.append(" onConfigurationChanged   " + configuration);
                int i2 = imageWallpaperCanvasHelper.mOrientation;
                ImageSmartCropper imageSmartCropper = imageWallpaperCanvasHelper.mImageSmartCropper;
                if (i2 != i) {
                    imageWallpaperCanvasHelper.mOrientation = i;
                    if (!imageWallpaperCanvasHelper.hasIntelligentCropHints(imageWallpaperCanvasHelper.getCurrentWhich()) && imageSmartCropper != null && imageSmartCropper.needToSmartCrop()) {
                        ImageSmartCropper.checkDisplaySize(configuration);
                        z3 = true;
                    }
                }
                boolean z5 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
                if (z5) {
                    sb.append(" ,  DeviceDisplay type " + imageWallpaperCanvasHelper.mDeviceDisplayType + " -> " + configuration.semDisplayDeviceType);
                    if (imageWallpaperCanvasHelper.mDeviceDisplayType != configuration.semDisplayDeviceType) {
                        int i3 = imageWallpaperCanvasHelper.mLidState;
                        WallpaperManager wallpaperManager = imageWallpaperCanvasHelper.mWallpaperManager;
                        if (i3 != wallpaperManager.getLidState() && ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDualView()) {
                            sb.append(", Dual dex mode . Update Now. " + ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mLidState) + " -> " + ImageWallpaperCanvasHelper.convertLidStateToString(wallpaperManager.getLidState()));
                            imageWallpaperCanvasHelper.setLidState(wallpaperManager.getLidState());
                            z3 = true;
                        }
                        if (imageSmartCropper != null && imageSmartCropper.needToSmartCrop()) {
                            ImageSmartCropper.checkDisplaySize(configuration);
                        }
                        imageWallpaperCanvasHelper.mDeviceDisplayType = configuration.semDisplayDeviceType;
                    }
                }
                ((WallpaperLoggerImpl) imageWallpaperCanvasHelper.mLoggerWrapper).log(imageWallpaperCanvasHelper.TAG, sb.toString() + " isNeedReDraw " + z3);
                if (z3) {
                    int currentWhich = canvasEngine.mHelper.getCurrentWhich();
                    if ((!z5 || LsRune.SUBSCREEN_WATCHFACE) ? true : !canvasEngine.updateSurfaceSizeIfNeed(currentWhich)) {
                        canvasEngine.updateRendering(currentWhich);
                        return;
                    }
                    return;
                }
                return;
            case 2:
                ImageWallpaper.CanvasEngine.C37206 c37206 = (ImageWallpaper.CanvasEngine.C37206) this.f$0;
                Boolean bool = (Boolean) this.f$1;
                ImageWallpaper.CanvasEngine canvasEngine2 = ImageWallpaper.CanvasEngine.this;
                if (!canvasEngine2.mIsEngineAlive) {
                    Log.w(canvasEngine2.TAG, " mPluginHomeWallpaperConsumer, skip, engine is destroyed");
                    return;
                }
                Log.i(canvasEngine2.TAG, " mPluginHomeWallpaperConsumer " + ImageWallpaper.this.mSubWallpaperType + " -> " + ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType() + " , " + bool);
                WallpaperUtils.clearCachedWallpaper(17);
                WallpaperUtils.clearCachedSmartCroppedRect(17);
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mSubWallpaperType = ((CoverWallpaperController) imageWallpaper.mCoverWallpaper).getWallpaperType();
                ImageWallpaper.CanvasEngine canvasEngine3 = ImageWallpaper.CanvasEngine.this;
                int coverWhich = ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getCoverWhich();
                if (((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType() != 21) {
                    WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).forceRebindWallpaper(coverWhich);
                    return;
                }
                if (canvasEngine3.updateSurfaceSizeIfNeed(coverWhich) || !canvasEngine3.mSurfaceCreated) {
                    return;
                }
                Rect rect = new Rect(canvasEngine3.mSurfaceHolder.getSurfaceFrame());
                synchronized (canvasEngine3.mLock) {
                    canvasEngine3.drawFullQualityFrame(coverWhich, rect);
                    canvasEngine3.finishRendering();
                }
                return;
            case 3:
                ImageWallpaper.GLEngine gLEngine2 = (ImageWallpaper.GLEngine) this.f$0;
                Configuration configuration2 = (Configuration) this.f$1;
                gLEngine2.getClass();
                int rotation = configuration2.windowConfiguration.getRotation();
                gLEngine2.mRotation = rotation;
                if (gLEngine2.mRenderer != null) {
                    gLEngine2.updateWallpaperOffset(rotation);
                    ImageWallpaperRenderer imageWallpaperRenderer = gLEngine2.mRenderer;
                    imageWallpaperRenderer.getClass();
                    StringBuilder sb2 = new StringBuilder();
                    boolean z6 = (configuration2.uiMode & 32) != 0;
                    if (imageWallpaperRenderer.mIsNightModeOn != z6) {
                        imageWallpaperRenderer.mIsNightModeOn = z6;
                        sb2.append(" Dark Mode change " + imageWallpaperRenderer.mIsNightModeOn);
                        z3 = true;
                    }
                    if (imageWallpaperRenderer.mCurDensityDpi != configuration2.densityDpi) {
                        sb2.append(" onConfigurationChanged  mCurDensityDpi " + imageWallpaperRenderer.mCurDensityDpi + " -> " + configuration2.densityDpi);
                        imageWallpaperRenderer.mCurDensityDpi = configuration2.densityDpi;
                        WallpaperUtils.clearCachedWallpaper(1);
                        WallpaperUtils.clearCachedSmartCroppedRect(1);
                        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                            WallpaperUtils.clearCachedWallpaper(5);
                            WallpaperUtils.clearCachedSmartCroppedRect(5);
                            WallpaperUtils.clearCachedWallpaper(17);
                            WallpaperUtils.clearCachedSmartCroppedRect(17);
                        }
                        z3 = true;
                    }
                    int i4 = configuration2.orientation;
                    sb2.append(" onConfigurationChanged   " + configuration2);
                    int i5 = imageWallpaperRenderer.mOrientation;
                    ImageSmartCropper imageSmartCropper2 = imageWallpaperRenderer.mImageSmartCropper;
                    if (i5 != i4) {
                        imageWallpaperRenderer.mOrientation = i4;
                        if (imageSmartCropper2 != null && imageSmartCropper2.needToSmartCrop()) {
                            ImageSmartCropper.checkDisplaySize(configuration2);
                            Point point = DeviceState.sDisplaySize;
                            if (DeviceType.isTablet()) {
                                z3 = true;
                            }
                        }
                    }
                    boolean z7 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
                    if (z7) {
                        sb2.append(" ,  DeviceDisplay type " + imageWallpaperRenderer.mDeviceDisplayType + " -> " + configuration2.semDisplayDeviceType);
                        if (imageWallpaperRenderer.mDeviceDisplayType != configuration2.semDisplayDeviceType) {
                            int i6 = imageWallpaperRenderer.mLidState;
                            WallpaperManager wallpaperManager2 = imageWallpaperRenderer.mWallpaperManager;
                            if (i6 != wallpaperManager2.getLidState() && ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDualView()) {
                                sb2.append(", Dual dex mode . Update Now. " + ImageWallpaperRenderer.showLidState(imageWallpaperRenderer.mLidState) + " -> " + ImageWallpaperRenderer.showLidState(wallpaperManager2.getLidState()));
                                imageWallpaperRenderer.setLidState(wallpaperManager2.getLidState());
                                z3 = true;
                            }
                            if (imageSmartCropper2 != null && imageSmartCropper2.needToSmartCrop()) {
                                ImageSmartCropper.checkDisplaySize(configuration2);
                            }
                            imageWallpaperRenderer.mDeviceDisplayType = configuration2.semDisplayDeviceType;
                        }
                    }
                    ((WallpaperLoggerImpl) imageWallpaperRenderer.mLoggerWrapper).log("ImageWallpaperRenderer", sb2.toString() + " isNeedReDraw " + z3);
                    if (z3) {
                        if ((!z7 || LsRune.SUBSCREEN_WATCHFACE) ? true : !gLEngine2.updateSurfaceSizeIfNeed()) {
                            gLEngine2.updateRendering();
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            case 4:
                ImageWallpaper.GLEngine.C37244 c37244 = (ImageWallpaper.GLEngine.C37244) this.f$0;
                Log.i("ImageWallpaper[GLEngine]", " mPluginHomeWallpaperConsumer " + ImageWallpaper.this.mSubWallpaperType + " -> " + ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType() + " , " + ((Boolean) this.f$1));
                WallpaperUtils.clearCachedWallpaper(17);
                WallpaperUtils.clearCachedSmartCroppedRect(17);
                ImageWallpaper imageWallpaper2 = ImageWallpaper.this;
                imageWallpaper2.mSubWallpaperType = ((CoverWallpaperController) imageWallpaper2.mCoverWallpaper).getWallpaperType();
                ImageWallpaper.GLEngine gLEngine3 = ImageWallpaper.GLEngine.this;
                if (gLEngine3.mRenderer == null || gLEngine3.mEglHelper == null || (handlerThread = ImageWallpaper.this.mWorker) == null) {
                    return;
                }
                if (handlerThread != null) {
                    handlerThread.getThreadHandler().removeCallbacks(gLEngine3.mUpdatePluginTask);
                }
                ImageWallpaper.this.mWorker.getThreadHandler().post(gLEngine3.mUpdatePluginTask);
                return;
            case 5:
                ImageWallpaper.GifGLEngine.C37251 c37251 = (ImageWallpaper.GifGLEngine.C37251) this.f$0;
                Boolean bool2 = (Boolean) this.f$1;
                Log.i("ImageWallpaper[GifGLEngine]", " mPluginGifWallpaperConsumer type previous " + ImageWallpaper.this.mSubWallpaperType + ", current " + ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType());
                if (!bool2.booleanValue()) {
                    ImageWallpaper.GifGLEngine gifGLEngine = ImageWallpaper.GifGLEngine.this;
                    HandlerThread handlerThread2 = ImageWallpaper.this.mWorker;
                    if (handlerThread2 == null) {
                        return;
                    }
                    if (handlerThread2 != null) {
                        handlerThread2.getThreadHandler().removeCallbacks(gifGLEngine.mPluginUpdateTask);
                    }
                    ImageWallpaper.this.mWorker.getThreadHandler().post(gifGLEngine.mPluginUpdateTask);
                    return;
                }
                Bitmap wallpaperBitmap = ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperBitmap(true);
                if (wallpaperBitmap != null) {
                    int max = Math.max(64, wallpaperBitmap.getWidth());
                    int max2 = Math.max(64, wallpaperBitmap.getHeight());
                    SurfaceHolder surfaceHolder2 = ImageWallpaper.GifGLEngine.this.getSurfaceHolder();
                    surfaceHolder2.setFixedSize(max, max2);
                    ImageWallpaper.GifGLEngine.this.mRenderer.setThumbnail(wallpaperBitmap, surfaceHolder2);
                    ImageWallpaperGifRenderer imageWallpaperGifRenderer = ImageWallpaper.GifGLEngine.this.mRenderer;
                    Rect rect2 = new Rect(0, 0, wallpaperBitmap.getWidth(), wallpaperBitmap.getHeight());
                    imageWallpaperGifRenderer.getClass();
                    Log.i("ImageWallpaperGifRenderer", "setBoundRect : " + rect2);
                    imageWallpaperGifRenderer.mBoundRect = rect2;
                } else {
                    Log.i("ImageWallpaper[GifGLEngine]", " bitmap is null");
                }
                ImageWallpaper.GifGLEngine gifGLEngine2 = ImageWallpaper.GifGLEngine.this;
                gifGLEngine2.mRenderer.setMediaPath(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperPath());
                ImageWallpaper.GifGLEngine gifGLEngine3 = ImageWallpaper.GifGLEngine.this;
                gifGLEngine3.mRenderer.updateGif(gifGLEngine3.getSurfaceHolder());
                ImageWallpaper imageWallpaper3 = ImageWallpaper.this;
                imageWallpaper3.mSubWallpaperType = ((CoverWallpaperController) imageWallpaper3.mCoverWallpaper).getWallpaperType();
                return;
            case 6:
                ImageWallpaper.VideoGLEngine videoGLEngine = (ImageWallpaper.VideoGLEngine) this.f$0;
                WallpaperManager wallpaperManager3 = (WallpaperManager) this.f$1;
                int coverWhich2 = ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getCoverWhich();
                if (wallpaperManager3.semGetWallpaperType(coverWhich2) != 3) {
                    videoFilePath = wallpaperManager3.getVideoFilePath(coverWhich2);
                    if (videoFilePath == null) {
                        Log.e("ImageWallpaper[VideoGLEngine]", "videoFilePath is NULL");
                        return;
                    }
                } else if (((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).isCoverWallpaperRequired()) {
                    videoFilePath = ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperPath();
                } else {
                    Log.w("ImageWallpaper[VideoGLEngine]", "multiple wallpaper isn't ready yet");
                    videoFilePath = null;
                }
                if (videoFilePath != null && !videoFilePath.isEmpty()) {
                    videoGLEngine.mRenderer.setMediaPath(videoFilePath);
                    ImageWallpaperVideoRenderer imageWallpaperVideoRenderer = videoGLEngine.mRenderer;
                    try {
                        mediaMetadataRetriever2 = new MediaMetadataRetriever();
                        mediaMetadataRetriever2.setDataSource(ImageWallpaper.this.getApplicationContext(), Uri.parse(videoFilePath));
                        str5 = mediaMetadataRetriever2.extractMetadata(1028);
                        try {
                            str6 = mediaMetadataRetriever2.extractMetadata(EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED);
                        } catch (IllegalArgumentException e) {
                            e = e;
                            str6 = null;
                            str7 = null;
                            str8 = null;
                            e.printStackTrace();
                            z2 = false;
                            StringBuilder sb3 = new StringBuilder("isHDRvideo : ");
                            sb3.append(z2);
                            sb3.append(" , ");
                            sb3.append(str6);
                            sb3.append(" , ");
                            AppOpItem$$ExternalSyntheticOutline0.m97m(sb3, str8, " , ", str5, " , ");
                            ExifInterface$$ExternalSyntheticOutline0.m35m(sb3, str7, "ImageWallpaper[VideoGLEngine]");
                            if (imageWallpaperVideoRenderer.mLayerContainer != null) {
                            }
                            Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                            return;
                        } catch (SecurityException e2) {
                            e = e2;
                            str6 = null;
                            str7 = null;
                            str8 = null;
                            e.printStackTrace();
                            z2 = false;
                            StringBuilder sb32 = new StringBuilder("isHDRvideo : ");
                            sb32.append(z2);
                            sb32.append(" , ");
                            sb32.append(str6);
                            sb32.append(" , ");
                            AppOpItem$$ExternalSyntheticOutline0.m97m(sb32, str8, " , ", str5, " , ");
                            ExifInterface$$ExternalSyntheticOutline0.m35m(sb32, str7, "ImageWallpaper[VideoGLEngine]");
                            if (imageWallpaperVideoRenderer.mLayerContainer != null) {
                            }
                            Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                            return;
                        }
                    } catch (IllegalArgumentException e3) {
                        e = e3;
                        str5 = null;
                    } catch (SecurityException e4) {
                        e = e4;
                        str5 = null;
                    }
                    try {
                        str8 = mediaMetadataRetriever2.extractMetadata(1022);
                        try {
                            str7 = mediaMetadataRetriever2.extractMetadata(1027);
                        } catch (IllegalArgumentException e5) {
                            e = e5;
                            str7 = null;
                        } catch (SecurityException e6) {
                            e = e6;
                            str7 = null;
                        }
                    } catch (IllegalArgumentException e7) {
                        e = e7;
                        str7 = null;
                        str8 = null;
                        e.printStackTrace();
                        z2 = false;
                        StringBuilder sb322 = new StringBuilder("isHDRvideo : ");
                        sb322.append(z2);
                        sb322.append(" , ");
                        sb322.append(str6);
                        sb322.append(" , ");
                        AppOpItem$$ExternalSyntheticOutline0.m97m(sb322, str8, " , ", str5, " , ");
                        ExifInterface$$ExternalSyntheticOutline0.m35m(sb322, str7, "ImageWallpaper[VideoGLEngine]");
                        if (imageWallpaperVideoRenderer.mLayerContainer != null) {
                        }
                        Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                        return;
                    } catch (SecurityException e8) {
                        e = e8;
                        str7 = null;
                        str8 = null;
                        e.printStackTrace();
                        z2 = false;
                        StringBuilder sb3222 = new StringBuilder("isHDRvideo : ");
                        sb3222.append(z2);
                        sb3222.append(" , ");
                        sb3222.append(str6);
                        sb3222.append(" , ");
                        AppOpItem$$ExternalSyntheticOutline0.m97m(sb3222, str8, " , ", str5, " , ");
                        ExifInterface$$ExternalSyntheticOutline0.m35m(sb3222, str7, "ImageWallpaper[VideoGLEngine]");
                        if (imageWallpaperVideoRenderer.mLayerContainer != null) {
                        }
                        Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                        return;
                    }
                    if (str6 != null) {
                        try {
                            parseInt3 = Integer.parseInt(str6);
                        } catch (IllegalArgumentException e9) {
                            e = e9;
                            e.printStackTrace();
                            z2 = false;
                            StringBuilder sb32222 = new StringBuilder("isHDRvideo : ");
                            sb32222.append(z2);
                            sb32222.append(" , ");
                            sb32222.append(str6);
                            sb32222.append(" , ");
                            AppOpItem$$ExternalSyntheticOutline0.m97m(sb32222, str8, " , ", str5, " , ");
                            ExifInterface$$ExternalSyntheticOutline0.m35m(sb32222, str7, "ImageWallpaper[VideoGLEngine]");
                            if (imageWallpaperVideoRenderer.mLayerContainer != null) {
                            }
                            Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                            return;
                        } catch (SecurityException e10) {
                            e = e10;
                            e.printStackTrace();
                            z2 = false;
                            StringBuilder sb322222 = new StringBuilder("isHDRvideo : ");
                            sb322222.append(z2);
                            sb322222.append(" , ");
                            sb322222.append(str6);
                            sb322222.append(" , ");
                            AppOpItem$$ExternalSyntheticOutline0.m97m(sb322222, str8, " , ", str5, " , ");
                            ExifInterface$$ExternalSyntheticOutline0.m35m(sb322222, str7, "ImageWallpaper[VideoGLEngine]");
                            if (imageWallpaperVideoRenderer.mLayerContainer != null) {
                            }
                            Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                            return;
                        }
                        if (str8 != null) {
                            parseInt4 = Integer.parseInt(str8);
                            z2 = ((parseInt3 != 0 || parseInt3 == 8) && DATA.DM_FIELD_INDEX.SMS_OVER_IMS.equals(str5) && (parseInt4 == 11 || parseInt4 == 10)) ? true : "yes".equals(str7);
                            StringBuilder sb3222222 = new StringBuilder("isHDRvideo : ");
                            sb3222222.append(z2);
                            sb3222222.append(" , ");
                            sb3222222.append(str6);
                            sb3222222.append(" , ");
                            AppOpItem$$ExternalSyntheticOutline0.m97m(sb3222222, str8, " , ", str5, " , ");
                            ExifInterface$$ExternalSyntheticOutline0.m35m(sb3222222, str7, "ImageWallpaper[VideoGLEngine]");
                            if (imageWallpaperVideoRenderer.mLayerContainer != null || (videoLayer2 = imageWallpaperVideoRenderer.mVideoLayer) == null) {
                                Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                                return;
                            } else {
                                videoLayer2.setHdrModeEnabled(z2, 0.7f, 1.6f);
                                return;
                            }
                        }
                    } else {
                        parseInt3 = -1;
                    }
                    parseInt4 = 0;
                    if (parseInt3 != 0) {
                    }
                    StringBuilder sb32222222 = new StringBuilder("isHDRvideo : ");
                    sb32222222.append(z2);
                    sb32222222.append(" , ");
                    sb32222222.append(str6);
                    sb32222222.append(" , ");
                    AppOpItem$$ExternalSyntheticOutline0.m97m(sb32222222, str8, " , ", str5, " , ");
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb32222222, str7, "ImageWallpaper[VideoGLEngine]");
                    if (imageWallpaperVideoRenderer.mLayerContainer != null) {
                    }
                    Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                    return;
                }
                AssetFileDescriptor videoFDFromPackage = WallpaperUtils.getVideoFDFromPackage(ImageWallpaper.this.getApplicationContext(), wallpaperManager3.getVideoPackage(coverWhich2), wallpaperManager3.getVideoFileName(coverWhich2));
                if (videoFDFromPackage == null) {
                    Log.e("ImageWallpaper[VideoGLEngine]", "fd is NULL");
                    return;
                }
                videoGLEngine.mRenderer.setMediaFd(videoFDFromPackage);
                ImageWallpaperVideoRenderer imageWallpaperVideoRenderer2 = videoGLEngine.mRenderer;
                try {
                    mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(videoFDFromPackage.getFileDescriptor(), videoFDFromPackage.getStartOffset(), videoFDFromPackage.getLength());
                    str = mediaMetadataRetriever.extractMetadata(1028);
                    try {
                        str2 = mediaMetadataRetriever.extractMetadata(EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED);
                    } catch (IllegalArgumentException e11) {
                        e = e11;
                        str2 = null;
                        str3 = null;
                        str4 = null;
                        e.printStackTrace();
                        z = false;
                        StringBuilder sb4 = new StringBuilder("isHDRvideo : ");
                        sb4.append(z);
                        sb4.append(" , ");
                        sb4.append(str2);
                        sb4.append(" , ");
                        AppOpItem$$ExternalSyntheticOutline0.m97m(sb4, str4, " , ", str, " , ");
                        ExifInterface$$ExternalSyntheticOutline0.m35m(sb4, str3, "ImageWallpaper[VideoGLEngine]");
                        if (imageWallpaperVideoRenderer2.mLayerContainer != null) {
                        }
                        Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                        return;
                    } catch (SecurityException e12) {
                        e = e12;
                        str2 = null;
                        str3 = null;
                        str4 = null;
                        e.printStackTrace();
                        z = false;
                        StringBuilder sb42 = new StringBuilder("isHDRvideo : ");
                        sb42.append(z);
                        sb42.append(" , ");
                        sb42.append(str2);
                        sb42.append(" , ");
                        AppOpItem$$ExternalSyntheticOutline0.m97m(sb42, str4, " , ", str, " , ");
                        ExifInterface$$ExternalSyntheticOutline0.m35m(sb42, str3, "ImageWallpaper[VideoGLEngine]");
                        if (imageWallpaperVideoRenderer2.mLayerContainer != null) {
                        }
                        Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                        return;
                    }
                } catch (IllegalArgumentException e13) {
                    e = e13;
                    str = null;
                } catch (SecurityException e14) {
                    e = e14;
                    str = null;
                }
                try {
                    str4 = mediaMetadataRetriever.extractMetadata(1022);
                    try {
                        str3 = mediaMetadataRetriever.extractMetadata(1027);
                    } catch (IllegalArgumentException e15) {
                        e = e15;
                        str3 = null;
                    } catch (SecurityException e16) {
                        e = e16;
                        str3 = null;
                    }
                } catch (IllegalArgumentException e17) {
                    e = e17;
                    str3 = null;
                    str4 = null;
                    e.printStackTrace();
                    z = false;
                    StringBuilder sb422 = new StringBuilder("isHDRvideo : ");
                    sb422.append(z);
                    sb422.append(" , ");
                    sb422.append(str2);
                    sb422.append(" , ");
                    AppOpItem$$ExternalSyntheticOutline0.m97m(sb422, str4, " , ", str, " , ");
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb422, str3, "ImageWallpaper[VideoGLEngine]");
                    if (imageWallpaperVideoRenderer2.mLayerContainer != null) {
                    }
                    Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                    return;
                } catch (SecurityException e18) {
                    e = e18;
                    str3 = null;
                    str4 = null;
                    e.printStackTrace();
                    z = false;
                    StringBuilder sb4222 = new StringBuilder("isHDRvideo : ");
                    sb4222.append(z);
                    sb4222.append(" , ");
                    sb4222.append(str2);
                    sb4222.append(" , ");
                    AppOpItem$$ExternalSyntheticOutline0.m97m(sb4222, str4, " , ", str, " , ");
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb4222, str3, "ImageWallpaper[VideoGLEngine]");
                    if (imageWallpaperVideoRenderer2.mLayerContainer != null) {
                    }
                    Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                    return;
                }
                if (str2 != null) {
                    try {
                        parseInt = Integer.parseInt(str2);
                    } catch (IllegalArgumentException e19) {
                        e = e19;
                        e.printStackTrace();
                        z = false;
                        StringBuilder sb42222 = new StringBuilder("isHDRvideo : ");
                        sb42222.append(z);
                        sb42222.append(" , ");
                        sb42222.append(str2);
                        sb42222.append(" , ");
                        AppOpItem$$ExternalSyntheticOutline0.m97m(sb42222, str4, " , ", str, " , ");
                        ExifInterface$$ExternalSyntheticOutline0.m35m(sb42222, str3, "ImageWallpaper[VideoGLEngine]");
                        if (imageWallpaperVideoRenderer2.mLayerContainer != null) {
                        }
                        Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                        return;
                    } catch (SecurityException e20) {
                        e = e20;
                        e.printStackTrace();
                        z = false;
                        StringBuilder sb422222 = new StringBuilder("isHDRvideo : ");
                        sb422222.append(z);
                        sb422222.append(" , ");
                        sb422222.append(str2);
                        sb422222.append(" , ");
                        AppOpItem$$ExternalSyntheticOutline0.m97m(sb422222, str4, " , ", str, " , ");
                        ExifInterface$$ExternalSyntheticOutline0.m35m(sb422222, str3, "ImageWallpaper[VideoGLEngine]");
                        if (imageWallpaperVideoRenderer2.mLayerContainer != null) {
                        }
                        Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                        return;
                    }
                    if (str4 != null) {
                        parseInt2 = Integer.parseInt(str4);
                        z = ((parseInt != 0 || parseInt == 8) && DATA.DM_FIELD_INDEX.SMS_OVER_IMS.equals(str) && (parseInt2 == 11 || parseInt2 == 10)) ? true : "yes".equals(str3);
                        StringBuilder sb4222222 = new StringBuilder("isHDRvideo : ");
                        sb4222222.append(z);
                        sb4222222.append(" , ");
                        sb4222222.append(str2);
                        sb4222222.append(" , ");
                        AppOpItem$$ExternalSyntheticOutline0.m97m(sb4222222, str4, " , ", str, " , ");
                        ExifInterface$$ExternalSyntheticOutline0.m35m(sb4222222, str3, "ImageWallpaper[VideoGLEngine]");
                        if (imageWallpaperVideoRenderer2.mLayerContainer != null || (videoLayer = imageWallpaperVideoRenderer2.mVideoLayer) == null) {
                            Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                            return;
                        } else {
                            videoLayer.setHdrModeEnabled(z, 0.7f, 1.6f);
                            return;
                        }
                    }
                } else {
                    parseInt = -1;
                }
                parseInt2 = 0;
                if (parseInt != 0) {
                }
                StringBuilder sb42222222 = new StringBuilder("isHDRvideo : ");
                sb42222222.append(z);
                sb42222222.append(" , ");
                sb42222222.append(str2);
                sb42222222.append(" , ");
                AppOpItem$$ExternalSyntheticOutline0.m97m(sb42222222, str4, " , ", str, " , ");
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb42222222, str3, "ImageWallpaper[VideoGLEngine]");
                if (imageWallpaperVideoRenderer2.mLayerContainer != null) {
                }
                Log.e("ImageWallpaperVideoRenderer", "Cannot set hdr mode enabled. Layer is null.");
                return;
            case 7:
                ImageWallpaper.VideoGLEngine videoGLEngine2 = (ImageWallpaper.VideoGLEngine) this.f$0;
                SurfaceHolder surfaceHolder3 = (SurfaceHolder) this.f$1;
                ImageWallpaperVideoRenderer imageWallpaperVideoRenderer3 = videoGLEngine2.mRenderer;
                imageWallpaperVideoRenderer3.getClass();
                Log.i("ImageWallpaperVideoRenderer", "onSurfaceCreated");
                imageWallpaperVideoRenderer3.mEglHelper.init(surfaceHolder3, false);
                imageWallpaperVideoRenderer3.mSurfaceHolder = surfaceHolder3;
                return;
            default:
                ImageWallpaper.VideoGLEngine.C37271 c37271 = (ImageWallpaper.VideoGLEngine.C37271) this.f$0;
                Boolean bool3 = (Boolean) this.f$1;
                if (ImageWallpaper.VideoGLEngine.this.mRenderer == null) {
                    Log.w("ImageWallpaper[VideoGLEngine]", " mPluginVideoWallpaperConsumer, skip, renderer is null");
                    return;
                }
                Log.i("ImageWallpaper[VideoGLEngine]", " mPluginVideoWallpaperConsumer type previous " + ImageWallpaper.this.mSubWallpaperType + ", current " + ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType());
                if (bool3.booleanValue()) {
                    ImageWallpaper.VideoGLEngine videoGLEngine3 = ImageWallpaper.VideoGLEngine.this;
                    videoGLEngine3.mRenderer.setBoundRect(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperRect());
                    ImageWallpaper.VideoGLEngine videoGLEngine4 = ImageWallpaper.VideoGLEngine.this;
                    videoGLEngine4.mRenderer.setMediaPath(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperPath());
                    ImageWallpaper imageWallpaper4 = ImageWallpaper.this;
                    imageWallpaper4.mSubWallpaperType = ((CoverWallpaperController) imageWallpaper4.mCoverWallpaper).getWallpaperType();
                    return;
                }
                ImageWallpaper.VideoGLEngine videoGLEngine5 = ImageWallpaper.VideoGLEngine.this;
                HandlerThread handlerThread3 = ImageWallpaper.this.mWorker;
                if (handlerThread3 == null) {
                    return;
                }
                if (handlerThread3 != null) {
                    handlerThread3.getThreadHandler().removeCallbacks(videoGLEngine5.mPluginUpdateTask);
                }
                ImageWallpaper.this.mWorker.getThreadHandler().post(videoGLEngine5.mPluginUpdateTask);
                return;
        }
    }
}
