package com.samsung.android.knox.container;

import android.content.pm.Signature;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IEnterpriseContainerCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IKnoxContainerManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.container.IKnoxContainerManager";

    boolean addConfigurationType(ContextInfo contextInfo, List list);

    boolean addHomeShortcutToPersonal(ContextInfo contextInfo, String str, String str2);

    boolean addNetworkSSID(ContextInfo contextInfo, String str);

    int addPackageToExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity);

    int addPackageToExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity);

    int addPackageToInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity);

    boolean addSecureKeyPad(int i, String str);

    boolean allowLayoutSwitching(ContextInfo contextInfo, boolean z);

    boolean cancelCreateContainer(ContainerCreationParams containerCreationParams);

    int checkProvisioningPreCondition(String str, int i);

    boolean clearNetworkSSID(ContextInfo contextInfo);

    int clearPackagesFromExternalStorageBlackList(ContextInfo contextInfo);

    int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo);

    int createContainer(ContextInfo contextInfo, CreationParams creationParams, int i);

    int createContainerInternal(ContainerCreationParams containerCreationParams);

    boolean createContainerMarkSuccess(ContainerCreationParams containerCreationParams);

    int createContainerWithCallback(ContextInfo contextInfo, CreationParams creationParams, int i, IEnterpriseContainerCallback iEnterpriseContainerCallback);

    boolean deleteHomeShortcutFromPersonal(ContextInfo contextInfo, String str, String str2);

    void doSelfUninstall();

    boolean enableBluetooth(ContextInfo contextInfo, boolean z, Bundle bundle);

    boolean enableExternalStorage(ContextInfo contextInfo, boolean z);

    boolean enableNFC(ContextInfo contextInfo, boolean z, Bundle bundle);

    boolean enableUsbAccess(ContextInfo contextInfo, boolean z, Bundle bundle);

    boolean enforceMultifactorAuthentication(ContextInfo contextInfo, boolean z);

    int forceResetPassword(ContextInfo contextInfo, String str, int i);

    Bundle getAppSeparationConfig();

    List getConfigurationType(ContextInfo contextInfo, int i);

    List getConfigurationTypeByName(ContextInfo contextInfo, String str);

    List getConfigurationTypes(ContextInfo contextInfo);

    ContainerCreationParams getContainerCreationParams(int i);

    List getContainers(ContextInfo contextInfo);

    String getCustomResource(int i, String str);

    List getDefaultConfigurationTypes();

    boolean getEnforceAuthForContainer(ContextInfo contextInfo);

    Bundle getFIDOInfo(ContextInfo contextInfo);

    long getHibernationTimeout(ContextInfo contextInfo);

    List<String> getKnoxCustomBadgePolicy();

    List<String> getNetworkSSID(ContextInfo contextInfo);

    EnterpriseContainerObject[] getOwnContainers();

    Signature[] getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str);

    List<String> getPackagesFromExternalStorageBlackList(ContextInfo contextInfo);

    List<String> getPackagesFromExternalStorageWhiteList(ContextInfo contextInfo);

    List<String> getPackagesFromInstallWhiteList(ContextInfo contextInfo);

    Bundle getProvisioningState();

    List<String> getSecureKeyPad(int i);

    int getStatus(ContextInfo contextInfo);

    int getStatusInternal(int i);

    boolean isBluetoothEnabled(ContextInfo contextInfo);

    boolean isBluetoothEnabledBeforeFOTA(ContextInfo contextInfo);

    boolean isContactsSharingEnabled(ContextInfo contextInfo);

    boolean isEmergencyModeSupported();

    boolean isExternalStorageEnabled(ContextInfo contextInfo);

    boolean isLayoutSwitchingAllowed(ContextInfo contextInfo);

    boolean isMultifactorAuthenticationEnforced(ContextInfo contextInfo);

    boolean isNFCEnabled(ContextInfo contextInfo);

    boolean isPackageAllowedToAccessExternalSdcard(ContextInfo contextInfo, int i);

    boolean isPackageInInstallWhiteList(ContextInfo contextInfo, String str);

    boolean isResetContainerOnRebootEnabled(ContextInfo contextInfo);

    boolean isSettingsOptionEnabled(ContextInfo contextInfo, String str);

    boolean isSettingsOptionEnabledInternal(int i, String str, boolean z);

    boolean isUsbAccessEnabled(ContextInfo contextInfo);

    boolean lockContainer(ContextInfo contextInfo, String str);

    boolean registerBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2);

    boolean removeConfigurationType(ContextInfo contextInfo, String str);

    int removeContainer(ContextInfo contextInfo);

    int removeContainerInternal(int i);

    boolean removeNetworkSSID(ContextInfo contextInfo, String str);

    int removePackageFromExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity);

    int removePackageFromExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity);

    int removePackageFromInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity);

    boolean removeSecureKeyPad(int i, String str);

    boolean resetContainerOnReboot(ContextInfo contextInfo, boolean z);

    boolean setAppSeparationCoexistentApps(ContextInfo contextInfo, List<String> list);

    boolean setAppSeparationConfig(ContextInfo contextInfo, Bundle bundle);

    boolean setAppSeparationWhitelistedApps(ContextInfo contextInfo, List<String> list);

    boolean setContactsSharingEnabled(ContextInfo contextInfo, boolean z);

    int setCustomResource(int i, ContextInfo contextInfo, Bundle bundle);

    boolean setEnforceAuthForContainer(ContextInfo contextInfo, boolean z);

    boolean setFIDOInfo(ContextInfo contextInfo, Bundle bundle);

    boolean setHibernationTimeout(ContextInfo contextInfo, long j);

    boolean setSettingsOptionEnabled(ContextInfo contextInfo, String str, boolean z);

    boolean unlockContainer(ContextInfo contextInfo);

    boolean unregisterBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2);

    boolean updateProvisioningState(Bundle bundle);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IKnoxContainerManager {
        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean addConfigurationType(ContextInfo contextInfo, List list) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean addHomeShortcutToPersonal(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean addNetworkSSID(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int addPackageToExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int addPackageToExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int addPackageToInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean addSecureKeyPad(int i, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean allowLayoutSwitching(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean cancelCreateContainer(ContainerCreationParams containerCreationParams) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int checkProvisioningPreCondition(String str, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean clearNetworkSSID(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int clearPackagesFromExternalStorageBlackList(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int createContainer(ContextInfo contextInfo, CreationParams creationParams, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int createContainerInternal(ContainerCreationParams containerCreationParams) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean createContainerMarkSuccess(ContainerCreationParams containerCreationParams) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int createContainerWithCallback(ContextInfo contextInfo, CreationParams creationParams, int i, IEnterpriseContainerCallback iEnterpriseContainerCallback) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean deleteHomeShortcutFromPersonal(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean enableBluetooth(ContextInfo contextInfo, boolean z, Bundle bundle) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean enableExternalStorage(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean enableNFC(ContextInfo contextInfo, boolean z, Bundle bundle) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean enableUsbAccess(ContextInfo contextInfo, boolean z, Bundle bundle) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean enforceMultifactorAuthentication(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int forceResetPassword(ContextInfo contextInfo, String str, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final Bundle getAppSeparationConfig() {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List getConfigurationType(ContextInfo contextInfo, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List getConfigurationTypeByName(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List getConfigurationTypes(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final ContainerCreationParams getContainerCreationParams(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List getContainers(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final String getCustomResource(int i, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List getDefaultConfigurationTypes() {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean getEnforceAuthForContainer(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final Bundle getFIDOInfo(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final long getHibernationTimeout(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List<String> getKnoxCustomBadgePolicy() {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List<String> getNetworkSSID(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final EnterpriseContainerObject[] getOwnContainers() {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final Signature[] getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List<String> getPackagesFromExternalStorageBlackList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List<String> getPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List<String> getPackagesFromInstallWhiteList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final Bundle getProvisioningState() {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final List<String> getSecureKeyPad(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int getStatus(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int getStatusInternal(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isBluetoothEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isBluetoothEnabledBeforeFOTA(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isContactsSharingEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isEmergencyModeSupported() {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isExternalStorageEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isLayoutSwitchingAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isMultifactorAuthenticationEnforced(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isNFCEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isPackageAllowedToAccessExternalSdcard(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isPackageInInstallWhiteList(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isResetContainerOnRebootEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isSettingsOptionEnabled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isSettingsOptionEnabledInternal(int i, String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean isUsbAccessEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean lockContainer(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean registerBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean removeConfigurationType(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int removeContainer(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int removeContainerInternal(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean removeNetworkSSID(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int removePackageFromExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int removePackageFromExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int removePackageFromInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean removeSecureKeyPad(int i, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean resetContainerOnReboot(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean setAppSeparationCoexistentApps(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean setAppSeparationConfig(ContextInfo contextInfo, Bundle bundle) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean setAppSeparationWhitelistedApps(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean setContactsSharingEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final int setCustomResource(int i, ContextInfo contextInfo, Bundle bundle) {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean setEnforceAuthForContainer(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean setFIDOInfo(ContextInfo contextInfo, Bundle bundle) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean setHibernationTimeout(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean setSettingsOptionEnabled(ContextInfo contextInfo, String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean unlockContainer(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean unregisterBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final boolean updateProvisioningState(Bundle bundle) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public final void doSelfUninstall() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IKnoxContainerManager {
        public static final int TRANSACTION_addConfigurationType = 10;
        public static final int TRANSACTION_addHomeShortcutToPersonal = 50;
        public static final int TRANSACTION_addNetworkSSID = 43;
        public static final int TRANSACTION_addPackageToExternalStorageBlackList = 71;
        public static final int TRANSACTION_addPackageToExternalStorageWhiteList = 72;
        public static final int TRANSACTION_addPackageToInstallWhiteList = 65;
        public static final int TRANSACTION_addSecureKeyPad = 62;
        public static final int TRANSACTION_allowLayoutSwitching = 78;
        public static final int TRANSACTION_cancelCreateContainer = 3;
        public static final int TRANSACTION_checkProvisioningPreCondition = 58;
        public static final int TRANSACTION_clearNetworkSSID = 46;
        public static final int TRANSACTION_clearPackagesFromExternalStorageBlackList = 68;
        public static final int TRANSACTION_clearPackagesFromExternalStorageWhiteList = 76;
        public static final int TRANSACTION_createContainer = 1;
        public static final int TRANSACTION_createContainerInternal = 2;
        public static final int TRANSACTION_createContainerMarkSuccess = 4;
        public static final int TRANSACTION_createContainerWithCallback = 21;
        public static final int TRANSACTION_deleteHomeShortcutFromPersonal = 51;
        public static final int TRANSACTION_doSelfUninstall = 42;
        public static final int TRANSACTION_enableBluetooth = 31;
        public static final int TRANSACTION_enableExternalStorage = 40;
        public static final int TRANSACTION_enableNFC = 34;
        public static final int TRANSACTION_enableUsbAccess = 36;
        public static final int TRANSACTION_enforceMultifactorAuthentication = 24;
        public static final int TRANSACTION_forceResetPassword = 23;
        public static final int TRANSACTION_getAppSeparationConfig = 81;
        public static final int TRANSACTION_getConfigurationType = 11;
        public static final int TRANSACTION_getConfigurationTypeByName = 8;
        public static final int TRANSACTION_getConfigurationTypes = 9;
        public static final int TRANSACTION_getContainerCreationParams = 18;
        public static final int TRANSACTION_getContainers = 7;
        public static final int TRANSACTION_getCustomResource = 57;
        public static final int TRANSACTION_getDefaultConfigurationTypes = 12;
        public static final int TRANSACTION_getEnforceAuthForContainer = 16;
        public static final int TRANSACTION_getFIDOInfo = 56;
        public static final int TRANSACTION_getHibernationTimeout = 27;
        public static final int TRANSACTION_getKnoxCustomBadgePolicy = 53;
        public static final int TRANSACTION_getNetworkSSID = 45;
        public static final int TRANSACTION_getOwnContainers = 22;
        public static final int TRANSACTION_getPackageSignaturesFromExternalStorageWhiteList = 75;
        public static final int TRANSACTION_getPackagesFromExternalStorageBlackList = 69;
        public static final int TRANSACTION_getPackagesFromExternalStorageWhiteList = 74;
        public static final int TRANSACTION_getPackagesFromInstallWhiteList = 67;
        public static final int TRANSACTION_getProvisioningState = 60;
        public static final int TRANSACTION_getSecureKeyPad = 61;
        public static final int TRANSACTION_getStatus = 13;
        public static final int TRANSACTION_getStatusInternal = 79;
        public static final int TRANSACTION_isBluetoothEnabled = 32;
        public static final int TRANSACTION_isBluetoothEnabledBeforeFOTA = 33;
        public static final int TRANSACTION_isContactsSharingEnabled = 39;
        public static final int TRANSACTION_isEmergencyModeSupported = 54;
        public static final int TRANSACTION_isExternalStorageEnabled = 41;
        public static final int TRANSACTION_isLayoutSwitchingAllowed = 77;
        public static final int TRANSACTION_isMultifactorAuthenticationEnforced = 25;
        public static final int TRANSACTION_isNFCEnabled = 35;
        public static final int TRANSACTION_isPackageAllowedToAccessExternalSdcard = 52;
        public static final int TRANSACTION_isPackageInInstallWhiteList = 64;
        public static final int TRANSACTION_isResetContainerOnRebootEnabled = 30;
        public static final int TRANSACTION_isSettingsOptionEnabled = 48;
        public static final int TRANSACTION_isSettingsOptionEnabledInternal = 49;
        public static final int TRANSACTION_isUsbAccessEnabled = 37;
        public static final int TRANSACTION_lockContainer = 14;
        public static final int TRANSACTION_registerBroadcastReceiverIntent = 19;
        public static final int TRANSACTION_removeConfigurationType = 26;
        public static final int TRANSACTION_removeContainer = 5;
        public static final int TRANSACTION_removeContainerInternal = 6;
        public static final int TRANSACTION_removeNetworkSSID = 44;
        public static final int TRANSACTION_removePackageFromExternalStorageBlackList = 70;
        public static final int TRANSACTION_removePackageFromExternalStorageWhiteList = 73;
        public static final int TRANSACTION_removePackageFromInstallWhiteList = 66;
        public static final int TRANSACTION_removeSecureKeyPad = 63;
        public static final int TRANSACTION_resetContainerOnReboot = 29;
        public static final int TRANSACTION_setAppSeparationCoexistentApps = 84;
        public static final int TRANSACTION_setAppSeparationConfig = 82;
        public static final int TRANSACTION_setAppSeparationWhitelistedApps = 83;
        public static final int TRANSACTION_setContactsSharingEnabled = 38;
        public static final int TRANSACTION_setCustomResource = 80;
        public static final int TRANSACTION_setEnforceAuthForContainer = 17;
        public static final int TRANSACTION_setFIDOInfo = 55;
        public static final int TRANSACTION_setHibernationTimeout = 28;
        public static final int TRANSACTION_setSettingsOptionEnabled = 47;
        public static final int TRANSACTION_unlockContainer = 15;
        public static final int TRANSACTION_unregisterBroadcastReceiverIntent = 20;
        public static final int TRANSACTION_updateProvisioningState = 59;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IKnoxContainerManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean addConfigurationType(ContextInfo contextInfo, List list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeList(list);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean addHomeShortcutToPersonal(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean addNetworkSSID(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int addPackageToExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int addPackageToExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int addPackageToInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean addSecureKeyPad(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean allowLayoutSwitching(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(78, obtain, obtain2, 0);
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

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean cancelCreateContainer(ContainerCreationParams containerCreationParams) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(containerCreationParams, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int checkProvisioningPreCondition(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean clearNetworkSSID(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int clearPackagesFromExternalStorageBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int createContainer(ContextInfo contextInfo, CreationParams creationParams, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(creationParams, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int createContainerInternal(ContainerCreationParams containerCreationParams) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(containerCreationParams, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean createContainerMarkSuccess(ContainerCreationParams containerCreationParams) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(containerCreationParams, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int createContainerWithCallback(ContextInfo contextInfo, CreationParams creationParams, int i, IEnterpriseContainerCallback iEnterpriseContainerCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(creationParams, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iEnterpriseContainerCallback);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean deleteHomeShortcutFromPersonal(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final void doSelfUninstall() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean enableBluetooth(ContextInfo contextInfo, boolean z, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean enableExternalStorage(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean enableNFC(ContextInfo contextInfo, boolean z, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean enableUsbAccess(ContextInfo contextInfo, boolean z, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean enforceMultifactorAuthentication(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int forceResetPassword(ContextInfo contextInfo, String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final Bundle getAppSeparationConfig() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List getConfigurationType(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List getConfigurationTypeByName(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List getConfigurationTypes(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final ContainerCreationParams getContainerCreationParams(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContainerCreationParams) obtain2.readTypedObject(ContainerCreationParams.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List getContainers(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final String getCustomResource(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List getDefaultConfigurationTypes() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean getEnforceAuthForContainer(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final Bundle getFIDOInfo(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final long getHibernationTimeout(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IKnoxContainerManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List<String> getKnoxCustomBadgePolicy() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List<String> getNetworkSSID(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final EnterpriseContainerObject[] getOwnContainers() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseContainerObject[]) obtain2.createTypedArray(EnterpriseContainerObject.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final Signature[] getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Signature[]) obtain2.createTypedArray(Signature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List<String> getPackagesFromExternalStorageBlackList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List<String> getPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List<String> getPackagesFromInstallWhiteList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final Bundle getProvisioningState() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final List<String> getSecureKeyPad(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int getStatus(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int getStatusInternal(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isBluetoothEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isBluetoothEnabledBeforeFOTA(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isContactsSharingEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isEmergencyModeSupported() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isExternalStorageEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isLayoutSwitchingAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isMultifactorAuthenticationEnforced(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isNFCEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isPackageAllowedToAccessExternalSdcard(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isPackageInInstallWhiteList(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isResetContainerOnRebootEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isSettingsOptionEnabled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isSettingsOptionEnabledInternal(int i, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean isUsbAccessEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean lockContainer(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean registerBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean removeConfigurationType(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int removeContainer(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int removeContainerInternal(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean removeNetworkSSID(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int removePackageFromExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int removePackageFromExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int removePackageFromInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean removeSecureKeyPad(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean resetContainerOnReboot(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean setAppSeparationCoexistentApps(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean setAppSeparationConfig(ContextInfo contextInfo, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean setAppSeparationWhitelistedApps(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean setContactsSharingEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final int setCustomResource(int i, ContextInfo contextInfo, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean setEnforceAuthForContainer(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean setFIDOInfo(ContextInfo contextInfo, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean setHibernationTimeout(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean setSettingsOptionEnabled(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean unlockContainer(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean unregisterBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public final boolean updateProvisioningState(Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxContainerManager.DESCRIPTOR);
        }

        public static IKnoxContainerManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxContainerManager.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxContainerManager)) ? new Proxy(iBinder) : (IKnoxContainerManager) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxContainerManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxContainerManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CreationParams creationParams = (CreationParams) parcel.readTypedObject(CreationParams.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createContainer = createContainer(contextInfo, creationParams, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(createContainer);
                    return true;
                case 2:
                    ContainerCreationParams containerCreationParams = (ContainerCreationParams) parcel.readTypedObject(ContainerCreationParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    int createContainerInternal = createContainerInternal(containerCreationParams);
                    parcel2.writeNoException();
                    parcel2.writeInt(createContainerInternal);
                    return true;
                case 3:
                    ContainerCreationParams containerCreationParams2 = (ContainerCreationParams) parcel.readTypedObject(ContainerCreationParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean cancelCreateContainer = cancelCreateContainer(containerCreationParams2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cancelCreateContainer);
                    return true;
                case 4:
                    ContainerCreationParams containerCreationParams3 = (ContainerCreationParams) parcel.readTypedObject(ContainerCreationParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean createContainerMarkSuccess = createContainerMarkSuccess(containerCreationParams3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(createContainerMarkSuccess);
                    return true;
                case 5:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removeContainer = removeContainer(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeContainer);
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int removeContainerInternal = removeContainerInternal(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeContainerInternal);
                    return true;
                case 7:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List containers = getContainers(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeList(containers);
                    return true;
                case 8:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List configurationTypeByName = getConfigurationTypeByName(contextInfo4, readString);
                    parcel2.writeNoException();
                    parcel2.writeList(configurationTypeByName);
                    return true;
                case 9:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List configurationTypes = getConfigurationTypes(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeList(configurationTypes);
                    return true;
                case 10:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    boolean addConfigurationType = addConfigurationType(contextInfo6, readArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addConfigurationType);
                    return true;
                case 11:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List configurationType = getConfigurationType(contextInfo7, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeList(configurationType);
                    return true;
                case 12:
                    List defaultConfigurationTypes = getDefaultConfigurationTypes();
                    parcel2.writeNoException();
                    parcel2.writeList(defaultConfigurationTypes);
                    return true;
                case 13:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int status = getStatus(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeInt(status);
                    return true;
                case 14:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean lockContainer = lockContainer(contextInfo9, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockContainer);
                    return true;
                case 15:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean unlockContainer = unlockContainer(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unlockContainer);
                    return true;
                case 16:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enforceAuthForContainer = getEnforceAuthForContainer(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enforceAuthForContainer);
                    return true;
                case 17:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enforceAuthForContainer2 = setEnforceAuthForContainer(contextInfo12, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enforceAuthForContainer2);
                    return true;
                case 18:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ContainerCreationParams containerCreationParams4 = getContainerCreationParams(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(containerCreationParams4, 1);
                    return true;
                case 19:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean registerBroadcastReceiverIntent = registerBroadcastReceiverIntent(contextInfo13, readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerBroadcastReceiverIntent);
                    return true;
                case 20:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean unregisterBroadcastReceiverIntent = unregisterBroadcastReceiverIntent(contextInfo14, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterBroadcastReceiverIntent);
                    return true;
                case 21:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CreationParams creationParams2 = (CreationParams) parcel.readTypedObject(CreationParams.CREATOR);
                    int readInt5 = parcel.readInt();
                    IEnterpriseContainerCallback asInterface = IEnterpriseContainerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int createContainerWithCallback = createContainerWithCallback(contextInfo15, creationParams2, readInt5, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(createContainerWithCallback);
                    return true;
                case 22:
                    EnterpriseContainerObject[] ownContainers = getOwnContainers();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(ownContainers, 1);
                    return true;
                case 23:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int forceResetPassword = forceResetPassword(contextInfo16, readString7, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceResetPassword);
                    return true;
                case 24:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enforceMultifactorAuthentication = enforceMultifactorAuthentication(contextInfo17, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enforceMultifactorAuthentication);
                    return true;
                case 25:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMultifactorAuthenticationEnforced = isMultifactorAuthenticationEnforced(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMultifactorAuthenticationEnforced);
                    return true;
                case 26:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeConfigurationType = removeConfigurationType(contextInfo19, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeConfigurationType);
                    return true;
                case 27:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long hibernationTimeout = getHibernationTimeout(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeLong(hibernationTimeout);
                    return true;
                case 28:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean hibernationTimeout2 = setHibernationTimeout(contextInfo21, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hibernationTimeout2);
                    return true;
                case 29:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean resetContainerOnReboot = resetContainerOnReboot(contextInfo22, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetContainerOnReboot);
                    return true;
                case 30:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isResetContainerOnRebootEnabled = isResetContainerOnRebootEnabled(contextInfo23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isResetContainerOnRebootEnabled);
                    return true;
                case 31:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enableBluetooth = enableBluetooth(contextInfo24, readBoolean4, bundle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableBluetooth);
                    return true;
                case 32:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothEnabled = isBluetoothEnabled(contextInfo25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothEnabled);
                    return true;
                case 33:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothEnabledBeforeFOTA = isBluetoothEnabledBeforeFOTA(contextInfo26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothEnabledBeforeFOTA);
                    return true;
                case 34:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enableNFC = enableNFC(contextInfo27, readBoolean5, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableNFC);
                    return true;
                case 35:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isNFCEnabled = isNFCEnabled(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNFCEnabled);
                    return true;
                case 36:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enableUsbAccess = enableUsbAccess(contextInfo29, readBoolean6, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableUsbAccess);
                    return true;
                case 37:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isUsbAccessEnabled = isUsbAccessEnabled(contextInfo30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbAccessEnabled);
                    return true;
                case 38:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean contactsSharingEnabled = setContactsSharingEnabled(contextInfo31, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(contactsSharingEnabled);
                    return true;
                case 39:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isContactsSharingEnabled = isContactsSharingEnabled(contextInfo32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isContactsSharingEnabled);
                    return true;
                case 40:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableExternalStorage = enableExternalStorage(contextInfo33, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableExternalStorage);
                    return true;
                case 41:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isExternalStorageEnabled = isExternalStorageEnabled(contextInfo34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExternalStorageEnabled);
                    return true;
                case 42:
                    doSelfUninstall();
                    parcel2.writeNoException();
                    return true;
                case 43:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addNetworkSSID = addNetworkSSID(contextInfo35, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNetworkSSID);
                    return true;
                case 44:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeNetworkSSID = removeNetworkSSID(contextInfo36, readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeNetworkSSID);
                    return true;
                case 45:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> networkSSID = getNetworkSSID(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeStringList(networkSSID);
                    return true;
                case 46:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearNetworkSSID = clearNetworkSSID(contextInfo38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearNetworkSSID);
                    return true;
                case 47:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString11 = parcel.readString();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean settingsOptionEnabled = setSettingsOptionEnabled(contextInfo39, readString11, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(settingsOptionEnabled);
                    return true;
                case 48:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSettingsOptionEnabled = isSettingsOptionEnabled(contextInfo40, readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSettingsOptionEnabled);
                    return true;
                case 49:
                    int readInt7 = parcel.readInt();
                    String readString13 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isSettingsOptionEnabledInternal = isSettingsOptionEnabledInternal(readInt7, readString13, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSettingsOptionEnabledInternal);
                    return true;
                case 50:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addHomeShortcutToPersonal = addHomeShortcutToPersonal(contextInfo41, readString14, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addHomeShortcutToPersonal);
                    return true;
                case 51:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteHomeShortcutFromPersonal = deleteHomeShortcutFromPersonal(contextInfo42, readString16, readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteHomeShortcutFromPersonal);
                    return true;
                case 52:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageAllowedToAccessExternalSdcard = isPackageAllowedToAccessExternalSdcard(contextInfo43, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageAllowedToAccessExternalSdcard);
                    return true;
                case 53:
                    List<String> knoxCustomBadgePolicy = getKnoxCustomBadgePolicy();
                    parcel2.writeNoException();
                    parcel2.writeStringList(knoxCustomBadgePolicy);
                    return true;
                case 54:
                    boolean isEmergencyModeSupported = isEmergencyModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEmergencyModeSupported);
                    return true;
                case 55:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean fIDOInfo = setFIDOInfo(contextInfo44, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(fIDOInfo);
                    return true;
                case 56:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle fIDOInfo2 = getFIDOInfo(contextInfo45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fIDOInfo2, 1);
                    return true;
                case 57:
                    int readInt9 = parcel.readInt();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String customResource = getCustomResource(readInt9, readString18);
                    parcel2.writeNoException();
                    parcel2.writeString(customResource);
                    return true;
                case 58:
                    String readString19 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkProvisioningPreCondition = checkProvisioningPreCondition(readString19, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkProvisioningPreCondition);
                    return true;
                case 59:
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateProvisioningState = updateProvisioningState(bundle5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateProvisioningState);
                    return true;
                case 60:
                    Bundle provisioningState = getProvisioningState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(provisioningState, 1);
                    return true;
                case 61:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> secureKeyPad = getSecureKeyPad(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeStringList(secureKeyPad);
                    return true;
                case 62:
                    int readInt12 = parcel.readInt();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addSecureKeyPad = addSecureKeyPad(readInt12, readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addSecureKeyPad);
                    return true;
                case 63:
                    int readInt13 = parcel.readInt();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeSecureKeyPad = removeSecureKeyPad(readInt13, readString21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeSecureKeyPad);
                    return true;
                case 64:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPackageInInstallWhiteList = isPackageInInstallWhiteList(contextInfo46, readString22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageInInstallWhiteList);
                    return true;
                case 65:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPackageToInstallWhiteList = addPackageToInstallWhiteList(contextInfo47, appIdentity);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackageToInstallWhiteList);
                    return true;
                case 66:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity2 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removePackageFromInstallWhiteList = removePackageFromInstallWhiteList(contextInfo48, appIdentity2);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePackageFromInstallWhiteList);
                    return true;
                case 67:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromInstallWhiteList = getPackagesFromInstallWhiteList(contextInfo49);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromInstallWhiteList);
                    return true;
                case 68:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int clearPackagesFromExternalStorageBlackList = clearPackagesFromExternalStorageBlackList(contextInfo50);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearPackagesFromExternalStorageBlackList);
                    return true;
                case 69:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromExternalStorageBlackList = getPackagesFromExternalStorageBlackList(contextInfo51);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromExternalStorageBlackList);
                    return true;
                case 70:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity3 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removePackageFromExternalStorageBlackList = removePackageFromExternalStorageBlackList(contextInfo52, appIdentity3);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePackageFromExternalStorageBlackList);
                    return true;
                case 71:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity4 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPackageToExternalStorageBlackList = addPackageToExternalStorageBlackList(contextInfo53, appIdentity4);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackageToExternalStorageBlackList);
                    return true;
                case 72:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity5 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPackageToExternalStorageWhiteList = addPackageToExternalStorageWhiteList(contextInfo54, appIdentity5);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPackageToExternalStorageWhiteList);
                    return true;
                case 73:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity6 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removePackageFromExternalStorageWhiteList = removePackageFromExternalStorageWhiteList(contextInfo55, appIdentity6);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePackageFromExternalStorageWhiteList);
                    return true;
                case 74:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromExternalStorageWhiteList = getPackagesFromExternalStorageWhiteList(contextInfo56);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromExternalStorageWhiteList);
                    return true;
                case 75:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Signature[] packageSignaturesFromExternalStorageWhiteList = getPackageSignaturesFromExternalStorageWhiteList(contextInfo57, readString23);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(packageSignaturesFromExternalStorageWhiteList, 1);
                    return true;
                case 76:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int clearPackagesFromExternalStorageWhiteList = clearPackagesFromExternalStorageWhiteList(contextInfo58);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearPackagesFromExternalStorageWhiteList);
                    return true;
                case 77:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isLayoutSwitchingAllowed = isLayoutSwitchingAllowed(contextInfo59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLayoutSwitchingAllowed);
                    return true;
                case 78:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowLayoutSwitching = allowLayoutSwitching(contextInfo60, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowLayoutSwitching);
                    return true;
                case 79:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int statusInternal = getStatusInternal(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeInt(statusInternal);
                    return true;
                case 80:
                    int readInt15 = parcel.readInt();
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int customResource2 = setCustomResource(readInt15, contextInfo61, bundle6);
                    parcel2.writeNoException();
                    parcel2.writeInt(customResource2);
                    return true;
                case 81:
                    Bundle appSeparationConfig = getAppSeparationConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appSeparationConfig, 1);
                    return true;
                case 82:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean appSeparationConfig2 = setAppSeparationConfig(contextInfo62, bundle7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appSeparationConfig2);
                    return true;
                case 83:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean appSeparationWhitelistedApps = setAppSeparationWhitelistedApps(contextInfo63, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appSeparationWhitelistedApps);
                    return true;
                case 84:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean appSeparationCoexistentApps = setAppSeparationCoexistentApps(contextInfo64, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appSeparationCoexistentApps);
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
}
