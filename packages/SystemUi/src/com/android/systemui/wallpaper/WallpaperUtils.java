package com.android.systemui.wallpaper;

import android.app.SemWallpaperColors;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.content.APKContents;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Debug;
import android.os.IBinder;
import android.provider.Settings;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.samsung.android.view.SemWindowManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WallpaperUtils {
    public static boolean mIsEmergencyMode = false;
    public static boolean mIsUltraPowerSavingMode = false;
    public static SettingsHelper mSettingsHelper = null;
    public static boolean sDrawState = false;
    public static float sScreenDensityRateFromBase;
    public static final boolean[] mIsLiveWallpaper = {false, false};
    public static float sLastAmount = 0.0f;
    public static boolean mIsAdaptiveColorMode = false;
    public static boolean mIsAdaptiveColorModeSub = false;
    public static int sCurrentWhich = 2;
    public static final SparseArray sCachedWallpaper = new SparseArray();
    public static final SparseArray sCachedSmartCroppedRect = new SparseArray();
    public static final SparseArray sCachedWallpaperColors = new SparseArray();
    public static final int[] sWallpaperType = {-1, -1};

    static {
        Debug.semIsProductDev();
    }

    public static Bitmap applyPreviewVisibility(Context context, Bitmap bitmap, Bitmap bitmap2) {
        int fontColorRgb = SemWallpaperColors.fromBitmap(context, bitmap, 2, false, (Rect[]) null).get(32L).getFontColorRgb();
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(fontColorRgb, PorterDuff.Mode.SRC_ATOP);
        Bitmap copy = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(copy);
        Paint paint = new Paint();
        paint.setColorFilter(porterDuffColorFilter);
        canvas.drawBitmap(copy, new Rect(0, 0, copy.getWidth(), copy.getHeight() / 2), new Rect(0, 0, copy.getWidth(), copy.getHeight() / 2), paint);
        Log.d("WallpaperUtils", "applyPreviewVisibility 0x" + Integer.toHexString(fontColorRgb));
        return copy;
    }

    public static void clearCachedSmartCroppedRect(int i) {
        sCachedSmartCroppedRect.put(i, null);
    }

    public static void clearCachedWallpaper(int i) {
        if (isCachedWallpaperAvailable(i)) {
            setCachedWallpaper(null, i);
        }
    }

    public static Bitmap cropBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap != null && !bitmap.isRecycled()) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float f = width;
            float f2 = f / 2.0f;
            float f3 = height;
            float f4 = f3 / 2.0f;
            float f5 = width * i2 > i * height ? (i2 / f3) * 1.0f : (i / f) * 1.0f;
            SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("metricsHeight=", i2, " metricsWidth=", i, "WallpaperUtils");
            float f6 = (i * 1.0f) / f5;
            float f7 = (i2 * 1.0f) / f5;
            float f8 = f2 - (f6 / 2.0f);
            if (f8 < 0.0f) {
                f8 = 0.0f;
            }
            float f9 = f4 - (f7 / 2.0f);
            float f10 = f9 >= 0.0f ? f9 : 0.0f;
            Log.d("WallpaperUtils", "widthOrigin = " + width);
            Log.d("WallpaperUtils", "heightOrigin = " + height);
            Log.d("WallpaperUtils", "scale = " + f5);
            Log.d("WallpaperUtils", "centerX = " + f2);
            Log.d("WallpaperUtils", "centerY = " + f4);
            Log.d("WallpaperUtils", "startX = " + f8);
            Log.d("WallpaperUtils", "startY = " + f10);
            Log.d("WallpaperUtils", "width = " + f6);
            Log.d("WallpaperUtils", "height = " + f7);
            if (Math.round(f8) == 0 && Math.round(f10) == 0 && width == Math.round(f6) && height == Math.round(f7)) {
                Log.d("WallpaperUtils", "It doesn't need to crop bitmap");
                return bitmap;
            }
            if (Math.round(f6) >= 1 && Math.round(f7) >= 1 && i >= 1 && i2 >= 1) {
                if (Math.round(f8) + Math.round(f6) <= width) {
                    if (Math.round(f10) + Math.round(f7) <= height) {
                        Log.d("WallpaperUtils", "Cropping...");
                        return Bitmap.createBitmap(bitmap, Math.round(f8), Math.round(f10), Math.round(f6), Math.round(f7));
                    }
                }
                Log.d("WallpaperUtils", "Calculated crop size error");
                return null;
            }
            Log.d("WallpaperUtils", "Math.round(width) < 1 || Math.round(height) < 1 || mMatricsWidth < 1 || mMatricsHeight < 1");
        }
        return null;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(3:2|3|4)|(6:8|9|(4:11|12|13|(3:15|16|(1:18)))(2:37|(1:41))|23|24|25)|42|9|(0)(0)|23|24|25|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008b, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x008c, code lost:
    
        r11.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap decodeStreamConsiderQMG(InputStream inputStream, Rect rect, BitmapFactory.Options options) {
        boolean z;
        Bitmap decodeStream;
        int read;
        int read2;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(2);
        try {
            read = bufferedInputStream.read();
            read2 = bufferedInputStream.read();
            bufferedInputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (read == 81 && read2 == 71) {
            z = true;
            if (z) {
                decodeStream = BitmapFactory.decodeStream(bufferedInputStream, rect, options);
                if (decodeStream == null && !options.inJustDecodeBounds) {
                    Log.w("WallpaperUtils", "decodeStream() bitmap is null");
                }
            } else {
                Bitmap bitmap = null;
                try {
                    try {
                        Method method = Class.forName("android.graphics.BitmapFactory").getMethod("decodeStreamQMG", InputStream.class, Rect.class, BitmapFactory.Options.class);
                        method.setAccessible(true);
                        decodeStream = (Bitmap) method.invoke(null, bufferedInputStream, rect, options);
                        if (decodeStream == null) {
                            try {
                                if (!options.inJustDecodeBounds) {
                                    Log.w("WallpaperUtils", "decodeStreamQMG() bitmap is null");
                                }
                            } catch (Exception e2) {
                                e = e2;
                                bitmap = decodeStream;
                                e.printStackTrace();
                                decodeStream = bitmap;
                                bufferedInputStream.close();
                                inputStream.close();
                                return decodeStream;
                            }
                        }
                    } catch (NoSuchMethodException unused) {
                        decodeStream = BitmapFactory.decodeStream(bufferedInputStream, rect, options);
                        if (decodeStream == null && !options.inJustDecodeBounds) {
                            Log.w("WallpaperUtils", "decodeStream() bitmap is null");
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
            bufferedInputStream.close();
            inputStream.close();
            return decodeStream;
        }
        z = false;
        if (z) {
        }
        bufferedInputStream.close();
        inputStream.close();
        return decodeStream;
    }

    public static Bitmap getBlurredBitmap(Context context, Bitmap bitmap, int i, int i2) {
        Bitmap.Config config = bitmap.getConfig();
        Bitmap.Config config2 = Bitmap.Config.ARGB_8888;
        if (config != config2) {
            bitmap = bitmap.copy(config2, true);
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int round = Math.round(i * 0.1f);
        int round2 = Math.round(i2 * 0.1f);
        if (width > round || height > round2) {
            bitmap = Bitmap.createScaledBitmap(bitmap, round, round2, true);
        }
        try {
            RenderScript create = RenderScript.create(context);
            Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
            Allocation createTyped = Allocation.createTyped(create, createFromBitmap.getType());
            ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
            create2.setRadius(25.0f);
            create2.setInput(createFromBitmap);
            create2.forEach(createTyped);
            createTyped.copyTo(bitmap);
            create.destroy();
            createFromBitmap.destroy();
            createTyped.destroy();
            create2.destroy();
        } catch (RSRuntimeException e) {
            e.printStackTrace();
        }
        return bitmap;
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

    public static Rect getCachedSmartCroppedRect(int i) {
        return (Rect) sCachedSmartCroppedRect.get(i);
    }

    public static Bitmap getCachedWallpaper(int i) {
        return (Bitmap) sCachedWallpaper.get(i & (-5));
    }

    public static int getFolderStateBasedWhich(int i, Context context) {
        return LsRune.WALLPAPER_SUB_DISPLAY_MODE ? WallpaperManager.getInstance(context).getLidState() == 0 ? i | 16 : i | 4 : i;
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
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("getLogicalDisplaySize: ", i4, " x ", i2, " dm ");
        m45m.append(displayMetrics.widthPixels);
        m45m.append(" x ");
        m45m.append(displayMetrics.heightPixels);
        m45m.append(" orientation:");
        m45m.append(i);
        Log.d("WallpaperUtils", m45m.toString());
        return new Size(i4, i2);
    }

    public static Size[] getPhysicalDisplaySizes(Display... displayArr) {
        Size[] sizeArr = new Size[displayArr.length];
        try {
            Object invoke = Class.forName("android.view.IWindowManager$Stub").getMethod("asInterface", IBinder.class).invoke(null, Class.forName("android.os.ServiceManager").getMethod("checkService", String.class).invoke(null, "window"));
            Method method = Class.forName("android.view.IWindowManager").getMethod("getInitialDisplaySize", Integer.TYPE, Point.class);
            for (int i = 0; i < displayArr.length; i++) {
                int displayId = displayArr[i].getDisplayId();
                Point point = new Point();
                method.invoke(invoke, Integer.valueOf(displayId), point);
                Log.i("WallpaperUtils", "getPhysicalDisplaySizes: " + displayId + " " + point);
                sizeArr[i] = new Size(point.x, point.y);
            }
            return sizeArr;
        } catch (Exception e) {
            Log.e("WallpaperUtils", "getPhysicalDisplaySizes: ", e);
            return null;
        }
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
        LogUtil.m225i("WallpaperUtils", "getScreenSize: " + point, new Object[0]);
        return point;
    }

    public static Bitmap getRotatedBitmap(Bitmap bitmap, int i) {
        int i2 = i == 1 ? 90 : 270;
        Matrix matrix = new Matrix();
        matrix.postRotate(-i2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap getScreenShot(Context context, int i, int i2, int i3, int i4) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        SemWindowManager semWindowManager = SemWindowManager.getInstance();
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("getScreenShot: start, width = ", i, " , height = ", i2, " , mRotation = ");
        m45m.append(i3);
        Log.i("WallpaperUtils", m45m.toString());
        Bitmap screenshot = semWindowManager.screenshot(windowManager.getDefaultDisplay().getDisplayId(), i4, false, new Rect(0, 0, 0, 0), Math.min(i, i2), Math.max(i, i2), true, 0, true);
        Log.i("WallpaperUtils", "getScreenShot: end bitmap = " + screenshot);
        if (i3 == 0 || screenshot == null) {
            return screenshot;
        }
        Bitmap rotatedBitmap = getRotatedBitmap(screenshot, i3);
        screenshot.recycle();
        return rotatedBitmap;
    }

    public static AssetFileDescriptor getVideoFDFromPackage(Context context, String str, String str2) {
        Context context2;
        Resources resources;
        AssetManager assets;
        AbstractC0000x2c234b15.m3m("getVideoFDFromPackage() pkgName = ", str, "WallpaperUtils");
        if (context != null && !TextUtils.isEmpty(str)) {
            try {
                context2 = context.createPackageContext(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.d("WallpaperUtils", "Can not found package name");
                context2 = null;
            }
            if (context2 == null) {
                APKContents aPKContents = new APKContents(APKContents.getMainThemePackagePath(str));
                resources = aPKContents.getResources();
                assets = aPKContents.getAssets();
                if (resources == null || assets == null) {
                    Log.e("WallpaperUtils", "getVideoFDFromPackage() otherContext is null");
                    return null;
                }
            } else {
                resources = context2.getResources();
                assets = context2.getAssets();
            }
            try {
                if ("com.samsung.android.wallpaper.res".equals(str)) {
                    return resources.openRawResourceFd(resources.getIdentifier(str2.substring(0, str2.lastIndexOf(46)), "raw", str));
                }
                if (assets != null) {
                    return assets.openFd(str2);
                }
                Log.e("WallpaperUtils", "getVideoFDFromPackage() assetManager is null");
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap getVideoFrame(Context context, AssetFileDescriptor assetFileDescriptor, String str, Uri uri, long j) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        MediaMetadataRetriever.BitmapParams bitmapParams = new MediaMetadataRetriever.BitmapParams();
        bitmapParams.setPreferredConfig(Bitmap.Config.RGBA_F16);
        try {
            if (!TextUtils.isEmpty(str)) {
                mediaMetadataRetriever.setDataSource(str);
            } else if (assetFileDescriptor != null) {
                mediaMetadataRetriever.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            } else if (uri != null) {
                mediaMetadataRetriever.setDataSource(context, uri);
            }
            return mediaMetadataRetriever.getFrameAtTime(j, 2, bitmapParams);
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                try {
                    mediaMetadataRetriever.close();
                    return null;
                } catch (Throwable th2) {
                    th2.printStackTrace();
                    return null;
                }
            } finally {
                try {
                    mediaMetadataRetriever.close();
                } catch (Throwable th3) {
                    th3.printStackTrace();
                }
            }
        }
    }

    public static int getWallpaperType() {
        isSubDisplay();
        return sWallpaperType[isSubDisplay() ? 1 : 0];
    }

    public static boolean isAODShowLockWallpaperAndLockDisabled(int i, Context context) {
        if (isAODShowLockWallpaperEnabled()) {
            return new LockPatternUtils(context).isLockScreenDisabled(i);
        }
        return false;
    }

    public static boolean isAODShowLockWallpaperEnabled() {
        boolean z = LsRune.AOD_FULLSCREEN;
        if (!z) {
            return false;
        }
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !isSubDisplay(sCurrentWhich)) {
            return false;
        }
        SettingsHelper settingsHelper = mSettingsHelper;
        settingsHelper.getClass();
        return (z && settingsHelper.mItemLists.get("aod_show_lockscreen_wallpaper").getIntValue() != 0) && mSettingsHelper.isAODEnabled();
    }

    public static boolean isCachedWallpaperAvailable(int i) {
        Bitmap cachedWallpaper = getCachedWallpaper(i & (-5));
        if (cachedWallpaper != null && !cachedWallpaper.isRecycled()) {
            return true;
        }
        if (cachedWallpaper == null) {
            IconCompat$$ExternalSyntheticOutline0.m30m("isCachedWallpaperAvailable cached wallpaper is null. which = ", i, "WallpaperUtils");
            return false;
        }
        if (!cachedWallpaper.isRecycled()) {
            return false;
        }
        IconCompat$$ExternalSyntheticOutline0.m30m("isCachedWallpaperAvailable cached wallpaper is recycled. which = ", i, "WallpaperUtils");
        return false;
    }

    public static boolean isCarUiMode(Context context) {
        int activeProjectionTypes = ((UiModeManager) context.getSystemService(UiModeManager.class)).getActiveProjectionTypes();
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("isCarUiMode type = ", activeProjectionTypes, "WallpaperUtils");
        return (activeProjectionTypes & 1) == 1;
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
            return ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isStandalone();
        } catch (Exception e) {
            Log.d("WallpaperUtils", "isDexStandAloneMode: " + e.getMessage());
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0029, code lost:
    
        if (android.app.WallpaperManager.isDefaultOperatorWallpaper(r7, 2) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isEnableTilt(Context context) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        int semGetWallpaperType = wallpaperManager.semGetWallpaperType(sCurrentWhich);
        boolean z = true;
        if (semGetWallpaperType != 3) {
            if (semGetWallpaperType == -1) {
                if (WallpaperManager.getInstance(context).getDefaultWallpaperType(2) == 3) {
                    WallpaperManager.getInstance(context);
                }
            }
            z = false;
        }
        if (!z) {
            Log.i("WallpaperUtils", "isEnableTilt: false (multipack is not applied.)");
            return false;
        }
        Uri semGetUri = wallpaperManager.semGetUri(2);
        if (semGetUri == null) {
            return false;
        }
        try {
            return Boolean.parseBoolean(semGetUri.getQueryParameter("tilt"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isExternalLiveWallpaper() {
        return (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) ? mSettingsHelper.isLiveWallpaperEnabled(false) : mSettingsHelper.isLiveWallpaperEnabled(isSubDisplay());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isLiveWallpaperAppliedOnLock(Context context) {
        isSubDisplay();
        if ((sWallpaperType[isSubDisplay() ? 1 : 0] == 7) == true) {
            return true;
        }
        int i = isSubDisplay() ? 16 : 4;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        return wallpaperManager.isSystemAndLockPaired(i) && wallpaperManager.semGetWallpaperType(i | 1) == 7;
    }

    public static boolean isLiveWallpaperEnabled() {
        return isLiveWallpaperEnabled((sCurrentWhich & 60) == 16);
    }

    public static boolean isMainScreenRatio(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return false;
        }
        return ((float) Math.max(i, i2)) / ((float) Math.min(i, i2)) <= 2.0f;
    }

    public static boolean isOpenThemeLook() {
        return mSettingsHelper.isOpenThemeLook();
    }

    public static boolean isStatusBarHeight(Context context, View view, int i) {
        int i2;
        if (context == null) {
            return false;
        }
        WindowInsets rootWindowInsets = view != null ? view.getRootWindowInsets() : null;
        DisplayCutout displayCutout = rootWindowInsets != null ? rootWindowInsets.getDisplayCutout() : null;
        if (displayCutout != null) {
            i2 = displayCutout.getSafeInsetTop() - displayCutout.getSafeInsetBottom();
            Log.d("WallpaperUtils", "updateStatusBarHeight - dc = " + displayCutout);
        } else {
            i2 = -1;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("Height from dc = ", i2, "WallpaperUtils");
        if (i2 <= 0) {
            i2 = context.getResources().getDimensionPixelOffset(17106177);
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Height from resource = ", i2, "WallpaperUtils");
        }
        Log.i("WallpaperUtils", "statusbar statusBarSize = " + i2 + ", view height = " + i);
        return i == i2;
    }

    public static boolean isSubDisplay(int i) {
        return (i & 60) == 16;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isVideoWallpaper() {
        isSubDisplay();
        boolean isSubDisplay = isSubDisplay();
        char c = isSubDisplay() ? (char) 18 : (char) 6;
        if (sWallpaperType[isSubDisplay ? 1 : 0] == 8) {
            return true;
        }
        SettingsHelper settingsHelper = mSettingsHelper;
        settingsHelper.getClass();
        Object[] objArr = (c & '<') == 16;
        SettingsHelper.ItemMap itemMap = settingsHelper.mItemLists;
        return (objArr != false ? itemMap.get("plugin_lock_wallpaper_type_sub").getIntValue() : itemMap.get("plugin_lock_wallpaper_type").getIntValue()) == 2;
    }

    public static boolean isWhiteKeyguardWallpaper(long j) {
        KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
        SemWallpaperColors.Item hint = keyguardWallpaperController != null ? keyguardWallpaperController.getHint(j, false) : null;
        return hint != null ? hint.getFontColor() == 1 : mSettingsHelper.isWhiteKeyguardWallpaper();
    }

    public static void loadDeviceState(int i, Context context) {
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            sCurrentWhich = getFolderStateBasedWhich(2, context);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            if (wallpaperManager != null) {
                SemWallpaperColors semGetWallpaperColors = wallpaperManager.semGetWallpaperColors(6);
                SparseArray sparseArray = sCachedWallpaperColors;
                sparseArray.put(4, semGetWallpaperColors);
                sparseArray.put(16, wallpaperManager.semGetWallpaperColors(18));
                int semGetWallpaperType = wallpaperManager.semGetWallpaperType(6);
                int[] iArr = sWallpaperType;
                iArr[0] = semGetWallpaperType;
                iArr[1] = wallpaperManager.semGetWallpaperType(18);
            }
        }
        if (LsRune.WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER && isDexStandAloneMode()) {
            sCurrentWhich = (sCurrentWhich | 8) & (-5);
        }
        mIsEmergencyMode = Settings.System.getIntForUser(context.getContentResolver(), "emergency_mode", 0, i) == 1;
        mIsUltraPowerSavingMode = Settings.System.getIntForUser(context.getContentResolver(), "ultra_powersaving_mode", 0, i) == 1 || Settings.System.getIntForUser(context.getContentResolver(), "minimal_battery_use", 0, i) == 1;
        mIsAdaptiveColorMode = mSettingsHelper.isAdaptiveColorMode();
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            mIsAdaptiveColorModeSub = mSettingsHelper.mItemLists.get("lock_adaptive_color_sub").getIntValue() != 0;
        }
        loadLiveWallpaperSettings(i, context);
    }

    public static void loadLiveWallpaperSettings(int i, Context context) {
        boolean[] zArr = mIsLiveWallpaper;
        zArr[0] = Settings.System.getIntForUser(context.getContentResolver(), "lockscreen_wallpaper", 1, i) == 0;
        zArr[1] = Settings.System.getIntForUser(context.getContentResolver(), "lockscreen_wallpaper_sub", 1, i) == 0;
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

    public static void setCachedWallpaper(Bitmap bitmap, int i) {
        sCachedWallpaper.put(i & (-5), bitmap);
    }

    public static void setScaledView(float f, View view) {
        boolean z;
        if (view == null || 1.0f == f) {
            return;
        }
        Object tag = view.getTag();
        if (tag != null && -1 == ((Integer) tag).intValue() && -1 == ((Integer) view.getTag()).intValue()) {
            Log.d("WallpaperUtils", "setScaledView: skip");
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                setScaledView(f, viewGroup.getChildAt(i));
            }
        }
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextSize(0, textView.getTextSize() * f);
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            int i2 = layoutParams.width;
            if (i2 > 0) {
                layoutParams.width = Math.round(i2 * f);
            }
            int i3 = layoutParams.height;
            if (i3 > 0) {
                layoutParams.height = Math.round(i3 * f);
            }
        }
        int minimumHeight = view.getMinimumHeight();
        int minimumWidth = view.getMinimumWidth();
        if (minimumHeight > 0) {
            int i4 = (int) (minimumHeight * f);
            view.setMinimumHeight(i4);
            if (view instanceof TextView) {
                ((TextView) view).setMinHeight(i4);
            }
        }
        if (minimumWidth > 0) {
            int i5 = (int) (minimumWidth * f);
            view.setMinimumWidth(i5);
            if (view instanceof TextView) {
                ((TextView) view).setMinWidth(i5);
            }
        }
        if (layoutParams != null && (layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            Rect rect = new Rect();
            if (marginLayoutParams.isMarginRelative()) {
                rect.left = Math.round(marginLayoutParams.getMarginStart() * f);
                rect.top = Math.round(marginLayoutParams.topMargin * f);
                rect.right = Math.round(marginLayoutParams.getMarginEnd() * f);
                rect.bottom = Math.round(marginLayoutParams.bottomMargin * f);
                marginLayoutParams.setMarginStart(rect.left);
                marginLayoutParams.setMarginEnd(rect.right);
                marginLayoutParams.setMargins(rect.left, rect.top, rect.right, rect.bottom);
            } else {
                rect.left = Math.round(marginLayoutParams.leftMargin * f);
                rect.top = Math.round(marginLayoutParams.topMargin * f);
                rect.right = Math.round(marginLayoutParams.rightMargin * f);
                int round = Math.round(marginLayoutParams.bottomMargin * f);
                rect.bottom = round;
                marginLayoutParams.setMargins(rect.left, rect.top, rect.right, round);
            }
        }
        Rect rect2 = new Rect();
        if (view.isPaddingRelative()) {
            rect2.left = Math.round(view.getPaddingStart() * f);
            rect2.top = Math.round(view.getPaddingTop() * f);
            rect2.right = Math.round(view.getPaddingEnd() * f);
            rect2.bottom = Math.round(view.getPaddingBottom() * f);
            z = (rect2.left == view.getPaddingStart() && rect2.top == view.getPaddingTop() && rect2.right == view.getPaddingEnd() && rect2.bottom == view.getPaddingBottom()) ? false : true;
            view.setPaddingRelative(rect2.left, rect2.top, rect2.right, rect2.bottom);
        } else {
            rect2.left = Math.round(view.getPaddingLeft() * f);
            rect2.top = Math.round(view.getPaddingTop() * f);
            rect2.right = Math.round(view.getPaddingRight() * f);
            rect2.bottom = Math.round(view.getPaddingBottom() * f);
            z = (rect2.left == view.getPaddingLeft() && rect2.top == view.getPaddingTop() && rect2.right == view.getPaddingRight() && rect2.bottom == view.getPaddingBottom()) ? false : true;
            view.setPadding(rect2.left, rect2.top, rect2.right, rect2.bottom);
        }
        if (z) {
            return;
        }
        view.requestLayout();
    }

    public static boolean isSubDisplay() {
        return LsRune.WALLPAPER_SUB_DISPLAY_MODE && (sCurrentWhich & 16) == 16;
    }

    public static boolean isLiveWallpaperEnabled(boolean z) {
        boolean[] zArr = mIsLiveWallpaper;
        if (z) {
            return zArr[1];
        }
        return zArr[0];
    }

    public static boolean isWhiteKeyguardWallpaper(String str) {
        long convertFlag = SystemUIWidgetUtil.convertFlag(str);
        if (convertFlag < 0) {
            return false;
        }
        return isWhiteKeyguardWallpaper(convertFlag);
    }
}
