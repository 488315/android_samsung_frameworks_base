package com.android.server.enterprise;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.enterprise.IEDMProxy;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.util.Log;

import com.android.internal.util.HexDump;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter.SystemUIAdapterCallbackDeathRecipient;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.auditlog.AuditLogService;
import com.android.server.enterprise.bluetooth.BluetoothPolicy;
import com.android.server.enterprise.browser.BrowserPolicy;
import com.android.server.enterprise.certificate.CertificatePolicy;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy;
import com.android.server.enterprise.datetime.DateTimePolicy;
import com.android.server.enterprise.device.DeviceInfo;
import com.android.server.enterprise.kioskmode.KioskModeService;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.restriction.PhoneRestrictionPolicy;
import com.android.server.enterprise.restriction.RestrictionPolicy;
import com.android.server.enterprise.restriction.RoamingPolicy;
import com.android.server.enterprise.security.DeviceAccountPolicy;
import com.android.server.enterprise.security.PasswordPolicy;
import com.android.server.enterprise.security.PasswordPolicy$$ExternalSyntheticLambda14;
import com.android.server.enterprise.utils.Utils;
import com.android.server.timedetector.NetworkTimeUpdateService;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ISecurityPolicy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.ucm.ucmservice.CredentialManagerService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class EDMProxyService extends IEDMProxy.Stub {
    public final Context mContext;

    public EDMProxyService(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0019, code lost:

       if (r0 < 0) goto L11;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addCallsCount(java.lang.String r4) {
        /*
            r3 = this;
            java.lang.String r3 = "device_info"
            java.lang.Object r3 = com.android.server.enterprise.EnterpriseService.getPolicyService(r3)
            com.android.server.enterprise.device.DeviceInfo r3 = (com.android.server.enterprise.device.DeviceInfo) r3
            if (r3 != 0) goto Lc
            return
        Lc:
            com.android.server.enterprise.storage.EdmStorageProvider r0 = r3.mEdmStorageProvider
            r1 = 0
            java.lang.String r0 = r0.getGenericValueAsUser(r1, r4)
            if (r0 == 0) goto L24
            int r0 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.NumberFormatException -> L1c
            if (r0 >= 0) goto L25
            goto L24
        L1c:
            java.lang.String r0 = "DeviceInfo"
            java.lang.String r2 = "could not parse integer "
            android.util.Log.w(r0, r2)
        L24:
            r0 = r1
        L25:
            com.android.server.enterprise.storage.EdmStorageProvider r3 = r3.mEdmStorageProvider
            int r0 = r0 + 1
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r3.putGenericValueAsUser(r1, r4, r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.EDMProxyService.addCallsCount(java.lang.String):void");
    }

    public final boolean addNumberOfIncomingCalls() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.addNumberOfIncomingCalls();
    }

    public final boolean addNumberOfIncomingSms() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.addNumberOfIncomingSms();
    }

    public final boolean addNumberOfOutgoingCalls() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.addNumberOfOutgoingCalls();
    }

    public final boolean addNumberOfOutgoingSms() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.addNumberOfOutgoingSms();
    }

    public final void auditLogger(int i, int i2, boolean z, int i3, String str, String str2) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.redactedAuditLogger(null, i, i2, z, i3, str, str2, null);
    }

    public final void auditLoggerAsUser(
            int i, int i2, boolean z, int i3, String str, String str2, int i4) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.redactedAuditLoggerAsUser(null, i, i2, z, i3, str, str2, null, i4);
    }

    public final void auditLoggerPrivileged(
            int i, int i2, boolean z, int i3, String str, String str2) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService != null && AuditLogService.checkAuditPrivilegedAllowed(str)) {
            auditLogService.auditLoggerInternal(
                    null,
                    i,
                    i2,
                    z,
                    i3,
                    str,
                    auditLogService.appendLogMessageWithCallingUser(str2),
                    null,
                    -1);
        }
    }

    public final void auditLoggerPrivilegedAsUser(
            int i, int i2, boolean z, int i3, String str, String str2, int i4) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService != null && AuditLogService.checkAuditPrivilegedAllowed(str)) {
            auditLogService.auditLoggerInternal(
                    null,
                    i,
                    i2,
                    z,
                    i3,
                    str,
                    auditLogService.appendLogMessageWithCallingUser(str2),
                    null,
                    i4);
        }
    }

    public final void bluetoothLog(String str, String str2) {
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return;
        }
        bluetoothPolicy.bluetoothLog(new ContextInfo(Binder.getCallingUid()), str, str2);
    }

    public final boolean canIncomingCall(String str) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.canIncomingCall(str);
    }

    public final boolean canIncomingSms(String str) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.canIncomingSms(str);
    }

    public final boolean canOutgoingCall(String str) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.canOutgoingCall(str);
    }

    public final boolean canOutgoingSms(String str) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.canOutgoingSms(str);
    }

    public final boolean decreaseNumberOfOutgoingSms() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.decreaseNumberOfOutgoingSms();
    }

    public final boolean getAddHomeShorcutRequested() {
        if (((ApplicationPolicy) EnterpriseService.getPolicyService("application_policy"))
                == null) {
            return false;
        }
        return ApplicationPolicy.addHomeShorcutRequested;
    }

    public final boolean getAllowBluetoothDataTransfer(boolean z) {
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.getAllowBluetoothDataTransfer(null, z);
    }

    public final byte[] getApplicationIconFromDb(String str, int i) {
        ApplicationPolicy applicationPolicy =
                (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            return null;
        }
        return applicationPolicy.getApplicationIconFromDbAsUser(i, str);
    }

    public final String getApplicationNameForComponent(String str, String str2, int i) {
        ApplicationPolicy applicationPolicy =
                (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            return null;
        }
        String applicationNameFromDb = applicationPolicy.getApplicationNameFromDb(str, i);
        return applicationNameFromDb == null
                ? applicationPolicy.getApplicationNameFromDb(str2, i)
                : applicationNameFromDb;
    }

    public final String getApplicationNameFromDb(String str, int i) {
        ApplicationPolicy applicationPolicy =
                (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            return null;
        }
        return applicationPolicy.getApplicationNameFromDb(str, i);
    }

    public final Bundle getApplicationRestrictions(String str, int i) {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        return knoxCustomManagerService == null
                ? Bundle.EMPTY
                : knoxCustomManagerService.getApplicationRestrictionsInternal(str, i);
    }

    public final boolean getBrowserSettingStatus(int i) {
        BrowserPolicy browserPolicy =
                (BrowserPolicy) EnterpriseService.getPolicyService("browser_policy");
        if (browserPolicy == null) {
            return true;
        }
        return browserPolicy.getBrowserSettingStatus(new ContextInfo(Binder.getCallingUid()), i);
    }

    public final List getELMPermissions(String str) {
        EnterpriseLicenseService enterpriseLicenseService =
                (EnterpriseLicenseService)
                        EnterpriseService.getPolicyService("enterprise_license_policy");
        if (enterpriseLicenseService == null) {
            return null;
        }
        return enterpriseLicenseService.getELMPermissions(str);
    }

    public final boolean getEmergencyCallOnly(boolean z) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.getEmergencyCallOnly(
                new ContextInfo(Binder.getCallingUid()), z);
    }

    public final boolean getExtendedCallInfoState() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getExtendedCallInfoState();
    }

    public final int getKeyboardMode() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getKeyboardMode();
    }

    public final String getNtpServer() {
        DateTimePolicy dateTimePolicy =
                (DateTimePolicy) EnterpriseService.getPolicyService("date_time_policy");
        if (dateTimePolicy == null) {
            return null;
        }
        return dateTimePolicy.mNtpInfo.getServer();
    }

    public final long getNtpTimeout() {
        DateTimePolicy dateTimePolicy =
                (DateTimePolicy) EnterpriseService.getPolicyService("date_time_policy");
        if (dateTimePolicy == null) {
            return 0L;
        }
        return dateTimePolicy.mNtpInfo.getTimeout();
    }

    public final int getProKioskHideNotificationMessages() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getHideNotificationMessages();
    }

    public final boolean getProKioskNotificationMessagesState() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return true;
        }
        return knoxCustomManagerService.getProKioskNotificationMessagesState();
    }

    public final boolean getProKioskState() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getProKioskState();
    }

    public final int getSensorDisabled() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getSensorDisabled();
    }

    public final boolean getToastEnabledState() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return true;
        }
        return knoxCustomManagerService.getToastEnabledState();
    }

    public final int getToastGravity() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getToastGravity();
    }

    public final boolean getToastGravityEnabledState() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getToastGravityEnabledState();
    }

    public final int getToastGravityXOffset() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getToastGravityXOffset();
    }

    public final int getToastGravityYOffset() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getToastGravityYOffset();
    }

    public final boolean getToastShowPackageNameState() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getToastShowPackageNameState();
    }

    public final String getUsbNetAddress(int i) {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return null;
        }
        return knoxCustomManagerService.getUsbNetAddress(i);
    }

    public final boolean getUsbNetStateInternal() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getUsbNetStateInternal();
    }

    public final boolean getVolumeButtonRotationState() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getVolumeButtonRotationState();
    }

    public final int getVolumeControlStream() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getVolumeControlStream();
    }

    public final boolean getVolumePanelEnabledState() {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return true;
        }
        return knoxCustomManagerService.getVolumePanelEnabledState();
    }

    public final boolean getWifiState() {
        return false;
    }

    public final boolean isAccountRemovalAllowed(String str, String str2, boolean z) {
        DeviceAccountPolicy deviceAccountPolicy =
                (DeviceAccountPolicy) EnterpriseService.getPolicyService("device_account_policy");
        if (deviceAccountPolicy == null) {
            return true;
        }
        return deviceAccountPolicy.isAccountRemovalAllowed(str, str2, z);
    }

    public final boolean isAllowedMamPackage(String str) {
        KnoxCustomManagerService knoxCustomManagerService =
                (KnoxCustomManagerService)
                        EnterpriseService.getPolicyService(
                                KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.isAllowedMamPackage(str);
    }

    public final boolean isAnyApplicationNameChangedAsUser(int i) {
        ApplicationPolicy applicationPolicy =
                (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            return false;
        }
        return applicationPolicy.isAnyApplicationNameChangedAsUser(i);
    }

    public final boolean isAudioRecordAllowed(boolean z) {
        RestrictionPolicy restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isAudioRecordAllowed(new ContextInfo(Binder.getCallingUid()), z);
    }

    public final boolean isAuditLogEnabledAsUser(int i) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return false;
        }
        return auditLogService.isAuditLogEnabledAsUser(i);
    }

    public final boolean isBackupAllowed(boolean z) {
        RestrictionPolicy restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isBackupAllowed(null, z);
    }

    public final boolean isBlockMmsWithStorageEnabled() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.isBlockMmsWithStorageEnabled(
                new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isBlockSmsWithStorageEnabled() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.isBlockSmsWithStorageEnabled(
                new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isBluetoothDeviceAllowed(String str) {
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        if (str == null || str.isEmpty()) {
            return false;
        }
        boolean isObjectAllowed = bluetoothPolicy.mDevicePolicy.isObjectAllowed(str);
        if (isObjectAllowed) {
            return isObjectAllowed;
        }
        RestrictionToastManager.show(R.string.config_deviceSpecificDeviceStatePolicyProvider);
        return isObjectAllowed;
    }

    public final boolean isBluetoothEnabled() {
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isBluetoothEnabled(true);
    }

    public final boolean isBluetoothLogEnabled() {
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return false;
        }
        return bluetoothPolicy.isBluetoothLogEnabled(null);
    }

    public final boolean isBluetoothUUIDAllowed(String str) {
        int i;
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        Iterator it = ((HashMap) bluetoothPolicy.mProfileMap).keySet().iterator();
        loop0:
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            Integer num = (Integer) it.next();
            List list = (List) ((HashMap) bluetoothPolicy.mProfileMap).get(num);
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (((String) list.get(i2)).equalsIgnoreCase(str)) {
                    i = num.intValue();
                    break loop0;
                }
            }
        }
        if (!bluetoothPolicy.mProfilePolicy.isObjectAllowed(str)) {
            BluetoothPolicy.showProfileBlockedToast(i);
        } else {
            if (i == -1) {
                return true;
            }
            Binder.getCallingUid();
            if (bluetoothPolicy.isProfileEnabled(i)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isCaCertificateTrustedAsUser(byte[] r7, boolean r8, boolean r9, int r10) {
        /*
            r6 = this;
            java.lang.String r6 = "Could not convert certificate."
            java.lang.String r0 = "EDMProxyService"
            java.lang.String r1 = "certificate_policy"
            java.lang.Object r1 = com.android.server.enterprise.EnterpriseService.getPolicyService(r1)
            com.android.server.enterprise.certificate.CertificatePolicy r1 = (com.android.server.enterprise.certificate.CertificatePolicy) r1
            r2 = 1
            if (r1 != 0) goto L11
            return r2
        L11:
            boolean r3 = r1.isCertificateTrustedUntrustedEnabledAsUser(r10)
            if (r3 != 0) goto L18
            return r2
        L18:
            r3 = 0
            if (r7 != 0) goto L1c
            return r3
        L1c:
            java.util.List r4 = android.security.Credentials.convertFromPem(r7)     // Catch: java.lang.IllegalArgumentException -> L29 java.security.cert.CertificateException -> L89 java.io.IOException -> L8d
            if (r4 == 0) goto L2b
            boolean r5 = r4.isEmpty()     // Catch: java.lang.IllegalArgumentException -> L29 java.security.cert.CertificateException -> L89 java.io.IOException -> L8d
            if (r5 == 0) goto L3e
            goto L2b
        L29:
            r6 = move-exception
            goto L73
        L2b:
            byte[] r7 = com.android.server.enterprise.utils.CertificateUtil.convertDerToPem(r7)     // Catch: java.lang.IllegalArgumentException -> L29 java.security.cert.CertificateException -> L89 java.io.IOException -> L8d
            if (r7 == 0) goto L35
            java.util.List r4 = android.security.Credentials.convertFromPem(r7)     // Catch: java.lang.IllegalArgumentException -> L29 java.security.cert.CertificateException -> L89 java.io.IOException -> L8d
        L35:
            if (r4 == 0) goto L6d
            boolean r7 = r4.isEmpty()     // Catch: java.lang.IllegalArgumentException -> L29 java.security.cert.CertificateException -> L89 java.io.IOException -> L8d
            if (r7 == 0) goto L3e
            goto L6d
        L3e:
            com.android.server.knox.dar.DarManagerService r6 = r1.mDarManagerService
            java.security.cert.Certificate[] r7 = new java.security.cert.Certificate[r3]
            java.lang.Object[] r7 = r4.toArray(r7)
            java.security.cert.Certificate[] r7 = (java.security.cert.Certificate[]) r7
            boolean r6 = r6.checkDeviceIntegrity(r7)
            if (r6 == 0) goto L4f
            return r2
        L4f:
            java.util.Iterator r6 = r4.iterator()
        L53:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L6c
            java.lang.Object r7 = r6.next()
            java.security.cert.X509Certificate r7 = (java.security.cert.X509Certificate) r7
            com.samsung.android.knox.keystore.CertificateInfo r0 = new com.samsung.android.knox.keystore.CertificateInfo
            r0.<init>(r7)
            boolean r7 = r1.isCaCertificateTrustedAsUser(r0, r8, r9, r10)
            r2 = r2 & r7
            if (r2 != 0) goto L53
            return r3
        L6c:
            return r2
        L6d:
            java.lang.String r7 = "Could not convert one certificate."
            android.util.Log.d(r0, r7)     // Catch: java.lang.IllegalArgumentException -> L29 java.security.cert.CertificateException -> L89 java.io.IOException -> L8d
            return r3
        L73:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "Not a certificate "
            r7.<init>(r8)
            java.lang.String r6 = r6.getMessage()
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            android.util.Log.d(r0, r6)
            return r3
        L89:
            android.util.Log.e(r0, r6)
            return r3
        L8d:
            android.util.Log.e(r0, r6)
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.EDMProxyService.isCaCertificateTrustedAsUser(byte[],"
                    + " boolean, boolean, int):boolean");
    }

    public final boolean isCallingCaptureEnabled() {
        DeviceInfo deviceInfo = (DeviceInfo) EnterpriseService.getPolicyService("device_info");
        if (deviceInfo == null) {
            return false;
        }
        return deviceInfo.isCallingCaptureEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isCertificateTrustedUntrustedEnabledAsUser(int i) {
        CertificatePolicy certificatePolicy =
                (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return false;
        }
        return certificatePolicy.isCertificateTrustedUntrustedEnabledAsUser(i);
    }

    public final boolean isCertificateValidationAtInstallEnabledAsUser(int i) {
        CertificatePolicy certificatePolicy =
                (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return false;
        }
        return certificatePolicy.isCertificateValidationAtInstallEnabledAsUser(i);
    }

    public final boolean isClipboardAllowed(boolean z) {
        RestrictionPolicy restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isClipboardAllowed(new ContextInfo(Binder.getCallingUid()), z);
    }

    public final boolean isClipboardShareAllowed() {
        RestrictionPolicy restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isClipboardShareAllowed(new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isCopyContactToSimAllowed(int i) {
        boolean z;
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        Iterator it =
                phoneRestrictionPolicy
                        .mEdmStorageProvider
                        .getBooleanListAsUser(0, "PHONERESTRICTION", "copyContactToSimEnabled")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        if (!z) {
            if (i == 1) {
                Log.d("PhoneRestrictionPolicy", "Access to PB ADD ");
                RestrictionToastManager.show(R.string.bluetooth_a2dp_audio_route_name);
            } else if (i != 3) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i,
                        "isCopyContactToSimAllowed wrong message value: ",
                        "PhoneRestrictionPolicy");
            } else {
                Log.d("PhoneRestrictionPolicy", "Access to PB Edit ");
                RestrictionToastManager.show(R.string.indeterminate_progress_44);
            }
        }
        return z;
    }

    public final boolean isDataAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isOperationAllowed(1, i);
    }

    public final boolean isDiscoverableEnabled() {
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isDiscoverableEnabled(null);
    }

    public final boolean isFactoryResetAllowed() {
        RestrictionPolicy restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isFactoryResetAllowed(null);
    }

    public final boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) {
        RestrictionPolicy restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isGoogleAccountsAutoSyncAllowedAsUser(i);
    }

    public final boolean isIncomingCallAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isOperationAllowed(2, i);
    }

    public final boolean isIncomingMmsAllowed() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.getSmsMmsAllowed("allowIncomingMms");
    }

    public final boolean isIncomingSmsAllowed() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        new ContextInfo(Binder.getCallingUid());
        return phoneRestrictionPolicy.getSmsMmsAllowed("allowIncomingSms");
    }

    public final boolean isIncomingSmsAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isOperationAllowed(4, i);
    }

    public final boolean isKnoxBluetoothEnabled(int i) {
        KnoxMUMContainerPolicy knoxMUMContainerPolicy =
                (KnoxMUMContainerPolicy) ServiceManager.getService("mum_container_policy");
        if (knoxMUMContainerPolicy == null) {
            return false;
        }
        return knoxMUMContainerPolicy.isBluetoothEnabled(new ContextInfo(0, i));
    }

    public final boolean isLimitNumberOfSmsEnabled() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.isLimitNumberOfSmsEnabled(
                new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isMicrophoneEnabled(boolean z) {
        RestrictionPolicy restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isMicrophoneEnabled(new ContextInfo(Binder.getCallingUid()), z);
    }

    public final boolean isMmsAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isOperationAllowed(6, i);
    }

    public final boolean isMockLocationEnabled() {
        RestrictionPolicy restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isMockLocationEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isNtpSetByMDM() {
        return NetworkTimeUpdateService.isNtpSetByMDM();
    }

    public final boolean isOcspCheckEnabled() {
        CertificatePolicy certificatePolicy =
                (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return false;
        }
        return certificatePolicy.isOcspCheckEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isOutgoingCallAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isOperationAllowed(3, i);
    }

    public final boolean isOutgoingCallsAllowed() {
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isOutgoingCallsAllowed(true);
    }

    public final boolean isOutgoingSmsAllowed() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        new ContextInfo(Binder.getCallingUid());
        return phoneRestrictionPolicy.getSmsMmsAllowed("allowOutgoingSms");
    }

    public final boolean isOutgoingSmsAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isOperationAllowed(5, i);
    }

    public final boolean isPackageAllowedToAccessExternalSdcard(int i, int i2) {
        KnoxMUMContainerPolicy knoxMUMContainerPolicy =
                (KnoxMUMContainerPolicy) ServiceManager.getService("mum_container_policy");
        if (knoxMUMContainerPolicy == null) {
            return false;
        }
        knoxMUMContainerPolicy.isPackageAllowedToAccessExternalSdcard(new ContextInfo(0, i), i2);
        return false;
    }

    public final boolean isPackageInAvrWhitelist(int i) {
        ApplicationPolicy applicationPolicy =
                (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy != null) {
            return applicationPolicy.isPackageInWhitelistInternal(
                    3, UserHandle.getCallingUserId(), i);
        }
        Log.e("EDMProxyService", "AVR Policy returning false due null applicationPolicy");
        return false;
    }

    public final boolean isPairingEnabled() {
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isPairingEnabled(true);
    }

    public final boolean isProfileEnabled(int i) {
        BluetoothPolicy bluetoothPolicy =
                (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isProfileEnabledInternal(i, true);
    }

    public final boolean isRevocationCheckEnabled() {
        CertificatePolicy certificatePolicy =
                (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return false;
        }
        return certificatePolicy.isRevocationCheckEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isRoamingDataEnabled() {
        RoamingPolicy roamingPolicy =
                (RoamingPolicy) EnterpriseService.getPolicyService("roaming_policy");
        if (roamingPolicy == null) {
            return true;
        }
        return roamingPolicy.isRoamingDataEnabled(null);
    }

    public final boolean isRoamingPushEnabled() {
        RoamingPolicy roamingPolicy =
                (RoamingPolicy) EnterpriseService.getPolicyService("roaming_policy");
        if (roamingPolicy == null) {
            return true;
        }
        return roamingPolicy.isRoamingPushEnabled(null);
    }

    public final boolean isSMSCaptureEnabled() {
        DeviceInfo deviceInfo = (DeviceInfo) EnterpriseService.getPolicyService("device_info");
        if (deviceInfo == null) {
            return false;
        }
        return deviceInfo.isSMSCaptureEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isScreenLockPatternVisibilityEnabled() {
        PasswordPolicy passwordPolicy =
                (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
        if (passwordPolicy == null) {
            return true;
        }
        return passwordPolicy.isScreenLockPatternVisibilityEnabled(
                new ContextInfo(Binder.getCallingUid()));
    }

    public final boolean isScreenLockPatternVisibilityEnabledAsUser(int i) {
        PasswordPolicy passwordPolicy =
                (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
        if (passwordPolicy == null) {
            return true;
        }
        return passwordPolicy.isScreenLockPatternVisibilityEnabledAsUser(i);
    }

    public final boolean isSmsPatternCheckRequired() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isSmsPatternCheckRequired();
    }

    public final boolean isTaskManagerAllowed(boolean z) {
        KioskModeService kioskModeService =
                (KioskModeService) EnterpriseService.getPolicyService("kioskmode");
        if (kioskModeService == null) {
            return true;
        }
        new ContextInfo(Binder.getCallingUid());
        return kioskModeService.isTaskManagerAllowedAsUser(z, 0);
    }

    public final boolean isVideoRecordAllowed(boolean z) {
        RestrictionPolicy restrictionPolicy =
                (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isVideoRecordAllowed(new ContextInfo(Binder.getCallingUid()), z);
    }

    public final boolean isWapPushAllowed() {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isWapPushAllowed(null);
    }

    public final void logEvent(int i, int i2, List list) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.logEventAsUser(null, -1, i, i2, list);
    }

    public final void logEventAsUser(int i, int i2, int i3, List list) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.logEventAsUser(null, i, i2, i3, list);
    }

    public final void logEventForComponent(int i, String str, int i2, List list) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.logEventAsUser(str, -1, i, i2, list);
    }

    public final void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) {
        CertificatePolicy certificatePolicy =
                (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return;
        }
        certificatePolicy.notifyCertificateFailureAsUser(str, str2, z, i);
    }

    public final void notifyCertificateRemovedAsUser(String str, int i) {
        CertificatePolicy certificatePolicy =
                (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return;
        }
        certificatePolicy.notifyCertificateRemovedAsUser(str, i);
    }

    public final void notifyPasswordPolicyOneLockChanged(boolean z, int i) {
        PasswordPolicy passwordPolicy =
                (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
        if (passwordPolicy == null) {
            return;
        }
        passwordPolicy.updateSystemUIMonitor$9(i);
        if (z) {
            PasswordPolicy.Injector injector = passwordPolicy.mInjector;
            PasswordPolicy$$ExternalSyntheticLambda14 passwordPolicy$$ExternalSyntheticLambda14 =
                    new PasswordPolicy$$ExternalSyntheticLambda14(passwordPolicy, i, 1);
            injector.getClass();
            Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda14);
        }
    }

    public final void redactedAuditLogger(
            int i, int i2, boolean z, int i3, String str, String str2, String str3) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.redactedAuditLogger(null, i, i2, z, i3, str, str2, str3);
    }

    public final void redactedAuditLoggerAsUser(
            int i, int i2, boolean z, int i3, String str, String str2, String str3, int i4) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.redactedAuditLoggerAsUser(null, i, i2, z, i3, str, str2, str3, i4);
    }

    public final void redactedAuditLoggerPrivileged(
            int i, int i2, boolean z, int i3, String str, String str2, String str3) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService != null && AuditLogService.checkAuditPrivilegedAllowed(str)) {
            auditLogService.auditLoggerInternal(
                    null,
                    i,
                    i2,
                    z,
                    i3,
                    str,
                    auditLogService.appendLogMessageWithCallingUser(str2),
                    str3,
                    -1);
        }
    }

    public final void redactedAuditLoggerPrivilegedAsUser(
            int i, int i2, boolean z, int i3, String str, String str2, String str3, int i4) {
        AuditLogService auditLogService =
                (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService != null && AuditLogService.checkAuditPrivilegedAllowed(str)) {
            auditLogService.auditLoggerInternal(
                    null,
                    i,
                    i2,
                    z,
                    i3,
                    str,
                    auditLogService.appendLogMessageWithCallingUser(str2),
                    str3,
                    i4);
        }
    }

    public final boolean registerSystemUICallback(
            ISystemUIAdapterCallback iSystemUIAdapterCallback) {
        int i;
        SystemUIAdapter systemUIAdapter = SystemUIAdapter.getInstance(this.mContext);
        systemUIAdapter.getClass();
        Log.d(
                "SystemUIAdapter",
                "registerSystemUICallback() is called " + iSystemUIAdapterCallback);
        int callingUid = Binder.getCallingUid();
        try {
            i =
                    SystemUIAdapter.mContext
                            .getPackageManager()
                            .getPackageUidAsUser(Constants.SYSTEMUI_PACKAGE_NAME, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(
                    "SystemUIAdapter",
                    "isCalledFromSystemUI() : Unable to resolve SystemUI's UID.",
                    e);
            i = -1;
        }
        int appId = UserHandle.getAppId(callingUid);
        String nameForUid = SystemUIAdapter.mContext.getPackageManager().getNameForUid(callingUid);
        if (nameForUid != null) {
            int lastIndexOf = nameForUid.lastIndexOf(":");
            if (lastIndexOf != -1) {
                nameForUid = nameForUid.substring(0, lastIndexOf);
            }
            if (nameForUid.equals("android.uid.systemui") && appId == i) {
                int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(null);
                if (callingOrCurrentUserId != 0) {
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                            callingOrCurrentUserId,
                            "registerSystemUICallback() has failed because it's only allowed"
                                + " user_system, but userId = ",
                            "SystemUIAdapter");
                    return false;
                }
                if (iSystemUIAdapterCallback == null) {
                    Log.d("SystemUIAdapter", "registerSystemUICallback() has failed.");
                    return false;
                }
                try {
                    int i2 = systemUIAdapter.mRegisteredCount + 1;
                    systemUIAdapter.mRegisteredCount = i2;
                    systemUIAdapter.mCallbacks.put(Integer.valueOf(i2), iSystemUIAdapterCallback);
                    iSystemUIAdapterCallback
                            .asBinder()
                            .linkToDeath(
                                    systemUIAdapter.new SystemUIAdapterCallbackDeathRecipient(i2),
                                    0);
                    Log.d("SystemUIAdapter", "registerSystemUICallback() successfully added");
                } catch (Exception unused) {
                }
                systemUIAdapter.isCallbackDied = false;
                Log.d(
                        "SystemUIAdapter",
                        "registerSystemUICallback() callback has registered. "
                                + systemUIAdapter.mRegisteredCount);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    systemUIAdapter.updateSystemUIMonitor(0);
                } catch (Exception unused2) {
                } catch (Throwable th) {
                    throw th;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        if (ISecurityPolicy.Stub.asInterface(
                                                ServiceManager.getService("security_policy"))
                                        != null
                                && systemUIAdapter.isFistcalled) {
                            systemUIAdapter.isFistcalled = false;
                            ISecurityPolicy.Stub.asInterface(
                                            ServiceManager.getService("security_policy"))
                                    .onKeyguardLaunched();
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Exception e2) {
                    Log.e("SystemUIAdapter", "onKeyguardLaunched() has failed.", e2);
                }
                return true;
            }
        }
        Log.d(
                "SystemUIAdapter",
                "registerSystemUICallback() has failed because it's only allowed to call by"
                    + " SystemUI ");
        return false;
    }

    public final boolean shallForceNtpMdmValues() {
        return NetworkTimeUpdateService.shallForceNtpMdmValues();
    }

    public final void storeBlockedSmsMms(
            boolean z, byte[] bArr, String str, int i, String str2, String str3, String str4) {
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                (PhoneRestrictionPolicy)
                        EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return;
        }
        PhoneRestrictionPolicy.enforcePhoneApp();
        ContentValues m =
                AccountManagerService$$ExternalSyntheticOutline0.m(
                        "smsMmsPdu", HexDump.toHexString(bArr));
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, m, "smsMmsSendType", z ? 1 : 0, "smsMmsType");
        m.put("smsMmsOrigAddress", str);
        if (!z) {
            m.put("smsMmsTimeStamp", str2);
            m.put("smsMmsMessageId", str3);
            m.put("smsMmsSubId", str4);
        }
        try {
            phoneRestrictionPolicy.mEdmStorageProvider.insertConfiguration(
                    "SMSMMSBlockedDelivery", m);
            Log.w("PhoneRestrictionPolicy", "sms/mms stored successfully");
        } catch (Exception unused) {
            Log.w("PhoneRestrictionPolicy", "could not write sms/mms into edm database");
        }
    }

    public final void storeCalling(String str, String str2, String str3, String str4, boolean z) {
        DeviceInfo deviceInfo = (DeviceInfo) EnterpriseService.getPolicyService("device_info");
        if (deviceInfo == null) {
            return;
        }
        deviceInfo.storeCalling(str, str2, str3, str4, z);
    }

    public final void storeSMS(String str, String str2, String str3, boolean z) {
        DeviceInfo deviceInfo = (DeviceInfo) EnterpriseService.getPolicyService("device_info");
        if (deviceInfo == null) {
            return;
        }
        deviceInfo.storeSMS(str, str2, str3, z);
    }

    public final byte[] ucmDecrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        CredentialManagerService credentialManagerService =
                (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null) {
            return null;
        }
        return credentialManagerService.decrypt(str, bArr, str2, bundle).mData;
    }

    public final byte[] ucmEncrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        CredentialManagerService credentialManagerService =
                (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null) {
            return null;
        }
        return credentialManagerService.encrypt(str, bArr, str2, bundle).mData;
    }

    public final byte[] ucmGetCertificateChain(String str) {
        CredentialManagerService credentialManagerService =
                (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null) {
            return null;
        }
        return credentialManagerService.getCertificateChain(str).mData;
    }

    public final byte[] ucmMac(String str, byte[] bArr, String str2) {
        CredentialManagerService credentialManagerService =
                (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null) {
            return null;
        }
        return credentialManagerService.mac(str, bArr, str2).mData;
    }

    public final byte[] ucmSign(String str, byte[] bArr, String str2) {
        CredentialManagerService credentialManagerService =
                (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null) {
            return null;
        }
        return credentialManagerService.sign(str, bArr, str2).mData;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x004c A[LOOP:0: B:16:0x0046->B:18:0x004c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int validateCertificateAtInstallAsUser(byte[] r6, int r7) {
        /*
            r5 = this;
            java.lang.String r5 = "Could not convert certificate."
            java.lang.String r0 = "EDMProxyService"
            java.lang.String r1 = "certificate_policy"
            java.lang.Object r1 = com.android.server.enterprise.EnterpriseService.getPolicyService(r1)
            com.android.server.enterprise.certificate.CertificatePolicy r1 = (com.android.server.enterprise.certificate.CertificatePolicy) r1
            if (r1 == 0) goto L97
            boolean r2 = r1.isCertificateValidationAtInstallEnabledAsUser(r7)
            if (r2 != 0) goto L17
            goto L97
        L17:
            r2 = 7
            if (r6 != 0) goto L1b
            return r2
        L1b:
            java.util.List r3 = android.security.Credentials.convertFromPem(r6)     // Catch: java.lang.IllegalArgumentException -> L28 java.security.cert.CertificateException -> L8f java.io.IOException -> L93
            if (r3 == 0) goto L2a
            boolean r4 = r3.isEmpty()     // Catch: java.lang.IllegalArgumentException -> L28 java.security.cert.CertificateException -> L8f java.io.IOException -> L93
            if (r4 == 0) goto L3d
            goto L2a
        L28:
            r5 = move-exception
            goto L79
        L2a:
            byte[] r6 = com.android.server.enterprise.utils.CertificateUtil.convertDerToPem(r6)     // Catch: java.lang.IllegalArgumentException -> L28 java.security.cert.CertificateException -> L8f java.io.IOException -> L93
            if (r6 == 0) goto L34
            java.util.List r3 = android.security.Credentials.convertFromPem(r6)     // Catch: java.lang.IllegalArgumentException -> L28 java.security.cert.CertificateException -> L8f java.io.IOException -> L93
        L34:
            if (r3 == 0) goto L73
            boolean r6 = r3.isEmpty()     // Catch: java.lang.IllegalArgumentException -> L28 java.security.cert.CertificateException -> L8f java.io.IOException -> L93
            if (r6 == 0) goto L3d
            goto L73
        L3d:
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r6 = r3.iterator()
        L46:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L5b
            java.lang.Object r0 = r6.next()
            java.security.cert.X509Certificate r0 = (java.security.cert.X509Certificate) r0
            com.samsung.android.knox.keystore.CertificateInfo r2 = new com.samsung.android.knox.keystore.CertificateInfo
            r2.<init>(r0)
            r5.add(r2)
            goto L46
        L5b:
            int r6 = r5.size()
            r0 = 1
            if (r6 != r0) goto L6e
            r6 = 0
            java.lang.Object r5 = r5.get(r6)
            com.samsung.android.knox.keystore.CertificateInfo r5 = (com.samsung.android.knox.keystore.CertificateInfo) r5
            int r5 = r1.validateCertificateAtInstallAsUser(r5, r7)
            return r5
        L6e:
            int r5 = r1.validateChainAtInstallAsUser(r5, r7)
            return r5
        L73:
            java.lang.String r6 = "Could not convert one certificate."
            android.util.Log.d(r0, r6)     // Catch: java.lang.IllegalArgumentException -> L28 java.security.cert.CertificateException -> L8f java.io.IOException -> L93
            return r2
        L79:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Not a certificate "
            r6.<init>(r7)
            java.lang.String r5 = r5.getMessage()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            android.util.Log.d(r0, r5)
            return r2
        L8f:
            android.util.Log.e(r0, r5)
            return r2
        L93:
            android.util.Log.e(r0, r5)
            return r2
        L97:
            r5 = -1
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.EDMProxyService.validateCertificateAtInstallAsUser(byte[],"
                    + " int):int");
    }
}
