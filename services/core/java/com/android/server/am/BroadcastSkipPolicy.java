package com.android.server.am;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.RemoteException;
import android.os.UserHandle;
import android.permission.IPermissionManager;
import android.permission.PermissionManager;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats;
import com.android.server.firewall.IntentFirewall;
import java.util.Objects;

public final class BroadcastSkipPolicy {
    public PermissionManager mPermissionManager;
    public final ActivityManagerService mService;

    public BroadcastSkipPolicy(ActivityManagerService activityManagerService) {
        Objects.requireNonNull(activityManagerService);
        this.mService = activityManagerService;
    }

    public static String broadcastDescription(BroadcastRecord broadcastRecord, ComponentName componentName) {
        return broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") to " + componentName.flattenToShortString();
    }

    public final boolean hasPermissionForDataDelivery(String str, String str2, AttributionSource... attributionSourceArr) {
        if (this.mPermissionManager == null) {
            this.mPermissionManager = (PermissionManager) this.mService.mContext.getSystemService(PermissionManager.class);
        }
        PermissionManager permissionManager = this.mPermissionManager;
        if (permissionManager == null) {
            return false;
        }
        for (AttributionSource attributionSource : attributionSourceArr) {
            if (permissionManager.checkPermissionForDataDelivery(str, attributionSource, str2) != 0) {
                return false;
            }
        }
        return true;
    }

    public final boolean noteOpForManifestReceiverInner(int i, BroadcastRecord broadcastRecord, ResolveInfo resolveInfo, ComponentName componentName, String str) {
        AppOpsManager appOpsManager$1 = this.mService.getAppOpsManager$1();
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        if (appOpsManager$1.noteOpNoThrow(i, activityInfo.applicationInfo.uid, activityInfo.packageName, str, "Broadcast delivered to " + resolveInfo.activityInfo.name) == 0) {
            return true;
        }
        StringBuilder sb = new StringBuilder("Appop Denial: receiving ");
        sb.append(broadcastRecord.intent);
        sb.append(" to ");
        sb.append(componentName.flattenToShortString());
        sb.append(" requires appop ");
        sb.append(AppOpsManager.opToName(i));
        sb.append(" due to sender ");
        sb.append(broadcastRecord.callerPackage);
        sb.append(" (uid ");
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(sb, broadcastRecord.callingUid, ")", "BroadcastQueue");
        return false;
    }

