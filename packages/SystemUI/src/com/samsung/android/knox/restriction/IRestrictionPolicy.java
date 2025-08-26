package com.samsung.android.knox.restriction;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IRestrictionPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.restriction.IRestrictionPolicy";

    boolean addNewAdminActivationAppWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean allowActivationLock(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowAirplaneMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowAudioRecord(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowBackgroundProcessLimit(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowClipboardShare(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowDataSaving(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowDeveloperMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowFaceRecognitionEvenCameraBlocked(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowFactoryReset(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowFastEncryption(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowFirmwareAutoUpdate(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowFirmwareRecovery(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowGoogleAccountsAutoSync(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowGoogleCrashReport(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowIntelligenceOnlineProcessing(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowKillingActivitiesOnLeave(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowLocalContactStorage(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowLockScreenView(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    boolean allowOTAUpgrade(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowPowerOff(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowPowerSavingMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowSDCardMove(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowSDCardWrite(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowSVoice(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowSafeMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowScreenPinning(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowSettingsChanges(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowShareList(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowSmartClipMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowStatusBarExpansion(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowStopSystemApp(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowUsbHostStorage(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowUserMobileDataLimit(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowVideoRecord(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowVpn(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowWallpaperChange(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowWiFiSharing(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowWifiDirect(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean checkAdminActivationEnabled(int i, String str) throws RemoteException;

    boolean checkIfRestrictionWasSetByKC(String str) throws RemoteException;

    boolean checkPackageSource(int i, String str) throws RemoteException;

    boolean clearNewAdminActivationAppWhiteList(ContextInfo contextInfo) throws RemoteException;

    boolean disableConstrainedState(ContextInfo contextInfo) throws RemoteException;

    boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) throws RemoteException;

    boolean enableODETrustedBootVerification(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableWearablePolicy(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    List<String> getAllowedFOTAInfo(ContextInfo contextInfo) throws RemoteException;

    String getAllowedFOTAVersion(ContextInfo contextInfo) throws RemoteException;

    int getCCModeState(ContextInfo contextInfo) throws RemoteException;

    int getConstrainedState() throws RemoteException;

    String getKcActionDisabledText() throws RemoteException;

    String getMultiSimPolicy() throws RemoteException;

    List<String> getNewAdminActivationAppWhiteList(ContextInfo contextInfo) throws RemoteException;

    int getUsbExceptionList() throws RemoteException;

    boolean isActivationLockAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isAirplaneModeAllowed(boolean z) throws RemoteException;

    boolean isAudioRecordAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isBackgroundDataEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isBackgroundProcessLimitAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isBackupAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isBluetoothTetheringEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isCCModeEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isCCModeSupported(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isCameraEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isCellularDataAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isClipboardAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isClipboardAllowedAsUser(boolean z, int i) throws RemoteException;

    boolean isClipboardShareAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isClipboardShareAllowedAsUser(int i) throws RemoteException;

    boolean isDataSavingAllowed() throws RemoteException;

    boolean isDeveloperModeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isFaceRecognitionAllowedEvenCameraBlocked(ContextInfo contextInfo) throws RemoteException;

    boolean isFactoryResetAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isFastEncryptionAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isFirmwareAutoUpdateAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isFirmwareRecoveryAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isGoogleAccountsAutoSyncAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) throws RemoteException;

    boolean isGoogleCrashReportAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isGoogleCrashReportAllowedAsUser(int i) throws RemoteException;

    boolean isHeadphoneEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isHomeKeyEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isIntelligenceOnlineProcessingAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isIrisCameraEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isKillingActivitiesOnLeaveAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isKnoxDelegationEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isLocalContactStorageAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isLockScreenEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isLockScreenViewAllowed(ContextInfo contextInfo, int i) throws RemoteException;

    boolean isMicrophoneEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isMicrophoneEnabledAsUser(boolean z, int i) throws RemoteException;

    boolean isMockLocationEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isNewAdminActivationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isNewAdminInstallationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isNewAdminInstallationEnabledAsUser(int i, boolean z) throws RemoteException;

    boolean isNonMarketAppAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isNonTrustedAppInstallBlocked(ContextInfo contextInfo) throws RemoteException;

    boolean isNonTrustedAppInstallBlockedAsUser(int i) throws RemoteException;

    boolean isODETrustedBootVerificationEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isOTAUpgradeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isPowerOffAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isPowerSavingModeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isSDCardMoveAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isSDCardWriteAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isSVoiceAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isSVoiceAllowedAsUser(boolean z, int i) throws RemoteException;

    boolean isSafeModeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isScreenCaptureEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isScreenCaptureEnabledEx(int i, boolean z) throws RemoteException;

    boolean isScreenCaptureEnabledInternal(boolean z) throws RemoteException;

    boolean isScreenPinningAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isSdCardEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isSettingsChangesAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isSettingsChangesAllowedAsUser(boolean z, int i) throws RemoteException;

    boolean isShareListAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isShareListAllowedAsUser(int i, boolean z) throws RemoteException;

    boolean isSmartClipModeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isSmartClipModeAllowedInternal(boolean z) throws RemoteException;

    boolean isStatusBarExpansionAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isStatusBarExpansionAllowedAsUser(boolean z, int i) throws RemoteException;

    boolean isStopSystemAppAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isTetheringEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isUsbDebuggingEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isUsbHostStorageAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isUsbKiesAvailable(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isUsbMassStorageEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isUsbMediaPlayerAvailable(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isUsbTetheringEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isUseSecureKeypadEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isUserMobileDataLimitAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isVideoRecordAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isVpnAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isWallpaperChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isWearablePolicyEnabled(ContextInfo contextInfo, int i) throws RemoteException;

    boolean isWiFiSharingEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isWifiDirectAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isWifiTetheringEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean preventNewAdminActivation(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean preventNewAdminInstallation(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setAllowNonMarketApps(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setAllowedFOTAVersion(ContextInfo contextInfo, String str, Bundle bundle, boolean z) throws RemoteException;

    boolean setBackgroundData(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setBackup(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setBluetoothTethering(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setCCMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setCCModeOnlyForCallerSystem(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setCamera(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setCellularData(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setClipboardEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setHeadphoneState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setHomeKeyState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setIrisCameraState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setKnoxDelegationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setLockScreenState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setMicrophoneState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setMockLocation(ContextInfo contextInfo, boolean z) throws RemoteException;

    void setMultiSimPolicy(ContextInfo contextInfo, int i, int i2, String[] strArr, String[] strArr2, String[] strArr3) throws RemoteException;

    boolean setNonTrustedAppInstallBlock(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setScreenCapture(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setSdCardState(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setTethering(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setUsbDebuggingEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setUsbExceptionList(ContextInfo contextInfo, int i) throws RemoteException;

    boolean setUsbKiesAvailability(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setUsbMassStorage(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setUsbMediaPlayerAvailability(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setUsbTethering(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setUseSecureKeypad(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setWifiTethering(ContextInfo contextInfo, boolean z) throws RemoteException;

    void showRestrictionToast(String str) throws RemoteException;

    void systemReady(int i) throws RemoteException;

    void updateUserRestrictionsByKC(String str, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IRestrictionPolicy {
        public static final int TRANSACTION_addNewAdminActivationAppWhiteList = 120;
        public static final int TRANSACTION_allowActivationLock = 153;
        public static final int TRANSACTION_allowAirplaneMode = 97;
        public static final int TRANSACTION_allowAudioRecord = 59;
        public static final int TRANSACTION_allowBackgroundProcessLimit = 67;
        public static final int TRANSACTION_allowClipboardShare = 73;
        public static final int TRANSACTION_allowDataSaving = 135;
        public static final int TRANSACTION_allowDeveloperMode = 95;
        public static final int TRANSACTION_allowFaceRecognitionEvenCameraBlocked = 142;
        public static final int TRANSACTION_allowFactoryReset = 31;
        public static final int TRANSACTION_allowFastEncryption = 108;
        public static final int TRANSACTION_allowFirmwareAutoUpdate = 102;
        public static final int TRANSACTION_allowFirmwareRecovery = 92;
        public static final int TRANSACTION_allowGoogleAccountsAutoSync = 99;
        public static final int TRANSACTION_allowGoogleCrashReport = 50;
        public static final int TRANSACTION_allowIntelligenceOnlineProcessing = 148;
        public static final int TRANSACTION_allowKillingActivitiesOnLeave = 69;
        public static final int TRANSACTION_allowLocalContactStorage = 144;
        public static final int TRANSACTION_allowLockScreenView = 88;
        public static final int TRANSACTION_allowOTAUpgrade = 46;
        public static final int TRANSACTION_allowPowerOff = 57;
        public static final int TRANSACTION_allowPowerSavingMode = 137;
        public static final int TRANSACTION_allowSDCardMove = 106;
        public static final int TRANSACTION_allowSDCardWrite = 48;
        public static final int TRANSACTION_allowSVoice = 76;
        public static final int TRANSACTION_allowSafeMode = 86;
        public static final int TRANSACTION_allowScreenPinning = 127;
        public static final int TRANSACTION_allowSettingsChanges = 39;
        public static final int TRANSACTION_allowShareList = 81;
        public static final int TRANSACTION_allowSmartClipMode = 124;
        public static final int TRANSACTION_allowStatusBarExpansion = 55;
        public static final int TRANSACTION_allowStopSystemApp = 63;
        public static final int TRANSACTION_allowUsbHostStorage = 79;
        public static final int TRANSACTION_allowUserMobileDataLimit = 71;
        public static final int TRANSACTION_allowVideoRecord = 61;
        public static final int TRANSACTION_allowVpn = 44;
        public static final int TRANSACTION_allowWallpaperChange = 53;
        public static final int TRANSACTION_allowWiFiSharing = 172;
        public static final int TRANSACTION_allowWifiDirect = 65;
        public static final int TRANSACTION_checkAdminActivationEnabled = 164;
        public static final int TRANSACTION_checkIfRestrictionWasSetByKC = 150;
        public static final int TRANSACTION_checkPackageSource = 163;
        public static final int TRANSACTION_clearNewAdminActivationAppWhiteList = 119;
        public static final int TRANSACTION_disableConstrainedState = 168;
        public static final int TRANSACTION_enableConstrainedState = 167;
        public static final int TRANSACTION_enableODETrustedBootVerification = 113;
        public static final int TRANSACTION_enableWearablePolicy = 128;
        public static final int TRANSACTION_getAllowedFOTAInfo = 132;
        public static final int TRANSACTION_getAllowedFOTAVersion = 131;
        public static final int TRANSACTION_getCCModeState = 125;
        public static final int TRANSACTION_getConstrainedState = 169;
        public static final int TRANSACTION_getKcActionDisabledText = 151;
        public static final int TRANSACTION_getMultiSimPolicy = 171;
        public static final int TRANSACTION_getNewAdminActivationAppWhiteList = 121;
        public static final int TRANSACTION_getUsbExceptionList = 140;
        public static final int TRANSACTION_isActivationLockAllowed = 154;
        public static final int TRANSACTION_isAirplaneModeAllowed = 98;
        public static final int TRANSACTION_isAudioRecordAllowed = 60;
        public static final int TRANSACTION_isBackgroundDataEnabled = 36;
        public static final int TRANSACTION_isBackgroundProcessLimitAllowed = 68;
        public static final int TRANSACTION_isBackupAllowed = 27;
        public static final int TRANSACTION_isBluetoothTetheringEnabled = 9;
        public static final int TRANSACTION_isCCModeEnabled = 162;
        public static final int TRANSACTION_isCCModeSupported = 112;
        public static final int TRANSACTION_isCameraEnabled = 2;
        public static final int TRANSACTION_isCellularDataAllowed = 38;
        public static final int TRANSACTION_isClipboardAllowed = 29;
        public static final int TRANSACTION_isClipboardAllowedAsUser = 30;
        public static final int TRANSACTION_isClipboardShareAllowed = 74;
        public static final int TRANSACTION_isClipboardShareAllowedAsUser = 75;
        public static final int TRANSACTION_isDataSavingAllowed = 136;
        public static final int TRANSACTION_isDeveloperModeAllowed = 96;
        public static final int TRANSACTION_isFaceRecognitionAllowedEvenCameraBlocked = 143;
        public static final int TRANSACTION_isFactoryResetAllowed = 32;
        public static final int TRANSACTION_isFastEncryptionAllowed = 109;
        public static final int TRANSACTION_isFirmwareAutoUpdateAllowed = 103;
        public static final int TRANSACTION_isFirmwareRecoveryAllowed = 93;
        public static final int TRANSACTION_isGoogleAccountsAutoSyncAllowed = 100;
        public static final int TRANSACTION_isGoogleAccountsAutoSyncAllowedAsUser = 101;
        public static final int TRANSACTION_isGoogleCrashReportAllowed = 51;
        public static final int TRANSACTION_isGoogleCrashReportAllowedAsUser = 52;
        public static final int TRANSACTION_isHeadphoneEnabled = 105;
        public static final int TRANSACTION_isHomeKeyEnabled = 34;
        public static final int TRANSACTION_isIntelligenceOnlineProcessingAllowed = 149;
        public static final int TRANSACTION_isIrisCameraEnabled = 133;
        public static final int TRANSACTION_isKillingActivitiesOnLeaveAllowed = 70;
        public static final int TRANSACTION_isKnoxDelegationEnabled = 147;
        public static final int TRANSACTION_isLocalContactStorageAllowed = 145;
        public static final int TRANSACTION_isLockScreenEnabled = 91;
        public static final int TRANSACTION_isLockScreenViewAllowed = 89;
        public static final int TRANSACTION_isMicrophoneEnabled = 4;
        public static final int TRANSACTION_isMicrophoneEnabledAsUser = 5;
        public static final int TRANSACTION_isMockLocationEnabled = 25;
        public static final int TRANSACTION_isNewAdminActivationEnabled = 118;
        public static final int TRANSACTION_isNewAdminInstallationEnabled = 116;
        public static final int TRANSACTION_isNewAdminInstallationEnabledAsUser = 165;
        public static final int TRANSACTION_isNonMarketAppAllowed = 43;
        public static final int TRANSACTION_isNonTrustedAppInstallBlocked = 155;
        public static final int TRANSACTION_isNonTrustedAppInstallBlockedAsUser = 156;
        public static final int TRANSACTION_isODETrustedBootVerificationEnabled = 114;
        public static final int TRANSACTION_isOTAUpgradeAllowed = 47;
        public static final int TRANSACTION_isPowerOffAllowed = 58;
        public static final int TRANSACTION_isPowerSavingModeAllowed = 138;
        public static final int TRANSACTION_isSDCardMoveAllowed = 107;
        public static final int TRANSACTION_isSDCardWriteAllowed = 49;
        public static final int TRANSACTION_isSVoiceAllowed = 77;
        public static final int TRANSACTION_isSVoiceAllowedAsUser = 78;
        public static final int TRANSACTION_isSafeModeAllowed = 87;
        public static final int TRANSACTION_isScreenCaptureEnabled = 21;
        public static final int TRANSACTION_isScreenCaptureEnabledEx = 22;
        public static final int TRANSACTION_isScreenCaptureEnabledInternal = 23;
        public static final int TRANSACTION_isScreenPinningAllowed = 126;
        public static final int TRANSACTION_isSdCardEnabled = 7;
        public static final int TRANSACTION_isSettingsChangesAllowed = 40;
        public static final int TRANSACTION_isSettingsChangesAllowedAsUser = 41;
        public static final int TRANSACTION_isShareListAllowed = 82;
        public static final int TRANSACTION_isShareListAllowedAsUser = 83;
        public static final int TRANSACTION_isSmartClipModeAllowed = 122;
        public static final int TRANSACTION_isSmartClipModeAllowedInternal = 123;
        public static final int TRANSACTION_isStatusBarExpansionAllowed = 56;
        public static final int TRANSACTION_isStatusBarExpansionAllowedAsUser = 94;
        public static final int TRANSACTION_isStopSystemAppAllowed = 64;
        public static final int TRANSACTION_isTetheringEnabled = 15;
        public static final int TRANSACTION_isUsbDebuggingEnabled = 17;
        public static final int TRANSACTION_isUsbHostStorageAllowed = 80;
        public static final int TRANSACTION_isUsbKiesAvailable = 158;
        public static final int TRANSACTION_isUsbMassStorageEnabled = 157;
        public static final int TRANSACTION_isUsbMediaPlayerAvailable = 19;
        public static final int TRANSACTION_isUsbTetheringEnabled = 11;
        public static final int TRANSACTION_isUseSecureKeypadEnabled = 85;
        public static final int TRANSACTION_isUserMobileDataLimitAllowed = 72;
        public static final int TRANSACTION_isVideoRecordAllowed = 62;
        public static final int TRANSACTION_isVpnAllowed = 45;
        public static final int TRANSACTION_isWallpaperChangeAllowed = 54;
        public static final int TRANSACTION_isWearablePolicyEnabled = 129;
        public static final int TRANSACTION_isWiFiSharingEnabled = 173;
        public static final int TRANSACTION_isWifiDirectAllowed = 66;
        public static final int TRANSACTION_isWifiTetheringEnabled = 13;
        public static final int TRANSACTION_preventNewAdminActivation = 117;
        public static final int TRANSACTION_preventNewAdminInstallation = 115;
        public static final int TRANSACTION_setAllowNonMarketApps = 42;
        public static final int TRANSACTION_setAllowedFOTAVersion = 130;
        public static final int TRANSACTION_setBackgroundData = 35;
        public static final int TRANSACTION_setBackup = 26;
        public static final int TRANSACTION_setBluetoothTethering = 8;
        public static final int TRANSACTION_setCCMode = 110;
        public static final int TRANSACTION_setCCModeOnlyForCallerSystem = 111;
        public static final int TRANSACTION_setCamera = 1;
        public static final int TRANSACTION_setCellularData = 37;
        public static final int TRANSACTION_setClipboardEnabled = 28;
        public static final int TRANSACTION_setHeadphoneState = 104;
        public static final int TRANSACTION_setHomeKeyState = 33;
        public static final int TRANSACTION_setIrisCameraState = 134;
        public static final int TRANSACTION_setKnoxDelegationEnabled = 146;
        public static final int TRANSACTION_setLockScreenState = 90;
        public static final int TRANSACTION_setMicrophoneState = 3;
        public static final int TRANSACTION_setMockLocation = 24;
        public static final int TRANSACTION_setMultiSimPolicy = 170;
        public static final int TRANSACTION_setNonTrustedAppInstallBlock = 159;
        public static final int TRANSACTION_setScreenCapture = 20;
        public static final int TRANSACTION_setSdCardState = 6;
        public static final int TRANSACTION_setTethering = 14;
        public static final int TRANSACTION_setUsbDebuggingEnabled = 16;
        public static final int TRANSACTION_setUsbExceptionList = 139;
        public static final int TRANSACTION_setUsbKiesAvailability = 160;
        public static final int TRANSACTION_setUsbMassStorage = 161;
        public static final int TRANSACTION_setUsbMediaPlayerAvailability = 18;
        public static final int TRANSACTION_setUsbTethering = 10;
        public static final int TRANSACTION_setUseSecureKeypad = 84;
        public static final int TRANSACTION_setWifiTethering = 12;
        public static final int TRANSACTION_showRestrictionToast = 166;
        public static final int TRANSACTION_systemReady = 141;
        public static final int TRANSACTION_updateUserRestrictionsByKC = 152;

        class Proxy implements IRestrictionPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean addNewAdminActivationAppWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowActivationLock(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowAirplaneMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowAudioRecord(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowBackgroundProcessLimit(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowClipboardShare(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowDataSaving(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowDeveloperMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowFaceRecognitionEvenCameraBlocked(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowFactoryReset(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowFastEncryption(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowFirmwareAutoUpdate(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowFirmwareRecovery(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowGoogleAccountsAutoSync(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowGoogleCrashReport(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowIntelligenceOnlineProcessing(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowKillingActivitiesOnLeave(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowLocalContactStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowLockScreenView(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowOTAUpgrade(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowPowerOff(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowPowerSavingMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowSDCardMove(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowSDCardWrite(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowSVoice(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowSafeMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowScreenPinning(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowSettingsChanges(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowShareList(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowSmartClipMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowStatusBarExpansion(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowStopSystemApp(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowUsbHostStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowUserMobileDataLimit(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowVideoRecord(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowVpn(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowWallpaperChange(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowWiFiSharing(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean allowWifiDirect(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean checkAdminActivationEnabled(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean checkIfRestrictionWasSetByKC(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean checkPackageSource(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(163, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean clearNewAdminActivationAppWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean disableConstrainedState(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(167, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean enableODETrustedBootVerification(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean enableWearablePolicy(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public List<String> getAllowedFOTAInfo(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public String getAllowedFOTAVersion(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public int getCCModeState(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public int getConstrainedState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(169, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IRestrictionPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public String getKcActionDisabledText() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public String getMultiSimPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public List<String> getNewAdminActivationAppWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public int getUsbExceptionList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isActivationLockAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isAirplaneModeAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isAudioRecordAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isBackgroundDataEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isBackgroundProcessLimitAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isBackupAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isBluetoothTetheringEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isCCModeEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isCCModeSupported(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isCameraEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isCellularDataAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isClipboardAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isClipboardAllowedAsUser(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isClipboardShareAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isClipboardShareAllowedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isDataSavingAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isDeveloperModeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isFaceRecognitionAllowedEvenCameraBlocked(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isFactoryResetAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isFastEncryptionAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isFirmwareAutoUpdateAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isFirmwareRecoveryAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isGoogleAccountsAutoSyncAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isGoogleCrashReportAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isGoogleCrashReportAllowedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isHeadphoneEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isHomeKeyEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isIntelligenceOnlineProcessingAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isIrisCameraEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isKillingActivitiesOnLeaveAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isKnoxDelegationEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isLocalContactStorageAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isLockScreenEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isLockScreenViewAllowed(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isMicrophoneEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isMicrophoneEnabledAsUser(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isMockLocationEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isNewAdminActivationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isNewAdminInstallationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isNewAdminInstallationEnabledAsUser(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isNonMarketAppAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isNonTrustedAppInstallBlocked(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isNonTrustedAppInstallBlockedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isODETrustedBootVerificationEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isOTAUpgradeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isPowerOffAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isPowerSavingModeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSDCardMoveAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSDCardWriteAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSVoiceAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSVoiceAllowedAsUser(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSafeModeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isScreenCaptureEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isScreenCaptureEnabledEx(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isScreenCaptureEnabledInternal(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isScreenPinningAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSdCardEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSettingsChangesAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSettingsChangesAllowedAsUser(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isShareListAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isShareListAllowedAsUser(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSmartClipModeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isSmartClipModeAllowedInternal(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isStatusBarExpansionAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isStatusBarExpansionAllowedAsUser(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isStopSystemAppAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isTetheringEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isUsbDebuggingEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isUsbHostStorageAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isUsbKiesAvailable(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isUsbMassStorageEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isUsbMediaPlayerAvailable(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isUsbTetheringEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isUseSecureKeypadEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isUserMobileDataLimitAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isVideoRecordAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isVpnAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isWallpaperChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isWearablePolicyEnabled(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isWiFiSharingEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(173, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isWifiDirectAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean isWifiTetheringEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean preventNewAdminActivation(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean preventNewAdminInstallation(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setAllowNonMarketApps(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setAllowedFOTAVersion(ContextInfo contextInfo, String str, Bundle bundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setBackgroundData(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setBackup(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setBluetoothTethering(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setCCMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setCCModeOnlyForCallerSystem(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setCamera(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setCellularData(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setClipboardEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setHeadphoneState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setHomeKeyState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setIrisCameraState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setKnoxDelegationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setLockScreenState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setMicrophoneState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setMockLocation(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public void setMultiSimPolicy(ContextInfo contextInfo, int i, int i2, String[] strArr, String[] strArr2, String[] strArr3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    parcelObtain.writeStringArray(strArr3);
                    this.mRemote.transact(170, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setNonTrustedAppInstallBlock(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setScreenCapture(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setSdCardState(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setTethering(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setUsbDebuggingEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setUsbExceptionList(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setUsbKiesAvailability(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setUsbMassStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setUsbMediaPlayerAvailability(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setUsbTethering(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setUseSecureKeypad(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public boolean setWifiTethering(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public void showRestrictionToast(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public void systemReady(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public void updateUserRestrictionsByKC(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IRestrictionPolicy.DESCRIPTOR);
        }

        public static IRestrictionPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRestrictionPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRestrictionPolicy)) ? new Proxy(iBinder) : (IRestrictionPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRestrictionPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRestrictionPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean camera = setCamera(contextInfo, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(camera);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsCameraEnabled = isCameraEnabled(contextInfo2, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCameraEnabled);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean microphoneState = setMicrophoneState(contextInfo3, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(microphoneState);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsMicrophoneEnabled = isMicrophoneEnabled(contextInfo4, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMicrophoneEnabled);
                    return true;
                case 5:
                    boolean z5 = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsMicrophoneEnabledAsUser = isMicrophoneEnabledAsUser(z5, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMicrophoneEnabledAsUser);
                    return true;
                case 6:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean sdCardState = setSdCardState(contextInfo5, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdCardState);
                    return true;
                case 7:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSdCardEnabled = isSdCardEnabled(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSdCardEnabled);
                    return true;
                case 8:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothTethering = setBluetoothTethering(contextInfo7, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothTethering);
                    return true;
                case 9:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothTetheringEnabled = isBluetoothTetheringEnabled(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothTetheringEnabled);
                    return true;
                case 10:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbTethering = setUsbTethering(contextInfo9, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbTethering);
                    return true;
                case 11:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsUsbTetheringEnabled = isUsbTetheringEnabled(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbTetheringEnabled);
                    return true;
                case 12:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiTethering = setWifiTethering(contextInfo11, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiTethering);
                    return true;
                case 13:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiTetheringEnabled = isWifiTetheringEnabled(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiTetheringEnabled);
                    return true;
                case 14:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean tethering = setTethering(contextInfo13, z10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tethering);
                    return true;
                case 15:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsTetheringEnabled = isTetheringEnabled(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTetheringEnabled);
                    return true;
                case 16:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbDebuggingEnabled = setUsbDebuggingEnabled(contextInfo15, z11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbDebuggingEnabled);
                    return true;
                case 17:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsUsbDebuggingEnabled = isUsbDebuggingEnabled(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbDebuggingEnabled);
                    return true;
                case 18:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbMediaPlayerAvailability = setUsbMediaPlayerAvailability(contextInfo17, z12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbMediaPlayerAvailability);
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUsbMediaPlayerAvailable = isUsbMediaPlayerAvailable(contextInfo18, z13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbMediaPlayerAvailable);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean screenCapture = setScreenCapture(contextInfo19, z14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(screenCapture);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsScreenCaptureEnabled = isScreenCaptureEnabled(contextInfo20, z15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenCaptureEnabled);
                    return true;
                case 22:
                    int i4 = parcel.readInt();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsScreenCaptureEnabledEx = isScreenCaptureEnabledEx(i4, z16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenCaptureEnabledEx);
                    return true;
                case 23:
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsScreenCaptureEnabledInternal = isScreenCaptureEnabledInternal(z17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenCaptureEnabledInternal);
                    return true;
                case 24:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean mockLocation = setMockLocation(contextInfo21, z18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(mockLocation);
                    return true;
                case 25:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsMockLocationEnabled = isMockLocationEnabled(contextInfo22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMockLocationEnabled);
                    return true;
                case 26:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean backup = setBackup(contextInfo23, z19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backup);
                    return true;
                case 27:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsBackupAllowed = isBackupAllowed(contextInfo24, z20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBackupAllowed);
                    return true;
                case 28:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean clipboardEnabled = setClipboardEnabled(contextInfo25, z21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clipboardEnabled);
                    return true;
                case 29:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsClipboardAllowed = isClipboardAllowed(contextInfo26, z22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsClipboardAllowed);
                    return true;
                case 30:
                    boolean z23 = parcel.readBoolean();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsClipboardAllowedAsUser = isClipboardAllowedAsUser(z23, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsClipboardAllowedAsUser);
                    return true;
                case 31:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowFactoryReset = allowFactoryReset(contextInfo27, z24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowFactoryReset);
                    return true;
                case 32:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsFactoryResetAllowed = isFactoryResetAllowed(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFactoryResetAllowed);
                    return true;
                case 33:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean homeKeyState = setHomeKeyState(contextInfo29, z25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(homeKeyState);
                    return true;
                case 34:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsHomeKeyEnabled = isHomeKeyEnabled(contextInfo30, z26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHomeKeyEnabled);
                    return true;
                case 35:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean backgroundData = setBackgroundData(contextInfo31, z27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backgroundData);
                    return true;
                case 36:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBackgroundDataEnabled = isBackgroundDataEnabled(contextInfo32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBackgroundDataEnabled);
                    return true;
                case 37:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean cellularData = setCellularData(contextInfo33, z28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cellularData);
                    return true;
                case 38:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCellularDataAllowed = isCellularDataAllowed(contextInfo34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCellularDataAllowed);
                    return true;
                case 39:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowSettingsChanges = allowSettingsChanges(contextInfo35, z29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowSettingsChanges);
                    return true;
                case 40:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsSettingsChangesAllowed = isSettingsChangesAllowed(contextInfo36, z30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSettingsChangesAllowed);
                    return true;
                case 41:
                    boolean z31 = parcel.readBoolean();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSettingsChangesAllowedAsUser = isSettingsChangesAllowedAsUser(z31, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSettingsChangesAllowedAsUser);
                    return true;
                case 42:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowNonMarketApps = setAllowNonMarketApps(contextInfo37, z32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowNonMarketApps);
                    return true;
                case 43:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsNonMarketAppAllowed = isNonMarketAppAllowed(contextInfo38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNonMarketAppAllowed);
                    return true;
                case 44:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowVpn = allowVpn(contextInfo39, z33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowVpn);
                    return true;
                case 45:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsVpnAllowed = isVpnAllowed(contextInfo40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVpnAllowed);
                    return true;
                case 46:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowOTAUpgrade = allowOTAUpgrade(contextInfo41, z34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowOTAUpgrade);
                    return true;
                case 47:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsOTAUpgradeAllowed = isOTAUpgradeAllowed(contextInfo42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOTAUpgradeAllowed);
                    return true;
                case 48:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z35 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowSDCardWrite = allowSDCardWrite(contextInfo43, z35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowSDCardWrite);
                    return true;
                case 49:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSDCardWriteAllowed = isSDCardWriteAllowed(contextInfo44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSDCardWriteAllowed);
                    return true;
                case 50:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowGoogleCrashReport = allowGoogleCrashReport(contextInfo45, z36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowGoogleCrashReport);
                    return true;
                case 51:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsGoogleCrashReportAllowed = isGoogleCrashReportAllowed(contextInfo46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGoogleCrashReportAllowed);
                    return true;
                case 52:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsGoogleCrashReportAllowedAsUser = isGoogleCrashReportAllowedAsUser(i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGoogleCrashReportAllowedAsUser);
                    return true;
                case 53:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z37 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowWallpaperChange = allowWallpaperChange(contextInfo47, z37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowWallpaperChange);
                    return true;
                case 54:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z38 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsWallpaperChangeAllowed = isWallpaperChangeAllowed(contextInfo48, z38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWallpaperChangeAllowed);
                    return true;
                case 55:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z39 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowStatusBarExpansion = allowStatusBarExpansion(contextInfo49, z39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowStatusBarExpansion);
                    return true;
                case 56:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z40 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsStatusBarExpansionAllowed = isStatusBarExpansionAllowed(contextInfo50, z40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStatusBarExpansionAllowed);
                    return true;
                case 57:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z41 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowPowerOff = allowPowerOff(contextInfo51, z41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowPowerOff);
                    return true;
                case 58:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z42 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsPowerOffAllowed = isPowerOffAllowed(contextInfo52, z42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPowerOffAllowed);
                    return true;
                case 59:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z43 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowAudioRecord = allowAudioRecord(contextInfo53, z43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowAudioRecord);
                    return true;
                case 60:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z44 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsAudioRecordAllowed = isAudioRecordAllowed(contextInfo54, z44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAudioRecordAllowed);
                    return true;
                case 61:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z45 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowVideoRecord = allowVideoRecord(contextInfo55, z45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowVideoRecord);
                    return true;
                case 62:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z46 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsVideoRecordAllowed = isVideoRecordAllowed(contextInfo56, z46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVideoRecordAllowed);
                    return true;
                case 63:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z47 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowStopSystemApp = allowStopSystemApp(contextInfo57, z47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowStopSystemApp);
                    return true;
                case 64:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsStopSystemAppAllowed = isStopSystemAppAllowed(contextInfo58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStopSystemAppAllowed);
                    return true;
                case 65:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z48 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowWifiDirect = allowWifiDirect(contextInfo59, z48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowWifiDirect);
                    return true;
                case 66:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z49 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiDirectAllowed = isWifiDirectAllowed(contextInfo60, z49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiDirectAllowed);
                    return true;
                case 67:
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z50 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowBackgroundProcessLimit = allowBackgroundProcessLimit(contextInfo61, z50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowBackgroundProcessLimit);
                    return true;
                case 68:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBackgroundProcessLimitAllowed = isBackgroundProcessLimitAllowed(contextInfo62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBackgroundProcessLimitAllowed);
                    return true;
                case 69:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z51 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowKillingActivitiesOnLeave = allowKillingActivitiesOnLeave(contextInfo63, z51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowKillingActivitiesOnLeave);
                    return true;
                case 70:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsKillingActivitiesOnLeaveAllowed = isKillingActivitiesOnLeaveAllowed(contextInfo64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKillingActivitiesOnLeaveAllowed);
                    return true;
                case 71:
                    ContextInfo contextInfo65 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z52 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowUserMobileDataLimit = allowUserMobileDataLimit(contextInfo65, z52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowUserMobileDataLimit);
                    return true;
                case 72:
                    ContextInfo contextInfo66 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsUserMobileDataLimitAllowed = isUserMobileDataLimitAllowed(contextInfo66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserMobileDataLimitAllowed);
                    return true;
                case 73:
                    ContextInfo contextInfo67 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z53 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowClipboardShare = allowClipboardShare(contextInfo67, z53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowClipboardShare);
                    return true;
                case 74:
                    ContextInfo contextInfo68 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsClipboardShareAllowed = isClipboardShareAllowed(contextInfo68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsClipboardShareAllowed);
                    return true;
                case 75:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsClipboardShareAllowedAsUser = isClipboardShareAllowedAsUser(i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsClipboardShareAllowedAsUser);
                    return true;
                case 76:
                    ContextInfo contextInfo69 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z54 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowSVoice = allowSVoice(contextInfo69, z54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowSVoice);
                    return true;
                case 77:
                    ContextInfo contextInfo70 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z55 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsSVoiceAllowed = isSVoiceAllowed(contextInfo70, z55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSVoiceAllowed);
                    return true;
                case 78:
                    boolean z56 = parcel.readBoolean();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSVoiceAllowedAsUser = isSVoiceAllowedAsUser(z56, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSVoiceAllowedAsUser);
                    return true;
                case 79:
                    ContextInfo contextInfo71 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z57 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowUsbHostStorage = allowUsbHostStorage(contextInfo71, z57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowUsbHostStorage);
                    return true;
                case 80:
                    ContextInfo contextInfo72 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z58 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUsbHostStorageAllowed = isUsbHostStorageAllowed(contextInfo72, z58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbHostStorageAllowed);
                    return true;
                case 81:
                    ContextInfo contextInfo73 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z59 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowShareList = allowShareList(contextInfo73, z59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowShareList);
                    return true;
                case 82:
                    ContextInfo contextInfo74 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z60 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsShareListAllowed = isShareListAllowed(contextInfo74, z60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsShareListAllowed);
                    return true;
                case 83:
                    int i10 = parcel.readInt();
                    boolean z61 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsShareListAllowedAsUser = isShareListAllowedAsUser(i10, z61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsShareListAllowedAsUser);
                    return true;
                case 84:
                    ContextInfo contextInfo75 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z62 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean useSecureKeypad = setUseSecureKeypad(contextInfo75, z62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(useSecureKeypad);
                    return true;
                case 85:
                    ContextInfo contextInfo76 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsUseSecureKeypadEnabled = isUseSecureKeypadEnabled(contextInfo76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUseSecureKeypadEnabled);
                    return true;
                case 86:
                    ContextInfo contextInfo77 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z63 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowSafeMode = allowSafeMode(contextInfo77, z63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowSafeMode);
                    return true;
                case 87:
                    ContextInfo contextInfo78 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSafeModeAllowed = isSafeModeAllowed(contextInfo78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSafeModeAllowed);
                    return true;
                case 88:
                    ContextInfo contextInfo79 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i11 = parcel.readInt();
                    boolean z64 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowLockScreenView = allowLockScreenView(contextInfo79, i11, z64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowLockScreenView);
                    return true;
                case 89:
                    ContextInfo contextInfo80 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsLockScreenViewAllowed = isLockScreenViewAllowed(contextInfo80, i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLockScreenViewAllowed);
                    return true;
                case 90:
                    ContextInfo contextInfo81 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z65 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean lockScreenState = setLockScreenState(contextInfo81, z65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockScreenState);
                    return true;
                case 91:
                    ContextInfo contextInfo82 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z66 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsLockScreenEnabled = isLockScreenEnabled(contextInfo82, z66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLockScreenEnabled);
                    return true;
                case 92:
                    ContextInfo contextInfo83 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z67 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowFirmwareRecovery = allowFirmwareRecovery(contextInfo83, z67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowFirmwareRecovery);
                    return true;
                case 93:
                    ContextInfo contextInfo84 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z68 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsFirmwareRecoveryAllowed = isFirmwareRecoveryAllowed(contextInfo84, z68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFirmwareRecoveryAllowed);
                    return true;
                case 94:
                    boolean z69 = parcel.readBoolean();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStatusBarExpansionAllowedAsUser = isStatusBarExpansionAllowedAsUser(z69, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStatusBarExpansionAllowedAsUser);
                    return true;
                case 95:
                    ContextInfo contextInfo85 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z70 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowDeveloperMode = allowDeveloperMode(contextInfo85, z70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowDeveloperMode);
                    return true;
                case 96:
                    ContextInfo contextInfo86 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z71 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsDeveloperModeAllowed = isDeveloperModeAllowed(contextInfo86, z71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeveloperModeAllowed);
                    return true;
                case 97:
                    ContextInfo contextInfo87 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z72 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowAirplaneMode = allowAirplaneMode(contextInfo87, z72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowAirplaneMode);
                    return true;
                case 98:
                    boolean z73 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsAirplaneModeAllowed = isAirplaneModeAllowed(z73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAirplaneModeAllowed);
                    return true;
                case 99:
                    ContextInfo contextInfo88 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z74 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowGoogleAccountsAutoSync = allowGoogleAccountsAutoSync(contextInfo88, z74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowGoogleAccountsAutoSync);
                    return true;
                case 100:
                    ContextInfo contextInfo89 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsGoogleAccountsAutoSyncAllowed = isGoogleAccountsAutoSyncAllowed(contextInfo89);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGoogleAccountsAutoSyncAllowed);
                    return true;
                case 101:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsGoogleAccountsAutoSyncAllowedAsUser = isGoogleAccountsAutoSyncAllowedAsUser(i14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGoogleAccountsAutoSyncAllowedAsUser);
                    return true;
                case 102:
                    ContextInfo contextInfo90 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z75 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowFirmwareAutoUpdate = allowFirmwareAutoUpdate(contextInfo90, z75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowFirmwareAutoUpdate);
                    return true;
                case 103:
                    ContextInfo contextInfo91 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z76 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsFirmwareAutoUpdateAllowed = isFirmwareAutoUpdateAllowed(contextInfo91, z76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFirmwareAutoUpdateAllowed);
                    return true;
                case 104:
                    ContextInfo contextInfo92 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z77 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean headphoneState = setHeadphoneState(contextInfo92, z77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(headphoneState);
                    return true;
                case 105:
                    ContextInfo contextInfo93 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z78 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsHeadphoneEnabled = isHeadphoneEnabled(contextInfo93, z78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHeadphoneEnabled);
                    return true;
                case 106:
                    ContextInfo contextInfo94 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z79 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowSDCardMove = allowSDCardMove(contextInfo94, z79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowSDCardMove);
                    return true;
                case 107:
                    ContextInfo contextInfo95 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z80 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsSDCardMoveAllowed = isSDCardMoveAllowed(contextInfo95, z80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSDCardMoveAllowed);
                    return true;
                case 108:
                    ContextInfo contextInfo96 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z81 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowFastEncryption = allowFastEncryption(contextInfo96, z81);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowFastEncryption);
                    return true;
                case 109:
                    ContextInfo contextInfo97 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z82 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsFastEncryptionAllowed = isFastEncryptionAllowed(contextInfo97, z82);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFastEncryptionAllowed);
                    return true;
                case 110:
                    ContextInfo contextInfo98 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z83 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean cCMode = setCCMode(contextInfo98, z83);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cCMode);
                    return true;
                case 111:
                    ContextInfo contextInfo99 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z84 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean cCModeOnlyForCallerSystem = setCCModeOnlyForCallerSystem(contextInfo99, z84);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cCModeOnlyForCallerSystem);
                    return true;
                case 112:
                    ContextInfo contextInfo100 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z85 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsCCModeSupported = isCCModeSupported(contextInfo100, z85);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCCModeSupported);
                    return true;
                case 113:
                    ContextInfo contextInfo101 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z86 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableODETrustedBootVerification = enableODETrustedBootVerification(contextInfo101, z86);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableODETrustedBootVerification);
                    return true;
                case 114:
                    ContextInfo contextInfo102 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsODETrustedBootVerificationEnabled = isODETrustedBootVerificationEnabled(contextInfo102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsODETrustedBootVerificationEnabled);
                    return true;
                case 115:
                    ContextInfo contextInfo103 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z87 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zPreventNewAdminInstallation = preventNewAdminInstallation(contextInfo103, z87);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPreventNewAdminInstallation);
                    return true;
                case 116:
                    ContextInfo contextInfo104 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z88 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsNewAdminInstallationEnabled = isNewAdminInstallationEnabled(contextInfo104, z88);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNewAdminInstallationEnabled);
                    return true;
                case 117:
                    ContextInfo contextInfo105 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z89 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zPreventNewAdminActivation = preventNewAdminActivation(contextInfo105, z89);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPreventNewAdminActivation);
                    return true;
                case 118:
                    ContextInfo contextInfo106 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z90 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsNewAdminActivationEnabled = isNewAdminActivationEnabled(contextInfo106, z90);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNewAdminActivationEnabled);
                    return true;
                case 119:
                    ContextInfo contextInfo107 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearNewAdminActivationAppWhiteList = clearNewAdminActivationAppWhiteList(contextInfo107);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearNewAdminActivationAppWhiteList);
                    return true;
                case 120:
                    ContextInfo contextInfo108 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddNewAdminActivationAppWhiteList = addNewAdminActivationAppWhiteList(contextInfo108, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNewAdminActivationAppWhiteList);
                    return true;
                case 121:
                    ContextInfo contextInfo109 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> newAdminActivationAppWhiteList = getNewAdminActivationAppWhiteList(contextInfo109);
                    parcel2.writeNoException();
                    parcel2.writeStringList(newAdminActivationAppWhiteList);
                    return true;
                case 122:
                    ContextInfo contextInfo110 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSmartClipModeAllowed = isSmartClipModeAllowed(contextInfo110);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSmartClipModeAllowed);
                    return true;
                case 123:
                    boolean z91 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsSmartClipModeAllowedInternal = isSmartClipModeAllowedInternal(z91);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSmartClipModeAllowedInternal);
                    return true;
                case 124:
                    ContextInfo contextInfo111 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z92 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowSmartClipMode = allowSmartClipMode(contextInfo111, z92);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowSmartClipMode);
                    return true;
                case 125:
                    ContextInfo contextInfo112 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int cCModeState = getCCModeState(contextInfo112);
                    parcel2.writeNoException();
                    parcel2.writeInt(cCModeState);
                    return true;
                case 126:
                    ContextInfo contextInfo113 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsScreenPinningAllowed = isScreenPinningAllowed(contextInfo113);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenPinningAllowed);
                    return true;
                case 127:
                    ContextInfo contextInfo114 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z93 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowScreenPinning = allowScreenPinning(contextInfo114, z93);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowScreenPinning);
                    return true;
                case 128:
                    ContextInfo contextInfo115 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i15 = parcel.readInt();
                    boolean z94 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableWearablePolicy = enableWearablePolicy(contextInfo115, i15, z94);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableWearablePolicy);
                    return true;
                case 129:
                    ContextInfo contextInfo116 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWearablePolicyEnabled = isWearablePolicyEnabled(contextInfo116, i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWearablePolicyEnabled);
                    return true;
                case 130:
                    ContextInfo contextInfo117 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z95 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowedFOTAVersion = setAllowedFOTAVersion(contextInfo117, string, bundle, z95);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowedFOTAVersion);
                    return true;
                case 131:
                    ContextInfo contextInfo118 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String allowedFOTAVersion2 = getAllowedFOTAVersion(contextInfo118);
                    parcel2.writeNoException();
                    parcel2.writeString(allowedFOTAVersion2);
                    return true;
                case 132:
                    ContextInfo contextInfo119 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> allowedFOTAInfo = getAllowedFOTAInfo(contextInfo119);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowedFOTAInfo);
                    return true;
                case 133:
                    ContextInfo contextInfo120 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z96 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsIrisCameraEnabled = isIrisCameraEnabled(contextInfo120, z96);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIrisCameraEnabled);
                    return true;
                case 134:
                    ContextInfo contextInfo121 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z97 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean irisCameraState = setIrisCameraState(contextInfo121, z97);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(irisCameraState);
                    return true;
                case 135:
                    ContextInfo contextInfo122 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z98 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowDataSaving = allowDataSaving(contextInfo122, z98);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowDataSaving);
                    return true;
                case 136:
                    boolean zIsDataSavingAllowed = isDataSavingAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDataSavingAllowed);
                    return true;
                case 137:
                    ContextInfo contextInfo123 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z99 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowPowerSavingMode = allowPowerSavingMode(contextInfo123, z99);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowPowerSavingMode);
                    return true;
                case 138:
                    ContextInfo contextInfo124 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsPowerSavingModeAllowed = isPowerSavingModeAllowed(contextInfo124);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPowerSavingModeAllowed);
                    return true;
                case 139:
                    ContextInfo contextInfo125 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean usbExceptionList = setUsbExceptionList(contextInfo125, i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbExceptionList);
                    return true;
                case 140:
                    int usbExceptionList2 = getUsbExceptionList();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbExceptionList2);
                    return true;
                case 141:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    systemReady(i18);
                    parcel2.writeNoException();
                    return true;
                case 142:
                    ContextInfo contextInfo126 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z100 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowFaceRecognitionEvenCameraBlocked = allowFaceRecognitionEvenCameraBlocked(contextInfo126, z100);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowFaceRecognitionEvenCameraBlocked);
                    return true;
                case 143:
                    ContextInfo contextInfo127 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsFaceRecognitionAllowedEvenCameraBlocked = isFaceRecognitionAllowedEvenCameraBlocked(contextInfo127);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFaceRecognitionAllowedEvenCameraBlocked);
                    return true;
                case 144:
                    ContextInfo contextInfo128 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z101 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowLocalContactStorage = allowLocalContactStorage(contextInfo128, z101);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowLocalContactStorage);
                    return true;
                case 145:
                    ContextInfo contextInfo129 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsLocalContactStorageAllowed = isLocalContactStorageAllowed(contextInfo129);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLocalContactStorageAllowed);
                    return true;
                case 146:
                    ContextInfo contextInfo130 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z102 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean knoxDelegationEnabled = setKnoxDelegationEnabled(contextInfo130, z102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(knoxDelegationEnabled);
                    return true;
                case 147:
                    ContextInfo contextInfo131 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsKnoxDelegationEnabled = isKnoxDelegationEnabled(contextInfo131);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKnoxDelegationEnabled);
                    return true;
                case 148:
                    ContextInfo contextInfo132 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z103 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowIntelligenceOnlineProcessing = allowIntelligenceOnlineProcessing(contextInfo132, z103);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowIntelligenceOnlineProcessing);
                    return true;
                case 149:
                    ContextInfo contextInfo133 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsIntelligenceOnlineProcessingAllowed = isIntelligenceOnlineProcessingAllowed(contextInfo133);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIntelligenceOnlineProcessingAllowed);
                    return true;
                case 150:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCheckIfRestrictionWasSetByKC = checkIfRestrictionWasSetByKC(string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckIfRestrictionWasSetByKC);
                    return true;
                case 151:
                    String kcActionDisabledText = getKcActionDisabledText();
                    parcel2.writeNoException();
                    parcel2.writeString(kcActionDisabledText);
                    return true;
                case 152:
                    String string3 = parcel.readString();
                    boolean z104 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateUserRestrictionsByKC(string3, z104);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    ContextInfo contextInfo134 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z105 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowActivationLock = allowActivationLock(contextInfo134, z105);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowActivationLock);
                    return true;
                case 154:
                    ContextInfo contextInfo135 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z106 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsActivationLockAllowed = isActivationLockAllowed(contextInfo135, z106);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsActivationLockAllowed);
                    return true;
                case 155:
                    ContextInfo contextInfo136 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsNonTrustedAppInstallBlocked = isNonTrustedAppInstallBlocked(contextInfo136);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNonTrustedAppInstallBlocked);
                    return true;
                case 156:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsNonTrustedAppInstallBlockedAsUser = isNonTrustedAppInstallBlockedAsUser(i19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNonTrustedAppInstallBlockedAsUser);
                    return true;
                case 157:
                    ContextInfo contextInfo137 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z107 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUsbMassStorageEnabled = isUsbMassStorageEnabled(contextInfo137, z107);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbMassStorageEnabled);
                    return true;
                case 158:
                    ContextInfo contextInfo138 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z108 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUsbKiesAvailable = isUsbKiesAvailable(contextInfo138, z108);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbKiesAvailable);
                    return true;
                case 159:
                    ContextInfo contextInfo139 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z109 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nonTrustedAppInstallBlock = setNonTrustedAppInstallBlock(contextInfo139, z109);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nonTrustedAppInstallBlock);
                    return true;
                case 160:
                    ContextInfo contextInfo140 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z110 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbKiesAvailability = setUsbKiesAvailability(contextInfo140, z110);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbKiesAvailability);
                    return true;
                case 161:
                    ContextInfo contextInfo141 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z111 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbMassStorage = setUsbMassStorage(contextInfo141, z111);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbMassStorage);
                    return true;
                case 162:
                    ContextInfo contextInfo142 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z112 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsCCModeEnabled = isCCModeEnabled(contextInfo142, z112);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCCModeEnabled);
                    return true;
                case 163:
                    int i20 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCheckPackageSource = checkPackageSource(i20, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckPackageSource);
                    return true;
                case 164:
                    int i21 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCheckAdminActivationEnabled = checkAdminActivationEnabled(i21, string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckAdminActivationEnabled);
                    return true;
                case 165:
                    int i22 = parcel.readInt();
                    boolean z113 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsNewAdminInstallationEnabledAsUser = isNewAdminInstallationEnabledAsUser(i22, z113);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNewAdminInstallationEnabledAsUser);
                    return true;
                case 166:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showRestrictionToast(string6);
                    parcel2.writeNoException();
                    return true;
                case 167:
                    ContextInfo contextInfo143 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zEnableConstrainedState = enableConstrainedState(contextInfo143, string7, string8, string9, string10, i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableConstrainedState);
                    return true;
                case 168:
                    ContextInfo contextInfo144 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDisableConstrainedState = disableConstrainedState(contextInfo144);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableConstrainedState);
                    return true;
                case 169:
                    int constrainedState = getConstrainedState();
                    parcel2.writeNoException();
                    parcel2.writeInt(constrainedState);
                    return true;
                case 170:
                    ContextInfo contextInfo145 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setMultiSimPolicy(contextInfo145, i24, i25, strArrCreateStringArray, strArrCreateStringArray2, strArrCreateStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 171:
                    String multiSimPolicy = getMultiSimPolicy();
                    parcel2.writeNoException();
                    parcel2.writeString(multiSimPolicy);
                    return true;
                case 172:
                    ContextInfo contextInfo146 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z114 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowWiFiSharing = allowWiFiSharing(contextInfo146, z114);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowWiFiSharing);
                    return true;
                case 173:
                    ContextInfo contextInfo147 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsWiFiSharingEnabled = isWiFiSharingEnabled(contextInfo147);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWiFiSharingEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IRestrictionPolicy {
        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean addNewAdminActivationAppWhiteList(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowActivationLock(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowAirplaneMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowAudioRecord(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowBackgroundProcessLimit(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowClipboardShare(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowDataSaving(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowDeveloperMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowFaceRecognitionEvenCameraBlocked(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowFactoryReset(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowFastEncryption(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowFirmwareAutoUpdate(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowFirmwareRecovery(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowGoogleAccountsAutoSync(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowGoogleCrashReport(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowIntelligenceOnlineProcessing(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowKillingActivitiesOnLeave(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowLocalContactStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowLockScreenView(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowOTAUpgrade(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowPowerOff(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowPowerSavingMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowSDCardMove(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowSDCardWrite(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowSVoice(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowSafeMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowScreenPinning(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowSettingsChanges(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowShareList(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowSmartClipMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowStatusBarExpansion(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowStopSystemApp(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowUsbHostStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowUserMobileDataLimit(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowVideoRecord(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowVpn(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowWallpaperChange(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowWiFiSharing(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean allowWifiDirect(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean checkAdminActivationEnabled(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean checkIfRestrictionWasSetByKC(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean checkPackageSource(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean clearNewAdminActivationAppWhiteList(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean disableConstrainedState(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean enableODETrustedBootVerification(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean enableWearablePolicy(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public List<String> getAllowedFOTAInfo(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public String getAllowedFOTAVersion(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public int getCCModeState(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public int getConstrainedState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public String getKcActionDisabledText() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public String getMultiSimPolicy() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public List<String> getNewAdminActivationAppWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public int getUsbExceptionList() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isActivationLockAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isAirplaneModeAllowed(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isAudioRecordAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isBackgroundDataEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isBackgroundProcessLimitAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isBackupAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isBluetoothTetheringEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isCCModeEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isCCModeSupported(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isCameraEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isCellularDataAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isClipboardAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isClipboardAllowedAsUser(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isClipboardShareAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isClipboardShareAllowedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isDataSavingAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isDeveloperModeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isFaceRecognitionAllowedEvenCameraBlocked(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isFactoryResetAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isFastEncryptionAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isFirmwareAutoUpdateAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isFirmwareRecoveryAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isGoogleAccountsAutoSyncAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isGoogleCrashReportAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isGoogleCrashReportAllowedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isHeadphoneEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isHomeKeyEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isIntelligenceOnlineProcessingAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isIrisCameraEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isKillingActivitiesOnLeaveAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isKnoxDelegationEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isLocalContactStorageAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isLockScreenEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isLockScreenViewAllowed(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isMicrophoneEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isMicrophoneEnabledAsUser(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isMockLocationEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isNewAdminActivationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isNewAdminInstallationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isNewAdminInstallationEnabledAsUser(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isNonMarketAppAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isNonTrustedAppInstallBlocked(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isNonTrustedAppInstallBlockedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isODETrustedBootVerificationEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isOTAUpgradeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isPowerOffAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isPowerSavingModeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSDCardMoveAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSDCardWriteAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSVoiceAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSVoiceAllowedAsUser(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSafeModeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isScreenCaptureEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isScreenCaptureEnabledEx(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isScreenCaptureEnabledInternal(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isScreenPinningAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSdCardEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSettingsChangesAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSettingsChangesAllowedAsUser(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isShareListAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isShareListAllowedAsUser(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSmartClipModeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isSmartClipModeAllowedInternal(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isStatusBarExpansionAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isStatusBarExpansionAllowedAsUser(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isStopSystemAppAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isTetheringEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isUsbDebuggingEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isUsbHostStorageAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isUsbKiesAvailable(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isUsbMassStorageEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isUsbMediaPlayerAvailable(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isUsbTetheringEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isUseSecureKeypadEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isUserMobileDataLimitAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isVideoRecordAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isVpnAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isWallpaperChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isWearablePolicyEnabled(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isWiFiSharingEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isWifiDirectAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean isWifiTetheringEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean preventNewAdminActivation(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean preventNewAdminInstallation(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setAllowNonMarketApps(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setAllowedFOTAVersion(ContextInfo contextInfo, String str, Bundle bundle, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setBackgroundData(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setBackup(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setBluetoothTethering(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setCCMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setCCModeOnlyForCallerSystem(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setCamera(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setCellularData(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setClipboardEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setHeadphoneState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setHomeKeyState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setIrisCameraState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setKnoxDelegationEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setLockScreenState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setMicrophoneState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setMockLocation(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setNonTrustedAppInstallBlock(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setScreenCapture(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setSdCardState(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setTethering(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setUsbDebuggingEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setUsbExceptionList(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setUsbKiesAvailability(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setUsbMassStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setUsbMediaPlayerAvailability(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setUsbTethering(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setUseSecureKeypad(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public boolean setWifiTethering(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public void showRestrictionToast(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public void systemReady(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public void updateUserRestrictionsByKC(String str, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public void setMultiSimPolicy(ContextInfo contextInfo, int i, int i2, String[] strArr, String[] strArr2, String[] strArr3) throws RemoteException {
        }
    }
}
