package android.sec.enterprise;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.sec.enterprise.content.SecContentProviderURI;
import com.samsung.android.provider.SemKnoxPolicyContract;
import java.util.List;

/* loaded from: classes3.dex */
public interface IEDMProxy extends IInterface {
  public static final String DESCRIPTOR = "android.sec.enterprise.IEDMProxy";

  void AuditLogger(int i, int i2, boolean z, int i3, String str, String str2)
      throws RemoteException;

  void AuditLoggerAsUser(int i, int i2, boolean z, int i3, String str, String str2, int i4)
      throws RemoteException;

  void AuditLoggerPrivileged(int i, int i2, boolean z, int i3, String str, String str2)
      throws RemoteException;

  void AuditLoggerPrivilegedAsUser(
      int i, int i2, boolean z, int i3, String str, String str2, int i4) throws RemoteException;

  void RedactedAuditLogger(int i, int i2, boolean z, int i3, String str, String str2, String str3)
      throws RemoteException;

  void RedactedAuditLoggerAsUser(
      int i, int i2, boolean z, int i3, String str, String str2, String str3, int i4)
      throws RemoteException;

  void RedactedAuditLoggerPrivileged(
      int i, int i2, boolean z, int i3, String str, String str2, String str3)
      throws RemoteException;

  void RedactedAuditLoggerPrivilegedAsUser(
      int i, int i2, boolean z, int i3, String str, String str2, String str3, int i4)
      throws RemoteException;

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

  boolean isCaCertificateTrustedAsUser(byte[] bArr, boolean z, boolean z2, int i)
      throws RemoteException;

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

