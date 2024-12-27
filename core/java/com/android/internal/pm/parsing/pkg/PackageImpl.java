package com.android.internal.pm.parsing.pkg;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.SigningDetails;
import android.content.res.TypedArray;
import android.hardware.tv.tuner.FrontendInnerFec;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.internal.pm.parsing.AppInfoUtils;
import com.android.internal.pm.pkg.AndroidPackageSplitImpl;
import com.android.internal.pm.pkg.SEInfoUtil;
import com.android.internal.pm.pkg.component.ComponentMutateUtils;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedActivityImpl;
import com.android.internal.pm.pkg.component.ParsedApexSystemService;
import com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl;
import com.android.internal.pm.pkg.component.ParsedAttribution;
import com.android.internal.pm.pkg.component.ParsedAttributionImpl;
import com.android.internal.pm.pkg.component.ParsedComponent;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.pm.pkg.component.ParsedInstrumentationImpl;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedPermissionGroup;
import com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl;
import com.android.internal.pm.pkg.component.ParsedPermissionImpl;
import com.android.internal.pm.pkg.component.ParsedProcess;
import com.android.internal.pm.pkg.component.ParsedProcessImpl;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedProviderImpl;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.internal.pm.pkg.component.ParsedServiceImpl;
import com.android.internal.pm.pkg.component.ParsedUsesPermission;
import com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageHidden;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.Parcelling;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;

