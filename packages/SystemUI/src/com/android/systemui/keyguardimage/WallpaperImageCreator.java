package com.android.systemui.keyguardimage;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.material3.internal.colorUtil.Frame$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.utils.BitmapUtils;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class WallpaperImageCreator implements ImageCreator {
    public final String TAG;
    public final Context mContext;
    public final CoverWallpaper mCoverWallpaper;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public final PluginWallpaperManager mPluginWallpaperManager;
    protected final SettingsHelper mSettingsHelper;

    public WallpaperImageCreator(String str, Context context, SettingsHelper settingsHelper, PluginWallpaperManager pluginWallpaperManager, CoverWallpaper coverWallpaper, KeyguardWallpaper keyguardWallpaper) {
        this.TAG = str;
        this.mContext = context;
        this.mSettingsHelper = settingsHelper;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mCoverWallpaper = coverWallpaper;
        this.mKeyguardWallpaper = keyguardWallpaper;
    }

    /* JADX WARN: Removed duplicated region for block: B:150:0x03b0 A[Catch: Exception -> 0x0397, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x0397, blocks: (B:132:0x036e, B:139:0x0393, B:146:0x03a5, B:150:0x03b0, B:157:0x03be, B:156:0x03bb, B:134:0x0376, B:136:0x0385, B:138:0x038d, B:145:0x039c, B:148:0x03a9, B:153:0x03b6), top: B:173:0x036e, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x03e2  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0376 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01a9  */
    @Override // com.android.systemui.keyguardimage.ImageCreator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) throws NoSuchMethodException, IOException, SecurityException {
        Drawable drawableSemGetDrawable;
        ParcelFileDescriptor parcelFileDescriptorSemGetThumbnailFileDescriptor;
        float f;
        float f2;
        Bundle bundleNotifyEvent;
        int wallpaperType;
        Bitmap videoFrame;
        Rect fbeWallpaperRect;
        Bitmap bitmapDecodeFileDescriptor;
        ParcelFileDescriptor parcelFileDescriptorSemGetScreenshotFileDescriptor;
        int i = WallpaperUtils.sCurrentWhich;
        int i2 = imageOption.which;
        if (i2 != -1) {
            i = i2;
        }
        int iSemGetCurrentUser = ActivityManager.semGetCurrentUser();
        CoverWallpaperController coverWallpaperController = (CoverWallpaperController) this.mCoverWallpaper;
        boolean zIsCoverWallpaperRequired = coverWallpaperController.isCoverWallpaperRequired();
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        boolean zIsDynamicWallpaperEnabled = pluginWallpaperManager.isDynamicWallpaperEnabled();
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, iSemGetCurrentUser, "createImage: which = ", ", userId = ", ", imageOption.type = ");
        sbM.append(imageOption.type);
        sbM.append(", imageOption.rotation = ");
        sbM.append(imageOption.rotation);
        sbM.append(", imageOption.DisplayType = ");
        sbM.append(imageOption.displayType);
        sbM.append(", isCoverWallpaper = ");
        sbM.append(zIsCoverWallpaperRequired);
        sbM.append(", isDynamicLockWallpaper = ");
        sbM.append(zIsDynamicWallpaperEnabled);
        String string = sbM.toString();
        String str = this.TAG;
        Log.i(str, string);
        if (WallpaperUtils.mIsUltraPowerSavingMode || WallpaperUtils.mIsEmergencyMode) {
            Log.i(str, "createBlackWallpaper");
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(imageOption.width, imageOption.height, Bitmap.Config.ARGB_8888);
            new Canvas(bitmapCreateBitmap).drawColor(-16777216);
            return bitmapCreateBitmap;
        }
        if (imageOption.type == 5) {
            if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                i = 33;
            } else if (LsRune.WALLPAPER_SUB_WATCHFACE) {
                i = 17;
            }
        }
        int i3 = i;
        if (imageOption.rotation < 0) {
            imageOption.rotation = this.mContext.getDisplay().getRotation();
        }
        Bitmap bitmapFromPath = null;
        if (imageOption.useScreenshot) {
            Log.i(str, "captureWallpaper");
            try {
                parcelFileDescriptorSemGetScreenshotFileDescriptor = WallpaperManager.getInstance(this.mContext).semGetScreenshotFileDescriptor(i3, iSemGetCurrentUser, null);
            } catch (Exception e) {
                e = e;
                bitmapDecodeFileDescriptor = null;
            }
            try {
                if (parcelFileDescriptorSemGetScreenshotFileDescriptor == null) {
                    Log.e(str, "captureWallpaper: failed to get screenshot");
                    if (parcelFileDescriptorSemGetScreenshotFileDescriptor != null) {
                        parcelFileDescriptorSemGetScreenshotFileDescriptor.close();
                    }
                    bitmapDecodeFileDescriptor = null;
                } else {
                    bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptorSemGetScreenshotFileDescriptor.getFileDescriptor(), null, new BitmapFactory.Options());
                    try {
                        parcelFileDescriptorSemGetScreenshotFileDescriptor.close();
                    } catch (Exception e2) {
                        e = e2;
                        WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("captureWallpaper: e = ", e, str, e);
                        if (bitmapDecodeFileDescriptor != null) {
                        }
                        if (imageOption.type == 5) {
                            wallpaperType = coverWallpaperController.getWallpaperType();
                            if (wallpaperType != 13) {
                                videoFrame = WallpaperUtils.getVideoFrame(this.mContext, null, coverWallpaperController.getWallpaperPath());
                                if (!coverWallpaperController.isFbeAvailable()) {
                                }
                                if (fbeWallpaperRect != null) {
                                    if (fbeWallpaperRect.isEmpty()) {
                                    }
                                }
                                if (videoFrame != null) {
                                }
                            }
                        }
                        if (zIsDynamicWallpaperEnabled) {
                        }
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
                        try {
                            parcelFileDescriptorSemGetThumbnailFileDescriptor = wallpaperManager.semGetThumbnailFileDescriptor(i3, iSemGetCurrentUser, imageOption.rotation);
                            if (parcelFileDescriptorSemGetThumbnailFileDescriptor != null) {
                            }
                            Log.w(str, "createImage: failed to get thumbnail");
                            if (parcelFileDescriptorSemGetThumbnailFileDescriptor != null) {
                            }
                        } catch (Exception e3) {
                            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("createImage: e = ", e3, str, e3);
                        }
                        drawableSemGetDrawable = wallpaperManager.semGetDrawable(i3);
                        if (!(drawableSemGetDrawable instanceof BitmapDrawable)) {
                        }
                    }
                }
                if (bitmapDecodeFileDescriptor != null) {
                    return makeResult(bitmapDecodeFileDescriptor, imageOption.width, imageOption.height, 0);
                }
            } finally {
            }
        }
        if (imageOption.type == 5 && zIsCoverWallpaperRequired) {
            wallpaperType = coverWallpaperController.getWallpaperType();
            if (wallpaperType != 13 || wallpaperType == 23) {
                videoFrame = WallpaperUtils.getVideoFrame(this.mContext, null, coverWallpaperController.getWallpaperPath());
                fbeWallpaperRect = !coverWallpaperController.isFbeAvailable() ? coverWallpaperController.mPluginWallpaperManager.getFbeWallpaperRect(1) : coverWallpaperController.mPluginWallpaperManager.getHomeWallpaperRect(CoverWallpaperController.getCoverMode());
                if (fbeWallpaperRect != null && fbeWallpaperRect.width() > 0 && fbeWallpaperRect.height() > 0 && videoFrame != null) {
                    if (fbeWallpaperRect.isEmpty()) {
                        Point realScreenSize = WallpaperUtils.getRealScreenSize(this.mContext, true);
                        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(realScreenSize.x, realScreenSize.y, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmapCreateBitmap2);
                        float width = videoFrame.getWidth() / fbeWallpaperRect.width();
                        float f3 = 1.0f / width;
                        canvas.scale(f3, f3);
                        canvas.translate(fbeWallpaperRect.left * width, fbeWallpaperRect.top * width);
                        canvas.drawBitmap(videoFrame, 0.0f, 0.0f, (Paint) null);
                        videoFrame.recycle();
                        videoFrame = bitmapCreateBitmap2;
                    } else {
                        Log.d(str, "getCroppedBitmapForCoverScreen: Invalid params.");
                    }
                }
            } else {
                videoFrame = coverWallpaperController.getWallpaperBitmap();
                ArrayList cropHints = IntelligentCropHelper.parseCropHints(coverWallpaperController.getWallpaperIntelligentCrop());
                if (videoFrame != null && cropHints != null && !cropHints.isEmpty()) {
                    Rect rect = (Rect) cropHints.getFirst();
                    rect.intersect(new Rect(0, 0, videoFrame.getWidth(), videoFrame.getHeight()));
                    videoFrame = Bitmap.createBitmap(videoFrame, rect.left, rect.top, rect.width(), rect.height());
                }
            }
            if (videoFrame != null) {
                return BitmapUtils.fitToCoverScreen(this.mContext, videoFrame);
            }
        }
        if (zIsDynamicWallpaperEnabled || imageOption.type == 5) {
            WallpaperManager wallpaperManager2 = WallpaperManager.getInstance(this.mContext);
            parcelFileDescriptorSemGetThumbnailFileDescriptor = wallpaperManager2.semGetThumbnailFileDescriptor(i3, iSemGetCurrentUser, imageOption.rotation);
            if (parcelFileDescriptorSemGetThumbnailFileDescriptor != null) {
                try {
                    Bitmap bitmapDecodeFileDescriptor2 = BitmapFactory.decodeFileDescriptor(parcelFileDescriptorSemGetThumbnailFileDescriptor.getFileDescriptor(), null, new BitmapFactory.Options());
                    if (bitmapDecodeFileDescriptor2 != null) {
                        if (WhichChecker.isWatchFace(imageOption.displayType)) {
                            Bitmap bitmapFitToCoverScreen = BitmapUtils.fitToCoverScreen(this.mContext, bitmapDecodeFileDescriptor2);
                            parcelFileDescriptorSemGetThumbnailFileDescriptor.close();
                            return bitmapFitToCoverScreen;
                        }
                        Bitmap bitmapMakeResult = makeResult(bitmapDecodeFileDescriptor2, imageOption.width, imageOption.height, 0);
                        parcelFileDescriptorSemGetThumbnailFileDescriptor.close();
                        return bitmapMakeResult;
                    }
                } finally {
                }
            }
            Log.w(str, "createImage: failed to get thumbnail");
            if (parcelFileDescriptorSemGetThumbnailFileDescriptor != null) {
                parcelFileDescriptorSemGetThumbnailFileDescriptor.close();
            }
            drawableSemGetDrawable = wallpaperManager2.semGetDrawable(i3);
            if (!(drawableSemGetDrawable instanceof BitmapDrawable)) {
                Log.i(str, "createImage: bitmap from wallpaper manager");
                return makeResult(((BitmapDrawable) drawableSemGetDrawable).getBitmap(), imageOption.width, imageOption.height, imageOption.rotation);
            }
            Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap(imageOption.width, imageOption.height, Bitmap.Config.ARGB_8888);
            if (drawableSemGetDrawable == null) {
                Log.w(str, "createImage: return blank bitmap");
                return bitmapCreateBitmap3;
            }
            Canvas canvas2 = new Canvas(bitmapCreateBitmap3);
            drawableSemGetDrawable.setBounds(0, 0, canvas2.getWidth(), canvas2.getHeight());
            drawableSemGetDrawable.draw(canvas2);
            return makeResult(bitmapCreateBitmap3, imageOption.width, imageOption.height, imageOption.rotation);
        }
        int i4 = imageOption.rotation;
        String wallpaperIntelligentCrop = pluginWallpaperManager.getWallpaperIntelligentCrop(WallpaperUtils.isSubDisplay() ? 1 : 0);
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("getDlsThumbnail: iCrops = ", wallpaperIntelligentCrop, str);
        Point displaySize = DisplayUtils.getDisplaySize(this.mContext, i3, i4);
        Rect nearestCropHint = !TextUtils.isEmpty(wallpaperIntelligentCrop) ? IntelligentCropHelper.getNearestCropHint(displaySize, IntelligentCropHelper.parseCropHints(wallpaperIntelligentCrop)) : null;
        boolean z = (nearestCropHint == null || nearestCropHint.isEmpty()) ? false : true;
        if (pluginWallpaperManager.isVideoWallpaperEnabled()) {
            String wallpaperPath = pluginWallpaperManager.getWallpaperPath();
            Uri wallpaperUri = pluginWallpaperManager.getWallpaperUri();
            if (wallpaperPath == null && wallpaperUri == null) {
                Log.w(str, "getDlsThumbnail: no video wallpaper data");
            } else {
                KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
                Log.i(str, "getDlsThumbnail: semIsKeyguardShowingAndNotOccluded() = " + keyguardManager.semIsKeyguardShowingAndNotOccluded());
                if (!keyguardManager.semIsKeyguardShowingAndNotOccluded() && (bundleNotifyEvent = ((KeyguardWallpaperController) this.mKeyguardWallpaper).notifyEvent(616)) != null) {
                    bitmapFromPath = (Bitmap) bundleNotifyEvent.getParcelable("wallpaper_bitmap", Bitmap.class);
                }
                if (bitmapFromPath == null) {
                    Log.w(str, "getDlsThumbnail: View or player is null, get frame from retriever. path = " + wallpaperPath + ", uri = " + wallpaperUri);
                    bitmapFromPath = WallpaperUtils.getVideoFrame(this.mContext, wallpaperUri, wallpaperPath);
                }
            }
        } else {
            bitmapFromPath = pluginWallpaperManager.isWallpaperSrcPath() ? pluginWallpaperManager.getBitmapFromPath(pluginWallpaperManager.getWallpaperPath(), z) : pluginWallpaperManager.isWallpaperSrcUri() ? pluginWallpaperManager.getBitmapFromUri(pluginWallpaperManager.getWallpaperUri(), z) : pluginWallpaperManager.getWallpaperBitmap();
        }
        if (bitmapFromPath != null && z) {
            try {
                nearestCropHint.intersect(0, 0, bitmapFromPath.getWidth(), bitmapFromPath.getHeight());
                Matrix matrix = new Matrix();
                Paint paint = new Paint(2);
                Bitmap bitmapCreateBitmap4 = Bitmap.createBitmap(displaySize.x, displaySize.y, Bitmap.Config.ARGB_8888);
                Canvas canvas3 = new Canvas(bitmapCreateBitmap4);
                int iWidth = nearestCropHint.width();
                int iHeight = nearestCropHint.height();
                int i5 = displaySize.x;
                int i6 = displaySize.y;
                if (iWidth * i6 > i5 * iHeight) {
                    f = i6;
                    f2 = iHeight;
                } else {
                    f = i5;
                    f2 = iWidth;
                }
                float f4 = f / f2;
                float fM = Frame$$ExternalSyntheticOutline0.m(iWidth, f4, i5, 0.5f);
                float fM2 = Frame$$ExternalSyntheticOutline0.m(iHeight, f4, i6, 0.5f);
                matrix.setScale(f4, f4);
                matrix.preTranslate(-nearestCropHint.left, -nearestCropHint.top);
                matrix.postTranslate(Math.round(fM), Math.round(fM2));
                canvas3.drawBitmap(bitmapFromPath, matrix, paint);
                Log.i(str, "getDlsThumbnail: src = " + nearestCropHint + ", dx = " + fM + ", dy = " + fM2 + ", displaySize.x = " + displaySize.x + ", displaySize.y = " + displaySize.y + ", scale = " + f4);
                bitmapFromPath.recycle();
                bitmapFromPath = bitmapCreateBitmap4;
            } catch (NullPointerException e4) {
                Log.e(str, "getDlsThumbnail: e = " + e4, e4);
            }
        }
        if (bitmapFromPath == null || bitmapFromPath.isRecycled()) {
            Log.w(str, "createImage: return DLS blank bitmap");
            bitmapFromPath = Bitmap.createBitmap(imageOption.width, imageOption.height, Bitmap.Config.ARGB_8888);
        }
        return makeResult(bitmapFromPath, imageOption.width, imageOption.height, 0);
    }

    public final Bitmap makeResult(Bitmap bitmap, int i, int i2, int i3) {
        Bitmap bitmapCreateBitmap = bitmap;
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "makeResult: w = ", ", h = ", ", rotation = ");
        sbM.append(i3);
        String string = sbM.toString();
        String str = this.TAG;
        Log.i(str, string);
        if (bitmapCreateBitmap == null || bitmapCreateBitmap.isRecycled()) {
            Log.e(str, "makeResult: thumbnail is invalid");
            return null;
        }
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        if (bitmapCreateBitmap.isRecycled()) {
            bitmapCreateBitmap = null;
        } else {
            int width = bitmapCreateBitmap.getWidth();
            int height = bitmapCreateBitmap.getHeight();
            float f = width;
            float f2 = f / 2.0f;
            float f3 = height;
            float f4 = f3 / 2.0f;
            float f5 = width * i2 > i * height ? (i2 / f3) * 1.0f : (i / f) * 1.0f;
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i, "metricsHeight=", " metricsWidth=", "WallpaperUtils");
            float f6 = (i * 1.0f) / f5;
            float f7 = (i2 * 1.0f) / f5;
            float f8 = f2 - (f6 / 2.0f);
            if (f8 < 0.0f) {
                f8 = 0.0f;
            }
            float f9 = f4 - (f7 / 2.0f);
            float f10 = f9 >= 0.0f ? f9 : 0.0f;
            Log.d("WallpaperUtils", "widthOrigin = " + width);
            Log.d("WallpaperUtils", "heightOrigin = " + height);
            Log.d("WallpaperUtils", "scale = " + f5);
            Log.d("WallpaperUtils", "centerX = " + f2);
            Log.d("WallpaperUtils", "centerY = " + f4);
            Log.d("WallpaperUtils", "startX = " + f8);
            Log.d("WallpaperUtils", "startY = " + f10);
            Log.d("WallpaperUtils", "width = " + f6);
            Log.d("WallpaperUtils", "height = " + f7);
            if (Math.round(f8) == 0 && Math.round(f10) == 0 && width == Math.round(f6) && height == Math.round(f7)) {
                Log.d("WallpaperUtils", "It doesn't need to crop bitmap");
            } else {
                if (Math.round(f6) < 1 || Math.round(f7) < 1 || i < 1 || i2 < 1) {
                    Log.d("WallpaperUtils", "Math.round(width) < 1 || Math.round(height) < 1 || mMatricsWidth < 1 || mMatricsHeight < 1");
                } else {
                    if (Math.round(f8) + Math.round(f6) <= width) {
                        if (Math.round(f10) + Math.round(f7) <= height) {
                            Log.d("WallpaperUtils", "Cropping...");
                            bitmapCreateBitmap = Bitmap.createBitmap(bitmapCreateBitmap, Math.round(f8), Math.round(f10), Math.round(f6), Math.round(f7));
                        }
                    }
                    Log.d("WallpaperUtils", "Calculated crop size error");
                }
                bitmapCreateBitmap = null;
            }
        }
        return i3 <= 0 ? bitmapCreateBitmap : WallpaperUtils.getRotatedBitmap(bitmapCreateBitmap, i3);
    }
}
