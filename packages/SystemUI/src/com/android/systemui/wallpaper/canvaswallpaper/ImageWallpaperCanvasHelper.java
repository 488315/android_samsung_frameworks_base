package com.android.systemui.wallpaper.canvaswallpaper;

import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.DisplayInfo;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.PluginWallpaper;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.effect.ColorDecorFilterHelper;
import com.android.systemui.wallpaper.effect.HighlightFilterHelper;
import com.android.systemui.wallpaper.glwallpaper.ImageDarkModeFilter;
import com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ImageWallpaperCanvasHelper {
    public final String TAG;
    public Bitmap mBitmap;
    public Consumer mBitmapUpdateConsumer;
    public final Callback mCallback;
    public final String mColorDecorFilterData;
    public final Context mContext;
    public final CoverWallpaper mCoverWallpaper;
    public int mCurDensityDpi;
    public int mCurrentUserId;
    public int mDeviceDisplayType;
    public final Rect mDimensions;
    public final int mDisplayId;
    public int mHighlightFilterAmount;
    public final ImageSmartCropper mImageSmartCropper;
    public boolean mIsNightModeOn;
    public final boolean mIsVirtualDisplay;
    public boolean mIsWcgContent;
    public final WallpaperLogger mLoggerWrapper;
    public int mOrientation;
    public final PluginWallpaper mPluginWallpaper;
    public final PowerManager mPm;
    public final AtomicInteger mRefCount;
    public int mSmartCropYOffset;
    public Bitmap mSubBitmap;
    public final SystemWallpaperColors mSystemWallpaperColors;
    public final WallpaperManager mWallpaperManager;
    public final Rect mSurfaceSize = new Rect();
    public boolean mIsSmartCropAllowed = true;
    public final float mYOffset = 0.5f;
    public int mLidState = -1;
    public boolean mIsFolded = false;
    public final HashMap mDownScaledSourceBitmapSet = new HashMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DownScaledSourceBitmap {
        public final Bitmap mBitmap;
        public final float mScale;

        public DownScaledSourceBitmap(int i, Bitmap bitmap, float f) {
            this.mBitmap = bitmap;
            this.mScale = f;
        }
    }

    public ImageWallpaperCanvasHelper(Context context, int i, WallpaperLogger wallpaperLogger, SystemWallpaperColors systemWallpaperColors, CoverWallpaper coverWallpaper, PluginWallpaper pluginWallpaper, IntelligentCropHelper intelligentCropHelper, Callback callback) {
        this.TAG = "ImageWallpaperCanvasHelper";
        this.mOrientation = -1;
        this.mCurDensityDpi = 0;
        this.mIsNightModeOn = false;
        this.mDeviceDisplayType = -1;
        this.mHighlightFilterAmount = -1;
        WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService(WallpaperManager.class);
        if (wallpaperManager == null) {
            Log.w("ImageWallpaperCanvasHelper", "WallpaperManager not available");
        }
        this.mSystemWallpaperColors = systemWallpaperColors;
        this.mContext = context;
        this.mLoggerWrapper = wallpaperLogger;
        this.mCurrentUserId = ActivityManager.getCurrentUser();
        this.mRefCount = new AtomicInteger();
        this.mDimensions = new Rect();
        this.mCoverWallpaper = coverWallpaper;
        this.mPluginWallpaper = pluginWallpaper;
        this.mWallpaperManager = wallpaperManager;
        this.mCallback = callback;
        StringBuilder sb = new StringBuilder("ImageWallpaperCanvasHelper_");
        ImageWallpaper.CanvasEngine.AnonymousClass3 anonymousClass3 = (ImageWallpaper.CanvasEngine.AnonymousClass3) this.mCallback;
        anonymousClass3.getClass();
        int i2 = ImageWallpaper.CanvasEngine.$r8$clinit;
        sb.append(ImageWallpaper.CanvasEngine.this.getWallpaperFlags() != 2 ? 1 : 2);
        String sb2 = sb.toString();
        this.TAG = sb2;
        this.mDisplayId = i;
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            this.mIsVirtualDisplay = WallpaperManager.isVirtualWallpaperDisplay(context, i);
        }
        this.mImageSmartCropper = new ImageSmartCropper(context, i);
        this.mCurDensityDpi = context.getResources().getConfiguration().densityDpi;
        this.mOrientation = context.getResources().getConfiguration().orientation;
        this.mSmartCropYOffset = -1000000;
        WallpaperUtils.sCachedSmartCroppedRect.put(getCurrentWhich(), null);
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
            int lidState = wallpaperManager.getLidState();
            this.mPm = (PowerManager) context.getSystemService("power");
            ((WallpaperLoggerImpl) wallpaperLogger).log(sb2, " initial lid state : " + convertLidStateToString(lidState) + " , " + context.getResources().getConfiguration().semDisplayDeviceType);
            int i3 = context.getResources().getConfiguration().semDisplayDeviceType;
            this.mDeviceDisplayType = i3;
            if (i3 == 5 && lidState != 0) {
                Log.i(sb2, " flex mode ".concat(convertLidStateToString(0)));
                lidState = 0;
            }
            setLidState(lidState);
        }
        this.mIsNightModeOn = (context.getResources().getConfiguration().uiMode & 32) != 0;
        int convertDisplayIdToMode = WhichChecker.convertDisplayIdToMode(i, context);
        if (convertDisplayIdToMode >= 0) {
            Bundle wallpaperExtras = WallpaperManager.getInstance(context).getWallpaperExtras(convertDisplayIdToMode | 1, context.getUserId());
            String string = wallpaperExtras != null ? wallpaperExtras.getString("imageFilterParams") : null;
            if (!TextUtils.isEmpty(string)) {
                this.mColorDecorFilterData = string;
            }
        }
        if (!TextUtils.isEmpty(this.mColorDecorFilterData)) {
            return;
        }
        this.mHighlightFilterAmount = 60;
    }

    public static String convertLidStateToString(int i) {
        return LsRune.WALLPAPER_SUB_DISPLAY_MODE ? i == 1 ? "LID_OPEN" : i == 0 ? "LID_CLOSED" : "LID_UNKNOWN" : "LID_UNKNOWN";
    }

    public final DownScaledSourceBitmap createDownScaledSourceBitmap(Bitmap bitmap, int i) {
        Point displaySize = getDisplaySize();
        int max = Math.max(displaySize.x, displaySize.y);
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float max2 = ((int) Math.max(1024.0f, max * 0.5f)) / min;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(max, min, "createDownScaledSourceBitmap: longDisplay=", ", shortBmpLen=", ", scale=");
        m.append(max2);
        String sb = m.toString();
        String str = this.TAG;
        Log.d(str, sb);
        if (max2 > 1.0f) {
            return null;
        }
        Bitmap copy = max2 == 1.0f ? bitmap.copy(bitmap.getConfig(), false) : Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * max2), (int) (bitmap.getHeight() * max2), true);
        if (copy != null && copy != bitmap) {
            return new DownScaledSourceBitmap(i, copy, max2);
        }
        Log.e(str, "createDownScaledSourceBitmap: Resized bitmap creation failed. org=" + bitmap + ", resized=" + copy);
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0014, code lost:
    
        if (r0 == 1) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0020, code lost:
    
        if (r6.mWallpaperManager.getLidState() == 0) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getCurrentWhich() {
        /*
            r6 = this;
            int r0 = r6.mDisplayId
            r1 = 1
            r2 = 2
            if (r0 != r2) goto L9
            r0 = 8
            goto L2d
        L9:
            boolean r3 = com.android.systemui.LsRune.WALLPAPER_SUB_DISPLAY_MODE
            r4 = 4
            if (r3 == 0) goto L23
            boolean r3 = com.android.systemui.LsRune.WALLPAPER_SUB_WATCHFACE
            r5 = 16
            if (r3 == 0) goto L1a
            if (r0 != r1) goto L18
        L16:
            r0 = r5
            goto L2d
        L18:
            r0 = r4
            goto L2d
        L1a:
            android.app.WallpaperManager r0 = r6.mWallpaperManager
            int r0 = r0.getLidState()
            if (r0 != 0) goto L23
            goto L16
        L23:
            boolean r0 = com.android.systemui.LsRune.COVER_VIRTUAL_DISPLAY
            if (r0 == 0) goto L18
            boolean r0 = r6.mIsVirtualDisplay
            if (r0 == 0) goto L18
            r0 = 32
        L2d:
            com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper$Callback r6 = r6.mCallback
            com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$3 r6 = (com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.AnonymousClass3) r6
            r6.getClass()
            int r3 = com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.$r8$clinit
            com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine r6 = com.android.systemui.wallpapers.ImageWallpaper.CanvasEngine.this
            int r6 = r6.getWallpaperFlags()
            if (r6 != r2) goto L3f
            r1 = r2
        L3f:
            r6 = r1 & 3
            r6 = r6 | r0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper.getCurrentWhich():int");
    }

    public final Integer getDimFilterColor(int i) {
        float[] wallpaperFilterColor = ImageDarkModeFilter.getWallpaperFilterColor(this.mContext, this.mSystemWallpaperColors.getColor(i));
        if (wallpaperFilterColor == null) {
            return null;
        }
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && this.mDisplayId == 1) {
            return null;
        }
        return Integer.valueOf(Color.argb(wallpaperFilterColor[3], wallpaperFilterColor[0], wallpaperFilterColor[1], wallpaperFilterColor[2]));
    }

    public final Point getDisplaySize() {
        Display display = ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(this.mDisplayId);
        if (display == null) {
            return null;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        return new Point(displayInfo.logicalWidth, displayInfo.logicalHeight);
    }

    public final Bitmap getFilterAppliedBitmap(Bitmap bitmap, int i) {
        if (!WallpaperUtils.isValidBitmap(bitmap)) {
            return null;
        }
        if (!TextUtils.isEmpty(this.mColorDecorFilterData)) {
            bitmap = ColorDecorFilterHelper.createFilteredBitmap(this.mColorDecorFilterData, bitmap);
        } else {
            int i2 = this.mHighlightFilterAmount;
            if (i2 >= 0) {
                bitmap = HighlightFilterHelper.createFilteredBitmap(bitmap, i2);
            }
        }
        boolean z = (this.mDisplayId == 2) || this.mIsVirtualDisplay;
        ImageSmartCropper imageSmartCropper = this.mImageSmartCropper;
        if (imageSmartCropper != null && !z) {
            imageSmartCropper.updateSmartCropRectIfNeeded(bitmap, i, this.mCurrentUserId);
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect rect2 = imageSmartCropper.mCropResult;
            if (rect2 == null) {
                this.mWallpaperManager.semSetSmartCropRect(1, rect, rect);
            } else {
                this.mWallpaperManager.semSetSmartCropRect(1, rect, rect2);
            }
        }
        return bitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.ArrayList getIntelligentCropHints(int r8) {
        /*
            r7 = this;
            boolean r0 = com.android.systemui.LsRune.WALLPAPER_SUB_WATCHFACE
            com.android.systemui.wallpaper.CoverWallpaper r1 = r7.mCoverWallpaper
            r2 = 1
            r3 = 0
            if (r0 != 0) goto Lc
            boolean r0 = com.android.systemui.LsRune.WALLPAPER_VIRTUAL_DISPLAY
            if (r0 == 0) goto L2d
        Lc:
            r0 = r8 & 16
            r4 = 16
            if (r0 != r4) goto L14
            r0 = r2
            goto L15
        L14:
            r0 = r3
        L15:
            r4 = r8 & 32
            r5 = 32
            if (r4 != r5) goto L1d
            r4 = r2
            goto L1e
        L1d:
            r4 = r3
        L1e:
            if (r0 != 0) goto L22
            if (r4 == 0) goto L2d
        L22:
            r0 = r1
            com.android.systemui.wallpaper.CoverWallpaperController r0 = (com.android.systemui.wallpaper.CoverWallpaperController) r0
            boolean r0 = r0.isCoverWallpaperRequired()
            if (r0 == 0) goto L2d
            r0 = r2
            goto L2e
        L2d:
            r0 = r3
        L2e:
            android.app.WallpaperManager r4 = r7.mWallpaperManager
            int r4 = r4.semGetWallpaperType(r8)
            r5 = 3
            com.android.systemui.wallpaper.PluginWallpaper r6 = r7.mPluginWallpaper
            if (r4 != r5) goto L43
            r4 = r6
            com.android.systemui.wallpaper.PluginWallpaperController r4 = (com.android.systemui.wallpaper.PluginWallpaperController) r4
            boolean r4 = r4.isPluginWallpaperRequired(r8)
            if (r4 == 0) goto L43
            goto L44
        L43:
            r2 = r3
        L44:
            java.lang.String r3 = r7.TAG
            if (r0 == 0) goto L54
            com.android.systemui.wallpaper.CoverWallpaperController r1 = (com.android.systemui.wallpaper.CoverWallpaperController) r1
            java.lang.String r7 = r1.getWallpaperIntelligentCrop()
            java.lang.String r8 = "getIntelligentCropHints: From CoverWallpaper. json = "
            com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m(r8, r7, r3)
            goto L88
        L54:
            if (r2 == 0) goto L76
            com.android.systemui.wallpaper.PluginWallpaperController r6 = (com.android.systemui.wallpaper.PluginWallpaperController) r6
            r6.getClass()
            int r7 = com.android.systemui.wallpaper.PluginWallpaperController.getScreen(r8)
            com.android.systemui.pluginlock.PluginWallpaperManager r8 = r6.mPluginWallpaperManager
            boolean r0 = r8.isFbeAvailable(r7)
            if (r0 == 0) goto L6c
            java.lang.String r7 = r8.getFbeWallpaperIntelligentCrop(r7)
            goto L70
        L6c:
            java.lang.String r7 = r8.getWallpaperIntelligentCrop(r7)
        L70:
            java.lang.String r8 = "getIntelligentCropHints: From PluginWallpaper. json = "
            androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0.m$1(r8, r7, r3)
            goto L88
        L76:
            android.app.WallpaperManager r0 = r7.mWallpaperManager
            int r7 = r7.mCurrentUserId
            android.os.Bundle r7 = r0.getWallpaperExtras(r8, r7)
            if (r7 != 0) goto L82
            r7 = 0
            return r7
        L82:
            java.lang.String r8 = "cropHints"
            java.lang.String r7 = r7.getString(r8)
        L88:
            java.util.ArrayList r7 = com.android.systemui.wallpaper.utils.IntelligentCropHelper.parseCropHints(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper.getIntelligentCropHints(int):java.util.ArrayList");
    }

    public final boolean hasIntelligentCropHints(int i) {
        ArrayList intelligentCropHints = getIntelligentCropHints(i);
        return intelligentCropHints != null && intelligentCropHints.size() > 0;
    }

    public final Bitmap loadBitmap(int i) {
        Bitmap wallpaperBitmap;
        Bitmap bitmapAsUser;
        boolean isWatchFace = WhichChecker.isWatchFace(i);
        String str = this.TAG;
        if (isWatchFace || WhichChecker.isVirtualDisplay(i)) {
            CoverWallpaper coverWallpaper = this.mCoverWallpaper;
            if (((CoverWallpaperController) coverWallpaper).isCoverWallpaperRequired()) {
                Log.i(str, "loadBitmap: Get cover wallpaper.");
                return ((CoverWallpaperController) coverWallpaper).getWallpaperBitmap();
            }
        }
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (wallpaperManager == null) {
            Log.d(str, "loadBitmap: mWallpaperManager is null.");
            return null;
        }
        int semGetWallpaperType = wallpaperManager.semGetWallpaperType(i);
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "loadBitmap: which = ", str);
        if (semGetWallpaperType == 3 || semGetWallpaperType == 1000) {
            wallpaperBitmap = ((PluginWallpaperController) this.mPluginWallpaper).getWallpaperBitmap(i);
            Log.i(str, "loadBitmap: Get plugin wallpaper. bitmap = " + wallpaperBitmap);
        } else {
            wallpaperBitmap = null;
        }
        if (WallpaperUtils.isValidBitmap(wallpaperBitmap)) {
            return wallpaperBitmap;
        }
        Log.i(str, "loadBitmap: displayId = " + this.mDisplayId + " which = " + i);
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && this.mWallpaperManager.semGetWallpaperType(i) != 0) {
            Log.d(str, "getBitmapFromWallpaperManager: Wallpaper type is not image.");
            return null;
        }
        if (hasIntelligentCropHints(i)) {
            ParcelFileDescriptor wallpaperFile = this.mWallpaperManager.getWallpaperFile(i, this.mCurrentUserId, false, 0);
            if (wallpaperFile == null) {
                bitmapAsUser = null;
            } else {
                bitmapAsUser = BitmapFactory.decodeFileDescriptor(wallpaperFile.getFileDescriptor());
                try {
                    wallpaperFile.close();
                } catch (IOException unused) {
                }
            }
        } else {
            bitmapAsUser = this.mWallpaperManager.getBitmapAsUser(this.mCurrentUserId, false, i, false);
        }
        if (!WallpaperUtils.isValidBitmap(bitmapAsUser) || bitmapAsUser.getByteCount() <= RecordingCanvas.MAX_BITMAP_SIZE) {
            return bitmapAsUser;
        }
        if (Debug.semIsProductDev()) {
            throw new RuntimeException("Wallpaper is too large! w=" + bitmapAsUser.getWidth() + ", h=" + bitmapAsUser.getHeight());
        }
        Log.e(str, "getBitmapFromWallpaperManager: Wallpaper is too large! w=" + bitmapAsUser.getWidth() + ", h=" + bitmapAsUser.getHeight());
        bitmapAsUser.recycle();
        return null;
    }

    public final Size reportSurfaceSize(int i) {
        Rect nearestCropHint = IntelligentCropHelper.getNearestCropHint(getDisplaySize(), getIntelligentCropHints(i));
        if (nearestCropHint != null) {
            this.mSurfaceSize.set(new Rect(0, 0, nearestCropHint.width(), nearestCropHint.height()));
        } else {
            useWallpaperBitmap(i, null);
            this.mSurfaceSize.set(this.mDimensions);
        }
        return new Size(this.mSurfaceSize.width(), this.mSurfaceSize.height());
    }

    public final void setLidState(int i) {
        boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z) {
            this.mLidState = i;
            ImageSmartCropper imageSmartCropper = this.mImageSmartCropper;
            if (imageSmartCropper != null) {
                imageSmartCropper.getClass();
                if (z) {
                    imageSmartCropper.mLidState = i;
                }
            }
        }
    }

    public final void useWallpaperBitmap(int i, Consumer consumer) {
        Bitmap bitmap;
        this.mRefCount.incrementAndGet();
        synchronized (this.mRefCount) {
            int i2 = i & 60;
            if (i2 == 0) {
                try {
                    Log.e(this.TAG, "useWallpaperBitmap: mode is missing on which. which=" + i, new RuntimeException());
                } finally {
                }
            }
            boolean z = i2 == 16;
            bitmap = z ? this.mSubBitmap : this.mBitmap;
            if (!WallpaperUtils.isValidBitmap(bitmap)) {
                bitmap = loadBitmap(i);
                if (z) {
                    this.mSubBitmap = bitmap;
                } else {
                    this.mBitmap = bitmap;
                }
                this.mWallpaperManager.forgetLoadedWallpaper();
                if (WallpaperUtils.isValidBitmap(bitmap)) {
                    Log.i(this.TAG, "useWallpaperBitmap: w=" + bitmap.getWidth() + ", h=" + bitmap.getHeight());
                    this.mIsWcgContent = this.mWallpaperManager.wallpaperSupportsWcg(bitmap);
                    this.mDimensions.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    if (((DownScaledSourceBitmap) this.mDownScaledSourceBitmapSet.get(Integer.valueOf(WhichChecker.getSourceWhich(i)))) == null) {
                        DownScaledSourceBitmap createDownScaledSourceBitmap = createDownScaledSourceBitmap(bitmap, i);
                        int sourceWhich = WhichChecker.getSourceWhich(i);
                        if (createDownScaledSourceBitmap == null) {
                            this.mDownScaledSourceBitmapSet.remove(Integer.valueOf(sourceWhich));
                        } else {
                            this.mDownScaledSourceBitmapSet.put(Integer.valueOf(sourceWhich), createDownScaledSourceBitmap);
                        }
                    }
                } else {
                    Log.w(this.TAG, "useWallpaperBitmap: Can't get bitmap");
                    this.mIsWcgContent = false;
                    this.mDownScaledSourceBitmapSet.remove(Integer.valueOf(WhichChecker.getSourceWhich(i)));
                }
            }
        }
        if (consumer != null) {
            consumer.accept(bitmap);
        }
        synchronized (this.mRefCount) {
            try {
                int decrementAndGet = this.mRefCount.decrementAndGet();
                if (decrementAndGet == 0 && bitmap != null) {
                    String str = this.TAG;
                    StringBuilder sb = new StringBuilder("useWallpaperBitmap: release 0x");
                    Bitmap bitmap2 = this.mBitmap;
                    sb.append(bitmap2 != null ? Integer.toHexString(bitmap2.hashCode()) : "null");
                    sb.append(" , ");
                    Bitmap bitmap3 = this.mSubBitmap;
                    sb.append(bitmap3 != null ? Integer.toHexString(bitmap3.hashCode()) : "null");
                    sb.append(", refCount=");
                    sb.append(decrementAndGet);
                    Log.i(str, sb.toString());
                    Bitmap bitmap4 = this.mBitmap;
                    if (bitmap4 != null) {
                        bitmap4.recycle();
                    }
                    Bitmap bitmap5 = this.mSubBitmap;
                    if (bitmap5 != null) {
                        bitmap5.recycle();
                    }
                    this.mBitmap = null;
                    this.mSubBitmap = null;
                }
            } finally {
            }
        }
    }
}
