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
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.HashMap;

/* loaded from: classes.dex */
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
    public static final Paint PAINT_FOR_NIGHT_LAYER;
    private static final String RES_LOCKSCREEN_SHORTCUT_BG = "ic_shortcut_theme_bg";
    private static final float SAMSUNG_THEME_APPICON_SCALE = 0.7f;
    public static final int SET_APPICON_COLORTHEME = 3;
    public static final int SET_APPICON_THEME = 0;
    private static final int SHADOW_ALPHA_AMBIENT = 41;
    private static final int SHADOW_ALPHA_AMBIENT2 = 26;
    private static final String TAG = "AppIconSolution";
    private static final String TYPE_BOOL = "bool";
    private static final String TYPE_DRAWABLE = "drawable";
    public static final int UNSET_APPICON_THEME = 1;
    private static int sLayerColorForNight = Color.parseColor("#19000000");
    private static SemAppIconSolution sUniqueInstance;
    private boolean mIgnoreAppIconThemeHost;
    private Paint mPaint;
    private Paint mPaintForCrop;
    private float mSamsungThemeAppIconScale = SAMSUNG_THEME_APPICON_SCALE;
    private int mSamsungThemeAppIconRange = 3;
    private String mAppIconPackageName = null;
    private String mThemePackageName = null;
    private boolean mSamsungThemeAppIconMask = false;
    private final Object mThemeSync = new Object();
    private HashMap<String, String> mThemeAppIconMap = null;
    private final int LIMIT_ICON_SIZE = 216;
    private final int LIMIT_SHADOW_SIZE = 1000;
    private Pair<String, APKContents> mCachedAPKContents = null;
    private final SparseArray<WeakReference<Bitmap>> mShadowCache = new SparseArray<>();
    private final String RESNAME_MONOCHROME = "sep_monochrome_icon";

    static {
        Paint paint = new Paint(1);
        PAINT_FOR_NIGHT_LAYER = paint;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        paint.setColor(sLayerColorForNight);
        MATRIX_PROGRESS = new int[][]{new int[]{1, 0}, new int[]{0, 1}, new int[]{-1, 0}, new int[]{0, -1}};
        MATRIX_MOVE = new int[][]{new int[]{1, 1}, new int[]{-1, 1}, new int[]{-1, -1}, new int[]{1, -1}};
        MATRIX_POINT_ONEDOT = new int[][]{new int[]{22, 22}, new int[]{96, 2}, new int[]{169, 22}, new int[]{189, 96}, new int[]{169, 169}, new int[]{96, 189}, new int[]{22, 169}, new int[]{2, 96}};
        MATRIX_POINT_THEMECROP = new int[][]{new int[]{22, 29}, new int[]{96, 3}, new int[]{170, 29}, new int[]{187, 94}, new int[]{170, 163}, new int[]{96, 186}, new int[]{22, 163}, new int[]{5, 94}};
    }

    private SemAppIconSolution(Context context) throws Resources.NotFoundException {
        this.mIgnoreAppIconThemeHost = false;
        if (context != null) {
            int identifier = context.getResources().getIdentifier("sem_appicon_layer_color_for_night", "color", "android");
            if (identifier > 0) {
                int color = context.getResources().getColor(identifier);
                sLayerColorForNight = color;
                PAINT_FOR_NIGHT_LAYER.setColor(color);
            }
            this.mIgnoreAppIconThemeHost = SamsungThemeConstants.ignoreAppIconThemeHosts.contains(context.getBasePackageName());
        }
    }

    public static synchronized SemAppIconSolution getInstance(Context context) {
        if (sUniqueInstance == null) {
            sUniqueInstance = new SemAppIconSolution(context);
        }
        return sUniqueInstance;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ac A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int checkAppIconThemePackage(Context context) {
        String string;
        String string2;
        String str;
        boolean z;
        String str2;
        String str3;
        boolean z2;
        try {
            string = Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage");
            try {
                string2 = Settings.System.getString(context.getContentResolver(), "current_sec_appicon_theme_package");
            } catch (SecurityException e) {
                e = e;
                Log.i(TAG, "couldn't access setting property, just keep appIconPackageName empty, ex = " + e);
                string2 = "";
                str = null;
                if ("".equals(string2)) {
                }
                if (this.mIgnoreAppIconThemeHost) {
                    str = string2;
                    z = false;
                }
                str2 = this.mAppIconPackageName;
                if (str2 == null) {
                    ApplicationPackageManager.configurationChanged();
                    this.mAppIconPackageName = str;
                    registerAppIconInfo(context);
                } else {
                    ApplicationPackageManager.configurationChanged();
                    this.mAppIconPackageName = str;
                    registerAppIconInfo(context);
                }
                str3 = this.mThemePackageName;
                if (str3 == null) {
                    this.mThemePackageName = string;
                } else {
                    this.mThemePackageName = string;
                }
                if (this.mAppIconPackageName != null) {
                }
                if (this.mAppIconPackageName == null) {
                }
            }
        } catch (SecurityException e2) {
            e = e2;
            string = "";
        }
        str = null;
        if ("".equals(string2)) {
            string2 = null;
        }
        if (this.mIgnoreAppIconThemeHost || string2 == null || !SamsungThemeConstants.ignoreAppIconThemeList.contains(string2)) {
            str = string2;
            z = false;
        } else {
            z = true;
        }
        str2 = this.mAppIconPackageName;
        if ((str2 == null && !str2.equals(str)) || ((str != null && !str.equals(this.mAppIconPackageName)) || this.mSamsungThemeAppIconRange == 3)) {
            ApplicationPackageManager.configurationChanged();
            this.mAppIconPackageName = str;
            registerAppIconInfo(context);
        }
        str3 = this.mThemePackageName;
        if ((str3 == null && !str3.equals(string)) || (string != null && !string.equals(this.mThemePackageName))) {
            this.mThemePackageName = string;
        }
        if (this.mAppIconPackageName != null) {
            try {
            } catch (SecurityException e3) {
                Log.i(TAG, "couldn't access setting property, just keep colortheme icon disabled, ex = " + e3);
            }
            z2 = Settings.Global.getInt(context.getContentResolver(), "colortheme_app_icon", 0) == 1;
        }
        if (this.mAppIconPackageName == null) {
            return 0;
        }
        if (!z2 || context.getUserId() == 77) {
            return z ? 2 : 1;
        }
        return 3;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:24:0x0088
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    private void registerAppIconInfo(android.content.Context r8) {
        /*
            r7 = this;
            java.lang.String r0 = "integer"
            java.lang.String r1 = "Icon package doesnt have resources "
            java.lang.String r2 = r7.mAppIconPackageName
            r3 = 1060320051(0x3f333333, float:0.7)
            r4 = 2
            r5 = 0
            if (r2 == 0) goto L96
            java.lang.String r2 = android.content.APKContents.getMainThemePackagePath(r2)     // Catch: java.lang.Exception -> L8b
            java.io.File r6 = new java.io.File     // Catch: java.lang.Exception -> L8b
            r6.<init>(r2)     // Catch: java.lang.Exception -> L8b
            boolean r6 = r6.exists()     // Catch: java.lang.Exception -> L8b
            if (r6 == 0) goto L26
            android.content.APKContents r8 = new android.content.APKContents     // Catch: java.lang.Exception -> L8b
            r8.<init>(r2)     // Catch: java.lang.Exception -> L8b
            android.content.res.Resources r8 = r8.getResources()     // Catch: java.lang.Exception -> L8b
            goto L30
        L26:
            android.content.pm.PackageManager r8 = r8.getPackageManager()     // Catch: java.lang.Exception -> L8b
            java.lang.String r2 = r7.mAppIconPackageName     // Catch: java.lang.Exception -> L8b
            android.content.res.Resources r8 = r8.getResourcesForApplicationAsUser(r2, r5)     // Catch: java.lang.Exception -> L8b
        L30:
            if (r8 != 0) goto L46
            java.lang.String r8 = "AppIconSolution"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L8b
            r0.<init>(r1)     // Catch: java.lang.Exception -> L8b
            java.lang.String r1 = r7.mAppIconPackageName     // Catch: java.lang.Exception -> L8b
            r0.append(r1)     // Catch: java.lang.Exception -> L8b
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L8b
            android.util.Log.e(r8, r0)     // Catch: java.lang.Exception -> L8b
            return
        L46:
            java.lang.String r1 = "icon_bg_range"
            java.lang.String r2 = r7.mAppIconPackageName     // Catch: java.lang.Exception -> L8b
            int r1 = r8.getIdentifier(r1, r0, r2)     // Catch: java.lang.Exception -> L8b
            if (r1 == 0) goto L57
            int r1 = r8.getInteger(r1)     // Catch: java.lang.Exception -> L8b
            r7.mSamsungThemeAppIconRange = r1     // Catch: java.lang.Exception -> L8b
            goto L59
        L57:
            r7.mSamsungThemeAppIconRange = r4     // Catch: java.lang.Exception -> L8b
        L59:
            java.lang.String r1 = "icon_scale_size"
            java.lang.String r2 = r7.mAppIconPackageName     // Catch: java.lang.Exception -> L8b
            int r0 = r8.getIdentifier(r1, r0, r2)     // Catch: java.lang.Exception -> L8b
            if (r0 == 0) goto L6f
            int r0 = r8.getInteger(r0)     // Catch: java.lang.Exception -> L8b
            float r0 = (float) r0     // Catch: java.lang.Exception -> L8b
            r1 = 1008981770(0x3c23d70a, float:0.01)
            float r0 = r0 * r1
            r7.mSamsungThemeAppIconScale = r0     // Catch: java.lang.Exception -> L8b
            goto L71
        L6f:
            r7.mSamsungThemeAppIconScale = r3     // Catch: java.lang.Exception -> L8b
        L71:
            java.lang.String r0 = "mask_from_theme"
            java.lang.String r1 = "bool"
            java.lang.String r2 = r7.mAppIconPackageName     // Catch: java.lang.Exception -> L88
            int r0 = r8.getIdentifier(r0, r1, r2)     // Catch: java.lang.Exception -> L88
            if (r0 == 0) goto L85
            boolean r8 = r8.getBoolean(r0)     // Catch: java.lang.Exception -> L88
            r7.mSamsungThemeAppIconMask = r8     // Catch: java.lang.Exception -> L88
            goto L95
        L85:
            r7.mSamsungThemeAppIconMask = r5     // Catch: java.lang.Exception -> L88
            goto L95
        L88:
            r7.mSamsungThemeAppIconMask = r5     // Catch: java.lang.Exception -> L8b
            goto L95
        L8b:
            r8 = move-exception
            r7.mSamsungThemeAppIconRange = r4
            r7.mSamsungThemeAppIconScale = r3
            r7.mSamsungThemeAppIconMask = r5
            r8.printStackTrace()
        L95:
            return
        L96:
            r7.mSamsungThemeAppIconRange = r4
            r7.mSamsungThemeAppIconScale = r3
            r7.mSamsungThemeAppIconMask = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.SemAppIconSolution.registerAppIconInfo(android.content.Context):void");
    }

    private Drawable getThemeParkAppIcon(Context context, PackageItemInfo packageItemInfo) {
        String str;
        String str2 = packageItemInfo.packageName;
        String str3 = packageItemInfo.name;
        if (str2 == null) {
            return null;
        }
        String str4 = SamsungThemeConstants.PATH_THEMEPARK_ICON + str2;
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        if (str3 != null) {
            str = NativeLibraryHelper.CLEAR_ABI_OVERRIDE + Integer.toHexString(str3.hashCode()).toLowerCase() + ".png";
        } else {
            str = ".png";
        }
        sb.append(str);
        String string = sb.toString();
        if (!new File(string).exists()) {
            string = SamsungThemeConstants.PATH_THEMEPARK_ICON + str2 + ".png";
            if (!new File(string).exists()) {
                return null;
            }
        }
        try {
            return new BitmapDrawable(context.getResources(), BitmapFactory.decodeFile(string));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    private Drawable getThemeAppIcon(Context context, PackageItemInfo packageItemInfo, boolean z, int i) {
        return getThemeAppIcon(context, packageItemInfo, z, false, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Drawable getThemeAppIcon(Context context, PackageItemInfo packageItemInfo, boolean z, boolean z2, int i) {
        String str;
        HashMap<String, String> themeAppIconMap = getThemeAppIconMap(context);
        String str2 = this.mAppIconPackageName;
        if (str2 != null && !str2.isEmpty()) {
            if ((i & 256) != 0 && z) {
                str = RES_LOCKSCREEN_SHORTCUT_BG;
            } else if (z) {
                str = themeAppIconMap.get("3rd_party_icon");
            } else if (z2) {
                str = themeAppIconMap.get("mask_for_crop");
            } else if (packageItemInfo == null) {
                str = null;
            } else if (packageItemInfo.name != null) {
                String str3 = themeAppIconMap.get(packageItemInfo.name);
                str = (str3 == null && (packageItemInfo instanceof ApplicationInfo)) ? themeAppIconMap.get(packageItemInfo.packageName) : str3;
            } else if (packageItemInfo.packageName != null) {
                str = themeAppIconMap.get(packageItemInfo.packageName);
            }
            if (str != null) {
                return getDrawableFromAppIconPackage(context, str, "[getThemeAppIcon]", i);
            }
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
            XmlResourceParser xml = context.getResources().getXml(R.xml.theme_app_icons);
            this.mThemeAppIconMap = new HashMap<>();
            if (xml == null) {
                return;
            }
            int depth = xml.getDepth();
            while (true) {
                int next = xml.next();
                if ((next == 3 && xml.getDepth() <= depth) || next == 1) {
                    return;
                }
                if (next == 2 && "ThemeApp".equals(xml.getName())) {
                    int attributeCount = xml.getAttributeCount();
                    String str = null;
                    String str2 = null;
                    for (int i = 0; i < attributeCount; i++) {
                        String attributeName = xml.getAttributeName(i);
                        String attributeValue = xml.getAttributeValue(i);
                        if (attributeName != null && attributeName.equals(SearchIndexablesContract.BaseColumns.COLUMN_CLASS_NAME)) {
                            str = attributeValue;
                        }
                        if (attributeName != null && attributeName.equals("iconId")) {
                            str2 = attributeValue;
                        }
                    }
                    this.mThemeAppIconMap.put(str, str2);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception during parsing theme app list" + e);
            e.printStackTrace();
        }
    }

    private Drawable getDrawableFromAppIconPackage(Context context, String str, String str2, int i) {
        Resources appIconPackageResources;
        Resources resourcesForApplicationAsUser;
        if ((i & 256) != 0) {
            try {
                try {
                    resourcesForApplicationAsUser = context.getPackageManager().getResourcesForApplicationAsUser("com.android.systemui", 0);
                } catch (Exception e) {
                    Log.e(TAG, str2 + ", Failed to get LockScreen Shorcut Icon=" + str + ", Exception=" + e.toString());
                }
            } catch (PackageManager.NameNotFoundException unused) {
                resourcesForApplicationAsUser = null;
            }
            if (resourcesForApplicationAsUser == null) {
                Log.e(TAG, "SystemUI package doesn't have resources");
                return null;
            }
            int identifier = resourcesForApplicationAsUser.getIdentifier(str, TYPE_DRAWABLE, "com.android.systemui");
            if (identifier != 0) {
                return resourcesForApplicationAsUser.getDrawable(identifier);
            }
            return null;
        }
        try {
            appIconPackageResources = getAppIconPackageResources(context);
        } catch (Exception e2) {
            Log.e(TAG, str2 + ", Icon=" + str + ", Exception=" + e2.toString());
        }
        if (appIconPackageResources == null) {
            Log.e(TAG, "Icon package doesnt have resources " + this.mAppIconPackageName);
            return null;
        }
        int identifier2 = appIconPackageResources.getIdentifier(str, TYPE_DRAWABLE, this.mAppIconPackageName);
        if (identifier2 != 0) {
            return appIconPackageResources.getDrawable(identifier2);
        }
        return null;
    }

    public Resources getAppIconPackageResources(Context context) {
        return getThemePackageResources(context, this.mAppIconPackageName);
    }

    private Resources getThemePackageResources(Context context, String str) {
        String currentThemePackagePath;
        if (str == null) {
            Log.e(TAG, "Couldn't get theme package resources, package is null");
            return null;
        }
        try {
            if (str.equals(this.mAppIconPackageName)) {
                currentThemePackagePath = APKContents.getMainThemePackagePath(str);
            } else {
                currentThemePackagePath = APKContents.getCurrentThemePackagePath(str);
            }
            if (new File(currentThemePackagePath).exists()) {
                Pair<String, APKContents> pair = this.mCachedAPKContents;
                APKContents aPKContents = (pair == null || !currentThemePackagePath.equals(pair.first)) ? null : this.mCachedAPKContents.second;
                if (aPKContents == null || aPKContents.getResources() == null) {
                    aPKContents = new APKContents(currentThemePackagePath);
                    this.mCachedAPKContents = new Pair<>(currentThemePackagePath, aPKContents);
                } else {
                    Log.e(TAG, "Using cached contents available for " + this.mAppIconPackageName);
                }
                return aPKContents.getResources();
            }
            if (!str.equals(this.mAppIconPackageName)) {
                return null;
            }
            try {
                return context.getPackageManager().getResourcesForApplicationAsUser(this.mAppIconPackageName, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed at get appIconPackage resources, e : " + e);
            return null;
        }
    }

    private static class IconScale {
        private int mAlpha;
        private boolean mIsCrop;
        private float mScale;

        public IconScale(int i, float f, boolean z) {
            this.mAlpha = i;
            this.mScale = f;
            this.mIsCrop = z;
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
            return "IconScale[alpha=" + this.mAlpha + ", scale=" + this.mScale + ", isCrop=" + this.mIsCrop + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    private IconScale getAppIconAlphaRelativeScale(Bitmap bitmap, int i, int i2, float f, int i3) {
        if (this.mSamsungThemeAppIconMask) {
            return getAppIconAlphaRelativeScaleForIconUnification(bitmap, i, i2, f);
        }
        return getAppIconAlphaRelativeScaleForIconTray(bitmap, i, i2, f, i3);
    }

    private IconScale getAppIconAlphaRelativeScaleForIconUnification(Bitmap bitmap, int i, int i2, float f) {
        int i3;
        int iMin = Math.min(i, i2) / 2;
        boolean z = false;
        int i4 = i - 1;
        int i5 = i2 - 1;
        int[][] iArr = {new int[]{0, 0}, new int[]{i4, 0}, new int[]{i4, i5}, new int[]{0, i5}};
        int[] iArr2 = new int[i * i2];
        int[][] iArr3 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i2, i);
        bitmap.getPixels(iArr2, 0, i, 0, 0, i, i2);
        for (int i6 = 0; i6 < i2; i6++) {
            System.arraycopy(iArr2, i * i6, iArr3[i6], 0, i);
        }
        int i7 = -1;
        int i8 = 0;
        while (true) {
            int i9 = 3;
            if (iMin <= i8 || i7 != -1) {
                break;
            }
            int i10 = 0;
            while (i10 < 4) {
                int[] iArr4 = iArr[i10];
                int i11 = iArr4[0];
                int i12 = iArr4[1];
                int i13 = i10 != i9 ? i10 + 1 : 0;
                boolean z2 = false;
                while (true) {
                    if (z2) {
                        i3 = i9;
                        break;
                    }
                    int[] iArr5 = iArr[i13];
                    i3 = i9;
                    if (i11 == iArr5[0] && i12 == iArr5[1]) {
                        z2 = true;
                    }
                    if ((iArr3[i12][i11] >>> 24) > 26) {
                        i7 = i8;
                        break;
                    }
                    int[] iArr6 = MATRIX_PROGRESS[i10];
                    i11 += iArr6[0];
                    i12 += iArr6[1];
                    i9 = i3;
                }
                if (i7 != -1) {
                    break;
                }
                i10++;
                i9 = i3;
            }
            for (int i14 = 0; i14 < 4; i14++) {
                int[] iArr7 = iArr[i14];
                int i15 = iArr7[0];
                int[] iArr8 = MATRIX_MOVE[i14];
                iArr7[0] = i15 + iArr8[0];
                iArr7[1] = iArr7[1] + iArr8[1];
            }
            i8++;
        }
        if (i7 == -1) {
            i7 = 0;
        }
        int[][] iArr9 = MATRIX_POINT_THEMECROP;
        int i16 = iArr[1][0];
        int[] iArr10 = iArr[0];
        int i17 = (i16 - iArr10[0]) + 1;
        int i18 = (iArr[3][1] - iArr10[1]) + 1;
        int i19 = 0;
        for (int i20 = 0; i20 < 8; i20++) {
            int[] iArr11 = iArr9[i20];
            int i21 = (iArr11[0] * i17) / 192;
            int[] iArr12 = iArr[0];
            if ((iArr3[((iArr11[1] * i18) / 192) + iArr12[1]][i21 + iArr12[0]] >>> 24) > 26) {
                i19++;
            }
        }
        float f2 = 1.0f;
        if (i19 == 8 && f <= 1.0f && i == i2) {
            z = true;
        } else if (f <= 1.0f) {
            f2 = f;
        }
        Log.i(TAG, "IconUnify : scaled rate=" + f2 + ", size=" + Math.max(i, i2) + ", alpha=" + i7 + ", hold=26");
        return new IconScale(i7, f2, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private IconScale getAppIconAlphaRelativeScaleForIconTray(Bitmap bitmap, int i, int i2, float f, int i3) {
        float f2;
        boolean z;
        int i4;
        int iMin = Math.min(i, i2) / 2;
        int i5 = 0;
        int i6 = i - 1;
        int i7 = i2 - 1;
        int[][] iArr = {new int[]{0, 0}, new int[]{i6, 0}, new int[]{i6, i7}, new int[]{0, i7}};
        int[] iArr2 = new int[i * i2];
        int[][] iArr3 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i2, i);
        int i8 = i3 > 0 ? 102 : 0;
        bitmap.getPixels(iArr2, 0, i, 0, 0, i, i2);
        for (int i9 = 0; i9 < i2; i9++) {
            System.arraycopy(iArr2, i * i9, iArr3[i9], 0, i);
        }
        int i10 = -1;
        int i11 = 0;
        while (true) {
            int i12 = 3;
            if (iMin <= i11 || i10 != -1) {
                break;
            }
            int i13 = i5;
            while (true) {
                if (i13 >= 4) {
                    i4 = i5;
                    break;
                }
                int[] iArr4 = iArr[i13];
                int i14 = iArr4[i5];
                int i15 = iArr4[1];
                int i16 = i13 != i12 ? i13 + 1 : i5;
                int i17 = i12;
                int i18 = i14;
                int i19 = i5;
                while (true) {
                    if (i19 != 0) {
                        i4 = i5;
                        break;
                    }
                    int[] iArr5 = iArr[i16];
                    i4 = i5;
                    if (i18 == iArr5[i4] && i15 == iArr5[1]) {
                        i19 = 1;
                    }
                    if ((iArr3[i15][i18] >>> 24) > i8) {
                        i10 = i11;
                        break;
                    }
                    int[] iArr6 = MATRIX_PROGRESS[i13];
                    i18 += iArr6[i4];
                    i15 += iArr6[1];
                    i5 = i4;
                }
                if (i10 != -1) {
                    break;
                }
                i13++;
                i12 = i17;
                i5 = i4;
            }
            if (i10 == -1) {
                for (int i20 = i4; i20 < 4; i20++) {
                    int[] iArr7 = iArr[i20];
                    int i21 = iArr7[i4];
                    int[] iArr8 = MATRIX_MOVE[i20];
                    iArr7[i4] = i21 + iArr8[i4];
                    iArr7[1] = iArr7[1] + iArr8[1];
                }
            }
            i11++;
            i5 = i4;
        }
        int i22 = i5;
        if (i10 == -1) {
            i10 = i22;
        }
        if (i3 != 0) {
            int[][] iArr9 = MATRIX_POINT_ONEDOT;
            int i23 = iArr[1][i22];
            int[] iArr10 = iArr[i22];
            int i24 = (i23 - iArr10[i22]) + 1;
            int i25 = (iArr[3][1] - iArr10[1]) + 1;
            int i26 = i22;
            int i27 = i26;
            while (i26 < 8) {
                int[] iArr11 = iArr9[i26];
                int i28 = (iArr11[i22] * i24) / 192;
                int[] iArr12 = iArr[i22];
                if ((iArr3[((iArr11[1] * i25) / 192) + iArr12[1]][i28 + iArr12[i22]] >>> 24) > 26) {
                    i27++;
                }
                i26++;
            }
            if (i27 != 8) {
                f2 = i3 == 1 ? 0.94f : DEFAULT_THEME_APPICON_SCALE;
            } else if (i3 == 1) {
                f2 = 0.88f;
            } else {
                if (i == i2) {
                    f2 = 1.0f;
                    z = 1;
                    return new IconScale(i10, f2, z);
                }
                f2 = 0.68f;
            }
        } else {
            f2 = f;
        }
        z = i22;
        return new IconScale(i10, f2, z);
    }

    public Drawable getThemeIconWithBG(Context context, PackageItemInfo packageItemInfo, Drawable drawable, int i) {
        return getThemeIconWithBG(context, packageItemInfo, drawable, false, i);
    }

    public Drawable getThemeIconWithBG(Context context, PackageItemInfo packageItemInfo, Drawable drawable, Boolean bool, int i) {
        return getThemeIconWithBG(context, packageItemInfo, drawable, bool, false, 0, "NULL", i);
    }

    public Drawable getThemeIconWithBG(Context context, PackageItemInfo packageItemInfo, Drawable drawable, Boolean bool, Boolean bool2, int i) {
        return getThemeIconWithBG(context, packageItemInfo, drawable, bool, bool2, 0, "NULL", i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Drawable getThemeIconWithBG(Context context, PackageItemInfo packageItemInfo, Drawable drawable, Boolean bool, Boolean bool2, int i, String str, int i2) throws Throwable {
        String str2;
        int i3;
        int i4;
        int intrinsicHeight;
        Bitmap bitmap;
        int intrinsicWidth;
        boolean z;
        int i5;
        Bitmap bitmap2;
        int i6;
        int i7;
        Bitmap bitmap3;
        int i8;
        Bitmap bitmap4;
        String str3;
        String str4;
        int i9;
        Bitmap bitmap5;
        int i10;
        String str5;
        boolean z2;
        String str6;
        int i11;
        String str7;
        String str8;
        IconScale appIconAlphaRelativeScale;
        int i12;
        int i13;
        int i14;
        Bitmap bitmap6;
        IconScale iconScale;
        float f;
        float f2;
        int height;
        boolean z3;
        float alpha;
        int i15;
        float f3;
        int i16;
        Bitmap bitmap7;
        boolean z4;
        int i17;
        Bitmap bitmapCreateBitmap;
        boolean z5;
        Canvas canvas;
        int i18;
        int alpha2;
        Bitmap bitmap8;
        Bitmap bitmapCreateBitmap2;
        boolean z6;
        int i19;
        Bitmap bitmap9;
        SemAppIconSolution semAppIconSolution = this;
        Drawable bitmapDrawable = drawable;
        if (packageItemInfo != null) {
            if (packageItemInfo.packageName != null && !SamsungThemeConstants.PACKAGE_NAME_FOR_SKIP_THEME_APPICON.equals(packageItemInfo.packageName)) {
                str2 = packageItemInfo.packageName;
            }
            return bitmapDrawable;
        }
        str2 = str;
        Configuration configuration = context.getResources().getConfiguration();
        if (bool.booleanValue()) {
            if (bitmapDrawable instanceof AdaptiveIconDrawable) {
                Log.i(TAG, "return adaptive icon for " + str2 + ", isNight = " + configuration.isNightModeActive());
                return semAppIconSolution.wrapIconShadowAndNight(context, bitmapDrawable, i2);
            }
        } else if (semAppIconSolution.mSamsungThemeAppIconRange == 2) {
            Log.i(TAG, "return the original icon because tray option is set to None for " + str2 + ", isNight = " + configuration.isNightModeActive());
            return semAppIconSolution.applyNightLayer(context, bitmapDrawable, i2);
        }
        if (bool.booleanValue() || (semAppIconSolution.mAppIconPackageName != null && semAppIconSolution.mSamsungThemeAppIconRange >= 2)) {
            i3 = 2;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inScaled = false;
            Resources system = Resources.getSystem();
            i4 = R.drawable.ic_bg_container_onedot;
            Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(system, R.drawable.ic_bg_container_onedot, options);
            int i20 = options.outWidth;
            intrinsicHeight = options.outHeight;
            bitmap = bitmapDecodeResource;
            intrinsicWidth = i20;
        } else {
            Drawable themeAppIcon = semAppIconSolution.getThemeAppIcon(context, packageItemInfo, true, i2);
            if (themeAppIcon != null) {
                if (themeAppIcon instanceof BitmapDrawable) {
                    bitmap9 = ((BitmapDrawable) themeAppIcon).getBitmap();
                    i3 = 2;
                } else {
                    i3 = 2;
                    Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap(themeAppIcon.getIntrinsicWidth(), themeAppIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(bitmapCreateBitmap3);
                    themeAppIcon.setBounds(0, 0, canvas2.getWidth(), canvas2.getHeight());
                    themeAppIcon.draw(canvas2);
                    bitmap9 = bitmapCreateBitmap3;
                }
                bitmap9.setDensity(0);
                intrinsicWidth = bitmap9.getWidth();
                bitmap = bitmap9;
                intrinsicHeight = bitmap9.getHeight();
            } else {
                i3 = 2;
                intrinsicWidth = -1;
                bitmap = null;
                intrinsicHeight = -1;
            }
            i4 = R.drawable.ic_bg_container_onedot;
        }
        if (intrinsicWidth >= 0 || semAppIconSolution.mAppIconPackageName == null || semAppIconSolution.mSamsungThemeAppIconRange > 1) {
            z = false;
        } else {
            Drawable drawable2 = Resources.getSystem().getDrawable(i4);
            intrinsicWidth = drawable2.getIntrinsicWidth();
            intrinsicHeight = drawable2.getIntrinsicHeight();
            z = true;
        }
        if (intrinsicWidth > 0 && intrinsicHeight > 0) {
            int intrinsicWidth2 = bitmapDrawable.getIntrinsicWidth();
            int intrinsicHeight2 = bitmapDrawable.getIntrinsicHeight();
            if (intrinsicWidth2 > 0 && intrinsicHeight2 > 0) {
                StringBuilder sb = new StringBuilder("start to load, pkg=");
                sb.append(str2);
                sb.append(", bg=");
                sb.append(intrinsicWidth);
                int i21 = intrinsicWidth;
                sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                sb.append(intrinsicHeight);
                sb.append(", dr=");
                sb.append(intrinsicWidth2);
                sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                sb.append(intrinsicHeight2);
                int i22 = intrinsicHeight;
                sb.append(", forDefault=");
                sb.append(bool);
                sb.append(", density=");
                sb.append(i);
                Log.i(TAG, sb.toString());
                if (bitmapDrawable instanceof BitmapDrawable) {
                    bitmap2 = ((BitmapDrawable) bitmapDrawable).getBitmap();
                    i5 = 0;
                } else {
                    Bitmap bitmapCreateBitmap4 = Bitmap.createBitmap(intrinsicWidth2, intrinsicHeight2, Bitmap.Config.ARGB_8888);
                    Canvas canvas3 = new Canvas(bitmapCreateBitmap4);
                    i5 = 0;
                    bitmapDrawable.setBounds(0, 0, canvas3.getWidth(), canvas3.getHeight());
                    bitmapDrawable.draw(canvas3);
                    bitmap2 = bitmapCreateBitmap4;
                }
                bitmap2.setDensity(i5);
                int width = bitmap2.getWidth();
                int height2 = bitmap2.getHeight();
                int iMax = Math.max(width, height2);
                int i23 = 216;
                if (216 < iMax) {
                    float f4 = 216.0f / iMax;
                    width = (int) (width * f4);
                    height2 = (int) (height2 * f4);
                    Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap2, width, height2, true);
                    int iMax2 = Math.max(width, height2);
                    if (bool.booleanValue()) {
                        i19 = 216;
                    } else {
                        i19 = i21;
                        i23 = i22;
                    }
                    Log.i(TAG, "scale down, pkg=" + str2 + ", dr=" + width + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + height2 + ", bg=" + i19 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + i23);
                    i7 = iMax2;
                    i6 = i19;
                    bitmap2 = bitmapCreateScaledBitmap;
                } else {
                    i6 = i21;
                    i23 = i22;
                    i7 = iMax;
                }
                if (semAppIconSolution.mPaint == null) {
                    Paint paint = new Paint();
                    semAppIconSolution.mPaint = paint;
                    bitmap3 = bitmap2;
                    paint.setAntiAlias(true);
                    semAppIconSolution.mPaint.setFilterBitmap(true);
                    i8 = 0;
                    semAppIconSolution.mPaint.setDither(false);
                } else {
                    bitmap3 = bitmap2;
                    i8 = 0;
                }
                if (bool.booleanValue()) {
                    boolean z7 = i8;
                    Bitmap bitmap10 = bitmap3;
                    IconScale appIconAlphaRelativeScale2 = semAppIconSolution.getAppIconAlphaRelativeScale(bitmap10, width, height2, 1.2f, 2);
                    Log.i(TAG, "getIconScale, pkg=" + str2 + ", size=" + Math.max(width, height2) + ", iconScale=" + appIconAlphaRelativeScale2);
                    if (appIconAlphaRelativeScale2.isCrop()) {
                        BitmapFactory.Options options2 = new BitmapFactory.Options();
                        options2.inScaled = z7;
                        Bitmap bitmapDecodeResource2 = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_bg_container_onedot_mask, options2);
                        bitmapDecodeResource2.setDensity(z7 ? 1 : 0);
                        alpha2 = i7 - (appIconAlphaRelativeScale2.getAlpha() * 2);
                        if (alpha2 != bitmapDecodeResource2.getWidth()) {
                            bitmapDecodeResource2 = Bitmap.createScaledBitmap(bitmapDecodeResource2, alpha2, alpha2, true);
                        }
                        bitmapCreateBitmap2 = Bitmap.createBitmap(alpha2, alpha2, Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(bitmapCreateBitmap2);
                        canvas.drawBitmap(bitmapDecodeResource2, 0.0f, 0.0f, semAppIconSolution.mPaint);
                        Log.i(TAG, "default container[CROP], pkg=" + str2 + ", bg=" + alpha2 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + alpha2 + ", dr=" + width + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + height2 + ", isNight = " + configuration.isNightModeActive());
                        bitmap8 = bitmap10;
                        z6 = true;
                    } else {
                        alpha2 = (int) ((i7 - (appIconAlphaRelativeScale2.getAlpha() * 2)) / appIconAlphaRelativeScale2.getScale());
                        if (alpha2 % 2 != 0) {
                            alpha2++;
                        }
                        BitmapFactory.Options options3 = new BitmapFactory.Options();
                        options3.inScaled = false;
                        Bitmap bitmapDecodeResource3 = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_bg_container_onedot, options3);
                        bitmapDecodeResource3.setDensity(0);
                        Bitmap bitmapCreateScaledBitmap2 = Bitmap.createScaledBitmap(bitmapDecodeResource3, alpha2, alpha2, true);
                        Bitmap bitmapCreateBitmap5 = Bitmap.createBitmap(alpha2, alpha2, Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(bitmapCreateBitmap5);
                        bitmap8 = bitmap10;
                        canvas.drawBitmap(bitmapCreateScaledBitmap2, 0.0f, 0.0f, semAppIconSolution.mPaint);
                        Log.i(TAG, "default container[Contain], pkg=" + str2 + ", bg=" + alpha2 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + alpha2 + ", dr=" + width + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + height2 + ", isNight = " + configuration.isNightModeActive());
                        bitmapCreateBitmap2 = bitmapCreateBitmap5;
                        z6 = false;
                    }
                    float f5 = alpha2 / 2.0f;
                    canvas.translate(f5, f5);
                    i12 = i2;
                    z5 = z6;
                    i15 = height2;
                    bitmapCreateBitmap = bitmapCreateBitmap2;
                    i18 = 0;
                    bitmap6 = bitmap8;
                } else {
                    int i24 = i8;
                    if (bool2.booleanValue()) {
                        float f6 = semAppIconSolution.mSamsungThemeAppIconScale;
                        float f7 = (i6 * f6) / width;
                        bitmap4 = bitmap;
                        Log.i(TAG, "fromTheme, pkg=" + str2 + ", bg=" + i6 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + i23 + ", dr=" + width + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + height2 + ", relScale=" + f7 + ", Scale = " + semAppIconSolution.mSamsungThemeAppIconScale + ", isNight = " + configuration.isNightModeActive());
                        f3 = (((float) i23) * f6) / ((float) height2);
                        bitmap6 = bitmap3;
                        i12 = i2;
                        z4 = z;
                        i13 = width;
                        alpha = f7;
                        i15 = height2;
                        i17 = i24;
                    } else {
                        bitmap4 = bitmap;
                        float f8 = semAppIconSolution.mSamsungThemeAppIconScale;
                        if (semAppIconSolution.mSamsungThemeAppIconRange == i3 || z) {
                            str3 = ", iconScale=";
                            str4 = ", isNight = ";
                            i9 = i24;
                            bitmap5 = bitmap3;
                            i10 = i6;
                            str5 = ", size=";
                            z2 = z;
                            str6 = "getIconScale, pkg=";
                            i11 = i23;
                            str7 = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
                            str8 = ", relScale=";
                            appIconAlphaRelativeScale = getAppIconAlphaRelativeScale(bitmap5, width, height2, f8, 1);
                        } else {
                            str3 = ", iconScale=";
                            str4 = ", isNight = ";
                            i9 = i24;
                            bitmap5 = bitmap3;
                            i10 = i6;
                            str5 = ", size=";
                            z2 = z;
                            str6 = "getIconScale, pkg=";
                            i11 = i23;
                            str7 = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
                            str8 = ", relScale=";
                            appIconAlphaRelativeScale = getAppIconAlphaRelativeScale(bitmap5, width, height2, f8, 0);
                        }
                        int i25 = width;
                        Bitmap bitmap11 = bitmap5;
                        int i26 = height2;
                        IconScale iconScale2 = appIconAlphaRelativeScale;
                        Log.i(TAG, str6 + str2 + str5 + Math.max(i25, i26) + str3 + iconScale2);
                        float scale = iconScale2.getScale();
                        if (iconScale2.isCrop()) {
                            i13 = i25;
                            i14 = i26;
                            iconScale = iconScale2;
                            f = f8;
                            semAppIconSolution = this;
                            i12 = i2;
                            bitmap6 = bitmap11;
                            Drawable themeAppIcon2 = semAppIconSolution.getThemeAppIcon(context, packageItemInfo, false, true, i12);
                            if (themeAppIcon2 != null) {
                                if (themeAppIcon2 instanceof BitmapDrawable) {
                                    bitmap7 = ((BitmapDrawable) themeAppIcon2).getBitmap();
                                    f2 = scale;
                                    i16 = i9;
                                } else {
                                    Bitmap bitmapCreateBitmap6 = Bitmap.createBitmap(themeAppIcon2.getIntrinsicWidth(), themeAppIcon2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                                    Canvas canvas4 = new Canvas(bitmapCreateBitmap6);
                                    f2 = scale;
                                    i16 = 0;
                                    themeAppIcon2.setBounds(0, 0, canvas4.getWidth(), canvas4.getHeight());
                                    themeAppIcon2.draw(canvas4);
                                    bitmap7 = bitmapCreateBitmap6;
                                }
                                bitmap7.setDensity(i16);
                                int width2 = bitmap7.getWidth();
                                height = bitmap7.getHeight();
                                bitmap4 = bitmap7;
                                i6 = width2;
                            } else {
                                f2 = scale;
                                i6 = i10;
                                height = i11;
                            }
                            z3 = 1;
                        } else {
                            i12 = i2;
                            i13 = i25;
                            i14 = i26;
                            bitmap6 = bitmap11;
                            iconScale = iconScale2;
                            f = f8;
                            f2 = scale;
                            semAppIconSolution = this;
                            i6 = i10;
                            height = i11;
                            z3 = 0;
                        }
                        alpha = (i6 * f2) / (i7 - (iconScale.getAlpha() * 2));
                        StringBuilder sb2 = new StringBuilder("fromTheme2, pkg=");
                        sb2.append(str2);
                        sb2.append(", bg=");
                        sb2.append(i6);
                        sb2.append(str7);
                        sb2.append(height);
                        sb2.append(", dr=");
                        sb2.append(i13);
                        sb2.append(str7);
                        i15 = i14;
                        sb2.append(i15);
                        sb2.append(", tarScale=");
                        sb2.append(f);
                        sb2.append(str8);
                        sb2.append(alpha);
                        sb2.append(", mask=");
                        sb2.append(semAppIconSolution.mSamsungThemeAppIconMask);
                        sb2.append(", isCropInTheme = ");
                        sb2.append(z3);
                        sb2.append(str4);
                        sb2.append(configuration.isNightModeActive());
                        Log.i(TAG, sb2.toString());
                        f3 = alpha;
                        i23 = height;
                        i17 = z3;
                        z4 = z2;
                    }
                    Bitmap bitmap12 = bitmap4;
                    bitmapCreateBitmap = Bitmap.createBitmap(i6, i23, Bitmap.Config.ARGB_8888);
                    Canvas canvas5 = new Canvas(bitmapCreateBitmap);
                    if (semAppIconSolution.mAppIconPackageName != null && semAppIconSolution.mSamsungThemeAppIconRange <= 1 && !z4) {
                        if (bitmap12 != null) {
                            canvas5.drawBitmap(bitmap12, 0.0f, 0.0f, semAppIconSolution.mPaint);
                        } else {
                            Log.i(TAG, "bgBitmap is null, so can't draw bg.");
                        }
                    }
                    if (i17 != 0) {
                        width = (int) (alpha * i13);
                        i15 = (int) (f3 * i15);
                        canvas5.translate(i6 / 2.0f, i23 / 2.0f);
                        canvas = canvas5;
                        z5 = false;
                        i18 = i17;
                    } else {
                        canvas5.translate(i6 / 2.0f, i23 / 2.0f);
                        canvas5.scale(alpha, f3);
                        width = i13;
                        z5 = false;
                        canvas = canvas5;
                        i18 = i17;
                    }
                }
                if (i18 != 0 && semAppIconSolution.mAppIconPackageName != null && semAppIconSolution.mSamsungThemeAppIconRange <= 1) {
                    Bitmap bitmapCreateScaledBitmap3 = Bitmap.createScaledBitmap(bitmap6, width, i15, true);
                    if (semAppIconSolution.mPaintForCrop == null) {
                        Paint paint2 = new Paint();
                        semAppIconSolution.mPaintForCrop = paint2;
                        paint2.setAntiAlias(true);
                        semAppIconSolution.mPaintForCrop.setFilterBitmap(true);
                        semAppIconSolution.mPaintForCrop.setDither(false);
                        semAppIconSolution.mPaintForCrop.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    }
                    canvas.drawBitmap(bitmapCreateScaledBitmap3, (-width) / 2.0f, (-i15) / 2.0f, semAppIconSolution.mPaintForCrop);
                } else if (z5) {
                    if (semAppIconSolution.mPaintForCrop == null) {
                        Paint paint3 = new Paint();
                        semAppIconSolution.mPaintForCrop = paint3;
                        paint3.setAntiAlias(true);
                        semAppIconSolution.mPaintForCrop.setFilterBitmap(true);
                        semAppIconSolution.mPaintForCrop.setDither(false);
                        semAppIconSolution.mPaintForCrop.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    }
                    canvas.drawBitmap(bitmap6, (-width) / 2.0f, (-i15) / 2.0f, semAppIconSolution.mPaintForCrop);
                } else {
                    canvas.drawBitmap(bitmap6, (-width) / 2.0f, (-i15) / 2.0f, semAppIconSolution.mPaint);
                }
                bitmapDrawable = new BitmapDrawable(Resources.getSystem(), bitmapCreateBitmap);
                if (bool.booleanValue()) {
                    return semAppIconSolution.wrapIconShadowAndNight(context, bitmapDrawable, i12);
                }
            }
        }
        return bitmapDrawable;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009b A[Catch: Exception -> 0x00bb, TryCatch #3 {Exception -> 0x00bb, blocks: (B:29:0x0072, B:41:0x00ac, B:43:0x00b2, B:39:0x0089, B:40:0x009b, B:32:0x007a), top: B:71:0x006a }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b2 A[Catch: Exception -> 0x00bb, TRY_LEAVE, TryCatch #3 {Exception -> 0x00bb, blocks: (B:29:0x0072, B:41:0x00ac, B:43:0x00b2, B:39:0x0089, B:40:0x009b, B:32:0x007a), top: B:71:0x006a }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00fd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x005d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Drawable checkAndDrawLiveIconFromTheme(Context context, PackageItemInfo packageItemInfo, Drawable drawable, boolean z, boolean z2, int i) {
        String str;
        int identifier;
        boolean z3;
        boolean z4;
        String str2;
        String str3;
        int iHashCode;
        char c;
        Resources themePackageResources;
        int i2;
        Resources appIconPackageResources;
        if (packageItemInfo.packageName != null) {
            String str4 = packageItemInfo.packageName;
            str4.hashCode();
            if (str4.equals(CALENDAR_PACKAGE_NAME)) {
                str = "calendar_liveicon_from_theme";
            } else {
                str = !str4.equals(CLOCK_PACKAGE_NAME) ? LIVEICON_BOOLEAN_NAME : "clock_liveicon_from_theme";
            }
            try {
                appIconPackageResources = getAppIconPackageResources(context);
            } catch (Resources.NotFoundException unused) {
                identifier = -1;
            }
            if (appIconPackageResources != null) {
                identifier = appIconPackageResources.getIdentifier(str, TYPE_BOOL, this.mAppIconPackageName);
                try {
                    z3 = appIconPackageResources.getBoolean(identifier);
                } catch (Resources.NotFoundException unused2) {
                    Log.e(TAG, "app icon package doesn't have 'liveicon_from_theme', pkg : " + packageItemInfo.packageName);
                    z3 = false;
                    if (identifier == 0) {
                    }
                    Log.i(TAG, "load= live icon for " + packageItemInfo.packageName + ", from overlay = " + z3);
                    i2 = this.mSamsungThemeAppIconRange;
                    if (i2 != 0) {
                    }
                    return getThemeIconWithBG(context, packageItemInfo, drawable, Boolean.valueOf(z4), Boolean.valueOf(z3), i);
                }
                if (identifier == 0) {
                    try {
                        str2 = this.mThemePackageName;
                        str3 = packageItemInfo.packageName;
                        iHashCode = str3.hashCode();
                        z4 = false;
                    } catch (Exception e) {
                        e = e;
                        z4 = false;
                    }
                    try {
                        if (iHashCode != -1955351778) {
                            c = (iHashCode == 138102030 && str3.equals(CLOCK_PACKAGE_NAME)) ? (char) 1 : (char) 65535;
                            if (c != 0) {
                                str2 = str2 + ".calendar";
                            } else if (c == 1) {
                                str2 = str2 + ".clockpackage";
                            }
                            themePackageResources = getThemePackageResources(context, str2);
                            if (themePackageResources != null) {
                                z3 = themePackageResources.getBoolean(themePackageResources.getIdentifier(LIVEICON_BOOLEAN_NAME, TYPE_BOOL, str2));
                            }
                        } else {
                            if (str3.equals(CALENDAR_PACKAGE_NAME)) {
                                c = 0;
                            }
                            if (c != 0) {
                            }
                            themePackageResources = getThemePackageResources(context, str2);
                            if (themePackageResources != null) {
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        Log.e(TAG, "Failed at get liveicon boolean on overlay pkg : " + packageItemInfo.packageName + ", e : " + e);
                        Log.i(TAG, "load= live icon for " + packageItemInfo.packageName + ", from overlay = " + z3);
                        i2 = this.mSamsungThemeAppIconRange;
                        if (i2 != 0) {
                        }
                        return getThemeIconWithBG(context, packageItemInfo, drawable, Boolean.valueOf(z4), Boolean.valueOf(z3), i);
                    }
                } else {
                    z4 = false;
                }
                Log.i(TAG, "load= live icon for " + packageItemInfo.packageName + ", from overlay = " + z3);
                i2 = this.mSamsungThemeAppIconRange;
                if (i2 != 0 || (!z3 && i2 <= 1)) {
                    return getThemeIconWithBG(context, packageItemInfo, drawable, Boolean.valueOf(z4), Boolean.valueOf(z3), i);
                }
                if (z) {
                    return z2 ? applyNightLayer(context, drawable, i) : wrapIconShadowAndNight(context, drawable, i);
                }
            } else {
                identifier = -1;
                z3 = false;
                if (identifier == 0) {
                }
                Log.i(TAG, "load= live icon for " + packageItemInfo.packageName + ", from overlay = " + z3);
                i2 = this.mSamsungThemeAppIconRange;
                if (i2 != 0) {
                }
                return getThemeIconWithBG(context, packageItemInfo, drawable, Boolean.valueOf(z4), Boolean.valueOf(z3), i);
            }
        }
        return drawable;
    }

    public Drawable applyPrimaryColorToIcon(Context context, Drawable drawable) {
        if (drawable != null) {
            drawable.setTint(context.getResources().getColor(R.color.sem_color_primary_light));
        }
        return drawable;
    }

    public boolean isAppIconThemePackageSet() {
        return this.mAppIconPackageName != null;
    }

    public Drawable getAppIconFromTheme(Context context, PackageItemInfo packageItemInfo, Drawable drawable, int i) {
        String str;
        String str2 = this.mAppIconPackageName;
        if (str2 != null && str2.startsWith(SamsungThemeConstants.PREFIX_THEMEPARK_CATEGORY)) {
            drawable = getThemeParkAppIcon(context, packageItemInfo);
        }
        if (drawable == null || ((str = this.mAppIconPackageName) != null && !str.startsWith(SamsungThemeConstants.PREFIX_THEMEPARK_CATEGORY))) {
            drawable = getThemeAppIcon(context, packageItemInfo, false, i);
        }
        Drawable drawable2 = drawable;
        if (drawable2 == null) {
            return null;
        }
        if (this.mSamsungThemeAppIconRange == 0) {
            return getThemeIconWithBG(context, packageItemInfo, drawable2, false, true, i);
        }
        return applyNightLayer(context, drawable2, i);
    }

    public boolean needToGetLiveIcon(Context context, PackageItemInfo packageItemInfo) {
        String str;
        if (!CALENDAR_PACKAGE_NAME.equals(packageItemInfo.packageName) && !CLOCK_PACKAGE_NAME.equals(packageItemInfo.packageName)) {
            return false;
        }
        String str2 = packageItemInfo.packageName;
        str2.hashCode();
        if (str2.equals(CALENDAR_PACKAGE_NAME)) {
            str = "calendar_liveicon_from_theme";
        } else {
            str = !str2.equals(CLOCK_PACKAGE_NAME) ? LIVEICON_BOOLEAN_NAME : "clock_liveicon_from_theme";
        }
        try {
            Resources appIconPackageResources = getAppIconPackageResources(context);
            boolean z = appIconPackageResources != null ? appIconPackageResources.getBoolean(appIconPackageResources.getIdentifier(str, TYPE_BOOL, this.mAppIconPackageName)) : false;
            if (!z) {
                Log.i(TAG, "app icon package doesn't support live theme icon for " + packageItemInfo.packageName);
            }
            return z;
        } catch (Resources.NotFoundException unused) {
            Log.e(TAG, "app icon package doesn't have 'liveicon_from_theme', pkg : " + packageItemInfo.packageName);
            return false;
        }
    }

    public boolean isCropAppIconUsingBitmap(Bitmap bitmap, int i, int i2) {
        return getAppIconAlphaRelativeScaleForIconTray(bitmap, i, i2, 1.2f, 2).isCrop();
    }

    public float getAppIconAlphaRelativeScaleRateForIconTray(Bitmap bitmap, int i, int i2) {
        return getAppIconAlphaRelativeScaleForIconTray(bitmap, i, i2, 1.2f, 2).mScale;
    }

    public Drawable wrapIconShadowAndNight(Context context, Drawable drawable, int i) {
        return wrapIconShadow(applyNightLayer(context, drawable, i));
    }

    public Drawable applyNightLayer(Context context, Drawable drawable, int i) {
        if (drawable != null) {
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicHeight <= 0) {
                Log.i(TAG, "skip applying night layer bitmap because of abnormal icon size = " + intrinsicHeight);
                return drawable;
            }
            boolean zIsNightModeActive = true;
            boolean z = (i & 64) != 0;
            boolean z2 = (i & 128) != 0;
            Configuration configuration = context.getResources().getConfiguration();
            if (!z2) {
                zIsNightModeActive = z ? false : configuration.isNightModeActive();
            }
            if (drawable instanceof AdaptiveIconDrawable) {
                ((AdaptiveIconDrawable) drawable).setNightModeLayer(zIsNightModeActive);
                return drawable;
            }
            if (zIsNightModeActive) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                canvas.drawPaint(PAINT_FOR_NIGHT_LAYER);
                return new BitmapDrawable(Resources.getSystem(), bitmapCreateBitmap);
            }
        }
        return drawable;
    }

    public Drawable wrapIconShadow(Drawable drawable) {
        if (drawable == null) {
            return drawable;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicHeight <= 0 || intrinsicHeight > 1000) {
            Log.i(TAG, "skip wrapping shadow bitmap because of abnormal icon size = " + intrinsicHeight);
            return drawable;
        }
        return new ShadowDrawable(getShadowBitmap(drawable), drawable);
    }

    private Bitmap getShadowBitmap(Drawable drawable) {
        Path iconMask;
        int intrinsicHeight = drawable.getIntrinsicHeight();
        synchronized (this.mShadowCache) {
            WeakReference<Bitmap> weakReference = this.mShadowCache.get(intrinsicHeight);
            Bitmap bitmap = weakReference != null ? weakReference.get() : null;
            if (bitmap != null) {
                return bitmap;
            }
            if (drawable instanceof AdaptiveIconDrawable) {
                drawable.setBounds(0, 0, intrinsicHeight, intrinsicHeight);
                iconMask = ((AdaptiveIconDrawable) drawable).getIconMask();
            } else {
                AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable((Drawable) null, drawable);
                adaptiveIconDrawable.setBounds(0, 0, intrinsicHeight, intrinsicHeight);
                iconMask = adaptiveIconDrawable.getIconMask();
            }
            float f = intrinsicHeight;
            float f2 = ICON_SIZE_FACTOR_AMBIENT * f;
            float f3 = ICON_SIZE_FACTOR_AMBIENT2 * f;
            int i = (int) (f + (2.0f * f3));
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.translate(f3, f3);
            Paint paint = new Paint(1);
            paint.setColor(0);
            paint.setShadowLayer(f2, 0.0f, 0.0f, Enums.AUDIO_FORMAT_LHDC_LL);
            canvas.drawPath(iconMask, paint);
            paint.setShadowLayer(f3, 0.0f, 0.0f, Enums.AUDIO_FORMAT_DSD);
            canvas.drawPath(iconMask, paint);
            canvas.setBitmap(null);
            synchronized (this.mShadowCache) {
                this.mShadowCache.put(intrinsicHeight, new WeakReference<>(bitmapCreateBitmap));
            }
            return bitmapCreateBitmap;
        }
    }

    public static class ShadowDrawable extends DrawableWrapper {
        final MyConstantState mState;

        public ShadowDrawable(Bitmap bitmap, Drawable drawable) {
            super(drawable);
            this.mState = new MyConstantState(bitmap, drawable.getConstantState());
        }

        ShadowDrawable(MyConstantState myConstantState) {
            super(myConstantState.mChildState.newDrawable());
            this.mState = myConstantState;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            canvas.drawBitmap(this.mState.mShadow, (Rect) null, getBounds(), this.mState.mPaint);
            canvas.save();
            canvas.translate(r0.width() * 0.96000004f * SemAppIconSolution.ICON_SIZE_FACTOR_AMBIENT2, r0.height() * 0.96000004f * SemAppIconSolution.ICON_SIZE_FACTOR_AMBIENT2);
            canvas.scale(0.96000004f, 0.96000004f);
            super.draw(canvas);
            canvas.restore();
        }

        private static class MyConstantState extends Drawable.ConstantState {
            final Drawable.ConstantState mChildState;
            final Paint mPaint = new Paint(2);
            final Bitmap mShadow;

            MyConstantState(Bitmap bitmap, Drawable.ConstantState constantState) {
                this.mShadow = bitmap;
                this.mChildState = constantState;
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

    public Drawable getColorThemeIcon(Context context, Drawable drawable, String str, int i) {
        Drawable drawable2;
        Resources resourcesForApplicationAsUser;
        int identifier;
        boolean z = (i & 4) != 0;
        boolean z2 = (i & 8) != 0;
        if (drawable instanceof AdaptiveIconDrawable) {
            if (z2) {
                drawable2 = ((AdaptiveIconDrawable) drawable).getForeground();
            } else {
                drawable2 = ((AdaptiveIconDrawable) drawable).getMonochrome();
            }
        } else if (!z || str == null) {
            drawable2 = null;
        } else {
            try {
                resourcesForApplicationAsUser = context.getPackageManager().getResourcesForApplicationAsUser(str, 0);
                identifier = resourcesForApplicationAsUser.getIdentifier("sep_monochrome_icon", TYPE_DRAWABLE, str);
            } catch (Exception e) {
                Log.w(TAG, "Failed to find monochrome, Pkg=" + str + ", Exception=" + e.toString());
            }
            if (identifier != 0) {
                drawable2 = resourcesForApplicationAsUser.getDrawable(identifier);
            } else {
                Log.w(TAG, "Monochrome image is not existed, Pkg=" + str);
                drawable2 = null;
            }
        }
        if (drawable2 == null) {
            return null;
        }
        Drawable drawableMutate = drawable2.mutate();
        int[] colorsForIcon = getColorsForIcon(context);
        if (!z2) {
            drawableMutate.setTint(colorsForIcon[1]);
        }
        Log.i(TAG, "ColorTheme icon has returned, color = #" + Integer.toHexString(colorsForIcon[0]) + ", isNoAdaptive = " + z + ", isOnlyBG = " + z2);
        return new AdaptiveIconDrawable(new ColorDrawable(colorsForIcon[0]), drawableMutate);
    }

    private int[] getColorsForIcon(Context context) {
        Resources resources = context.getResources();
        int[] iArr = new int[2];
        if (resources.getConfiguration().isNightModeActive()) {
            iArr[0] = resources.getColor(17170494);
            iArr[1] = Color.parseColor("#ff000000");
            return iArr;
        }
        iArr[0] = resources.getColor(17170493);
        iArr[1] = Color.parseColor("#ffffffff");
        return iArr;
    }
}
