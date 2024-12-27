package android.app;

import android.content.APKContents;
import android.content.Context;
import android.content.om.SamsungThemeConstants;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audio.Enums;
import android.provider.SearchIndexablesContract;
import android.provider.Settings;
import android.telecom.Logging.Session;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;

import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;

import com.samsung.android.desktopmode.SemDesktopModeManager;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Locale;

public class SemAppIconSolution {
    private static final int APPICON_RANGE_ALL_APPS = 0;
    private static final int APPICON_RANGE_NONE = 2;
    private static final int APPICON_RANGE_UNASSIGNED_APPS = 1;
    private static final int APPICON_RANGE_UNDEFINED = 3;
    private static final int APPICON_SCALE_TYPE_DEFAULT_CONTAINER = 2;
    private static final int APPICON_SCALE_TYPE_DETERMINED = 0;
    private static final int APPICON_SCALE_TYPE_THEME = 1;
    private static final String CALENDAR_PACKAGE_NAME = "com.samsung.android.calendar";
    private static final String CLOCK_PACKAGE_NAME = "com.sec.android.app.clockpackage";
    private static final float DEFAULT_THEME_APPICON_SCALE = 0.72f;
    private static final float ICON_SIZE_FACTOR_AMBIENT = 0.010416667f;
    private static final float ICON_SIZE_FACTOR_AMBIENT2 = 0.020833334f;
    public static final int IGNORE_APPICON_THEME = 2;
    private static final int INVALID_RESOURCE_ID = 0;
    private static final String LIVEICON_BOOLEAN_NAME = "liveicon_from_theme";
    private static final int[][] MATRIX_MOVE;
    private static final int[][] MATRIX_POINT_ONEDOT;
    private static final int[][] MATRIX_POINT_THEMECROP;
    private static final int[][] MATRIX_PROGRESS;
    private static final String PACKAGE_NAME_SYSTEMUI = "com.android.systemui";
    private static final String RES_LOCKSCREEN_SHORTCUT_BG = "ic_shortcut_theme_bg";
    private static final float SAMSUNG_THEME_APPICON_SCALE = 0.7f;
    public static final int SET_APPICON_COLORTHEME = 3;
    public static final int SET_APPICON_THEME = 0;
    private static final int SHADOW_ALPHA_AMBIENT = 41;
    private static final int SHADOW_ALPHA_AMBIENT2 = 26;
    private static final String TAG = "AppIconSolution";
    private static final String THEME_DESIGNER_THIRD_PARTY_APP_ICON =
            "theme_designer_enable_third_party_app_icon";
    private static final String TYPE_BOOL = "bool";
    private static final String TYPE_DRAWABLE = "drawable";
    public static final int UNSET_APPICON_THEME = 1;
    private boolean mIgnoreAppIconThemeHost;
    private boolean mIsThemeParkExist;
    private Paint mPaint;
    private Paint mPaintForCrop;
    private static SemAppIconSolution sUniqueInstance = null;
    private static int sLayerColorForNight = Color.parseColor("#19000000");
    public static final Paint PAINT_FOR_NIGHT_LAYER = new Paint(1);
    private float mSamsungThemeAppIconScale = SAMSUNG_THEME_APPICON_SCALE;
    private int mSamsungThemeAppIconRange = 3;
    private String mAppIconPackageName = null;
    private String mThemePackageName = null;
    private boolean mSamsungThemeAppIconMask = false;
    private final Object mThemeSync = new Object();
    private HashMap<String, String> mThemeAppIconMap = null;
    private final int LIMIT_ICON_SIZE = 216;
    private final int LIMIT_SHADOW_SIZE = 1000;
    private ColorFilter mColorFilter = null;
    private boolean mEnabledThirdPartyAppIcon = false;
    private Pair<String, APKContents> mCachedAPKContents = null;
    private final SparseArray<WeakReference<Bitmap>> mShadowCache = new SparseArray<>();
    private final String RESNAME_MONOCHROME = "sep_monochrome_icon";

    static {
        PAINT_FOR_NIGHT_LAYER.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        PAINT_FOR_NIGHT_LAYER.setColor(sLayerColorForNight);
        MATRIX_PROGRESS =
                new int[][] {
                    new int[] {1, 0}, new int[] {0, 1}, new int[] {-1, 0}, new int[] {0, -1}
                };
        MATRIX_MOVE =
                new int[][] {
                    new int[] {1, 1}, new int[] {-1, 1}, new int[] {-1, -1}, new int[] {1, -1}
                };
        MATRIX_POINT_ONEDOT =
                new int[][] {
                    new int[] {22, 22},
                    new int[] {96, 2},
                    new int[] {169, 22},
                    new int[] {189, 96},
                    new int[] {169, 169},
                    new int[] {96, 189},
                    new int[] {22, 169},
                    new int[] {2, 96}
                };
        MATRIX_POINT_THEMECROP =
                new int[][] {
                    new int[] {22, 29},
                    new int[] {96, 3},
                    new int[] {170, 29},
                    new int[] {187, 94},
                    new int[] {170, 163},
                    new int[] {96, 186},
                    new int[] {22, 163},
                    new int[] {5, 94}
                };
    }

    private SemAppIconSolution(Context context) {
        this.mIgnoreAppIconThemeHost = false;
        this.mIsThemeParkExist = false;
        if (context != null) {
            int resId =
                    context.getResources()
                            .getIdentifier("sem_appicon_layer_color_for_night", "color", "android");
            if (resId > 0) {
                sLayerColorForNight = context.getResources().getColor(resId);
                PAINT_FOR_NIGHT_LAYER.setColor(sLayerColorForNight);
            }
            this.mIgnoreAppIconThemeHost =
                    SamsungThemeConstants.ignoreAppIconThemeHosts.contains(
                            context.getBasePackageName());
            File appIconThemePark = new File(SamsungThemeConstants.PATH_THEMEPARK_ICON);
            this.mIsThemeParkExist = appIconThemePark.exists();
        }
    }

