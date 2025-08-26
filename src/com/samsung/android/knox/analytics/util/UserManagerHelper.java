package com.samsung.android.knox.analytics.util;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.UserInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import com.samsung.android.knox.SemPersonaManager;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class UserManagerHelper {
    private static final String TAG = "[KnoxAnalytics] UserManagerHelper";
    private static final int USER_TYPE_APP_SEPARATION = 11;
    private static final int USER_TYPE_COWP = 9;
    private static final int USER_TYPE_DO = 2;
    private static final int USER_TYPE_PO = 0;
    private static final int USER_TYPE_REGULAR = 7;
    private static final int USER_TYPE_SECURE_FOLDER = 8;
    private Context mContext;
    private IDevicePolicyManager mDevicePolicyManagerService;
    private UserManager mUserManager;

    public UserManagerHelper(Context context) {
        this.mContext = context;
    }

    public boolean isAnyPOActive() {
        UserManager userManager = getUserManager();
        IDevicePolicyManager devicePolicyManagerService = getDevicePolicyManagerService();
        boolean z = false;
        if (devicePolicyManagerService == null) {
            return false;
        }
        try {
            for (UserInfo userInfo : userManager.getUsers(true)) {
                try {
                    if (userInfo.id != 0) {
                        if (userInfo.isSecureFolder() || userInfo.isPrivateProfile()) {
                            Log.d(TAG, "isAnyPOActive(): Skipping Secure Folder or Private Profile");
                        } else if (devicePolicyManagerService.getProfileOwnerAsUser(userInfo.id) != null) {
                            z = true;
                        }
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "isAnyPOActive() - Remote exception: ", e);
                }
            }
            Log.d(TAG, "isAnyPOActive(): " + String.valueOf(z));
            return z;
        } catch (RuntimeException e2) {
            Log.e(TAG, "isAnyPOActive() - Runtime exception: ", e2);
            return false;
        }
    }

    public String getPoPackageName(int i) {
        IDevicePolicyManager devicePolicyManagerService = getDevicePolicyManagerService();
        if (devicePolicyManagerService == null) {
            return null;
        }
        String packageName = "";
        try {
            ComponentName profileOwnerAsUser = devicePolicyManagerService.getProfileOwnerAsUser(i);
            if (profileOwnerAsUser != null) {
                packageName = profileOwnerAsUser.getPackageName();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "getPoPackageName() - Remote exception: ", e);
        }
        Log.d(TAG, "getPoPackageName(int userId): " + i + " - " + packageName);
        return packageName;
    }

    public String getPoPackageName() {
        UserManager userManager = getUserManager();
        IDevicePolicyManager devicePolicyManagerService = getDevicePolicyManagerService();
        if (devicePolicyManagerService != null && userManager != null) {
            try {
                List<UserInfo> users = userManager.getUsers(true);
                String packageName = "";
                if (users != null) {
                    Iterator<UserInfo> it = users.iterator();
                    while (it.hasNext()) {
                        try {
                            ComponentName profileOwnerAsUser = devicePolicyManagerService.getProfileOwnerAsUser(it.next().id);
                            if (profileOwnerAsUser != null) {
                                packageName = profileOwnerAsUser.getPackageName();
                            }
                        } catch (RemoteException e) {
                            Log.e(TAG, "getPoPackageName() - Remote exception: ", e);
                        }
                    }
                }
                Log.d(TAG, "getPoPackageName(): " + packageName);
                return packageName;
            } catch (RuntimeException e2) {
                Log.e(TAG, "getPoPackageName() - Runtime exception: ", e2);
            }
        }
        return null;
    }

    public String getDoPackageName() {
        IDevicePolicyManager devicePolicyManagerService = getDevicePolicyManagerService();
        if (devicePolicyManagerService == null) {
            return null;
        }
        String packageName = "";
        try {
            ComponentName deviceOwnerComponent = devicePolicyManagerService.getDeviceOwnerComponent(true);
            if (deviceOwnerComponent != null) {
                packageName = deviceOwnerComponent.getPackageName();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "isDoActive(): Exception in DPMS.getDeviceOwnerComponent - ", e);
        }
        Log.d(TAG, "getDoPackageName(): " + packageName);
        return packageName;
    }

    public boolean isDoActive() {
        IDevicePolicyManager devicePolicyManagerService = getDevicePolicyManagerService();
        boolean zHasDeviceOwner = false;
        if (devicePolicyManagerService == null) {
            return false;
        }
        try {
            zHasDeviceOwner = devicePolicyManagerService.hasDeviceOwner();
        } catch (RemoteException e) {
            Log.e(TAG, "isDoActive(): Exception in DPMS.hasDeviceOwner - ", e);
        }
        Log.d(TAG, "isDoActive(): " + String.valueOf(zHasDeviceOwner));
        return zHasDeviceOwner;
    }

    public int getUserType(int i) {
        String str = TAG;
        Log.d(str, "getUserType(" + i + NavigationBarInflaterView.KEY_CODE_END);
        UserInfo userInfo = getUserInfo(i);
        if (userInfo == null) {
            Log.e(str, "getUserType(): UserInfo is null!");
            return -1;
        }
        if (userInfo.isSecureFolder() || userInfo.isPrivateProfile()) {
            return 8;
        }
        if (SemPersonaManager.isDoEnabled(i)) {
            return 2;
        }
        if (userInfo.isUserTypeAppSeparation()) {
            return 11;
        }
        if (userInfo.isManagedProfile()) {
            return isCOWP() ? 9 : 0;
        }
        return 7;
    }

    private boolean isCOWP() {
        IDevicePolicyManager devicePolicyManagerService = getDevicePolicyManagerService();
        if (devicePolicyManagerService == null) {
            return false;
        }
        try {
            return devicePolicyManagerService.isOrganizationOwnedDeviceWithManagedProfile();
        } catch (RemoteException e) {
            Log.e(TAG, "isDoActive(): Exception in DPMS.hasDeviceOwner - ", e);
            return false;
        }
    }

    private IDevicePolicyManager getDevicePolicyManagerService() {
        if (this.mDevicePolicyManagerService == null) {
            IDevicePolicyManager iDevicePolicyManager = (IDevicePolicyManager) ServiceManager.getService(Context.DEVICE_POLICY_SERVICE);
            this.mDevicePolicyManagerService = iDevicePolicyManager;
            if (iDevicePolicyManager == null) {
                Log.e(TAG, "getDevicePolicyManagerService(): could not get DevicePolicyManager!");
            }
        }
        return this.mDevicePolicyManagerService;
    }

    private UserInfo getUserInfo(int i) {
        return getUserManager().getUserInfo(i);
    }

    private UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }
}
