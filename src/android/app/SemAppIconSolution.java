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

    private SemAppIconSolution(Context context) {
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
        SemAppIconSolution semAppIconSolution;
        synchronized (SemAppIconSolution.class) {
            if (sUniqueInstance == null) {
                sUniqueInstance = new SemAppIconSolution(context);
            }
            semAppIconSolution = sUniqueInstance;
        }
        return semAppIconSolution;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ac A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int checkAppIconThemePackage(android.content.Context r9) {
        /*
            r8 = this;
            java.lang.String r0 = "AppIconSolution"
            java.lang.String r1 = ""
            android.content.ContentResolver r2 = r9.getContentResolver()     // Catch: java.lang.SecurityException -> L1b
            java.lang.String r3 = "current_sec_active_themepackage"
            java.lang.String r2 = android.provider.Settings.System.getString(r2, r3)     // Catch: java.lang.SecurityException -> L1b
            android.content.ContentResolver r3 = r9.getContentResolver()     // Catch: java.lang.SecurityException -> L19
            java.lang.String r4 = "current_sec_appicon_theme_package"
            java.lang.String r3 = android.provider.Settings.System.getString(r3, r4)     // Catch: java.lang.SecurityException -> L19
            goto L2f
        L19:
            r3 = move-exception
            goto L1d
        L1b:
            r3 = move-exception
            r2 = r1
        L1d:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "couldn't access setting property, just keep appIconPackageName empty, ex = "
            r4.<init>(r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            android.util.Log.i(r0, r3)
            r3 = r1
        L2f:
            boolean r1 = r1.equals(r3)
            r4 = 0
            if (r1 == 0) goto L37
            r3 = r4
        L37:
            boolean r1 = r8.mIgnoreAppIconThemeHost
            r5 = 1
            r6 = 0
            if (r1 == 0) goto L49
            if (r3 == 0) goto L49
            java.util.ArrayList<java.lang.String> r1 = android.content.om.SamsungThemeConstants.ignoreAppIconThemeList
            boolean r1 = r1.contains(r3)
            if (r1 == 0) goto L49
            r1 = r5
            goto L4b
        L49:
            r4 = r3
            r1 = r6
        L4b:
            java.lang.String r3 = r8.mAppIconPackageName
            r7 = 3
            if (r3 == 0) goto L56
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L65
        L56:
            if (r4 == 0) goto L60
            java.lang.String r3 = r8.mAppIconPackageName
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L65
        L60:
            int r3 = r8.mSamsungThemeAppIconRange
            if (r3 == r7) goto L65
            goto L6d
        L65:
            android.app.ApplicationPackageManager.configurationChanged()
            r8.mAppIconPackageName = r4
            r8.registerAppIconInfo(r9)
        L6d:
            java.lang.String r3 = r8.mThemePackageName
            if (r3 == 0) goto L77
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L81
        L77:
            if (r2 == 0) goto L83
            java.lang.String r3 = r8.mThemePackageName
            boolean r3 = r2.equals(r3)
            if (r3 != 0) goto L83
        L81:
            r8.mThemePackageName = r2
        L83:
            java.lang.String r2 = r8.mAppIconPackageName
            if (r2 != 0) goto La7
            android.content.ContentResolver r2 = r9.getContentResolver()     // Catch: java.lang.SecurityException -> L95
            java.lang.String r3 = "colortheme_app_icon"
            int r0 = android.provider.Settings.Global.getInt(r2, r3, r6)     // Catch: java.lang.SecurityException -> L95
            if (r0 != r5) goto La7
            r0 = r5
            goto La8
        L95:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "couldn't access setting property, just keep colortheme icon disabled, ex = "
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            android.util.Log.i(r0, r2)
        La7:
            r0 = r6
        La8:
            java.lang.String r8 = r8.mAppIconPackageName
            if (r8 == 0) goto Lad
            return r6
        Lad:
            if (r0 == 0) goto Lb8
            int r8 = r9.getUserId()
            r9 = 77
            if (r8 == r9) goto Lb8
            return r7
        Lb8:
            if (r1 == 0) goto Lbc
            r8 = 2
            return r8
        Lbc:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.SemAppIconSolution.checkAppIconThemePackage(android.content.Context):int");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:27:0x0088
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
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
        String sb2 = sb.toString();
        if (!new File(sb2).exists()) {
            sb2 = SamsungThemeConstants.PATH_THEMEPARK_ICON + str2 + ".png";
            if (!new File(sb2).exists()) {
                return null;
            }
        }
        try {
            return new BitmapDrawable(context.getResources(), BitmapFactory.decodeFile(sb2));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    private Drawable getThemeAppIcon(Context context, PackageItemInfo packageItemInfo, boolean z, int i) {
        return getThemeAppIcon(context, packageItemInfo, z, false, i);
    }

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
            } else {
                if (packageItemInfo != null) {
                    if (packageItemInfo.name != null) {
                        String str3 = themeAppIconMap.get(packageItemInfo.name);
                        str = (str3 == null && (packageItemInfo instanceof ApplicationInfo)) ? themeAppIconMap.get(packageItemInfo.packageName) : str3;
                    } else if (packageItemInfo.packageName != null) {
                        str = themeAppIconMap.get(packageItemInfo.packageName);
                    }
                }
                str = null;
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
        Resources resources;
        if ((i & 256) != 0) {
            try {
                try {
                    resources = context.getPackageManager().getResourcesForApplicationAsUser("com.android.systemui", 0);
                } catch (PackageManager.NameNotFoundException unused) {
                    resources = null;
                }
            } catch (Exception e) {
                Log.e(TAG, str2 + ", Failed to get LockScreen Shorcut Icon=" + str + ", Exception=" + e.toString());
            }
            if (resources == null) {
                Log.e(TAG, "SystemUI package doesn't have resources");
                return null;
            }
            int identifier = resources.getIdentifier(str, TYPE_DRAWABLE, "com.android.systemui");
            if (identifier != 0) {
                return resources.getDrawable(identifier);
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
                if (aPKContents != null && aPKContents.getResources() != null) {
                    Log.e(TAG, "Using cached contents available for " + this.mAppIconPackageName);
                    return aPKContents.getResources();
                }
                aPKContents = new APKContents(currentThemePackagePath);
                this.mCachedAPKContents = new Pair<>(currentThemePackagePath, aPKContents);
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
        int min = Math.min(i, i2) / 2;
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
            if (min <= i8 || i7 != -1) {
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
        int min = Math.min(i, i2) / 2;
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
            if (min <= i11 || i10 != -1) {
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
    public Drawable getThemeIconWithBG(Context context, PackageItemInfo packageItemInfo, Drawable drawable, Boolean bool, Boolean bool2, int i, String str, int i2) {
        String str2;
        int i3;
        int i4;
        int i5;
        Bitmap bitmap;
        int i6;
        boolean z;
        int i7;
        Bitmap bitmap2;
        int i8;
        int i9;
        Bitmap bitmap3;
        int i10;
        Bitmap bitmap4;
        String str3;
        String str4;
        int i11;
        Bitmap bitmap5;
        int i12;
        String str5;
        boolean z2;
        String str6;
        int i13;
        String str7;
        String str8;
        IconScale appIconAlphaRelativeScale;
        int i14;
        int i15;
        int i16;
        Bitmap bitmap6;
        IconScale iconScale;
        float f;
        float f2;
        int i17;
        boolean z3;
        float alpha;
        int i18;
        float f3;
        int i19;
        Bitmap bitmap7;
        boolean z4;
        int i20;
        Bitmap createBitmap;
        boolean z5;
        Canvas canvas;
        int i21;
        int alpha2;
        Bitmap bitmap8;
        Bitmap bitmap9;
        boolean z6;
        int i22;
        Bitmap bitmap10;
        SemAppIconSolution semAppIconSolution = this;
        Drawable drawable2 = drawable;
        if (packageItemInfo != null) {
            if (packageItemInfo.packageName != null && !SamsungThemeConstants.PACKAGE_NAME_FOR_SKIP_THEME_APPICON.equals(packageItemInfo.packageName)) {
                str2 = packageItemInfo.packageName;
            }
            return drawable2;
        }
        str2 = str;
        Configuration configuration = context.getResources().getConfiguration();
        if (bool.booleanValue()) {
            if (drawable2 instanceof AdaptiveIconDrawable) {
                Log.i(TAG, "return adaptive icon for " + str2 + ", isNight = " + configuration.isNightModeActive());
                return semAppIconSolution.wrapIconShadowAndNight(context, drawable2, i2);
            }
        } else if (semAppIconSolution.mSamsungThemeAppIconRange == 2) {
            Log.i(TAG, "return the original icon because tray option is set to None for " + str2 + ", isNight = " + configuration.isNightModeActive());
            return semAppIconSolution.applyNightLayer(context, drawable2, i2);
        }
        if (bool.booleanValue() || (semAppIconSolution.mAppIconPackageName != null && semAppIconSolution.mSamsungThemeAppIconRange >= 2)) {
            i3 = 2;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inScaled = false;
            Resources system = Resources.getSystem();
            i4 = R.drawable.ic_bg_container_onedot;
            Bitmap decodeResource = BitmapFactory.decodeResource(system, R.drawable.ic_bg_container_onedot, options);
            int i23 = options.outWidth;
            i5 = options.outHeight;
            bitmap = decodeResource;
            i6 = i23;
        } else {
            Drawable themeAppIcon = semAppIconSolution.getThemeAppIcon(context, packageItemInfo, true, i2);
            if (themeAppIcon != null) {
                if (themeAppIcon instanceof BitmapDrawable) {
                    bitmap10 = ((BitmapDrawable) themeAppIcon).getBitmap();
                    i3 = 2;
                } else {
                    i3 = 2;
                    Bitmap createBitmap2 = Bitmap.createBitmap(themeAppIcon.getIntrinsicWidth(), themeAppIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(createBitmap2);
                    themeAppIcon.setBounds(0, 0, canvas2.getWidth(), canvas2.getHeight());
                    themeAppIcon.draw(canvas2);
                    bitmap10 = createBitmap2;
                }
                bitmap10.setDensity(0);
                i6 = bitmap10.getWidth();
                bitmap = bitmap10;
                i5 = bitmap10.getHeight();
            } else {
                i3 = 2;
                i6 = -1;
                bitmap = null;
                i5 = -1;
            }
            i4 = R.drawable.ic_bg_container_onedot;
        }
        if (i6 >= 0 || semAppIconSolution.mAppIconPackageName == null || semAppIconSolution.mSamsungThemeAppIconRange > 1) {
            z = false;
        } else {
            Drawable drawable3 = Resources.getSystem().getDrawable(i4);
            i6 = drawable3.getIntrinsicWidth();
            i5 = drawable3.getIntrinsicHeight();
            z = true;
        }
        if (i6 > 0 && i5 > 0) {
            int intrinsicWidth = drawable2.getIntrinsicWidth();
            int intrinsicHeight = drawable2.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                StringBuilder sb = new StringBuilder("start to load, pkg=");
                sb.append(str2);
                sb.append(", bg=");
                sb.append(i6);
                int i24 = i6;
                sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                sb.append(i5);
                sb.append(", dr=");
                sb.append(intrinsicWidth);
                sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                sb.append(intrinsicHeight);
                int i25 = i5;
                sb.append(", forDefault=");
                sb.append(bool);
                sb.append(", density=");
                sb.append(i);
                Log.i(TAG, sb.toString());
                if (drawable2 instanceof BitmapDrawable) {
                    bitmap2 = ((BitmapDrawable) drawable2).getBitmap();
                    i7 = 0;
                } else {
                    Bitmap createBitmap3 = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                    Canvas canvas3 = new Canvas(createBitmap3);
                    i7 = 0;
                    drawable2.setBounds(0, 0, canvas3.getWidth(), canvas3.getHeight());
                    drawable2.draw(canvas3);
                    bitmap2 = createBitmap3;
                }
                bitmap2.setDensity(i7);
                int width = bitmap2.getWidth();
                int height = bitmap2.getHeight();
                int max = Math.max(width, height);
                int i26 = 216;
                if (216 < max) {
                    float f4 = 216.0f / max;
                    width = (int) (width * f4);
                    height = (int) (height * f4);
                    Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap2, width, height, true);
                    int max2 = Math.max(width, height);
                    if (bool.booleanValue()) {
                        i22 = 216;
                    } else {
                        i22 = i24;
                        i26 = i25;
                    }
                    Log.i(TAG, "scale down, pkg=" + str2 + ", dr=" + width + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + height + ", bg=" + i22 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + i26);
                    i9 = max2;
                    i8 = i22;
                    bitmap2 = createScaledBitmap;
                } else {
                    i8 = i24;
                    i26 = i25;
                    i9 = max;
                }
                if (semAppIconSolution.mPaint == null) {
                    Paint paint = new Paint();
                    semAppIconSolution.mPaint = paint;
                    bitmap3 = bitmap2;
                    paint.setAntiAlias(true);
                    semAppIconSolution.mPaint.setFilterBitmap(true);
                    i10 = 0;
                    semAppIconSolution.mPaint.setDither(false);
                } else {
                    bitmap3 = bitmap2;
                    i10 = 0;
                }
                if (bool.booleanValue()) {
                    boolean z7 = i10;
                    Bitmap bitmap11 = bitmap3;
                    IconScale appIconAlphaRelativeScale2 = semAppIconSolution.getAppIconAlphaRelativeScale(bitmap11, width, height, 1.2f, 2);
                    Log.i(TAG, "getIconScale, pkg=" + str2 + ", size=" + Math.max(width, height) + ", iconScale=" + appIconAlphaRelativeScale2);
                    if (appIconAlphaRelativeScale2.isCrop()) {
                        BitmapFactory.Options options2 = new BitmapFactory.Options();
                        options2.inScaled = z7;
                        Bitmap decodeResource2 = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_bg_container_onedot_mask, options2);
                        decodeResource2.setDensity(z7 ? 1 : 0);
                        alpha2 = i9 - (appIconAlphaRelativeScale2.getAlpha() * 2);
                        if (alpha2 != decodeResource2.getWidth()) {
                            decodeResource2 = Bitmap.createScaledBitmap(decodeResource2, alpha2, alpha2, true);
                        }
                        bitmap9 = Bitmap.createBitmap(alpha2, alpha2, Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(bitmap9);
                        canvas.drawBitmap(decodeResource2, 0.0f, 0.0f, semAppIconSolution.mPaint);
                        Log.i(TAG, "default container[CROP], pkg=" + str2 + ", bg=" + alpha2 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + alpha2 + ", dr=" + width + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + height + ", isNight = " + configuration.isNightModeActive());
                        bitmap8 = bitmap11;
                        z6 = true;
                    } else {
                        alpha2 = (int) ((i9 - (appIconAlphaRelativeScale2.getAlpha() * 2)) / appIconAlphaRelativeScale2.getScale());
                        if (alpha2 % 2 != 0) {
                            alpha2++;
                        }
                        BitmapFactory.Options options3 = new BitmapFactory.Options();
                        options3.inScaled = false;
                        Bitmap decodeResource3 = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_bg_container_onedot, options3);
                        decodeResource3.setDensity(0);
                        Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(decodeResource3, alpha2, alpha2, true);
                        Bitmap createBitmap4 = Bitmap.createBitmap(alpha2, alpha2, Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(createBitmap4);
                        bitmap8 = bitmap11;
                        canvas.drawBitmap(createScaledBitmap2, 0.0f, 0.0f, semAppIconSolution.mPaint);
                        Log.i(TAG, "default container[Contain], pkg=" + str2 + ", bg=" + alpha2 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + alpha2 + ", dr=" + width + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + height + ", isNight = " + configuration.isNightModeActive());
                        bitmap9 = createBitmap4;
                        z6 = false;
                    }
                    float f5 = alpha2 / 2.0f;
                    canvas.translate(f5, f5);
                    i14 = i2;
                    z5 = z6;
                    i18 = height;
                    createBitmap = bitmap9;
                    i21 = 0;
                    bitmap6 = bitmap8;
                } else {
                    int i27 = i10;
                    if (bool2.booleanValue()) {
                        float f6 = semAppIconSolution.mSamsungThemeAppIconScale;
                        float f7 = (i8 * f6) / width;
                        bitmap4 = bitmap;
                        Log.i(TAG, "fromTheme, pkg=" + str2 + ", bg=" + i8 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + i26 + ", dr=" + width + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + height + ", relScale=" + f7 + ", Scale = " + semAppIconSolution.mSamsungThemeAppIconScale + ", isNight = " + configuration.isNightModeActive());
                        f3 = (((float) i26) * f6) / ((float) height);
                        bitmap6 = bitmap3;
                        i14 = i2;
                        z4 = z;
                        i15 = width;
                        alpha = f7;
                        i18 = height;
                        i20 = i27;
                    } else {
                        bitmap4 = bitmap;
                        float f8 = semAppIconSolution.mSamsungThemeAppIconScale;
                        if (semAppIconSolution.mSamsungThemeAppIconRange == i3 || z) {
                            str3 = ", iconScale=";
                            str4 = ", isNight = ";
                            i11 = i27;
                            bitmap5 = bitmap3;
                            i12 = i8;
                            str5 = ", size=";
                            z2 = z;
                            str6 = "getIconScale, pkg=";
                            i13 = i26;
                            str7 = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
                            str8 = ", relScale=";
                            appIconAlphaRelativeScale = getAppIconAlphaRelativeScale(bitmap5, width, height, f8, 1);
                        } else {
                            str3 = ", iconScale=";
                            str4 = ", isNight = ";
                            i11 = i27;
                            bitmap5 = bitmap3;
                            i12 = i8;
                            str5 = ", size=";
                            z2 = z;
                            str6 = "getIconScale, pkg=";
                            i13 = i26;
                            str7 = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
                            str8 = ", relScale=";
                            appIconAlphaRelativeScale = getAppIconAlphaRelativeScale(bitmap5, width, height, f8, 0);
                        }
                        int i28 = width;
                        Bitmap bitmap12 = bitmap5;
                        int i29 = height;
                        IconScale iconScale2 = appIconAlphaRelativeScale;
                        Log.i(TAG, str6 + str2 + str5 + Math.max(i28, i29) + str3 + iconScale2);
                        float scale = iconScale2.getScale();
                        if (iconScale2.isCrop()) {
                            i15 = i28;
                            i16 = i29;
                            iconScale = iconScale2;
                            f = f8;
                            semAppIconSolution = this;
                            i14 = i2;
                            bitmap6 = bitmap12;
                            Drawable themeAppIcon2 = semAppIconSolution.getThemeAppIcon(context, packageItemInfo, false, true, i14);
                            if (themeAppIcon2 != null) {
                                if (themeAppIcon2 instanceof BitmapDrawable) {
                                    bitmap7 = ((BitmapDrawable) themeAppIcon2).getBitmap();
                                    f2 = scale;
                                    i19 = i11;
                                } else {
                                    Bitmap createBitmap5 = Bitmap.createBitmap(themeAppIcon2.getIntrinsicWidth(), themeAppIcon2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                                    Canvas canvas4 = new Canvas(createBitmap5);
                                    f2 = scale;
                                    i19 = 0;
                                    themeAppIcon2.setBounds(0, 0, canvas4.getWidth(), canvas4.getHeight());
                                    themeAppIcon2.draw(canvas4);
                                    bitmap7 = createBitmap5;
                                }
                                bitmap7.setDensity(i19);
                                int width2 = bitmap7.getWidth();
                                i17 = bitmap7.getHeight();
                                bitmap4 = bitmap7;
                                i8 = width2;
                            } else {
                                f2 = scale;
                                i8 = i12;
                                i17 = i13;
                            }
                            z3 = 1;
                        } else {
                            i14 = i2;
                            i15 = i28;
                            i16 = i29;
                            bitmap6 = bitmap12;
                            iconScale = iconScale2;
                            f = f8;
                            f2 = scale;
                            semAppIconSolution = this;
                            i8 = i12;
                            i17 = i13;
                            z3 = 0;
                        }
                        alpha = (i8 * f2) / (i9 - (iconScale.getAlpha() * 2));
                        StringBuilder sb2 = new StringBuilder("fromTheme2, pkg=");
                        sb2.append(str2);
                        sb2.append(", bg=");
                        sb2.append(i8);
                        sb2.append(str7);
                        sb2.append(i17);
                        sb2.append(", dr=");
                        sb2.append(i15);
                        sb2.append(str7);
                        i18 = i16;
                        sb2.append(i18);
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
                        i26 = i17;
                        i20 = z3;
                        z4 = z2;
                    }
                    Bitmap bitmap13 = bitmap4;
                    createBitmap = Bitmap.createBitmap(i8, i26, Bitmap.Config.ARGB_8888);
                    Canvas canvas5 = new Canvas(createBitmap);
                    if (semAppIconSolution.mAppIconPackageName != null && semAppIconSolution.mSamsungThemeAppIconRange <= 1 && !z4) {
                        if (bitmap13 != null) {
                            canvas5.drawBitmap(bitmap13, 0.0f, 0.0f, semAppIconSolution.mPaint);
                        } else {
                            Log.i(TAG, "bgBitmap is null, so can't draw bg.");
                        }
                    }
                    if (i20 != 0) {
                        width = (int) (alpha * i15);
                        i18 = (int) (f3 * i18);
                        canvas5.translate(i8 / 2.0f, i26 / 2.0f);
                        canvas = canvas5;
                        z5 = false;
                        i21 = i20;
                    } else {
                        canvas5.translate(i8 / 2.0f, i26 / 2.0f);
                        canvas5.scale(alpha, f3);
                        width = i15;
                        z5 = false;
                        canvas = canvas5;
                        i21 = i20;
                    }
                }
                if (i21 != 0 && semAppIconSolution.mAppIconPackageName != null && semAppIconSolution.mSamsungThemeAppIconRange <= 1) {
                    Bitmap createScaledBitmap3 = Bitmap.createScaledBitmap(bitmap6, width, i18, true);
                    if (semAppIconSolution.mPaintForCrop == null) {
                        Paint paint2 = new Paint();
                        semAppIconSolution.mPaintForCrop = paint2;
                        paint2.setAntiAlias(true);
                        semAppIconSolution.mPaintForCrop.setFilterBitmap(true);
                        semAppIconSolution.mPaintForCrop.setDither(false);
                        semAppIconSolution.mPaintForCrop.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    }
                    canvas.drawBitmap(createScaledBitmap3, (-width) / 2.0f, (-i18) / 2.0f, semAppIconSolution.mPaintForCrop);
                } else if (z5) {
                    if (semAppIconSolution.mPaintForCrop == null) {
                        Paint paint3 = new Paint();
                        semAppIconSolution.mPaintForCrop = paint3;
                        paint3.setAntiAlias(true);
                        semAppIconSolution.mPaintForCrop.setFilterBitmap(true);
                        semAppIconSolution.mPaintForCrop.setDither(false);
                        semAppIconSolution.mPaintForCrop.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    }
                    canvas.drawBitmap(bitmap6, (-width) / 2.0f, (-i18) / 2.0f, semAppIconSolution.mPaintForCrop);
                } else {
                    canvas.drawBitmap(bitmap6, (-width) / 2.0f, (-i18) / 2.0f, semAppIconSolution.mPaint);
                }
                drawable2 = new BitmapDrawable(Resources.getSystem(), createBitmap);
                if (bool.booleanValue()) {
                    return semAppIconSolution.wrapIconShadowAndNight(context, drawable2, i14);
                }
            }
        }
        return drawable2;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00fd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b2 A[Catch: Exception -> 0x00bb, TRY_LEAVE, TryCatch #3 {Exception -> 0x00bb, blocks: (B:37:0x0072, B:43:0x00ac, B:45:0x00b2, B:47:0x0089, B:48:0x009b, B:50:0x007a), top: B:33:0x006a }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x009b A[Catch: Exception -> 0x00bb, TryCatch #3 {Exception -> 0x00bb, blocks: (B:37:0x0072, B:43:0x00ac, B:45:0x00b2, B:47:0x0089, B:48:0x009b, B:50:0x007a), top: B:33:0x006a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.drawable.Drawable checkAndDrawLiveIconFromTheme(android.content.Context r18, android.content.pm.PackageItemInfo r19, android.graphics.drawable.Drawable r20, boolean r21, boolean r22, int r23) {
        /*
            Method dump skipped, instructions count: 286
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.SemAppIconSolution.checkAndDrawLiveIconFromTheme(android.content.Context, android.content.pm.PackageItemInfo, android.graphics.drawable.Drawable, boolean, boolean, int):android.graphics.drawable.Drawable");
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
            boolean z = true;
            boolean z2 = (i & 64) != 0;
            boolean z3 = (i & 128) != 0;
            Configuration configuration = context.getResources().getConfiguration();
            if (!z3) {
                z = z2 ? false : configuration.isNightModeActive();
            }
            if (drawable instanceof AdaptiveIconDrawable) {
                ((AdaptiveIconDrawable) drawable).setNightModeLayer(z);
                return drawable;
            }
            if (z) {
                Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                canvas.drawPaint(PAINT_FOR_NIGHT_LAYER);
                return new BitmapDrawable(Resources.getSystem(), createBitmap);
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
            Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            canvas.translate(f3, f3);
            Paint paint = new Paint(1);
            paint.setColor(0);
            paint.setShadowLayer(f2, 0.0f, 0.0f, Enums.AUDIO_FORMAT_LHDC_LL);
            canvas.drawPath(iconMask, paint);
            paint.setShadowLayer(f3, 0.0f, 0.0f, Enums.AUDIO_FORMAT_DSD);
            canvas.drawPath(iconMask, paint);
            canvas.setBitmap(null);
            synchronized (this.mShadowCache) {
                this.mShadowCache.put(intrinsicHeight, new WeakReference<>(createBitmap));
            }
            return createBitmap;
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
        boolean z = (i & 4) != 0;
        boolean z2 = (i & 8) != 0;
        if (!(drawable instanceof AdaptiveIconDrawable)) {
            if (z && str != null) {
                try {
                    Resources resourcesForApplicationAsUser = context.getPackageManager().getResourcesForApplicationAsUser(str, 0);
                    int identifier = resourcesForApplicationAsUser.getIdentifier("sep_monochrome_icon", TYPE_DRAWABLE, str);
                    if (identifier != 0) {
                        drawable2 = resourcesForApplicationAsUser.getDrawable(identifier);
                    } else {
                        Log.w(TAG, "Monochrome image is not existed, Pkg=" + str);
                    }
                } catch (Exception e) {
                    Log.w(TAG, "Failed to find monochrome, Pkg=" + str + ", Exception=" + e.toString());
                }
            }
            drawable2 = null;
        } else if (z2) {
            drawable2 = ((AdaptiveIconDrawable) drawable).getForeground();
        } else {
            drawable2 = ((AdaptiveIconDrawable) drawable).getMonochrome();
        }
        if (drawable2 == null) {
            return null;
        }
        Drawable mutate = drawable2.mutate();
        int[] colorsForIcon = getColorsForIcon(context);
        if (!z2) {
            mutate.setTint(colorsForIcon[1]);
        }
        Log.i(TAG, "ColorTheme icon has returned, color = #" + Integer.toHexString(colorsForIcon[0]) + ", isNoAdaptive = " + z + ", isOnlyBG = " + z2);
        return new AdaptiveIconDrawable(new ColorDrawable(colorsForIcon[0]), mutate);
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
