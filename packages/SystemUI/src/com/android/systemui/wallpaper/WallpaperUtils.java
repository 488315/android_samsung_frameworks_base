package com.android.systemui.wallpaper;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.view.SemWindowManager;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/* loaded from: classes3.dex */
public class WallpaperUtils {
    public static boolean mIsAdaptiveColorMode = false;
    public static boolean mIsAdaptiveColorModeSub = false;
    public static boolean mIsEmergencyMode = false;
    public static boolean mIsExternalLiveWallpaper = false;
    public static boolean mIsInfinityLiveWallpaper = false;
    public static boolean mIsUltraPowerSavingMode = false;
    private static SettingsHelper mSettingsHelper = null;
    public static int sCurrentWhich = 6;
    public static int sFullScreenAodFeature = -1;
    public static final SparseArray sCachedSmartCroppedRect = new SparseArray();
    public static final SparseArray sCachedWallpaperColors = new SparseArray();
    public static int sWallpaperType = -1;

    public static Bitmap decodeStreamConsiderQMG(InputStream inputStream, Rect rect, BitmapFactory.Options options) throws NoSuchMethodException, IOException, SecurityException {
        Bitmap bitmapDecodeStream;
        int i;
        int i2;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(2);
        try {
            i = bufferedInputStream.read();
            i2 = bufferedInputStream.read();
            bufferedInputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (i == 81 && i2 == 71) {
            Bitmap bitmap = null;
            try {
                try {
                    Method method = Class.forName("android.graphics.BitmapFactory").getMethod("decodeStreamQMG", InputStream.class, Rect.class, BitmapFactory.Options.class);
                    method.setAccessible(true);
                    bitmapDecodeStream = (Bitmap) method.invoke(null, bufferedInputStream, rect, options);
                    if (bitmapDecodeStream == null) {
                        try {
                            if (!options.inJustDecodeBounds) {
                                Log.w("WallpaperUtils", "decodeStreamQMG() bitmap is null");
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bitmap = bitmapDecodeStream;
                            e.printStackTrace();
                            bitmapDecodeStream = bitmap;
                            bufferedInputStream.close();
                            inputStream.close();
                            return bitmapDecodeStream;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (NoSuchMethodException unused) {
                bitmapDecodeStream = BitmapFactory.decodeStream(bufferedInputStream, rect, options);
                if (bitmapDecodeStream == null && !options.inJustDecodeBounds) {
                    Log.w("WallpaperUtils", "decodeStream() bitmap is null");
                }
            }
        } else {
            bitmapDecodeStream = BitmapFactory.decodeStream(bufferedInputStream, rect, options);
            if (bitmapDecodeStream == null && !options.inJustDecodeBounds) {
                Log.w("WallpaperUtils", "decodeStream() bitmap is null");
            }
        }
        try {
            bufferedInputStream.close();
            inputStream.close();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return bitmapDecodeStream;
    }

    public static void dump(Context context, PrintWriter printWriter) {
        printWriter.println("Dump of WallpaperUtils: ");
        printWriter.print("  isAdaptiveColorMode: ");
        printWriter.println(mSettingsHelper.isAdaptiveColorMode());
        printWriter.print("  Type: ");
        printWriter.println(sWallpaperType);
        printWriter.print("  isExternalLiveWallpaper: ");
        printWriter.println(mIsExternalLiveWallpaper);
        printWriter.print("  isInfinityLiveWallpaper: ");
        printWriter.println(mIsInfinityLiveWallpaper);
        printWriter.print("  Emergency mode: ");
        printWriter.println(mIsEmergencyMode);
        printWriter.print("  UltraPowerSavingMode: ");
        printWriter.println(mIsUltraPowerSavingMode);
        printWriter.print("  DeXMode: ");
        printWriter.println(false);
        printWriter.print("  isVideoWallpaper: ");
        printWriter.println(isVideoWallpaper(context));
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            printWriter.print("  sCachedWallpaperColors(FLAG_DISPLAY_PHONE): ");
            SparseArray sparseArray = sCachedWallpaperColors;
            printWriter.println(sparseArray.get(4));
            printWriter.print("  sCachedWallpaperColors(FLAG_DISPLAY_SUB): ");
            printWriter.println(sparseArray.get(16));
        }
        printWriter.println();
    }

    public static SemWallpaperColors getCachedSemWallpaperColors(boolean z) {
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            SparseArray sparseArray = sCachedWallpaperColors;
            if (sparseArray.size() > 0) {
                return (SemWallpaperColors) sparseArray.get(z ? 16 : 4);
            }
        }
        return SemWallpaperColors.getBlankWallpaperColors();
    }

    public static Point getRealScreenSize(Context context, boolean z) {
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Point point = new Point();
        if (!z) {
            context.getDisplay().getRealSize(point);
        } else if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
            for (Display display : displays) {
                if (display.getDisplayId() == 1) {
                    display.getRealSize(point);
                }
            }
        } else if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            Display[] displays2 = displayManager.getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
            if (displays2.length > 0) {
                displays2[0].getRealSize(point);
            }
        }
        LogUtil.i("WallpaperUtils", "getScreenSize: " + point, new Object[0]);
        return point;
    }

    public static Bitmap getRotatedBitmap(Bitmap bitmap, int i) {
        int i2 = i == 1 ? 90 : 270;
        Matrix matrix = new Matrix();
        matrix.postRotate(-i2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap getScreenShot(Context context, int i, int i2, int i3) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        SemWindowManager semWindowManager = SemWindowManager.getInstance();
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "getScreenShot: start, width = ", " , height = ", " , mRotation = ");
        sbM.append(i3);
        Log.i("WallpaperUtils", sbM.toString());
        Bitmap bitmapScreenshot = semWindowManager.screenshot(windowManager.getDefaultDisplay().getDisplayId(), 2000, false, new Rect(0, 0, 0, 0), Math.min(i, i2), Math.max(i, i2), true, 0, true);
        Log.i("WallpaperUtils", "getScreenShot: end bitmap = " + bitmapScreenshot);
        if (i3 == 0 || bitmapScreenshot == null) {
            return bitmapScreenshot;
        }
        Bitmap rotatedBitmap = getRotatedBitmap(bitmapScreenshot, i3);
        bitmapScreenshot.recycle();
        return rotatedBitmap;
    }

    public static Bitmap getVideoFrame(Context context, Uri uri, String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        MediaMetadataRetriever.BitmapParams bitmapParams = new MediaMetadataRetriever.BitmapParams();
        bitmapParams.setPreferredConfig(Bitmap.Config.RGBA_F16);
        try {
            if (!TextUtils.isEmpty(str)) {
                mediaMetadataRetriever.setDataSource(str);
            } else if (uri != null) {
                mediaMetadataRetriever.setDataSource(context, uri);
            }
            Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime(0L, 2, bitmapParams);
            try {
                return frameAtTime;
            } catch (Throwable th) {
                return frameAtTime;
            }
        } catch (Throwable th2) {
            try {
                th2.printStackTrace();
                try {
                    mediaMetadataRetriever.close();
                } catch (Throwable th3) {
                    th3.printStackTrace();
                }
                return null;
            } finally {
                try {
                    mediaMetadataRetriever.close();
                } catch (Throwable th4) {
                    th4.printStackTrace();
                }
            }
        }
    }

    public static boolean isAdaptiveColorEnabled() {
        try {
            if (mSettingsHelper.isAdaptiveColorMode() && !mIsEmergencyMode && !mIsUltraPowerSavingMode && !mIsExternalLiveWallpaper) {
                if (!mSettingsHelper.isOpenThemeLockWallpaper()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.d("WallpaperUtils", "isAdaptiveColorEnabled: Error while reading settings (" + e.getMessage() + ")");
            return false;
        }
    }

    public static boolean isNoSensorRequired(Context context) {
        if (!mSettingsHelper.isLockScreenRotationAllowed()) {
            Log.i("WallpaperUtils", "isNoSensorRequired: Lockscreen rotation is not allowed.");
            return true;
        }
        boolean z = mIsUltraPowerSavingMode;
        boolean z2 = mIsEmergencyMode;
        if (z || z2) {
            Log.i("WallpaperUtils", "isNoSensorRequired: upsm or emergency mode.");
            return false;
        }
        if (isVideoWallpaper(context)) {
            if (isSubDisplay()) {
                Log.i("WallpaperUtils", "isNoSensorRequired: video and sub display.");
                return true;
            }
            if (!LsRune.WALLPAPER_ROTATABLE_WALLPAPER) {
                Log.i("WallpaperUtils", "isNoSensorRequired: video and !LsRune.WALLPAPER_ROTATABLE_WALLPAPER.");
                return true;
            }
        }
        Log.i("WallpaperUtils", "isNoSensorRequired: Returns false.");
        return false;
    }

    public static boolean isOpenThemeLockWallpaper() {
        return mSettingsHelper.isOpenThemeLockWallpaper();
    }

    public static boolean isOpenThemeLook() {
        return mSettingsHelper.isOpenThemeLook();
    }

    public static boolean isShowWallpaperOnAodEnabled(int i) {
        int i2 = sFullScreenAodFeature;
        return (i2 != 1 ? i2 != 2 ? i2 != 3 ? false : WhichChecker.isFlagEnabled(i, 16) : WhichChecker.isFlagEnabled(i, 4) : true) && mSettingsHelper.isAODEnabled() && mSettingsHelper.isAODShowLockWallpaper();
    }

    public static boolean isSubDisplay(int i) {
        return (i & 60) == 16;
    }

    public static boolean isValidBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return false;
        }
        if (!bitmap.isRecycled()) {
            return true;
        }
        Log.e("WallpaperUtils", "isValidBitmap: Bitmap is recycled. bitmap = " + bitmap, new Exception());
        return false;
    }

    public static boolean isVideoWallpaper(Context context) {
        if (!LsRune.WALLPAPER_VIDEO_WALLPAPER) {
            return false;
        }
        int iSemGetWallpaperType = WallpaperManager.getInstance(context).semGetWallpaperType(sCurrentWhich);
        if (iSemGetWallpaperType != 8) {
            return iSemGetWallpaperType == 3 && ((PluginWallpaperController) Dependency.sDependency.getDependencyInner(PluginWallpaperController.class)).isPluginWallpaperRequired(sCurrentWhich) && ((PluginWallpaperController) Dependency.sDependency.getDependencyInner(PluginWallpaperController.class)).containsVideo(sCurrentWhich);
        }
        return true;
    }

    public static boolean isWhiteKeyguardWallpaper(long j, boolean z) {
        KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
        SemWallpaperColors.Item hint = keyguardWallpaperController != null ? keyguardWallpaperController.getHint(j, z) : null;
        return hint != null ? hint.getFontColor() == 1 : mSettingsHelper.isWhiteKeyguardWallpaper();
    }

    public static void loadDeviceState(int i, Context context) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            sCurrentWhich = z ? WallpaperManager.getInstance(context).getLidState() == 0 ? 18 : 6 : 2;
            SemWallpaperColors semWallpaperColorsSemGetWallpaperColors = wallpaperManager.semGetWallpaperColors(6);
            SparseArray sparseArray = sCachedWallpaperColors;
            sparseArray.put(4, semWallpaperColorsSemGetWallpaperColors);
            sparseArray.put(16, wallpaperManager.semGetWallpaperColors(18));
        }
        mIsEmergencyMode = Settings.System.getIntForUser(context.getContentResolver(), SettingsHelper.INDEX_EMERGENCY_MODE, 0, i) == 1;
        mIsUltraPowerSavingMode = Settings.System.getIntForUser(context.getContentResolver(), SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE, 0, i) == 1 || Settings.System.getIntForUser(context.getContentResolver(), SettingsHelper.INDEX_MINIMAL_BATTERY_USE, 0, i) == 1;
        mIsAdaptiveColorMode = mSettingsHelper.isAdaptiveColorMode();
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            mIsAdaptiveColorModeSub = mSettingsHelper.isAdaptiveColorMode(true);
        }
        setWallpaperType(wallpaperManager.semGetWallpaperType(sCurrentWhich), context);
        sFullScreenAodFeature = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1);
    }

    public static void registerSystemUIWidgetCallback(SystemUIWidgetCallback systemUIWidgetCallback, long j) {
        if (j == 0) {
            return;
        }
        if (j != -1) {
            j |= 1;
        }
        if ((32 & j) != 0) {
            j |= 2;
        }
        if (WallpaperEventNotifier.getInstance() != null) {
            WallpaperEventNotifier.getInstance().registerCallback(false, systemUIWidgetCallback, j);
        }
    }

    public static void removeSystemUIWidgetCallback(SystemUIWidgetCallback systemUIWidgetCallback) {
        if (WallpaperEventNotifier.getInstance() != null) {
            WallpaperEventNotifier.getInstance().removeCallback(false, systemUIWidgetCallback);
        }
    }

    public static void setSettingsHelper(SettingsHelper settingsHelper) {
        mSettingsHelper = settingsHelper;
    }

    public static void setWallpaperType(int i, Context context) {
        sWallpaperType = i;
        WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
        if (sWallpaperType == 7) {
            mIsExternalLiveWallpaper = wallpaperManager.isStockLiveWallpaper(sCurrentWhich);
            mIsInfinityLiveWallpaper = "infinity".equals(new SemWallpaperProperties(context, sCurrentWhich, context.getUserId()).getContentType());
        } else {
            mIsExternalLiveWallpaper = false;
            mIsInfinityLiveWallpaper = false;
        }
    }

    public static boolean isSubDisplay() {
        return LsRune.WALLPAPER_SUB_DISPLAY_MODE && (sCurrentWhich & 16) == 16;
    }

    public static boolean isWhiteKeyguardWallpaper(String str) {
        long jConvertFlag = SystemUIWidgetUtil.convertFlag(str);
        if (jConvertFlag < 0) {
            return false;
        }
        return isWhiteKeyguardWallpaper(jConvertFlag, false);
    }
}
