package com.samsung.android.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class AppJumpBlockTool {
    private static final String APP_JUMP_BLOCK_ALLOW_LIST =
            "com.samsung.ssd.wolfserver;com.tencent.mm;com.eg.android.AlipayGphone;-com.tencent.mobileqq;-com.sina.weibo";
    public static final String CONTINUE_FLAG =
            "com.samsung.android.core_app_jump_block_continue_flag";
    private static final int DEFAULT_EMPTY_VALUE = -100;
    public static final String IDENTIFY = "com.samsung.android.core_app_jump_block_identify";
    private static final String SHARE_KEY = "com.samsung.android.core_app_jump_block_";
    private static final int STATUS_OPEN = 1;
    public static final String TAG = "AppJumpBlockTool";
    private static String sLastFromPackage = "";
    private static String sLastToPackage = "";
    private static final boolean isEngBinary =
            SystemProperties.get("ro.build.type", "").equals("eng");

    public static Intent createAppBlockIntent(
            Context context,
            String sourcePackage,
            int callingPid,
            int callingUid,
            Intent intent,
            int requestCode,
            Bundle options) {
        return createAppBlockIntent(
                context,
                sourcePackage,
                callingPid,
                callingUid,
                (List<Intent>) Collections.singletonList(intent),
                requestCode,
                options);
    }

    public static Intent createAppBlockIntent(
            Context context,
            String sourcePackage,
            int callingPid,
            int callingUid,
            List<Intent> intents,
            Bundle options) {
        return createAppBlockIntent(
                context, sourcePackage, callingPid, callingUid, intents, -1, options);
    }

    public static Intent createAppBlockIntent(
            Context context,
            String sourcePackage,
            int callingPid,
            int callingUid,
            List<Intent> intents,
            int requestCode,
            Bundle options) {
        AppInfo sourceAppInfo;
        if (!TextUtils.isEmpty(sourcePackage) && context != null && intents != null) {
            if (!intents.isEmpty()) {
                int appJumpBlockValue =
                        Settings.Global.getInt(context.getContentResolver(), "appJumpBlock", -100);
                boolean isAdbOpen =
                        Settings.Global.getInt(context.getContentResolver(), "adb_enabled", -100)
                                == 1;
                Log.i(
                        "AppJumpBlockTool",
                        "isEngBinary:"
                                + isEngBinary
                                + ",isAdbOpen="
                                + isAdbOpen
                                + ",appJumpBlockValue="
                                + appJumpBlockValue);
                if (!isEngBinary && isAdbOpen && appJumpBlockValue == -100) {
                    Log.e("AppJumpBlockTool", "skip for USB Debugging opened!");
                    removeIntentBlockKeys(intents);
                    return null;
                }
                if (appJumpBlockValue != 1 && appJumpBlockValue != -100) {
                    Log.e("AppJumpBlockTool", "skip for Block Function closed!");
                    removeIntentBlockKeys(intents);
                    return null;
                }
                Log.i(
                        "AppJumpBlockTool",
                        "createAppBlockIntent:sourcePackage="
                                + sourcePackage
                                + ",intents="
                                + intents
                                + ",requestCode="
                                + requestCode
                                + ",options="
                                + options);
                Log.i(
                        "AppJumpBlockTool",
                        "createAppBlockIntent:callingPid="
                                + callingPid
                                + ",callingUid="
                                + callingUid);
                boolean isContinue = intents.get(0).getBooleanExtra(CONTINUE_FLAG, false);
                if (isContinue) {
                    Log.i("AppJumpBlockTool", "isContinue:true");
                    removeIntentBlockKeys(intents);
                    return null;
                }
                Intent result = null;
                long token = Binder.clearCallingIdentity();
                try {
                    sourceAppInfo = AppInfo.get(context, sourcePackage);
                    Log.i("AppJumpBlockTool", "sourceAppInfo=" + sourceAppInfo);
                } finally {
                    try {
                        return result;
                    } finally {
                    }
                }
                if (sourceAppInfo != null
                        && !sourceAppInfo.packageName.equals("android")
                        && !isNeedSkipPackage(sourceAppInfo)
                        && !sourceAppInfo.isSystemApp) {
                    if (isPlatformOrSamsungSignature(context, sourceAppInfo.packageName)) {
                        Log.e("AppJumpBlockTool", "skip for source platform or samsung signature!");
                        removeIntentBlockKeys(intents);
                        return null;
                    }
                    List<String> allowList = getAlwaysAllowList(context, sourcePackage);
                    List<AppInfo> blockedAppList =
                            getBlockedAppList(context, sourceAppInfo, intents, allowList);
                    Log.i(
                            "AppJumpBlockTool",
                            "blockedAppList:" + Arrays.toString(blockedAppList.toArray()));
                    if (blockedAppList.isEmpty()) {
                        Log.i("AppJumpBlockTool", "skip for empty blockedAppList!");
                        removeIntentBlockKeys(intents);
                        return null;
                    }
                    Log.i("AppJumpBlockTool", "startShowConfirmDialog");
                    result =
                            buildInterceptIntent(
                                    context,
                                    callingPid,
                                    callingUid,
                                    sourceAppInfo,
                                    blockedAppList,
                                    intents,
                                    requestCode,
                                    options);
                    return result;
                }
                Log.i(
                        "AppJumpBlockTool",
                        "skip for android process or system app or samsung app,sourceAppInfo="
                                + sourceAppInfo);
                removeIntentBlockKeys(intents);
                return null;
            }
        }
        Log.e("AppJumpBlockTool", "skip for error params!");
        return null;
    }

    private static void removeIntentBlockKeys(List<Intent> intents) {
        Intent intent = intents.get(0);
        if (intent.hasExtra(CONTINUE_FLAG)) {
            intent.removeExtra(CONTINUE_FLAG);
        }
        if (intent.hasExtra(IDENTIFY)) {
            intent.removeExtra(IDENTIFY);
        }
    }

    private static boolean isNeedSkipPackage(AppInfo appInfo) {
        return isCtsApp(appInfo.packageName) || isCtsApp(appInfo.resolvedInfo);
    }

    private static boolean isCtsApp(Object data) {
        if (data == null || TextUtils.isEmpty(data.toString())) {
            return false;
        }
        List<String> list = Arrays.asList(data.toString().toLowerCase().split("\\."));
        return list.contains("cts");
    }

    private static List<String> getAlwaysAllowList(Context context, String sourcePackageName) {
        String alwaysAllowPackageNames =
                Settings.System.getString(
                        context.getContentResolver(), SHARE_KEY + sourcePackageName);
        if (TextUtils.isEmpty(alwaysAllowPackageNames)) {
            return new ArrayList();
        }
        Log.i("AppJumpBlockTool", "alwaysAllowPackageNames:" + alwaysAllowPackageNames);
        return new ArrayList(
                Arrays.asList(
                        alwaysAllowPackageNames.split(
                                NavigationBarInflaterView.GRAVITY_SEPARATOR)));
    }

    public static void resetAlwaysAllowList(Context context, String sourcePackageName) {
        Log.i("AppJumpBlockTool", "resetAlwaysAllowList:" + sourcePackageName);
        Settings.System.putString(context.getContentResolver(), SHARE_KEY + sourcePackageName, "");
    }

    public static void addAlwaysAllowList(
            Context context, String sourcePackageName, List<String> packageList) {
        if (packageList == null || packageList.isEmpty()) {
            return;
        }
        List<String> alwaysAllowList = getAlwaysAllowList(context, sourcePackageName);
        alwaysAllowList.addAll(packageList);
        String newAllowList =
                String.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, alwaysAllowList);
        Log.i("AppJumpBlockTool", "newAllowList:" + newAllowList);
        Settings.System.putString(
                context.getContentResolver(), SHARE_KEY + sourcePackageName, newAllowList);
    }

    private static Intent buildInterceptIntent(
            Context context,
            int callingPid,
            int callingUid,
            AppInfo sourceApp,
            List<AppInfo> blockedAppList,
            List<Intent> targetIntents,
            int requestCode,
            Bundle options) {
        Intent interceptIntent = new Intent(BlockDialogReceiver.INTENT_ACTION);
        interceptIntent.setPackage("android");
        Bundle data = new Bundle();
        data.putInt("callingPid", callingPid);
        data.putInt("callingUid", callingUid);
        AppInfo[] blockedAppArr = new AppInfo[blockedAppList.size()];
        blockedAppList.toArray(blockedAppArr);
        data.putParcelableArray("blockedAppList", blockedAppArr);
        data.putParcelable("sourceAppInfo", sourceApp);
        targetIntents.get(0).putExtra(CONTINUE_FLAG, true);
        Intent[] intentsArr = new Intent[targetIntents.size()];
        targetIntents.toArray(intentsArr);
        data.putParcelableArray("targetIntents", intentsArr);
        data.putInt("requestCode", requestCode);
        data.putParcelable("options", options);
        interceptIntent.putExtras(data);
        interceptIntent.addFlags(268435456);
        return interceptIntent;
    }

    public static List<AppInfo> getBlockedAppList(
            Context context,
            AppInfo sourceAppInfo,
            List<Intent> targetIntents,
            List<String> alwaysAllowPackageNames) {
        List<AppInfo> interceptAppList = new ArrayList<>();
        try {
            for (Intent intent : targetIntents) {
                List<AppInfo> appInfoList = getTargetAppInfo(context, sourceAppInfo, intent);
                if (!appInfoList.isEmpty()) {
                    for (AppInfo appInfo : appInfoList) {
                        boolean isAlwaysAllow =
                                alwaysAllowPackageNames.contains(appInfo.packageName);
                        if (!isAlwaysAllow) {
                            interceptAppList.add(appInfo);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            Log.e("AppJumpBlockTool", "getBlockedAppList fail!", e);
        }
        List<AppInfo> interceptAppList2 = removeRepeatData(interceptAppList);
        Log.i(
                "AppJumpBlockTool",
                "getBlockedAppList=" + Arrays.toString(interceptAppList2.toArray()));
        return interceptAppList2;
    }

    private static List<AppInfo> removeRepeatData(List<AppInfo> dataList) {
        if (dataList.isEmpty()) {
            return dataList;
        }
        List<AppInfo> resultList = new ArrayList<>();
        List<String> packageNameList = new ArrayList<>();
        for (AppInfo appInfo : dataList) {
            if (!packageNameList.contains(appInfo.packageName)) {
                packageNameList.add(appInfo.packageName);
                resultList.add(appInfo);
            }
        }
        return resultList;
    }

    private static ArrayList<AppInfo> getTargetAppInfo(
            Context context, AppInfo sourceAppInfo, Intent intent) {
        ArrayList<AppInfo> appInfoList = new ArrayList<>();
        List<ResolveInfo> resolveInfoList =
                context.getPackageManager().queryIntentActivities(intent, 131072);
        Log.e("AppJumpBlockTool", "resolveInfoList：" + resolveInfoList.size());
        List<String> resolvePackageList = new ArrayList<>();
        Log.i("AppJumpBlockTool", "last launch:" + sLastFromPackage + " >> " + sLastToPackage);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            AppInfo targetAppInfo = AppInfo.parse(context, resolveInfo);
            if (targetAppInfo != null) {
                Log.i("AppJumpBlockTool", sourceAppInfo + " want launcher:" + targetAppInfo);
                if (!resolvePackageList.contains(targetAppInfo.packageName)) {
                    resolvePackageList.add(targetAppInfo.packageName);
                }
                if (TextUtils.equals(sourceAppInfo.packageName, sLastToPackage)
                        && TextUtils.equals(targetAppInfo.packageName, sLastFromPackage)) {
                    Log.i("AppJumpBlockTool", "skip for app A>B>A ");
                } else if (!isInAllowList(sourceAppInfo.packageName, targetAppInfo.packageName)) {
                    if (sourceAppInfo.packageName.equals(targetAppInfo.packageName)
                            || isNeedSkipPackage(targetAppInfo)
                            || targetAppInfo.isSystemApp) {
                        Log.e(
                                "AppJumpBlockTool",
                                "skip for jump self or target app is system app!");
                    } else if (isPlatformOrSamsungSignature(context, targetAppInfo.packageName)) {
                        Log.e("AppJumpBlockTool", "skip for target platform or samsung signature!");
                    } else {
                        appInfoList.add(targetAppInfo);
                    }
                }
            }
        }
        if (!resolvePackageList.isEmpty()
                && !TextUtils.equals(sourceAppInfo.packageName, resolvePackageList.get(0))) {
            sLastFromPackage = sourceAppInfo.packageName;
            sLastToPackage = resolvePackageList.get(0);
        }
        if (resolvePackageList.size() > 1) {
            Log.i(
                    "AppJumpBlockTool",
                    "skip for resolve package size > 1,size:" + resolvePackageList.size());
            return new ArrayList<>();
        }
        return appInfoList;
    }

    private static boolean isInAllowList(String fromPackageName, String toPackageName) {
        Log.i("AppJumpBlockTool", "allowListStr：" + APP_JUMP_BLOCK_ALLOW_LIST);
        String[] packageArray =
                APP_JUMP_BLOCK_ALLOW_LIST.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        List<String> allowList = new ArrayList<>();
        List<String> singleAllowList = new ArrayList<>();
        for (String packageName : packageArray) {
            if (!packageName.isEmpty()) {
                if (packageName.startsWith(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
                    singleAllowList.add(
                            packageName.replaceFirst(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, ""));
                } else {
                    allowList.add(packageName);
                }
            }
        }
        if (allowList.contains(fromPackageName)) {
            Log.i("AppJumpBlockTool", "skip from " + fromPackageName + " for allow list! ");
            return true;
        }
        if (toPackageName == null
                || !(allowList.contains(toPackageName)
                        || singleAllowList.contains(toPackageName))) {
            return false;
        }
        Log.i("AppJumpBlockTool", "skip to " + toPackageName + " for allow list! ");
        return true;
    }

    public static final class AppInfo implements Parcelable {
        public static final Parcelable.Creator<AppInfo> CREATOR =
                new Parcelable.Creator<
                        AppInfo>() { // from class:
                                     // com.samsung.android.core.AppJumpBlockTool.AppInfo.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public AppInfo createFromParcel(Parcel in) {
                        AppInfo appInfo = new AppInfo();
                        appInfo.appName = in.readString();
                        appInfo.isSystemApp = in.readByte() != 0;
                        appInfo.packageName = in.readString();
                        return appInfo;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public AppInfo[] newArray(int size) {
                        return new AppInfo[size];
                    }
                };
        boolean isSystemApp;
        ResolveInfo resolvedInfo;
        String appName = "";
        String packageName = "";

        public static AppInfo parse(Context context, ResolveInfo resolveInfo) {
            if (context == null || resolveInfo == null) {
                return null;
            }
            AppInfo appInfo = new AppInfo();
            ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
            appInfo.appName =
                    ((Object) applicationInfo.loadLabel(context.getPackageManager())) + "";
            appInfo.packageName = applicationInfo.packageName;
            boolean z = true;
            boolean isSysApp = (applicationInfo.flags & 1) == 1;
            boolean isSysUpd = (applicationInfo.flags & 128) == 1;
            if (!isSysApp && !isSysUpd) {
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
            appInfo.appName =
                    ((Object) applicationInfo.loadLabel(context.getPackageManager())) + "";
            appInfo.packageName = applicationInfo.packageName;
            boolean z = true;
            boolean isSysApp = (applicationInfo.flags & 1) == 1;
            boolean isSysUpd = (applicationInfo.flags & 128) == 1;
            if (!isSysApp && !isSysUpd) {
                z = false;
            }
            appInfo.isSystemApp = z;
            return appInfo;
        }

        public static AppInfo get(Context context, String packageName) {
            AppInfo appInfo = null;
            try {
                PackageInfo packageInfo =
                        context.getPackageManager().getPackageInfo(packageName, 0);
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                appInfo = new AppInfo();
                appInfo.appName =
                        ((Object) applicationInfo.loadLabel(context.getPackageManager())) + "";
                appInfo.packageName = applicationInfo.packageName;
                boolean isSysApp = (applicationInfo.flags & 1) == 1;
                boolean isSysUpd = (applicationInfo.flags & 128) == 1;
                appInfo.isSystemApp = isSysApp || isSysUpd;
            } catch (Throwable e) {
                Log.e(
                        "AppJumpBlockTool",
                        "get app info fail!["
                                + packageName
                                + NavigationBarInflaterView.SIZE_MOD_END,
                        e);
            }
            return appInfo;
        }

        public String toString() {
            return "AppInfo{appName="
                    + this.appName
                    + ",packageName="
                    + this.packageName
                    + ",isSystemApp="
                    + this.isSystemApp
                    + "}"
                    + this.resolvedInfo;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.appName);
            parcel.writeByte(this.isSystemApp ? (byte) 1 : (byte) 0);
            parcel.writeString(this.packageName);
        }
    }

    public static void registerBroadcast(Context context, BlockDialogReceiver receiver) {
        context.getApplicationContext()
                .registerReceiver(
                        receiver, new IntentFilter(BlockDialogReceiver.BROADCAST_ACTION), 2);
    }

    public static class BlockDialogReceiver extends BroadcastReceiver {
        public static final String BROADCAST_ACTION =
                "com.samsung.intent.action.APP_JUMP_BLOCK_DIALOG_RESULT";
        public static final String INTENT_ACTION =
                "com.samsung.intent.action.APP_JUMP_BLOCK_DIALOG";
        public static final String RESULT_ALLOW = "Allow";
        public static final String RESULT_CANCEL = "Cancel";
        private boolean isResultAllow = false;
        private int sourceRequestCode = -1;
        private Bundle sourceOptions = null;
        private List<Intent> sourceIntents = new ArrayList();
        private int identify = hashCode();

        public BlockDialogReceiver() {
            Log.i("AppJumpBlockTool", "identify:" + this.identify);
        }

        public int getIdentify() {
            return this.identify;
        }

        public final boolean isResultAllow() {
            return this.isResultAllow;
        }

        public final int getSourceRequestCode() {
            return this.sourceRequestCode;
        }

        public final Bundle getSourceOptions() {
            return this.sourceOptions;
        }

        public final List<Intent> getSourceIntents() {
            return this.sourceIntents;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Bundle data;
            try {
                Log.i(
                        "AppJumpBlockTool",
                        "BlockDialogReceiver context：" + context + ",intent=" + intent);
                data = intent.getExtras();
            } catch (Throwable e) {
                Log.e("AppJumpBlockTool", "receive broadcast fail", e);
            }
            if (data == null) {
                return;
            }
            String reason = intent.getStringExtra("reason");
            Log.i("AppJumpBlockTool", "reason=" + reason);
            this.sourceIntents.addAll(
                    Arrays.asList(
                            (Intent[]) data.getParcelableArray("targetIntents", Intent.class)));
            Log.i("AppJumpBlockTool", "intents:" + this.sourceIntents);
            int receiveIdentify =
                    this.sourceIntents.get(0).getIntExtra(AppJumpBlockTool.IDENTIFY, 0);
            Log.i("AppJumpBlockTool", "receiveIdentify:" + receiveIdentify);
            Log.i("AppJumpBlockTool", "identify:" + this.identify);
            if (this.identify != receiveIdentify) {
                Log.e("AppJumpBlockTool", "the broadcast not for this receiver!");
                return;
            }
            this.sourceRequestCode = data.getInt("requestCode", -1);
            Log.i("AppJumpBlockTool", "requestCode:" + this.sourceRequestCode);
            this.sourceOptions = (Bundle) data.getParcelable("options");
            Log.i("AppJumpBlockTool", "options:" + this.sourceOptions);
            this.isResultAllow = RESULT_ALLOW.equals(reason);
            context.unregisterReceiver(this);
        }
    }

    private static boolean isPlatformOrSamsungSignature(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        boolean sameSignature = packageManager.checkSignatures("android", packageName) == 0;
        Log.i("AppJumpBlockTool", packageName + " isPlatformSignature:" + sameSignature);
        if (!sameSignature) {
            sameSignature =
                    packageManager.checkSignatures(SemShareConstants.NEARBY_SHARE_PKG, packageName)
                            == 0;
            Log.i("AppJumpBlockTool", packageName + " isGoogleSignature:" + sameSignature);
        }
        if (!sameSignature) {
            sameSignature =
                    packageManager.checkSignatures(
                                    "com.sec.location.nfwlocationprivacy", packageName)
                            == 0;
            Log.i(
                    "AppJumpBlockTool",
                    packageName + " isSamsungOfficialSignature-1:" + sameSignature);
        }
        if (!sameSignature) {
            boolean sameSignature2 =
                    packageManager.checkSignatures("com.sec.clocationservice", packageName) == 0;
            Log.i(
                    "AppJumpBlockTool",
                    packageName + " isSamsungOfficialSignature-2:" + sameSignature2);
            return sameSignature2;
        }
        return sameSignature;
    }
}
