package com.samsung.android.knox.container;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IEnterpriseContainerCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IKnoxContainerManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.container.IKnoxContainerManager";

    boolean addConfigurationType(ContextInfo contextInfo, List list) throws RemoteException;

    boolean addHomeShortcutToPersonal(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean addNetworkSSID(ContextInfo contextInfo, String str) throws RemoteException;

    int addPackageToExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    int addPackageToExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    int addPackageToInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    boolean allowLayoutSwitching(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean cancelCreateContainer(ContainerCreationParams containerCreationParams) throws RemoteException;

    int checkProvisioningPreCondition(String str, int i) throws RemoteException;

    boolean clearNetworkSSID(ContextInfo contextInfo) throws RemoteException;

    int clearPackagesFromExternalStorageBlackList(ContextInfo contextInfo) throws RemoteException;

    int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) throws RemoteException;

    int createContainer(ContextInfo contextInfo, CreationParams creationParams, int i) throws RemoteException;

    int createContainerInternal(ContainerCreationParams containerCreationParams) throws RemoteException;

    boolean createContainerMarkSuccess(ContainerCreationParams containerCreationParams) throws RemoteException;

    int createContainerWithCallback(ContextInfo contextInfo, CreationParams creationParams, int i, IEnterpriseContainerCallback iEnterpriseContainerCallback) throws RemoteException;

    boolean deleteHomeShortcutFromPersonal(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    void doSelfUninstall() throws RemoteException;

    boolean enableBluetooth(ContextInfo contextInfo, boolean z, Bundle bundle) throws RemoteException;

    boolean enableExternalStorage(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableNFC(ContextInfo contextInfo, boolean z, Bundle bundle) throws RemoteException;

    boolean enableUsbAccess(ContextInfo contextInfo, boolean z, Bundle bundle) throws RemoteException;

    boolean enforceMultifactorAuthentication(ContextInfo contextInfo, boolean z) throws RemoteException;

    int forceResetPassword(ContextInfo contextInfo, String str, int i) throws RemoteException;

    Bundle getAppSeparationConfig() throws RemoteException;

    List getConfigurationType(ContextInfo contextInfo, int i) throws RemoteException;

    List getConfigurationTypeByName(ContextInfo contextInfo, String str) throws RemoteException;

    List getConfigurationTypes(ContextInfo contextInfo) throws RemoteException;

    ContainerCreationParams getContainerCreationParams(int i) throws RemoteException;

    List getContainers(ContextInfo contextInfo) throws RemoteException;

    String getCustomResource(int i, String str) throws RemoteException;

    List getDefaultConfigurationTypes() throws RemoteException;

    boolean getEnforceAuthForContainer(ContextInfo contextInfo) throws RemoteException;

    Bundle getFIDOInfo(ContextInfo contextInfo) throws RemoteException;

    long getHibernationTimeout(ContextInfo contextInfo) throws RemoteException;

    List<String> getKnoxCustomBadgePolicy() throws RemoteException;

    List<String> getNetworkSSID(ContextInfo contextInfo) throws RemoteException;

    EnterpriseContainerObject[] getOwnContainers() throws RemoteException;

    List<String> getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getPackagesFromExternalStorageBlackList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) throws RemoteException;

    List<String> getPackagesFromInstallWhiteList(ContextInfo contextInfo) throws RemoteException;

    Bundle getProvisioningState() throws RemoteException;

    int getStatus(ContextInfo contextInfo) throws RemoteException;

    int getStatusInternal(int i) throws RemoteException;

    boolean isBluetoothEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isBluetoothEnabledBeforeFOTA(ContextInfo contextInfo) throws RemoteException;

    boolean isContactsSharingEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isEmergencyModeSupported() throws RemoteException;

    boolean isExternalStorageEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isLayoutSwitchingAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isMultifactorAuthenticationEnforced(ContextInfo contextInfo) throws RemoteException;

    boolean isNFCEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isPackageAllowedToAccessExternalSdcard(ContextInfo contextInfo, int i) throws RemoteException;

    boolean isPackageInInstallWhiteList(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isResetContainerOnRebootEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isSettingsOptionEnabled(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isSettingsOptionEnabledInternal(int i, String str, boolean z) throws RemoteException;

    boolean isUsbAccessEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean lockContainer(ContextInfo contextInfo, String str) throws RemoteException;

    boolean registerBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean removeConfigurationType(ContextInfo contextInfo, String str) throws RemoteException;

    int removeContainer(ContextInfo contextInfo) throws RemoteException;

    int removeContainerInternal(int i) throws RemoteException;

    boolean removeNetworkSSID(ContextInfo contextInfo, String str) throws RemoteException;

    int removePackageFromExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    int removePackageFromExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    int removePackageFromInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException;

    boolean resetContainerOnReboot(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setAppSeparationCoexistentApps(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean setAppSeparationConfig(ContextInfo contextInfo, Bundle bundle) throws RemoteException;

    boolean setAppSeparationWhitelistedApps(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean setContactsSharingEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    int setCustomResource(int i, ContextInfo contextInfo, Bundle bundle) throws RemoteException;

    boolean setEnforceAuthForContainer(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setFIDOInfo(ContextInfo contextInfo, Bundle bundle) throws RemoteException;

    boolean setHibernationTimeout(ContextInfo contextInfo, long j) throws RemoteException;

    boolean setSettingsOptionEnabled(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean unlockContainer(ContextInfo contextInfo) throws RemoteException;

    boolean unregisterBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean updateProvisioningState(Bundle bundle) throws RemoteException;

    public class Default implements IKnoxContainerManager {
        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean addConfigurationType(ContextInfo contextInfo, List list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean addHomeShortcutToPersonal(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean addNetworkSSID(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int addPackageToExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int addPackageToExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int addPackageToInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean allowLayoutSwitching(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean cancelCreateContainer(ContainerCreationParams containerCreationParams) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int checkProvisioningPreCondition(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean clearNetworkSSID(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int clearPackagesFromExternalStorageBlackList(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int createContainer(ContextInfo contextInfo, CreationParams creationParams, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int createContainerInternal(ContainerCreationParams containerCreationParams) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean createContainerMarkSuccess(ContainerCreationParams containerCreationParams) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int createContainerWithCallback(ContextInfo contextInfo, CreationParams creationParams, int i, IEnterpriseContainerCallback iEnterpriseContainerCallback) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean deleteHomeShortcutFromPersonal(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean enableBluetooth(ContextInfo contextInfo, boolean z, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean enableExternalStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean enableNFC(ContextInfo contextInfo, boolean z, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean enableUsbAccess(ContextInfo contextInfo, boolean z, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean enforceMultifactorAuthentication(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int forceResetPassword(ContextInfo contextInfo, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public Bundle getAppSeparationConfig() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List getConfigurationType(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List getConfigurationTypeByName(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List getConfigurationTypes(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public ContainerCreationParams getContainerCreationParams(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List getContainers(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public String getCustomResource(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List getDefaultConfigurationTypes() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean getEnforceAuthForContainer(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public Bundle getFIDOInfo(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public long getHibernationTimeout(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List<String> getKnoxCustomBadgePolicy() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List<String> getNetworkSSID(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public EnterpriseContainerObject[] getOwnContainers() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List<String> getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List<String> getPackagesFromExternalStorageBlackList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List<String> getPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public List<String> getPackagesFromInstallWhiteList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public Bundle getProvisioningState() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int getStatus(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int getStatusInternal(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isBluetoothEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isBluetoothEnabledBeforeFOTA(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isContactsSharingEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isEmergencyModeSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isExternalStorageEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isLayoutSwitchingAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isMultifactorAuthenticationEnforced(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isNFCEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isPackageAllowedToAccessExternalSdcard(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isPackageInInstallWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isResetContainerOnRebootEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isSettingsOptionEnabled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isSettingsOptionEnabledInternal(int i, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean isUsbAccessEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean lockContainer(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean registerBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean removeConfigurationType(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int removeContainer(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int removeContainerInternal(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean removeNetworkSSID(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int removePackageFromExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int removePackageFromExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int removePackageFromInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean resetContainerOnReboot(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean setAppSeparationCoexistentApps(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean setAppSeparationConfig(ContextInfo contextInfo, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean setAppSeparationWhitelistedApps(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean setContactsSharingEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public int setCustomResource(int i, ContextInfo contextInfo, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean setEnforceAuthForContainer(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean setFIDOInfo(ContextInfo contextInfo, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean setHibernationTimeout(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean setSettingsOptionEnabled(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean unlockContainer(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean unregisterBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public boolean updateProvisioningState(Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IKnoxContainerManager
        public void doSelfUninstall() throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IKnoxContainerManager {
        public static final int TRANSACTION_addConfigurationType = 10;
        public static final int TRANSACTION_addHomeShortcutToPersonal = 50;
        public static final int TRANSACTION_addNetworkSSID = 43;
        public static final int TRANSACTION_addPackageToExternalStorageBlackList = 68;
        public static final int TRANSACTION_addPackageToExternalStorageWhiteList = 69;
        public static final int TRANSACTION_addPackageToInstallWhiteList = 62;
        public static final int TRANSACTION_allowLayoutSwitching = 75;
        public static final int TRANSACTION_cancelCreateContainer = 3;
        public static final int TRANSACTION_checkProvisioningPreCondition = 58;
        public static final int TRANSACTION_clearNetworkSSID = 46;
        public static final int TRANSACTION_clearPackagesFromExternalStorageBlackList = 65;
        public static final int TRANSACTION_clearPackagesFromExternalStorageWhiteList = 73;
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
        public static final int TRANSACTION_getAppSeparationConfig = 78;
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
        public static final int TRANSACTION_getPackageSignaturesFromExternalStorageWhiteList = 72;
        public static final int TRANSACTION_getPackagesFromExternalStorageBlackList = 66;
        public static final int TRANSACTION_getPackagesFromExternalStorageWhiteList = 71;
        public static final int TRANSACTION_getPackagesFromInstallWhiteList = 64;
        public static final int TRANSACTION_getProvisioningState = 60;
        public static final int TRANSACTION_getStatus = 13;
        public static final int TRANSACTION_getStatusInternal = 76;
        public static final int TRANSACTION_isBluetoothEnabled = 32;
        public static final int TRANSACTION_isBluetoothEnabledBeforeFOTA = 33;
        public static final int TRANSACTION_isContactsSharingEnabled = 39;
        public static final int TRANSACTION_isEmergencyModeSupported = 54;
        public static final int TRANSACTION_isExternalStorageEnabled = 41;
        public static final int TRANSACTION_isLayoutSwitchingAllowed = 74;
        public static final int TRANSACTION_isMultifactorAuthenticationEnforced = 25;
        public static final int TRANSACTION_isNFCEnabled = 35;
        public static final int TRANSACTION_isPackageAllowedToAccessExternalSdcard = 52;
        public static final int TRANSACTION_isPackageInInstallWhiteList = 61;
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
        public static final int TRANSACTION_removePackageFromExternalStorageBlackList = 67;
        public static final int TRANSACTION_removePackageFromExternalStorageWhiteList = 70;
        public static final int TRANSACTION_removePackageFromInstallWhiteList = 63;
        public static final int TRANSACTION_resetContainerOnReboot = 29;
        public static final int TRANSACTION_setAppSeparationCoexistentApps = 81;
        public static final int TRANSACTION_setAppSeparationConfig = 79;
        public static final int TRANSACTION_setAppSeparationWhitelistedApps = 80;
        public static final int TRANSACTION_setContactsSharingEnabled = 38;
        public static final int TRANSACTION_setCustomResource = 77;
        public static final int TRANSACTION_setEnforceAuthForContainer = 17;
        public static final int TRANSACTION_setFIDOInfo = 55;
        public static final int TRANSACTION_setHibernationTimeout = 28;
        public static final int TRANSACTION_setSettingsOptionEnabled = 47;
        public static final int TRANSACTION_unlockContainer = 15;
        public static final int TRANSACTION_unregisterBroadcastReceiverIntent = 20;
        public static final int TRANSACTION_updateProvisioningState = 59;

        class Proxy implements IKnoxContainerManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean addConfigurationType(ContextInfo contextInfo, List list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeList(list);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean addHomeShortcutToPersonal(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean addNetworkSSID(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int addPackageToExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int addPackageToExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int addPackageToInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean allowLayoutSwitching(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
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

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean cancelCreateContainer(ContainerCreationParams containerCreationParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(containerCreationParams, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int checkProvisioningPreCondition(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean clearNetworkSSID(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int clearPackagesFromExternalStorageBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int clearPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int createContainer(ContextInfo contextInfo, CreationParams creationParams, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(creationParams, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int createContainerInternal(ContainerCreationParams containerCreationParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(containerCreationParams, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean createContainerMarkSuccess(ContainerCreationParams containerCreationParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(containerCreationParams, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int createContainerWithCallback(ContextInfo contextInfo, CreationParams creationParams, int i, IEnterpriseContainerCallback iEnterpriseContainerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(creationParams, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iEnterpriseContainerCallback);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean deleteHomeShortcutFromPersonal(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public void doSelfUninstall() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean enableBluetooth(ContextInfo contextInfo, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean enableExternalStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean enableNFC(ContextInfo contextInfo, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean enableUsbAccess(ContextInfo contextInfo, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean enforceMultifactorAuthentication(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int forceResetPassword(ContextInfo contextInfo, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public Bundle getAppSeparationConfig() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List getConfigurationType(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List getConfigurationTypeByName(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List getConfigurationTypes(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public ContainerCreationParams getContainerCreationParams(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContainerCreationParams) parcelObtain2.readTypedObject(ContainerCreationParams.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List getContainers(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public String getCustomResource(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List getDefaultConfigurationTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean getEnforceAuthForContainer(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public Bundle getFIDOInfo(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public long getHibernationTimeout(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxContainerManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List<String> getKnoxCustomBadgePolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List<String> getNetworkSSID(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public EnterpriseContainerObject[] getOwnContainers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseContainerObject[]) parcelObtain2.createTypedArray(EnterpriseContainerObject.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List<String> getPackageSignaturesFromExternalStorageWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List<String> getPackagesFromExternalStorageBlackList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List<String> getPackagesFromExternalStorageWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public List<String> getPackagesFromInstallWhiteList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public Bundle getProvisioningState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int getStatus(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int getStatusInternal(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isBluetoothEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isBluetoothEnabledBeforeFOTA(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isContactsSharingEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isEmergencyModeSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isExternalStorageEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isLayoutSwitchingAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isMultifactorAuthenticationEnforced(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isNFCEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isPackageAllowedToAccessExternalSdcard(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isPackageInInstallWhiteList(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isResetContainerOnRebootEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isSettingsOptionEnabled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isSettingsOptionEnabledInternal(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean isUsbAccessEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean lockContainer(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean registerBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean removeConfigurationType(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int removeContainer(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int removeContainerInternal(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean removeNetworkSSID(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int removePackageFromExternalStorageBlackList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int removePackageFromExternalStorageWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int removePackageFromInstallWhiteList(ContextInfo contextInfo, AppIdentity appIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(appIdentity, 0);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean resetContainerOnReboot(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean setAppSeparationCoexistentApps(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean setAppSeparationConfig(ContextInfo contextInfo, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean setAppSeparationWhitelistedApps(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean setContactsSharingEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public int setCustomResource(int i, ContextInfo contextInfo, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean setEnforceAuthForContainer(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean setFIDOInfo(ContextInfo contextInfo, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean setHibernationTimeout(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean setSettingsOptionEnabled(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean unlockContainer(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean unregisterBroadcastReceiverIntent(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IKnoxContainerManager
            public boolean updateProvisioningState(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxContainerManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxContainerManager.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxContainerManager)) ? new Proxy(iBinder) : (IKnoxContainerManager) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCreateContainer = createContainer(contextInfo, creationParams, i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateContainer);
                    return true;
                case 2:
                    ContainerCreationParams containerCreationParams = (ContainerCreationParams) parcel.readTypedObject(ContainerCreationParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iCreateContainerInternal = createContainerInternal(containerCreationParams);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateContainerInternal);
                    return true;
                case 3:
                    ContainerCreationParams containerCreationParams2 = (ContainerCreationParams) parcel.readTypedObject(ContainerCreationParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zCancelCreateContainer = cancelCreateContainer(containerCreationParams2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCancelCreateContainer);
                    return true;
                case 4:
                    ContainerCreationParams containerCreationParams3 = (ContainerCreationParams) parcel.readTypedObject(ContainerCreationParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zCreateContainerMarkSuccess = createContainerMarkSuccess(containerCreationParams3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCreateContainerMarkSuccess);
                    return true;
                case 5:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemoveContainer = removeContainer(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveContainer);
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRemoveContainerInternal = removeContainerInternal(i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveContainerInternal);
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List configurationTypeByName = getConfigurationTypeByName(contextInfo4, string);
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
                    ArrayList arrayList = parcel.readArrayList(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    boolean zAddConfigurationType = addConfigurationType(contextInfo6, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddConfigurationType);
                    return true;
                case 11:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List configurationType = getConfigurationType(contextInfo7, i5);
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
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zLockContainer = lockContainer(contextInfo9, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zLockContainer);
                    return true;
                case 15:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUnlockContainer = unlockContainer(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnlockContainer);
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
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enforceAuthForContainer2 = setEnforceAuthForContainer(contextInfo12, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enforceAuthForContainer2);
                    return true;
                case 18:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ContainerCreationParams containerCreationParams4 = getContainerCreationParams(i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(containerCreationParams4, 1);
                    return true;
                case 19:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterBroadcastReceiverIntent = registerBroadcastReceiverIntent(contextInfo13, string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterBroadcastReceiverIntent);
                    return true;
                case 20:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterBroadcastReceiverIntent = unregisterBroadcastReceiverIntent(contextInfo14, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterBroadcastReceiverIntent);
                    return true;
                case 21:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    CreationParams creationParams2 = (CreationParams) parcel.readTypedObject(CreationParams.CREATOR);
                    int i7 = parcel.readInt();
                    IEnterpriseContainerCallback iEnterpriseContainerCallbackAsInterface = IEnterpriseContainerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iCreateContainerWithCallback = createContainerWithCallback(contextInfo15, creationParams2, i7, iEnterpriseContainerCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateContainerWithCallback);
                    return true;
                case 22:
                    EnterpriseContainerObject[] ownContainers = getOwnContainers();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(ownContainers, 1);
                    return true;
                case 23:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string7 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iForceResetPassword = forceResetPassword(contextInfo16, string7, i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iForceResetPassword);
                    return true;
                case 24:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnforceMultifactorAuthentication = enforceMultifactorAuthentication(contextInfo17, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnforceMultifactorAuthentication);
                    return true;
                case 25:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsMultifactorAuthenticationEnforced = isMultifactorAuthenticationEnforced(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMultifactorAuthenticationEnforced);
                    return true;
                case 26:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveConfigurationType = removeConfigurationType(contextInfo19, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveConfigurationType);
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
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean hibernationTimeout2 = setHibernationTimeout(contextInfo21, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hibernationTimeout2);
                    return true;
                case 29:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zResetContainerOnReboot = resetContainerOnReboot(contextInfo22, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetContainerOnReboot);
                    return true;
                case 30:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsResetContainerOnRebootEnabled = isResetContainerOnRebootEnabled(contextInfo23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsResetContainerOnRebootEnabled);
                    return true;
                case 31:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zEnableBluetooth = enableBluetooth(contextInfo24, z4, bundle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableBluetooth);
                    return true;
                case 32:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothEnabled = isBluetoothEnabled(contextInfo25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothEnabled);
                    return true;
                case 33:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBluetoothEnabledBeforeFOTA = isBluetoothEnabledBeforeFOTA(contextInfo26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBluetoothEnabledBeforeFOTA);
                    return true;
                case 34:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zEnableNFC = enableNFC(contextInfo27, z5, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableNFC);
                    return true;
                case 35:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsNFCEnabled = isNFCEnabled(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNFCEnabled);
                    return true;
                case 36:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zEnableUsbAccess = enableUsbAccess(contextInfo29, z6, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableUsbAccess);
                    return true;
                case 37:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsUsbAccessEnabled = isUsbAccessEnabled(contextInfo30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbAccessEnabled);
                    return true;
                case 38:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean contactsSharingEnabled = setContactsSharingEnabled(contextInfo31, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(contactsSharingEnabled);
                    return true;
                case 39:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsContactsSharingEnabled = isContactsSharingEnabled(contextInfo32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsContactsSharingEnabled);
                    return true;
                case 40:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableExternalStorage = enableExternalStorage(contextInfo33, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableExternalStorage);
                    return true;
                case 41:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsExternalStorageEnabled = isExternalStorageEnabled(contextInfo34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsExternalStorageEnabled);
                    return true;
                case 42:
                    doSelfUninstall();
                    parcel2.writeNoException();
                    return true;
                case 43:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddNetworkSSID = addNetworkSSID(contextInfo35, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNetworkSSID);
                    return true;
                case 44:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveNetworkSSID = removeNetworkSSID(contextInfo36, string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveNetworkSSID);
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
                    boolean zClearNetworkSSID = clearNetworkSSID(contextInfo38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearNetworkSSID);
                    return true;
                case 47:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string11 = parcel.readString();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean settingsOptionEnabled = setSettingsOptionEnabled(contextInfo39, string11, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(settingsOptionEnabled);
                    return true;
                case 48:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSettingsOptionEnabled = isSettingsOptionEnabled(contextInfo40, string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSettingsOptionEnabled);
                    return true;
                case 49:
                    int i9 = parcel.readInt();
                    String string13 = parcel.readString();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsSettingsOptionEnabledInternal = isSettingsOptionEnabledInternal(i9, string13, z10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSettingsOptionEnabledInternal);
                    return true;
                case 50:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddHomeShortcutToPersonal = addHomeShortcutToPersonal(contextInfo41, string14, string15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddHomeShortcutToPersonal);
                    return true;
                case 51:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteHomeShortcutFromPersonal = deleteHomeShortcutFromPersonal(contextInfo42, string16, string17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteHomeShortcutFromPersonal);
                    return true;
                case 52:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageAllowedToAccessExternalSdcard = isPackageAllowedToAccessExternalSdcard(contextInfo43, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageAllowedToAccessExternalSdcard);
                    return true;
                case 53:
                    List<String> knoxCustomBadgePolicy = getKnoxCustomBadgePolicy();
                    parcel2.writeNoException();
                    parcel2.writeStringList(knoxCustomBadgePolicy);
                    return true;
                case 54:
                    boolean zIsEmergencyModeSupported = isEmergencyModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEmergencyModeSupported);
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
                    int i11 = parcel.readInt();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String customResource = getCustomResource(i11, string18);
                    parcel2.writeNoException();
                    parcel2.writeString(customResource);
                    return true;
                case 58:
                    String string19 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckProvisioningPreCondition = checkProvisioningPreCondition(string19, i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckProvisioningPreCondition);
                    return true;
                case 59:
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUpdateProvisioningState = updateProvisioningState(bundle5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateProvisioningState);
                    return true;
                case 60:
                    Bundle provisioningState = getProvisioningState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(provisioningState, 1);
                    return true;
                case 61:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageInInstallWhiteList = isPackageInInstallWhiteList(contextInfo46, string20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageInInstallWhiteList);
                    return true;
                case 62:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddPackageToInstallWhiteList = addPackageToInstallWhiteList(contextInfo47, appIdentity);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPackageToInstallWhiteList);
                    return true;
                case 63:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity2 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemovePackageFromInstallWhiteList = removePackageFromInstallWhiteList(contextInfo48, appIdentity2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePackageFromInstallWhiteList);
                    return true;
                case 64:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromInstallWhiteList = getPackagesFromInstallWhiteList(contextInfo49);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromInstallWhiteList);
                    return true;
                case 65:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iClearPackagesFromExternalStorageBlackList = clearPackagesFromExternalStorageBlackList(contextInfo50);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearPackagesFromExternalStorageBlackList);
                    return true;
                case 66:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromExternalStorageBlackList = getPackagesFromExternalStorageBlackList(contextInfo51);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromExternalStorageBlackList);
                    return true;
                case 67:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity3 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemovePackageFromExternalStorageBlackList = removePackageFromExternalStorageBlackList(contextInfo52, appIdentity3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePackageFromExternalStorageBlackList);
                    return true;
                case 68:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity4 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddPackageToExternalStorageBlackList = addPackageToExternalStorageBlackList(contextInfo53, appIdentity4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPackageToExternalStorageBlackList);
                    return true;
                case 69:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity5 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddPackageToExternalStorageWhiteList = addPackageToExternalStorageWhiteList(contextInfo54, appIdentity5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPackageToExternalStorageWhiteList);
                    return true;
                case 70:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AppIdentity appIdentity6 = (AppIdentity) parcel.readTypedObject(AppIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemovePackageFromExternalStorageWhiteList = removePackageFromExternalStorageWhiteList(contextInfo55, appIdentity6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemovePackageFromExternalStorageWhiteList);
                    return true;
                case 71:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromExternalStorageWhiteList = getPackagesFromExternalStorageWhiteList(contextInfo56);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromExternalStorageWhiteList);
                    return true;
                case 72:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packageSignaturesFromExternalStorageWhiteList = getPackageSignaturesFromExternalStorageWhiteList(contextInfo57, string21);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packageSignaturesFromExternalStorageWhiteList);
                    return true;
                case 73:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iClearPackagesFromExternalStorageWhiteList = clearPackagesFromExternalStorageWhiteList(contextInfo58);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearPackagesFromExternalStorageWhiteList);
                    return true;
                case 74:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsLayoutSwitchingAllowed = isLayoutSwitchingAllowed(contextInfo59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLayoutSwitchingAllowed);
                    return true;
                case 75:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowLayoutSwitching = allowLayoutSwitching(contextInfo60, z11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowLayoutSwitching);
                    return true;
                case 76:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int statusInternal = getStatusInternal(i13);
                    parcel2.writeNoException();
                    parcel2.writeInt(statusInternal);
                    return true;
                case 77:
                    int i14 = parcel.readInt();
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int customResource2 = setCustomResource(i14, contextInfo61, bundle6);
                    parcel2.writeNoException();
                    parcel2.writeInt(customResource2);
                    return true;
                case 78:
                    Bundle appSeparationConfig = getAppSeparationConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appSeparationConfig, 1);
                    return true;
                case 79:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean appSeparationConfig2 = setAppSeparationConfig(contextInfo62, bundle7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appSeparationConfig2);
                    return true;
                case 80:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean appSeparationWhitelistedApps = setAppSeparationWhitelistedApps(contextInfo63, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appSeparationWhitelistedApps);
                    return true;
                case 81:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean appSeparationCoexistentApps = setAppSeparationCoexistentApps(contextInfo64, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appSeparationCoexistentApps);
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
}
