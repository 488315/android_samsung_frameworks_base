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
import java.util.List;

/* loaded from: classes6.dex */
public class UserManagerHelper {
    private static final String TAG = "[KnoxAnalytics] " + UserManagerHelper.class.getSimpleName();
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
        IDevicePolicyManager devicePolicyManager = getDevicePolicyManagerService();
        if (devicePolicyManager == null) {
            return false;
        }
        try {
            List<UserInfo> userInfoList = userManager.getUsers(true);
            boolean hasAnyPo = false;
            for (UserInfo userInfo : userInfoList) {
                try {
                    if (devicePolicyManager.getProfileOwnerAsUser(userInfo.id) != null) {
                        hasAnyPo = true;
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "isAnyPOActive() - Remote exception: ", e);
                }
            }
            Log.d(TAG, "isAnyPOActive(): " + String.valueOf(hasAnyPo));
            return hasAnyPo;
        } catch (RuntimeException e2) {
            Log.e(TAG, "isAnyPOActive() - Runtime exception: ", e2);
            return false;
        }
    }

    public String getPoPackageName(int userId) {
        IDevicePolicyManager devicePolicyManager = getDevicePolicyManagerService();
        if (devicePolicyManager == null) {
            return null;
        }
        String poPackageName = "";
        try {
            ComponentName profileComponent = devicePolicyManager.getProfileOwnerAsUser(userId);
            if (profileComponent != null) {
                poPackageName = profileComponent.getPackageName();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "getPoPackageName() - Remote exception: ", e);
        }
        Log.d(TAG, "getPoPackageName(int userId): " + userId + " - " + poPackageName);
        return poPackageName;
    }

    public String getPoPackageName() {
        UserManager userManager = getUserManager();
        IDevicePolicyManager devicePolicyManager = getDevicePolicyManagerService();
        if (devicePolicyManager == null || userManager == null) {
            return null;
        }
        try {
            List<UserInfo> userInfoList = userManager.getUsers(true);
            String poPackageName = "";
            if (userInfoList != null) {
                for (UserInfo userInfo : userInfoList) {
                    try {
                        ComponentName profileComponent = devicePolicyManager.getProfileOwnerAsUser(userInfo.id);
                        if (profileComponent != null) {
                            poPackageName = profileComponent.getPackageName();
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "getPoPackageName() - Remote exception: ", e);
                    }
                }
            }
            Log.d(TAG, "getPoPackageName(): " + poPackageName);
            return poPackageName;
        } catch (RuntimeException e2) {
            Log.e(TAG, "getPoPackageName() - Runtime exception: ", e2);
            return null;
        }
    }

    public String getDoPackageName() {
        IDevicePolicyManager dpms = getDevicePolicyManagerService();
        if (dpms == null) {
            return null;
        }
        String doPackageName = "";
        try {
            ComponentName ownerComponent = dpms.getDeviceOwnerComponent(true);
            if (ownerComponent != null) {
                doPackageName = ownerComponent.getPackageName();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "isDoActive(): Exception in DPMS.getDeviceOwnerComponent - ", e);
        }
        Log.d(TAG, "getDoPackageName(): " + doPackageName);
        return doPackageName;
    }

    public boolean isDoActive() {
        IDevicePolicyManager dpms = getDevicePolicyManagerService();
        if (dpms == null) {
            return false;
        }
        boolean res = false;
        try {
            res = dpms.hasDeviceOwner();
        } catch (RemoteException e) {
            Log.e(TAG, "isDoActive(): Exception in DPMS.hasDeviceOwner - ", e);
        }
        Log.d(TAG, "isDoActive(): " + String.valueOf(res));
        return res;
    }

    public int getUserType(int userId) {
        Log.d(TAG, "getUserType(" + userId + NavigationBarInflaterView.KEY_CODE_END);
        UserInfo ui = getUserInfo(userId);
        if (ui == null) {
            Log.e(TAG, "getUserType(): UserInfo is null!");
            return -1;
        }
        if (ui.isSecureFolder()) {
            return 8;
        }
        if (SemPersonaManager.isDoEnabled(userId)) {
            return 2;
        }
        if (ui.isUserTypeAppSeparation()) {
            return 11;
        }
        if (ui.isManagedProfile()) {
            if (isCOWP()) {
                return 9;
            }
            return 0;
        }
        return 7;
    }

    private boolean isCOWP() {
        IDevicePolicyManager devicePolicyManager = getDevicePolicyManagerService();
        if (devicePolicyManager == null) {
            return false;
        }
        try {
            return devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile();
        } catch (RemoteException e) {
            Log.e(TAG, "isDoActive(): Exception in DPMS.hasDeviceOwner - ", e);
            return false;
        }
    }

    private IDevicePolicyManager getDevicePolicyManagerService() {
        if (this.mDevicePolicyManagerService == null) {
            this.mDevicePolicyManagerService = (IDevicePolicyManager) ServiceManager.getService(Context.DEVICE_POLICY_SERVICE);
            if (this.mDevicePolicyManagerService == null) {
                Log.e(TAG, "getDevicePolicyManagerService(): could not get DevicePolicyManager!");
            }
        }
        return this.mDevicePolicyManagerService;
    }

    private UserInfo getUserInfo(int userId) {
        return getUserManager().getUserInfo(userId);
    }

    private UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }
}
