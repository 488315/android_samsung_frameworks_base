package com.android.server;

import android.app.ActivityManagerNative;
import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;

import com.samsung.android.app.ISemDualAppManager;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DualAppManagerService extends ISemDualAppManager.Stub {
    public static int inOpsCallback;
    public static Context mContext;
    public static final String[] DEFAULT_PACKAGES_NOT_FORWARDING = {
        "com.google.android.gms",
        "com.google.android.gsf",
        "com.google.android.gsf.login",
        "com.android.chrome",
        "com.google.android.webview",
        "com.android.nfc",
        "com.google.android.permissioncontroller",
        "com.android.permissioncontroller",
        "com.samsung.android.permissioncontroller",
        "com.google.android.overlay.modules.permissioncontroller",
        "com.google.android.overlay.modules.permissioncontroller.forframework",
        "com.google.android.overlay.modules.modulemetadata.forframework"
    };
    public static final String[] DUALAPP_DEFAULT_PACKAGES = {
        "android",
        "android.auto_generated_rro_product__",
        "com.sec.android.provider.badge",
        "com.android.chrome",
        "com.android.credentialmanager",
        "com.android.providers.downloads",
        "com.facebook.appmanager",
        "com.google.android.overlay.gmsconfig.geotz",
        "com.google.android.overlay.modules.modulemetadata.forframework",
        "com.google.android.overlay.modules.permissioncontroller",
        "com.google.android.overlay.modules.permissioncontroller.forframework",
        "com.google.android.packageinstaller",
        "com.google.android.gms",
        "com.google.android.permissioncontroller",
        "com.samsung.android.permissioncontroller",
        "com.google.android.apps.restore",
        "com.google.android.gsf",
        "com.android.intentresolver",
        "com.android.keychain",
        "com.android.nfc",
        "com.samsung.android.packageinstaller",
        "com.android.phone",
        KnoxCustomManagerService.SETTING_PKG_NAME,
        "com.android.providers.settings",
        "com.android.server.telecom",
        "com.google.android.webview"
    };
    public static final HashMap mInstalledWhitelistedPkgMap = new HashMap();
    public static HashMap mWhitelistedPkgMap = new HashMap();
    public static final List mDaWeeklyUsageCount = new ArrayList();
    public static final List mDaMonthlyUsageCount = new ArrayList();
    public static int thisWeek = -1;
    public static DualAppManagerService sDAMSInstance = null;
    public static InternalHandler mHandler = null;
    public static String lastResumedActivity = null;
    public static boolean isNotNullInputContextNotified = false;
    public static boolean isNullInputContextNotified = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InternalHandler extends Handler {
        public InternalHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Log.d("DualAppManagerService", "handleMessage() START " + message);
            try {
                int i = message.what;
                if (i != 1) {
                    DualAppManagerService dualAppManagerService = DualAppManagerService.this;
                    if (i == 2) {
                        dualAppManagerService.getClass();
                        DualAppManagerService.updateWhitelistPackages();
                        DualAppManagerService.updateInstalledWhitelistPackages();
                    } else if (i == 3) {
                        dualAppManagerService.getClass();
                        DualAppManagerService.updateDAUsage();
                    } else if (i == 4) {
                        String str = (String) message.obj;
                        Uri parse =
                                Uri.parse(
                                        "content://com.samsung.android.bbc.bbcagent/updateForegroundActivity");
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("component", str);
                        DualAppManagerService.mContext
                                .getContentResolver()
                                .update(parse, contentValues, null, null);
                    } else if (i == 5) {
                        String str2 = (String) message.obj;
                        int i2 = message.arg1;
                        Uri parse2 =
                                Uri.parse(
                                        "content://com.samsung.android.bbc.bbcagent/updateInputContext");
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("newInputContextNotNull", Integer.valueOf(i2));
                        contentValues2.put("component", str2);
                        DualAppManagerService.mContext
                                .getContentResolver()
                                .update(parse2, contentValues2, null, null);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setClassName(
                            "com.samsung.android.da.daagent",
                            "com.samsung.android.da.daagent.service.SwitchLauncherService");
                    intent.putExtra("defaultLauncher", (String) message.obj);
                    DualAppManagerService.mContext.startServiceAsUser(intent, UserHandle.SYSTEM);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("DualAppManagerService", "handleMessage() END" + message);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OpChangeListener extends AppOpsManager.OnOpChangedInternalListener {
        public final void onOpChanged(int i, String str) {
            AppOpsManager appOpsManager;
            Integer num;
            if (i == 24) {
                return;
            }
            if (DualAppManagerService.inOpsCallback != 0) {
                Log.d("DualAppManagerService", "onOpChanged() is in progress");
                return;
            }
            DualAppManagerService.inOpsCallback = 1;
            try {
                appOpsManager =
                        (AppOpsManager)
                                DualAppManagerService.mContext.getSystemService(
                                        AppOpsManager.class);
                num = (Integer) DualAppManagerService.mInstalledWhitelistedPkgMap.get(str);
            } catch (Exception unused) {
            } catch (Throwable th) {
                DualAppManagerService.inOpsCallback = 0;
                throw th;
            }
            if (num == null) {
                DualAppManagerService.inOpsCallback = 0;
                return;
            }
            appOpsManager.setMode(
                    i,
                    num.intValue(),
                    str,
                    appOpsManager.checkOpNoThrow(i, UserHandle.getAppId(num.intValue()), str));
            ActivityManagerNative.getDefault()
                    .killUid(
                            UserHandle.getAppId(num.intValue()),
                            UserHandle.getUserId(num.intValue()),
                            "Permission related app op changed");
            DualAppManagerService.inOpsCallback = 0;
        }

        public final void onOpChanged(String str, String str2) {
            Log.d("DualAppManagerService", "onOpChanged() " + str + "/" + str2);
        }
    }

    public static Bundle addWhitelistedPkg(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        String string = bundle.getString("pkgName");
        int i = bundle.getInt("pkgUid");
        if (string == null) {
            bundle2.putInt("result_code", 0);
            bundle2.putString("result_desc", "package name is null");
        } else {
            HashMap hashMap = mInstalledWhitelistedPkgMap;
            if (hashMap.get(string) != null) {
                bundle2.putInt("result_code", 1);
                bundle2.putString("result_desc", "package is already added");
            } else {
                try {
                    hashMap.put(string, Integer.valueOf(i));
                    bundle2.putInt("result_code", 1);
                    bundle2.putString("result_desc", "success");
                } catch (Exception e) {
                    bundle2.putInt("result_code", 0);
                    bundle2.putString("result_desc", e.getMessage());
                }
            }
        }
        return bundle2;
    }

    public static ActivityInfo changeInfoIfDeletingDualApp(
            Context context, ActivityInfo activityInfo, Intent intent, int i, String str) {
        if (activityInfo == null) {
            return null;
        }
        if ("com.samsung.android.da.daagent".equals(str)
                || KnoxCustomManagerService.SETTING_PKG_NAME.equals(str)
                || SemDualAppManager.isSamsungLauncher(str)) {
            return activityInfo;
        }
        String schemeSpecificPart =
                intent.getData() != null ? intent.getData().getSchemeSpecificPart() : null;
        if (!SemDualAppManager.getInstance(context).isWhitelistedPackage(schemeSpecificPart)) {
            return activityInfo;
        }
        UserHandle userHandle = (UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
        if (userHandle != null) {
            i = userHandle.getIdentifier();
        }
        if (!SemDualAppManager.isDualAppId(i) && i != 0) {
            return activityInfo;
        }
        PackageManager packageManager = context.getPackageManager();
        Intent intent2 = new Intent();
        intent2.setClassName(
                "com.samsung.android.da.daagent", "com.samsung.android.da.daagent.RemoveDualIM");
        ResolveInfo resolveActivityAsUser = packageManager.resolveActivityAsUser(intent2, 66688, 0);
        ActivityInfo activityInfo2 =
                resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null;
        if (activityInfo2 == null) {
            return activityInfo;
        }
        intent.setClassName(
                "com.samsung.android.da.daagent", "com.samsung.android.da.daagent.RemoveDualIM");
        intent.putExtra("com.samsung.android.da.original_intent", new Intent(intent));
        intent.putExtra("packageName", schemeSpecificPart);
        intent.putExtra("userId", i);
        return activityInfo2;
    }

    public static void changeUriForDualApp(int i, Intent intent) {
        Uri uri;
        try {
            Uri uri2 = (Uri) intent.getExtra("output");
            if (uri2 != null && SemDualAppManager.shouldAddUserId(uri2, i)) {
                intent.putExtra("output", ContentProvider.maybeAddUserId(uri2, i));
            }
            if (intent.getClipData() != null) {
                intent.getClipData().fixUris(i);
            }
            if (intent.getData() != null
                    && SemDualAppManager.shouldAddUserId(intent.getData(), i)) {
                intent.setDataAndType(
                        ContentProvider.maybeAddUserId(intent.getData(), i), intent.getType());
            }
            if (!"android.intent.action.SEND_MULTIPLE".equals(intent.getAction())) {
                if (!"android.intent.action.SEND".equals(intent.getAction())
                        || intent.getExtras() == null
                        || (uri =
                                        (Uri)
                                                intent.getExtras()
                                                        .getParcelable(
                                                                "android.intent.extra.STREAM"))
                                == null
                        || !SemDualAppManager.shouldAddUserId(uri, i)) {
                    return;
                }
                intent.putExtra(
                        "android.intent.extra.STREAM", ContentProvider.maybeAddUserId(uri, i));
                return;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList parcelableArrayListExtra =
                    intent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            if (parcelableArrayListExtra == null || parcelableArrayListExtra.size() <= 0) {
                return;
            }
            Iterator it = parcelableArrayListExtra.iterator();
            while (it.hasNext()) {
                Uri uri3 = (Uri) it.next();
                if (SemDualAppManager.shouldAddUserId(uri3, i)) {
                    arrayList.add(ContentProvider.maybeAddUserId(uri3, i));
                } else {
                    arrayList.add(uri3);
                }
            }
            intent.putExtra("android.intent.extra.STREAM", arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DualAppManagerService getInstance(Context context) {
        if (sDAMSInstance == null) {
            synchronized (DualAppManagerService.class) {
                try {
                    if (sDAMSInstance == null) {
                        DualAppManagerService dualAppManagerService = new DualAppManagerService();
                        new OpChangeListener();
                        mHandler = dualAppManagerService.new InternalHandler();
                        sDAMSInstance = dualAppManagerService;
                        mContext = context;
                    }
                } finally {
                }
            }
        }
        return sDAMSInstance;
    }

    public static boolean hasExternalAppDirPath(Intent intent, String str) {
        String path;
        String path2;
        String path3;
        String path4;
        String path5;
        String path6;
        String m =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "/storage/emulated/0/Android/data/", str);
        if (!"android.intent.action.SEND_MULTIPLE".equals(intent.getAction())) {
            if (!"android.intent.action.SEND".equals(intent.getAction())) {
                return false;
            }
            ClipData clipData = intent.getClipData();
            if (clipData != null) {
                ArrayList arrayList = new ArrayList();
                clipData.collectUris(arrayList);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Uri uri = (Uri) it.next();
                    if (uri != null
                            && "file".equals(uri.getScheme())
                            && (path3 = uri.getPath()) != null
                            && path3.startsWith(m)) {
                        return true;
                    }
                }
            }
            Uri data = intent.getData();
            if (data != null
                    && "file".equals(data.getScheme())
                    && (path2 = data.getPath()) != null
                    && path2.startsWith(m)) {
                return true;
            }
            Uri uri2 = (Uri) intent.getExtras().getParcelable("android.intent.extra.STREAM");
            return uri2 != null
                    && "file".equals(uri2.getScheme())
                    && (path = uri2.getPath()) != null
                    && path.startsWith(m);
        }
        ClipData clipData2 = intent.getClipData();
        if (clipData2 != null) {
            ArrayList arrayList2 = new ArrayList();
            clipData2.collectUris(arrayList2);
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                Uri uri3 = (Uri) it2.next();
                if (uri3 != null
                        && "file".equals(uri3.getScheme())
                        && (path6 = uri3.getPath()) != null
                        && path6.startsWith(m)) {
                    return true;
                }
            }
        }
        Uri data2 = intent.getData();
        if (data2 != null
                && "file".equals(data2.getScheme())
                && (path5 = data2.getPath()) != null
                && path5.startsWith(m)) {
            return true;
        }
        ArrayList parcelableArrayListExtra =
                intent.getParcelableArrayListExtra("android.intent.extra.STREAM");
        if (parcelableArrayListExtra == null) {
            return false;
        }
        Iterator it3 = parcelableArrayListExtra.iterator();
        while (it3.hasNext()) {
            Uri uri4 = (Uri) it3.next();
            if (uri4 != null
                    && "file".equals(uri4.getScheme())
                    && (path4 = uri4.getPath()) != null
                    && path4.startsWith(m)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInstalledWhitelistedPackageInternal(String str) {
        try {
            return mInstalledWhitelistedPkgMap.containsKey(str);
        } catch (Exception e) {
            Log.e(
                    "DualAppManagerService",
                    "Exception occured in isInstalledWhitelistedPackageInternal. retrun false");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean mayForwardShare(Intent intent, String str, int i, int i2) {
        ComponentName component;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (intent != null
                && "com.samsung.android.da.daagent.FORWARD_SHARE_FROM_OWNER"
                        .equals(intent.getAction())) {
            Log.d("DualAppManagerService", "illegal action. this action doens't use other app.");
            throw new SecurityException("not allow this action");
        }
        try {
            if (SemDualAppManager.isDualAppId(i2)) {
                Intent intent2 =
                        (intent == null
                                        || !"android.intent.action.CHOOSER"
                                                .equals(intent.getAction()))
                                ? intent
                                : (Intent)
                                        intent.getExtras()
                                                .getParcelable("android.intent.extra.INTENT");
                if (intent2 != null
                        && intent2.getComponent() == null
                        && intent2.getPackage() == null
                        && !"com.samsung.android.da.daagent".equals(str)
                        && hasExternalAppDirPath(intent2, str)) {
                    intent.setClassName(
                            "com.samsung.android.da.daagent",
                            "com.samsung.android.da.daagent.activity.ForwardShareActivity");
                    return true;
                }
            } else if (i2 == 0) {
                String str2 = intent.getPackage();
                if (str2 == null && (component = intent.getComponent()) != null) {
                    str2 = component.getPackageName();
                }
                if (!"com.samsung.android.da.daagent".equals(str)
                        && SemDualAppManager.isDualAppId(i)
                        && str.equals(str2)
                        && hasExternalAppDirPath(intent, str)) {
                    intent.putExtra("android.intent.extra.INTENT", (Parcelable) intent.clone());
                    intent.setAction("com.samsung.android.da.daagent.FORWARD_SHARE_FROM_OWNER");
                    intent.setClassName(
                            "com.samsung.android.da.daagent",
                            "com.samsung.android.da.daagent.activity.ForwardShareActivity");
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static void notifyActivityResumedLocked(String str) {
        if (SystemProperties.getInt("sys.datawedge.prop", 0) == 1) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "DW::notifyActivityResumedLocked ", str, " 0", "DualAppManagerService");
            lastResumedActivity = str;
            isNullInputContextNotified = false;
            isNotNullInputContextNotified = false;
            sendInternalMsg(4, 0, str);
        }
    }

    public static void printInstalledWhitelistedPkg() {
        Log.d("DualAppManagerService", "printInstalledWhitelistedPkg called!");
        for (String str : mInstalledWhitelistedPkgMap.keySet()) {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "installed whitelisted dual app [", str, "/");
            m.append(String.valueOf(mInstalledWhitelistedPkgMap.get(str)));
            m.append("]");
            Log.d("DualAppManagerService", m.toString());
        }
    }

    public static void recordDaUsageCount(int i, Intent intent) {
        if (!SemDualAppManager.isDualAppId(i) || intent == null) {
            return;
        }
        if (isInstalledWhitelistedPackageInternal(
                intent.getComponent() != null
                        ? intent.getComponent().getPackageName()
                        : intent.getPackage())) {
            sendInternalMsg(3, 0, null);
        }
    }

    public static Bundle removeAllWhitelistedPkgs() {
        Bundle bundle = new Bundle();
        mInstalledWhitelistedPkgMap.clear();
        bundle.putInt("result_code", 1);
        bundle.putString("result_desc", "success");
        return bundle;
    }

    public static Bundle removeWhitelistedPkg(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        String string = bundle.getString("pkgName");
        if (string == null) {
            bundle2.putInt("result_code", 0);
            bundle2.putString("result_desc", "package name is null");
        } else {
            HashMap hashMap = mInstalledWhitelistedPkgMap;
            if (hashMap.get(string) == null) {
                bundle2.putInt("result_code", 1);
                bundle2.putString("result_desc", "package doesn't exist");
            } else {
                try {
                    hashMap.remove(string);
                    bundle2.putInt("result_code", 1);
                    bundle2.putString("result_desc", "success");
                } catch (Exception e) {
                    bundle2.putInt("result_code", 0);
                    bundle2.putString("result_desc", e.getMessage());
                }
            }
        }
        return bundle2;
    }

    public static Bundle renewWhitelistedPkg(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        HashMap hashMap = mInstalledWhitelistedPkgMap;
        hashMap.clear();
        hashMap.putAll((HashMap) bundle.getSerializable("allInstalledWhitelistedPkgs"));
        for (String str : hashMap.keySet()) {}
        bundle2.putInt("result_code", 1);
        bundle2.putString("result_desc", "success");
        return bundle2;
    }

    public static void sendInternalMsg(int i, int i2, Object obj) {
        Log.d(
                "DualAppManagerService",
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        i, i2, "sendInternalMsg() ", "/", "/0"));
        InternalHandler internalHandler = mHandler;
        if (internalHandler != null) {
            internalHandler.sendMessage(internalHandler.obtainMessage(i, i2, 0, obj));
        } else {
            Log.d("DualAppManagerService", "sendInternalMsg() failed, handler is null");
        }
    }

    public static Bundle setDualAppNotificationSound(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        String string = bundle.getString("sound_uri");
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "setDualAppNotificationSound : ", string, "DualAppManagerService");
        if (string == null) {
            bundle2.putInt("result_code", 0);
            bundle2.putString("result_desc", "uri is null");
        } else {
            try {
                bundle2.putInt("result_code", 1);
                bundle2.putString("result_desc", "success");
            } catch (Exception e) {
                bundle2.putInt("result_code", 0);
                bundle2.putString("result_desc", e.getMessage());
            }
        }
        return bundle2;
    }

    public static boolean shouldForwardToOwner(String str) {
        if (str == null || "".equalsIgnoreCase(str)) {
            return true;
        }
        Context context = mContext;
        if (context != null
                && SemDualAppManager.getInstance(context).isWhitelistedPackage(str)
                && (SemDualAppManager.isInstalledWhitelistedPackage(str)
                        || str.equals(
                                mContext.getPackageManager()
                                        .getNameForUid(Binder.getCallingUid())))) {
            return false;
        }
        String[] strArr = DEFAULT_PACKAGES_NOT_FORWARDING;
        for (int i = 0; i < 12; i++) {
            if (strArr[i].equals(str)) {
                return false;
            }
        }
        return ("com.bst.floatingmsgproxy".equals(str) || "com.bst.airmessage".equals(str))
                ? false
                : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x02ef A[Catch: Exception -> 0x005c, TryCatch #0 {Exception -> 0x005c, blocks: (B:10:0x003a, B:12:0x0050, B:16:0x0061, B:19:0x0068, B:21:0x0077, B:25:0x0090, B:27:0x00e5, B:30:0x00f1, B:32:0x0102, B:34:0x0108, B:36:0x010e, B:38:0x0114, B:40:0x0123, B:43:0x012a, B:46:0x02ef, B:48:0x02f6, B:49:0x02f9, B:51:0x02ff, B:52:0x0312, B:54:0x0318, B:55:0x032b, B:57:0x011d, B:58:0x013a, B:61:0x0143, B:63:0x0154, B:65:0x015a, B:67:0x0172, B:69:0x017b, B:71:0x0183, B:72:0x0190, B:74:0x0196, B:76:0x01a7, B:78:0x01ad, B:80:0x01b3, B:82:0x01c6, B:84:0x01cc, B:86:0x01d5, B:88:0x01de, B:90:0x01e7, B:92:0x01ed, B:95:0x01fe, B:97:0x0204, B:99:0x020a, B:101:0x0210, B:103:0x01f6, B:104:0x0221, B:106:0x022e, B:108:0x023b, B:110:0x0248, B:113:0x0256, B:115:0x025c, B:117:0x0269, B:119:0x0272, B:120:0x0296, B:122:0x029c, B:124:0x02a2, B:125:0x0096, B:129:0x00a0, B:131:0x00ae, B:133:0x00bc, B:135:0x00c5, B:137:0x00d3, B:141:0x02b9, B:143:0x02bf, B:146:0x02d0, B:148:0x02d6, B:150:0x02dc, B:151:0x02c8), top: B:9:0x003a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.Intent startDAChooserActivity(
            int r19, int r20, int r21, android.content.Intent r22, java.lang.String r23) {
        /*
            Method dump skipped, instructions count: 842
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.DualAppManagerService.startDAChooserActivity(int, int,"
                    + " int, android.content.Intent, java.lang.String):android.content.Intent");
    }

    public static void systemReady() {
        Log.d("DualAppManagerService", "DualAppManagerService ready");
        try {
            if (!writeDualAppProfileId(mContext)
                    && SystemProperties.getInt("persist.sys.dualapp.directory.revision", 0) == 0) {
                SystemProperties.set("persist.sys.dualapp.directory.revision", "1");
            }
        } catch (Exception e) {
            Log.e("DualAppManagerService", "Failed to write dual app profile id");
            e.printStackTrace();
        }
        Log.d("DualAppManagerService", "systemReady done.");
    }

    public static void updateDAUsage() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(7);
        int i2 = calendar.get(3);
        int i3 = calendar.get(5);
        if (thisWeek != i2) {
            ((ArrayList) mDaWeeklyUsageCount).clear();
            thisWeek = i2;
        }
        ArrayList arrayList = (ArrayList) mDaWeeklyUsageCount;
        if (!arrayList.contains(Integer.valueOf(i))) {
            arrayList.add(Integer.valueOf(i));
            try {
                mContext.getContentResolver()
                        .update(
                                Uri.parse(
                                        "content://com.samsung.android.da.daagent.provider/recordUsage"),
                                new ContentValues(),
                                null,
                                null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayList arrayList2 = (ArrayList) mDaMonthlyUsageCount;
        if (arrayList2.contains(Integer.valueOf(i3))) {
            return;
        }
        arrayList2.add(Integer.valueOf(i3));
    }

    public static void updateInstalledWhitelistPackages() {
        Cursor query =
                mContext.getContentResolver()
                        .query(
                                Uri.parse(
                                        "content://com.samsung.android.da.daagent.provider/getInstalledApps"),
                                null,
                                null,
                                null,
                                null);
        mInstalledWhitelistedPkgMap.clear();
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    Log.d(
                            "DualAppManagerService",
                            "updateInstalledWhitelistPackages : "
                                    + query.getString(0)
                                    + "/"
                                    + query.getInt(1));
                    mInstalledWhitelistedPkgMap.put(
                            query.getString(0), Integer.valueOf(query.getInt(1)));
                } finally {
                    query.close();
                }
            }
        }
    }

    public static void updateWhitelistPackages() {
        Cursor query =
                mContext.getContentResolver()
                        .query(
                                Uri.parse(
                                        "content://com.samsung.android.da.daagent.provider/getWhitelistApps"),
                                null,
                                null,
                                null,
                                null);
        mWhitelistedPkgMap.clear();
        if (query != null) {
            while (query.moveToNext()) {
                Log.d("DualAppManagerService", "updateWhitelistPackages : " + query.getString(0));
                mWhitelistedPkgMap.put(query.getString(0), 0);
            }
            query.close();
        }
    }

    public static Bundle updateWhitelistPkg(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        HashMap hashMap = (HashMap) bundle.getSerializable("packageList");
        if (hashMap != null) {
            mWhitelistedPkgMap = hashMap;
        }
        bundle2.putInt("result_code", 1);
        bundle2.putString("result_desc", "success");
        return bundle2;
    }

    public static boolean writeDualAppProfileId(Context context) {
        int i;
        boolean z;
        Iterator it = UserManager.get(context).getUsers().iterator();
        while (true) {
            if (!it.hasNext()) {
                i = -10000;
                z = false;
                break;
            }
            UserInfo userInfo = (UserInfo) it.next();
            if (SemDualAppManager.isDualAppId(userInfo.id)) {
                i = userInfo.id;
                z = true;
                break;
            }
        }
        String str = SystemProperties.get("persist.sys.dualapp.prop");
        if (str != null && str.length() > 0) {
            Log.d("DualAppManagerService", "dualapp_prop ".concat(str));
        } else if (z) {
            SystemProperties.set("persist.sys.dualapp.prop", "0");
        } else {
            SystemProperties.set("persist.sys.dualapp.prop", "1");
        }
        if (!z) {
            Log.d("DualAppManagerService", "Can not found DA Profile. Id");
            SystemProperties.set("sys.dualapp.profile_id", "");
            return false;
        }
        Log.d("DualAppManagerService", "Found DA Profile. Id = " + i);
        SystemProperties.set("sys.dualapp.profile_id", String.valueOf(i));
        return true;
    }

    public final List getAllInstalledWhitelistedPackages() {
        try {
            Set keySet = mInstalledWhitelistedPkgMap.keySet();
            if (keySet != null) {
                return new ArrayList(keySet);
            }
            return null;
        } catch (Exception unused) {
            Log.e(
                    "DualAppManagerService",
                    "Exception occured in getAllInstalledWhitelistedPackages. retrun null");
            return null;
        }
    }

    public final String[] getAllWhitelistedPackages() {
        HashMap hashMap = mWhitelistedPkgMap;
        if (hashMap == null) {
            return null;
        }
        if (!hashMap.isEmpty()) {
            return (String[])
                    mWhitelistedPkgMap.keySet().toArray(new String[mWhitelistedPkgMap.size()]);
        }
        Log.e("DualAppManagerService", "getAllWhitelistedPackages : empty");
        return null;
    }

    public final boolean isInstalledWhitelistedPackage(String str) {
        try {
            if (!mInstalledWhitelistedPkgMap.containsKey(str)) {
                return false;
            }
            Log.d("DualAppManagerService", "Found!");
            return true;
        } catch (Exception e) {
            Log.e(
                    "DualAppManagerService",
                    "Exception occured in isInstalledWhitelistedPackage. retrun false");
            e.printStackTrace();
            return false;
        }
    }

    public final synchronized Bundle updateDualAppData(String str, int i, Bundle bundle) {
        Bundle bundle2 = null;
        if ("com.samsung.android.da.daagent".equals(str) && Binder.getCallingUid() == 1000) {
            Log.d("DualAppManagerService", "updateDualAppData " + str + "/" + i + "/" + bundle);
            if (bundle == null) {
                Log.d("DualAppManagerService", "updateDualAppData. Bundle is null");
                return null;
            }
            String string = bundle.getString("command");
            try {
                if ("renewInstalledWhitelistedPkgs".equals(string)) {
                    bundle2 = renewWhitelistedPkg(bundle);
                } else if ("addInstalledWhitelistedPkg".equals(string)) {
                    bundle2 = addWhitelistedPkg(bundle);
                } else if ("removeInstalledWhitelistedPkg".equals(string)) {
                    bundle2 = removeWhitelistedPkg(bundle);
                } else if ("removeAllInstalledWhitelistedPkgs".equals(string)) {
                    bundle2 = removeAllWhitelistedPkgs();
                } else if ("printInstalledWhitelistedPkg".equals(string)) {
                    printInstalledWhitelistedPkg();
                } else if ("setDualAppNotificationSound".equals(string)) {
                    bundle2 = setDualAppNotificationSound(bundle);
                } else if ("updateWhitelistPkgs".equals(string)) {
                    bundle2 = updateWhitelistPkg(bundle);
                } else {
                    Bundle bundle3 = new Bundle();
                    try {
                        bundle3.putInt("result_code", 0);
                        bundle3.putString("result_desc", "not defined command");
                        bundle2 = bundle3;
                    } catch (Exception e) {
                        bundle2 = bundle3;
                        e = e;
                        e.printStackTrace();
                        return bundle2;
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
            return bundle2;
        }
        Log.d("DualAppManagerService", "updateDualAppData is called from unauthorized app");
        return null;
    }
}
