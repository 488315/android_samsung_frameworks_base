package com.android.server.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.InstallSourceInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.KeySet;
import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.SigningDetails;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.SharedUserApi;
import com.android.server.pm.resolution.ComponentResolverApi;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.utils.WatchedArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public interface Computer extends PackageDataSnapshot {
  boolean activitySupportsIntentAsUser(
      ComponentName componentName, ComponentName componentName2, Intent intent, String str, int i);

  List applyPostResolutionFilter(
      List list, String str, boolean z, int i, boolean z2, int i2, Intent intent);

  boolean canAccessComponent(int i, ComponentName componentName, int i2);

  boolean canForwardTo(Intent intent, String str, int i, int i2);

  boolean[] canPackageQuery(String str, String[] strArr, int i);

  boolean canQueryPackage(int i, String str);

  boolean canRequestPackageInstalls(String str, int i, int i2, boolean z);

  boolean canViewInstantApps(int i, int i2);

  String[] canonicalToCurrentPackageNames(String[] strArr);

  void checkPackageFrozen(String str);

  int checkSignatures(String str, String str2, int i);

  int checkUidPermission(String str, int i);

  int checkUidSignatures(int i, int i2);

  int checkUidSignaturesForAllUsers(int i, int i2);

  ResolveInfo createForwardingResolveInfoUnchecked(
      WatchedIntentFilter watchedIntentFilter, int i, int i2);

  String[] currentToCanonicalPackageNames(String[] strArr);

  void dump(int i, FileDescriptor fileDescriptor, PrintWriter printWriter, DumpState dumpState);

  void dumpKeySet(PrintWriter printWriter, String str, DumpState dumpState);

  void dumpPackages(
      PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState, boolean z);

  void dumpPackagesProto(ProtoOutputStream protoOutputStream);

  void dumpPermissions(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState);

  void dumpSharedLibrariesProto(ProtoOutputStream protoOutputStream);

  void dumpSharedUsers(
      PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState, boolean z);

  void dumpSharedUsersProto(ProtoOutputStream protoOutputStream);

  void enforceCrossUserOrProfilePermission(int i, int i2, boolean z, boolean z2, String str);

  void enforceCrossUserPermission(int i, int i2, boolean z, boolean z2, String str);

  boolean filterAppAccess(int i, int i2);

  boolean filterAppAccess(AndroidPackage androidPackage, int i, int i2);

  boolean filterAppAccess(String str, int i, int i2, boolean z);

  String[] filterOnlySystemPackages(String... strArr);

  ResolveInfo findPersistentPreferredActivity(
      Intent intent, String str, long j, List list, boolean z, int i);

  PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityInternal2(
      Intent intent,
      String str,
      long j,
      List list,
      boolean z,
      boolean z2,
      boolean z3,
      int i,
      boolean z4,
      boolean z5);

  List findSharedNonSystemLibraries(PackageStateInternal packageStateInternal);

  ActivityInfo getActivityInfo(ComponentName componentName, long j, int i);

  ActivityInfo getActivityInfoInternal(ComponentName componentName, long j, int i, int i2);

  String[] getAllAvailablePackageNames();

  ParceledListSlice getAllIntentFilters(String str);

  List getAllPackages();

  String[] getAppOpPermissionPackages(String str, int i);

  int getApplicationEnabledSetting(String str, int i);

  boolean getApplicationHiddenSettingAsUser(String str, int i);

  ApplicationInfo getApplicationInfo(String str, long j, int i);

  ApplicationInfo getApplicationInfoInternal(String str, long j, int i, int i2);

  boolean getBlockUninstall(int i, String str);

  boolean getBlockUninstallForUser(String str, int i);

  int getComponentEnabledSetting(ComponentName componentName, int i, int i2);

  int getComponentEnabledSettingInternal(ComponentName componentName, int i, int i2);

  ComponentResolverApi getComponentResolver();

  CrossProfileDomainInfo getCrossProfileDomainPreferredLpr(
      Intent intent, String str, long j, int i, int i2);

  ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i);

  ComponentName getDefaultHomeActivity(int i);

  PackageStateInternal getDisabledSystemPackage(String str);

  ArrayMap getDisabledSystemPackageStates();

  int getFlagsForUid(int i);

  WatchedArrayMap getFrozenPackages();

  ProviderInfo getGrantImplicitAccessProviderInfo(int i, String str);

  CharSequence getHarmfulAppWarning(String str, int i);

  ComponentName getHomeActivitiesAsUser(List list, int i);

  Intent getHomeIntent();

  int getInstallReason(String str, int i);

  InstallSourceInfo getInstallSourceInfo(String str, int i);

  List getInstalledApplications(long j, int i, int i2);

  ParceledListSlice getInstalledPackages(long j, int i);

  String getInstallerPackageName(String str, int i);

  ComponentName getInstantAppInstallerComponent();

  ResolveInfo getInstantAppInstallerInfo();

  String getInstantAppPackageName(int i);

  InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2);

  KeySet getKeySetByAlias(String str, String str2);

  List getMatchingCrossProfileIntentFilters(Intent intent, String str, int i);

  String getNameForUid(int i);

  String[] getNamesForUids(int[] iArr);

  ArraySet getNotifyPackagesForReplacedReceived(String[] strArr);

  AndroidPackage getPackage(int i);

  AndroidPackage getPackage(String str);

  int[] getPackageGids(String str, long j, int i);

  PackageInfo getPackageInfo(String str, long j, int i);

  PackageInfo getPackageInfoInternal(String str, long j, long j2, int i, int i2);

  Pair getPackageOrSharedUser(int i);

  int getPackageStartability(boolean z, String str, int i, int i2);

  PackageStateInternal getPackageStateFiltered(String str, int i, int i2);

  PackageStateInternal getPackageStateForInstalledAndFiltered(String str, int i, int i2);

  PackageStateInternal getPackageStateInternal(String str);

  PackageStateInternal getPackageStateInternal(String str, int i);

  ArrayMap getPackageStates();

  int getPackageUid(String str, long j, int i);

  int getPackageUidInternal(String str, long j, int i, int i2);

  List getPackagesForAppId(int i);

  String[] getPackagesForUid(int i);

  ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i);

  List getPackagesUsingSharedLibrary(SharedLibraryInfo sharedLibraryInfo, long j, int i, int i2);

  List getPersistentApplications(boolean z, int i);

  PreferredIntentResolver getPreferredActivities(int i);

  int getPrivateFlagsForUid(int i);

  ArrayMap getProcessesForUid(int i);

  UserInfo getProfileParent(int i);

  ProviderInfo getProviderInfo(ComponentName componentName, long j, int i);

  ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i);

  ServiceInfo getServiceInfo(ComponentName componentName, long j, int i);

  ParceledListSlice getSharedLibraries(String str, long j, int i);

  WatchedArrayMap getSharedLibraries();

  SharedLibraryInfo getSharedLibraryInfo(String str, long j);

  SharedUserApi getSharedUser(int i);

  ArraySet getSharedUserPackages(int i);

  String[] getSharedUserPackagesForPackage(String str, int i);

  SigningDetails getSigningDetails(int i);

  SigningDetails getSigningDetails(String str);

  KeySet getSigningKeySet(String str);

  String[] getSystemSharedLibraryNames();

  int getTargetSdkVersion(String str);

  List getTopPriorityPackages(long j, int i);

  int getUidForSharedUser(String str);

  int getUidTargetSdkVersion(int i);

  Set getUnusedPackages(long j);

  default int getUsed() {
    return 0;
  }

  UserInfo[] getUserInfos();

  int getVersion();

  int[] getVisibilityAllowList(String str, int i);

  SparseArray getVisibilityAllowLists(String str, int[] iArr);

  List getVolumePackages(String str);

  boolean hasSigningCertificate(String str, byte[] bArr, int i);

  boolean hasUidSigningCertificate(int i, byte[] bArr, int i2);

  boolean isApexPackage(String str);

  boolean isApplicationEffectivelyEnabled(String str, UserHandle userHandle);

  boolean isCallerInstallerOfRecord(AndroidPackage androidPackage, int i);

  boolean isCallerSameApp(String str, int i);

  boolean isCallerSameApp(String str, int i, boolean z);

  boolean isComponentEffectivelyEnabled(ComponentInfo componentInfo, UserHandle userHandle);

  boolean isImplicitImageCaptureIntentAndNotSetByDpc(Intent intent, int i, String str, long j);

  boolean isInstallDisabledForPackage(String str, int i, int i2);

  boolean isInstantApp(String str, int i);

  boolean isInstantAppInternal(String str, int i, int i2);

  boolean isPackageAvailable(String str, int i);

  boolean isPackageSignedByKeySet(String str, KeySet keySet);

  boolean isPackageSignedByKeySetExactly(String str, KeySet keySet);

  boolean isPackageSuspendedForUser(String str, int i);

  boolean isSuspendingAnyPackages(String str, int i);

  boolean isUidPrivileged(int i);

  ParceledListSlice queryContentProviders(String str, int i, long j, String str2);

  ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2);

  List queryIntentActivitiesInternal(Intent intent, String str, long j, int i);

  List queryIntentActivitiesInternal(Intent intent, String str, long j, int i, int i2);

  List queryIntentActivitiesInternal(
      Intent intent, String str, long j, long j2, int i, int i2, boolean z, boolean z2);

  List queryIntentServicesInternal(Intent intent, String str, long j, int i, int i2, boolean z);

  void querySyncProviders(boolean z, List list, List list2);

  ProviderInfo resolveContentProvider(String str, long j, int i, int i2);

  String resolveInternalPackageName(String str, long j);

  boolean shouldFilterApplication(PackageStateInternal packageStateInternal, int i, int i2);

  boolean shouldFilterApplicationIncludingUninstalled(
      PackageStateInternal packageStateInternal, int i, int i2);

  long updateFlagsForResolve(long j, int i, int i2, boolean z, boolean z2);

  Computer use();
}
