package com.android.internal.pm.pkg.parsing;

import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.SigningDetails;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.internal.pm.parsing.pkg.ParsedPackage;
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

/* loaded from: classes5.dex */
public interface ParsingPackage {
    ParsingPackage addActivity(ParsedActivity parsedActivity);

    ParsingPackage addAdoptPermission(String str);

    ParsingPackage addApexSystemService(ParsedApexSystemService parsedApexSystemService);

    ParsingPackage addAttribution(ParsedAttribution parsedAttribution);

    ParsingPackage addConfigPreference(ConfigurationInfo configurationInfo);

    ParsingPackage addFeatureGroup(FeatureGroupInfo featureGroupInfo);

    ParsingPackage addImplicitPermission(String str);

    ParsingPackage addInstrumentation(ParsedInstrumentation parsedInstrumentation);

    ParsingPackage addKeySet(String str, PublicKey publicKey);

    ParsingPackage addLibraryName(String str);

    ParsingPackage addOriginalPackage(String str);

    ParsingPackage addOverlayable(String str, String str2);

    ParsingPackage addPermission(ParsedPermission parsedPermission);

    ParsingPackage addPermissionGroup(ParsedPermissionGroup parsedPermissionGroup);

    ParsingPackage addPreferredActivityFilter(String str, ParsedIntentInfo parsedIntentInfo);

    ParsingPackage addProperty(PackageManager.Property property);

    ParsingPackage addProtectedBroadcast(String str);

    ParsingPackage addProvider(ParsedProvider parsedProvider);

    ParsingPackage addQueriesIntent(Intent intent);

    ParsingPackage addQueriesPackage(String str);

    ParsingPackage addQueriesProvider(String str);

    ParsingPackage addReceiver(ParsedActivity parsedActivity);

    ParsingPackage addReqFeature(FeatureInfo featureInfo);

    ParsingPackage addService(ParsedService parsedService);

    ParsingPackage addUsesLibrary(String str);

    ParsingPackage addUsesNativeLibrary(String str);

    ParsingPackage addUsesOptionalLibrary(String str);

    ParsingPackage addUsesOptionalNativeLibrary(String str);

    ParsingPackage addUsesPermission(ParsedUsesPermission parsedUsesPermission);

    ParsingPackage addUsesSdkLibrary(String str, long j, String[] strArr, boolean z);

    ParsingPackage addUsesStaticLibrary(String str, long j, String[] strArr);

    ParsingPackage asSplit(
            String[] strArr, String[] strArr2, int[] iArr, SparseArray<int[]> sparseArray);

    List<ParsedActivity> getActivities();

    List<ParsedAttribution> getAttributions();

    String getBaseApkPath();

    String getClassLoaderName();

    List<ConfigurationInfo> getConfigPreferences();

    List<ParsedInstrumentation> getInstrumentations();

    Map<String, ArraySet<PublicKey>> getKeySetMapping();

    List<String> getLibraryNames();

    float getMaxAspectRatio();

    int getMaxSdkVersion();

    Bundle getMetaData();

    float getMinAspectRatio();

    int getMinSdkVersion();

    String getPackageName();

    String getPermission();

    List<ParsedPermission> getPermissions();

    String getProcessName();

    List<ParsedProvider> getProviders();

    List<ParsedActivity> getReceivers();

    Set<String> getRequestedPermissions();

    Boolean getResizeableActivity();

    String getSdkLibraryName();

    List<ParsedService> getServices();

    String getSharedUserId();

    String[] getSplitCodePaths();

    String[] getSplitNames();

    String getStaticSharedLibraryName();

    int getTargetSdkVersion();

    String getTaskAffinity();

    int getUiOptions();

    List<String> getUsesLibraries();

    List<String> getUsesNativeLibraries();

    List<ParsedUsesPermission> getUsesPermissions();

    List<String> getUsesSdkLibraries();

    long[] getUsesSdkLibrariesVersionsMajor();

    List<String> getUsesStaticLibraries();

    String getZygotePreloadName();

    ParsedPackage hideAsParsed();

    boolean isAllowCrossUidActivitySwitchFromBelow();

    boolean isAnyDensity();

    boolean isBackupAllowed();

    boolean isExtraLargeScreensSupported();

    boolean isHardwareAccelerated();