    public static synchronized SemAppIconSolution getInstance(Context context) {
        SemAppIconSolution semAppIconSolution;
        synchronized (SemAppIconSolution.class) {
            if (sUniqueInstance == null) {
                sUniqueInstance = new SemAppIconSolution(context);
            }
            semAppIconSolution = sUniqueInstance;
        }
        return semAppIconSolution;
    }

    public int checkAppIconThemePackage(Context context) {
        boolean ignoreAppIconTheme = false;
        String appIconPackageName = "";
        String themePackageName = "";
        try {
            themePackageName =
                    Settings.System.getString(
                            context.getContentResolver(), "current_sec_active_themepackage");
            appIconPackageName =
                    Settings.System.getString(
                            context.getContentResolver(),
                            Settings.System.SEM_CURRENT_APP_ICON_PACKAGE);
        } catch (SecurityException se) {
            Log.i(
                    TAG,
                    "couldn't access setting property, just keep appIconPackageName empty, ex = "
                            + se);
        }
        if ("".equals(appIconPackageName)) {
            appIconPackageName = null;
        }
        File appIconThemePark = new File(SamsungThemeConstants.PATH_THEMEPARK_ICON);
        boolean isThemeParkExist = appIconThemePark.exists();
        if (this.mIgnoreAppIconThemeHost
                && appIconPackageName != null
                && SamsungThemeConstants.ignoreAppIconThemeList.contains(appIconPackageName)) {
            appIconPackageName = null;
            ignoreAppIconTheme = true;
        }
        try {
            Configuration cf = context.getResources().getConfiguration();
            if (cf != null && appIconPackageName != null && (cf.uiMode & 15) == 2) {
                appIconPackageName = null;
                isThemeParkExist = false;
                Log.i(TAG, "set theme to null for dex mode");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if ((this.mAppIconPackageName != null
                        && !this.mAppIconPackageName.equals(appIconPackageName))
                || ((appIconPackageName != null
                                && !appIconPackageName.equals(this.mAppIconPackageName))
                        || isThemeParkExist != this.mIsThemeParkExist
                        || this.mSamsungThemeAppIconRange == 3
                        || 0 != 0)) {
            ApplicationPackageManager.configurationChanged();
            this.mAppIconPackageName = appIconPackageName;
            this.mIsThemeParkExist = isThemeParkExist;
            registerAppIconInfo(context);
        }
        if ((this.mThemePackageName != null && !this.mThemePackageName.equals(themePackageName))
                || (themePackageName != null && !themePackageName.equals(this.mThemePackageName))) {
            this.mThemePackageName = themePackageName;
        }
        boolean enableColorThemeIcon = false;
        if (this.mAppIconPackageName == null) {
            try {
                enableColorThemeIcon =
                        Settings.Global.getInt(
                                        context.getContentResolver(), "colortheme_app_icon", 0)
                                == 1;
            } catch (SecurityException se2) {
                Log.i(
                        TAG,
                        "couldn't access setting property, just keep colortheme icon disabled, ex ="
                                + " "
                                + se2);
            }
        }
        if (this.mAppIconPackageName != null || this.mIsThemeParkExist) {
            return 0;
        }
        if (!enableColorThemeIcon || context.getUserId() == 77) {
            return ignoreAppIconTheme ? 2 : 1;
        }
        return 3;
    }

    private void registerAppIconInfo(Context context) {
        Resources r;
        this.mColorFilter = null;
        this.mEnabledThirdPartyAppIcon = false;
        if (this.mAppIconPackageName != null) {
            try {
                String iconPackagePath =
                        APKContents.getMainThemePackagePath(this.mAppIconPackageName);
                if (!new File(iconPackagePath).exists()) {
                    r =
                            context.getPackageManager()
                                    .getResourcesForApplicationAsUser(this.mAppIconPackageName, 0);
                } else {
                    r = new APKContents(iconPackagePath).getResources();
                }
                if (r == null) {
                    Log.e(TAG, "Icon package doesnt have resources " + this.mAppIconPackageName);
                    return;
                }
                int resID = r.getIdentifier("icon_bg_range", "integer", this.mAppIconPackageName);
                if (resID != 0) {
                    this.mSamsungThemeAppIconRange = r.getInteger(resID);
                } else {
                    this.mSamsungThemeAppIconRange = 2;
                }
                int resID2 =
                        r.getIdentifier("icon_scale_size", "integer", this.mAppIconPackageName);
                if (resID2 != 0) {
                    int iconScale = r.getInteger(resID2);
                    this.mSamsungThemeAppIconScale = iconScale * 0.01f;
                } else {
                    this.mSamsungThemeAppIconScale = SAMSUNG_THEME_APPICON_SCALE;
                }
                try {
                    int resID3 =
                            r.getIdentifier("mask_from_theme", TYPE_BOOL, this.mAppIconPackageName);
                    if (resID3 != 0) {
                        this.mSamsungThemeAppIconMask = r.getBoolean(resID3);
                    } else {
                        this.mSamsungThemeAppIconMask = false;
                    }
                } catch (Exception e) {
                    this.mSamsungThemeAppIconMask = false;
                }
                try {
                    int resID4 =
                            r.getIdentifier(
                                    THEME_DESIGNER_THIRD_PARTY_APP_ICON,
                                    TYPE_BOOL,
                                    this.mAppIconPackageName);
                    if (resID4 != 0) {
                        this.mEnabledThirdPartyAppIcon = r.getBoolean(resID4);
                    } else {
                        this.mEnabledThirdPartyAppIcon = false;
                    }
                } catch (Exception e2) {
                    this.mEnabledThirdPartyAppIcon = false;
                }
                createColorFilter(r);
                return;
            } catch (Exception e3) {
                this.mSamsungThemeAppIconRange = 2;
                this.mSamsungThemeAppIconScale = SAMSUNG_THEME_APPICON_SCALE;
                this.mSamsungThemeAppIconMask = false;
                e3.printStackTrace();
                return;
            }
        }
        this.mSamsungThemeAppIconRange = 2;
        this.mSamsungThemeAppIconScale = SAMSUNG_THEME_APPICON_SCALE;
        this.mSamsungThemeAppIconMask = false;
    }

    private Drawable getThemeParkAppIcon(Context context, PackageItemInfo itemInfo) {
        String packageName = itemInfo.packageName;
        String className = itemInfo.name;
        if (packageName == null) {
            return null;
        }
        String path =
                (SamsungThemeConstants.PATH_THEMEPARK_ICON + packageName)
                        + (className != null
                                ? NativeLibraryHelper.CLEAR_ABI_OVERRIDE
                                        + Integer.toHexString(className.hashCode()).toLowerCase()
                                        + ".png"
                                : ".png");
        if (!new File(path).exists()) {
            path = SamsungThemeConstants.PATH_THEMEPARK_ICON + packageName + ".png";
            if (!new File(path).exists()) {
                return null;
            }
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            return new BitmapDrawable(context.getResources(), bitmap);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            return null;
        }
    }

    private Drawable getThemeAppIcon(
            Context context, PackageItemInfo itemInfo, boolean background, int mode) {
        return getThemeAppIcon(context, itemInfo, background, false, mode);
    }

    private Drawable getThemeAppIcon(
            Context context, PackageItemInfo itemInfo, boolean background, boolean mask, int mode) {
        HashMap<String, String> packageIconMap = getThemeAppIconMap(context);
        if (this.mAppIconPackageName != null && !this.mAppIconPackageName.isEmpty()) {
            String overlayIcon = null;
            if ((mode & 256) != 0 && background) {
                overlayIcon = RES_LOCKSCREEN_SHORTCUT_BG;
            } else if (background) {
                overlayIcon = packageIconMap.get("3rd_party_icon");
            } else if (mask) {
                overlayIcon = packageIconMap.get("mask_for_crop");
            } else if (itemInfo != null) {
                if (itemInfo.name != null) {
                    overlayIcon = packageIconMap.get(itemInfo.name);
                    if (overlayIcon == null && (itemInfo instanceof ApplicationInfo)) {
                        overlayIcon = packageIconMap.get(itemInfo.packageName);
                    }
                } else if (itemInfo.packageName != null) {
                    overlayIcon = packageIconMap.get(itemInfo.packageName);
                }
            }
            Drawable appIcon = null;
            StringBuilder builder = new StringBuilder("[getThemeAppIcon]");
            if (overlayIcon != null) {
                appIcon =
                        getDrawableFromAppIconPackage(
                                context, overlayIcon, builder.toString(), mode);
            }
            if (this.mEnabledThirdPartyAppIcon && appIcon == null && itemInfo != null) {
                if (itemInfo.name != null) {
                    String overlayIcon2 =
                            itemInfo.name
                                    .toLowerCase(new Locale("en", "US"))
                                    .replaceAll("\\.", Session.SESSION_SEPARATION_CHAR_CHILD);
                    if (overlayIcon2.length() > 90) {
                        overlayIcon2 = overlayIcon2.substring(overlayIcon2.length() - 90);
                    }
                    appIcon =
                            getDrawableFromAppIconPackage(
                                    context, overlayIcon2, builder.append("name").toString(), mode);
                }
                if (appIcon == null && itemInfo.packageName != null) {
                    String overlayIcon3 =
                            itemInfo.packageName
                                    .toLowerCase(new Locale("en", "US"))
                                    .replaceAll("\\.", Session.SESSION_SEPARATION_CHAR_CHILD);
                    if (overlayIcon3.length() > 90) {
                        overlayIcon3 = overlayIcon3.substring(overlayIcon3.length() - 90);
                    }
                    Drawable appIcon2 =
                            getDrawableFromAppIconPackage(
                                    context,
                                    overlayIcon3,
                                    builder.append("package").toString(),
                                    mode);
                    return appIcon2;
                }
                return appIcon;
            }
            return appIcon;
        }
        return null;
    }

    private HashMap<String, String> getThemeAppIconMap(Context context) {
        synchronized (this.mThemeSync) {
            if (this.mThemeAppIconMap == null) {
                getThemeResourceFromMappingTable(context);
            }
        }
        return this.mThemeAppIconMap;
    }

    private void getThemeResourceFromMappingTable(Context context) {
        try {
            XmlResourceParser parser = context.getResources().getXml(R.xml.theme_app_icons);
            this.mThemeAppIconMap = new HashMap<>();
            if (parser != null) {
                int depth = parser.getDepth();
                while (true) {
                    int type = parser.next();
                    if ((type != 3 || parser.getDepth() > depth) && type != 1) {
                        if (type == 2) {
                            String className = null;
                            String iconId = null;
                            String name = parser.getName();
                            if ("ThemeApp".equals(name)) {
                                int size = parser.getAttributeCount();
                                for (int i = 0; i < size; i++) {
                                    String attrName = parser.getAttributeName(i);
                                    String attrValue = parser.getAttributeValue(i);
                                    if (attrName != null
                                            && attrName.equals(
                                                    SearchIndexablesContract.BaseColumns
                                                            .COLUMN_CLASS_NAME)) {
                                        className = attrValue;
                                    }
                                    if (attrName != null && attrName.equals("iconId")) {
                                        iconId = attrValue;
                                    }
                                }
                                this.mThemeAppIconMap.put(className, iconId);
                            }
                        }
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception during parsing theme app list" + e);
            e.printStackTrace();
        }
    }

    private Drawable getDrawableFromAppIconPackage(
            Context context, String overlayIcon, String logText, int mode) {
        Resources resources;
        if ((mode & 256) != 0) {
            Resources systemUiRes = null;
            try {
                try {
                    systemUiRes =
                            context.getPackageManager()
                                    .getResourcesForApplicationAsUser("com.android.systemui", 0);
                } catch (PackageManager.NameNotFoundException e) {
                }
            } catch (Exception e2) {
                Log.e(
                        TAG,
                        logText
                                + ", Failed to get LockScreen Shorcut Icon="
                                + overlayIcon
                                + ", Exception="
                                + e2.toString());
            }
            if (systemUiRes == null) {
                Log.e(TAG, "SystemUI package doesn't have resources");
                return null;
            }
            int resID =
                    systemUiRes.getIdentifier(overlayIcon, TYPE_DRAWABLE, "com.android.systemui");
            if (resID != 0) {
                return systemUiRes.getDrawable(resID);
            }
            return null;
        }
        try {
            resources = getAppIconPackageResources(context);
        } catch (Exception e3) {
            Log.e(TAG, logText + ", Icon=" + overlayIcon + ", Exception=" + e3.toString());
        }
        if (resources == null) {
            Log.e(TAG, "Icon package doesnt have resources " + this.mAppIconPackageName);
            return null;
        }
        int resID2 = resources.getIdentifier(overlayIcon, TYPE_DRAWABLE, this.mAppIconPackageName);
        if (resID2 != 0) {
            return resources.getDrawable(resID2);
        }
        return null;
    }

    public Resources getAppIconPackageResources(Context context) {
        return getThemePackageResources(context, this.mAppIconPackageName);
    }

    private Resources getThemePackageResources(Context context, String packageName) {
        String themePackagePath;
        Resources resources = null;
        if (packageName == null) {
            Log.e(TAG, "Couldn't get theme package resources, package is null");
            return null;
        }
        try {
            if (packageName.equals(this.mAppIconPackageName)) {
                themePackagePath = APKContents.getMainThemePackagePath(packageName);
            } else {
                themePackagePath = APKContents.getCurrentThemePackagePath(packageName);
            }
            if (new File(themePackagePath).exists()) {
                APKContents apkContents =
                        (this.mCachedAPKContents == null
                                        || !themePackagePath.equals(this.mCachedAPKContents.first))
                                ? null
                                : this.mCachedAPKContents.second;
                if (apkContents != null && apkContents.getResources() != null) {
                    Log.e(TAG, "Using cached contents available for " + this.mAppIconPackageName);
                    resources = apkContents.getResources();
                }
                apkContents = new APKContents(themePackagePath);
                this.mCachedAPKContents = new Pair<>(themePackagePath, apkContents);
                resources = apkContents.getResources();
            } else if (packageName.equals(this.mAppIconPackageName)) {
                try {
                    Resources resources2 =
                            context.getPackageManager()
                                    .getResourcesForApplicationAsUser(this.mAppIconPackageName, 0);
                    return resources2;
                } catch (PackageManager.NameNotFoundException e) {
                    return null;
                }
            }
            return resources;
        } catch (Exception e2) {
            Log.e(TAG, "Failed at get appIconPackage resources, e : " + e2);
            return null;
        }
    }

    private static class IconScale {
        private int mAlpha;
        private boolean mIsCrop;
        private float mScale;

        public IconScale(int alpha, float scale, boolean isCrop) {
            this.mAlpha = alpha;
            this.mScale = scale;
            this.mIsCrop = isCrop;
        }

        public int getAlpha() {
            return this.mAlpha;
        }

        public float getScale() {
            return this.mScale;
        }

        public boolean isCrop() {
            return this.mIsCrop;
        }

        public String toString() {
            return "IconScale[alpha="
                    + this.mAlpha
                    + ", scale="
                    + this.mScale
                    + ", isCrop="
                    + this.mIsCrop
                    + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public boolean isThemeActive(Context context) {
        boolean hasAppIconPack =
                (this.mAppIconPackageName == null
                                || this.mAppIconPackageName.startsWith("com.samsung.upsmtheme"))
                        ? false
                        : true;
        boolean isDexMode = SemDesktopModeManager.LAUNCHER_PACKAGE.equals(context.getPackageName());
        return hasAppIconPack && !isDexMode;
    }

    private IconScale getAppIconAlphaRelativeScale(
            Bitmap bm, int sizeX, int sizeY, float scale, int scaleType) {
        if (this.mSamsungThemeAppIconMask) {
            return getAppIconAlphaRelativeScaleForIconUnification(bm, sizeX, sizeY, scale);
        }
        return getAppIconAlphaRelativeScaleForIconTray(bm, sizeX, sizeY, scale, scaleType);
    }

    private IconScale getAppIconAlphaRelativeScaleForIconUnification(
            Bitmap bm, int sizeX, int sizeY, float scale) {
        float scale2;
        boolean fullUp;
        int threshold;
        int count = 0;
        int alpha = -1;
        int smallestSide = Math.min(sizeX, sizeY) / 2;
        char c = 0;
        int[][] startPoint = {
            new int[] {0, 0},
            new int[] {sizeX - 1, 0},
            new int[] {sizeX - 1, sizeY - 1},
            new int[] {0, sizeY - 1}
        };
        int[] bmArray = new int[sizeX * sizeY];
        int[][] colorArray = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, sizeY, sizeX);
        int threshold2 = 26;
        bm.getPixels(bmArray, 0, sizeX, 0, 0, sizeX, sizeY);
        for (int i = 0; i < sizeY; i++) {
            System.arraycopy(bmArray, sizeX * i, colorArray[i], 0, sizeX);
        }
        while (true) {
            int i2 = 3;
            if (smallestSide <= count || alpha != -1) {
                break;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= 4) {
                    threshold = threshold2;
                    break;
                }
                int x = startPoint[i3][0];
                int y = startPoint[i3][1];
                int endPoint = 0;
                if (i3 != i2) {
                    endPoint = i3 + 1;
                }
                boolean completed = false;
                while (true) {
                    if (!completed) {
                        if (x == startPoint[endPoint][0] && y == startPoint[endPoint][1]) {
                            completed = true;
                        }
                        threshold = threshold2;
                        if ((colorArray[y][x] >>> 24) <= threshold) {
                            x += MATRIX_PROGRESS[i3][0];
                            y += MATRIX_PROGRESS[i3][1];
                            threshold2 = threshold;
                        } else {
                            int alpha2 = count;
                            alpha = alpha2;
                            break;
                        }
                    } else {
                        threshold = threshold2;
                        break;
                    }
                }
                if (alpha != -1) {
                    break;
                }
                i3++;
                threshold2 = threshold;
                i2 = 3;
            }
            for (int i4 = 0; i4 < 4; i4++) {
                int[] iArr = startPoint[i4];
                iArr[0] = iArr[0] + MATRIX_MOVE[i4][0];
                int[] iArr2 = startPoint[i4];
                iArr2[1] = iArr2[1] + MATRIX_MOVE[i4][1];
            }
            count++;
            threshold2 = threshold;
        }
        int threshold3 = threshold2;
        if (alpha == -1) {
            alpha = 0;
        }
        int[][] judge = MATRIX_POINT_THEMECROP;
        int detectedX = (startPoint[1][0] - startPoint[0][0]) + 1;
        int detectedY = (startPoint[3][1] - startPoint[0][1]) + 1;
        int judgeCount = 0;
        int i5 = 0;
        while (i5 < 8) {
            int x2 = ((judge[i5][c] * detectedX) / 192) + startPoint[c][c];
            int y2 = ((judge[i5][1] * detectedY) / 192) + startPoint[c][1];
            if ((colorArray[y2][x2] >>> 24) > threshold3) {
                judgeCount++;
            }
            i5++;
            c = 0;
        }
        if (judgeCount == 8 && scale <= 1.0f && sizeX == sizeY) {
            scale2 = 1.0f;
            fullUp = true;
        } else if (scale <= 1.0f) {
            scale2 = scale;
            fullUp = false;
        } else {
            scale2 = 1.0f;
            fullUp = false;
        }
        Log.i(
                TAG,
                "IconUnify : scaled rate="
                        + scale2
                        + ", size="
                        + Math.max(sizeX, sizeY)
                        + ", alpha="
                        + alpha
                        + ", hold="
                        + threshold3);
        return new IconScale(alpha, scale2, fullUp);
    }

    private IconScale getAppIconAlphaRelativeScaleForIconTray(
            Bitmap bm, int sizeX, int sizeY, float scale, int scaleType) {
        float scale2;
        boolean fullUp;
        int threshold;
        char c;
        int alpha = -1;
        int smallestSide = Math.min(sizeX, sizeY) / 2;
        char c2 = 0;
        int[][] startPoint = {
            new int[] {0, 0},
            new int[] {sizeX - 1, 0},
            new int[] {sizeX - 1, sizeY - 1},
            new int[] {0, sizeY - 1}
        };
        int[] bmArray = new int[sizeX * sizeY];
        int[][] colorArray = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, sizeY, sizeX);
        int threshold2 = scaleType > 0 ? 102 : 0;
        int threshold3 = threshold2;
        bm.getPixels(bmArray, 0, sizeX, 0, 0, sizeX, sizeY);
        for (int i = 0; i < sizeY; i++) {
            System.arraycopy(bmArray, sizeX * i, colorArray[i], 0, sizeX);
        }
        int count = 0;
        while (true) {
            int i2 = 3;
            if (smallestSide <= count || alpha != -1) {
                break;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= 4) {
                    threshold = threshold3;
                    c = 1;
                    break;
                }
                int x = startPoint[i3][0];
                c = 1;
                int y = startPoint[i3][1];
                int endPoint = 0;
                if (i3 != i2) {
                    endPoint = i3 + 1;
                }
                boolean completed = false;
                while (true) {
                    if (!completed) {
                        if (x == startPoint[endPoint][0] && y == startPoint[endPoint][1]) {
                            completed = true;
                        }
                        threshold = threshold3;
                        if ((colorArray[y][x] >>> 24) <= threshold) {
                            x += MATRIX_PROGRESS[i3][0];
                            y += MATRIX_PROGRESS[i3][1];
                            threshold3 = threshold;
                        } else {
                            int alpha2 = count;
                            alpha = alpha2;
                            break;
                        }
                    } else {
                        threshold = threshold3;
                        break;
                    }
                }
                if (alpha != -1) {
                    break;
                }
                i3++;
                threshold3 = threshold;
                i2 = 3;
            }
            if (alpha == -1) {
                for (int i4 = 0; i4 < 4; i4++) {
                    int[] iArr = startPoint[i4];
                    iArr[0] = iArr[0] + MATRIX_MOVE[i4][0];
                    int[] iArr2 = startPoint[i4];
                    iArr2[c] = iArr2[c] + MATRIX_MOVE[i4][c];
                }
            }
            count++;
            threshold3 = threshold;
        }
        if (alpha == -1) {
            alpha = 0;
        }
        if (scaleType == 0) {
            scale2 = scale;
            fullUp = false;
        } else {
            int[][] judge = MATRIX_POINT_ONEDOT;
            int detectedX = (startPoint[1][0] - startPoint[0][0]) + 1;
            int detectedY = (startPoint[3][1] - startPoint[0][1]) + 1;
            int judgeCount = 0;
            int i5 = 0;
            while (i5 < 8) {
                int x2 = ((judge[i5][c2] * detectedX) / 192) + startPoint[c2][c2];
                int count2 = count;
                int count3 = detectedY * judge[i5][1];
                int y2 = (count3 / 192) + startPoint[c2][1];
                if ((colorArray[y2][x2] >>> 24) > 26) {
                    judgeCount++;
                }
                i5++;
                count = count2;
                c2 = 0;
            }
            if (judgeCount == 8) {
                if (scaleType == 1) {
                    scale2 = 0.88f;
                    fullUp = false;
                } else if (sizeX == sizeY) {
                    scale2 = 1.0f;
                    fullUp = true;
                } else {
                    scale2 = 0.68f;
                    fullUp = false;
                }
            } else {
                scale2 = scaleType == 1 ? 0.94f : 0.72f;
                fullUp = false;
            }
        }
        return new IconScale(alpha, scale2, fullUp);
    }

    public Drawable getThemeIconWithBG(
            Context context, PackageItemInfo itemInfo, Drawable dr, int mode) {
        return getThemeIconWithBG(context, itemInfo, dr, false, mode);
    }

    public Drawable getThemeIconWithBG(
            Context context,
            PackageItemInfo itemInfo,
            Drawable dr,
            Boolean forDefaultContainer,
            int mode) {
        return getThemeIconWithBG(
                context, itemInfo, dr, forDefaultContainer, false, 0, "NULL", mode);
    }

    public Drawable getThemeIconWithBG(
            Context context,
            PackageItemInfo itemInfo,
            Drawable dr,
            Boolean forDefaultContainer,
            Boolean fromThemePackage,
            int mode) {
        return getThemeIconWithBG(
                context, itemInfo, dr, forDefaultContainer, fromThemePackage, 0, "NULL", mode);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.drawable.Drawable getThemeIconWithBG(
            android.content.Context r43,
            android.content.pm.PackageItemInfo r44,
            android.graphics.drawable.Drawable r45,
            java.lang.Boolean r46,
            java.lang.Boolean r47,
            int r48,
            java.lang.String r49,
            int r50) {
        /*
            Method dump skipped, instructions count: 1844
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.app.SemAppIconSolution.getThemeIconWithBG(android.content.Context,"
                    + " android.content.pm.PackageItemInfo, android.graphics.drawable.Drawable,"
                    + " java.lang.Boolean, java.lang.Boolean, int, java.lang.String,"
                    + " int):android.graphics.drawable.Drawable");
    }

    public Drawable checkAndDrawLiveIconFromTheme(
            Context context,
            PackageItemInfo itemInfo,
            Drawable drLiveIcon,
            boolean forIconContainer,
            boolean useThemeIcon,
            int mode) {
        boolean z;
        String liveIconBooleanName;
        boolean fromOverlay;
        String appThemePackage;
        boolean fromOverlay2 = false;
        int resID = -1;
        String str = itemInfo.packageName;
        switch (str.hashCode()) {
            case -1955351778:
                if (str.equals(CALENDAR_PACKAGE_NAME)) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 138102030:
                if (str.equals(CLOCK_PACKAGE_NAME)) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                String liveIconBooleanName2 = "calendar_" + LIVEICON_BOOLEAN_NAME;
                liveIconBooleanName = liveIconBooleanName2;
                break;
            case true:
                String liveIconBooleanName3 = "clock_" + LIVEICON_BOOLEAN_NAME;
                liveIconBooleanName = liveIconBooleanName3;
                break;
            default:
                liveIconBooleanName = LIVEICON_BOOLEAN_NAME;
                break;
        }
        try {
            Resources r = getAppIconPackageResources(context);
            if (r != null) {
                resID = r.getIdentifier(liveIconBooleanName, TYPE_BOOL, this.mAppIconPackageName);
                fromOverlay2 = r.getBoolean(resID);
            }
        } catch (Resources.NotFoundException e) {
            Log.e(
                    TAG,
                    "app icon package doesn't have 'liveicon_from_theme', pkg : "
                            + itemInfo.packageName);
        }
        if (resID != 0) {
            fromOverlay = fromOverlay2;
        } else {
            try {
                appThemePackage = this.mThemePackageName;
                switch (itemInfo.packageName) {
                    case "com.samsung.android.calendar":
                        appThemePackage = appThemePackage + ".calendar";
                        break;
                    case "com.sec.android.app.clockpackage":
                        appThemePackage = appThemePackage + ".clockpackage";
                        break;
                }
                Resources r2 = getThemePackageResources(context, appThemePackage);
                if (r2 != null) {
                    int resID2 =
                            r2.getIdentifier(LIVEICON_BOOLEAN_NAME, TYPE_BOOL, appThemePackage);
                    try {
                        fromOverlay2 = r2.getBoolean(resID2);
                        resID = resID2;
                    } catch (Exception e2) {
                        e = e2;
                        resID = resID2;
                        Log.e(
                                TAG,
                                "Failed at get liveicon boolean on overlay pkg : "
                                        + itemInfo.packageName
                                        + ", e : "
                                        + e);
                        fromOverlay = fromOverlay2;
                        Log.i(
                                TAG,
                                "load= live icon for "
                                        + itemInfo.packageName
                                        + ", from overlay = "
                                        + fromOverlay);
                        if (this.mSamsungThemeAppIconRange != 0) {}
                        return getThemeIconWithBG(
                                context,
                                itemInfo,
                                drLiveIcon,
                                false,
                                Boolean.valueOf(fromOverlay),
                                mode);
                    }
                }
                fromOverlay = fromOverlay2;
            } catch (Exception e3) {
                e = e3;
            }
        }
        Log.i(
                TAG,
                "load= live icon for " + itemInfo.packageName + ", from overlay = " + fromOverlay);
        if (this.mSamsungThemeAppIconRange != 0
                || (!fromOverlay && this.mSamsungThemeAppIconRange <= 1)) {
            return getThemeIconWithBG(
                    context, itemInfo, drLiveIcon, false, Boolean.valueOf(fromOverlay), mode);
        }
        return forIconContainer
                ? useThemeIcon
                        ? applyNightLayer(context, drLiveIcon, mode)
                        : wrapIconShadowAndNight(context, drLiveIcon, mode)
                : drLiveIcon;
    }

    public Drawable applyPrimaryColorToIcon(Context context, Drawable dr) {
        if (dr != null) {
            dr.setTint(context.getResources().getColor(R.color.sem_color_primary_light));
        }
        return dr;
    }

    public boolean isAppIconThemePackageSet() {
        return this.mAppIconPackageName != null || this.mIsThemeParkExist;
    }

    public Drawable getAppIconFromTheme(
            Context context, PackageItemInfo itemInfo, Drawable dr, int mode) {
        if (this.mIsThemeParkExist) {
            dr = getThemeParkAppIcon(context, itemInfo);
        }
        if (dr == null || !this.mIsThemeParkExist) {
            dr = getThemeAppIcon(context, itemInfo, false, mode);
        }
        if (dr == null) {
            return null;
        }
        if (this.mSamsungThemeAppIconRange == 0) {
            return getThemeIconWithBG(context, itemInfo, dr, false, true, mode);
        }
        return applyNightLayer(context, dr, mode);
    }

    public boolean needToGetLiveIcon(Context context, PackageItemInfo itemInfo) {
        char c;
        if (!CALENDAR_PACKAGE_NAME.equals(itemInfo.packageName)
                && !CLOCK_PACKAGE_NAME.equals(itemInfo.packageName)) {
            return false;
        }
        boolean isLiveIconFromTheme = false;
        String liveIconBooleanName = LIVEICON_BOOLEAN_NAME;
        String str = itemInfo.packageName;
        switch (str.hashCode()) {
            case -1955351778:
                if (str.equals(CALENDAR_PACKAGE_NAME)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 138102030:
                if (str.equals(CLOCK_PACKAGE_NAME)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                liveIconBooleanName = "calendar_" + LIVEICON_BOOLEAN_NAME;
                break;
            case 1:
                liveIconBooleanName = "clock_" + LIVEICON_BOOLEAN_NAME;
                break;
        }
        try {
            Resources r = getAppIconPackageResources(context);
            if (r != null) {
                int resID =
                        r.getIdentifier(liveIconBooleanName, TYPE_BOOL, this.mAppIconPackageName);
                isLiveIconFromTheme = r.getBoolean(resID);
            }
            if (!isLiveIconFromTheme) {
                Log.i(
                        TAG,
                        "app icon package doesn't support live theme icon for "
                                + itemInfo.packageName);
            }
            return isLiveIconFromTheme;
        } catch (Resources.NotFoundException e) {
            Log.e(
                    TAG,
                    "app icon package doesn't have 'liveicon_from_theme', pkg : "
                            + itemInfo.packageName);
            return false;
        }
    }

    public boolean isCropAppIconUsingBitmap(Bitmap bm, int sizeX, int sizeY) {
        return getAppIconAlphaRelativeScaleForIconTray(bm, sizeX, sizeY, 1.2f, 2).isCrop();
    }

    public float getAppIconAlphaRelativeScaleRateForIconTray(Bitmap bm, int sizeX, int sizeY) {
        return getAppIconAlphaRelativeScaleForIconTray(bm, sizeX, sizeY, 1.2f, 2).mScale;
    }

    public Drawable wrapIconShadowAndNight(Context context, Drawable drawable, int mode) {
        Drawable nightLayerDrawable = applyNightLayer(context, drawable, mode);
        return wrapIconShadow(nightLayerDrawable);
    }

    public Drawable applyNightLayer(Context context, Drawable drawable, int mode) {
        if (drawable == null) {
            return drawable;
        }
        int iconSize = drawable.getIntrinsicHeight();
        if (iconSize <= 0) {
            Log.i(
                    TAG,
                    "skip applying night layer bitmap because of abnormal icon size = " + iconSize);
            return drawable;
        }
        boolean needOnlyDay = (mode & 64) != 0;
        boolean needOnlyNight = (mode & 128) != 0;
        Configuration cf = context.getResources().getConfiguration();
        boolean isNightMode = needOnlyNight ? true : needOnlyDay ? false : cf.isNightModeActive();
        if (drawable instanceof AdaptiveIconDrawable) {
            ((AdaptiveIconDrawable) drawable).setNightModeLayer(isNightMode);
        } else if (isNightMode) {
            Bitmap bitmap =
                    Bitmap.createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            canvas.drawPaint(PAINT_FOR_NIGHT_LAYER);
            return new BitmapDrawable(Resources.getSystem(), bitmap);
        }
        return drawable;
    }

    public Drawable wrapIconShadow(Drawable drawable) {
        if (drawable == null) {
            return drawable;
        }
        int iconSize = drawable.getIntrinsicHeight();
        if (iconSize <= 0 || iconSize > 1000) {
            Log.i(TAG, "skip wrapping shadow bitmap because of abnormal icon size = " + iconSize);
            return drawable;
        }
        Bitmap shadow = getShadowBitmap(drawable);
        return new ShadowDrawable(shadow, drawable);
    }

    private Bitmap getShadowBitmap(Drawable d) {
        Path iconMaskPath;
        int shadowSize = d.getIntrinsicHeight();
        synchronized (this.mShadowCache) {
            WeakReference<Bitmap> shadowRef = this.mShadowCache.get(shadowSize);
            Bitmap shadow = shadowRef != null ? shadowRef.get() : null;
            if (shadow != null) {
                return shadow;
            }
            if (d instanceof AdaptiveIconDrawable) {
                d.setBounds(0, 0, shadowSize, shadowSize);
                Path iconMaskPath2 = ((AdaptiveIconDrawable) d).getIconMask();
                iconMaskPath = iconMaskPath2;
            } else {
                AdaptiveIconDrawable dummyIconForPath =
                        new AdaptiveIconDrawable((Drawable) null, d);
                dummyIconForPath.setBounds(0, 0, shadowSize, shadowSize);
                Path iconMaskPath3 = dummyIconForPath.getIconMask();
                iconMaskPath = iconMaskPath3;
            }
            float blur = shadowSize * ICON_SIZE_FACTOR_AMBIENT;
            float blur2 = shadowSize * ICON_SIZE_FACTOR_AMBIENT2;
            int bitmapSize = (int) (shadowSize + (2.0f * blur2));
            Bitmap shadow2 = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(shadow2);
            canvas.translate(blur2, blur2);
            Paint paint = new Paint(1);
            paint.setColor(0);
            paint.setShadowLayer(blur, 0.0f, 0.0f, Enums.AUDIO_FORMAT_LHDC_LL);
            canvas.drawPath(iconMaskPath, paint);
            paint.setShadowLayer(blur2, 0.0f, 0.0f, Enums.AUDIO_FORMAT_DSD);
            canvas.drawPath(iconMaskPath, paint);
            canvas.setBitmap(null);
            synchronized (this.mShadowCache) {
                this.mShadowCache.put(shadowSize, new WeakReference<>(shadow2));
            }
            return shadow2;
        }
    }

    public static class ShadowDrawable extends DrawableWrapper {
        final MyConstantState mState;

        public ShadowDrawable(Bitmap shadow, Drawable dr) {
            super(dr);
            this.mState = new MyConstantState(shadow, dr.getConstantState());
        }

        ShadowDrawable(MyConstantState state) {
            super(state.mChildState.newDrawable());
            this.mState = state;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Rect bounds = getBounds();
            canvas.drawBitmap(this.mState.mShadow, (Rect) null, bounds, this.mState.mPaint);
            canvas.save();
            canvas.translate(
                    bounds.width() * 0.96000004f * SemAppIconSolution.ICON_SIZE_FACTOR_AMBIENT2,
                    bounds.height() * 0.96000004f * SemAppIconSolution.ICON_SIZE_FACTOR_AMBIENT2);
            canvas.scale(0.96000004f, 0.96000004f);
            super.draw(canvas);
            canvas.restore();
        }

        private static class MyConstantState extends Drawable.ConstantState {
            final Drawable.ConstantState mChildState;
            final Paint mPaint = new Paint(2);
            final Bitmap mShadow;

            MyConstantState(Bitmap shadow, Drawable.ConstantState childState) {
                this.mShadow = shadow;
                this.mChildState = childState;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return new ShadowDrawable(this);
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return this.mChildState.getChangingConfigurations();
            }
        }
    }

    public Drawable getColorThemeIcon(
            Context context, Drawable dr, String packageName, int iconFeature) {
        boolean isNoAdaptive = (iconFeature & 4) != 0;
        boolean isOnlyBG = (iconFeature & 8) != 0;
        Drawable mono = null;
        if (dr instanceof AdaptiveIconDrawable) {
            mono =
                    isOnlyBG
                            ? ((AdaptiveIconDrawable) dr).getForeground()
                            : ((AdaptiveIconDrawable) dr).getMonochrome();
        } else if (isNoAdaptive && packageName != null) {
            try {
                Resources resources =
                        context.getPackageManager()
                                .getResourcesForApplicationAsUser(packageName, 0);
                int resID =
                        resources.getIdentifier("sep_monochrome_icon", TYPE_DRAWABLE, packageName);
                if (resID != 0) {
                    mono = resources.getDrawable(resID);
                } else {
                    Log.w(TAG, "Monochrome image is not existed, Pkg=" + packageName);
                }
            } catch (Exception e) {
                Log.w(
                        TAG,
                        "Failed to find monochrome, Pkg="
                                + packageName
                                + ", Exception="
                                + e.toString());
            }
        }
        if (mono != null) {
            Drawable mono2 = mono.mutate();
            int[] colors = getColorsForIcon(context);
            if (!isOnlyBG) {
                mono2.setTint(colors[1]);
            }
            Log.i(
                    TAG,
                    "ColorTheme icon has returned, color = #"
                            + Integer.toHexString(colors[0])
                            + ", isNoAdaptive = "
                            + isNoAdaptive
                            + ", isOnlyBG = "
                            + isOnlyBG);
            return new AdaptiveIconDrawable(new ColorDrawable(colors[0]), mono2);
        }
        return null;
    }

    private int[] getColorsForIcon(Context context) {
        Resources res = context.getResources();
        int[] colors = new int[2];
        Configuration cf = res.getConfiguration();
        if (cf.isNightModeActive()) {
            colors[0] = res.getColor(17170494);
            colors[1] = Color.parseColor("#ff000000");
        } else {
            colors[0] = res.getColor(17170493);
            colors[1] = Color.parseColor("#ffffffff");
        }
        return colors;
    }

    private void createColorFilter(Resources r) {
        try {
            int resID =
                    r.getIdentifier(
                            "theme_designer_icon_add_color", "color", this.mAppIconPackageName);
            int colorToAdd = r.getInteger(resID);
            int resID2 =
                    r.getIdentifier(
                            "theme_designer_icon_saturation", "integer", this.mAppIconPackageName);
            int saturation = r.getInteger(resID2);
            int resID3 =
                    r.getIdentifier(
                            "theme_designer_icon_mult_color", "color", this.mAppIconPackageName);
            int colorToMultiply = r.getInteger(resID3);
            this.mColorFilter = createColorFilter(colorToMultiply, saturation / 100.0f, colorToAdd);
        } catch (Exception e) {
            this.mColorFilter = null;
        }
    }

    private ColorFilter createColorFilter(int colorToMultiply, float saturation, int colorToAdd) {
        Log.d(
                TAG,
                "createFilter colorToMultiply: "
                        + colorToMultiply
                        + " ,colorToAdd: "
                        + colorToAdd
                        + " , saturation: "
                        + saturation);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(saturation);
        float[] matrix = colorMatrix.getArray();
        float r = ((colorToMultiply >> 16) & 255) / 255.0f;
        float g = ((colorToMultiply >> 8) & 255) / 255.0f;
        float b = (colorToMultiply & 255) / 255.0f;
        matrix[0] = matrix[0] * r;
        matrix[1] = matrix[1] * r;
        matrix[2] = matrix[2] * r;
        matrix[5] = matrix[5] * g;
        matrix[6] = matrix[6] * g;
        matrix[7] = matrix[7] * g;
        matrix[10] = matrix[10] * b;
        matrix[11] = matrix[11] * b;
        matrix[12] = matrix[12] * b;
        matrix[4] = (colorToAdd >> 16) & 255;
        matrix[9] = (colorToAdd >> 8) & 255;
        matrix[14] = colorToAdd & 255;
        ColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        return colorMatrixColorFilter;
    }

    public void applyAppIconColorFilter(Drawable dr) {
        dr.setColorFilter(this.mColorFilter);
    }

    public boolean hasAppIconColorFilter() {
        return this.mColorFilter != null;
    }
}
