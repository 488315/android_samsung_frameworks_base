package com.samsung.android.knox.restriction;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IRestrictionPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.restriction.IRestrictionPolicy";

    boolean addNewAdminActivationAppWhiteList(ContextInfo contextInfo, List<String> list);

    boolean allowActivationLock(ContextInfo contextInfo, boolean z);

    boolean allowAirplaneMode(ContextInfo contextInfo, boolean z);

    boolean allowAudioRecord(ContextInfo contextInfo, boolean z);

    boolean allowBackgroundProcessLimit(ContextInfo contextInfo, boolean z);

    boolean allowClipboardShare(ContextInfo contextInfo, boolean z);

    boolean allowDataSaving(ContextInfo contextInfo, boolean z);

    boolean allowDeveloperMode(ContextInfo contextInfo, boolean z);

    boolean allowFaceRecognitionEvenCameraBlocked(ContextInfo contextInfo, boolean z);

    boolean allowFactoryReset(ContextInfo contextInfo, boolean z);

    boolean allowFastEncryption(ContextInfo contextInfo, boolean z);

    boolean allowFirmwareAutoUpdate(ContextInfo contextInfo, boolean z);

    boolean allowFirmwareRecovery(ContextInfo contextInfo, boolean z);

    boolean allowGoogleAccountsAutoSync(ContextInfo contextInfo, boolean z);

    boolean allowGoogleCrashReport(ContextInfo contextInfo, boolean z);

    boolean allowIntelligenceOnlineProcessing(ContextInfo contextInfo, boolean z);

    boolean allowKillingActivitiesOnLeave(ContextInfo contextInfo, boolean z);

    boolean allowLocalContactStorage(ContextInfo contextInfo, boolean z);

    boolean allowLockScreenView(ContextInfo contextInfo, int i, boolean z);

    boolean allowOTAUpgrade(ContextInfo contextInfo, boolean z);

    boolean allowPowerOff(ContextInfo contextInfo, boolean z);

    boolean allowPowerSavingMode(ContextInfo contextInfo, boolean z);

    boolean allowSDCardMove(ContextInfo contextInfo, boolean z);

    boolean allowSDCardWrite(ContextInfo contextInfo, boolean z);

    boolean allowSVoice(ContextInfo contextInfo, boolean z);

    boolean allowSafeMode(ContextInfo contextInfo, boolean z);

    boolean allowScreenPinning(ContextInfo contextInfo, boolean z);

    boolean allowSettingsChanges(ContextInfo contextInfo, boolean z);

    boolean allowShareList(ContextInfo contextInfo, boolean z);

    boolean allowSmartClipMode(ContextInfo contextInfo, boolean z);

    boolean allowStatusBarExpansion(ContextInfo contextInfo, boolean z);

    boolean allowStopSystemApp(ContextInfo contextInfo, boolean z);

    boolean allowUsbHostStorage(ContextInfo contextInfo, boolean z);

    boolean allowUserMobileDataLimit(ContextInfo contextInfo, boolean z);

    boolean allowVideoRecord(ContextInfo contextInfo, boolean z);

    boolean allowVpn(ContextInfo contextInfo, boolean z);

    boolean allowWallpaperChange(ContextInfo contextInfo, boolean z);

    boolean allowWifiDirect(ContextInfo contextInfo, boolean z);

    boolean checkAdminActivationEnabled(int i, String str);

    boolean checkIfRestrictionWasSetByKC(String str);

    boolean checkPackageSource(int i, String str);

    boolean clearNewAdminActivationAppWhiteList(ContextInfo contextInfo);

    boolean disableConstrainedState(ContextInfo contextInfo);

    boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i);

    boolean enableODETrustedBootVerification(ContextInfo contextInfo, boolean z);

    boolean enableWearablePolicy(ContextInfo contextInfo, int i, boolean z);

    List<String> getAllowedFOTAInfo(ContextInfo contextInfo);

    String getAllowedFOTAVersion(ContextInfo contextInfo);

    int getCCModeState(ContextInfo contextInfo);

    int getConstrainedState();

    String getKcActionDisabledText();

    List<String> getNewAdminActivationAppWhiteList(ContextInfo contextInfo);

    int getUsbExceptionList();

    boolean isActivationLockAllowed(ContextInfo contextInfo, boolean z);

    boolean isAirplaneModeAllowed(boolean z);

    boolean isAudioRecordAllowed(ContextInfo contextInfo, boolean z);

    boolean isBackgroundDataEnabled(ContextInfo contextInfo);

    boolean isBackgroundProcessLimitAllowed(ContextInfo contextInfo);

    boolean isBackupAllowed(ContextInfo contextInfo, boolean z);

    boolean isBluetoothTetheringEnabled(ContextInfo contextInfo);

    boolean isCCModeEnabled(ContextInfo contextInfo, boolean z);

    boolean isCCModeSupported(ContextInfo contextInfo, boolean z);

    boolean isCameraEnabled(ContextInfo contextInfo, boolean z);

    boolean isCellularDataAllowed(ContextInfo contextInfo);

    boolean isClipboardAllowed(ContextInfo contextInfo, boolean z);

    boolean isClipboardAllowedAsUser(boolean z, int i);

    boolean isClipboardShareAllowed(ContextInfo contextInfo);

    boolean isClipboardShareAllowedAsUser(int i);

    boolean isDataSavingAllowed();

    boolean isDeveloperModeAllowed(ContextInfo contextInfo, boolean z);

    boolean isFaceRecognitionAllowedEvenCameraBlocked(ContextInfo contextInfo);

    boolean isFactoryResetAllowed(ContextInfo contextInfo);

    boolean isFastEncryptionAllowed(ContextInfo contextInfo, boolean z);

    boolean isFirmwareAutoUpdateAllowed(ContextInfo contextInfo, boolean z);

    boolean isFirmwareRecoveryAllowed(ContextInfo contextInfo, boolean z);

    boolean isGoogleAccountsAutoSyncAllowed(ContextInfo contextInfo);

    boolean isGoogleAccountsAutoSyncAllowedAsUser(int i);

    boolean isGoogleCrashReportAllowed(ContextInfo contextInfo);

    boolean isGoogleCrashReportAllowedAsUser(int i);

    boolean isHeadphoneEnabled(ContextInfo contextInfo, boolean z);

    boolean isHomeKeyEnabled(ContextInfo contextInfo, boolean z);

    boolean isIntelligenceOnlineProcessingAllowed(ContextInfo contextInfo);

    boolean isIrisCameraEnabled(ContextInfo contextInfo, boolean z);

    boolean isKillingActivitiesOnLeaveAllowed(ContextInfo contextInfo);

    boolean isKnoxDelegationEnabled(ContextInfo contextInfo);

    boolean isLocalContactStorageAllowed(ContextInfo contextInfo);

    boolean isLockScreenEnabled(ContextInfo contextInfo, boolean z);

    boolean isLockScreenViewAllowed(ContextInfo contextInfo, int i);

    boolean isMicrophoneEnabled(ContextInfo contextInfo, boolean z);

    boolean isMicrophoneEnabledAsUser(boolean z, int i);

    boolean isMockLocationEnabled(ContextInfo contextInfo);

    boolean isNewAdminActivationEnabled(ContextInfo contextInfo, boolean z);

    boolean isNewAdminInstallationEnabled(ContextInfo contextInfo, boolean z);

    boolean isNewAdminInstallationEnabledAsUser(int i, boolean z);

    boolean isNonMarketAppAllowed(ContextInfo contextInfo);

    boolean isNonTrustedAppInstallBlocked(ContextInfo contextInfo);

    boolean isNonTrustedAppInstallBlockedAsUser(int i);

    boolean isODETrustedBootVerificationEnabled(ContextInfo contextInfo);

    boolean isOTAUpgradeAllowed(ContextInfo contextInfo);

    boolean isPowerOffAllowed(ContextInfo contextInfo, boolean z);

    boolean isPowerSavingModeAllowed(ContextInfo contextInfo);

    boolean isSDCardMoveAllowed(ContextInfo contextInfo, boolean z);

    boolean isSDCardWriteAllowed(ContextInfo contextInfo);

    boolean isSVoiceAllowed(ContextInfo contextInfo, boolean z);

    boolean isSVoiceAllowedAsUser(boolean z, int i);

    boolean isSafeModeAllowed(ContextInfo contextInfo);

    boolean isScreenCaptureEnabled(ContextInfo contextInfo, boolean z);

    boolean isScreenCaptureEnabledEx(int i, boolean z);

    boolean isScreenCaptureEnabledInternal(boolean z);

    boolean isScreenPinningAllowed(ContextInfo contextInfo);

    boolean isSdCardEnabled(ContextInfo contextInfo);

    boolean isSettingsChangesAllowed(ContextInfo contextInfo, boolean z);

    boolean isSettingsChangesAllowedAsUser(boolean z, int i);

    boolean isShareListAllowed(ContextInfo contextInfo, boolean z);

    boolean isShareListAllowedAsUser(int i, boolean z);

    boolean isSmartClipModeAllowed(ContextInfo contextInfo);

    boolean isSmartClipModeAllowedInternal(boolean z);

    boolean isStatusBarExpansionAllowed(ContextInfo contextInfo, boolean z);

    boolean isStatusBarExpansionAllowedAsUser(boolean z, int i);

    boolean isStopSystemAppAllowed(ContextInfo contextInfo);

    boolean isTetheringEnabled(ContextInfo contextInfo);

    boolean isUsbDebuggingEnabled(ContextInfo contextInfo);

    boolean isUsbHostStorageAllowed(ContextInfo contextInfo, boolean z);

    boolean isUsbKiesAvailable(ContextInfo contextInfo, boolean z);

    boolean isUsbMassStorageEnabled(ContextInfo contextInfo, boolean z);

    boolean isUsbMediaPlayerAvailable(ContextInfo contextInfo, boolean z);

    boolean isUsbTetheringEnabled(ContextInfo contextInfo);

    boolean isUseSecureKeypadEnabled(ContextInfo contextInfo);

    boolean isUserMobileDataLimitAllowed(ContextInfo contextInfo);

    boolean isVideoRecordAllowed(ContextInfo contextInfo, boolean z);

    boolean isVpnAllowed(ContextInfo contextInfo);

    boolean isWallpaperChangeAllowed(ContextInfo contextInfo, boolean z);

    boolean isWearablePolicyEnabled(ContextInfo contextInfo, int i);

    boolean isWifiDirectAllowed(ContextInfo contextInfo, boolean z);

    boolean isWifiTetheringEnabled(ContextInfo contextInfo);

    boolean preventNewAdminActivation(ContextInfo contextInfo, boolean z);

    boolean preventNewAdminInstallation(ContextInfo contextInfo, boolean z);

    boolean setAllowNonMarketApps(ContextInfo contextInfo, boolean z);

    boolean setAllowedFOTAVersion(ContextInfo contextInfo, String str, Bundle bundle, boolean z);

    boolean setBackgroundData(ContextInfo contextInfo, boolean z);

    boolean setBackup(ContextInfo contextInfo, boolean z);

    boolean setBluetoothTethering(ContextInfo contextInfo, boolean z);

    boolean setCCMode(ContextInfo contextInfo, boolean z);

    boolean setCCModeOnlyForCallerSystem(ContextInfo contextInfo, boolean z);

    boolean setCamera(ContextInfo contextInfo, boolean z);

    boolean setCellularData(ContextInfo contextInfo, boolean z);

    boolean setClipboardEnabled(ContextInfo contextInfo, boolean z);

    boolean setHeadphoneState(ContextInfo contextInfo, boolean z);

    boolean setHomeKeyState(ContextInfo contextInfo, boolean z);

    boolean setIrisCameraState(ContextInfo contextInfo, boolean z);

    boolean setKnoxDelegationEnabled(ContextInfo contextInfo, boolean z);

    boolean setLockScreenState(ContextInfo contextInfo, boolean z);

    boolean setMicrophoneState(ContextInfo contextInfo, boolean z);

    boolean setMockLocation(ContextInfo contextInfo, boolean z);

    boolean setNonTrustedAppInstallBlock(ContextInfo contextInfo, boolean z);

    boolean setScreenCapture(ContextInfo contextInfo, boolean z);

    boolean setSdCardState(ContextInfo contextInfo, boolean z);

    boolean setTethering(ContextInfo contextInfo, boolean z);

    boolean setUsbDebuggingEnabled(ContextInfo contextInfo, boolean z);

    boolean setUsbExceptionList(ContextInfo contextInfo, int i);

    boolean setUsbKiesAvailability(ContextInfo contextInfo, boolean z);

    boolean setUsbMassStorage(ContextInfo contextInfo, boolean z);

    boolean setUsbMediaPlayerAvailability(ContextInfo contextInfo, boolean z);

    boolean setUsbTethering(ContextInfo contextInfo, boolean z);

    boolean setUseSecureKeypad(ContextInfo contextInfo, boolean z);

    boolean setWifiTethering(ContextInfo contextInfo, boolean z);

    void showRestrictionToast(String str);

    void systemReady(int i);

    void updateUserRestrictionsByKC(String str, boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IRestrictionPolicy {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IRestrictionPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean addNewAdminActivationAppWhiteList(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowActivationLock(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowAirplaneMode(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowAudioRecord(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowBackgroundProcessLimit(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowClipboardShare(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowDataSaving(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowDeveloperMode(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowFaceRecognitionEvenCameraBlocked(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowFactoryReset(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowFastEncryption(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowFirmwareAutoUpdate(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowFirmwareRecovery(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowGoogleAccountsAutoSync(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowGoogleCrashReport(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowIntelligenceOnlineProcessing(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowKillingActivitiesOnLeave(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowLocalContactStorage(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowLockScreenView(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowOTAUpgrade(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowPowerOff(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowPowerSavingMode(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowSDCardMove(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowSDCardWrite(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowSVoice(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowSafeMode(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowScreenPinning(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowSettingsChanges(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowShareList(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowSmartClipMode(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowStatusBarExpansion(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowStopSystemApp(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowUsbHostStorage(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowUserMobileDataLimit(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowVideoRecord(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowVpn(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowWallpaperChange(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean allowWifiDirect(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean checkAdminActivationEnabled(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean checkIfRestrictionWasSetByKC(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean checkPackageSource(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean clearNewAdminActivationAppWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean disableConstrainedState(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean enableODETrustedBootVerification(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean enableWearablePolicy(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final List<String> getAllowedFOTAInfo(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final String getAllowedFOTAVersion(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final int getCCModeState(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final int getConstrainedState() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IRestrictionPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final String getKcActionDisabledText() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final List<String> getNewAdminActivationAppWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final int getUsbExceptionList() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isActivationLockAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isAirplaneModeAllowed(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isAudioRecordAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isBackgroundDataEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isBackgroundProcessLimitAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isBackupAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isBluetoothTetheringEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isCCModeEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isCCModeSupported(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isCameraEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isCellularDataAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isClipboardAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isClipboardAllowedAsUser(boolean z, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isClipboardShareAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isClipboardShareAllowedAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isDataSavingAllowed() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isDeveloperModeAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isFaceRecognitionAllowedEvenCameraBlocked(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isFactoryResetAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isFastEncryptionAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isFirmwareAutoUpdateAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isFirmwareRecoveryAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isGoogleAccountsAutoSyncAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isGoogleCrashReportAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isGoogleCrashReportAllowedAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isHeadphoneEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isHomeKeyEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isIntelligenceOnlineProcessingAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isIrisCameraEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isKillingActivitiesOnLeaveAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isKnoxDelegationEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isLocalContactStorageAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isLockScreenEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isLockScreenViewAllowed(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isMicrophoneEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isMicrophoneEnabledAsUser(boolean z, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isMockLocationEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isNewAdminActivationEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isNewAdminInstallationEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isNewAdminInstallationEnabledAsUser(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isNonMarketAppAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isNonTrustedAppInstallBlocked(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isNonTrustedAppInstallBlockedAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isODETrustedBootVerificationEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isOTAUpgradeAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isPowerOffAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isPowerSavingModeAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSDCardMoveAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSDCardWriteAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSVoiceAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSVoiceAllowedAsUser(boolean z, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSafeModeAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isScreenCaptureEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isScreenCaptureEnabledEx(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isScreenCaptureEnabledInternal(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isScreenPinningAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSdCardEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSettingsChangesAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSettingsChangesAllowedAsUser(boolean z, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isShareListAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isShareListAllowedAsUser(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSmartClipModeAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isSmartClipModeAllowedInternal(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isStatusBarExpansionAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isStatusBarExpansionAllowedAsUser(boolean z, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isStopSystemAppAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isTetheringEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isUsbDebuggingEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isUsbHostStorageAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isUsbKiesAvailable(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isUsbMassStorageEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isUsbMediaPlayerAvailable(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isUsbTetheringEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isUseSecureKeypadEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isUserMobileDataLimitAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isVideoRecordAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isVpnAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isWallpaperChangeAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isWearablePolicyEnabled(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isWifiDirectAllowed(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean isWifiTetheringEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean preventNewAdminActivation(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean preventNewAdminInstallation(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setAllowNonMarketApps(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setAllowedFOTAVersion(ContextInfo contextInfo, String str, Bundle bundle, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setBackgroundData(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setBackup(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setBluetoothTethering(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setCCMode(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setCCModeOnlyForCallerSystem(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setCamera(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setCellularData(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setClipboardEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setHeadphoneState(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setHomeKeyState(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setIrisCameraState(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setKnoxDelegationEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setLockScreenState(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setMicrophoneState(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setMockLocation(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setNonTrustedAppInstallBlock(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setScreenCapture(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setSdCardState(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setTethering(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setUsbDebuggingEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setUsbExceptionList(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setUsbKiesAvailability(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setUsbMassStorage(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setUsbMediaPlayerAvailability(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setUsbTethering(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setUseSecureKeypad(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final boolean setWifiTethering(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final void showRestrictionToast(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final void systemReady(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
            public final void updateUserRestrictionsByKC(String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRestrictionPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRestrictionPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IRestrictionPolicy)) ? new Proxy(iBinder) : (IRestrictionPolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean camera = setCamera(contextInfo, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(camera);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isCameraEnabled = isCameraEnabled(contextInfo2, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCameraEnabled);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean microphoneState = setMicrophoneState(contextInfo3, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(microphoneState);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isMicrophoneEnabled = isMicrophoneEnabled(contextInfo4, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMicrophoneEnabled);
                    return true;
                case 5:
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMicrophoneEnabledAsUser = isMicrophoneEnabledAsUser(readBoolean5, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMicrophoneEnabledAsUser);
                    return true;
                case 6:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean sdCardState = setSdCardState(contextInfo5, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdCardState);
                    return true;
                case 7:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSdCardEnabled = isSdCardEnabled(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSdCardEnabled);
                    return true;
                case 8:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothTethering = setBluetoothTethering(contextInfo7, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothTethering);
                    return true;
                case 9:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothTetheringEnabled = isBluetoothTetheringEnabled(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothTetheringEnabled);
                    return true;
                case 10:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbTethering = setUsbTethering(contextInfo9, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbTethering);
                    return true;
                case 11:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isUsbTetheringEnabled = isUsbTetheringEnabled(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbTetheringEnabled);
                    return true;
                case 12:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiTethering = setWifiTethering(contextInfo11, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiTethering);
                    return true;
                case 13:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isWifiTetheringEnabled = isWifiTetheringEnabled(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiTetheringEnabled);
                    return true;
                case 14:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean tethering = setTethering(contextInfo13, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tethering);
                    return true;
                case 15:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isTetheringEnabled = isTetheringEnabled(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTetheringEnabled);
                    return true;
                case 16:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbDebuggingEnabled = setUsbDebuggingEnabled(contextInfo15, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbDebuggingEnabled);
                    return true;
                case 17:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isUsbDebuggingEnabled = isUsbDebuggingEnabled(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbDebuggingEnabled);
                    return true;
                case 18:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbMediaPlayerAvailability = setUsbMediaPlayerAvailability(contextInfo17, readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbMediaPlayerAvailability);
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUsbMediaPlayerAvailable = isUsbMediaPlayerAvailable(contextInfo18, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbMediaPlayerAvailable);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean screenCapture = setScreenCapture(contextInfo19, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(screenCapture);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isScreenCaptureEnabled = isScreenCaptureEnabled(contextInfo20, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenCaptureEnabled);
                    return true;
                case 22:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isScreenCaptureEnabledEx = isScreenCaptureEnabledEx(readInt2, readBoolean16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenCaptureEnabledEx);
                    return true;
                case 23:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isScreenCaptureEnabledInternal = isScreenCaptureEnabledInternal(readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenCaptureEnabledInternal);
                    return true;
                case 24:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean mockLocation = setMockLocation(contextInfo21, readBoolean18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(mockLocation);
                    return true;
                case 25:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMockLocationEnabled = isMockLocationEnabled(contextInfo22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMockLocationEnabled);
                    return true;
                case 26:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean backup = setBackup(contextInfo23, readBoolean19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backup);
                    return true;
                case 27:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isBackupAllowed = isBackupAllowed(contextInfo24, readBoolean20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupAllowed);
                    return true;
                case 28:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean clipboardEnabled = setClipboardEnabled(contextInfo25, readBoolean21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clipboardEnabled);
                    return true;
                case 29:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isClipboardAllowed = isClipboardAllowed(contextInfo26, readBoolean22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClipboardAllowed);
                    return true;
                case 30:
                    boolean readBoolean23 = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isClipboardAllowedAsUser = isClipboardAllowedAsUser(readBoolean23, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClipboardAllowedAsUser);
                    return true;
                case 31:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowFactoryReset = allowFactoryReset(contextInfo27, readBoolean24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowFactoryReset);
                    return true;
                case 32:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isFactoryResetAllowed = isFactoryResetAllowed(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFactoryResetAllowed);
                    return true;
                case 33:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean homeKeyState = setHomeKeyState(contextInfo29, readBoolean25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(homeKeyState);
                    return true;
                case 34:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isHomeKeyEnabled = isHomeKeyEnabled(contextInfo30, readBoolean26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHomeKeyEnabled);
                    return true;
                case 35:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean backgroundData = setBackgroundData(contextInfo31, readBoolean27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backgroundData);
                    return true;
                case 36:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBackgroundDataEnabled = isBackgroundDataEnabled(contextInfo32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackgroundDataEnabled);
                    return true;
                case 37:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean cellularData = setCellularData(contextInfo33, readBoolean28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cellularData);
                    return true;
                case 38:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCellularDataAllowed = isCellularDataAllowed(contextInfo34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCellularDataAllowed);
                    return true;
                case 39:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowSettingsChanges = allowSettingsChanges(contextInfo35, readBoolean29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowSettingsChanges);
                    return true;
                case 40:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isSettingsChangesAllowed = isSettingsChangesAllowed(contextInfo36, readBoolean30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSettingsChangesAllowed);
                    return true;
                case 41:
                    boolean readBoolean31 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSettingsChangesAllowedAsUser = isSettingsChangesAllowedAsUser(readBoolean31, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSettingsChangesAllowedAsUser);
                    return true;
                case 42:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowNonMarketApps = setAllowNonMarketApps(contextInfo37, readBoolean32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowNonMarketApps);
                    return true;
                case 43:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isNonMarketAppAllowed = isNonMarketAppAllowed(contextInfo38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNonMarketAppAllowed);
                    return true;
                case 44:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowVpn = allowVpn(contextInfo39, readBoolean33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowVpn);
                    return true;
                case 45:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isVpnAllowed = isVpnAllowed(contextInfo40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVpnAllowed);
                    return true;
                case 46:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowOTAUpgrade = allowOTAUpgrade(contextInfo41, readBoolean34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowOTAUpgrade);
                    return true;
                case 47:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isOTAUpgradeAllowed = isOTAUpgradeAllowed(contextInfo42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOTAUpgradeAllowed);
                    return true;
                case 48:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean35 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowSDCardWrite = allowSDCardWrite(contextInfo43, readBoolean35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowSDCardWrite);
                    return true;
                case 49:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSDCardWriteAllowed = isSDCardWriteAllowed(contextInfo44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSDCardWriteAllowed);
                    return true;
                case 50:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowGoogleCrashReport = allowGoogleCrashReport(contextInfo45, readBoolean36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowGoogleCrashReport);
                    return true;
                case 51:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isGoogleCrashReportAllowed = isGoogleCrashReportAllowed(contextInfo46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGoogleCrashReportAllowed);
                    return true;
                case 52:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isGoogleCrashReportAllowedAsUser = isGoogleCrashReportAllowedAsUser(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGoogleCrashReportAllowedAsUser);
                    return true;
                case 53:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean37 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowWallpaperChange = allowWallpaperChange(contextInfo47, readBoolean37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowWallpaperChange);
                    return true;
                case 54:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean38 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isWallpaperChangeAllowed = isWallpaperChangeAllowed(contextInfo48, readBoolean38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWallpaperChangeAllowed);
                    return true;
                case 55:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean39 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowStatusBarExpansion = allowStatusBarExpansion(contextInfo49, readBoolean39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowStatusBarExpansion);
                    return true;
                case 56:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean40 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isStatusBarExpansionAllowed = isStatusBarExpansionAllowed(contextInfo50, readBoolean40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStatusBarExpansionAllowed);
                    return true;
                case 57:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean41 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowPowerOff = allowPowerOff(contextInfo51, readBoolean41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowPowerOff);
                    return true;
                case 58:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean42 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isPowerOffAllowed = isPowerOffAllowed(contextInfo52, readBoolean42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPowerOffAllowed);
                    return true;
                case 59:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean43 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowAudioRecord = allowAudioRecord(contextInfo53, readBoolean43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowAudioRecord);
                    return true;
                case 60:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean44 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isAudioRecordAllowed = isAudioRecordAllowed(contextInfo54, readBoolean44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAudioRecordAllowed);
                    return true;
                case 61:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean45 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowVideoRecord = allowVideoRecord(contextInfo55, readBoolean45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowVideoRecord);
                    return true;
                case 62:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean46 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isVideoRecordAllowed = isVideoRecordAllowed(contextInfo56, readBoolean46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVideoRecordAllowed);
                    return true;
                case 63:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean47 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowStopSystemApp = allowStopSystemApp(contextInfo57, readBoolean47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowStopSystemApp);
                    return true;
                case 64:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isStopSystemAppAllowed = isStopSystemAppAllowed(contextInfo58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStopSystemAppAllowed);
                    return true;
                case 65:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean48 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowWifiDirect = allowWifiDirect(contextInfo59, readBoolean48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowWifiDirect);
                    return true;
                case 66:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean49 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isWifiDirectAllowed = isWifiDirectAllowed(contextInfo60, readBoolean49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiDirectAllowed);
                    return true;
                case 67:
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean50 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowBackgroundProcessLimit = allowBackgroundProcessLimit(contextInfo61, readBoolean50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowBackgroundProcessLimit);
                    return true;
                case 68:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBackgroundProcessLimitAllowed = isBackgroundProcessLimitAllowed(contextInfo62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackgroundProcessLimitAllowed);
                    return true;
                case 69:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean51 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowKillingActivitiesOnLeave = allowKillingActivitiesOnLeave(contextInfo63, readBoolean51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowKillingActivitiesOnLeave);
                    return true;
                case 70:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isKillingActivitiesOnLeaveAllowed = isKillingActivitiesOnLeaveAllowed(contextInfo64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKillingActivitiesOnLeaveAllowed);
                    return true;
                case 71:
                    ContextInfo contextInfo65 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean52 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserMobileDataLimit = allowUserMobileDataLimit(contextInfo65, readBoolean52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserMobileDataLimit);
                    return true;
                case 72:
                    ContextInfo contextInfo66 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isUserMobileDataLimitAllowed = isUserMobileDataLimitAllowed(contextInfo66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserMobileDataLimitAllowed);
                    return true;
                case 73:
                    ContextInfo contextInfo67 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean53 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowClipboardShare = allowClipboardShare(contextInfo67, readBoolean53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowClipboardShare);
                    return true;
                case 74:
                    ContextInfo contextInfo68 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isClipboardShareAllowed = isClipboardShareAllowed(contextInfo68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClipboardShareAllowed);
                    return true;
                case 75:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isClipboardShareAllowedAsUser = isClipboardShareAllowedAsUser(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClipboardShareAllowedAsUser);
                    return true;
                case 76:
                    ContextInfo contextInfo69 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean54 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowSVoice = allowSVoice(contextInfo69, readBoolean54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowSVoice);
                    return true;
                case 77:
                    ContextInfo contextInfo70 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean55 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isSVoiceAllowed = isSVoiceAllowed(contextInfo70, readBoolean55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSVoiceAllowed);
                    return true;
                case 78:
                    boolean readBoolean56 = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSVoiceAllowedAsUser = isSVoiceAllowedAsUser(readBoolean56, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSVoiceAllowedAsUser);
                    return true;
                case 79:
                    ContextInfo contextInfo71 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean57 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUsbHostStorage = allowUsbHostStorage(contextInfo71, readBoolean57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUsbHostStorage);
                    return true;
                case 80:
                    ContextInfo contextInfo72 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean58 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUsbHostStorageAllowed = isUsbHostStorageAllowed(contextInfo72, readBoolean58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbHostStorageAllowed);
                    return true;
                case 81:
                    ContextInfo contextInfo73 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean59 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowShareList = allowShareList(contextInfo73, readBoolean59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowShareList);
                    return true;
                case 82:
                    ContextInfo contextInfo74 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean60 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isShareListAllowed = isShareListAllowed(contextInfo74, readBoolean60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isShareListAllowed);
                    return true;
                case 83:
                    int readInt8 = parcel.readInt();
                    boolean readBoolean61 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isShareListAllowedAsUser = isShareListAllowedAsUser(readInt8, readBoolean61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isShareListAllowedAsUser);
                    return true;
                case 84:
                    ContextInfo contextInfo75 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean62 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean useSecureKeypad = setUseSecureKeypad(contextInfo75, readBoolean62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(useSecureKeypad);
                    return true;
                case 85:
                    ContextInfo contextInfo76 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isUseSecureKeypadEnabled = isUseSecureKeypadEnabled(contextInfo76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUseSecureKeypadEnabled);
                    return true;
                case 86:
                    ContextInfo contextInfo77 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean63 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowSafeMode = allowSafeMode(contextInfo77, readBoolean63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowSafeMode);
                    return true;
                case 87:
                    ContextInfo contextInfo78 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSafeModeAllowed = isSafeModeAllowed(contextInfo78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSafeModeAllowed);
                    return true;
                case 88:
                    ContextInfo contextInfo79 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    boolean readBoolean64 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowLockScreenView = allowLockScreenView(contextInfo79, readInt9, readBoolean64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowLockScreenView);
                    return true;
                case 89:
                    ContextInfo contextInfo80 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isLockScreenViewAllowed = isLockScreenViewAllowed(contextInfo80, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLockScreenViewAllowed);
                    return true;
                case 90:
                    ContextInfo contextInfo81 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean65 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean lockScreenState = setLockScreenState(contextInfo81, readBoolean65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockScreenState);
                    return true;
                case 91:
                    ContextInfo contextInfo82 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean66 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isLockScreenEnabled = isLockScreenEnabled(contextInfo82, readBoolean66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLockScreenEnabled);
                    return true;
                case 92:
                    ContextInfo contextInfo83 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean67 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowFirmwareRecovery = allowFirmwareRecovery(contextInfo83, readBoolean67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowFirmwareRecovery);
                    return true;
                case 93:
                    ContextInfo contextInfo84 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean68 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isFirmwareRecoveryAllowed = isFirmwareRecoveryAllowed(contextInfo84, readBoolean68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFirmwareRecoveryAllowed);
                    return true;
                case 94:
                    boolean readBoolean69 = parcel.readBoolean();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStatusBarExpansionAllowedAsUser = isStatusBarExpansionAllowedAsUser(readBoolean69, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStatusBarExpansionAllowedAsUser);
                    return true;
                case 95:
                    ContextInfo contextInfo85 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean70 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowDeveloperMode = allowDeveloperMode(contextInfo85, readBoolean70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowDeveloperMode);
                    return true;
                case 96:
                    ContextInfo contextInfo86 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean71 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isDeveloperModeAllowed = isDeveloperModeAllowed(contextInfo86, readBoolean71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeveloperModeAllowed);
                    return true;
                case 97:
                    ContextInfo contextInfo87 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean72 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowAirplaneMode = allowAirplaneMode(contextInfo87, readBoolean72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowAirplaneMode);
                    return true;
                case 98:
                    boolean readBoolean73 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isAirplaneModeAllowed = isAirplaneModeAllowed(readBoolean73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAirplaneModeAllowed);
                    return true;
                case 99:
                    ContextInfo contextInfo88 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean74 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowGoogleAccountsAutoSync = allowGoogleAccountsAutoSync(contextInfo88, readBoolean74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowGoogleAccountsAutoSync);
                    return true;
                case 100:
                    ContextInfo contextInfo89 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isGoogleAccountsAutoSyncAllowed = isGoogleAccountsAutoSyncAllowed(contextInfo89);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGoogleAccountsAutoSyncAllowed);
                    return true;
                case 101:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isGoogleAccountsAutoSyncAllowedAsUser = isGoogleAccountsAutoSyncAllowedAsUser(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGoogleAccountsAutoSyncAllowedAsUser);
                    return true;
                case 102:
                    ContextInfo contextInfo90 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean75 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowFirmwareAutoUpdate = allowFirmwareAutoUpdate(contextInfo90, readBoolean75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowFirmwareAutoUpdate);
                    return true;
                case 103:
                    ContextInfo contextInfo91 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean76 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isFirmwareAutoUpdateAllowed = isFirmwareAutoUpdateAllowed(contextInfo91, readBoolean76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFirmwareAutoUpdateAllowed);
                    return true;
                case 104:
                    ContextInfo contextInfo92 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean77 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean headphoneState = setHeadphoneState(contextInfo92, readBoolean77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(headphoneState);
                    return true;
                case 105:
                    ContextInfo contextInfo93 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean78 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isHeadphoneEnabled = isHeadphoneEnabled(contextInfo93, readBoolean78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHeadphoneEnabled);
                    return true;
                case 106:
                    ContextInfo contextInfo94 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean79 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowSDCardMove = allowSDCardMove(contextInfo94, readBoolean79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowSDCardMove);
                    return true;
                case 107:
                    ContextInfo contextInfo95 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean80 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isSDCardMoveAllowed = isSDCardMoveAllowed(contextInfo95, readBoolean80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSDCardMoveAllowed);
                    return true;
                case 108:
                    ContextInfo contextInfo96 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean81 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowFastEncryption = allowFastEncryption(contextInfo96, readBoolean81);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowFastEncryption);
                    return true;
                case 109:
                    ContextInfo contextInfo97 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean82 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isFastEncryptionAllowed = isFastEncryptionAllowed(contextInfo97, readBoolean82);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFastEncryptionAllowed);
                    return true;
                case 110:
                    ContextInfo contextInfo98 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean83 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean cCMode = setCCMode(contextInfo98, readBoolean83);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cCMode);
                    return true;
                case 111:
                    ContextInfo contextInfo99 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean84 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean cCModeOnlyForCallerSystem = setCCModeOnlyForCallerSystem(contextInfo99, readBoolean84);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cCModeOnlyForCallerSystem);
                    return true;
                case 112:
                    ContextInfo contextInfo100 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean85 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isCCModeSupported = isCCModeSupported(contextInfo100, readBoolean85);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCCModeSupported);
                    return true;
                case 113:
                    ContextInfo contextInfo101 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean86 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableODETrustedBootVerification = enableODETrustedBootVerification(contextInfo101, readBoolean86);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableODETrustedBootVerification);
                    return true;
                case 114:
                    ContextInfo contextInfo102 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isODETrustedBootVerificationEnabled = isODETrustedBootVerificationEnabled(contextInfo102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isODETrustedBootVerificationEnabled);
                    return true;
                case 115:
                    ContextInfo contextInfo103 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean87 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean preventNewAdminInstallation = preventNewAdminInstallation(contextInfo103, readBoolean87);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(preventNewAdminInstallation);
                    return true;
                case 116:
                    ContextInfo contextInfo104 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean88 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isNewAdminInstallationEnabled = isNewAdminInstallationEnabled(contextInfo104, readBoolean88);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNewAdminInstallationEnabled);
                    return true;
                case 117:
                    ContextInfo contextInfo105 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean89 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean preventNewAdminActivation = preventNewAdminActivation(contextInfo105, readBoolean89);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(preventNewAdminActivation);
                    return true;
                case 118:
                    ContextInfo contextInfo106 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean90 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isNewAdminActivationEnabled = isNewAdminActivationEnabled(contextInfo106, readBoolean90);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNewAdminActivationEnabled);
                    return true;
                case 119:
                    ContextInfo contextInfo107 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearNewAdminActivationAppWhiteList = clearNewAdminActivationAppWhiteList(contextInfo107);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearNewAdminActivationAppWhiteList);
                    return true;
                case 120:
                    ContextInfo contextInfo108 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addNewAdminActivationAppWhiteList = addNewAdminActivationAppWhiteList(contextInfo108, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNewAdminActivationAppWhiteList);
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
                    boolean isSmartClipModeAllowed = isSmartClipModeAllowed(contextInfo110);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSmartClipModeAllowed);
                    return true;
                case 123:
                    boolean readBoolean91 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isSmartClipModeAllowedInternal = isSmartClipModeAllowedInternal(readBoolean91);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSmartClipModeAllowedInternal);
                    return true;
                case 124:
                    ContextInfo contextInfo111 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean92 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowSmartClipMode = allowSmartClipMode(contextInfo111, readBoolean92);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowSmartClipMode);
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
                    boolean isScreenPinningAllowed = isScreenPinningAllowed(contextInfo113);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenPinningAllowed);
                    return true;
                case 127:
                    ContextInfo contextInfo114 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean93 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowScreenPinning = allowScreenPinning(contextInfo114, readBoolean93);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowScreenPinning);
                    return true;
                case 128:
                    ContextInfo contextInfo115 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt13 = parcel.readInt();
                    boolean readBoolean94 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableWearablePolicy = enableWearablePolicy(contextInfo115, readInt13, readBoolean94);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableWearablePolicy);
                    return true;
                case 129:
                    ContextInfo contextInfo116 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWearablePolicyEnabled = isWearablePolicyEnabled(contextInfo116, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWearablePolicyEnabled);
                    return true;
                case 130:
                    ContextInfo contextInfo117 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean95 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowedFOTAVersion = setAllowedFOTAVersion(contextInfo117, readString, bundle, readBoolean95);
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
                    boolean readBoolean96 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isIrisCameraEnabled = isIrisCameraEnabled(contextInfo120, readBoolean96);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIrisCameraEnabled);
                    return true;
                case 134:
                    ContextInfo contextInfo121 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean97 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean irisCameraState = setIrisCameraState(contextInfo121, readBoolean97);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(irisCameraState);
                    return true;
                case 135:
                    ContextInfo contextInfo122 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean98 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowDataSaving = allowDataSaving(contextInfo122, readBoolean98);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowDataSaving);
                    return true;
                case 136:
                    boolean isDataSavingAllowed = isDataSavingAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDataSavingAllowed);
                    return true;
                case 137:
                    ContextInfo contextInfo123 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean99 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowPowerSavingMode = allowPowerSavingMode(contextInfo123, readBoolean99);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowPowerSavingMode);
                    return true;
                case 138:
                    ContextInfo contextInfo124 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPowerSavingModeAllowed = isPowerSavingModeAllowed(contextInfo124);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPowerSavingModeAllowed);
                    return true;
                case 139:
                    ContextInfo contextInfo125 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean usbExceptionList = setUsbExceptionList(contextInfo125, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbExceptionList);
                    return true;
                case 140:
                    int usbExceptionList2 = getUsbExceptionList();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbExceptionList2);
                    return true;
                case 141:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    systemReady(readInt16);
                    parcel2.writeNoException();
                    return true;
                case 142:
                    ContextInfo contextInfo126 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean100 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowFaceRecognitionEvenCameraBlocked = allowFaceRecognitionEvenCameraBlocked(contextInfo126, readBoolean100);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowFaceRecognitionEvenCameraBlocked);
                    return true;
                case 143:
                    ContextInfo contextInfo127 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isFaceRecognitionAllowedEvenCameraBlocked = isFaceRecognitionAllowedEvenCameraBlocked(contextInfo127);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFaceRecognitionAllowedEvenCameraBlocked);
                    return true;
                case 144:
                    ContextInfo contextInfo128 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean101 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowLocalContactStorage = allowLocalContactStorage(contextInfo128, readBoolean101);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowLocalContactStorage);
                    return true;
                case 145:
                    ContextInfo contextInfo129 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isLocalContactStorageAllowed = isLocalContactStorageAllowed(contextInfo129);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLocalContactStorageAllowed);
                    return true;
                case 146:
                    ContextInfo contextInfo130 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean102 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean knoxDelegationEnabled = setKnoxDelegationEnabled(contextInfo130, readBoolean102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(knoxDelegationEnabled);
                    return true;
                case 147:
                    ContextInfo contextInfo131 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isKnoxDelegationEnabled = isKnoxDelegationEnabled(contextInfo131);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKnoxDelegationEnabled);
                    return true;
                case 148:
                    ContextInfo contextInfo132 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean103 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowIntelligenceOnlineProcessing = allowIntelligenceOnlineProcessing(contextInfo132, readBoolean103);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowIntelligenceOnlineProcessing);
                    return true;
                case 149:
                    ContextInfo contextInfo133 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isIntelligenceOnlineProcessingAllowed = isIntelligenceOnlineProcessingAllowed(contextInfo133);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIntelligenceOnlineProcessingAllowed);
                    return true;
                case 150:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean checkIfRestrictionWasSetByKC = checkIfRestrictionWasSetByKC(readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkIfRestrictionWasSetByKC);
                    return true;
                case 151:
                    String kcActionDisabledText = getKcActionDisabledText();
                    parcel2.writeNoException();
                    parcel2.writeString(kcActionDisabledText);
                    return true;
                case 152:
                    String readString3 = parcel.readString();
                    boolean readBoolean104 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateUserRestrictionsByKC(readString3, readBoolean104);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    ContextInfo contextInfo134 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean105 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowActivationLock = allowActivationLock(contextInfo134, readBoolean105);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowActivationLock);
                    return true;
                case 154:
                    ContextInfo contextInfo135 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean106 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isActivationLockAllowed = isActivationLockAllowed(contextInfo135, readBoolean106);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivationLockAllowed);
                    return true;
                case 155:
                    ContextInfo contextInfo136 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isNonTrustedAppInstallBlocked = isNonTrustedAppInstallBlocked(contextInfo136);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNonTrustedAppInstallBlocked);
                    return true;
                case 156:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNonTrustedAppInstallBlockedAsUser = isNonTrustedAppInstallBlockedAsUser(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNonTrustedAppInstallBlockedAsUser);
                    return true;
                case 157:
                    ContextInfo contextInfo137 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean107 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUsbMassStorageEnabled = isUsbMassStorageEnabled(contextInfo137, readBoolean107);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbMassStorageEnabled);
                    return true;
                case 158:
                    ContextInfo contextInfo138 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean108 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUsbKiesAvailable = isUsbKiesAvailable(contextInfo138, readBoolean108);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbKiesAvailable);
                    return true;
                case 159:
                    ContextInfo contextInfo139 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean109 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nonTrustedAppInstallBlock = setNonTrustedAppInstallBlock(contextInfo139, readBoolean109);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nonTrustedAppInstallBlock);
                    return true;
                case 160:
                    ContextInfo contextInfo140 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean110 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbKiesAvailability = setUsbKiesAvailability(contextInfo140, readBoolean110);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbKiesAvailability);
                    return true;
                case 161:
                    ContextInfo contextInfo141 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean111 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean usbMassStorage = setUsbMassStorage(contextInfo141, readBoolean111);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usbMassStorage);
                    return true;
                case 162:
                    ContextInfo contextInfo142 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean112 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isCCModeEnabled = isCCModeEnabled(contextInfo142, readBoolean112);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCCModeEnabled);
                    return true;
                case 163:
                    int readInt18 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean checkPackageSource = checkPackageSource(readInt18, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkPackageSource);
                    return true;
                case 164:
                    int readInt19 = parcel.readInt();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean checkAdminActivationEnabled = checkAdminActivationEnabled(readInt19, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkAdminActivationEnabled);
                    return true;
                case 165:
                    int readInt20 = parcel.readInt();
                    boolean readBoolean113 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isNewAdminInstallationEnabledAsUser = isNewAdminInstallationEnabledAsUser(readInt20, readBoolean113);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNewAdminInstallationEnabledAsUser);
                    return true;
                case 166:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showRestrictionToast(readString6);
                    parcel2.writeNoException();
                    return true;
                case 167:
                    ContextInfo contextInfo143 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enableConstrainedState = enableConstrainedState(contextInfo143, readString7, readString8, readString9, readString10, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableConstrainedState);
                    return true;
                case 168:
                    ContextInfo contextInfo144 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean disableConstrainedState = disableConstrainedState(contextInfo144);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableConstrainedState);
                    return true;
                case 169:
                    int constrainedState = getConstrainedState();
                    parcel2.writeNoException();
                    parcel2.writeInt(constrainedState);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IRestrictionPolicy {
        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean addNewAdminActivationAppWhiteList(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowActivationLock(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowAirplaneMode(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowAudioRecord(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowBackgroundProcessLimit(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowClipboardShare(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowDataSaving(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowDeveloperMode(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowFaceRecognitionEvenCameraBlocked(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowFactoryReset(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowFastEncryption(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowFirmwareAutoUpdate(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowFirmwareRecovery(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowGoogleAccountsAutoSync(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowGoogleCrashReport(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowIntelligenceOnlineProcessing(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowKillingActivitiesOnLeave(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowLocalContactStorage(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowLockScreenView(ContextInfo contextInfo, int i, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowOTAUpgrade(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowPowerOff(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowPowerSavingMode(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowSDCardMove(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowSDCardWrite(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowSVoice(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowSafeMode(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowScreenPinning(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowSettingsChanges(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowShareList(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowSmartClipMode(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowStatusBarExpansion(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowStopSystemApp(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowUsbHostStorage(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowUserMobileDataLimit(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowVideoRecord(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowVpn(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowWallpaperChange(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean allowWifiDirect(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean checkAdminActivationEnabled(int i, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean checkIfRestrictionWasSetByKC(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean checkPackageSource(int i, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean clearNewAdminActivationAppWhiteList(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean disableConstrainedState(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean enableODETrustedBootVerification(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean enableWearablePolicy(ContextInfo contextInfo, int i, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final List<String> getAllowedFOTAInfo(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final String getAllowedFOTAVersion(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final int getCCModeState(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final int getConstrainedState() {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final String getKcActionDisabledText() {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final List<String> getNewAdminActivationAppWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final int getUsbExceptionList() {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isActivationLockAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isAirplaneModeAllowed(boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isAudioRecordAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isBackgroundDataEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isBackgroundProcessLimitAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isBackupAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isBluetoothTetheringEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isCCModeEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isCCModeSupported(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isCameraEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isCellularDataAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isClipboardAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isClipboardAllowedAsUser(boolean z, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isClipboardShareAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isClipboardShareAllowedAsUser(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isDataSavingAllowed() {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isDeveloperModeAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isFaceRecognitionAllowedEvenCameraBlocked(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isFactoryResetAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isFastEncryptionAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isFirmwareAutoUpdateAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isFirmwareRecoveryAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isGoogleAccountsAutoSyncAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isGoogleAccountsAutoSyncAllowedAsUser(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isGoogleCrashReportAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isGoogleCrashReportAllowedAsUser(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isHeadphoneEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isHomeKeyEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isIntelligenceOnlineProcessingAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isIrisCameraEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isKillingActivitiesOnLeaveAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isKnoxDelegationEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isLocalContactStorageAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isLockScreenEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isLockScreenViewAllowed(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isMicrophoneEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isMicrophoneEnabledAsUser(boolean z, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isMockLocationEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isNewAdminActivationEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isNewAdminInstallationEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isNewAdminInstallationEnabledAsUser(int i, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isNonMarketAppAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isNonTrustedAppInstallBlocked(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isNonTrustedAppInstallBlockedAsUser(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isODETrustedBootVerificationEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isOTAUpgradeAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isPowerOffAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isPowerSavingModeAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSDCardMoveAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSDCardWriteAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSVoiceAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSVoiceAllowedAsUser(boolean z, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSafeModeAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isScreenCaptureEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isScreenCaptureEnabledEx(int i, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isScreenCaptureEnabledInternal(boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isScreenPinningAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSdCardEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSettingsChangesAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSettingsChangesAllowedAsUser(boolean z, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isShareListAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isShareListAllowedAsUser(int i, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSmartClipModeAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isSmartClipModeAllowedInternal(boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isStatusBarExpansionAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isStatusBarExpansionAllowedAsUser(boolean z, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isStopSystemAppAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isTetheringEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isUsbDebuggingEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isUsbHostStorageAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isUsbKiesAvailable(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isUsbMassStorageEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isUsbMediaPlayerAvailable(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isUsbTetheringEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isUseSecureKeypadEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isUserMobileDataLimitAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isVideoRecordAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isVpnAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isWallpaperChangeAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isWearablePolicyEnabled(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isWifiDirectAllowed(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean isWifiTetheringEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean preventNewAdminActivation(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean preventNewAdminInstallation(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setAllowNonMarketApps(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setAllowedFOTAVersion(ContextInfo contextInfo, String str, Bundle bundle, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setBackgroundData(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setBackup(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setBluetoothTethering(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setCCMode(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setCCModeOnlyForCallerSystem(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setCamera(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setCellularData(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setClipboardEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setHeadphoneState(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setHomeKeyState(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setIrisCameraState(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setKnoxDelegationEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setLockScreenState(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setMicrophoneState(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setMockLocation(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setNonTrustedAppInstallBlock(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setScreenCapture(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setSdCardState(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setTethering(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setUsbDebuggingEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setUsbExceptionList(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setUsbKiesAvailability(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setUsbMassStorage(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setUsbMediaPlayerAvailability(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setUsbTethering(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setUseSecureKeypad(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final boolean setWifiTethering(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final void showRestrictionToast(String str) {
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final void systemReady(int i) {
        }

        @Override // com.samsung.android.knox.restriction.IRestrictionPolicy
        public final void updateUserRestrictionsByKC(String str, boolean z) {
        }
    }
}
