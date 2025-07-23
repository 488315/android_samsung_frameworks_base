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
import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import libcore.util.EmptyArray;

/* loaded from: classes5.dex */
public class PackageImpl implements ParsedPackage, AndroidPackageInternal, AndroidPackageHidden, ParsingPackage, ParsingPackageHidden, Parcelable {
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
    private int[] mAlternateLauncherIconResIds;
    private int[] mAlternateLauncherLabelResIds;
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
    private Map<String, Boolean> mFeatureFlagState;
    private int mIntentMatchingFlags;
    private Set<String> mKnownActivityEmbeddingCerts;
    private int mLocaleConfigRes;
    private long mLongVersionCode;
    private int mPageSizeAppCompatFlags;
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

    @Deprecated
    protected Set<String> requestedPermissions;
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
    private static final Comparator<ParsedMainComponent> ORDER_COMPARATOR = new Comparator() { // from class: com.android.internal.pm.parsing.pkg.PackageImpl$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int compare;
            compare = Integer.compare(((ParsedMainComponent) obj2).getOrder(), ((ParsedMainComponent) obj).getOrder());
            return compare;
        }
    };
    public static final Parcelling.BuiltIn.ForBoolean sForBoolean = (Parcelling.BuiltIn.ForBoolean) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForBoolean.class);
    public static final Parcelling.BuiltIn.ForInternedString sForInternedString = (Parcelling.BuiltIn.ForInternedString) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForInternedString.class);
    public static final Parcelling.BuiltIn.ForInternedStringArray sForInternedStringArray = (Parcelling.BuiltIn.ForInternedStringArray) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForInternedStringArray.class);
    public static final Parcelling.BuiltIn.ForInternedStringList sForInternedStringList = (Parcelling.BuiltIn.ForInternedStringList) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForInternedStringList.class);
    public static final Parcelling.BuiltIn.ForInternedStringValueMap sForInternedStringValueMap = (Parcelling.BuiltIn.ForInternedStringValueMap) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForInternedStringValueMap.class);
    public static final Parcelling.BuiltIn.ForStringSet sForStringSet = (Parcelling.BuiltIn.ForStringSet) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForStringSet.class);
    public static final Parcelling.BuiltIn.ForInternedStringSet sForInternedStringSet = (Parcelling.BuiltIn.ForInternedStringSet) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForInternedStringSet.class);
    protected static final ParsingUtils.StringPairListParceler sForIntentInfoPairs = new ParsingUtils.StringPairListParceler();
    public static final Parcelable.Creator<PackageImpl> CREATOR = new Parcelable.Creator<PackageImpl>() { // from class: com.android.internal.pm.parsing.pkg.PackageImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageImpl createFromParcel(Parcel parcel) {
            return new PackageImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageImpl[] newArray(int i) {
            return new PackageImpl[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public /* bridge */ /* synthetic */ ParsingPackage asSplit(String[] strArr, String[] strArr2, int[] iArr, SparseArray sparseArray) {
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

    public static PackageImpl forParsing(String str, String str2, String str3, TypedArray typedArray, boolean z, ParsingPackageUtils.Callback callback) {
        return new PackageImpl(str, str2, str3, typedArray, z, callback);
    }

    public static AndroidPackage buildFakeForDeletion(String str, String str2) {
        return forTesting(str).setVolumeUuid(str2).hideAsParsed().hideAsFinal();
    }

    public static ParsingPackage forTesting(String str) {
        return forTesting(str, "");
    }

    public static ParsingPackage forTesting(String str, String str2) {
        return new PackageImpl(str, str2, str2, null, false, null);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addActivity(ParsedActivity parsedActivity) {
        this.activities = CollectionUtils.add(this.activities, parsedActivity);
        addMimeGroupsFromComponent(parsedActivity);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addAdoptPermission(String str) {
        this.adoptPermissions = CollectionUtils.add(this.adoptPermissions, TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public final PackageImpl addApexSystemService(ParsedApexSystemService parsedApexSystemService) {
        this.apexSystemServices = CollectionUtils.add(this.apexSystemServices, parsedApexSystemService);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addAttribution(ParsedAttribution parsedAttribution) {
        this.attributions = CollectionUtils.add(this.attributions, parsedAttribution);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addConfigPreference(ConfigurationInfo configurationInfo) {
        this.configPreferences = CollectionUtils.add(this.configPreferences, configurationInfo);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addFeatureGroup(FeatureGroupInfo featureGroupInfo) {
        this.featureGroups = CollectionUtils.add(this.featureGroups, featureGroupInfo);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addImplicitPermission(String str) {
        addUsesPermission((ParsedUsesPermission) new ParsedUsesPermissionImpl(str, 0));
        this.implicitPermissions = CollectionUtils.add(this.implicitPermissions, TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addInstrumentation(ParsedInstrumentation parsedInstrumentation) {
        this.instrumentations = CollectionUtils.add(this.instrumentations, parsedInstrumentation);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addKeySet(String str, PublicKey publicKey) {
        ArraySet<PublicKey> arraySet = this.keySetMapping.get(str);
        if (arraySet == null) {
            arraySet = new ArraySet<>();
        }
        arraySet.add(publicKey);
        this.keySetMapping = CollectionUtils.add(this.keySetMapping, str, arraySet);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addLibraryName(String str) {
        this.libraryNames = CollectionUtils.add(this.libraryNames, TextUtils.safeIntern(str));
        return this;
    }

    private void addMimeGroupsFromComponent(ParsedComponent parsedComponent) {
        for (int size = parsedComponent.getIntents().size() - 1; size >= 0; size--) {
            IntentFilter intentFilter = parsedComponent.getIntents().get(size).getIntentFilter();
            for (int countMimeGroups = intentFilter.countMimeGroups() - 1; countMimeGroups >= 0; countMimeGroups--) {
                Set<String> set = this.mimeGroups;
                if (set != null && set.size() > 500) {
                    throw new IllegalStateException("Max limit on number of MIME Groups reached");
                }
                this.mimeGroups = CollectionUtils.add(this.mimeGroups, intentFilter.getMimeGroup(countMimeGroups));
            }
        }
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addOriginalPackage(String str) {
        this.originalPackages = CollectionUtils.add(this.originalPackages, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage addOverlayable(String str, String str2) {
        this.overlayables = CollectionUtils.add(this.overlayables, str, TextUtils.safeIntern(str2));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addPermission(ParsedPermission parsedPermission) {
        this.permissions = CollectionUtils.add(this.permissions, parsedPermission);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addPermissionGroup(ParsedPermissionGroup parsedPermissionGroup) {
        this.permissionGroups = CollectionUtils.add(this.permissionGroups, parsedPermissionGroup);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addPreferredActivityFilter(String str, ParsedIntentInfo parsedIntentInfo) {
        this.preferredActivityFilters = CollectionUtils.add(this.preferredActivityFilters, Pair.create(str, parsedIntentInfo));
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
    public PackageImpl addProtectedBroadcast(String str) {
        if (!this.protectedBroadcasts.contains(str)) {
            this.protectedBroadcasts = CollectionUtils.add(this.protectedBroadcasts, TextUtils.safeIntern(str));
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
    public PackageImpl addQueriesPackage(String str) {
        this.queriesPackages = CollectionUtils.add(this.queriesPackages, TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addQueriesProvider(String str) {
        this.queriesProviders = CollectionUtils.add(this.queriesProviders, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addReceiver(ParsedActivity parsedActivity) {
        this.receivers = CollectionUtils.add(this.receivers, parsedActivity);
        addMimeGroupsFromComponent(parsedActivity);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addReqFeature(FeatureInfo featureInfo) {
        this.reqFeatures = CollectionUtils.add(this.reqFeatures, featureInfo);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addService(ParsedService parsedService) {
        this.services = CollectionUtils.add(this.services, parsedService);
        addMimeGroupsFromComponent(parsedService);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesLibrary(String str) {
        String safeIntern = TextUtils.safeIntern(str);
        if (!ArrayUtils.contains(this.usesLibraries, safeIntern)) {
            this.usesLibraries = CollectionUtils.add(this.usesLibraries, safeIntern);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public final PackageImpl addUsesNativeLibrary(String str) {
        String safeIntern = TextUtils.safeIntern(str);
        if (!ArrayUtils.contains(this.usesNativeLibraries, safeIntern)) {
            this.usesNativeLibraries = CollectionUtils.add(this.usesNativeLibraries, safeIntern);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesOptionalLibrary(String str) {
        String safeIntern = TextUtils.safeIntern(str);
        if (!ArrayUtils.contains(this.usesOptionalLibraries, safeIntern)) {
            this.usesOptionalLibraries = CollectionUtils.add(this.usesOptionalLibraries, safeIntern);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public final PackageImpl addUsesOptionalNativeLibrary(String str) {
        String safeIntern = TextUtils.safeIntern(str);
        if (!ArrayUtils.contains(this.usesOptionalNativeLibraries, safeIntern)) {
            this.usesOptionalNativeLibraries = CollectionUtils.add(this.usesOptionalNativeLibraries, safeIntern);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesPermission(ParsedUsesPermission parsedUsesPermission) {
        this.usesPermissions = CollectionUtils.add(this.usesPermissions, parsedUsesPermission);
        this.requestedPermissions = CollectionUtils.add(this.requestedPermissions, parsedUsesPermission.getName());
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesSdkLibrary(String str, long j, String[] strArr, boolean z) {
        this.usesSdkLibraries = CollectionUtils.add(this.usesSdkLibraries, TextUtils.safeIntern(str));
        this.usesSdkLibrariesVersionsMajor = ArrayUtils.appendLong(this.usesSdkLibrariesVersionsMajor, j, true);
        this.usesSdkLibrariesCertDigests = (String[][]) ArrayUtils.appendElement(String[].class, this.usesSdkLibrariesCertDigests, strArr, true);
        this.usesSdkLibrariesOptional = ArrayUtils.appendBooleanDuplicatesAllowed(this.usesSdkLibrariesOptional, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addUsesStaticLibrary(String str, long j, String[] strArr) {
        this.usesStaticLibraries = CollectionUtils.add(this.usesStaticLibraries, TextUtils.safeIntern(str));
        this.usesStaticLibrariesVersions = ArrayUtils.appendLong(this.usesStaticLibrariesVersions, j, true);
        this.usesStaticLibrariesCertDigests = (String[][]) ArrayUtils.appendElement(String[].class, this.usesStaticLibrariesCertDigests, strArr, true);
        return this;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isAttributionsUserVisible() {
        return getBoolean(FrontendInnerFec.FEC_96_180);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl asSplit(String[] strArr, String[] strArr2, int[] iArr, SparseArray<int[]> sparseArray) {
        this.splitNames = strArr;
        this.splitCodePaths = strArr2;
        this.splitRevisionCodes = iArr;
        this.splitDependencies = sparseArray;
        int length = strArr.length;
        this.splitFlags = new int[length];
        this.splitClassLoaderNames = new String[length];
        return this;
    }

    protected void assignDerivedFields() {
        this.mStorageUuid = StorageManager.convert(this.volumeUuid);
        this.mLongVersionCode = PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    private ArrayMap<String, String> buildAppClassNamesByProcess() {
        if (ArrayUtils.size(this.processes) == 0) {
            return null;
        }
        ArrayMap<String, String> arrayMap = new ArrayMap<>(4);
        for (String str : this.processes.keySet()) {
            ArrayMap<String, String> appClassNamesByPackage = this.processes.get(str).getAppClassNamesByPackage();
            for (int i = 0; i < appClassNamesByPackage.size(); i++) {
                if (this.packageName.equals(appClassNamesByPackage.keyAt(i))) {
                    String valueAt = appClassNamesByPackage.valueAt(i);
                    if (!TextUtils.isEmpty(valueAt)) {
                        arrayMap.put(str, valueAt);
                    }
                }
            }
        }
        return arrayMap;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<AndroidPackageSplit> getSplits() {
        if (this.mSplits == null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new AndroidPackageSplitImpl(null, getBaseApkPath(), getBaseRevisionCode(), isDeclaredHavingCode() ? 4 : 0, getClassLoaderName()));
            if (this.splitNames != null) {
                for (int i = 0; i < this.splitNames.length; i++) {
                    arrayList.add(new AndroidPackageSplitImpl(this.splitNames[i], this.splitCodePaths[i], this.splitRevisionCodes[i], this.splitFlags[i], this.splitClassLoaderNames[i]));
                }
            }
            if (this.splitDependencies != null) {
                for (int i2 = 0; i2 < this.splitDependencies.size(); i2++) {
                    int keyAt = this.splitDependencies.keyAt(i2);
                    int[] valueAt = this.splitDependencies.valueAt(i2);
                    ArrayList arrayList2 = new ArrayList();
                    for (int i3 : valueAt) {
                        if (i3 >= 0) {
                            arrayList2.add((AndroidPackageSplit) arrayList.get(i3));
                        }
                    }
                    ((AndroidPackageSplitImpl) arrayList.get(keyAt)).fillDependencies(Collections.unmodifiableList(arrayList2));
                }
            }
            this.mSplits = Collections.unmodifiableList(arrayList);
        }
        return this.mSplits;
    }

    public String toString() {
        return "Package{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedActivity> getActivities() {
        return this.activities;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<String> getAdoptPermissions() {
        return this.adoptPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int[] getAlternateLauncherIconResIds() {
        return this.mAlternateLauncherIconResIds;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int[] getAlternateLauncherLabelResIds() {
        return this.mAlternateLauncherLabelResIds;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<ParsedApexSystemService> getApexSystemServices() {
        return this.apexSystemServices;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getAppComponentFactory() {
        return this.appComponentFactory;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedInstrumentation> getInstrumentations() {
        return this.instrumentations;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public float getMaxAspectRatio() {
        return this.maxAspectRatio;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getMaxSdkVersion() {
        return this.maxSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getMemtagMode() {
        return this.memtagMode;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public Bundle getMetaData() {
        return this.metaData;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Set<String> getMimeGroups() {
        return this.mimeGroups;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public float getMinAspectRatio() {
        return this.minAspectRatio;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public SparseIntArray getMinExtensionVersions() {
        return this.minExtensionVersions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getMinSdkVersion() {
        return this.minSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getNativeHeapZeroInitialized() {
        return this.nativeHeapZeroInitialized;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getPageSizeAppCompatFlags() {
        return this.mPageSizeAppCompatFlags;
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package
    public int getOverlayPriority() {
        return this.overlayPriority;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package, com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getPackageName() {
        return this.packageName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public String getPath() {
        return this.mPath;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getPermission() {
        return this.permission;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<ParsedPermissionGroup> getPermissionGroups() {
        return this.permissionGroups;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedPermission> getPermissions() {
        return this.permissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<Pair<String, ParsedIntentInfo>> getPreferredActivityFilters() {
        return this.preferredActivityFilters;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getProcessName() {
        String str = this.processName;
        return str != null ? str : this.packageName;
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedActivity> getReceivers() {
        return this.receivers;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public List<FeatureInfo> getRequestedFeatures() {
        return this.reqFeatures;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getSdkLibraryName() {
        return this.sdkLibraryName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getSdkLibVersionMajor() {
        return this.sdkLibVersionMajor;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedService> getServices() {
        return this.services;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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
        String[] strArr = this.splitClassLoaderNames;
        return strArr == null ? EmptyArray.STRING : strArr;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public String[] getSplitCodePaths() {
        String[] strArr = this.splitCodePaths;
        return strArr == null ? EmptyArray.STRING : strArr;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public SparseArray<int[]> getSplitDependencies() {
        SparseArray<int[]> sparseArray = this.splitDependencies;
        return sparseArray == null ? EMPTY_INT_ARRAY_SPARSE_ARRAY : sparseArray;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int[] getSplitFlags() {
        return this.splitFlags;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public String[] getSplitNames() {
        String[] strArr = this.splitNames;
        return strArr == null ? EmptyArray.STRING : strArr;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int[] getSplitRevisionCodes() {
        int[] iArr = this.splitRevisionCodes;
        return iArr == null ? EmptyArray.INT : iArr;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package, com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getTargetSdkVersion() {
        return this.targetSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getTaskAffinity() {
        return this.taskAffinity;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getThemeResourceId() {
        return this.theme;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getUiOptions() {
        return this.uiOptions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public Set<String> getUpgradeKeySets() {
        return this.upgradeKeySets;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public List<ParsedUsesPermission> getUsesPermissions() {
        return this.usesPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public long[] getUsesSdkLibrariesVersionsMajor() {
        return this.usesSdkLibrariesVersionsMajor;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean[] getUsesSdkLibrariesOptional() {
        return this.usesSdkLibrariesOptional;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden, com.android.internal.pm.pkg.parsing.ParsingPackageHidden
    public int getVersionCode() {
        return this.versionCode;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden, com.android.internal.pm.pkg.parsing.ParsingPackageHidden
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public String getZygotePreloadName() {
        return this.zygotePreloadName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isTaskReparentingAllowed() {
        return getBoolean(1024L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isAnyDensity() {
        Boolean bool = this.anyDensity;
        if (bool == null) {
            return this.targetSdkVersion >= 4;
        }
        return bool.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isBackupInForeground() {
        return getBoolean(16777216L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isHardwareAccelerated() {
        return getBoolean(2L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isProfileable() {
        return !getBoolean(FrontendInnerFec.FEC_20_30);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isResizeable() {
        Boolean bool = this.resizeable;
        if (bool == null) {
            return this.targetSdkVersion >= 4;
        }
        return bool.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
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

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isStaticSharedLibrary() {
        return getBoolean(524288L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isExtraLargeScreensSupported() {
        Boolean bool = this.supportsExtraLargeScreens;
        if (bool == null) {
            return this.targetSdkVersion >= 9;
        }
        return bool.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isLargeScreensSupported() {
        Boolean bool = this.supportsLargeScreens;
        if (bool == null) {
            return this.targetSdkVersion >= 4;
        }
        return bool.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isNormalScreensSupported() {
        Boolean bool = this.supportsNormalScreens;
        return bool == null || bool.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isRtlSupported() {
        return getBoolean(16384L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isSmallScreensSupported() {
        Boolean bool = this.supportsSmallScreens;
        if (bool == null) {
            return this.targetSdkVersion >= 4;
        }
        return bool.booleanValue();
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
    public PackageImpl removeUsesOptionalNativeLibrary(String str) {
        this.usesOptionalNativeLibraries = CollectionUtils.remove(this.usesOptionalNativeLibraries, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAllowAudioPlaybackCapture(boolean z) {
        return setBoolean(2147483648L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBackupAllowed(boolean z) {
        return setBoolean(4L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setClearUserDataAllowed(boolean z) {
        return setBoolean(2048L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setClearUserDataOnFailedRestoreAllowed(boolean z) {
        return setBoolean(1073741824L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAllowNativeHeapPointerTagging(boolean z) {
        return setBoolean(68719476736L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAlternateLauncherIconResIds(int[] iArr) {
        this.mAlternateLauncherIconResIds = iArr;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAlternateLauncherLabelResIds(int[] iArr) {
        this.mAlternateLauncherLabelResIds = iArr;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTaskReparentingAllowed(boolean z) {
        return setBoolean(1024L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAnyDensity(int i) {
        if (i == 1) {
            return this;
        }
        this.anyDensity = Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAppComponentFactory(String str) {
        this.appComponentFactory = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setAttributionsAreUserVisible(boolean z) {
        setBoolean(FrontendInnerFec.FEC_96_180, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setAutoRevokePermissions(int i) {
        this.autoRevokePermissions = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBackupAgentName(String str) {
        this.backupAgentName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBackupInForeground(boolean z) {
        return setBoolean(16777216L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBannerResourceId(int i) {
        this.banner = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setHardwareAccelerated(boolean z) {
        return setBoolean(2L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setBaseRevisionCode(int i) {
        this.baseRevisionCode = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSaveStateDisallowed(boolean z) {
        return setBoolean(34359738368L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCategory(int i) {
        this.category = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setClassLoaderName(String str) {
        this.classLoaderName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setApplicationClassName(String str) {
        this.className = str == null ? null : str.trim();
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCompatibleWidthLimitDp(int i) {
        this.compatibleWidthLimitDp = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCompileSdkVersion(int i) {
        this.compileSdkVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setCompileSdkVersionCodeName(String str) {
        this.compileSdkVersionCodeName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCrossProfile(boolean z) {
        return setBoolean(8796093022208L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDataExtractionRulesResourceId(int i) {
        this.dataExtractionRules = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDebuggable(boolean z) {
        return setBoolean(128L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDescriptionResourceId(int i) {
        this.descriptionRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setEnabled(boolean z) {
        return setBoolean(FrontendInnerFec.FEC_18_30, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setExternalStorage(boolean z) {
        return setBoolean(1L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setExtractNativeLibrariesRequested(boolean z) {
        return setBoolean(131072L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setForceQueryable(boolean z) {
        return setBoolean(4398046511104L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setFullBackupContentResourceId(int i) {
        this.fullBackupContent = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setFullBackupOnly(boolean z) {
        return setBoolean(32L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setGame(boolean z) {
        return setBoolean(262144L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setGwpAsanMode(int i) {
        this.gwpAsanMode = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDeclaredHavingCode(boolean z) {
        return setBoolean(512L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setHasDomainUrls(boolean z) {
        return setBoolean(4194304L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUserDataFragile(boolean z) {
        return setBoolean(17179869184L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setIconResourceId(int i) {
        this.iconRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setInstallLocation(int i) {
        this.installLocation = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setIsolatedSplitLoading(boolean z) {
        return setBoolean(2097152L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setKillAfterRestoreAllowed(boolean z) {
        return setBoolean(8L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setKnownActivityEmbeddingCerts(Set<String> set) {
        this.mKnownActivityEmbeddingCerts = set;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLabelResourceId(int i) {
        this.labelRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLargeHeap(boolean z) {
        return setBoolean(4096L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLargestWidthLimitDp(int i) {
        this.largestWidthLimitDp = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLeavingSharedUser(boolean z) {
        return setBoolean(FrontendInnerFec.FEC_135_180, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLocaleConfigResourceId(int i) {
        this.mLocaleConfigRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLogoResourceId(int i) {
        this.logo = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setManageSpaceActivityName(String str) {
        this.manageSpaceActivityName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMaxAspectRatio(float f) {
        this.maxAspectRatio = f;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMaxSdkVersion(int i) {
        this.maxSdkVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMemtagMode(int i) {
        this.memtagMode = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMetaData(Bundle bundle) {
        this.metaData = bundle;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMinAspectRatio(float f) {
        this.minAspectRatio = f;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMinExtensionVersions(SparseIntArray sparseIntArray) {
        this.minExtensionVersions = sparseIntArray;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMinSdkVersion(int i) {
        this.minSdkVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setMultiArch(boolean z) {
        return setBoolean(65536L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNativeHeapZeroInitialized(int i) {
        this.nativeHeapZeroInitialized = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setPageSizeAppCompatFlags(int i) {
        this.mPageSizeAppCompatFlags = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNetworkSecurityConfigResourceId(int i) {
        this.networkSecurityConfigRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNonLocalizedLabel(CharSequence charSequence) {
        this.nonLocalizedLabel = charSequence == null ? null : charSequence.toString().trim();
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setOnBackInvokedCallbackEnabled(boolean z) {
        setBoolean(FrontendInnerFec.FEC_132_180, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setAllowCrossUidActivitySwitchFromBelow(boolean z) {
        this.mAllowCrossUidActivitySwitchFromBelow = z;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setResourceOverlay(boolean z) {
        return setBoolean(1048576L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayCategory(String str) {
        this.overlayCategory = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayIsStatic(boolean z) {
        return setBoolean(549755813888L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayPriority(int i) {
        this.overlayPriority = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayTarget(String str) {
        this.overlayTarget = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setOverlayTargetOverlayableName(String str) {
        this.overlayTargetOverlayableName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setPartiallyDirectBootAware(boolean z) {
        return setBoolean(268435456L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setPermission(String str) {
        this.permission = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setPreserveLegacyExternalStorage(boolean z) {
        return setBoolean(137438953472L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setProcessName(String str) {
        this.processName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setProcesses(Map<String, ParsedProcess> map) {
        this.processes = map;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setProfileable(boolean z) {
        return setBoolean(FrontendInnerFec.FEC_20_30, !z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setProfileableByShell(boolean z) {
        return setBoolean(8388608L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequestForegroundServiceExemption(boolean z) {
        return setBoolean(FrontendInnerFec.FEC_90_180, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequestLegacyExternalStorage(boolean z) {
        return setBoolean(4294967296L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequestRawExternalStorageAccess(Boolean bool) {
        this.requestRawExternalStorageAccess = bool;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequiredAccountType(String str) {
        this.requiredAccountType = TextUtils.nullIfEmpty(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequiredForAllUsers(boolean z) {
        return setBoolean(274877906944L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRequiresSmallestWidthDp(int i) {
        this.requiresSmallestWidthDp = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setResetEnabledSettingsOnAppDataCleared(boolean z) {
        setBoolean(281474976710656L, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setResizeable(int i) {
        if (i == 1) {
            return this;
        }
        this.resizeable = Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setResizeableActivity(Boolean bool) {
        this.resizeableActivity = bool;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setResizeableActivityViaSdkVersion(boolean z) {
        return setBoolean(536870912L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRestoreAnyVersion(boolean z) {
        return setBoolean(16L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRestrictedAccountType(String str) {
        this.restrictedAccountType = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setEmergencyInstaller(String str) {
        this.mEmergencyInstaller = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRoundIconResourceId(int i) {
        this.roundIconRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSdkLibraryName(String str) {
        this.sdkLibraryName = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSdkLibVersionMajor(int i) {
        this.sdkLibVersionMajor = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSdkLibrary(boolean z) {
        return setBoolean(562949953421312L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSharedUserId(String str) {
        this.sharedUserId = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSharedUserLabelResourceId(int i) {
        this.sharedUserLabel = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSplitClassLoaderName(int i, String str) {
        this.splitClassLoaderNames[i] = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSplitHasCode(int i, boolean z) {
        int i2;
        int[] iArr = this.splitFlags;
        if (z) {
            i2 = iArr[i] | 4;
        } else {
            i2 = iArr[i] & (-5);
        }
        iArr[i] = i2;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setStaticSharedLibraryName(String str) {
        this.staticSharedLibraryName = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setStaticSharedLibraryVersion(long j) {
        this.staticSharedLibVersion = j;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setStaticSharedLibrary(boolean z) {
        return setBoolean(524288L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setExtraLargeScreensSupported(int i) {
        if (i == 1) {
            return this;
        }
        this.supportsExtraLargeScreens = Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setLargeScreensSupported(int i) {
        if (i == 1) {
            return this;
        }
        this.supportsLargeScreens = Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNormalScreensSupported(int i) {
        if (i == 1) {
            return this;
        }
        this.supportsNormalScreens = Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRtlSupported(boolean z) {
        return setBoolean(16384L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSmallScreensSupported(int i) {
        if (i == 1) {
            return this;
        }
        this.supportsSmallScreens = Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTargetSandboxVersion(int i) {
        this.targetSandboxVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTargetSdkVersion(int i) {
        this.targetSdkVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTaskAffinity(String str) {
        this.taskAffinity = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setTestOnly(boolean z) {
        return setBoolean(32768L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setThemeResourceId(int i) {
        this.theme = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUiOptions(int i) {
        this.uiOptions = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUpgradeKeySets(Set<String> set) {
        this.upgradeKeySets = set;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl set32BitAbiPreferred(boolean z) {
        return setBoolean(1099511627776L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUseEmbeddedDex(boolean z) {
        return setBoolean(33554432L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setCleartextTrafficAllowed(boolean z) {
        return setBoolean(8192L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setNonSdkApiRequested(boolean z) {
        return setBoolean(8589934592L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVersionName(String str) {
        this.versionName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVisibleToInstantApps(boolean z) {
        return setBoolean(2199023255552L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVmSafeMode(boolean z) {
        return setBoolean(256L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVolumeUuid(String str) {
        this.volumeUuid = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setZygotePreloadName(String str) {
        this.zygotePreloadName = str;
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
        int i;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.appComponentFactory = this.appComponentFactory;
        applicationInfo.backupAgentName = this.backupAgentName;
        applicationInfo.banner = this.banner;
        applicationInfo.category = this.category;
        applicationInfo.classLoaderName = this.classLoaderName;
        applicationInfo.className = this.className;
        applicationInfo.compatibleWidthLimitDp = this.compatibleWidthLimitDp;
        applicationInfo.compileSdkVersion = this.compileSdkVersion;
        applicationInfo.compileSdkVersionCodename = this.compileSdkVersionCodeName;
        applicationInfo.crossProfile = isCrossProfile();
        applicationInfo.descriptionRes = this.descriptionRes;
        applicationInfo.enabled = getBoolean(FrontendInnerFec.FEC_18_30);
        applicationInfo.fullBackupContent = this.fullBackupContent;
        applicationInfo.dataExtractionRulesRes = this.dataExtractionRules;
        if (!ParsingPackageUtils.sUseRoundIcon || (i = this.roundIconRes) == 0) {
            i = this.iconRes;
        }
        applicationInfo.icon = i;
        applicationInfo.iconRes = this.iconRes;
        applicationInfo.roundIconRes = this.roundIconRes;
        applicationInfo.installLocation = this.installLocation;
        applicationInfo.labelRes = this.labelRes;
        applicationInfo.largestWidthLimitDp = this.largestWidthLimitDp;
        applicationInfo.logo = this.logo;
        applicationInfo.manageSpaceActivityName = this.manageSpaceActivityName;
        applicationInfo.maxAspectRatio = this.maxAspectRatio;
        applicationInfo.metaData = this.metaData;
        applicationInfo.minAspectRatio = this.minAspectRatio;
        applicationInfo.minSdkVersion = this.minSdkVersion;
        applicationInfo.name = this.className;
        applicationInfo.networkSecurityConfigRes = this.networkSecurityConfigRes;
        applicationInfo.nonLocalizedLabel = this.nonLocalizedLabel;
        applicationInfo.packageName = this.packageName;
        applicationInfo.permission = this.permission;
        applicationInfo.processName = getProcessName();
        applicationInfo.requiresSmallestWidthDp = this.requiresSmallestWidthDp;
        applicationInfo.splitClassLoaderNames = this.splitClassLoaderNames;
        SparseArray<int[]> sparseArray = this.splitDependencies;
        applicationInfo.splitDependencies = (sparseArray == null || sparseArray.size() == 0) ? null : this.splitDependencies;
        applicationInfo.splitNames = this.splitNames;
        applicationInfo.storageUuid = this.mStorageUuid;
        applicationInfo.targetSandboxVersion = this.targetSandboxVersion;
        applicationInfo.targetSdkVersion = this.targetSdkVersion;
        applicationInfo.taskAffinity = this.taskAffinity;
        applicationInfo.theme = this.theme;
        applicationInfo.uiOptions = this.uiOptions;
        applicationInfo.volumeUuid = this.volumeUuid;
        applicationInfo.zygotePreloadName = this.zygotePreloadName;
        applicationInfo.setGwpAsanMode(this.gwpAsanMode);
        applicationInfo.setMemtagMode(this.memtagMode);
        applicationInfo.setNativeHeapZeroInitialized(this.nativeHeapZeroInitialized);
        applicationInfo.setRequestRawExternalStorageAccess(this.requestRawExternalStorageAccess);
        applicationInfo.setBaseCodePath(this.mBaseApkPath);
        applicationInfo.setBaseResourcePath(this.mBaseApkPath);
        applicationInfo.setCodePath(this.mPath);
        applicationInfo.setResourcePath(this.mPath);
        applicationInfo.setSplitCodePaths(ArrayUtils.size(this.splitCodePaths) == 0 ? null : this.splitCodePaths);
        applicationInfo.setSplitResourcePaths(ArrayUtils.size(this.splitCodePaths) != 0 ? this.splitCodePaths : null);
        applicationInfo.setVersionCode(this.mLongVersionCode);
        applicationInfo.setAppClassNamesByProcess(buildAppClassNamesByProcess());
        applicationInfo.setLocaleConfigRes(this.mLocaleConfigRes);
        if (!this.mKnownActivityEmbeddingCerts.isEmpty()) {
            applicationInfo.setKnownActivityEmbeddingCerts(this.mKnownActivityEmbeddingCerts);
        }
        applicationInfo.allowCrossUidActivitySwitchFromBelow = this.mAllowCrossUidActivitySwitchFromBelow;
        applicationInfo.setPageSizeAppCompatFlags(this.mPageSizeAppCompatFlags);
        return applicationInfo;
    }

    private PackageImpl setBoolean(long j, boolean z) {
        if (z) {
            this.mBooleans = j | this.mBooleans;
            return this;
        }
        this.mBooleans = (~j) & this.mBooleans;
        return this;
    }

    private boolean getBoolean(long j) {
        return (this.mBooleans & j) != 0;
    }

    private PackageImpl setBoolean2(long j, boolean z) {
        if (z) {
            this.mBooleans2 = j | this.mBooleans2;
            return this;
        }
        this.mBooleans2 = (~j) & this.mBooleans2;
        return this;
    }

    private boolean getBoolean2(long j) {
        return (this.mBooleans2 & j) != 0;
    }

    public PackageImpl(String str, String str2, String str3, TypedArray typedArray, boolean z, ParsingPackageUtils.Callback callback) {
        this.usesLibraries = Collections.EMPTY_LIST;
        this.usesOptionalLibraries = Collections.EMPTY_LIST;
        this.usesNativeLibraries = Collections.EMPTY_LIST;
        this.usesOptionalNativeLibraries = Collections.EMPTY_LIST;
        this.originalPackages = Collections.EMPTY_LIST;
        this.adoptPermissions = Collections.EMPTY_LIST;
        this.requestedPermissions = Collections.EMPTY_SET;
        this.protectedBroadcasts = Collections.EMPTY_LIST;
        this.activities = Collections.EMPTY_LIST;
        this.apexSystemServices = Collections.EMPTY_LIST;
        this.receivers = Collections.EMPTY_LIST;
        this.services = Collections.EMPTY_LIST;
        this.providers = Collections.EMPTY_LIST;
        this.permissions = Collections.EMPTY_LIST;
        this.permissionGroups = Collections.EMPTY_LIST;
        this.instrumentations = Collections.EMPTY_LIST;
        this.overlayables = Collections.EMPTY_MAP;
        this.libraryNames = Collections.EMPTY_LIST;
        this.usesStaticLibraries = Collections.EMPTY_LIST;
        this.usesSdkLibraries = Collections.EMPTY_LIST;
        this.configPreferences = Collections.EMPTY_LIST;
        this.reqFeatures = Collections.EMPTY_LIST;
        this.featureGroups = Collections.EMPTY_LIST;
        this.usesPermissions = Collections.EMPTY_LIST;
        this.implicitPermissions = Collections.EMPTY_SET;
        this.upgradeKeySets = Collections.EMPTY_SET;
        this.keySetMapping = Collections.EMPTY_MAP;
        this.attributions = Collections.EMPTY_LIST;
        this.preferredActivityFilters = Collections.EMPTY_LIST;
        this.processes = Collections.EMPTY_MAP;
        this.mProperties = Collections.EMPTY_MAP;
        this.signingDetails = SigningDetails.UNKNOWN;
        this.queriesIntents = Collections.EMPTY_LIST;
        this.queriesPackages = Collections.EMPTY_LIST;
        this.queriesProviders = Collections.EMPTY_SET;
        this.category = -1;
        this.installLocation = -1;
        this.minSdkVersion = 1;
        this.maxSdkVersion = Integer.MAX_VALUE;
        this.targetSdkVersion = 0;
        this.mPageSizeAppCompatFlags = 0;
        this.mimeGroups = Collections.EMPTY_SET;
        this.mBooleans = FrontendInnerFec.FEC_18_30;
        this.mBooleans2 = 4L;
        this.mKnownActivityEmbeddingCerts = Collections.EMPTY_SET;
        this.mFeatureFlagState = new ArrayMap();
        this.uid = -1;
        this.packageName = TextUtils.safeIntern(str);
        this.mBaseApkPath = str2;
        this.mPath = str3;
        this.mCallback = callback;
        if (typedArray != null) {
            this.versionCode = typedArray.getInteger(1, 0);
            this.versionCodeMajor = typedArray.getInteger(11, 0);
            setBaseRevisionCode(typedArray.getInteger(5, 0));
            setVersionName(typedArray.getNonConfigurationString(2, 0));
            setCompileSdkVersion(typedArray.getInteger(9, 0));
            setCompileSdkVersionCodeName(typedArray.getNonConfigurationString(10, 0));
            setIsolatedSplitLoading(typedArray.getBoolean(6, false));
        }
        this.manifestPackageName = this.packageName;
        setBoolean(FrontendInnerFec.FEC_140_180, z);
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

    private static String[] sortLibraries(List<String> list) {
        if (list.size() == 0) {
            return EmptyArray.STRING;
        }
        String[] strArr = (String[]) list.toArray(EmptyArray.STRING);
        Arrays.sort(strArr);
        return strArr;
    }

    private void assignDerivedFields2() {
        this.mBaseAppInfoFlags = AppInfoUtils.appInfoFlags(this);
        this.mBaseAppInfoPrivateFlags = AppInfoUtils.appInfoPrivateFlags(this);
        ParsingPackageUtils.Callback callback = this.mCallback;
        this.mBaseAppInfoPrivateFlagsExt = AppInfoUtils.appInfoPrivateFlagsExt(this, callback == null ? false : callback.getHiddenApiWhitelistedApps().contains(this.packageName));
        String str = Environment.getDataDirectoryPath(getVolumeUuid()) + File.separator;
        String str2 = File.separator + 0 + File.separator;
        this.mBaseAppDataCredentialProtectedDirForSystemUser = TextUtils.safeIntern(str + "user" + str2);
        this.mBaseAppDataDeviceProtectedDirForSystemUser = TextUtils.safeIntern(str + Environment.DIR_USER_DE + str2);
    }

    private void makeImmutable() {
        this.usesLibraries = Collections.unmodifiableList(this.usesLibraries);
        this.usesOptionalLibraries = Collections.unmodifiableList(this.usesOptionalLibraries);
        this.usesNativeLibraries = Collections.unmodifiableList(this.usesNativeLibraries);
        this.usesOptionalNativeLibraries = Collections.unmodifiableList(this.usesOptionalNativeLibraries);
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
        this.mKnownActivityEmbeddingCerts = Collections.unmodifiableSet(this.mKnownActivityEmbeddingCerts);
        this.mFeatureFlagState = Collections.unmodifiableMap(this.mFeatureFlagState);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public long getLongVersionCode() {
        return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl removePermission(int i) {
        this.permissions.remove(i);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl addUsesOptionalLibrary(int i, String str) {
        this.usesOptionalLibraries = CollectionUtils.add(this.usesOptionalLibraries, i, TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl addUsesLibrary(int i, String str) {
        this.usesLibraries = CollectionUtils.add(this.usesLibraries, i, TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl removeUsesLibrary(String str) {
        this.usesLibraries = CollectionUtils.remove(this.usesLibraries, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl removeUsesOptionalLibrary(String str) {
        this.usesOptionalLibraries = CollectionUtils.remove(this.usesOptionalLibraries, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setSigningDetails(SigningDetails signingDetails) {
        this.signingDetails = signingDetails;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setRestrictUpdateHash(byte... bArr) {
        this.restrictUpdateHash = bArr;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setPersistent(boolean z) {
        setBoolean(64L, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDefaultToDeviceProtectedStorage(boolean z) {
        setBoolean(67108864L, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setDirectBootAware(boolean z) {
        setBoolean(134217728L, z);
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
    public PackageImpl setPath(String str) {
        this.mPath = str;
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setPackageName(String str) {
        this.packageName = TextUtils.safeIntern(str);
        int size = this.permissions.size();
        for (int i = 0; i < size; i++) {
            ComponentMutateUtils.setPackageName(this.permissions.get(i), this.packageName);
        }
        int size2 = this.permissionGroups.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ComponentMutateUtils.setPackageName(this.permissionGroups.get(i2), this.packageName);
        }
        int size3 = this.activities.size();
        for (int i3 = 0; i3 < size3; i3++) {
            ComponentMutateUtils.setPackageName(this.activities.get(i3), this.packageName);
        }
        int size4 = this.receivers.size();
        for (int i4 = 0; i4 < size4; i4++) {
            ComponentMutateUtils.setPackageName(this.receivers.get(i4), this.packageName);
        }
        int size5 = this.providers.size();
        for (int i5 = 0; i5 < size5; i5++) {
            ComponentMutateUtils.setPackageName(this.providers.get(i5), this.packageName);
        }
        int size6 = this.services.size();
        for (int i6 = 0; i6 < size6; i6++) {
            ComponentMutateUtils.setPackageName(this.services.get(i6), this.packageName);
        }
        int size7 = this.instrumentations.size();
        for (int i7 = 0; i7 < size7; i7++) {
            ComponentMutateUtils.setPackageName(this.instrumentations.get(i7), this.packageName);
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setAllComponentsDirectBootAware(boolean z) {
        int size = this.activities.size();
        for (int i = 0; i < size; i++) {
            ComponentMutateUtils.setDirectBootAware(this.activities.get(i), z);
        }
        int size2 = this.receivers.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ComponentMutateUtils.setDirectBootAware(this.receivers.get(i2), z);
        }
        int size3 = this.providers.size();
        for (int i3 = 0; i3 < size3; i3++) {
            ComponentMutateUtils.setDirectBootAware(this.providers.get(i3), z);
        }
        int size4 = this.services.size();
        for (int i4 = 0; i4 < size4; i4++) {
            ComponentMutateUtils.setDirectBootAware(this.services.get(i4), z);
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setBaseApkPath(String str) {
        this.mBaseApkPath = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setNativeLibraryDir(String str) {
        this.nativeLibraryDir = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setNativeLibraryRootDir(String str) {
        this.nativeLibraryRootDir = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setPrimaryCpuAbi(String str) {
        this.primaryCpuAbi = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSecondaryCpuAbi(String str) {
        this.secondaryCpuAbi = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSecondaryNativeLibraryDir(String str) {
        this.secondaryNativeLibraryDir = TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSplitCodePaths(String[] strArr) {
        this.splitCodePaths = strArr;
        this.mSplits = null;
        if (strArr != null) {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String[] strArr2 = this.splitCodePaths;
                strArr2[i] = TextUtils.safeIntern(strArr2[i]);
            }
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl capPermissionPriorities() {
        for (int size = this.permissionGroups.size() - 1; size >= 0; size--) {
            ComponentMutateUtils.setPriority(this.permissionGroups.get(size), 0);
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl markNotActivitiesAsNotExportedIfSingleUser() {
        int size = this.receivers.size();
        for (int i = 0; i < size; i++) {
            ParsedActivity parsedActivity = this.receivers.get(i);
            if ((1073741824 & parsedActivity.getFlags()) != 0) {
                ComponentMutateUtils.setExported(parsedActivity, false);
            }
        }
        int size2 = this.services.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ParsedService parsedService = this.services.get(i2);
            if ((parsedService.getFlags() & 1073741824) != 0) {
                ComponentMutateUtils.setExported(parsedService, false);
            }
        }
        int size3 = this.providers.size();
        for (int i3 = 0; i3 < size3; i3++) {
            ParsedProvider parsedProvider = this.providers.get(i3);
            if ((parsedProvider.getFlags() & 1073741824) != 0) {
                ComponentMutateUtils.setExported(parsedProvider, false);
            }
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setCoreApp(boolean z) {
        return setBoolean(FrontendInnerFec.FEC_140_180, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVersionCode(int i) {
        this.versionCode = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setVersionCodeMajor(int i) {
        this.versionCodeMajor = i;
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden, com.android.internal.pm.pkg.parsing.ParsingPackageHidden
    public ApplicationInfo toAppInfoWithoutState() {
        ApplicationInfo appInfoWithoutStateWithoutFlags = toAppInfoWithoutStateWithoutFlags();
        appInfoWithoutStateWithoutFlags.flags = this.mBaseAppInfoFlags;
        appInfoWithoutStateWithoutFlags.privateFlags = this.mBaseAppInfoPrivateFlags;
        appInfoWithoutStateWithoutFlags.privateFlagsExt = this.mBaseAppInfoPrivateFlagsExt;
        appInfoWithoutStateWithoutFlags.nativeLibraryDir = this.nativeLibraryDir;
        appInfoWithoutStateWithoutFlags.nativeLibraryRootDir = this.nativeLibraryRootDir;
        appInfoWithoutStateWithoutFlags.nativeLibraryRootRequiresIsa = this.nativeLibraryRootRequiresIsa;
        appInfoWithoutStateWithoutFlags.primaryCpuAbi = this.primaryCpuAbi;
        appInfoWithoutStateWithoutFlags.secondaryCpuAbi = this.secondaryCpuAbi;
        appInfoWithoutStateWithoutFlags.secondaryNativeLibraryDir = this.secondaryNativeLibraryDir;
        appInfoWithoutStateWithoutFlags.seInfoUser = SEInfoUtil.COMPLETE_STR;
        appInfoWithoutStateWithoutFlags.uid = this.uid;
        return appInfoWithoutStateWithoutFlags;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeFeatureFlagState(parcel);
        Parcelling.BuiltIn.ForBoolean forBoolean = sForBoolean;
        forBoolean.parcel(this.supportsSmallScreens, parcel, i);
        forBoolean.parcel(this.supportsNormalScreens, parcel, i);
        forBoolean.parcel(this.supportsLargeScreens, parcel, i);
        forBoolean.parcel(this.supportsExtraLargeScreens, parcel, i);
        forBoolean.parcel(this.resizeable, parcel, i);
        forBoolean.parcel(this.anyDensity, parcel, i);
        parcel.writeInt(this.versionCode);
        parcel.writeInt(this.versionCodeMajor);
        parcel.writeInt(this.baseRevisionCode);
        Parcelling.BuiltIn.ForInternedString forInternedString = sForInternedString;
        forInternedString.parcel(this.versionName, parcel, i);
        parcel.writeInt(this.compileSdkVersion);
        parcel.writeString(this.compileSdkVersionCodeName);
        forInternedString.parcel(this.packageName, parcel, i);
        parcel.writeString(this.mBaseApkPath);
        parcel.writeString(this.restrictedAccountType);
        parcel.writeString(this.requiredAccountType);
        parcel.writeString(this.mEmergencyInstaller);
        forInternedString.parcel(this.overlayTarget, parcel, i);
        parcel.writeString(this.overlayTargetOverlayableName);
        parcel.writeString(this.overlayCategory);
        parcel.writeInt(this.overlayPriority);
        sForInternedStringValueMap.parcel(this.overlayables, parcel, i);
        forInternedString.parcel(this.sdkLibraryName, parcel, i);
        parcel.writeInt(this.sdkLibVersionMajor);
        forInternedString.parcel(this.staticSharedLibraryName, parcel, i);
        parcel.writeLong(this.staticSharedLibVersion);
        Parcelling.BuiltIn.ForInternedStringList forInternedStringList = sForInternedStringList;
        forInternedStringList.parcel(this.libraryNames, parcel, i);
        forInternedStringList.parcel(this.usesLibraries, parcel, i);
        forInternedStringList.parcel(this.usesOptionalLibraries, parcel, i);
        forInternedStringList.parcel(this.usesNativeLibraries, parcel, i);
        forInternedStringList.parcel(this.usesOptionalNativeLibraries, parcel, i);
        forInternedStringList.parcel(this.usesStaticLibraries, parcel, i);
        parcel.writeLongArray(this.usesStaticLibrariesVersions);
        String[][] strArr = this.usesStaticLibrariesCertDigests;
        int i2 = 0;
        if (strArr == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(strArr.length);
            int i3 = 0;
            while (true) {
                String[][] strArr2 = this.usesStaticLibrariesCertDigests;
                if (i3 >= strArr2.length) {
                    break;
                }
                parcel.writeStringArray(strArr2[i3]);
                i3++;
            }
        }
        sForInternedStringList.parcel(this.usesSdkLibraries, parcel, i);
        parcel.writeLongArray(this.usesSdkLibrariesVersionsMajor);
        String[][] strArr3 = this.usesSdkLibrariesCertDigests;
        if (strArr3 == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(strArr3.length);
            while (true) {
                String[][] strArr4 = this.usesSdkLibrariesCertDigests;
                if (i2 >= strArr4.length) {
                    break;
                }
                parcel.writeStringArray(strArr4[i2]);
                i2++;
            }
        }
        parcel.writeBooleanArray(this.usesSdkLibrariesOptional);
        Parcelling.BuiltIn.ForInternedString forInternedString2 = sForInternedString;
        forInternedString2.parcel(this.sharedUserId, parcel, i);
        parcel.writeInt(this.sharedUserLabel);
        parcel.writeTypedList(this.configPreferences);
        parcel.writeTypedList(this.reqFeatures);
        parcel.writeTypedList(this.featureGroups);
        parcel.writeByteArray(this.restrictUpdateHash);
        parcel.writeStringList(this.originalPackages);
        Parcelling.BuiltIn.ForInternedStringList forInternedStringList2 = sForInternedStringList;
        forInternedStringList2.parcel(this.adoptPermissions, parcel, i);
        Parcelling.BuiltIn.ForInternedStringSet forInternedStringSet = sForInternedStringSet;
        forInternedStringSet.parcel(this.requestedPermissions, parcel, i);
        ParsingUtils.writeParcelableList(parcel, this.usesPermissions);
        forInternedStringSet.parcel(this.implicitPermissions, parcel, i);
        Parcelling.BuiltIn.ForStringSet forStringSet = sForStringSet;
        forStringSet.parcel(this.upgradeKeySets, parcel, i);
        ParsingPackageUtils.writeKeySetMapping(parcel, this.keySetMapping);
        forInternedStringList2.parcel(this.protectedBroadcasts, parcel, i);
        ParsingUtils.writeParcelableList(parcel, this.activities);
        ParsingUtils.writeParcelableList(parcel, this.apexSystemServices);
        ParsingUtils.writeParcelableList(parcel, this.receivers);
        ParsingUtils.writeParcelableList(parcel, this.services);
        ParsingUtils.writeParcelableList(parcel, this.providers);
        ParsingUtils.writeParcelableList(parcel, this.attributions);
        ParsingUtils.writeParcelableList(parcel, this.permissions);
        ParsingUtils.writeParcelableList(parcel, this.permissionGroups);
        ParsingUtils.writeParcelableList(parcel, this.instrumentations);
        sForIntentInfoPairs.parcel(this.preferredActivityFilters, parcel, i);
        parcel.writeMap(this.processes);
        parcel.writeBundle(this.metaData);
        forInternedString2.parcel(this.volumeUuid, parcel, i);
        parcel.writeParcelable(this.signingDetails, i);
        parcel.writeString(this.mPath);
        parcel.writeTypedList(this.queriesIntents, i);
        forInternedStringList2.parcel(this.queriesPackages, parcel, i);
        forInternedStringSet.parcel(this.queriesProviders, parcel, i);
        parcel.writeString(this.appComponentFactory);
        parcel.writeString(this.backupAgentName);
        parcel.writeInt(this.banner);
        parcel.writeInt(this.category);
        parcel.writeString(this.classLoaderName);
        parcel.writeString(this.className);
        parcel.writeInt(this.compatibleWidthLimitDp);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.fullBackupContent);
        parcel.writeInt(this.dataExtractionRules);
        parcel.writeInt(this.iconRes);
        parcel.writeInt(this.installLocation);
        parcel.writeInt(this.labelRes);
        parcel.writeInt(this.largestWidthLimitDp);
        parcel.writeInt(this.logo);
        parcel.writeString(this.manageSpaceActivityName);
        parcel.writeFloat(this.maxAspectRatio);
        parcel.writeFloat(this.minAspectRatio);
        parcel.writeInt(this.minSdkVersion);
        parcel.writeInt(this.maxSdkVersion);
        parcel.writeInt(this.networkSecurityConfigRes);
        parcel.writeCharSequence(this.nonLocalizedLabel);
        parcel.writeString(this.permission);
        parcel.writeString(this.processName);
        parcel.writeInt(this.requiresSmallestWidthDp);
        parcel.writeInt(this.roundIconRes);
        parcel.writeInt(this.targetSandboxVersion);
        parcel.writeInt(this.targetSdkVersion);
        parcel.writeString(this.taskAffinity);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.uiOptions);
        parcel.writeString(this.zygotePreloadName);
        parcel.writeStringArray(this.splitClassLoaderNames);
        parcel.writeStringArray(this.splitCodePaths);
        parcel.writeSparseArray(this.splitDependencies);
        parcel.writeIntArray(this.splitFlags);
        parcel.writeStringArray(this.splitNames);
        parcel.writeIntArray(this.splitRevisionCodes);
        Parcelling.BuiltIn.ForBoolean forBoolean2 = sForBoolean;
        forBoolean2.parcel(this.resizeableActivity, parcel, i);
        parcel.writeInt(this.autoRevokePermissions);
        forInternedStringSet.parcel(this.mimeGroups, parcel, i);
        parcel.writeInt(this.gwpAsanMode);
        parcel.writeSparseIntArray(this.minExtensionVersions);
        parcel.writeMap(this.mProperties);
        parcel.writeInt(this.memtagMode);
        parcel.writeInt(this.nativeHeapZeroInitialized);
        forBoolean2.parcel(this.requestRawExternalStorageAccess, parcel, i);
        parcel.writeInt(this.mLocaleConfigRes);
        forStringSet.parcel(this.mKnownActivityEmbeddingCerts, parcel, i);
        forInternedString2.parcel(this.manifestPackageName, parcel, i);
        parcel.writeString(this.nativeLibraryDir);
        parcel.writeString(this.nativeLibraryRootDir);
        parcel.writeBoolean(this.nativeLibraryRootRequiresIsa);
        forInternedString2.parcel(this.primaryCpuAbi, parcel, i);
        forInternedString2.parcel(this.secondaryCpuAbi, parcel, i);
        parcel.writeString(this.secondaryNativeLibraryDir);
        parcel.writeInt(this.uid);
        parcel.writeLong(this.mBooleans);
        parcel.writeLong(this.mBooleans2);
        parcel.writeBoolean(this.mAllowCrossUidActivitySwitchFromBelow);
        parcel.writeInt(this.mIntentMatchingFlags);
        parcel.writeIntArray(this.mAlternateLauncherIconResIds);
        parcel.writeIntArray(this.mAlternateLauncherLabelResIds);
        parcel.writeInt(this.mPageSizeAppCompatFlags);
    }

    private void writeFeatureFlagState(Parcel parcel) {
        int size = this.mFeatureFlagState.size();
        String[] strArr = new String[size];
        Iterator<Map.Entry<String, Boolean>> it = this.mFeatureFlagState.entrySet().iterator();
        for (int i = 0; i < size; i++) {
            Map.Entry<String, Boolean> next = it.next();
            Boolean value = next.getValue();
            if (value == null) {
                strArr[i] = next.getKey() + "=?";
            } else if (value.booleanValue()) {
                strArr[i] = next.getKey() + "=1";
            } else {
                strArr[i] = next.getKey() + "=0";
            }
        }
        parcel.writeStringArray(strArr);
    }

    public PackageImpl(Parcel parcel) {
        this(parcel, null);
    }

    public PackageImpl(Parcel parcel, ParsingPackageUtils.Callback callback) {
        this.usesLibraries = Collections.EMPTY_LIST;
        this.usesOptionalLibraries = Collections.EMPTY_LIST;
        this.usesNativeLibraries = Collections.EMPTY_LIST;
        this.usesOptionalNativeLibraries = Collections.EMPTY_LIST;
        this.originalPackages = Collections.EMPTY_LIST;
        this.adoptPermissions = Collections.EMPTY_LIST;
        this.requestedPermissions = Collections.EMPTY_SET;
        this.protectedBroadcasts = Collections.EMPTY_LIST;
        this.activities = Collections.EMPTY_LIST;
        this.apexSystemServices = Collections.EMPTY_LIST;
        this.receivers = Collections.EMPTY_LIST;
        this.services = Collections.EMPTY_LIST;
        this.providers = Collections.EMPTY_LIST;
        this.permissions = Collections.EMPTY_LIST;
        this.permissionGroups = Collections.EMPTY_LIST;
        this.instrumentations = Collections.EMPTY_LIST;
        this.overlayables = Collections.EMPTY_MAP;
        this.libraryNames = Collections.EMPTY_LIST;
        this.usesStaticLibraries = Collections.EMPTY_LIST;
        this.usesSdkLibraries = Collections.EMPTY_LIST;
        this.configPreferences = Collections.EMPTY_LIST;
        this.reqFeatures = Collections.EMPTY_LIST;
        this.featureGroups = Collections.EMPTY_LIST;
        this.usesPermissions = Collections.EMPTY_LIST;
        this.implicitPermissions = Collections.EMPTY_SET;
        this.upgradeKeySets = Collections.EMPTY_SET;
        this.keySetMapping = Collections.EMPTY_MAP;
        this.attributions = Collections.EMPTY_LIST;
        this.preferredActivityFilters = Collections.EMPTY_LIST;
        this.processes = Collections.EMPTY_MAP;
        this.mProperties = Collections.EMPTY_MAP;
        this.signingDetails = SigningDetails.UNKNOWN;
        this.queriesIntents = Collections.EMPTY_LIST;
        this.queriesPackages = Collections.EMPTY_LIST;
        this.queriesProviders = Collections.EMPTY_SET;
        this.category = -1;
        this.installLocation = -1;
        this.minSdkVersion = 1;
        this.maxSdkVersion = Integer.MAX_VALUE;
        this.targetSdkVersion = 0;
        this.mPageSizeAppCompatFlags = 0;
        this.mimeGroups = Collections.EMPTY_SET;
        this.mBooleans = FrontendInnerFec.FEC_18_30;
        this.mBooleans2 = 4L;
        this.mKnownActivityEmbeddingCerts = Collections.EMPTY_SET;
        this.mFeatureFlagState = new ArrayMap();
        this.uid = -1;
        this.mCallback = callback;
        ClassLoader classLoader = Object.class.getClassLoader();
        readFeatureFlagState(parcel);
        Parcelling.BuiltIn.ForBoolean forBoolean = sForBoolean;
        this.supportsSmallScreens = forBoolean.unparcel(parcel);
        this.supportsNormalScreens = forBoolean.unparcel(parcel);
        this.supportsLargeScreens = forBoolean.unparcel(parcel);
        this.supportsExtraLargeScreens = forBoolean.unparcel(parcel);
        this.resizeable = forBoolean.unparcel(parcel);
        this.anyDensity = forBoolean.unparcel(parcel);
        this.versionCode = parcel.readInt();
        this.versionCodeMajor = parcel.readInt();
        this.baseRevisionCode = parcel.readInt();
        Parcelling.BuiltIn.ForInternedString forInternedString = sForInternedString;
        this.versionName = forInternedString.unparcel(parcel);
        this.compileSdkVersion = parcel.readInt();
        this.compileSdkVersionCodeName = parcel.readString();
        this.packageName = forInternedString.unparcel(parcel);
        this.mBaseApkPath = parcel.readString();
        this.restrictedAccountType = parcel.readString();
        this.requiredAccountType = parcel.readString();
        this.mEmergencyInstaller = parcel.readString();
        this.overlayTarget = forInternedString.unparcel(parcel);
        this.overlayTargetOverlayableName = parcel.readString();
        this.overlayCategory = parcel.readString();
        this.overlayPriority = parcel.readInt();
        this.overlayables = sForInternedStringValueMap.unparcel(parcel);
        this.sdkLibraryName = forInternedString.unparcel(parcel);
        this.sdkLibVersionMajor = parcel.readInt();
        this.staticSharedLibraryName = forInternedString.unparcel(parcel);
        this.staticSharedLibVersion = parcel.readLong();
        Parcelling.BuiltIn.ForInternedStringList forInternedStringList = sForInternedStringList;
        this.libraryNames = forInternedStringList.unparcel(parcel);
        this.usesLibraries = forInternedStringList.unparcel(parcel);
        this.usesOptionalLibraries = forInternedStringList.unparcel(parcel);
        this.usesNativeLibraries = forInternedStringList.unparcel(parcel);
        this.usesOptionalNativeLibraries = forInternedStringList.unparcel(parcel);
        this.usesStaticLibraries = forInternedStringList.unparcel(parcel);
        this.usesStaticLibrariesVersions = parcel.createLongArray();
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            this.usesStaticLibrariesCertDigests = new String[readInt][];
            for (int i = 0; i < readInt; i++) {
                this.usesStaticLibrariesCertDigests[i] = sForInternedStringArray.unparcel(parcel);
            }
        }
        this.usesSdkLibraries = sForInternedStringList.unparcel(parcel);
        this.usesSdkLibrariesVersionsMajor = parcel.createLongArray();
        int readInt2 = parcel.readInt();
        if (readInt2 >= 0) {
            this.usesSdkLibrariesCertDigests = new String[readInt2][];
            for (int i2 = 0; i2 < readInt2; i2++) {
                this.usesSdkLibrariesCertDigests[i2] = sForInternedStringArray.unparcel(parcel);
            }
        }
        this.usesSdkLibrariesOptional = parcel.createBooleanArray();
        Parcelling.BuiltIn.ForInternedString forInternedString2 = sForInternedString;
        this.sharedUserId = forInternedString2.unparcel(parcel);
        this.sharedUserLabel = parcel.readInt();
        this.configPreferences = parcel.createTypedArrayList(ConfigurationInfo.CREATOR);
        this.reqFeatures = parcel.createTypedArrayList(FeatureInfo.CREATOR);
        this.featureGroups = parcel.createTypedArrayList(FeatureGroupInfo.CREATOR);
        this.restrictUpdateHash = parcel.createByteArray();
        this.originalPackages = parcel.createStringArrayList();
        Parcelling.BuiltIn.ForInternedStringList forInternedStringList2 = sForInternedStringList;
        this.adoptPermissions = forInternedStringList2.unparcel(parcel);
        Parcelling.BuiltIn.ForInternedStringSet forInternedStringSet = sForInternedStringSet;
        this.requestedPermissions = forInternedStringSet.unparcel(parcel);
        this.usesPermissions = ParsingUtils.createTypedInterfaceList(parcel, ParsedUsesPermissionImpl.CREATOR);
        this.implicitPermissions = forInternedStringSet.unparcel(parcel);
        Parcelling.BuiltIn.ForStringSet forStringSet = sForStringSet;
        this.upgradeKeySets = forStringSet.unparcel(parcel);
        this.keySetMapping = ParsingPackageUtils.readKeySetMapping(parcel);
        this.protectedBroadcasts = forInternedStringList2.unparcel(parcel);
        this.activities = ParsingUtils.createTypedInterfaceList(parcel, ParsedActivityImpl.CREATOR);
        this.apexSystemServices = ParsingUtils.createTypedInterfaceList(parcel, ParsedApexSystemServiceImpl.CREATOR);
        this.receivers = ParsingUtils.createTypedInterfaceList(parcel, ParsedActivityImpl.CREATOR);
        this.services = ParsingUtils.createTypedInterfaceList(parcel, ParsedServiceImpl.CREATOR);
        this.providers = ParsingUtils.createTypedInterfaceList(parcel, ParsedProviderImpl.CREATOR);
        this.attributions = ParsingUtils.createTypedInterfaceList(parcel, ParsedAttributionImpl.CREATOR);
        this.permissions = ParsingUtils.createTypedInterfaceList(parcel, ParsedPermissionImpl.CREATOR);
        this.permissionGroups = ParsingUtils.createTypedInterfaceList(parcel, ParsedPermissionGroupImpl.CREATOR);
        this.instrumentations = ParsingUtils.createTypedInterfaceList(parcel, ParsedInstrumentationImpl.CREATOR);
        this.preferredActivityFilters = sForIntentInfoPairs.unparcel(parcel);
        this.processes = parcel.readHashMap(ParsedProcessImpl.class.getClassLoader());
        this.metaData = parcel.readBundle(classLoader);
        this.volumeUuid = forInternedString2.unparcel(parcel);
        this.signingDetails = (SigningDetails) parcel.readParcelable(classLoader, SigningDetails.class);
        this.mPath = parcel.readString();
        this.queriesIntents = parcel.createTypedArrayList(Intent.CREATOR);
        this.queriesPackages = forInternedStringList2.unparcel(parcel);
        this.queriesProviders = forInternedStringSet.unparcel(parcel);
        this.appComponentFactory = parcel.readString();
        this.backupAgentName = parcel.readString();
        this.banner = parcel.readInt();
        this.category = parcel.readInt();
        this.classLoaderName = parcel.readString();
        this.className = parcel.readString();
        this.compatibleWidthLimitDp = parcel.readInt();
        this.descriptionRes = parcel.readInt();
        this.fullBackupContent = parcel.readInt();
        this.dataExtractionRules = parcel.readInt();
        this.iconRes = parcel.readInt();
        this.installLocation = parcel.readInt();
        this.labelRes = parcel.readInt();
        this.largestWidthLimitDp = parcel.readInt();
        this.logo = parcel.readInt();
        this.manageSpaceActivityName = parcel.readString();
        this.maxAspectRatio = parcel.readFloat();
        this.minAspectRatio = parcel.readFloat();
        this.minSdkVersion = parcel.readInt();
        this.maxSdkVersion = parcel.readInt();
        this.networkSecurityConfigRes = parcel.readInt();
        this.nonLocalizedLabel = parcel.readCharSequence();
        this.permission = parcel.readString();
        this.processName = parcel.readString();
        this.requiresSmallestWidthDp = parcel.readInt();
        this.roundIconRes = parcel.readInt();
        this.targetSandboxVersion = parcel.readInt();
        this.targetSdkVersion = parcel.readInt();
        this.taskAffinity = parcel.readString();
        this.theme = parcel.readInt();
        this.uiOptions = parcel.readInt();
        this.zygotePreloadName = parcel.readString();
        this.splitClassLoaderNames = parcel.createStringArray();
        this.splitCodePaths = parcel.createStringArray();
        this.splitDependencies = parcel.readSparseArray(classLoader);
        this.splitFlags = parcel.createIntArray();
        this.splitNames = parcel.createStringArray();
        this.splitRevisionCodes = parcel.createIntArray();
        Parcelling.BuiltIn.ForBoolean forBoolean2 = sForBoolean;
        this.resizeableActivity = forBoolean2.unparcel(parcel);
        this.autoRevokePermissions = parcel.readInt();
        this.mimeGroups = forInternedStringSet.unparcel(parcel);
        this.gwpAsanMode = parcel.readInt();
        this.minExtensionVersions = parcel.readSparseIntArray();
        this.mProperties = parcel.readHashMap(classLoader);
        this.memtagMode = parcel.readInt();
        this.nativeHeapZeroInitialized = parcel.readInt();
        this.requestRawExternalStorageAccess = forBoolean2.unparcel(parcel);
        this.mLocaleConfigRes = parcel.readInt();
        this.mKnownActivityEmbeddingCerts = forStringSet.unparcel(parcel);
        this.manifestPackageName = forInternedString2.unparcel(parcel);
        this.nativeLibraryDir = parcel.readString();
        this.nativeLibraryRootDir = parcel.readString();
        this.nativeLibraryRootRequiresIsa = parcel.readBoolean();
        this.primaryCpuAbi = forInternedString2.unparcel(parcel);
        this.secondaryCpuAbi = forInternedString2.unparcel(parcel);
        this.secondaryNativeLibraryDir = parcel.readString();
        this.uid = parcel.readInt();
        this.mBooleans = parcel.readLong();
        this.mBooleans2 = parcel.readLong();
        this.mAllowCrossUidActivitySwitchFromBelow = parcel.readBoolean();
        this.mIntentMatchingFlags = parcel.readInt();
        this.mAlternateLauncherIconResIds = parcel.createIntArray();
        this.mAlternateLauncherLabelResIds = parcel.createIntArray();
        this.mPageSizeAppCompatFlags = parcel.readInt();
        assignDerivedFields();
        assignDerivedFields2();
    }

    private void readFeatureFlagState(Parcel parcel) {
        Boolean bool;
        for (String str : parcel.createStringArray()) {
            int lastIndexOf = str.lastIndexOf(61);
            if (lastIndexOf >= 0 && lastIndexOf == str.length() - 2) {
                String substring = str.substring(0, lastIndexOf);
                char charAt = str.charAt(lastIndexOf + 1);
                if (charAt == '1') {
                    bool = Boolean.TRUE;
                } else if (charAt == '0') {
                    bool = Boolean.FALSE;
                } else if (charAt == '?') {
                    bool = null;
                }
                this.mFeatureFlagState.put(substring, bool);
            }
        }
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
    public PackageImpl setStub(boolean z) {
        setBoolean2(1L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setNativeLibraryRootRequiresIsa(boolean z) {
        this.nativeLibraryRootRequiresIsa = z;
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSystem(boolean z) {
        setBoolean(9007199254740992L, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl setUpdatableSystem(boolean z) {
        return setBoolean2(4L, z);
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setFactoryTest(boolean z) {
        setBoolean(18014398509481984L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setApex(boolean z) {
        setBoolean2(2L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSystemExt(boolean z) {
        setBoolean(72057594037927936L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setPrivileged(boolean z) {
        setBoolean(144115188075855872L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setOem(boolean z) {
        setBoolean(288230376151711744L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setVendor(boolean z) {
        setBoolean(576460752303423488L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setProduct(boolean z) {
        setBoolean(1152921504606846976L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setOdm(boolean z) {
        setBoolean(2305843009213693952L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setSignedWithPlatformKey(boolean z) {
        setBoolean(Context.BIND_EXTERNAL_SERVICE_LONG, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public PackageImpl setUid(int i) {
        this.uid = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public ParsingPackage setIntentMatchingFlags(int i) {
        this.mIntentMatchingFlags = i;
        return this;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getIntentMatchingFlags() {
        return this.mIntentMatchingFlags;
    }

    public String getBaseAppDataCredentialProtectedDirForSystemUser() {
        return this.mBaseAppDataCredentialProtectedDirForSystemUser;
    }

    public String getBaseAppDataDeviceProtectedDirForSystemUser() {
        return this.mBaseAppDataDeviceProtectedDirForSystemUser;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public PackageImpl addFeatureFlag(String str, Boolean bool) {
        this.mFeatureFlagState.put(str, bool);
        return this;
    }

    public Map<String, Boolean> getFeatureFlagState() {
        return this.mFeatureFlagState;
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

        public @interface Flags {
        }

        private Booleans() {
        }
    }

    private static class Booleans2 {
        private static final long APEX = 2;
        private static final long STUB = 1;
        private static final long UPDATABLE_SYSTEM = 4;

        public @interface Flags {
        }

        private Booleans2() {
        }
    }
}
