package com.android.server.devicepolicy;

import android.content.ComponentName;
import android.os.FileUtils;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Xml;

import com.android.internal.util.JournaledFile;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.utils.Slogf;

import com.samsung.android.knox.SemPersonaManager;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class DevicePolicyData {
    public String mCurrentRoleHolder;
    public int mFactoryResetFlags;
    public String mFactoryResetReason;
    public ActiveAdmin mPermissionBasedAdmin;
    public int mPermissionPolicy;
    public ComponentName mRestrictionsProvider;
    public List mUserControlDisabledPackages;
    public final int mUserId;
    public int mUserProvisioningState;
    public int mFailedPasswordAttempts = 0;
    public int mFailedPasswordAttemptsFromGateKeeper = 0;
    public int mFailedBiometricAttempts = 0;
    public boolean mPasswordValidAtLastCheckpoint = true;
    public int mPasswordOwner = -1;
    public long mLastMaximumTimeToLock = -1;
    public boolean mUserSetupComplete = false;
    public boolean mBypassDevicePolicyManagementRoleQualifications = false;
    public boolean mPaired = false;
    public int mLastResetPassword = -1;
    public boolean mDeviceProvisioningConfigApplied = false;
    public final ArrayMap mAdminMap = new ArrayMap();
    public final ArrayList mAdminList = new ArrayList();
    public final ArrayList mRemovingAdmins = new ArrayList();
    public final ArraySet mAcceptedCaCertificates = new ArraySet();
    public final List mLockTaskPackages = new ArrayList();
    public int mLockTaskFeatures = 16;
    public boolean mStatusBarDisabled = false;
    public final ArrayMap mDelegationMap = new ArrayMap();
    public boolean mDoNotAskCredentialsOnBoot = false;
    public Set mAffiliationIds = new ArraySet();
    public long mLastSecurityLogRetrievalTime = -1;
    public long mLastBugReportRequestTime = -1;
    public long mLastNetworkLogsRetrievalTime = -1;
    public boolean mCurrentInputMethodSet = false;
    public boolean mSecondaryLockscreenEnabled = false;
    public final Set mOwnerInstalledCaCerts = new ArraySet();
    public boolean mAdminBroadcastPending = false;
    public PersistableBundle mInitBundle = null;
    public long mPasswordTokenHandle = 0;
    public boolean mAppsSuspended = false;
    public String mNewUserDisclaimer = "not_needed";
    public boolean mEffectiveKeepProfilesRunning = false;

    public DevicePolicyData(int i) {
        this.mUserId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0371 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void load(
            com.android.server.devicepolicy.DevicePolicyData r11,
            com.android.internal.util.JournaledFile r12,
            java.util.function.Function r13,
            android.content.ComponentName r14) {
        /*
            Method dump skipped, instructions count: 896
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.devicepolicy.DevicePolicyData.load(com.android.server.devicepolicy.DevicePolicyData,"
                    + " com.android.internal.util.JournaledFile, java.util.function.Function,"
                    + " android.content.ComponentName):void");
    }

    public static boolean store(DevicePolicyData devicePolicyData, JournaledFile journaledFile) {
        File file;
        FileOutputStream fileOutputStream;
        String str;
        String str2 = "lock-task-features";
        String str3 = "lock-task-component";
        String str4 = "owner-installed-ca-cert";
        String str5 = "affiliation-id";
        try {
            file = journaledFile.chooseForWrite();
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file, false);
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream2);
                    try {
                        fileOutputStream = fileOutputStream2;
                        try {
                            resolveSerializer.startDocument((String) null, Boolean.TRUE);
                            resolveSerializer.startTag((String) null, "policies");
                            ComponentName componentName = devicePolicyData.mRestrictionsProvider;
                            if (componentName != null) {
                                str = "policies";
                                resolveSerializer.attribute(
                                        (String) null,
                                        "permission-provider",
                                        componentName.flattenToString());
                            } else {
                                str = "policies";
                            }
                            if (devicePolicyData.mUserSetupComplete) {
                                resolveSerializer.attributeBoolean(
                                        (String) null, "setup-complete", true);
                            }
                            if (devicePolicyData.mPaired) {
                                resolveSerializer.attributeBoolean(
                                        (String) null, "device-paired", true);
                            }
                            if (devicePolicyData.mDeviceProvisioningConfigApplied) {
                                resolveSerializer.attributeBoolean(
                                        (String) null, "device-provisioning-config-applied", true);
                            }
                            int i = devicePolicyData.mUserProvisioningState;
                            if (i != 0) {
                                resolveSerializer.attributeInt(
                                        (String) null, "provisioning-state", i);
                            }
                            int i2 = devicePolicyData.mPermissionPolicy;
                            if (i2 != 0) {
                                resolveSerializer.attributeInt(
                                        (String) null, "permission-policy", i2);
                            }
                            if ("needed".equals(devicePolicyData.mNewUserDisclaimer)) {
                                resolveSerializer.attribute(
                                        (String) null,
                                        "new-user-disclaimer",
                                        devicePolicyData.mNewUserDisclaimer);
                            }
                            int i3 = devicePolicyData.mFactoryResetFlags;
                            if (i3 != 0) {
                                resolveSerializer.attributeInt(
                                        (String) null, "factory-reset-flags", i3);
                            }
                            String str6 = devicePolicyData.mFactoryResetReason;
                            if (str6 != null) {
                                resolveSerializer.attribute(
                                        (String) null, "factory-reset-reason", str6);
                            }
                            for (int i4 = 0; i4 < devicePolicyData.mDelegationMap.size(); i4++) {
                                String str7 = (String) devicePolicyData.mDelegationMap.keyAt(i4);
                                Iterator it =
                                        ((List) devicePolicyData.mDelegationMap.valueAt(i4))
                                                .iterator();
                                while (it.hasNext()) {
                                    Iterator it2 = it;
                                    String str8 = (String) it.next();
                                    resolveSerializer.startTag((String) null, "delegation");
                                    resolveSerializer.attribute(
                                            (String) null, "delegatePackage", str7);
                                    resolveSerializer.attribute((String) null, "scope", str8);
                                    resolveSerializer.endTag((String) null, "delegation");
                                    str2 = str2;
                                    it = it2;
                                    str3 = str3;
                                }
                            }
                            String str9 = str2;
                            String str10 = str3;
                            int size = devicePolicyData.mAdminList.size();
                            for (int i5 = 0; i5 < size; i5++) {
                                ActiveAdmin activeAdmin =
                                        (ActiveAdmin) devicePolicyData.mAdminList.get(i5);
                                if (activeAdmin != null) {
                                    resolveSerializer.startTag((String) null, "admin");
                                    resolveSerializer.attribute(
                                            (String) null,
                                            "name",
                                            activeAdmin.info.getComponent().flattenToString());
                                    activeAdmin.writeToXml(resolveSerializer);
                                    resolveSerializer.endTag((String) null, "admin");
                                }
                            }
                            if (devicePolicyData.mPermissionBasedAdmin != null) {
                                resolveSerializer.startTag((String) null, "permission-based-admin");
                                devicePolicyData.mPermissionBasedAdmin.writeToXml(
                                        resolveSerializer);
                                resolveSerializer.endTag((String) null, "permission-based-admin");
                            }
                            if (devicePolicyData.mPasswordOwner >= 0) {
                                resolveSerializer.startTag((String) null, "password-owner");
                                resolveSerializer.attributeInt(
                                        (String) null, "value", devicePolicyData.mPasswordOwner);
                                resolveSerializer.endTag((String) null, "password-owner");
                            }
                            if (devicePolicyData.mFailedPasswordAttempts != 0) {
                                resolveSerializer.startTag(
                                        (String) null, "failed-password-attempts");
                                resolveSerializer.attributeInt(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mFailedPasswordAttempts);
                                resolveSerializer.endTag((String) null, "failed-password-attempts");
                            }
                            if (devicePolicyData.mFailedPasswordAttemptsFromGateKeeper != 0) {
                                resolveSerializer.startTag(
                                        (String) null, "failed-password-attempts-from-gatekeeper");
                                resolveSerializer.attributeInt(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mFailedPasswordAttemptsFromGateKeeper);
                                resolveSerializer.endTag(
                                        (String) null, "failed-password-attempts-from-gatekeeper");
                            }
                            if (SemPersonaManager.isKnoxId(devicePolicyData.mUserId)
                                    && devicePolicyData.mFailedBiometricAttempts != 0) {
                                resolveSerializer.startTag(
                                        (String) null, "failed-biometric-attempts");
                                resolveSerializer.attributeInt(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mFailedBiometricAttempts);
                                resolveSerializer.endTag(
                                        (String) null, "failed-biometric-attempts");
                            }
                            if (devicePolicyData.mLastResetPassword != -1) {
                                resolveSerializer.startTag(
                                        (String) null, "last-resetpassword-admin");
                                resolveSerializer.attributeInt(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mLastResetPassword);
                                resolveSerializer.endTag((String) null, "last-resetpassword-admin");
                            }
                            for (int i6 = 0;
                                    i6 < devicePolicyData.mAcceptedCaCertificates.size();
                                    i6++) {
                                resolveSerializer.startTag(
                                        (String) null, "accepted-ca-certificate");
                                resolveSerializer.attribute(
                                        (String) null,
                                        "name",
                                        (String)
                                                devicePolicyData.mAcceptedCaCertificates.valueAt(
                                                        i6));
                                resolveSerializer.endTag((String) null, "accepted-ca-certificate");
                            }
                            int i7 = 0;
                            while (i7 < ((ArrayList) devicePolicyData.mLockTaskPackages).size()) {
                                String str11 =
                                        (String)
                                                ((ArrayList) devicePolicyData.mLockTaskPackages)
                                                        .get(i7);
                                String str12 = str10;
                                resolveSerializer.startTag((String) null, str12);
                                resolveSerializer.attribute((String) null, "name", str11);
                                resolveSerializer.endTag((String) null, str12);
                                i7++;
                                str10 = str12;
                            }
                            if (devicePolicyData.mLockTaskFeatures != 0) {
                                resolveSerializer.startTag((String) null, str9);
                                resolveSerializer.attributeInt(
                                        (String) null, "value", devicePolicyData.mLockTaskFeatures);
                                resolveSerializer.endTag((String) null, str9);
                            }
                            if (devicePolicyData.mSecondaryLockscreenEnabled) {
                                resolveSerializer.startTag((String) null, "secondary-lock-screen");
                                resolveSerializer.attributeBoolean((String) null, "value", true);
                                resolveSerializer.endTag((String) null, "secondary-lock-screen");
                            }
                            if (devicePolicyData.mStatusBarDisabled) {
                                resolveSerializer.startTag((String) null, "statusbar");
                                resolveSerializer.attributeBoolean(
                                        (String) null,
                                        "disabled",
                                        devicePolicyData.mStatusBarDisabled);
                                resolveSerializer.endTag((String) null, "statusbar");
                            }
                            if (devicePolicyData.mDoNotAskCredentialsOnBoot) {
                                resolveSerializer.startTag(
                                        (String) null, "do-not-ask-credentials-on-boot");
                                resolveSerializer.endTag(
                                        (String) null, "do-not-ask-credentials-on-boot");
                            }
                            Iterator it3 = ((ArraySet) devicePolicyData.mAffiliationIds).iterator();
                            while (it3.hasNext()) {
                                String str13 = (String) it3.next();
                                String str14 = str5;
                                resolveSerializer.startTag((String) null, str14);
                                resolveSerializer.attribute((String) null, "id", str13);
                                resolveSerializer.endTag((String) null, str14);
                                str5 = str14;
                            }
                            if (devicePolicyData.mLastSecurityLogRetrievalTime >= 0) {
                                resolveSerializer.startTag(
                                        (String) null, "last-security-log-retrieval");
                                resolveSerializer.attributeLong(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mLastSecurityLogRetrievalTime);
                                resolveSerializer.endTag(
                                        (String) null, "last-security-log-retrieval");
                            }
                            if (devicePolicyData.mLastBugReportRequestTime >= 0) {
                                resolveSerializer.startTag(
                                        (String) null, "last-bug-report-request");
                                resolveSerializer.attributeLong(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mLastBugReportRequestTime);
                                resolveSerializer.endTag((String) null, "last-bug-report-request");
                            }
                            if (devicePolicyData.mLastNetworkLogsRetrievalTime >= 0) {
                                resolveSerializer.startTag(
                                        (String) null, "last-network-log-retrieval");
                                resolveSerializer.attributeLong(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mLastNetworkLogsRetrievalTime);
                                resolveSerializer.endTag(
                                        (String) null, "last-network-log-retrieval");
                            }
                            if (devicePolicyData.mAdminBroadcastPending) {
                                resolveSerializer.startTag(
                                        (String) null, "admin-broadcast-pending");
                                resolveSerializer.attributeBoolean(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mAdminBroadcastPending);
                                resolveSerializer.endTag((String) null, "admin-broadcast-pending");
                            }
                            if (devicePolicyData.mInitBundle != null) {
                                resolveSerializer.startTag((String) null, "initialization-bundle");
                                devicePolicyData.mInitBundle.saveToXml(resolveSerializer);
                                resolveSerializer.endTag((String) null, "initialization-bundle");
                            }
                            if (devicePolicyData.mPasswordTokenHandle != 0) {
                                resolveSerializer.startTag((String) null, "password-token");
                                resolveSerializer.attributeLong(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mPasswordTokenHandle);
                                resolveSerializer.endTag((String) null, "password-token");
                            }
                            if (devicePolicyData.mCurrentInputMethodSet) {
                                resolveSerializer.startTag((String) null, "current-ime-set");
                                resolveSerializer.endTag((String) null, "current-ime-set");
                            }
                            Iterator it4 =
                                    ((ArraySet) devicePolicyData.mOwnerInstalledCaCerts).iterator();
                            while (it4.hasNext()) {
                                String str15 = (String) it4.next();
                                String str16 = str4;
                                resolveSerializer.startTag((String) null, str16);
                                resolveSerializer.attribute((String) null, "alias", str15);
                                resolveSerializer.endTag((String) null, str16);
                                str4 = str16;
                            }
                            if (devicePolicyData.mAppsSuspended) {
                                resolveSerializer.startTag((String) null, "apps-suspended");
                                resolveSerializer.attributeBoolean(
                                        (String) null, "value", devicePolicyData.mAppsSuspended);
                                resolveSerializer.endTag((String) null, "apps-suspended");
                            }
                            if (devicePolicyData.mBypassDevicePolicyManagementRoleQualifications) {
                                resolveSerializer.startTag(
                                        (String) null, "bypass-role-qualifications");
                                resolveSerializer.attribute(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mCurrentRoleHolder);
                                resolveSerializer.endTag(
                                        (String) null, "bypass-role-qualifications");
                            }
                            if (devicePolicyData.mEffectiveKeepProfilesRunning) {
                                resolveSerializer.startTag((String) null, "keep-profiles-running");
                                resolveSerializer.attributeBoolean(
                                        (String) null,
                                        "value",
                                        devicePolicyData.mEffectiveKeepProfilesRunning);
                                resolveSerializer.endTag((String) null, "keep-profiles-running");
                            }
                            resolveSerializer.endTag((String) null, str);
                            resolveSerializer.endDocument();
                            fileOutputStream.flush();
                            FileUtils.sync(fileOutputStream);
                            fileOutputStream.close();
                            journaledFile.commit();
                            return true;
                        } catch (IOException | XmlPullParserException e) {
                            e = e;
                            file = file;
                            Slogf.w("DevicePolicyManager", e, "failed writing file %s", file);
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException unused) {
                                }
                            }
                            journaledFile.rollback();
                            return false;
                        }
                    } catch (IOException | XmlPullParserException e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                    }
                } catch (IOException | XmlPullParserException e3) {
                    e = e3;
                    fileOutputStream = fileOutputStream2;
                }
            } catch (IOException | XmlPullParserException e4) {
                e = e4;
                fileOutputStream = null;
            }
        } catch (IOException | XmlPullParserException e5) {
            e = e5;
            file = null;
            fileOutputStream = null;
        }
    }

    public final void validatePasswordOwner() {
        if (this.mPasswordOwner >= 0) {
            for (int size = this.mAdminList.size() - 1; size >= 0; size--) {
                if (((ActiveAdmin) this.mAdminList.get(size)).getUid() == this.mPasswordOwner) {
                    return;
                }
            }
            Slogf.w(
                    "DevicePolicyManager",
                    "Previous password owner %s no longer active; disabling",
                    Integer.valueOf(this.mPasswordOwner));
            this.mPasswordOwner = -1;
        }
    }
}
