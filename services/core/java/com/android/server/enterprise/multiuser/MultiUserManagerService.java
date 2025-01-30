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
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.multiuser.IMultiUserManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class MultiUserManagerService extends IMultiUserManager.Stub implements EnterpriseServiceCallback {
    public Context mContext;
    public EnterpriseDeviceManager mEDM = null;
    public EdmStorageProvider mEdmStorageProvider;
    public UserManager mUserManager;

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public MultiUserManagerService(Context context) {
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceOwnerOnlyAndMultiUserPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_MULTI_USER_MGMT")));
    }

    public boolean multipleUsersSupported(ContextInfo contextInfo) {
        try {
            return UserManager.supportsMultipleUsers();
        } catch (Error e) {
            Log.e("MultiUserManagerService", e.getMessage());
            return false;
        }
    }

    public int multipleUsersAllowed(ContextInfo contextInfo, boolean z) {
        if (!UserManager.supportsMultipleUsers()) {
            return -1;
        }
        int i = !this.mEdmStorageProvider.getBooleanList("MULTI_USER_MGMT", "multiUserAllowed").contains(Boolean.FALSE) ? 1 : 0;
        if (z && i == 0) {
            RestrictionToastManager.show(R.string.personal_apps_suspension_title);
        }
        return i;
    }

    public int allowMultipleUsers(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndMultiUserPermission = enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        if (!UserManager.supportsMultipleUsers()) {
            return -1;
        }
        int i = (this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndMultiUserPermission.mCallerUid, "MULTI_USER_MGMT", "multiUserAllowed", z) ? 1 : 0) & 1;
        if (i != 0 && multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) == 0) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            List<ActivityManager.RecentTaskInfo> list = ActivityManagerNative.getDefault().getRecentTasks(12, 0, 0).getList();
            if (!z && !list.isEmpty()) {
                for (ActivityManager.RecentTaskInfo recentTaskInfo : list) {
                    ComponentName component = recentTaskInfo.baseIntent.getComponent();
                    if (component != null) {
                        String packageName = component.getPackageName();
                        Log.w("MultiUserManagerService", "packageName " + packageName);
                        if (packageName != null && packageName.equals("com.android.settings")) {
                            ActivityManagerNative.getDefault().removeTask(recentTaskInfo.persistentId);
                        }
                    }
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return i;
    }

    public int createUser(ContextInfo contextInfo, String str) {
        ContextInfo enforceOwnerOnlyAndMultiUserPermission = enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        int i = -1;
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) == 1 && isUserCreationAllowed(enforceOwnerOnlyAndMultiUserPermission, false)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            UserManager userManager = this.mUserManager;
            if (userManager != null) {
                if (userManager.getUserCount() >= UserManager.getMaxSupportedUsers()) {
                    return -1;
                }
                String trim = str != null ? str.trim() : "";
                UserInfo createUser = this.mUserManager.createUser(trim, 4);
                if (createUser != null) {
                    int i2 = createUser.id;
                    if (trim.length() <= 0) {
                        this.mUserManager.setUserName(i2, this.mContext.getString(R.string.phoneTypeCallback, Integer.valueOf(i2)));
                    }
                    i = i2;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return i;
    }

    public boolean removeUser(ContextInfo contextInfo, int i) {
        UserManager userManager;
        UserInfo userInfo;
        ContextInfo enforceOwnerOnlyAndMultiUserPermission = enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        boolean z = false;
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) != 1) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (i > 0 && (userManager = this.mUserManager) != null && (userInfo = userManager.getUserInfo(i)) != null && !userInfo.isKnoxWorkspace() && !userInfo.isManagedProfile() && isUserRemovalAllowed(enforceOwnerOnlyAndMultiUserPermission, false)) {
            z = this.mUserManager.removeUser(i);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public int[] getUsers(ContextInfo contextInfo) {
        int[] iArr = null;
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission(contextInfo), false) == 1) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            UserManager userManager = this.mUserManager;
            if (userManager != null) {
                List users = userManager.getUsers();
                int size = users.size();
                int[] iArr2 = new int[size];
                for (int i = 0; i < size; i++) {
                    iArr2[i] = ((UserInfo) users.get(i)).id;
                }
                iArr = iArr2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return iArr;
    }

    public boolean allowUserCreation(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndMultiUserPermission = enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) == 1) {
            return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndMultiUserPermission.mCallerUid, "MULTI_USER_MGMT", "multiUserCreationAllowed", z);
        }
        return false;
    }

    public boolean isUserCreationAllowed(ContextInfo contextInfo, boolean z) {
        boolean z2 = !this.mEdmStorageProvider.getBooleanList("MULTI_USER_MGMT", "multiUserCreationAllowed").contains(Boolean.FALSE);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.phoneTypeAssistant);
        }
        Log.w("MultiUserManagerService", "isUserCreationAllowed " + z2);
        return z2;
    }

    public boolean allowUserRemoval(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndMultiUserPermission = enforceOwnerOnlyAndMultiUserPermission(contextInfo);
        if (multipleUsersAllowed(enforceOwnerOnlyAndMultiUserPermission, false) == 1) {
            return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndMultiUserPermission.mCallerUid, "MULTI_USER_MGMT", "multiUserRemovalAllowed", z);
        }
        return false;
    }

    public boolean isUserRemovalAllowed(ContextInfo contextInfo, boolean z) {
        boolean z2 = !this.mEdmStorageProvider.getBooleanList("MULTI_USER_MGMT", "multiUserRemovalAllowed").contains(Boolean.FALSE);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.phoneTypeCar);
        }
        Log.w("MultiUserManagerService", "isUserRemovalAllowed " + z2);
        return z2;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Multi User Service");
            return;
        }
        new EnterpriseDumpHelper(this.mContext).dumpTable(printWriter, "MULTI_USER_MGMT", new String[]{"multiUserRemovalAllowed", "multiUserAllowed"});
        try {
            printWriter.println("    isUserRemovalAllowed = " + isUserRemovalAllowed(null, false));
            printWriter.println("    multipleUsersAllowed = " + multipleUsersAllowed(null, false));
        } catch (RemoteException e) {
            Log.d("MultiUserManagerService", "Exception in Multi User Service Dump" + e.getMessage());
        }
    }
}
