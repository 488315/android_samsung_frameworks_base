package com.samsung.ucm.ucmservice;

import android.content.Context;
import android.os.ServiceManager;
import android.util.Log;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.OomAdjuster$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PolicyManager {
    public ArrayList hiddenPluginPackages;
    public Context mContext;
    public IUniversalCredentialManager mUCMService;

    public static boolean containsCaseInsensitive(String str, List list) {
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

    public final synchronized IUniversalCredentialManager getUCMService() {
        try {
            if (this.mUCMService == null) {
                this.mUCMService = IUniversalCredentialManager.Stub.asInterface(ServiceManager.getService("knox_ucsm_policy"));
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mUCMService;
    }

    public final String[] getWifiCertificateAliasesAsUser(UcmAgentWrapper ucmAgentWrapper) {
        Log.d("PolicyManager", "PolicyManager.getWifiCertificateAliasesAsUser() ");
        try {
            CredentialStorage credentialStorage = new CredentialStorage();
            credentialStorage.name = ucmAgentWrapper.info.id;
            Log.d("PolicyManager", "PolicyManager.getWifiCertificateAliasesAsUser() for userId=0");
            if (ucmAgentWrapper.componentName.getPackageName() != null) {
                credentialStorage.packageName = ucmAgentWrapper.componentName.getPackageName();
            }
            IUniversalCredentialManager uCMService = getUCMService();
            if (uCMService != null) {
                return uCMService.getWifiCertificateAliasesAsUser(0, credentialStorage);
            }
            return null;
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(e, new StringBuilder("Error in getWifiCertificateAliasesAsUser: "), "PolicyManager");
            return null;
        }
    }

    public final String[] getallAliasesforCS(UcmAgentWrapper ucmAgentWrapper) {
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = ucmAgentWrapper.info.id;
        VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("PolicyManager.getallAliasesforCS() for csname="), credentialStorage.name, "PolicyManager");
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
            OomAdjuster$$ExternalSyntheticOutline0.m(e, new StringBuilder("Error in getallAliasesforCS: "), "PolicyManager");
            return null;
        }
    }

    public final String[] getallAliasesforUid(int i, UcmAgentWrapper ucmAgentWrapper) {
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = ucmAgentWrapper.info.id;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "PolicyManager.getallAliasesforUid() for uid=", "PolicyManager");
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
            OomAdjuster$$ExternalSyntheticOutline0.m(e, new StringBuilder("Error in getallAliasesforUid: "), "PolicyManager");
            return null;
        }
    }

    public final String[] getallAliasesforUserId(int i, UcmAgentWrapper ucmAgentWrapper) {
        CredentialStorage credentialStorage = new CredentialStorage();
        credentialStorage.name = ucmAgentWrapper.info.id;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "PolicyManager.getallAliasesforUserId() for userid=", "PolicyManager");
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
            OomAdjuster$$ExternalSyntheticOutline0.m(e, new StringBuilder("Error in getallAliasesforUserId: "), "PolicyManager");
            return null;
        }
    }

    public final boolean isCSobscure(UcmAgentWrapper ucmAgentWrapper) {
        String str;
        if (ucmAgentWrapper == null) {
            Log.d("PolicyManager", "csAgent is null");
            return false;
        }
        if (ucmAgentWrapper.componentName.getPackageName() != null) {
            str = ucmAgentWrapper.componentName.getPackageName();
            Log.i("PolicyManager", "SE visibility being checked for cs Name = " + ucmAgentWrapper.info.id + " Package name = " + str);
        } else {
            str = null;
        }
        if (str != null) {
            return this.hiddenPluginPackages.contains(str);
        }
        Log.i("PolicyManager", "cspkgname = NULL. Unknown CS. CS Not Obscure");
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0201 A[Catch: Exception -> 0x0206, TRY_LEAVE, TryCatch #5 {Exception -> 0x0206, blocks: (B:96:0x01fb, B:98:0x0201), top: B:95:0x01fb }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int isSEStorageAccessAllowed(com.samsung.ucm.ucmservice.UcmAgentWrapper r10, int r11, int r12, boolean r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 672
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.ucm.ucmservice.PolicyManager.isSEStorageAccessAllowed(com.samsung.ucm.ucmservice.UcmAgentWrapper, int, int, boolean, java.lang.String):int");
    }

    public final boolean isStorageEnabled(int i, CredentialStorage credentialStorage) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "PolicyManager.isStorageEnabled() for userId = ", "PolicyManager");
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
            OomAdjuster$$ExternalSyntheticOutline0.m(e, new StringBuilder("Error in isStorageEnabled: "), "PolicyManager");
            return z;
        }
    }
}
