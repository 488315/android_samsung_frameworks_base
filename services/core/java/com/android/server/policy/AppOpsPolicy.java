package com.android.server.policy;

import android.app.AppOpsManager;
import android.app.AppOpsManagerInternal;
import android.app.SyncNotedAppOp;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.AttributionSource;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.location.LocationManagerInternal;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PackageTagsList;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.service.voice.VoiceInteractionManagerInternal;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.util.function.DodecFunction;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.HexFunction;
import com.android.internal.util.function.OctFunction;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.UndecFunction;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppOpsPolicy implements AppOpsManagerInternal.CheckOpsDelegate {
    public static final String LOG_TAG = AppOpsPolicy.class.getName();
    public static final boolean SYSPROP_HOTWORD_DETECTION_SERVICE_REQUIRED =
            SystemProperties.getBoolean("ro.hotword.detection_service_required", false);
    public final ConcurrentHashMap mActivityRecognitionTags;
    public final Context mContext;
    public final boolean mIsHotwordDetectionServiceRequired;
    public final ConcurrentHashMap mLocationTags;
    public final Object mLock = new Object();
    public final SparseArray mPerUidLocationTags;
    public final RoleManager mRoleManager;
    public final IBinder mToken;
    public final VoiceInteractionManagerInternal mVoiceInteractionManagerInternal;

    public AppOpsPolicy(Context context) {
        Binder binder = new Binder();
        this.mLocationTags = new ConcurrentHashMap();
        this.mPerUidLocationTags = new SparseArray();
        this.mActivityRecognitionTags = new ConcurrentHashMap();
        this.mContext = context;
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        this.mRoleManager = roleManager;
        this.mVoiceInteractionManagerInternal =
                (VoiceInteractionManagerInternal)
                        LocalServices.getService(VoiceInteractionManagerInternal.class);
        this.mIsHotwordDetectionServiceRequired =
                isHotwordDetectionServiceRequired(context.getPackageManager());
        ((LocationManagerInternal) LocalServices.getService(LocationManagerInternal.class))
                .setLocationPackageTagsListener(
                        new LocationManagerInternal
                                .LocationPackageTagsListener() { // from class:
                                                                 // com.android.server.policy.AppOpsPolicy$$ExternalSyntheticLambda0
                            public final void onLocationPackageTagsChanged(
                                    int i, PackageTagsList packageTagsList) {
                                AppOpsPolicy appOpsPolicy = AppOpsPolicy.this;
                                synchronized (appOpsPolicy.mLock) {
                                    try {
                                        if (packageTagsList.isEmpty()) {
                                            appOpsPolicy.mPerUidLocationTags.remove(i);
                                        } else {
                                            appOpsPolicy.mPerUidLocationTags.set(
                                                    i, packageTagsList);
                                        }
                                        int appId = UserHandle.getAppId(i);
                                        PackageTagsList.Builder builder =
                                                new PackageTagsList.Builder(1);
                                        int size = appOpsPolicy.mPerUidLocationTags.size();
                                        for (int i2 = 0; i2 < size; i2++) {
                                            if (UserHandle.getAppId(
                                                            appOpsPolicy.mPerUidLocationTags.keyAt(
                                                                    i2))
                                                    == appId) {
                                                builder.add(
                                                        (PackageTagsList)
                                                                appOpsPolicy.mPerUidLocationTags
                                                                        .valueAt(i2));
                                            }
                                        }
                                        appOpsPolicy.mLocationTags.put(
                                                Integer.valueOf(appId), builder.build());
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class: com.android.server.policy.AppOpsPolicy.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        Uri data = intent.getData();
                        if (data == null) {
                            return;
                        }
                        String schemeSpecificPart = data.getSchemeSpecificPart();
                        if (!TextUtils.isEmpty(schemeSpecificPart)
                                && AppOpsPolicy.this
                                        .mRoleManager
                                        .getRoleHolders(
                                                "android.app.role.SYSTEM_ACTIVITY_RECOGNIZER")
                                        .contains(schemeSpecificPart)) {
                            AppOpsPolicy.this.updateActivityRecognizerTags(schemeSpecificPart);
                        }
                    }
                };
        UserHandle userHandle = UserHandle.SYSTEM;
        context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, null);
        roleManager.addOnRoleHoldersChangedListenerAsUser(
                context.getMainExecutor(),
                new OnRoleHoldersChangedListener() { // from class:
                                                     // com.android.server.policy.AppOpsPolicy$$ExternalSyntheticLambda1
                    public final void onRoleHoldersChanged(String str, UserHandle userHandle2) {
                        AppOpsPolicy appOpsPolicy = AppOpsPolicy.this;
                        appOpsPolicy.getClass();
                        if ("android.app.role.SYSTEM_ACTIVITY_RECOGNIZER".equals(str)) {
                            appOpsPolicy.initializeActivityRecognizersTags();
                        }
                    }
                },
                userHandle);
        initializeActivityRecognizersTags();
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature("android.hardware.telephony")
                || packageManager.hasSystemFeature("android.hardware.microphone")
                || packageManager.hasSystemFeature("android.software.telecom")) {
            return;
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        appOpsManager.setUserRestrictionForUser(100, true, binder, null, -1);
        appOpsManager.setUserRestrictionForUser(101, true, binder, null, -1);
    }

    public static boolean isHotwordDetectionServiceRequired(PackageManager packageManager) {
        if (packageManager.hasSystemFeature("android.hardware.type.automotive")
                || packageManager.hasSystemFeature("android.software.leanback")) {
            return false;
        }
        return SYSPROP_HOTWORD_DETECTION_SERVICE_REQUIRED;
    }

    public static void writeTags(Map map, PrintWriter printWriter) {
        int i = 0;
        for (Map.Entry entry : ((ConcurrentHashMap) map).entrySet()) {
            printWriter.print("    #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.print(((Integer) entry.getKey()).toString());
            printWriter.print("=");
            ((PackageTagsList) entry.getValue()).dump(printWriter);
            i++;
        }
    }

    public final int checkAudioOperation(
            int i, int i2, int i3, String str, QuadFunction quadFunction) {
        return ((Integer)
                        quadFunction.apply(
                                Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str))
                .intValue();
    }

    public final int checkOperation(
            int i, int i2, String str, String str2, int i3, boolean z, HexFunction hexFunction) {
        return ((Integer)
                        hexFunction.apply(
                                Integer.valueOf(i),
                                Integer.valueOf(resolveUid(i, i2)),
                                str,
                                str2,
                                Integer.valueOf(i3),
                                Boolean.valueOf(z)))
                .intValue();
    }

    public final void dumpTags(PrintWriter printWriter) {
        if (!this.mLocationTags.isEmpty()) {
            printWriter.println("  AppOps policy location tags:");
            writeTags(this.mLocationTags, printWriter);
            printWriter.println();
        }
        if (this.mActivityRecognitionTags.isEmpty()) {
            return;
        }
        printWriter.println("  AppOps policy activity recognition tags:");
        writeTags(this.mActivityRecognitionTags, printWriter);
        printWriter.println();
    }

    public final void finishOperation(
            IBinder iBinder,
            int i,
            int i2,
            String str,
            String str2,
            int i3,
            HexConsumer hexConsumer) {
        hexConsumer.accept(
                iBinder,
                Integer.valueOf(resolveDatasourceOp(i, i2, str, str2)),
                Integer.valueOf(resolveUid(i, i2)),
                str,
                str2,
                Integer.valueOf(i3));
    }

    public final void finishProxyOperation(
            IBinder iBinder,
            int i,
            AttributionSource attributionSource,
            boolean z,
            QuadFunction quadFunction) {
        quadFunction.apply(
                iBinder,
                Integer.valueOf(
                        resolveDatasourceOp(
                                i,
                                attributionSource.getUid(),
                                attributionSource.getPackageName(),
                                attributionSource.getAttributionTag())),
                attributionSource,
                Boolean.valueOf(z));
    }

    public final void initializeActivityRecognizersTags() {
        List roleHolders =
                this.mRoleManager.getRoleHolders("android.app.role.SYSTEM_ACTIVITY_RECOGNIZER");
        int size = roleHolders.size();
        if (size <= 0) {
            synchronized (this.mLock) {
                this.mActivityRecognitionTags.clear();
            }
        } else {
            for (int i = 0; i < size; i++) {
                updateActivityRecognizerTags((String) roleHolders.get(i));
            }
        }
    }

    public final SyncNotedAppOp noteOperation(
            int i,
            int i2,
            String str,
            String str2,
            int i3,
            boolean z,
            String str3,
            boolean z2,
            OctFunction octFunction) {
        return (SyncNotedAppOp)
                octFunction.apply(
                        Integer.valueOf(resolveDatasourceOp(i, i2, str, str2)),
                        Integer.valueOf(resolveUid(i, i2)),
                        str,
                        str2,
                        Integer.valueOf(i3),
                        Boolean.valueOf(z),
                        str3,
                        Boolean.valueOf(z2));
    }

    public final SyncNotedAppOp noteProxyOperation(
            int i,
            AttributionSource attributionSource,
            boolean z,
            String str,
            boolean z2,
            boolean z3,
            HexFunction hexFunction) {
        return (SyncNotedAppOp)
                hexFunction.apply(
                        Integer.valueOf(
                                resolveDatasourceOp(
                                        i,
                                        attributionSource.getUid(),
                                        attributionSource.getPackageName(),
                                        attributionSource.getAttributionTag())),
                        attributionSource,
                        Boolean.valueOf(z),
                        str,
                        Boolean.valueOf(z2),
                        Boolean.valueOf(z3));
    }

    public final int resolveDatasourceOp(int i, int i2, String str, String str2) {
        PackageTagsList packageTagsList;
        VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity
                hotwordDetectionServiceIdentity;
        VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity
                hotwordDetectionServiceIdentity2;
        if (i == 102
                && this.mIsHotwordDetectionServiceRequired
                && ((hotwordDetectionServiceIdentity2 =
                                        this.mVoiceInteractionManagerInternal
                                                .getHotwordDetectionServiceIdentity())
                                == null
                        || i2 != hotwordDetectionServiceIdentity2.getIsolatedUid())) {
            i = 27;
        }
        if (Process.isIsolated(i2)
                && ((i == 27 || i == 26)
                        && (hotwordDetectionServiceIdentity =
                                        this.mVoiceInteractionManagerInternal
                                                .getHotwordDetectionServiceIdentity())
                                != null
                        && i2 == hotwordDetectionServiceIdentity.getIsolatedUid())) {
            if (i == 26) {
                i = 134;
            } else if (i == 27) {
                i = 135;
            }
        }
        if (str2 == null) {
            return i;
        }
        int i3 = i != 0 ? i != 1 ? i : 108 : 109;
        if (i3 != i) {
            PackageTagsList packageTagsList2 =
                    (PackageTagsList)
                            this.mLocationTags.get(Integer.valueOf(UserHandle.getAppId(i2)));
            if (packageTagsList2 != null && packageTagsList2.contains(str, str2)) {
                return i3;
            }
        } else {
            int i4 = i == 79 ? 113 : i;
            if (i4 != i
                    && (packageTagsList =
                                    (PackageTagsList)
                                            this.mActivityRecognitionTags.get(
                                                    Integer.valueOf(UserHandle.getAppId(i2))))
                            != null
                    && packageTagsList.contains(str, str2)) {
                return i4;
            }
        }
        return i;
    }

    public final int resolveUid(int i, int i2) {
        VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity
                hotwordDetectionServiceIdentity;
        return Process.isIsolated(i2)
                ? ((i == 27 || i == 102 || i == 26)
                                && (hotwordDetectionServiceIdentity =
                                                this.mVoiceInteractionManagerInternal
                                                        .getHotwordDetectionServiceIdentity())
                                        != null
                                && i2 == hotwordDetectionServiceIdentity.getIsolatedUid())
                        ? hotwordDetectionServiceIdentity.getOwnerUid()
                        : i2
                : i2;
    }

    public final SyncNotedAppOp startOperation(
            IBinder iBinder,
            int i,
            int i2,
            String str,
            String str2,
            int i3,
            boolean z,
            boolean z2,
            String str3,
            boolean z3,
            int i4,
            int i5,
            DodecFunction dodecFunction) {
        return (SyncNotedAppOp)
                dodecFunction.apply(
                        iBinder,
                        Integer.valueOf(resolveDatasourceOp(i, i2, str, str2)),
                        Integer.valueOf(resolveUid(i, i2)),
                        str,
                        str2,
                        Integer.valueOf(i3),
                        Boolean.valueOf(z),
                        Boolean.valueOf(z2),
                        str3,
                        Boolean.valueOf(z3),
                        Integer.valueOf(i4),
                        Integer.valueOf(i5));
    }

    public final SyncNotedAppOp startProxyOperation(
            IBinder iBinder,
            int i,
            AttributionSource attributionSource,
            boolean z,
            boolean z2,
            String str,
            boolean z3,
            boolean z4,
            int i2,
            int i3,
            int i4,
            UndecFunction undecFunction) {
        return (SyncNotedAppOp)
                undecFunction.apply(
                        iBinder,
                        Integer.valueOf(
                                resolveDatasourceOp(
                                        i,
                                        attributionSource.getUid(),
                                        attributionSource.getPackageName(),
                                        attributionSource.getAttributionTag())),
                        attributionSource,
                        Boolean.valueOf(z),
                        Boolean.valueOf(z2),
                        str,
                        Boolean.valueOf(z3),
                        Boolean.valueOf(z4),
                        Integer.valueOf(i2),
                        Integer.valueOf(i3),
                        Integer.valueOf(i4));
    }

    public final void updateActivityRecognizerTags(String str) {
        ServiceInfo serviceInfo;
        ResolveInfo resolveServiceAsUser =
                this.mContext
                        .getPackageManager()
                        .resolveServiceAsUser(
                                ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(
                                        "android.intent.action.ACTIVITY_RECOGNIZER", str),
                                819332,
                                0);
        if (resolveServiceAsUser == null
                || (serviceInfo = resolveServiceAsUser.serviceInfo) == null) {
            Log.w(
                    LOG_TAG,
                    "Service recognizer doesn't handle android.intent.action.ACTIVITY_RECOGNIZER,"
                        + " ignoring!");
            return;
        }
        Bundle bundle = serviceInfo.metaData;
        if (bundle == null) {
            return;
        }
        String string = bundle.getString("android:activity_recognition_allow_listed_tags");
        if (TextUtils.isEmpty(string)) {
            return;
        }
        PackageTagsList build =
                new PackageTagsList.Builder(1)
                        .add(
                                resolveServiceAsUser.serviceInfo.packageName,
                                Arrays.asList(string.split(";")))
                        .build();
        synchronized (this.mLock) {
            this.mActivityRecognitionTags.put(
                    Integer.valueOf(
                            UserHandle.getAppId(
                                    resolveServiceAsUser.serviceInfo.applicationInfo.uid)),
                    build);
        }
    }
}