import libcore.util.EmptyArray;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes5.dex */
public class PackageImpl
        implements ParsedPackage,
                AndroidPackageInternal,
                AndroidPackageHidden,
                ParsingPackage,
                ParsingPackageHidden,
                Parcelable {
    protected List<ParsedActivity> activities;
    protected List<String> adoptPermissions;
    private Boolean anyDensity;
    protected List<ParsedApexSystemService> apexSystemServices;
    private String appComponentFactory;
    private List<ParsedAttribution> attributions;
    private int autoRevokePermissions;
    private String backupAgentName;
    private int banner;
    private int baseRevisionCode;
    private int category;
    private String classLoaderName;
    private String className;
    private int compatibleWidthLimitDp;
    private int compileSdkVersion;
    private String compileSdkVersionCodeName;
    private List<ConfigurationInfo> configPreferences;
    private int dataExtractionRules;
    private int descriptionRes;
    private List<FeatureGroupInfo> featureGroups;
    private int fullBackupContent;
    private int gwpAsanMode;
    private int iconRes;
    private Set<String> implicitPermissions;
    private int installLocation;
    protected List<ParsedInstrumentation> instrumentations;
    private Map<String, ArraySet<PublicKey>> keySetMapping;
    private int labelRes;
    private int largestWidthLimitDp;
    private List<String> libraryNames;
    private int logo;
    private boolean mAllowCrossUidActivitySwitchFromBelow;
    protected String mBaseApkPath;
    private String mBaseAppDataCredentialProtectedDirForSystemUser;
    private String mBaseAppDataDeviceProtectedDirForSystemUser;
    private int mBaseAppInfoFlags;
    private int mBaseAppInfoPrivateFlags;
    private int mBaseAppInfoPrivateFlagsExt;
    private long mBooleans;
    private long mBooleans2;
    ParsingPackageUtils.Callback mCallback;
    private String mEmergencyInstaller;
    private Set<String> mKnownActivityEmbeddingCerts;
    private int mLocaleConfigRes;
    private long mLongVersionCode;
    protected String mPath;
    private Map<String, PackageManager.Property> mProperties;
    private List<AndroidPackageSplit> mSplits;
    protected UUID mStorageUuid;
    private String[] mUsesLibrariesSorted;
    private String[] mUsesOptionalLibrariesSorted;
    private String[] mUsesSdkLibrariesSorted;
    private String[] mUsesStaticLibrariesSorted;
    private String manageSpaceActivityName;
    private final String manifestPackageName;
    private float maxAspectRatio;
    private int maxSdkVersion;
    private int memtagMode;
    private Bundle metaData;
    private Set<String> mimeGroups;
    private float minAspectRatio;
    private SparseIntArray minExtensionVersions;
    private int minSdkVersion;
    private int nativeHeapZeroInitialized;
    protected String nativeLibraryDir;
    protected String nativeLibraryRootDir;
    private boolean nativeLibraryRootRequiresIsa;
    private int networkSecurityConfigRes;
    private CharSequence nonLocalizedLabel;
    protected List<String> originalPackages;
    private String overlayCategory;
    private int overlayPriority;
    private String overlayTarget;
    private String overlayTargetOverlayableName;
    private Map<String, String> overlayables;
    protected String packageName;
    private String permission;
    protected List<ParsedPermissionGroup> permissionGroups;
    protected List<ParsedPermission> permissions;
    private List<Pair<String, ParsedIntentInfo>> preferredActivityFilters;
    protected String primaryCpuAbi;
    private String processName;
    private Map<String, ParsedProcess> processes;
    protected List<String> protectedBroadcasts;
    protected List<ParsedProvider> providers;
    private List<Intent> queriesIntents;
    private List<String> queriesPackages;
    private Set<String> queriesProviders;
    protected List<ParsedActivity> receivers;
    private List<FeatureInfo> reqFeatures;
    private Boolean requestRawExternalStorageAccess;

    @Deprecated protected Set<String> requestedPermissions;
    private String requiredAccountType;
    private int requiresSmallestWidthDp;
    private Boolean resizeable;
    private Boolean resizeableActivity;
    private byte[] restrictUpdateHash;
    private String restrictedAccountType;
    private int roundIconRes;
    private int sdkLibVersionMajor;
    private String sdkLibraryName;
    protected String secondaryCpuAbi;
    protected String secondaryNativeLibraryDir;
    protected List<ParsedService> services;
    private String sharedUserId;
    private int sharedUserLabel;
    private SigningDetails signingDetails;
    private String[] splitClassLoaderNames;
    protected String[] splitCodePaths;
    private SparseArray<int[]> splitDependencies;
    private int[] splitFlags;
    private String[] splitNames;
    private int[] splitRevisionCodes;
    private long staticSharedLibVersion;
    private String staticSharedLibraryName;
    private Boolean supportsExtraLargeScreens;
    private Boolean supportsLargeScreens;
    private Boolean supportsNormalScreens;
    private Boolean supportsSmallScreens;
    private int targetSandboxVersion;
    private int targetSdkVersion;
    private String taskAffinity;
    private int theme;
    private int uiOptions;
    private int uid;
    private Set<String> upgradeKeySets;
    protected List<String> usesLibraries;
    protected List<String> usesNativeLibraries;
    protected List<String> usesOptionalLibraries;
    protected List<String> usesOptionalNativeLibraries;
    private List<ParsedUsesPermission> usesPermissions;
    private List<String> usesSdkLibraries;
    private String[][] usesSdkLibrariesCertDigests;
    private boolean[] usesSdkLibrariesOptional;
    private long[] usesSdkLibrariesVersionsMajor;
    private List<String> usesStaticLibraries;
    private String[][] usesStaticLibrariesCertDigests;
    private long[] usesStaticLibrariesVersions;
    protected int versionCode;
    protected int versionCodeMajor;
    private String versionName;
    protected String volumeUuid;
    private String zygotePreloadName;
    private static final SparseArray<int[]> EMPTY_INT_ARRAY_SPARSE_ARRAY = new SparseArray<>();
    private static final Comparator<ParsedMainComponent> ORDER_COMPARATOR =
            new Comparator() { // from class:
                               // com.android.internal.pm.parsing.pkg.PackageImpl$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int compare;
                    compare =
                            Integer.compare(
                                    ((ParsedMainComponent) obj2).getOrder(),
                                    ((ParsedMainComponent) obj).getOrder());
                    return compare;
                }
            };
    public static final Parcelling.BuiltIn.ForBoolean sForBoolean =
            (Parcelling.BuiltIn.ForBoolean)
                    Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForBoolean.class);
    public static final Parcelling.BuiltIn.ForInternedString sForInternedString =
            (Parcelling.BuiltIn.ForInternedString)
                    Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForInternedString.class);
    public static final Parcelling.BuiltIn.ForInternedStringArray sForInternedStringArray =
            (Parcelling.BuiltIn.ForInternedStringArray)
                    Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForInternedStringArray.class);
    public static final Parcelling.BuiltIn.ForInternedStringList sForInternedStringList =
            (Parcelling.BuiltIn.ForInternedStringList)
                    Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForInternedStringList.class);
    public static final Parcelling.BuiltIn.ForInternedStringValueMap sForInternedStringValueMap =
            (Parcelling.BuiltIn.ForInternedStringValueMap)
                    Parcelling.Cache.getOrCreate(
                            Parcelling.BuiltIn.ForInternedStringValueMap.class);
    public static final Parcelling.BuiltIn.ForStringSet sForStringSet =
            (Parcelling.BuiltIn.ForStringSet)
                    Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForStringSet.class);
    public static final Parcelling.BuiltIn.ForInternedStringSet sForInternedStringSet =
            (Parcelling.BuiltIn.ForInternedStringSet)
                    Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForInternedStringSet.class);
    protected static final ParsingUtils.StringPairListParceler sForIntentInfoPairs =
            new ParsingUtils.StringPairListParceler();
    public static final Parcelable.Creator<PackageImpl> CREATOR =
            new Parcelable.Creator<
                    PackageImpl>() { // from class:
                                     // com.android.internal.pm.parsing.pkg.PackageImpl.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PackageImpl createFromParcel(Parcel source) {
                    return new PackageImpl(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PackageImpl[] newArray(int size) {
                    return new PackageImpl[size];
                }
            };

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public /* bridge */ /* synthetic */ ParsingPackage asSplit(
            String[] strArr, String[] strArr2, int[] iArr, SparseArray sparseArray) {
        return asSplit(strArr, strArr2, iArr, (SparseArray<int[]>) sparseArray);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public /* bridge */ /* synthetic */ ParsingPackage setProcesses(Map map) {
        return setProcesses((Map<String, ParsedProcess>) map);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public /* bridge */ /* synthetic */ ParsingPackage setUpgradeKeySets(Set set) {
        return setUpgradeKeySets((Set<String>) set);
    }

    public static PackageImpl forParsing(
            String packageName,
            String baseCodePath,
            String codePath,
            TypedArray manifestArray,
            boolean isCoreApp,
            ParsingPackageUtils.Callback callback) {
        return new PackageImpl(
                packageName, baseCodePath, codePath, manifestArray, isCoreApp, callback);
    }

    public static AndroidPackage buildFakeForDeletion(String packageName, String volumeUuid) {
        return forTesting(packageName).setVolumeUuid(volumeUuid).hideAsParsed().hideAsFinal();
    }

    public static ParsingPackage forTesting(String packageName) {
        return forTesting(packageName, "");
    }

    public static ParsingPackage forTesting(String packageName, String baseCodePath) {
        return new PackageImpl(packageName, baseCodePath, baseCodePath, null, false, null);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addActivity(ParsedActivity parsedActivity) {
        this.activities = CollectionUtils.add(this.activities, parsedActivity);
        addMimeGroupsFromComponent(parsedActivity);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addAdoptPermission(String adoptPermission) {
        this.adoptPermissions =
                CollectionUtils.add(this.adoptPermissions, TextUtils.safeIntern(adoptPermission));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public final PackageImpl addApexSystemService(ParsedApexSystemService parsedApexSystemService) {
        this.apexSystemServices =
                CollectionUtils.add(this.apexSystemServices, parsedApexSystemService);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addAttribution(ParsedAttribution attribution) {
        this.attributions = CollectionUtils.add(this.attributions, attribution);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addConfigPreference(ConfigurationInfo configPreference) {
        this.configPreferences = CollectionUtils.add(this.configPreferences, configPreference);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addFeatureGroup(FeatureGroupInfo featureGroup) {
        this.featureGroups = CollectionUtils.add(this.featureGroups, featureGroup);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addImplicitPermission(String permission) {
        addUsesPermission((ParsedUsesPermission) new ParsedUsesPermissionImpl(permission, 0));
        this.implicitPermissions =
                CollectionUtils.add(this.implicitPermissions, TextUtils.safeIntern(permission));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addInstrumentation(ParsedInstrumentation instrumentation) {
        this.instrumentations = CollectionUtils.add(this.instrumentations, instrumentation);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addKeySet(String keySetName, PublicKey publicKey) {
        ArraySet<PublicKey> publicKeys = this.keySetMapping.get(keySetName);
        if (publicKeys == null) {
            publicKeys = new ArraySet<>();
        }
        publicKeys.add(publicKey);
        this.keySetMapping = CollectionUtils.add(this.keySetMapping, keySetName, publicKeys);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addLibraryName(String libraryName) {
        this.libraryNames =
                CollectionUtils.add(this.libraryNames, TextUtils.safeIntern(libraryName));
        return this;
    }

    private void addMimeGroupsFromComponent(ParsedComponent component) {
        for (int i = component.getIntents().size() - 1; i >= 0; i--) {
            IntentFilter filter = component.getIntents().get(i).getIntentFilter();
            for (int groupIndex = filter.countMimeGroups() - 1; groupIndex >= 0; groupIndex--) {
                if (this.mimeGroups != null && this.mimeGroups.size() > 500) {
                    throw new IllegalStateException("Max limit on number of MIME Groups reached");
                }
                this.mimeGroups =
                        CollectionUtils.add(this.mimeGroups, filter.getMimeGroup(groupIndex));
            }
        }
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addOriginalPackage(String originalPackage) {
        this.originalPackages = CollectionUtils.add(this.originalPackages, originalPackage);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage addOverlayable(String overlayableName, String actorName) {
        this.overlayables =
                CollectionUtils.add(
                        this.overlayables, overlayableName, TextUtils.safeIntern(actorName));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addPermission(ParsedPermission permission) {
        this.permissions = CollectionUtils.add(this.permissions, permission);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addPermissionGroup(ParsedPermissionGroup permissionGroup) {
        this.permissionGroups = CollectionUtils.add(this.permissionGroups, permissionGroup);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addPreferredActivityFilter(String className, ParsedIntentInfo intentInfo) {
        this.preferredActivityFilters =
                CollectionUtils.add(
                        this.preferredActivityFilters, Pair.create(className, intentInfo));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addProperty(PackageManager.Property property) {
        if (property == null) {
            return this;
        }
        this.mProperties = CollectionUtils.add(this.mProperties, property.getName(), property);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addProtectedBroadcast(String protectedBroadcast) {
        if (!this.protectedBroadcasts.contains(protectedBroadcast)) {
            this.protectedBroadcasts =
                    CollectionUtils.add(
                            this.protectedBroadcasts, TextUtils.safeIntern(protectedBroadcast));
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addProvider(ParsedProvider parsedProvider) {
        this.providers = CollectionUtils.add(this.providers, parsedProvider);
        addMimeGroupsFromComponent(parsedProvider);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addQueriesIntent(Intent intent) {
        this.queriesIntents = CollectionUtils.add(this.queriesIntents, intent);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addQueriesPackage(String packageName) {
        this.queriesPackages =
                CollectionUtils.add(this.queriesPackages, TextUtils.safeIntern(packageName));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addQueriesProvider(String authority) {
        this.queriesProviders = CollectionUtils.add(this.queriesProviders, authority);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addReceiver(ParsedActivity parsedReceiver) {
        this.receivers = CollectionUtils.add(this.receivers, parsedReceiver);
        addMimeGroupsFromComponent(parsedReceiver);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addReqFeature(FeatureInfo reqFeature) {
        this.reqFeatures = CollectionUtils.add(this.reqFeatures, reqFeature);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addService(ParsedService parsedService) {
        this.services = CollectionUtils.add(this.services, parsedService);
        addMimeGroupsFromComponent(parsedService);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesLibrary(String libraryName) {
        String libraryName2 = TextUtils.safeIntern(libraryName);
        if (!ArrayUtils.contains(this.usesLibraries, libraryName2)) {
            this.usesLibraries = CollectionUtils.add(this.usesLibraries, libraryName2);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public final PackageImpl addUsesNativeLibrary(String libraryName) {
        String libraryName2 = TextUtils.safeIntern(libraryName);
        if (!ArrayUtils.contains(this.usesNativeLibraries, libraryName2)) {
            this.usesNativeLibraries = CollectionUtils.add(this.usesNativeLibraries, libraryName2);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesOptionalLibrary(String libraryName) {
        String libraryName2 = TextUtils.safeIntern(libraryName);
        if (!ArrayUtils.contains(this.usesOptionalLibraries, libraryName2)) {
            this.usesOptionalLibraries =
                    CollectionUtils.add(this.usesOptionalLibraries, libraryName2);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public final PackageImpl addUsesOptionalNativeLibrary(String libraryName) {
        String libraryName2 = TextUtils.safeIntern(libraryName);
        if (!ArrayUtils.contains(this.usesOptionalNativeLibraries, libraryName2)) {
            this.usesOptionalNativeLibraries =
                    CollectionUtils.add(this.usesOptionalNativeLibraries, libraryName2);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesPermission(ParsedUsesPermission permission) {
        this.usesPermissions = CollectionUtils.add(this.usesPermissions, permission);
        this.requestedPermissions =
                CollectionUtils.add(this.requestedPermissions, permission.getName());
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesSdkLibrary(
            String libraryName,
            long versionMajor,
            String[] certSha256Digests,
            boolean usesSdkLibrariesOptional) {
        this.usesSdkLibraries =
                CollectionUtils.add(this.usesSdkLibraries, TextUtils.safeIntern(libraryName));
        this.usesSdkLibrariesVersionsMajor =
                ArrayUtils.appendLong(this.usesSdkLibrariesVersionsMajor, versionMajor, true);
        this.usesSdkLibrariesCertDigests =
                (String[][])
                        ArrayUtils.appendElement(
                                String[].class,
                                this.usesSdkLibrariesCertDigests,
                                certSha256Digests,
                                true);
        this.usesSdkLibrariesOptional =
                ArrayUtils.appendBoolean(this.usesSdkLibrariesOptional, usesSdkLibrariesOptional);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesStaticLibrary(
            String libraryName, long version, String[] certSha256Digests) {
        this.usesStaticLibraries =
                CollectionUtils.add(this.usesStaticLibraries, TextUtils.safeIntern(libraryName));
        this.usesStaticLibrariesVersions =
                ArrayUtils.appendLong(this.usesStaticLibrariesVersions, version, true);
        this.usesStaticLibrariesCertDigests =
                (String[][])
                        ArrayUtils.appendElement(
                                String[].class,
                                this.usesStaticLibrariesCertDigests,
                                certSha256Digests,
                                true);
        return this;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isAttributionsUserVisible() {
        return getBoolean(FrontendInnerFec.FEC_96_180);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl asSplit(
            String[] splitNames,
            String[] splitCodePaths,
            int[] splitRevisionCodes,
            SparseArray<int[]> splitDependencies) {
        this.splitNames = splitNames;
        this.splitCodePaths = splitCodePaths;
        this.splitRevisionCodes = splitRevisionCodes;
        this.splitDependencies = splitDependencies;
        int count = splitNames.length;
        this.splitFlags = new int[count];
        this.splitClassLoaderNames = new String[count];
        return this;
    }

    protected void assignDerivedFields() {
        this.mStorageUuid = StorageManager.convert(this.volumeUuid);
        this.mLongVersionCode =
                PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    private ArrayMap<String, String> buildAppClassNamesByProcess() {
        if (ArrayUtils.size(this.processes) == 0) {
            return null;
        }
        ArrayMap<String, String> ret = new ArrayMap<>(4);
        for (String processName : this.processes.keySet()) {
            ParsedProcess process = this.processes.get(processName);
            ArrayMap<String, String> appClassesByPackage = process.getAppClassNamesByPackage();
            for (int i = 0; i < appClassesByPackage.size(); i++) {
                String packageName = appClassesByPackage.keyAt(i);
                if (this.packageName.equals(packageName)) {
                    String appClassName = appClassesByPackage.valueAt(i);
                    if (!TextUtils.isEmpty(appClassName)) {
                        ret.put(processName, appClassName);
                    }
                }
            }
        }
        return ret;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<AndroidPackageSplit> getSplits() {
        if (this.mSplits == null) {
            ArrayList<AndroidPackageSplit> splits = new ArrayList<>();
            splits.add(
                    new AndroidPackageSplitImpl(
                            null,
                            getBaseApkPath(),
                            getBaseRevisionCode(),
                            isDeclaredHavingCode() ? 4 : 0,
                            getClassLoaderName()));
            if (this.splitNames != null) {
                for (int index = 0; index < this.splitNames.length; index++) {
                    splits.add(
                            new AndroidPackageSplitImpl(
                                    this.splitNames[index],
                                    this.splitCodePaths[index],
                                    this.splitRevisionCodes[index],
                                    this.splitFlags[index],
                                    this.splitClassLoaderNames[index]));
                }
            }
            if (this.splitDependencies != null) {
                for (int index2 = 0; index2 < this.splitDependencies.size(); index2++) {
                    int splitIndex = this.splitDependencies.keyAt(index2);
                    int[] dependenciesByIndex = this.splitDependencies.valueAt(index2);
                    ArrayList<AndroidPackageSplit> dependencies = new ArrayList<>();
                    for (int dependencyIndex : dependenciesByIndex) {
                        if (dependencyIndex >= 0) {
                            dependencies.add(splits.get(dependencyIndex));
                        }
                    }
                    ((AndroidPackageSplitImpl) splits.get(splitIndex))
                            .fillDependencies(Collections.unmodifiableList(dependencies));
                }
            }
            this.mSplits = Collections.unmodifiableList(splits);
        }
        return this.mSplits;
    }

    public String toString() {
        return "Package{"
                + Integer.toHexString(System.identityHashCode(this))
                + " "
                + this.packageName
                + "}";
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedActivity> getActivities() {
        return this.activities;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<String> getAdoptPermissions() {
        return this.adoptPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<ParsedApexSystemService> getApexSystemServices() {
        return this.apexSystemServices;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getAppComponentFactory() {
        return this.appComponentFactory;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedAttribution> getAttributions() {
        return this.attributions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getAutoRevokePermissions() {
        return this.autoRevokePermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getBackupAgentName() {
        return this.backupAgentName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getBannerResourceId() {
        return this.banner;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.content.om.OverlayConfig.PackageProvider.Package,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getBaseApkPath() {
        return this.mBaseApkPath;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getBaseRevisionCode() {
        return this.baseRevisionCode;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getCategory() {
        return this.category;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getClassLoaderName() {
        return this.classLoaderName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getApplicationClassName() {
        return this.className;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getCompatibleWidthLimitDp() {
        return this.compatibleWidthLimitDp;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getCompileSdkVersion() {
        return this.compileSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getCompileSdkVersionCodeName() {
        return this.compileSdkVersionCodeName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ConfigurationInfo> getConfigPreferences() {
        return this.configPreferences;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getDataExtractionRulesResourceId() {
        return this.dataExtractionRules;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getDescriptionResourceId() {
        return this.descriptionRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<FeatureGroupInfo> getFeatureGroups() {
        return this.featureGroups;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getFullBackupContentResourceId() {
        return this.fullBackupContent;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getGwpAsanMode() {
        return this.gwpAsanMode;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getIconResourceId() {
        return this.iconRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Set<String> getImplicitPermissions() {
        return this.implicitPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getInstallLocation() {
        return this.installLocation;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedInstrumentation> getInstrumentations() {
        return this.instrumentations;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public Map<String, ArraySet<PublicKey>> getKeySetMapping() {
        return this.keySetMapping;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Set<String> getKnownActivityEmbeddingCerts() {
        return this.mKnownActivityEmbeddingCerts;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getLabelResourceId() {
        return this.labelRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getLargestWidthLimitDp() {
        return this.largestWidthLimitDp;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<String> getLibraryNames() {
        return this.libraryNames;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getLocaleConfigResourceId() {
        return this.mLocaleConfigRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getLogoResourceId() {
        return this.logo;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getManageSpaceActivityName() {
        return this.manageSpaceActivityName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public float getMaxAspectRatio() {
        return this.maxAspectRatio;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getMaxSdkVersion() {
        return this.maxSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getMemtagMode() {
        return this.memtagMode;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public Bundle getMetaData() {
        return this.metaData;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Set<String> getMimeGroups() {
        return this.mimeGroups;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public float getMinAspectRatio() {
        return this.minAspectRatio;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public SparseIntArray getMinExtensionVersions() {
        return this.minExtensionVersions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getMinSdkVersion() {
        return this.minSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getNativeHeapZeroInitialized() {
        return this.nativeHeapZeroInitialized;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getNetworkSecurityConfigResourceId() {
        return this.networkSecurityConfigRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public CharSequence getNonLocalizedLabel() {
        return this.nonLocalizedLabel;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<String> getOriginalPackages() {
        return this.originalPackages;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getOverlayCategory() {
        return this.overlayCategory;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.content.om.OverlayConfig.PackageProvider.Package
    public int getOverlayPriority() {
        return this.overlayPriority;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.content.om.OverlayConfig.PackageProvider.Package
    public String getOverlayTarget() {
        return this.overlayTarget;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getOverlayTargetOverlayableName() {
        return this.overlayTargetOverlayableName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Map<String, String> getOverlayables() {
        return this.overlayables;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.content.om.OverlayConfig.PackageProvider.Package,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getPackageName() {
        return this.packageName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getPath() {
        return this.mPath;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getPermission() {
        return this.permission;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<ParsedPermissionGroup> getPermissionGroups() {
        return this.permissionGroups;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedPermission> getPermissions() {
        return this.permissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<Pair<String, ParsedIntentInfo>> getPreferredActivityFilters() {
        return this.preferredActivityFilters;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getProcessName() {
        return this.processName != null ? this.processName : this.packageName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Map<String, ParsedProcess> getProcesses() {
        return this.processes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Map<String, PackageManager.Property> getProperties() {
        return this.mProperties;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<String> getProtectedBroadcasts() {
        return this.protectedBroadcasts;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedProvider> getProviders() {
        return this.providers;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<Intent> getQueriesIntents() {
        return this.queriesIntents;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<String> getQueriesPackages() {
        return this.queriesPackages;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Set<String> getQueriesProviders() {
        return this.queriesProviders;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedActivity> getReceivers() {
        return this.receivers;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<FeatureInfo> getRequestedFeatures() {
        return this.reqFeatures;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    @Deprecated
    public Set<String> getRequestedPermissions() {
        return this.requestedPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getRequiredAccountType() {
        return this.requiredAccountType;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getRequiresSmallestWidthDp() {
        return this.requiresSmallestWidthDp;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public Boolean getResizeableActivity() {
        return this.resizeableActivity;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public byte[] getRestrictUpdateHash() {
        return this.restrictUpdateHash;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getRestrictedAccountType() {
        return this.restrictedAccountType;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getEmergencyInstaller() {
        return this.mEmergencyInstaller;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getRoundIconResourceId() {
        return this.roundIconRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getSdkLibraryName() {
        return this.sdkLibraryName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getSdkLibVersionMajor() {
        return this.sdkLibVersionMajor;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedService> getServices() {
        return this.services;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getSharedUserId() {
        return this.sharedUserId;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getSharedUserLabelResourceId() {
        return this.sharedUserLabel;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public SigningDetails getSigningDetails() {
        return this.signingDetails;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String[] getSplitClassLoaderNames() {
        return this.splitClassLoaderNames == null ? EmptyArray.STRING : this.splitClassLoaderNames;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String[] getSplitCodePaths() {
        return this.splitCodePaths == null ? EmptyArray.STRING : this.splitCodePaths;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public SparseArray<int[]> getSplitDependencies() {
        return this.splitDependencies == null
                ? EMPTY_INT_ARRAY_SPARSE_ARRAY
                : this.splitDependencies;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int[] getSplitFlags() {
        return this.splitFlags;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String[] getSplitNames() {
        return this.splitNames == null ? EmptyArray.STRING : this.splitNames;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int[] getSplitRevisionCodes() {
        return this.splitRevisionCodes == null ? EmptyArray.INT : this.splitRevisionCodes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getStaticSharedLibraryName() {
        return this.staticSharedLibraryName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public long getStaticSharedLibraryVersion() {
        return this.staticSharedLibVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public UUID getStorageUuid() {
        return this.mStorageUuid;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getTargetSandboxVersion() {
        return this.targetSandboxVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.content.om.OverlayConfig.PackageProvider.Package,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getTargetSdkVersion() {
        return this.targetSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getTaskAffinity() {
        return this.taskAffinity;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getThemeResourceId() {
        return this.theme;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getUiOptions() {
        return this.uiOptions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Set<String> getUpgradeKeySets() {
        return this.upgradeKeySets;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<String> getUsesLibraries() {
        return this.usesLibraries;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageInternal
    public String[] getUsesLibrariesSorted() {
        if (this.mUsesLibrariesSorted == null) {
            this.mUsesLibrariesSorted = sortLibraries(this.usesLibraries);
        }
        return this.mUsesLibrariesSorted;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<String> getUsesNativeLibraries() {
        return this.usesNativeLibraries;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<String> getUsesOptionalLibraries() {
        return this.usesOptionalLibraries;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageInternal
    public String[] getUsesOptionalLibrariesSorted() {
        if (this.mUsesOptionalLibrariesSorted == null) {
            this.mUsesOptionalLibrariesSorted = sortLibraries(this.usesOptionalLibraries);
        }
        return this.mUsesOptionalLibrariesSorted;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<String> getUsesOptionalNativeLibraries() {
        return this.usesOptionalNativeLibraries;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedUsesPermission> getUsesPermissions() {
        return this.usesPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<String> getUsesSdkLibraries() {
        return this.usesSdkLibraries;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageInternal
    public String[] getUsesSdkLibrariesSorted() {
        if (this.mUsesSdkLibrariesSorted == null) {
            this.mUsesSdkLibrariesSorted = sortLibraries(this.usesSdkLibraries);
        }
        return this.mUsesSdkLibrariesSorted;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String[][] getUsesSdkLibrariesCertDigests() {
        return this.usesSdkLibrariesCertDigests;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public long[] getUsesSdkLibrariesVersionsMajor() {
        return this.usesSdkLibrariesVersionsMajor;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean[] getUsesSdkLibrariesOptional() {
        return this.usesSdkLibrariesOptional;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<String> getUsesStaticLibraries() {
        return this.usesStaticLibraries;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageInternal
    public String[] getUsesStaticLibrariesSorted() {
        if (this.mUsesStaticLibrariesSorted == null) {
            this.mUsesStaticLibrariesSorted = sortLibraries(this.usesStaticLibraries);
        }
        return this.mUsesStaticLibrariesSorted;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String[][] getUsesStaticLibrariesCertDigests() {
        return this.usesStaticLibrariesCertDigests;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public long[] getUsesStaticLibrariesVersions() {
        return this.usesStaticLibrariesVersions;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden,
              // com.android.internal.pm.pkg.parsing.ParsingPackageHidden
    public int getVersionCode() {
        return this.versionCode;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden,
              // com.android.internal.pm.pkg.parsing.ParsingPackageHidden
    public int getVersionCodeMajor() {
        return this.versionCodeMajor;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getVersionName() {
        return this.versionName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getVolumeUuid() {
        return this.volumeUuid;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getZygotePreloadName() {
        return this.zygotePreloadName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isAllowCrossUidActivitySwitchFromBelow() {
        return this.mAllowCrossUidActivitySwitchFromBelow;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean hasPreserveLegacyExternalStorage() {
        return getBoolean(137438953472L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean hasRequestForegroundServiceExemption() {
        return getBoolean(FrontendInnerFec.FEC_90_180);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Boolean hasRequestRawExternalStorageAccess() {
        return this.requestRawExternalStorageAccess;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isAllowAudioPlaybackCapture() {
        return getBoolean(2147483648L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isBackupAllowed() {
        return getBoolean(4L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isClearUserDataAllowed() {
        return getBoolean(2048L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isClearUserDataOnFailedRestoreAllowed() {
        return getBoolean(1073741824L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isAllowNativeHeapPointerTagging() {
        return getBoolean(68719476736L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isTaskReparentingAllowed() {
        return getBoolean(1024L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isAnyDensity() {
        if (this.anyDensity == null) {
            return this.targetSdkVersion >= 4;
        }
        return this.anyDensity.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isBackupInForeground() {
        return getBoolean(16777216L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isHardwareAccelerated() {
        return getBoolean(2L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isSaveStateDisallowed() {
        return getBoolean(34359738368L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isCrossProfile() {
        return getBoolean(8796093022208L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isDebuggable() {
        return getBoolean(128L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isDefaultToDeviceProtectedStorage() {
        return getBoolean(67108864L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isDirectBootAware() {
        return getBoolean(134217728L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isEnabled() {
        return getBoolean(FrontendInnerFec.FEC_18_30);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isExternalStorage() {
        return getBoolean(1L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isExtractNativeLibrariesRequested() {
        return getBoolean(131072L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isForceQueryable() {
        return getBoolean(4398046511104L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isFullBackupOnly() {
        return getBoolean(32L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isGame() {
        return getBoolean(262144L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isDeclaredHavingCode() {
        return getBoolean(512L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isHasDomainUrls() {
        return getBoolean(4194304L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isUserDataFragile() {
        return getBoolean(17179869184L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isIsolatedSplitLoading() {
        return getBoolean(2097152L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isKillAfterRestoreAllowed() {
        return getBoolean(8L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isLargeHeap() {
        return getBoolean(4096L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isLeavingSharedUser() {
        return getBoolean(FrontendInnerFec.FEC_135_180);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isMultiArch() {
        return getBoolean(65536L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isOnBackInvokedCallbackEnabled() {
        return getBoolean(FrontendInnerFec.FEC_132_180);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isResourceOverlay() {
        return getBoolean(1048576L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.content.om.OverlayConfig.PackageProvider.Package
    public boolean isOverlayIsStatic() {
        return getBoolean(549755813888L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isPartiallyDirectBootAware() {
        return getBoolean(268435456L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isPersistent() {
        return getBoolean(64L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isProfileable() {
        return !getBoolean(FrontendInnerFec.FEC_20_30);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isProfileableByShell() {
        return isProfileable() && getBoolean(8388608L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isRequestLegacyExternalStorage() {
        return getBoolean(4294967296L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isRequiredForAllUsers() {
        return getBoolean(274877906944L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isResetEnabledSettingsOnAppDataCleared() {
        return getBoolean(281474976710656L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isResizeable() {
        if (this.resizeable == null) {
            return this.targetSdkVersion >= 4;
        }
        return this.resizeable.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isResizeableActivityViaSdkVersion() {
        return getBoolean(536870912L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isRestoreAnyVersion() {
        return getBoolean(16L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isSdkLibrary() {
        return getBoolean(562949953421312L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isStaticSharedLibrary() {
        return getBoolean(524288L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isExtraLargeScreensSupported() {
        if (this.supportsExtraLargeScreens == null) {
            return this.targetSdkVersion >= 9;
        }
        return this.supportsExtraLargeScreens.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isLargeScreensSupported() {
        if (this.supportsLargeScreens == null) {
            return this.targetSdkVersion >= 4;
        }
        return this.supportsLargeScreens.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isNormalScreensSupported() {
        return this.supportsNormalScreens == null || this.supportsNormalScreens.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isRtlSupported() {
        return getBoolean(16384L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage,
              // com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isSmallScreensSupported() {
        if (this.supportsSmallScreens == null) {
            return this.targetSdkVersion >= 4;
        }
        return this.supportsSmallScreens.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isTestOnly() {
        return getBoolean(32768L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean is32BitAbiPreferred() {
        return getBoolean(1099511627776L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isUseEmbeddedDex() {
        return getBoolean(33554432L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isCleartextTrafficAllowed() {
        return getBoolean(8192L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isNonSdkApiRequested() {
        return getBoolean(8589934592L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isVisibleToInstantApps() {
        return getBoolean(2199023255552L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isVmSafeMode() {
        return getBoolean(256L);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl removeUsesOptionalNativeLibrary(String libraryName) {
        this.usesOptionalNativeLibraries =
                CollectionUtils.remove(this.usesOptionalNativeLibraries, libraryName);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAllowAudioPlaybackCapture(boolean value) {
        return setBoolean(2147483648L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBackupAllowed(boolean value) {
        return setBoolean(4L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setClearUserDataAllowed(boolean value) {
        return setBoolean(2048L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setClearUserDataOnFailedRestoreAllowed(boolean value) {
        return setBoolean(1073741824L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAllowNativeHeapPointerTagging(boolean value) {
        return setBoolean(68719476736L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTaskReparentingAllowed(boolean value) {
        return setBoolean(1024L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAnyDensity(int anyDensity) {
        if (anyDensity == 1) {
            return this;
        }
        this.anyDensity = Boolean.valueOf(anyDensity < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAppComponentFactory(String appComponentFactory) {
        this.appComponentFactory = appComponentFactory;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setAttributionsAreUserVisible(boolean attributionsAreUserVisible) {
        setBoolean(FrontendInnerFec.FEC_96_180, attributionsAreUserVisible);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAutoRevokePermissions(int value) {
        this.autoRevokePermissions = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBackupAgentName(String backupAgentName) {
        this.backupAgentName = backupAgentName;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBackupInForeground(boolean value) {
        return setBoolean(16777216L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBannerResourceId(int value) {
        this.banner = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setHardwareAccelerated(boolean value) {
        return setBoolean(2L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBaseRevisionCode(int value) {
        this.baseRevisionCode = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSaveStateDisallowed(boolean value) {
        return setBoolean(34359738368L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCategory(int value) {
        this.category = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setClassLoaderName(String classLoaderName) {
        this.classLoaderName = classLoaderName;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setApplicationClassName(String className) {
        this.className = className == null ? null : className.trim();
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCompatibleWidthLimitDp(int value) {
        this.compatibleWidthLimitDp = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCompileSdkVersion(int value) {
        this.compileSdkVersion = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setCompileSdkVersionCodeName(String compileSdkVersionCodeName) {
        this.compileSdkVersionCodeName = compileSdkVersionCodeName;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCrossProfile(boolean value) {
        return setBoolean(8796093022208L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDataExtractionRulesResourceId(int value) {
        this.dataExtractionRules = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDebuggable(boolean value) {
        return setBoolean(128L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDescriptionResourceId(int value) {
        this.descriptionRes = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setEnabled(boolean value) {
        return setBoolean(FrontendInnerFec.FEC_18_30, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setExternalStorage(boolean value) {
        return setBoolean(1L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setExtractNativeLibrariesRequested(boolean value) {
        return setBoolean(131072L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setForceQueryable(boolean value) {
        return setBoolean(4398046511104L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setFullBackupContentResourceId(int value) {
        this.fullBackupContent = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setFullBackupOnly(boolean value) {
        return setBoolean(32L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setGame(boolean value) {
        return setBoolean(262144L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setGwpAsanMode(int value) {
        this.gwpAsanMode = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDeclaredHavingCode(boolean value) {
        return setBoolean(512L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setHasDomainUrls(boolean value) {
        return setBoolean(4194304L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUserDataFragile(boolean value) {
        return setBoolean(17179869184L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setIconResourceId(int value) {
        this.iconRes = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setInstallLocation(int value) {
        this.installLocation = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setIsolatedSplitLoading(boolean value) {
        return setBoolean(2097152L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setKillAfterRestoreAllowed(boolean value) {
        return setBoolean(8L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setKnownActivityEmbeddingCerts(Set<String> knownEmbeddingCerts) {
        this.mKnownActivityEmbeddingCerts = knownEmbeddingCerts;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLabelResourceId(int value) {
        this.labelRes = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLargeHeap(boolean value) {
        return setBoolean(4096L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLargestWidthLimitDp(int value) {
        this.largestWidthLimitDp = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLeavingSharedUser(boolean value) {
        return setBoolean(FrontendInnerFec.FEC_135_180, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLocaleConfigResourceId(int value) {
        this.mLocaleConfigRes = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLogoResourceId(int value) {
        this.logo = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setManageSpaceActivityName(String manageSpaceActivityName) {
        this.manageSpaceActivityName = manageSpaceActivityName;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMaxAspectRatio(float value) {
        this.maxAspectRatio = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMaxSdkVersion(int value) {
        this.maxSdkVersion = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMemtagMode(int value) {
        this.memtagMode = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMetaData(Bundle value) {
        this.metaData = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMinAspectRatio(float value) {
        this.minAspectRatio = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMinExtensionVersions(SparseIntArray value) {
        this.minExtensionVersions = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMinSdkVersion(int value) {
        this.minSdkVersion = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMultiArch(boolean value) {
        return setBoolean(65536L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNativeHeapZeroInitialized(int value) {
        this.nativeHeapZeroInitialized = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNetworkSecurityConfigResourceId(int value) {
        this.networkSecurityConfigRes = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNonLocalizedLabel(CharSequence value) {
        this.nonLocalizedLabel = value == null ? null : value.toString().trim();
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setOnBackInvokedCallbackEnabled(boolean value) {
        setBoolean(FrontendInnerFec.FEC_132_180, value);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setAllowCrossUidActivitySwitchFromBelow(boolean value) {
        this.mAllowCrossUidActivitySwitchFromBelow = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setResourceOverlay(boolean value) {
        return setBoolean(1048576L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayCategory(String overlayCategory) {
        this.overlayCategory = overlayCategory;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayIsStatic(boolean value) {
        return setBoolean(549755813888L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayPriority(int value) {
        this.overlayPriority = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayTarget(String overlayTarget) {
        this.overlayTarget = TextUtils.safeIntern(overlayTarget);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayTargetOverlayableName(String overlayTargetOverlayableName) {
        this.overlayTargetOverlayableName = overlayTargetOverlayableName;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setPartiallyDirectBootAware(boolean value) {
        return setBoolean(268435456L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setPreserveLegacyExternalStorage(boolean value) {
        return setBoolean(137438953472L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setProcessName(String processName) {
        this.processName = processName;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setProcesses(Map<String, ParsedProcess> value) {
        this.processes = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setProfileable(boolean value) {
        return setBoolean(FrontendInnerFec.FEC_20_30, !value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setProfileableByShell(boolean value) {
        return setBoolean(8388608L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequestForegroundServiceExemption(boolean value) {
        return setBoolean(FrontendInnerFec.FEC_90_180, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequestLegacyExternalStorage(boolean value) {
        return setBoolean(4294967296L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequestRawExternalStorageAccess(Boolean value) {
        this.requestRawExternalStorageAccess = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequiredAccountType(String requiredAccountType) {
        this.requiredAccountType = TextUtils.nullIfEmpty(requiredAccountType);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequiredForAllUsers(boolean value) {
        return setBoolean(274877906944L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequiresSmallestWidthDp(int value) {
        this.requiresSmallestWidthDp = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setResetEnabledSettingsOnAppDataCleared(
            boolean resetEnabledSettingsOnAppDataCleared) {
        setBoolean(281474976710656L, resetEnabledSettingsOnAppDataCleared);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setResizeable(int resizeable) {
        if (resizeable == 1) {
            return this;
        }
        this.resizeable = Boolean.valueOf(resizeable < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setResizeableActivity(Boolean value) {
        this.resizeableActivity = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setResizeableActivityViaSdkVersion(boolean value) {
        return setBoolean(536870912L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRestoreAnyVersion(boolean value) {
        return setBoolean(16L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRestrictedAccountType(String restrictedAccountType) {
        this.restrictedAccountType = restrictedAccountType;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setEmergencyInstaller(String emergencyInstaller) {
        this.mEmergencyInstaller = emergencyInstaller;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRoundIconResourceId(int value) {
        this.roundIconRes = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSdkLibraryName(String sdkLibraryName) {
        this.sdkLibraryName = TextUtils.safeIntern(sdkLibraryName);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSdkLibVersionMajor(int sdkLibVersionMajor) {
        this.sdkLibVersionMajor = sdkLibVersionMajor;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSdkLibrary(boolean value) {
        return setBoolean(562949953421312L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSharedUserId(String sharedUserId) {
        this.sharedUserId = TextUtils.safeIntern(sharedUserId);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSharedUserLabelResourceId(int value) {
        this.sharedUserLabel = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSplitClassLoaderName(int splitIndex, String classLoaderName) {
        this.splitClassLoaderNames[splitIndex] = classLoaderName;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSplitHasCode(int splitIndex, boolean splitHasCode) {
        int i;
        int[] iArr = this.splitFlags;
        if (splitHasCode) {
            i = this.splitFlags[splitIndex] | 4;
        } else {
            i = this.splitFlags[splitIndex] & (-5);
        }
        iArr[splitIndex] = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setStaticSharedLibraryName(String staticSharedLibraryName) {
        this.staticSharedLibraryName = TextUtils.safeIntern(staticSharedLibraryName);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setStaticSharedLibraryVersion(long value) {
        this.staticSharedLibVersion = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setStaticSharedLibrary(boolean value) {
        return setBoolean(524288L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setExtraLargeScreensSupported(int supportsExtraLargeScreens) {
        if (supportsExtraLargeScreens == 1) {
            return this;
        }
        this.supportsExtraLargeScreens = Boolean.valueOf(supportsExtraLargeScreens < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLargeScreensSupported(int supportsLargeScreens) {
        if (supportsLargeScreens == 1) {
            return this;
        }
        this.supportsLargeScreens = Boolean.valueOf(supportsLargeScreens < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNormalScreensSupported(int supportsNormalScreens) {
        if (supportsNormalScreens == 1) {
            return this;
        }
        this.supportsNormalScreens = Boolean.valueOf(supportsNormalScreens < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRtlSupported(boolean value) {
        return setBoolean(16384L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSmallScreensSupported(int supportsSmallScreens) {
        if (supportsSmallScreens == 1) {
            return this;
        }
        this.supportsSmallScreens = Boolean.valueOf(supportsSmallScreens < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTargetSandboxVersion(int value) {
        this.targetSandboxVersion = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTargetSdkVersion(int value) {
        this.targetSdkVersion = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTaskAffinity(String taskAffinity) {
        this.taskAffinity = taskAffinity;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTestOnly(boolean value) {
        return setBoolean(32768L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setThemeResourceId(int value) {
        this.theme = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUiOptions(int value) {
        this.uiOptions = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUpgradeKeySets(Set<String> value) {
        this.upgradeKeySets = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl set32BitAbiPreferred(boolean value) {
        return setBoolean(1099511627776L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUseEmbeddedDex(boolean value) {
        return setBoolean(33554432L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCleartextTrafficAllowed(boolean value) {
        return setBoolean(8192L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNonSdkApiRequested(boolean value) {
        return setBoolean(8589934592L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVersionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVisibleToInstantApps(boolean value) {
        return setBoolean(2199023255552L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVmSafeMode(boolean value) {
        return setBoolean(256L, value);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVolumeUuid(String volumeUuid) {
        this.volumeUuid = TextUtils.safeIntern(volumeUuid);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setZygotePreloadName(String zygotePreloadName) {
        this.zygotePreloadName = zygotePreloadName;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl sortActivities() {
        Collections.sort(this.activities, ORDER_COMPARATOR);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl sortReceivers() {
        Collections.sort(this.receivers, ORDER_COMPARATOR);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl sortServices() {
        Collections.sort(this.services, ORDER_COMPARATOR);
        return this;
    }

    public ApplicationInfo toAppInfoWithoutStateWithoutFlags() {
        ApplicationInfo appInfo = new ApplicationInfo();
        appInfo.appComponentFactory = this.appComponentFactory;
        appInfo.backupAgentName = this.backupAgentName;
        appInfo.banner = this.banner;
        appInfo.category = this.category;
        appInfo.classLoaderName = this.classLoaderName;
        appInfo.className = this.className;
        appInfo.compatibleWidthLimitDp = this.compatibleWidthLimitDp;
        appInfo.compileSdkVersion = this.compileSdkVersion;
        appInfo.compileSdkVersionCodename = this.compileSdkVersionCodeName;
        appInfo.crossProfile = isCrossProfile();
        appInfo.descriptionRes = this.descriptionRes;
        appInfo.enabled = getBoolean(FrontendInnerFec.FEC_18_30);
        appInfo.fullBackupContent = this.fullBackupContent;
        appInfo.dataExtractionRulesRes = this.dataExtractionRules;
        appInfo.icon =
                (!ParsingPackageUtils.sUseRoundIcon || this.roundIconRes == 0)
                        ? this.iconRes
                        : this.roundIconRes;
        appInfo.iconRes = this.iconRes;
        appInfo.roundIconRes = this.roundIconRes;
        appInfo.installLocation = this.installLocation;
        appInfo.labelRes = this.labelRes;
        appInfo.largestWidthLimitDp = this.largestWidthLimitDp;
        appInfo.logo = this.logo;
        appInfo.manageSpaceActivityName = this.manageSpaceActivityName;
        appInfo.maxAspectRatio = this.maxAspectRatio;
        appInfo.metaData = this.metaData;
        appInfo.minAspectRatio = this.minAspectRatio;
        appInfo.minSdkVersion = this.minSdkVersion;
        appInfo.name = this.className;
        appInfo.networkSecurityConfigRes = this.networkSecurityConfigRes;
        appInfo.nonLocalizedLabel = this.nonLocalizedLabel;
        appInfo.packageName = this.packageName;
        appInfo.permission = this.permission;
        appInfo.processName = getProcessName();
        appInfo.requiresSmallestWidthDp = this.requiresSmallestWidthDp;
        appInfo.splitClassLoaderNames = this.splitClassLoaderNames;
        appInfo.splitDependencies =
                (this.splitDependencies == null || this.splitDependencies.size() == 0)
                        ? null
                        : this.splitDependencies;
        appInfo.splitNames = this.splitNames;
        appInfo.storageUuid = this.mStorageUuid;
        appInfo.targetSandboxVersion = this.targetSandboxVersion;
        appInfo.targetSdkVersion = this.targetSdkVersion;
        appInfo.taskAffinity = this.taskAffinity;
        appInfo.theme = this.theme;
        appInfo.uiOptions = this.uiOptions;
        appInfo.volumeUuid = this.volumeUuid;
        appInfo.zygotePreloadName = this.zygotePreloadName;
        appInfo.setGwpAsanMode(this.gwpAsanMode);
        appInfo.setMemtagMode(this.memtagMode);
        appInfo.setNativeHeapZeroInitialized(this.nativeHeapZeroInitialized);
        appInfo.setRequestRawExternalStorageAccess(this.requestRawExternalStorageAccess);
        appInfo.setBaseCodePath(this.mBaseApkPath);
        appInfo.setBaseResourcePath(this.mBaseApkPath);
        appInfo.setCodePath(this.mPath);
        appInfo.setResourcePath(this.mPath);
        appInfo.setSplitCodePaths(
                ArrayUtils.size(this.splitCodePaths) == 0 ? null : this.splitCodePaths);
        appInfo.setSplitResourcePaths(
                ArrayUtils.size(this.splitCodePaths) != 0 ? this.splitCodePaths : null);
        appInfo.setVersionCode(this.mLongVersionCode);
        appInfo.setAppClassNamesByProcess(buildAppClassNamesByProcess());
        appInfo.setLocaleConfigRes(this.mLocaleConfigRes);
        if (!this.mKnownActivityEmbeddingCerts.isEmpty()) {
            appInfo.setKnownActivityEmbeddingCerts(this.mKnownActivityEmbeddingCerts);
        }
        appInfo.allowCrossUidActivitySwitchFromBelow = this.mAllowCrossUidActivitySwitchFromBelow;
        return appInfo;
    }

    private PackageImpl setBoolean(long flag, boolean value) {
        if (value) {
            this.mBooleans |= flag;
        } else {
            this.mBooleans &= ~flag;
        }
        return this;
    }

    private boolean getBoolean(long flag) {
        return (this.mBooleans & flag) != 0;
    }

    private PackageImpl setBoolean2(long flag, boolean value) {
        if (value) {
            this.mBooleans2 |= flag;
        } else {
            this.mBooleans2 &= ~flag;
        }
        return this;
    }

    private boolean getBoolean2(long flag) {
        return (this.mBooleans2 & flag) != 0;
    }

    public PackageImpl(
            String packageName,
            String baseApkPath,
            String path,
            TypedArray manifestArray,
            boolean isCoreApp,
            ParsingPackageUtils.Callback callback) {
        this.usesLibraries = Collections.emptyList();
        this.usesOptionalLibraries = Collections.emptyList();
        this.usesNativeLibraries = Collections.emptyList();
        this.usesOptionalNativeLibraries = Collections.emptyList();
        this.originalPackages = Collections.emptyList();
        this.adoptPermissions = Collections.emptyList();
        this.requestedPermissions = Collections.emptySet();
        this.protectedBroadcasts = Collections.emptyList();
        this.activities = Collections.emptyList();
        this.apexSystemServices = Collections.emptyList();
        this.receivers = Collections.emptyList();
        this.services = Collections.emptyList();
        this.providers = Collections.emptyList();
        this.permissions = Collections.emptyList();
        this.permissionGroups = Collections.emptyList();
        this.instrumentations = Collections.emptyList();
        this.overlayables = Collections.emptyMap();
        this.libraryNames = Collections.emptyList();
        this.usesStaticLibraries = Collections.emptyList();
        this.usesSdkLibraries = Collections.emptyList();
        this.configPreferences = Collections.emptyList();
        this.reqFeatures = Collections.emptyList();
        this.featureGroups = Collections.emptyList();
        this.usesPermissions = Collections.emptyList();
        this.implicitPermissions = Collections.emptySet();
        this.upgradeKeySets = Collections.emptySet();
        this.keySetMapping = Collections.emptyMap();
        this.attributions = Collections.emptyList();
        this.preferredActivityFilters = Collections.emptyList();
        this.processes = Collections.emptyMap();
        this.mProperties = Collections.emptyMap();
        this.signingDetails = SigningDetails.UNKNOWN;
        this.queriesIntents = Collections.emptyList();
        this.queriesPackages = Collections.emptyList();
        this.queriesProviders = Collections.emptySet();
        this.category = -1;
        this.installLocation = -1;
        this.minSdkVersion = 1;
        this.maxSdkVersion = Integer.MAX_VALUE;
        this.targetSdkVersion = 0;
        this.mimeGroups = Collections.emptySet();
        this.mBooleans = FrontendInnerFec.FEC_18_30;
        this.mBooleans2 = 4L;
        this.mKnownActivityEmbeddingCerts = Collections.emptySet();
        this.uid = -1;
        this.packageName = TextUtils.safeIntern(packageName);
        this.mBaseApkPath = baseApkPath;
        this.mPath = path;
        this.mCallback = callback;
        if (manifestArray != null) {
            this.versionCode = manifestArray.getInteger(1, 0);
            this.versionCodeMajor = manifestArray.getInteger(11, 0);
            setBaseRevisionCode(manifestArray.getInteger(5, 0));
            setVersionName(manifestArray.getNonConfigurationString(2, 0));
            setCompileSdkVersion(manifestArray.getInteger(9, 0));
            setCompileSdkVersionCodeName(manifestArray.getNonConfigurationString(10, 0));
            setIsolatedSplitLoading(manifestArray.getBoolean(6, false));
        }
        this.manifestPackageName = this.packageName;
        setBoolean(FrontendInnerFec.FEC_140_180, isCoreApp);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl hideAsParsed() {
        assignDerivedFields();
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public AndroidPackageInternal hideAsFinal() {
        if (this.mStorageUuid == null) {
            assignDerivedFields();
        }
        assignDerivedFields2();
        makeImmutable();
        return this;
    }

    private static String[] sortLibraries(List<String> libraryNames) {
        int size = libraryNames.size();
        if (size == 0) {
            return EmptyArray.STRING;
        }
        String[] arr = (String[]) libraryNames.toArray(EmptyArray.STRING);
        Arrays.sort(arr);
        return arr;
    }

    private void assignDerivedFields2() {
        this.mBaseAppInfoFlags = AppInfoUtils.appInfoFlags(this);
        this.mBaseAppInfoPrivateFlags = AppInfoUtils.appInfoPrivateFlags(this);
        this.mBaseAppInfoPrivateFlagsExt =
                AppInfoUtils.appInfoPrivateFlagsExt(
                        this,
                        this.mCallback == null
                                ? false
                                : this.mCallback
                                        .getHiddenApiWhitelistedApps()
                                        .contains(this.packageName));
        String baseAppDataDir = Environment.getDataDirectoryPath(getVolumeUuid()) + File.separator;
        String systemUserSuffix = File.separator + 0 + File.separator;
        this.mBaseAppDataCredentialProtectedDirForSystemUser =
                TextUtils.safeIntern(baseAppDataDir + "user" + systemUserSuffix);
        this.mBaseAppDataDeviceProtectedDirForSystemUser =
                TextUtils.safeIntern(baseAppDataDir + Environment.DIR_USER_DE + systemUserSuffix);
    }

    private void makeImmutable() {
        this.usesLibraries = Collections.unmodifiableList(this.usesLibraries);
        this.usesOptionalLibraries = Collections.unmodifiableList(this.usesOptionalLibraries);
        this.usesNativeLibraries = Collections.unmodifiableList(this.usesNativeLibraries);
        this.usesOptionalNativeLibraries =
                Collections.unmodifiableList(this.usesOptionalNativeLibraries);
        this.originalPackages = Collections.unmodifiableList(this.originalPackages);
        this.adoptPermissions = Collections.unmodifiableList(this.adoptPermissions);
        this.requestedPermissions = Collections.unmodifiableSet(this.requestedPermissions);
        this.protectedBroadcasts = Collections.unmodifiableList(this.protectedBroadcasts);
        this.apexSystemServices = Collections.unmodifiableList(this.apexSystemServices);
        this.activities = Collections.unmodifiableList(this.activities);
        this.receivers = Collections.unmodifiableList(this.receivers);
        this.services = Collections.unmodifiableList(this.services);
        this.providers = Collections.unmodifiableList(this.providers);
        this.permissions = Collections.unmodifiableList(this.permissions);
        this.permissionGroups = Collections.unmodifiableList(this.permissionGroups);
        this.instrumentations = Collections.unmodifiableList(this.instrumentations);
        this.overlayables = Collections.unmodifiableMap(this.overlayables);
        this.libraryNames = Collections.unmodifiableList(this.libraryNames);
        this.usesStaticLibraries = Collections.unmodifiableList(this.usesStaticLibraries);
        this.usesSdkLibraries = Collections.unmodifiableList(this.usesSdkLibraries);
        this.configPreferences = Collections.unmodifiableList(this.configPreferences);
        this.reqFeatures = Collections.unmodifiableList(this.reqFeatures);
        this.featureGroups = Collections.unmodifiableList(this.featureGroups);
        this.usesPermissions = Collections.unmodifiableList(this.usesPermissions);
        this.usesSdkLibraries = Collections.unmodifiableList(this.usesSdkLibraries);
        this.implicitPermissions = Collections.unmodifiableSet(this.implicitPermissions);
        this.upgradeKeySets = Collections.unmodifiableSet(this.upgradeKeySets);
        this.keySetMapping = Collections.unmodifiableMap(this.keySetMapping);
        this.attributions = Collections.unmodifiableList(this.attributions);
        this.preferredActivityFilters = Collections.unmodifiableList(this.preferredActivityFilters);
        this.processes = Collections.unmodifiableMap(this.processes);
        this.mProperties = Collections.unmodifiableMap(this.mProperties);
        this.queriesIntents = Collections.unmodifiableList(this.queriesIntents);
        this.queriesPackages = Collections.unmodifiableList(this.queriesPackages);
        this.queriesProviders = Collections.unmodifiableSet(this.queriesProviders);
        this.mimeGroups = Collections.unmodifiableSet(this.mimeGroups);
        this.mKnownActivityEmbeddingCerts =
                Collections.unmodifiableSet(this.mKnownActivityEmbeddingCerts);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public long getLongVersionCode() {
        return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl removePermission(int index) {
        this.permissions.remove(index);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl addUsesOptionalLibrary(int index, String libraryName) {
        this.usesOptionalLibraries =
                CollectionUtils.add(
                        this.usesOptionalLibraries, index, TextUtils.safeIntern(libraryName));
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl addUsesLibrary(int index, String libraryName) {
        this.usesLibraries =
                CollectionUtils.add(this.usesLibraries, index, TextUtils.safeIntern(libraryName));
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl removeUsesLibrary(String libraryName) {
        this.usesLibraries = CollectionUtils.remove(this.usesLibraries, libraryName);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl removeUsesOptionalLibrary(String libraryName) {
        this.usesOptionalLibraries =
                CollectionUtils.remove(this.usesOptionalLibraries, libraryName);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSigningDetails(SigningDetails value) {
        this.signingDetails = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRestrictUpdateHash(byte... value) {
        this.restrictUpdateHash = value;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setPersistent(boolean value) {
        setBoolean(64L, value);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDefaultToDeviceProtectedStorage(boolean value) {
        setBoolean(67108864L, value);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDirectBootAware(boolean value) {
        setBoolean(134217728L, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl clearProtectedBroadcasts() {
        this.protectedBroadcasts.clear();
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl clearOriginalPackages() {
        this.originalPackages.clear();
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl clearAdoptPermissions() {
        this.adoptPermissions.clear();
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setPath(String path) {
        this.mPath = path;
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setPackageName(String packageName) {
        this.packageName = TextUtils.safeIntern(packageName);
        int permissionsSize = this.permissions.size();
        for (int index = 0; index < permissionsSize; index++) {
            ComponentMutateUtils.setPackageName(this.permissions.get(index), this.packageName);
        }
        int permissionGroupsSize = this.permissionGroups.size();
        for (int index2 = 0; index2 < permissionGroupsSize; index2++) {
            ComponentMutateUtils.setPackageName(
                    this.permissionGroups.get(index2), this.packageName);
        }
        int activitiesSize = this.activities.size();
        for (int index3 = 0; index3 < activitiesSize; index3++) {
            ComponentMutateUtils.setPackageName(this.activities.get(index3), this.packageName);
        }
        int receiversSize = this.receivers.size();
        for (int index4 = 0; index4 < receiversSize; index4++) {
            ComponentMutateUtils.setPackageName(this.receivers.get(index4), this.packageName);
        }
        int providersSize = this.providers.size();
        for (int index5 = 0; index5 < providersSize; index5++) {
            ComponentMutateUtils.setPackageName(this.providers.get(index5), this.packageName);
        }
        int servicesSize = this.services.size();
        for (int index6 = 0; index6 < servicesSize; index6++) {
            ComponentMutateUtils.setPackageName(this.services.get(index6), this.packageName);
        }
        int instrumentationsSize = this.instrumentations.size();
        for (int index7 = 0; index7 < instrumentationsSize; index7++) {
            ComponentMutateUtils.setPackageName(
                    this.instrumentations.get(index7), this.packageName);
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setAllComponentsDirectBootAware(boolean allComponentsDirectBootAware) {
        int activitiesSize = this.activities.size();
        for (int index = 0; index < activitiesSize; index++) {
            ComponentMutateUtils.setDirectBootAware(
                    this.activities.get(index), allComponentsDirectBootAware);
        }
        int receiversSize = this.receivers.size();
        for (int index2 = 0; index2 < receiversSize; index2++) {
            ComponentMutateUtils.setDirectBootAware(
                    this.receivers.get(index2), allComponentsDirectBootAware);
        }
        int providersSize = this.providers.size();
        for (int index3 = 0; index3 < providersSize; index3++) {
            ComponentMutateUtils.setDirectBootAware(
                    this.providers.get(index3), allComponentsDirectBootAware);
        }
        int servicesSize = this.services.size();
        for (int index4 = 0; index4 < servicesSize; index4++) {
            ComponentMutateUtils.setDirectBootAware(
                    this.services.get(index4), allComponentsDirectBootAware);
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setBaseApkPath(String baseApkPath) {
        this.mBaseApkPath = TextUtils.safeIntern(baseApkPath);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setNativeLibraryDir(String nativeLibraryDir) {
        this.nativeLibraryDir = TextUtils.safeIntern(nativeLibraryDir);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setNativeLibraryRootDir(String nativeLibraryRootDir) {
        this.nativeLibraryRootDir = TextUtils.safeIntern(nativeLibraryRootDir);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setPrimaryCpuAbi(String primaryCpuAbi) {
        this.primaryCpuAbi = TextUtils.safeIntern(primaryCpuAbi);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSecondaryCpuAbi(String secondaryCpuAbi) {
        this.secondaryCpuAbi = TextUtils.safeIntern(secondaryCpuAbi);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSecondaryNativeLibraryDir(String secondaryNativeLibraryDir) {
        this.secondaryNativeLibraryDir = TextUtils.safeIntern(secondaryNativeLibraryDir);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSplitCodePaths(String[] splitCodePaths) {
        this.splitCodePaths = splitCodePaths;
        if (splitCodePaths != null) {
            int size = splitCodePaths.length;
            for (int index = 0; index < size; index++) {
                this.splitCodePaths[index] = TextUtils.safeIntern(this.splitCodePaths[index]);
            }
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl capPermissionPriorities() {
        int size = this.permissionGroups.size();
        for (int index = size - 1; index >= 0; index--) {
            ComponentMutateUtils.setPriority(this.permissionGroups.get(index), 0);
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl markNotActivitiesAsNotExportedIfSingleUser() {
        int receiversSize = this.receivers.size();
        for (int index = 0; index < receiversSize; index++) {
            ParsedActivity receiver = this.receivers.get(index);
            if ((1073741824 & receiver.getFlags()) != 0) {
                ComponentMutateUtils.setExported(receiver, false);
            }
        }
        int servicesSize = this.services.size();
        for (int index2 = 0; index2 < servicesSize; index2++) {
            ParsedService service = this.services.get(index2);
            if ((service.getFlags() & 1073741824) != 0) {
                ComponentMutateUtils.setExported(service, false);
            }
        }
        int providersSize = this.providers.size();
        for (int index3 = 0; index3 < providersSize; index3++) {
            ParsedProvider provider = this.providers.get(index3);
            if ((provider.getFlags() & 1073741824) != 0) {
                ComponentMutateUtils.setExported(provider, false);
            }
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setCoreApp(boolean coreApp) {
        return setBoolean(FrontendInnerFec.FEC_140_180, coreApp);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVersionCode(int versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVersionCodeMajor(int versionCodeMajor) {
        this.versionCodeMajor = versionCodeMajor;
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden,
              // com.android.internal.pm.pkg.parsing.ParsingPackageHidden
    public ApplicationInfo toAppInfoWithoutState() {
        ApplicationInfo appInfo = toAppInfoWithoutStateWithoutFlags();
        appInfo.flags = this.mBaseAppInfoFlags;
        appInfo.privateFlags = this.mBaseAppInfoPrivateFlags;
        appInfo.privateFlagsExt = this.mBaseAppInfoPrivateFlagsExt;
        appInfo.nativeLibraryDir = this.nativeLibraryDir;
        appInfo.nativeLibraryRootDir = this.nativeLibraryRootDir;
        appInfo.nativeLibraryRootRequiresIsa = this.nativeLibraryRootRequiresIsa;
        appInfo.primaryCpuAbi = this.primaryCpuAbi;
        appInfo.secondaryCpuAbi = this.secondaryCpuAbi;
        appInfo.secondaryNativeLibraryDir = this.secondaryNativeLibraryDir;
        appInfo.seInfoUser = SEInfoUtil.COMPLETE_STR;
        appInfo.uid = this.uid;
        return appInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        sForBoolean.parcel(this.supportsSmallScreens, dest, flags);
        sForBoolean.parcel(this.supportsNormalScreens, dest, flags);
        sForBoolean.parcel(this.supportsLargeScreens, dest, flags);
        sForBoolean.parcel(this.supportsExtraLargeScreens, dest, flags);
        sForBoolean.parcel(this.resizeable, dest, flags);
        sForBoolean.parcel(this.anyDensity, dest, flags);
        dest.writeInt(this.versionCode);
        dest.writeInt(this.versionCodeMajor);
        dest.writeInt(this.baseRevisionCode);
        sForInternedString.parcel(this.versionName, dest, flags);
        dest.writeInt(this.compileSdkVersion);
        dest.writeString(this.compileSdkVersionCodeName);
        sForInternedString.parcel(this.packageName, dest, flags);
        dest.writeString(this.mBaseApkPath);
        dest.writeString(this.restrictedAccountType);
        dest.writeString(this.requiredAccountType);
        dest.writeString(this.mEmergencyInstaller);
        sForInternedString.parcel(this.overlayTarget, dest, flags);
        dest.writeString(this.overlayTargetOverlayableName);
        dest.writeString(this.overlayCategory);
        dest.writeInt(this.overlayPriority);
        sForInternedStringValueMap.parcel(this.overlayables, dest, flags);
        sForInternedString.parcel(this.sdkLibraryName, dest, flags);
        dest.writeInt(this.sdkLibVersionMajor);
        sForInternedString.parcel(this.staticSharedLibraryName, dest, flags);
        dest.writeLong(this.staticSharedLibVersion);
        sForInternedStringList.parcel(this.libraryNames, dest, flags);
        sForInternedStringList.parcel(this.usesLibraries, dest, flags);
        sForInternedStringList.parcel(this.usesOptionalLibraries, dest, flags);
        sForInternedStringList.parcel(this.usesNativeLibraries, dest, flags);
        sForInternedStringList.parcel(this.usesOptionalNativeLibraries, dest, flags);
        sForInternedStringList.parcel(this.usesStaticLibraries, dest, flags);
        dest.writeLongArray(this.usesStaticLibrariesVersions);
        if (this.usesStaticLibrariesCertDigests == null) {
            dest.writeInt(-1);
        } else {
            dest.writeInt(this.usesStaticLibrariesCertDigests.length);
            for (int index = 0; index < this.usesStaticLibrariesCertDigests.length; index++) {
                dest.writeStringArray(this.usesStaticLibrariesCertDigests[index]);
            }
        }
        sForInternedStringList.parcel(this.usesSdkLibraries, dest, flags);
        dest.writeLongArray(this.usesSdkLibrariesVersionsMajor);
        if (this.usesSdkLibrariesCertDigests == null) {
            dest.writeInt(-1);
        } else {
            dest.writeInt(this.usesSdkLibrariesCertDigests.length);
            for (int index2 = 0; index2 < this.usesSdkLibrariesCertDigests.length; index2++) {
                dest.writeStringArray(this.usesSdkLibrariesCertDigests[index2]);
            }
        }
        dest.writeBooleanArray(this.usesSdkLibrariesOptional);
        sForInternedString.parcel(this.sharedUserId, dest, flags);
        dest.writeInt(this.sharedUserLabel);
        dest.writeTypedList(this.configPreferences);
        dest.writeTypedList(this.reqFeatures);
        dest.writeTypedList(this.featureGroups);
        dest.writeByteArray(this.restrictUpdateHash);
        dest.writeStringList(this.originalPackages);
        sForInternedStringList.parcel(this.adoptPermissions, dest, flags);
        sForInternedStringSet.parcel(this.requestedPermissions, dest, flags);
        ParsingUtils.writeParcelableList(dest, this.usesPermissions);
        sForInternedStringSet.parcel(this.implicitPermissions, dest, flags);
        sForStringSet.parcel(this.upgradeKeySets, dest, flags);
        ParsingPackageUtils.writeKeySetMapping(dest, this.keySetMapping);
        sForInternedStringList.parcel(this.protectedBroadcasts, dest, flags);
        ParsingUtils.writeParcelableList(dest, this.activities);
        ParsingUtils.writeParcelableList(dest, this.apexSystemServices);
        ParsingUtils.writeParcelableList(dest, this.receivers);
        ParsingUtils.writeParcelableList(dest, this.services);
        ParsingUtils.writeParcelableList(dest, this.providers);
        ParsingUtils.writeParcelableList(dest, this.attributions);
        ParsingUtils.writeParcelableList(dest, this.permissions);
        ParsingUtils.writeParcelableList(dest, this.permissionGroups);
        ParsingUtils.writeParcelableList(dest, this.instrumentations);
        sForIntentInfoPairs.parcel(this.preferredActivityFilters, dest, flags);
        dest.writeMap(this.processes);
        dest.writeBundle(this.metaData);
        sForInternedString.parcel(this.volumeUuid, dest, flags);
        dest.writeParcelable(this.signingDetails, flags);
        dest.writeString(this.mPath);
        dest.writeTypedList(this.queriesIntents, flags);
        sForInternedStringList.parcel(this.queriesPackages, dest, flags);
        sForInternedStringSet.parcel(this.queriesProviders, dest, flags);
        dest.writeString(this.appComponentFactory);
        dest.writeString(this.backupAgentName);
        dest.writeInt(this.banner);
        dest.writeInt(this.category);
        dest.writeString(this.classLoaderName);
        dest.writeString(this.className);
        dest.writeInt(this.compatibleWidthLimitDp);
        dest.writeInt(this.descriptionRes);
        dest.writeInt(this.fullBackupContent);
        dest.writeInt(this.dataExtractionRules);
        dest.writeInt(this.iconRes);
        dest.writeInt(this.installLocation);
        dest.writeInt(this.labelRes);
        dest.writeInt(this.largestWidthLimitDp);
        dest.writeInt(this.logo);
        dest.writeString(this.manageSpaceActivityName);
        dest.writeFloat(this.maxAspectRatio);
        dest.writeFloat(this.minAspectRatio);
        dest.writeInt(this.minSdkVersion);
        dest.writeInt(this.maxSdkVersion);
        dest.writeInt(this.networkSecurityConfigRes);
        dest.writeCharSequence(this.nonLocalizedLabel);
        dest.writeString(this.permission);
        dest.writeString(this.processName);
        dest.writeInt(this.requiresSmallestWidthDp);
        dest.writeInt(this.roundIconRes);
        dest.writeInt(this.targetSandboxVersion);
        dest.writeInt(this.targetSdkVersion);
        dest.writeString(this.taskAffinity);
        dest.writeInt(this.theme);
        dest.writeInt(this.uiOptions);
        dest.writeString(this.zygotePreloadName);
        dest.writeStringArray(this.splitClassLoaderNames);
        dest.writeStringArray(this.splitCodePaths);
        dest.writeSparseArray(this.splitDependencies);
        dest.writeIntArray(this.splitFlags);
        dest.writeStringArray(this.splitNames);
        dest.writeIntArray(this.splitRevisionCodes);
        sForBoolean.parcel(this.resizeableActivity, dest, flags);
        dest.writeInt(this.autoRevokePermissions);
        sForInternedStringSet.parcel(this.mimeGroups, dest, flags);
        dest.writeInt(this.gwpAsanMode);
        dest.writeSparseIntArray(this.minExtensionVersions);
        dest.writeMap(this.mProperties);
        dest.writeInt(this.memtagMode);
        dest.writeInt(this.nativeHeapZeroInitialized);
        sForBoolean.parcel(this.requestRawExternalStorageAccess, dest, flags);
        dest.writeInt(this.mLocaleConfigRes);
        sForStringSet.parcel(this.mKnownActivityEmbeddingCerts, dest, flags);
        sForInternedString.parcel(this.manifestPackageName, dest, flags);
        dest.writeString(this.nativeLibraryDir);
        dest.writeString(this.nativeLibraryRootDir);
        dest.writeBoolean(this.nativeLibraryRootRequiresIsa);
        sForInternedString.parcel(this.primaryCpuAbi, dest, flags);
        sForInternedString.parcel(this.secondaryCpuAbi, dest, flags);
        dest.writeString(this.secondaryNativeLibraryDir);
        dest.writeInt(this.uid);
        dest.writeLong(this.mBooleans);
        dest.writeLong(this.mBooleans2);
        dest.writeBoolean(this.mAllowCrossUidActivitySwitchFromBelow);
    }

    public PackageImpl(Parcel in) {
        this(in, null);
    }

    public PackageImpl(Parcel in, ParsingPackageUtils.Callback callback) {
        this.usesLibraries = Collections.emptyList();
        this.usesOptionalLibraries = Collections.emptyList();
        this.usesNativeLibraries = Collections.emptyList();
        this.usesOptionalNativeLibraries = Collections.emptyList();
        this.originalPackages = Collections.emptyList();
        this.adoptPermissions = Collections.emptyList();
        this.requestedPermissions = Collections.emptySet();
        this.protectedBroadcasts = Collections.emptyList();
        this.activities = Collections.emptyList();
        this.apexSystemServices = Collections.emptyList();
        this.receivers = Collections.emptyList();
        this.services = Collections.emptyList();
        this.providers = Collections.emptyList();
        this.permissions = Collections.emptyList();
        this.permissionGroups = Collections.emptyList();
        this.instrumentations = Collections.emptyList();
        this.overlayables = Collections.emptyMap();
        this.libraryNames = Collections.emptyList();
        this.usesStaticLibraries = Collections.emptyList();
        this.usesSdkLibraries = Collections.emptyList();
        this.configPreferences = Collections.emptyList();
        this.reqFeatures = Collections.emptyList();
        this.featureGroups = Collections.emptyList();
        this.usesPermissions = Collections.emptyList();
        this.implicitPermissions = Collections.emptySet();
        this.upgradeKeySets = Collections.emptySet();
        this.keySetMapping = Collections.emptyMap();
        this.attributions = Collections.emptyList();
        this.preferredActivityFilters = Collections.emptyList();
        this.processes = Collections.emptyMap();
        this.mProperties = Collections.emptyMap();
        this.signingDetails = SigningDetails.UNKNOWN;
        this.queriesIntents = Collections.emptyList();
        this.queriesPackages = Collections.emptyList();
        this.queriesProviders = Collections.emptySet();
        this.category = -1;
        this.installLocation = -1;
        this.minSdkVersion = 1;
        this.maxSdkVersion = Integer.MAX_VALUE;
        this.targetSdkVersion = 0;
        this.mimeGroups = Collections.emptySet();
        this.mBooleans = FrontendInnerFec.FEC_18_30;
        this.mBooleans2 = 4L;
        this.mKnownActivityEmbeddingCerts = Collections.emptySet();
        this.uid = -1;
        this.mCallback = callback;
        ClassLoader boot = Object.class.getClassLoader();
        this.supportsSmallScreens = sForBoolean.unparcel(in);
        this.supportsNormalScreens = sForBoolean.unparcel(in);
        this.supportsLargeScreens = sForBoolean.unparcel(in);
        this.supportsExtraLargeScreens = sForBoolean.unparcel(in);
        this.resizeable = sForBoolean.unparcel(in);
        this.anyDensity = sForBoolean.unparcel(in);
        this.versionCode = in.readInt();
        this.versionCodeMajor = in.readInt();
        this.baseRevisionCode = in.readInt();
        this.versionName = sForInternedString.unparcel(in);
        this.compileSdkVersion = in.readInt();
        this.compileSdkVersionCodeName = in.readString();
        this.packageName = sForInternedString.unparcel(in);
        this.mBaseApkPath = in.readString();
        this.restrictedAccountType = in.readString();
        this.requiredAccountType = in.readString();
        this.mEmergencyInstaller = in.readString();
        this.overlayTarget = sForInternedString.unparcel(in);
        this.overlayTargetOverlayableName = in.readString();
        this.overlayCategory = in.readString();
        this.overlayPriority = in.readInt();
        this.overlayables = sForInternedStringValueMap.unparcel(in);
        this.sdkLibraryName = sForInternedString.unparcel(in);
        this.sdkLibVersionMajor = in.readInt();
        this.staticSharedLibraryName = sForInternedString.unparcel(in);
        this.staticSharedLibVersion = in.readLong();
        this.libraryNames = sForInternedStringList.unparcel(in);
        this.usesLibraries = sForInternedStringList.unparcel(in);
        this.usesOptionalLibraries = sForInternedStringList.unparcel(in);
        this.usesNativeLibraries = sForInternedStringList.unparcel(in);
        this.usesOptionalNativeLibraries = sForInternedStringList.unparcel(in);
        this.usesStaticLibraries = sForInternedStringList.unparcel(in);
        this.usesStaticLibrariesVersions = in.createLongArray();
        int digestsSize = in.readInt();
        if (digestsSize >= 0) {
            this.usesStaticLibrariesCertDigests = new String[digestsSize][];
            for (int index = 0; index < digestsSize; index++) {
                this.usesStaticLibrariesCertDigests[index] = sForInternedStringArray.unparcel(in);
            }
        }
        this.usesSdkLibraries = sForInternedStringList.unparcel(in);
        this.usesSdkLibrariesVersionsMajor = in.createLongArray();
        int digestsSize2 = in.readInt();
        if (digestsSize2 >= 0) {
            this.usesSdkLibrariesCertDigests = new String[digestsSize2][];
            for (int index2 = 0; index2 < digestsSize2; index2++) {
                this.usesSdkLibrariesCertDigests[index2] = sForInternedStringArray.unparcel(in);
            }
        }
        this.usesSdkLibrariesOptional = in.createBooleanArray();
        this.sharedUserId = sForInternedString.unparcel(in);
        this.sharedUserLabel = in.readInt();
        this.configPreferences = in.createTypedArrayList(ConfigurationInfo.CREATOR);
        this.reqFeatures = in.createTypedArrayList(FeatureInfo.CREATOR);
        this.featureGroups = in.createTypedArrayList(FeatureGroupInfo.CREATOR);
        this.restrictUpdateHash = in.createByteArray();
        this.originalPackages = in.createStringArrayList();
        this.adoptPermissions = sForInternedStringList.unparcel(in);
        this.requestedPermissions = sForInternedStringSet.unparcel(in);
        this.usesPermissions =
                ParsingUtils.createTypedInterfaceList(in, ParsedUsesPermissionImpl.CREATOR);
        this.implicitPermissions = sForInternedStringSet.unparcel(in);
        this.upgradeKeySets = sForStringSet.unparcel(in);
        this.keySetMapping = ParsingPackageUtils.readKeySetMapping(in);
        this.protectedBroadcasts = sForInternedStringList.unparcel(in);
        this.activities = ParsingUtils.createTypedInterfaceList(in, ParsedActivityImpl.CREATOR);
        this.apexSystemServices =
                ParsingUtils.createTypedInterfaceList(in, ParsedApexSystemServiceImpl.CREATOR);
        this.receivers = ParsingUtils.createTypedInterfaceList(in, ParsedActivityImpl.CREATOR);
        this.services = ParsingUtils.createTypedInterfaceList(in, ParsedServiceImpl.CREATOR);
        this.providers = ParsingUtils.createTypedInterfaceList(in, ParsedProviderImpl.CREATOR);
        this.attributions =
                ParsingUtils.createTypedInterfaceList(in, ParsedAttributionImpl.CREATOR);
        this.permissions = ParsingUtils.createTypedInterfaceList(in, ParsedPermissionImpl.CREATOR);
        this.permissionGroups =
                ParsingUtils.createTypedInterfaceList(in, ParsedPermissionGroupImpl.CREATOR);
        this.instrumentations =
                ParsingUtils.createTypedInterfaceList(in, ParsedInstrumentationImpl.CREATOR);
        this.preferredActivityFilters = sForIntentInfoPairs.unparcel(in);
        this.processes = in.readHashMap(ParsedProcessImpl.class.getClassLoader());
        this.metaData = in.readBundle(boot);
        this.volumeUuid = sForInternedString.unparcel(in);
        this.signingDetails = (SigningDetails) in.readParcelable(boot, SigningDetails.class);
        this.mPath = in.readString();
        this.queriesIntents = in.createTypedArrayList(Intent.CREATOR);
        this.queriesPackages = sForInternedStringList.unparcel(in);
        this.queriesProviders = sForInternedStringSet.unparcel(in);
        this.appComponentFactory = in.readString();
        this.backupAgentName = in.readString();
        this.banner = in.readInt();
        this.category = in.readInt();
        this.classLoaderName = in.readString();
        this.className = in.readString();
        this.compatibleWidthLimitDp = in.readInt();
        this.descriptionRes = in.readInt();
        this.fullBackupContent = in.readInt();
        this.dataExtractionRules = in.readInt();
        this.iconRes = in.readInt();
        this.installLocation = in.readInt();
        this.labelRes = in.readInt();
        this.largestWidthLimitDp = in.readInt();
        this.logo = in.readInt();
        this.manageSpaceActivityName = in.readString();
        this.maxAspectRatio = in.readFloat();
        this.minAspectRatio = in.readFloat();
        this.minSdkVersion = in.readInt();
        this.maxSdkVersion = in.readInt();
        this.networkSecurityConfigRes = in.readInt();
        this.nonLocalizedLabel = in.readCharSequence();
        this.permission = in.readString();
        this.processName = in.readString();
        this.requiresSmallestWidthDp = in.readInt();
        this.roundIconRes = in.readInt();
        this.targetSandboxVersion = in.readInt();
        this.targetSdkVersion = in.readInt();
        this.taskAffinity = in.readString();
        this.theme = in.readInt();
        this.uiOptions = in.readInt();
        this.zygotePreloadName = in.readString();
        this.splitClassLoaderNames = in.createStringArray();
        this.splitCodePaths = in.createStringArray();
        this.splitDependencies = in.readSparseArray(boot);
        this.splitFlags = in.createIntArray();
        this.splitNames = in.createStringArray();
        this.splitRevisionCodes = in.createIntArray();
        this.resizeableActivity = sForBoolean.unparcel(in);
        this.autoRevokePermissions = in.readInt();
        this.mimeGroups = sForInternedStringSet.unparcel(in);
        this.gwpAsanMode = in.readInt();
        this.minExtensionVersions = in.readSparseIntArray();
        this.mProperties = in.readHashMap(boot);
        this.memtagMode = in.readInt();
        this.nativeHeapZeroInitialized = in.readInt();
        this.requestRawExternalStorageAccess = sForBoolean.unparcel(in);
        this.mLocaleConfigRes = in.readInt();
        this.mKnownActivityEmbeddingCerts = sForStringSet.unparcel(in);
        this.manifestPackageName = sForInternedString.unparcel(in);
        this.nativeLibraryDir = in.readString();
        this.nativeLibraryRootDir = in.readString();
        this.nativeLibraryRootRequiresIsa = in.readBoolean();
        this.primaryCpuAbi = sForInternedString.unparcel(in);
        this.secondaryCpuAbi = sForInternedString.unparcel(in);
        this.secondaryNativeLibraryDir = in.readString();
        this.uid = in.readInt();
        this.mBooleans = in.readLong();
        this.mBooleans2 = in.readLong();
        this.mAllowCrossUidActivitySwitchFromBelow = in.readBoolean();
        assignDerivedFields();
        assignDerivedFields2();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getManifestPackageName() {
        return this.manifestPackageName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isStub() {
        return getBoolean2(1L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getNativeLibraryDir() {
        return this.nativeLibraryDir;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getNativeLibraryRootDir() {
        return this.nativeLibraryRootDir;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isNativeLibraryRootRequiresIsa() {
        return this.nativeLibraryRootRequiresIsa;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public String getPrimaryCpuAbi() {
        return this.primaryCpuAbi;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public String getSecondaryCpuAbi() {
        return this.secondaryCpuAbi;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getSecondaryNativeLibraryDir() {
        return this.secondaryNativeLibraryDir;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isCoreApp() {
        return getBoolean(FrontendInnerFec.FEC_140_180);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isSystem() {
        return getBoolean(9007199254740992L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isUpdatableSystem() {
        return getBoolean2(4L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isFactoryTest() {
        return getBoolean(18014398509481984L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isApex() {
        return getBoolean2(2L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isSystemExt() {
        return getBoolean(72057594037927936L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isPrivileged() {
        return getBoolean(144115188075855872L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isOem() {
        return getBoolean(288230376151711744L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isVendor() {
        return getBoolean(576460752303423488L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isProduct() {
        return getBoolean(1152921504606846976L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isOdm() {
        return getBoolean(2305843009213693952L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isSignedWithPlatformKey() {
        return getBoolean(Context.BIND_EXTERNAL_SERVICE_LONG);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getUid() {
        return this.uid;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setStub(boolean value) {
        setBoolean2(1L, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setNativeLibraryRootRequiresIsa(boolean value) {
        this.nativeLibraryRootRequiresIsa = value;
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSystem(boolean value) {
        setBoolean(9007199254740992L, value);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUpdatableSystem(boolean value) {
        return setBoolean2(4L, value);
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setFactoryTest(boolean value) {
        setBoolean(18014398509481984L, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setApex(boolean isApex) {
        setBoolean2(2L, isApex);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSystemExt(boolean value) {
        setBoolean(72057594037927936L, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setPrivileged(boolean value) {
        setBoolean(144115188075855872L, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setOem(boolean value) {
        setBoolean(288230376151711744L, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setVendor(boolean value) {
        setBoolean(576460752303423488L, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setProduct(boolean value) {
        setBoolean(1152921504606846976L, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setOdm(boolean value) {
        setBoolean(2305843009213693952L, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSignedWithPlatformKey(boolean value) {
        setBoolean(Context.BIND_EXTERNAL_SERVICE_LONG, value);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setUid(int value) {
        this.uid = value;
        return this;
    }

    public String getBaseAppDataCredentialProtectedDirForSystemUser() {
        return this.mBaseAppDataCredentialProtectedDirForSystemUser;
    }

    public String getBaseAppDataDeviceProtectedDirForSystemUser() {
        return this.mBaseAppDataDeviceProtectedDirForSystemUser;
    }

    private static class Booleans {
        private static final long ALLOW_AUDIO_PLAYBACK_CAPTURE = 2147483648L;
        private static final long ALLOW_BACKUP = 4;
        private static final long ALLOW_CLEAR_USER_DATA = 2048;
        private static final long ALLOW_CLEAR_USER_DATA_ON_FAILED_RESTORE = 1073741824;
        private static final long ALLOW_NATIVE_HEAP_POINTER_TAGGING = 68719476736L;
        private static final long ALLOW_TASK_REPARENTING = 1024;
        private static final long ATTRIBUTIONS_ARE_USER_VISIBLE = 140737488355328L;
        private static final long BACKUP_IN_FOREGROUND = 16777216;
        private static final long CANT_SAVE_STATE = 34359738368L;
        private static final long CORE_APP = 4503599627370496L;
        private static final long CROSS_PROFILE = 8796093022208L;
        private static final long DEBUGGABLE = 128;
        private static final long DEFAULT_TO_DEVICE_PROTECTED_STORAGE = 67108864;
        private static final long DIRECT_BOOT_AWARE = 134217728;
        private static final long DISALLOW_PROFILING = 35184372088832L;
        private static final long ENABLED = 17592186044416L;
        private static final long ENABLE_ON_BACK_INVOKED_CALLBACK = 1125899906842624L;
        private static final long EXTERNAL_STORAGE = 1;
        private static final long EXTRACT_NATIVE_LIBS = 131072;
        private static final long FACTORY_TEST = 18014398509481984L;
        private static final long FORCE_QUERYABLE = 4398046511104L;
        private static final long FULL_BACKUP_ONLY = 32;
        private static final long GAME = 262144;
        private static final long HARDWARE_ACCELERATED = 2;
        private static final long HAS_CODE = 512;
        private static final long HAS_DOMAIN_URLS = 4194304;
        private static final long HAS_FRAGILE_USER_DATA = 17179869184L;
        private static final long ISOLATED_SPLIT_LOADING = 2097152;
        private static final long KILL_AFTER_RESTORE = 8;
        private static final long LARGE_HEAP = 4096;
        private static final long LEAVING_SHARED_UID = 2251799813685248L;
        private static final long MULTI_ARCH = 65536;
        private static final long NATIVE_LIBRARY_ROOT_REQUIRES_ISA = Long.MIN_VALUE;
        private static final long ODM = 2305843009213693952L;
        private static final long OEM = 288230376151711744L;
        private static final long OVERLAY = 1048576;
        private static final long OVERLAY_IS_STATIC = 549755813888L;
        private static final long PARTIALLY_DIRECT_BOOT_AWARE = 268435456;
        private static final long PERSISTENT = 64;
        private static final long PRESERVE_LEGACY_EXTERNAL_STORAGE = 137438953472L;
        private static final long PRIVILEGED = 144115188075855872L;
        private static final long PRODUCT = 1152921504606846976L;
        private static final long PROFILEABLE_BY_SHELL = 8388608;
        private static final long REQUEST_FOREGROUND_SERVICE_EXEMPTION = 70368744177664L;
        private static final long REQUEST_LEGACY_EXTERNAL_STORAGE = 4294967296L;
        private static final long REQUIRED_FOR_ALL_USERS = 274877906944L;
        private static final long RESET_ENABLED_SETTINGS_ON_APP_DATA_CLEARED = 281474976710656L;
        private static final long RESIZEABLE_ACTIVITY_VIA_SDK_VERSION = 536870912;
        private static final long RESTORE_ANY_VERSION = 16;
        private static final long SDK_LIBRARY = 562949953421312L;
        private static final long SIGNED_WITH_PLATFORM_KEY = 4611686018427387904L;
        private static final long STATIC_SHARED_LIBRARY = 524288;
        private static final long SUPPORTS_RTL = 16384;
        private static final long SYSTEM = 9007199254740992L;
        private static final long SYSTEM_EXT = 72057594037927936L;
        private static final long TEST_ONLY = 32768;
        private static final long USES_CLEARTEXT_TRAFFIC = 8192;
        private static final long USES_NON_SDK_API = 8589934592L;
        private static final long USE_32_BIT_ABI = 1099511627776L;
        private static final long USE_EMBEDDED_DEX = 33554432;
        private static final long VENDOR = 576460752303423488L;
        private static final long VISIBLE_TO_INSTANT_APPS = 2199023255552L;
        private static final long VM_SAFE_MODE = 256;

        public @interface Flags {}

        private Booleans() {}
    }

    private static class Booleans2 {
        private static final long APEX = 2;
        private static final long STUB = 1;
        private static final long UPDATABLE_SYSTEM = 4;

        public @interface Flags {}

        private Booleans2() {}
    }
}
