package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.BroadcastOptions;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.IntArray;
import android.util.Pair;
import android.util.SparseArray;
import com.android.internal.util.ArrayUtils;
import com.android.server.DualAppManagerService;
import com.android.server.LocalServices;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.desktopmode.DesktopModeUtils;
import com.samsung.android.server.pm.monetization.MonetizationUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final class BroadcastHelper {
    public static final String[] INSTANT_APP_BROADCAST_PERMISSION = {"android.permission.ACCESS_INSTANT_APPS"};
    public static final String vzwTag = SemCscFeature.getInstance().getString("CscFeature_COMMON_ConfigImplicitBroadcasts");
    public final ActivityManagerInternal mAmInternal;
    public final Context mContext;
    public MonetizationUtils mMonetizationUtils;
    public final UserManagerInternal mUmInternal;

    public BroadcastHelper(PackageManagerServiceInjector packageManagerServiceInjector) {
        this.mUmInternal = packageManagerServiceInjector.getUserManagerInternal();
        this.mAmInternal = packageManagerServiceInjector.getActivityManagerInternal();
        Context context = packageManagerServiceInjector.getContext();
        this.mContext = context;
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            this.mMonetizationUtils = MonetizationUtils.getInstance(context);
        }
    }

    public final String getMVSPackageName() {
        String str = "VZW".equals(vzwTag) ? "com.verizon.mips.services" : "com.verizon.loginengine.unbranded";
        try {
            if ((this.mContext.getPackageManager().getPackageInfo(str, PackageManager.PackageInfoFlags.of(0L)).applicationInfo.flags & 129) != 0) {
                return str;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public final String getDSPackageName() {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SECURITY_CONFIG_DEVICEMONITOR_PACKAGE_NAME", "com.samsung.android.sm.devicesecurity");
        try {
            if ((this.mContext.getPackageManager().getPackageInfo(string, PackageManager.PackageInfoFlags.of(0L)).applicationInfo.flags & 129) != 0) {
                return string;
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public void sendPackageBroadcast(String str, String str2, Bundle bundle, int i, String str3, IIntentReceiver iIntentReceiver, int[] iArr, int[] iArr2, SparseArray sparseArray, BiFunction biFunction, Bundle bundle2) {
        try {
            IActivityManager service = ActivityManager.getService();
            if (service == null) {
                return;
            }
            int[] runningUserIds = iArr == null ? service.getRunningUserIds() : iArr;
            if (ArrayUtils.isEmpty(iArr2)) {
                doSendBroadcast(str, str2, bundle, i, str3, iIntentReceiver, runningUserIds, false, sparseArray, biFunction, bundle2);
            } else {
                doSendBroadcast(str, str2, bundle, i, str3, iIntentReceiver, iArr2, true, null, null, bundle2);
            }
        } catch (RemoteException unused) {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01f7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01d6  */
    /* JADX WARN: Type inference failed for: r12v11 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void doSendBroadcast(String str, String str2, Bundle bundle, int i, String str3, IIntentReceiver iIntentReceiver, int[] iArr, boolean z, SparseArray sparseArray, BiFunction biFunction, Bundle bundle2) {
        boolean z2;
        int i2;
        Object obj;
        Object obj2;
        int i3;
        String dSPackageName;
        String str4;
        String mVSPackageName;
        ?? r12;
        String str5;
        int[] iArr2 = iArr;
        int length = iArr2.length;
        boolean z3 = false;
        int i4 = 0;
        while (i4 < length) {
            int i5 = iArr2[i4];
            Intent intent = new Intent(str, str2 != null ? Uri.fromParts("package", str2, null) : null);
            String[] strArr = z ? INSTANT_APP_BROADCAST_PERMISSION : null;
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if (str3 != null) {
                intent.setPackage(str3);
            }
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            if (intExtra >= 0 && UserHandle.getUserId(intExtra) != i5) {
                intent.putExtra("android.intent.extra.UID", UserHandle.getUid(i5, UserHandle.getAppId(intExtra)));
            }
            if (sparseArray != null && "android".equals(str3)) {
                intent.putExtra("android.intent.extra.VISIBILITY_ALLOW_LIST", (int[]) sparseArray.get(i5));
            }
            intent.putExtra("android.intent.extra.user_handle", i5);
            intent.addFlags(i | 67108864);
            if (this.mAmInternal.isModernQueueEnabled()) {
                z2 = z3;
            } else {
                z2 = iIntentReceiver != null ? true : z3;
            }
            this.mAmInternal.broadcastIntent(intent, iIntentReceiver, strArr, z2, i5, sparseArray == null ? null : (int[]) sparseArray.get(i5), biFunction, bundle2);
            if (str.equals("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE") || str.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) {
                String[] stringArray = bundle.getStringArray("android.intent.extra.changed_package_list");
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" : user ");
                i2 = i5;
                sb.append(i2);
                PackageManagerServiceUtils.logCriticalInfo(4, sb.toString());
                if (stringArray != null) {
                    PackageManagerServiceUtils.logCriticalInfo(4, "pkgs: " + Arrays.toString(stringArray));
                }
            } else {
                i2 = i5;
            }
            if (str3 == null && (str.equals("android.intent.action.PACKAGE_REMOVED") || str.equals("android.intent.action.PACKAGE_ADDED"))) {
                DualAppManagerService.sendBroadcastCustomIntent(this.mContext, i2, (Intent) intent.clone());
            }
            if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED && this.mMonetizationUtils.isGalaxyStoreFeatureEnabled() && str.equals("android.intent.action.PACKAGE_REMOVED") && i2 == 0 && "com.sec.android.app.samsungapps".equals(str3) && str2 != null) {
                this.mMonetizationUtils.updateSettingsForMonetization(str2, true, z3, z3);
            }
            if (str3 == null) {
                String str6 = vzwTag;
                if (("VZW".equals(str6) || "VZW_OPEN".equals(str6)) && (mVSPackageName = getMVSPackageName()) != null) {
                    switch (str.hashCode()) {
                        case -810471698:
                            if (str.equals("android.intent.action.PACKAGE_REPLACED")) {
                                r12 = z3;
                                break;
                            }
                            r12 = -1;
                            break;
                        case 525384130:
                            if (str.equals("android.intent.action.PACKAGE_REMOVED")) {
                                r12 = 1;
                                break;
                            }
                            r12 = -1;
                            break;
                        case 1544582882:
                            if (str.equals("android.intent.action.PACKAGE_ADDED")) {
                                r12 = 2;
                                break;
                            }
                            r12 = -1;
                            break;
                        default:
                            r12 = -1;
                            break;
                    }
                    switch (r12) {
                        case 0:
                            str5 = "com.verizon.provider.PACKAGE_REPLACED";
                            break;
                        case 1:
                            str5 = "com.verizon.provider.PACKAGE_REMOVED";
                            break;
                        case 2:
                            str5 = "com.verizon.provider.PACKAGE_ADDED";
                            break;
                        default:
                            str5 = null;
                            break;
                    }
                    if (str5 != null) {
                        Intent intent2 = (Intent) intent.clone();
                        intent2.setAction(str5);
                        intent2.setPackage(mVSPackageName);
                        obj = "android.intent.action.PACKAGE_REMOVED";
                        obj2 = "android.intent.action.PACKAGE_ADDED";
                        i3 = i2;
                        this.mAmInternal.broadcastIntent(intent2, (IIntentReceiver) null, (String[]) null, false, i2, (int[]) null, (BiFunction) null, (Bundle) null);
                        if (str3 == null && (dSPackageName = getDSPackageName()) != null) {
                            str4 = str.equals(obj) ? !str.equals(obj2) ? null : "com.samsung.android.sm.devicesecurity.PACKAGE_ADDED" : "com.samsung.android.sm.devicesecurity.PACKAGE_REMOVED";
                            if (str4 == null) {
                                Intent intent3 = (Intent) intent.clone();
                                intent3.setPackage(dSPackageName);
                                intent3.setAction(str4);
                                this.mAmInternal.broadcastIntent(intent3, (IIntentReceiver) null, (String[]) null, false, i3, (int[]) null, (BiFunction) null, (Bundle) null);
                            }
                        }
                        i4++;
                        iArr2 = iArr;
                        z3 = false;
                    }
                }
            }
            obj = "android.intent.action.PACKAGE_REMOVED";
            obj2 = "android.intent.action.PACKAGE_ADDED";
            i3 = i2;
            if (str3 == null) {
                if (str.equals(obj)) {
                }
                if (str4 == null) {
                }
            }
            i4++;
            iArr2 = iArr;
            z3 = false;
        }
    }

    public void sendResourcesChangedBroadcast(Supplier supplier, boolean z, boolean z2, String[] strArr, int[] iArr) {
        sendResourcesChangedBroadcast(supplier, z, z2, strArr, iArr, null);
    }

    public void sendResourcesChangedBroadcast(final Supplier supplier, boolean z, boolean z2, String[] strArr, int[] iArr, IIntentReceiver iIntentReceiver) {
        if (ArrayUtils.isEmpty(strArr) || ArrayUtils.isEmpty(iArr)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        if (z2) {
            bundle.putBoolean("android.intent.extra.REPLACING", z2);
        }
        sendPackageBroadcast(z ? "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE" : "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE", null, bundle, 0, null, iIntentReceiver, null, null, null, new BiFunction() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                Bundle lambda$sendResourcesChangedBroadcast$0;
                lambda$sendResourcesChangedBroadcast$0 = BroadcastHelper.lambda$sendResourcesChangedBroadcast$0(supplier, (Integer) obj, (Bundle) obj2);
                return lambda$sendResourcesChangedBroadcast$0;
            }
        }, null);
    }

    public static /* synthetic */ Bundle lambda$sendResourcesChangedBroadcast$0(Supplier supplier, Integer num, Bundle bundle) {
        return filterExtrasChangedPackageList((Computer) supplier.get(), num.intValue(), bundle);
    }

    public void sendBootCompletedBroadcastToSystemApp(String str, boolean z, int i) {
        if (this.mUmInternal.isUserRunning(i)) {
            IActivityManager service = ActivityManager.getService();
            try {
                Intent intent = new Intent("android.intent.action.LOCKED_BOOT_COMPLETED").setPackage(str);
                intent.putExtra("android.intent.extra.user_handle", i);
                if (z) {
                    intent.addFlags(32);
                }
                String[] strArr = {"android.permission.RECEIVE_BOOT_COMPLETED"};
                BroadcastOptions temporaryAppAllowlistBroadcastOptions = getTemporaryAppAllowlistBroadcastOptions(202);
                service.broadcastIntentWithFeature((IApplicationThread) null, (String) null, intent, (String) null, (IIntentReceiver) null, 0, (String) null, (Bundle) null, strArr, (String[]) null, (String[]) null, -1, temporaryAppAllowlistBroadcastOptions.toBundle(), false, false, i);
                if (this.mUmInternal.isUserUnlockingOrUnlocked(i)) {
                    Intent intent2 = new Intent("android.intent.action.BOOT_COMPLETED").setPackage(str);
                    intent2.putExtra("android.intent.extra.user_handle", i);
                    if (z) {
                        intent2.addFlags(32);
                    }
                    service.broadcastIntentWithFeature((IApplicationThread) null, (String) null, intent2, (String) null, (IIntentReceiver) null, 0, (String) null, (Bundle) null, strArr, (String[]) null, (String[]) null, -1, temporaryAppAllowlistBroadcastOptions.toBundle(), false, false, i);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public BroadcastOptions getTemporaryAppAllowlistBroadcastOptions(int i) {
        ActivityManagerInternal activityManagerInternal = this.mAmInternal;
        long bootTimeTempAllowListDuration = activityManagerInternal != null ? activityManagerInternal.getBootTimeTempAllowListDuration() : 10000L;
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(bootTimeTempAllowListDuration, 0, i, "");
        return makeBasic;
    }

    public void sendPackageChangedBroadcast(String str, boolean z, ArrayList arrayList, int i, String str2, int[] iArr, int[] iArr2, SparseArray sparseArray) {
        Bundle bundle = new Bundle(4);
        bundle.putString("android.intent.extra.changed_component_name", (String) arrayList.get(0));
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        bundle.putStringArray("android.intent.extra.changed_component_name_list", strArr);
        bundle.putBoolean("android.intent.extra.DONT_KILL_APP", z);
        bundle.putInt("android.intent.extra.UID", i);
        if (str2 != null) {
            bundle.putString("android.intent.extra.REASON", str2);
        }
        sendPackageBroadcast("android.intent.action.PACKAGE_CHANGED", str, bundle, !arrayList.contains(str) ? 1073741824 : 0, null, null, iArr, iArr2, sparseArray, null, null);
    }

    public static void sendDeviceCustomizationReadyBroadcast() {
        Intent intent = new Intent("android.intent.action.DEVICE_CUSTOMIZATION_READY");
        intent.setFlags(16777216);
        try {
            ActivityManager.getService().broadcastIntentWithFeature((IApplicationThread) null, (String) null, intent, (String) null, (IIntentReceiver) null, 0, (String) null, (Bundle) null, new String[]{"android.permission.RECEIVE_DEVICE_CUSTOMIZATION_READY"}, (String[]) null, (String[]) null, -1, (Bundle) null, false, false, -1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendSessionCommitBroadcast(PackageInstaller.SessionInfo sessionInfo, int i, int i2, ComponentName componentName, String str) {
        if (componentName != null) {
            this.mContext.sendBroadcastAsUser(new Intent("android.content.pm.action.SESSION_COMMITTED").putExtra("android.content.pm.extra.SESSION", sessionInfo).putExtra("android.intent.extra.USER", UserHandle.of(i)).setPackage(componentName.getPackageName()), UserHandle.of(i2));
        }
        if (str != null) {
            this.mContext.sendBroadcastAsUser(new Intent("android.content.pm.action.SESSION_COMMITTED").putExtra("android.content.pm.extra.SESSION", sessionInfo).putExtra("android.intent.extra.USER", UserHandle.of(i)).setPackage(str), UserHandle.of(i2));
        }
        DesktopModeUtils.sendSessionCommitBroadcastToDesktopLauncherIfNeeded(this.mContext, componentName, "com.sec.android.app.desktoplauncher", sessionInfo, i, new Supplier() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                Boolean lambda$sendSessionCommitBroadcast$1;
                lambda$sendSessionCommitBroadcast$1 = BroadcastHelper.lambda$sendSessionCommitBroadcast$1();
                return lambda$sendSessionCommitBroadcast$1;
            }
        });
    }

    public static /* synthetic */ Boolean lambda$sendSessionCommitBroadcast$1() {
        DesktopModeManagerInternal desktopModeManagerInternal = (DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class);
        return Boolean.valueOf(desktopModeManagerInternal != null && desktopModeManagerInternal.isDesktopModeEnablingOrEnabled());
    }

    public void sendPreferredActivityChangedBroadcast(int i) {
        IActivityManager service = ActivityManager.getService();
        if (service == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED");
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.addFlags(67108864);
        try {
            service.broadcastIntentWithFeature((IApplicationThread) null, (String) null, intent, (String) null, (IIntentReceiver) null, 0, (String) null, (Bundle) null, (String[]) null, (String[]) null, (String[]) null, -1, (Bundle) null, false, false, i);
        } catch (RemoteException unused) {
        }
    }

    public void sendPackageAddedForNewUsers(String str, int i, int[] iArr, int[] iArr2, int i2, SparseArray sparseArray) {
        Bundle bundle = new Bundle(1);
        bundle.putInt("android.intent.extra.UID", UserHandle.getUid(ArrayUtils.isEmpty(iArr) ? iArr2[0] : iArr[0], i));
        bundle.putInt("android.content.pm.extra.DATA_LOADER_TYPE", i2);
        sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 0, null, null, iArr, iArr2, sparseArray, null, null);
        if (isPrivacySafetyLabelChangeNotificationsEnabled(this.mContext)) {
            sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 0, this.mContext.getPackageManager().getPermissionControllerPackageName(), null, iArr, iArr2, sparseArray, null, null);
        }
    }

    public void sendFirstLaunchBroadcast(String str, String str2, int[] iArr, int[] iArr2) {
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            if ("MONETIZED_APP_OPENED".equals(str2)) {
                this.mMonetizationUtils.updateSettingsForMonetization(str, true, true, true);
                this.mMonetizationUtils.sendChangePackageIconInfo(str, iArr);
                return;
            }
            sendPackageBroadcast("android.intent.action.PACKAGE_FIRST_LAUNCH", str, null, 0, str2, null, iArr, iArr2, null, null, null);
            if (this.mMonetizationUtils.isGalaxyStoreFeatureEnabled() && "com.sec.android.app.samsungapps".equals(str2) && iArr[0] == 0) {
                this.mMonetizationUtils.updateSettingsForMonetization(str, true, false, false);
                this.mMonetizationUtils.sendChangePackageIconInfo(str, iArr);
                return;
            }
            return;
        }
        sendPackageBroadcast("android.intent.action.PACKAGE_FIRST_LAUNCH", str, null, 0, str2, null, iArr, iArr2, null, null, null);
    }

    public static Bundle filterExtrasChangedPackageList(Computer computer, int i, Bundle bundle) {
        if (UserHandle.isCore(i)) {
            return bundle;
        }
        String[] stringArray = bundle.getStringArray("android.intent.extra.changed_package_list");
        if (ArrayUtils.isEmpty(stringArray)) {
            return bundle;
        }
        Pair filterPackages = filterPackages(computer, stringArray, bundle.getIntArray("android.intent.extra.changed_uid_list"), i, bundle.getInt("android.intent.extra.user_handle", UserHandle.getUserId(i)));
        if (ArrayUtils.isEmpty((String[]) filterPackages.first)) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        bundle2.putStringArray("android.intent.extra.changed_package_list", (String[]) filterPackages.first);
        bundle2.putIntArray("android.intent.extra.changed_uid_list", (int[]) filterPackages.second);
        return bundle2;
    }

    public static boolean isPrivacySafetyLabelChangeNotificationsEnabled(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return (!DeviceConfig.getBoolean("privacy", "safety_label_change_notifications_enabled", true) || packageManager.hasSystemFeature("android.hardware.type.automotive") || packageManager.hasSystemFeature("android.software.leanback") || packageManager.hasSystemFeature("android.hardware.type.watch")) ? false : true;
    }

    public static Pair filterPackages(Computer computer, String[] strArr, int[] iArr, int i, int i2) {
        int length = strArr.length;
        int length2 = !ArrayUtils.isEmpty(iArr) ? iArr.length : 0;
        ArrayList arrayList = new ArrayList(length);
        int[] iArr2 = null;
        IntArray intArray = length2 > 0 ? new IntArray(length2) : null;
        for (int i3 = 0; i3 < length; i3++) {
            String str = strArr[i3];
            if (!computer.shouldFilterApplication(computer.getPackageStateInternal(str), i, i2)) {
                arrayList.add(str);
                if (intArray != null && i3 < length2) {
                    intArray.add(iArr[i3]);
                }
            }
        }
        String[] strArr2 = arrayList.size() > 0 ? (String[]) arrayList.toArray(new String[arrayList.size()]) : null;
        if (intArray != null && intArray.size() > 0) {
            iArr2 = intArray.toArray();
        }
        return new Pair(strArr2, iArr2);
    }
}
