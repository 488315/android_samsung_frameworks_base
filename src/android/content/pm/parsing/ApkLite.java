package android.content.pm.parsing;

import android.content.pm.ArchivedPackageParcel;
import android.content.pm.PackageInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.SigningDetails;
import android.content.pm.VerifierInfo;
import com.android.internal.util.CollectionUtils;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class ApkLite {
    private final ArchivedPackageParcel mArchivedPackage;
    private final String mConfigForSplit;
    private final boolean mCoreApp;
    private final boolean mDebuggable;
    private final List<SharedLibraryInfo> mDeclaredLibraries;
    private final String mEmergencyInstaller;
    private final boolean mExtractNativeLibs;
    private final boolean mFeatureSplit;
    private final boolean mHasDeviceAdminReceiver;
    private final int mInstallLocation;
    private final boolean mIsSdkLibrary;
    private final boolean mIsStaticLibrary;
    private final boolean mIsolatedSplits;
    private final int mMinSdkVersion;
    private final boolean mMultiArch;
    private final boolean mOverlayIsStatic;
    private final int mOverlayPriority;
    private final String mPackageName;
    private final int mPageSizeCompat;
    private final String mPath;
    private final boolean mProfileableByShell;
    private final Set<String> mRequiredSplitTypes;
    private final String mRequiredSystemPropertyName;
    private final String mRequiredSystemPropertyValue;
    private final int mRevisionCode;
    private final int mRollbackDataPolicy;
    private final SigningDetails mSigningDetails;
    private final String mSplitName;
    private final boolean mSplitRequired;
    private final Set<String> mSplitTypes;
    private final String mTargetPackageName;
    private final int mTargetSdkVersion;
    private final boolean mUpdatableSystem;
    private final boolean mUse32bitAbi;
    private final boolean mUseEmbeddedDex;
    private final List<String> mUsesSdkLibraries;
    private final String[][] mUsesSdkLibrariesCertDigests;
    private final long[] mUsesSdkLibrariesVersionsMajor;
    private final String mUsesSplitName;
    private final List<String> mUsesStaticLibraries;
    private final String[][] mUsesStaticLibrariesCertDigests;
    private final long[] mUsesStaticLibrariesVersions;
    private final VerifierInfo[] mVerifiers;
    private final int mVersionCode;
    private final int mVersionCodeMajor;

    @Deprecated
    private void __metadata() {
    }

    public ApkLite(String str, String str2, String str3, boolean z, String str4, String str5, boolean z2, int i, int i2, int i3, int i4, List<VerifierInfo> list, SigningDetails signingDetails, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, String str6, boolean z11, int i5, String str7, String str8, int i6, int i7, int i8, Set<String> set, Set<String> set2, boolean z12, boolean z13, List<String> list2, long[] jArr, String[][] strArr, boolean z14, List<String> list3, long[] jArr2, String[][] strArr2, boolean z15, String str9, List<SharedLibraryInfo> list4, int i9) {
        this.mPath = str;
        this.mPackageName = str2;
        this.mSplitName = str3;
        this.mSplitTypes = set2;
        this.mFeatureSplit = z;
        this.mConfigForSplit = str4;
        this.mUsesSplitName = str5;
        this.mRequiredSplitTypes = set;
        this.mSplitRequired = z2 || hasAnyRequiredSplitTypes();
        this.mVersionCode = i;
        this.mVersionCodeMajor = i2;
        this.mRevisionCode = i3;
        this.mInstallLocation = i4;
        this.mVerifiers = (VerifierInfo[]) list.toArray(new VerifierInfo[list.size()]);
        this.mSigningDetails = signingDetails;
        this.mCoreApp = z3;
        this.mDebuggable = z4;
        this.mProfileableByShell = z5;
        this.mMultiArch = z6;
        this.mUse32bitAbi = z7;
        this.mUseEmbeddedDex = z8;
        this.mExtractNativeLibs = z9;
        this.mIsolatedSplits = z10;
        this.mTargetPackageName = str6;
        this.mOverlayIsStatic = z11;
        this.mOverlayPriority = i5;
        this.mRequiredSystemPropertyName = str7;
        this.mRequiredSystemPropertyValue = str8;
        this.mMinSdkVersion = i6;
        this.mTargetSdkVersion = i7;
        this.mRollbackDataPolicy = i8;
        this.mHasDeviceAdminReceiver = z12;
        this.mIsSdkLibrary = z13;
        this.mIsStaticLibrary = z14;
        this.mUsesSdkLibraries = list2;
        this.mUsesSdkLibrariesVersionsMajor = jArr;
        this.mUsesSdkLibrariesCertDigests = strArr;
        this.mUsesStaticLibraries = list3;
        this.mUsesStaticLibrariesVersions = jArr2;
        this.mUsesStaticLibrariesCertDigests = strArr2;
        this.mUpdatableSystem = z15;
        this.mEmergencyInstaller = str9;
        this.mArchivedPackage = null;
        this.mDeclaredLibraries = list4;
        this.mPageSizeCompat = i9;
    }

    public ApkLite(String str, ArchivedPackageParcel archivedPackageParcel) {
        this.mPath = str;
        this.mPackageName = archivedPackageParcel.packageName;
        this.mSplitName = null;
        this.mSplitTypes = null;
        this.mFeatureSplit = false;
        this.mConfigForSplit = null;
        this.mUsesSplitName = null;
        this.mRequiredSplitTypes = null;
        this.mSplitRequired = hasAnyRequiredSplitTypes();
        this.mVersionCode = archivedPackageParcel.versionCode;
        this.mVersionCodeMajor = archivedPackageParcel.versionCodeMajor;
        this.mRevisionCode = 0;
        this.mInstallLocation = -1;
        this.mVerifiers = new VerifierInfo[0];
        this.mSigningDetails = archivedPackageParcel.signingDetails;
        this.mCoreApp = false;
        this.mDebuggable = false;
        this.mProfileableByShell = false;
        this.mMultiArch = false;
        this.mUse32bitAbi = false;
        this.mUseEmbeddedDex = false;
        this.mExtractNativeLibs = false;
        this.mIsolatedSplits = false;
        this.mTargetPackageName = null;
        this.mOverlayIsStatic = false;
        this.mOverlayPriority = 0;
        this.mRequiredSystemPropertyName = null;
        this.mRequiredSystemPropertyValue = null;
        this.mMinSdkVersion = 1;
        this.mTargetSdkVersion = archivedPackageParcel.targetSdkVersion;
        this.mRollbackDataPolicy = 0;
        this.mHasDeviceAdminReceiver = false;
        this.mIsSdkLibrary = false;
        this.mIsStaticLibrary = false;
        this.mUsesSdkLibraries = Collections.EMPTY_LIST;
        this.mUsesSdkLibrariesVersionsMajor = null;
        this.mUsesSdkLibrariesCertDigests = null;
        this.mUsesStaticLibraries = Collections.EMPTY_LIST;
        this.mUsesStaticLibrariesVersions = null;
        this.mUsesStaticLibrariesCertDigests = null;
        this.mUpdatableSystem = true;
        this.mEmergencyInstaller = null;
        this.mArchivedPackage = archivedPackageParcel;
        this.mDeclaredLibraries = null;
        this.mPageSizeCompat = 0;
    }

    public long getLongVersionCode() {
        return PackageInfo.composeLongVersionCode(this.mVersionCodeMajor, this.mVersionCode);
    }

    private boolean hasAnyRequiredSplitTypes() {
        return !CollectionUtils.isEmpty(this.mRequiredSplitTypes);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getPath() {
        return this.mPath;
    }

    public String getSplitName() {
        return this.mSplitName;
    }

    public String getUsesSplitName() {
        return this.mUsesSplitName;
    }

    public String getConfigForSplit() {
        return this.mConfigForSplit;
    }

    public Set<String> getRequiredSplitTypes() {
        return this.mRequiredSplitTypes;
    }

    public Set<String> getSplitTypes() {
        return this.mSplitTypes;
    }

    public int getVersionCodeMajor() {
        return this.mVersionCodeMajor;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int getRevisionCode() {
        return this.mRevisionCode;
    }

    public int getInstallLocation() {
        return this.mInstallLocation;
    }

    public int getMinSdkVersion() {
        return this.mMinSdkVersion;
    }

    public int getTargetSdkVersion() {
        return this.mTargetSdkVersion;
    }

    public VerifierInfo[] getVerifiers() {
        return this.mVerifiers;
    }

    public SigningDetails getSigningDetails() {
        return this.mSigningDetails;
    }

    public boolean isFeatureSplit() {
        return this.mFeatureSplit;
    }

    public boolean isIsolatedSplits() {
        return this.mIsolatedSplits;
    }

    public boolean isSplitRequired() {
        return this.mSplitRequired;
    }

    public boolean isCoreApp() {
        return this.mCoreApp;
    }

    public boolean isDebuggable() {
        return this.mDebuggable;
    }

    public boolean isProfileableByShell() {
        return this.mProfileableByShell;
    }

    public boolean isMultiArch() {
        return this.mMultiArch;
    }

    public boolean isUse32bitAbi() {
        return this.mUse32bitAbi;
    }

    public boolean isExtractNativeLibs() {
        return this.mExtractNativeLibs;
    }

    public boolean isUseEmbeddedDex() {
        return this.mUseEmbeddedDex;
    }

    public String getTargetPackageName() {
        return this.mTargetPackageName;
    }

    public boolean isOverlayIsStatic() {
        return this.mOverlayIsStatic;
    }

    public int getOverlayPriority() {
        return this.mOverlayPriority;
    }

    public String getRequiredSystemPropertyName() {
        return this.mRequiredSystemPropertyName;
    }

    public String getRequiredSystemPropertyValue() {
        return this.mRequiredSystemPropertyValue;
    }

    public int getRollbackDataPolicy() {
        return this.mRollbackDataPolicy;
    }

    public boolean isHasDeviceAdminReceiver() {
        return this.mHasDeviceAdminReceiver;
    }

    public boolean isIsSdkLibrary() {
        return this.mIsSdkLibrary;
    }

    public boolean isIsStaticLibrary() {
        return this.mIsStaticLibrary;
    }

    public List<String> getUsesSdkLibraries() {
        return this.mUsesSdkLibraries;
    }

    public long[] getUsesSdkLibrariesVersionsMajor() {
        return this.mUsesSdkLibrariesVersionsMajor;
    }

    public String[][] getUsesSdkLibrariesCertDigests() {
        return this.mUsesSdkLibrariesCertDigests;
    }

    public List<String> getUsesStaticLibraries() {
        return this.mUsesStaticLibraries;
    }

    public long[] getUsesStaticLibrariesVersions() {
        return this.mUsesStaticLibrariesVersions;
    }

    public String[][] getUsesStaticLibrariesCertDigests() {
        return this.mUsesStaticLibrariesCertDigests;
    }

    public boolean isUpdatableSystem() {
        return this.mUpdatableSystem;
    }

    public String getEmergencyInstaller() {
        return this.mEmergencyInstaller;
    }

    public List<SharedLibraryInfo> getDeclaredLibraries() {
        return this.mDeclaredLibraries;
    }

    public ArchivedPackageParcel getArchivedPackage() {
        return this.mArchivedPackage;
    }

    public int getPageSizeCompat() {
        return this.mPageSizeCompat;
    }
}
