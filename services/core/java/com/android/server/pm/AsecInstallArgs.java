package com.android.server.pm;

import android.app.ResourcesManager;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.os.Process;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.policy.AttributeCache;
import com.android.internal.util.Preconditions;
import com.android.server.pm.Installer;
import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class AsecInstallArgs {
    public String cid;
    public boolean isException;
    public final String mAbiOverride;
    public final List mAllowlistedRestrictedPermissions;
    public final boolean mApplicationEnabledSettingPersistent;
    public final int mAutoRevokePermissionsMode;
    public final int mDataLoaderType;
    public final boolean mForceQueryableOverride;
    public final int mInstallFlags;
    public final String[] mInstallGrantPermissions;
    public final int mInstallReason;
    public final int mInstallScenario;
    public final InstallSource mInstallSource;
    public String[] mInstructionSets;
    public final MoveInfo mMoveInfo;
    public final IPackageInstallObserver2 mObserver;
    public final OriginInfo mOriginInfo;
    public final int mPackageSource;
    public final PackageManagerService mPm;
    public final SigningDetails mSigningDetails;
    public final int mTraceCookie;
    public final String mTraceMethod;
    public final UserHandle mUser;
    public final String mVolumeUuid;
    public String packagePath;
    public String resourcePath;

    public AsecInstallArgs(OriginInfo originInfo, MoveInfo moveInfo, IPackageInstallObserver2 iPackageInstallObserver2, int i, InstallSource installSource, String str, UserHandle userHandle, String[] strArr, String str2, String[] strArr2, List list, int i2, String str3, int i3, SigningDetails signingDetails, int i4, int i5, boolean z, int i6, int i7, boolean z2, PackageManagerService packageManagerService) {
        this.isException = false;
        this.mPm = packageManagerService;
        this.mOriginInfo = originInfo;
        this.mMoveInfo = moveInfo;
        this.mInstallFlags = i;
        this.mObserver = iPackageInstallObserver2;
        this.mInstallSource = (InstallSource) Preconditions.checkNotNull(installSource);
        this.mVolumeUuid = str;
        this.mUser = userHandle;
        this.mInstructionSets = strArr;
        this.mAbiOverride = str2;
        this.mInstallGrantPermissions = strArr2;
        this.mAllowlistedRestrictedPermissions = list;
        this.mAutoRevokePermissionsMode = i2;
        this.mTraceMethod = str3;
        this.mTraceCookie = i3;
        this.mSigningDetails = signingDetails;
        this.mInstallReason = i4;
        this.mInstallScenario = i5;
        this.mForceQueryableOverride = z;
        this.mDataLoaderType = i6;
        this.mPackageSource = i7;
        this.mApplicationEnabledSettingPersistent = z2;
    }

    public AsecInstallArgs(String str, String[] strArr, boolean z, PackageManagerService packageManagerService) {
        this(OriginInfo.fromNothing(), null, null, z ? 8 : 0, InstallSource.EMPTY, null, null, strArr, null, null, null, 3, null, 0, SigningDetails.UNKNOWN, 0, 0, false, 0, 0, false, packageManagerService);
        String absolutePath = !str.endsWith("pkg.apk") ? new File(str, "pkg.apk").getAbsolutePath() : str;
        int lastIndexOf = absolutePath.lastIndexOf("/");
        String substring = absolutePath.substring(0, lastIndexOf);
        this.cid = substring.substring(substring.lastIndexOf("/") + 1, lastIndexOf);
        setMountPath(substring);
    }

    public AsecInstallArgs(String str, String[] strArr, PackageManagerService packageManagerService) {
        this(OriginInfo.fromNothing(), null, null, AsecInstallHelper.isAsecExternal(str) ? 8 : 0, InstallSource.EMPTY, null, null, strArr, null, null, null, 3, null, 0, SigningDetails.UNKNOWN, 0, 0, false, 0, 0, false, packageManagerService);
        this.cid = str;
        try {
            setMountPath(PackageHelperExt.getSdDir(str));
        } catch (NullPointerException unused) {
            this.isException = true;
            Slog.i("PackageManager", "Catch nullpointer exception");
        }
    }

    public String getCodePath() {
        return this.packagePath;
    }

    public int doPreInstall(int i) {
        if (i != 1) {
            PackageHelperExt.destroySdDir(this.cid);
        } else if (!PackageHelperExt.isContainerMounted(this.cid)) {
            String mountSdDir = PackageHelperExt.mountSdDir(this.cid, AsecInstallHelper.getEncryptKey(), 1000);
            if (mountSdDir == null) {
                return -18;
            }
            setMountPath(mountSdDir);
        }
        return i;
    }

    public boolean isExternalAsec() {
        return (this.mInstallFlags & 8) != 0;
    }

    public final void setMountPath(String str) {
        File file = new File(str);
        File file2 = new File(file, "pkg.apk");
        if (file2.exists()) {
            this.packagePath = file2.getAbsolutePath();
        } else {
            this.packagePath = file.getAbsolutePath();
        }
        this.resourcePath = this.packagePath;
    }

    public int doPostInstall(int i, int i2) {
        if (i != 1) {
            cleanUp();
        } else {
            if (i2 < 10000 || !PackageHelperExt.fixSdPermissions(this.cid, -1, (String) null)) {
                Slog.e("PackageManager", "Failed to finalize " + this.cid);
                PackageHelperExt.destroySdDir(this.cid);
                return -18;
            }
            if (!PackageHelperExt.isContainerMounted(this.cid)) {
                PackageHelperExt.mountSdDir(this.cid, AsecInstallHelper.getEncryptKey(), Process.myUid());
            }
        }
        return i;
    }

    public final void cleanUp() {
        PackageHelperExt.destroySdDir(this.cid);
    }

    public final List getAllCodePaths() {
        File file = new File(getCodePath());
        if (file.exists()) {
            ParseResult parsePackageLite = ApkLiteParseUtils.parsePackageLite(ParseTypeImpl.forDefaultParsing().reset(), file, 0);
            if (parsePackageLite.isSuccess()) {
                return ((PackageLite) parsePackageLite.getResult()).getAllApkPaths();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public final void cleanUpResourcesLI(List list) {
        cleanUp();
        removeDexFiles(list, this.mInstructionSets);
    }

    public void removeDexFiles(List list, String[] strArr) {
        if (list.isEmpty()) {
            return;
        }
        if (strArr == null) {
            throw new IllegalStateException("instructionSet == null");
        }
        String[] dexCodeInstructionSets = InstructionSets.getDexCodeInstructionSets(strArr);
        if (DexOptHelper.useArtService()) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            for (String str2 : dexCodeInstructionSets) {
                try {
                    this.mPm.mInstaller.deleteOdex(getPackageName(), str, str2, null);
                } catch (Installer.InstallerException unused) {
                } catch (Installer.LegacyDexoptDisabledException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String getPackageName() {
        return AsecInstallHelper.getAsecPackageName(this.cid);
    }

    public final boolean pendingPostDeleteLI(final boolean z, final int i) {
        List list = Collections.EMPTY_LIST;
        if (z) {
            list = getAllCodePaths();
        }
        boolean isContainerMounted = PackageHelperExt.isContainerMounted(this.cid);
        if (isContainerMounted) {
            Slog.d("PackageManager", "AsecInstallArgs.doPostDeleteLI(" + i + "): " + getPackageName() + ", codePath: " + getCodePath());
            AttributeCache.instance().removePackage(getPackageName());
            ResourcesManager.getInstance().invalidatePath(getCodePath());
            Runtime.getRuntime().gc();
            System.runFinalization();
            if (PackageHelperExt.unMountSdDir(this.cid, !z)) {
                isContainerMounted = false;
            } else if (z) {
                if (i < 5) {
                    this.mPm.mHandler.postDelayed(new Runnable() { // from class: com.android.server.pm.AsecInstallArgs$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AsecInstallArgs.this.lambda$pendingPostDeleteLI$0(z, i);
                        }
                    }, 60000 * i);
                }
                if (i == 4) {
                    PackageManagerServiceUtils.logCriticalInfo(5, "ASEC unmount failed and will try for last chance: " + this.cid);
                }
            }
        }
        if (!isContainerMounted && z) {
            cleanUpResourcesLI(list);
        }
        return !isContainerMounted;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pendingPostDeleteLI$0(boolean z, int i) {
        synchronized (this.mPm.mInstallLock) {
            pendingPostDeleteLI(z, i + 1);
        }
    }

    public boolean doPostDeleteLI(boolean z) {
        return pendingPostDeleteLI(z, 0);
    }
}
