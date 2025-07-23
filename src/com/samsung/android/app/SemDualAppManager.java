package com.samsung.android.app;

import android.appwidget.AppWidgetHostView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Base64;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.R;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.samsung.android.app.ISemDualAppManager;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemDualAppManager {
    private static final String ACTION3_PACKAGE_NAME;
    private static final String ADW_PACKAGE_NAME;
    private static final String[] AFW_CAPABLE_LAUNCHER_APPS;
    private static final String BLACKBERRYMESSENGER_PACKAGE_NAME;
    private static final String[] CHINA_SALES_CODES;
    public static final String DA_PROFILE_ID_PROPERTY_NAME = "sys.dualapp.profile_id";
    private static final String DCM_LIVEUX_PACKAGE_NAME;
    static final String[] DUAL_APP_WHITELIST_PACKAGES;
    static final String[] DUAL_APP_WHITELIST_PACKAGES_FOR_CHINA;
    public static final String DUAL_CALLER_PACKAGE_NAME = "callerPackage";
    public static final String DUAL_ORI_SHORTCUT_COMPONENT = "dual_shortcut_component";
    private static final String FACEBOOKMESSENGER_PACKAGE_NAME;
    private static final String FACEBOOK_PACKAGE_NAME;
    private static final String GOOGLE_QUICKSEARCHBOX_PACKGE_NAME;
    private static final String HIKE_PACKAGE_NAME;
    private static final String HOLO_PACKAGE_NAME;
    private static final String ICQ_PACKAGE_NAME;
    private static final String KAKAOTALK_PACKAGE_NAME;
    private static final String KAKAOTALK_SETTINGS_THEME_URI = "kakaotalk://settings/theme/";
    private static final String KIK_PACKAGE_NAME;
    private static final String LINE_PACKAGE_NAME;
    public static final int MAX_DUALAPP_ID = 99;
    private static final String MICROSOFT_PACKAGE_NAME;
    public static final int MIN_DUALAPP_ID = 95;
    private static final String NOUGAT_PACKAGE_NAME;
    private static final String NOVA_PACKAGE_NAME;
    private static final String QQMOBILECHINA_PACKAGE_NAME;
    private static final String QQMOBILEINTERNATIONAL_PACKAGE_NAME;
    private static final String[] SAMSUNG_LAUNCHER_APPS;
    private static final String SEC_DESKTOP_LAUNCHER_PACKGE_NAME;
    private static final String SEC_EASY_LAUNCHER_PACKGE_NAME;
    private static final String SEC_EMERGENCY_LAUNCHER_PACKGE_NAME;
    private static final String SEC_LAUNCHER_PACKGE_NAME;
    private static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_DUAL_APP = true;
    private static final String SKYPE_PACKAGE_NAME;
    private static final String SMART3_PACKAGE_NAME;
    private static final String SNAPCHAT_PACKAGE_NAME;
    private static final String TAG = "SemDualAppManager";
    private static final String TELEGRAM_PACKAGE_NAME;
    private static final String VIBER_PACKAGE_NAME;
    private static final String WECHAT_PACKAGE_NAME;
    private static final String WEIBO_PACKAGE_NAME;
    private static final String WHATSAPP_PACKAGE_NAME;
    private static final String YAHOOMESSENGER_PACKAGE_NAME;
    private static final String YANDEX_PACKAGE_NAME;
    private static final String ZALO_PACKAGE_NAME;
    private static boolean mIsChinaModel;
    private static String mSalesCode;
    private static ISemDualAppManager mService;
    private static SemDualAppManager sDAInstance;
    private Map<ComponentName, Integer> mDuplicateInitialIntents = new HashMap();

    public interface DualAppVersion {
        public static final int DUAL_APP_VERSION_1_0_0 = 100;
        public static final int DUAL_APP_VERSION_1_1_0 = 110;
        public static final int DUAL_APP_VERSION_2_0_0 = 200;
        public static final int DUAL_APP_VERSION_3_0_0 = 300;
        public static final int DUAL_APP_VERSION_3_1_0 = 310;
        public static final int DUAL_APP_VERSION_3_2_0 = 320;
        public static final int DUAL_APP_VERSION_3_3_0 = 330;
        public static final int DUAL_APP_VERSION_3_4_0 = 340;
        public static final int DUAL_APP_VERSION_3_5_0 = 350;
        public static final int DUAL_APP_VERSION_3_6_0 = 360;
        public static final int DUAL_APP_VERSION_NONE = 0;
    }

    private interface SepVersionInt {
        public static final int SEP_VER_10_0_INT = 100000;
        public static final int SEP_VER_11_0_INT = 110000;
        public static final int SEP_VER_12_0_INT = 120000;
        public static final int SEP_VER_13_0_INT = 130000;
        public static final int SEP_VER_14_0_INT = 140000;
        public static final int SEP_VER_15_0_INT = 150000;
        public static final int SEP_VER_16_0_INT = 160000;
        public static final int SEP_VER_17_0_INT = 170000;
        public static final int SEP_VER_8_1_INT = 80100;
        public static final int SEP_VER_8_5_INT = 80500;
        public static final int SEP_VER_9_0_INT = 90000;
    }

    public static int getDualAppVersion() {
        return 360;
    }

    public static boolean isDualAppId(int i) {
        return i >= 95 && i <= 99;
    }

    private static boolean isDualAppIdInternal(int i) {
        return i >= 95 && i <= 99;
    }

    static {
        String decodeString = decodeString("Y29tLmZhY2Vib29rLmthdGFuYQ==");
        FACEBOOK_PACKAGE_NAME = decodeString;
        String decodeString2 = decodeString("Y29tLndoYXRzYXBw");
        WHATSAPP_PACKAGE_NAME = decodeString2;
        String decodeString3 = decodeString("Y29tLmZhY2Vib29rLm9yY2E=");
        FACEBOOKMESSENGER_PACKAGE_NAME = decodeString3;
        String decodeString4 = decodeString("Y29tLnRlbmNlbnQubW9iaWxlcXE=");
        QQMOBILECHINA_PACKAGE_NAME = decodeString4;
        String decodeString5 = decodeString("Y29tLnRlbmNlbnQubW9iaWxlcXFp");
        QQMOBILEINTERNATIONAL_PACKAGE_NAME = decodeString5;
        String decodeString6 = decodeString("Y29tLnRlbmNlbnQubW0=");
        WECHAT_PACKAGE_NAME = decodeString6;
        String decodeString7 = decodeString("Y29tLnNreXBlLnJhaWRlcg==");
        SKYPE_PACKAGE_NAME = decodeString7;
        String decodeString8 = decodeString("Y29tLnZpYmVyLnZvaXA=");
        VIBER_PACKAGE_NAME = decodeString8;
        String decodeString9 = decodeString("anAubmF2ZXIubGluZS5hbmRyb2lk");
        LINE_PACKAGE_NAME = decodeString9;
        String decodeString10 = decodeString("Y29tLmJibQ==");
        BLACKBERRYMESSENGER_PACKAGE_NAME = decodeString10;
        String decodeString11 = decodeString("b3JnLnRlbGVncmFtLm1lc3Nlbmdlcg==");
        TELEGRAM_PACKAGE_NAME = decodeString11;
        String decodeString12 = decodeString("Y29tLmtha2FvLnRhbGs=");
        KAKAOTALK_PACKAGE_NAME = decodeString12;
        String decodeString13 = decodeString("Y29tLmJzYi5oaWtl");
        HIKE_PACKAGE_NAME = decodeString13;
        String decodeString14 = decodeString("Y29tLmljcS5tb2JpbGUuY2xpZW50");
        ICQ_PACKAGE_NAME = decodeString14;
        String decodeString15 = decodeString("Y29tLnlhaG9vLm1vYmlsZS5jbGllbnQuYW5kcm9pZC5pbQ==");
        YAHOOMESSENGER_PACKAGE_NAME = decodeString15;
        String decodeString16 = decodeString("Y29tLnppbmcuemFsbw==");
        ZALO_PACKAGE_NAME = decodeString16;
        String decodeString17 = decodeString("Y29tLnNuYXBjaGF0LmFuZHJvaWQ=");
        SNAPCHAT_PACKAGE_NAME = decodeString17;
        String decodeString18 = decodeString("Y29tLnNpbmEud2VpYm8=");
        WEIBO_PACKAGE_NAME = decodeString18;
        String decodeString19 = decodeString("a2lrLmFuZHJvaWQ=");
        KIK_PACKAGE_NAME = decodeString19;
        String decodeString20 = decodeString("Y29tLnNlYy5hbmRyb2lkLmFwcC5sYXVuY2hlcg==");
        SEC_LAUNCHER_PACKGE_NAME = decodeString20;
        String decodeString21 = decodeString("Y29tLnNlYy5hbmRyb2lkLmFwcC5lYXN5bGF1bmNoZXI=");
        SEC_EASY_LAUNCHER_PACKGE_NAME = decodeString21;
        String decodeString22 = decodeString("Y29tLnNlYy5hbmRyb2lkLmVtZXJnZW5jeWxhdW5jaGVy");
        SEC_EMERGENCY_LAUNCHER_PACKGE_NAME = decodeString22;
        String decodeString23 = decodeString("Y29tLnNlYy5hbmRyb2lkLmFwcC5kZXNrdG9wbGF1bmNoZXI=");
        SEC_DESKTOP_LAUNCHER_PACKGE_NAME = decodeString23;
        String decodeString24 = decodeString("Y29tLmdvb2dsZS5hbmRyb2lkLmdvb2dsZXF1aWNrc2VhcmNoYm94");
        GOOGLE_QUICKSEARCHBOX_PACKGE_NAME = decodeString24;
        String decodeString25 = decodeString("Y29tLnRlc2xhY29pbHN3LmxhdW5jaGVy");
        NOVA_PACKAGE_NAME = decodeString25;
        String decodeString26 = decodeString("Y29tLm1pY3Jvc29mdC5sYXVuY2hlcg==");
        MICROSOFT_PACKAGE_NAME = decodeString26;
        String decodeString27 = decodeString("b3JnLmFkdy5sYXVuY2hlcg==");
        ADW_PACKAGE_NAME = decodeString27;
        String decodeString28 = decodeString("Y29tLmFjdGlvbmxhdW5jaGVyLnBsYXlzdG9yZQ==");
        ACTION3_PACKAGE_NAME = decodeString28;
        String decodeString29 = decodeString("Y29tLm1vYmludC5ob2xvbGF1bmNoZXI=");
        HOLO_PACKAGE_NAME = decodeString29;
        String decodeString30 = decodeString("Z2lubGVtb24uZmxvd2VyZnJlZQ==");
        SMART3_PACKAGE_NAME = decodeString30;
        String decodeString31 = decodeString("Y29tLmNtbmxhdW5jaGVy");
        NOUGAT_PACKAGE_NAME = decodeString31;
        String decodeString32 = decodeString("Y29tLnlhbmRleC5sYXVuY2hlcg==");
        YANDEX_PACKAGE_NAME = decodeString32;
        String decodeString33 = decodeString("Y29tLm50dGRvY29tby5hbmRyb2lkLmRob21l");
        DCM_LIVEUX_PACKAGE_NAME = decodeString33;
        mSalesCode = SemSystemProperties.getSalesCode();
        CHINA_SALES_CODES = new String[]{"CHN", "CHM", "CBK", "CTC", "CHU", "CHC"};
        mIsChinaModel = isChinaModel();
        DUAL_APP_WHITELIST_PACKAGES = new String[]{decodeString, decodeString2, decodeString3, decodeString4, decodeString5, decodeString6, decodeString18, decodeString7, decodeString8, decodeString9, decodeString10, decodeString11, decodeString12, decodeString13, decodeString14, decodeString15, decodeString16, decodeString17, decodeString19};
        DUAL_APP_WHITELIST_PACKAGES_FOR_CHINA = new String[]{decodeString6, decodeString4, decodeString18};
        AFW_CAPABLE_LAUNCHER_APPS = new String[]{decodeString20, decodeString21, decodeString22, decodeString23, decodeString24, decodeString25, decodeString28, decodeString29, decodeString30, decodeString31, decodeString32, decodeString33, decodeString26, decodeString27};
        SAMSUNG_LAUNCHER_APPS = new String[]{decodeString20, decodeString21, decodeString22, decodeString23};
    }

    private SemDualAppManager() {
    }

    private static ISemDualAppManager getDualAppService() {
        if (mService == null) {
            mService = ISemDualAppManager.Stub.asInterface(ServiceManager.getService("dual_app"));
        }
        return mService;
    }

    public static SemDualAppManager getInstance(Context context) {
        if (sDAInstance == null) {
            synchronized (SemDualAppManager.class) {
                if (sDAInstance == null) {
                    sDAInstance = new SemDualAppManager();
                }
            }
        }
        return sDAInstance;
    }

    public boolean isWhitelistedPackage(String str) {
        String[] allWhitelistedPackages;
        int myUserId = UserHandle.myUserId();
        if ((myUserId == 0 || isDualAppIdInternal(myUserId)) && str != null && !"".equalsIgnoreCase(str) && (allWhitelistedPackages = getAllWhitelistedPackages()) != null) {
            for (String str2 : allWhitelistedPackages) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSupported() {
        int myUserId = UserHandle.myUserId();
        return myUserId == 0 || isDualAppIdInternal(myUserId);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] getAllWhitelistedPackages() {
        /*
            com.samsung.android.app.ISemDualAppManager r0 = getDualAppService()
            java.lang.String r1 = "SemDualAppManager"
            if (r0 == 0) goto L12
            java.lang.String[] r0 = r0.getAllWhitelistedPackages()     // Catch: android.os.RemoteException -> Ld
            goto L13
        Ld:
            java.lang.String r0 = "getAllWhitelistedPackages : RemoteException occured"
            android.util.Log.e(r1, r0)
        L12:
            r0 = 0
        L13:
            if (r0 != 0) goto L23
            java.lang.String r0 = "getAllWhitelistedPackages : null returned. Return default"
            android.util.Log.e(r1, r0)
            boolean r0 = com.samsung.android.app.SemDualAppManager.mIsChinaModel
            if (r0 == 0) goto L21
            java.lang.String[] r0 = com.samsung.android.app.SemDualAppManager.DUAL_APP_WHITELIST_PACKAGES_FOR_CHINA
            return r0
        L21:
            java.lang.String[] r0 = com.samsung.android.app.SemDualAppManager.DUAL_APP_WHITELIST_PACKAGES
        L23:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.SemDualAppManager.getAllWhitelistedPackages():java.lang.String[]");
    }

    public static int getDualAppProfileId() {
        String str = SystemProperties.get(DA_PROFILE_ID_PROPERTY_NAME, null);
        if (str == null) {
            return -10000;
        }
        try {
            if (str.isEmpty()) {
                return -10000;
            }
            return Integer.valueOf(str).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -10000;
        }
    }

    public static List<String> getAllInstalledWhitelistedPackages() {
        ISemDualAppManager dualAppService = getDualAppService();
        if (dualAppService != null) {
            try {
                return dualAppService.getAllInstalledWhitelistedPackages();
            } catch (RemoteException unused) {
                Log.e(TAG, "getAllInstalledWhitelistedPackages : RemoteException occured");
            }
        }
        Log.e(TAG, "getAllInstalledWhitelistedPackages : Can not connect to DualAppManagerService");
        return null;
    }

    public static boolean isInstalledWhitelistedPackage(String str) {
        int myUserId = UserHandle.myUserId();
        if (myUserId != 0 && !isDualAppIdInternal(myUserId)) {
            return false;
        }
        ISemDualAppManager dualAppService = getDualAppService();
        if (dualAppService != null) {
            try {
                return dualAppService.isInstalledWhitelistedPackage(str);
            } catch (RemoteException unused) {
                Log.e(TAG, "isInstalledWhitelistedPackage : RemoteException occured");
            }
        }
        Log.e(TAG, "isInstalledWhitelistedPackage : Can not connect to DualAppManagerService");
        return false;
    }

    public static Bundle updateDualAppData(Context context, int i, Bundle bundle) {
        ISemDualAppManager dualAppService = getDualAppService();
        if (dualAppService != null) {
            try {
                return dualAppService.updateDualAppData(context.getPackageName(), i, bundle);
            } catch (RemoteException unused) {
                Log.e(TAG, "updateDualAppData : RemoteException occured");
            }
        }
        Log.e(TAG, "updateDualAppData : Can not connect to DualAppManagerService");
        return null;
    }

    public static boolean shouldAddUserId(Uri uri, int i) {
        if (uri == null) {
            return false;
        }
        String scheme = uri.getScheme();
        String authority = uri.getAuthority();
        return (!"content".equals(scheme) || "com.android.contacts".equals(authority) || "com.android.calendar".equals(authority) || "com.android.providers.downloads.documents".equals(authority)) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00bd A[Catch: Exception -> 0x00ea, TryCatch #2 {Exception -> 0x00ea, blocks: (B:3:0x0006, B:8:0x0012, B:12:0x0024, B:14:0x003a, B:16:0x003f, B:20:0x004b, B:27:0x005b, B:29:0x005f, B:31:0x0071, B:33:0x00bd, B:34:0x00c5, B:36:0x0081, B:41:0x008d, B:39:0x00a4), top: B:2:0x0006, inners: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addDualAppAccounts(android.content.Context r17, android.widget.LinearLayout r18, int r19, int r20) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.SemDualAppManager.addDualAppAccounts(android.content.Context, android.widget.LinearLayout, int, int):void");
    }

    public static boolean isDualAppVersionSupported(int i) {
        return getDualAppVersion() >= i;
    }

    public boolean isNeedAddResolveInfoForOtherUser(ActivityInfo activityInfo, Intent intent) {
        int userId;
        if (activityInfo == null || (!((userId = UserHandle.getUserId(activityInfo.applicationInfo.uid)) == 0 || isDualAppIdInternal(userId)) || ((intent instanceof LabeledIntent) && !isInstalledWhitelistedPackage(activityInfo.packageName)))) {
            return false;
        }
        if (this.mDuplicateInitialIntents.containsKey(activityInfo.getComponentName())) {
            Log.w(TAG, "Duplicate activity found for " + intent);
            return false;
        }
        if (isInstalledWhitelistedPackage(activityInfo.packageName) && (Intent.ACTION_SEND.equals(intent.getAction()) || Intent.ACTION_SEND_MULTIPLE.equals(intent.getAction()) || ((intent.getComponent() != null && isChooserRequired(intent.getComponent().getClassName())) || isChinaDualApp(activityInfo.packageName) || ((intent.getData() != null && "mqqapi".equals(intent.getData().getScheme())) || isKakaoThemeIntent(activityInfo.packageName, intent))))) {
            this.mDuplicateInitialIntents.put(activityInfo.getComponentName(), Integer.valueOf(userId));
            return true;
        }
        return false;
    }

    public boolean isDuplicateEntry(PackageManager packageManager, List<DisplayResolveInfo> list, ActivityInfo activityInfo, Intent intent) {
        int userId;
        if (activityInfo == null || (!((userId = UserHandle.getUserId(activityInfo.applicationInfo.uid)) == 0 || isDualAppIdInternal(userId)) || ((intent instanceof LabeledIntent) && !isInstalledWhitelistedPackage(activityInfo.packageName)))) {
            return false;
        }
        if (this.mDuplicateInitialIntents.containsKey(activityInfo.getComponentName())) {
            return true;
        }
        if (isInstalledWhitelistedPackage(activityInfo.packageName) && (Intent.ACTION_SEND.equals(intent.getAction()) || Intent.ACTION_SEND_MULTIPLE.equals(intent.getAction()) || ((intent.getComponent() != null && isChooserRequired(intent.getComponent().getClassName())) || isChinaDualApp(activityInfo.packageName) || ((intent.getData() != null && "mqqapi".equals(intent.getData().getScheme())) || isKakaoThemeIntent(activityInfo.packageName, intent))))) {
            this.mDuplicateInitialIntents.put(activityInfo.getComponentName(), Integer.valueOf(userId));
            addResolveInfoFromOtherUser(packageManager, list, activityInfo, intent);
        }
        return false;
    }

    public void clearDuplicateMaps() {
        this.mDuplicateInitialIntents.clear();
    }

    private void addResolveInfoFromOtherUser(PackageManager packageManager, List<DisplayResolveInfo> list, ActivityInfo activityInfo, Intent intent) {
        int dualAppProfileId = getDualAppProfileId();
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        if (activityInfo == null || !isDualAppId(dualAppProfileId)) {
            return;
        }
        if (UserHandle.getUserId(activityInfo.applicationInfo.uid) != 0) {
            dualAppProfileId = 0;
        }
        ComponentName componentName = activityInfo.getComponentName();
        ResolveInfo resolveInfo = null;
        if (componentName != null) {
            try {
                activityInfo = asInterface.getActivityInfo(componentName, 0L, dualAppProfileId);
                ResolveInfo resolveInfo2 = new ResolveInfo();
                try {
                    resolveInfo2.activityInfo = activityInfo;
                } catch (RemoteException unused) {
                }
                resolveInfo = resolveInfo2;
            } catch (RemoteException unused2) {
            }
        }
        if (intent instanceof LabeledIntent) {
            LabeledIntent labeledIntent = (LabeledIntent) intent;
            resolveInfo.resolvePackageName = labeledIntent.getSourcePackage();
            resolveInfo.labelRes = labeledIntent.getLabelResource();
            resolveInfo.nonLocalizedLabel = labeledIntent.getNonLocalizedLabel();
            resolveInfo.icon = labeledIntent.getIconResource();
            resolveInfo.iconResourceId = resolveInfo.icon;
        }
        if (activityInfo != null) {
            list.add(new DisplayResolveInfo(intent, resolveInfo, resolveInfo.loadLabel(packageManager), null, intent, null));
        }
    }

    public static void drawDualAppBadge(final Context context, final AppWidgetHostView appWidgetHostView, UserHandle userHandle) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.samsung.android.app.SemDualAppManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ImageView imageView = new ImageView(Context.this);
                    Drawable drawableForDensity = Resources.getSystem().getDrawableForDensity(R.drawable.ic_dualapp_widget_badge, Context.this.getResources().getDisplayMetrics().densityDpi);
                    if (drawableForDensity != null) {
                        imageView.lambda$setImageURIAsync$2(drawableForDensity);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(drawableForDensity.getIntrinsicWidth(), drawableForDensity.getIntrinsicHeight());
                        layoutParams.gravity = 85;
                        appWidgetHostView.addView(imageView, layoutParams);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000L);
    }

    public static boolean isAfwSupportLauncher(String str) {
        if (str != null) {
            for (String str2 : AFW_CAPABLE_LAUNCHER_APPS) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSamsungLauncher(String str) {
        if (str != null) {
            for (String str2 : SAMSUNG_LAUNCHER_APPS) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isChooserRequired(String str) {
        return "com.tencent.mm.plugin.base.stub.WXEntryActivity".equals(str) || "com.tencent.open.agent.AgentActivity".equals(str) || "com.tencent.mm.plugin.base.stub.WXPayEntryActivity".equals(str) || "com.sina.weibo.SSOActivity".equals(str);
    }

    public static boolean isChinaDualApp(String str) {
        return QQMOBILECHINA_PACKAGE_NAME.equals(str) || QQMOBILEINTERNATIONAL_PACKAGE_NAME.equals(str) || WEIBO_PACKAGE_NAME.equals(str) || WECHAT_PACKAGE_NAME.equals(str);
    }

    private static boolean isKakaoThemeIntent(String str, Intent intent) {
        return KAKAOTALK_PACKAGE_NAME.equals(str) && intent.getDataString() != null && intent.getDataString().contains(KAKAOTALK_SETTINGS_THEME_URI);
    }

    public static boolean isChinaModel() {
        if (mSalesCode != null) {
            for (String str : CHINA_SALES_CODES) {
                if (str.equals(mSalesCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String decodeString(String str) {
        return new String(Base64.decode(str, 0), StandardCharsets.UTF_8);
    }

    public static boolean shouldRemove(ResolveInfo resolveInfo) {
        if (isDualAppId(resolveInfo.userHandle.getIdentifier())) {
            return resolveInfo.activityInfo.packageName.equals("com.android.settings") || resolveInfo.activityInfo.packageName.equals("com.android.chrome");
        }
        return false;
    }
}
