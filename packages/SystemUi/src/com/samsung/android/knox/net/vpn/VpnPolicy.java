package com.samsung.android.knox.net.vpn;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.multiuser.MultiUserManager$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.vpn.IVpnInfoPolicy;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VpnPolicy {
    public static String TAG = "VpnPolicy";
    public IVpnInfoPolicy lService;
    public ContextInfo mContextInfo;

    public VpnPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean allowOnlySecureConnections(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.allowOnlySecureConnections");
        try {
            if (getService() != null) {
                return this.lService.allowOnlySecureConnections(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API allowOnlySecureConnections ", e);
            return false;
        }
    }

    public final boolean allowUserAddProfiles(boolean z) {
        try {
            if (getService() != null) {
                return this.lService.allowUserAddProfiles(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with Vpn Policy service"), TAG);
            return false;
        }
    }

    public final boolean allowUserChangeProfiles(boolean z) {
        try {
            if (getService() != null) {
                return this.lService.allowUserChangeProfiles(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with Vpn Policy service"), TAG);
            return false;
        }
    }

    public final boolean allowUserSetAlwaysOn(boolean z) {
        try {
            if (getService() != null) {
                return this.lService.allowUserSetAlwaysOn(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with Vpn Policy service"), TAG);
            return false;
        }
    }

    public final boolean checkRacoonSecurity(String[] strArr) {
        try {
            if (getService() != null) {
                return this.lService.checkRacoonSecurity(this.mContextInfo, strArr);
            }
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API checkRacoonSecurity ", e);
            return true;
        }
    }

    public final boolean createProfile(VpnAdminProfile vpnAdminProfile) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.createProfile");
        try {
            if (getService() != null) {
                return this.lService.createProfile(this.mContextInfo, vpnAdminProfile);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API createProfile", e);
            return false;
        }
    }

    public final void deleteProfile(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.deleteProfile");
        try {
            if (getService() != null) {
                this.lService.deleteProfile(this.mContextInfo, str);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API deleteProfile", e);
        }
    }

    public final String getAlwaysOnProfile() {
        try {
            if (getService() != null) {
                return this.lService.getAlwaysOnProfile(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with Vpn Policy service"), TAG);
            return null;
        }
    }

    public final List<String> getDnsDomains(String str) {
        try {
            if (getService() != null) {
                return this.lService.getDnsDomains(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getDnsDomains ", e);
            return null;
        }
    }

    public final List<String> getDnsServers(String str) {
        try {
            if (getService() != null) {
                return this.lService.getDnsServers(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getDnsServers ", e);
            return null;
        }
    }

    public final List<String> getForwardRoutes(String str) {
        try {
            if (getService() != null) {
                return this.lService.getForwardRoutes(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getForwardRoutes ", e);
            return null;
        }
    }

    public final String getIPSecCaCertificate(String str) {
        try {
            if (getService() != null) {
                return this.lService.getCaCertificate(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getCaCertificate", e);
            return null;
        }
    }

    public final String getIPSecPreSharedKey(String str) {
        try {
            if (getService() != null) {
                return this.lService.getPresharedKey(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getSharedSecret", e);
            return null;
        }
    }

    public final String getIPSecUserCertificate(String str) {
        try {
            if (getService() != null) {
                return this.lService.getUserCertificate(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getCaCertificate", e);
            return null;
        }
    }

    public final String getId(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.getId", true);
        try {
            if (getService() != null) {
                return this.lService.getId(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getId", e);
            return null;
        }
    }

    public final String getIpSecIdentifier(String str) {
        try {
            if (getService() != null) {
                return this.lService.getIpSecIdentifier(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getIpSecIdentifier ", e);
            return null;
        }
    }

    public final String getL2TPSecret(String str) {
        try {
            if (getService() != null) {
                return this.lService.getL2TPSecret(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getL2TPSecret ", e);
            return null;
        }
    }

    public final String getOcspServerUrl(String str) {
        try {
            if (getService() != null) {
                return this.lService.getOcspServerUrl(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getOcspServerUrl ", e);
            return null;
        }
    }

    public final String getServerName(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.getServerName", true);
        try {
            if (getService() != null) {
                return this.lService.getServerName(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getServerName", e);
            return null;
        }
    }

    public final IVpnInfoPolicy getService() {
        if (this.lService == null) {
            this.lService = IVpnInfoPolicy.Stub.asInterface(ServiceManager.getService("vpn_policy"));
        }
        return this.lService;
    }

    public final String getState(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.getState");
        try {
            if (getService() != null) {
                return this.lService.getState(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getState", e);
            return null;
        }
    }

    public final List<String> getSupportedConnectionTypes() {
        try {
            if (getService() != null) {
                return this.lService.getSupportedConnectionTypes(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getSupportedConnectionTypes ", e);
            return null;
        }
    }

    public final String getType(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.getType", true);
        try {
            if (getService() != null) {
                return this.lService.getType(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getType", e);
            return null;
        }
    }

    public final String getUserName(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.getUserName", true);
        try {
            if (getService() != null) {
                return this.lService.getUserName(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final String getUserPassword(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.getUserPassword", true);
        try {
            if (getService() != null) {
                return this.lService.getUserPwd(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final String[] getVpnList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.getVpnList");
        try {
            if (getService() != null) {
                return this.lService.getVPNList(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getVpnList", e);
            return null;
        }
    }

    public final boolean isAdminProfile(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.isAdminProfile");
        try {
            if (getService() == null) {
                return false;
            }
            IVpnInfoPolicy iVpnInfoPolicy = this.lService;
            ContextInfo contextInfo = this.mContextInfo;
            return iVpnInfoPolicy.isAdminProfile(contextInfo, iVpnInfoPolicy.getId(contextInfo, str));
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API isAdminProfile", e);
            return false;
        }
    }

    public final boolean isL2TPSecretEnabled(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.isL2TPSecretEnabled", true);
        try {
            if (getService() != null) {
                return this.lService.isL2TPSecretEnabled(this.mContextInfo, str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API isL2TPSecretEnabled ", e);
            return false;
        }
    }

    public final boolean isOnlySecureConnectionsAllowed() {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.isOnlySecureConnectionsAllowed", true);
        try {
            if (getService() != null) {
                return this.lService.isOnlySecureConnectionsAllowed(this.mContextInfo);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API isOnlySecureConnectionsAllowed ", e);
            return false;
        }
    }

    public final boolean isPPTPEncryptionEnabled(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.isPPTPEncryptionEnabled", true);
        try {
            if (getService() != null) {
                return this.lService.isPPTPEncryptionEnabled(this.mContextInfo, str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API getCaCertificate", e);
            return false;
        }
    }

    public final boolean isUserAddProfilesAllowed() {
        return isUserAddProfilesAllowed(false);
    }

    public final boolean isUserChangeProfilesAllowed() {
        return isUserChangeProfilesAllowed(false);
    }

    public final boolean isUserSetAlwaysOnAllowed() {
        return isUserSetAlwaysOnAllowed(false);
    }

    public final boolean setAlwaysOnProfile(String str) {
        try {
            if (getService() != null) {
                return this.lService.setAlwaysOnProfile(this.mContextInfo, str);
            }
            return false;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with Vpn Policy service"), TAG);
            return false;
        }
    }

    public final boolean setDnsDomains(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setDnsDomains");
        try {
            if (getService() != null) {
                return this.lService.setDnsDomains(this.mContextInfo, str, list);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setDnsDomains ", e);
            return false;
        }
    }

    public final boolean setDnsServers(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setDnsServers");
        try {
            if (getService() != null) {
                return this.lService.setDnsServers(this.mContextInfo, str, list);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setDnsServers ", e);
            return false;
        }
    }

    public final boolean setForwardRoutes(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setForwardRoutes");
        try {
            if (getService() != null) {
                return this.lService.setForwardRoutes(this.mContextInfo, str, list);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setForwardRoutes ", e);
            return false;
        }
    }

    public final boolean setIPSecCaCertificate(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setIPSecCaCertificate");
        try {
            if (getService() != null) {
                return this.lService.setCaCertificate(this.mContextInfo, str, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setCaCertificate", e);
            return false;
        }
    }

    public final boolean setIPSecPreSharedKey(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setIPSecPreSharedKey");
        try {
            if (getService() != null) {
                return this.lService.setPresharedKey(this.mContextInfo, str, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setSharedSecret", e);
            return false;
        }
    }

    public final boolean setIPSecUserCertificate(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setIPSecUserCertificate");
        try {
            if (getService() != null) {
                return this.lService.setUserCertificate(this.mContextInfo, str, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setCaCertificate", e);
            return false;
        }
    }

    public final void setId(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setId");
        try {
            if (getService() != null) {
                this.lService.setId(this.mContextInfo, str, str2);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setId", e);
        }
    }

    public final boolean setIpSecIdentifier(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setIpSecIdentifier");
        try {
            if (getService() != null) {
                return this.lService.setIpSecIdentifier(this.mContextInfo, str, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setIpSecIdentifier ", e);
            return false;
        }
    }

    public final boolean setL2TPSecret(String str, boolean z, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setL2TPSecret");
        try {
            if (getService() != null) {
                return this.lService.setL2TPSecret(this.mContextInfo, str, z, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setL2TPSecret", e);
            return false;
        }
    }

    public final boolean setOcspServerUrl(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setOcspServerUrl");
        try {
            if (getService() != null) {
                return this.lService.setOcspServerUrl(this.mContextInfo, str, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setOcspServerUrl ", e);
            return false;
        }
    }

    public final boolean setPPTPEncryptionEnabled(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setPPTPEncryptionEnabled");
        try {
            if (getService() != null) {
                return this.lService.setEncryptionEnabledForPPTP(this.mContextInfo, str, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setCaCertificate", e);
            return false;
        }
    }

    public final void setProfileName(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setProfileName");
        try {
            if (getService() != null) {
                this.lService.setName(this.mContextInfo, str, str2);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setName", e);
        }
    }

    public final void setServerName(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setServerName");
        try {
            if (getService() != null) {
                this.lService.setServerName(this.mContextInfo, str, str2);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setServerName", e);
        }
    }

    public final boolean setUserName(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setUserName");
        try {
            if (getService() != null) {
                return this.lService.setUserName(this.mContextInfo, str, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setServerName", e);
            return false;
        }
    }

    public final boolean setUserPassword(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "VpnPolicy.setUserPassword");
        try {
            if (getService() != null) {
                return this.lService.setUserPassword(this.mContextInfo, str, str2);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at Vpn policy API setServerName", e);
            return false;
        }
    }

    public final boolean isUserAddProfilesAllowed(boolean z) {
        try {
            if (getService() != null) {
                return this.lService.isUserAddProfilesAllowed(this.mContextInfo, z);
            }
            return true;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with Vpn Policy service"), TAG);
            return true;
        }
    }

    public final boolean isUserChangeProfilesAllowed(boolean z) {
        try {
            if (getService() != null) {
                return this.lService.isUserChangeProfilesAllowed(this.mContextInfo, z);
            }
            return true;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with Vpn Policy service"), TAG);
            return true;
        }
    }

    public final boolean isUserSetAlwaysOnAllowed(boolean z) {
        try {
            if (getService() != null) {
                return this.lService.isUserSetAlwaysOnAllowed(this.mContextInfo, z);
            }
            return true;
        } catch (RemoteException e) {
            MultiUserManager$$ExternalSyntheticOutline0.m259m(e, new StringBuilder("Failed to communicate with Vpn Policy service"), TAG);
            return true;
        }
    }
}
