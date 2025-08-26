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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEDMProxy.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEDMProxy)) {
                return (IEDMProxy) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCallsCount(string);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean zIsCallingCaptureEnabled = isCallingCaptureEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallingCaptureEnabled);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeCalling(string2, string3, string4, string5, z);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean zIsSMSCaptureEnabled = isSMSCaptureEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSMSCaptureEnabled);
                    return true;
                case 5:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeSMS(string6, string7, string8, z2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string9 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] applicationIconFromDb = getApplicationIconFromDb(string9, i3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(applicationIconFromDb);
                    return true;
                case 7:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowBluetoothDataTransfer = getAllowBluetoothDataTransfer(z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowBluetoothDataTransfer);
                    return true;
                case 8:
                    boolean zIsOutgoingCallsAllowed = isOutgoingCallsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingCallsAllowed);
                    return true;
                case 9:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothUUIDAllowed = isBluetoothUUIDAllowed(string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothUUIDAllowed);
                    return true;
                case 10:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsProfileEnabled = isProfileEnabled(i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProfileEnabled);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothDeviceAllowed = isBluetoothDeviceAllowed(string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothDeviceAllowed);
                    return true;
                case 12:
                    boolean zIsPairingEnabled = isPairingEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPairingEnabled);
                    return true;
                case 13:
                    boolean zIsDiscoverableEnabled = isDiscoverableEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDiscoverableEnabled);
                    return true;
                case 14:
                    boolean zIsBluetoothEnabled = isBluetoothEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothEnabled);
                    return true;
                case 15:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean browserSettingStatus = getBrowserSettingStatus(i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(browserSettingStatus);
                    return true;
                case 16:
                    boolean zIsRoamingPushEnabled = isRoamingPushEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRoamingPushEnabled);
                    return true;
                case 17:
                    boolean zIsRoamingDataEnabled = isRoamingDataEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRoamingDataEnabled);
                    return true;
                case 18:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsClipboardAllowed = isClipboardAllowed(z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsClipboardAllowed);
                    return true;
                case 19:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsMicrophoneEnabled = isMicrophoneEnabled(z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMicrophoneEnabled);
                    return true;
                case 20:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean emergencyCallOnly = getEmergencyCallOnly(z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emergencyCallOnly);
                    return true;
                case 21:
                    boolean zAddNumberOfIncomingCalls = addNumberOfIncomingCalls();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNumberOfIncomingCalls);
                    return true;
                case 22:
                    boolean zAddNumberOfOutgoingCalls = addNumberOfOutgoingCalls();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNumberOfOutgoingCalls);
                    return true;
                case 23:
                    boolean zIsLimitNumberOfSmsEnabled = isLimitNumberOfSmsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLimitNumberOfSmsEnabled);
                    return true;
                case 24:
                    boolean zAddNumberOfIncomingSms = addNumberOfIncomingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNumberOfIncomingSms);
                    return true;
                case 25:
                    boolean zAddNumberOfOutgoingSms = addNumberOfOutgoingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNumberOfOutgoingSms);
                    return true;
                case 26:
                    boolean zDecreaseNumberOfOutgoingSms = decreaseNumberOfOutgoingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDecreaseNumberOfOutgoingSms);
                    return true;
                case 27:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanOutgoingSms = canOutgoingSms(string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanOutgoingSms);
                    return true;
                case 28:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanIncomingSms = canIncomingSms(string13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanIncomingSms);
                    return true;
                case 29:
                    boolean zIsSmsPatternCheckRequired = isSmsPatternCheckRequired();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSmsPatternCheckRequired);
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
                    boolean zShallForceNtpMdmValues = shallForceNtpMdmValues();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShallForceNtpMdmValues);
                    return true;
                case 33:
                    boolean zIsNtpSetByMDM = isNtpSetByMDM();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNtpSetByMDM);
                    return true;
                case 34:
                    boolean zIsScreenLockPatternVisibilityEnabled = isScreenLockPatternVisibilityEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenLockPatternVisibilityEnabled);
                    return true;
                case 35:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsScreenLockPatternVisibilityEnabledAsUser = isScreenLockPatternVisibilityEnabledAsUser(i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenLockPatternVisibilityEnabledAsUser);
                    return true;
                case 36:
                    boolean zIsFactoryResetAllowed = isFactoryResetAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFactoryResetAllowed);
                    return true;
                case 37:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsTaskManagerAllowed = isTaskManagerAllowed(z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTaskManagerAllowed);
                    return true;
                case 38:
                    boolean zIsIncomingSmsAllowed = isIncomingSmsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingSmsAllowed);
                    return true;
                case 39:
                    boolean zIsOutgoingSmsAllowed = isOutgoingSmsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingSmsAllowed);
                    return true;
                case 40:
                    boolean zIsBluetoothLogEnabled = isBluetoothLogEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothLogEnabled);
                    return true;
                case 41:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    bluetoothLog(string14, string15);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean zIsIncomingMmsAllowed = isIncomingMmsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingMmsAllowed);
                    return true;
                case 43:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsBackupAllowed = isBackupAllowed(z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBackupAllowed);
                    return true;
                case 44:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    boolean z9 = parcel.readBoolean();
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    logEventForLegacyComponents(i7, i8, i9, z9, string16, string17);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    String string18 = parcel.readString();
                    int i12 = parcel.readInt();
                    AuditLogParams auditLogParams = (AuditLogParams) parcel.readTypedObject(AuditLogParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    logEventAsUser(i10, i11, string18, i12, auditLogParams);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAuditLogEnabledAsUser = isAuditLogEnabledAsUser(i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAuditLogEnabledAsUser);
                    return true;
                case 47:
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    boolean z10 = parcel.readBoolean();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCertificateFailureAsUser(string19, string20, z10, i14);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    boolean zIsRevocationCheckEnabled = isRevocationCheckEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRevocationCheckEnabled);
                    return true;
                case 49:
                    boolean zIsOcspCheckEnabled = isOcspCheckEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOcspCheckEnabled);
                    return true;
                case 50:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    boolean z11 = parcel.readBoolean();
                    boolean z12 = parcel.readBoolean();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCaCertificateTrustedAsUser = isCaCertificateTrustedAsUser(bArrCreateByteArray, z11, z12, i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCaCertificateTrustedAsUser);
                    return true;
                case 51:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCertificateTrustedUntrustedEnabledAsUser = isCertificateTrustedUntrustedEnabledAsUser(i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCertificateTrustedUntrustedEnabledAsUser);
                    return true;
                case 52:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCertificateValidationAtInstallEnabledAsUser = isCertificateValidationAtInstallEnabledAsUser(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCertificateValidationAtInstallEnabledAsUser);
                    return true;
                case 53:
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsAudioRecordAllowed = isAudioRecordAllowed(z13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAudioRecordAllowed);
                    return true;
                case 54:
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsVideoRecordAllowed = isVideoRecordAllowed(z14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVideoRecordAllowed);
                    return true;
                case 55:
                    String string21 = parcel.readString();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCertificateRemovedAsUser(string21, i18);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iValidateCertificateAtInstallAsUser = validateCertificateAtInstallAsUser(bArrCreateByteArray2, i19);
                    parcel2.writeNoException();
                    parcel2.writeInt(iValidateCertificateAtInstallAsUser);
                    return true;
                case 57:
                    boolean zIsClipboardShareAllowed = isClipboardShareAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsClipboardShareAllowed);
                    return true;
                case 58:
                    boolean zIsBlockSmsWithStorageEnabled = isBlockSmsWithStorageEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBlockSmsWithStorageEnabled);
                    return true;
                case 59:
                    boolean zIsBlockMmsWithStorageEnabled = isBlockMmsWithStorageEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBlockMmsWithStorageEnabled);
                    return true;
                case 60:
                    boolean z15 = parcel.readBoolean();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    String string22 = parcel.readString();
                    int i20 = parcel.readInt();
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    storeBlockedSmsMms(z15, bArrCreateByteArray3, string22, i20, string23, string24, string25);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    boolean zIsWapPushAllowed = isWapPushAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWapPushAllowed);
                    return true;
                case 62:
                    String string26 = parcel.readString();
                    String string27 = parcel.readString();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsAccountRemovalAllowed = isAccountRemovalAllowed(string26, string27, z16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAccountRemovalAllowed);
                    return true;
                case 63:
                    String string28 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String applicationNameFromDb = getApplicationNameFromDb(string28, i21);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationNameFromDb);
                    return true;
                case 64:
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String applicationNameForComponent = getApplicationNameForComponent(string29, string30, i22);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationNameForComponent);
                    return true;
                case 65:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAnyApplicationNameChangedAsUser = isAnyApplicationNameChangedAsUser(i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAnyApplicationNameChangedAsUser);
                    return true;
                case 66:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCopyContactToSimAllowed = isCopyContactToSimAllowed(i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCopyContactToSimAllowed);
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
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String usbNetAddress = getUsbNetAddress(i25);
                    parcel2.writeNoException();
                    parcel2.writeString(usbNetAddress);
                    return true;
                case 84:
                    String string31 = parcel.readString();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle applicationRestrictions = getApplicationRestrictions(string31, i26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictions, 1);
                    return true;
                case 85:
                    boolean extendedCallInfoState = getExtendedCallInfoState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(extendedCallInfoState);
                    return true;
                case 86:
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAllowedMamPackage = isAllowedMamPackage(string32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAllowedMamPackage);
                    return true;
                case 87:
                    String string33 = parcel.readString();
                    String string34 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setKeyedAppStatesReport(string33, string34, bundle, i27);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsKnoxBluetoothEnabled = isKnoxBluetoothEnabled(i28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKnoxBluetoothEnabled);
                    return true;
                case 89:
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageAllowedToAccessExternalSdcard = isPackageAllowedToAccessExternalSdcard(i29, i30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageAllowedToAccessExternalSdcard);
                    return true;
                case 90:
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] bArrUcmGetCertificateChain = ucmGetCertificateChain(string35);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrUcmGetCertificateChain);
                    return true;
                case 91:
                    String string36 = parcel.readString();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    String string37 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] bArrUcmDecrypt = ucmDecrypt(string36, bArrCreateByteArray4, string37, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrUcmDecrypt);
                    return true;
                case 92:
                    String string38 = parcel.readString();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    String string39 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] bArrUcmEncrypt = ucmEncrypt(string38, bArrCreateByteArray5, string39, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrUcmEncrypt);
                    return true;
                case 93:
                    String string40 = parcel.readString();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] bArrUcmSign = ucmSign(string40, bArrCreateByteArray6, string41);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrUcmSign);
                    return true;
                case 94:
                    String string42 = parcel.readString();
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] bArrUcmMac = ucmMac(string42, bArrCreateByteArray7, string43);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrUcmMac);
                    return true;
                case 95:
                    String string44 = parcel.readString();
                    String string45 = parcel.readString();
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrKeyAgreement = keyAgreement(string44, string45, bArrCreateByteArray8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrKeyAgreement);
                    return true;
                case 96:
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> eLMPermissions = getELMPermissions(string46);
                    parcel2.writeNoException();
                    parcel2.writeStringList(eLMPermissions);
                    return true;
                case 97:
                    boolean addHomeShorcutRequested = getAddHomeShorcutRequested();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addHomeShorcutRequested);
                    return true;
                case 98:
                    ISystemUIAdapterCallback iSystemUIAdapterCallbackAsInterface = ISystemUIAdapterCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterSystemUICallback = registerSystemUICallback(iSystemUIAdapterCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterSystemUICallback);
                    return true;
                case 99:
                    boolean zIsMockLocationEnabled = isMockLocationEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMockLocationEnabled);
                    return true;
                case 100:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsGoogleAccountsAutoSyncAllowedAsUser = isGoogleAccountsAutoSyncAllowedAsUser(i31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGoogleAccountsAutoSyncAllowedAsUser);
                    return true;
                case 101:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageInAvrWhitelist = isPackageInAvrWhitelist(i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageInAvrWhitelist);
                    return true;
                case 102:
                    boolean z17 = parcel.readBoolean();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPasswordPolicyOneLockChanged(z17, i33);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsIncomingSmsAllowedFromSimSlot = isIncomingSmsAllowedFromSimSlot(i34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingSmsAllowedFromSimSlot);
                    return true;
                case 104:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsOutgoingSmsAllowedFromSimSlot = isOutgoingSmsAllowedFromSimSlot(i35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingSmsAllowedFromSimSlot);
                    return true;
                case 105:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsMmsAllowedFromSimSlot = isMmsAllowedFromSimSlot(i36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMmsAllowedFromSimSlot);
                    return true;
                case 106:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsOutgoingCallAllowedFromSimSlot = isOutgoingCallAllowedFromSimSlot(i37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingCallAllowedFromSimSlot);
                    return true;
                case 107:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsIncomingCallAllowedFromSimSlot = isIncomingCallAllowedFromSimSlot(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingCallAllowedFromSimSlot);
                    return true;
                case 108:
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanOutgoingCall = canOutgoingCall(string47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanOutgoingCall);
                    return true;
                case 109:
                    String string48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanIncomingCall = canIncomingCall(string48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanIncomingCall);
                    return true;
                case 110:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDataAllowedFromSimSlot = isDataAllowedFromSimSlot(i39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDataAllowedFromSimSlot);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCallingCaptureEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void storeCalling(String str, String str2, String str3, String str4, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isSMSCaptureEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void storeSMS(String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] getApplicationIconFromDb(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getAllowBluetoothDataTransfer(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOutgoingCallsAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBluetoothUUIDAllowed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isProfileEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBluetoothDeviceAllowed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isPairingEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isDiscoverableEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBluetoothEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getBrowserSettingStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isRoamingPushEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isRoamingDataEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isClipboardAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isMicrophoneEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getEmergencyCallOnly(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean addNumberOfIncomingCalls() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean addNumberOfOutgoingCalls() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isLimitNumberOfSmsEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean addNumberOfIncomingSms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean addNumberOfOutgoingSms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean decreaseNumberOfOutgoingSms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean canOutgoingSms(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean canIncomingSms(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isSmsPatternCheckRequired() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public String getNtpServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public long getNtpTimeout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean shallForceNtpMdmValues() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isNtpSetByMDM() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isScreenLockPatternVisibilityEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isScreenLockPatternVisibilityEnabledAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isFactoryResetAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isTaskManagerAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isIncomingSmsAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOutgoingSmsAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBluetoothLogEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void bluetoothLog(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isIncomingMmsAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBackupAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void logEventForLegacyComponents(int i, int i2, int i3, boolean z, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void logEventAsUser(int i, int i2, String str, int i3, AuditLogParams auditLogParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(auditLogParams, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAuditLogEnabledAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isRevocationCheckEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOcspCheckEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCaCertificateTrustedAsUser(byte[] bArr, boolean z, boolean z2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCertificateTrustedUntrustedEnabledAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCertificateValidationAtInstallEnabledAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAudioRecordAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isVideoRecordAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int validateCertificateAtInstallAsUser(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isClipboardShareAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBlockSmsWithStorageEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isBlockMmsWithStorageEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void storeBlockedSmsMms(boolean z, byte[] bArr, String str, int i, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isWapPushAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAccountRemovalAllowed(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public String getApplicationNameFromDb(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public String getApplicationNameForComponent(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAnyApplicationNameChangedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isCopyContactToSimAllowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getProKioskState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getProKioskNotificationMessagesState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getProKioskHideNotificationMessages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getVolumeControlStream() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getToastEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getToastShowPackageNameState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getSensorDisabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getVolumePanelEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getVolumeButtonRotationState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getToastGravityEnabledState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getToastGravity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getToastGravityXOffset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getToastGravityYOffset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public int getKeyboardMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getWifiState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getUsbNetStateInternal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public String getUsbNetAddress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public Bundle getApplicationRestrictions(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getExtendedCallInfoState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isAllowedMamPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void setKeyedAppStatesReport(String str, String str2, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isKnoxBluetoothEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isPackageAllowedToAccessExternalSdcard(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmGetCertificateChain(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmDecrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmEncrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmSign(String str, byte[] bArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] ucmMac(String str, byte[] bArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public byte[] keyAgreement(String str, String str2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public List<String> getELMPermissions(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean getAddHomeShorcutRequested() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean registerSystemUICallback(ISystemUIAdapterCallback iSystemUIAdapterCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSystemUIAdapterCallback);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isMockLocationEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isPackageInAvrWhitelist(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public void notifyPasswordPolicyOneLockChanged(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isIncomingSmsAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOutgoingSmsAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isMmsAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isOutgoingCallAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isIncomingCallAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean canOutgoingCall(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean canIncomingCall(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.enterprise.IEDMProxy
            public boolean isDataAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
