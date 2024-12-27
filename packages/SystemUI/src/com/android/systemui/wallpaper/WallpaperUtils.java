package com.android.systemui.wallpaper;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Debug;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WallpaperUtils {
    public static boolean mIsAdaptiveColorMode = false;
    public static boolean mIsAdaptiveColorModeSub = false;
    public static boolean mIsEmergencyMode = false;
    public static boolean mIsExternalLiveWallpaper = false;
    public static boolean mIsInfinityLiveWallpaper = false;
    public static boolean mIsUltraPowerSavingMode = false;
    private static SettingsHelper mSettingsHelper = null;
    public static int sCurrentWhich = 6;
    public static final SparseArray sCachedSmartCroppedRect = new SparseArray();
    public static final SparseArray sCachedWallpaperColors = new SparseArray();
    public static int sWallpaperType = -1;

    static {
        Debug.semIsProductDev();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:0|1|2|3|4|(7:8|9|10|(3:12|13|(1:15))|20|21|22)|34|(1:38)|20|21|22|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0086, code lost:
    
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0087, code lost:
    
        r10.printStackTrace();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap decodeStreamConsiderQMG(java.io.InputStream r10, android.graphics.Rect r11, android.graphics.BitmapFactory.Options r12) {
        /*
            java.lang.String r0 = "decodeStream() bitmap is null"
            java.lang.String r1 = "WallpaperUtils"
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream
            r2.<init>(r10)
            r3 = 2
            r2.mark(r3)
            int r4 = r2.read()     // Catch: java.io.IOException -> L6e
            int r5 = r2.read()     // Catch: java.io.IOException -> L6e
            r2.reset()     // Catch: java.io.IOException -> L6e
            r6 = 81
            if (r4 != r6) goto L72
            r4 = 71
            if (r5 != r4) goto L72
            r4 = 0
            java.lang.String r5 = "android.graphics.BitmapFactory"
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            java.lang.String r6 = "decodeStreamQMG"
            r7 = 3
            java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            java.lang.Class<java.io.InputStream> r8 = java.io.InputStream.class
            r9 = 0
            r7[r9] = r8     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            java.lang.Class<android.graphics.Rect> r8 = android.graphics.Rect.class
            r9 = 1
            r7[r9] = r8     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            java.lang.Class<android.graphics.BitmapFactory$Options> r8 = android.graphics.BitmapFactory.Options.class
            r7[r3] = r8     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            java.lang.reflect.Method r3 = r5.getMethod(r6, r7)     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            r3.setAccessible(r9)     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            java.lang.Object[] r5 = new java.lang.Object[]{r2, r11, r12}     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            java.lang.Object r3 = r3.invoke(r4, r5)     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            android.graphics.Bitmap r3 = (android.graphics.Bitmap) r3     // Catch: java.lang.Exception -> L5a java.lang.NoSuchMethodException -> L60
            if (r3 != 0) goto L7f
            boolean r4 = r12.inJustDecodeBounds     // Catch: java.lang.Exception -> L57 java.lang.NoSuchMethodException -> L60
            if (r4 != 0) goto L7f
            java.lang.String r4 = "decodeStreamQMG() bitmap is null"
            android.util.Log.w(r1, r4)     // Catch: java.lang.Exception -> L57 java.lang.NoSuchMethodException -> L60
            goto L7f
        L57:
            r11 = move-exception
            r4 = r3
            goto L5b
        L5a:
            r11 = move-exception
        L5b:
            r11.printStackTrace()
            r3 = r4
            goto L7f
        L60:
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeStream(r2, r11, r12)
            if (r3 != 0) goto L7f
            boolean r11 = r12.inJustDecodeBounds
            if (r11 != 0) goto L7f
            android.util.Log.w(r1, r0)
            goto L7f
        L6e:
            r3 = move-exception
            r3.printStackTrace()
        L72:
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeStream(r2, r11, r12)
            if (r3 != 0) goto L7f
            boolean r11 = r12.inJustDecodeBounds
            if (r11 != 0) goto L7f
            android.util.Log.w(r1, r0)
        L7f:
            r2.close()     // Catch: java.lang.Exception -> L86
            r10.close()     // Catch: java.lang.Exception -> L86
            goto L8a
        L86:
            r10 = move-exception
            r10.printStackTrace()
        L8a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.WallpaperUtils.decodeStreamConsiderQMG(java.io.InputStream, android.graphics.Rect, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
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

    public static Size getLogicalDisplaySize(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = configuration.orientation;
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        int i2 = point.x;
        int i3 = point.y;
        boolean z = configuration.semMobileKeyboardCovered == 1;
        int i4 = z ? displayMetrics.widthPixels : i == 1 ? i2 : i3;
        if (z) {
            i2 = displayMetrics.heightPixels;
        } else if (i == 1) {
            i2 = i3;
        }
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i4, i2, "getLogicalDisplaySize: ", " x ", " dm ");
        m.append(displayMetrics.widthPixels);
        m.append(" x ");
        m.append(displayMetrics.heightPixels);
        m.append(" orientation:");
        m.append(i);
        Log.d("WallpaperUtils", m.toString());
        return new Size(i4, i2);
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
                mediaMetadataRetriever.close();
                return frameAtTime;
            } catch (Throwable th) {
                th.printStackTrace();
                return frameAtTime;
            }
        } catch (Throwable th2) {
            try {
                th2.printStackTrace();
                return null;
            } finally {
                try {
                    mediaMetadataRetriever.close();
                } catch (Throwable th3) {
                    th3.printStackTrace();
                }
            }
        }
    }

    public static boolean isAdaptiveColorEnabled() {
        try {
            if (!mSettingsHelper.isAdaptiveColorMode() || mIsEmergencyMode || mIsUltraPowerSavingMode || mIsExternalLiveWallpaper) {
                return false;
            }
            return !mSettingsHelper.isOpenThemeLockWallpaper();
        } catch (Exception e) {
            Log.d("WallpaperUtils", "isAdaptiveColorEnabled: Error while reading settings (" + e.getMessage() + ")");
            return false;
        }
    }

    public static boolean isCoverScreen(int i) {
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            return (i & 2) == 0 && (i & 16) != 0;
        }
        if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            return (i & 2) == 0 && (i & 32) != 0;
        }
        return false;
    }

    public static boolean isDexStandAloneMode() {
        try {
            return ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isStandalone();
        } catch (Exception e) {
            Log.d("WallpaperUtils", "isDexStandAloneMode: " + e.getMessage());
            return false;
        }
    }

    public static boolean isLiveWallpaper() {
        return sWallpaperType == 7;
    }

    public static boolean isOpenThemeLockWallpaper() {
        return mSettingsHelper.isOpenThemeLockWallpaper();
    }

    public static boolean isOpenThemeLook() {
        return mSettingsHelper.isOpenThemeLook();
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
        int semGetWallpaperType = WallpaperManager.getInstance(context).semGetWallpaperType(sCurrentWhich);
        return semGetWallpaperType == 8 || (semGetWallpaperType == 3 && ((PluginWallpaperController) Dependency.sDependency.getDependencyInner(PluginWallpaperController.class)).isPluginWallpaperRequired(sCurrentWhich) && ((PluginWallpaperController) Dependency.sDependency.getDependencyInner(PluginWallpaperController.class)).containsVideo(sCurrentWhich));
    }

    public static boolean isWhiteKeyguardWallpaper(long j) {
        KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
        SemWallpaperColors.Item hint = keyguardWallpaperController != null ? keyguardWallpaperController.getHint(j, false) : null;
        return hint != null ? hint.getFontColor() == 1 : mSettingsHelper.isWhiteKeyguardWallpaper();
    }

    public static void loadDeviceState(int i, Context context) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            sCurrentWhich = z ? WallpaperManager.getInstance(context).getLidState() == 0 ? 18 : 6 : 2;
            SemWallpaperColors semGetWallpaperColors = wallpaperManager.semGetWallpaperColors(6);
            SparseArray sparseArray = sCachedWallpaperColors;
            sparseArray.put(4, semGetWallpaperColors);
            sparseArray.put(16, wallpaperManager.semGetWallpaperColors(18));
        }
        if (LsRune.WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER && isDexStandAloneMode()) {
            sCurrentWhich = (sCurrentWhich | 8) & (-5);
        }
        mIsEmergencyMode = Settings.System.getIntForUser(context.getContentResolver(), SettingsHelper.INDEX_EMERGENCY_MODE, 0, i) == 1;
        mIsUltraPowerSavingMode = Settings.System.getIntForUser(context.getContentResolver(), SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE, 0, i) == 1 || Settings.System.getIntForUser(context.getContentResolver(), SettingsHelper.INDEX_MINIMAL_BATTERY_USE, 0, i) == 1;
        mIsAdaptiveColorMode = mSettingsHelper.isAdaptiveColorMode();
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            mIsAdaptiveColorModeSub = mSettingsHelper.isAdaptiveColorMode(true);
        }
        setWallpaperType(wallpaperManager.semGetWallpaperType(sCurrentWhich), context);
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
        if (isLiveWallpaper()) {
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
        long convertFlag = SystemUIWidgetUtil.convertFlag(str);
        if (convertFlag < 0) {
            return false;
        }
        return isWhiteKeyguardWallpaper(convertFlag);
    }
}
