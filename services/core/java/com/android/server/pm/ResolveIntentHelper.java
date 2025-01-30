package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.IUnsafeIntentStrictModeCallback;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.app.ResolverActivity;
import com.android.internal.util.ArrayUtils;
import com.android.server.LocalServices;
import com.android.server.am.ActivityManagerUtils;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.resolution.ComponentResolverApi;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.custom.ProKioskManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final class ResolveIntentHelper {
  public final Context mContext;
  public final DomainVerificationManagerInternal mDomainVerificationManager;
  public final Handler mHandler;
  public final Supplier mInstantAppInstallerActivitySupplier;
  public final PlatformCompat mPlatformCompat;
  public final PreferredActivityHelper mPreferredActivityHelper;
  public final Supplier mResolveInfoSupplier;
  public final UserManagerService mUserManager;
  public final UserNeedsBadgingCache mUserNeedsBadging;

  public ResolveIntentHelper(
      Context context,
      PreferredActivityHelper preferredActivityHelper,
      PlatformCompat platformCompat,
      UserManagerService userManagerService,
      DomainVerificationManagerInternal domainVerificationManagerInternal,
      UserNeedsBadgingCache userNeedsBadgingCache,
      Supplier supplier,
      Supplier supplier2,
      Handler handler) {
    this.mContext = context;
    this.mPreferredActivityHelper = preferredActivityHelper;
    this.mPlatformCompat = platformCompat;
    this.mUserManager = userManagerService;
    this.mDomainVerificationManager = domainVerificationManagerInternal;
    this.mUserNeedsBadging = userNeedsBadgingCache;
    this.mResolveInfoSupplier = supplier;
    this.mInstantAppInstallerActivitySupplier = supplier2;
    this.mHandler = handler;
  }

  public static void filterNonExportedComponents(
      final Intent intent,
      int i,
      final int i2,
      List list,
      PlatformCompat platformCompat,
      String str,
      Computer computer,
      Handler handler) {
    if (list == null
        || intent.getPackage() != null
        || intent.getComponent() != null
        || ActivityManager.canAccessUnexportedComponents(i)) {
      return;
    }
    AndroidPackage androidPackage = computer.getPackage(i);
    if (androidPackage != null) {
      androidPackage.getPackageName();
    }
    final ActivityManagerInternal activityManagerInternal =
        (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
    final IUnsafeIntentStrictModeCallback registeredStrictModeCallback =
        activityManagerInternal.getRegisteredStrictModeCallback(i2);
    for (int size = list.size() - 1; size >= 0; size--) {
      if (!((ResolveInfo) list.get(size)).getComponentInfo().exported) {
        boolean isChangeEnabledByUid = platformCompat.isChangeEnabledByUid(229362273L, i);
        ActivityManagerUtils.logUnsafeIntentEvent(2, i, intent, str, isChangeEnabledByUid);
        if (registeredStrictModeCallback != null) {
          handler.post(
              new Runnable() { // from class:
                // com.android.server.pm.ResolveIntentHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  ResolveIntentHelper.lambda$filterNonExportedComponents$0(
                      registeredStrictModeCallback, intent, activityManagerInternal, i2);
                }
              });
        }
        if (!isChangeEnabledByUid) {
          return;
        } else {
          list.remove(size);
        }
      }
    }
  }

  public static /* synthetic */ void lambda$filterNonExportedComponents$0(
      IUnsafeIntentStrictModeCallback iUnsafeIntentStrictModeCallback,
      Intent intent,
      ActivityManagerInternal activityManagerInternal,
      int i) {
    try {
      iUnsafeIntentStrictModeCallback.onImplicitIntentMatchedInternalComponent(
          intent.cloneFilter());
    } catch (RemoteException unused) {
      activityManagerInternal.unregisterStrictModeCallback(i);
    }
  }

  public ResolveInfo resolveIntentInternal(
      Computer computer, Intent intent, String str, long j, long j2, int i, boolean z, int i2) {
    return resolveIntentInternal(computer, intent, str, j, j2, i, z, i2, false, 0);
  }

  public ResolveInfo resolveIntentInternal(
      Computer computer,
      Intent intent,
      String str,
      long j,
      long j2,
      int i,
      boolean z,
      int i2,
      boolean z2,
      int i3) {
    try {
      Trace.traceBegin(262144L, "resolveIntent");
      if (!this.mUserManager.exists(i)) {
        return null;
      }
      int callingUid = Binder.getCallingUid();
      long updateFlagsForResolve =
          computer.updateFlagsForResolve(
              j, i, i2, z, computer.isImplicitImageCaptureIntentAndNotSetByDpc(intent, i, str, j));
      computer.enforceCrossUserPermission(callingUid, i, false, false, "resolve intent");
      if (PersonaServiceHelper.isForKnoxNFC()) {
        Log.d(
            "PackageManager",
            "Resolving for NFC " + intent + " flag " + updateFlagsForResolve + " user " + i);
        if ((131072 & updateFlagsForResolve) != 0) {
          Log.d("PackageManager", "Get preferred activity for NFC of user " + i);
          return PersonaServiceHelper.getPreferredInfoForUser(
              computer, intent, str, updateFlagsForResolve, i, this.mPreferredActivityHelper);
        }
      }
      Trace.traceBegin(262144L, "queryIntentActivities");
      List queryIntentActivitiesInternal =
          computer.queryIntentActivitiesInternal(
              intent, str, updateFlagsForResolve, j2, i2, i, z, true);
      if (z2) {
        filterNonExportedComponents(
            intent,
            i2,
            i3,
            queryIntentActivitiesInternal,
            this.mPlatformCompat,
            str,
            computer,
            this.mHandler);
      }
      Trace.traceEnd(262144L);
      boolean z3 = true;
      ResolveInfo chooseBestActivity =
          chooseBestActivity(
              computer,
              intent,
              str,
              updateFlagsForResolve,
              j2,
              queryIntentActivitiesInternal,
              i,
              UserHandle.getAppId(i2) >= 10000 && !z);
      if ((j2 & 1) == 0) {
        z3 = false;
      }
      if (z3 && chooseBestActivity != null && chooseBestActivity.handleAllWebDataURI) {
        return null;
      }
      return chooseBestActivity;
    } finally {
      Trace.traceEnd(262144L);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public final ResolveInfo chooseBestActivity(
      Computer computer, Intent intent, String str, long j, long j2, List list, int i, boolean z) {
    int i2;
    int i3;
    int i4;
    PackageStateInternal packageStateInternal;
    String homeActivity;
    if (list == null) {
      return null;
    }
    int size = list.size();
    ProKioskManager proKioskManager = ProKioskManager.getInstance();
    if (proKioskManager == null
        || !intent.hasCategory("android.intent.category.HOME")
        || intent.getComponent() != null
        || size <= 0
        || !proKioskManager.getProKioskState()
        || (homeActivity = proKioskManager.getHomeActivity()) == null) {
      i2 = 1;
      i3 = size;
    } else {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        ResolveInfo resolveInfo = (ResolveInfo) it.next();
        if (homeActivity.startsWith(resolveInfo.activityInfo.packageName)) {
          return resolveInfo;
        }
      }
      i2 = 1;
      i3 = size;
      ResolveInfo findPreferredActivityNotLocked =
          this.mPreferredActivityHelper.findPreferredActivityNotLocked(
              computer, intent, str, j, list, true, false, (intent.getFlags() & 8) != 0, i, z);
      if (findPreferredActivityNotLocked != null) {
        return findPreferredActivityNotLocked;
      }
    }
    if (i3 == i2) {
      return (ResolveInfo) list.get(0);
    }
    if (i3 <= i2) {
      return null;
    }
    boolean z2 = (intent.getFlags() & 8) != 0 ? i2 : 0;
    ResolveInfo resolveInfo2 = (ResolveInfo) list.get(0);
    ResolveInfo resolveInfo3 = (ResolveInfo) list.get(i2);
    if (z2 != 0) {
      Slog.v(
          "PackageManager",
          resolveInfo2.activityInfo.name
              + "="
              + resolveInfo2.priority
              + " vs "
              + resolveInfo3.activityInfo.name
              + "="
              + resolveInfo3.priority);
    }
    if (resolveInfo2.priority != resolveInfo3.priority
        || resolveInfo2.preferredOrder != resolveInfo3.preferredOrder
        || resolveInfo2.isDefault != resolveInfo3.isDefault) {
      return (ResolveInfo) list.get(0);
    }
    ResolveInfo findPreferredActivityNotLocked2 =
        this.mPreferredActivityHelper.findPreferredActivityNotLocked(
            computer, intent, str, j, list, true, false, z2, i, z);
    if (findPreferredActivityNotLocked2 != null) {
      return findPreferredActivityNotLocked2;
    }
    int i5 = 0;
    int i6 = 0;
    while (i6 < i3) {
      ResolveInfo resolveInfo4 = (ResolveInfo) list.get(i6);
      if (resolveInfo4.handleAllWebDataURI) {
        i5++;
      }
      int i7 = i5;
      if (resolveInfo4.activityInfo.applicationInfo.isInstantApp()
          && (packageStateInternal =
                  computer.getPackageStateInternal(resolveInfo4.activityInfo.packageName))
              != null
          && PackageManagerServiceUtils.hasAnyDomainApproval(
              this.mDomainVerificationManager, packageStateInternal, intent, j, i)) {
        return resolveInfo4;
      }
      i6++;
      i5 = i7;
    }
    if ((j2 & 2) != 0) {
      return null;
    }
    ResolveInfo resolveInfo5 = new ResolveInfo((ResolveInfo) this.mResolveInfoSupplier.get());
    resolveInfo5.handleAllWebDataURI = i5 == i3;
    ActivityInfo activityInfo = new ActivityInfo(resolveInfo5.activityInfo);
    resolveInfo5.activityInfo = activityInfo;
    activityInfo.labelRes = ResolverActivity.getLabelRes(intent.getAction());
    if (resolveInfo5.userHandle == null) {
      resolveInfo5.userHandle = UserHandle.of(i);
    }
    String str2 = intent.getPackage();
    if (TextUtils.isEmpty(str2) || !allHavePackage(list, str2)) {
      i4 = i;
    } else {
      ApplicationInfo applicationInfo = ((ResolveInfo) list.get(0)).activityInfo.applicationInfo;
      resolveInfo5.resolvePackageName = str2;
      i4 = i;
      if (this.mUserNeedsBadging.get(i4)) {
        resolveInfo5.noResourceId = true;
      } else {
        resolveInfo5.icon = applicationInfo.icon;
      }
      resolveInfo5.iconResourceId = applicationInfo.icon;
      resolveInfo5.labelRes = applicationInfo.labelRes;
    }
    resolveInfo5.activityInfo.applicationInfo =
        new ApplicationInfo(resolveInfo5.activityInfo.applicationInfo);
    if (i4 != 0) {
      ApplicationInfo applicationInfo2 = resolveInfo5.activityInfo.applicationInfo;
      applicationInfo2.uid = UserHandle.getUid(i4, UserHandle.getAppId(applicationInfo2.uid));
    }
    ActivityInfo activityInfo2 = resolveInfo5.activityInfo;
    if (activityInfo2.metaData == null) {
      activityInfo2.metaData = new Bundle();
    }
    resolveInfo5.activityInfo.metaData.putBoolean("android.dock_home", true);
    return resolveInfo5;
  }

  public final boolean allHavePackage(List list, String str) {
    if (ArrayUtils.isEmpty(list)) {
      return false;
    }
    int size = list.size();
    for (int i = 0; i < size; i++) {
      ResolveInfo resolveInfo = (ResolveInfo) list.get(i);
      ActivityInfo activityInfo = resolveInfo != null ? resolveInfo.activityInfo : null;
      if (activityInfo == null || !str.equals(activityInfo.packageName)) {
        return false;
      }
    }
    return true;
  }

  public IntentSender getLaunchIntentSenderForPackage(
      Computer computer, String str, String str2, String str3, int i) {
    Objects.requireNonNull(str);
    int callingUid = Binder.getCallingUid();
    computer.enforceCrossUserPermission(
        callingUid, i, false, false, "get launch intent sender for package");
    if (!UserHandle.isSameApp(callingUid, computer.getPackageUid(str2, 0L, i))) {
      throw new SecurityException(
          "getLaunchIntentSenderForPackage() from calling uid: "
              + callingUid
              + " does not own package: "
              + str2);
    }
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.INFO");
    intent.setPackage(str);
    ContentResolver contentResolver = this.mContext.getContentResolver();
    String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(contentResolver);
    List queryIntentActivitiesInternal =
        computer.queryIntentActivitiesInternal(
            intent, resolveTypeIfNeeded, 0L, 0L, callingUid, i, true, false);
    if (queryIntentActivitiesInternal == null || queryIntentActivitiesInternal.size() <= 0) {
      intent.removeCategory("android.intent.category.INFO");
      intent.addCategory("android.intent.category.LAUNCHER");
      intent.setPackage(str);
      resolveTypeIfNeeded = intent.resolveTypeIfNeeded(contentResolver);
      queryIntentActivitiesInternal =
          computer.queryIntentActivitiesInternal(
              intent, resolveTypeIfNeeded, 0L, 0L, callingUid, i, true, false);
    }
    Intent intent2 = new Intent(intent);
    intent2.setFlags(268435456);
    if (queryIntentActivitiesInternal != null && !queryIntentActivitiesInternal.isEmpty()) {
      intent2.setPackage(null);
      intent2.setClassName(
          ((ResolveInfo) queryIntentActivitiesInternal.get(0)).activityInfo.packageName,
          ((ResolveInfo) queryIntentActivitiesInternal.get(0)).activityInfo.name);
    }
    return new IntentSender(
        ActivityManager.getService()
            .getIntentSenderWithFeature(
                2,
                str2,
                str3,
                (IBinder) null,
                (String) null,
                1,
                new Intent[] {intent2},
                resolveTypeIfNeeded != null ? new String[] {resolveTypeIfNeeded} : null,
                67108864,
                (Bundle) null,
                i));
  }

  public List queryIntentReceiversInternal(
      Computer computer, Intent intent, String str, long j, int i, int i2) {
    return queryIntentReceiversInternal(computer, intent, str, j, i, i2, false);
  }

  /* JADX WARN: Code restructure failed: missing block: B:78:0x0144, code lost:

     if (r1 != null) goto L80;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public List queryIntentReceiversInternal(
      Computer computer, Intent intent, String str, long j, int i, int i2, boolean z) {
    Intent intent2;
    Intent intent3;
    List queryReceivers;
    List queryReceivers2;
    if (!this.mUserManager.exists(i)) {
      return Collections.emptyList();
    }
    boolean z2 = false;
    int i3 =
        ("com.sec.android.app.dictionary.SEARCH".equals(intent.getAction())
                && SemDualAppManager.isDualAppId(i))
            ? 0
            : i;
    int i4 = z ? 1000 : i2;
    int i5 = i3;
    computer.enforceCrossUserPermission(i4, i5, false, false, "query intent receivers");
    String instantAppPackageName = computer.getInstantAppPackageName(i4);
    long updateFlagsForResolve =
        computer.updateFlagsForResolve(
            j,
            i3,
            i4,
            false,
            computer.isImplicitImageCaptureIntentAndNotSetByDpc(intent, i5, str, j));
    ComponentName component = intent.getComponent();
    if (component != null || intent.getSelector() == null) {
      intent2 = intent;
      intent3 = null;
    } else {
      Intent selector = intent.getSelector();
      intent3 = intent;
      intent2 = selector;
      component = selector.getComponent();
    }
    ComponentResolverApi componentResolver = computer.getComponentResolver();
    List emptyList = Collections.emptyList();
    if (component != null) {
      ActivityInfo receiverInfo = computer.getReceiverInfo(component, updateFlagsForResolve, i3);
      if (receiverInfo != null) {
        boolean z3 = (8388608 & updateFlagsForResolve) != 0;
        boolean z4 = (updateFlagsForResolve & 16777216) != 0;
        boolean z5 = (updateFlagsForResolve & 33554432) != 0;
        boolean z6 = instantAppPackageName != null;
        boolean equals = component.getPackageName().equals(instantAppPackageName);
        boolean z7 = (receiverInfo.applicationInfo.privateFlags & 128) != 0;
        int i6 = receiverInfo.flags;
        boolean z8 = (i6 & 1048576) != 0;
        boolean z9 = !z8 || (z5 && !(z8 && (i6 & 2097152) == 0));
        if (!equals && ((!z3 && !z6 && z7) || (z4 && z6 && z9))) {
          z2 = true;
        }
        if (!z2) {
          ResolveInfo resolveInfo = new ResolveInfo();
          resolveInfo.activityInfo = receiverInfo;
          emptyList = new ArrayList(1);
          emptyList.add(resolveInfo);
          PackageManagerServiceUtils.applyEnforceIntentFilterMatching(
              this.mPlatformCompat, componentResolver, emptyList, true, intent2, str, i2);
        }
      }
    } else {
      String str2 = intent2.getPackage();
      if (str2 == null
          && (queryReceivers2 =
                  componentResolver.queryReceivers(
                      computer, intent2, str, updateFlagsForResolve, i3))
              != null) {
        emptyList = queryReceivers2;
      }
      AndroidPackage androidPackage = computer.getPackage(str2);
      if (androidPackage != null) {
        queryReceivers =
            componentResolver.queryReceivers(
                computer, intent2, str, updateFlagsForResolve, androidPackage.getReceivers(), i3);
      }
    }
    queryReceivers = emptyList;
    if (intent3 != null) {
      PackageManagerServiceUtils.applyEnforceIntentFilterMatching(
          this.mPlatformCompat, componentResolver, queryReceivers, true, intent3, str, i2);
    }
    return computer.applyPostResolutionFilter(
        queryReceivers, instantAppPackageName, false, i4, false, i3, intent2);
  }

  public ResolveInfo resolveServiceInternal(
      Computer computer, Intent intent, String str, long j, int i, int i2) {
    List queryIntentServicesInternal;
    if (this.mUserManager.exists(i)
        && (queryIntentServicesInternal =
                computer.queryIntentServicesInternal(
                    intent,
                    str,
                    computer.updateFlagsForResolve(j, i, i2, false, false),
                    i,
                    i2,
                    false))
            != null
        && queryIntentServicesInternal.size() >= 1) {
      return (ResolveInfo) queryIntentServicesInternal.get(0);
    }
    return null;
  }

  public List queryIntentContentProvidersInternal(
      Computer computer, Intent intent, String str, long j, int i) {
    Intent intent2;
    if (!this.mUserManager.exists(i)) {
      return Collections.emptyList();
    }
    int callingUid = Binder.getCallingUid();
    String instantAppPackageName = computer.getInstantAppPackageName(callingUid);
    long updateFlagsForResolve = computer.updateFlagsForResolve(j, i, callingUid, false, false);
    ComponentName component = intent.getComponent();
    if (component != null || intent.getSelector() == null) {
      intent2 = intent;
    } else {
      Intent selector = intent.getSelector();
      intent2 = selector;
      component = selector.getComponent();
    }
    if (component != null) {
      ArrayList arrayList = new ArrayList(1);
      ProviderInfo providerInfo = computer.getProviderInfo(component, updateFlagsForResolve, i);
      if (providerInfo != null) {
        boolean z = (8388608 & updateFlagsForResolve) != 0;
        boolean z2 = (updateFlagsForResolve & 16777216) != 0;
        boolean z3 = instantAppPackageName != null;
        boolean equals = component.getPackageName().equals(instantAppPackageName);
        ApplicationInfo applicationInfo = providerInfo.applicationInfo;
        boolean z4 = (applicationInfo.privateFlags & 128) != 0;
        boolean z5 =
            !equals && (!(z || z3 || !z4) || (z2 && z3 && ((providerInfo.flags & 1048576) == 0)));
        boolean z6 =
            (z4
                    || z3
                    || !computer.shouldFilterApplication(
                        computer.getPackageStateInternal(applicationInfo.packageName, 1000),
                        callingUid,
                        i))
                ? false
                : true;
        if (!z5 && !z6) {
          ResolveInfo resolveInfo = new ResolveInfo();
          resolveInfo.providerInfo = providerInfo;
          arrayList.add(resolveInfo);
        }
      }
      return arrayList;
    }
    ComponentResolverApi componentResolver = computer.getComponentResolver();
    String str2 = intent2.getPackage();
    if (str2 == null) {
      List queryProviders =
          componentResolver.queryProviders(computer, intent2, str, updateFlagsForResolve, i);
      if (queryProviders == null) {
        return Collections.emptyList();
      }
      return applyPostContentProviderResolutionFilter(
          computer, queryProviders, instantAppPackageName, i, callingUid);
    }
    AndroidPackage androidPackage = computer.getPackage(str2);
    if (androidPackage != null) {
      List queryProviders2 =
          componentResolver.queryProviders(
              computer, intent2, str, updateFlagsForResolve, androidPackage.getProviders(), i);
      if (queryProviders2 == null) {
        return Collections.emptyList();
      }
      return applyPostContentProviderResolutionFilter(
          computer, queryProviders2, instantAppPackageName, i, callingUid);
    }
    return Collections.emptyList();
  }

  public final List applyPostContentProviderResolutionFilter(
      Computer computer, List list, String str, int i, int i2) {
    for (int size = list.size() - 1; size >= 0; size--) {
      ResolveInfo resolveInfo = (ResolveInfo) list.get(size);
      if (str != null
          || computer.shouldFilterApplication(
              computer.getPackageStateInternal(resolveInfo.providerInfo.packageName, 0), i2, i)) {
        boolean isInstantApp = resolveInfo.providerInfo.applicationInfo.isInstantApp();
        if (isInstantApp && str.equals(resolveInfo.providerInfo.packageName)) {
          ProviderInfo providerInfo = resolveInfo.providerInfo;
          String str2 = providerInfo.splitName;
          if (str2 != null && !ArrayUtils.contains(providerInfo.applicationInfo.splitNames, str2)) {
            if (this.mInstantAppInstallerActivitySupplier.get() == null) {
              if (PackageManagerService.DEBUG_INSTANT) {
                Slog.v("PackageManager", "No installer - not adding it to the ResolveInfo list");
              }
              list.remove(size);
            } else {
              if (PackageManagerService.DEBUG_INSTANT) {
                Slog.v("PackageManager", "Adding ephemeral installer to the ResolveInfo list");
              }
              ResolveInfo resolveInfo2 = new ResolveInfo(computer.getInstantAppInstallerInfo());
              ProviderInfo providerInfo2 = resolveInfo.providerInfo;
              resolveInfo2.auxiliaryInfo =
                  new AuxiliaryResolveInfo(
                      (ComponentName) null,
                      providerInfo2.packageName,
                      providerInfo2.applicationInfo.longVersionCode,
                      providerInfo2.splitName);
              resolveInfo2.filter = new IntentFilter();
              resolveInfo2.resolvePackageName = resolveInfo.getComponentInfo().packageName;
              list.set(size, resolveInfo2);
            }
          }
        } else if (isInstantApp || (resolveInfo.providerInfo.flags & 1048576) == 0) {
          list.remove(size);
        }
      }
    }
    return list;
  }

  /* JADX WARN: Removed duplicated region for block: B:32:0x00e8  */
  /* JADX WARN: Removed duplicated region for block: B:52:0x0133  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public List queryIntentActivityOptionsInternal(
      Computer computer,
      ComponentName componentName,
      Intent[] intentArr,
      String[] strArr,
      Intent intent,
      String str,
      long j,
      int i) {
    List list;
    String str2;
    int i2;
    Iterator<String> actionsIterator;
    String str3;
    int i3;
    int i4;
    List list2;
    String str4;
    ResolveInfo resolveInfo;
    ComponentName componentName2;
    ActivityInfo activityInfo;
    int size;
    int i5;
    List list3;
    int i6;
    String str5;
    Intent[] intentArr2 = intentArr;
    if (!this.mUserManager.exists(i)) {
      return Collections.emptyList();
    }
    int callingUid = Binder.getCallingUid();
    long updateFlagsForResolve =
        computer.updateFlagsForResolve(
            j,
            i,
            callingUid,
            false,
            computer.isImplicitImageCaptureIntentAndNotSetByDpc(intent, i, str, j));
    computer.enforceCrossUserPermission(
        callingUid, i, false, false, "query intent activity options");
    String action = intent.getAction();
    List queryIntentActivitiesInternal =
        computer.queryIntentActivitiesInternal(intent, str, updateFlagsForResolve | 64, i);
    String str6 = null;
    if (intentArr2 != null) {
      int i7 = 0;
      i2 = 0;
      while (i7 < intentArr2.length) {
        Intent intent2 = intentArr2[i7];
        if (intent2 == null) {
          i3 = i7;
          i4 = i2;
          list2 = queryIntentActivitiesInternal;
          str4 = action;
        } else {
          String action2 = intent2.getAction();
          String str7 = (action == null || !action.equals(action2)) ? action2 : str6;
          ComponentName component = intent2.getComponent();
          if (component == null) {
            str3 = str7;
            i3 = i7;
            i4 = i2;
            list2 = queryIntentActivitiesInternal;
            str4 = action;
            resolveInfo =
                resolveIntentInternal(
                    computer,
                    intent2,
                    strArr != null ? strArr[i7] : str6,
                    updateFlagsForResolve,
                    0L,
                    i,
                    false,
                    Binder.getCallingUid());
            if (resolveInfo != null) {
              this.mResolveInfoSupplier.get();
              activityInfo = resolveInfo.activityInfo;
              componentName2 =
                  new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
              size = list2.size();
              i5 = i4;
              while (i5 < size) {
                List list4 = list2;
                ResolveInfo resolveInfo2 = (ResolveInfo) list4.get(i5);
                if (resolveInfo2.activityInfo.name.equals(componentName2.getClassName())
                    && resolveInfo2.activityInfo.applicationInfo.packageName.equals(
                        componentName2.getPackageName())) {
                  str5 = str3;
                } else {
                  str5 = str3;
                  if (str5 != null) {
                    if (!resolveInfo2.filter.matchAction(str5)) {}
                  }
                  i5++;
                  list2 = list4;
                  str3 = str5;
                }
                list4.remove(i5);
                if (resolveInfo == null) {
                  resolveInfo = resolveInfo2;
                }
                i5--;
                size--;
                i5++;
                list2 = list4;
                str3 = str5;
              }
              list3 = list2;
              if (resolveInfo == null) {
                resolveInfo = new ResolveInfo();
                resolveInfo.activityInfo = activityInfo;
              }
              int i8 = i4;
              list3.add(i8, resolveInfo);
              i6 = i3;
              resolveInfo.specificIndex = i6;
              i2 = i8 + 1;
            }
          } else {
            str3 = str7;
            i3 = i7;
            i4 = i2;
            list2 = queryIntentActivitiesInternal;
            str4 = action;
            ActivityInfo activityInfo2 =
                computer.getActivityInfo(component, updateFlagsForResolve, i);
            if (activityInfo2 != null) {
              resolveInfo = null;
              componentName2 = component;
              activityInfo = activityInfo2;
              size = list2.size();
              i5 = i4;
              while (i5 < size) {}
              list3 = list2;
              if (resolveInfo == null) {}
              int i82 = i4;
              list3.add(i82, resolveInfo);
              i6 = i3;
              resolveInfo.specificIndex = i6;
              i2 = i82 + 1;
            }
          }
          i7 = i6 + 1;
          queryIntentActivitiesInternal = list3;
          action = str4;
          str6 = null;
          intentArr2 = intentArr;
        }
        list3 = list2;
        i2 = i4;
        i6 = i3;
        i7 = i6 + 1;
        queryIntentActivitiesInternal = list3;
        action = str4;
        str6 = null;
        intentArr2 = intentArr;
      }
      list = queryIntentActivitiesInternal;
      str2 = action;
    } else {
      list = queryIntentActivitiesInternal;
      str2 = action;
      i2 = 0;
    }
    int size2 = list.size();
    while (i2 < size2 - 1) {
      ResolveInfo resolveInfo3 = (ResolveInfo) list.get(i2);
      IntentFilter intentFilter = resolveInfo3.filter;
      if (intentFilter != null && (actionsIterator = intentFilter.actionsIterator()) != null) {
        while (actionsIterator.hasNext()) {
          String next = actionsIterator.next();
          if (str2 == null || !str2.equals(next)) {
            int i9 = i2 + 1;
            while (i9 < size2) {
              IntentFilter intentFilter2 = ((ResolveInfo) list.get(i9)).filter;
              if (intentFilter2 != null && intentFilter2.hasAction(next)) {
                list.remove(i9);
                i9--;
                size2--;
              }
              i9++;
            }
          }
        }
        if ((updateFlagsForResolve & 64) == 0) {
          resolveInfo3.filter = null;
          i2++;
        }
      }
      i2++;
    }
    if (componentName != null) {
      int size3 = list.size();
      int i10 = 0;
      while (true) {
        if (i10 >= size3) {
          break;
        }
        ActivityInfo activityInfo3 = ((ResolveInfo) list.get(i10)).activityInfo;
        if (componentName.getPackageName().equals(activityInfo3.applicationInfo.packageName)
            && componentName.getClassName().equals(activityInfo3.name)) {
          list.remove(i10);
          break;
        }
        i10++;
      }
    }
    if ((updateFlagsForResolve & 64) == 0) {
      int size4 = list.size();
      for (int i11 = 0; i11 < size4; i11++) {
        ((ResolveInfo) list.get(i11)).filter = null;
      }
    }
    return list;
  }
}
