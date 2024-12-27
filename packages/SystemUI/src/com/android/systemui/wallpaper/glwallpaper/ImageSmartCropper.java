package com.android.systemui.wallpaper.glwallpaper;

import android.app.WindowConfiguration;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Rect rect = (Rect) WallpaperUtils.sCachedSmartCroppedRect.get(i);
        if (rect != null) {
            float f = width;
            float f2 = height;
            float f3 = f / f2;
            float f4 = f2 / f;
            float width2 = rect.width() / rect.height();
            if (Math.abs(f3 - width2) <= 0.3f || Math.abs(f4 - width2) <= 0.3f) {
                return;
            }
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, width, "Smart Crop ratio different display size.So clear cache. which : ", " display w: ", " , h: ");
            m.append(height);
            m.append(", cropRect : ");
            m.append(rect);
            Log.i("ImageSmartCropper", m.toString());
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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004f, code lost:
    
        if ((java.lang.Math.max(r6, r7) / java.lang.Math.min(r6, r7)) > 2.0f) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006a, code lost:
    
        if ((java.lang.Math.max(r6, r7) / java.lang.Math.min(r6, r7)) > 2.0f) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean needToExtractSmartCropRect(int r5, int r6, int r7) {
        /*
            r4 = this;
            boolean r0 = com.android.systemui.LsRune.WALLPAPER_SUB_DISPLAY_MODE
            r1 = 1
            if (r0 == 0) goto L74
            boolean r0 = com.android.systemui.LsRune.WALLPAPER_SUB_WATCHFACE
            if (r0 != 0) goto L74
            android.content.Context r4 = r4.mContext
            boolean r0 = com.android.systemui.wallpaper.WallpaperUtils.mIsExternalLiveWallpaper
            java.lang.String r0 = "display"
            java.lang.Object r4 = r4.getSystemService(r0)
            android.hardware.display.DisplayManager r4 = (android.hardware.display.DisplayManager) r4
            android.hardware.display.SemWifiDisplayStatus r4 = r4.semGetWifiDisplayStatus()
            java.lang.String r0 = "ImageSmartCropper"
            if (r4 == 0) goto L30
            int r2 = r4.getActiveDisplayState()
            r3 = 2
            if (r2 != r3) goto L30
            int r4 = r4.getConnectedState()
            if (r4 != 0) goto L30
            java.lang.String r4 = "SmartView is connected (fixed ratio), so extract rect"
            android.util.Log.w(r0, r4)
            return r1
        L30:
            java.lang.String r4 = "SmartView is not connected"
            android.util.Log.d(r0, r4)
            boolean r4 = com.android.systemui.wallpaper.WallpaperUtils.isSubDisplay(r5)
            r2 = 1073741824(0x40000000, float:2.0)
            if (r4 != 0) goto L52
            if (r6 == 0) goto L6d
            if (r7 != 0) goto L42
            goto L6d
        L42:
            int r4 = java.lang.Math.max(r6, r7)
            int r3 = java.lang.Math.min(r6, r7)
            float r4 = (float) r4
            float r3 = (float) r3
            float r4 = r4 / r3
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 <= 0) goto L52
            goto L6d
        L52:
            boolean r4 = com.android.systemui.wallpaper.WallpaperUtils.isSubDisplay(r5)
            if (r4 == 0) goto L74
            if (r6 == 0) goto L74
            if (r7 != 0) goto L5d
            goto L74
        L5d:
            int r4 = java.lang.Math.max(r6, r7)
            int r5 = java.lang.Math.min(r6, r7)
            float r4 = (float) r4
            float r5 = (float) r5
            float r4 = r4 / r5
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 <= 0) goto L6d
            goto L74
        L6d:
            java.lang.String r4 = "Display info is not updated yet."
            android.util.Log.w(r0, r4)
            r4 = 0
            return r4
        L74:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper.needToExtractSmartCropRect(int, int, int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean needToSmartCrop() {
        /*
            r6 = this;
            boolean r0 = com.android.systemui.wallpaper.WallpaperUtils.isDexStandAloneMode()
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            android.content.Context r0 = r6.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r2 = "sehome_portrait_mode_only"
            r3 = 1
            int r0 = android.provider.Settings.Global.getInt(r0, r2, r3)
            if (r0 != r3) goto L19
            return r1
        L19:
            android.content.Context r0 = r6.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            boolean r2 = com.android.systemui.LsRune.WALLPAPER_SUB_DISPLAY_MODE
            if (r2 == 0) goto L39
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = " getSettingKey "
            r2.<init>(r4)
            int r4 = r6.mLidState
            java.lang.String r5 = "ImageSmartCropper"
            androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0.m(r4, r5, r2)
            int r2 = r6.mLidState
            if (r2 != 0) goto L39
            java.lang.String r2 = "sub_display_system_wallpaper_transparency"
            goto L3b
        L39:
            java.lang.String r2 = "android.wallpaper.settings_systemui_transparency"
        L3b:
            int r6 = r6.mDisplayId
            r4 = 2
            if (r6 != r4) goto L42
            java.lang.String r2 = "dex_system_wallpaper_transparency"
        L42:
            int r6 = android.provider.Settings.System.getInt(r0, r2, r3)
            if (r6 != 0) goto L49
            r1 = r3
        L49:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper.needToSmartCrop():boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ff A[Catch: OutOfMemoryError -> 0x0042, LinkageError -> 0x0045, Exception -> 0x0048, TryCatch #2 {Exception -> 0x0048, LinkageError -> 0x0045, OutOfMemoryError -> 0x0042, blocks: (B:11:0x003c, B:13:0x004b, B:15:0x0051, B:17:0x0057, B:20:0x009e, B:25:0x00b8, B:26:0x00ba, B:27:0x00bf, B:30:0x00c5, B:31:0x00f9, B:33:0x00ff, B:37:0x0159, B:40:0x015c, B:42:0x0161, B:44:0x0188, B:45:0x01a6, B:46:0x01af, B:48:0x01d7, B:49:0x01ec, B:51:0x01dc, B:53:0x01e0, B:55:0x01e8, B:56:0x01a1, B:58:0x00bc), top: B:9:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0161 A[Catch: OutOfMemoryError -> 0x0042, LinkageError -> 0x0045, Exception -> 0x0048, TryCatch #2 {Exception -> 0x0048, LinkageError -> 0x0045, OutOfMemoryError -> 0x0042, blocks: (B:11:0x003c, B:13:0x004b, B:15:0x0051, B:17:0x0057, B:20:0x009e, B:25:0x00b8, B:26:0x00ba, B:27:0x00bf, B:30:0x00c5, B:31:0x00f9, B:33:0x00ff, B:37:0x0159, B:40:0x015c, B:42:0x0161, B:44:0x0188, B:45:0x01a6, B:46:0x01af, B:48:0x01d7, B:49:0x01ec, B:51:0x01dc, B:53:0x01e0, B:55:0x01e8, B:56:0x01a1, B:58:0x00bc), top: B:9:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01d7 A[Catch: OutOfMemoryError -> 0x0042, LinkageError -> 0x0045, Exception -> 0x0048, TryCatch #2 {Exception -> 0x0048, LinkageError -> 0x0045, OutOfMemoryError -> 0x0042, blocks: (B:11:0x003c, B:13:0x004b, B:15:0x0051, B:17:0x0057, B:20:0x009e, B:25:0x00b8, B:26:0x00ba, B:27:0x00bf, B:30:0x00c5, B:31:0x00f9, B:33:0x00ff, B:37:0x0159, B:40:0x015c, B:42:0x0161, B:44:0x0188, B:45:0x01a6, B:46:0x01af, B:48:0x01d7, B:49:0x01ec, B:51:0x01dc, B:53:0x01e0, B:55:0x01e8, B:56:0x01a1, B:58:0x00bc), top: B:9:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01dc A[Catch: OutOfMemoryError -> 0x0042, LinkageError -> 0x0045, Exception -> 0x0048, TryCatch #2 {Exception -> 0x0048, LinkageError -> 0x0045, OutOfMemoryError -> 0x0042, blocks: (B:11:0x003c, B:13:0x004b, B:15:0x0051, B:17:0x0057, B:20:0x009e, B:25:0x00b8, B:26:0x00ba, B:27:0x00bf, B:30:0x00c5, B:31:0x00f9, B:33:0x00ff, B:37:0x0159, B:40:0x015c, B:42:0x0161, B:44:0x0188, B:45:0x01a6, B:46:0x01af, B:48:0x01d7, B:49:0x01ec, B:51:0x01dc, B:53:0x01e0, B:55:0x01e8, B:56:0x01a1, B:58:0x00bc), top: B:9:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSmartCropRect(android.graphics.Bitmap r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 588
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper.updateSmartCropRect(android.graphics.Bitmap, int, int):void");
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
        if (z && needToSmartCrop()) {
            Context context = this.mContext;
            if (TextUtils.isEmpty(new SemWallpaperProperties(context, i, context.getUserId()).getImageCategory())) {
                if (z) {
                    updateSmartCropRect(bitmap, i, i2);
                    return;
                }
                return;
            }
        }
        Log.i("ImageSmartCropper", "updateSmartCropRectIfNeeded: Do not update SmartCrop.");
    }
}
