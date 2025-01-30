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
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.DisplayInfo;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.utils.BitmapUtils;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ImageWallpaperCanvasHelper {
    public final String TAG;
    public Bitmap mBitmap;
    public Consumer mBitmapUpdateConsumer;
    public final Callback mCallback;
    public String mColorDecorFilterData;
    public final Context mContext;
    public final CoverWallpaper mCoverWallpaper;
    public int mCurDensityDpi;
    public int mCurrentUserId;
    public int mDeviceDisplayType;
    public final Rect mDimensions;
    public int mDisplayId;
    public int mHighlightFilterAmount;
    public final ImageSmartCropper mImageSmartCropper;
    public boolean mIsNightModeOn;
    public boolean mIsVirtualDisplay;
    public boolean mIsWcgContent;
    public final WallpaperLogger mLoggerWrapper;
    public int mOrientation;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DownScaledSourceBitmap {
        public final Bitmap mBitmap;
        public final float mScale;

        public DownScaledSourceBitmap(int i, Bitmap bitmap, float f) {
            this.mBitmap = bitmap;
            this.mScale = f;
        }
    }

    public ImageWallpaperCanvasHelper(Context context, int i, WallpaperLogger wallpaperLogger, SystemWallpaperColors systemWallpaperColors, CoverWallpaper coverWallpaper, IntelligentCropHelper intelligentCropHelper, boolean z, Callback callback) {
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
        this.mWallpaperManager = wallpaperManager;
        this.mCallback = callback;
        StringBuilder sb = new StringBuilder("ImageWallpaperCanvasHelper_");
        ImageWallpaper.CanvasEngine.C37162 c37162 = (ImageWallpaper.CanvasEngine.C37162) callback;
        c37162.getClass();
        int i2 = ImageWallpaper.CanvasEngine.$r8$clinit;
        sb.append(ImageWallpaper.CanvasEngine.this.getWallpaperFlags() == 2 ? 2 : 1);
        String sb2 = sb.toString();
        this.TAG = sb2;
        this.mDisplayId = i;
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            this.mIsVirtualDisplay = WallpaperManager.isVirtualWallpaperDisplay(context, i);
        }
        boolean z2 = LsRune.WALLPAPER_CACHED_WALLPAPER;
        if (z2) {
            if (!(this.mDisplayId == 2) && z2) {
                if (WallpaperUtils.isCachedWallpaperAvailable(1)) {
                    ((WallpaperLoggerImpl) wallpaperLogger).log(sb2, " Already exist in cache : main ");
                } else {
                    Bitmap bitmapFromWallpaperManager = getBitmapFromWallpaperManager(5);
                    if (bitmapFromWallpaperManager != null) {
                        ((WallpaperLoggerImpl) wallpaperLogger).log(sb2, "Load main bitmap save in cache " + bitmapFromWallpaperManager.getWidth() + "  , " + bitmapFromWallpaperManager.getHeight());
                        WallpaperUtils.setCachedWallpaper(bitmapFromWallpaperManager, 1);
                        WallpaperUtils.clearCachedSmartCroppedRect(1);
                    }
                }
                if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                    if (WallpaperUtils.isCachedWallpaperAvailable(17)) {
                        ((WallpaperLoggerImpl) wallpaperLogger).log(sb2, " Already exist in cache :  sub");
                    } else {
                        Bitmap bitmapFromWallpaperManager2 = getBitmapFromWallpaperManager(17);
                        if (bitmapFromWallpaperManager2 != null) {
                            ((WallpaperLoggerImpl) wallpaperLogger).log(sb2, "Load sub bitmap save in cache " + bitmapFromWallpaperManager2.getWidth() + "  , " + bitmapFromWallpaperManager2.getHeight());
                            WallpaperUtils.setCachedWallpaper(bitmapFromWallpaperManager2, 17);
                            WallpaperUtils.clearCachedSmartCroppedRect(17);
                        }
                    }
                }
            }
        }
        this.mImageSmartCropper = new ImageSmartCropper(context, i);
        this.mCurDensityDpi = context.getResources().getConfiguration().densityDpi;
        this.mOrientation = context.getResources().getConfiguration().orientation;
        this.mSmartCropYOffset = -1000000;
        WallpaperUtils.clearCachedSmartCroppedRect(getCurrentWhich());
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
            String filterData = ColorDecorFilterHelper.getFilterData(convertDisplayIdToMode | 1, context, context.getUserId());
            if (!TextUtils.isEmpty(filterData)) {
                this.mColorDecorFilterData = filterData;
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
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("createDownScaledSourceBitmap: longDisplay=", max, ", shortBmpLen=", min, ", scale=");
        m45m.append(max2);
        String sb = m45m.toString();
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

    public final Bitmap getBitmapFromWallpaperManager(int i) {
        boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        Bitmap bitmap = null;
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (z && wallpaperManager.semGetWallpaperType(i) != 0 && !wallpaperManager.isWaitingForUnlockUser(i, this.mCurrentUserId)) {
            Log.d(this.TAG, "getBitmapFromWallpaperManager: Wallpaper type is not image.");
            return null;
        }
        if (hasIntelligentCropHints(i)) {
            ParcelFileDescriptor wallpaperFile = wallpaperManager.getWallpaperFile(i, this.mCurrentUserId, false, 0);
            if (wallpaperFile != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(wallpaperFile.getFileDescriptor());
                try {
                    wallpaperFile.close();
                } catch (IOException unused) {
                }
            }
        } else {
            bitmap = wallpaperManager.getBitmapAsUser(this.mCurrentUserId, false, i, false);
        }
        if (bitmap == null || bitmap.getByteCount() <= RecordingCanvas.MAX_BITMAP_SIZE) {
            return bitmap;
        }
        throw new RuntimeException("Wallpaper is too large! w=" + bitmap.getWidth() + ", h=" + bitmap.getHeight());
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0011, code lost:
    
        if (r0 == 1) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x001a, code lost:
    
        if (r4.mWallpaperManager.getLidState() == 0) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getCurrentWhich() {
        int i;
        int i2 = this.mDisplayId;
        if (i2 == 2) {
            i = 8;
        } else {
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                if (LsRune.WALLPAPER_SUB_WATCHFACE) {
                }
                i = 16;
            }
            if (LsRune.COVER_VIRTUAL_DISPLAY && this.mIsVirtualDisplay) {
                i = 32;
            }
            i = 4;
        }
        ImageWallpaper.CanvasEngine.C37162 c37162 = (ImageWallpaper.CanvasEngine.C37162) this.mCallback;
        c37162.getClass();
        int i3 = ImageWallpaper.CanvasEngine.$r8$clinit;
        return ((ImageWallpaper.CanvasEngine.this.getWallpaperFlags() == 2 ? 2 : 1) & 3) | i;
    }

    public final Integer getDimFilterColor(int i) {
        float[] wallpaperFilterColor = ImageDarkModeFilter.getWallpaperFilterColor(this.mContext, this.mSystemWallpaperColors.getColor(i));
        if (wallpaperFilterColor == null) {
            return null;
        }
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && this.mDisplayId == 1) {
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
        if (bitmap == null) {
            Log.w(this.TAG, "getFilterAppliedBitmap : bitmap == null || mHelper == null");
            return null;
        }
        if (!TextUtils.isEmpty(this.mColorDecorFilterData)) {
            bitmap = ColorDecorFilterHelper.createFilteredBitmap(bitmap, this.mColorDecorFilterData);
        } else {
            int i2 = this.mHighlightFilterAmount;
            if (i2 >= 0) {
                bitmap = HighlightFilterHelper.createFilteredBitmap(bitmap, i2);
            }
        }
        if (bitmap != null) {
            boolean z = (this.mDisplayId == 2) || this.mIsVirtualDisplay;
            ImageSmartCropper imageSmartCropper = this.mImageSmartCropper;
            if (imageSmartCropper != null && !z) {
                imageSmartCropper.updateSmartCropRectIfNeeded(bitmap, i);
                Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                Rect rect2 = imageSmartCropper.mCropResult;
                WallpaperManager wallpaperManager = this.mWallpaperManager;
                if (rect2 == null) {
                    wallpaperManager.semSetSmartCropRect(1, rect, rect);
                } else {
                    wallpaperManager.semSetSmartCropRect(1, rect, rect2);
                }
            }
        }
        return bitmap;
    }

    public final ArrayList getIntelligentCropHints(int i) {
        String string;
        boolean z = LsRune.WALLPAPER_SUB_WATCHFACE;
        boolean z2 = false;
        CoverWallpaper coverWallpaper = this.mCoverWallpaper;
        if (z || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            if ((((i & 16) == 16) || ((i & 32) == 32)) && ((CoverWallpaperController) coverWallpaper).isCoverWallpaperRequired()) {
                z2 = true;
            }
        }
        if (z2) {
            string = ((CoverWallpaperController) coverWallpaper).getWallpaperIntelligentCrop();
            Log.i(this.TAG, KeyAttributes$$ExternalSyntheticOutline0.m21m("getIntelligentCropHints: From CoverWallpaper. json = ", string));
        } else {
            Bundle wallpaperExtras = this.mWallpaperManager.getWallpaperExtras(i, this.mCurrentUserId);
            if (wallpaperExtras == null) {
                return null;
            }
            string = wallpaperExtras.getString("cropHints");
        }
        return IntelligentCropHelper.parseCropHints(string);
    }

    public final boolean hasIntelligentCropHints(int i) {
        ArrayList intelligentCropHints = getIntelligentCropHints(i);
        return intelligentCropHints != null && intelligentCropHints.size() > 0;
    }

    public final Bitmap loadBitmap(int i) {
        Bitmap bitmapFromWallpaperManager;
        Rect nearestCropHint;
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (wallpaperManager == null) {
            return null;
        }
        String str = "loadBitmap: displayId=" + this.mDisplayId + " which=" + i;
        String str2 = this.TAG;
        Log.i(str2, str);
        if (LsRune.WALLPAPER_PLAY_GIF) {
            if (((i & 16) == 16) || ((i & 32) == 32)) {
                CoverWallpaperController coverWallpaperController = (CoverWallpaperController) this.mCoverWallpaper;
                if (coverWallpaperController.isCoverWallpaperRequired()) {
                    Log.i(str2, "loadCachedBitmapByWhich: from cover wallpaper controller");
                    Bitmap wallpaperBitmap = coverWallpaperController.getWallpaperBitmap(!hasIntelligentCropHints(i));
                    if (!hasIntelligentCropHints(i) || (nearestCropHint = IntelligentCropHelper.getNearestCropHint(getDisplaySize(), getIntelligentCropHints(i))) == null || wallpaperBitmap == null) {
                        return wallpaperBitmap;
                    }
                    Log.i(str2, "loadCachedBitmapByWhich: cropRect = " + nearestCropHint + ", bitmap w = " + wallpaperBitmap.getWidth() + ", h = " + wallpaperBitmap.getHeight());
                    if (wallpaperBitmap.getWidth() < nearestCropHint.right || wallpaperBitmap.getHeight() < nearestCropHint.bottom) {
                        return wallpaperBitmap;
                    }
                    bitmapFromWallpaperManager = Bitmap.createBitmap(wallpaperBitmap, nearestCropHint.left, nearestCropHint.top, nearestCropHint.width(), nearestCropHint.height());
                    return bitmapFromWallpaperManager;
                }
                if (wallpaperManager.semGetWallpaperType(i) == 3) {
                    Log.i(str2, "loadCachedBitmapByWhich: Just return null in case of custom pack.");
                    return null;
                }
            }
        }
        if (!LsRune.WALLPAPER_CACHED_WALLPAPER) {
            bitmapFromWallpaperManager = getBitmapFromWallpaperManager(i);
        } else if (WallpaperUtils.isCachedWallpaperAvailable(i)) {
            Log.i(str2, "loadCachedBitmapByWhich: get cached bitmap " + i);
            bitmapFromWallpaperManager = WallpaperUtils.getCachedWallpaper(i);
        } else {
            Log.i(str2, "loadCachedBitmapByWhich: from wallpaper manager " + i);
            bitmapFromWallpaperManager = getBitmapFromWallpaperManager(i);
            WallpaperUtils.clearCachedWallpaper(i);
            WallpaperUtils.setCachedWallpaper(bitmapFromWallpaperManager, i);
        }
        return bitmapFromWallpaperManager;
    }

    public final Size reportSurfaceSize(int i) {
        Rect nearestCropHint = IntelligentCropHelper.getNearestCropHint(getDisplaySize(), getIntelligentCropHints(i));
        Rect rect = this.mSurfaceSize;
        if (nearestCropHint == null) {
            useWallpaperBitmap(i, null);
            rect.set(this.mDimensions);
        } else if (WhichChecker.isWatchFace(i) && ((CoverWallpaperController) this.mCoverWallpaper).isCoverWallpaperRequired()) {
            rect.set(new Rect(0, 0, getDisplaySize().x, getDisplaySize().y));
        } else {
            rect.set(new Rect(0, 0, nearestCropHint.width(), nearestCropHint.height()));
        }
        return new Size(rect.width(), rect.height());
    }

    public final void setLidState(int i) {
        boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z) {
            this.mLidState = i;
            ImageSmartCropper imageSmartCropper = this.mImageSmartCropper;
            if (imageSmartCropper == null || !z) {
                return;
            }
            imageSmartCropper.mLidState = i;
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
            boolean z = false;
            boolean z2 = i2 == 16;
            bitmap = z2 ? this.mSubBitmap : this.mBitmap;
            if (bitmap == null) {
                bitmap = loadBitmap(i);
                if (WhichChecker.isWatchFace(i) && ((CoverWallpaperController) this.mCoverWallpaper).isCoverWallpaperRequired()) {
                    bitmap = BitmapUtils.fitToScreen(this.mContext, bitmap, true);
                }
                if (z2) {
                    this.mSubBitmap = bitmap;
                } else {
                    this.mBitmap = bitmap;
                }
                this.mWallpaperManager.forgetLoadedWallpaper();
                if (bitmap != null) {
                    Log.i(this.TAG, "useWallpaperBitmap: w=" + bitmap.getWidth() + ", h=" + bitmap.getHeight());
                    this.mIsWcgContent = this.mWallpaperManager.wallpaperSupportsWcg(bitmap);
                    this.mDimensions.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    if (((DownScaledSourceBitmap) this.mDownScaledSourceBitmapSet.get(Integer.valueOf(WhichChecker.isFlagEnabled(i, 1) && WhichChecker.isFlagEnabled(i, 2) ? i2 | 1 : i))) == null) {
                        DownScaledSourceBitmap createDownScaledSourceBitmap = createDownScaledSourceBitmap(bitmap, i);
                        if (WhichChecker.isFlagEnabled(i, 1) && WhichChecker.isFlagEnabled(i, 2)) {
                            z = true;
                        }
                        if (z) {
                            i = i2 | 1;
                        }
                        HashMap hashMap = this.mDownScaledSourceBitmapSet;
                        if (createDownScaledSourceBitmap == null) {
                            hashMap.remove(Integer.valueOf(i));
                        } else {
                            hashMap.put(Integer.valueOf(i), createDownScaledSourceBitmap);
                        }
                    }
                } else {
                    Log.w(this.TAG, "useWallpaperBitmap: Can't get bitmap");
                    this.mIsWcgContent = false;
                    if (WhichChecker.isFlagEnabled(i, 1) && WhichChecker.isFlagEnabled(i, 2)) {
                        z = true;
                    }
                    if (z) {
                        i = i2 | 1;
                    }
                    this.mDownScaledSourceBitmapSet.remove(Integer.valueOf(i));
                }
            }
        }
        if (consumer != null) {
            consumer.accept(bitmap);
        }
        synchronized (this.mRefCount) {
            int decrementAndGet = this.mRefCount.decrementAndGet();
            if (decrementAndGet == 0 && bitmap != null) {
                if (!LsRune.WALLPAPER_CACHED_WALLPAPER) {
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
                }
                this.mBitmap = null;
                this.mSubBitmap = null;
            }
        }
    }
}
