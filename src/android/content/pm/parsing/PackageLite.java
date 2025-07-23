package android.content.pm.parsing;

import android.content.pm.ArchivedPackageParcel;
import android.content.pm.PackageInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.SigningDetails;
import android.content.pm.VerifierInfo;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class PackageLite {
    private final ArchivedPackageParcel mArchivedPackage;
    private final String mBaseApkPath;
    private final Set<String> mBaseRequiredSplitTypes;
    private final int mBaseRevisionCode;
    private final String[] mConfigForSplit;
    private final boolean mCoreApp;
    private final boolean mDebuggable;
    private final List<SharedLibraryInfo> mDeclaredLibraries;
    private final boolean mExtractNativeLibs;
    private final int mInstallLocation;
    private final boolean[] mIsFeatureSplits;
    private final boolean mIsSdkLibrary;
    private final boolean mIsStaticLibrary;
    private final boolean mIsolatedSplits;
    private final boolean mMultiArch;
    private final String mPackageName;
    private final int mPageSizeCompat;
    private final String mPath;
    private final boolean mProfileableByShell;
    private final Set<String>[] mRequiredSplitTypes;
    private final SigningDetails mSigningDetails;
    private final String[] mSplitApkPaths;
    private final String[] mSplitNames;
    private final boolean mSplitRequired;
    private final int[] mSplitRevisionCodes;
    private final Set<String>[] mSplitTypes;
    private final int mTargetSdk;
    private final boolean mUse32bitAbi;
    private final boolean mUseEmbeddedDex;
    private final List<String> mUsesSdkLibraries;
    private final String[][] mUsesSdkLibrariesCertDigests;
    private final long[] mUsesSdkLibrariesVersionsMajor;
    private final String[] mUsesSplitNames;
    private final List<String> mUsesStaticLibraries;
    private final String[][] mUsesStaticLibrariesCertDigests;
    private final long[] mUsesStaticLibrariesVersions;
    private final VerifierInfo[] mVerifiers;
    private final int mVersionCode;
    private final int mVersionCodeMajor;

    @Deprecated
    private void __metadata() {
    }

    public PackageLite(String str, String str2, ApkLite apkLite, String[] strArr, boolean[] zArr, String[] strArr2, String[] strArr3, String[] strArr4, int[] iArr, int i, Set<String>[] setArr, Set<String>[] setArr2) {
        this.mPath = str;
        this.mBaseApkPath = str2;
        this.mPackageName = apkLite.getPackageName();
        this.mVersionCode = apkLite.getVersionCode();
        this.mVersionCodeMajor = apkLite.getVersionCodeMajor();
        this.mInstallLocation = apkLite.getInstallLocation();
        this.mVerifiers = apkLite.getVerifiers();
        this.mSigningDetails = apkLite.getSigningDetails();
        this.mBaseRevisionCode = apkLite.getRevisionCode();
        this.mCoreApp = apkLite.isCoreApp();
        this.mDebuggable = apkLite.isDebuggable();
        this.mMultiArch = apkLite.isMultiArch();
        this.mUse32bitAbi = apkLite.isUse32bitAbi();
        this.mExtractNativeLibs = apkLite.isExtractNativeLibs();
        this.mIsolatedSplits = apkLite.isIsolatedSplits();
        this.mUseEmbeddedDex = apkLite.isUseEmbeddedDex();
        this.mBaseRequiredSplitTypes = apkLite.getRequiredSplitTypes();
        this.mRequiredSplitTypes = setArr;
        this.mSplitRequired = apkLite.isSplitRequired() || hasAnyRequiredSplitTypes();
        this.mProfileableByShell = apkLite.isProfileableByShell();
        this.mIsSdkLibrary = apkLite.isIsSdkLibrary();
        this.mUsesSdkLibraries = apkLite.getUsesSdkLibraries();
        this.mUsesSdkLibrariesVersionsMajor = apkLite.getUsesSdkLibrariesVersionsMajor();
        this.mUsesSdkLibrariesCertDigests = apkLite.getUsesSdkLibrariesCertDigests();
        this.mIsStaticLibrary = apkLite.isIsStaticLibrary();
        this.mUsesStaticLibraries = apkLite.getUsesStaticLibraries();
        this.mUsesStaticLibrariesVersions = apkLite.getUsesStaticLibrariesVersions();
        this.mUsesStaticLibrariesCertDigests = apkLite.getUsesStaticLibrariesCertDigests();
        this.mSplitNames = strArr;
        this.mSplitTypes = setArr2;
        this.mIsFeatureSplits = zArr;
        this.mUsesSplitNames = strArr2;
        this.mConfigForSplit = strArr3;
        this.mSplitApkPaths = strArr4;
        this.mSplitRevisionCodes = iArr;
        this.mTargetSdk = i;
        this.mDeclaredLibraries = apkLite.getDeclaredLibraries();
        this.mArchivedPackage = apkLite.getArchivedPackage();
        this.mPageSizeCompat = apkLite.getPageSizeCompat();
    }

    public List<String> getAllApkPaths() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mBaseApkPath);
        if (!ArrayUtils.isEmpty(this.mSplitApkPaths)) {
            Collections.addAll(arrayList, this.mSplitApkPaths);
        }
        return arrayList;
    }

    public long getLongVersionCode() {
        return PackageInfo.composeLongVersionCode(this.mVersionCodeMajor, this.mVersionCode);
    }

    private boolean hasAnyRequiredSplitTypes() {
        return (CollectionUtils.isEmpty(this.mBaseRequiredSplitTypes) && ArrayUtils.find(this.mRequiredSplitTypes, new Predicate() { // from class: android.content.pm.parsing.PackageLite$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PackageLite.lambda$hasAnyRequiredSplitTypes$0((Set) obj);
            }
        }) == null) ? false : true;
    }

    static /* synthetic */ boolean lambda$hasAnyRequiredSplitTypes$0(Set set) {
        return !CollectionUtils.isEmpty(set);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getPath() {
        return this.mPath;
    }

    public String getBaseApkPath() {
        return this.mBaseApkPath;
    }

    public String[] getSplitApkPaths() {
        return this.mSplitApkPaths;
    }

    public String[] getSplitNames() {
        return this.mSplitNames;
    }

    public String[] getUsesSplitNames() {
        return this.mUsesSplitNames;
    }

    public String[] getConfigForSplit() {
        return this.mConfigForSplit;
    }

    public Set<String> getBaseRequiredSplitTypes() {
        return this.mBaseRequiredSplitTypes;
    }

    public Set<String>[] getRequiredSplitTypes() {
        return this.mRequiredSplitTypes;
    }

    public Set<String>[] getSplitTypes() {
        return this.mSplitTypes;
    }

    public int getVersionCodeMajor() {
        return this.mVersionCodeMajor;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int getTargetSdk() {
        return this.mTargetSdk;
    }

    public int getBaseRevisionCode() {
        return this.mBaseRevisionCode;
    }

    public int[] getSplitRevisionCodes() {
        return this.mSplitRevisionCodes;
    }

    public int getInstallLocation() {
        return this.mInstallLocation;
    }

    public VerifierInfo[] getVerifiers() {
        return this.mVerifiers;
    }

    public SigningDetails getSigningDetails() {
        return this.mSigningDetails;
    }

    public boolean[] getIsFeatureSplits() {
        return this.mIsFeatureSplits;
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

    public boolean isMultiArch() {
        return this.mMultiArch;
    }

    public boolean isUse32bitAbi() {
        return this.mUse32bitAbi;
    }

    public boolean isExtractNativeLibs() {
        return this.mExtractNativeLibs;
    }

    public boolean isProfileableByShell() {
        return this.mProfileableByShell;
    }

    public boolean isUseEmbeddedDex() {
        return this.mUseEmbeddedDex;
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
