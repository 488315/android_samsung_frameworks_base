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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
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
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // com.android.systemui.keyguardimage.ImageCreator
    public Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) {
        boolean z = WallpaperUtils.mIsUltraPowerSavingMode;
        String str = this.TAG;
        if (z || WallpaperUtils.mIsEmergencyMode) {
            Log.i(str, "createImage: Black bitmap.");
            Bitmap createBitmap = Bitmap.createBitmap(imageOption.width, imageOption.height, Bitmap.Config.ARGB_8888);
            new Canvas(createBitmap).drawColor(-16777216);
            return createBitmap;
        }
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
        int i = WallpaperUtils.sCurrentWhich;
        if (imageOption.type == 5) {
            i = 33;
        }
        int semGetWallpaperType = wallpaperManager.semGetWallpaperType(i);
        boolean isCoverWallpaperRequired = ((CoverWallpaperController) this.mCoverWallpaper).isCoverWallpaperRequired();
        boolean isDynamicWallpaperEnabled = this.mPluginWallpaperManager.isDynamicWallpaperEnabled();
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "createImage: which = ", ", imageOption.type = ");
        m.append(imageOption.type);
        m.append(", imageOption.DisplayType = ");
        m.append(imageOption.displayType);
        m.append(", isCoverWallpaper = ");
        m.append(isCoverWallpaperRequired);
        m.append(", isDynamicLockWallpaper = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(m, isDynamicWallpaperEnabled, str);
        try {
        } catch (Exception e) {
            Log.e(str, "createImage: e=" + e, e);
        }
        if (imageOption.type == 5) {
            return BitmapUtils.fitToCoverScreen(this.mContext, isCoverWallpaperRequired ? getDlsCoverWallpaperBitmap() : getCoverWallpaperBitmap(semGetWallpaperType, imageOption.displayType));
        }
        if (isDynamicWallpaperEnabled) {
            Log.d(str, "createImage: bitmap from dls");
            return makeResult(getDlsWallpaperBitmap(imageOption), imageOption.width, imageOption.height, imageOption.rotation);
        }
        if (imageOption.useScreenshot) {
            if (imageOption.rotation == -1) {
                imageOption.rotation = this.mContext.getDisplay().getRotation();
            }
            ParcelFileDescriptor semGetScreenshotFileDescriptor = wallpaperManager.semGetScreenshotFileDescriptor(i, ActivityManager.semGetCurrentUser(), null);
            try {
                if (semGetScreenshotFileDescriptor == null) {
                    Log.e(str, "createImage: failed to get screenshot");
                } else {
                    Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(semGetScreenshotFileDescriptor.getFileDescriptor(), null, new BitmapFactory.Options());
                    if (decodeFileDescriptor != null) {
                        Log.d(str, "createImage: live screenshot = " + decodeFileDescriptor);
                        Bitmap makeResult = makeResult(decodeFileDescriptor, imageOption.width, imageOption.height, imageOption.rotation);
                        semGetScreenshotFileDescriptor.close();
                        return makeResult;
                    }
                }
                if (semGetScreenshotFileDescriptor != null) {
                    semGetScreenshotFileDescriptor.close();
                }
            } finally {
            }
        }
        if (imageOption.rotation == -1) {
            imageOption.rotation = this.mContext.getDisplay().getRotation();
        }
        try {
            ParcelFileDescriptor semGetThumbnailFileDescriptor = wallpaperManager.semGetThumbnailFileDescriptor(i, ActivityManager.semGetCurrentUser(), imageOption.rotation);
            try {
                if (semGetThumbnailFileDescriptor == null) {
                    Log.e(str, "createImage: failed to get thumbnail");
                } else {
                    Bitmap decodeFileDescriptor2 = BitmapFactory.decodeFileDescriptor(semGetThumbnailFileDescriptor.getFileDescriptor(), null, new BitmapFactory.Options());
                    if (decodeFileDescriptor2 != null) {
                        Log.d(str, "createImage: thumbnail = " + decodeFileDescriptor2);
                        Bitmap makeResult2 = makeResult(decodeFileDescriptor2, imageOption.width, imageOption.height, 0);
                        semGetThumbnailFileDescriptor.close();
                        return makeResult2;
                    }
                }
                if (semGetThumbnailFileDescriptor != null) {
                    semGetThumbnailFileDescriptor.close();
                }
            } finally {
            }
        } catch (Exception e2) {
            Log.e(str, "createImage: e=" + e2, e2);
        }
        Drawable semGetDrawable = wallpaperManager.semGetDrawable(i);
        if (semGetDrawable instanceof BitmapDrawable) {
            Log.d(str, "createImage: bitmap from wallpaper manager");
            return makeResult(((BitmapDrawable) semGetDrawable).getBitmap(), imageOption.width, imageOption.height, imageOption.rotation);
        }
        Bitmap createBitmap2 = Bitmap.createBitmap(imageOption.width, imageOption.height, Bitmap.Config.ARGB_8888);
        if (semGetDrawable == null) {
            Log.w(str, "no bitmap.");
            return createBitmap2;
        }
        Canvas canvas = new Canvas(createBitmap2);
        semGetDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        semGetDrawable.draw(canvas);
        return makeResult(createBitmap2, imageOption.width, imageOption.height, imageOption.rotation);
    }

    public final Bitmap decodeGif(String str) {
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        String str2 = this.TAG;
        Bitmap bitmap = null;
        if (str == null || str.isEmpty()) {
            Log.w(str2, "decodeGif() bitmap return null");
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = false;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                bufferedInputStream = new BufferedInputStream(fileInputStream);
            } finally {
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("decodeGif() Exception occurs ", e, str2);
        }
        try {
            bitmap = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            if (bitmap == null && !options.inJustDecodeBounds) {
                Log.w(str2, "decodeGif() bitmap is null");
            }
            bufferedInputStream.close();
            fileInputStream.close();
            return bitmap;
        } finally {
        }
    }

    public final Bitmap getCoverWallpaperBitmap(int i, int i2) {
        String str = this.TAG;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
        boolean z = i == 5;
        Bitmap bitmap = null;
        try {
            Log.d(str, "getCoverWallpaperBitmap() wallpaperType:" + i + ", display:" + i2);
            if (z) {
                bitmap = decodeGif(getGifWallpaperPath(i2));
            } else {
                ParcelFileDescriptor wallpaperFile = wallpaperManager.getWallpaperFile(i2, ActivityManager.semGetCurrentUser(), i);
                if (wallpaperFile != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(wallpaperFile.getFileDescriptor(), null, new BitmapFactory.Options());
                    wallpaperFile.close();
                } else {
                    Log.e(str, "wallpaper is null");
                }
            }
        } catch (Throwable th) {
            Log.e(str, "getCoverWallpaperBitmap() " + th);
        }
        return bitmap;
    }

    public final Bitmap getDlsCoverWallpaperBitmap() {
        CoverWallpaperController coverWallpaperController = (CoverWallpaperController) this.mCoverWallpaper;
        int wallpaperType = coverWallpaperController.getWallpaperType();
        if (wallpaperType != 13 && wallpaperType != 23) {
            Bitmap wallpaperBitmap = coverWallpaperController.getWallpaperBitmap();
            ArrayList parseCropHints = IntelligentCropHelper.parseCropHints(coverWallpaperController.getWallpaperIntelligentCrop());
            if (wallpaperBitmap == null || parseCropHints == null) {
                return wallpaperBitmap;
            }
            Rect rect = (Rect) parseCropHints.getFirst();
            rect.intersect(new Rect(0, 0, wallpaperBitmap.getWidth(), wallpaperBitmap.getHeight()));
            return Bitmap.createBitmap(wallpaperBitmap, rect.left, rect.top, rect.width(), rect.height());
        }
        Bitmap videoFrame = WallpaperUtils.getVideoFrame(this.mContext, null, coverWallpaperController.getWallpaperPath());
        Rect fbeWallpaperRect = coverWallpaperController.isFbeAvailable() ? coverWallpaperController.mPluginWallpaperManager.getFbeWallpaperRect(1) : coverWallpaperController.mPluginWallpaperManager.getHomeWallpaperRect(CoverWallpaperController.getCoverMode());
        if (fbeWallpaperRect != null && fbeWallpaperRect.width() > 0 && fbeWallpaperRect.height() > 0 && videoFrame != null) {
            if (fbeWallpaperRect.isEmpty()) {
                Log.d(this.TAG, "getCroppedBitmapForCoverScreen: Invalid params.");
            } else {
                Point realScreenSize = WallpaperUtils.getRealScreenSize(this.mContext, true);
                Bitmap createBitmap = Bitmap.createBitmap(realScreenSize.x, realScreenSize.y, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                float width = videoFrame.getWidth() / fbeWallpaperRect.width();
                float f = 1.0f / width;
                canvas.scale(f, f);
                canvas.translate(fbeWallpaperRect.left * width, fbeWallpaperRect.top * width);
                canvas.drawBitmap(videoFrame, 0.0f, 0.0f, (Paint) null);
                videoFrame.recycle();
                videoFrame = createBitmap;
            }
        }
        return videoFrame;
    }

    public final Bitmap getDlsWallpaperBitmap(ImageOptionCreator.ImageOption imageOption) {
        Bundle notifyEvent;
        boolean isSubDisplay = WallpaperUtils.isSubDisplay();
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        String wallpaperIntelligentCrop = pluginWallpaperManager.getWallpaperIntelligentCrop(isSubDisplay ? 1 : 0);
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("getDlsWallpaperBitmap: iCrops = ", wallpaperIntelligentCrop);
        String str = this.TAG;
        Log.d(str, m);
        Size logicalDisplaySize = WallpaperUtils.getLogicalDisplaySize(this.mContext);
        Bitmap bitmap = null;
        Rect nearestCropHint = !TextUtils.isEmpty(wallpaperIntelligentCrop) ? IntelligentCropHelper.getNearestCropHint(new Point(logicalDisplaySize.getWidth(), logicalDisplaySize.getHeight()), IntelligentCropHelper.parseCropHints(wallpaperIntelligentCrop)) : null;
        boolean z = (nearestCropHint == null || nearestCropHint.isEmpty()) ? false : true;
        if (pluginWallpaperManager.isVideoWallpaperEnabled()) {
            String wallpaperPath = pluginWallpaperManager.getWallpaperPath();
            Uri wallpaperUri = pluginWallpaperManager.getWallpaperUri();
            if (wallpaperPath == null && wallpaperUri == null) {
                Log.w(str, "no video wallpaper data");
            } else {
                KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
                Log.d(str, "getDlsWallpaperBitmap: kgm.semIsKeyguardShowingAndNotOccluded() = " + keyguardManager.semIsKeyguardShowingAndNotOccluded());
                if (!keyguardManager.semIsKeyguardShowingAndNotOccluded() && (notifyEvent = ((KeyguardWallpaperController) this.mKeyguardWallpaper).notifyEvent(616)) != null) {
                    bitmap = (Bitmap) notifyEvent.getParcelable("wallpaper_bitmap", Bitmap.class);
                }
                if (bitmap == null) {
                    Log.w(str, "View or player is null, get frame from retriever : path = " + wallpaperPath + " , uri = " + wallpaperUri);
                    bitmap = WallpaperUtils.getVideoFrame(this.mContext, wallpaperUri, wallpaperPath);
                }
            }
        } else {
            bitmap = pluginWallpaperManager.isWallpaperSrcPath() ? pluginWallpaperManager.getBitmapFromPath(pluginWallpaperManager.getWallpaperPath(), z) : pluginWallpaperManager.isWallpaperSrcUri() ? pluginWallpaperManager.getBitmapFromUri(pluginWallpaperManager.getWallpaperUri(), z) : pluginWallpaperManager.getWallpaperBitmap();
        }
        if (bitmap != null && z) {
            try {
                Matrix matrix = new Matrix();
                Paint paint = new Paint(2);
                Bitmap createBitmap = Bitmap.createBitmap(logicalDisplaySize.getWidth(), logicalDisplaySize.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                float max = Math.max(logicalDisplaySize.getWidth() / nearestCropHint.width(), logicalDisplaySize.getHeight() / nearestCropHint.height());
                matrix.postScale(max, max);
                matrix.postTranslate(Math.round(r9), Math.round(r10));
                canvas.drawBitmap(bitmap, matrix, paint);
                Log.d(str, "getDlsWallpaperBitmap: src = " + nearestCropHint + ", dx = " + ((-nearestCropHint.left) * max) + ", dy = " + ((-nearestCropHint.top) * max) + ", displaySize.getWidth() = " + logicalDisplaySize.getWidth() + ", displaySize.getHeight() = " + logicalDisplaySize.getHeight() + ", scale = " + max);
                bitmap.recycle();
                return createBitmap;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return bitmap == null ? Bitmap.createBitmap(imageOption.width, imageOption.height, Bitmap.Config.ARGB_8888) : bitmap;
    }

    public final String getGifWallpaperPath(int i) {
        String str = this.TAG;
        String str2 = null;
        try {
            str2 = WallpaperManager.getInstance(this.mContext).semGetUri(i).getPath();
            Log.d(str, "getGifWallpaperPath() display: " + i + " , gifPath:" + str2);
            return str2;
        } catch (Throwable th) {
            Log.e(str, "getGifWallpaperPath() " + th);
            return str2;
        }
    }

    public final Bitmap makeResult(Bitmap bitmap, int i, int i2, int i3) {
        Bitmap createScaledBitmap;
        Bitmap bitmap2 = bitmap;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "makeResult, w: ", ", h: ", ", rotation: ");
        m.append(i3);
        Log.d(this.TAG, m.toString());
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        if (bitmap2 != null && !bitmap.isRecycled()) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
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
            if (f9 < 0.0f) {
                f9 = 0.0f;
            }
            Log.d("WallpaperUtils", "widthOrigin = " + width);
            Log.d("WallpaperUtils", "heightOrigin = " + height);
            Log.d("WallpaperUtils", "scale = " + f5);
            Log.d("WallpaperUtils", "centerX = " + f2);
            Log.d("WallpaperUtils", "centerY = " + f4);
            Log.d("WallpaperUtils", "startX = " + f8);
            Log.d("WallpaperUtils", "startY = " + f9);
            Log.d("WallpaperUtils", "width = " + f6);
            Log.d("WallpaperUtils", "height = " + f7);
            if (Math.round(f8) == 0 && Math.round(f9) == 0 && width == Math.round(f6) && height == Math.round(f7)) {
                Log.d("WallpaperUtils", "It doesn't need to crop bitmap");
            } else if (Math.round(f6) < 1 || Math.round(f7) < 1 || i < 1 || i2 < 1) {
                Log.d("WallpaperUtils", "Math.round(width) < 1 || Math.round(height) < 1 || mMatricsWidth < 1 || mMatricsHeight < 1");
            } else {
                if (Math.round(f8) + Math.round(f6) <= width) {
                    if (Math.round(f9) + Math.round(f7) <= height) {
                        Log.d("WallpaperUtils", "Cropping...");
                        bitmap2 = Bitmap.createBitmap(bitmap2, Math.round(f8), Math.round(f9), Math.round(f6), Math.round(f7));
                    }
                }
                Log.d("WallpaperUtils", "Calculated crop size error");
            }
            createScaledBitmap = Bitmap.createScaledBitmap(bitmap2, i, i2, true);
            if (i3 != -1 || i3 == 0) {
                return createScaledBitmap;
            }
            int i4 = i3 == 1 ? 90 : 270;
            Matrix matrix = new Matrix();
            matrix.postRotate(-i4);
            return Bitmap.createBitmap(createScaledBitmap, 0, 0, createScaledBitmap.getWidth(), createScaledBitmap.getHeight(), matrix, true);
        }
        bitmap2 = null;
        createScaledBitmap = Bitmap.createScaledBitmap(bitmap2, i, i2, true);
        if (i3 != -1) {
        }
        return createScaledBitmap;
    }
}