  void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i)
      throws RemoteException;

  void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException;

  void notifyPasswordPolicyOneLockChanged(boolean z, int i) throws RemoteException;

  boolean registerSystemUICallback(ISystemUIAdapterCallback iSystemUIAdapterCallback)
      throws RemoteException;

  boolean shallForceNtpMdmValues() throws RemoteException;

  void storeBlockedSmsMms(
      boolean z, byte[] bArr, String str, int i, String str2, String str3, String str4)
      throws RemoteException;

  void storeCalling(String str, String str2, String str3, String str4, boolean z)
      throws RemoteException;

  void storeSMS(String str, String str2, String str3, boolean z) throws RemoteException;

  byte[] ucmDecrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

  byte[] ucmEncrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

  byte[] ucmGetCertificateChain(String str) throws RemoteException;

  byte[] ucmMac(String str, byte[] bArr, String str2) throws RemoteException;

  byte[] ucmSign(String str, byte[] bArr, String str2) throws RemoteException;

  int validateCertificateAtInstallAsUser(byte[] bArr, int i) throws RemoteException;

  public static class Default implements IEDMProxy {
    @Override // android.sec.enterprise.IEDMProxy
    public void addCallsCount(String callType) throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isCallingCaptureEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public void storeCalling(
        String address, String timeStamp, String duration, String status, boolean isIncoming)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isSMSCaptureEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public void storeSMS(String address, String timeStamp, String message, boolean isInbound)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public byte[] getApplicationIconFromDb(String pkgName, int userId) throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getAllowBluetoothDataTransfer(boolean showMsg) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isOutgoingCallsAllowed() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isBluetoothUUIDAllowed(String uuid) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isProfileEnabled(int setting) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isBluetoothDeviceAllowed(String address) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isPairingEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isDiscoverableEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isBluetoothEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getBrowserSettingStatus(int setting) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isRoamingPushEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isRoamingDataEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isClipboardAllowed(boolean showMsg) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isMicrophoneEnabled(boolean showMsg) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getEmergencyCallOnly(boolean showMsg) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean addNumberOfIncomingCalls() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean addNumberOfOutgoingCalls() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isLimitNumberOfSmsEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean addNumberOfIncomingSms() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean addNumberOfOutgoingSms() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean decreaseNumberOfOutgoingSms() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean canOutgoingSms(String phoneNumber) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean canIncomingSms(String phoneNumber) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isSmsPatternCheckRequired() throws RemoteException {
      return false;
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
    public boolean shallForceNtpMdmValues() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isNtpSetByMDM() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isScreenLockPatternVisibilityEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isScreenLockPatternVisibilityEnabledAsUser(int userId) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isFactoryResetAllowed() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isTaskManagerAllowed(boolean showMsg) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isIncomingSmsAllowed() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isOutgoingSmsAllowed() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isBluetoothLogEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public void bluetoothLog(String tag, String msg) throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isIncomingMmsAllowed() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isBackupAllowed(boolean showMsg) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public void AuditLogger(
        int severityGrade,
        int moduleGroup,
        boolean outcome,
        int uid,
        String swComponent,
        String logMessage)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public void RedactedAuditLogger(
        int severityGrade,
        int moduleGroup,
        boolean outcome,
        int uid,
        String swComponent,
        String logMessage,
        String redactedLogMessage)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public void AuditLoggerAsUser(
        int severityGrade,
        int moduleGroup,
        boolean outcome,
        int uid,
        String swComponent,
        String logMessage,
        int userId)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public void RedactedAuditLoggerAsUser(
        int severityGrade,
        int moduleGroup,
        boolean outcome,
        int uid,
        String swComponent,
        String logMessage,
        String redactedLogMessage,
        int userId)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public void AuditLoggerPrivileged(
        int severityGrade,
        int moduleGroup,
        boolean outcome,
        int pid,
        String swComponent,
        String logMessage)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public void RedactedAuditLoggerPrivileged(
        int severityGrade,
        int moduleGroup,
        boolean outcome,
        int pid,
        String swComponent,
        String logMessage,
        String redactedLogMessage)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public void AuditLoggerPrivilegedAsUser(
        int severityGrade,
        int moduleGroup,
        boolean outcome,
        int pid,
        String swComponent,
        String logMessage,
        int userId)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public void RedactedAuditLoggerPrivilegedAsUser(
        int severityGrade,
        int moduleGroup,
        boolean outcome,
        int pid,
        String swComponent,
        String logMessage,
        String redactedLogMessage,
        int userId)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isAuditLogEnabledAsUser(int userId) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public void notifyCertificateFailureAsUser(
        String module, String msg, boolean showMsg, int userId) throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isRevocationCheckEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isOcspCheckEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isCaCertificateTrustedAsUser(
        byte[] certBytes, boolean showMsg, boolean checkTrusted, int userId)
        throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isCertificateTrustedUntrustedEnabledAsUser(int userId) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isCertificateValidationAtInstallEnabledAsUser(int userId)
        throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isAudioRecordAllowed(boolean showMsg) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isVideoRecordAllowed(boolean showMsg) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public void notifyCertificateRemovedAsUser(String subject, int userId) throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public int validateCertificateAtInstallAsUser(byte[] certBytes, int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isClipboardShareAllowed() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isBlockSmsWithStorageEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isBlockMmsWithStorageEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public void storeBlockedSmsMms(
        boolean isSms,
        byte[] pdu,
        String srcAddress,
        int sendType,
        String timeStamp,
        String messageId,
        String subId)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isWapPushAllowed() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isAccountRemovalAllowed(String type, String account, boolean showMsg)
        throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public String getApplicationNameFromDb(String packageName, int userId) throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public String getApplicationNameForComponent(
        String componentName, String packageName, int userId) throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isAnyApplicationNameChangedAsUser(int userId) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isCopyContactToSimAllowed(int message) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getProKioskState() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getProKioskNotificationMessagesState() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public int getProKioskHideNotificationMessages() throws RemoteException {
      return 0;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public int getVolumeControlStream() throws RemoteException {
      return 0;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getToastEnabledState() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getToastShowPackageNameState() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public int getSensorDisabled() throws RemoteException {
      return 0;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getVolumePanelEnabledState() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getVolumeButtonRotationState() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getToastGravityEnabledState() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public int getToastGravity() throws RemoteException {
      return 0;
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
    public int getKeyboardMode() throws RemoteException {
      return 0;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getWifiState() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getUsbNetStateInternal() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public String getUsbNetAddress(int addressType) throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public Bundle getApplicationRestrictions(String packageName, int userId)
        throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getExtendedCallInfoState() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isAllowedMamPackage(String packageName) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isKnoxBluetoothEnabled(int userId) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isPackageAllowedToAccessExternalSdcard(int userId, int packageUid)
        throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public byte[] ucmGetCertificateChain(String alias) throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public byte[] ucmDecrypt(String uri, byte[] data, String algo, Bundle params)
        throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public byte[] ucmEncrypt(String uri, byte[] data, String algo, Bundle params)
        throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public byte[] ucmSign(String uri, byte[] data, String algo) throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public byte[] ucmMac(String uri, byte[] data, String algo) throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public List<String> getELMPermissions(String packageName) throws RemoteException {
      return null;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean getAddHomeShorcutRequested() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean registerSystemUICallback(ISystemUIAdapterCallback callback)
        throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isMockLocationEnabled() throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isGoogleAccountsAutoSyncAllowedAsUser(int userId) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isPackageInAvrWhitelist(int packageUid) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public void notifyPasswordPolicyOneLockChanged(boolean enabled, int userId)
        throws RemoteException {}

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isIncomingSmsAllowedFromSimSlot(int slotNum) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isOutgoingSmsAllowedFromSimSlot(int slotNum) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isMmsAllowedFromSimSlot(int slotNum) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isOutgoingCallAllowedFromSimSlot(int simSlot) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isIncomingCallAllowedFromSimSlot(int simSlot) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean canOutgoingCall(String dialNumber) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean canIncomingCall(String dialNumber) throws RemoteException {
      return false;
    }

    @Override // android.sec.enterprise.IEDMProxy
    public boolean isDataAllowedFromSimSlot(int simSlot) throws RemoteException {
      return false;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IEDMProxy {
    static final int TRANSACTION_AuditLogger = 44;
    static final int TRANSACTION_AuditLoggerAsUser = 46;
    static final int TRANSACTION_AuditLoggerPrivileged = 48;
    static final int TRANSACTION_AuditLoggerPrivilegedAsUser = 50;
    static final int TRANSACTION_RedactedAuditLogger = 45;
    static final int TRANSACTION_RedactedAuditLoggerAsUser = 47;
    static final int TRANSACTION_RedactedAuditLoggerPrivileged = 49;
    static final int TRANSACTION_RedactedAuditLoggerPrivilegedAsUser = 51;
    static final int TRANSACTION_addCallsCount = 1;
    static final int TRANSACTION_addNumberOfIncomingCalls = 21;
    static final int TRANSACTION_addNumberOfIncomingSms = 24;
    static final int TRANSACTION_addNumberOfOutgoingCalls = 22;
    static final int TRANSACTION_addNumberOfOutgoingSms = 25;
    static final int TRANSACTION_bluetoothLog = 41;
    static final int TRANSACTION_canIncomingCall = 113;
    static final int TRANSACTION_canIncomingSms = 28;
    static final int TRANSACTION_canOutgoingCall = 112;
    static final int TRANSACTION_canOutgoingSms = 27;
    static final int TRANSACTION_decreaseNumberOfOutgoingSms = 26;
    static final int TRANSACTION_getAddHomeShorcutRequested = 101;
    static final int TRANSACTION_getAllowBluetoothDataTransfer = 7;
    static final int TRANSACTION_getApplicationIconFromDb = 6;
    static final int TRANSACTION_getApplicationNameForComponent = 70;
    static final int TRANSACTION_getApplicationNameFromDb = 69;
    static final int TRANSACTION_getApplicationRestrictions = 90;
    static final int TRANSACTION_getBrowserSettingStatus = 15;
    static final int TRANSACTION_getELMPermissions = 100;
    static final int TRANSACTION_getEmergencyCallOnly = 20;
    static final int TRANSACTION_getExtendedCallInfoState = 91;
    static final int TRANSACTION_getKeyboardMode = 86;
    static final int TRANSACTION_getNtpServer = 30;
    static final int TRANSACTION_getNtpTimeout = 31;
    static final int TRANSACTION_getProKioskHideNotificationMessages = 75;
    static final int TRANSACTION_getProKioskNotificationMessagesState = 74;
    static final int TRANSACTION_getProKioskState = 73;
    static final int TRANSACTION_getSensorDisabled = 79;
    static final int TRANSACTION_getToastEnabledState = 77;
    static final int TRANSACTION_getToastGravity = 83;
    static final int TRANSACTION_getToastGravityEnabledState = 82;
    static final int TRANSACTION_getToastGravityXOffset = 84;
    static final int TRANSACTION_getToastGravityYOffset = 85;
    static final int TRANSACTION_getToastShowPackageNameState = 78;
    static final int TRANSACTION_getUsbNetAddress = 89;
    static final int TRANSACTION_getUsbNetStateInternal = 88;
    static final int TRANSACTION_getVolumeButtonRotationState = 81;
    static final int TRANSACTION_getVolumeControlStream = 76;
    static final int TRANSACTION_getVolumePanelEnabledState = 80;
    static final int TRANSACTION_getWifiState = 87;
    static final int TRANSACTION_isAccountRemovalAllowed = 68;
    static final int TRANSACTION_isAllowedMamPackage = 92;
    static final int TRANSACTION_isAnyApplicationNameChangedAsUser = 71;
    static final int TRANSACTION_isAudioRecordAllowed = 59;
    static final int TRANSACTION_isAuditLogEnabledAsUser = 52;
    static final int TRANSACTION_isBackupAllowed = 43;
    static final int TRANSACTION_isBlockMmsWithStorageEnabled = 65;
    static final int TRANSACTION_isBlockSmsWithStorageEnabled = 64;
    static final int TRANSACTION_isBluetoothDeviceAllowed = 11;
    static final int TRANSACTION_isBluetoothEnabled = 14;
    static final int TRANSACTION_isBluetoothLogEnabled = 40;
    static final int TRANSACTION_isBluetoothUUIDAllowed = 9;
    static final int TRANSACTION_isCaCertificateTrustedAsUser = 56;
    static final int TRANSACTION_isCallingCaptureEnabled = 2;
    static final int TRANSACTION_isCertificateTrustedUntrustedEnabledAsUser = 57;
    static final int TRANSACTION_isCertificateValidationAtInstallEnabledAsUser = 58;
    static final int TRANSACTION_isClipboardAllowed = 18;
    static final int TRANSACTION_isClipboardShareAllowed = 63;
    static final int TRANSACTION_isCopyContactToSimAllowed = 72;
    static final int TRANSACTION_isDataAllowedFromSimSlot = 114;
    static final int TRANSACTION_isDiscoverableEnabled = 13;
    static final int TRANSACTION_isFactoryResetAllowed = 36;
    static final int TRANSACTION_isGoogleAccountsAutoSyncAllowedAsUser = 104;
    static final int TRANSACTION_isIncomingCallAllowedFromSimSlot = 111;
    static final int TRANSACTION_isIncomingMmsAllowed = 42;
    static final int TRANSACTION_isIncomingSmsAllowed = 38;
    static final int TRANSACTION_isIncomingSmsAllowedFromSimSlot = 107;
    static final int TRANSACTION_isKnoxBluetoothEnabled = 93;
    static final int TRANSACTION_isLimitNumberOfSmsEnabled = 23;
    static final int TRANSACTION_isMicrophoneEnabled = 19;
    static final int TRANSACTION_isMmsAllowedFromSimSlot = 109;
    static final int TRANSACTION_isMockLocationEnabled = 103;
    static final int TRANSACTION_isNtpSetByMDM = 33;
    static final int TRANSACTION_isOcspCheckEnabled = 55;
    static final int TRANSACTION_isOutgoingCallAllowedFromSimSlot = 110;
    static final int TRANSACTION_isOutgoingCallsAllowed = 8;
    static final int TRANSACTION_isOutgoingSmsAllowed = 39;
    static final int TRANSACTION_isOutgoingSmsAllowedFromSimSlot = 108;
    static final int TRANSACTION_isPackageAllowedToAccessExternalSdcard = 94;
    static final int TRANSACTION_isPackageInAvrWhitelist = 105;
    static final int TRANSACTION_isPairingEnabled = 12;
    static final int TRANSACTION_isProfileEnabled = 10;
    static final int TRANSACTION_isRevocationCheckEnabled = 54;
    static final int TRANSACTION_isRoamingDataEnabled = 17;
    static final int TRANSACTION_isRoamingPushEnabled = 16;
    static final int TRANSACTION_isSMSCaptureEnabled = 4;
    static final int TRANSACTION_isScreenLockPatternVisibilityEnabled = 34;
    static final int TRANSACTION_isScreenLockPatternVisibilityEnabledAsUser = 35;
    static final int TRANSACTION_isSmsPatternCheckRequired = 29;
    static final int TRANSACTION_isTaskManagerAllowed = 37;
    static final int TRANSACTION_isVideoRecordAllowed = 60;
    static final int TRANSACTION_isWapPushAllowed = 67;
    static final int TRANSACTION_notifyCertificateFailureAsUser = 53;
    static final int TRANSACTION_notifyCertificateRemovedAsUser = 61;
    static final int TRANSACTION_notifyPasswordPolicyOneLockChanged = 106;
    static final int TRANSACTION_registerSystemUICallback = 102;
    static final int TRANSACTION_shallForceNtpMdmValues = 32;
    static final int TRANSACTION_storeBlockedSmsMms = 66;
    static final int TRANSACTION_storeCalling = 3;
    static final int TRANSACTION_storeSMS = 5;
    static final int TRANSACTION_ucmDecrypt = 96;
    static final int TRANSACTION_ucmEncrypt = 97;
    static final int TRANSACTION_ucmGetCertificateChain = 95;
    static final int TRANSACTION_ucmMac = 99;
    static final int TRANSACTION_ucmSign = 98;
    static final int TRANSACTION_validateCertificateAtInstallAsUser = 62;

    public Stub() {
      attachInterface(this, IEDMProxy.DESCRIPTOR);
    }

    public static IEDMProxy asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IEDMProxy.DESCRIPTOR);
      if (iin != null && (iin instanceof IEDMProxy)) {
        return (IEDMProxy) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
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
          return SecContentProviderURI.APPLICATIONPOLICY_APPLICATIONICONFROMDB_METHOD;
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
          return SecContentProviderURI.BROWSERPOLICY_SETTINGSTATUS_METHOD;
        case 16:
          return SecContentProviderURI.ROAMINGPOLICY_PUSH_METHOD;
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
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_LIMITNUMBEROFSMS_METHOD;
        case 24:
          return "addNumberOfIncomingSms";
        case 25:
          return "addNumberOfOutgoingSms";
        case 26:
          return "decreaseNumberOfOutgoingSms";
        case 27:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_CANOUTGINGSMS_METHOD;
        case 28:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_CANINCOMINGSMS_METHOD;
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
          return SecContentProviderURI.PASSWORDPOLICY_SCREENLOCKPATTERNVISIBILITY_METHOD;
        case 35:
          return "isScreenLockPatternVisibilityEnabledAsUser";
        case 36:
          return SecContentProviderURI.RESTRICTIONPOLICY_FACTORYRESETALLOWED_METHOD;
        case 37:
          return SecContentProviderURI.KIOSKMODE_TASKMANAGERALLOWED_METHOD;
        case 38:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_INCOMINGSMS_METHOD;
        case 39:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_OUTGOINGSMS_METHOD;
        case 40:
          return "isBluetoothLogEnabled";
        case 41:
          return "bluetoothLog";
        case 42:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_INCOMINGMMS_METHOD;
        case 43:
          return SecContentProviderURI.RESTRICTIONPOLICY_BACKUPALLOWED_METHOD;
        case 44:
          return "AuditLogger";
        case 45:
          return SemKnoxPolicyContract.AuditLog.REDACTED_AUDITLOG;
        case 46:
          return SecContentProviderURI.AUDITLOGPOLICY_AUDITLOGASUSER_METHOD;
        case 47:
          return SemKnoxPolicyContract.AuditLog.REDACTED_AUDITLOG_AS_USER;
        case 48:
          return "AuditLoggerPrivileged";
        case 49:
          return "RedactedAuditLoggerPrivileged";
        case 50:
          return "AuditLoggerPrivilegedAsUser";
        case 51:
          return "RedactedAuditLoggerPrivilegedAsUser";
        case 52:
          return "isAuditLogEnabledAsUser";
        case 53:
          return "notifyCertificateFailureAsUser";
        case 54:
          return SecContentProviderURI.CERTIFICATEPOLICY_REVOCATIONCHECK_METHOD;
        case 55:
          return SecContentProviderURI.CERTIFICATEPOLICY_OCSPCHECK_METHOD;
        case 56:
          return SecContentProviderURI.CERTIFICATEPOLICY_CACERTIFICATETRUSTEDASUSER_METHOD;
        case 57:
          return "isCertificateTrustedUntrustedEnabledAsUser";
        case 58:
          return "isCertificateValidationAtInstallEnabledAsUser";
        case 59:
          return SecContentProviderURI.RESTRICTIONPOLICY_AUDIORECORDALLOWED_METHOD;
        case 60:
          return SecContentProviderURI.RESTRICTIONPOLICY_VIDEORECORD_METHOD;
        case 61:
          return "notifyCertificateRemovedAsUser";
        case 62:
          return "validateCertificateAtInstallAsUser";
        case 63:
          return "isClipboardShareAllowed";
        case 64:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_BLOCKSMSWITHSTORAGE_METHOD;
        case 65:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_BLOCKMMSWITHSTORAGE_METHOD;
        case 66:
          return "storeBlockedSmsMms";
        case 67:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_WAPPUSHALLOWED_METHOD;
        case 68:
          return "isAccountRemovalAllowed";
        case 69:
          return SecContentProviderURI.APPLICATIONPOLICY_APPLICATIONNAMEFROMDB_METHOD;
        case 70:
          return "getApplicationNameForComponent";
        case 71:
          return "isAnyApplicationNameChangedAsUser";
        case 72:
          return "isCopyContactToSimAllowed";
        case 73:
          return "getProKioskState";
        case 74:
          return "getProKioskNotificationMessagesState";
        case 75:
          return "getProKioskHideNotificationMessages";
        case 76:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_VOLUMECONTROLSTREAM_METHOD;
        case 77:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTENABLEDSTATE_METHOD;
        case 78:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTSHOWPACKAGENAMESTATE_METHOD;
        case 79:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_SENSORDISABLED_METHOD;
        case 80:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_VOLUMEPANELENABLEDSTATE_METHOD;
        case 81:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_VOLUMEBUTTONROTATIONSTATE_METHOD;
        case 82:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITYENABLEDSTATE_METHOD;
        case 83:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITY_METHOD;
        case 84:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITYXOFFSET_METHOD;
        case 85:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_TOASTGRAVITYYOFFSET_METHOD;
        case 86:
          return "getKeyboardMode";
        case 87:
          return "getWifiState";
        case 88:
          return "getUsbNetStateInternal";
        case 89:
          return "getUsbNetAddress";
        case 90:
          return "getApplicationRestrictions";
        case 91:
          return SecContentProviderURI.KNOXCUSTOMMANAGERSERVICE_EXTENTEDCALLINFOSTATE_METHOD;
        case 92:
          return "isAllowedMamPackage";
        case 93:
          return "isKnoxBluetoothEnabled";
        case 94:
          return "isPackageAllowedToAccessExternalSdcard";
        case 95:
          return "ucmGetCertificateChain";
        case 96:
          return "ucmDecrypt";
        case 97:
          return "ucmEncrypt";
        case 98:
          return "ucmSign";
        case 99:
          return "ucmMac";
        case 100:
          return "getELMPermissions";
        case 101:
          return "getAddHomeShorcutRequested";
        case 102:
          return "registerSystemUICallback";
        case 103:
          return SecContentProviderURI.RESTRICTIONPOLICY_MOCKLOCATION_METHOD;
        case 104:
          return "isGoogleAccountsAutoSyncAllowedAsUser";
        case 105:
          return "isPackageInAvrWhitelist";
        case 106:
          return "notifyPasswordPolicyOneLockChanged";
        case 107:
          return "isIncomingSmsAllowedFromSimSlot";
        case 108:
          return "isOutgoingSmsAllowedFromSimSlot";
        case 109:
          return "isMmsAllowedFromSimSlot";
        case 110:
          return "isOutgoingCallAllowedFromSimSlot";
        case 111:
          return "isIncomingCallAllowedFromSimSlot";
        case 112:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_CANOUTGOINGCALL_METHOD;
        case 113:
          return SecContentProviderURI.PHONERESTRICTIONPOLICY_CANINCOMINGCALL_METHOD;
        case 114:
          return "isDataAllowedFromSimSlot";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IEDMProxy.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IEDMProxy.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              addCallsCount(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              boolean _result = isCallingCaptureEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 3:
              String _arg02 = data.readString();
              String _arg1 = data.readString();
              String _arg2 = data.readString();
              String _arg3 = data.readString();
              boolean _arg4 = data.readBoolean();
              data.enforceNoDataAvail();
              storeCalling(_arg02, _arg1, _arg2, _arg3, _arg4);
              reply.writeNoException();
              return true;
            case 4:
              boolean _result2 = isSMSCaptureEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 5:
              String _arg03 = data.readString();
              String _arg12 = data.readString();
              String _arg22 = data.readString();
              boolean _arg32 = data.readBoolean();
              data.enforceNoDataAvail();
              storeSMS(_arg03, _arg12, _arg22, _arg32);
              reply.writeNoException();
              return true;
            case 6:
              String _arg04 = data.readString();
              int _arg13 = data.readInt();
              data.enforceNoDataAvail();
              byte[] _result3 = getApplicationIconFromDb(_arg04, _arg13);
              reply.writeNoException();
              reply.writeByteArray(_result3);
              return true;
            case 7:
              boolean _arg05 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result4 = getAllowBluetoothDataTransfer(_arg05);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 8:
              boolean _result5 = isOutgoingCallsAllowed();
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 9:
              String _arg06 = data.readString();
              data.enforceNoDataAvail();
              boolean _result6 = isBluetoothUUIDAllowed(_arg06);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 10:
              int _arg07 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result7 = isProfileEnabled(_arg07);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 11:
              String _arg08 = data.readString();
              data.enforceNoDataAvail();
              boolean _result8 = isBluetoothDeviceAllowed(_arg08);
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 12:
              boolean _result9 = isPairingEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 13:
              boolean _result10 = isDiscoverableEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result10);
              return true;
            case 14:
              boolean _result11 = isBluetoothEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result11);
              return true;
            case 15:
              int _arg09 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result12 = getBrowserSettingStatus(_arg09);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 16:
              boolean _result13 = isRoamingPushEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result13);
              return true;
            case 17:
              boolean _result14 = isRoamingDataEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 18:
              boolean _arg010 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result15 = isClipboardAllowed(_arg010);
              reply.writeNoException();
              reply.writeBoolean(_result15);
              return true;
            case 19:
              boolean _arg011 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result16 = isMicrophoneEnabled(_arg011);
              reply.writeNoException();
              reply.writeBoolean(_result16);
              return true;
            case 20:
              boolean _arg012 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result17 = getEmergencyCallOnly(_arg012);
              reply.writeNoException();
              reply.writeBoolean(_result17);
              return true;
            case 21:
              boolean _result18 = addNumberOfIncomingCalls();
              reply.writeNoException();
              reply.writeBoolean(_result18);
              return true;
            case 22:
              boolean _result19 = addNumberOfOutgoingCalls();
              reply.writeNoException();
              reply.writeBoolean(_result19);
              return true;
            case 23:
              boolean _result20 = isLimitNumberOfSmsEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result20);
              return true;
            case 24:
              boolean _result21 = addNumberOfIncomingSms();
              reply.writeNoException();
              reply.writeBoolean(_result21);
              return true;
            case 25:
              boolean _result22 = addNumberOfOutgoingSms();
              reply.writeNoException();
              reply.writeBoolean(_result22);
              return true;
            case 26:
              boolean _result23 = decreaseNumberOfOutgoingSms();
              reply.writeNoException();
              reply.writeBoolean(_result23);
              return true;
            case 27:
              String _arg013 = data.readString();
              data.enforceNoDataAvail();
              boolean _result24 = canOutgoingSms(_arg013);
              reply.writeNoException();
              reply.writeBoolean(_result24);
              return true;
            case 28:
              String _arg014 = data.readString();
              data.enforceNoDataAvail();
              boolean _result25 = canIncomingSms(_arg014);
              reply.writeNoException();
              reply.writeBoolean(_result25);
              return true;
            case 29:
              boolean _result26 = isSmsPatternCheckRequired();
              reply.writeNoException();
              reply.writeBoolean(_result26);
              return true;
            case 30:
              String _result27 = getNtpServer();
              reply.writeNoException();
              reply.writeString(_result27);
              return true;
            case 31:
              long _result28 = getNtpTimeout();
              reply.writeNoException();
              reply.writeLong(_result28);
              return true;
            case 32:
              boolean _result29 = shallForceNtpMdmValues();
              reply.writeNoException();
              reply.writeBoolean(_result29);
              return true;
            case 33:
              boolean _result30 = isNtpSetByMDM();
              reply.writeNoException();
              reply.writeBoolean(_result30);
              return true;
            case 34:
              boolean _result31 = isScreenLockPatternVisibilityEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result31);
              return true;
            case 35:
              int _arg015 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result32 = isScreenLockPatternVisibilityEnabledAsUser(_arg015);
              reply.writeNoException();
              reply.writeBoolean(_result32);
              return true;
            case 36:
              boolean _result33 = isFactoryResetAllowed();
              reply.writeNoException();
              reply.writeBoolean(_result33);
              return true;
            case 37:
              boolean _arg016 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result34 = isTaskManagerAllowed(_arg016);
              reply.writeNoException();
              reply.writeBoolean(_result34);
              return true;
            case 38:
              boolean _result35 = isIncomingSmsAllowed();
              reply.writeNoException();
              reply.writeBoolean(_result35);
              return true;
            case 39:
              boolean _result36 = isOutgoingSmsAllowed();
              reply.writeNoException();
              reply.writeBoolean(_result36);
              return true;
            case 40:
              boolean _result37 = isBluetoothLogEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result37);
              return true;
            case 41:
              String _arg017 = data.readString();
              String _arg14 = data.readString();
              data.enforceNoDataAvail();
              bluetoothLog(_arg017, _arg14);
              reply.writeNoException();
              return true;
            case 42:
              boolean _result38 = isIncomingMmsAllowed();
              reply.writeNoException();
              reply.writeBoolean(_result38);
              return true;
            case 43:
              boolean _arg018 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result39 = isBackupAllowed(_arg018);
              reply.writeNoException();
              reply.writeBoolean(_result39);
              return true;
            case 44:
              int _arg019 = data.readInt();
              int _arg15 = data.readInt();
              boolean _arg23 = data.readBoolean();
              int _arg33 = data.readInt();
              String _arg42 = data.readString();
              String _arg5 = data.readString();
              data.enforceNoDataAvail();
              AuditLogger(_arg019, _arg15, _arg23, _arg33, _arg42, _arg5);
              reply.writeNoException();
              return true;
            case 45:
              int _arg020 = data.readInt();
              int _arg16 = data.readInt();
              boolean _arg24 = data.readBoolean();
              int _arg34 = data.readInt();
              String _arg43 = data.readString();
              String _arg52 = data.readString();
              String _arg6 = data.readString();
              data.enforceNoDataAvail();
              RedactedAuditLogger(_arg020, _arg16, _arg24, _arg34, _arg43, _arg52, _arg6);
              reply.writeNoException();
              return true;
            case 46:
              int _arg021 = data.readInt();
              int _arg17 = data.readInt();
              boolean _arg25 = data.readBoolean();
              int _arg35 = data.readInt();
              String _arg44 = data.readString();
              String _arg53 = data.readString();
              int _arg62 = data.readInt();
              data.enforceNoDataAvail();
              AuditLoggerAsUser(_arg021, _arg17, _arg25, _arg35, _arg44, _arg53, _arg62);
              reply.writeNoException();
              return true;
            case 47:
              int _arg022 = data.readInt();
              int _arg18 = data.readInt();
              boolean _arg26 = data.readBoolean();
              int _arg36 = data.readInt();
              String _arg45 = data.readString();
              String _arg54 = data.readString();
              String _arg63 = data.readString();
              int _arg7 = data.readInt();
              data.enforceNoDataAvail();
              RedactedAuditLoggerAsUser(
                  _arg022, _arg18, _arg26, _arg36, _arg45, _arg54, _arg63, _arg7);
              reply.writeNoException();
              return true;
            case 48:
              int _arg023 = data.readInt();
              int _arg19 = data.readInt();
              boolean _arg27 = data.readBoolean();
              int _arg37 = data.readInt();
              String _arg46 = data.readString();
              String _arg55 = data.readString();
              data.enforceNoDataAvail();
              AuditLoggerPrivileged(_arg023, _arg19, _arg27, _arg37, _arg46, _arg55);
              reply.writeNoException();
              return true;
            case 49:
              int _arg024 = data.readInt();
              int _arg110 = data.readInt();
              boolean _arg28 = data.readBoolean();
              int _arg38 = data.readInt();
              String _arg47 = data.readString();
              String _arg56 = data.readString();
              String _arg64 = data.readString();
              data.enforceNoDataAvail();
              RedactedAuditLoggerPrivileged(
                  _arg024, _arg110, _arg28, _arg38, _arg47, _arg56, _arg64);
              reply.writeNoException();
              return true;
            case 50:
              int _arg025 = data.readInt();
              int _arg111 = data.readInt();
              boolean _arg29 = data.readBoolean();
              int _arg39 = data.readInt();
              String _arg48 = data.readString();
              String _arg57 = data.readString();
              int _arg65 = data.readInt();
              data.enforceNoDataAvail();
              AuditLoggerPrivilegedAsUser(_arg025, _arg111, _arg29, _arg39, _arg48, _arg57, _arg65);
              reply.writeNoException();
              return true;
            case 51:
              int _arg026 = data.readInt();
              int _arg112 = data.readInt();
              boolean _arg210 = data.readBoolean();
              int _arg310 = data.readInt();
              String _arg49 = data.readString();
              String _arg58 = data.readString();
              String _arg66 = data.readString();
              int _arg72 = data.readInt();
              data.enforceNoDataAvail();
              RedactedAuditLoggerPrivilegedAsUser(
                  _arg026, _arg112, _arg210, _arg310, _arg49, _arg58, _arg66, _arg72);
              reply.writeNoException();
              return true;
            case 52:
              int _arg027 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result40 = isAuditLogEnabledAsUser(_arg027);
              reply.writeNoException();
              reply.writeBoolean(_result40);
              return true;
            case 53:
              String _arg028 = data.readString();
              String _arg113 = data.readString();
              boolean _arg211 = data.readBoolean();
              int _arg311 = data.readInt();
              data.enforceNoDataAvail();
              notifyCertificateFailureAsUser(_arg028, _arg113, _arg211, _arg311);
              reply.writeNoException();
              return true;
            case 54:
              boolean _result41 = isRevocationCheckEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result41);
              return true;
            case 55:
              boolean _result42 = isOcspCheckEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result42);
              return true;
            case 56:
              byte[] _arg029 = data.createByteArray();
              boolean _arg114 = data.readBoolean();
              boolean _arg212 = data.readBoolean();
              int _arg312 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result43 = isCaCertificateTrustedAsUser(_arg029, _arg114, _arg212, _arg312);
              reply.writeNoException();
              reply.writeBoolean(_result43);
              return true;
            case 57:
              int _arg030 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result44 = isCertificateTrustedUntrustedEnabledAsUser(_arg030);
              reply.writeNoException();
              reply.writeBoolean(_result44);
              return true;
            case 58:
              int _arg031 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result45 = isCertificateValidationAtInstallEnabledAsUser(_arg031);
              reply.writeNoException();
              reply.writeBoolean(_result45);
              return true;
            case 59:
              boolean _arg032 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result46 = isAudioRecordAllowed(_arg032);
              reply.writeNoException();
              reply.writeBoolean(_result46);
              return true;
            case 60:
              boolean _arg033 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result47 = isVideoRecordAllowed(_arg033);
              reply.writeNoException();
              reply.writeBoolean(_result47);
              return true;
            case 61:
              String _arg034 = data.readString();
              int _arg115 = data.readInt();
              data.enforceNoDataAvail();
              notifyCertificateRemovedAsUser(_arg034, _arg115);
              reply.writeNoException();
              return true;
            case 62:
              byte[] _arg035 = data.createByteArray();
              int _arg116 = data.readInt();
              data.enforceNoDataAvail();
              int _result48 = validateCertificateAtInstallAsUser(_arg035, _arg116);
              reply.writeNoException();
              reply.writeInt(_result48);
              return true;
            case 63:
              boolean _result49 = isClipboardShareAllowed();
              reply.writeNoException();
              reply.writeBoolean(_result49);
              return true;
            case 64:
              boolean _result50 = isBlockSmsWithStorageEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result50);
              return true;
            case 65:
              boolean _result51 = isBlockMmsWithStorageEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result51);
              return true;
            case 66:
              boolean _arg036 = data.readBoolean();
              byte[] _arg117 = data.createByteArray();
              String _arg213 = data.readString();
              int _arg313 = data.readInt();
              String _arg410 = data.readString();
              String _arg59 = data.readString();
              String _arg67 = data.readString();
              data.enforceNoDataAvail();
              storeBlockedSmsMms(_arg036, _arg117, _arg213, _arg313, _arg410, _arg59, _arg67);
              reply.writeNoException();
              return true;
            case 67:
              boolean _result52 = isWapPushAllowed();
              reply.writeNoException();
              reply.writeBoolean(_result52);
              return true;
            case 68:
              String _arg037 = data.readString();
              String _arg118 = data.readString();
              boolean _arg214 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result53 = isAccountRemovalAllowed(_arg037, _arg118, _arg214);
              reply.writeNoException();
              reply.writeBoolean(_result53);
              return true;
            case 69:
              String _arg038 = data.readString();
              int _arg119 = data.readInt();
              data.enforceNoDataAvail();
              String _result54 = getApplicationNameFromDb(_arg038, _arg119);
              reply.writeNoException();
              reply.writeString(_result54);
              return true;
            case 70:
              String _arg039 = data.readString();
              String _arg120 = data.readString();
              int _arg215 = data.readInt();
              data.enforceNoDataAvail();
              String _result55 = getApplicationNameForComponent(_arg039, _arg120, _arg215);
              reply.writeNoException();
              reply.writeString(_result55);
              return true;
            case 71:
              int _arg040 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result56 = isAnyApplicationNameChangedAsUser(_arg040);
              reply.writeNoException();
              reply.writeBoolean(_result56);
              return true;
            case 72:
              int _arg041 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result57 = isCopyContactToSimAllowed(_arg041);
              reply.writeNoException();
              reply.writeBoolean(_result57);
              return true;
            case 73:
              boolean _result58 = getProKioskState();
              reply.writeNoException();
              reply.writeBoolean(_result58);
              return true;
            case 74:
              boolean _result59 = getProKioskNotificationMessagesState();
              reply.writeNoException();
              reply.writeBoolean(_result59);
              return true;
            case 75:
              int _result60 = getProKioskHideNotificationMessages();
              reply.writeNoException();
              reply.writeInt(_result60);
              return true;
            case 76:
              int _result61 = getVolumeControlStream();
              reply.writeNoException();
              reply.writeInt(_result61);
              return true;
            case 77:
              boolean _result62 = getToastEnabledState();
              reply.writeNoException();
              reply.writeBoolean(_result62);
              return true;
            case 78:
              boolean _result63 = getToastShowPackageNameState();
              reply.writeNoException();
              reply.writeBoolean(_result63);
              return true;
            case 79:
              int _result64 = getSensorDisabled();
              reply.writeNoException();
              reply.writeInt(_result64);
              return true;
            case 80:
              boolean _result65 = getVolumePanelEnabledState();
              reply.writeNoException();
              reply.writeBoolean(_result65);
              return true;
            case 81:
              boolean _result66 = getVolumeButtonRotationState();
              reply.writeNoException();
              reply.writeBoolean(_result66);
              return true;
            case 82:
              boolean _result67 = getToastGravityEnabledState();
              reply.writeNoException();
              reply.writeBoolean(_result67);
              return true;
            case 83:
              int _result68 = getToastGravity();
              reply.writeNoException();
              reply.writeInt(_result68);
              return true;
            case 84:
              int _result69 = getToastGravityXOffset();
              reply.writeNoException();
              reply.writeInt(_result69);
              return true;
            case 85:
              int _result70 = getToastGravityYOffset();
              reply.writeNoException();
              reply.writeInt(_result70);
              return true;
            case 86:
              int _result71 = getKeyboardMode();
              reply.writeNoException();
              reply.writeInt(_result71);
              return true;
            case 87:
              boolean _result72 = getWifiState();
              reply.writeNoException();
              reply.writeBoolean(_result72);
              return true;
            case 88:
              boolean _result73 = getUsbNetStateInternal();
              reply.writeNoException();
              reply.writeBoolean(_result73);
              return true;
            case 89:
              int _arg042 = data.readInt();
              data.enforceNoDataAvail();
              String _result74 = getUsbNetAddress(_arg042);
              reply.writeNoException();
              reply.writeString(_result74);
              return true;
            case 90:
              String _arg043 = data.readString();
              int _arg121 = data.readInt();
              data.enforceNoDataAvail();
              Bundle _result75 = getApplicationRestrictions(_arg043, _arg121);
              reply.writeNoException();
              reply.writeTypedObject(_result75, 1);
              return true;
            case 91:
              boolean _result76 = getExtendedCallInfoState();
              reply.writeNoException();
              reply.writeBoolean(_result76);
              return true;
            case 92:
              String _arg044 = data.readString();
              data.enforceNoDataAvail();
              boolean _result77 = isAllowedMamPackage(_arg044);
              reply.writeNoException();
              reply.writeBoolean(_result77);
              return true;
            case 93:
              int _arg045 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result78 = isKnoxBluetoothEnabled(_arg045);
              reply.writeNoException();
              reply.writeBoolean(_result78);
              return true;
            case 94:
              int _arg046 = data.readInt();
              int _arg122 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result79 = isPackageAllowedToAccessExternalSdcard(_arg046, _arg122);
              reply.writeNoException();
              reply.writeBoolean(_result79);
              return true;
            case 95:
              String _arg047 = data.readString();
              data.enforceNoDataAvail();
              byte[] _result80 = ucmGetCertificateChain(_arg047);
              reply.writeNoException();
              reply.writeByteArray(_result80);
              return true;
            case 96:
              String _arg048 = data.readString();
              byte[] _arg123 = data.createByteArray();
              String _arg216 = data.readString();
              Bundle _arg314 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              byte[] _result81 = ucmDecrypt(_arg048, _arg123, _arg216, _arg314);
              reply.writeNoException();
              reply.writeByteArray(_result81);
              return true;
            case 97:
              String _arg049 = data.readString();
              byte[] _arg124 = data.createByteArray();
              String _arg217 = data.readString();
              Bundle _arg315 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              byte[] _result82 = ucmEncrypt(_arg049, _arg124, _arg217, _arg315);
              reply.writeNoException();
              reply.writeByteArray(_result82);
              return true;
            case 98:
              String _arg050 = data.readString();
              byte[] _arg125 = data.createByteArray();
              String _arg218 = data.readString();
              data.enforceNoDataAvail();
              byte[] _result83 = ucmSign(_arg050, _arg125, _arg218);
              reply.writeNoException();
              reply.writeByteArray(_result83);
              return true;
            case 99:
              String _arg051 = data.readString();
              byte[] _arg126 = data.createByteArray();
              String _arg219 = data.readString();
              data.enforceNoDataAvail();
              byte[] _result84 = ucmMac(_arg051, _arg126, _arg219);
              reply.writeNoException();
              reply.writeByteArray(_result84);
              return true;
            case 100:
              String _arg052 = data.readString();
              data.enforceNoDataAvail();
              List<String> _result85 = getELMPermissions(_arg052);
              reply.writeNoException();
              reply.writeStringList(_result85);
              return true;
            case 101:
              boolean _result86 = getAddHomeShorcutRequested();
              reply.writeNoException();
              reply.writeBoolean(_result86);
              return true;
            case 102:
              ISystemUIAdapterCallback _arg053 =
                  ISystemUIAdapterCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result87 = registerSystemUICallback(_arg053);
              reply.writeNoException();
              reply.writeBoolean(_result87);
              return true;
            case 103:
              boolean _result88 = isMockLocationEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result88);
              return true;
            case 104:
              int _arg054 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result89 = isGoogleAccountsAutoSyncAllowedAsUser(_arg054);
              reply.writeNoException();
              reply.writeBoolean(_result89);
              return true;
            case 105:
              int _arg055 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result90 = isPackageInAvrWhitelist(_arg055);
              reply.writeNoException();
              reply.writeBoolean(_result90);
              return true;
            case 106:
              boolean _arg056 = data.readBoolean();
              int _arg127 = data.readInt();
              data.enforceNoDataAvail();
              notifyPasswordPolicyOneLockChanged(_arg056, _arg127);
              reply.writeNoException();
              return true;
            case 107:
              int _arg057 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result91 = isIncomingSmsAllowedFromSimSlot(_arg057);
              reply.writeNoException();
              reply.writeBoolean(_result91);
              return true;
            case 108:
              int _arg058 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result92 = isOutgoingSmsAllowedFromSimSlot(_arg058);
              reply.writeNoException();
              reply.writeBoolean(_result92);
              return true;
            case 109:
              int _arg059 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result93 = isMmsAllowedFromSimSlot(_arg059);
              reply.writeNoException();
              reply.writeBoolean(_result93);
              return true;
            case 110:
              int _arg060 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result94 = isOutgoingCallAllowedFromSimSlot(_arg060);
              reply.writeNoException();
              reply.writeBoolean(_result94);
              return true;
            case 111:
              int _arg061 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result95 = isIncomingCallAllowedFromSimSlot(_arg061);
              reply.writeNoException();
              reply.writeBoolean(_result95);
              return true;
            case 112:
              String _arg062 = data.readString();
              data.enforceNoDataAvail();
              boolean _result96 = canOutgoingCall(_arg062);
              reply.writeNoException();
              reply.writeBoolean(_result96);
              return true;
            case 113:
              String _arg063 = data.readString();
              data.enforceNoDataAvail();
              boolean _result97 = canIncomingCall(_arg063);
              reply.writeNoException();
              reply.writeBoolean(_result97);
              return true;
            case 114:
              int _arg064 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result98 = isDataAllowedFromSimSlot(_arg064);
              reply.writeNoException();
              reply.writeBoolean(_result98);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IEDMProxy {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IEDMProxy.DESCRIPTOR;
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void addCallsCount(String callType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(callType);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isCallingCaptureEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void storeCalling(
          String address, String timeStamp, String duration, String status, boolean isIncoming)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(address);
          _data.writeString(timeStamp);
          _data.writeString(duration);
          _data.writeString(status);
          _data.writeBoolean(isIncoming);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isSMSCaptureEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void storeSMS(String address, String timeStamp, String message, boolean isInbound)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(address);
          _data.writeString(timeStamp);
          _data.writeString(message);
          _data.writeBoolean(isInbound);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public byte[] getApplicationIconFromDb(String pkgName, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(pkgName);
          _data.writeInt(userId);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getAllowBluetoothDataTransfer(boolean showMsg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(showMsg);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isOutgoingCallsAllowed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isBluetoothUUIDAllowed(String uuid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(uuid);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isProfileEnabled(int setting) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(setting);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isBluetoothDeviceAllowed(String address) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(address);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isPairingEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isDiscoverableEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isBluetoothEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getBrowserSettingStatus(int setting) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(setting);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isRoamingPushEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isRoamingDataEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isClipboardAllowed(boolean showMsg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(showMsg);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isMicrophoneEnabled(boolean showMsg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(showMsg);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getEmergencyCallOnly(boolean showMsg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(showMsg);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean addNumberOfIncomingCalls() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean addNumberOfOutgoingCalls() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isLimitNumberOfSmsEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean addNumberOfIncomingSms() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean addNumberOfOutgoingSms() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean decreaseNumberOfOutgoingSms() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean canOutgoingSms(String phoneNumber) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(phoneNumber);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean canIncomingSms(String phoneNumber) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(phoneNumber);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isSmsPatternCheckRequired() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public String getNtpServer() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public long getNtpTimeout() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean shallForceNtpMdmValues() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isNtpSetByMDM() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isScreenLockPatternVisibilityEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isScreenLockPatternVisibilityEnabledAsUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isFactoryResetAllowed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isTaskManagerAllowed(boolean showMsg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(showMsg);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isIncomingSmsAllowed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isOutgoingSmsAllowed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isBluetoothLogEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void bluetoothLog(String tag, String msg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(tag);
          _data.writeString(msg);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isIncomingMmsAllowed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isBackupAllowed(boolean showMsg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(showMsg);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void AuditLogger(
          int severityGrade,
          int moduleGroup,
          boolean outcome,
          int uid,
          String swComponent,
          String logMessage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(severityGrade);
          _data.writeInt(moduleGroup);
          _data.writeBoolean(outcome);
          _data.writeInt(uid);
          _data.writeString(swComponent);
          _data.writeString(logMessage);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void RedactedAuditLogger(
          int severityGrade,
          int moduleGroup,
          boolean outcome,
          int uid,
          String swComponent,
          String logMessage,
          String redactedLogMessage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(severityGrade);
          _data.writeInt(moduleGroup);
          _data.writeBoolean(outcome);
          _data.writeInt(uid);
          _data.writeString(swComponent);
          _data.writeString(logMessage);
          _data.writeString(redactedLogMessage);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void AuditLoggerAsUser(
          int severityGrade,
          int moduleGroup,
          boolean outcome,
          int uid,
          String swComponent,
          String logMessage,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(severityGrade);
          _data.writeInt(moduleGroup);
          _data.writeBoolean(outcome);
          _data.writeInt(uid);
          _data.writeString(swComponent);
          _data.writeString(logMessage);
          _data.writeInt(userId);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void RedactedAuditLoggerAsUser(
          int severityGrade,
          int moduleGroup,
          boolean outcome,
          int uid,
          String swComponent,
          String logMessage,
          String redactedLogMessage,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(severityGrade);
          _data.writeInt(moduleGroup);
          _data.writeBoolean(outcome);
          _data.writeInt(uid);
          _data.writeString(swComponent);
          _data.writeString(logMessage);
          _data.writeString(redactedLogMessage);
          _data.writeInt(userId);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void AuditLoggerPrivileged(
          int severityGrade,
          int moduleGroup,
          boolean outcome,
          int pid,
          String swComponent,
          String logMessage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(severityGrade);
          _data.writeInt(moduleGroup);
          _data.writeBoolean(outcome);
          _data.writeInt(pid);
          _data.writeString(swComponent);
          _data.writeString(logMessage);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void RedactedAuditLoggerPrivileged(
          int severityGrade,
          int moduleGroup,
          boolean outcome,
          int pid,
          String swComponent,
          String logMessage,
          String redactedLogMessage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(severityGrade);
          _data.writeInt(moduleGroup);
          _data.writeBoolean(outcome);
          _data.writeInt(pid);
          _data.writeString(swComponent);
          _data.writeString(logMessage);
          _data.writeString(redactedLogMessage);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void AuditLoggerPrivilegedAsUser(
          int severityGrade,
          int moduleGroup,
          boolean outcome,
          int pid,
          String swComponent,
          String logMessage,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(severityGrade);
          _data.writeInt(moduleGroup);
          _data.writeBoolean(outcome);
          _data.writeInt(pid);
          _data.writeString(swComponent);
          _data.writeString(logMessage);
          _data.writeInt(userId);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void RedactedAuditLoggerPrivilegedAsUser(
          int severityGrade,
          int moduleGroup,
          boolean outcome,
          int pid,
          String swComponent,
          String logMessage,
          String redactedLogMessage,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(severityGrade);
          _data.writeInt(moduleGroup);
          _data.writeBoolean(outcome);
          _data.writeInt(pid);
          _data.writeString(swComponent);
          _data.writeString(logMessage);
          _data.writeString(redactedLogMessage);
          _data.writeInt(userId);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isAuditLogEnabledAsUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void notifyCertificateFailureAsUser(
          String module, String msg, boolean showMsg, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(module);
          _data.writeString(msg);
          _data.writeBoolean(showMsg);
          _data.writeInt(userId);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isRevocationCheckEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isOcspCheckEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isCaCertificateTrustedAsUser(
          byte[] certBytes, boolean showMsg, boolean checkTrusted, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeByteArray(certBytes);
          _data.writeBoolean(showMsg);
          _data.writeBoolean(checkTrusted);
          _data.writeInt(userId);
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isCertificateTrustedUntrustedEnabledAsUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(57, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isCertificateValidationAtInstallEnabledAsUser(int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(58, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isAudioRecordAllowed(boolean showMsg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(showMsg);
          this.mRemote.transact(59, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isVideoRecordAllowed(boolean showMsg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(showMsg);
          this.mRemote.transact(60, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void notifyCertificateRemovedAsUser(String subject, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(subject);
          _data.writeInt(userId);
          this.mRemote.transact(61, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public int validateCertificateAtInstallAsUser(byte[] certBytes, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeByteArray(certBytes);
          _data.writeInt(userId);
          this.mRemote.transact(62, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isClipboardShareAllowed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(63, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isBlockSmsWithStorageEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(64, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isBlockMmsWithStorageEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(65, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void storeBlockedSmsMms(
          boolean isSms,
          byte[] pdu,
          String srcAddress,
          int sendType,
          String timeStamp,
          String messageId,
          String subId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(isSms);
          _data.writeByteArray(pdu);
          _data.writeString(srcAddress);
          _data.writeInt(sendType);
          _data.writeString(timeStamp);
          _data.writeString(messageId);
          _data.writeString(subId);
          this.mRemote.transact(66, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isWapPushAllowed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(67, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isAccountRemovalAllowed(String type, String account, boolean showMsg)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(type);
          _data.writeString(account);
          _data.writeBoolean(showMsg);
          this.mRemote.transact(68, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public String getApplicationNameFromDb(String packageName, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(69, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public String getApplicationNameForComponent(
          String componentName, String packageName, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(componentName);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(70, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isAnyApplicationNameChangedAsUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(71, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isCopyContactToSimAllowed(int message) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(message);
          this.mRemote.transact(72, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getProKioskState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(73, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getProKioskNotificationMessagesState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(74, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public int getProKioskHideNotificationMessages() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(75, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public int getVolumeControlStream() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(76, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getToastEnabledState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(77, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getToastShowPackageNameState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(78, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public int getSensorDisabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(79, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getVolumePanelEnabledState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(80, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getVolumeButtonRotationState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(81, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getToastGravityEnabledState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(82, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public int getToastGravity() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(83, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public int getToastGravityXOffset() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(84, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public int getToastGravityYOffset() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(85, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public int getKeyboardMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(86, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getWifiState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(87, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getUsbNetStateInternal() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(88, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public String getUsbNetAddress(int addressType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(addressType);
          this.mRemote.transact(89, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public Bundle getApplicationRestrictions(String packageName, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(90, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getExtendedCallInfoState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(91, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isAllowedMamPackage(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(92, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isKnoxBluetoothEnabled(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(93, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isPackageAllowedToAccessExternalSdcard(int userId, int packageUid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(packageUid);
          this.mRemote.transact(94, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public byte[] ucmGetCertificateChain(String alias) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(alias);
          this.mRemote.transact(95, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public byte[] ucmDecrypt(String uri, byte[] data, String algo, Bundle params)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(uri);
          _data.writeByteArray(data);
          _data.writeString(algo);
          _data.writeTypedObject(params, 0);
          this.mRemote.transact(96, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public byte[] ucmEncrypt(String uri, byte[] data, String algo, Bundle params)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(uri);
          _data.writeByteArray(data);
          _data.writeString(algo);
          _data.writeTypedObject(params, 0);
          this.mRemote.transact(97, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public byte[] ucmSign(String uri, byte[] data, String algo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(uri);
          _data.writeByteArray(data);
          _data.writeString(algo);
          this.mRemote.transact(98, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public byte[] ucmMac(String uri, byte[] data, String algo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(uri);
          _data.writeByteArray(data);
          _data.writeString(algo);
          this.mRemote.transact(99, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public List<String> getELMPermissions(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(100, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean getAddHomeShorcutRequested() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(101, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean registerSystemUICallback(ISystemUIAdapterCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(102, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isMockLocationEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          this.mRemote.transact(103, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isGoogleAccountsAutoSyncAllowedAsUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(104, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isPackageInAvrWhitelist(int packageUid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(packageUid);
          this.mRemote.transact(105, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public void notifyPasswordPolicyOneLockChanged(boolean enabled, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeBoolean(enabled);
          _data.writeInt(userId);
          this.mRemote.transact(106, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isIncomingSmsAllowedFromSimSlot(int slotNum) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(slotNum);
          this.mRemote.transact(107, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isOutgoingSmsAllowedFromSimSlot(int slotNum) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(slotNum);
          this.mRemote.transact(108, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isMmsAllowedFromSimSlot(int slotNum) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(slotNum);
          this.mRemote.transact(109, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isOutgoingCallAllowedFromSimSlot(int simSlot) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(simSlot);
          this.mRemote.transact(110, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isIncomingCallAllowedFromSimSlot(int simSlot) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(simSlot);
          this.mRemote.transact(111, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean canOutgoingCall(String dialNumber) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(dialNumber);
          this.mRemote.transact(112, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean canIncomingCall(String dialNumber) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeString(dialNumber);
          this.mRemote.transact(113, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.sec.enterprise.IEDMProxy
      public boolean isDataAllowedFromSimSlot(int simSlot) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEDMProxy.DESCRIPTOR);
          _data.writeInt(simSlot);
          this.mRemote.transact(114, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 113;
    }
  }
}
