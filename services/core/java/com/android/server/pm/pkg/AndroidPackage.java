package com.android.server.pm.pkg;

import android.annotation.SystemApi;
import android.content.pm.SigningDetails;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes3.dex */
public interface AndroidPackage {
  List getActivities();

  List getAdoptPermissions();

  List getApexSystemServices();

  String getAppComponentFactory();

  String getApplicationClassName();

  List getAttributions();

  int getAutoRevokePermissions();

  String getBackupAgentName();

  int getBannerResourceId();

  @Deprecated
  String getBaseApkPath();

  int getBaseRevisionCode();

  int getCategory();

  String getClassLoaderName();

  int getCompatibleWidthLimitDp();

  int getCompileSdkVersion();

  String getCompileSdkVersionCodeName();

  List getConfigPreferences();

  int getDataExtractionRulesResourceId();

  int getDescriptionResourceId();

  List getFeatureGroups();

  int getFullBackupContentResourceId();

  int getGwpAsanMode();

  int getIconResourceId();

  List getImplicitPermissions();

  int getInstallLocation();

  List getInstrumentations();

  Map getKeySetMapping();

  Set getKnownActivityEmbeddingCerts();

  int getLabelResourceId();

  int getLargestWidthLimitDp();

  List getLibraryNames();

  int getLocaleConfigResourceId();

  int getLogoResourceId();

  long getLongVersionCode();

  String getManageSpaceActivityName();

  String getManifestPackageName();

  float getMaxAspectRatio();

  int getMaxSdkVersion();

  int getMemtagMode();

  Bundle getMetaData();

  Set getMimeGroups();

  float getMinAspectRatio();

  SparseIntArray getMinExtensionVersions();

  int getMinSdkVersion();

  int getNativeHeapZeroInitialized();

  String getNativeLibraryDir();

  String getNativeLibraryRootDir();

  int getNetworkSecurityConfigResourceId();

  CharSequence getNonLocalizedLabel();

  List getOriginalPackages();

  String getOverlayCategory();

  int getOverlayPriority();

  String getOverlayTarget();

  String getOverlayTargetOverlayableName();

  Map getOverlayables();

  String getPackageName();

  String getPath();

  String getPermission();

  List getPermissionGroups();

  List getPermissions();

  List getPreferredActivityFilters();

  String getProcessName();

  Map getProcesses();

  Map getProperties();

  List getProtectedBroadcasts();

  List getProviders();

  List getQueriesIntents();

  List getQueriesPackages();

  Set getQueriesProviders();

  List getReceivers();

  List getRequestedFeatures();

  List getRequestedPermissions();

  String getRequiredAccountType();

  int getRequiresSmallestWidthDp();

  Boolean getResizeableActivity();

  byte[] getRestrictUpdateHash();

  String getRestrictedAccountType();

  int getRoundIconResourceId();

  int getSdkLibVersionMajor();

  String getSdkLibraryName();

  String getSecondaryNativeLibraryDir();

  List getServices();

  String getSharedUserId();

  int getSharedUserLabelResourceId();

  SigningDetails getSigningDetails();

  String[] getSplitClassLoaderNames();

  String[] getSplitCodePaths();

  SparseArray getSplitDependencies();

  int[] getSplitFlags();

  String[] getSplitNames();

  int[] getSplitRevisionCodes();

  List getSplits();

  String getStaticSharedLibraryName();

  long getStaticSharedLibraryVersion();

  UUID getStorageUuid();

  int getTargetSandboxVersion();

  int getTargetSdkVersion();

  String getTaskAffinity();

  int getThemeResourceId();

  int getUiOptions();

  @Deprecated
  int getUid();

  Set getUpgradeKeySets();

  List getUsesLibraries();

  List getUsesNativeLibraries();

  List getUsesOptionalLibraries();

  List getUsesOptionalNativeLibraries();

  List getUsesPermissions();

  List getUsesSdkLibraries();

  String[][] getUsesSdkLibrariesCertDigests();

  long[] getUsesSdkLibrariesVersionsMajor();

  List getUsesStaticLibraries();

  String[][] getUsesStaticLibrariesCertDigests();

  long[] getUsesStaticLibrariesVersions();

  String getVersionName();

  String getVolumeUuid();

  String getZygotePreloadName();

  boolean hasPreserveLegacyExternalStorage();

  boolean hasRequestForegroundServiceExemption();

  Boolean hasRequestRawExternalStorageAccess();

  boolean is32BitAbiPreferred();

  boolean isAllowAudioPlaybackCapture();

  boolean isAllowNativeHeapPointerTagging();

  boolean isAnyDensity();

  boolean isApex();

  boolean isAttributionsUserVisible();

  boolean isBackupAllowed();

  boolean isBackupInForeground();

  boolean isClearUserDataAllowed();

  boolean isClearUserDataOnFailedRestoreAllowed();

  boolean isCleartextTrafficAllowed();

  boolean isCoreApp();

  boolean isCrossProfile();

  boolean isDebuggable();

  boolean isDeclaredHavingCode();

  boolean isDefaultToDeviceProtectedStorage();

  boolean isDirectBootAware();

  boolean isEnabled();

  boolean isExternalStorage();

  boolean isExtraLargeScreensSupported();

  boolean isExtractNativeLibrariesRequested();

  boolean isFactoryTest();

  boolean isForceQueryable();

  boolean isFullBackupOnly();

  @Deprecated
  boolean isGame();

  boolean isHardwareAccelerated();

  boolean isHasDomainUrls();

  boolean isIsolatedSplitLoading();

  boolean isKillAfterRestoreAllowed();

  boolean isLargeHeap();

  boolean isLargeScreensSupported();

  boolean isLeavingSharedUser();

  boolean isMultiArch();

  boolean isNativeLibraryRootRequiresIsa();

  boolean isNonSdkApiRequested();

  boolean isNormalScreensSupported();

  boolean isOnBackInvokedCallbackEnabled();

  boolean isOverlayIsStatic();

  boolean isPartiallyDirectBootAware();

  boolean isPersistent();

  boolean isProfileable();

  boolean isProfileableByShell();

  boolean isRequestLegacyExternalStorage();

  boolean isRequiredForAllUsers();

  boolean isResetEnabledSettingsOnAppDataCleared();

  boolean isResizeable();

  boolean isResizeableActivityViaSdkVersion();

  boolean isResourceOverlay();

  boolean isRestoreAnyVersion();

  boolean isRtlSupported();

  boolean isSaveStateDisallowed();

  boolean isSdkLibrary();

  boolean isSignedWithPlatformKey();

  boolean isSmallScreensSupported();

  boolean isStaticSharedLibrary();

  boolean isStub();

  boolean isTaskReparentingAllowed();

  boolean isTestOnly();

  boolean isUseEmbeddedDex();

  boolean isUserDataFragile();

  boolean isVisibleToInstantApps();

  boolean isVmSafeMode();
}
