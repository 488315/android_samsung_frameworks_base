package com.android.server.enterprise.vpn;

import android.content.ContentValues;
import android.content.Context;
import android.net.VpnManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.LegacyVpnProfileStore;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnProfile;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.restriction.RestrictionPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.vpn.IVpnInfoPolicy;
import com.samsung.android.knox.net.vpn.VpnAdminProfile;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VpnInfoPolicy extends IVpnInfoPolicy.Stub implements EnterpriseServiceCallback {
    public Context mContext;
    public EdmStorageProvider mEDMStorageProvider;
    public LegacyVpnInfo mInfo;
    public final String TAG = "VpnPolicy";
    public EnterpriseDeviceManager mEDM = null;
    public VpnManager mVpnManager = null;

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceVpnPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_VPN")));
    }

    public final ContextInfo enforceOwnerOnlyAndVpnPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_VPN")));
    }

    public final ContextInfo enforceOwnerOnlyAndKnoxVpnPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_VPN", "com.samsung.android.knox.permission.KNOX_VPN_GENERIC")));
    }

    public final ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
    }

    public VpnInfoPolicy(Context context) {
        this.mEDMStorageProvider = null;
        this.mContext = context;
        this.mEDMStorageProvider = new EdmStorageProvider(context);
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage != null) {
            retrieveVpnListFromStorage.size();
        }
    }

    public final VpnProfile decode(String str, byte[] bArr) {
        VpnProfile decode = VpnProfile.decode(str, bArr);
        if (decode == null) {
            return null;
        }
        VpnProfile.decrypt(decode);
        return decode;
    }

    public synchronized boolean setId(ContextInfo contextInfo, String str, String str2) {
        VpnProfile vpnProfile;
        VpnProfile decode;
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        ContentValues contentValues = new ContentValues();
        try {
            if (!TextUtils.isEmpty(str)) {
                int profileIndexFromName = getProfileIndexFromName(str);
                ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
                if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0 && (vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName)) != null && !TextUtils.isEmpty(str2) && (decode = decode(str2, vpnProfile.encode(true))) != null && !checkDuplicateName(decode, profileIndexFromName)) {
                    replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, decode);
                    contentValues.put("adminUid", Integer.valueOf(enforceOwnerOnlyAndVpnPermission.mCallerUid));
                    contentValues.put("VpnID", decode.key);
                    return this.mEDMStorageProvider.putDataByFields("VPN", new String[]{"adminUid", "VpnID"}, new String[]{String.valueOf(enforceOwnerOnlyAndVpnPermission.mCallerUid), decode.key}, contentValues);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean setName(ContextInfo contextInfo, String str, String str2) {
        VpnProfile vpnProfile;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        try {
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str) && str2.length() < 33) {
                int profileIndexFromName = getProfileIndexFromName(str);
                ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
                if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0 && (vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName)) != null) {
                    vpnProfile.name = str2;
                    if (!checkDuplicateName(vpnProfile, profileIndexFromName)) {
                        replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean setServerName(ContextInfo contextInfo, String str, String str2) {
        VpnProfile vpnProfile;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        try {
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
                int profileIndexFromName = getProfileIndexFromName(str);
                ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
                if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0 && (vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName)) != null) {
                    String alwaysOnProfile = getAlwaysOnProfile(enforceOwnerOnlyAndVpnPermission);
                    vpnProfile.server = str2;
                    if (alwaysOnProfile == null || !alwaysOnProfile.equals(str) || isValidAlwaysOnProfile(vpnProfile)) {
                        replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean setUserName(ContextInfo contextInfo, String str, String str2) {
        VpnProfile vpnProfile;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        ContentValues contentValues = new ContentValues();
        if (!TextUtils.isEmpty(str)) {
            try {
                int profileIndexFromName = getProfileIndexFromName(str);
                ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
                if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0 && (vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName)) != null) {
                    if (!TextUtils.isEmpty(str2)) {
                        vpnProfile.username = str2;
                        vpnProfile.saveLogin = true;
                    } else {
                        vpnProfile.saveLogin = false;
                        vpnProfile.username = "";
                    }
                    replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
                    contentValues.put("adminUid", Integer.valueOf(enforceOwnerOnlyAndVpnPermission.mCallerUid));
                    contentValues.put("VpnID", vpnProfile.key);
                    contentValues.put("UsrName", vpnProfile.username);
                    return this.mEDMStorageProvider.putDataByFields("VPN", new String[]{"adminUid", "VpnID"}, new String[]{String.valueOf(enforceOwnerOnlyAndVpnPermission.mCallerUid), vpnProfile.key}, contentValues);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public synchronized boolean setUserPassword(ContextInfo contextInfo, String str, String str2) {
        VpnProfile vpnProfile;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (str2 != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    int profileIndexFromName = getProfileIndexFromName(str);
                    ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
                    if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0 && (vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName)) != null) {
                        vpnProfile.password = str2;
                        replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("UsrPwd", vpnProfile.password);
                        contentValues.put("adminUid", Integer.valueOf(enforceOwnerOnlyAndVpnPermission.mCallerUid));
                        contentValues.put("VpnID", vpnProfile.key);
                        return this.mEDMStorageProvider.putDataByFields("VPN", new String[]{"adminUid", "VpnID"}, new String[]{String.valueOf(enforceOwnerOnlyAndVpnPermission.mCallerUid), vpnProfile.key}, contentValues);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public synchronized String getType(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.name.equals(str)) {
                    switch (vpnProfile.type) {
                        case 0:
                            return "PPTP";
                        case 1:
                            return "L2TP_IPSEC_PSK";
                        case 2:
                            return "L2TP_IPSEC";
                        case 3:
                            return "IPSEC_XAUTH_PSK";
                        case 4:
                            return "IPSEC_XAUTH_RSA";
                        case 5:
                            return "IPSEC_HYBRID_RSA";
                        case 6:
                        default:
                            return "UNSUPPORTED_PROTOCOL";
                        case 7:
                            return "IKEV2_IPSEC_PSK";
                        case 8:
                            return "IKEV2_IPSEC_RSA";
                    }
                }
            }
        }
        return null;
    }

    public synchronized String getName(ContextInfo contextInfo, String str) {
        enforceVpnPermission(contextInfo);
        if (str == null) {
            return null;
        }
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.key.equals(str)) {
                    return vpnProfile.name;
                }
            }
        }
        return null;
    }

    public synchronized String getServerName(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.name.equals(str)) {
                    return vpnProfile.server;
                }
            }
        }
        return null;
    }

    public synchronized String getId(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.name.equals(str)) {
                    return vpnProfile.key;
                }
            }
        }
        return null;
    }

    public synchronized String getState(ContextInfo contextInfo, String str) {
        boolean z;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (str == null) {
            return null;
        }
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                if (((VpnProfile) it.next()).name.equals(str)) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (!z) {
            return null;
        }
        try {
            if (this.mVpnManager == null) {
                getVpnManager();
            }
            if (this.mVpnManager == null) {
                return "IDLE";
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            this.mInfo = this.mVpnManager.getLegacyVpnInfo(UserHandle.myUserId());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            LegacyVpnInfo legacyVpnInfo = this.mInfo;
            String name = legacyVpnInfo != null ? getName(enforceOwnerOnlyAndVpnPermission, legacyVpnInfo.key) : null;
            if (name == null || !name.equals(str)) {
                return "IDLE";
            }
            int i = this.mInfo.state;
            return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "IDLE" : "FAILED" : "TIMEOUT" : "CONNECTED" : "CONNECTING" : "INITIALIZING" : "DISCONNECTED";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized String getUserName(ContextInfo contextInfo, String str) {
        ArrayList retrieveVpnListFromStorage;
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (!TextUtils.isEmpty(str) && (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.name.equals(str)) {
                    return vpnProfile.username;
                }
            }
        }
        return null;
    }

    public synchronized String getUserNameById(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        return this.mEDMStorageProvider.getString("VPN", "VpnID", str, "UsrName");
    }

    public synchronized String getUserPwdById(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        return this.mEDMStorageProvider.getString("VPN", "VpnID", str, "UsrPwd");
    }

    public synchronized String getUserPwd(ContextInfo contextInfo, String str) {
        ArrayList retrieveVpnListFromStorage;
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (!TextUtils.isEmpty(str) && (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.name.equals(str)) {
                    return vpnProfile.password;
                }
            }
        }
        return null;
    }

    public synchronized boolean setPresharedKey(ContextInfo contextInfo, String str, String str2) {
        boolean z;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        z = false;
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            try {
                int profileIndexFromName = getProfileIndexFromName(str);
                ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
                if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
                    VpnProfile vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName);
                    if (vpnProfile != null) {
                        vpnProfile.ipsecSecret = str2.intern();
                        replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
                    }
                    z = true;
                }
            } catch (IOException unused) {
            }
        }
        return z;
    }

    public synchronized String getPresharedKey(ContextInfo contextInfo, String str) {
        ArrayList retrieveVpnListFromStorage;
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (!TextUtils.isEmpty(str) && (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.name.equals(str)) {
                    return vpnProfile.ipsecSecret.intern();
                }
            }
        }
        return null;
    }

    public synchronized boolean setCaCertificate(ContextInfo contextInfo, String str, String str2) {
        boolean z;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        z = false;
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            int profileIndexFromName = getProfileIndexFromName(str);
            ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
            if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
                VpnProfile vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName);
                vpnProfile.ipsecCaCert = str2;
                try {
                    replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
                    z = true;
                } catch (IOException unused) {
                    return false;
                }
            }
        }
        return z;
    }

    public synchronized String getCaCertificate(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.name.equals(str)) {
                    return vpnProfile.ipsecCaCert;
                }
            }
        }
        return null;
    }

    public synchronized boolean setUserCertificate(ContextInfo contextInfo, String str, String str2) {
        boolean z;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        z = false;
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            int profileIndexFromName = getProfileIndexFromName(str);
            ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
            if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
                VpnProfile vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName);
                vpnProfile.ipsecUserCert = str2;
                try {
                    replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
                    z = true;
                } catch (IOException unused) {
                    return false;
                }
            }
        }
        return z;
    }

    public synchronized boolean deleteProfile(ContextInfo contextInfo, String str) {
        ArrayList retrieveVpnListFromStorage;
        ContextInfo enforceOwnerOnlyAndKnoxVpnPermission = enforceOwnerOnlyAndKnoxVpnPermission(contextInfo);
        if (isKnoxVpnProfile(str)) {
            Log.d("VpnPolicy", "cannot delete knox vpn profile through this API " + str);
            return false;
        }
        try {
            if (!TextUtils.isEmpty(str) && (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) != null && retrieveVpnListFromStorage.size() > 0) {
                for (int i = 0; i < retrieveVpnListFromStorage.size(); i++) {
                    if (((VpnProfile) retrieveVpnListFromStorage.get(i)).name.equals(str)) {
                        VpnProfile vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(i);
                        if (this.mVpnManager == null) {
                            getVpnManager();
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            VpnManager vpnManager = this.mVpnManager;
                            if (vpnManager != null) {
                                LegacyVpnInfo legacyVpnInfo = vpnManager.getLegacyVpnInfo(UserHandle.myUserId());
                                this.mInfo = legacyVpnInfo;
                                if (legacyVpnInfo != null && legacyVpnInfo.state != 0) {
                                    disconnect();
                                }
                            }
                            String alwaysOnProfile = getAlwaysOnProfile(enforceOwnerOnlyAndKnoxVpnPermission);
                            Log.v("VpnPolicy", "alwaysOnProfile " + alwaysOnProfile);
                            if (alwaysOnProfile != null && alwaysOnProfile.equals(str)) {
                                Log.v("VpnPolicy", "clearing enterprise db");
                                saveAlwaysOnProfileToDb(enforceOwnerOnlyAndKnoxVpnPermission, null);
                                releaseAlwaysOnVPNLockdown(enforceOwnerOnlyAndKnoxVpnPermission);
                                VpnManager vpnManager2 = this.mVpnManager;
                                if (vpnManager2 != null) {
                                    vpnManager2.updateLockdownVpn();
                                }
                            }
                            removeProfileFromStorage(vpnProfile);
                            return true;
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("VpnPolicy", "Error deleting VPN profile: " + e.getMessage());
        }
        return false;
    }

    public synchronized String getUserCertificate(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.name.equals(str)) {
                    return vpnProfile.ipsecUserCert;
                }
            }
        }
        return null;
    }

    public synchronized boolean setL2TPSecret(ContextInfo contextInfo, String str, boolean z, String str2) {
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (!TextUtils.isEmpty(str)) {
            int profileIndexFromName = getProfileIndexFromName(str);
            ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
            if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
                VpnProfile vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName);
                if (vpnProfile == null) {
                    return false;
                }
                if (z && !TextUtils.isEmpty(str2)) {
                    vpnProfile.l2tpSecret = str2;
                } else {
                    if (z) {
                        return false;
                    }
                    vpnProfile.l2tpSecret = "";
                }
                try {
                    replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public synchronized String getL2TPSecret(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
            for (int i = 0; i < retrieveVpnListFromStorage.size(); i++) {
                if (((VpnProfile) retrieveVpnListFromStorage.get(i)).name.equals(str)) {
                    return ((VpnProfile) retrieveVpnListFromStorage.get(i)).l2tpSecret;
                }
            }
        }
        return null;
    }

    public boolean isL2TPSecretEnabled(ContextInfo contextInfo, String str) {
        ArrayList retrieveVpnListFromStorage;
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (!TextUtils.isEmpty(str) && (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) != null && retrieveVpnListFromStorage.size() > 0) {
            for (int i = 0; i < retrieveVpnListFromStorage.size(); i++) {
                if (((VpnProfile) retrieveVpnListFromStorage.get(i)).name.equals(str)) {
                    return !TextUtils.isEmpty(((VpnProfile) retrieveVpnListFromStorage.get(i)).l2tpSecret);
                }
            }
        }
        return false;
    }

    public synchronized boolean setEncryptionEnabledForPPTP(ContextInfo contextInfo, String str, boolean z) {
        VpnProfile vpnProfile;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int profileIndexFromName = getProfileIndexFromName(str);
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage == null || retrieveVpnListFromStorage.size() <= 0 || (vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName)) == null || vpnProfile.type != 0) {
            return false;
        }
        vpnProfile.mppe = z;
        try {
            replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPPTPEncryptionEnabled(ContextInfo contextInfo, String str) {
        ArrayList retrieveVpnListFromStorage;
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (!TextUtils.isEmpty(str) && (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) != null && retrieveVpnListFromStorage.size() > 0) {
            for (int i = 0; i < retrieveVpnListFromStorage.size(); i++) {
                if (((VpnProfile) retrieveVpnListFromStorage.get(i)).name.equals(str)) {
                    if (((VpnProfile) retrieveVpnListFromStorage.get(i)).type == 0) {
                        return ((VpnProfile) retrieveVpnListFromStorage.get(i)).mppe;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isAdminProfile(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        List<String> stringList = this.mEDMStorageProvider.getStringList("VPN", "VpnID");
        if (stringList == null || str == null) {
            return false;
        }
        for (String str2 : stringList) {
            if (str2 != null && str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public String[] getVPNList(ContextInfo contextInfo) {
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage == null || retrieveVpnListFromStorage.size() <= 0) {
            return null;
        }
        String[] strArr = new String[retrieveVpnListFromStorage.size()];
        int i = 0;
        for (int i2 = 0; i2 < retrieveVpnListFromStorage.size(); i2++) {
            strArr[i] = ((VpnProfile) retrieveVpnListFromStorage.get(i2)).name;
            i++;
        }
        return strArr;
    }

    public List getAllVpnSettingsProfiles(ContextInfo contextInfo) {
        enforceOwnerOnlyAndKnoxVpnPermission(contextInfo);
        try {
            ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
            if (retrieveVpnListFromStorage == null) {
                return null;
            }
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (KnoxVpnHelper.getInstance(this.mContext).isKnoxVpnProfile(vpnProfile.name)) {
                    retrieveVpnListFromStorage.remove(vpnProfile);
                }
            }
            return getJsonResultFromSettingsVpnProfiles(retrieveVpnListFromStorage);
        } catch (Exception e) {
            Log.e("VpnPolicy", "getAllSettingsVpnProfiles exception result is " + e.getMessage());
            return null;
        }
    }

    public final ArrayList retrieveVpnListFromStorage() {
        VpnProfile decode;
        ArrayList arrayList = new ArrayList();
        String[] list = LegacyVpnProfileStore.list("VPN_");
        if (list != null && list.length > 0) {
            for (String str : list) {
                if (!TextUtils.isEmpty(str)) {
                    byte[] bArr = LegacyVpnProfileStore.get("VPN_" + str);
                    if (bArr != null && (decode = decode(str, bArr)) != null) {
                        arrayList.add(decode);
                    }
                }
            }
        }
        return arrayList;
    }

    public synchronized boolean createProfile(ContextInfo contextInfo, VpnAdminProfile vpnAdminProfile) {
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (vpnAdminProfile == null) {
            return false;
        }
        try {
            if (!TextUtils.isEmpty(vpnAdminProfile.profileName) && !TextUtils.isEmpty(vpnAdminProfile.serverName) && vpnAdminProfile.profileName.length() <= 32 && TextUtils.isEmpty(getId(enforceOwnerOnlyAndVpnPermission, vpnAdminProfile.profileName))) {
                if (!isProfileTypeAllowed(vpnAdminProfile)) {
                    Log.e("VpnPolicy", "Vpn type not allowed by CCMode");
                    return false;
                }
                VpnProfile vpnProfile = new VpnProfile(Long.toHexString(setProfileId()));
                vpnProfile.name = vpnAdminProfile.profileName;
                vpnProfile.server = vpnAdminProfile.serverName;
                vpnProfile.saveLogin = !TextUtils.isEmpty(vpnAdminProfile.userName);
                StringBuilder sb = new StringBuilder("");
                for (String str : vpnAdminProfile.dnsServers) {
                    if (str == null) {
                        return false;
                    }
                    sb.append(str);
                    sb.append(" ");
                }
                String sb2 = sb.toString();
                if (sb2.length() > 1) {
                    sb2 = sb2.substring(0, sb2.length() - 1);
                }
                if (sb2.length() > 0 && !validateAddresses(sb2, false)) {
                    return false;
                }
                vpnProfile.dnsServers = sb2;
                StringBuilder sb3 = new StringBuilder("");
                for (String str2 : vpnAdminProfile.forwardRoutes) {
                    if (str2 == null) {
                        return false;
                    }
                    sb3.append(str2);
                    sb3.append(" ");
                }
                String sb4 = sb3.toString();
                if (sb4.length() > 1) {
                    sb4 = sb4.substring(0, sb4.length() - 1);
                }
                if (sb4.length() > 0 && !validateAddresses(sb4, true)) {
                    return false;
                }
                vpnProfile.routes = sb4;
                StringBuilder sb5 = new StringBuilder("");
                for (String str3 : vpnAdminProfile.searchDomains) {
                    if (str3 == null) {
                        return false;
                    }
                    sb5.append(str3);
                    sb5.append(" ");
                }
                String sb6 = sb5.toString();
                if (sb6.length() > 1) {
                    sb6 = sb6.substring(0, sb6.length() - 1);
                }
                vpnProfile.searchDomains = sb6;
                String str4 = vpnAdminProfile.userName;
                if (str4 != null) {
                    vpnProfile.username = str4;
                }
                String str5 = vpnAdminProfile.userPassword;
                if (str5 != null) {
                    vpnProfile.password = str5;
                }
                if (vpnAdminProfile.vpnType.equals("IPSEC_IKEV2_PSK")) {
                    if (TextUtils.isEmpty(vpnAdminProfile.ipsecPreSharedKey)) {
                        return false;
                    }
                    vpnProfile.type = 6;
                    vpnProfile.ipsecSecret = vpnAdminProfile.ipsecPreSharedKey.intern();
                    vpnProfile.ipsecIdentifier = vpnAdminProfile.ipsecIdentifier;
                } else {
                    if (!vpnAdminProfile.vpnType.equals("IPSEC_IKEV2_RSA")) {
                        return false;
                    }
                    if (!TextUtils.isEmpty(vpnAdminProfile.ipsecUserCertificate) && !TextUtils.isEmpty(vpnAdminProfile.ipsecCaCertificate)) {
                        vpnProfile.type = 7;
                        vpnProfile.ipsecUserCert = vpnAdminProfile.ipsecUserCertificate;
                        vpnProfile.ipsecCaCert = vpnAdminProfile.ipsecCaCertificate;
                        String str6 = vpnAdminProfile.ocspServerUrl;
                        if (str6 != null) {
                            if (!validateUrl(str6)) {
                                return false;
                            }
                            vpnProfile.ocspServerUrl = vpnAdminProfile.ocspServerUrl;
                        }
                    }
                    return false;
                }
                int profileIndexFromName = getProfileIndexFromName(vpnProfile.name);
                if (profileIndexFromName >= 0 || checkDuplicateName(vpnProfile, profileIndexFromName)) {
                    return false;
                }
                return saveProfileToStorage(enforceOwnerOnlyAndVpnPermission, vpnProfile);
            }
            return false;
        } catch (IOException e) {
            Log.e("VpnPolicy", "createProfile exception: " + e.getMessage());
            return false;
        }
    }

    public boolean setDnsServers(ContextInfo contextInfo, String str, List list) {
        return setProfileProperty(contextInfo, str, list, 0);
    }

    public List getDnsServers(ContextInfo contextInfo, String str) {
        return getProfileProperty(contextInfo, str, 0);
    }

    public boolean setDnsDomains(ContextInfo contextInfo, String str, List list) {
        return setProfileProperty(contextInfo, str, list, 1);
    }

    public List getDnsDomains(ContextInfo contextInfo, String str) {
        return getProfileProperty(contextInfo, str, 1);
    }

    public boolean setForwardRoutes(ContextInfo contextInfo, String str, List list) {
        return setProfileProperty(contextInfo, str, list, 2);
    }

    public List getForwardRoutes(ContextInfo contextInfo, String str) {
        return getProfileProperty(contextInfo, str, 2);
    }

    public final boolean setProfileProperty(ContextInfo contextInfo, String str, List list, int i) {
        VpnProfile vpnProfile;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (!TextUtils.isEmpty(str) && list != null) {
            StringBuilder sb = new StringBuilder("");
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (str2 == null) {
                    return false;
                }
                sb.append(str2);
                sb.append(" ");
            }
            String sb2 = sb.toString();
            if (sb2.length() > 1) {
                sb2 = sb2.substring(0, sb2.length() - 1);
            }
            if (i == 0 && sb2.length() > 0 && !validateAddresses(sb2, false)) {
                return false;
            }
            if (i == 2 && sb2.length() > 0 && !validateAddresses(sb2, true)) {
                return false;
            }
            if (i == 3 && sb2.length() > 0 && !validateUrl(sb2)) {
                return false;
            }
            int profileIndexFromName = getProfileIndexFromName(str);
            ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
            if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0 && (vpnProfile = (VpnProfile) retrieveVpnListFromStorage.get(profileIndexFromName)) != null) {
                if (i == 0) {
                    vpnProfile.dnsServers = sb2;
                } else if (i == 1) {
                    vpnProfile.searchDomains = sb2;
                } else if (i == 2) {
                    vpnProfile.routes = sb2;
                } else if (i == 3) {
                    vpnProfile.ocspServerUrl = sb2;
                }
                try {
                    replaceProfile(enforceOwnerOnlyAndVpnPermission, profileIndexFromName, vpnProfile);
                    return true;
                } catch (IOException unused) {
                    Log.w("VpnPolicy", "VpnInfoPolicy.setProfileProperty() - Error to save profile !");
                }
            }
        }
        return false;
    }

    public final List getProfileProperty(ContextInfo contextInfo, String str, int i) {
        ArrayList retrieveVpnListFromStorage;
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (TextUtils.isEmpty(str) || (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) == null || retrieveVpnListFromStorage.size() <= 0) {
            return null;
        }
        Iterator it = retrieveVpnListFromStorage.iterator();
        while (it.hasNext()) {
            VpnProfile vpnProfile = (VpnProfile) it.next();
            if (vpnProfile.name.equals(str)) {
                ArrayList arrayList = new ArrayList();
                int i2 = 0;
                if (i == 0) {
                    String[] split = vpnProfile.dnsServers.split(" ");
                    int length = split.length;
                    while (i2 < length) {
                        arrayList.add(split[i2]);
                        i2++;
                    }
                } else if (i == 1) {
                    String[] split2 = vpnProfile.searchDomains.split(" ");
                    int length2 = split2.length;
                    while (i2 < length2) {
                        arrayList.add(split2[i2]);
                        i2++;
                    }
                } else if (i == 2) {
                    String[] split3 = vpnProfile.routes.split(" ");
                    int length3 = split3.length;
                    while (i2 < length3) {
                        arrayList.add(split3[i2]);
                        i2++;
                    }
                } else if (i == 3) {
                    arrayList.add(vpnProfile.ocspServerUrl);
                }
                return arrayList;
            }
        }
        return null;
    }

    public final boolean validateAddresses(String str, boolean z) {
        int i;
        try {
            for (String str2 : str.split(" ")) {
                if (!str2.isEmpty()) {
                    if (z) {
                        String[] split = str2.split("/", 2);
                        String str3 = split[0];
                        i = Integer.parseInt(split[1]);
                        str2 = str3;
                    } else {
                        i = 32;
                    }
                    byte[] address = InetAddress.parseNumericAddress(str2).getAddress();
                    int i2 = ((address[1] & 255) << 16) | ((address[2] & 255) << 8) | (address[3] & 255) | ((address[0] & 255) << 24);
                    if (address.length != 4 || i < 0 || i > 32 || (i < 32 && (i2 << i) != 0)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean setIpSecIdentifier(ContextInfo contextInfo, String str, String str2) {
        ArrayList retrieveVpnListFromStorage;
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (!TextUtils.isEmpty(str) && str2 != null && (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) != null && retrieveVpnListFromStorage.size() > 0) {
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (vpnProfile.name.equals(str)) {
                    vpnProfile.ipsecIdentifier = str2;
                    try {
                        replaceProfile(enforceOwnerOnlyAndVpnPermission, getProfileIndexFromName(str), vpnProfile);
                        return true;
                    } catch (Exception unused) {
                        Log.w("VpnPolicy", "VpnInfoPolicy.setIpSecIdentifier() - failed to save profile !");
                    }
                }
            }
        }
        return false;
    }

    public String getIpSecIdentifier(ContextInfo contextInfo, String str) {
        ArrayList retrieveVpnListFromStorage;
        enforceOwnerOnlyAndVpnPermission(contextInfo);
        if (TextUtils.isEmpty(str) || (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) == null || retrieveVpnListFromStorage.size() <= 0) {
            return null;
        }
        Iterator it = retrieveVpnListFromStorage.iterator();
        while (it.hasNext()) {
            VpnProfile vpnProfile = (VpnProfile) it.next();
            if (vpnProfile.name.equals(str)) {
                return vpnProfile.ipsecIdentifier;
            }
        }
        return null;
    }

    public boolean allowOnlySecureConnections(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission = enforceOwnerOnlyAndAdvancedRestrictionPermission(contextInfo);
        ArrayList<VpnProfile> retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (!retrieveVpnListFromStorage.isEmpty()) {
            for (VpnProfile vpnProfile : retrieveVpnListFromStorage) {
                String type = getType(enforceOwnerOnlyAndAdvancedRestrictionPermission, vpnProfile.name);
                String state = getState(enforceOwnerOnlyAndAdvancedRestrictionPermission, vpnProfile.name);
                if (type != null && type.equals("PPTP") && state != null && state.equals("CONNECTED")) {
                    disconnect();
                }
            }
        }
        return this.mEDMStorageProvider.putBoolean(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid, "RESTRICTION", "allowOnlySecureVPN", z);
    }

    public boolean isOnlySecureConnectionsAllowed(ContextInfo contextInfo) {
        enforceOwnerOnlyAndAdvancedRestrictionPermission(contextInfo);
        return this.mEDMStorageProvider.getBooleanList("RESTRICTION", "allowOnlySecureVPN").contains(Boolean.TRUE);
    }

    public boolean checkRacoonSecurity(ContextInfo contextInfo, String[] strArr) {
        String str;
        String str2;
        boolean isOnlySecureConnectionsAllowed = isOnlySecureConnectionsAllowed(enforceVpnPermission(contextInfo));
        boolean z = true;
        if (isOnlySecureConnectionsAllowed) {
            if (strArr == null || strArr.length <= 1 || (((str = strArr[2]) == null || (!str.equals("udprsa") && !strArr[2].equals("hybridrsa") && !strArr[2].equals("udppsk"))) && ((str2 = strArr[1]) == null || (!str2.equals("xauthrsa") && !strArr[1].equals("xauthpsk") && !strArr[1].equals("ikev2psk") && !strArr[1].equals("ikev2rsa"))))) {
                z = false;
            }
            if (!z) {
                RestrictionToastManager.show(17043240);
            }
        }
        return z;
    }

    public final int getProfileIndexFromName(String str) {
        try {
            ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
            if (retrieveVpnListFromStorage == null || retrieveVpnListFromStorage.size() <= 0) {
                return -1;
            }
            for (int i = 0; i < retrieveVpnListFromStorage.size(); i++) {
                if (((VpnProfile) retrieveVpnListFromStorage.get(i)).name.equals(str)) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final boolean checkDuplicateName(VpnProfile vpnProfile, int i) {
        if (i > 0) {
            try {
                ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
                if (retrieveVpnListFromStorage != null && retrieveVpnListFromStorage.size() > 0) {
                    for (int i2 = 0; i2 < retrieveVpnListFromStorage.size(); i2++) {
                        if (vpnProfile.name.equals(((VpnProfile) retrieveVpnListFromStorage.get(i2)).name)) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public final long setProfileId() {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            String[] list = LegacyVpnProfileStore.list("VPN_");
            if (list != null) {
                int i = 0;
                while (i < list.length) {
                    if (list[i].equals(String.valueOf(currentTimeMillis))) {
                        currentTimeMillis++;
                        i = 0;
                    }
                    i++;
                }
            }
            return currentTimeMillis;
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public final boolean saveProfileToStorage(ContextInfo contextInfo, VpnProfile vpnProfile) {
        try {
            if (LegacyVpnProfileStore.put("VPN_" + vpnProfile.key, vpnProfile.encode(true))) {
                int callingUid = Binder.getCallingUid();
                String userName = getUserName(contextInfo, vpnProfile.name);
                String userPwd = getUserPwd(contextInfo, vpnProfile.name);
                String[] strArr = {"adminUid", "VpnID"};
                String[] strArr2 = {String.valueOf(callingUid), vpnProfile.key};
                ContentValues contentValues = new ContentValues();
                contentValues.put("adminUid", Integer.valueOf(callingUid));
                contentValues.put("VpnID", vpnProfile.key);
                contentValues.put("UsrName", userName);
                contentValues.put("UsrPwd", userPwd);
                return this.mEDMStorageProvider.putDataByFields("VPN", strArr, strArr2, contentValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public final void removeProfileFromStorage(VpnProfile vpnProfile) {
        try {
            this.mEDMStorageProvider.deleteDataByFields("VPN", new String[]{"VpnID"}, new String[]{vpnProfile.key});
            LegacyVpnProfileStore.remove("VPN_" + vpnProfile.key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void replaceProfile(ContextInfo contextInfo, int i, VpnProfile vpnProfile) {
        ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
        if (retrieveVpnListFromStorage == null || retrieveVpnListFromStorage.size() <= 0) {
            return;
        }
        LegacyVpnProfileStore.remove("VPN_" + ((VpnProfile) retrieveVpnListFromStorage.get(i)).key);
        if (((VpnProfile) retrieveVpnListFromStorage.set(i, vpnProfile)) != null) {
            saveProfileToStorage(contextInfo, vpnProfile);
        }
    }

    public synchronized boolean setVpnProfile(String str) {
        enforceOwnerOnlyAndVpnPermission(null);
        return false;
    }

    public final synchronized void getVpnManager() {
        try {
            this.mVpnManager = (VpnManager) this.mContext.getSystemService(VpnManager.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final synchronized void disconnect() {
        try {
            if (this.mVpnManager == null) {
                getVpnManager();
            }
            if (this.mVpnManager != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mVpnManager.prepareVpn("[Legacy VPN]", "[Legacy VPN]", UserHandle.myUserId());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectActiveVpnConnections() {
        disconnect();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean setAlwaysOnProfile(ContextInfo contextInfo, String str) {
        boolean releaseAlwaysOnVPNLockdown;
        long clearCallingIdentity;
        Log.d("VpnPolicy", "setAlwaysOnProfile - " + str);
        ContextInfo enforceOwnerOnlyAndVpnPermission = enforceOwnerOnlyAndVpnPermission(contextInfo);
        boolean z = false;
        if (canChangeAlwaysOn(enforceOwnerOnlyAndVpnPermission)) {
            if (str == null || TextUtils.isEmpty(str)) {
                releaseAlwaysOnVPNLockdown = releaseAlwaysOnVPNLockdown(enforceOwnerOnlyAndVpnPermission);
            } else {
                VpnProfile profileByName = getProfileByName(str);
                if (profileByName != null && isValidAlwaysOnProfile(profileByName)) {
                    releaseAlwaysOnVPNLockdown = LegacyVpnProfileStore.put("LOCKDOWN_VPN", profileByName.key.getBytes());
                    if (releaseAlwaysOnVPNLockdown) {
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            AuditLog.logAsUser(5, 1, true, Process.myPid(), "VpnPolicy", String.format("Admin %d has enabled %s VPN profile to Always On mode.", Integer.valueOf(enforceOwnerOnlyAndVpnPermission.mCallerUid), str), UserHandle.getUserId(enforceOwnerOnlyAndVpnPermission.mCallerUid));
                        } finally {
                        }
                    }
                }
                if (z) {
                    z = saveAlwaysOnProfileToDb(enforceOwnerOnlyAndVpnPermission, str);
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        if (this.mVpnManager == null) {
                            getVpnManager();
                        }
                        VpnManager vpnManager = this.mVpnManager;
                        if (vpnManager != null) {
                            vpnManager.updateLockdownVpn();
                        }
                    } finally {
                    }
                }
            }
            z = releaseAlwaysOnVPNLockdown;
            if (z) {
            }
        }
        return z;
    }

    public final boolean releaseAlwaysOnVPNLockdown(ContextInfo contextInfo) {
        boolean remove = LegacyVpnProfileStore.remove("LOCKDOWN_VPN");
        if (remove) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "VpnPolicy", String.format("Admin %d has disabled VPN Always On mode.", Integer.valueOf(contextInfo.mCallerUid)), UserHandle.getUserId(contextInfo.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return remove;
    }

    public String getAlwaysOnProfile(ContextInfo contextInfo) {
        ArrayList<VpnProfile> retrieveVpnListFromStorage;
        Log.d("VpnPolicy", "getAlwaysOnProfile");
        byte[] bArr = LegacyVpnProfileStore.get("LOCKDOWN_VPN");
        if (bArr != null && (retrieveVpnListFromStorage = retrieveVpnListFromStorage()) != null && retrieveVpnListFromStorage.size() > 0) {
            String str = new String(bArr);
            for (VpnProfile vpnProfile : retrieveVpnListFromStorage) {
                if (TextUtils.equals(vpnProfile.key, str)) {
                    return vpnProfile.name;
                }
            }
        }
        return null;
    }

    public boolean allowUserSetAlwaysOn(ContextInfo contextInfo, boolean z) {
        Log.d("VpnPolicy", "allowUserSetAlwaysOn");
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission = enforceOwnerOnlyAndAdvancedRestrictionPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "VpnPolicy", String.format(z ? "Admin %d has allowed user to set VPN Always On mode." : "Admin %d has disallowed user to set VPN Always On mode.", Integer.valueOf(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return this.mEDMStorageProvider.putBoolean(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid, "VPN", "allowUserSetAlwaysOn", z);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean isUserSetAlwaysOnAllowed(ContextInfo contextInfo, boolean z) {
        Log.d("VpnPolicy", "isUserSetAlwaysOnAllowed");
        boolean z2 = !this.mEDMStorageProvider.getBooleanList("VPN", "allowUserSetAlwaysOn").contains(Boolean.FALSE);
        if (z && !z2) {
            RestrictionToastManager.show(17042442);
        }
        return z2;
    }

    public boolean allowUserChangeProfiles(ContextInfo contextInfo, boolean z) {
        Log.d("VpnPolicy", "allowUserChangeProfiles");
        return this.mEDMStorageProvider.putBoolean(enforceOwnerOnlyAndVpnPermission(contextInfo).mCallerUid, "VPN", "allowUserChangeProfiles", z);
    }

    public boolean isUserChangeProfilesAllowed(ContextInfo contextInfo, boolean z) {
        Log.d("VpnPolicy", "isUserChangeProfilesAllowed");
        boolean z2 = !this.mEDMStorageProvider.getBooleanList("VPN", "allowUserChangeProfiles").contains(Boolean.FALSE);
        if (z && !z2) {
            RestrictionToastManager.show(17042441);
        }
        return z2;
    }

    public boolean allowUserAddProfiles(ContextInfo contextInfo, boolean z) {
        Log.d("VpnPolicy", "allowUserAddProfiles");
        return this.mEDMStorageProvider.putBoolean(enforceOwnerOnlyAndVpnPermission(contextInfo).mCallerUid, "VPN", "allowUserAddProfiles", z);
    }

    public boolean isUserAddProfilesAllowed(ContextInfo contextInfo, boolean z) {
        Log.d("VpnPolicy", "isUserAddProfilesAllowed");
        boolean z2 = !this.mEDMStorageProvider.getBooleanList("VPN", "allowUserAddProfiles").contains(Boolean.FALSE);
        if (z && !z2) {
            RestrictionToastManager.show(17042440);
        }
        return z2;
    }

    public final boolean isValidAlwaysOnProfile(VpnProfile vpnProfile) {
        return (vpnProfile == null || vpnProfile.type == 0 || !vpnProfile.isValidLockdownProfile()) ? false : true;
    }

    public final boolean isKnoxVpnProfile(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("profileName", str);
        return this.mEDMStorageProvider.getCount("VpnProfileInfo", contentValues) > 0;
    }

    public final boolean canChangeAlwaysOn(ContextInfo contextInfo) {
        String genericValueAsUser = this.mEDMStorageProvider.getGenericValueAsUser("vpnAlwaysOnProfile", 0);
        return genericValueAsUser == null || TextUtils.isEmpty(genericValueAsUser) || String.valueOf(contextInfo.mCallerUid).equals(this.mEDMStorageProvider.getGenericValueAsUser("vpnAlwaysOnOwner", 0));
    }

    public final boolean saveAlwaysOnProfileToDb(ContextInfo contextInfo, String str) {
        if (str == null) {
            str = "";
        }
        return this.mEDMStorageProvider.putGenericValueAsUser("vpnAlwaysOnOwner", String.valueOf(contextInfo.mCallerUid), 0) & this.mEDMStorageProvider.putGenericValueAsUser("vpnAlwaysOnProfile", str, 0);
    }

    public final VpnProfile getProfileByName(String str) {
        if (str == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            ArrayList retrieveVpnListFromStorage = retrieveVpnListFromStorage();
            if (retrieveVpnListFromStorage == null || retrieveVpnListFromStorage.size() <= 0) {
                return null;
            }
            Iterator it = retrieveVpnListFromStorage.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                if (str.equals(vpnProfile.name)) {
                    return vpnProfile;
                }
            }
            return null;
        } catch (Exception e) {
            Log.e("VpnPolicy", "Error in getProfileByName(" + str + "): " + e.getMessage());
            return null;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        ContextInfo contextInfo = new ContextInfo(i);
        if (canChangeAlwaysOn(new ContextInfo(i))) {
            saveAlwaysOnProfileToDb(contextInfo, null);
        }
    }

    public boolean setOcspServerUrl(ContextInfo contextInfo, String str, String str2) {
        ArrayList arrayList = new ArrayList(1);
        if (str2 == null) {
            str2 = "";
        }
        arrayList.add(str2);
        return setProfileProperty(contextInfo, str, arrayList, 3);
    }

    public String getOcspServerUrl(ContextInfo contextInfo, String str) {
        List profileProperty = getProfileProperty(contextInfo, str, 3);
        if (profileProperty == null || profileProperty.isEmpty()) {
            return null;
        }
        return (String) profileProperty.get(0);
    }

    public List getSupportedConnectionTypes(ContextInfo contextInfo) {
        ArrayList arrayList = new ArrayList(2);
        arrayList.add("IPSEC_IKEV2_PSK");
        arrayList.add("IPSEC_IKEV2_RSA");
        return arrayList;
    }

    public final boolean validateUrl(String str) {
        if (str == null || str.length() <= 0) {
            return true;
        }
        try {
            new URL(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public final boolean isProfileTypeAllowed(VpnAdminProfile vpnAdminProfile) {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null || !restrictionPolicy.isCCModeEnabled(null, false)) {
            return true;
        }
        String str = vpnAdminProfile.vpnType;
        str.hashCode();
        switch (str) {
            case "IPSEC_XAUTH_PSK":
            case "IPSEC_XAUTH_RSA":
            case "IPSEC_IKEV2_PSK":
            case "IPSEC_IKEV2_RSA":
                return true;
            default:
                return false;
        }
    }

    public final List getJsonResultFromSettingsVpnProfiles(List list) {
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                VpnProfile vpnProfile = (VpnProfile) it.next();
                VpnProfile.decrypt(vpnProfile);
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("name", vpnProfile.name).put("server", vpnProfile.server).put("username", vpnProfile.username).put("password", vpnProfile.password).put("mppe", vpnProfile.mppe).put("searchDomains", vpnProfile.searchDomains).put("dnsServer", vpnProfile.dnsServers).put("frwRoutes", vpnProfile.routes).put("type", vpnProfile.type).put("l2tp_secret", vpnProfile.l2tpSecret).put("ipsec_identifier", vpnProfile.ipsecIdentifier).put("ipsec_pre_shared_key", vpnProfile.ipsecSecret).put("ipsec_user_certificate", vpnProfile.ipsecUserCert).put("ipsec_server_certificate", vpnProfile.ipsecServerCert).put("ipsec_ca_certificate", vpnProfile.ipsecCaCert).put("ocspServerUrl", vpnProfile.ocspServerUrl);
                jSONObject.putOpt("ANDROID_VPN_PARAMETERS", jSONObject2);
                arrayList.add(jSONObject.toString());
            }
        } catch (JSONException e) {
            Log.e("VpnPolicy", "getJsonResultFromSettingsVpnProfiles exception result is " + e.getMessage());
        }
        return arrayList;
    }
}
