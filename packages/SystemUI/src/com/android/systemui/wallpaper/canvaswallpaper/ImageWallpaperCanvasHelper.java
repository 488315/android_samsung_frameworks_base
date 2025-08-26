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
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.util.DeviceType;
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
import com.android.systemui.wallpapers.ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ImageWallpaperCanvasHelper {
    public final String TAG;
    public Bitmap mBitmap;
    public ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1 mBitmapUpdateConsumer;
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

    public interface Callback {
    }

    public class DownScaledSourceBitmap {
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
        String string = sb.toString();
        this.TAG = string;
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
            ((WallpaperLoggerImpl) wallpaperLogger).log(string, " initial lid state : " + convertLidStateToString(lidState) + " , " + context.getResources().getConfiguration().semDisplayDeviceType);
            int i3 = context.getResources().getConfiguration().semDisplayDeviceType;
            this.mDeviceDisplayType = i3;
            if (i3 == 5 && lidState != 0) {
                Log.i(string, " flex mode ".concat(convertLidStateToString(0)));
                lidState = 0;
            }
            setLidState(lidState);
        }
        this.mIsNightModeOn = (context.getResources().getConfiguration().uiMode & 32) != 0;
        int iConvertDisplayIdToMode = WhichChecker.convertDisplayIdToMode(i, context);
        if (iConvertDisplayIdToMode >= 0) {
            Bundle wallpaperExtras = WallpaperManager.getInstance(context).getWallpaperExtras(iConvertDisplayIdToMode | 1, context.getUserId());
            String string2 = wallpaperExtras != null ? wallpaperExtras.getString("imageFilterParams") : null;
            if (!TextUtils.isEmpty(string2)) {
                this.mColorDecorFilterData = string2;
            }
        }
        if (TextUtils.isEmpty(this.mColorDecorFilterData)) {
            this.mHighlightFilterAmount = 60;
        }
    }

    public static String convertLidStateToString(int i) {
        return LsRune.WALLPAPER_SUB_DISPLAY_MODE ? i == 1 ? "LID_OPEN" : i == 0 ? "LID_CLOSED" : "LID_UNKNOWN" : "LID_UNKNOWN";
    }

    public final DownScaledSourceBitmap createDownScaledSourceBitmap(Bitmap bitmap, int i) {
        Point displaySize = getDisplaySize();
        int iMax = Math.max(displaySize.x, displaySize.y);
        int iMin = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float fMax = ((int) Math.max(1024.0f, iMax * 0.5f)) / iMin;
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(iMax, iMin, "createDownScaledSourceBitmap: longDisplay=", ", shortBmpLen=", ", scale=");
        sbM.append(fMax);
        String string = sbM.toString();
        String str = this.TAG;
        Log.d(str, string);
        if (fMax > 1.0f) {
            return null;
        }
        Bitmap bitmapCopy = fMax == 1.0f ? bitmap.copy(bitmap.getConfig(), false) : Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * fMax), (int) (bitmap.getHeight() * fMax), true);
        if (bitmapCopy != null && bitmapCopy != bitmap) {
            return new DownScaledSourceBitmap(i, bitmapCopy, fMax);
        }
        Log.e(str, "createDownScaledSourceBitmap: Resized bitmap creation failed. org=" + bitmap + ", resized=" + bitmapCopy);
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getCurrentWhich() {
        int i;
        int i2 = this.mDisplayId;
        if (i2 == 2) {
            i = 8;
        } else if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
            if (LsRune.WALLPAPER_SUB_WATCHFACE) {
                if (i2 == 1) {
                }
            } else if (this.mWallpaperManager.getLidState() == 0) {
            }
            i = 16;
        } else {
            i = (LsRune.COVER_VIRTUAL_DISPLAY && this.mIsVirtualDisplay) ? 32 : 4;
        }
        ImageWallpaper.CanvasEngine.AnonymousClass3 anonymousClass3 = (ImageWallpaper.CanvasEngine.AnonymousClass3) this.mCallback;
        anonymousClass3.getClass();
        int i3 = ImageWallpaper.CanvasEngine.$r8$clinit;
        return ((ImageWallpaper.CanvasEngine.this.getWallpaperFlags() == 2 ? 2 : 1) & 3) | i;
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
        if (TextUtils.isEmpty(this.mColorDecorFilterData)) {
            int i2 = this.mHighlightFilterAmount;
            if (i2 >= 0) {
                bitmap = HighlightFilterHelper.createFilteredBitmap(bitmap, i2);
            }
        } else {
            bitmap = ColorDecorFilterHelper.createFilteredBitmap(this.mColorDecorFilterData, bitmap);
        }
        boolean z = (this.mDisplayId == 2) || this.mIsVirtualDisplay;
        ImageSmartCropper imageSmartCropper = this.mImageSmartCropper;
        if (imageSmartCropper != null && !z) {
            imageSmartCropper.updateSmartCropRectIfNeeded(bitmap, i, this.mCurrentUserId);
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect rect2 = imageSmartCropper.mCropResult;
            if (rect2 == null) {
                this.mWallpaperManager.semSetSmartCropRect(1, rect, rect);
                return bitmap;
            }
            this.mWallpaperManager.semSetSmartCropRect(1, rect, rect2);
        }
        return bitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ArrayList getIntelligentCropHints(int i) {
        boolean z;
        String string;
        boolean z2 = LsRune.WALLPAPER_SUB_WATCHFACE;
        CoverWallpaper coverWallpaper = this.mCoverWallpaper;
        if (z2 || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            z = (((i & 16) == 16) || ((i & 32) == 32)) && ((CoverWallpaperController) coverWallpaper).isCoverWallpaperRequired();
        }
        int iSemGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(i);
        PluginWallpaper pluginWallpaper = this.mPluginWallpaper;
        boolean z3 = iSemGetWallpaperType == 3 && ((PluginWallpaperController) pluginWallpaper).isPluginWallpaperRequired(i);
        String str = this.TAG;
        if (z) {
            string = ((CoverWallpaperController) coverWallpaper).getWallpaperIntelligentCrop();
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("getIntelligentCropHints: From CoverWallpaper. json = ", string, str);
        } else if (z3) {
            PluginWallpaperController pluginWallpaperController = (PluginWallpaperController) pluginWallpaper;
            pluginWallpaperController.getClass();
            int screen = PluginWallpaperController.getScreen(i);
            PluginWallpaperManager pluginWallpaperManager = pluginWallpaperController.mPluginWallpaperManager;
            string = pluginWallpaperManager.isFbeAvailable(screen) ? pluginWallpaperManager.getFbeWallpaperIntelligentCrop(screen) : pluginWallpaperManager.getWallpaperIntelligentCrop(screen);
            Log.e(str, "getIntelligentCropHints: From PluginWallpaper. json = " + string);
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

    public final Bitmap loadBitmap(int i) throws IOException {
        Bitmap wallpaperBitmap;
        Bitmap bitmapAsUser;
        boolean zIsWatchFace = WhichChecker.isWatchFace(i);
        String str = this.TAG;
        if (zIsWatchFace || WhichChecker.isVirtualDisplay(i)) {
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
        int iSemGetWallpaperType = wallpaperManager.semGetWallpaperType(i);
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "loadBitmap: which = ", str);
        if (iSemGetWallpaperType == 3 || iSemGetWallpaperType == 1000) {
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
        if (!DeviceType.isShipBuild()) {
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
        Bitmap bitmapLoadBitmap;
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
            bitmapLoadBitmap = z ? this.mSubBitmap : this.mBitmap;
            if (!WallpaperUtils.isValidBitmap(bitmapLoadBitmap)) {
                bitmapLoadBitmap = loadBitmap(i);
                if (z) {
                    this.mSubBitmap = bitmapLoadBitmap;
                } else {
                    this.mBitmap = bitmapLoadBitmap;
                }
                this.mWallpaperManager.forgetLoadedWallpaper();
                if (WallpaperUtils.isValidBitmap(bitmapLoadBitmap)) {
                    Log.i(this.TAG, "useWallpaperBitmap: w=" + bitmapLoadBitmap.getWidth() + ", h=" + bitmapLoadBitmap.getHeight());
                    this.mIsWcgContent = this.mWallpaperManager.wallpaperSupportsWcg(bitmapLoadBitmap);
                    this.mDimensions.set(0, 0, bitmapLoadBitmap.getWidth(), bitmapLoadBitmap.getHeight());
                    if (((DownScaledSourceBitmap) this.mDownScaledSourceBitmapSet.get(Integer.valueOf(WhichChecker.getSourceWhich(i)))) == null) {
                        DownScaledSourceBitmap downScaledSourceBitmapCreateDownScaledSourceBitmap = createDownScaledSourceBitmap(bitmapLoadBitmap, i);
                        int sourceWhich = WhichChecker.getSourceWhich(i);
                        if (downScaledSourceBitmapCreateDownScaledSourceBitmap == null) {
                            this.mDownScaledSourceBitmapSet.remove(Integer.valueOf(sourceWhich));
                        } else {
                            this.mDownScaledSourceBitmapSet.put(Integer.valueOf(sourceWhich), downScaledSourceBitmapCreateDownScaledSourceBitmap);
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
            consumer.accept(bitmapLoadBitmap);
        }
        synchronized (this.mRefCount) {
            try {
                int iDecrementAndGet = this.mRefCount.decrementAndGet();
                if (iDecrementAndGet == 0 && bitmapLoadBitmap != null) {
                    String str = this.TAG;
                    StringBuilder sb = new StringBuilder("useWallpaperBitmap: release 0x");
                    Bitmap bitmap = this.mBitmap;
                    sb.append(bitmap != null ? Integer.toHexString(bitmap.hashCode()) : "null");
                    sb.append(" , ");
                    Bitmap bitmap2 = this.mSubBitmap;
                    sb.append(bitmap2 != null ? Integer.toHexString(bitmap2.hashCode()) : "null");
                    sb.append(", refCount=");
                    sb.append(iDecrementAndGet);
                    Log.i(str, sb.toString());
                    Bitmap bitmap3 = this.mBitmap;
                    if (bitmap3 != null) {
                        bitmap3.recycle();
                    }
                    Bitmap bitmap4 = this.mSubBitmap;
                    if (bitmap4 != null) {
                        bitmap4.recycle();
                    }
                    this.mBitmap = null;
                    this.mSubBitmap = null;
                }
            } finally {
            }
        }
    }
}