    boolean isLargeScreensSupported();

    boolean isNormalScreensSupported();

    boolean isProfileable();

    boolean isProfileableByShell();

    boolean isResizeable();

    boolean isResizeableActivityViaSdkVersion();

    boolean isSaveStateDisallowed();

    boolean isSmallScreensSupported();

    boolean isStaticSharedLibrary();

    boolean isTaskReparentingAllowed();

    ParsingPackage removeUsesOptionalLibrary(String str);

    ParsingPackage removeUsesOptionalNativeLibrary(String str);

    ParsingPackage set32BitAbiPreferred(boolean z);

    ParsingPackage setAllowAudioPlaybackCapture(boolean z);

    ParsingPackage setAllowCrossUidActivitySwitchFromBelow(boolean z);

    ParsingPackage setAllowNativeHeapPointerTagging(boolean z);

    ParsingPackage setAnyDensity(int i);

    ParsingPackage setAppComponentFactory(String str);

    ParsingPackage setApplicationClassName(String str);

    ParsingPackage setAttributionsAreUserVisible(boolean z);

    ParsingPackage setAutoRevokePermissions(int i);

    ParsingPackage setBackupAgentName(String str);

    ParsingPackage setBackupAllowed(boolean z);

    ParsingPackage setBackupInForeground(boolean z);

    ParsingPackage setBannerResourceId(int i);

    ParsingPackage setBaseRevisionCode(int i);

    ParsingPackage setCategory(int i);

    ParsingPackage setClassLoaderName(String str);

    ParsingPackage setClearUserDataAllowed(boolean z);

    ParsingPackage setClearUserDataOnFailedRestoreAllowed(boolean z);

    ParsingPackage setCleartextTrafficAllowed(boolean z);

    ParsingPackage setCompatibleWidthLimitDp(int i);

    ParsingPackage setCompileSdkVersion(int i);

    ParsingPackage setCompileSdkVersionCodeName(String str);

    ParsingPackage setCrossProfile(boolean z);

    ParsingPackage setDataExtractionRulesResourceId(int i);

    ParsingPackage setDebuggable(boolean z);

    ParsingPackage setDeclaredHavingCode(boolean z);

    ParsingPackage setDefaultToDeviceProtectedStorage(boolean z);

    ParsingPackage setDescriptionResourceId(int i);

    ParsingPackage setDirectBootAware(boolean z);

    ParsingPackage setEmergencyInstaller(String str);

    ParsingPackage setEnabled(boolean z);

    ParsingPackage setExternalStorage(boolean z);

    ParsingPackage setExtraLargeScreensSupported(int i);

    ParsingPackage setExtractNativeLibrariesRequested(boolean z);

    ParsingPackage setForceQueryable(boolean z);

    ParsingPackage setFullBackupContentResourceId(int i);

    ParsingPackage setFullBackupOnly(boolean z);

    ParsingPackage setGame(boolean z);

    ParsingPackage setGwpAsanMode(int i);

    ParsingPackage setHardwareAccelerated(boolean z);

    ParsingPackage setHasDomainUrls(boolean z);

    ParsingPackage setIconResourceId(int i);

    ParsingPackage setInstallLocation(int i);

    ParsingPackage setIsolatedSplitLoading(boolean z);

    ParsingPackage setKillAfterRestoreAllowed(boolean z);

    ParsingPackage setKnownActivityEmbeddingCerts(Set<String> set);

    ParsingPackage setLabelResourceId(int i);

    ParsingPackage setLargeHeap(boolean z);

    ParsingPackage setLargeScreensSupported(int i);

    ParsingPackage setLargestWidthLimitDp(int i);

    ParsingPackage setLeavingSharedUser(boolean z);

    ParsingPackage setLocaleConfigResourceId(int i);

    ParsingPackage setLogoResourceId(int i);

    ParsingPackage setManageSpaceActivityName(String str);

    ParsingPackage setMaxAspectRatio(float f);

    ParsingPackage setMaxSdkVersion(int i);

    ParsingPackage setMemtagMode(int i);

    ParsingPackage setMetaData(Bundle bundle);

    ParsingPackage setMinAspectRatio(float f);

    ParsingPackage setMinExtensionVersions(SparseIntArray sparseIntArray);

    ParsingPackage setMinSdkVersion(int i);

