package com.android.server.pm.pkg.mutate;

import android.util.ArraySet;
import com.android.server.pm.InstallSource;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.pkg.PackageUserStateImpl;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageStateMutator {
    public static final AtomicLong sStateChangeSequence = new AtomicLong();
    public final Function mActiveStateFunction;
    public final Function mDisabledStateFunction;
    public final StateWriteWrapper mStateWrite = new StateWriteWrapper();
    public final ArraySet mChangedStates = new ArraySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InitialState {
        public final int mPackageSequence;
        public final long mStateSequence;

        public InitialState(int i, long j) {
            this.mPackageSequence = i;
            this.mStateSequence = j;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Result {
        public final boolean mCommitted;
        public final boolean mPackagesChanged;
        public final boolean mSpecificPackageNull;
        public final boolean mStateChanged;
        public static final Result SUCCESS = new Result(true, false, false, false);
        public static final Result PACKAGES_CHANGED = new Result(false, true, false, false);
        public static final Result STATE_CHANGED = new Result(false, false, true, false);
        public static final Result PACKAGES_AND_STATE_CHANGED = new Result(false, true, true, false);

        public Result(boolean z, boolean z2, boolean z3, boolean z4) {
            this.mCommitted = z;
            this.mPackagesChanged = z2;
            this.mStateChanged = z3;
            this.mSpecificPackageNull = z4;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StateWriteWrapper {
        public PackageSetting mState;
        public final UserStateWriteWrapper mUserStateWrite = new UserStateWriteWrapper();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class UserStateWriteWrapper {
            public PackageUserStateImpl mUserState;
        }

        public final void setInstaller(int i, String str) {
            PackageSetting packageSetting = this.mState;
            if (packageSetting != null) {
                InstallSource installSource = packageSetting.installSource;
                if (!Objects.equals(str, installSource.mInstallerPackageName)) {
                    String intern = InstallSource.intern(str);
                    int i2 = installSource.mPackageSource;
                    boolean z = installSource.mIsOrphaned;
                    installSource = InstallSource.createInternal(i, i2, installSource.mInitiatingPackageSignatures, installSource.mInitiatingPackageName, installSource.mOriginatingPackageName, intern, installSource.mUpdateOwnerPackageName, installSource.mInstallerAttributionTag, z, installSource.mIsInitiatingPackageUninstalled);
                }
                packageSetting.installSource = installSource;
                packageSetting.onChanged$2();
            }
        }

        public final UserStateWriteWrapper userState(int i) {
            PackageUserStateImpl packageUserStateImpl;
            PackageSetting packageSetting = this.mState;
            if (packageSetting == null) {
                packageUserStateImpl = null;
            } else {
                PackageUserStateImpl packageUserStateImpl2 = (PackageUserStateImpl) packageSetting.mUserStates.get(i);
                if (packageUserStateImpl2 == null) {
                    packageUserStateImpl2 = new PackageUserStateImpl(packageSetting);
                    packageSetting.mUserStates.put(i, packageUserStateImpl2);
                }
                packageUserStateImpl = packageUserStateImpl2;
            }
            if (packageUserStateImpl != null) {
                packageUserStateImpl.mWatchable = this.mState;
            }
            UserStateWriteWrapper userStateWriteWrapper = this.mUserStateWrite;
            userStateWriteWrapper.mUserState = packageUserStateImpl;
            return userStateWriteWrapper;
        }
    }

    public PackageStateMutator(Function function, Function function2) {
        this.mActiveStateFunction = function;
        this.mDisabledStateFunction = function2;
    }

    public final StateWriteWrapper forPackage(String str) {
        PackageSetting packageSetting = (PackageSetting) this.mActiveStateFunction.apply(str);
        if (packageSetting != null) {
            this.mChangedStates.add(packageSetting);
        }
        StateWriteWrapper stateWriteWrapper = this.mStateWrite;
        stateWriteWrapper.mState = packageSetting;
        return stateWriteWrapper;
    }
}
