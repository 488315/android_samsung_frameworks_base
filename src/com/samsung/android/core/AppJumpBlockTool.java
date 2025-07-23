package com.samsung.android.core;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.share.SemShareConstants;
import com.sec.android.iaft.SmLib_IafdConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class AppJumpBlockTool {
    private static final String APP_JUMP_BLOCK_ALLOW_LIST = "com.tencent.wework;com.alibaba.android.rimet;com.samsung.ssd.wolfserver;com.tencent.mm;com.eg.android.AlipayGphone;com.tencent.mobileqq;-com.sina.weibo";
    public static final String BROADCAST_ACTION = "com.samsung.intent.action.APP_JUMP_BLOCK_DIALOG_RESULT";
    private static final int DEFAULT_EMPTY_VALUE = -100;
    public static final String INTENT_ACTION = "com.samsung.intent.action.APP_JUMP_BLOCK_DIALOG";
    public static final String RESULT_ALLOW = "Allow";
    public static final String RESULT_CANCEL = "Cancel";
    public static final String RESULT_FAIL = "Fail";
    private static final String SHARE_KEY = "com.samsung.android.core_app_jump_block_";
    private static final int STATUS_OPEN = 1;
    public static final String TAG = "AppJumpBlockTool";
    private static final boolean isEngBinary = SystemProperties.get("ro.build.type", "").equals("eng");
    private static String sLastFromPackage = "";
    private static String sLastToPackage = "";

    public static Intent createAppBlockIntent(Context context, String str, int i, int i2, int i3, Intent intent, int i4, Bundle bundle) {
        return createAppBlockIntent(context, str, i, i2, i3, (List<Intent>) Collections.singletonList(intent), i4, bundle);
    }

    public static Intent createAppBlockIntent(Context context, String str, int i, int i2, int i3, List<Intent> list, Bundle bundle) {
        return createAppBlockIntent(context, str, i, i2, i3, list, -1, bundle);
    }

    public static Intent createAppBlockIntent(Context context, String str, int i, int i2, int i3, List<Intent> list, int i4, Bundle bundle) {
        Intent intent;
        AppInfo appInfo;
        if (TextUtils.isEmpty(str) || context == null || list == null || list.isEmpty()) {
            Log.e("AppJumpBlockTool", "skip for error params!");
            return null;
        }
        if (isRDUorLDU(context)) {
            Log.e("AppJumpBlockTool", "skip for RDU or LDU binary!");
            return null;
        }
        int i5 = Settings.Global.getInt(context.getContentResolver(), "appJumpBlock", -100);
        boolean z = Settings.Global.getInt(context.getContentResolver(), "adb_enabled", -100) == 1;
        StringBuilder sb = new StringBuilder("isEngBinary:");
        boolean z2 = isEngBinary;
        sb.append(z2);
        sb.append(",isAdbOpen=");
        sb.append(z);
        sb.append(",appJumpBlockValue=");
        sb.append(i5);
        Log.i("AppJumpBlockTool", sb.toString());
        if (!z2 && z && i5 == -100) {
            Log.e("AppJumpBlockTool", "skip for USB Debugging opened!");
            return null;
        }
        if (i5 != 1 && i5 != -100) {
            Log.e("AppJumpBlockTool", "skip for Block Function closed!");
            return null;
        }
        Log.i("AppJumpBlockTool", "createAppBlockIntent:sourcePackage=" + str + ",intents=" + list + ",requestCode=" + i4 + ",options=" + bundle);
        StringBuilder sb2 = new StringBuilder("createAppBlockIntent:callingPid=");
        sb2.append(i2);
        sb2.append(",callingUid=");
        sb2.append(i3);
        sb2.append(",userId=");
        sb2.append(i);
        Log.i("AppJumpBlockTool", sb2.toString());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            appInfo = AppInfo.get(context, str);
            intent = null;
        } catch (Throwable th) {
            th = th;
            intent = null;
        }
        try {
            Log.i("AppJumpBlockTool", "sourceAppInfo=" + appInfo);
            if (appInfo != null && !appInfo.packageName.equals("android") && !appInfo.isSystemApp) {
                if (isPlatformOrSamsungSignature(context, appInfo.packageName)) {
                    Log.e("AppJumpBlockTool", "skip for source platform or samsung signature!");
                    return null;
                }
                List<AppInfo> blockedAppList = getBlockedAppList(context, appInfo, list, getAlwaysAllowList(context, str));
                Log.i("AppJumpBlockTool", "blockedAppList:" + Arrays.toString(blockedAppList.toArray()));
                if (blockedAppList.isEmpty()) {
                    Log.i("AppJumpBlockTool", "skip for empty blockedAppList!");
                    return null;
                }
                Log.i("AppJumpBlockTool", "startShowConfirmDialog");
                return buildInterceptIntent(context, i, i2, i3, appInfo, blockedAppList, list, i4, bundle);
            }
            Log.i("AppJumpBlockTool", "skip for android process or system app or samsung app,sourceAppInfo=" + appInfo);
            return null;
        } catch (Throwable th2) {
            th = th2;
            try {
                Log.e("AppJumpBlockTool", "get error!", th);
                return intent;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private static List<String> getAlwaysAllowList(Context context, String str) {
        String string = Settings.System.getString(context.getContentResolver(), SHARE_KEY + str);
        if (TextUtils.isEmpty(string)) {
            return new ArrayList();
        }
        Log.i("AppJumpBlockTool", "alwaysAllowPackageNames:" + string);
        return new ArrayList(Arrays.asList(string.split(NavigationBarInflaterView.GRAVITY_SEPARATOR)));
    }

    public static void resetAlwaysAllowList(Context context, Intent intent) {
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
        if (intExtra == -10000) {
            Log.e("AppJumpBlockTool", "Intent broadcast does not contain user handle: " + intent);
            return;
        }
        Uri data = intent.getData();
        String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
        if (schemeSpecificPart == null) {
            Log.e("AppJumpBlockTool", "Intent broadcast does not contain package name: " + intent);
            return;
        }
        Log.i("AppJumpBlockTool", "resetAlwaysAllowList:[" + schemeSpecificPart + "] for userId:" + intExtra);
        ContentResolver contentResolver = context.getContentResolver();
        StringBuilder sb = new StringBuilder(SHARE_KEY);
        sb.append(schemeSpecificPart);
        Settings.System.putString(contentResolver, sb.toString(), "");
    }

    public static void addAlwaysAllowList(Context context, String str, List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<String> alwaysAllowList = getAlwaysAllowList(context, str);
        alwaysAllowList.addAll(list);
        String join = String.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, alwaysAllowList);
        Log.i("AppJumpBlockTool", "newAllowList:" + join);
        Settings.System.putString(context.getContentResolver(), SHARE_KEY + str, join);
    }

    private static Intent buildInterceptIntent(Context context, int i, int i2, int i3, AppInfo appInfo, List<AppInfo> list, List<Intent> list2, int i4, Bundle bundle) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("android", "com.samsung.android.core.AppBlockDialogActivity"));
        Bundle bundle2 = new Bundle();
        bundle2.putInt(SmLib_IafdConstant.KEY_USER_ID, i);
        bundle2.putInt("callingPid", i2);
        bundle2.putInt("callingUid", i3);
        AppInfo[] appInfoArr = new AppInfo[list.size()];
        list.toArray(appInfoArr);
        bundle2.putParcelableArray("blockedAppList", appInfoArr);
        bundle2.putParcelable("sourceAppInfo", appInfo);
        Intent[] intentArr = new Intent[list2.size()];
        list2.toArray(intentArr);
        bundle2.putParcelableArray("targetIntents", intentArr);
        bundle2.putInt("requestCode", i4);
        bundle2.putParcelable("options", bundle);
        intent.putExtras(bundle2);
        return intent;
    }

    public static List<AppInfo> getBlockedAppList(Context context, AppInfo appInfo, List<Intent> list, List<String> list2) {
        ArrayList arrayList = new ArrayList();
        try {
            Iterator<Intent> it = list.iterator();
            while (it.hasNext()) {
                ArrayList<AppInfo> targetAppInfo = getTargetAppInfo(context, appInfo, it.next());
                if (!targetAppInfo.isEmpty()) {
                    for (AppInfo appInfo2 : targetAppInfo) {
                        if (!list2.contains(appInfo2.packageName)) {
                            arrayList.add(appInfo2);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            Log.e("AppJumpBlockTool", "getBlockedAppList fail!", th);
        }
        List<AppInfo> removeRepeatData = removeRepeatData(arrayList);
        Log.i("AppJumpBlockTool", "getBlockedAppList=" + Arrays.toString(removeRepeatData.toArray()));
        return removeRepeatData;
    }

    private static List<AppInfo> removeRepeatData(List<AppInfo> list) {
        if (list.isEmpty()) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (AppInfo appInfo : list) {
            if (!arrayList2.contains(appInfo.packageName)) {
                arrayList2.add(appInfo.packageName);
                arrayList.add(appInfo);
            }
        }
        return arrayList;
    }

    private static ArrayList<AppInfo> getTargetAppInfo(Context context, AppInfo appInfo, Intent intent) {
        ArrayList<AppInfo> arrayList = new ArrayList<>();
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 131072);
        Log.e("AppJumpBlockTool", "resolveInfoList：" + queryIntentActivities.size());
        ArrayList arrayList2 = new ArrayList();
        Log.i("AppJumpBlockTool", "last launch:" + sLastFromPackage + " >> " + sLastToPackage);
        Iterator<ResolveInfo> it = queryIntentActivities.iterator();
        boolean z = true;
        while (it.hasNext()) {
            AppInfo parse = AppInfo.parse(context, it.next());
            if (parse != null) {
                Log.i("AppJumpBlockTool", appInfo + " want launcher:" + parse);
                if (!arrayList2.contains(parse.packageName)) {
                    arrayList2.add(parse.packageName);
                }
                if (TextUtils.equals(appInfo.packageName, sLastToPackage) && TextUtils.equals(parse.packageName, sLastFromPackage)) {
                    Log.i("AppJumpBlockTool", "skip for app A>B>A ");
                } else if (!isInAllowList(appInfo.packageName, parse.packageName)) {
                    if (appInfo.packageName.equals(parse.packageName) || parse.isSystemApp) {
                        Log.e("AppJumpBlockTool", "skip for jump self or target app is system app!");
                    } else if (isPlatformOrSamsungSignature(context, parse.packageName)) {
                        Log.e("AppJumpBlockTool", "skip for target platform or samsung signature!");
                    } else {
                        arrayList.add(parse);
                    }
                    z = false;
                }
            }
        }
        if (!arrayList2.isEmpty() && !TextUtils.equals(appInfo.packageName, (CharSequence) arrayList2.get(0)) && z) {
            sLastFromPackage = appInfo.packageName;
            sLastToPackage = (String) arrayList2.get(0);
        }
        if (arrayList2.size() <= 1) {
            return arrayList;
        }
        Log.i("AppJumpBlockTool", "skip for resolve package size > 1,size:" + arrayList2.size());
        return new ArrayList<>();
    }

    private static boolean isInAllowList(String str, String str2) {
        String[] split = APP_JUMP_BLOCK_ALLOW_LIST.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str3 : split) {
            if (!str3.isEmpty()) {
                if (str3.startsWith(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
                    arrayList2.add(str3.replaceFirst(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, ""));
                } else {
                    arrayList.add(str3);
                }
            }
        }
        if (arrayList.contains(str)) {
            Log.i("AppJumpBlockTool", "skip from " + str + " for allow list! ");
            return true;
        }
        if (str2 == null || !(arrayList.contains(str2) || arrayList2.contains(str2))) {
            return false;
        }
        Log.i("AppJumpBlockTool", "skip to " + str2 + " for allow list! ");
        return true;
    }

    public static final class AppInfo implements Parcelable {
        public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() { // from class: com.samsung.android.core.AppJumpBlockTool.AppInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppInfo createFromParcel(Parcel parcel) {
                AppInfo appInfo = new AppInfo();
                appInfo.appName = parcel.readString();
                appInfo.isSystemApp = parcel.readByte() != 0;
                appInfo.packageName = parcel.readString();
                appInfo.resolvedInfo = (ResolveInfo) parcel.readParcelable(ResolveInfo.class.getClassLoader());
                return appInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppInfo[] newArray(int i) {
                return new AppInfo[i];
            }
        };
        boolean isSystemApp;
        ResolveInfo resolvedInfo;
        String appName = "";
        String packageName = "";

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static AppInfo parse(Context context, ResolveInfo resolveInfo) {
            if (context == null || resolveInfo == null) {
                return null;
            }
            AppInfo appInfo = new AppInfo();
            ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
            appInfo.appName = ((Object) applicationInfo.loadLabel(context.getPackageManager())) + "";
            appInfo.packageName = applicationInfo.packageName;
            boolean z = true;
            boolean z2 = (applicationInfo.flags & 1) == 1;
            boolean z3 = (applicationInfo.flags & 128) == 1;
            if (!z2 && !z3) {
                z = false;
            }
            appInfo.isSystemApp = z;
            appInfo.resolvedInfo = resolveInfo;
            return appInfo;
        }

        public static AppInfo get(Context context) {
            if (context == null) {
                return null;
            }
            AppInfo appInfo = new AppInfo();
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            appInfo.appName = ((Object) applicationInfo.loadLabel(context.getPackageManager())) + "";
            appInfo.packageName = applicationInfo.packageName;
            boolean z = true;
            boolean z2 = (applicationInfo.flags & 1) == 1;
            boolean z3 = (applicationInfo.flags & 128) == 1;
            if (!z2 && !z3) {
                z = false;
            }
            appInfo.isSystemApp = z;
            return appInfo;
        }

        public static AppInfo get(Context context, String str) {
            ApplicationInfo applicationInfo;
            AppInfo appInfo;
            AppInfo appInfo2 = null;
            try {
                applicationInfo = context.getPackageManager().getPackageInfo(str, 0).applicationInfo;
                appInfo = new AppInfo();
            } catch (Throwable th) {
                th = th;
            }
            try {
                appInfo.appName = ((Object) applicationInfo.loadLabel(context.getPackageManager())) + "";
                appInfo.packageName = applicationInfo.packageName;
                appInfo.isSystemApp = ((applicationInfo.flags & 1) == 1) || ((applicationInfo.flags & 128) == 1);
                return appInfo;
            } catch (Throwable th2) {
                th = th2;
                appInfo2 = appInfo;
                Log.e("AppJumpBlockTool", "get app info fail![" + str + NavigationBarInflaterView.SIZE_MOD_END, th);
                return appInfo2;
            }
        }

        public String toString() {
            return "AppInfo{appName=" + this.appName + ",packageName=" + this.packageName + ",isSystemApp=" + this.isSystemApp + "}" + this.resolvedInfo;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.appName);
            parcel.writeByte(this.isSystemApp ? (byte) 1 : (byte) 0);
            parcel.writeString(this.packageName);
            parcel.writeParcelable(this.resolvedInfo, i);
        }
    }

    private static boolean isPlatformOrSamsungSignature(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        boolean z = packageManager.checkSignatures("android", str) == 0;
        Log.i("AppJumpBlockTool", str + " isPlatformSignature:" + z);
        if (!z) {
            z = packageManager.checkSignatures(SemShareConstants.NEARBY_SHARE_PKG, str) == 0;
            Log.i("AppJumpBlockTool", str + " isGoogleSignature:" + z);
        }
        if (!z) {
            z = packageManager.checkSignatures("com.sec.location.nfwlocationprivacy", str) == 0;
            Log.i("AppJumpBlockTool", str + " isSamsungOfficialSignature-1:" + z);
        }
        if (z) {
            return z;
        }
        boolean z2 = packageManager.checkSignatures("com.sec.clocationservice", str) == 0;
        Log.i("AppJumpBlockTool", str + " isSamsungOfficialSignature-2:" + z2);
        return z2;
    }

    public static boolean isRDUorLDU(Context context) {
        return isShopDemo(context) || isLDUModel();
    }

    public static boolean isShopDemo(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "shopdemo", 0) == 1;
    }

    public static boolean isLDUModel() {
        if (isLDUSKUbinary()) {
            return true;
        }
        return isLDUOLDModel();
    }

    private static boolean isLDUOLDModel() {
        String salesCode = getSalesCode();
        return "PAP".equals(salesCode) || "FOP".equals(salesCode) || "LDU".equals(salesCode);
    }

    private static boolean isLDUSKUbinary() {
        String str = SystemProperties.get("ril.product_code", "");
        if (str.length() < 11) {
            return false;
        }
        return str.charAt(10) == '8' || str.charAt(10) == '9';
    }

    public static String getSalesCode() {
        try {
            String str = SystemProperties.get("persist.omc.sales_code");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            String str2 = SystemProperties.get("ro.csc.sales_code");
            return TextUtils.isEmpty(str2) ? SystemProperties.get("ril.sales_code") : str2;
        } catch (Exception e) {
            Log.e("AppJumpBlockTool", "get getSalesCode fail!", e);
            return "";
        }
    }
}
