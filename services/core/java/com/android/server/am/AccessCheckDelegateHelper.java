package com.android.server.am;

import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.android.server.appop.AppOpsService;
import com.android.server.pm.permission.AccessCheckDelegate$AccessCheckDelegateImpl;
import com.android.server.pm.permission.PermissionManagerService;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AccessCheckDelegateHelper {
    public AccessCheckDelegate$AccessCheckDelegateImpl mAccessCheckDelegate;
    public final List mActiveInstrumentation;
    public final AppOpsService mAppOpsService;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl
            mPermissionManagerInternal;
    public final ActivityManagerGlobalLock mProcLock;

    public AccessCheckDelegateHelper(
            ActivityManagerGlobalLock activityManagerGlobalLock,
            List list,
            AppOpsService appOpsService,
            PermissionManagerService.PermissionManagerServiceInternalImpl
                    permissionManagerServiceInternalImpl) {
        this.mProcLock = activityManagerGlobalLock;
        this.mActiveInstrumentation = list;
        this.mAppOpsService = appOpsService;
        this.mPermissionManagerInternal = permissionManagerServiceInternalImpl;
    }

    public final AccessCheckDelegate$AccessCheckDelegateImpl getAccessCheckDelegateLPr() {
        if (this.mAccessCheckDelegate == null) {
            AccessCheckDelegate$AccessCheckDelegateImpl
                    accessCheckDelegate$AccessCheckDelegateImpl =
                            new AccessCheckDelegate$AccessCheckDelegateImpl();
            accessCheckDelegate$AccessCheckDelegateImpl.mDelegateAndOwnerUid = -1;
            this.mAccessCheckDelegate = accessCheckDelegate$AccessCheckDelegateImpl;
            this.mAppOpsService.setCheckOpsDelegate(accessCheckDelegate$AccessCheckDelegateImpl);
            PermissionManagerService.PermissionManagerServiceInternalImpl
                    permissionManagerServiceInternalImpl = this.mPermissionManagerInternal;
            AccessCheckDelegate$AccessCheckDelegateImpl
                    accessCheckDelegate$AccessCheckDelegateImpl2 = this.mAccessCheckDelegate;
            PermissionManagerService permissionManagerService = PermissionManagerService.this;
            synchronized (permissionManagerService.mLock) {
                permissionManagerService.mCheckPermissionDelegate =
                        accessCheckDelegate$AccessCheckDelegateImpl2;
            }
        }
        return this.mAccessCheckDelegate;
    }

    public final void onInstrumentationFinished(int i, String str) {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                AccessCheckDelegate$AccessCheckDelegateImpl
                        accessCheckDelegate$AccessCheckDelegateImpl = this.mAccessCheckDelegate;
                if (accessCheckDelegate$AccessCheckDelegateImpl != null) {
                    if (accessCheckDelegate$AccessCheckDelegateImpl.mDelegateAndOwnerUid == i
                            && TextUtils.equals(
                                    accessCheckDelegate$AccessCheckDelegateImpl.mDelegatePackage,
                                    str)) {
                        accessCheckDelegate$AccessCheckDelegateImpl.mDelegatePackage = null;
                        accessCheckDelegate$AccessCheckDelegateImpl.mDelegatePermissions = null;
                        accessCheckDelegate$AccessCheckDelegateImpl.mDelegateAllPermissions = false;
                        PackageManager.invalidatePackageInfoCache();
                    }
                    if (accessCheckDelegate$AccessCheckDelegateImpl.isDelegateAndOwnerUid(i)) {
                        accessCheckDelegate$AccessCheckDelegateImpl.mOverridePermissionStates =
                                null;
                        PackageManager.invalidatePackageInfoCache();
                    }
                    if (!accessCheckDelegate$AccessCheckDelegateImpl.hasDelegateOrOverrides()) {
                        removeAccessCheckDelegateLPr();
                    }
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    public final void removeAccessCheckDelegateLPr() {
        this.mAccessCheckDelegate = null;
        this.mAppOpsService.setCheckOpsDelegate(null);
        PermissionManagerService permissionManagerService = PermissionManagerService.this;
        synchronized (permissionManagerService.mLock) {
            permissionManagerService.mCheckPermissionDelegate = null;
        }
    }
}
