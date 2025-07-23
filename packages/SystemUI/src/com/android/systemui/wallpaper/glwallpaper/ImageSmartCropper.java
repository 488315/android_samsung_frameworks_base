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
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, width, "Smart Crop ratio different display size.So clear cache. which : ", " display w: ", " , h: ");
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

    public final boolean needToSmartCrop(int i) {
        if (!needToSmartCrop()) {
            return false;
        }
        Context context = this.mContext;
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        return TextUtils.isEmpty(new SemWallpaperProperties(context, i, context.getUserId()).getImageCategory());
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0100 A[Catch: OutOfMemoryError -> 0x0042, LinkageError -> 0x0045, Exception -> 0x0048, TryCatch #2 {Exception -> 0x0048, LinkageError -> 0x0045, OutOfMemoryError -> 0x0042, blocks: (B:11:0x003c, B:13:0x004b, B:15:0x0051, B:17:0x0057, B:21:0x009f, B:26:0x00b9, B:27:0x00bb, B:28:0x00c0, B:31:0x00c6, B:32:0x00fa, B:34:0x0100, B:38:0x015a, B:41:0x015d, B:43:0x0162, B:45:0x0188, B:46:0x01a6, B:47:0x01ae, B:49:0x01d6, B:50:0x01eb, B:52:0x01db, B:54:0x01df, B:56:0x01e7, B:57:0x01a1, B:59:0x00bd), top: B:9:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0162 A[Catch: OutOfMemoryError -> 0x0042, LinkageError -> 0x0045, Exception -> 0x0048, TryCatch #2 {Exception -> 0x0048, LinkageError -> 0x0045, OutOfMemoryError -> 0x0042, blocks: (B:11:0x003c, B:13:0x004b, B:15:0x0051, B:17:0x0057, B:21:0x009f, B:26:0x00b9, B:27:0x00bb, B:28:0x00c0, B:31:0x00c6, B:32:0x00fa, B:34:0x0100, B:38:0x015a, B:41:0x015d, B:43:0x0162, B:45:0x0188, B:46:0x01a6, B:47:0x01ae, B:49:0x01d6, B:50:0x01eb, B:52:0x01db, B:54:0x01df, B:56:0x01e7, B:57:0x01a1, B:59:0x00bd), top: B:9:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01d6 A[Catch: OutOfMemoryError -> 0x0042, LinkageError -> 0x0045, Exception -> 0x0048, TryCatch #2 {Exception -> 0x0048, LinkageError -> 0x0045, OutOfMemoryError -> 0x0042, blocks: (B:11:0x003c, B:13:0x004b, B:15:0x0051, B:17:0x0057, B:21:0x009f, B:26:0x00b9, B:27:0x00bb, B:28:0x00c0, B:31:0x00c6, B:32:0x00fa, B:34:0x0100, B:38:0x015a, B:41:0x015d, B:43:0x0162, B:45:0x0188, B:46:0x01a6, B:47:0x01ae, B:49:0x01d6, B:50:0x01eb, B:52:0x01db, B:54:0x01df, B:56:0x01e7, B:57:0x01a1, B:59:0x00bd), top: B:9:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01db A[Catch: OutOfMemoryError -> 0x0042, LinkageError -> 0x0045, Exception -> 0x0048, TryCatch #2 {Exception -> 0x0048, LinkageError -> 0x0045, OutOfMemoryError -> 0x0042, blocks: (B:11:0x003c, B:13:0x004b, B:15:0x0051, B:17:0x0057, B:21:0x009f, B:26:0x00b9, B:27:0x00bb, B:28:0x00c0, B:31:0x00c6, B:32:0x00fa, B:34:0x0100, B:38:0x015a, B:41:0x015d, B:43:0x0162, B:45:0x0188, B:46:0x01a6, B:47:0x01ae, B:49:0x01d6, B:50:0x01eb, B:52:0x01db, B:54:0x01df, B:56:0x01e7, B:57:0x01a1, B:59:0x00bd), top: B:9:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSmartCropRect(android.graphics.Bitmap r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 587
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
        if (!z || !needToSmartCrop(i)) {
            Log.i("ImageSmartCropper", "updateSmartCropRectIfNeeded: Do not update SmartCrop.");
        } else if (z) {
            updateSmartCropRect(bitmap, i, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0043 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0044 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean needToSmartCrop() {
        /*
            r6 = this;
            boolean r0 = com.android.systemui.wallpaper.WallpaperUtils.mIsExternalLiveWallpaper
            android.content.Context r0 = r6.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "sehome_portrait_mode_only"
            r2 = 1
            int r0 = android.provider.Settings.Global.getInt(r0, r1, r2)
            r1 = 0
            if (r0 != r2) goto L14
            return r1
        L14:
            android.content.Context r0 = r6.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            boolean r3 = com.android.systemui.LsRune.WALLPAPER_SUB_DISPLAY_MODE
            if (r3 == 0) goto L34
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = " getSettingKey "
            r3.<init>(r4)
            int r4 = r6.mLidState
            java.lang.String r5 = "ImageSmartCropper"
            androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0.m(r4, r5, r3)
            int r3 = r6.mLidState
            if (r3 != 0) goto L34
            java.lang.String r3 = "sub_display_system_wallpaper_transparency"
            goto L36
        L34:
            java.lang.String r3 = "android.wallpaper.settings_systemui_transparency"
        L36:
            int r6 = r6.mDisplayId
            r4 = 2
            if (r6 != r4) goto L3d
            java.lang.String r3 = "dex_system_wallpaper_transparency"
        L3d:
            int r6 = android.provider.Settings.System.getInt(r0, r3, r2)
            if (r6 != 0) goto L44
            return r2
        L44:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper.needToSmartCrop():boolean");
    }
}
