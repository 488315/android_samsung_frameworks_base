package com.android.server.pm;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.SigningDetails;
import android.content.pm.SigningInfo;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.CollectionUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.ArchiveState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUnserialized;
import com.android.server.pm.pkg.PackageUserState;
import com.android.server.pm.pkg.PackageUserStateImpl;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;

import libcore.util.EmptyArray;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class PackageSetting extends SettingBase implements PackageStateInternal {
    public int categoryOverride;
    public InstallSource installSource;
    public PackageKeySetData keySetData;
    public long lastUpdateTime;
    public String legacyNativeLibraryPath;
    public int mAppId;
    public String mAppMetadataFilePath;
    public int mAppMetadataSource;
    public int mBaseRevisionCode;
    public int mBooleans;
    public String mCpuAbiOverride;
    public UUID mDomainSetId;
    public long mLastModifiedTime;
    public long mLoadingCompletedTime;
    public float mLoadingProgress;
    public String mName;
    public LinkedHashSet mOldPaths;
    public File mPath;
    public String mPathString;
    public String mPrimaryCpuAbi;
    public String mRealName;
    public byte[] mRestrictUpdateHash;
    public String mSecondaryCpuAbi;
    public int mSharedUserAppId;
    public final SnapshotCache mSnapshot;
    public String[] mSplitNames;
    public int[] mSplitRevisionCodes;
    public int mTargetSdkVersion;
    public final SparseArray mUserStates;
    public Map mimeGroups;
    public AndroidPackageInternal pkg;
    public final PackageStateUnserialized pkgState;
    public PackageSignatures signatures;
    public String[] usesSdkLibraries;
    public boolean[] usesSdkLibrariesOptional;
    public long[] usesSdkLibrariesVersionsMajor;
    public String[] usesStaticLibraries;
    public long[] usesStaticLibrariesVersions;
    public long versionCode;
    public String volumeUuid;

    /* renamed from: com.android.server.pm.PackageSetting$1, reason: invalid class name */
    public final class AnonymousClass1 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            return new PackageSetting((PackageSetting) this.mSource, true);
        }
    }

    public PackageSetting(PackageSetting packageSetting, boolean z) {
        super(packageSetting);
        this.keySetData = new PackageKeySetData();
        this.mUserStates = new SparseArray();
        this.categoryOverride = -1;
        this.pkgState = new PackageStateUnserialized(this);
        this.mAppMetadataSource = 0;
        copyPackageSetting(packageSetting, z);
        if (z) {
            this.mSnapshot = new SnapshotCache.Auto();
        } else {
            this.mSnapshot = new AnonymousClass1(this, this, null);
        }
    }

    public PackageSetting(String str, String str2, File file, int i, int i2, UUID uuid) {
        super(i, i2);
        this.keySetData = new PackageKeySetData();
        this.mUserStates = new SparseArray();
        this.categoryOverride = -1;
        this.pkgState = new PackageStateUnserialized(this);
        this.mAppMetadataSource = 0;
        this.mName = str;
        this.mRealName = str2;
        this.mPath = file;
        this.mPathString = file.toString();
        this.signatures = new PackageSignatures();
        this.installSource = InstallSource.EMPTY;
        this.mDomainSetId = uuid;
        this.mSnapshot = new AnonymousClass1(this, this, null);
    }

    public PackageSetting addOldPath(File file) {
        if (this.mOldPaths == null) {
            this.mOldPaths = new LinkedHashSet();
        }
        this.mOldPaths.add(file);
        onChanged$2();
        return this;
    }

    public final void copyMimeGroups(Map map) {
        if (map == null) {
            this.mimeGroups = null;
            return;
        }
        this.mimeGroups = new ArrayMap(map.size());
        for (String str : map.keySet()) {
            Set set = (Set) map.get(str);
            if (set != null) {
                this.mimeGroups.put(str, new ArraySet(set));
            } else {
                this.mimeGroups.put(str, new ArraySet());
            }
        }
    }

    public final void copyPackageSetting(PackageSetting packageSetting, boolean z) {
        this.mPkgFlags = packageSetting.mPkgFlags;
        this.mPkgPrivateFlags = packageSetting.mPkgPrivateFlags;
        this.mLegacyPermissionsState.copyFrom(packageSetting.mLegacyPermissionsState);
        onChanged$2();
        this.mBooleans = packageSetting.mBooleans;
        this.mSharedUserAppId = packageSetting.mSharedUserAppId;
        this.mLoadingProgress = packageSetting.mLoadingProgress;
        this.mLoadingCompletedTime = packageSetting.mLoadingCompletedTime;
        this.legacyNativeLibraryPath = packageSetting.legacyNativeLibraryPath;
        this.mName = packageSetting.mName;
        this.mRealName = packageSetting.mRealName;
        this.mAppId = packageSetting.mAppId;
        this.pkg = packageSetting.pkg;
        this.mPath = packageSetting.mPath;
        this.mPathString = packageSetting.mPathString;
        this.mOldPaths =
                packageSetting.mOldPaths == null
                        ? null
                        : new LinkedHashSet(packageSetting.mOldPaths);
        this.mPrimaryCpuAbi = packageSetting.mPrimaryCpuAbi;
        this.mSecondaryCpuAbi = packageSetting.mSecondaryCpuAbi;
        this.mCpuAbiOverride = packageSetting.mCpuAbiOverride;
        this.mLastModifiedTime = packageSetting.mLastModifiedTime;
        this.lastUpdateTime = packageSetting.lastUpdateTime;
        this.versionCode = packageSetting.versionCode;
        this.signatures = packageSetting.signatures;
        this.keySetData = new PackageKeySetData(packageSetting.keySetData);
        this.installSource = packageSetting.installSource;
        this.volumeUuid = packageSetting.volumeUuid;
        this.categoryOverride = packageSetting.categoryOverride;
        this.mDomainSetId = packageSetting.mDomainSetId;
        this.mAppMetadataFilePath = packageSetting.mAppMetadataFilePath;
        this.mAppMetadataSource = packageSetting.mAppMetadataSource;
        this.mTargetSdkVersion = packageSetting.mTargetSdkVersion;
        byte[] bArr = packageSetting.mRestrictUpdateHash;
        this.mRestrictUpdateHash = bArr == null ? null : (byte[]) bArr.clone();
        this.mBaseRevisionCode = packageSetting.mBaseRevisionCode;
        String[] strArr = packageSetting.mSplitNames;
        this.mSplitNames = strArr != null ? (String[]) Arrays.copyOf(strArr, strArr.length) : null;
        int[] iArr = packageSetting.mSplitRevisionCodes;
        this.mSplitRevisionCodes = iArr != null ? Arrays.copyOf(iArr, iArr.length) : null;
        String[] strArr2 = packageSetting.usesSdkLibraries;
        this.usesSdkLibraries =
                strArr2 != null ? (String[]) Arrays.copyOf(strArr2, strArr2.length) : null;
        long[] jArr = packageSetting.usesSdkLibrariesVersionsMajor;
        this.usesSdkLibrariesVersionsMajor = jArr != null ? Arrays.copyOf(jArr, jArr.length) : null;
        boolean[] zArr = packageSetting.usesSdkLibrariesOptional;
        this.usesSdkLibrariesOptional = zArr != null ? Arrays.copyOf(zArr, zArr.length) : null;
        String[] strArr3 = packageSetting.usesStaticLibraries;
        this.usesStaticLibraries =
                strArr3 != null ? (String[]) Arrays.copyOf(strArr3, strArr3.length) : null;
        long[] jArr2 = packageSetting.usesStaticLibrariesVersions;
        this.usesStaticLibrariesVersions =
                jArr2 != null ? Arrays.copyOf(jArr2, jArr2.length) : null;
        this.mUserStates.clear();
        for (int i = 0; i < packageSetting.mUserStates.size(); i++) {
            if (z) {
                this.mUserStates.put(
                        packageSetting.mUserStates.keyAt(i),
                        (PackageUserStateImpl)
                                ((PackageUserStateImpl) packageSetting.mUserStates.valueAt(i))
                                        .mSnapshot.snapshot());
            } else {
                PackageUserStateImpl packageUserStateImpl =
                        (PackageUserStateImpl) packageSetting.mUserStates.valueAt(i);
                packageUserStateImpl.mWatchable = this;
                this.mUserStates.put(packageSetting.mUserStates.keyAt(i), packageUserStateImpl);
            }
        }
        copyMimeGroups(packageSetting.mimeGroups);
        PackageStateUnserialized packageStateUnserialized = this.pkgState;
        PackageStateUnserialized packageStateUnserialized2 = packageSetting.pkgState;
        packageStateUnserialized.getClass();
        packageStateUnserialized.hiddenUntilInstalled =
                packageStateUnserialized2.hiddenUntilInstalled;
        if (!packageStateUnserialized2.usesLibraryInfos.isEmpty()) {
            packageStateUnserialized.usesLibraryInfos =
                    new ArrayList(packageStateUnserialized2.usesLibraryInfos);
        }
        if (!packageStateUnserialized2.usesLibraryFiles.isEmpty()) {
            packageStateUnserialized.usesLibraryFiles =
                    new ArrayList(packageStateUnserialized2.usesLibraryFiles);
        }
        packageStateUnserialized.updatedSystemApp = packageStateUnserialized2.updatedSystemApp;
        packageStateUnserialized.apkInUpdatedApex = packageStateUnserialized2.apkInUpdatedApex;
        packageStateUnserialized.lastPackageUsageTimeInMills =
                packageStateUnserialized2.lastPackageUsageTimeInMills;
        packageStateUnserialized.overrideSeInfo = packageStateUnserialized2.overrideSeInfo;
        packageStateUnserialized.seInfo = packageStateUnserialized2.seInfo;
        packageStateUnserialized.mApexModuleName = packageStateUnserialized2.mApexModuleName;
        packageStateUnserialized.mPackageSetting.onChanged$2();
        onChanged$2();
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final AndroidPackage getAndroidPackage() {
        return this.pkg;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final String getApexModuleName() {
        return this.pkgState.mApexModuleName;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final int getAppId() {
        return this.mAppId;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final String getAppMetadataFilePath() {
        return this.mAppMetadataFilePath;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final int getAppMetadataSource() {
        return this.mAppMetadataSource;
    }

    public final boolean getBoolean(int i) {
        return (this.mBooleans & i) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final int getCategoryOverride() {
        return this.categoryOverride;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final String getCpuAbiOverride() {
        return this.mCpuAbiOverride;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final UUID getDomainSetId() {
        return this.mDomainSetId;
    }

    public final int getEnabled(int i) {
        return readUserState(i).getEnabledState();
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final int getHiddenApiEnforcementPolicy() {
        return AndroidPackageUtils.getHiddenApiEnforcementPolicy(this.pkg, this);
    }

    public final int getInstallReason(int i) {
        return readUserState(i).getInstallReason();
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final InstallSource getInstallSource() {
        return this.installSource;
    }

    public final boolean getInstalled(int i) {
        return readUserState(i).isInstalled();
    }

    public final boolean getInstantApp(int i) {
        return readUserState(i).isInstantApp();
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final PackageKeySetData getKeySetData() {
        return this.keySetData;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final long getLastModifiedTime() {
        return this.mLastModifiedTime;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final long[] getLastPackageUsageTime() {
        return this.pkgState.getLastPackageUsageTimeInMills();
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    @Override // com.android.server.pm.SettingBase, com.android.server.pm.pkg.PackageStateInternal
    public final LegacyPermissionState getLegacyPermissionState() {
        return this.mLegacyPermissionsState;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final long getLoadingCompletedTime() {
        return this.mLoadingCompletedTime;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final float getLoadingProgress() {
        return this.mLoadingProgress;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final Map getMimeGroups() {
        return CollectionUtils.isEmpty(this.mimeGroups)
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(this.mimeGroups);
    }

    public final int[] getNotInstalledUserIds() {
        int size = this.mUserStates.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (!((PackageUserStateImpl) this.mUserStates.valueAt(i2)).getBoolean$1(1)) {
                i++;
            }
        }
        if (i == 0) {
            return EmptyArray.INT;
        }
        int[] iArr = new int[i];
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            if (!((PackageUserStateImpl) this.mUserStates.valueAt(i4)).getBoolean$1(1)) {
                iArr[i3] = this.mUserStates.keyAt(i4);
                i3++;
            }
        }
        return iArr;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final Set getOldPaths() {
        return this.mOldPaths;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final String getPackageName() {
        return this.mName;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final File getPath() {
        return this.mPath;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final String getPathString() {
        return this.mPathString;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final AndroidPackageInternal getPkg() {
        return this.pkg;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final String getPrimaryCpuAbi() {
        AndroidPackageHidden androidPackageHidden;
        return (!TextUtils.isEmpty(this.mPrimaryCpuAbi)
                        || (androidPackageHidden = this.pkg) == null)
                ? this.mPrimaryCpuAbi
                : androidPackageHidden.getPrimaryCpuAbi();
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final String getPrimaryCpuAbiLegacy() {
        return this.mPrimaryCpuAbi;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final String getRealName() {
        return this.mRealName;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final byte[] getRestrictUpdateHash() {
        return this.mRestrictUpdateHash;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final String getSeInfo() {
        PackageStateUnserialized packageStateUnserialized = this.pkgState;
        String str = packageStateUnserialized.overrideSeInfo;
        return !TextUtils.isEmpty(str) ? str : packageStateUnserialized.seInfo;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final String getSecondaryCpuAbi() {
        AndroidPackageHidden androidPackageHidden;
        return (!TextUtils.isEmpty(this.mSecondaryCpuAbi)
                        || (androidPackageHidden = this.pkg) == null)
                ? this.mSecondaryCpuAbi
                : androidPackageHidden.getSecondaryCpuAbi();
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final String getSecondaryCpuAbiLegacy() {
        return this.mSecondaryCpuAbi;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final List getSharedLibraryDependencies() {
        return Collections.unmodifiableList(this.pkgState.usesLibraryInfos);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final int getSharedUserAppId() {
        return this.mSharedUserAppId;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final SigningDetails getSigningDetails() {
        return this.signatures.mSigningDetails;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final SigningInfo getSigningInfo() {
        return new SigningInfo(this.signatures.mSigningDetails);
    }

    public final String[] getSplitNames() {
        AndroidPackageInternal androidPackageInternal = this.pkg;
        if (androidPackageInternal != null) {
            return androidPackageInternal.getSplitNames();
        }
        String[] strArr = this.mSplitNames;
        return strArr == null ? EmptyArray.STRING : strArr;
    }

    public final int[] getSplitRevisionCodes() {
        AndroidPackageInternal androidPackageInternal = this.pkg;
        if (androidPackageInternal != null) {
            return androidPackageInternal.getSplitRevisionCodes();
        }
        int[] iArr = this.mSplitRevisionCodes;
        return iArr == null ? EmptyArray.INT : iArr;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final PackageUserState getStateForUser(UserHandle userHandle) {
        PackageUserState packageUserState =
                (PackageUserState) this.mUserStates.get(userHandle.getIdentifier());
        return packageUserState == null ? PackageUserState.DEFAULT : packageUserState;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final int getTargetSdkVersion() {
        return this.mTargetSdkVersion;
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final PackageStateUnserialized getTransientState() {
        return this.pkgState;
    }

    public final int getUninstallReason(int i) {
        return readUserState(i).getUninstallReason();
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal,
              // com.android.server.pm.pkg.PackageState
    public final SparseArray getUserStates() {
        return this.mUserStates;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final List getUsesLibraryFiles() {
        return Collections.unmodifiableList(this.pkgState.usesLibraryFiles);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final String[] getUsesSdkLibraries() {
        String[] strArr = this.usesSdkLibraries;
        return strArr == null ? EmptyArray.STRING : strArr;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean[] getUsesSdkLibrariesOptional() {
        boolean[] zArr = this.usesSdkLibrariesOptional;
        return zArr == null ? EmptyArray.BOOLEAN : zArr;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final long[] getUsesSdkLibrariesVersionsMajor() {
        long[] jArr = this.usesSdkLibrariesVersionsMajor;
        return jArr == null ? EmptyArray.LONG : jArr;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final String[] getUsesStaticLibraries() {
        String[] strArr = this.usesStaticLibraries;
        return strArr == null ? EmptyArray.STRING : strArr;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final long[] getUsesStaticLibrariesVersions() {
        long[] jArr = this.usesStaticLibrariesVersions;
        return jArr == null ? EmptyArray.LONG : jArr;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final long getVersionCode() {
        return this.versionCode;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final String getVolumeUuid() {
        return this.volumeUuid;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean hasSharedUser() {
        return this.mSharedUserAppId > 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isApex() {
        AndroidPackageInternal androidPackageInternal = this.pkg;
        return androidPackageInternal != null && androidPackageInternal.isApex();
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isApkInUpdatedApex() {
        return this.pkgState.apkInUpdatedApex;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isDebuggable() {
        return getBoolean(32);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isDefaultToDeviceProtectedStorage() {
        return (this.mPkgPrivateFlags & 32) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isExternalStorage() {
        return (this.mPkgFlags & 262144) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isForceQueryableOverride() {
        return getBoolean(4);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isHiddenUntilInstalled() {
        return this.pkgState.hiddenUntilInstalled;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isInstallPermissionsFixed() {
        return getBoolean(1);
    }

    @Override // com.android.server.pm.pkg.PackageStateInternal
    public final boolean isLoading() {
        return Math.abs(1.0f - this.mLoadingProgress) >= 1.0E-8f;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isOdm() {
        return (this.mPkgPrivateFlags & 1073741824) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isOem() {
        return (this.mPkgPrivateFlags & 131072) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isPendingRestore() {
        return getBoolean(16);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isPersistent() {
        return (this.mPkgFlags & 8) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isPrivileged() {
        return (this.mPkgPrivateFlags & 8) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isProduct() {
        return (this.mPkgPrivateFlags & 524288) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isRequiredForSystemUser() {
        return (this.mPkgPrivateFlags & 512) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isScannedAsStoppedSystemApp() {
        return getBoolean(8);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isSystem() {
        return (this.mPkgFlags & 1) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isSystemExt() {
        return (this.mPkgPrivateFlags & 2097152) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isUpdateAvailable() {
        return getBoolean(2);
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isUpdatedSystemApp() {
        return this.pkgState.updatedSystemApp;
    }

    @Override // com.android.server.pm.pkg.PackageState
    public final boolean isVendor() {
        return (this.mPkgPrivateFlags & 262144) != 0;
    }

    public PackageUserStateImpl modifyUserState(int i) {
        PackageUserStateImpl packageUserStateImpl = (PackageUserStateImpl) this.mUserStates.get(i);
        if (packageUserStateImpl != null) {
            return packageUserStateImpl;
        }
        PackageUserStateImpl packageUserStateImpl2 = new PackageUserStateImpl(this);
        this.mUserStates.put(i, packageUserStateImpl2);
        onChanged$2();
        return packageUserStateImpl2;
    }

    public final PackageUserStateImpl modifyUserStateComponents(int i, boolean z, boolean z2) {
        boolean z3;
        PackageUserStateImpl modifyUserState = modifyUserState(i);
        boolean z4 = true;
        if (z && modifyUserState.mDisabledComponentsWatched == null) {
            modifyUserState.setDisabledComponents(new ArraySet(1));
            z3 = true;
        } else {
            z3 = false;
        }
        if (z2 && modifyUserState.mEnabledComponentsWatched == null) {
            modifyUserState.setEnabledComponents(new ArraySet(1));
        } else {
            z4 = z3;
        }
        if (z4) {
            onChanged$2();
        }
        return modifyUserState;
    }

    public boolean overrideNonLocalizedLabelAndIcon(
            ComponentName componentName, String str, Integer num, int i) {
        boolean overrideLabelAndIcon =
                modifyUserState(i).overrideLabelAndIcon(componentName, str, num);
        onChanged$2();
        return overrideLabelAndIcon;
    }

    public final int[] queryInstalledUsers(int[] iArr, boolean z) {
        int i = 0;
        for (int i2 : iArr) {
            if (getInstalled(i2) == z) {
                i++;
            }
        }
        int[] iArr2 = new int[i];
        int i3 = 0;
        for (int i4 : iArr) {
            if (getInstalled(i4) == z) {
                iArr2[i3] = i4;
                i3++;
            }
        }
        return iArr2;
    }

    public final int[] queryUsersInstalledOrHasData(int[] iArr) {
        int i = 0;
        for (int i2 : iArr) {
            if (getInstalled(i2) || readUserState(i2).dataExists()) {
                i++;
            }
        }
        int[] iArr2 = new int[i];
        int i3 = 0;
        for (int i4 : iArr) {
            if (getInstalled(i4) || readUserState(i4).dataExists()) {
                iArr2[i3] = i4;
                i3++;
            }
        }
        return iArr2;
    }

    public final PackageUserStateInternal readUserState(int i) {
        PackageUserStateInternal packageUserStateInternal =
                (PackageUserStateInternal) this.mUserStates.get(i);
        return packageUserStateInternal == null
                ? PackageUserStateInternal.DEFAULT
                : packageUserStateInternal;
    }

    public PackageSetting removeOldPath(File file) {
        LinkedHashSet linkedHashSet;
        if (file != null
                && (linkedHashSet = this.mOldPaths) != null
                && linkedHashSet.remove(file)) {
            onChanged$2();
        }
        return this;
    }

    public final boolean restoreComponentLPw(int i, String str) {
        PackageUserStateImpl modifyUserStateComponents = modifyUserStateComponents(i, true, true);
        WatchedArraySet watchedArraySet = modifyUserStateComponents.mDisabledComponentsWatched;
        boolean remove = watchedArraySet != null ? watchedArraySet.remove(str) : false;
        WatchedArraySet watchedArraySet2 = modifyUserStateComponents.mEnabledComponentsWatched;
        boolean remove2 =
                remove | (watchedArraySet2 != null ? watchedArraySet2.remove(str) : false);
        if (remove2) {
            onChanged$2();
        }
        return remove2;
    }

    public final void restoreComponentSettings(int i) {
        PackageUserStateImpl modifyUserStateComponents = modifyUserStateComponents(i, true, true);
        WatchedArraySet watchedArraySet = modifyUserStateComponents.mEnabledComponentsWatched;
        WatchedArraySet watchedArraySet2 = modifyUserStateComponents.mDisabledComponentsWatched;
        boolean z = false;
        for (int size = watchedArraySet.mStorage.size() - 1; size >= 0; size--) {
            if (!AndroidPackageUtils.hasComponentClassName(
                    this.pkg, (String) watchedArraySet.mStorage.valueAt(size))) {
                Object removeAt = watchedArraySet.mStorage.removeAt(size);
                if (watchedArraySet.mWatching
                        && (removeAt instanceof Watchable)
                        && !watchedArraySet.mStorage.contains(removeAt)) {
                    ((Watchable) removeAt).unregisterObserver(watchedArraySet.mObserver);
                }
                watchedArraySet.dispatchChange(watchedArraySet);
                z = true;
            }
        }
        for (int size2 = watchedArraySet2.mStorage.size() - 1; size2 >= 0; size2--) {
            if (!AndroidPackageUtils.hasComponentClassName(
                    this.pkg, (String) watchedArraySet2.mStorage.valueAt(size2))) {
                Object removeAt2 = watchedArraySet2.mStorage.removeAt(size2);
                if (watchedArraySet2.mWatching
                        && (removeAt2 instanceof Watchable)
                        && !watchedArraySet2.mStorage.contains(removeAt2)) {
                    ((Watchable) removeAt2).unregisterObserver(watchedArraySet2.mObserver);
                }
                watchedArraySet2.dispatchChange(watchedArraySet2);
                z = true;
            }
        }
        if (z) {
            onChanged$2();
        }
    }

    public final void setAppId(int i) {
        this.mAppId = i;
        onChanged$2();
    }

    public final void setAppMetadataFilePath(String str) {
        this.mAppMetadataFilePath = str;
        onChanged$2();
    }

    public final void setAppMetadataSource(int i) {
        this.mAppMetadataSource = i;
        onChanged$2();
    }

    public final void setBoolean(int i, boolean z) {
        if (z) {
            this.mBooleans = i | this.mBooleans;
        } else {
            this.mBooleans = (~i) & this.mBooleans;
        }
    }

    public final void setEnabled(int i, int i2, String str) {
        PackageUserStateImpl modifyUserState = modifyUserState(i2);
        modifyUserState.mEnabledState = i;
        modifyUserState.onChanged$4();
        modifyUserState.mLastDisableAppCaller = str;
        modifyUserState.onChanged$4();
        onChanged$2();
    }

    public final void setFirstInstallTime(int i, long j) {
        if (i == -1) {
            int size = this.mUserStates.size();
            for (int i2 = 0; i2 < size; i2++) {
                PackageUserStateImpl packageUserStateImpl =
                        (PackageUserStateImpl) this.mUserStates.valueAt(i2);
                packageUserStateImpl.mFirstInstallTimeMillis = j;
                packageUserStateImpl.onChanged$4();
            }
        } else {
            PackageUserStateImpl modifyUserState = modifyUserState(i);
            modifyUserState.mFirstInstallTimeMillis = j;
            modifyUserState.onChanged$4();
        }
        onChanged$2();
    }

    public final void setInstalled(int i, boolean z) {
        PackageUserStateImpl modifyUserState = modifyUserState(i);
        modifyUserState.setBoolean$1(1, z);
        modifyUserState.onChanged$4();
        onChanged$2();
    }

    public final void setLastUpdateTime(long j) {
        this.lastUpdateTime = j;
        onChanged$2();
    }

    public PackageSetting setPath(File file) {
        this.mPath = file;
        this.mPathString = file.toString();
        onChanged$2();
        return this;
    }

    public final void setSharedUserAppId(int i) {
        this.mSharedUserAppId = i;
        onChanged$2();
    }

    public final void setUninstallReason(int i, int i2) {
        PackageUserStateImpl modifyUserState = modifyUserState(i2);
        modifyUserState.mUninstallReason = i;
        AnnotationValidations.validate(PackageManager.UninstallReason.class, (Annotation) null, i);
        modifyUserState.onChanged$4();
        onChanged$2();
    }

    public final void setUpdateOwnerPackage(String str) {
        this.installSource = this.installSource.setUpdateOwnerPackageName(str);
        onChanged$2();
    }

    public final void setUserState(
            int i,
            long j,
            long j2,
            int i2,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            int i3,
            ArrayMap arrayMap,
            boolean z5,
            boolean z6,
            String str,
            ArraySet arraySet,
            ArraySet arraySet2,
            int i4,
            int i5,
            String str2,
            String str3,
            long j3,
            int i6,
            ArchiveState archiveState) {
        PackageUserStateImpl modifyUserState = modifyUserState(i);
        if (arrayMap == null) {
            modifyUserState.getClass();
        } else {
            if (modifyUserState.mSuspendParams == null) {
                modifyUserState.mSuspendParams = new WatchedArrayMap(0);
                modifyUserState.registerObserver(modifyUserState.mSnapshot);
            }
            modifyUserState.mSuspendParams.clear();
            modifyUserState.mSuspendParams.putAll(arrayMap);
            modifyUserState.onChanged$4();
        }
        modifyUserState.mCeDataInode = j;
        modifyUserState.onChanged$4();
        modifyUserState.mDeDataInode = j2;
        modifyUserState.onChanged$4();
        modifyUserState.mEnabledState = i2;
        modifyUserState.onChanged$4();
        modifyUserState.setBoolean$1(1, z);
        modifyUserState.onChanged$4();
        modifyUserState.setBoolean$1(2, z2);
        modifyUserState.onChanged$4();
        modifyUserState.setBoolean$1(4, z3);
        modifyUserState.onChanged$4();
        modifyUserState.setBoolean$1(8, z4);
        modifyUserState.onChanged$4();
        modifyUserState.mDistractionFlags = i3;
        modifyUserState.onChanged$4();
        modifyUserState.mLastDisableAppCaller = str;
        modifyUserState.onChanged$4();
        modifyUserState.setEnabledComponents(arraySet);
        modifyUserState.setDisabledComponents(arraySet2);
        modifyUserState.mInstallReason = i4;
        AnnotationValidations.validate(PackageManager.InstallReason.class, (Annotation) null, i4);
        modifyUserState.onChanged$4();
        modifyUserState.mUninstallReason = i5;
        AnnotationValidations.validate(PackageManager.UninstallReason.class, (Annotation) null, i5);
        modifyUserState.onChanged$4();
        modifyUserState.setBoolean$1(16, z5);
        modifyUserState.onChanged$4();
        modifyUserState.setBoolean$1(32, z6);
        modifyUserState.onChanged$4();
        modifyUserState.mHarmfulAppWarning = str2;
        modifyUserState.onChanged$4();
        modifyUserState.mSplashScreenTheme = str3;
        modifyUserState.onChanged$4();
        modifyUserState.mFirstInstallTimeMillis = j3;
        modifyUserState.onChanged$4();
        modifyUserState.mMinAspectRatio = i6;
        AnnotationValidations.validate(
                PackageManager.UserMinAspectRatio.class, (Annotation) null, i6);
        modifyUserState.onChanged$4();
        modifyUserState.mArchiveState = archiveState;
        modifyUserState.onChanged$4();
        onChanged$2();
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (PackageSetting) this.mSnapshot.snapshot();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PackageSetting{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ");
        sb.append(this.mName);
        sb.append("/");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mAppId, sb, "}");
    }

    public final void updateMimeGroups(Set set) {
        if (set == null) {
            this.mimeGroups = null;
            return;
        }
        if (this.mimeGroups == null) {
            this.mimeGroups = Collections.emptyMap();
        }
        ArrayMap arrayMap = new ArrayMap(set.size());
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (this.mimeGroups.containsKey(str)) {
                arrayMap.put(str, (Set) this.mimeGroups.get(str));
            } else {
                arrayMap.put(str, new ArraySet());
            }
        }
        onChanged$2();
        this.mimeGroups = arrayMap;
    }
}
