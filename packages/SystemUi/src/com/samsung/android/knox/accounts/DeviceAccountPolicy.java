package com.samsung.android.knox.accounts;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ISecurityPolicy;
import com.samsung.android.knox.accounts.IDeviceAccountPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DeviceAccountPolicy {
    public static final String ALL_ACCOUNTS = ".*";
    public static String TAG = "DeviceAccountPolicy";
    public final ContextInfo mContextInfo;
    public ISecurityPolicy mSecurityService;
    public IDeviceAccountPolicy mService;

    public DeviceAccountPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean addAccountsToAdditionBlackList(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.addAccountsToAdditionBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAccountsToAdditionBlackList(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean addAccountsToAdditionWhiteList(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.addAccountsToAdditionWhiteList(String, List<String>)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAccountsToAdditionWhiteList(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean addAccountsToRemovalBlackList(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.addAccountsToRemovalBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAccountsToRemovalBlackList(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean addAccountsToRemovalWhiteList(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.addAccountsToRemovalWhiteList(String, List<String>)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addAccountsToRemovalWhiteList(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean clearAccountsFromAdditionBlackList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.clearAccountsFromAdditionBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearAccountsFromAdditionBlackList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean clearAccountsFromAdditionList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.clearAccountsFromAdditionList");
        return clearAccountsFromAdditionWhiteList(str) && clearAccountsFromAdditionBlackList(str);
    }

    public final boolean clearAccountsFromAdditionWhiteList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.clearAccountsFromAdditionWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearAccountsFromAdditionWhiteList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean clearAccountsFromRemovalBlackList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.clearAccountsFromRemovalBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearAccountsFromRemovalBlackList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean clearAccountsFromRemovalList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.clearAccountsFromRemovalList");
        return clearAccountsFromRemovalWhiteList(str) && clearAccountsFromRemovalBlackList(str);
    }

    public final boolean clearAccountsFromRemovalWhiteList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.clearAccountsFromRemovalWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearAccountsFromRemovalWhiteList(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final List<AccountControlInfo> getAccountsFromAdditionBlackLists(String str) {
        if (getService() != null) {
            try {
                return this.mService.getAccountsFromAdditionBlackLists(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Device Account policy", e);
            }
        }
        return new ArrayList();
    }

    public final List<AccountControlInfo> getAccountsFromAdditionWhiteLists(String str) {
        if (getService() != null) {
            try {
                return this.mService.getAccountsFromAdditionWhiteLists(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Device Account policy", e);
            }
        }
        return new ArrayList();
    }

    public final List<AccountControlInfo> getAccountsFromRemovalBlackLists(String str) {
        if (getService() != null) {
            try {
                return this.mService.getAccountsFromRemovalBlackLists(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Device Account policy", e);
            }
        }
        return new ArrayList();
    }

    public final List<AccountControlInfo> getAccountsFromRemovalWhiteLists(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.getAccountsFromRemovalWhiteLists", true);
        if (getService() != null) {
            try {
                return this.mService.getAccountsFromRemovalWhiteLists(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Device Account policy", e);
            }
        }
        return new ArrayList();
    }

    public final ISecurityPolicy getSecurityService() {
        if (this.mSecurityService == null) {
            this.mSecurityService = ISecurityPolicy.Stub.asInterface(ServiceManager.getService("security_policy"));
        }
        return this.mSecurityService;
    }

    public final IDeviceAccountPolicy getService() {
        if (this.mService == null) {
            this.mService = IDeviceAccountPolicy.Stub.asInterface(ServiceManager.getService("device_account_policy"));
        }
        return this.mService;
    }

    public final List<String> getSupportedAccountTypes() {
        if (getService() != null) {
            try {
                return this.mService.getSupportedAccountTypes();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with Device Account policy", e);
            }
        }
        return new ArrayList();
    }

    public final boolean isAccountAdditionAllowed(String str, String str2, boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isAccountAdditionAllowed(str, str2, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return true;
        }
    }

    public final boolean isAccountRemovalAllowed(String str, String str2, boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isAccountRemovalAllowed(str, str2, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return true;
        }
    }

    public final boolean isAccountRemovalAllowedAsUser(String str, String str2, boolean z, int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isAccountRemovalAllowedAsUser(str, str2, z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return true;
        }
    }

    public final boolean removeAccountsByType(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.removeAccountsByType");
        if (getSecurityService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.removeAccountsByType(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Security policy", e);
            return false;
        }
    }

    public final boolean removeAccountsFromAdditionBlackList(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.removeAccountsFromAdditionBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAccountsFromAdditionBlackList(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean removeAccountsFromAdditionWhiteList(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.removeAccountsFromAdditionWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAccountsFromAdditionWhiteList(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean removeAccountsFromRemovalBlackList(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.removeAccountsFromRemovalBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAccountsFromRemovalBlackList(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean removeAccountsFromRemovalWhiteList(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.removeAccountsFromRemovalWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeAccountsFromRemovalWhiteList(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Device Account policy", e);
            return false;
        }
    }

    public final boolean removeAccountsWithoutAdminPrivilege(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.removeAccountsWithoutAdminPrivilege");
        if (getSecurityService() == null) {
            return false;
        }
        try {
            return this.mSecurityService.removeAccountsWithoutAdminPrivilege(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Security policy", e);
            return false;
        }
    }

    public final boolean addAccountsToAdditionWhiteList(String str, List<String> list, boolean z) {
        boolean z2;
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.addAccountsToAdditionWhiteList(String, List<String>, boolean)");
        ArrayList arrayList = new ArrayList();
        arrayList.add(ALL_ACCOUNTS);
        if (!z || addAccountsToAdditionBlackList(str, arrayList)) {
            z2 = true;
        } else {
            Log.d(TAG, "Failed to update wildCard in Blacklist, Wildcard may be already present!");
            z2 = false;
        }
        return addAccountsToAdditionWhiteList(str, list) && z2;
    }

    public final boolean addAccountsToRemovalWhiteList(String str, List<String> list, boolean z) {
        boolean z2;
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceAccountPolicy.addAccountsToRemovalWhiteList(String, List<String>, boolean)");
        ArrayList arrayList = new ArrayList();
        arrayList.add(ALL_ACCOUNTS);
        if (!z || addAccountsToRemovalBlackList(str, arrayList)) {
            z2 = true;
        } else {
            Log.d(TAG, "Failed to update wildCard in Blacklist, Wildcard may be already present!");
            z2 = false;
        }
        return addAccountsToRemovalWhiteList(str, list) && z2;
    }
}
