package com.android.server.pm;

import android.content.pm.IPackageInstallObserver2;
import android.content.pm.SigningDetails;
import android.util.ArrayMap;

import com.android.internal.util.Preconditions;

import java.io.File;
import java.util.List;

public final class InstallArgs {
    public final String mAbiOverride;
    public final List mAllowlistedRestrictedPermissions;
    public final boolean mApplicationEnabledSettingPersistent;
    public final int mAutoRevokePermissionsMode;
    public File mCodeFile;
    public final int mDataLoaderType;
    public final int mDevelopmentInstallFlags;
    public final String mDexoptCompilerFilter;
    public final boolean mForceQueryableOverride;
    public final int mInstallFlags;
    public final int mInstallReason;
    public final int mInstallScenario;
    public final InstallSource mInstallSource;
    public final MoveInfo mMoveInfo;
    public final IPackageInstallObserver2 mObserver;
    public final OriginInfo mOriginInfo;
    public final int mPackageSource;
    public final ArrayMap mPermissionStates;
    public final SigningDetails mSigningDetails;
    public final int mTraceCookie;
    public final String mTraceMethod;
    public final String mVolumeUuid;

    public InstallArgs(
            OriginInfo originInfo,
            MoveInfo moveInfo,
            IPackageInstallObserver2 iPackageInstallObserver2,
            int i,
            int i2,
            InstallSource installSource,
            String str,
            String str2,
            ArrayMap arrayMap,
            List list,
            int i3,
            String str3,
            int i4,
            SigningDetails signingDetails,
            int i5,
            int i6,
            boolean z,
            int i7,
            int i8,
            boolean z2,
            String str4) {
        this.mOriginInfo = originInfo;
        this.mMoveInfo = moveInfo;
        this.mInstallFlags = i;
        this.mDevelopmentInstallFlags = i2;
        this.mObserver = iPackageInstallObserver2;
        this.mInstallSource = (InstallSource) Preconditions.checkNotNull(installSource);
        this.mVolumeUuid = str;
        this.mAbiOverride = str2;
        this.mPermissionStates = arrayMap;
        this.mAllowlistedRestrictedPermissions = list;
        this.mAutoRevokePermissionsMode = i3;
        this.mTraceMethod = str3;
        this.mTraceCookie = i4;
        this.mSigningDetails = signingDetails;
        this.mInstallReason = i5;
        this.mInstallScenario = i6;
        this.mForceQueryableOverride = z;
        this.mDataLoaderType = i7;
        this.mPackageSource = i8;
        this.mApplicationEnabledSettingPersistent = z2;
        this.mDexoptCompilerFilter = str4;
    }
}