    ParsingPackage setMultiArch(boolean z);

    ParsingPackage setNativeHeapZeroInitialized(int i);

    ParsingPackage setNetworkSecurityConfigResourceId(int i);

    ParsingPackage setNonLocalizedLabel(CharSequence charSequence);

    ParsingPackage setNonSdkApiRequested(boolean z);

    ParsingPackage setNormalScreensSupported(int i);

    ParsingPackage setOnBackInvokedCallbackEnabled(boolean z);

    ParsingPackage setOverlayCategory(String str);

    ParsingPackage setOverlayIsStatic(boolean z);

    ParsingPackage setOverlayPriority(int i);

    ParsingPackage setOverlayTarget(String str);

    ParsingPackage setOverlayTargetOverlayableName(String str);

    ParsingPackage setPartiallyDirectBootAware(boolean z);

    ParsingPackage setPermission(String str);

    ParsingPackage setPersistent(boolean z);

    ParsingPackage setPreserveLegacyExternalStorage(boolean z);

    ParsingPackage setProcessName(String str);

    ParsingPackage setProcesses(Map<String, ParsedProcess> map);

    ParsingPackage setProfileable(boolean z);

    ParsingPackage setProfileableByShell(boolean z);

    ParsingPackage setRequestForegroundServiceExemption(boolean z);

    ParsingPackage setRequestLegacyExternalStorage(boolean z);

    ParsingPackage setRequestRawExternalStorageAccess(Boolean bool);

    ParsingPackage setRequiredAccountType(String str);

    ParsingPackage setRequiredForAllUsers(boolean z);

    ParsingPackage setRequiresSmallestWidthDp(int i);

    ParsingPackage setResetEnabledSettingsOnAppDataCleared(boolean z);

    ParsingPackage setResizeable(int i);

    ParsingPackage setResizeableActivity(Boolean bool);

    ParsingPackage setResizeableActivityViaSdkVersion(boolean z);

    ParsingPackage setResourceOverlay(boolean z);

    ParsingPackage setRestoreAnyVersion(boolean z);

    ParsingPackage setRestrictUpdateHash(byte[] bArr);

    ParsingPackage setRestrictedAccountType(String str);

    ParsingPackage setRoundIconResourceId(int i);

    ParsingPackage setRtlSupported(boolean z);

    ParsingPackage setSaveStateDisallowed(boolean z);

    ParsingPackage setSdkLibVersionMajor(int i);

    ParsingPackage setSdkLibrary(boolean z);

    ParsingPackage setSdkLibraryName(String str);

    ParsingPackage setSharedUserId(String str);

    ParsingPackage setSharedUserLabelResourceId(int i);

    ParsingPackage setSigningDetails(SigningDetails signingDetails);

    ParsingPackage setSmallScreensSupported(int i);

    ParsingPackage setSplitClassLoaderName(int i, String str);

    ParsingPackage setSplitHasCode(int i, boolean z);

    ParsingPackage setStaticSharedLibrary(boolean z);

    ParsingPackage setStaticSharedLibraryName(String str);

    ParsingPackage setStaticSharedLibraryVersion(long j);

    ParsingPackage setTargetSandboxVersion(int i);

    ParsingPackage setTargetSdkVersion(int i);

    ParsingPackage setTaskAffinity(String str);

    ParsingPackage setTaskReparentingAllowed(boolean z);

    ParsingPackage setTestOnly(boolean z);

    ParsingPackage setThemeResourceId(int i);

    ParsingPackage setUiOptions(int i);

    ParsingPackage setUpdatableSystem(boolean z);

    ParsingPackage setUpgradeKeySets(Set<String> set);

    ParsingPackage setUseEmbeddedDex(boolean z);

    ParsingPackage setUserDataFragile(boolean z);

    ParsingPackage setVersionCode(int i);

    ParsingPackage setVersionCodeMajor(int i);

    ParsingPackage setVersionName(String str);

    ParsingPackage setVisibleToInstantApps(boolean z);

    ParsingPackage setVmSafeMode(boolean z);

    ParsingPackage setVolumeUuid(String str);

    ParsingPackage setZygotePreloadName(String str);

    ParsingPackage sortActivities();

    ParsingPackage sortReceivers();

    ParsingPackage sortServices();
}