    public final boolean requestStartTargetPermissionsReviewIfNeededLocked(BroadcastRecord broadcastRecord, String str, final int i) {
        ActivityManagerService activityManagerService = this.mService;
        if (!activityManagerService.getPackageManagerInternal().isPermissionsReviewRequired(i, str)) {
            return true;
        }
        ProcessRecord processRecord = broadcastRecord.callerApp;
        if ((processRecord == null || processRecord.mState.mSetSchedGroup > 0) && broadcastRecord.intent.getComponent() != null) {
            PendingIntentController pendingIntentController = activityManagerService.mPendingIntentController;
            String str2 = broadcastRecord.callerPackage;
            String str3 = broadcastRecord.callerFeatureId;
            int i2 = broadcastRecord.callingUid;
            int i3 = broadcastRecord.userId;
            Intent intent = broadcastRecord.intent;
            PendingIntentRecord intentSender = pendingIntentController.getIntentSender(1, i2, i3, 0, 1409286144, null, null, str2, str3, null, new Intent[]{intent}, new String[]{intent.resolveType(activityManagerService.mContext.getContentResolver())});
            final Intent intent2 = new Intent("android.intent.action.REVIEW_PERMISSIONS");
            intent2.addFlags(411041792);
            intent2.putExtra("android.intent.extra.PACKAGE_NAME", str);
            intent2.putExtra("android.intent.extra.INTENT", new IntentSender(intentSender));
            activityManagerService.mHandler.post(new Runnable() { // from class: com.android.server.am.BroadcastSkipPolicy.1
                @Override // java.lang.Runnable
                public final void run() {
                    BroadcastSkipPolicy.this.mService.mContext.startActivityAsUser(intent2, new UserHandle(i));
                }
            });
        } else {
            Slog.w("BroadcastQueue", AccountManagerService$$ExternalSyntheticOutline0.m(i, "u", " Receiving a broadcast in package", str, " requires a permissions review"));
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v35 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6, types: [int] */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15, types: [int] */
    /* JADX WARN: Type inference failed for: r4v66 */
    public final String shouldSkipMessage(BroadcastRecord broadcastRecord, Object obj) {
        ComponentName componentName;
        boolean z;
        boolean z2;
        ResolveInfo resolveInfo;
        String[] strArr;
        AttributionSource[] attributionSourceArr;
        String str;
        int i;
        ActivityManagerService activityManagerService;
        int permissionToOpCode;
        String str2;
        boolean z3 = obj instanceof BroadcastFilter;
        ActivityManagerService activityManagerService2 = this.mService;
        if (!z3) {
            ResolveInfo resolveInfo2 = (ResolveInfo) obj;
            BroadcastOptions broadcastOptions = broadcastRecord.options;
            ActivityInfo activityInfo = resolveInfo2.activityInfo;
            componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
            if (broadcastOptions != null && (resolveInfo2.activityInfo.applicationInfo.targetSdkVersion < broadcastOptions.getMinManifestReceiverApiLevel() || resolveInfo2.activityInfo.applicationInfo.targetSdkVersion > broadcastOptions.getMaxManifestReceiverApiLevel())) {
                return "Target SDK mismatch: receiver " + resolveInfo2.activityInfo + " targets " + resolveInfo2.activityInfo.applicationInfo.targetSdkVersion + " but delivery restricted to [" + broadcastOptions.getMinManifestReceiverApiLevel() + ", " + broadcastOptions.getMaxManifestReceiverApiLevel() + "] broadcasting " + broadcastDescription(broadcastRecord, componentName);
            }
            if (broadcastOptions != null && !broadcastOptions.testRequireCompatChange(resolveInfo2.activityInfo.applicationInfo.uid)) {
                return "Compat change filtered: broadcasting " + broadcastDescription(broadcastRecord, componentName) + " to uid " + resolveInfo2.activityInfo.applicationInfo.uid + " due to compat change " + broadcastRecord.options.getRequireCompatChangeId();
            }
            ActivityManagerService activityManagerService3 = activityManagerService2;
            if (!activityManagerService3.validateAssociationAllowedLocked(broadcastRecord.callingUid, resolveInfo2.activityInfo.applicationInfo.uid, broadcastRecord.callerPackage, componentName.getPackageName())) {
                return "Association not allowed: broadcasting " + broadcastDescription(broadcastRecord, componentName);
            }
            IntentFirewall intentFirewall = activityManagerService3.mIntentFirewall;
            Intent intent = broadcastRecord.intent;
            if (!intentFirewall.checkIntent(intentFirewall.mBroadcastResolver, intent.getComponent(), 1, intent, broadcastRecord.callingUid, broadcastRecord.callingPid, broadcastRecord.resolvedType, resolveInfo2.activityInfo.applicationInfo.uid)) {
                return "Firewall blocked: broadcasting " + broadcastDescription(broadcastRecord, componentName);
            }
            ActivityInfo activityInfo2 = resolveInfo2.activityInfo;
            if (ActivityManagerService.checkComponentPermission(broadcastRecord.callingPid, broadcastRecord.callingUid, activityInfo2.permission, 0, activityInfo2.applicationInfo.uid, activityInfo2.exported) != 0) {
                if (resolveInfo2.activityInfo.exported) {
                    return "Permission Denial: broadcasting " + broadcastDescription(broadcastRecord, componentName) + " requires " + resolveInfo2.activityInfo.permission;
                }
                return "Permission Denial: broadcasting " + broadcastDescription(broadcastRecord, componentName) + " is not exported from uid " + resolveInfo2.activityInfo.applicationInfo.uid;
            }
            String str3 = resolveInfo2.activityInfo.permission;
            if (str3 != null && (permissionToOpCode = AppOpsManager.permissionToOpCode(str3)) != -1) {
                if (activityManagerService3.getAppOpsManager$1().noteOpNoThrow(permissionToOpCode, broadcastRecord.callingUid, broadcastRecord.callerPackage, broadcastRecord.callerFeatureId, "Broadcast delivered to " + resolveInfo2.activityInfo.name) != 0) {
                    return "Appop Denial: broadcasting " + broadcastDescription(broadcastRecord, componentName) + " requires appop " + AppOpsManager.permissionToOp(resolveInfo2.activityInfo.permission);
                }
            }
            ActivityInfo activityInfo3 = resolveInfo2.activityInfo;
            if ((activityInfo3.flags & 1073741824) != 0 && ActivityManager.checkUidPermission("android.permission.INTERACT_ACROSS_USERS", activityInfo3.applicationInfo.uid) != 0) {
                return "Permission Denial: Receiver " + componentName.flattenToShortString() + " requests FLAG_SINGLE_USER, but app does not hold android.permission.INTERACT_ACROSS_USERS";
            }
            if (resolveInfo2.activityInfo.applicationInfo.isInstantApp() && broadcastRecord.callingUid != resolveInfo2.activityInfo.applicationInfo.uid) {
                StringBuilder sb = new StringBuilder("Instant App Denial: receiving ");
                sb.append(broadcastRecord.intent);
                sb.append(" to ");
                sb.append(componentName.flattenToShortString());
                sb.append(" due to sender ");
                sb.append(broadcastRecord.callerPackage);
                sb.append(" (uid ");
                return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb, ") Instant Apps do not support manifest receivers");
            }
            if (broadcastRecord.callerInstantApp) {
                ActivityInfo activityInfo4 = resolveInfo2.activityInfo;
                if ((activityInfo4.flags & 1048576) == 0 && broadcastRecord.callingUid != activityInfo4.applicationInfo.uid) {
                    StringBuilder sb2 = new StringBuilder("Instant App Denial: receiving ");
                    sb2.append(broadcastRecord.intent);
                    sb2.append(" to ");
                    sb2.append(componentName.flattenToShortString());
                    sb2.append(" requires receiver have visibleToInstantApps set due to sender ");
                    sb2.append(broadcastRecord.callerPackage);
                    sb2.append(" (uid ");
                    return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb2, ")");
                }
            }
            try {
                IPackageManager packageManager = AppGlobals.getPackageManager();
                ActivityInfo activityInfo5 = resolveInfo2.activityInfo;
                if (!packageManager.isPackageAvailable(activityInfo5.packageName, UserHandle.getUserId(activityInfo5.applicationInfo.uid))) {
                    StringBuilder sb3 = new StringBuilder("Skipping delivery to ");
                    sb3.append(resolveInfo2.activityInfo.packageName);
                    sb3.append(" / ");
                    return AmFmBandRange$$ExternalSyntheticOutline0.m(resolveInfo2.activityInfo.applicationInfo.uid, sb3, " : package no longer available");
                }
                ActivityInfo activityInfo6 = resolveInfo2.activityInfo;
                if (!requestStartTargetPermissionsReviewIfNeededLocked(broadcastRecord, activityInfo6.packageName, UserHandle.getUserId(activityInfo6.applicationInfo.uid))) {
                    return "Skipping delivery: permission review required for " + broadcastDescription(broadcastRecord, componentName);
                }
                ActivityInfo activityInfo7 = resolveInfo2.activityInfo;
                ApplicationInfo applicationInfo = activityInfo7.applicationInfo;
                int appStartModeLOSP = this.mService.getAppStartModeLOSP(applicationInfo.uid, applicationInfo.targetSdkVersion, -1, activityInfo7.packageName, true, false, false);
                if (appStartModeLOSP != 0) {
                    if (appStartModeLOSP == 3) {
                        return "Background execution disabled: receiving " + broadcastRecord.intent + " to " + componentName.flattenToShortString();
                    }
                    if ((broadcastRecord.intent.getFlags() & 8388608) == 0) {
                        if (broadcastRecord.intent.getComponent() == null && broadcastRecord.intent.getPackage() == null && (broadcastRecord.intent.getFlags() & 16777216) == 0) {
                            String[] strArr2 = broadcastRecord.requiredPermissions;
                            if (strArr2 != null) {
                                IPermissionManager permissionManager = AppGlobals.getPermissionManager();
                                int length = strArr2.length - 1;
                                while (length >= 0) {
                                    try {
                                        String[] strArr3 = strArr2;
                                        PermissionInfo permissionInfo = permissionManager.getPermissionInfo(strArr2[length], "android", 0);
                                        if (permissionInfo != null && (permissionInfo.protectionLevel & 31) == 2) {
                                            length--;
                                            strArr2 = strArr3;
                                        }
                                    } catch (RemoteException unused) {
                                    }
                                }
                            }
                        }
                    }
                    String action = broadcastRecord.intent.getAction();
                    String packageName = componentName.getPackageName();
                    activityManagerService3.rotateBroadcastStatsIfNeededLocked();
                    BroadcastStats broadcastStats = activityManagerService3.mCurBroadcastStats;
                    BroadcastStats.ActionEntry actionEntry = (BroadcastStats.ActionEntry) broadcastStats.mActions.get(action);
                    if (actionEntry == null) {
                        actionEntry = new BroadcastStats.ActionEntry(action);
                        broadcastStats.mActions.put(action, actionEntry);
                    }
                    BroadcastStats.ViolationEntry violationEntry = (BroadcastStats.ViolationEntry) actionEntry.mBackgroundCheckViolations.get(packageName);
                    if (violationEntry == null) {
                        violationEntry = new BroadcastStats.ViolationEntry();
                        actionEntry.mBackgroundCheckViolations.put(packageName, violationEntry);
                    }
                    violationEntry.mCount++;
                    return "Background execution not allowed: receiving " + broadcastRecord.intent + " to " + componentName.flattenToShortString();
                }
                if (!"android.intent.action.ACTION_SHUTDOWN".equals(broadcastRecord.intent.getAction()) && !activityManagerService3.mUserController.isUserRunning(UserHandle.getUserId(resolveInfo2.activityInfo.applicationInfo.uid), 0)) {
                    StringBuilder sb4 = new StringBuilder("Skipping delivery to ");
                    sb4.append(resolveInfo2.activityInfo.packageName);
                    sb4.append(" / ");
                    return AmFmBandRange$$ExternalSyntheticOutline0.m(resolveInfo2.activityInfo.applicationInfo.uid, sb4, " : user is not running");
                }
                String[] strArr4 = broadcastRecord.excludedPermissions;
                if (strArr4 != null && strArr4.length > 0) {
                    int i2 = 0;
                    while (true) {
                        String[] strArr5 = broadcastRecord.excludedPermissions;
                        if (i2 >= strArr5.length) {
                            break;
                        }
                        String str4 = strArr5[i2];
                        try {
                            IPackageManager packageManager2 = AppGlobals.getPackageManager();
                            ApplicationInfo applicationInfo2 = resolveInfo2.activityInfo.applicationInfo;
                            i = packageManager2.checkPermission(str4, applicationInfo2.packageName, UserHandle.getUserId(applicationInfo2.uid));
                        } catch (RemoteException unused2) {
                            i = -1;
                        }
                        int permissionToOpCode2 = AppOpsManager.permissionToOpCode(str4);
                        if (permissionToOpCode2 == -1) {
                            activityManagerService = activityManagerService3;
                            if (i == 0) {
                                return BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("Skipping delivery to "), resolveInfo2.activityInfo.packageName, " due to excluded permission ", str4);
                            }
                        } else if (i == 0) {
                            AppOpsManager appOpsManager$1 = activityManagerService3.getAppOpsManager$1();
                            ActivityInfo activityInfo8 = resolveInfo2.activityInfo;
                            activityManagerService = activityManagerService3;
                            if (appOpsManager$1.checkOpNoThrow(permissionToOpCode2, activityInfo8.applicationInfo.uid, activityInfo8.packageName) == 0) {
                                return BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("Skipping delivery to "), resolveInfo2.activityInfo.packageName, " due to excluded permission ", str4);
                            }
                        } else {
                            activityManagerService = activityManagerService3;
                        }
                        i2++;
                        activityManagerService3 = activityManagerService;
                    }
                }
                String[] strArr6 = broadcastRecord.excludedPackages;
                if (strArr6 != null && strArr6.length > 0 && ArrayUtils.contains(strArr6, componentName.getPackageName())) {
                    StringBuilder sb5 = new StringBuilder("Skipping delivery of excluded package ");
                    sb5.append(broadcastRecord.intent);
                    sb5.append(" to ");
                    sb5.append(componentName.flattenToShortString());
                    sb5.append(" excludes package ");
                    sb5.append(componentName.getPackageName());
                    sb5.append(" due to sender ");
                    sb5.append(broadcastRecord.callerPackage);
                    sb5.append(" (uid ");
                    return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb5, ")");
                }
                if (resolveInfo2.activityInfo.applicationInfo.uid == 1000 || (strArr = broadcastRecord.requiredPermissions) == null || strArr.length <= 0) {
                    z = true;
                    z2 = false;
                } else {
                    Flags.usePermissionManagerForBroadcastDeliveryCheck();
                    String[] strArr7 = resolveInfo2.activityInfo.attributionTags;
                    if (ArrayUtils.isEmpty(strArr7)) {
                        z = true;
                        z2 = false;
                        attributionSourceArr = new AttributionSource[]{new AttributionSource.Builder(resolveInfo2.activityInfo.applicationInfo.uid).setPackageName(resolveInfo2.activityInfo.packageName).build()};
                    } else {
                        z = true;
                        z2 = false;
                        AttributionSource[] attributionSourceArr2 = new AttributionSource[strArr7.length];
                        for (int i3 = 0; i3 < strArr7.length; i3++) {
                            attributionSourceArr2[i3] = new AttributionSource.Builder(resolveInfo2.activityInfo.applicationInfo.uid).setPackageName(resolveInfo2.activityInfo.packageName).setAttributionTag(strArr7[i3]).build();
                        }
                        attributionSourceArr = attributionSourceArr2;
                    }
                    ?? r4 = z2;
                    while (true) {
                        String[] strArr8 = broadcastRecord.requiredPermissions;
                        if (r4 >= strArr8.length) {
                            break;
                        }
                        str = strArr8[r4];
                        try {
                            Flags.usePermissionManagerForBroadcastDeliveryCheck();
                            if (!hasPermissionForDataDelivery(str, "Broadcast delivered to " + resolveInfo2.activityInfo.name, attributionSourceArr)) {
                                break;
                            }
                            Flags.usePermissionManagerForBroadcastDeliveryCheck();
                            r4++;
                        } catch (RemoteException unused3) {
                        }
                    }
                }
                int i4 = broadcastRecord.appOp;
                if (i4 != -1) {
                    if (!ArrayUtils.isEmpty(resolveInfo2.activityInfo.attributionTags)) {
                        resolveInfo = resolveInfo2;
                        String[] strArr9 = resolveInfo.activityInfo.attributionTags;
                        int length2 = strArr9.length;
                        ?? r13 = z2;
                        while (true) {
                            if (r13 >= length2) {
                                break;
                            }
                            ComponentName componentName2 = componentName;
                            if (!noteOpForManifestReceiverInner(i4, broadcastRecord, resolveInfo, componentName, strArr9[r13])) {
                                z = z2;
                                break;
                            }
                            componentName = componentName2;
                            r13++;
                        }
                    } else {
                        resolveInfo = resolveInfo2;
                        z = noteOpForManifestReceiverInner(i4, broadcastRecord, resolveInfo, componentName, null);
                    }
                    if (!z) {
                        return "Skipping delivery to " + resolveInfo.activityInfo.packageName + " due to required appop " + broadcastRecord.appOp;
                    }
                }
                return null;
            } catch (Exception unused4) {
                return "Exception getting recipient info for " + resolveInfo2.activityInfo.packageName;
            }
        }
        BroadcastFilter broadcastFilter = (BroadcastFilter) obj;
        BroadcastOptions broadcastOptions2 = broadcastRecord.options;
        if (broadcastOptions2 != null && !broadcastOptions2.testRequireCompatChange(broadcastFilter.owningUid)) {
            return "Compat change filtered: broadcasting " + broadcastRecord.intent.toString() + " to uid " + broadcastFilter.owningUid + " due to compat change " + broadcastRecord.options.getRequireCompatChangeId();
        }
        String str5 = " from ";
        if (!activityManagerService2.validateAssociationAllowedLocked(broadcastRecord.callingUid, broadcastFilter.owningUid, broadcastRecord.callerPackage, broadcastFilter.packageName)) {
            return "Association not allowed: broadcasting " + broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") to " + broadcastFilter.packageName + " through " + broadcastFilter;
        }
        IntentFirewall intentFirewall2 = activityManagerService2.mIntentFirewall;
        Intent intent2 = broadcastRecord.intent;
        if (!intentFirewall2.checkIntent(intentFirewall2.mBroadcastResolver, intent2.getComponent(), 1, intent2, broadcastRecord.callingUid, broadcastRecord.callingPid, broadcastRecord.resolvedType, broadcastFilter.receiverList.uid)) {
            return "Firewall blocked: broadcasting " + broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") to " + broadcastFilter.packageName + " through " + broadcastFilter;
        }
        String str6 = broadcastFilter.requiredPermission;
        String str7 = ") requires appop ";
        if (str6 != null) {
            if (ActivityManagerService.checkComponentPermission(broadcastRecord.callingPid, broadcastRecord.callingUid, str6, 0, -1, true) != 0) {
                return "Permission Denial: broadcasting " + broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") requires " + broadcastFilter.requiredPermission + " due to registered receiver " + broadcastFilter;
            }
            int permissionToOpCode3 = AppOpsManager.permissionToOpCode(broadcastFilter.requiredPermission);
            if (permissionToOpCode3 != -1 && activityManagerService2.getAppOpsManager$1().noteOpNoThrow(permissionToOpCode3, broadcastRecord.callingUid, broadcastRecord.callerPackage, broadcastRecord.callerFeatureId, "Broadcast sent to protected receiver") != 0) {
                return "Appop Denial: broadcasting " + broadcastRecord.intent.toString() + " from " + broadcastRecord.callerPackage + " (pid=" + broadcastRecord.callingPid + ", uid=" + broadcastRecord.callingUid + ") requires appop " + AppOpsManager.permissionToOp(broadcastFilter.requiredPermission) + " due to registered receiver " + broadcastFilter;
            }
        }
        ProcessRecord processRecord = broadcastFilter.receiverList.app;
        if (processRecord == null || processRecord.mKilled || processRecord.mErrorState.mCrashing) {
            return "Skipping deliver [" + broadcastRecord.queue.mQueueName + "] " + broadcastRecord + " to " + broadcastFilter.receiverList + ": process gone or crashing";
        }
        if ((broadcastRecord.intent.getFlags() & 2097152) == 0 && broadcastFilter.instantApp && broadcastFilter.receiverList.uid != broadcastRecord.callingUid) {
            StringBuilder sb6 = new StringBuilder("Instant App Denial: receiving ");
            sb6.append(broadcastRecord.intent.toString());
            sb6.append(" to ");
            sb6.append(broadcastFilter.receiverList.app);
            sb6.append(" (pid=");
            sb6.append(broadcastFilter.receiverList.pid);
            sb6.append(", uid=");
            sb6.append(broadcastFilter.receiverList.uid);
            sb6.append(") due to sender ");
            sb6.append(broadcastRecord.callerPackage);
            sb6.append(" (uid ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb6, ") not specifying FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS");
        }
        if (!broadcastFilter.visibleToInstantApp && broadcastRecord.callerInstantApp && broadcastFilter.receiverList.uid != broadcastRecord.callingUid) {
            StringBuilder sb7 = new StringBuilder("Instant App Denial: receiving ");
            sb7.append(broadcastRecord.intent.toString());
            sb7.append(" to ");
            sb7.append(broadcastFilter.receiverList.app);
            sb7.append(" (pid=");
            sb7.append(broadcastFilter.receiverList.pid);
            sb7.append(", uid=");
            sb7.append(broadcastFilter.receiverList.uid);
            sb7.append(") requires receiver be visible to instant apps due to sender ");
            sb7.append(broadcastRecord.callerPackage);
            sb7.append(" (uid ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb7, ")");
        }
        String[] strArr10 = broadcastRecord.requiredPermissions;
        String str8 = "Broadcast delivered to registered receiver ";
        if (strArr10 != null && strArr10.length > 0) {
            Flags.usePermissionManagerForBroadcastDeliveryCheck();
            AttributionSource build = new AttributionSource.Builder(broadcastFilter.receiverList.uid).setPid(broadcastFilter.receiverList.pid).setPackageName(broadcastFilter.packageName).setAttributionTag(broadcastFilter.featureId).build();
            int i5 = 0;
            while (true) {
                String[] strArr11 = broadcastRecord.requiredPermissions;
                if (i5 >= strArr11.length) {
                    break;
                }
                String str9 = strArr11[i5];
                Flags.usePermissionManagerForBroadcastDeliveryCheck();
                StringBuilder sb8 = new StringBuilder("Broadcast delivered to registered receiver ");
                String str10 = str5;
                sb8.append(broadcastFilter.receiverId);
                String str11 = str7;
                if (!hasPermissionForDataDelivery(str9, sb8.toString(), build)) {
                    StringBuilder sb9 = new StringBuilder("Permission Denial: receiving ");
                    sb9.append(broadcastRecord.intent.toString());
                    sb9.append(" to ");
                    sb9.append(broadcastFilter.receiverList.app);
                    sb9.append(" (pid=");
                    sb9.append(broadcastFilter.receiverList.pid);
                    sb9.append(", uid=");
                    AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(broadcastFilter.receiverList.uid, ") requires ", str9, " due to sender ", sb9);
                    sb9.append(broadcastRecord.callerPackage);
                    sb9.append(" (uid ");
                    return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb9, ")");
                }
                Flags.usePermissionManagerForBroadcastDeliveryCheck();
                i5++;
                str5 = str10;
                str7 = str11;
            }
        }
        String str12 = str7;
        String str13 = str5;
        String[] strArr12 = broadcastRecord.requiredPermissions;
        if (strArr12 == null || strArr12.length == 0) {
            ReceiverList receiverList = broadcastFilter.receiverList;
            if (ActivityManagerService.checkComponentPermission(receiverList.pid, receiverList.uid, null, 0, -1, true) != 0) {
                StringBuilder sb10 = new StringBuilder("Permission Denial: security check failed when receiving ");
                sb10.append(broadcastRecord.intent.toString());
                sb10.append(" to ");
                sb10.append(broadcastFilter.receiverList.app);
                sb10.append(" (pid=");
                sb10.append(broadcastFilter.receiverList.pid);
                sb10.append(", uid=");
                sb10.append(broadcastFilter.receiverList.uid);
                sb10.append(") due to sender ");
                sb10.append(broadcastRecord.callerPackage);
                sb10.append(" (uid ");
                return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb10, ")");
            }
        }
        String[] strArr13 = broadcastRecord.excludedPermissions;
        if (strArr13 != null && strArr13.length > 0) {
            int i6 = 0;
            while (true) {
                String[] strArr14 = broadcastRecord.excludedPermissions;
                if (i6 >= strArr14.length) {
                    break;
                }
                String str14 = strArr14[i6];
                ReceiverList receiverList2 = broadcastFilter.receiverList;
                int checkComponentPermission = ActivityManagerService.checkComponentPermission(receiverList2.pid, receiverList2.uid, str14, 0, -1, true);
                int permissionToOpCode4 = AppOpsManager.permissionToOpCode(str14);
                if (permissionToOpCode4 == -1) {
                    str2 = str8;
                    if (checkComponentPermission == 0) {
                        StringBuilder sb11 = new StringBuilder("Permission Denial: receiving ");
                        sb11.append(broadcastRecord.intent.toString());
                        sb11.append(" to ");
                        sb11.append(broadcastFilter.receiverList.app);
                        sb11.append(" (pid=");
                        sb11.append(broadcastFilter.receiverList.pid);
                        sb11.append(", uid=");
                        AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(broadcastFilter.receiverList.uid, ") excludes ", str14, " due to sender ", sb11);
                        sb11.append(broadcastRecord.callerPackage);
                        sb11.append(" (uid ");
                        return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb11, ")");
                    }
                } else if (checkComponentPermission == 0) {
                    str2 = str8;
                    if (activityManagerService2.getAppOpsManager$1().checkOpNoThrow(permissionToOpCode4, broadcastFilter.receiverList.uid, broadcastFilter.packageName) == 0) {
                        StringBuilder sb12 = new StringBuilder("Appop Denial: receiving ");
                        sb12.append(broadcastRecord.intent.toString());
                        sb12.append(" to ");
                        sb12.append(broadcastFilter.receiverList.app);
                        sb12.append(" (pid=");
                        sb12.append(broadcastFilter.receiverList.pid);
                        sb12.append(", uid=");
                        sb12.append(broadcastFilter.receiverList.uid);
                        sb12.append(") excludes appop ");
                        sb12.append(AppOpsManager.permissionToOp(str14));
                        sb12.append(" due to sender ");
                        sb12.append(broadcastRecord.callerPackage);
                        sb12.append(" (uid ");
                        return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb12, ")");
                    }
                } else {
                    str2 = str8;
                }
                i6++;
                str8 = str2;
            }
        }
        String str15 = str8;
        String[] strArr15 = broadcastRecord.excludedPackages;
        if (strArr15 != null && strArr15.length > 0 && ArrayUtils.contains(strArr15, broadcastFilter.packageName)) {
            StringBuilder sb13 = new StringBuilder("Skipping delivery of excluded package ");
            sb13.append(broadcastRecord.intent.toString());
            sb13.append(" to ");
            sb13.append(broadcastFilter.receiverList.app);
            sb13.append(" (pid=");
            sb13.append(broadcastFilter.receiverList.pid);
            sb13.append(", uid=");
            sb13.append(broadcastFilter.receiverList.uid);
            sb13.append(") excludes package ");
            sb13.append(broadcastFilter.packageName);
            sb13.append(" due to sender ");
            sb13.append(broadcastRecord.callerPackage);
            sb13.append(" (uid ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb13, ")");
        }
        if (broadcastRecord.appOp != -1) {
            if (activityManagerService2.getAppOpsManager$1().noteOpNoThrow(broadcastRecord.appOp, broadcastFilter.receiverList.uid, broadcastFilter.packageName, broadcastFilter.featureId, str15 + broadcastFilter.receiverId) != 0) {
                StringBuilder sb14 = new StringBuilder("Appop Denial: receiving ");
                sb14.append(broadcastRecord.intent.toString());
                sb14.append(" to ");
                sb14.append(broadcastFilter.receiverList.app);
                sb14.append(" (pid=");
                sb14.append(broadcastFilter.receiverList.pid);
                sb14.append(", uid=");
                sb14.append(broadcastFilter.receiverList.uid);
                sb14.append(str12);
                sb14.append(AppOpsManager.opToName(broadcastRecord.appOp));
                sb14.append(" due to sender ");
                sb14.append(broadcastRecord.callerPackage);
                sb14.append(" (uid ");
                return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb14, ")");
            }
        }
        int i7 = broadcastRecord.sticky ? broadcastRecord.originalStickyCallingUid : broadcastRecord.callingUid;
        boolean z4 = broadcastFilter.exported;
        if (z4 || ActivityManagerService.checkComponentPermission(broadcastRecord.callingPid, i7, null, 0, broadcastFilter.receiverList.uid, z4) == 0) {
            if (requestStartTargetPermissionsReviewIfNeededLocked(broadcastRecord, broadcastFilter.packageName, broadcastFilter.owningUserId)) {
                return null;
            }
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder("Skipping delivery to "), broadcastFilter.packageName, " due to permissions review");
        }
        StringBuilder sb15 = new StringBuilder("Exported Denial: sending ");
        sb15.append(broadcastRecord.intent.toString());
        sb15.append(", action: ");
        sb15.append(broadcastRecord.intent.getAction());
        sb15.append(str13);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(i7, broadcastRecord.callerPackage, " (uid=", ") due to receiver ", sb15);
        sb15.append(broadcastFilter.receiverList.app);
        sb15.append(" (uid ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastFilter.receiverList.uid, sb15, ") not specifying RECEIVER_EXPORTED");
        StringBuilder sb16 = new StringBuilder("Permission Denial: receiving ");
        sb16.append(broadcastRecord.intent);
        sb16.append(" to ");
        sb16.append(componentName.flattenToShortString());
        sb16.append(" requires ");
        sb16.append(str);
        sb16.append(" due to sender ");
        sb16.append(broadcastRecord.callerPackage);
        sb16.append(" (uid ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(broadcastRecord.callingUid, sb16, ")");
    }
}
