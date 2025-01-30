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
import com.android.internal.util.function.HeptFunction;
import com.android.internal.util.function.HexFunction;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.QuintFunction;
import com.android.internal.util.function.UndecFunction;
import com.android.server.LocalServices;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
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

  public static int resolveArOp(int i) {
    if (i == 79) {
      return 113;
    }
    return i;
  }

  public static int resolveLocationOp(int i) {
    if (i == 0) {
      return 109;
    }
    if (i != 1) {
      return i;
    }
    return 108;
  }

  public AppOpsPolicy(Context context) {
    Binder binder = new Binder();
    this.mToken = binder;
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
                AppOpsPolicy.this.lambda$new$0(i, packageTagsList);
              }
            });
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
    intentFilter.addDataScheme("package");
    context.registerReceiverAsUser(
        new BroadcastReceiver() { // from class: com.android.server.policy.AppOpsPolicy.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            Uri data = intent.getData();
            if (data == null) {
              return;
            }
            String schemeSpecificPart = data.getSchemeSpecificPart();
            if (!TextUtils.isEmpty(schemeSpecificPart)
                && AppOpsPolicy.this
                    .mRoleManager
                    .getRoleHolders("android.app.role.SYSTEM_ACTIVITY_RECOGNIZER")
                    .contains(schemeSpecificPart)) {
              AppOpsPolicy.this.updateActivityRecognizerTags(schemeSpecificPart);
            }
          }
        },
        UserHandle.SYSTEM,
        intentFilter,
        null,
        null);
    roleManager.addOnRoleHoldersChangedListenerAsUser(
        context.getMainExecutor(),
        new OnRoleHoldersChangedListener() { // from class:
                                             // com.android.server.policy.AppOpsPolicy$$ExternalSyntheticLambda1
          public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
            AppOpsPolicy.this.lambda$new$1(str, userHandle);
          }
        },
        UserHandle.SYSTEM);
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

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(int i, PackageTagsList packageTagsList) {
    synchronized (this.mLock) {
      if (packageTagsList.isEmpty()) {
        this.mPerUidLocationTags.remove(i);
      } else {
        this.mPerUidLocationTags.set(i, packageTagsList);
      }
      int appId = UserHandle.getAppId(i);
      PackageTagsList.Builder builder = new PackageTagsList.Builder(1);
      int size = this.mPerUidLocationTags.size();
      for (int i2 = 0; i2 < size; i2++) {
        if (UserHandle.getAppId(this.mPerUidLocationTags.keyAt(i2)) == appId) {
          builder.add((PackageTagsList) this.mPerUidLocationTags.valueAt(i2));
        }
      }
      updateAllowListedTagsForPackageLocked(appId, builder.build(), this.mLocationTags);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$1(String str, UserHandle userHandle) {
    if ("android.app.role.SYSTEM_ACTIVITY_RECOGNIZER".equals(str)) {
      initializeActivityRecognizersTags();
    }
  }

  public static boolean isHotwordDetectionServiceRequired(PackageManager packageManager) {
    if (packageManager.hasSystemFeature("android.hardware.type.automotive")
        || packageManager.hasSystemFeature("android.software.leanback")) {
      return false;
    }
    return SYSPROP_HOTWORD_DETECTION_SERVICE_REQUIRED;
  }

  public int checkOperation(
      int i, int i2, String str, String str2, boolean z, QuintFunction quintFunction) {
    return ((Integer)
            quintFunction.apply(
                Integer.valueOf(i),
                Integer.valueOf(resolveUid(i, i2)),
                str,
                str2,
                Boolean.valueOf(z)))
        .intValue();
  }

  public int checkAudioOperation(int i, int i2, int i3, String str, QuadFunction quadFunction) {
    return ((Integer)
            quadFunction.apply(Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str))
        .intValue();
  }

  public SyncNotedAppOp noteOperation(
      int i,
      int i2,
      String str,
      String str2,
      boolean z,
      String str3,
      boolean z2,
      HeptFunction heptFunction) {
    return (SyncNotedAppOp)
        heptFunction.apply(
            Integer.valueOf(resolveDatasourceOp(i, i2, str, str2)),
            Integer.valueOf(resolveUid(i, i2)),
            str,
            str2,
            Boolean.valueOf(z),
            str3,
            Boolean.valueOf(z2));
  }

  public SyncNotedAppOp noteProxyOperation(
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

  public SyncNotedAppOp startOperation(
      IBinder iBinder,
      int i,
      int i2,
      String str,
      String str2,
      boolean z,
      boolean z2,
      String str3,
      boolean z3,
      int i3,
      int i4,
      UndecFunction undecFunction) {
    return (SyncNotedAppOp)
        undecFunction.apply(
            iBinder,
            Integer.valueOf(resolveDatasourceOp(i, i2, str, str2)),
            Integer.valueOf(resolveUid(i, i2)),
            str,
            str2,
            Boolean.valueOf(z),
            Boolean.valueOf(z2),
            str3,
            Boolean.valueOf(z3),
            Integer.valueOf(i3),
            Integer.valueOf(i4));
  }

  public SyncNotedAppOp startProxyOperation(
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

  public void finishOperation(
      IBinder iBinder, int i, int i2, String str, String str2, QuintConsumer quintConsumer) {
    quintConsumer.accept(
        iBinder,
        Integer.valueOf(resolveDatasourceOp(i, i2, str, str2)),
        Integer.valueOf(resolveUid(i, i2)),
        str,
        str2);
  }

  public void finishProxyOperation(
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

  public void dumpTags(PrintWriter printWriter) {
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

  public final void writeTags(Map map, PrintWriter printWriter) {
    int i = 0;
    for (Map.Entry entry : map.entrySet()) {
      printWriter.print("    #");
      printWriter.print(i);
      printWriter.print(": ");
      printWriter.print(((Integer) entry.getKey()).toString());
      printWriter.print("=");
      ((PackageTagsList) entry.getValue()).dump(printWriter);
      i++;
    }
  }

  public final int resolveDatasourceOp(int i, int i2, String str, String str2) {
    int resolveSandboxedServiceOp = resolveSandboxedServiceOp(resolveRecordAudioOp(i, i2), i2);
    if (str2 == null) {
      return resolveSandboxedServiceOp;
    }
    int resolveLocationOp = resolveLocationOp(resolveSandboxedServiceOp);
    if (resolveLocationOp != resolveSandboxedServiceOp) {
      if (isDatasourceAttributionTag(i2, str, str2, this.mLocationTags)) {
        return resolveLocationOp;
      }
    } else {
      int resolveArOp = resolveArOp(resolveSandboxedServiceOp);
      if (resolveArOp != resolveSandboxedServiceOp
          && isDatasourceAttributionTag(i2, str, str2, this.mActivityRecognitionTags)) {
        return resolveArOp;
      }
    }
    return resolveSandboxedServiceOp;
  }

  public final void initializeActivityRecognizersTags() {
    List roleHolders =
        this.mRoleManager.getRoleHolders("android.app.role.SYSTEM_ACTIVITY_RECOGNIZER");
    int size = roleHolders.size();
    if (size <= 0) {
      clearActivityRecognitionTags();
      return;
    }
    for (int i = 0; i < size; i++) {
      updateActivityRecognizerTags((String) roleHolders.get(i));
    }
  }

  public final void clearActivityRecognitionTags() {
    synchronized (this.mLock) {
      this.mActivityRecognitionTags.clear();
    }
  }

  public final void updateActivityRecognizerTags(String str) {
    ServiceInfo serviceInfo;
    Intent intent = new Intent("android.intent.action.ACTIVITY_RECOGNIZER");
    intent.setPackage(str);
    ResolveInfo resolveServiceAsUser =
        this.mContext.getPackageManager().resolveServiceAsUser(intent, 819332, 0);
    if (resolveServiceAsUser == null || (serviceInfo = resolveServiceAsUser.serviceInfo) == null) {
      Log.w(
          LOG_TAG,
          "Service recognizer doesn't handle android.intent.action.ACTIVITY_RECOGNIZER, ignoring!");
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
                Arrays.asList(string.split(KnoxVpnFirewallHelper.DELIMITER)))
            .build();
    synchronized (this.mLock) {
      updateAllowListedTagsForPackageLocked(
          UserHandle.getAppId(resolveServiceAsUser.serviceInfo.applicationInfo.uid),
          build,
          this.mActivityRecognitionTags);
    }
  }

  public static void updateAllowListedTagsForPackageLocked(
      int i, PackageTagsList packageTagsList, ConcurrentHashMap concurrentHashMap) {
    concurrentHashMap.put(Integer.valueOf(i), packageTagsList);
  }

  public static boolean isDatasourceAttributionTag(int i, String str, String str2, Map map) {
    PackageTagsList packageTagsList =
        (PackageTagsList) map.get(Integer.valueOf(UserHandle.getAppId(i)));
    return packageTagsList != null && packageTagsList.contains(str, str2);
  }

  public final int resolveRecordAudioOp(int i, int i2) {
    if (i != 102 || !this.mIsHotwordDetectionServiceRequired) {
      return i;
    }
    VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity
        hotwordDetectionServiceIdentity =
            this.mVoiceInteractionManagerInternal.getHotwordDetectionServiceIdentity();
    if (hotwordDetectionServiceIdentity == null
        || i2 != hotwordDetectionServiceIdentity.getIsolatedUid()) {
      return 27;
    }
    return i;
  }

  public final int resolveSandboxedServiceOp(int i, int i2) {
    VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity;
    if (Process.isIsolated(i2)
        && ((i == 27 || i == 26)
            && (hotwordDetectionServiceIdentity =
                    this.mVoiceInteractionManagerInternal.getHotwordDetectionServiceIdentity())
                != null
            && i2 == hotwordDetectionServiceIdentity.getIsolatedUid())) {
      if (i == 26) {
        return 134;
      }
      if (i == 27) {
        return 135;
      }
    }
    return i;
  }

  public final int resolveUid(int i, int i2) {
    VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity;
    return Process.isIsolated(i2)
        ? ((i == 27 || i == 102 || i == 26)
                && (hotwordDetectionServiceIdentity =
                        this.mVoiceInteractionManagerInternal.getHotwordDetectionServiceIdentity())
                    != null
                && i2 == hotwordDetectionServiceIdentity.getIsolatedUid())
            ? hotwordDetectionServiceIdentity.getOwnerUid()
            : i2
        : i2;
  }
}
