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
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.media.face.SemFace;
import com.samsung.android.media.face.SemFaceDetection;
import com.samsung.android.saiv.imageprocessing.SmartCropper;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ImageSmartCropper {
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
        int iWidth = windowConfiguration.getBounds().width();
        int iHeight = windowConfiguration.getBounds().height();
        int i = (LsRune.WALLPAPER_SUB_DISPLAY_MODE && configuration.semDisplayDeviceType == 5) ? 17 : 1;
        Rect rect = (Rect) WallpaperUtils.sCachedSmartCroppedRect.get(i);
        if (rect != null) {
            float f = iWidth;
            float f2 = iHeight;
            float f3 = f / f2;
            float f4 = f2 / f;
            float fWidth = rect.width() / rect.height();
            if (Math.abs(f3 - fWidth) <= 0.3f || Math.abs(f4 - fWidth) <= 0.3f) {
                return;
            }
            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, iWidth, "Smart Crop ratio different display size.So clear cache. which : ", " display w: ", " , h: ");
            sbM.append(iHeight);
            sbM.append(", cropRect : ");
            sbM.append(rect);
            Log.i("ImageSmartCropper", sbM.toString());
            WallpaperUtils.sCachedSmartCroppedRect.put(i, null);
        }
    }

    public final DisplayInfo getDefaultDisplayInfo() {
        if (this.mDefaultDisplay == null) {
            this.mDefaultDisplay = ((WindowManager) this.mContext.getSystemService(WindowManager.class)).getDefaultDisplay();
        }
        this.mDefaultDisplay.getDisplayInfo(this.mTmpDisplayInfo);
        return this.mTmpDisplayInfo;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x006a, code lost:
    
        if ((java.lang.Math.max(r6, r7) / java.lang.Math.min(r6, r7)) > 2.0f) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean needToExtractSmartCropRect(int i, int i2, int i3) {
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            Context context = this.mContext;
            boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
            SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus = ((DisplayManager) context.getSystemService("display")).semGetWifiDisplayStatus();
            if (semWifiDisplayStatusSemGetWifiDisplayStatus != null && semWifiDisplayStatusSemGetWifiDisplayStatus.getActiveDisplayState() == 2 && semWifiDisplayStatusSemGetWifiDisplayStatus.getConnectedState() == 0) {
                Log.w("ImageSmartCropper", "SmartView is connected (fixed ratio), so extract rect");
                return true;
            }
            Log.d("ImageSmartCropper", "SmartView is not connected");
            if (!WallpaperUtils.isSubDisplay(i)) {
                if (i2 != 0 && i3 != 0) {
                    if (Math.max(i2, i3) / Math.min(i2, i3) <= 2.0f) {
                    }
                }
                Log.w("ImageSmartCropper", "Display info is not updated yet.");
                return false;
            }
            if (WallpaperUtils.isSubDisplay(i) && i2 != 0 && i3 != 0) {
            }
        }
        return true;
    }

    public final boolean needToSmartCrop(int i) {
        if (!needToSmartCrop()) {
            return false;
        }
        Context context = this.mContext;
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        return TextUtils.isEmpty(new SemWallpaperProperties(context, i, context.getUserId()).getImageCategory());
    }

    public final void updateSmartCropRect(Bitmap bitmap, int i, int i2) {
        float f;
        float f2;
        int i3;
        int i4;
        if (LsRune.WALLPAPER_ROTATABLE_WALLPAPER && WallpaperManager.getInstance(this.mContext).getWallpaperOrientation(i, i2) == 2) {
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
            int i5 = defaultDisplayInfo.logicalWidth;
            int i6 = defaultDisplayInfo.logicalHeight;
            int i7 = defaultDisplayInfo.rotation;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Log.d("ImageSmartCropper", "bmpWidth : " + width + ", bmpHeight : " + height);
            Log.d("ImageSmartCropper", "deviceWidth : " + i5 + ", deviceHeight : " + i6);
            if (needToExtractSmartCropRect(i, i5, i6)) {
                SemFaceDetection semFaceDetection = new SemFaceDetection();
                semFaceDetection.init();
                ArrayList arrayList = new ArrayList();
                Rect rect = new Rect(0, 0, 0, 0);
                if (i7 == 1 || i7 == 3) {
                    f = i6;
                    f2 = i5;
                } else {
                    f = i5;
                    f2 = i6;
                }
                float f3 = f / f2;
                int i8 = (int) (width * f3);
                if (i8 > height) {
                    i8 = height;
                }
                Log.d("ImageSmartCropper", "deviceRatio: " + f3 + ", landBitmapWidth : " + width + ", landBitmapHeight : " + i8);
                int iRun = semFaceDetection.run(bitmap, arrayList);
                StringBuilder sb = new StringBuilder("Number of faces = ");
                sb.append(iRun);
                Log.d("ImageSmartCropper", sb.toString());
                Rect rectFindObjectRect = rect;
                for (int i9 = 0; i9 < arrayList.size(); i9++) {
                    Rect rect2 = ((SemFace) arrayList.get(i9)).rect;
                    Log.d("ImageSmartCropper", "faceRect is : [" + rect2.left + "] [" + rect2.top + "] [" + rect2.right + "] [" + rect2.bottom + "] [" + rect2.centerX() + "] [" + rect2.centerY() + "]");
                    if (rect2.width() > rectFindObjectRect.width()) {
                        rectFindObjectRect = rect2;
                    }
                }
                semFaceDetection.release();
                if (iRun == 0) {
                    SmartCropper smartCropper = new SmartCropper();
                    int[] iArr = new int[width * height];
                    i4 = 0;
                    bitmap.copy(Bitmap.Config.ARGB_8888, true).getPixels(iArr, 0, width, 0, 0, width, height);
                    i3 = height;
                    if (smartCropper.setImage(width, i3, iArr)) {
                        rectFindObjectRect = smartCropper.findObjectRect();
                        Log.d("ImageSmartCropper", "[ findObjectRect() ] : " + rectFindObjectRect);
                    } else {
                        Log.d("ImageSmartCropper", "do not find object");
                    }
                    SmartCropper.releaseOneImage(smartCropper.mBDPtr);
                } else {
                    i3 = height;
                    i4 = 0;
                }
                Log.d("ImageSmartCropper", "recognizedRect: " + rectFindObjectRect);
                Rect rect3 = new Rect(i4, i4, width, i8);
                int iCenterY = rectFindObjectRect.centerY();
                int i10 = i8 / 2;
                int i11 = i3 / 2;
                if (rectFindObjectRect.isEmpty()) {
                    rect3.offset(i4, i11 - i10);
                } else if (iCenterY > i3 - i10) {
                    rect3.offset(i4, i3 - i8);
                } else if (iCenterY >= i10) {
                    rect3.offset(i4, iCenterY - i10);
                }
                this.mCropResult = rect3;
                WallpaperUtils.sCachedSmartCroppedRect.put(i, rect3);
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

    public final void updateSmartCropRectIfNeeded(Bitmap bitmap, int i, int i2) {
        this.mCropResult = (Rect) WallpaperUtils.sCachedSmartCroppedRect.get(i);
        boolean z = false;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            this.mFromLandScape = true;
        } else {
            this.mFromLandScape = false;
        }
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder(" updateSmartCropRectIfNeeded: from landscape = "), this.mFromLandScape, "ImageSmartCropper");
        if (this.mCropResult == null && !this.mFromLandScape) {
            z = true;
        }
        if (!z || !needToSmartCrop(i)) {
            Log.i("ImageSmartCropper", "updateSmartCropRectIfNeeded: Do not update SmartCrop.");
        } else if (z) {
            updateSmartCropRect(bitmap, i, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean needToSmartCrop() {
        String str;
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_ROTATION_HOME_SCREEN, 1) == 1) {
            return false;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
            TooltipPopup$$ExternalSyntheticOutline0.m(this.mLidState, "ImageSmartCropper", new StringBuilder(" getSettingKey "));
            if (this.mLidState == 0) {
                str = SettingsHelper.INDEX_HOME_WALLPAPER_SOURCE_SUB;
            } else {
                str = SettingsHelper.INDEX_HOME_WALLPAPER_SOURCE;
            }
        }
        if (this.mDisplayId == 2) {
            str = "dex_system_wallpaper_transparency";
        }
        return Settings.System.getInt(contentResolver, str, 1) == 0;
    }
}
