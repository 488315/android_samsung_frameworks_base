package com.android.server.enterprise.vpn.knoxvpn;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.KnoxVpnContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxVpnApiValidation {
    public static Context mContext;
    public static EdmStorageProvider mEdmStorageProvider;
    public static KnoxVpnApiValidation mKnoxVpnApiValidation;
    public static SemPersonaManager mPersonaManager;
    public KnoxVpnHelper mKnoxVpnHelper;
    public UserManager mUserMgr;
    public VpnProfileConfig vpnConfig;

    static {
        Debug.semIsProductDev();
        mKnoxVpnApiValidation = null;
        mPersonaManager = null;
        mEdmStorageProvider = null;
        mContext = null;
    }

    public static SemPersonaManager getPersonaManager() {
        if (mPersonaManager == null) {
            mPersonaManager = (SemPersonaManager) mContext.getSystemService("persona");
        }
        return mPersonaManager;
    }

    public final int activateVpnProfileValidation(KnoxVpnContext knoxVpnContext, String str, boolean z) {
        int i;
        if (str == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at activateVpnProfileValidation API "), "KnoxVpnApiValidation");
            i = 101;
        }
        if (knoxVpnContext.vendorName == null) {
            return 104;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return 108;
        }
        if (profileEntry.admin_id != knoxVpnContext.adminId) {
            return 112;
        }
        if (profileEntry.routeType == 0) {
            return 109;
        }
        if (z) {
            if (profileEntry.activateState == 1) {
                return 115;
            }
        } else if (profileEntry.activateState == 0) {
            return 116;
        }
        i = 100;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "activateVpnProfileValidation : validationResult value is ", "KnoxVpnApiValidation");
        return i;
    }

    public final int addContainerPackagesToVpnValidation(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
        int i2;
        if (str == null || strArr == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at addContainerPackagesToVpnValidation API "), "KnoxVpnApiValidation");
            i2 = 101;
        }
        if (strArr.length >= 1 && knoxVpnContext.vendorName != null) {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return 108;
            }
            if (profileEntry.admin_id != knoxVpnContext.adminId) {
                return 112;
            }
            int userId = UserHandle.getUserId(profileEntry.admin_id);
            int personaId = knoxVpnContext.getPersonaId();
            if (userId != 0 && personaId == 0) {
                Log.e("KnoxVpnApiValidation", "EMM present in work profile trying to configure vpn for user 0");
                return 114;
            }
            if (profileEntry.routeType == 0) {
                return 109;
            }
            if (!isUserTypeAppSeparation(i)) {
                if (getPersonaManager() != null && !getPersonaManager().exists(i)) {
                    return 113;
                }
                if (UserHandle.getAppId(mEdmStorageProvider.getMUMContainerOwnerUid(i)) != UserHandle.getAppId(knoxVpnContext.adminId)) {
                    return 114;
                }
                if (getPersonaManager() != null && getPersonaManager().exists(knoxVpnContext.getPersonaId()) && profileEntry.personaId != i) {
                    return 113;
                }
            }
            for (String str2 : strArr) {
                this.mKnoxVpnHelper.getClass();
                if (str2.equalsIgnoreCase(KnoxVpnHelper.getPersonifiedName(i, "ADD_ALL_PACKAGES"))) {
                    return 137;
                }
                this.mKnoxVpnHelper.getClass();
                VpnProfileInfo profileEntry2 = this.vpnConfig.getProfileEntry(this.mKnoxVpnHelper.getProfileOwningThePackage(KnoxVpnHelper.getPersonifiedName(i, str2)));
                if (profileEntry2 != null && !profileEntry2.mProfileName.equalsIgnoreCase(str)) {
                    if (profileEntry2.admin_id == knoxVpnContext.adminId) {
                        return 135;
                    }
                    if (profileEntry2.admin_id != knoxVpnContext.adminId) {
                        return 136;
                    }
                }
                if (profileEntry2 != null && profileEntry2.mProfileName.equalsIgnoreCase(str) && profileEntry2.admin_id == knoxVpnContext.adminId) {
                    return 135;
                }
                this.mKnoxVpnHelper.getClass();
                int uIDForPackage = KnoxVpnHelper.getUIDForPackage(i, str2);
                if (uIDForPackage > 0) {
                    String profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(uIDForPackage);
                    if (profileOwningTheUid != null && !profileOwningTheUid.equalsIgnoreCase(str)) {
                        return 139;
                    }
                    String profileOwningTheUidWithNoInternetPermission = this.mKnoxVpnHelper.getProfileOwningTheUidWithNoInternetPermission(uIDForPackage);
                    if (profileOwningTheUidWithNoInternetPermission != null && !profileOwningTheUidWithNoInternetPermission.equalsIgnoreCase(str)) {
                        return 139;
                    }
                }
                this.mKnoxVpnHelper.getClass();
                if (KnoxVpnHelper.checkIfUidIsBlackListed(uIDForPackage, i) || str2.equalsIgnoreCase("com.knox.vpn.proxyhandler")) {
                    return 137;
                }
                if (str2.trim().equals("")) {
                    return 134;
                }
            }
            i2 = 100;
            if (this.mKnoxVpnHelper.isWideVpnExists(i)) {
                if (this.mKnoxVpnHelper.getAdminIdForUserVpn(i) != knoxVpnContext.adminId) {
                    return 120;
                }
                KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                knoxVpnHelper.getClass();
                String profileOwningThePackage = knoxVpnHelper.getProfileOwningThePackage(KnoxVpnHelper.getPersonifiedName(i, "ADD_ALL_PACKAGES"));
                if (profileOwningThePackage != null) {
                    if (!profileOwningThePackage.equalsIgnoreCase(str)) {
                        return this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(profileOwningThePackage) != 1 ? 119 : 100;
                    }
                    if (profileEntry.activateState == 1) {
                        return 115;
                    }
                    HashSet hashSet = profileEntry.mExemptPackageList;
                    if (hashSet.isEmpty()) {
                        return 119;
                    }
                    for (String str3 : strArr) {
                        this.mKnoxVpnHelper.getClass();
                        int uIDForPackage2 = KnoxVpnHelper.getUIDForPackage(i, str3);
                        if (uIDForPackage2 != -1 && hashSet.contains(Integer.valueOf(uIDForPackage2))) {
                            if (profileEntry.mExemptPackageList.contains(Integer.valueOf(uIDForPackage2))) {
                                profileEntry.mExemptPackageList.remove(Integer.valueOf(uIDForPackage2));
                            }
                            this.mKnoxVpnHelper.removeExemptedListToDatabase(uIDForPackage2);
                            Log.d("KnoxVpnApiValidation", "user wide vpn was configured " + i + "removing uid from exempt list " + uIDForPackage2);
                        }
                    }
                    this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
                }
            }
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "addContainerPackagesToVpnValidation : validationResult value is ", "KnoxVpnApiValidation");
            return i2;
        }
        return 104;
    }

    public final int addPackagesToVpnValidation(KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
        int i;
        if (str == null || strArr == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at addPackagesToVpnValidation API "), "KnoxVpnApiValidation");
            i = 101;
        }
        if (strArr.length >= 1 && knoxVpnContext.vendorName != null) {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return 108;
            }
            if (profileEntry.admin_id != knoxVpnContext.adminId) {
                return 112;
            }
            int userId = UserHandle.getUserId(profileEntry.admin_id);
            int personaId = knoxVpnContext.getPersonaId();
            if (userId != 0 && personaId == 0) {
                Log.e("KnoxVpnApiValidation", "EMM present in work profile trying to configure vpn for user 0");
                return 114;
            }
            if (profileEntry.routeType == 0) {
                return 109;
            }
            for (String str2 : strArr) {
                KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                int i2 = knoxVpnContext.personaId;
                knoxVpnHelper.getClass();
                if (str2.equalsIgnoreCase(KnoxVpnHelper.getPersonifiedName(i2, "ADD_ALL_PACKAGES"))) {
                    return 137;
                }
                KnoxVpnHelper knoxVpnHelper2 = this.mKnoxVpnHelper;
                int i3 = knoxVpnContext.personaId;
                knoxVpnHelper2.getClass();
                VpnProfileInfo profileEntry2 = this.vpnConfig.getProfileEntry(this.mKnoxVpnHelper.getProfileOwningThePackage(KnoxVpnHelper.getPersonifiedName(i3, str2)));
                if (profileEntry2 != null && !profileEntry2.mProfileName.equalsIgnoreCase(str)) {
                    if (profileEntry2.admin_id == knoxVpnContext.adminId) {
                        return 135;
                    }
                    if (profileEntry2.admin_id != knoxVpnContext.adminId) {
                        return 136;
                    }
                }
                if (profileEntry2 != null && profileEntry2.mProfileName.equalsIgnoreCase(str) && profileEntry2.admin_id == knoxVpnContext.adminId) {
                    return 135;
                }
                KnoxVpnHelper knoxVpnHelper3 = this.mKnoxVpnHelper;
                int i4 = knoxVpnContext.personaId;
                knoxVpnHelper3.getClass();
                int uIDForPackage = KnoxVpnHelper.getUIDForPackage(i4, str2);
                if (uIDForPackage > 0) {
                    String profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(uIDForPackage);
                    if (profileOwningTheUid != null && !profileOwningTheUid.equalsIgnoreCase(str)) {
                        return 139;
                    }
                    String profileOwningTheUidWithNoInternetPermission = this.mKnoxVpnHelper.getProfileOwningTheUidWithNoInternetPermission(uIDForPackage);
                    if (profileOwningTheUidWithNoInternetPermission != null && !profileOwningTheUidWithNoInternetPermission.equalsIgnoreCase(str)) {
                        return 139;
                    }
                }
                KnoxVpnHelper knoxVpnHelper4 = this.mKnoxVpnHelper;
                int i5 = knoxVpnContext.personaId;
                knoxVpnHelper4.getClass();
                if (KnoxVpnHelper.checkIfUidIsBlackListed(uIDForPackage, i5) || str2.equalsIgnoreCase("com.knox.vpn.proxyhandler")) {
                    return 137;
                }
                if (str2.trim().equals("")) {
                    return 134;
                }
            }
            i = 100;
            if (this.mKnoxVpnHelper.isWideVpnExists(knoxVpnContext.personaId)) {
                if (this.mKnoxVpnHelper.getAdminIdForUserVpn(knoxVpnContext.personaId) != knoxVpnContext.adminId) {
                    return 120;
                }
                KnoxVpnHelper knoxVpnHelper5 = this.mKnoxVpnHelper;
                int i6 = knoxVpnContext.personaId;
                knoxVpnHelper5.getClass();
                String profileOwningThePackage = knoxVpnHelper5.getProfileOwningThePackage(KnoxVpnHelper.getPersonifiedName(i6, "ADD_ALL_PACKAGES"));
                if (profileOwningThePackage != null) {
                    if (!profileOwningThePackage.equalsIgnoreCase(str)) {
                        return this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(profileOwningThePackage) != 1 ? 119 : 100;
                    }
                    if (profileEntry.activateState == 1) {
                        return 115;
                    }
                    HashSet hashSet = profileEntry.mExemptPackageList;
                    if (hashSet.isEmpty()) {
                        return 119;
                    }
                    for (String str3 : strArr) {
                        KnoxVpnHelper knoxVpnHelper6 = this.mKnoxVpnHelper;
                        int i7 = knoxVpnContext.personaId;
                        knoxVpnHelper6.getClass();
                        int uIDForPackage2 = KnoxVpnHelper.getUIDForPackage(i7, str3);
                        if (uIDForPackage2 != -1 && hashSet.contains(Integer.valueOf(uIDForPackage2))) {
                            if (profileEntry.mExemptPackageList.contains(Integer.valueOf(uIDForPackage2))) {
                                profileEntry.mExemptPackageList.remove(Integer.valueOf(uIDForPackage2));
                            }
                            Log.d("KnoxVpnApiValidation", "user wide vpn was configured " + knoxVpnContext.personaId + "removing uid from exempt list " + uIDForPackage2 + " " + this.mKnoxVpnHelper.removeExemptedListToDatabase(uIDForPackage2));
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnApiValidation", "Package name " + str3 + " added to vpn for profile " + str + ". Removed from exempt list", knoxVpnContext.personaId);
                        }
                    }
                    this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
                }
            }
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "addPackagesToVpnValidation : validationResult value is ", "KnoxVpnApiValidation");
            return i;
        }
        return 104;
    }

    public final int allowUsbTetheringValidation(KnoxVpnContext knoxVpnContext, String str, Bundle bundle) {
        String str2;
        String str3;
        int i;
        if (str == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            e = e;
            str2 = "KnoxVpnApiValidation";
        }
        if (knoxVpnContext.vendorName == null) {
            return 104;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return 108;
        }
        str2 = "KnoxVpnApiValidation";
        try {
        } catch (Exception e2) {
            e = e2;
            str3 = str2;
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at enableUsbTethering API "), str3);
            i = 101;
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "enableUsbTethering : validationResult value is ", str3);
            return i;
        }
        if (profileEntry.admin_id != knoxVpnContext.adminId) {
            return 112;
        }
        if (profileEntry.routeType == 0) {
            return 109;
        }
        if (profileEntry.chainingEnabled == 0 || profileEntry.vpnConnectionType == 1 || profileEntry.uidPidSearchEnabled == 1) {
            return 901;
        }
        Collection values = this.vpnConfig.vpnProfileInfoMap.values();
        if (values != null && values.size() > 0) {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                if (vpnProfileInfo.mUsbTethering == 1) {
                    return !str.equalsIgnoreCase(vpnProfileInfo.mProfileName) ? 901 : 100;
                }
            }
        }
        if (bundle != null && !bundle.isEmpty()) {
            KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
            int i2 = knoxVpnContext.personaId;
            knoxVpnHelper.getClass();
            if (!KnoxVpnHelper.isPackageInstalled(i2, "com.samsung.knox.vpn.tether.auth")) {
                Log.e("KnoxVpnTetherAuthentication", "knox vpn usb tether auth application not installed");
                return 901;
            }
            KnoxVpnHelper knoxVpnHelper2 = this.mKnoxVpnHelper;
            int i3 = knoxVpnContext.personaId;
            knoxVpnHelper2.getClass();
            if (!KnoxVpnHelper.checkIfPlatformSigned(i3)) {
                Log.e("KnoxVpnTetherAuthentication", "knox vpn usb tether auth application is not signed with proper key");
                return 901;
            }
            if (bundle.containsKey("key-tether-auth-login-page")) {
                if (bundle.getString("key-tether-auth-login-page") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "loginpage string value is null");
                    return 901;
                }
                if (bundle.getString("key-tether-auth-login-page").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "loginpage string value is empty");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-auth-response-page")) {
                if (bundle.getString("key-tether-auth-response-page") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "responsepage string value is null");
                    return 901;
                }
                if (bundle.getString("key-tether-auth-response-page").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "responsepage string value is empty");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-client-certificate-issuer-cn")) {
                if (bundle.getString("key-tether-client-certificate-issuer-cn") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "client cert issuer CN string value is null");
                    return 901;
                }
                if (bundle.getString("key-tether-client-certificate-issuer-cn").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "client cert issuer CN string value is empty");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-client-certificate-issued-cn")) {
                if (bundle.getString("key-tether-client-certificate-issued-cn") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "client cert issued CN string value is null");
                    return 901;
                }
                if (bundle.getString("key-tether-client-certificate-issued-cn").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "client cert issued CN string value is empty");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-captive-portal-certificate") && bundle.containsKey("key-tether-captive-portal-alias")) {
                Log.e("KnoxVpnTetherAuthentication", "config contains both captive portal cert and alias");
                return 901;
            }
            if (bundle.containsKey("key-tether-captive-portal-alias")) {
                if (bundle.getString("key-tether-captive-portal-alias") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "captiveAlias string value is null");
                    return 901;
                }
                if (bundle.getString("key-tether-captive-portal-alias").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "captiveAlias string value is empty");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-captive-portal-certificate")) {
                if (bundle.getByteArray("key-tether-captive-portal-certificate") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "captiveCert value is null");
                    return 901;
                }
                if (bundle.getByteArray("key-tether-captive-portal-certificate").length <= 0) {
                    Log.e("KnoxVpnTetherAuthentication", "captiveCert length is not valid");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-captive-portal-cert-password")) {
                if (bundle.getString("key-tether-captive-portal-cert-password") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "captiveCert credential value is null");
                    return 901;
                }
                if (bundle.getString("key-tether-captive-portal-cert-password").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "captiveCert credential value is empty");
                    return 901;
                }
                String string = bundle.getString("key-tether-captive-portal-cert-password");
                String str4 = str + "_captivecert_pwd";
                this.mKnoxVpnHelper.getClass();
                if (!KnoxVpnHelper.saveProfileCredentials(str4, string)) {
                    Log.e("KnoxVpnTetherAuthentication", "Saving the captive cert credential inside keystore failed");
                    return 901;
                }
                bundle.remove("key-tether-captive-portal-cert-password");
            }
            if (bundle.containsKey("key-tether-ca-certificate") && bundle.containsKey("key-tether-ca-alias")) {
                Log.e("KnoxVpnTetherAuthentication", "config contains both ca cert and alias");
                return 901;
            }
            if (bundle.containsKey("key-tether-ca-alias")) {
                if (bundle.getString("key-tether-ca-alias") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "caAlias string value is null");
                    return 901;
                }
                if (bundle.getString("key-tether-ca-alias").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "caAlias string value is empty");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-ca-certificate")) {
                if (bundle.getByteArray("key-tether-ca-certificate") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "caCert value is null");
                    return 901;
                }
                if (bundle.getByteArray("key-tether-ca-certificate").length <= 0) {
                    Log.e("KnoxVpnTetherAuthentication", "caCert length is not valid");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-ca-cert-password")) {
                if (bundle.getString("key-tether-ca-cert-password") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "caCert credential value is empty");
                    return 901;
                }
                if (bundle.getString("key-tether-ca-cert-password").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "caCert credential value is empty");
                    return 901;
                }
                String string2 = bundle.getString("key-tether-ca-cert-password");
                String str5 = str + "_cacert_pwd";
                this.mKnoxVpnHelper.getClass();
                if (!KnoxVpnHelper.saveProfileCredentials(str5, string2)) {
                    Log.e("KnoxVpnTetherAuthentication", "Saving the ca cert credential inside keystore failed");
                    return 901;
                }
                bundle.remove("key-tether-ca-cert-password");
            }
            if (bundle.containsKey("key-tether-user-certificate") && bundle.containsKey("key-tether-user-alias")) {
                Log.e("KnoxVpnTetherAuthentication", "config contains both server cert and alias");
                return 901;
            }
            if (bundle.containsKey("key-tether-user-alias")) {
                if (bundle.getString("key-tether-user-alias") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "serverAlias string value is null");
                    return 901;
                }
                if (bundle.getString("key-tether-user-alias").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "serverAlias string value is empty");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-user-certificate")) {
                if (bundle.getByteArray("key-tether-user-certificate") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "serverCert value is null");
                    return 901;
                }
                if (bundle.getByteArray("key-tether-user-certificate").length <= 0) {
                    Log.e("KnoxVpnTetherAuthentication", "serverCert length is not valid");
                    return 901;
                }
                byte[] byteArray = bundle.getByteArray("key-tether-user-certificate");
                String string3 = bundle.getString("key-tether-user-cert-password", "");
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
                try {
                    if (string3 == null) {
                        keyStore.load(byteArrayInputStream, null);
                    } else {
                        keyStore.load(byteArrayInputStream, string3.toCharArray());
                    }
                    Enumeration<String> aliases = keyStore.aliases();
                    if (aliases.hasMoreElements()) {
                        keyStore.deleteEntry(aliases.nextElement());
                    }
                } catch (IOException unused) {
                    byteArrayInputStream.close();
                    Log.e("KnoxVpnTetherAuthentication", "serverCert pwd is incorrect");
                    return 901;
                }
            }
            if (bundle.containsKey("key-tether-user-cert-password")) {
                if (bundle.getString("key-tether-user-cert-password") == null) {
                    Log.e("KnoxVpnTetherAuthentication", "serverCert credential value is null");
                    return 901;
                }
                if (bundle.getString("key-tether-user-cert-password").isEmpty()) {
                    Log.e("KnoxVpnTetherAuthentication", "serverCert credential value is empty");
                    return 901;
                }
                String string4 = bundle.getString("key-tether-user-cert-password");
                String str6 = str + "_servercert_pwd";
                this.mKnoxVpnHelper.getClass();
                if (!KnoxVpnHelper.saveProfileCredentials(str6, string4)) {
                    Log.e("KnoxVpnTetherAuthentication", "Saving the server cert password inside keystore failed");
                    return 901;
                }
                bundle.remove("key-tether-user-cert-password");
            }
        }
        i = 100;
        str3 = str2;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "enableUsbTethering : validationResult value is ", str3);
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x028b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int createVpnProfileValidation(com.samsung.android.knox.net.vpn.KnoxVpnContext r24, java.lang.String r25) {
        /*
            Method dump skipped, instructions count: 774
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnApiValidation.createVpnProfileValidation(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String):int");
    }

    public final int disallowUsbTetheringValidation(KnoxVpnContext knoxVpnContext, String str) {
        int i;
        if (str == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at enableUsbTethering API "), "KnoxVpnApiValidation");
            i = 101;
        }
        if (knoxVpnContext.vendorName == null) {
            return 104;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return 108;
        }
        if (profileEntry.admin_id != knoxVpnContext.adminId) {
            return 112;
        }
        if (profileEntry.routeType == 0) {
            return 109;
        }
        if (profileEntry.chainingEnabled == 0 || profileEntry.vpnConnectionType == 1 || profileEntry.uidPidSearchEnabled == 1) {
            return 901;
        }
        Collection values = this.vpnConfig.vpnProfileInfoMap.values();
        i = 100;
        if (values != null && values.size() > 0) {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                if (vpnProfileInfo.mUsbTethering == 1) {
                    return !str.equalsIgnoreCase(vpnProfileInfo.mProfileName) ? 901 : 100;
                }
            }
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "enableUsbTethering : validationResult value is ", "KnoxVpnApiValidation");
        return i;
    }

    public final String getErrorStringValidation(KnoxVpnContext knoxVpnContext, String str) {
        if (str != null) {
            try {
            } catch (Exception e) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at getErrorStringValidation API "), "KnoxVpnApiValidation");
            }
            if (knoxVpnContext.vendorName != null) {
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                if (profileEntry == null) {
                    Log.d("KnoxVpnApiValidation", "getErrorStringValidation: profileInfo value is null");
                    return null;
                }
                if (profileEntry.admin_id != knoxVpnContext.adminId) {
                    Log.d("KnoxVpnApiValidation", "getErrorStringValidation: Not the same admin");
                    return null;
                }
                return str;
            }
        }
        Log.d("KnoxVpnApiValidation", "getErrorStringValidation: profileName value is null");
        return null;
    }

    public final int getVpnModeOfOperationValidation(KnoxVpnContext knoxVpnContext, String str) {
        int i;
        if (str == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at getVpnModeOfOperationValidation API "), "KnoxVpnApiValidation");
            i = 101;
        }
        if (knoxVpnContext.vendorName == null) {
            return 104;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return 108;
        }
        if (profileEntry.admin_id != knoxVpnContext.adminId) {
            return 112;
        }
        i = 100;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "getVpnModeOfOperationValidation : validationResult value is ", "KnoxVpnApiValidation");
        return i;
    }

    public final String getVpnProfileValidation(KnoxVpnContext knoxVpnContext, String str) {
        if (str != null) {
            try {
            } catch (Exception e) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at getVpnProfileValidation API "), "KnoxVpnApiValidation");
            }
            if (knoxVpnContext.vendorName != null) {
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                if (profileEntry == null) {
                    Log.d("KnoxVpnApiValidation", "getVpnProfileValidation: profileInfo value is null");
                    return null;
                }
                if (profileEntry.admin_id != knoxVpnContext.adminId) {
                    Log.d("KnoxVpnApiValidation", "getVpnProfileValidation: Not the same admin");
                    return null;
                }
                return str;
            }
        }
        Log.d("KnoxVpnApiValidation", "getVpnProfileValidation: profileName value is null");
        return null;
    }

    public final int isUsbTetheringOverVpnEnabledValidation(KnoxVpnContext knoxVpnContext, String str) {
        int i;
        if (str == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at isUsbTetheringOverVpnEnabledValidation API "), "KnoxVpnApiValidation");
            i = 101;
        }
        if (knoxVpnContext.vendorName == null) {
            return 104;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return 108;
        }
        if (profileEntry.admin_id != knoxVpnContext.adminId) {
            return 112;
        }
        if (profileEntry.routeType == 0) {
            return 109;
        }
        if (profileEntry.chainingEnabled == 0 || profileEntry.vpnConnectionType == 1) {
            return 901;
        }
        if (profileEntry.uidPidSearchEnabled == 1) {
            return 901;
        }
        i = 100;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "isUsbTetheringOverVpnEnabledValidation : validationResult value is ", "KnoxVpnApiValidation");
        return i;
    }

    public final boolean isUserTypeAppSeparation(int i) {
        boolean z;
        if (this.mUserMgr == null) {
            this.mUserMgr = (UserManager) mContext.getSystemService("user");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserInfo userInfo = this.mUserMgr.getUserInfo(i);
            if (userInfo != null) {
                if (userInfo.isUserTypeAppSeparation()) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int removeAllPackagesFromVpnValidation(KnoxVpnContext knoxVpnContext, String str) {
        int i;
        if (str == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at removeAllPackagesFromVpnValidation API "), "KnoxVpnApiValidation");
            i = 101;
        }
        if (knoxVpnContext.vendorName == null) {
            return 104;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return 108;
        }
        if (profileEntry.admin_id != knoxVpnContext.adminId) {
            return 112;
        }
        int userId = UserHandle.getUserId(profileEntry.admin_id);
        int personaId = knoxVpnContext.getPersonaId();
        if (userId != 0 && personaId == 0) {
            Log.e("KnoxVpnApiValidation", "EMM present in work profile trying to configure vpn for user 0");
            return 114;
        }
        if (profileEntry.routeType == 0) {
            return 109;
        }
        i = 100;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "removeAllPackagesFromVpnValidation : validationResult value is ", "KnoxVpnApiValidation");
        return i;
    }

    public final int removeContainerPackagesFromVpnValidation(KnoxVpnContext knoxVpnContext, int i, String[] strArr, String str) {
        int i2;
        if (str == null || strArr == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at removeContainerPackagesFromVpnValidation API "), "KnoxVpnApiValidation");
            i2 = 101;
        }
        if (strArr.length >= 1 && knoxVpnContext.vendorName != null) {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return 108;
            }
            if (profileEntry.admin_id != knoxVpnContext.adminId) {
                return 112;
            }
            int userId = UserHandle.getUserId(profileEntry.admin_id);
            int personaId = knoxVpnContext.getPersonaId();
            if (userId != 0 && personaId == 0) {
                Log.e("KnoxVpnApiValidation", "EMM present in work profile trying to configure vpn for user 0");
                return 114;
            }
            if (profileEntry.routeType == 0) {
                return 109;
            }
            if (!isUserTypeAppSeparation(i)) {
                if (getPersonaManager() != null && !getPersonaManager().exists(i)) {
                    return 113;
                }
                if (UserHandle.getAppId(mEdmStorageProvider.getMUMContainerOwnerUid(i)) != UserHandle.getAppId(knoxVpnContext.adminId)) {
                    return 114;
                }
                if (getPersonaManager() != null && getPersonaManager().exists(knoxVpnContext.getPersonaId()) && profileEntry.personaId != i) {
                    return 113;
                }
            }
            if (this.mKnoxVpnHelper.isUsingKnoxPackageExists(i)) {
                for (String str2 : strArr) {
                    this.mKnoxVpnHelper.getClass();
                    String personifiedName = KnoxVpnHelper.getPersonifiedName(i, str2);
                    String profileNameForPackage = this.vpnConfig.getProfileNameForPackage(personifiedName);
                    if (personifiedName != null && profileNameForPackage != null && !profileNameForPackage.equalsIgnoreCase(str)) {
                        return 129;
                    }
                    this.mKnoxVpnHelper.getClass();
                    int uIDForPackage = KnoxVpnHelper.getUIDForPackage(i, str2);
                    if (uIDForPackage > 0) {
                        String profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(uIDForPackage);
                        if (profileOwningTheUid != null && !profileOwningTheUid.equalsIgnoreCase(str)) {
                            return 139;
                        }
                        String profileOwningTheUidWithNoInternetPermission = this.mKnoxVpnHelper.getProfileOwningTheUidWithNoInternetPermission(uIDForPackage);
                        if (profileOwningTheUidWithNoInternetPermission != null && !profileOwningTheUidWithNoInternetPermission.equalsIgnoreCase(str)) {
                            return 139;
                        }
                    }
                }
            }
            i2 = 100;
            if (this.mKnoxVpnHelper.isWideVpnExists(i)) {
                if (this.mKnoxVpnHelper.getAdminIdForUserVpn(i) != knoxVpnContext.adminId) {
                    return 122;
                }
                KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                knoxVpnHelper.getClass();
                String profileOwningThePackage = knoxVpnHelper.getProfileOwningThePackage(KnoxVpnHelper.getPersonifiedName(i, "ADD_ALL_PACKAGES"));
                if (profileOwningThePackage != null) {
                    if (!profileOwningThePackage.equalsIgnoreCase(str)) {
                        return this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(profileOwningThePackage) != 1 ? 121 : 100;
                    }
                    HashSet hashSet = profileEntry.mExemptPackageList;
                    for (String str3 : strArr) {
                        this.mKnoxVpnHelper.getClass();
                        int uIDForPackage2 = KnoxVpnHelper.getUIDForPackage(i, str3);
                        if (uIDForPackage2 != -1) {
                            hashSet.add(Integer.valueOf(uIDForPackage2));
                            this.mKnoxVpnHelper.getClass();
                            this.mKnoxVpnHelper.addExemptedListToDatabase(uIDForPackage2, str, KnoxVpnHelper.getPersonifiedName(i, str3));
                            Log.d("KnoxVpnApiValidation", "user wide vpn was configured " + i + "adding uid to exempt list " + uIDForPackage2);
                        } else {
                            this.mKnoxVpnHelper.getClass();
                            this.mKnoxVpnHelper.addExemptedListToDatabase(uIDForPackage2, str, KnoxVpnHelper.getPersonifiedName(i, str3));
                            Log.d("KnoxVpnApiValidation", "user wide vpn was configured " + i + "adding uid to exempt list " + uIDForPackage2);
                        }
                    }
                    this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
                }
            }
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "removeContainerPackagesFromVpnValidation : validationResult value is ", "KnoxVpnApiValidation");
            return i2;
        }
        return 104;
    }

    public final int removePackagesFromVpnValidation(KnoxVpnContext knoxVpnContext, String[] strArr, String str) {
        int i;
        HashSet hashSet;
        String[] strArr2 = strArr;
        if (str == null || strArr2 == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at removePackagesFromVpnValidation API "), "KnoxVpnApiValidation");
            i = 101;
        }
        if (strArr2.length >= 1 && knoxVpnContext.vendorName != null) {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return 108;
            }
            if (profileEntry.admin_id != knoxVpnContext.adminId) {
                return 112;
            }
            int userId = UserHandle.getUserId(profileEntry.admin_id);
            int personaId = knoxVpnContext.getPersonaId();
            if (userId != 0 && personaId == 0) {
                Log.e("KnoxVpnApiValidation", "EMM present in work profile trying to configure vpn for user 0");
                return 114;
            }
            if (profileEntry.routeType == 0) {
                return 109;
            }
            int i2 = 0;
            if (this.mKnoxVpnHelper.isUsingKnoxPackageExists(knoxVpnContext.personaId)) {
                for (String str2 : strArr2) {
                    KnoxVpnHelper knoxVpnHelper = this.mKnoxVpnHelper;
                    int i3 = knoxVpnContext.personaId;
                    knoxVpnHelper.getClass();
                    String personifiedName = KnoxVpnHelper.getPersonifiedName(i3, str2);
                    String profileNameForPackage = this.vpnConfig.getProfileNameForPackage(personifiedName);
                    if (personifiedName != null && profileNameForPackage != null && !profileNameForPackage.equalsIgnoreCase(str)) {
                        return 129;
                    }
                    KnoxVpnHelper knoxVpnHelper2 = this.mKnoxVpnHelper;
                    int i4 = knoxVpnContext.personaId;
                    knoxVpnHelper2.getClass();
                    int uIDForPackage = KnoxVpnHelper.getUIDForPackage(i4, str2);
                    if (uIDForPackage > 0) {
                        String profileOwningTheUid = this.mKnoxVpnHelper.getProfileOwningTheUid(uIDForPackage);
                        if (profileOwningTheUid != null && !profileOwningTheUid.equalsIgnoreCase(str)) {
                            return 139;
                        }
                        String profileOwningTheUidWithNoInternetPermission = this.mKnoxVpnHelper.getProfileOwningTheUidWithNoInternetPermission(uIDForPackage);
                        if (profileOwningTheUidWithNoInternetPermission != null && !profileOwningTheUidWithNoInternetPermission.equalsIgnoreCase(str)) {
                            return 139;
                        }
                    }
                }
            }
            if (this.mKnoxVpnHelper.isWideVpnExists(knoxVpnContext.personaId)) {
                if (this.mKnoxVpnHelper.getAdminIdForUserVpn(knoxVpnContext.personaId) != knoxVpnContext.adminId) {
                    return 122;
                }
                KnoxVpnHelper knoxVpnHelper3 = this.mKnoxVpnHelper;
                int i5 = knoxVpnContext.personaId;
                knoxVpnHelper3.getClass();
                String profileOwningThePackage = knoxVpnHelper3.getProfileOwningThePackage(KnoxVpnHelper.getPersonifiedName(i5, "ADD_ALL_PACKAGES"));
                if (profileOwningThePackage != null) {
                    if (!profileOwningThePackage.equalsIgnoreCase(str)) {
                        return this.mKnoxVpnHelper.checkIfProfileHasChainingFeature(profileOwningThePackage) != 1 ? 121 : 100;
                    }
                    HashSet hashSet2 = profileEntry.mExemptPackageList;
                    int length = strArr2.length;
                    while (i2 < length) {
                        String str3 = strArr2[i2];
                        KnoxVpnHelper knoxVpnHelper4 = this.mKnoxVpnHelper;
                        int i6 = knoxVpnContext.personaId;
                        knoxVpnHelper4.getClass();
                        int uIDForPackage2 = KnoxVpnHelper.getUIDForPackage(i6, str3);
                        if (knoxVpnContext.personaId == 0 && str3.equalsIgnoreCase("com.android.networkstack")) {
                            Log.d("KnoxVpnApiValidation", "network stack uid is exempted by the Admin");
                            uIDForPackage2 = 1073;
                        }
                        if (uIDForPackage2 != -1) {
                            hashSet2.add(Integer.valueOf(uIDForPackage2));
                            KnoxVpnHelper knoxVpnHelper5 = this.mKnoxVpnHelper;
                            hashSet = hashSet2;
                            int i7 = knoxVpnContext.personaId;
                            knoxVpnHelper5.getClass();
                            Log.d("KnoxVpnApiValidation", "user wide vpn was configured " + knoxVpnContext.personaId + "adding uid to exempt list " + uIDForPackage2 + " " + this.mKnoxVpnHelper.addExemptedListToDatabase(uIDForPackage2, str, KnoxVpnHelper.getPersonifiedName(i7, str3)));
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnApiValidation", "Package name " + str3 + " removed from vpn for profile " + str + ". Added to exempt list", knoxVpnContext.personaId);
                        } else {
                            hashSet = hashSet2;
                            KnoxVpnHelper knoxVpnHelper6 = this.mKnoxVpnHelper;
                            int i8 = knoxVpnContext.personaId;
                            knoxVpnHelper6.getClass();
                            Log.d("KnoxVpnApiValidation", "user wide vpn was configured " + knoxVpnContext.personaId + "adding uid to exempt list " + uIDForPackage2 + " " + this.mKnoxVpnHelper.addExemptedListToDatabase(uIDForPackage2, str, KnoxVpnHelper.getPersonifiedName(i8, str3)));
                            AuditLog.logAsUser(5, 5, true, Process.myPid(), "KnoxVpnApiValidation", "Package name " + str3 + " removed from vpn for profile " + str + ". Added to exempt list", knoxVpnContext.personaId);
                        }
                        i2++;
                        strArr2 = strArr;
                        hashSet2 = hashSet;
                    }
                    this.mKnoxVpnHelper.updateUidsToVpnUidRange(str);
                }
            }
            i = 100;
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "removePackagesFromVpnValidation : validationResult value is ", "KnoxVpnApiValidation");
            return i;
        }
        return 104;
    }

    public final int removeVpnProfileValidation(KnoxVpnContext knoxVpnContext, String str) {
        int i;
        if (str == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at removeVpnProfileValidation API "), "KnoxVpnApiValidation");
            i = 101;
        }
        if (knoxVpnContext.vendorName == null) {
            return 104;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return 108;
        }
        if (profileEntry.admin_id != knoxVpnContext.adminId) {
            return 112;
        }
        i = (profileEntry.routeType == 1 && profileEntry.activateState == 1) ? 125 : 100;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "removeVpnProfileValidation : validationResult value is ", "KnoxVpnApiValidation");
        return i;
    }

    public final int setVpnModeOfOperationValidation(KnoxVpnContext knoxVpnContext, String str, int i) {
        int i2;
        if (str == null) {
            return 104;
        }
        try {
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at setVpnModeOfOperationValidation API "), "KnoxVpnApiValidation");
            i2 = 101;
        }
        if (knoxVpnContext.vendorName == null) {
            return 104;
        }
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return 108;
        }
        if (profileEntry.admin_id != knoxVpnContext.adminId) {
            return 112;
        }
        if (i != 0 && i != 1) {
            return 138;
        }
        i2 = 100;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "setVpnModeOfOperationValidation : validationResult value is ", "KnoxVpnApiValidation");
        return i2;
    }
}
