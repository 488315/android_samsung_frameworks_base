package com.android.server.pm.pkg;

import android.annotation.SystemApi;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.SigningDetails;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedApexSystemService;
import com.android.internal.pm.pkg.component.ParsedAttribution;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedPermissionGroup;
import com.android.internal.pm.pkg.component.ParsedProcess;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.internal.pm.pkg.component.ParsedUsesPermission;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes5.dex */
public interface AndroidPackage {
    List<ParsedActivity> getActivities();

    List<String> getAdoptPermissions();

    List<ParsedApexSystemService> getApexSystemServices();

    String getAppComponentFactory();

    String getApplicationClassName();

    List<ParsedAttribution> getAttributions();

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

    List<ConfigurationInfo> getConfigPreferences();

    int getDataExtractionRulesResourceId();

    int getDescriptionResourceId();

    String getEmergencyInstaller();

    List<FeatureGroupInfo> getFeatureGroups();

    int getFullBackupContentResourceId();

    int getGwpAsanMode();

    int getIconResourceId();

    Set<String> getImplicitPermissions();

    int getInstallLocation();

    List<ParsedInstrumentation> getInstrumentations();

    Map<String, ArraySet<PublicKey>> getKeySetMapping();

    Set<String> getKnownActivityEmbeddingCerts();

    int getLabelResourceId();

    int getLargestWidthLimitDp();

    List<String> getLibraryNames();

    int getLocaleConfigResourceId();

    int getLogoResourceId();

    long getLongVersionCode();

    String getManageSpaceActivityName();

    String getManifestPackageName();

    float getMaxAspectRatio();

    int getMaxSdkVersion();

    int getMemtagMode();

    Bundle getMetaData();

    Set<String> getMimeGroups();

    float getMinAspectRatio();

    SparseIntArray getMinExtensionVersions();

    int getMinSdkVersion();

    int getNativeHeapZeroInitialized();

    String getNativeLibraryDir();

    String getNativeLibraryRootDir();

    int getNetworkSecurityConfigResourceId();

    CharSequence getNonLocalizedLabel();

    List<String> getOriginalPackages();

    String getOverlayCategory();

    int getOverlayPriority();

    String getOverlayTarget();

    String getOverlayTargetOverlayableName();

    Map<String, String> getOverlayables();

    String getPackageName();

    String getPath();

    String getPermission();

    List<ParsedPermissionGroup> getPermissionGroups();

    List<ParsedPermission> getPermissions();

    List<Pair<String, ParsedIntentInfo>> getPreferredActivityFilters();

    String getProcessName();

    Map<String, ParsedProcess> getProcesses();

    Map<String, PackageManager.Property> getProperties();

    List<String> getProtectedBroadcasts();

    List<ParsedProvider> getProviders();

    List<Intent> getQueriesIntents();

    List<String> getQueriesPackages();

    Set<String> getQueriesProviders();

    List<ParsedActivity> getReceivers();

    List<FeatureInfo> getRequestedFeatures();

    Set<String> getRequestedPermissions();

    String getRequiredAccountType();

    int getRequiresSmallestWidthDp();

    Boolean getResizeableActivity();

    byte[] getRestrictUpdateHash();

    String getRestrictedAccountType();

    int getRoundIconResourceId();

    int getSdkLibVersionMajor();

    String getSdkLibraryName();

    String getSecondaryNativeLibraryDir();

    List<ParsedService> getServices();

    String getSharedUserId();

    int getSharedUserLabelResourceId();

    SigningDetails getSigningDetails();

    String[] getSplitClassLoaderNames();

    String[] getSplitCodePaths();

    SparseArray<int[]> getSplitDependencies();

    int[] getSplitFlags();

    String[] getSplitNames();

    int[] getSplitRevisionCodes();

    List<AndroidPackageSplit> getSplits();

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

    Set<String> getUpgradeKeySets();

    List<String> getUsesLibraries();

    List<String> getUsesNativeLibraries();

    List<String> getUsesOptionalLibraries();

    List<String> getUsesOptionalNativeLibraries();

    List<ParsedUsesPermission> getUsesPermissions();

    List<String> getUsesSdkLibraries();

    String[][] getUsesSdkLibrariesCertDigests();

    boolean[] getUsesSdkLibrariesOptional();

    long[] getUsesSdkLibrariesVersionsMajor();

    List<String> getUsesStaticLibraries();

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

    boolean isAllowCrossUidActivitySwitchFromBelow();

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

    boolean isUpdatableSystem();

    boolean isUseEmbeddedDex();

    boolean isUserDataFragile();

    boolean isVisibleToInstantApps();

    boolean isVmSafeMode();
}
