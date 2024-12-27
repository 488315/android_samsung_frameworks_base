package com.android.systemui.wallpapers;

import android.content.res.Configuration;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper;
import com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpapers.ImageWallpaper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda7(ImageWallpaper.BaseEngine baseEngine, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = baseEngine;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.CanvasEngine canvasEngine = (ImageWallpaper.CanvasEngine) this.f$0;
                Configuration configuration = (Configuration) this.f$1;
                boolean z = true;
                if (!(!canvasEngine.mIsEngineAlive)) {
                    canvasEngine.mRotation = configuration.windowConfiguration.getRotation();
                    ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = canvasEngine.mHelper;
                    imageWallpaperCanvasHelper.getClass();
                    StringBuilder sb = new StringBuilder();
                    boolean z2 = false;
                    boolean z3 = (configuration.uiMode & 32) != 0;
                    if (imageWallpaperCanvasHelper.mIsNightModeOn != z3) {
                        imageWallpaperCanvasHelper.mIsNightModeOn = z3;
                        sb.append(" Dark Mode change " + imageWallpaperCanvasHelper.mIsNightModeOn);
                        z2 = true;
                    }
                    if (imageWallpaperCanvasHelper.mCurDensityDpi != configuration.densityDpi) {
                        sb.append(" onConfigurationChanged  mCurDensityDpi " + imageWallpaperCanvasHelper.mCurDensityDpi + " -> " + configuration.densityDpi);
                        imageWallpaperCanvasHelper.mCurDensityDpi = configuration.densityDpi;
                        z2 = true;
                    }
                    int i = configuration.orientation;
                    sb.append(" onConfigurationChanged   " + configuration);
                    int i2 = imageWallpaperCanvasHelper.mOrientation;
                    ImageSmartCropper imageSmartCropper = imageWallpaperCanvasHelper.mImageSmartCropper;
                    if (i2 != i) {
                        imageWallpaperCanvasHelper.mOrientation = i;
                        if (!imageWallpaperCanvasHelper.hasIntelligentCropHints(imageWallpaperCanvasHelper.getCurrentWhich()) && imageSmartCropper != null && imageSmartCropper.needToSmartCrop()) {
                            ImageSmartCropper.checkDisplaySize(configuration);
                            z2 = true;
                        }
                    }
                    boolean z4 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
                    if (z4) {
                        sb.append(" ,  DeviceDisplay type " + imageWallpaperCanvasHelper.mDeviceDisplayType + " -> " + configuration.semDisplayDeviceType);
                        if (imageWallpaperCanvasHelper.mDeviceDisplayType != configuration.semDisplayDeviceType) {
                            if (imageWallpaperCanvasHelper.mLidState != imageWallpaperCanvasHelper.mWallpaperManager.getLidState() && ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isDualView()) {
                                sb.append(", Dual dex mode . Update Now. " + ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mLidState) + " -> " + ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mWallpaperManager.getLidState()));
                                imageWallpaperCanvasHelper.setLidState(imageWallpaperCanvasHelper.mWallpaperManager.getLidState());
                                z2 = true;
                            }
                            if (imageSmartCropper != null && imageSmartCropper.needToSmartCrop()) {
                                ImageSmartCropper.checkDisplaySize(configuration);
                            }
                            imageWallpaperCanvasHelper.mDeviceDisplayType = configuration.semDisplayDeviceType;
                        }
                    }
                    ((WallpaperLoggerImpl) imageWallpaperCanvasHelper.mLoggerWrapper).log(imageWallpaperCanvasHelper.TAG, sb.toString() + " isNeedReDraw " + z2);
                    if (z2) {
                        int currentWhich = canvasEngine.mHelper.getCurrentWhich();
                        if (z4 && !LsRune.SUBSCREEN_WATCHFACE) {
                            z = true ^ canvasEngine.updateSurfaceSizeIfNeed(currentWhich);
                        }
                        if (z) {
                            canvasEngine.updateRendering(currentWhich);
                            break;
                        }
                    }
                }
                break;
            case 1:
                ImageWallpaper.CanvasEngine.AnonymousClass7 anonymousClass7 = (ImageWallpaper.CanvasEngine.AnonymousClass7) this.f$0;
                Boolean bool = (Boolean) this.f$1;
                ImageWallpaper.CanvasEngine canvasEngine2 = ImageWallpaper.CanvasEngine.this;
                if (!(!canvasEngine2.mIsEngineAlive)) {
                    Log.i(canvasEngine2.TAG, " mPluginHomeWallpaperConsumer " + ImageWallpaper.this.mSubWallpaperType + " -> " + ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType() + " , " + bool);
                    ImageWallpaper imageWallpaper = ImageWallpaper.this;
                    imageWallpaper.mSubWallpaperType = ((CoverWallpaperController) imageWallpaper.mCoverWallpaper).getWallpaperType();
                    ImageWallpaper.CanvasEngine.m2376$$Nest$mupdatePluginWallpaper(ImageWallpaper.CanvasEngine.this);
                    break;
                } else {
                    Log.w(canvasEngine2.TAG, " mPluginHomeWallpaperConsumer, skip, engine is destroyed");
                    break;
                }
            default:
                ImageWallpaper.IntegratedEngine integratedEngine = (ImageWallpaper.IntegratedEngine) this.f$0;
                WallpaperService.Engine.SurfaceData surfaceData = (WallpaperService.Engine.SurfaceData) this.f$1;
                Log.d(integratedEngine.TAG, "recreateSurfaceControl: releasing older surface control" + surfaceData);
                integratedEngine.semReleaseSurface(surfaceData);
                break;
        }
    }

    public /* synthetic */ ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda7(ImageWallpaper.CanvasEngine.AnonymousClass7 anonymousClass7, Boolean bool) {
        this.$r8$classId = 1;
        this.f$0 = anonymousClass7;
        this.f$1 = bool;
    }
}
