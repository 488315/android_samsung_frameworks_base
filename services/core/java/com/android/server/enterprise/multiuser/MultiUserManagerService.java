package com.android.server.enterprise.multiuser;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Log;

import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.multiuser.IMultiUserManager;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MultiUserManagerService extends IMultiUserManager.Stub
        implements EnterpriseServiceCallback {
    public Context mContext;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;
    public UserManager mUserManager;

    public final int allowMultipleUsers(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndMultiUserPermission =
                enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        if (!UserManager.supportsMultipleUsers()) {
            return -1;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "MULTI_USER_MGMT",
                        enforceOwnerOnlyAndMultiUserPermission.mCallerUid,
                        z,
                        0,
                        "multiUserAllowed");
        if (putBoolean
                && multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) == 0) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            List<ActivityManager.RecentTaskInfo> list =
                    ActivityManagerNative.getDefault().getRecentTasks(12, 0, 0).getList();
            if (!z && !list.isEmpty()) {
                for (ActivityManager.RecentTaskInfo recentTaskInfo : list) {
                    ComponentName component = recentTaskInfo.baseIntent.getComponent();
                    if (component != null) {
                        String packageName = component.getPackageName();
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                "packageName ", packageName, "MultiUserManagerService");
                        if (packageName != null
                                && packageName.equals(KnoxCustomManagerService.SETTING_PKG_NAME)) {
                            ActivityManagerNative.getDefault()
                                    .removeTask(recentTaskInfo.persistentId);
                        }
                    }
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return putBoolean ? 1 : 0;
    }

    public final boolean allowUserCreation(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndMultiUserPermission =
                enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) == 1) {
            return this.mEdmStorageProvider.putBoolean(
                    "MULTI_USER_MGMT",
                    enforceOwnerOnlyAndMultiUserPermission.mCallerUid,
                    z,
                    0,
                    "multiUserCreationAllowed");
        }
        return false;
    }

    public final boolean allowUserRemoval(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndMultiUserPermission =
                enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) == 1) {
            return this.mEdmStorageProvider.putBoolean(
                    "MULTI_USER_MGMT",
                    enforceOwnerOnlyAndMultiUserPermission.mCallerUid,
                    z,
                    0,
                    "multiUserRemovalAllowed");
        }
        return false;
    }

    public final int createUser(ContextInfo contextInfo, String str) {
        ContextInfo enforceOwnerOnlyAndMultiUserPermission =
                enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        int i = -1;
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) == 1
                && isUserCreationAllowed(enforceOwnerOnlyAndMultiUserPermission, false)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            UserManager userManager = this.mUserManager;
            if (userManager != null) {
                if (userManager.getUserCount() >= UserManager.getMaxSupportedUsers()) {
                    return -1;
                }
                String trim = str != null ? str.trim() : "";
                UserInfo createUser = this.mUserManager.createUser(trim, 4);
                if (createUser != null) {
                    i = createUser.id;
                    if (trim.length() <= 0) {
                        this.mUserManager.setUserName(
                                i,
                                this.mContext.getString(
                                        R.string.restr_pin_create_pin, Integer.valueOf(i)));
                    }
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return i;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Multi User Service");
            return;
        }
        new EnterpriseDumpHelper(this.mContext)
                .dumpTable(
                        printWriter,
                        "MULTI_USER_MGMT",
                        new String[] {"multiUserRemovalAllowed", "multiUserAllowed"},
                        null);
        try {
            printWriter.println("    isUserRemovalAllowed = " + isUserRemovalAllowed(null, false));
            printWriter.println("    multipleUsersAllowed = " + multipleUsersAllowed(null, false));
        } catch (RemoteException e) {
            Log.d(
                    "MultiUserManagerService",
                    "Exception in Multi User Service Dump" + e.getMessage());
        }
    }

    public final ContextInfo enforceOwnerOnlyAndMultiUserPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(
                contextInfo,
                new ArrayList(
                        Arrays.asList("com.samsung.android.knox.permission.KNOX_MULTI_USER_MGMT")));
    }

    public final int[] getUsers(ContextInfo contextInfo) {
        int[] iArr = null;
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission(contextInfo), false) == 1) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            UserManager userManager = this.mUserManager;
            if (userManager != null) {
                List users = userManager.getUsers();
                int size = users.size();
                iArr = new int[size];
                for (int i = 0; i < size; i++) {
                    iArr[i] = ((UserInfo) users.get(i)).id;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return iArr;
    }

    public final boolean isUserCreationAllowed(ContextInfo contextInfo, boolean z) {
        boolean z2 =
                !this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "MULTI_USER_MGMT", "multiUserCreationAllowed")
                        .contains(Boolean.FALSE);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.restr_pin_confirm_pin);
        }
        Log.w("MultiUserManagerService", "isUserCreationAllowed " + z2);
        return z2;
    }

    public final boolean isUserRemovalAllowed(ContextInfo contextInfo, boolean z) {
        boolean z2 =
                !this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "MULTI_USER_MGMT", "multiUserRemovalAllowed")
                        .contains(Boolean.FALSE);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.restr_pin_enter_admin_pin);
        }
        Log.w("MultiUserManagerService", "isUserRemovalAllowed " + z2);
        return z2;
    }

    public final int multipleUsersAllowed(ContextInfo contextInfo, boolean z) {
        if (!UserManager.supportsMultipleUsers()) {
            return -1;
        }
        int i =
                !this.mEdmStorageProvider
                                .getBooleanListAsUser(0, "MULTI_USER_MGMT", "multiUserAllowed")
                                .contains(Boolean.FALSE)
                        ? 1
                        : 0;
        if (z && i == 0) {
            RestrictionToastManager.show(R.string.resolver_work_tab_accessibility);
        }
        return i;
    }

    public final boolean multipleUsersSupported(ContextInfo contextInfo) {
        try {
            return UserManager.supportsMultipleUsers();
        } catch (Error e) {
            Log.e("MultiUserManagerService", e.getMessage());
            return false;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final boolean removeUser(ContextInfo contextInfo, int i) {
        UserManager userManager;
        UserInfo userInfo;
        ContextInfo enforceOwnerOnlyAndMultiUserPermission =
                enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        boolean z = false;
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) != 1) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (i > 0
                && (userManager = this.mUserManager) != null
                && (userInfo = userManager.getUserInfo(i)) != null
                && !userInfo.isKnoxWorkspace()
                && !userInfo.isManagedProfile()
                && isUserRemovalAllowed(enforceOwnerOnlyAndMultiUserPermission, false)) {
            z = this.mUserManager.removeUser(i);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
