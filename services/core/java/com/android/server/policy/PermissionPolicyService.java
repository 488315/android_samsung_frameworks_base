package com.android.server.policy;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AppOpsManager;
import android.app.AppOpsManagerInternal;
import android.app.KeyguardManager;
import android.app.TaskInfo;
import android.app.compat.CompatChanges;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.permission.LegacyPermissionManager;
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongSparseLongArray;
import android.util.Slog;
import android.util.SparseBooleanArray;

import com.android.internal.R;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.policy.AttributeCache;
import com.android.internal.util.IntPair;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.PermissionThread;
import com.android.server.SystemService;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.notification.NotificationManagerInternal;
import com.android.server.notification.NotificationManagerService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.Settings;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.permission.PermissionManagerServiceImpl;
import com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda15;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.wm.ActivityInterceptorCallback;
import com.android.server.wm.ActivityTaskManagerInternal;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PermissionPolicyService extends SystemService {
    public List mAppOpPermissions;
    public AnonymousClass2 mAppOpsCallback;
    public boolean mBootCompleted;
    public final Context mContext;
    public final Handler mHandler;
    public final SparseBooleanArray mIsStarted;
    public final SparseBooleanArray mIsUidResetScheduled;
    public final SparseBooleanArray mIsUidSyncScheduled;
    public final boolean mIsVivo;
    public final KeyguardManager mKeyguardManager;
    public final Object mLock;
    public NotificationManagerInternal mNotificationManager;
    public PermissionManagerServiceImpl$$ExternalSyntheticLambda15 mOnInitializedCallback;
    public final PackageManager mPackageManager;
    public PackageManagerInternal mPackageManagerInternal;
    public PermissionManagerService.PermissionManagerServiceInternalImpl mPermissionManagerInternal;
    public final ArrayList mPhoneCarrierPrivilegesCallbacks;
    public final AnonymousClass4 mSimConfigBroadcastReceiver;
    public TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Internal {
        public final AnonymousClass1 mActivityInterceptorCallback = new AnonymousClass1();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.policy.PermissionPolicyService$Internal$1, reason: invalid class name */
        public final class AnonymousClass1 implements ActivityInterceptorCallback {
            public AnonymousClass1() {}

            @Override // com.android.server.wm.ActivityInterceptorCallback
            public final void onActivityLaunched(
                    final TaskInfo taskInfo,
                    final ActivityInfo activityInfo,
                    final ActivityInterceptorCallback.ActivityInterceptorInfo
                            activityInterceptorInfo) {
                AttributeCache.Entry entry;
                if (Internal.this.shouldShowNotificationDialogOrClearFlags(
                        taskInfo,
                        activityInfo.packageName,
                        activityInterceptorInfo.getCallingPackage(),
                        activityInterceptorInfo.getIntent(),
                        activityInterceptorInfo.getCheckedOptions(),
                        activityInfo.name,
                        true)) {
                    Internal internal = Internal.this;
                    internal.getClass();
                    int themeResource = activityInfo.getThemeResource();
                    boolean z = false;
                    if (themeResource != 0
                            && (entry =
                                            AttributeCache.instance()
                                                    .get(
                                                            activityInfo.packageName,
                                                            themeResource,
                                                            R.styleable.Window,
                                                            0))
                                    != null) {
                        z = entry.array.getBoolean(10, false);
                    }
                    if (z) {
                        return;
                    }
                    if (CompatChanges.isChangeEnabled(
                            194833441L, activityInfo.packageName, UserHandle.of(taskInfo.userId))) {
                        return;
                    }
                    PermissionPolicyService.this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.policy.PermissionPolicyService$Internal$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PermissionPolicyService.Internal.AnonymousClass1
                                            anonymousClass1 =
                                                    PermissionPolicyService.Internal.AnonymousClass1
                                                            .this;
                                    ActivityInfo activityInfo2 = activityInfo;
                                    TaskInfo taskInfo2 = taskInfo;
                                    ActivityInterceptorCallback.ActivityInterceptorInfo
                                            activityInterceptorInfo2 = activityInterceptorInfo;
                                    anonymousClass1.getClass();
                                    PermissionPolicyService.Internal.this
                                            .showNotificationPromptIfNeeded(
                                                    activityInfo2.packageName,
                                                    taskInfo2.userId,
                                                    taskInfo2.taskId,
                                                    activityInterceptorInfo2);
                                }
                            });
                }
            }

            @Override // com.android.server.wm.ActivityInterceptorCallback
            public final ActivityInterceptorCallback.ActivityInterceptResult
                    onInterceptActivityLaunch(
                            ActivityInterceptorCallback.ActivityInterceptorInfo
                                    activityInterceptorInfo) {
                return null;
            }
        }

        public Internal() {}

        public static boolean isLauncherIntent(Intent intent) {
            return "android.intent.action.MAIN".equals(intent.getAction())
                    && intent.getCategories() != null
                    && (intent.getCategories().contains("android.intent.category.LAUNCHER")
                            || intent.getCategories()
                                    .contains("android.intent.category.LEANBACK_LAUNCHER")
                            || intent.getCategories()
                                    .contains("android.intent.category.CAR_LAUNCHER"));
        }

        public final boolean checkStartActivity(Intent intent, String str, int i) {
            String action;
            if (str != null
                    && (action = intent.getAction()) != null
                    && (action.equals("android.provider.Telephony.ACTION_CHANGE_DEFAULT")
                            || action.equals("android.telecom.action.CHANGE_DEFAULT_DIALER"))) {
                try {
                    if (PermissionPolicyService.this
                                    .getContext()
                                    .getPackageManager()
                                    .getApplicationInfoAsUser(str, 0, UserHandle.getUserId(i))
                                    .targetSdkVersion
                            >= 29) {
                        StringBuilder sb = new StringBuilder("Action Removed: starting ");
                        sb.append(intent.toString());
                        sb.append(" from ");
                        sb.append(str);
                        sb.append(" (uid=");
                        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                sb, i, ")", "PermissionPolicyService");
                        return false;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.i(
                            "PermissionPolicyService",
                            "Cannot find application info for ".concat(str));
                }
                intent.putExtra("android.intent.extra.CALLING_PACKAGE", str);
            }
            if ("android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER"
                    .equals(intent.getAction())) {
                return i == 1000 && "android".equals(str);
            }
            return true;
        }

        public final boolean shouldShowNotificationDialogOrClearFlags(
                TaskInfo taskInfo,
                String str,
                String str2,
                Intent intent,
                ActivityOptions activityOptions,
                String str3,
                boolean z) {
            ComponentName componentName;
            ComponentName componentName2;
            if (intent == null || str == null || taskInfo == null || str3 == null) {
                return false;
            }
            if ((!taskInfo.isFocused || !taskInfo.isVisible || !taskInfo.isRunning) && !z) {
                return false;
            }
            if (!isLauncherIntent(intent)
                    && ((activityOptions == null
                                    || !activityOptions.isEligibleForLegacyPermissionPrompt())
                            && ((componentName = taskInfo.baseActivity) == null
                                    || !str.equals(componentName.getPackageName())
                                    || !isLauncherIntent(taskInfo.baseIntent)))) {
                if (!str.equals(str2)
                        || !taskInfo.baseIntent.filterEquals(intent)
                        || taskInfo.numActivities != 1
                        || !str3.equals(taskInfo.topActivityInfo.name)) {
                    return false;
                }
                if (z) {
                    try {
                        List appTasks =
                                ((ActivityTaskManagerInternal)
                                                LocalServices.getService(
                                                        ActivityTaskManagerInternal.class))
                                        .getAppTasks(
                                                PermissionPolicyService.this.mPackageManager
                                                        .getPackageUid(str, 0),
                                                str);
                        int i = 0;
                        while (true) {
                            ArrayList arrayList = (ArrayList) appTasks;
                            if (i >= arrayList.size()) {
                                return false;
                            }
                            ActivityManager.RecentTaskInfo taskInfo2 =
                                    ((ActivityManager.AppTask) arrayList.get(i)).getTaskInfo();
                            if (((TaskInfo) taskInfo2).taskId != taskInfo.taskId
                                    && ((TaskInfo) taskInfo2).isFocused
                                    && ((TaskInfo) taskInfo2).isRunning
                                    && (componentName2 = ((TaskInfo) taskInfo2).baseActivity)
                                            != null
                                    && str.equals(componentName2.getPackageName())
                                    && isLauncherIntent(((TaskInfo) taskInfo2).baseIntent)) {
                                break;
                            }
                            i++;
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        return false;
                    }
                }
            }
            return true;
        }

        public final void showNotificationPromptIfNeeded(
                String str,
                int i,
                int i2,
                ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
            UserHandle of = UserHandle.of(i);
            if (str == null || i2 == -1) {
                return;
            }
            AndroidPackage androidPackage =
                    PermissionPolicyService.this.mPackageManagerInternal.getPackage(str);
            if (androidPackage == null
                    || androidPackage.getPackageName() == null
                    || str.equals(
                            PermissionPolicyService.this.mPackageManager
                                    .getPermissionControllerPackageName())
                    || androidPackage.getTargetSdkVersion() < 23) {
                if (androidPackage == null) {
                    Slog.w(
                            "PermissionPolicyService",
                            "Cannot check for Notification prompt, no package for ".concat(str));
                    return;
                }
                return;
            }
            synchronized (PermissionPolicyService.this.mLock) {
                try {
                    if (PermissionPolicyService.this.mBootCompleted) {
                        if (androidPackage
                                        .getRequestedPermissions()
                                        .contains("android.permission.POST_NOTIFICATIONS")
                                && !CompatChanges.isChangeEnabled(194833441L, str, of)
                                && !PermissionPolicyService.this.mKeyguardManager
                                        .isKeyguardLocked()) {
                            int uid = of.getUid(androidPackage.getUid());
                            PermissionPolicyService permissionPolicyService =
                                    PermissionPolicyService.this;
                            if (permissionPolicyService.mNotificationManager == null) {
                                permissionPolicyService.mNotificationManager =
                                        (NotificationManagerInternal)
                                                LocalServices.getService(
                                                        NotificationManagerInternal.class);
                            }
                            boolean z =
                                    NotificationManagerService.this
                                                    .getNumNotificationChannelsForPackage(
                                                            str, uid, true)
                                            > 0;
                            boolean z2 =
                                    PermissionManagerService.this.checkUidPermission(
                                                    uid, "android.permission.POST_NOTIFICATIONS", 0)
                                            == 0;
                            boolean z3 =
                                    (PermissionPolicyService.this.mPackageManager
                                                            .getPermissionFlags(
                                                                    "android.permission.POST_NOTIFICATIONS",
                                                                    str,
                                                                    of)
                                                    & 32823)
                                            != 0;
                            if (!z2 && z && !z3) {
                                PermissionPolicyService permissionPolicyService2 =
                                        PermissionPolicyService.this;
                                Intent buildRequestPermissionsIntent =
                                        permissionPolicyService2.mPackageManager
                                                .buildRequestPermissionsIntent(
                                                        new String[] {
                                                            "android.permission.POST_NOTIFICATIONS"
                                                        });
                                buildRequestPermissionsIntent.addFlags(268697600);
                                buildRequestPermissionsIntent.setAction(
                                        "android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER");
                                buildRequestPermissionsIntent.putExtra(
                                        "android.intent.extra.PACKAGE_NAME", str);
                                boolean z4 =
                                        (activityInterceptorInfo == null
                                                        || activityInterceptorInfo
                                                                        .getCheckedOptions()
                                                                == null
                                                        || activityInterceptorInfo
                                                                        .getCheckedOptions()
                                                                        .getAnimationType()
                                                                != 13
                                                        || activityInterceptorInfo
                                                                        .getClearOptionsAnimationRunnable()
                                                                == null)
                                                ? false
                                                : true;
                                ActivityOptions makeRemoteAnimation =
                                        z4
                                                ? ActivityOptions.makeRemoteAnimation(
                                                        activityInterceptorInfo
                                                                .getCheckedOptions()
                                                                .getRemoteAnimationAdapter(),
                                                        activityInterceptorInfo
                                                                .getCheckedOptions()
                                                                .getRemoteTransition())
                                                : new ActivityOptions(new Bundle());
                                makeRemoteAnimation.setTaskOverlay(true, false);
                                makeRemoteAnimation.setLaunchTaskId(i2);
                                if (z4) {
                                    activityInterceptorInfo
                                            .getClearOptionsAnimationRunnable()
                                            .run();
                                }
                                try {
                                    permissionPolicyService2.mContext.startActivityAsUser(
                                            buildRequestPermissionsIntent,
                                            makeRemoteAnimation.toBundle(),
                                            of);
                                } catch (Exception e) {
                                    Log.e(
                                            "PermissionPolicyService",
                                            "couldn't start grant permission dialogfor other package "
                                                    .concat(str),
                                            e);
                                }
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PermissionToOpSynchroniser {
        public final AppOpsManager mAppOpsManager;
        public final Context mContext;
        public final PackageManager mPackageManager;
        public final ArrayList mOpsToAllow = new ArrayList();
        public final ArrayList mOpsToIgnore = new ArrayList();
        public final ArrayList mOpsToIgnoreIfNotAllowed = new ArrayList();
        public final ArrayList mOpsToForeground = new ArrayList();
        public final AppOpsManagerInternal mAppOpsManagerInternal =
                (AppOpsManagerInternal) LocalServices.getService(AppOpsManagerInternal.class);
        public final ArrayMap mRuntimeAndTheirBgPermissionInfos = new ArrayMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class OpToChange {
            public final int code;
            public final String packageName;
            public final int uid;

            public OpToChange(int i, String str, int i2) {
                this.uid = i;
                this.packageName = str;
                this.code = i2;
            }
        }

        /* renamed from: -$$Nest$msyncPackages, reason: not valid java name */
        public static void m789$$Nest$msyncPackages(
                PermissionToOpSynchroniser permissionToOpSynchroniser) {
            LongSparseLongArray longSparseLongArray = new LongSparseLongArray();
            int size = permissionToOpSynchroniser.mOpsToAllow.size();
            for (int i = 0; i < size; i++) {
                OpToChange opToChange = (OpToChange) permissionToOpSynchroniser.mOpsToAllow.get(i);
                int i2 = opToChange.code;
                int i3 = opToChange.uid;
                permissionToOpSynchroniser.setUidMode(i2, i3, 0, opToChange.packageName);
                longSparseLongArray.put(IntPair.of(i3, opToChange.code), 1L);
            }
            int size2 = permissionToOpSynchroniser.mOpsToForeground.size();
            for (int i4 = 0; i4 < size2; i4++) {
                OpToChange opToChange2 =
                        (OpToChange) permissionToOpSynchroniser.mOpsToForeground.get(i4);
                int i5 = opToChange2.uid;
                int i6 = opToChange2.code;
                if (longSparseLongArray.indexOfKey(IntPair.of(i5, i6)) < 0) {
                    int i7 = opToChange2.uid;
                    permissionToOpSynchroniser.setUidMode(i6, i7, 4, opToChange2.packageName);
                    longSparseLongArray.put(IntPair.of(i7, i6), 1L);
                }
            }
            int size3 = permissionToOpSynchroniser.mOpsToIgnore.size();
            for (int i8 = 0; i8 < size3; i8++) {
                OpToChange opToChange3 =
                        (OpToChange) permissionToOpSynchroniser.mOpsToIgnore.get(i8);
                int i9 = opToChange3.uid;
                int i10 = opToChange3.code;
                if (longSparseLongArray.indexOfKey(IntPair.of(i9, i10)) < 0) {
                    int i11 = opToChange3.uid;
                    permissionToOpSynchroniser.setUidMode(i10, i11, 1, opToChange3.packageName);
                    longSparseLongArray.put(IntPair.of(i11, i10), 1L);
                }
            }
            int size4 = permissionToOpSynchroniser.mOpsToIgnoreIfNotAllowed.size();
            for (int i12 = 0; i12 < size4; i12++) {
                OpToChange opToChange4 =
                        (OpToChange) permissionToOpSynchroniser.mOpsToIgnoreIfNotAllowed.get(i12);
                int i13 = opToChange4.uid;
                int i14 = opToChange4.code;
                if (longSparseLongArray.indexOfKey(IntPair.of(i13, i14)) < 0) {
                    AppOpsManager appOpsManager = permissionToOpSynchroniser.mAppOpsManager;
                    String opToPublicName = AppOpsManager.opToPublicName(i14);
                    int i15 = opToChange4.uid;
                    int unsafeCheckOpRaw =
                            appOpsManager.unsafeCheckOpRaw(
                                    opToPublicName, i15, opToChange4.packageName);
                    if (unsafeCheckOpRaw != 0) {
                        if (unsafeCheckOpRaw != 1) {
                            permissionToOpSynchroniser.mAppOpsManagerInternal
                                    .setUidModeFromPermissionPolicy(
                                            i14,
                                            i15,
                                            1,
                                            PermissionPolicyService.this.mAppOpsCallback);
                        }
                        longSparseLongArray.put(IntPair.of(i15, i14), 1L);
                    }
                }
            }
        }

        public PermissionToOpSynchroniser(Context context) {
            this.mContext = context;
            this.mPackageManager = context.getPackageManager();
            this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
            ArrayList arrayList =
                    (ArrayList)
                            PermissionManagerService.this.mPermissionManagerServiceImpl
                                    .getAllPermissionsWithProtection();
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                PermissionInfo permissionInfo = (PermissionInfo) arrayList.get(i);
                this.mRuntimeAndTheirBgPermissionInfos.put(permissionInfo.name, permissionInfo);
                String str = permissionInfo.backgroundPermission;
                if (str != null) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        if (permissionInfo.backgroundPermission.equals(
                                ((PermissionInfo) arrayList.get(i2)).name)) {
                            str = null;
                            break;
                        }
                        i2++;
                    }
                    if (str != null) {
                        try {
                            PermissionInfo permissionInfo2 =
                                    this.mPackageManager.getPermissionInfo(str, 0);
                            this.mRuntimeAndTheirBgPermissionInfos.put(
                                    permissionInfo2.name, permissionInfo2);
                        } catch (PackageManager.NameNotFoundException unused) {
                            Slog.w(
                                    "PermissionPolicyService",
                                    "Unknown background permission: ".concat(str));
                        }
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:46:0x00e0  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x00f1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void addPackage(java.lang.String r17) {
            /*
                Method dump skipped, instructions count: 319
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.addPackage(java.lang.String):void");
        }

        public final void setUidMode(int i, int i2, int i3, String str) {
            if (this.mAppOpsManager.unsafeCheckOpRaw(AppOpsManager.opToPublicName(i), i2, str)
                    != i3) {
                AppOpsManagerInternal appOpsManagerInternal = this.mAppOpsManagerInternal;
                PermissionPolicyService permissionPolicyService = PermissionPolicyService.this;
                appOpsManagerInternal.setUidModeFromPermissionPolicy(
                        i, i2, i3, permissionPolicyService.mAppOpsCallback);
                if (this.mAppOpsManager.unsafeCheckOpRaw(AppOpsManager.opToPublicName(i), i2, str)
                        != i3) {
                    this.mAppOpsManagerInternal.setModeFromPermissionPolicy(
                            i,
                            i2,
                            str,
                            AppOpsManager.opToDefaultMode(i),
                            permissionPolicyService.mAppOpsCallback);
                }
            }
        }

        public final boolean shouldGrantAppOp(
                PackageInfo packageInfo,
                AndroidPackage androidPackage,
                PermissionInfo permissionInfo) {
            String str = permissionInfo.name;
            String str2 = packageInfo.packageName;
            if (this.mPackageManager.checkPermission(str, str2) != 0) {
                return false;
            }
            int permissionFlags =
                    this.mPackageManager.getPermissionFlags(str, str2, this.mContext.getUser());
            if ((permissionFlags & 8) == 8) {
                return false;
            }
            if (permissionInfo.isHardRestricted()) {
                return !((permissionFlags
                                & EndpointMonitorConst
                                        .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                        == 16384);
            }
            if (!permissionInfo.isSoftRestricted()) {
                return true;
            }
            Context context = this.mContext;
            return SoftRestrictedPermissionPolicy.forPermission(
                            context,
                            packageInfo.applicationInfo,
                            androidPackage,
                            context.getUser(),
                            str)
                    .mayGrantPermission();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhoneCarrierPrivilegesCallback
            implements TelephonyManager.CarrierPrivilegesCallback {
        public final int mPhoneId;

        public PhoneCarrierPrivilegesCallback(int i) {
            this.mPhoneId = i;
        }

        public final void onCarrierPrivilegesChanged(Set set, Set set2) {
            PermissionPolicyService permissionPolicyService = PermissionPolicyService.this;
            if (permissionPolicyService.mTelephonyManager == null) {
                permissionPolicyService.mTelephonyManager =
                        TelephonyManager.from(permissionPolicyService.mContext);
            }
            TelephonyManager telephonyManager = PermissionPolicyService.this.mTelephonyManager;
            if (telephonyManager == null) {
                Log.e(
                        "PermissionPolicyService",
                        "Cannot grant default permissions to Carrier Service app. TelephonyManager"
                            + " is null");
                return;
            }
            String carrierServicePackageNameForLogicalSlot =
                    telephonyManager.getCarrierServicePackageNameForLogicalSlot(this.mPhoneId);
            if (carrierServicePackageNameForLogicalSlot == null) {
                return;
            }
            int[] userIds =
                    ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                            .getUserIds();
            LegacyPermissionManager legacyPermissionManager =
                    (LegacyPermissionManager)
                            PermissionPolicyService.this.mContext.getSystemService(
                                    LegacyPermissionManager.class);
            for (int i = 0; i < userIds.length; i++) {
                try {
                    PermissionPolicyService.this.mPackageManager.getPackageInfoAsUser(
                            carrierServicePackageNameForLogicalSlot, 0, userIds[i]);
                    legacyPermissionManager.grantDefaultPermissionsToCarrierServiceApp(
                            carrierServicePackageNameForLogicalSlot, userIds[i]);
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.server.policy.PermissionPolicyService$4] */
    public PermissionPolicyService(Context context) {
        super(context);
        this.mLock = new Object();
        this.mBootCompleted = false;
        this.mIsStarted = new SparseBooleanArray();
        this.mIsUidSyncScheduled = new SparseBooleanArray();
        this.mIsUidResetScheduled = new SparseBooleanArray();
        this.mIsVivo =
                "ZVV"
                        .equals(
                                SemCscFeature.getInstance()
                                        .getString(
                                                "CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon",
                                                ""));
        this.mPhoneCarrierPrivilegesCallbacks = new ArrayList();
        this.mSimConfigBroadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.policy.PermissionPolicyService.4
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.telephony.action.MULTI_SIM_CONFIG_CHANGED"
                                .equals(intent.getAction())) {
                            PermissionPolicyService permissionPolicyService =
                                    PermissionPolicyService.this;
                            if (permissionPolicyService.mTelephonyManager == null) {
                                permissionPolicyService.mTelephonyManager =
                                        TelephonyManager.from(permissionPolicyService.mContext);
                            }
                            if (permissionPolicyService.mTelephonyManager != null) {
                                for (int i = 0;
                                        i
                                                < permissionPolicyService
                                                        .mPhoneCarrierPrivilegesCallbacks.size();
                                        i++) {
                                    PhoneCarrierPrivilegesCallback phoneCarrierPrivilegesCallback =
                                            (PhoneCarrierPrivilegesCallback)
                                                    permissionPolicyService
                                                            .mPhoneCarrierPrivilegesCallbacks.get(
                                                            i);
                                    if (phoneCarrierPrivilegesCallback != null) {
                                        permissionPolicyService.mTelephonyManager
                                                .unregisterCarrierPrivilegesCallback(
                                                        phoneCarrierPrivilegesCallback);
                                    }
                                }
                                permissionPolicyService.mPhoneCarrierPrivilegesCallbacks.clear();
                            }
                            PermissionPolicyService.this.registerCarrierPrivilegesCallbacks();
                        }
                    }
                };
        this.mContext = context;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mPackageManager = context.getPackageManager();
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        LocalServices.addService(Internal.class, new Internal());
    }

    public static Context getUserContext(Context context, UserHandle userHandle) {
        if (context.getUser().equals(userHandle)) {
            return context;
        }
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, userHandle);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("PermissionPolicyService", "Cannot create context for user " + userHandle, e);
            return null;
        }
    }

    public final boolean isStarted(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsStarted.get(i);
        }
        return z;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 520) {
            registerCarrierPrivilegesCallbacks();
            this.mContext.registerReceiver(
                    this.mSimConfigBroadcastReceiver,
                    new IntentFilter("android.telephony.action.MULTI_SIM_CONFIG_CHANGED"));
        }
        if (i == 550) {
            UserManagerInternal userManagerInternal =
                    (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            for (int i2 : userManagerInternal.getUserIds()) {
                if (userManagerInternal.isUserRunning(i2)) {
                    onStartUser$1(i2);
                }
            }
        }
        if (i == 550) {
            Internal internal = (Internal) LocalServices.getService(Internal.class);
            internal.getClass();
            ((ActivityTaskManagerInternal)
                            LocalServices.getService(ActivityTaskManagerInternal.class))
                    .registerActivityStartInterceptor(1, internal.mActivityInterceptorCallback);
        }
        if (i == 1000) {
            synchronized (this.mLock) {
                this.mBootCompleted = true;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.server.policy.PermissionPolicyService$2] */
    @Override // com.android.server.SystemService
    public final void onStart() {
        char c;
        int extraAppOpCode;
        this.mPackageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mPermissionManagerInternal =
                (PermissionManagerService.PermissionManagerServiceInternalImpl)
                        LocalServices.getService(
                                PermissionManagerService.PermissionManagerServiceInternalImpl
                                        .class);
        IAppOpsService asInterface =
                IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
        this.mPackageManagerInternal.getPackageList(
                new PackageManagerInternal
                        .PackageListObserver() { // from class:
                                                 // com.android.server.policy.PermissionPolicyService.1
                    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
                    public final void onPackageAdded(String str, int i) {
                        for (int i2 :
                                ((UserManagerInternal)
                                                LocalServices.getService(UserManagerInternal.class))
                                        .getUserIds()) {
                            PermissionPolicyService permissionPolicyService =
                                    PermissionPolicyService.this;
                            if (permissionPolicyService.isStarted(i2)) {
                                permissionPolicyService.synchronizeUidPermissionsAndAppOps(
                                        UserHandle.getUid(i2, i));
                            }
                        }
                    }

                    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
                    public final void onPackageChanged(int i, String str) {
                        for (int i2 :
                                ((UserManagerInternal)
                                                LocalServices.getService(UserManagerInternal.class))
                                        .getUserIds()) {
                            PermissionPolicyService permissionPolicyService =
                                    PermissionPolicyService.this;
                            if (permissionPolicyService.isStarted(i2)) {
                                int uid = UserHandle.getUid(i2, i);
                                permissionPolicyService.synchronizeUidPermissionsAndAppOps(uid);
                                permissionPolicyService.resetAppOpPermissionsIfNotRequestedForUid(
                                        uid);
                            }
                        }
                    }

                    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
                    public final void onPackageRemoved(String str, int i) {
                        for (int i2 :
                                ((UserManagerInternal)
                                                LocalServices.getService(UserManagerInternal.class))
                                        .getUserIds()) {
                            PermissionPolicyService permissionPolicyService =
                                    PermissionPolicyService.this;
                            if (permissionPolicyService.isStarted(i2)) {
                                permissionPolicyService.resetAppOpPermissionsIfNotRequestedForUid(
                                        UserHandle.getUid(i2, i));
                            }
                        }
                    }
                });
        this.mPackageManager.addOnPermissionsChangeListener(
                new PackageManager
                        .OnPermissionsChangedListener() { // from class:
                                                          // com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda0
                    public final void onPermissionsChanged(int i) {
                        PermissionPolicyService.this.synchronizeUidPermissionsAndAppOpsAsync(i);
                    }
                });
        this.mAppOpsCallback =
                new IAppOpsCallback
                        .Stub() { // from class: com.android.server.policy.PermissionPolicyService.2
                    public final void opChanged(int i, int i2, String str, String str2) {
                        if (Objects.equals(str2, "default:0")) {
                            if (str != null) {
                                PermissionPolicyService.this
                                        .synchronizeUidPermissionsAndAppOpsAsync(i2);
                            }
                            PermissionPolicyService permissionPolicyService =
                                    PermissionPolicyService.this;
                            permissionPolicyService.getClass();
                            if (permissionPolicyService.isStarted(UserHandle.getUserId(i2))) {
                                synchronized (permissionPolicyService.mLock) {
                                    try {
                                        if (!permissionPolicyService.mIsUidResetScheduled.get(i2)) {
                                            permissionPolicyService.mIsUidResetScheduled.put(
                                                    i2, true);
                                            PermissionThread.getHandler()
                                                    .sendMessage(
                                                            PooledLambda.obtainMessage(
                                                                    new PermissionPolicyService$$ExternalSyntheticLambda4(
                                                                            1),
                                                                    permissionPolicyService,
                                                                    Integer.valueOf(i2)));
                                        }
                                    } finally {
                                    }
                                }
                            }
                        }
                    }
                };
        try {
            ArrayList arrayList =
                    (ArrayList)
                            PermissionManagerService.this.mPermissionManagerServiceImpl
                                    .getAllPermissionsWithProtection();
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                PermissionInfo permissionInfo = (PermissionInfo) arrayList.get(i);
                if (permissionInfo.isRuntime()) {
                    int permissionToOpCode = AppOpsManager.permissionToOpCode(permissionInfo.name);
                    asInterface.startWatchingMode(
                            permissionToOpCode == -1
                                    ? -1
                                    : AppOpsManager.opToSwitch(permissionToOpCode),
                            (String) null,
                            this.mAppOpsCallback);
                }
                if (permissionInfo.isSoftRestricted()
                        && (extraAppOpCode =
                                        SoftRestrictedPermissionPolicy.forPermission(
                                                        null, null, null, null, permissionInfo.name)
                                                .getExtraAppOpCode())
                                != -1) {
                    asInterface.startWatchingMode(
                            extraAppOpCode, (String) null, this.mAppOpsCallback);
                }
            }
        } catch (RemoteException unused) {
            Slog.wtf("PermissionPolicyService", "Cannot set up app-ops listener");
        }
        List allPermissionsWithProtectionFlags =
                PermissionManagerService.this.mPermissionManagerServiceImpl
                        .getAllPermissionsWithProtectionFlags();
        this.mAppOpPermissions = new ArrayList();
        ArrayList arrayList2 = (ArrayList) allPermissionsWithProtectionFlags;
        int size2 = arrayList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            PermissionInfo permissionInfo2 = (PermissionInfo) arrayList2.get(i2);
            String str = permissionInfo2.name;
            str.getClass();
            switch (str.hashCode()) {
                case 309844284:
                    if (str.equals("android.permission.MANAGE_IPSEC_TUNNELS")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1353874541:
                    if (str.equals("android.permission.ACCESS_NOTIFICATIONS")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1777263169:
                    if (str.equals("android.permission.REQUEST_INSTALL_PACKAGES")) {
                        c = 2;
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
                case 1:
                case 2:
                    break;
                default:
                    int permissionToOpCode2 =
                            AppOpsManager.permissionToOpCode(permissionInfo2.name);
                    if (permissionToOpCode2 != -1) {
                        ((ArrayList) this.mAppOpPermissions).add(permissionInfo2.name);
                        try {
                            asInterface.startWatchingMode(
                                    permissionToOpCode2, (String) null, this.mAppOpsCallback);
                            break;
                        } catch (RemoteException e) {
                            Slog.wtf(
                                    "PermissionPolicyService", "Cannot set up app-ops listener", e);
                            break;
                        }
                    } else {
                        break;
                    }
            }
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        getContext()
                .registerReceiverAsUser(
                        new BroadcastReceiver() { // from class:
                            // com.android.server.policy.PermissionPolicyService.3
                            public final List mUserSetupUids = new ArrayList(200);
                            public final Map mPermControllerManagers = new HashMap();

                            /* JADX WARN: Removed duplicated region for block: B:10:0x002e  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x002d A[RETURN] */
                            @Override // android.content.BroadcastReceiver
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void onReceive(
                                    android.content.Context r4, android.content.Intent r5) {
                                /*
                                    r3 = this;
                                    r4 = 1
                                    com.android.server.policy.PermissionPolicyService r0 = com.android.server.policy.PermissionPolicyService.this     // Catch: android.provider.Settings.SettingNotFoundException -> L1b
                                    android.content.Context r0 = r0.getContext()     // Catch: android.provider.Settings.SettingNotFoundException -> L1b
                                    android.content.ContentResolver r0 = r0.getContentResolver()     // Catch: android.provider.Settings.SettingNotFoundException -> L1b
                                    java.lang.String r1 = "user_setup_complete"
                                    int r2 = r0.getUserId()     // Catch: android.provider.Settings.SettingNotFoundException -> L1b
                                    int r0 = android.provider.Settings.Secure.getIntForUser(r0, r1, r2)     // Catch: android.provider.Settings.SettingNotFoundException -> L1b
                                    if (r0 == 0) goto L19
                                    goto L1b
                                L19:
                                    r0 = 0
                                    goto L1c
                                L1b:
                                    r0 = r4
                                L1c:
                                    java.lang.String r1 = "android.intent.extra.UID"
                                    r2 = -1
                                    int r5 = r5.getIntExtra(r1, r2)
                                    com.android.server.policy.PermissionPolicyService r1 = com.android.server.policy.PermissionPolicyService.this
                                    android.content.pm.PackageManagerInternal r1 = r1.mPackageManagerInternal
                                    com.android.server.pm.pkg.AndroidPackage r1 = r1.getPackage(r5)
                                    if (r1 != 0) goto L2e
                                    return
                                L2e:
                                    if (r0 == 0) goto L6d
                                    java.util.List r0 = r3.mUserSetupUids
                                    java.util.ArrayList r0 = (java.util.ArrayList) r0
                                    boolean r0 = r0.isEmpty()
                                    if (r0 != 0) goto L69
                                    java.util.List r0 = r3.mUserSetupUids
                                    monitor-enter(r0)
                                    java.util.List r1 = r3.mUserSetupUids     // Catch: java.lang.Throwable -> L5c
                                    java.util.ArrayList r1 = (java.util.ArrayList) r1     // Catch: java.lang.Throwable -> L5c
                                    int r1 = r1.size()     // Catch: java.lang.Throwable -> L5c
                                    int r1 = r1 - r4
                                L46:
                                    if (r1 < 0) goto L5e
                                    java.util.List r4 = r3.mUserSetupUids     // Catch: java.lang.Throwable -> L5c
                                    java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch: java.lang.Throwable -> L5c
                                    java.lang.Object r4 = r4.get(r1)     // Catch: java.lang.Throwable -> L5c
                                    java.lang.Integer r4 = (java.lang.Integer) r4     // Catch: java.lang.Throwable -> L5c
                                    int r4 = r4.intValue()     // Catch: java.lang.Throwable -> L5c
                                    r3.updateUid(r4)     // Catch: java.lang.Throwable -> L5c
                                    int r1 = r1 + (-1)
                                    goto L46
                                L5c:
                                    r3 = move-exception
                                    goto L67
                                L5e:
                                    java.util.List r4 = r3.mUserSetupUids     // Catch: java.lang.Throwable -> L5c
                                    java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch: java.lang.Throwable -> L5c
                                    r4.clear()     // Catch: java.lang.Throwable -> L5c
                                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L5c
                                    goto L69
                                L67:
                                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L5c
                                    throw r3
                                L69:
                                    r3.updateUid(r5)
                                    goto L8d
                                L6d:
                                    java.util.List r4 = r3.mUserSetupUids
                                    monitor-enter(r4)
                                    java.util.List r0 = r3.mUserSetupUids     // Catch: java.lang.Throwable -> L8a
                                    java.lang.Integer r1 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Throwable -> L8a
                                    java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: java.lang.Throwable -> L8a
                                    boolean r0 = r0.contains(r1)     // Catch: java.lang.Throwable -> L8a
                                    if (r0 != 0) goto L8c
                                    java.util.List r3 = r3.mUserSetupUids     // Catch: java.lang.Throwable -> L8a
                                    java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Throwable -> L8a
                                    java.util.ArrayList r3 = (java.util.ArrayList) r3     // Catch: java.lang.Throwable -> L8a
                                    r3.add(r5)     // Catch: java.lang.Throwable -> L8a
                                    goto L8c
                                L8a:
                                    r3 = move-exception
                                    goto L8e
                                L8c:
                                    monitor-exit(r4)     // Catch: java.lang.Throwable -> L8a
                                L8d:
                                    return
                                L8e:
                                    monitor-exit(r4)     // Catch: java.lang.Throwable -> L8a
                                    throw r3
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.policy.PermissionPolicyService.AnonymousClass3.onReceive(android.content.Context,"
                                            + " android.content.Intent):void");
                            }

                            public final void updateUid(int i3) {
                                UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i3);
                                PermissionControllerManager permissionControllerManager =
                                        (PermissionControllerManager)
                                                ((HashMap) this.mPermControllerManagers)
                                                        .get(userHandleForUid);
                                if (permissionControllerManager == null) {
                                    try {
                                        permissionControllerManager =
                                                new PermissionControllerManager(
                                                        PermissionPolicyService.getUserContext(
                                                                PermissionPolicyService.this
                                                                        .getContext(),
                                                                userHandleForUid),
                                                        PermissionThread.getHandler());
                                        ((HashMap) this.mPermControllerManagers)
                                                .put(userHandleForUid, permissionControllerManager);
                                    } catch (IllegalArgumentException e2) {
                                        Log.e(
                                                "PermissionPolicyService",
                                                "Could not create PermissionControllerManager for"
                                                    + " user"
                                                        + userHandleForUid,
                                                e2);
                                        return;
                                    }
                                }
                                permissionControllerManager.updateUserSensitiveForApp(i3);
                            }
                        },
                        UserHandle.ALL,
                        intentFilter,
                        null,
                        null);
        final PermissionControllerManager permissionControllerManager =
                new PermissionControllerManager(
                        getUserContext(getContext(), Process.myUserHandle()),
                        PermissionThread.getHandler());
        PermissionThread.getHandler()
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                permissionControllerManager.updateUserSensitive();
                            }
                        },
                        60000L);
    }

    public final void onStartUser$1(final int i) {
        boolean z;
        PermissionManagerServiceImpl$$ExternalSyntheticLambda15
                permissionManagerServiceImpl$$ExternalSyntheticLambda15;
        if (isStarted(i)) {
            return;
        }
        if (!this.mIsVivo && i == 0) {
            AppOpsManager appOpsManager =
                    (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
            try {
                PackageInfo packageInfo =
                        this.mContext
                                .getPackageManager()
                                .getPackageInfo(Constants.SYSTEMUI_PACKAGE_NAME, 0);
                if (packageInfo != null && packageInfo.applicationInfo.isSystemApp()) {
                    appOpsManager.setMode(
                            "android:coarse_location",
                            packageInfo.applicationInfo.uid,
                            Constants.SYSTEMUI_PACKAGE_NAME,
                            1);
                    appOpsManager.setMode(
                            "android:fine_location",
                            packageInfo.applicationInfo.uid,
                            Constants.SYSTEMUI_PACKAGE_NAME,
                            1);
                    Slog.d(
                            "PermissionPolicyService",
                            "Ignore location AppOps which come from systemui");
                }
            } catch (Exception unused) {
            }
        }
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("Permission_grant_default_permissions-" + i);
        Settings.RuntimePermissionPersistence runtimePermissionPersistence =
                PackageManagerService.this.mSettings.mRuntimePermissionsPersistence;
        synchronized (runtimePermissionPersistence.mLock) {
            z = runtimePermissionPersistence.mPermissionUpgradeNeeded.get(i, true);
        }
        if (z) {
            if (!PermissionManager.USE_ACCESS_CHECKING_SERVICE) {
                timingsTraceAndSlog = new TimingsTraceAndSlog();
                Slog.i("PermissionPolicyService", "defaultPermsWereGrantedSinceBoot(" + i + ")");
                final AndroidFuture androidFuture = new AndroidFuture();
                new PermissionControllerManager(
                                getUserContext(getContext(), UserHandle.of(i)),
                                PermissionThread.getHandler())
                        .grantOrUpgradeDefaultRuntimePermissions(
                                PermissionThread.getExecutor(),
                                new Consumer() { // from class:
                                    // com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        AndroidFuture androidFuture2 = androidFuture;
                                        int i2 = i;
                                        if (((Boolean) obj).booleanValue()) {
                                            androidFuture2.complete((Object) null);
                                            return;
                                        }
                                        String str =
                                                "Error granting/upgrading runtime permissions for"
                                                    + " user "
                                                        + i2;
                                        Slog.wtf("PermissionPolicyService", str);
                                        androidFuture2.completeExceptionally(
                                                new IllegalStateException(str));
                                    }
                                });
                try {
                    try {
                        timingsTraceAndSlog.traceBegin("Permission_callback_waiting-" + i);
                        androidFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new IllegalStateException(e);
                    }
                } finally {
                    timingsTraceAndSlog.traceEnd();
                }
            }
            new PermissionControllerManager(
                            getUserContext(getContext(), UserHandle.of(i)),
                            PermissionThread.getHandler())
                    .updateUserSensitive();
            Settings.RuntimePermissionPersistence runtimePermissionPersistence2 =
                    PackageManagerService.this.mSettings.mRuntimePermissionsPersistence;
            synchronized (runtimePermissionPersistence2.mLock) {
                try {
                    String str = runtimePermissionPersistence2.mExtendedFingerprint;
                    if (str == null) {
                        throw new RuntimeException(
                                "The version of the permission controller hasn't been set before"
                                    + " trying to update the fingerprint.");
                    }
                    runtimePermissionPersistence2.mFingerprints.put(i, str);
                    runtimePermissionPersistence2.mPermissionUpgradeNeeded.put(i, false);
                    runtimePermissionPersistence2.writeStateForUserAsync(i);
                } finally {
                }
            }
        }
        timingsTraceAndSlog.traceEnd();
        synchronized (this.mLock) {
            this.mIsStarted.put(i, true);
            permissionManagerServiceImpl$$ExternalSyntheticLambda15 = this.mOnInitializedCallback;
        }
        timingsTraceAndSlog.traceBegin("Permission_synchronize_permissions-" + i);
        TimingsTraceAndSlog timingsTraceAndSlog2 = new TimingsTraceAndSlog();
        final PermissionToOpSynchroniser permissionToOpSynchroniser =
                new PermissionToOpSynchroniser(getUserContext(getContext(), UserHandle.of(i)));
        timingsTraceAndSlog2.traceBegin("Permission_synchronize_addPackages-" + i);
        this.mPackageManagerInternal.forEachPackage(
                new Consumer() { // from class:
                                 // com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PermissionPolicyService.PermissionToOpSynchroniser.this.addPackage(
                                ((AndroidPackage) obj).getPackageName());
                    }
                });
        timingsTraceAndSlog2.traceEnd();
        timingsTraceAndSlog2.traceBegin("Permission_syncPackages-" + i);
        PermissionToOpSynchroniser.m789$$Nest$msyncPackages(permissionToOpSynchroniser);
        timingsTraceAndSlog2.traceEnd();
        if (permissionManagerServiceImpl$$ExternalSyntheticLambda15 != null) {
            timingsTraceAndSlog.traceBegin("Permission_onInitialized-" + i);
            PermissionManagerServiceImpl permissionManagerServiceImpl =
                    permissionManagerServiceImpl$$ExternalSyntheticLambda15.f$0;
            permissionManagerServiceImpl.getClass();
            permissionManagerServiceImpl.updateAllPermissions(
                    StorageManager.UUID_PRIVATE_INTERNAL, false);
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        onStartUser$1(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mIsStarted.delete(targetUser.getUserIdentifier());
        }
    }

    public final void registerCarrierPrivilegesCallbacks() {
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = TelephonyManager.from(this.mContext);
        }
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            return;
        }
        int activeModemCount = telephonyManager.getActiveModemCount();
        for (int i = 0; i < activeModemCount; i++) {
            PhoneCarrierPrivilegesCallback phoneCarrierPrivilegesCallback =
                    new PhoneCarrierPrivilegesCallback(i);
            this.mPhoneCarrierPrivilegesCallbacks.add(phoneCarrierPrivilegesCallback);
            this.mTelephonyManager.registerCarrierPrivilegesCallback(
                    i, this.mContext.getMainExecutor(), phoneCarrierPrivilegesCallback);
        }
    }

    public final void resetAppOpPermissionsIfNotRequestedForUid(int i) {
        int i2;
        int i3;
        int i4;
        String[] strArr;
        synchronized (this.mLock) {
            this.mIsUidResetScheduled.delete(i);
        }
        Context context = getContext();
        PackageManager packageManager =
                getUserContext(context, UserHandle.getUserHandleForUid(i)).getPackageManager();
        String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (packagesForUid == null || packagesForUid.length == 0) {
            return;
        }
        ArraySet arraySet = new ArraySet();
        for (String str : packagesForUid) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 4096);
                if (packageInfo != null && (strArr = packageInfo.requestedPermissions) != null) {
                    Collections.addAll(arraySet, strArr);
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        AppOpsManagerInternal appOpsManagerInternal =
                (AppOpsManagerInternal) LocalServices.getService(AppOpsManagerInternal.class);
        int size = ((ArrayList) this.mAppOpPermissions).size();
        for (int i5 = 0; i5 < size; i5++) {
            String str2 = (String) ((ArrayList) this.mAppOpPermissions).get(i5);
            if (!arraySet.contains(str2)) {
                int permissionToOpCode = AppOpsManager.permissionToOpCode(str2);
                int opToDefaultMode = AppOpsManager.opToDefaultMode(permissionToOpCode);
                int length = packagesForUid.length;
                int i6 = 0;
                while (i6 < length) {
                    String str3 = packagesForUid[i6];
                    if (appOpsManager.unsafeCheckOpRawNoThrow(permissionToOpCode, i, str3)
                            != opToDefaultMode) {
                        appOpsManagerInternal.setUidModeFromPermissionPolicy(
                                permissionToOpCode, i, opToDefaultMode, this.mAppOpsCallback);
                        i2 = i6;
                        i3 = length;
                        i4 = opToDefaultMode;
                        appOpsManagerInternal.setModeFromPermissionPolicy(
                                permissionToOpCode, i, str3, opToDefaultMode, this.mAppOpsCallback);
                    } else {
                        i2 = i6;
                        i3 = length;
                        i4 = opToDefaultMode;
                    }
                    i6 = i2 + 1;
                    length = i3;
                    opToDefaultMode = i4;
                }
            }
        }
    }

    public final void synchronizeUidPermissionsAndAppOps(int i) {
        synchronized (this.mLock) {
            this.mIsUidSyncScheduled.delete(i);
        }
        PermissionToOpSynchroniser permissionToOpSynchroniser =
                new PermissionToOpSynchroniser(
                        getUserContext(getContext(), UserHandle.getUserHandleForUid(i)));
        int appId = UserHandle.getAppId(i);
        if (appId == 0 || appId == 1000) {
            return;
        }
        List packagesForAppId =
                ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal)
                        .mService
                        .snapshotComputer()
                        .getPackagesForAppId(appId);
        int size = packagesForAppId.size();
        for (int i2 = 0; i2 < size; i2++) {
            permissionToOpSynchroniser.addPackage(
                    ((AndroidPackage) packagesForAppId.get(i2)).getPackageName());
        }
        PermissionToOpSynchroniser.m789$$Nest$msyncPackages(permissionToOpSynchroniser);
    }

    public final void synchronizeUidPermissionsAndAppOpsAsync(int i) {
        if (isStarted(UserHandle.getUserId(i))) {
            synchronized (this.mLock) {
                try {
                    if (!this.mIsUidSyncScheduled.get(i)) {
                        FgThread.getHandler()
                                .sendMessage(
                                        PooledLambda.obtainMessage(
                                                new PermissionPolicyService$$ExternalSyntheticLambda4(
                                                        0),
                                                this,
                                                Integer.valueOf(i)));
                        this.mIsUidSyncScheduled.put(i, true);
                    }
                } finally {
                }
            }
        }
    }
}
