package com.android.systemui.wallpaper.glwallpaper;

import android.app.WallpaperManager;
import android.app.WindowConfiguration;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.media.face.SemFace;
import com.samsung.android.media.face.SemFaceDetection;
import com.samsung.android.saiv.imageprocessing.SmartCropper;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ImageSmartCropper {
    public final Context mContext;
    public final int mDisplayId;
    public boolean mFromLandScape;
    public Rect mCropResult = null;
    public Display mDefaultDisplay = null;
    public final DisplayInfo mTmpDisplayInfo = new DisplayInfo();
    public int mLidState = 1;

    public ImageSmartCropper(Context context, int i) {
        this.mContext = context;
        this.mDisplayId = i;
    }

    public static void checkDisplaySize(Configuration configuration) {
        WindowConfiguration windowConfiguration = configuration.windowConfiguration;
        int width = windowConfiguration.getBounds().width();
        int height = windowConfiguration.getBounds().height();
        int i = (LsRune.WALLPAPER_SUB_DISPLAY_MODE && configuration.semDisplayDeviceType == 5) ? 17 : 1;
        Rect cachedSmartCroppedRect = WallpaperUtils.getCachedSmartCroppedRect(i);
        if (cachedSmartCroppedRect != null) {
            float f = width;
            float f2 = height;
            float f3 = f / f2;
            float f4 = f2 / f;
            float width2 = cachedSmartCroppedRect.width() / cachedSmartCroppedRect.height();
            if (Math.abs(f3 - width2) <= 0.3f || Math.abs(f4 - width2) <= 0.3f) {
                return;
            }
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Smart Crop ratio different display size.So clear cache. which : ", i, " display w: ", width, " , h: ");
            m45m.append(height);
            m45m.append(", cropRect : ");
            m45m.append(cachedSmartCroppedRect);
            Log.i("ImageSmartCropper", m45m.toString());
            WallpaperUtils.clearCachedSmartCroppedRect(i);
        }
    }

    public final DisplayInfo getDefaultDisplayInfo() {
        if (this.mDefaultDisplay == null) {
            this.mDefaultDisplay = ((WindowManager) this.mContext.getSystemService(WindowManager.class)).getDefaultDisplay();
        }
        Display display = this.mDefaultDisplay;
        DisplayInfo displayInfo = this.mTmpDisplayInfo;
        display.getDisplayInfo(displayInfo);
        return displayInfo;
    }

    public final boolean needToExtractSmartCropRect(int i, int i2, int i3) {
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            boolean z = WallpaperUtils.mIsEmergencyMode;
            SemWifiDisplayStatus semGetWifiDisplayStatus = ((DisplayManager) this.mContext.getSystemService("display")).semGetWifiDisplayStatus();
            if (semGetWifiDisplayStatus != null && semGetWifiDisplayStatus.getActiveDisplayState() == 2 && semGetWifiDisplayStatus.getConnectedState() == 0) {
                Log.w("ImageSmartCropper", "SmartView is connected (fixed ratio), so extract rect");
                return true;
            }
            Log.d("ImageSmartCropper", "SmartView is not connected");
            if ((!WallpaperUtils.isSubDisplay(i) && !WallpaperUtils.isMainScreenRatio(i2, i3)) || (WallpaperUtils.isSubDisplay(i) && WallpaperUtils.isMainScreenRatio(i2, i3))) {
                Log.w("ImageSmartCropper", "Display info is not updated yet.");
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean needToSmartCrop() {
        String str;
        if (WallpaperUtils.isDexStandAloneMode()) {
            return false;
        }
        Context context = this.mContext;
        if (Settings.Global.getInt(context.getContentResolver(), "sehome_portrait_mode_only", 1) == 1) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder(" getSettingKey "), this.mLidState, "ImageSmartCropper");
            if (this.mLidState == 0) {
                str = "sub_display_system_wallpaper_transparency";
                if (this.mDisplayId == 2) {
                    str = "dex_system_wallpaper_transparency";
                }
                return Settings.System.getInt(contentResolver, str, 1) != 0;
            }
        }
        str = "android.wallpaper.settings_systemui_transparency";
        if (this.mDisplayId == 2) {
        }
        if (Settings.System.getInt(contentResolver, str, 1) != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00b6 A[Catch: OutOfMemoryError -> 0x0208, LinkageError -> 0x021f, Exception -> 0x0236, TryCatch #2 {Exception -> 0x0236, LinkageError -> 0x021f, OutOfMemoryError -> 0x0208, blocks: (B:11:0x003e, B:13:0x0044, B:15:0x004a, B:17:0x0050, B:20:0x0097, B:27:0x00b6, B:28:0x00bb, B:31:0x00c2, B:32:0x00f6, B:34:0x00fc, B:38:0x0156, B:41:0x0159, B:43:0x015e, B:45:0x0186, B:46:0x01a4, B:47:0x01ad, B:49:0x01d5, B:50:0x01ea, B:52:0x01da, B:54:0x01de, B:56:0x01e6, B:57:0x019f, B:59:0x00b9), top: B:9:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00fc A[Catch: OutOfMemoryError -> 0x0208, LinkageError -> 0x021f, Exception -> 0x0236, TryCatch #2 {Exception -> 0x0236, LinkageError -> 0x021f, OutOfMemoryError -> 0x0208, blocks: (B:11:0x003e, B:13:0x0044, B:15:0x004a, B:17:0x0050, B:20:0x0097, B:27:0x00b6, B:28:0x00bb, B:31:0x00c2, B:32:0x00f6, B:34:0x00fc, B:38:0x0156, B:41:0x0159, B:43:0x015e, B:45:0x0186, B:46:0x01a4, B:47:0x01ad, B:49:0x01d5, B:50:0x01ea, B:52:0x01da, B:54:0x01de, B:56:0x01e6, B:57:0x019f, B:59:0x00b9), top: B:9:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x015e A[Catch: OutOfMemoryError -> 0x0208, LinkageError -> 0x021f, Exception -> 0x0236, TryCatch #2 {Exception -> 0x0236, LinkageError -> 0x021f, OutOfMemoryError -> 0x0208, blocks: (B:11:0x003e, B:13:0x0044, B:15:0x004a, B:17:0x0050, B:20:0x0097, B:27:0x00b6, B:28:0x00bb, B:31:0x00c2, B:32:0x00f6, B:34:0x00fc, B:38:0x0156, B:41:0x0159, B:43:0x015e, B:45:0x0186, B:46:0x01a4, B:47:0x01ad, B:49:0x01d5, B:50:0x01ea, B:52:0x01da, B:54:0x01de, B:56:0x01e6, B:57:0x019f, B:59:0x00b9), top: B:9:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01d5 A[Catch: OutOfMemoryError -> 0x0208, LinkageError -> 0x021f, Exception -> 0x0236, TryCatch #2 {Exception -> 0x0236, LinkageError -> 0x021f, OutOfMemoryError -> 0x0208, blocks: (B:11:0x003e, B:13:0x0044, B:15:0x004a, B:17:0x0050, B:20:0x0097, B:27:0x00b6, B:28:0x00bb, B:31:0x00c2, B:32:0x00f6, B:34:0x00fc, B:38:0x0156, B:41:0x0159, B:43:0x015e, B:45:0x0186, B:46:0x01a4, B:47:0x01ad, B:49:0x01d5, B:50:0x01ea, B:52:0x01da, B:54:0x01de, B:56:0x01e6, B:57:0x019f, B:59:0x00b9), top: B:9:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01da A[Catch: OutOfMemoryError -> 0x0208, LinkageError -> 0x021f, Exception -> 0x0236, TryCatch #2 {Exception -> 0x0236, LinkageError -> 0x021f, OutOfMemoryError -> 0x0208, blocks: (B:11:0x003e, B:13:0x0044, B:15:0x004a, B:17:0x0050, B:20:0x0097, B:27:0x00b6, B:28:0x00bb, B:31:0x00c2, B:32:0x00f6, B:34:0x00fc, B:38:0x0156, B:41:0x0159, B:43:0x015e, B:45:0x0186, B:46:0x01a4, B:47:0x01ad, B:49:0x01d5, B:50:0x01ea, B:52:0x01da, B:54:0x01de, B:56:0x01e6, B:57:0x019f, B:59:0x00b9), top: B:9:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b9 A[Catch: OutOfMemoryError -> 0x0208, LinkageError -> 0x021f, Exception -> 0x0236, TryCatch #2 {Exception -> 0x0236, LinkageError -> 0x021f, OutOfMemoryError -> 0x0208, blocks: (B:11:0x003e, B:13:0x0044, B:15:0x004a, B:17:0x0050, B:20:0x0097, B:27:0x00b6, B:28:0x00bb, B:31:0x00c2, B:32:0x00f6, B:34:0x00fc, B:38:0x0156, B:41:0x0159, B:43:0x015e, B:45:0x0186, B:46:0x01a4, B:47:0x01ad, B:49:0x01d5, B:50:0x01ea, B:52:0x01da, B:54:0x01de, B:56:0x01e6, B:57:0x019f, B:59:0x00b9), top: B:9:0x003c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSmartCropRect(Bitmap bitmap, int i) {
        boolean z;
        float f;
        float f2;
        int i2;
        int run;
        Rect rect;
        int i3;
        int i4;
        int i5;
        int i6;
        if (LsRune.WALLPAPER_ROTATABLE_WALLPAPER && WallpaperManager.getInstance(this.mContext).getWallpaperOrientation(i, KeyguardUpdateMonitor.getCurrentUser()) == 2) {
            this.mCropResult = null;
            WallpaperUtils.sCachedSmartCroppedRect.put(i, null);
            Log.d("ImageSmartCropper", "updateSmartCropRect landscape mode. do not smart crop");
            return;
        }
        Log.d("ImageSmartCropper", "updateSmartCropRect");
        try {
            if (bitmap == null) {
                Log.d("ImageSmartCropper", "mBackground == null");
                return;
            }
            if (bitmap.isRecycled()) {
                Log.d("ImageSmartCropper", "mBackground is recycled");
                return;
            }
            DisplayInfo defaultDisplayInfo = getDefaultDisplayInfo();
            int i7 = defaultDisplayInfo.logicalWidth;
            int i8 = defaultDisplayInfo.logicalHeight;
            int i9 = defaultDisplayInfo.rotation;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Log.d("ImageSmartCropper", "bmpWidth : " + width + ", bmpHeight : " + height);
            Log.d("ImageSmartCropper", "deviceWidth : " + i7 + ", deviceHeight : " + i8);
            if (needToExtractSmartCropRect(i, i7, i8)) {
                SemFaceDetection semFaceDetection = new SemFaceDetection();
                semFaceDetection.init();
                ArrayList arrayList = new ArrayList();
                Rect rect2 = new Rect(0, 0, 0, 0);
                if (i9 != 1 && i9 != 3) {
                    z = false;
                    if (z) {
                        f = i7;
                        f2 = i8;
                    } else {
                        f = i8;
                        f2 = i7;
                    }
                    float f3 = f / f2;
                    i2 = (int) (width * f3);
                    if (i2 > height) {
                        i2 = height;
                    }
                    Log.d("ImageSmartCropper", "deviceRatio: " + f3 + ", landBitmapWidth : " + width + ", landBitmapHeight : " + i2);
                    run = semFaceDetection.run(bitmap, arrayList);
                    StringBuilder sb = new StringBuilder("Number of faces = ");
                    sb.append(run);
                    Log.d("ImageSmartCropper", sb.toString());
                    rect = rect2;
                    for (i3 = 0; i3 < arrayList.size(); i3++) {
                        Rect rect3 = ((SemFace) arrayList.get(i3)).rect;
                        Log.d("ImageSmartCropper", "faceRect is : [" + rect3.left + "] [" + rect3.top + "] [" + rect3.right + "] [" + rect3.bottom + "] [" + rect3.centerX() + "] [" + rect3.centerY() + "]");
                        if (rect3.width() > rect.width()) {
                            rect = rect3;
                        }
                    }
                    semFaceDetection.release();
                    if (run != 0) {
                        SmartCropper smartCropper = new SmartCropper();
                        int[] iArr = new int[width * height];
                        i6 = 0;
                        i4 = height;
                        i5 = width;
                        bitmap.copy(Bitmap.Config.ARGB_8888, true).getPixels(iArr, 0, width, 0, 0, i5, i4);
                        if (smartCropper.setImage(i5, i4, iArr)) {
                            rect = smartCropper.findObjectRect();
                            Log.d("ImageSmartCropper", "[ findObjectRect() ] : " + rect);
                        } else {
                            Log.d("ImageSmartCropper", "do not find object");
                        }
                        SmartCropper.releaseOneImage(smartCropper.mBDPtr);
                    } else {
                        i4 = height;
                        i5 = width;
                        i6 = 0;
                    }
                    Log.d("ImageSmartCropper", "recognizedRect: " + rect);
                    Rect rect4 = new Rect(i6, i6, i5, i2);
                    int centerY = rect.centerY();
                    int i10 = i2 / 2;
                    int i11 = i4 / 2;
                    if (!rect.isEmpty()) {
                        rect4.offset(i6, i11 - i10);
                    } else if (centerY > i4 - i10) {
                        rect4.offset(i6, i4 - i2);
                    } else if (centerY >= i10) {
                        rect4.offset(i6, centerY - i10);
                    }
                    this.mCropResult = rect4;
                    WallpaperUtils.sCachedSmartCroppedRect.put(i, rect4);
                    Log.d("ImageSmartCropper", "[ findCropRect() of Real Image] : " + this.mCropResult);
                }
                z = true;
                if (z) {
                }
                float f32 = f / f2;
                i2 = (int) (width * f32);
                if (i2 > height) {
                }
                Log.d("ImageSmartCropper", "deviceRatio: " + f32 + ", landBitmapWidth : " + width + ", landBitmapHeight : " + i2);
                run = semFaceDetection.run(bitmap, arrayList);
                StringBuilder sb2 = new StringBuilder("Number of faces = ");
                sb2.append(run);
                Log.d("ImageSmartCropper", sb2.toString());
                rect = rect2;
                while (i3 < arrayList.size()) {
                }
                semFaceDetection.release();
                if (run != 0) {
                }
                Log.d("ImageSmartCropper", "recognizedRect: " + rect);
                Rect rect42 = new Rect(i6, i6, i5, i2);
                int centerY2 = rect.centerY();
                int i102 = i2 / 2;
                int i112 = i4 / 2;
                if (!rect.isEmpty()) {
                }
                this.mCropResult = rect42;
                WallpaperUtils.sCachedSmartCroppedRect.put(i, rect42);
                Log.d("ImageSmartCropper", "[ findCropRect() of Real Image] : " + this.mCropResult);
            }
        } catch (Exception e) {
            Log.d("ImageSmartCropper", "Exception occurred when smart cropping " + e.getMessage());
        } catch (LinkageError e2) {
            Log.d("ImageSmartCropper", "java.lang.LinkageError occurred when smart cropping " + e2.getMessage());
        } catch (OutOfMemoryError e3) {
            Log.d("ImageSmartCropper", "OutOfMemoryError while smart cropping: " + e3.getMessage());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x005e, code lost:
    
        if (android.text.TextUtils.isEmpty(new com.samsung.android.wallpaper.utils.SemWallpaperProperties(r5, r9, r5.getUserId()).getImageCategory()) == false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSmartCropRectIfNeeded(Bitmap bitmap, int i) {
        this.mCropResult = WallpaperUtils.getCachedSmartCroppedRect(i);
        boolean z = true;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            this.mFromLandScape = true;
        } else {
            this.mFromLandScape = false;
        }
        StringBuilder m1m = AbstractC0000x2c234b15.m1m(" updateSmartCropRectIfNeeded: which = ", i, ", landscape = ");
        m1m.append(this.mFromLandScape);
        m1m.append(" , rect = ");
        m1m.append(this.mCropResult);
        Log.i("ImageSmartCropper", m1m.toString());
        boolean z2 = this.mCropResult == null && !this.mFromLandScape;
        if (z2) {
            if (needToSmartCrop()) {
                Context context = this.mContext;
            }
            z = false;
            if (z) {
                if (z2) {
                    updateSmartCropRect(bitmap, i);
                    return;
                }
                return;
            }
        }
        Log.i("ImageSmartCropper", "updateSmartCropRectIfNeeded: Do not update SmartCrop.");
    }
}
