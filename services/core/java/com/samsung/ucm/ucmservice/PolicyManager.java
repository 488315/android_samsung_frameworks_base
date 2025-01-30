package com.samsung.ucm.ucmservice;

import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class PolicyManager {
    public Context mContext;
    public IUniversalCredentialManager mUCMService = null;
    public ArrayList hiddenPluginPackages = new ArrayList(Arrays.asList("com.sec.smartcard.manager", "com.samsung.ucs.agent.boot"));

    public PolicyManager(Context context) {
        this.mContext = context;
    }

    public final synchronized IUniversalCredentialManager getUCMService() {
        if (this.mUCMService == null) {
            this.mUCMService = IUniversalCredentialManager.Stub.asInterface(ServiceManager.getService("knox_ucsm_policy"));
        }
        return this.mUCMService;
    }

    public final boolean isStorageEnabled(int i, CredentialStorage credentialStorage) {
        Log.d("PolicyManager", "PolicyManager.isStorageEnabled() for userId = " + i);
        boolean z = false;
        try {
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService == null) {
                return false;
            }
            z = uCMService.isCredentialStorageManagedAsUser(i, credentialStorage);
            Log.d("PolicyManager", "PolicyManager.isStorageEnabled() result = " + z);
            return z;
        } catch (Exception e) {
            Log.e("PolicyManager", "Error in isStorageEnabled: " + e.getMessage());
            e.printStackTrace();
            return z;
        }
    }

    public int getStorageAuthenticationType(int i, CredentialStorage credentialStorage) {
        Log.d("PolicyManager", "PolicyManager.getStorageAuthenticationType() ");
        try {
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService != null) {
                return uCMService.getStorageAuthenticationType(i, credentialStorage);
            }
            return -1;
        } catch (Exception e) {
            Log.e("PolicyManager", "Error in getStorageAuthenticationType: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public String[] getWifiCertificateAliasesAsUser(int i, UcmAgentWrapper ucmAgentWrapper) {
        Log.d("PolicyManager", "PolicyManager.getWifiCertificateAliasesAsUser() ");
        try {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = ucmAgentWrapper.info.f1778id;
            Log.d("PolicyManager", "PolicyManager.getWifiCertificateAliasesAsUser() for userId=" + i);
            if (ucmAgentWrapper.componentName.getPackageName() != null) {
                credentialStorage.packageName = ucmAgentWrapper.componentName.getPackageName();
            }
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService != null) {
                return uCMService.getWifiCertificateAliasesAsUser(i, credentialStorage);
            }
            return null;
        } catch (Exception e) {
            Log.e("PolicyManager", "Error in getWifiCertificateAliasesAsUser: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean isPackageFromExemptList(int i, int i2, CredentialStorage credentialStorage) {
        Log.d("PolicyManager", "PolicyManager.isPackageFromExemptList() ");
        try {
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService != null) {
                return uCMService.isPackageFromExemptList(UserHandle.getUid(i, i2), credentialStorage, 106);
            }
            return false;
        } catch (Exception e) {
            Log.e("PolicyManager", "Error in isPackageFromExemptList: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAccessAllowedforUid(int i, int i2, CredentialStorage credentialStorage) {
        Log.d("PolicyManager", "PolicyManager.isAccessAllowedforUid() for uid=" + i);
        Bundle bundle = new Bundle();
        bundle.putInt("access_type", 103);
        bundle.putInt("userId", i2);
        boolean z = false;
        try {
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService != null) {
                boolean isAccessAllowed = uCMService.isAccessAllowed(i, credentialStorage, bundle);
                try {
                    Log.d("PolicyManager", "PolicyManager.isAccessAllowedforUid():  result = " + isAccessAllowed);
                    return isAccessAllowed;
                } catch (Exception e) {
                    e = e;
                    z = isAccessAllowed;
                    Log.e("PolicyManager", "Error in isAccessAllowedforUid: " + e.getMessage());
                    e.printStackTrace();
                    return z;
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        return z;
    }

    public boolean isAliasAllowedforUid(int i, int i2, String str, CredentialStorage credentialStorage) {
        Log.d("PolicyManager", "PolicyManager.isAliasAllowedforUid() for uid=" + i);
        Bundle bundle = new Bundle();
        bundle.putInt("access_type", 104);
        bundle.putString("alias", str);
        bundle.putInt("userId", i2);
        boolean z = false;
        try {
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService != null) {
                boolean isAccessAllowed = uCMService.isAccessAllowed(i, credentialStorage, bundle);
                try {
                    Log.d("PolicyManager", "PolicyManager.isAliasAllowedforUid():  result = " + isAccessAllowed);
                    return isAccessAllowed;
                } catch (Exception e) {
                    e = e;
                    z = isAccessAllowed;
                    Log.e("PolicyManager", "Error in isAliasAllowedforUid: " + e.getMessage());
                    e.printStackTrace();
                    return z;
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        return z;
    }

    public boolean containsCaseInsensitive(String str, List list) {
        if (str == null || list == null) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((String) it.next()).equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAliasknown(int i, String str, CredentialStorage credentialStorage) {
        String[] certificateAliasesAsUser;
        Log.d("PolicyManager", "PolicyManager.isAliasknown() for uid=" + i);
        try {
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService == null || (certificateAliasesAsUser = uCMService.getCertificateAliasesAsUser(UserHandle.getUserId(i), credentialStorage)) == null) {
                return false;
            }
            return containsCaseInsensitive(str, Arrays.asList(certificateAliasesAsUser));
        } catch (Exception e) {
            Log.e("PolicyManager", "Error in isAliasknown: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public String[] getallAliasesforUid(int i, UcmAgentWrapper ucmAgentWrapper) {
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = ucmAgentWrapper.info.f1778id;
        Log.d("PolicyManager", "PolicyManager.getallAliasesforUid() for uid=" + i);
        if (ucmAgentWrapper.componentName.getPackageName() != null) {
            credentialStorage.packageName = ucmAgentWrapper.componentName.getPackageName();
        }
        try {
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService != null) {
                return uCMService.getCertificateAliases(i, credentialStorage);
            }
            return null;
        } catch (Exception e) {
            Log.e("PolicyManager", "Error in getallAliasesforUid: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String[] getallAliasesforUserId(int i, UcmAgentWrapper ucmAgentWrapper) {
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = ucmAgentWrapper.info.f1778id;
        Log.d("PolicyManager", "PolicyManager.getallAliasesforUserId() for userid=" + i);
        if (ucmAgentWrapper.componentName.getPackageName() != null) {
            credentialStorage.packageName = ucmAgentWrapper.componentName.getPackageName();
        }
        try {
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService != null) {
                return uCMService.getCertificateAliasesAsUser(i, credentialStorage);
            }
            return null;
        } catch (Exception e) {
            Log.e("PolicyManager", "Error in getallAliasesforUserId: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String[] getallAliasesforCS(UcmAgentWrapper ucmAgentWrapper) {
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = ucmAgentWrapper.info.f1778id;
        Log.d("PolicyManager", "PolicyManager.getallAliasesforCS() for csname=" + credentialStorage.name);
        if (ucmAgentWrapper.componentName.getPackageName() != null) {
            credentialStorage.packageName = ucmAgentWrapper.componentName.getPackageName();
        }
        try {
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService != null) {
                return uCMService.getAllCertificateAliases(credentialStorage);
            }
            return null;
        } catch (Exception e) {
            Log.e("PolicyManager", "Error in getallAliasesforCS: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public final boolean isAuthEnabled(int i, CredentialStorage credentialStorage, int i2) {
        boolean z;
        Log.d("PolicyManager", "isAuthEnabled() called");
        int storageAuthenticationType = getStorageAuthenticationType(i, credentialStorage);
        Log.d("PolicyManager", "isAuthEnabled authType = " + storageAuthenticationType);
        if (100 == storageAuthenticationType) {
            if (!isPackageFromExemptList(i, i2, credentialStorage)) {
                Log.d("PolicyManager", "isAuthEnabled calleruid = " + i2 + " is not auth exempt package");
                z = isAuthTypeLockforUser(i);
                Log.d("PolicyManager", "isAuthEnabled() status - " + z);
                return z;
            }
            Log.d("PolicyManager", "isAuthEnabled calleruid = " + i2 + " is auth exempt package");
        }
        z = false;
        Log.d("PolicyManager", "isAuthEnabled() status - " + z);
        return z;
    }

    public final boolean isAuthTypeLockforUser(int i) {
        Log.d("PolicyManager", "isAuthTypeLockforUser() called : " + i);
        KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        if (keyguardManager == null) {
            Log.i("PolicyManager", "isAuthTypeLockforUser() KeyguardManager is null");
            return false;
        }
        return keyguardManager.isDeviceLocked(i);
    }

    public boolean isCSobscure(UcmAgentWrapper ucmAgentWrapper) {
        String str;
        if (ucmAgentWrapper == null) {
            Log.d("PolicyManager", "csAgent is null");
            return false;
        }
        if (ucmAgentWrapper.componentName.getPackageName() != null) {
            str = ucmAgentWrapper.componentName.getPackageName();
            Log.i("PolicyManager", "SE visibility being checked for cs Name = " + ucmAgentWrapper.info.f1778id + " Package name = " + str);
        } else {
            str = null;
        }
        if (str != null) {
            return this.hiddenPluginPackages.contains(str);
        }
        Log.i("PolicyManager", "cspkgname = NULL. Unknown CS. CS Not Obscure");
        return false;
    }

    public static boolean isValidUser(int i) {
        Log.d("PolicyManager", "isValidUser userId-" + i);
        boolean z = i == 0 || i >= 10;
        Log.d("PolicyManager", "isValidUser status-" + z);
        return z;
    }

    public int isSEStorageAccessAllowed(UcmAgentWrapper ucmAgentWrapper, int i, int i2, boolean z, String str) {
        if (ucmAgentWrapper != null) {
            Log.d("PolicyManager", "--isSEStorageAccessAllowed()-- for Source=" + ucmAgentWrapper.info.f1778id + "; UserId=" + i + "; uid=" + i2);
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = ucmAgentWrapper.info.f1778id;
            if (ucmAgentWrapper.componentName.getPackageName() != null) {
                credentialStorage.packageName = ucmAgentWrapper.componentName.getPackageName();
                Log.i("PolicyManager", "SE access being checked for cs Name = " + credentialStorage.name + " Package name = " + credentialStorage.packageName);
                if (isCSobscure(ucmAgentWrapper)) {
                    return 1;
                }
                if (z) {
                    Log.i("PolicyManager", "MDM or KNOX licensed caller. Skipping additional validation");
                    return 1;
                }
                Log.i("PolicyManager", "Not a MDM or KNOX licensed caller. Performing additional validation checks");
                if (!isStorageEnabled(i, credentialStorage)) {
                    boolean z2 = ucmAgentWrapper.info.enforceManagement;
                    int userId = UserHandle.getUserId(i2);
                    if (z2 || userId >= 10 || !isValidUser(userId)) {
                        Log.i("PolicyManager", "WARNING!!!! Storage is not allowed for userid= " + i + " and uid-" + i2);
                        return 0;
                    }
                    Log.i("PolicyManager", "Storage is allowed for userid= " + i + " and  uid-" + i2);
                    return 1;
                }
                if (i2 == 1010 || i2 == 1000 || UserHandle.getAppId(i2) == 1000 || i2 == 1016) {
                    Log.i("PolicyManager", "Calling Id is either WIFI or System; skip additional validation");
                    return 1;
                }
                if (getPackageNameForUid(i2)) {
                    Log.i("PolicyManager", "Calling Id is SystemUI application");
                    return 1;
                }
                if (!isAccessAllowedforUid(i2, i, credentialStorage)) {
                    Log.i("PolicyManager", "WARNING!!!!  isAccessAllowedforUid() returned false: SE access is NOT allowed for calleruid = " + i2);
                    return 0;
                }
                if (str != null) {
                    int uid = UserHandle.getUid(i, i2);
                    if (!isAliasknown(uid, str, credentialStorage)) {
                        Log.i("PolicyManager", "WARNING!!!!  isAliasknown() returned false: ALIAS not known to MDM for " + uid);
                        return 1;
                    }
                    if (!isAliasAllowedforUid(i2, i, str, credentialStorage)) {
                        Log.i("PolicyManager", "WARNING!!!!  isAliasAllowedforUid() returned false: ALIAS access is NOT allowed for calleruid = " + i2);
                        return 0;
                    }
                }
                if (isAuthEnabled(i, credentialStorage, i2)) {
                    Log.i("PolicyManager", "Storage access is locked currently for userid = " + i);
                    return 0;
                }
                Log.d("PolicyManager", "isSEStorageAccessAllowed() VALIDATED.....");
                return 1;
            }
            Log.i("PolicyManager", "Package name for CS found NULL. Denying CS access.");
            return 0;
        }
        Log.d("PolicyManager", "csAgent is null");
        return 0;
    }

    public final boolean getPackageNameForUid(int i) {
        String str = null;
        try {
            str = AppGlobals.getPackageManager().getNameForUid(i);
            Log.d("PolicyManager", "uid : " + i + ", packageName : " + str);
        } catch (Exception unused) {
            Log.d("PolicyManager", "Exception in getPackageNameForUid");
        }
        if (str == null || str.length() <= 0) {
            return false;
        }
        int lastIndexOf = str.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR);
        if (lastIndexOf != -1) {
            str = str.substring(0, lastIndexOf);
        }
        return str.equals("android.uid.systemui");
    }
}
