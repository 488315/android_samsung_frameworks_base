package com.android.server.enterprise;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.enterprise.IEDMProxy;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.security.Credentials;
import android.util.Log;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
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
import com.android.server.enterprise.utils.CertificateUtil;
import com.android.server.timedetector.NetworkTimeUpdateService;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.keystore.CertificateInfo;
import com.samsung.android.knox.ucm.core.ucmRetParcelable;
import com.samsung.ucm.ucmservice.CredentialManagerService;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class EDMProxyService extends IEDMProxy.Stub {
    public final Context mContext;

    public EDMProxyService(Context context) {
        this.mContext = context;
    }

    public void addCallsCount(String str) {
        DeviceInfo deviceInfo = (DeviceInfo) EnterpriseService.getPolicyService("device_info");
        if (deviceInfo == null) {
            return;
        }
        deviceInfo.addCallsCount(str);
    }

    public boolean isCallingCaptureEnabled() {
        DeviceInfo deviceInfo = (DeviceInfo) EnterpriseService.getPolicyService("device_info");
        if (deviceInfo == null) {
            return false;
        }
        return deviceInfo.isCallingCaptureEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public void storeCalling(String str, String str2, String str3, String str4, boolean z) {
        DeviceInfo deviceInfo = (DeviceInfo) EnterpriseService.getPolicyService("device_info");
        if (deviceInfo == null) {
            return;
        }
        deviceInfo.storeCalling(str, str2, str3, str4, z);
    }

    public boolean isSMSCaptureEnabled() {
        DeviceInfo deviceInfo = (DeviceInfo) EnterpriseService.getPolicyService("device_info");
        if (deviceInfo == null) {
            return false;
        }
        return deviceInfo.isSMSCaptureEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public void storeSMS(String str, String str2, String str3, boolean z) {
        DeviceInfo deviceInfo = (DeviceInfo) EnterpriseService.getPolicyService("device_info");
        if (deviceInfo == null) {
            return;
        }
        deviceInfo.storeSMS(str, str2, str3, z);
    }

    public byte[] getApplicationIconFromDb(String str, int i) {
        ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            return null;
        }
        return applicationPolicy.getApplicationIconFromDbAsUser(str, i);
    }

    public boolean getAddHomeShorcutRequested() {
        ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            return false;
        }
        return applicationPolicy.getAddHomeShorcutRequested();
    }

    public boolean getAllowBluetoothDataTransfer(boolean z) {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.getAllowBluetoothDataTransfer(null, z);
    }

    public boolean isOutgoingCallsAllowed() {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isOutgoingCallsAllowed(true);
    }

    public boolean isBluetoothUUIDAllowed(String str) {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isBluetoothUUIDAllowedInternal(str);
    }

    public boolean isProfileEnabled(int i) {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isProfileEnabledInternal(i, true);
    }

    public boolean isBluetoothDeviceAllowed(String str) {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isBluetoothDeviceAllowed(str, true);
    }

    public boolean isPairingEnabled() {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isPairingEnabled(true);
    }

    public boolean isDiscoverableEnabled() {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isDiscoverableEnabled(null);
    }

    public boolean isBluetoothEnabled() {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return true;
        }
        return bluetoothPolicy.isBluetoothEnabled(true);
    }

    public boolean getBrowserSettingStatus(int i) {
        BrowserPolicy browserPolicy = (BrowserPolicy) EnterpriseService.getPolicyService("browser_policy");
        if (browserPolicy == null) {
            return true;
        }
        return browserPolicy.getBrowserSettingStatus(new ContextInfo(Binder.getCallingUid()), i);
    }

    public boolean isRoamingPushEnabled() {
        RoamingPolicy roamingPolicy = (RoamingPolicy) EnterpriseService.getPolicyService("roaming_policy");
        if (roamingPolicy == null) {
            return true;
        }
        return roamingPolicy.isRoamingPushEnabled(null);
    }

    public boolean isRoamingDataEnabled() {
        RoamingPolicy roamingPolicy = (RoamingPolicy) EnterpriseService.getPolicyService("roaming_policy");
        if (roamingPolicy == null) {
            return true;
        }
        return roamingPolicy.isRoamingDataEnabled(null);
    }

    public boolean isClipboardAllowed(boolean z) {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isClipboardAllowed(new ContextInfo(Binder.getCallingUid()), z);
    }

    public boolean isMicrophoneEnabled(boolean z) {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isMicrophoneEnabled(new ContextInfo(Binder.getCallingUid()), z);
    }

    public boolean getEmergencyCallOnly(boolean z) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.getEmergencyCallOnly(new ContextInfo(Binder.getCallingUid()), z);
    }

    public boolean addNumberOfIncomingCalls() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.addNumberOfIncomingCalls();
    }

    public boolean addNumberOfOutgoingCalls() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.addNumberOfOutgoingCalls();
    }

    public boolean isLimitNumberOfSmsEnabled() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.isLimitNumberOfSmsEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public boolean addNumberOfIncomingSms() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.addNumberOfIncomingSms();
    }

    public boolean addNumberOfOutgoingSms() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.addNumberOfOutgoingSms();
    }

    public boolean decreaseNumberOfOutgoingSms() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.decreaseNumberOfOutgoingSms();
    }

    public boolean canOutgoingSms(String str) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.canOutgoingSms(str);
    }

    public boolean canIncomingSms(String str) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.canIncomingSms(str);
    }

    public boolean isSmsPatternCheckRequired() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isSmsPatternCheckRequired();
    }

    public String getNtpServer() {
        DateTimePolicy dateTimePolicy = (DateTimePolicy) EnterpriseService.getPolicyService("date_time_policy");
        if (dateTimePolicy == null) {
            return null;
        }
        return dateTimePolicy.getNtpServer();
    }

    public long getNtpTimeout() {
        DateTimePolicy dateTimePolicy = (DateTimePolicy) EnterpriseService.getPolicyService("date_time_policy");
        if (dateTimePolicy == null) {
            return 0L;
        }
        return dateTimePolicy.getNtpTimeout();
    }

    public boolean shallForceNtpMdmValues() {
        return NetworkTimeUpdateService.shallForceNtpMdmValues();
    }

    public boolean isNtpSetByMDM() {
        return NetworkTimeUpdateService.isNtpSetByMDM();
    }

    public boolean isScreenLockPatternVisibilityEnabled() {
        PasswordPolicy passwordPolicy = (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
        if (passwordPolicy == null) {
            return true;
        }
        return passwordPolicy.isScreenLockPatternVisibilityEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public boolean isScreenLockPatternVisibilityEnabledAsUser(int i) {
        PasswordPolicy passwordPolicy = (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
        if (passwordPolicy == null) {
            return true;
        }
        return passwordPolicy.isScreenLockPatternVisibilityEnabledAsUser(i);
    }

    public boolean isBackupAllowed(boolean z) {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isBackupAllowed(null, z);
    }

    public boolean isFactoryResetAllowed() {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isFactoryResetAllowed(null);
    }

    public boolean isBluetoothLogEnabled() {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return false;
        }
        return bluetoothPolicy.isBluetoothLogEnabled(null);
    }

    public void bluetoothLog(String str, String str2) {
        BluetoothPolicy bluetoothPolicy = (BluetoothPolicy) EnterpriseService.getPolicyService("bluetooth_policy");
        if (bluetoothPolicy == null) {
            return;
        }
        bluetoothPolicy.bluetoothLog(new ContextInfo(Binder.getCallingUid()), str, str2);
    }

    public boolean isTaskManagerAllowed(boolean z) {
        KioskModeService kioskModeService = (KioskModeService) EnterpriseService.getPolicyService("kioskmode");
        if (kioskModeService == null) {
            return true;
        }
        return kioskModeService.isTaskManagerAllowed(new ContextInfo(Binder.getCallingUid()), z);
    }

    public boolean isIncomingSmsAllowed() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isIncomingSmsAllowed(new ContextInfo(Binder.getCallingUid()));
    }

    public boolean isOutgoingSmsAllowed() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isOutgoingSmsAllowed(new ContextInfo(Binder.getCallingUid()));
    }

    public boolean isIncomingMmsAllowed() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isIncomingMmsAllowed(null);
    }

    public void AuditLogger(int i, int i2, boolean z, int i3, String str, String str2) {
        AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.AuditLogger(null, i, i2, z, i3, str, str2);
    }

    public void RedactedAuditLogger(int i, int i2, boolean z, int i3, String str, String str2, String str3) {
        AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.RedactedAuditLogger(null, i, i2, z, i3, str, str2, str3);
    }

    public void AuditLoggerAsUser(int i, int i2, boolean z, int i3, String str, String str2, int i4) {
        AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.AuditLoggerAsUser(null, i, i2, z, i3, str, str2, i4);
    }

    public void RedactedAuditLoggerAsUser(int i, int i2, boolean z, int i3, String str, String str2, String str3, int i4) {
        AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.RedactedAuditLoggerAsUser(null, i, i2, z, i3, str, str2, str3, i4);
    }

    public void AuditLoggerPrivileged(int i, int i2, boolean z, int i3, String str, String str2) {
        AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.AuditLoggerPrivileged(null, i, i2, z, i3, str, str2);
    }

    public void RedactedAuditLoggerPrivileged(int i, int i2, boolean z, int i3, String str, String str2, String str3) {
        AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.RedactedAuditLoggerPrivileged(null, i, i2, z, i3, str, str2, str3);
    }

    public void AuditLoggerPrivilegedAsUser(int i, int i2, boolean z, int i3, String str, String str2, int i4) {
        AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.AuditLoggerPrivilegedAsUser(null, i, i2, z, i3, str, str2, i4);
    }

    public void RedactedAuditLoggerPrivilegedAsUser(int i, int i2, boolean z, int i3, String str, String str2, String str3, int i4) {
        AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return;
        }
        auditLogService.RedactedAuditLoggerPrivilegedAsUser(null, i, i2, z, i3, str, str2, str3, i4);
    }

    public boolean isAuditLogEnabledAsUser(int i) {
        AuditLogService auditLogService = (AuditLogService) EnterpriseService.getPolicyService("auditlog");
        if (auditLogService == null) {
            return false;
        }
        return auditLogService.isAuditLogEnabledAsUser(i);
    }

    public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) {
        CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return;
        }
        certificatePolicy.notifyCertificateFailureAsUser(str, str2, z, i);
    }

    public boolean isCaCertificateTrustedAsUser(byte[] bArr, boolean z, boolean z2, int i) {
        CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        boolean z3 = true;
        if (certificatePolicy == null || !certificatePolicy.isCertificateTrustedUntrustedEnabledAsUser(i)) {
            return true;
        }
        if (bArr == null) {
            return false;
        }
        try {
            List convertFromPem = Credentials.convertFromPem(bArr);
            if (convertFromPem == null || convertFromPem.isEmpty()) {
                byte[] convertDerToPem = CertificateUtil.convertDerToPem(bArr);
                if (convertDerToPem != null) {
                    convertFromPem = Credentials.convertFromPem(convertDerToPem);
                }
                if (convertFromPem == null || convertFromPem.isEmpty()) {
                    Log.d("EDMProxyService", "Could not convert one certificate.");
                    return false;
                }
            }
            Iterator it = convertFromPem.iterator();
            while (it.hasNext()) {
                z3 &= certificatePolicy.isCaCertificateTrustedAsUser(new CertificateInfo((X509Certificate) it.next()), z, z2, i);
                if (!z3) {
                    return false;
                }
            }
            return z3;
        } catch (IOException unused) {
            Log.e("EDMProxyService", "Could not convert certificate.");
            return false;
        } catch (IllegalArgumentException e) {
            Log.d("EDMProxyService", "Not a certificate " + e.getMessage());
            return false;
        } catch (CertificateException unused2) {
            Log.e("EDMProxyService", "Could not convert certificate.");
            return false;
        }
    }

    public boolean isCertificateTrustedUntrustedEnabledAsUser(int i) {
        CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return false;
        }
        return certificatePolicy.isCertificateTrustedUntrustedEnabledAsUser(i);
    }

    public boolean isCertificateValidationAtInstallEnabledAsUser(int i) {
        CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return false;
        }
        return certificatePolicy.isCertificateValidationAtInstallEnabledAsUser(i);
    }

    public int validateCertificateAtInstallAsUser(byte[] bArr, int i) {
        CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null || !certificatePolicy.isCertificateValidationAtInstallEnabledAsUser(i)) {
            return -1;
        }
        if (bArr == null) {
            return 7;
        }
        try {
            List convertFromPem = Credentials.convertFromPem(bArr);
            if (convertFromPem == null || convertFromPem.isEmpty()) {
                byte[] convertDerToPem = CertificateUtil.convertDerToPem(bArr);
                if (convertDerToPem != null) {
                    convertFromPem = Credentials.convertFromPem(convertDerToPem);
                }
                if (convertFromPem == null || convertFromPem.isEmpty()) {
                    Log.d("EDMProxyService", "Could not convert one certificate.");
                    return 7;
                }
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = convertFromPem.iterator();
            while (it.hasNext()) {
                arrayList.add(new CertificateInfo((X509Certificate) it.next()));
            }
            if (arrayList.size() == 1) {
                return certificatePolicy.validateCertificateAtInstallAsUser((CertificateInfo) arrayList.get(0), i);
            }
            return certificatePolicy.validateChainAtInstallAsUser(arrayList, i);
        } catch (IOException unused) {
            Log.e("EDMProxyService", "Could not convert certificate.");
            return 7;
        } catch (IllegalArgumentException e) {
            Log.d("EDMProxyService", "Not a certificate " + e.getMessage());
            return 7;
        } catch (CertificateException unused2) {
            Log.e("EDMProxyService", "Could not convert certificate.");
            return 7;
        }
    }

    public boolean isAudioRecordAllowed(boolean z) {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isAudioRecordAllowed(new ContextInfo(Binder.getCallingUid()), z);
    }

    public boolean isVideoRecordAllowed(boolean z) {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isVideoRecordAllowed(new ContextInfo(Binder.getCallingUid()), z);
    }

    public boolean isRevocationCheckEnabled() {
        CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return false;
        }
        return certificatePolicy.isRevocationCheckEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public boolean isOcspCheckEnabled() {
        CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return false;
        }
        return certificatePolicy.isOcspCheckEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public void notifyCertificateRemovedAsUser(String str, int i) {
        CertificatePolicy certificatePolicy = (CertificatePolicy) EnterpriseService.getPolicyService("certificate_policy");
        if (certificatePolicy == null) {
            return;
        }
        certificatePolicy.notifyCertificateRemovedAsUser(str, i);
    }

    public boolean isClipboardShareAllowed() {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isClipboardShareAllowed(new ContextInfo(Binder.getCallingUid()));
    }

    public boolean isBlockSmsWithStorageEnabled() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.isBlockSmsWithStorageEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public boolean isBlockMmsWithStorageEnabled() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return false;
        }
        return phoneRestrictionPolicy.isBlockMmsWithStorageEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public void storeBlockedSmsMms(boolean z, byte[] bArr, String str, int i, String str2, String str3, String str4) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return;
        }
        phoneRestrictionPolicy.storeBlockedSmsMms(z, bArr, str, i, str2, str3, str4);
    }

    public boolean isWapPushAllowed() {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isWapPushAllowed(null);
    }

    public boolean isMockLocationEnabled() {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isMockLocationEnabled(new ContextInfo(Binder.getCallingUid()));
    }

    public boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) {
        RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
        if (restrictionPolicy == null) {
            return true;
        }
        return restrictionPolicy.isGoogleAccountsAutoSyncAllowedAsUser(i);
    }

    public boolean isAccountRemovalAllowed(String str, String str2, boolean z) {
        DeviceAccountPolicy deviceAccountPolicy = (DeviceAccountPolicy) EnterpriseService.getPolicyService("device_account_policy");
        if (deviceAccountPolicy == null) {
            return true;
        }
        return deviceAccountPolicy.isAccountRemovalAllowed(str, str2, z);
    }

    public String getApplicationNameFromDb(String str, int i) {
        ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            return null;
        }
        return applicationPolicy.getApplicationNameFromDb(str, i);
    }

    public String getApplicationNameForComponent(String str, String str2, int i) {
        ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            return null;
        }
        return applicationPolicy.getApplicationNameFromDb(str, str2, i);
    }

    public boolean isAnyApplicationNameChangedAsUser(int i) {
        ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            return false;
        }
        return applicationPolicy.isAnyApplicationNameChangedAsUser(i);
    }

    public boolean isCopyContactToSimAllowed(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isCopyContactToSimAllowed(i);
    }

    public boolean getProKioskState() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getProKioskState();
    }

    public boolean getProKioskNotificationMessagesState() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return true;
        }
        return knoxCustomManagerService.getProKioskNotificationMessagesState();
    }

    public int getProKioskHideNotificationMessages() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getHideNotificationMessages();
    }

    public int getVolumeControlStream() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getVolumeControlStream();
    }

    public boolean getToastEnabledState() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return true;
        }
        return knoxCustomManagerService.getToastEnabledState();
    }

    public boolean getToastShowPackageNameState() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getToastShowPackageNameState();
    }

    public int getSensorDisabled() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getSensorDisabled();
    }

    public boolean getVolumePanelEnabledState() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return true;
        }
        return knoxCustomManagerService.getVolumePanelEnabledState();
    }

    public boolean getVolumeButtonRotationState() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getVolumeButtonRotationState();
    }

    public boolean getToastGravityEnabledState() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getToastGravityEnabledState();
    }

    public int getToastGravity() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getToastGravity();
    }

    public int getToastGravityXOffset() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getToastGravityXOffset();
    }

    public int getToastGravityYOffset() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getToastGravityYOffset();
    }

    public int getKeyboardMode() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return 0;
        }
        return knoxCustomManagerService.getKeyboardMode();
    }

    public boolean getWifiState() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getWifiState();
    }

    public boolean getUsbNetStateInternal() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getUsbNetStateInternal();
    }

    public String getUsbNetAddress(int i) {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return null;
        }
        return knoxCustomManagerService.getUsbNetAddress(i);
    }

    public Bundle getApplicationRestrictions(String str, int i) {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return Bundle.EMPTY;
        }
        return knoxCustomManagerService.getApplicationRestrictionsInternal(str, i);
    }

    public boolean getExtendedCallInfoState() {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.getExtendedCallInfoState();
    }

    public boolean isAllowedMamPackage(String str) {
        KnoxCustomManagerService knoxCustomManagerService = (KnoxCustomManagerService) EnterpriseService.getPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE);
        if (knoxCustomManagerService == null) {
            return false;
        }
        return knoxCustomManagerService.isAllowedMamPackage(str);
    }

    public boolean isKnoxBluetoothEnabled(int i) {
        KnoxMUMContainerPolicy knoxMUMContainerPolicy = (KnoxMUMContainerPolicy) ServiceManager.getService("mum_container_policy");
        if (knoxMUMContainerPolicy == null) {
            return false;
        }
        return knoxMUMContainerPolicy.isBluetoothEnabled(new ContextInfo(0, i));
    }

    public boolean isPackageAllowedToAccessExternalSdcard(int i, int i2) {
        KnoxMUMContainerPolicy knoxMUMContainerPolicy = (KnoxMUMContainerPolicy) ServiceManager.getService("mum_container_policy");
        if (knoxMUMContainerPolicy == null) {
            return false;
        }
        return knoxMUMContainerPolicy.isPackageAllowedToAccessExternalSdcard(new ContextInfo(0, i), i2);
    }

    public byte[] ucmGetCertificateChain(String str) {
        ucmRetParcelable certificateChain;
        CredentialManagerService credentialManagerService = (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null || (certificateChain = credentialManagerService.getCertificateChain(str)) == null) {
            return null;
        }
        return certificateChain.mData;
    }

    public byte[] ucmDecrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        ucmRetParcelable decrypt;
        CredentialManagerService credentialManagerService = (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null || (decrypt = credentialManagerService.decrypt(str, bArr, str2, bundle)) == null) {
            return null;
        }
        return decrypt.mData;
    }

    public byte[] ucmEncrypt(String str, byte[] bArr, String str2, Bundle bundle) {
        ucmRetParcelable encrypt;
        CredentialManagerService credentialManagerService = (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null || (encrypt = credentialManagerService.encrypt(str, bArr, str2, bundle)) == null) {
            return null;
        }
        return encrypt.mData;
    }

    public byte[] ucmSign(String str, byte[] bArr, String str2) {
        ucmRetParcelable sign;
        CredentialManagerService credentialManagerService = (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null || (sign = credentialManagerService.sign(str, bArr, str2)) == null) {
            return null;
        }
        return sign.mData;
    }

    public byte[] ucmMac(String str, byte[] bArr, String str2) {
        ucmRetParcelable mac;
        CredentialManagerService credentialManagerService = (CredentialManagerService) ServiceManager.getService("com.samsung.ucs.ucsservice");
        if (credentialManagerService == null || (mac = credentialManagerService.mac(str, bArr, str2)) == null) {
            return null;
        }
        return mac.mData;
    }

    public List getELMPermissions(String str) {
        EnterpriseLicenseService enterpriseLicenseService = (EnterpriseLicenseService) EnterpriseService.getPolicyService("enterprise_license_policy");
        if (enterpriseLicenseService == null) {
            return null;
        }
        return enterpriseLicenseService.getELMPermissions(str);
    }

    public boolean registerSystemUICallback(ISystemUIAdapterCallback iSystemUIAdapterCallback) {
        return SystemUIAdapter.getInstance(this.mContext).registerSystemUICallback(iSystemUIAdapterCallback);
    }

    public boolean isPackageInAvrWhitelist(int i) {
        ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (applicationPolicy == null) {
            Log.e("EDMProxyService", "AVR Policy returning false due null applicationPolicy");
            return false;
        }
        return applicationPolicy.isPackageInWhitelistInternal(3, UserHandle.getCallingUserId(), i);
    }

    public void notifyPasswordPolicyOneLockChanged(boolean z, int i) {
        PasswordPolicy passwordPolicy = (PasswordPolicy) EnterpriseService.getPolicyService("password_policy");
        if (passwordPolicy == null) {
            return;
        }
        passwordPolicy.notifyPasswordPolicyOneLockChanged(z, i);
    }

    public boolean isIncomingSmsAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isIncomingSmsAllowedFromSimSlot(i);
    }

    public boolean isOutgoingSmsAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isOutgoingSmsAllowedFromSimSlot(i);
    }

    public boolean isMmsAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isMmsAllowedFromSimSlot(i);
    }

    public boolean isOutgoingCallAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isOutgoingCallAllowedFromSimSlot(i);
    }

    public boolean isIncomingCallAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isIncomingCallAllowedFromSimSlot(i);
    }

    public boolean canIncomingCall(String str) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.canIncomingCall(str);
    }

    public boolean canOutgoingCall(String str) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.canOutgoingCall(str);
    }

    public boolean isDataAllowedFromSimSlot(int i) {
        PhoneRestrictionPolicy phoneRestrictionPolicy = (PhoneRestrictionPolicy) EnterpriseService.getPolicyService("phone_restriction_policy");
        if (phoneRestrictionPolicy == null) {
            return true;
        }
        return phoneRestrictionPolicy.isDataAllowedFromSimSlot(i);
    }
}
