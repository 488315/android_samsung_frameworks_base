package com.android.server.pm;

import android.util.SparseArray;
import android.util.SparseIntArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageRemovedInfo {
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public SparseArray mBroadcastAllowList;
    public boolean mDataRemoved;
    public boolean mHidden;
    public SparseIntArray mInstallReasons;
    public String mInstallerPackageName;
    public boolean mIsExternal;
    public boolean mIsStaticSharedLib;
    public boolean mIsUpdate;
    public int[] mOrigUsers;
    public boolean mRemovedForAllUsers;
    public String mRemovedPackage;
    public long mRemovedPackageVersionCode;
    public SparseIntArray mUninstallReasons;
    public int mUid = -1;
    public boolean mIsAppIdRemoved = false;
    public int[] mRemovedUsers = null;
    public int[] mBroadcastUsers = null;
    public int[] mInstantUserIds = null;
    public boolean mIsRemovedPackageSystemUpdate = false;
    public CleanUpArgs mArgs = null;
}
