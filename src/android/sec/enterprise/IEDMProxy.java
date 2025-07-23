package android.sec.enterprise;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.sec.enterprise.auditlog.AuditLogParams;
import android.sec.enterprise.content.SecContentProviderURI;
import com.samsung.android.provider.SemKnoxPolicyContract;
import java.util.List;

/* loaded from: classes3.dex */
public interface IEDMProxy extends IInterface {
    public static final String DESCRIPTOR = "android.sec.enterprise.IEDMProxy";

    public static class Default implements IEDMProxy {
        @Override // android.sec.enterprise.IEDMProxy
        public void addCallsCount(String str) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean addNumberOfIncomingCalls() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean addNumberOfIncomingSms() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean addNumberOfOutgoingCalls() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean addNumberOfOutgoingSms() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void bluetoothLog(String str, String str2) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean canIncomingCall(String str) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean canIncomingSms(String str) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean canOutgoingCall(String str) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean canOutgoingSms(String str) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean decreaseNumberOfOutgoingSms() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getAddHomeShorcutRequested() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getAllowBluetoothDataTransfer(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public byte[] getApplicationIconFromDb(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public String getApplicationNameForComponent(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public String getApplicationNameFromDb(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public Bundle getApplicationRestrictions(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getBrowserSettingStatus(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public List<String> getELMPermissions(String str) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getEmergencyCallOnly(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getExtendedCallInfoState() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public int getKeyboardMode() throws RemoteException {
            return 0;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public String getNtpServer() throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public long getNtpTimeout() throws RemoteException {
            return 0L;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public int getProKioskHideNotificationMessages() throws RemoteException {
            return 0;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getProKioskNotificationMessagesState() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getProKioskState() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public int getSensorDisabled() throws RemoteException {
            return 0;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getToastEnabledState() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public int getToastGravity() throws RemoteException {
            return 0;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getToastGravityEnabledState() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public int getToastGravityXOffset() throws RemoteException {
            return 0;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public int getToastGravityYOffset() throws RemoteException {
            return 0;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getToastShowPackageNameState() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public String getUsbNetAddress(int i) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getUsbNetStateInternal() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getVolumeButtonRotationState() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public int getVolumeControlStream() throws RemoteException {
            return 0;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getVolumePanelEnabledState() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean getWifiState() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isAccountRemovalAllowed(String str, String str2, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isAllowedMamPackage(String str) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isAnyApplicationNameChangedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isAudioRecordAllowed(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isAuditLogEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isBackupAllowed(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isBlockMmsWithStorageEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isBlockSmsWithStorageEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isBluetoothDeviceAllowed(String str) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isBluetoothEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isBluetoothLogEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isBluetoothUUIDAllowed(String str) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isCaCertificateTrustedAsUser(byte[] bArr, boolean z, boolean z2, int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isCallingCaptureEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isCertificateTrustedUntrustedEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isCertificateValidationAtInstallEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isClipboardAllowed(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isClipboardShareAllowed() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isCopyContactToSimAllowed(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isDataAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isDiscoverableEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isFactoryResetAllowed() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isIncomingCallAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isIncomingMmsAllowed() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isIncomingSmsAllowed() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isIncomingSmsAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isKnoxBluetoothEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isLimitNumberOfSmsEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isMicrophoneEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isMmsAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isMockLocationEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isNtpSetByMDM() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isOcspCheckEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isOutgoingCallAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isOutgoingCallsAllowed() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isOutgoingSmsAllowed() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isOutgoingSmsAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isPackageAllowedToAccessExternalSdcard(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isPackageInAvrWhitelist(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isPairingEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isProfileEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isRevocationCheckEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isRoamingDataEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isRoamingPushEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isSMSCaptureEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isScreenLockPatternVisibilityEnabled() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isScreenLockPatternVisibilityEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isSmsPatternCheckRequired() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isTaskManagerAllowed(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isVideoRecordAllowed(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean isWapPushAllowed() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public byte[] keyAgreement(String str, String str2, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void logEventAsUser(int i, int i2, String str, int i3, AuditLogParams auditLogParams) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void logEventForLegacyComponents(int i, int i2, int i3, boolean z, String str, String str2) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void notifyPasswordPolicyOneLockChanged(boolean z, int i) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean registerSystemUICallback(ISystemUIAdapterCallback iSystemUIAdapterCallback) throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void setKeyedAppStatesReport(String str, String str2, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public boolean shallForceNtpMdmValues() throws RemoteException {
            return false;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void storeBlockedSmsMms(boolean z, byte[] bArr, String str, int i, String str2, String str3, String str4) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void storeCalling(String str, String str2, String str3, String str4, boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public void storeSMS(String str, String str2, String str3, boolean z) throws RemoteException {
        }

        @Override // android.sec.enterprise.IEDMProxy
        public byte[] ucmDecrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public byte[] ucmEncrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public byte[] ucmGetCertificateChain(String str) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public byte[] ucmMac(String str, byte[] bArr, String str2) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public byte[] ucmSign(String str, byte[] bArr, String str2) throws RemoteException {
            return null;
        }

        @Override // android.sec.enterprise.IEDMProxy
        public int validateCertificateAtInstallAsUser(byte[] bArr, int i) throws RemoteException {
            return 0;
        }
    }

    void addCallsCount(String str) throws RemoteException;

    boolean addNumberOfIncomingCalls() throws RemoteException;

    boolean addNumberOfIncomingSms() throws RemoteException;

    boolean addNumberOfOutgoingCalls() throws RemoteException;

    boolean addNumberOfOutgoingSms() throws RemoteException;

    void bluetoothLog(String str, String str2) throws RemoteException;

    boolean canIncomingCall(String str) throws RemoteException;

    boolean canIncomingSms(String str) throws RemoteException;

    boolean canOutgoingCall(String str) throws RemoteException;

    boolean canOutgoingSms(String str) throws RemoteException;

    boolean decreaseNumberOfOutgoingSms() throws RemoteException;

    boolean getAddHomeShorcutRequested() throws RemoteException;

    boolean getAllowBluetoothDataTransfer(boolean z) throws RemoteException;

    byte[] getApplicationIconFromDb(String str, int i) throws RemoteException;

    String getApplicationNameForComponent(String str, String str2, int i) throws RemoteException;

    String getApplicationNameFromDb(String str, int i) throws RemoteException;

    Bundle getApplicationRestrictions(String str, int i) throws RemoteException;

    boolean getBrowserSettingStatus(int i) throws RemoteException;

    List<String> getELMPermissions(String str) throws RemoteException;

    boolean getEmergencyCallOnly(boolean z) throws RemoteException;

    boolean getExtendedCallInfoState() throws RemoteException;

    int getKeyboardMode() throws RemoteException;

    String getNtpServer() throws RemoteException;

    long getNtpTimeout() throws RemoteException;

    int getProKioskHideNotificationMessages() throws RemoteException;

    boolean getProKioskNotificationMessagesState() throws RemoteException;

    boolean getProKioskState() throws RemoteException;

    int getSensorDisabled() throws RemoteException;

    boolean getToastEnabledState() throws RemoteException;

    int getToastGravity() throws RemoteException;

    boolean getToastGravityEnabledState() throws RemoteException;

    int getToastGravityXOffset() throws RemoteException;

    int getToastGravityYOffset() throws RemoteException;

    boolean getToastShowPackageNameState() throws RemoteException;

    String getUsbNetAddress(int i) throws RemoteException;

    boolean getUsbNetStateInternal() throws RemoteException;

    boolean getVolumeButtonRotationState() throws RemoteException;

    int getVolumeControlStream() throws RemoteException;

    boolean getVolumePanelEnabledState() throws RemoteException;

    boolean getWifiState() throws RemoteException;

    boolean isAccountRemovalAllowed(String str, String str2, boolean z) throws RemoteException;

    boolean isAllowedMamPackage(String str) throws RemoteException;

    boolean isAnyApplicationNameChangedAsUser(int i) throws RemoteException;

    boolean isAudioRecordAllowed(boolean z) throws RemoteException;

    boolean isAuditLogEnabledAsUser(int i) throws RemoteException;

    boolean isBackupAllowed(boolean z) throws RemoteException;

    boolean isBlockMmsWithStorageEnabled() throws RemoteException;

    boolean isBlockSmsWithStorageEnabled() throws RemoteException;

    boolean isBluetoothDeviceAllowed(String str) throws RemoteException;

    boolean isBluetoothEnabled() throws RemoteException;

    boolean isBluetoothLogEnabled() throws RemoteException;

    boolean isBluetoothUUIDAllowed(String str) throws RemoteException;

    boolean isCaCertificateTrustedAsUser(byte[] bArr, boolean z, boolean z2, int i) throws RemoteException;

    boolean isCallingCaptureEnabled() throws RemoteException;

    boolean isCertificateTrustedUntrustedEnabledAsUser(int i) throws RemoteException;

    boolean isCertificateValidationAtInstallEnabledAsUser(int i) throws RemoteException;

    boolean isClipboardAllowed(boolean z) throws RemoteException;

    boolean isClipboardShareAllowed() throws RemoteException;

    boolean isCopyContactToSimAllowed(int i) throws RemoteException;

    boolean isDataAllowedFromSimSlot(int i) throws RemoteException;

    boolean isDiscoverableEnabled() throws RemoteException;

    boolean isFactoryResetAllowed() throws RemoteException;

    boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) throws RemoteException;

    boolean isIncomingCallAllowedFromSimSlot(int i) throws RemoteException;

    boolean isIncomingMmsAllowed() throws RemoteException;

    boolean isIncomingSmsAllowed() throws RemoteException;

    boolean isIncomingSmsAllowedFromSimSlot(int i) throws RemoteException;

    boolean isKnoxBluetoothEnabled(int i) throws RemoteException;

    boolean isLimitNumberOfSmsEnabled() throws RemoteException;

    boolean isMicrophoneEnabled(boolean z) throws RemoteException;

    boolean isMmsAllowedFromSimSlot(int i) throws RemoteException;

    boolean isMockLocationEnabled() throws RemoteException;

    boolean isNtpSetByMDM() throws RemoteException;

    boolean isOcspCheckEnabled() throws RemoteException;

    boolean isOutgoingCallAllowedFromSimSlot(int i) throws RemoteException;

    boolean isOutgoingCallsAllowed() throws RemoteException;

    boolean isOutgoingSmsAllowed() throws RemoteException;

    boolean isOutgoingSmsAllowedFromSimSlot(int i) throws RemoteException;

    boolean isPackageAllowedToAccessExternalSdcard(int i, int i2) throws RemoteException;

    boolean isPackageInAvrWhitelist(int i) throws RemoteException;

    boolean isPairingEnabled() throws RemoteException;

    boolean isProfileEnabled(int i) throws RemoteException;

    boolean isRevocationCheckEnabled() throws RemoteException;

    boolean isRoamingDataEnabled() throws RemoteException;

    boolean isRoamingPushEnabled() throws RemoteException;

    boolean isSMSCaptureEnabled() throws RemoteException;

    boolean isScreenLockPatternVisibilityEnabled() throws RemoteException;

    boolean isScreenLockPatternVisibilityEnabledAsUser(int i) throws RemoteException;

    boolean isSmsPatternCheckRequired() throws RemoteException;

    boolean isTaskManagerAllowed(boolean z) throws RemoteException;

    boolean isVideoRecordAllowed(boolean z) throws RemoteException;

    boolean isWapPushAllowed() throws RemoteException;

    byte[] keyAgreement(String str, String str2, byte[] bArr) throws RemoteException;

    void logEventAsUser(int i, int i2, String str, int i3, AuditLogParams auditLogParams) throws RemoteException;

    void logEventForLegacyComponents(int i, int i2, int i3, boolean z, String str, String str2) throws RemoteException;

    void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) throws RemoteException;

    void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException;

    void notifyPasswordPolicyOneLockChanged(boolean z, int i) throws RemoteException;

    boolean registerSystemUICallback(ISystemUIAdapterCallback iSystemUIAdapterCallback) throws RemoteException;

    void setKeyedAppStatesReport(String str, String str2, Bundle bundle, int i) throws RemoteException;

    boolean shallForceNtpMdmValues() throws RemoteException;

    void storeBlockedSmsMms(boolean z, byte[] bArr, String str, int i, String str2, String str3, String str4) throws RemoteException;

    void storeCalling(String str, String str2, String str3, String str4, boolean z) throws RemoteException;

    void storeSMS(String str, String str2, String str3, boolean z) throws RemoteException;

    byte[] ucmDecrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

    byte[] ucmEncrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

    byte[] ucmGetCertificateChain(String str) throws RemoteException;

    byte[] ucmMac(String str, byte[] bArr, String str2) throws RemoteException;

    byte[] ucmSign(String str, byte[] bArr, String str2) throws RemoteException;

    int validateCertificateAtInstallAsUser(byte[] bArr, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IEDMProxy {
        static final int TRANSACTION_addCallsCount = 1;
        static final int TRANSACTION_addNumberOfIncomingCalls = 21;
        static final int TRANSACTION_addNumberOfIncomingSms = 24;
        static final int TRANSACTION_addNumberOfOutgoingCalls = 22;
        static final int TRANSACTION_addNumberOfOutgoingSms = 25;
        static final int TRANSACTION_bluetoothLog = 41;
        static final int TRANSACTION_canIncomingCall = 109;
        static final int TRANSACTION_canIncomingSms = 28;
        static final int TRANSACTION_canOutgoingCall = 108;
        static final int TRANSACTION_canOutgoingSms = 27;
        static final int TRANSACTION_decreaseNumberOfOutgoingSms = 26;
        static final int TRANSACTION_getAddHomeShorcutRequested = 97;
        static final int TRANSACTION_getAllowBluetoothDataTransfer = 7;
        static final int TRANSACTION_getApplicationIconFromDb = 6;
        static final int TRANSACTION_getApplicationNameForComponent = 64;
        static final int TRANSACTION_getApplicationNameFromDb = 63;
        static final int TRANSACTION_getApplicationRestrictions = 84;
        static final int TRANSACTION_getBrowserSettingStatus = 15;
        static final int TRANSACTION_getELMPermissions = 96;
        static final int TRANSACTION_getEmergencyCallOnly = 20;
        static final int TRANSACTION_getExtendedCallInfoState = 85;
        static final int TRANSACTION_getKeyboardMode = 80;
        static final int TRANSACTION_getNtpServer = 30;
        static final int TRANSACTION_getNtpTimeout = 31;
        static final int TRANSACTION_getProKioskHideNotificationMessages = 69;
        static final int TRANSACTION_getProKioskNotificationMessagesState = 68;
        static final int TRANSACTION_getProKioskState = 67;
        static final int TRANSACTION_getSensorDisabled = 73;
        static final int TRANSACTION_getToastEnabledState = 71;
        static final int TRANSACTION_getToastGravity = 77;
        static final int TRANSACTION_getToastGravityEnabledState = 76;
        static final int TRANSACTION_getToastGravityXOffset = 78;
        static final int TRANSACTION_getToastGravityYOffset = 79;
        static final int TRANSACTION_getToastShowPackageNameState = 72;
        static final int TRANSACTION_getUsbNetAddress = 83;
        static final int TRANSACTION_getUsbNetStateInternal = 82;
        static final int TRANSACTION_getVolumeButtonRotationState = 75;
        static final int TRANSACTION_getVolumeControlStream = 70;
        static final int TRANSACTION_getVolumePanelEnabledState = 74;
        static final int TRANSACTION_getWifiState = 81;
        static final int TRANSACTION_isAccountRemovalAllowed = 62;
        static final int TRANSACTION_isAllowedMamPackage = 86;
        static final int TRANSACTION_isAnyApplicationNameChangedAsUser = 65;
        static final int TRANSACTION_isAudioRecordAllowed = 53;
        static final int TRANSACTION_isAuditLogEnabledAsUser = 46;
        static final int TRANSACTION_isBackupAllowed = 43;
        static final int TRANSACTION_isBlockMmsWithStorageEnabled = 59;
        static final int TRANSACTION_isBlockSmsWithStorageEnabled = 58;
        static final int TRANSACTION_isBluetoothDeviceAllowed = 11;
        static final int TRANSACTION_isBluetoothEnabled = 14;
        static final int TRANSACTION_isBluetoothLogEnabled = 40;
        static final int TRANSACTION_isBluetoothUUIDAllowed = 9;
        static final int TRANSACTION_isCaCertificateTrustedAsUser = 50;
        static final int TRANSACTION_isCallingCaptureEnabled = 2;
        static final int TRANSACTION_isCertificateTrustedUntrustedEnabledAsUser = 51;
        static final int TRANSACTION_isCertificateValidationAtInstallEnabledAsUser = 52;
        static final int TRANSACTION_isClipboardAllowed = 18;
        static final int TRANSACTION_isClipboardShareAllowed = 57;
        static final int TRANSACTION_isCopyContactToSimAllowed = 66;
        static final int TRANSACTION_isDataAllowedFromSimSlot = 110;
        static final int TRANSACTION_isDiscoverableEnabled = 13;
        static final int TRANSACTION_isFactoryResetAllowed = 36;
        static final int TRANSACTION_isGoogleAccountsAutoSyncAllowedAsUser = 100;
        static final int TRANSACTION_isIncomingCallAllowedFromSimSlot = 107;
        static final int TRANSACTION_isIncomingMmsAllowed = 42;
        static final int TRANSACTION_isIncomingSmsAllowed = 38;
        static final int TRANSACTION_isIncomingSmsAllowedFromSimSlot = 103;
        static final int TRANSACTION_isKnoxBluetoothEnabled = 88;
        static final int TRANSACTION_isLimitNumberOfSmsEnabled = 23;
        static final int TRANSACTION_isMicrophoneEnabled = 19;
        static final int TRANSACTION_isMmsAllowedFromSimSlot = 105;
        static final int TRANSACTION_isMockLocationEnabled = 99;
        static final int TRANSACTION_isNtpSetByMDM = 33;
        static final int TRANSACTION_isOcspCheckEnabled = 49;
        static final int TRANSACTION_isOutgoingCallAllowedFromSimSlot = 106;
        static final int TRANSACTION_isOutgoingCallsAllowed = 8;
        static final int TRANSACTION_isOutgoingSmsAllowed = 39;
        static final int TRANSACTION_isOutgoingSmsAllowedFromSimSlot = 104;
        static final int TRANSACTION_isPackageAllowedToAccessExternalSdcard = 89;
        static final int TRANSACTION_isPackageInAvrWhitelist = 101;
        static final int TRANSACTION_isPairingEnabled = 12;
        static final int TRANSACTION_isProfileEnabled = 10;
        static final int TRANSACTION_isRevocationCheckEnabled = 48;
        static final int TRANSACTION_isRoamingDataEnabled = 17;
        static final int TRANSACTION_isRoamingPushEnabled = 16;
        static final int TRANSACTION_isSMSCaptureEnabled = 4;
        static final int TRANSACTION_isScreenLockPatternVisibilityEnabled = 34;
        static final int TRANSACTION_isScreenLockPatternVisibilityEnabledAsUser = 35;
        static final int TRANSACTION_isSmsPatternCheckRequired = 29;
        static final int TRANSACTION_isTaskManagerAllowed = 37;
        static final int TRANSACTION_isVideoRecordAllowed = 54;
        static final int TRANSACTION_isWapPushAllowed = 61;
        static final int TRANSACTION_keyAgreement = 95;
        static final int TRANSACTION_logEventAsUser = 45;
        static final int TRANSACTION_logEventForLegacyComponents = 44;
        static final int TRANSACTION_notifyCertificateFailureAsUser = 47;
        static final int TRANSACTION_notifyCertificateRemovedAsUser = 55;
        static final int TRANSACTION_notifyPasswordPolicyOneLockChanged = 102;
        static final int TRANSACTION_registerSystemUICallback = 98;
        static final int TRANSACTION_setKeyedAppStatesReport = 87;
        static final int TRANSACTION_shallForceNtpMdmValues = 32;
        static final int TRANSACTION_storeBlockedSmsMms = 60;
        static final int TRANSACTION_storeCalling = 3;
        static final int TRANSACTION_storeSMS = 5;
        static final int TRANSACTION_ucmDecrypt = 91;
        static final int TRANSACTION_ucmEncrypt = 92;
        static final int TRANSACTION_ucmGetCertificateChain = 90;
        static final int TRANSACTION_ucmMac = 94;
        static final int TRANSACTION_ucmSign = 93;
        static final int TRANSACTION_validateCertificateAtInstallAsUser = 56;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 109;
        }

        public Stub() {
            attachInterface(this, IEDMProxy.DESCRIPTOR);
        }

        public static IEDMProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEDMProxy.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEDMProxy)) {
                return (IEDMProxy) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addCallsCount";
                case 2:
                    return "isCallingCaptureEnabled";
                case 3:
                    return "storeCalling";
                case 4:
                    return "isSMSCaptureEnabled";
                case 5:
                    return "storeSMS";
                case 6:
                    return "getApplicationIconFromDb";
                case 7:
                    return "getAllowBluetoothDataTransfer";
                case 8:
                    return SecContentProviderURI.BLUETOOTHPOLICY_OUTGOINGCALLSALLOWED_METHOD;
                case 9:
                    return "isBluetoothUUIDAllowed";
                case 10:
                    return "isProfileEnabled";
                case 11:
                    return "isBluetoothDeviceAllowed";
                case 12:
                    return "isPairingEnabled";
                case 13:
                    return SecContentProviderURI.BLUETOOTHPOLICY_DISCOVERABLE_METHOD;
                case 14:
                    return "isBluetoothEnabled";
                case 15:
                    return "getBrowserSettingStatus";
                case 16:
                    return "isRoamingPushEnabled";
                case 17:
                    return SecContentProviderURI.ROAMINGPOLICY_DATA_METHOD;
                case 18:
                    return "isClipboardAllowed";
                case 19:
                    return SecContentProviderURI.RESTRICTIONPOLICY_MICROPHONE_METHOD;
                case 20:
                    return SecContentProviderURI.PHONERESTRICTIONPOLICY_EMERGENCYCALLONLY_METHOD;
                case 21:
                    return "addNumberOfIncomingCalls";
                case 22:
                    return "addNumberOfOutgoingCalls";
                case 23:
                    return "isLimitNumberOfSmsEnabled";
                case 24:
                    return "addNumberOfIncomingSms";
                case 25:
                    return "addNumberOfOutgoingSms";
                case 26:
                    return "decreaseNumberOfOutgoingSms";
                case 27:
                    return "canOutgoingSms";
                case 28:
                    return "canIncomingSms";
                case 29:
                    return "isSmsPatternCheckRequired";
                case 30:
                    return "getNtpServer";
                case 31:
                    return "getNtpTimeout";
                case 32:
                    return "shallForceNtpMdmValues";
                case 33:
                    return "isNtpSetByMDM";
                case 34:
                    return "isScreenLockPatternVisibilityEnabled";
                case 35:
                    return "isScreenLockPatternVisibilityEnabledAsUser";
                case 36:
                    return SecContentProviderURI.RESTRICTIONPOLICY_FACTORYRESETALLOWED_METHOD;
                case 37:
                    return "isTaskManagerAllowed";
                case 38:
                    return "isIncomingSmsAllowed";
                case 39:
                    return "isOutgoingSmsAllowed";
                case 40:
                    return SecContentProviderURI.BLUETOOTHUTILS_BLUETOOTHLOGENABLED_METHOD;
                case 41:
                    return SecContentProviderURI.BLUETOOTHUTILS_BLUETOOTHLOG_METHOD;
                case 42:
                    return SecContentProviderURI.PHONERESTRICTIONPOLICY_INCOMINGMMS_METHOD;
                case 43:
                    return SecContentProviderURI.RESTRICTIONPOLICY_BACKUPALLOWED_METHOD;
                case 44:
                    return "logEventForLegacyComponents";
                case 45:
                    return "logEventAsUser";
                case 46:
                    return "isAuditLogEnabledAsUser";
                case 47:
                    return "notifyCertificateFailureAsUser";
                case 48:
                    return SecContentProviderURI.CERTIFICATEPOLICY_REVOCATIONCHECK_METHOD;
                case 49:
                    return SecContentProviderURI.CERTIFICATEPOLICY_OCSPCHECK_METHOD;
                case 50:
                    return "isCaCertificateTrustedAsUser";
                case 51:
                    return "isCertificateTrustedUntrustedEnabledAsUser";
                case 52:
                    return "isCertificateValidationAtInstallEnabledAsUser";
                case 53:
                    return SecContentProviderURI.RESTRICTIONPOLICY_AUDIORECORDALLOWED_METHOD;
                case 54:
                    return SecContentProviderURI.RESTRICTIONPOLICY_VIDEORECORD_METHOD;
                case 55:
                    return "notifyCertificateRemovedAsUser";
                case 56:
                    return "validateCertificateAtInstallAsUser";
                case 57:
                    return SemKnoxPolicyContract.RestrictionPolicy.CLIPBOARD_SHARE_ALLOWED;
                case 58:
                    return "isBlockSmsWithStorageEnabled";
                case 59:
                    return "isBlockMmsWithStorageEnabled";
                case 60:
                    return "storeBlockedSmsMms";
                case 61:
                    return "isWapPushAllowed";
                case 62:
                    return "isAccountRemovalAllowed";
                case 63:
                    return SecContentProviderURI.APPLICATIONPOLICY_APPLICATIONNAMEFROMDB_METHOD;
                case 64:
                    return "getApplicationNameForComponent";
                case 65:
                    return "isAnyApplicationNameChangedAsUser";
                case 66:
                    return "isCopyContactToSimAllowed";
                case 67:
                    return "getProKioskState";
                case 68:
                    return "getProKioskNotificationMessagesState";
                case 69:
                    return "getProKioskHideNotificationMessages";
                case 70:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_VOLUMECONTROLSTREAM_METHOD;
                case 71:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTENABLEDSTATE_METHOD;
                case 72:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTSHOWPACKAGENAMESTATE_METHOD;
                case 73:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_SENSORDISABLED_METHOD;
                case 74:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_VOLUMEPANELENABLEDSTATE_METHOD;
                case 75:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_VOLUMEBUTTONROTATIONSTATE_METHOD;
                case 76:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITYENABLEDSTATE_METHOD;
                case 77:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITY_METHOD;
                case 78:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITYXOFFSET_METHOD;
                case 79:
                    return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITYYOFFSET_METHOD;
                case 80:
                    return "getKeyboardMode";
                case 81:
                    return "getWifiState";
                case 82:
                    return "getUsbNetStateInternal";
                case 83:
                    return "getUsbNetAddress";
                case 84:
                    return "getApplicationRestrictions";
                case 85:
                    return "getExtendedCallInfoState";
                case 86:
                    return "isAllowedMamPackage";
                case 87:
                    return "setKeyedAppStatesReport";
                case 88:
                    return "isKnoxBluetoothEnabled";
                case 89:
                    return "isPackageAllowedToAccessExternalSdcard";
                case 90:
                    return "ucmGetCertificateChain";
                case 91:
                    return "ucmDecrypt";
                case 92:
                    return "ucmEncrypt";
                case 93:
                    return "ucmSign";
                case 94:
                    return "ucmMac";
                case 95:
                    return "keyAgreement";
                case 96:
                    return "getELMPermissions";
                case 97:
                    return "getAddHomeShorcutRequested";
                case 98:
                    return "registerSystemUICallback";
                case 99:
                    return SecContentProviderURI.RESTRICTIONPOLICY_MOCKLOCATION_METHOD;
                case 100:
                    return "isGoogleAccountsAutoSyncAllowedAsUser";
                case 101:
                    return "isPackageInAvrWhitelist";
                case 102:
                    return "notifyPasswordPolicyOneLockChanged";
                case 103:
                    return "isIncomingSmsAllowedFromSimSlot";
                case 104:
                    return "isOutgoingSmsAllowedFromSimSlot";
                case 105:
                    return "isMmsAllowedFromSimSlot";
                case 106:
                    return "isOutgoingCallAllowedFromSimSlot";
                case 107:
                    return "isIncomingCallAllowedFromSimSlot";
                case 108:
                    return "canOutgoingCall";
                case 109:
                    return "canIncomingCall";
                case 110:
                    return "isDataAllowedFromSimSlot";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEDMProxy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEDMProxy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCallsCount(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean isCallingCaptureEnabled = isCallingCaptureEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallingCaptureEnabled);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeCalling(readString2, readString3, readString4, readString5, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean isSMSCaptureEnabled = isSMSCaptureEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSMSCaptureEnabled);
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeSMS(readString6, readString7, readString8, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString9 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] applicationIconFromDb = getApplicationIconFromDb(readString9, readInt);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(applicationIconFromDb);
                    return true;
                case 7:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowBluetoothDataTransfer = getAllowBluetoothDataTransfer(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowBluetoothDataTransfer);
                    return true;
                case 8:
                    boolean isOutgoingCallsAllowed = isOutgoingCallsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingCallsAllowed);
                    return true;
                case 9:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothUUIDAllowed = isBluetoothUUIDAllowed(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothUUIDAllowed);
                    return true;
                case 10:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isProfileEnabled = isProfileEnabled(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProfileEnabled);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothDeviceAllowed = isBluetoothDeviceAllowed(readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothDeviceAllowed);
                    return true;
                case 12:
                    boolean isPairingEnabled = isPairingEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPairingEnabled);
                    return true;
                case 13:
                    boolean isDiscoverableEnabled = isDiscoverableEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDiscoverableEnabled);
                    return true;
                case 14:
                    boolean isBluetoothEnabled = isBluetoothEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothEnabled);
                    return true;
                case 15:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean browserSettingStatus = getBrowserSettingStatus(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(browserSettingStatus);
                    return true;
                case 16:
                    boolean isRoamingPushEnabled = isRoamingPushEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRoamingPushEnabled);
                    return true;
                case 17:
                    boolean isRoamingDataEnabled = isRoamingDataEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRoamingDataEnabled);
                    return true;
                case 18:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isClipboardAllowed = isClipboardAllowed(readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClipboardAllowed);
                    return true;
                case 19:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isMicrophoneEnabled = isMicrophoneEnabled(readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMicrophoneEnabled);
                    return true;
                case 20:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean emergencyCallOnly = getEmergencyCallOnly(readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emergencyCallOnly);
                    return true;
                case 21:
                    boolean addNumberOfIncomingCalls = addNumberOfIncomingCalls();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNumberOfIncomingCalls);
                    return true;
                case 22:
                    boolean addNumberOfOutgoingCalls = addNumberOfOutgoingCalls();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNumberOfOutgoingCalls);
                    return true;
                case 23:
                    boolean isLimitNumberOfSmsEnabled = isLimitNumberOfSmsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLimitNumberOfSmsEnabled);
                    return true;
                case 24:
                    boolean addNumberOfIncomingSms = addNumberOfIncomingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNumberOfIncomingSms);
                    return true;
                case 25:
                    boolean addNumberOfOutgoingSms = addNumberOfOutgoingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNumberOfOutgoingSms);
                    return true;
                case 26:
                    boolean decreaseNumberOfOutgoingSms = decreaseNumberOfOutgoingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(decreaseNumberOfOutgoingSms);
                    return true;
                case 27:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canOutgoingSms = canOutgoingSms(readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canOutgoingSms);
                    return true;
                case 28:
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canIncomingSms = canIncomingSms(readString13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canIncomingSms);
                    return true;
                case 29:
                    boolean isSmsPatternCheckRequired = isSmsPatternCheckRequired();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSmsPatternCheckRequired);
                    return true;
                case 30:
                    String ntpServer = getNtpServer();
                    parcel2.writeNoException();
                    parcel2.writeString(ntpServer);
                    return true;
                case 31:
                    long ntpTimeout = getNtpTimeout();
                    parcel2.writeNoException();
                    parcel2.writeLong(ntpTimeout);
                    return true;
                case 32:
                    boolean shallForceNtpMdmValues = shallForceNtpMdmValues();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shallForceNtpMdmValues);
                    return true;
                case 33:
                    boolean isNtpSetByMDM = isNtpSetByMDM();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNtpSetByMDM);
                    return true;
                case 34:
                    boolean isScreenLockPatternVisibilityEnabled = isScreenLockPatternVisibilityEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenLockPatternVisibilityEnabled);
                    return true;
                case 35:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isScreenLockPatternVisibilityEnabledAsUser = isScreenLockPatternVisibilityEnabledAsUser(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenLockPatternVisibilityEnabledAsUser);
                    return true;
                case 36:
                    boolean isFactoryResetAllowed = isFactoryResetAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFactoryResetAllowed);
                    return true;
                case 37:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isTaskManagerAllowed = isTaskManagerAllowed(readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTaskManagerAllowed);
                    return true;
                case 38:
                    boolean isIncomingSmsAllowed = isIncomingSmsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingSmsAllowed);
                    return true;
                case 39:
                    boolean isOutgoingSmsAllowed = isOutgoingSmsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingSmsAllowed);
                    return true;
                case 40:
                    boolean isBluetoothLogEnabled = isBluetoothLogEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothLogEnabled);
                    return true;
                case 41:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    bluetoothLog(readString14, readString15);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean isIncomingMmsAllowed = isIncomingMmsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingMmsAllowed);
                    return true;
                case 43:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isBackupAllowed = isBackupAllowed(readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupAllowed);
                    return true;
                case 44:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    logEventForLegacyComponents(readInt5, readInt6, readInt7, readBoolean9, readString16, readString17);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    String readString18 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    AuditLogParams auditLogParams = (AuditLogParams) parcel.readTypedObject(AuditLogParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    logEventAsUser(readInt8, readInt9, readString18, readInt10, auditLogParams);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAuditLogEnabledAsUser = isAuditLogEnabledAsUser(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAuditLogEnabledAsUser);
                    return true;
                case 47:
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCertificateFailureAsUser(readString19, readString20, readBoolean10, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    boolean isRevocationCheckEnabled = isRevocationCheckEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRevocationCheckEnabled);
                    return true;
                case 49:
                    boolean isOcspCheckEnabled = isOcspCheckEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOcspCheckEnabled);
                    return true;
                case 50:
                    byte[] createByteArray = parcel.createByteArray();
                    boolean readBoolean11 = parcel.readBoolean();
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCaCertificateTrustedAsUser = isCaCertificateTrustedAsUser(createByteArray, readBoolean11, readBoolean12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCaCertificateTrustedAsUser);
                    return true;
                case 51:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCertificateTrustedUntrustedEnabledAsUser = isCertificateTrustedUntrustedEnabledAsUser(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCertificateTrustedUntrustedEnabledAsUser);
                    return true;
                case 52:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCertificateValidationAtInstallEnabledAsUser = isCertificateValidationAtInstallEnabledAsUser(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCertificateValidationAtInstallEnabledAsUser);
                    return true;
                case 53:
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isAudioRecordAllowed = isAudioRecordAllowed(readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAudioRecordAllowed);
                    return true;
                case 54:
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isVideoRecordAllowed = isVideoRecordAllowed(readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVideoRecordAllowed);
                    return true;
                case 55:
                    String readString21 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCertificateRemovedAsUser(readString21, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int validateCertificateAtInstallAsUser = validateCertificateAtInstallAsUser(createByteArray2, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeInt(validateCertificateAtInstallAsUser);
                    return true;
                case 57:
                    boolean isClipboardShareAllowed = isClipboardShareAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClipboardShareAllowed);
                    return true;
                case 58:
                    boolean isBlockSmsWithStorageEnabled = isBlockSmsWithStorageEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBlockSmsWithStorageEnabled);
                    return true;
                case 59:
                    boolean isBlockMmsWithStorageEnabled = isBlockMmsWithStorageEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBlockMmsWithStorageEnabled);
                    return true;
                case 60:
                    boolean readBoolean15 = parcel.readBoolean();
                    byte[] createByteArray3 = parcel.createByteArray();
                    String readString22 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    storeBlockedSmsMms(readBoolean15, createByteArray3, readString22, readInt18, readString23, readString24, readString25);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    boolean isWapPushAllowed = isWapPushAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWapPushAllowed);
                    return true;
                case 62:
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isAccountRemovalAllowed = isAccountRemovalAllowed(readString26, readString27, readBoolean16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccountRemovalAllowed);
                    return true;
                case 63:
                    String readString28 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String applicationNameFromDb = getApplicationNameFromDb(readString28, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationNameFromDb);
                    return true;
                case 64:
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String applicationNameForComponent = getApplicationNameForComponent(readString29, readString30, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationNameForComponent);
                    return true;
                case 65:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAnyApplicationNameChangedAsUser = isAnyApplicationNameChangedAsUser(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAnyApplicationNameChangedAsUser);
                    return true;
                case 66:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCopyContactToSimAllowed = isCopyContactToSimAllowed(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCopyContactToSimAllowed);
                    return true;
                case 67:
                    boolean proKioskState = getProKioskState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(proKioskState);
                    return true;
                case 68:
                    boolean proKioskNotificationMessagesState = getProKioskNotificationMessagesState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(proKioskNotificationMessagesState);
                    return true;
                case 69:
                    int proKioskHideNotificationMessages = getProKioskHideNotificationMessages();
                    parcel2.writeNoException();
                    parcel2.writeInt(proKioskHideNotificationMessages);
                    return true;
                case 70:
                    int volumeControlStream = getVolumeControlStream();
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeControlStream);
                    return true;
                case 71:
                    boolean toastEnabledState = getToastEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(toastEnabledState);
                    return true;
                case 72:
                    boolean toastShowPackageNameState = getToastShowPackageNameState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(toastShowPackageNameState);
                    return true;
                case 73:
                    int sensorDisabled = getSensorDisabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(sensorDisabled);
                    return true;
                case 74:
                    boolean volumePanelEnabledState = getVolumePanelEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(volumePanelEnabledState);
                    return true;
                case 75:
                    boolean volumeButtonRotationState = getVolumeButtonRotationState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(volumeButtonRotationState);
                    return true;
                case 76:
                    boolean toastGravityEnabledState = getToastGravityEnabledState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(toastGravityEnabledState);
                    return true;
                case 77:
                    int toastGravity = getToastGravity();
                    parcel2.writeNoException();
                    parcel2.writeInt(toastGravity);
                    return true;
                case 78:
                    int toastGravityXOffset = getToastGravityXOffset();
                    parcel2.writeNoException();
                    parcel2.writeInt(toastGravityXOffset);
                    return true;
                case 79:
                    int toastGravityYOffset = getToastGravityYOffset();
                    parcel2.writeNoException();
                    parcel2.writeInt(toastGravityYOffset);
                    return true;
                case 80:
                    int keyboardMode = getKeyboardMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(keyboardMode);
                    return true;
                case 81:
                    boolean wifiState = getWifiState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiState);
                    return true;
                case 82:
                    boolean usbNetStateInternal = getUsbNetStateInternal();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbNetStateInternal);
                    return true;
                case 83:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String usbNetAddress = getUsbNetAddress(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeString(usbNetAddress);
                    return true;
                case 84:
                    String readString31 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle applicationRestrictions = getApplicationRestrictions(readString31, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictions, 1);
                    return true;
                case 85:
                    boolean extendedCallInfoState = getExtendedCallInfoState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(extendedCallInfoState);
                    return true;
                case 86:
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAllowedMamPackage = isAllowedMamPackage(readString32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAllowedMamPackage);
                    return true;
                case 87:
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setKeyedAppStatesReport(readString33, readString34, bundle, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKnoxBluetoothEnabled = isKnoxBluetoothEnabled(readInt26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKnoxBluetoothEnabled);
                    return true;
                case 89:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageAllowedToAccessExternalSdcard = isPackageAllowedToAccessExternalSdcard(readInt27, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageAllowedToAccessExternalSdcard);
                    return true;
                case 90:
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] ucmGetCertificateChain = ucmGetCertificateChain(readString35);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(ucmGetCertificateChain);
                    return true;
                case 91:
                    String readString36 = parcel.readString();
                    byte[] createByteArray4 = parcel.createByteArray();
                    String readString37 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] ucmDecrypt = ucmDecrypt(readString36, createByteArray4, readString37, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(ucmDecrypt);
                    return true;
                case 92:
                    String readString38 = parcel.readString();
                    byte[] createByteArray5 = parcel.createByteArray();
                    String readString39 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] ucmEncrypt = ucmEncrypt(readString38, createByteArray5, readString39, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(ucmEncrypt);
                    return true;
                case 93:
                    String readString40 = parcel.readString();
                    byte[] createByteArray6 = parcel.createByteArray();
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] ucmSign = ucmSign(readString40, createByteArray6, readString41);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(ucmSign);
                    return true;
                case 94:
                    String readString42 = parcel.readString();
                    byte[] createByteArray7 = parcel.createByteArray();
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] ucmMac = ucmMac(readString42, createByteArray7, readString43);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(ucmMac);
                    return true;
                case 95:
                    String readString44 = parcel.readString();
                    String readString45 = parcel.readString();
                    byte[] createByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] keyAgreement = keyAgreement(readString44, readString45, createByteArray8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(keyAgreement);
                    return true;
                case 96:
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> eLMPermissions = getELMPermissions(readString46);
                    parcel2.writeNoException();
                    parcel2.writeStringList(eLMPermissions);
                    return true;
                case 97:
                    boolean addHomeShorcutRequested = getAddHomeShorcutRequested();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addHomeShorcutRequested);
                    return true;
                case 98:
                    ISystemUIAdapterCallback asInterface = ISystemUIAdapterCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerSystemUICallback = registerSystemUICallback(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerSystemUICallback);
                    return true;
                case 99:
                    boolean isMockLocationEnabled = isMockLocationEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMockLocationEnabled);
                    return true;
                case 100:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isGoogleAccountsAutoSyncAllowedAsUser = isGoogleAccountsAutoSyncAllowedAsUser(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGoogleAccountsAutoSyncAllowedAsUser);
                    return true;
                case 101:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageInAvrWhitelist = isPackageInAvrWhitelist(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageInAvrWhitelist);
                    return true;
                case 102:
                    boolean readBoolean17 = parcel.readBoolean();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPasswordPolicyOneLockChanged(readBoolean17, readInt31);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isIncomingSmsAllowedFromSimSlot = isIncomingSmsAllowedFromSimSlot(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingSmsAllowedFromSimSlot);
                    return true;
                case 104:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isOutgoingSmsAllowedFromSimSlot = isOutgoingSmsAllowedFromSimSlot(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingSmsAllowedFromSimSlot);
                    return true;
                case 105:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMmsAllowedFromSimSlot = isMmsAllowedFromSimSlot(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMmsAllowedFromSimSlot);
                    return true;
                case 106:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isOutgoingCallAllowedFromSimSlot = isOutgoingCallAllowedFromSimSlot(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingCallAllowedFromSimSlot);
                    return true;
                case 107:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isIncomingCallAllowedFromSimSlot = isIncomingCallAllowedFromSimSlot(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingCallAllowedFromSimSlot);
                    return true;
                case 108:
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canOutgoingCall = canOutgoingCall(readString47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canOutgoingCall);
                    return true;
                case 109:
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canIncomingCall = canIncomingCall(readString48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canIncomingCall);
                    return true;
                case 110:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDataAllowedFromSimSlot = isDataAllowedFromSimSlot(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDataAllowedFromSimSlot);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEDMProxy {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEDMProxy.DESCRIPTOR;
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void addCallsCount(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCallingCaptureEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void storeCalling(String str, String str2, String str3, String str4, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isSMSCaptureEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void storeSMS(String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] getApplicationIconFromDb(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getAllowBluetoothDataTransfer(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOutgoingCallsAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBluetoothUUIDAllowed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isProfileEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBluetoothDeviceAllowed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isPairingEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isDiscoverableEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBluetoothEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getBrowserSettingStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isRoamingPushEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isRoamingDataEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isClipboardAllowed(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isMicrophoneEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getEmergencyCallOnly(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean addNumberOfIncomingCalls() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean addNumberOfOutgoingCalls() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isLimitNumberOfSmsEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean addNumberOfIncomingSms() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean addNumberOfOutgoingSms() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean decreaseNumberOfOutgoingSms() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean canOutgoingSms(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean canIncomingSms(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isSmsPatternCheckRequired() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public String getNtpServer() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public long getNtpTimeout() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean shallForceNtpMdmValues() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isNtpSetByMDM() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isScreenLockPatternVisibilityEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isScreenLockPatternVisibilityEnabledAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isFactoryResetAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isTaskManagerAllowed(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isIncomingSmsAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOutgoingSmsAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBluetoothLogEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void bluetoothLog(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isIncomingMmsAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBackupAllowed(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void logEventForLegacyComponents(int i, int i2, int i3, boolean z, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void logEventAsUser(int i, int i2, String str, int i3, AuditLogParams auditLogParams) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(auditLogParams, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAuditLogEnabledAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isRevocationCheckEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOcspCheckEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCaCertificateTrustedAsUser(byte[] bArr, boolean z, boolean z2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCertificateTrustedUntrustedEnabledAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCertificateValidationAtInstallEnabledAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAudioRecordAllowed(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isVideoRecordAllowed(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int validateCertificateAtInstallAsUser(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isClipboardShareAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBlockSmsWithStorageEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBlockMmsWithStorageEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void storeBlockedSmsMms(boolean z, byte[] bArr, String str, int i, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isWapPushAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAccountRemovalAllowed(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public String getApplicationNameFromDb(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public String getApplicationNameForComponent(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAnyApplicationNameChangedAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCopyContactToSimAllowed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getProKioskState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getProKioskNotificationMessagesState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getProKioskHideNotificationMessages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getVolumeControlStream() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getToastEnabledState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getToastShowPackageNameState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getSensorDisabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getVolumePanelEnabledState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getVolumeButtonRotationState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getToastGravityEnabledState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getToastGravity() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getToastGravityXOffset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getToastGravityYOffset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getKeyboardMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getWifiState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getUsbNetStateInternal() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public String getUsbNetAddress(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public Bundle getApplicationRestrictions(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getExtendedCallInfoState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAllowedMamPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void setKeyedAppStatesReport(String str, String str2, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isKnoxBluetoothEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isPackageAllowedToAccessExternalSdcard(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmGetCertificateChain(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmDecrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmEncrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmSign(String str, byte[] bArr, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmMac(String str, byte[] bArr, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] keyAgreement(String str, String str2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public List<String> getELMPermissions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getAddHomeShorcutRequested() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean registerSystemUICallback(ISystemUIAdapterCallback iSystemUIAdapterCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeStrongInterface(iSystemUIAdapterCallback);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isMockLocationEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isPackageInAvrWhitelist(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void notifyPasswordPolicyOneLockChanged(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isIncomingSmsAllowedFromSimSlot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOutgoingSmsAllowedFromSimSlot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isMmsAllowedFromSimSlot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOutgoingCallAllowedFromSimSlot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isIncomingCallAllowedFromSimSlot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean canOutgoingCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean canIncomingCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isDataAllowedFromSimSlot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
